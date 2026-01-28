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
    
    public void sendHoneypotAlertEmail(String triggerType, String ipAddress, String name, String email, String botTrapValue, String userAgent, Long submissionTimeMs) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(adminEmail);
        mailMessage.setSubject("HONEYPOT ALERT: " + triggerType + " Triggered");
        
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("A bot/spam attempt was detected on your portfolio contact form.\n\n");
        emailBody.append("Trigger Type: ").append(triggerType).append("\n");
        emailBody.append("IP Address: ").append(ipAddress).append("\n");
        emailBody.append("Timestamp: ").append(java.time.LocalDateTime.now()).append("\n\n");
        
        emailBody.append("Submitted Data:\n");
        emailBody.append("Name: ").append(name).append("\n");
        emailBody.append("Email: ").append(email).append("\n");
        
        if (triggerType.equals("HONEYPOT")) {
            emailBody.append("Bot Trap Value: ").append(botTrapValue).append("\n");
        } else if (triggerType.equals("FAST_SUBMISSION") && submissionTimeMs != null) {
            emailBody.append("Submission Time: ").append(submissionTimeMs).append("ms\n");
        }
        
        emailBody.append("\nUser Agent: ").append(userAgent);
        
        mailMessage.setText(emailBody.toString());
        mailSender.send(mailMessage);
    }
}
