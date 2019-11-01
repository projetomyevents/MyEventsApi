package br.com.myevents.service;

import br.com.myevents.exception.CPFExistsException;
import br.com.myevents.exception.ConfirmationTokenExpiredException;
import br.com.myevents.exception.ConfirmationTokenNotFoundException;
import br.com.myevents.exception.ConfirmationTokenUserNotFoundException;
import br.com.myevents.exception.EmailExistsException;
import br.com.myevents.exception.EmailNotFoundException;
import br.com.myevents.exception.UserAccountNotFoundException;
import br.com.myevents.model.ConfirmationToken;
import br.com.myevents.model.User;
import br.com.myevents.model.dto.NewUserDTO;
import br.com.myevents.model.dto.UserAccountDTO;
import br.com.myevents.repository.ConfirmationTokenRepository;
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
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    /**
     * Registra um novo usuário validado. (não realiza validação de dados)
     *
     * @param newUser o novo usuário
     * @return um usuário registrado
     */
    public User registerUser(NewUserDTO newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new EmailExistsException(
                    String.format("O email '%s' já está vinculado a uma conta de usuário.", newUser.getEmail()));
        }

        if (userRepository.findByCPF(newUser.getCPF()).isPresent()) {
            throw new CPFExistsException(
                    String.format("O CPF '%s' já está vinculado a uma conta de usuário.", newUser.getCPF()));
        }

        // após passar pelas validações, salvar o usuário (desativado) na base de dados
        User user = userRepository.save(User.builder()
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .name(newUser.getName())
                .CPF(newUser.getCPF())
                .phone(newUser.getPhone())
                .role(Role.USUARIO.getId())
                .build());

        // enviar mensagem de confirmação com o link para a ativação de conta para o email do usuário
        mailSenderService.sendHtml(
                user.getEmail(),
                "Verificação de Conta MyEvents",
                String.format(
                        "Olá %s, uma conta foi registrada com o seu email no site " +
                                "<a href='http://localhost:4200/'>MyEvents</a>, para ativá-la " +
                                "<a href='http://localhost:8080/user/confirm?token=%s'>clique aqui</a>. " +
                                "Caso voçê não tenha criado uma conta neste site ignore esta mensagem.",
                        user.getName(),
                        // criar e salvar o token de confirmação para a conta de usuário na base de dados
                        confirmationTokenRepository.save(ConfirmationToken.builder().user(user).build())
                                .getToken()));

        return user;
    }

    /**
     * Retorna um usuário da base de dados a partir do seu email.
     *
     * @param email o email
     * @return o usuário
     */
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(
                        String.format("O email '%s' não está vinculado a nenhum usuário conhecido.", email)));
    }

    /**
     * Tenta ativar uma conta de usuário através de um token de confirmação.
     *
     * @param token o token de confirmação
     * @return o resultado
     */
    public String confirmUser(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(
                () -> new ConfirmationTokenNotFoundException(
                        String.format("O token de confirmação '%s' não existe.", token)));

        if (confirmationToken.getExpiration().isBefore(Instant.now())) {
            throw new ConfirmationTokenExpiredException(String.format("O token de confirmação '%s' expirou.", token));
        }

        User user = Optional.of(confirmationToken.getUser()).orElseThrow(
                () -> new ConfirmationTokenUserNotFoundException(
                        String.format("O token de confirmação '%s' não está vinculado a nenhuma conta de usuário.",
                                token)));

        // ativar a conta do usuário
        user.setEnabled(true);
        userRepository.save(user);

        // com a conta do usuário ativada podemos remover todos os tokens vinculados a ela
        confirmationTokenRepository.findAllByUser(user).forEach(confirmationTokenRepository::delete);

        // enviar mensagem confirmando que a conta foi ativada para o email do usuário
        mailSenderService.sendHtml(
                user.getEmail(),
                "Ativação de Conta MyEvents",
                String.format(
                        "Olá %s, sua conta no site " +
                                "<a href='http://localhost:4200/'>MyEvents</a> foi ativada com sucesso, " +
                                "<a href='http://localhost:4200/signin'>clique aqui</a> para entrar.",
                        user.getName()));

        return String.format(
                "{\"message\": \"A conta de usuário com email '%s' foi ativada.\"}", user.getEmail());
    }

    /**
     * Reenvia o email de confirmação da conta de usuário.
     *
     * @param userAccount a conta de usuário
     * @return o resultado
     */
    public String resendUserConfirmation(UserAccountDTO userAccount) {
        User user = userRepository.findByEmail(userAccount.getEmail()).orElseThrow(UserAccountNotFoundException::new);

        // por algum motivo que eu desconheço isso não funciona
//        if (passwordEncoder.matches(userAccount.getPassword(), user.getPassword())) {
//            throw new UserAccountNotFoundException();
//        }

        // não fazer nada e retornar uma mensagem caso a conta de usuário já esteja ativada
        if (user.isEnabled()) {
            return String.format(
                    "{\"message\": \"A conta de usuário com o email '%s' já está ativada.\"}", user.getEmail());
        }

        // reenviar mensagem de confirmação com o link para a ativação de conta para o email do usuário
        mailSenderService.sendHtml(
                user.getEmail(),
                "Verificação de Conta MyEvents",
                String.format(
                        "Olá %s, um novo token de confirmação foi requisitado para a sua conta em " +
                                "<a href='http://localhost:4200/'>MyEvents</a>, para ativá-la " +
                                "<a href='http://localhost:8080/user/confirm?token=%s'>clique aqui</a>. " +
                                "Caso voçê não tenha criado uma conta neste site ignore esta mensagem.",
                        user.getName(),
                        // criar e salvar um novo token de confirmação para a conta de usuário na base de dados
                        confirmationTokenRepository.save(ConfirmationToken.builder().user(user).build())
                                .getToken()));

        return String.format(
                "{\"message\": \"A mensagem com o link de ativação da conta foi enviada para '%s'.\"}",
                user.getEmail());
    }

}
