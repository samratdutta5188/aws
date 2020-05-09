package com.handson.aws.clients;

import com.handson.aws.utils.Credentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

public class MyS3Client {

    private S3Client s3Client;

    public S3Client initialize() {
        AwsCredentials credentials = AwsBasicCredentials.create(Credentials.ACCESS_KEY_ID, Credentials.SECRET_KEY);
        this.s3Client = software.amazon.awssdk.services.s3.S3Client.builder()
                .credentialsProvider(() -> credentials)
                .region(Credentials.REGION)
                .build();
        return s3Client;
    }

}
