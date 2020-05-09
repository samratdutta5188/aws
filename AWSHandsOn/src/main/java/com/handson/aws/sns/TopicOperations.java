package com.handson.aws.sns;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;

public class TopicOperations {

    private SnsClient snsClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public TopicOperations(final SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public void createTopic() {
        CreateTopicRequest createTopicRequest = CreateTopicRequest.builder()
                .name(Constants.SNS_TOPIC_NAME)
                .build();
        snsClient.createTopic(createTopicRequest);
    }

    public void listTopics() {
        ListTopicsResponse listTopicsResponse = snsClient.listTopics();
        System.out.println("Topics: " + gson.toJson(listTopicsResponse.topics()));
    }

    public void deleteTopic() {
        DeleteTopicRequest deleteTopicRequest = DeleteTopicRequest.builder()
                .topicArn(Constants.SNS_TOPIC_ARN)
                .build();
        snsClient.deleteTopic(deleteTopicRequest);
    }

    public void listSubscriptionsByTopic() {
        ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest = ListSubscriptionsByTopicRequest.builder()
                .topicArn(Constants.SNS_TOPIC_ARN)
                .build();
        ListSubscriptionsByTopicResponse listSubscriptionsByTopicResponse =
                snsClient.listSubscriptionsByTopic(listSubscriptionsByTopicRequest);
        System.out.println("Subscriptions: " + gson.toJson(listSubscriptionsByTopicResponse.subscriptions()));
    }

}
