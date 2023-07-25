package server.server.services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import server.server.repositories.ListingInfoRepository;
import server.server.models.Listing;
import server.server.repositories.ListingImageRepository;

@Service
public class ListingService {

    @Autowired
    private ListingImageRepository listingImageRepository; 

    @Autowired
    private ListingInfoRepository listingInfoRepository; 

    public String uploadNewListing(String listingName, 
                                String description, 
                                String email, 
                                MultipartFile file) throws IOException {

        String key = UUID.randomUUID().toString().substring(0, 8);

        URL url = listingImageRepository.uploadNewListingImage(key, file);
        
        System.out.println(">> url: " + url);

        String result = listingInfoRepository.uploadNewListingInfo(key, listingName, description, email, url); 

        System.out.println(">> key: " + result);

        return key; 
    }
    
    public List<Listing> getAllListings() {

        List<Listing> listings = new ArrayList<>();
        List<Document> documents = listingInfoRepository.getAllListings();
        
        for(Document d: documents) {
            if(d != null) {
                Listing listing = Listing.toListing(d); 
                listings.add(listing);
            } else if (d == null) {
                return null; 
            }
        }
        
        return listings; 
    }

    public Listing getListingByKey(String imageKey) {

        try {
            Optional<Document> document = listingInfoRepository.getListingByKey(imageKey);
            Listing listing = Listing.toListing(document.get());
            
            System.out.println(">> listing: " + listing);

            return listing; 
            
        } catch (Exception e) {
            return null; 
        }
    }

    public List<Listing> getListingsByKeyword(String keyword) {

        List<Listing> listings = new ArrayList<>();
        List<Document> documents = listingInfoRepository.getListingsByKeyword(keyword);
        
        for(Document d: documents) {
            if(d != null) {
                Listing listing = Listing.toListing(d); 
                listings.add(listing);
            } else if (d == null) {
                return null; 
            }
        }
        
        return listings; 
    }

    public boolean deleteListingByKey(String imageKey) {
        try {
            boolean isDeletedFromS3 = listingImageRepository.deleteListingByKey(imageKey);
            boolean isDeletedFromMongo = listingInfoRepository.deleteListingByKey(imageKey);
            return isDeletedFromS3 && isDeletedFromMongo;
        } catch (Exception e) {
            return false; 
        }
    }
}
