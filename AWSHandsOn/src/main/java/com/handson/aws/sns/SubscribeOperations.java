package com.handson.aws.sns;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

public class SubscribeOperations {

    private SnsClient snsClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SubscribeOperations(final SnsClient sqsClient) {
        this.snsClient = sqsClient;
    }

    public void subscribeEmail() {
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .topicArn(Constants.SNS_TOPIC_ARN)
                .protocol(Constants.SNS_SUBSCRIBE_EMAIL_PROTOCOL)
                .endpoint(Constants.EMAIL)
                .returnSubscriptionArn(true)
                .build();
        SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);
        System.out.println("Email subscribe ARN: " + subscribeResponse.subscriptionArn());
    }

    public void subscribeQueue() {
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .topicArn(Constants.SNS_TOPIC_ARN)
                .protocol(Constants.SNS_SUBSCRIBE_SQS_PROTOCOL)
                .endpoint(Constants.SQS_QUEUE_ARN)
                .returnSubscriptionArn(true)
                .build();
        SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);
        System.out.println("Queue subscribe ARN: " + subscribeResponse.subscriptionArn());
    }

    public void publishMessage() {
        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(Constants.SNS_TOPIC_ARN)
                .message(Constants.SNS_PUBLISH_MESSAGE)
                .build();
        snsClient.publish(publishRequest);
    }

    public void confirmSubscription() {
        ConfirmSubscriptionRequest confirmSubscriptionRequest = ConfirmSubscriptionRequest.builder()
                .token(Constants.SNS_CONFIRM_SUBSCRIPTION_TOKEN)
                .topicArn(Constants.SNS_TOPIC_ARN)
                .build();
        ConfirmSubscriptionResponse confirmSubscriptionResponse = snsClient.confirmSubscription(confirmSubscriptionRequest);
        System.out.println("Subscription ARN: " + confirmSubscriptionResponse.subscriptionArn());
    }

    public void unsubscribe() {
        UnsubscribeRequest unsubscribeRequest = UnsubscribeRequest.builder()
                .subscriptionArn(Constants.SNS_EMAIL_SUBSCRIPTION_ARN)
                .build();
        snsClient.unsubscribe(unsubscribeRequest);
    }

}
