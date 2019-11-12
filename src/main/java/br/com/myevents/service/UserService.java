package br.com.myevents.service;

import br.com.myevents.exception.CPFExistsException;
import br.com.myevents.exception.EmailExistsException;
import br.com.myevents.exception.TokenExpiredException;
import br.com.myevents.exception.TokenNotFoundException;
import br.com.myevents.exception.UserNotFoundException;
import br.com.myevents.model.ActivationToken;
import br.com.myevents.model.PasswordResetToken;
import br.com.myevents.model.User;
import br.com.myevents.model.dto.NewPasswordDTO;
import br.com.myevents.model.dto.NewUserDTO;
import br.com.myevents.model.dto.SimpleUserDTO;
import br.com.myevents.repository.ActivationTokenRepository;
import br.com.myevents.repository.PasswordResetTokenRepository;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.security.enums.Role;
import br.com.myevents.utils.SimpleMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Implementa a lógica de serviços de {@link User}.
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final ActivationTokenRepository activationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    @Value("${website.url}")
    private String WEBSITE_URL;

    /**
     * Registra um novo usuário.
     *
     * @param newUser o novo usuário
     * @return o resultado
     */
    public SimpleMessage registerUser(NewUserDTO newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new EmailExistsException("O email já está sendo usado por outro usuário.");
        }

        if (userRepository.existsByCPF(newUser.getCPF())) {
            throw new CPFExistsException("O CPF já está sendo usado por outro usuário.");
        }

        User user = userRepository.save(User.builder()
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .name(newUser.getName())
                .CPF(newUser.getCPF())
                .phone(newUser.getPhone())
                .role(Role.USER)
                .build());

        mailSenderService.sendHtml(
                user.getEmail(),
                "Verificação de Conta MyEvents",
                String.format(
                        "Olá %1$s, uma conta foi registrada com o seu email no site <a href='%2$s'>MyEvents</a>, " +
                                "para ativá-la siga este <a href='%2$sactivate;token=%3$s'>link</a>. " +
                                "Caso você não tenha criado uma conta neste site ignore esta mensagem. " +
                                "<strong>Este token de ativação dura apenas 72 horas.</strong>",
                        user.getName(),
                        WEBSITE_URL,
                        activationTokenRepository.save(new ActivationToken(user)).getToken()));

        return new SimpleMessage("Registrado com sucesso! Verifique sua caixa de entrada e ative sua conta.");
    }

    /**
     * Retorna as informações básicas de um usuário da base de dados a partir do seu email.
     *
     * @param email o email do usuário
     * @return as informações básicas do usuário
     */
    public SimpleUserDTO retrieveSimpleUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("O email não está vinculado a nenhum usuário."));

        return new SimpleUserDTO(user.getEmail(), user.getName(), user.getPhone());
    }

    /**
     * Ativa uma conta de usuário através do seu token de ativação.
     *
     * @param token o token de ativação
     * @return o resultado
     */
    public SimpleMessage activateUserAccount(String token) {
        ActivationToken activationToken = activationTokenRepository.findByToken(token).orElseThrow(
                () -> new TokenNotFoundException("Token de ativação inválido."));

        if (activationToken.getExpiration().isBefore(Instant.now())) {
            throw new TokenExpiredException("Token de ativação expirado.");
        }

        User user = activationToken.getUser();

        user.setEnabled(true);
        userRepository.save(user);

        activationTokenRepository.deleteAllByUser_Id(user.getId());

        mailSenderService.sendHtml(
                user.getEmail(),
                "Ativação de Conta MyEvents",
                String.format(
                        "Olá %1$s, sua conta no site " +
                                "<a href='%2$s'>MyEvents</a> foi ativada com sucesso, " +
                                "<a href='%2$ssignin'>clique aqui</a> para realizar login.",
                        user.getName(),
                        WEBSITE_URL));

        return new SimpleMessage("Conta ativada.");
    }

    /**
     * Reenvia a mensagem de email com um link para realizar a ativação de uma conta de usuário.
     *
     * @param email o email da conta de usuário
     * @return o resultado
     */
    public SimpleMessage resendUserAccountActivation(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("O email não está vinculado a nenhum usuário."));

        if (user.isEnabled()) {
            return new SimpleMessage("Conta já foi ativada.");
        }

        mailSenderService.sendHtml(
                user.getEmail(),
                "Verificação de Conta MyEvents",
                String.format("Olá %1$s, um novo token de confirmação foi solicitado para a sua conta em " +
                                "<a href='%2$s/'>MyEvents</a>, para ativá-la siga este " +
                                "<a href='%2$sactivate;token=%3$s'>link</a>. " +
                                "Caso você não tenha criado uma conta neste site ignore esta mensagem. " +
                                "<strong>Este token de ativação dura apenas 72 horas.</strong>",
                        user.getName(),
                        WEBSITE_URL,
                        activationTokenRepository.save(new ActivationToken(user)).getToken()));

        return new SimpleMessage(
                "Mensagem enviada! Verifique sua caixa de entrada e siga o link para ativar sua conta.");
    }

    /**
     * Realiza a redefinição de senha de uma conta de usuário.
     *
     * @param token o token de redefinição de senha
     * @param newPassword a nova senha
     * @return o resultado
     */
    public SimpleMessage resetUserAccountPassword(String token, NewPasswordDTO newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(
                () -> new TokenNotFoundException("Token de redefinição de senha inválido."));

        if (passwordResetToken.getExpiration().isBefore(Instant.now())) {
            throw new TokenExpiredException("Token de redefinição de senha expirado.");
        }

        User user = passwordResetToken.getUser();

        user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        userRepository.save(user);

        passwordResetTokenRepository.deleteAllByUser_Id(user.getId());

        return new SimpleMessage("Sua senha foi atualizada.");
    }

    /**
     * Envia uma mensagem de email com um link para realizar a redefinição de senha de uma conta de usuário.
     *
     * @param email o email da conta de usuário
     * @return o resultado
     */
    public SimpleMessage sendUserAccountPasswordReset(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("O email não está vinculado a nenhum usuário."));

        mailSenderService.sendHtml(
                user.getEmail(),
                "Redefinição de Senha MyEvents",
                String.format(
                        "Olá %1$s, um token de redefinição de senha foi solicitado para a sua conta em " +
                                "<a href='%2$s'>MyEvents</a>, para atualizar sua senha siga este " +
                                "<a href='%2$spassword-reset;token=%3$s'>link</a>. " +
                                "Caso você não tenha requisitado uma redefinição de senha ignore esta mensagem. " +
                                "<strong>Este token de redefinição de senha dura apenas 24 horas.</strong>",
                        user.getName(),
                        WEBSITE_URL,
                        passwordResetTokenRepository.save(new PasswordResetToken(user)).getToken()));

        return new SimpleMessage(
                "Mensagem enviada! Verifique sua caixa de entrada e siga o link para redefinir sua senha.");
    }

}
