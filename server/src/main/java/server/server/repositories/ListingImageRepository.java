package server.server.repositories;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class ListingImageRepository {

    @Value("${S3_IMAGE_ENDPOINT}")
    private URL image_s3endpoint;
    
    @Autowired
    private AmazonS3 s3; 

    public URL uploadNewListingImage(String key, MultipartFile file) throws IOException {

        Map<String, String> userData = new HashMap<>();
        userData.put("fileName", file.getOriginalFilename());
        userData.put("uploadDate", (new Date()).toString());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        String listingKey = "image/%s".formatted(key);

        System.out.println(">> listingKey: " + listingKey);
        
        PutObjectRequest putRequest = new PutObjectRequest(
                                            "abcde", 
                                            listingKey, 
                                            file.getInputStream(), 
                                            metadata);
        putRequest = putRequest.withCannedAcl(CannedAccessControlList.PublicRead);

        PutObjectResult putResult = s3.putObject(putRequest);
        System.out.printf(">> putResult: %s\n", putResult);

        URL url = new URL(image_s3endpoint + listingKey);

        System.out.println(">> url: " + url);

        return url; 
    }

    public boolean deleteListingByKey(String imageKey) {

        String imageKeyPath = "image/%s".formatted(imageKey);
        try {
            s3.deleteObject(new DeleteObjectRequest("abcde", imageKeyPath));  
            return true;
        } catch (Exception e) {
            return false; 
        }
    }
}
