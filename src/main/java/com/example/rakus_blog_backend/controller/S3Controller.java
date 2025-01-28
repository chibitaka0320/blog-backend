package com.example.rakus_blog_backend.controller;

import java.net.URL;
import java.time.Duration;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Presigner s3Presigner;

    @GetMapping("/put-url")
    public ResponseEntity<Map<String, String>> generatePutUrl(@RequestParam String fileName,
            @RequestParam String contentType) {

        try (s3Presigner) {

            // PutObjectのリクエストを作成
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("rakus-blog-test")
                    .key("image/" + fileName)
                    .contentType(contentType)
                    .build();

            PresignedPutObjectRequest presigneRequest = s3Presigner.presignPutObject(
                    r -> r.signatureDuration(Duration.ofMinutes(5)).putObjectRequest(putObjectRequest));

            URL url = presigneRequest.url();

            Map<String, String> response = Map.of("url", url.toString());

            return ResponseEntity.ok(response);
        }
    }
}
