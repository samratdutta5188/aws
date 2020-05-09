package com.handson.aws.clients;

import com.handson.aws.utils.Credentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.swf.SwfClient;

public class MySWFClient {

    private SwfClient swfClient;

    public SwfClient initialize() {
        AwsCredentials credentials = AwsBasicCredentials.create(Credentials.ACCESS_KEY_ID, Credentials.SECRET_KEY);
        this.swfClient = SwfClient.builder()
                .credentialsProvider(() -> credentials)
                .region(Credentials.REGION)
                .build();
        return this.swfClient;
    }

}
