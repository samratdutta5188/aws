package com.handson.aws.s3;

import com.handson.aws.utils.Constants;
import com.handson.aws.utils.Credentials;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class BucketOperations {

    private S3Client s3Client;

    public BucketOperations(final S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void createBucket() {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        .locationConstraint(Credentials.REGION.id())
                        .build())
                .build();
        s3Client.createBucket(createBucketRequest);
    }

    public void listBuckets() {
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
        for (Bucket bucket : listBucketsResponse.buckets()) {
            System.out.println("Bucket Name: " + bucket.name());
            System.out.println("Bucket Creation Date: " + bucket.creationDate());
        }
        System.out.println("Owner: " + listBucketsResponse.owner().displayName());
    }

    public void deleteBucket() {
        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .build();
        s3Client.deleteBucket(deleteBucketRequest);
    }

    public void putBucketPolicy() {
        StringBuilder policy = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(Constants.S3_BUCKET_POLICY), StandardCharsets.UTF_8)) {
            stream.forEach(s -> policy.append(s).append("\n"));
        } catch (IOException e) {
            System.out.println("Exception occurred during Policy File parsing: " + e.getMessage());
        }

        PutBucketPolicyRequest putBucketPolicyRequest = PutBucketPolicyRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .policy(policy.toString())
                .build();
        s3Client.putBucketPolicy(putBucketPolicyRequest);
    }

    public void getBucketPolicy() {
        GetBucketPolicyRequest getBucketPolicyRequest = GetBucketPolicyRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .build();
        GetBucketPolicyResponse getBucketPolicyResponse = s3Client.getBucketPolicy(getBucketPolicyRequest);
        System.out.println("Bucket policy: " + getBucketPolicyResponse.policy());
    }

    public void deleteBucketPolicy() {
        DeleteBucketPolicyRequest deleteBucketPolicyRequest = DeleteBucketPolicyRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .build();
        s3Client.deleteBucketPolicy(deleteBucketPolicyRequest);
    }

}
