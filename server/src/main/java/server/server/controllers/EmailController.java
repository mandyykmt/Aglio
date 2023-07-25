package server.server.controllers;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import server.server.services.EmailService;

@Controller
@RequestMapping(path="/email", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins="*")
public class EmailController {
    
    @Autowired
    private EmailService emailService; 

    @RequestMapping(path="/send")
    public ResponseEntity<String> sendEmail(@RequestBody String emailRequest) {
        
        System.out.println(">> startingRequest");

        JsonReader jsonReader = Json.createReader(new StringReader(emailRequest));
        JsonObject req = jsonReader.readObject();

        String recipientEmail = req.getString("recipientEmail");
        String requestorEmail = req.getString("requestorEmail");
        String listingName = req.getString("listingName");

        try { 

            emailService.sendSimpleMessage(recipientEmail, requestorEmail, listingName);

            return ResponseEntity.status(200)
                                .body("{\"message\": \"Email sent successfully\"}");

        } catch (Exception e) {

            return ResponseEntity.status(500)
                                .body("{\"message\": \"Error sending email. Please try again.\"}");

        }
    }
}