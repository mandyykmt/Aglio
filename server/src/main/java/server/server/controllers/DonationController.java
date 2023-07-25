package server.server.controllers;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Controller
@RequestMapping(path="/donate")
@CrossOrigin(origins="*")
public class DonationController {
    
    @Value("${stripe.secret.key}")
    private String stripeKey; 


    @PostMapping(path="/thankyou")
    @ResponseBody
    public ResponseEntity<String> postDonation(@RequestBody String donationData) {

        System.out.println(">> connectDonationController");

        Stripe.apiKey = stripeKey;

        try {

            JsonReader jsonReader = Json.createReader(new StringReader(donationData));
            JsonObject req = jsonReader.readObject();
            
            Long longAmount = Long.parseLong(req.getString("amount")); 
            
            System.out.println(">> donationAmount: " + longAmount);

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(1000L)
                .setCurrency("sgd")
                .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            JsonObject resp = Json.createObjectBuilder()
                                        .add("ok", true)
                                        .add("pi", paymentIntent.getClientSecret())
                                        .build();

            System.out.println(">> jsonResp: " + resp);

            return ResponseEntity.ok(resp.toString());

        } catch (StripeException e) {
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error creating PaymentIntent");
        }
    }
}
