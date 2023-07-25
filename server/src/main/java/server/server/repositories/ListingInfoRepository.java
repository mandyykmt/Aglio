package server.server.repositories;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import server.server.models.Listing;

@Repository
public class ListingInfoRepository {

    @Autowired
    private MongoTemplate mongoTemplate; 

    public String uploadNewListingInfo(String key, 
                                    String listingName, 
                                    String description, 
                                    String email, URL url) {
        
        // db.listingInfo.findOneAndUpdate(
        //     {_id: <string>},
        //     {
        //         $setOnInsert: {
        //             imageKey: <string>
        //             date: <string>
        //             listingName: <string>
        //             descripton: <string>
        //             email: <string> 
        //             url: <string>
        //             }
        //     },
        //     {
        //         upsert: true
        //     }
        // );

        Query query = new Query(Criteria.where("imageKey").is(key));
        Update update = new Update().set("_id", key)
                                    .set("date", LocalDateTime.now())
                                    .set("listingName", listingName)
                                    .set("description", description)
                                    .set("email", email)
                                    .set("url", url);

        UpdateResult result =  mongoTemplate.upsert(query, update, Document.class, "listingInfo");

        System.out.println(">> upsertResult: " + result);

        return key;
    }

    public List<Document> getAllListings() {

        Query query = new Query(Criteria.where("imageKey").exists(true));
        List<Document> documents = mongoTemplate.find(query, Document.class, "listingInfo");

        return documents; 
    }

    public Optional<Document> getListingByKey(String imageKey) {

        try {
            Query query = new Query(Criteria.where("imageKey").is(imageKey));
            Document document = mongoTemplate.findOne(query, Document.class, "listingInfo");

            System.out.println(">> query: " + query);
            System.out.println(">> document: " + document);

            return Optional.of(document);

        } catch (Exception e) {
            return null;
        }
    }

    public List<Document> getListingsByKeyword(String keyword) {
        
        try {
            Query query = new Query(Criteria.where("listingName").regex(keyword, "i"));
            List<Document> documents = mongoTemplate.find(query, Document.class, "listingInfo");

            System.out.println(">> query: " + query);
            System.out.println(">> document: " + documents);

            return documents; 

        } catch (Exception e) {
            return null;
        }     
    }

    public boolean deleteListingByKey(String imageKey) {

        try {
            Query query = new Query(Criteria.where("imageKey").is(imageKey));
            DeleteResult result = mongoTemplate.remove(query, "listingInfo");
            return result.getDeletedCount() > 0; 
        } catch (Exception e) {
            return false; 
        }
    }
}
