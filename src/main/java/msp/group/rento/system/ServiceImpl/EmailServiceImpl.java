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
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public void sendResetPassword(String to, String subject, String text , String name) throws IOException, MessagingException, URISyntaxException {
        String content = loadTemplate("New-Reset-password.html" , name , text);
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
    public void userCreationMail(String to, String subject, String text, String name) throws IOException, MessagingException, URISyntaxException {
        String content = loadUserCreationTemplate("UserCreationTemplate.html" , name , to );
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
    public void otpForUserCreationMail(String to, String name, String otp ) throws MessagingException, IOException, URISyntaxException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSentDate(new Date());
        String content = loadOTPEmailVerificationTemplate("OTPForEmailVerificationTemplate.html" , name , otp);
        helper.setSubject("OTP for Email Verification");
//        System.out.print(content);
        helper.setText(content , true);
        mailSender.send(mimeMessage);

    }


    private String loadUserCreationTemplate(String fileName, String name, String email ) throws IOException, URISyntaxException {
        // Read the HTML template file
        String content  = "";
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource != null) {
            content = new String(Files.readAllBytes(Paths.get(resource.toURI())));
//            System.out.println(content);
        } else {
            System.out.println("File not found!");
        }
        // Replace user-specific details
        content =content.replace("[Subuser's Name]", name);
        content = content.replace("[username]", email);


        return content;
    }
    private String loadTemplate(String fileName, String name, String newPassword) throws IOException, URISyntaxException {

        String content  = "";
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource != null) {
            content = new String(Files.readAllBytes(Paths.get(resource.toURI())));
//            System.out.println(content);
        } else {
            System.out.println("File not found!");
        }
        content = content.replace("{{name}}", name);
        content = content.replace("{{newPassword}}", newPassword);
        return content;
    }

    private String loadOTPEmailVerificationTemplate(String fileName, String name, String otp) throws IOException, URISyntaxException {
        String content  = "";
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource != null) {
             content = new String(Files.readAllBytes(Paths.get(resource.toURI())));
//            System.out.println(content);
        } else {
            System.out.println("File not found!");
        }
//        System.out.println(content);
        content = content.replace("{User's Name}", name);
        content = content.replace("{OTP}", otp);
        return content;
    }
}
