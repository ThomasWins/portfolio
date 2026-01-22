package in.thomaswins.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${admin.email}")
    private String adminEmail;

    public void sendContactFormEmail(String name, String email, String phone, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(adminEmail);
        mailMessage.setFrom(email);
        mailMessage.setSubject("New Contact Form Submission from " + name);
        mailMessage.setText("Name: " + name + "\n" +
                            "Email: " + email + "\n" +
                            "Phone: " + phone + "\n\n" +
                            "Message:\n" + message);
        mailSender.send(mailMessage);
    }
}
