package server.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String serverEmail; 

    @Autowired
    private JavaMailSender javaMailSender; 

    public void sendSimpleMessage(String recipientEmail,
                                String requestorEmail,
                                String listingName) throws MessagingException {
    
        MimeMessage mimeMessage = javaMailSender.createMimeMessage(); 
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    
        mimeMessageHelper.setFrom(serverEmail);
        mimeMessageHelper.setTo(recipientEmail);
        mimeMessageHelper.setSubject("Request For Listing: " + listingName);
        mimeMessageHelper.setText("Someone has requested your listing: " + listingName + 
                                ". You can contact them at " + requestorEmail);
    
        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent successfully.");
    }
    
}
