package com.handson.aws.clients;

import com.handson.aws.utils.Credentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.cloudwatchevents.CloudWatchEventsClient;

public class MyCloudWatchEventsClient {

    private CloudWatchEventsClient cloudWatchEventsClient;

    public CloudWatchEventsClient initialize() {
        AwsCredentials credentials = AwsBasicCredentials.create(Credentials.ACCESS_KEY_ID, Credentials.SECRET_KEY);
        this.cloudWatchEventsClient = CloudWatchEventsClient.builder()
                .credentialsProvider(() -> credentials)
                .region(Credentials.REGION)
                .build();
        return this.cloudWatchEventsClient;
    }

}
