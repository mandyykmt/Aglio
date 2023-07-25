package server.server.controllers;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import server.server.models.LoginData;
import server.server.models.User;
import server.server.services.AccountService;

@Controller
@RequestMapping(path="/account", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins="*")
public class AccountController {

    @Autowired
    private AccountService accountService; 
    
    // todo: seperate users table into user_info and user_contact
    @PostMapping(path="/register", consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postNewRegistration(@RequestBody String payload) {
        
        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        JsonObject req = jsonReader.readObject();

        String username = req.getString("username");
        String password = req.getString("password");
        String firstName = req.getString("firstName");
        String lastName = req.getString("lastName");
        String email = req.getString("email");
        String phone = req.getString("phone");
        String country  = req.getString("country");
        String postal = req.getString("postalCode");

        User existingUser = accountService.getUserByEmail(email);

        if (existingUser != null) {
            JsonObject errorResp = Json.createObjectBuilder()
                                    .add("error", "Email already registered")
                                    .build();

            System.out.println(">> ControlRegistrationError: " + errorResp.toString());
            
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResp.toString());
        } else { 

            User user = new User(username, password, firstName, lastName, email, phone, country, postal);

            User result = accountService.insertRegistration(user);

            System.out.println(">> ControlRegistrationResult: " + result); 

            JsonObject resp = Json.createObjectBuilder()
                                .add("username", username)
                                .add("password", password)
                                .add("firstName", firstName)
                                .add("lastName", lastName)
                                .add("email", email)
                                .add("phone", phone)
                                .add("country", country)
                                .add("postal", postal)
                                .build(); 

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resp.toString());
        }
    }

    @PostMapping(path="/login", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postLogin(@RequestBody String payload) {

        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        JsonObject req = jsonReader.readObject();

        String email = req.getString("email");
        String password = req.getString("password");

        User user = new User(email, password);

        User existingUser = accountService.getUserByEmail(email);

        if (existingUser == null) {
            JsonObject errorResp = Json.createObjectBuilder()
                                    .add("error", "Email not registered")
                                    .build();

            System.out.println(">> ControlLoginError: " + errorResp.toString());
            
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResp.toString());
                    
        } else if (!existingUser.getPassword().equals(password)) {

            LoginData result = accountService.login(user);

            JsonObject errorResp = Json.createObjectBuilder()
                                        .add("error", "Invalid password")
                                        .add("id", result.getId())
                                        .add("email", result.getEmail())
                                        .add("dateTime", result.getDateTime())
                                        .build();

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResp.toString());

        } else {

            LoginData result = accountService.login(user);

            JsonObject resp = Json.createObjectBuilder()
                                    .add("id", result.getId())
                                    .add("email", result.getEmail())
                                    .add("dateTime", result.getDateTime())
                                    .build();

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resp.toString());
            }
    }
}
