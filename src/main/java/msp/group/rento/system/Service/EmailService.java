package msp.group.rento.system.Service;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
    void sendResetPassword(String to, String subject, String text , String name) throws IOException, MessagingException, URISyntaxException;
    void userCreationMail(String to, String subject, String text , String name ) throws IOException, MessagingException, URISyntaxException;
    void otpForUserCreationMail(String to ,String name, String otp) throws MessagingException, IOException, URISyntaxException;
}
