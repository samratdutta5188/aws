package com.handson.aws.clients;

import com.handson.aws.utils.Credentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.iam.IamClient;

public class MyIAMClient {

    private IamClient iamClient;

    public IamClient initialize() {
        AwsCredentials credentials = AwsBasicCredentials.create(Credentials.ACCESS_KEY_ID, Credentials.SECRET_KEY);
        this.iamClient = IamClient.builder()
                .credentialsProvider(() -> credentials)
                .region(Credentials.GLOBAL_REGION)
                .build();
        return this.iamClient;
    }

}
