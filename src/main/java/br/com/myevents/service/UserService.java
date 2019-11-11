package br.com.myevents.service;

import br.com.myevents.exception.CPFExistsException;
import br.com.myevents.exception.EmailExistsException;
import br.com.myevents.exception.EmailNotFoundException;
import br.com.myevents.exception.TokenExpiredException;
import br.com.myevents.exception.TokenNotFoundException;
import br.com.myevents.exception.TokenUserNotFoundException;
import br.com.myevents.exception.UserAccountNotFoundException;
import br.com.myevents.model.ActivationToken;
import br.com.myevents.model.PasswordResetToken;
import br.com.myevents.model.User;
import br.com.myevents.model.dto.NewPasswordDTO;
import br.com.myevents.model.dto.NewUserDTO;
import br.com.myevents.model.dto.SimpleMessage;
import br.com.myevents.model.dto.SimpleUserDTO;
import br.com.myevents.repository.ActivationTokenRepository;
import br.com.myevents.repository.PasswordResetTokenRepository;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.security.enums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

/**
 * Implementa a lógica de serviços de {@link User}.
 */
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final ActivationTokenRepository activationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    /**
     * Registra um novo usuário.
     *
     * @param newUser o novo usuário
     */
    public void registerUser(NewUserDTO newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new EmailExistsException(
                    String.format("O email '%s' já está vinculado a um usuário.", newUser.getEmail()));
        }

        if (userRepository.findByCPF(newUser.getCPF()).isPresent()) {
            throw new CPFExistsException(
                    String.format("O CPF '%s' já está vinculado a um usuário.", newUser.getCPF()));
        }

        // após passar pelas validações, salvar o usuário (desativado) na base de dados
        User user = userRepository.save(User.builder()
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .name(newUser.getName())
                .CPF(newUser.getCPF())
                .phone(newUser.getPhone())
                .role(Role.USER)
                .build());

        // enviar mensagem de confirmação com o link para a ativação de conta para o email do usuário
        mailSenderService.sendHtml(
                user.getEmail(),
                "Verificação de Conta MyEvents",
                String.format(
                        "Olá %s, uma conta foi registrada com o seu email no site " +
                                "<a href='http://localhost:4200/'>MyEvents</a>, para ativá-la " +
                                "<a href='http://localhost:4200/activate;token=%s'>clique aqui</a>. " +
                                "Caso voçê não tenha criado uma conta neste site ignore esta mensagem.",
                        user.getName(),
                        // criar e salvar o token de confirmação para a conta de usuário na base de dados
                        activationTokenRepository.save(ActivationToken.builder().user(user).build())
                                .getValue()));
    }

    /**
     * Retorna as informações básicas de uma conta de usuário da base de dados a partir do seu email.
     *
     * @param email o email
     * @return o usuário
     */
    public SimpleUserDTO retrieveSimpleUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(
                        String.format("O email '%s' não está vinculado a nenhum usuário conhecido.", email)));

        return SimpleUserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.phoneRepr())
                .build();
    }

    /**
     * Ativa uma conta de usuário através do seu token de ativação.
     *
     * @param token o token de ativação
     * @return o resultado
     */
    public SimpleMessage activateUserAccount(String token) {
        ActivationToken activationToken = activationTokenRepository.findByValue(token).orElseThrow(
                () -> new TokenNotFoundException("O token de ativação não existe."));

        if (activationToken.getExpiration().isBefore(Instant.now())) {
            throw new TokenExpiredException("O token de confirmação expirou.");
        }

        User user = Optional.of(activationToken.getUser()).orElseThrow(
                () -> new TokenUserNotFoundException("O token de ativação não está vinculado a nenhum usuário."));

        // ativar a conta do usuário
        user.setEnabled(true);
        userRepository.save(user);

        // com a conta do usuário ativada podemos remover todos os tokens de confirmação vinculados a ela
        activationTokenRepository.findAllByUser(user).forEach(activationTokenRepository::delete);

        // enviar mensagem confirmando que a conta foi ativada para o email do usuário
        mailSenderService.sendHtml(
                user.getEmail(),
                "Ativação de Conta MyEvents",
                String.format(
                        "Olá %s, sua conta no site " +
                                "<a href='http://localhost:4200/'>MyEvents</a> foi ativada com sucesso, " +
                                "<a href='http://localhost:4200/signin'>clique aqui</a> para entrar.",
                        user.getName()));

        return SimpleMessage.builder().message("Sua conta foi ativada.").build();
    }

    /**
     * Reenvia a mensagem de email com um link de ativação de conta de usuário.
     *
     * @param email o email da conta de usuário
     * @return o resultado
     */
    public SimpleMessage resendUserAccountActivation(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserAccountNotFoundException("O email não pertence a nenhuma conta de usuário conhecido."));

        // não fazer nada e retornar uma mensagem caso a conta de usuário já esteja ativada
        if (user.isEnabled()) {
            return SimpleMessage.builder().message("Sua conta já está ativada.").build();
        }

        // reenviar mensagem de confirmação com o link para a ativação de conta para o email do usuário
        mailSenderService.sendHtml(
                user.getEmail(),
                "Verificação de Conta MyEvents",
                String.format(
                        "Olá %s, um novo token de confirmação foi requisitado para a sua conta em " +
                                "<a href='http://localhost:4200/'>MyEvents</a>, para ativá-la " +
                                "<a href='http://localhost:4200/activate;token=%s'>clique aqui</a>. " +
                                "Caso voçê não tenha criado uma conta neste site ignore esta mensagem.",
                        user.getName(),
                        // criar e salvar um novo token de confirmação para a conta de usuário na base de dados
                        activationTokenRepository.save(ActivationToken.builder().user(user).build()).getValue()));

        return SimpleMessage.builder()
                .message("Mensagem enviada! Verifique seu email e siga o link para ativar sua conta.")
                .build();
    }

    /**
     * Realiza a redefinição de senha da conta de usuário.
     *
     * @param token o token
     * @param newPassword a nova senha
     * @return o resultado
     */
    public SimpleMessage resetUserAccountPassword(String token, NewPasswordDTO newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByValue(token).orElseThrow(
                () -> new TokenNotFoundException("O token de redefinição de senha não existe."));

        if (passwordResetToken.getExpiration().isBefore(Instant.now())) {
            throw new TokenExpiredException("O token de redefinição de senha expirou.");
        }

        User user = Optional.of(passwordResetToken.getUser()).orElseThrow(
                () -> new TokenUserNotFoundException(
                        "O token de redefinição de senha não está vinculado a nenhuma conta de usuário."));

        // atualizar a senha do usuário
        user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        userRepository.save(user);

        // com a senha do usuário atualizada podemos remover todos os tokens de redefinição de senha vinculados a ele
        passwordResetTokenRepository.findAllByUser(user).forEach(passwordResetTokenRepository::delete);

        return SimpleMessage.builder().message("Sua senha foi atualizada.").build();
    }

    /**
     * Envia uma mensagem de email com um link de redefinição de senha da conta de usuário.
     *
     * @param email o email da conta de usuário
     * @return o resultado
     */
    public SimpleMessage sendUserAccountPasswordReset(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserAccountNotFoundException("O email não pertence a nenhuma conta de usuário conhecida."));

        // enviar mensagme com o link para a página de redefinição de senha
        mailSenderService.sendHtml(
                user.getEmail(),
                "Redefinição de Senha MyEvents",
                String.format(
                        "Olá %s, um token de redefinição de senha foi requisitado para a sua conta em " +
                                "<a href='http://localhost:4200/'>MyEvents</a>, para atualizar sua senha " +
                                "<a href='http://localhost:4200/password-reset;token=%s'>clique aqui</a>. " +
                                "Caso voçê não tenha requisitado uma redefinição de senha ignore esta mensagem.",
                        user.getName(),
                        // criar e salvar um novo token de redefinição de senha para a conta de usuário na base de dados
                        passwordResetTokenRepository.save(PasswordResetToken.builder().user(user).build()).getValue()));

        return SimpleMessage.builder()
                .message("Mensagem enviada! Verifique seu email e siga o link para redefinir sua senha.")
                .build();
    }

}
