package msp.group.rento.system.Service;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
    void sendResetPassword(String to, String subject, String text , String name) throws IOException, MessagingException;
    void userCreationMail(String to, String subject, String text , String name ) throws IOException, MessagingException;
    void otpForUserCreationMail(String to ,String name, String otp) throws MessagingException, IOException;
}
