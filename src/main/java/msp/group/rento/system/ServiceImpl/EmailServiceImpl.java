package msp.group.rento.system.ServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import msp.group.rento.system.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.io.IOException;
import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(fromEmail);

        mailSender.send(message);
    }

    public void sendResetPassword(String to, String subject, String text , String name) throws IOException, MessagingException {
        String content = loadTemplate("src/main/resources/New-Reset-password.html" , name , text);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper =  new MimeMessageHelper(mimeMessage , true);
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setSentDate(new Date());
        helper.setText(content ,true);
        mailSender.send(mimeMessage);

    }

    @Override
    public void userCreationMail(String to, String subject, String text, String name) throws IOException, MessagingException {
        String content = loadUserCreationTemplate("src/main/resources/UserCreationTemplate.html" , name , to );
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper =  new MimeMessageHelper(mimeMessage , true);
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setSentDate(new Date());
        helper.setText(content ,true);
        mailSender.send(mimeMessage);
    }

    @Override
    public void otpForUserCreationMail(String to, String name, String otp ) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSentDate(new Date());
        String content = loadOTPEmailVerificationTemplate("src/main/resources/OTPForEmailVerificationTemplate.html" , name , otp);
        helper.setSubject("OTP for Email Verification");
        helper.setText(content , true);

    }


    private String loadUserCreationTemplate(String fileName, String name, String email ) throws IOException {
        // Read the HTML template file
        ClassLoader classLoader = getClass().getClassLoader();
        String content = new String(String.valueOf(classLoader.getResource(fileName)));

        // Replace user-specific details
        content =content.replace("[User's Name]", name);
        content = content.replace("[username]", email);


        return content;
    }
    private String loadTemplate(String fileName, String name, String newPassword) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        String content = new String(String.valueOf(classLoader.getResource(fileName)));

        content = content.replace("{{name}}", name);
        content = content.replace("{{newPassword}}", newPassword);
        return content;
    }

    private String loadOTPEmailVerificationTemplate(String fileName, String name, String otp) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        String content = new String(String.valueOf(classLoader.getResource(fileName)));

        content = content.replace("{{name}}", name);
        content = content.replace("[OTP]", otp);
        return content;
    }
}
