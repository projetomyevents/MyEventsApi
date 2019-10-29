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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

        User user = userRepository.save(User.builder()
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .name(newUser.getName())
                .CPF(newUser.getCPF())
                .phone(newUser.getPhone())
                .role(Role.USUARIO.getId())
                .build());

        // criar e salvar token de confirmação de conta de usuário
        ConfirmationToken confirmationToken = confirmationTokenRepository.save(ConfirmationToken.builder()
                .user(user)
                .build());

        // enviar mensagem de confirmação para o email do usuário
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Verificação de Conta MyEvents");
            messageHelper.setText(String.format("Olá %s, percebemos que você se cadastrou no site " +
                            "<a href='http://localhost:4200/'>MyEvents</a>, para ativar sua conta " +
                            "<a href='http://localhost:8080/user/confirm?token=%s'>clique aqui</a>.",
                    user.getName(),
                    confirmationToken.getToken()), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Verificação de Conta MyEvents");
            message.setText(String.format("Olá %s, percebemos que você se cadastrou no site MyEvents " +
                            "(http://localhost:4200/), para ativar sua conta siga esse link: " +
                            "http://localhost:8080/user/confirm?token=%s.",
                    user.getName(),
                    confirmationToken.getToken()));

            mailSender.send(message);
        }

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
        Optional<User> optionalUser =  userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new EmailNotFoundException(
                    String.format("O email '%s' não está vinculado a nenhum usuário conhecido.", email));
        }
    }

    public String confirmUser(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(
                () -> new ConfirmationTokenNotFoundException(
                        String.format("O token de confirmação '%s' não existe.", token)));

        if (confirmationToken.getExpiration().isBefore(Instant.now())) {
            throw new ConfirmationTokenExpiredException(String.format("O token de confirmação '%s' expirou.", token));
        }

        User user = userRepository.findByEmail(confirmationToken.getUser().getEmail()).orElseThrow(
                () -> new ConfirmationTokenUserNotFoundException(
                        String.format("O token de confirmação '%s' não está vinculado a nenhuma conta de usuário.",
                                token)));
        user.setEnabled(true);
        userRepository.save(user);

        // enviar mensagem confirmando que a conta do usuário foi ativada
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Sua Conta MyEvents foi Ativada");
            messageHelper.setText(String.format("Olá %s, sua conta no site " +
                            "<a href='http://localhost:4200/'>MyEvents</a> foi ativada com sucesso.",
                    user.getName()), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Verificação de Conta MyEvents");
            message.setText(String.format("Olá %s, sua conta no site MyEvents " +
                            "(http://localhost:4200/) foi ativada com sucesso.",
                    user.getName()));

            mailSender.send(message);
        }

        return String.format(
                "{\"message\": \"A conta de usuário com email '%s' foi confirmada e ativada.\"}", user.getEmail());
    }

}
