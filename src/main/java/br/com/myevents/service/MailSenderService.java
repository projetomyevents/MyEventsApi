package br.com.myevents.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Implementa a lógica de serviços de {@link JavaMailSender}.
 */
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MailSenderService {

    private final JavaMailSender mailSender;

    /**
     * Monta uma mensagem de email com conteúdo HTML e a envia para o endereço de email informado.
     *
     * @param email o endereço de email do destinatário
     * @param subject o título da mensagem
     * @param htmlText o conteúdo da mensagem
     * @throws MailParseException ?
     */
    @Async
    public void sendHtml(String email, String subject, String htmlText) throws MailParseException {
        try {
            MimeMailMessage message = new MimeMailMessage(mailSender.createMimeMessage());
            message.setTo(email);
            message.setSubject(subject);
            message.getMimeMessageHelper().setText(htmlText, true);

            mailSender.send(message.getMimeMessage());
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
    }

}
