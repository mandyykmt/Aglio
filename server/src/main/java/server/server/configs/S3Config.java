package server.server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

    @Value("${S3_ACCESS_KEY}")
    private String s3accessKey;

    @Value("${S3_SECRET_KEY}")
    private String s3secretKey;

    @Value("${S3_ENDPOINT}")
    private String s3endpoint;

    @Bean
    public AmazonS3 createS3Client() {

        BasicAWSCredentials cred = new BasicAWSCredentials(s3accessKey, s3secretKey);

        EndpointConfiguration epConfig = new EndpointConfiguration(
                                        s3endpoint, 
                                        "auto");

        AmazonS3 client = AmazonS3ClientBuilder.standard()
                        .withEndpointConfiguration(epConfig)
                        .withCredentials(new AWSStaticCredentialsProvider(cred))
                        .build();
        
        return client;
    }
}
