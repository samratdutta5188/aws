package com.handson.aws.clients;

import com.handson.aws.utils.Credentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;

public class MyCloudWatchLogsClient {

    private CloudWatchLogsClient cloudWatchLogsClient;

    public CloudWatchLogsClient initialize() {
        AwsCredentials credentials = AwsBasicCredentials.create(Credentials.ACCESS_KEY_ID, Credentials.SECRET_KEY);
        this.cloudWatchLogsClient = CloudWatchLogsClient.builder()
                .credentialsProvider(() -> credentials)
                .region(Credentials.REGION)
                .build();
        return this.cloudWatchLogsClient;
    }

}
