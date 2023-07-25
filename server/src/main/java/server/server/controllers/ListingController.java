package server.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import server.server.models.Listing;
import server.server.services.ListingService;

@Controller
@RequestMapping(path="/listings")
@CrossOrigin(origins="*")
public class ListingController {
    
    @Autowired
    private ListingService listingService; 

    @PostMapping(path="/new", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> postNewListing(
                        @RequestPart String listingName,
                        @RequestPart String description,
                        @RequestPart String email,  
                        @RequestPart MultipartFile file) {
        
        System.out.println("*************************************************************");

        System.out.println(">> listingName: " + listingName);
        System.out.println(">> description: " + description);
        System.out.println(">> email: " + email);
        System.out.println(">> file: " + file.getOriginalFilename());

        try {
            String key = listingService.uploadNewListing(listingName, description, email, file); 

            System.out.println("*************************************************************");

            return ResponseEntity.status(201)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Json.createObjectBuilder()
                                .add("id", key)
                                .build().toString());

        } catch (Exception e) {
            return ResponseEntity.status(500)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Json.createObjectBuilder()
                                .add("error", e.getMessage())
                                .build().toString());
        }
    }

    @GetMapping(path="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAllListings() {

        List<Listing> listings = listingService.getAllListings(); 

        for(Listing l: listings) {
            System.out.println(">> getListingKey: " + l.getKey());
            System.out.println(">> getListingName: " + l.getListingName());
            System.out.println(">> getListingDesc: " + l.getDescription());
            System.out.println(">> getListingEmail: " + l.getEmail());
            System.out.println(">> getListingUrl: " + l.getUrl());
        }

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for(Listing l: listings) {
            jsonArrayBuilder.add(l.toJson()); 
        }

        JsonArray jsonArray = jsonArrayBuilder.build(); 

        System.out.println(">> getAllListingsJson: " + jsonArray);

        return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(jsonArray.toString());
    }

    @GetMapping(path="/search/{imageKey}") 
    @ResponseBody
    public ResponseEntity<String> getListingByKey(@PathVariable String imageKey) {

        Listing listing = listingService.getListingByKey(imageKey);

        System.out.println(">> listing: " + listing);

        if(imageKey != null) {
            return ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                // .add("imageKey", listing.getKey())
                                .body(listing.toJson().toString());
        } else {
            return ResponseEntity.status(404)
                                .body(Json.createObjectBuilder()
                                .add("error", "listing " + imageKey + " not found")
                                .build().toString());
                                
        }
    }

    @GetMapping(path="/search/keyword")
    @ResponseBody
    public ResponseEntity<String> getListingByKeyword(@RequestParam String search) {
        System.out.println(search); // search == keyword
        List<Listing> listings = listingService.getListingsByKeyword(search); 

        for(Listing l: listings) {
            System.out.println(">> getListingKey: " + l.getKey());
            System.out.println(">> getListingName: " + l.getListingName());
            System.out.println(">> getListingDesc: " + l.getDescription());
            System.out.println(">> getListingEmail: " + l.getEmail());
            System.out.println(">> getListingUrl: " + l.getUrl());
        }
// ....?search=keyword
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for(Listing l: listings) {
            jsonArrayBuilder.add(l.toJson()); 
        }

        JsonArray jsonArray = jsonArrayBuilder.build(); 

        System.out.println(">> getListingsByKeywordJson: " + jsonArray);

        return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(jsonArray.toString());
    }

    @DeleteMapping(path="/delete/{imageKey}")
    @ResponseBody
    public ResponseEntity<String> deleteListingByKey(@PathVariable String imageKey) {
        
        try {
            boolean isDeleted = this.listingService.deleteListingByKey(imageKey);

            if(isDeleted) {
                return ResponseEntity.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body("{\"message\": \"Listing deleted successfully.\"}");
            } else {
                return ResponseEntity.status(404)
                                    .body("{\"error\": \"Listing " + imageKey + "not found.\"}"); 
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                                .body("{\"error\": \"An error has occured.\"}"); 
        }
    }
}