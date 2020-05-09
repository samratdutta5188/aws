package com.handson.aws.clients;

import com.handson.aws.utils.Credentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;

public class MyCloudWatchClient {

    private CloudWatchClient cloudWatchClient;

    public CloudWatchClient initialize() {
        AwsCredentials credentials = AwsBasicCredentials.create(Credentials.ACCESS_KEY_ID, Credentials.SECRET_KEY);
        this.cloudWatchClient = CloudWatchClient.builder()
                .credentialsProvider(() -> credentials)
                .region(Credentials.REGION)
                .build();
        return this.cloudWatchClient;
    }

}
