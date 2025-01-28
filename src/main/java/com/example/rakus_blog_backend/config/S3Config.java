package com.example.rakus_blog_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Bean
    public S3Presigner s3Presigner() {

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                "XXXXXXXXXXXX",
                "XXXXXXXXXXXX");
        Region region = Region.AP_NORTHEAST_1;

        S3Presigner s3Presigner = S3Presigner.builder().region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).build();
        return s3Presigner;
    }
}
