package br.com.myevents.service;

import br.com.myevents.exception.CPFExistsException;
import br.com.myevents.exception.ConfirmationTokenExpiredException;
import br.com.myevents.exception.ConfirmationTokenNotFoundException;
import br.com.myevents.exception.ConfirmationTokenUserNotFoundException;
import br.com.myevents.exception.EmailExistsException;
import br.com.myevents.exception.EmailNotFoundException;
import br.com.myevents.model.ConfirmationToken;
import br.com.myevents.model.User;
import br.com.myevents.model.dto.NewUserDTO;
import br.com.myevents.repository.ConfirmationTokenRepository;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.security.enums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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
    private final JavaMailSender mailSender;

    /**
     * Registra um novo usuário validado. (não realiza validação de dados)
     *
     * @param newUser o novo usuário
     * @return um usuário registrado
     * @throws EmailExistsException levantada sempre que um email já esteja vinculado a uma conta de usuário
     * @throws CPFExistsException levantada sempre que um CPF já esteja vinculado a uma conta de usuário
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
        this.sendConfirmationEmail(user);

        return user;
    }

    /**
     * Retorna um usuário da base de dados a partir do seu email.
     *
     * @param email o email
     * @return o usuário
     * @throws EmailNotFoundException levantada sempre que nenhum usuário esteja vinculado ao email
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
        try {
            MimeMailMessage message = new MimeMailMessage(mailSender.createMimeMessage());
            message.setTo(user.getEmail());
            message.setSubject("Ativação de Conta MyEvents");
            message.getMimeMessageHelper().setText(
                    String.format("Olá %s, sua conta no site " +
                            "<a href='http://localhost:4200/'>MyEvents</a> foi ativada com sucesso, " +
                            "<a href='http://localhost:4200/user/signin'>clique aqui</a> para entrar.",
                            user.getName()),
                    true);

            mailSender.send(message.getMimeMessage());
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }

        return String.format(
                "{\"message\": \"A conta de usuário com email '%s' foi ativada.\"}", user.getEmail());
    }

    /**
     * Envia o email de confirmação para a conta de usuário.
     *
     * @param user o usuário
     */
    private void sendConfirmationEmail(User user) {
        try {
            MimeMailMessage message = new MimeMailMessage(mailSender.createMimeMessage());
            message.setTo(user.getEmail());
            message.setSubject("Verificação de Conta MyEvents");
            message.getMimeMessageHelper().setText(
                    String.format("Olá %s, uma conta foi registrada com o seu email no site " +
                                    "<a href='http://localhost:4200/'>MyEvents</a>, para ativá-la " +
                                    "<a href='http://localhost:8080/user/confirm?token=%s'>clique aqui</a>. " +
                                    "Caso voçê não tenha criado uma conta neste site ignore esta mensagem.",
                            user.getName(),
                            // criar e salvar um novo token de confirmação para a conta de usuário na base de dados
                            confirmationTokenRepository.save(ConfirmationToken.builder().user(user).build())
                                    .getToken()),
                    true);

            mailSender.send(message.getMimeMessage());
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
    }

}
