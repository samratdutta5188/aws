package com.handson.aws.clients;

import com.handson.aws.utils.Credentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.sns.SnsClient;

public class MySNSClient {

    private SnsClient sqsClient;

    public SnsClient initialize() {
        AwsCredentials credentials = AwsBasicCredentials.create(Credentials.ACCESS_KEY_ID, Credentials.SECRET_KEY);
        this.sqsClient = SnsClient.builder()
                .credentialsProvider(() -> credentials)
                .region(Credentials.REGION)
                .build();
        return this.sqsClient;
    }

}
