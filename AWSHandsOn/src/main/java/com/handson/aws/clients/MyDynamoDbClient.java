package com.handson.aws.clients;

import com.handson.aws.utils.Credentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class MyDynamoDbClient {

    private DynamoDbClient dynamoDbClient;

    public DynamoDbClient initialize() {
        AwsCredentials credentials = AwsBasicCredentials.create(Credentials.ACCESS_KEY_ID, Credentials.SECRET_KEY);
        this.dynamoDbClient = DynamoDbClient.builder()
                .credentialsProvider(() -> credentials)
                .region(Credentials.REGION)
                .build();
        return dynamoDbClient;
    }

}
