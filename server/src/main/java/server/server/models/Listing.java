package server.server.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Listing {
    String key;
    String listingName;
    String description;
    String email;
    String url;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getListingName() {
        return listingName;
    }
    public void setListingName(String listingName) {
        this.listingName = listingName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Listing() {
    }
    public Listing(String key, String listingName, String description, String email, String url) {
        this.key = key;
        this.listingName = listingName;
        this.description = description;
        this.email = email;
        this.url = url;
    }
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                    .add("key", getKey())
                    .add("listingName", getListingName())
                    .add("description", getDescription())
                    .add("email", getEmail())
                    .add("url", getUrl())
                    .build();
    }
    public static Listing toListing(Document doc) {
        Listing listing = new Listing();
        
        listing.setKey(doc.getString("imageKey"));
        listing.setListingName(doc.getString("listingName"));
        listing.setDescription(doc.getString("description"));
        listing.setEmail(doc.getString("email"));
        listing.setUrl(doc.getString("url"));
        
        return listing;
    }
}