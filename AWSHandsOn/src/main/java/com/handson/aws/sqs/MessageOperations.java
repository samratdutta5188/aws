package com.handson.aws.sqs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.Arrays;

public class MessageOperations {

    private SqsClient sqsClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public MessageOperations(final SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage() {
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .messageBody(Constants.SQS_SEND_MESSAGE_BODY)
                .delaySeconds(Constants.SQS_SEND_MESSAGE_DELAY)
                .build();
        sqsClient.sendMessage(sendMessageRequest);
    }

    public void batchSendMessage() {
        SendMessageBatchRequestEntry sendMessageBatchRequestEntry1 = SendMessageBatchRequestEntry.builder()
                .id(Constants.SQS_SEND_BATCH_FIRST_MESSAGE_ID)
                .messageBody(Constants.SQS_SEND_BATCH_FIRST_MESSAGE_BODY)
                .build();
        SendMessageBatchRequestEntry sendMessageBatchRequestEntry2 = SendMessageBatchRequestEntry.builder()
                .id(Constants.SQS_SEND_BATCH_SECOND_MESSAGE_ID)
                .messageBody(Constants.SQS_SEND_BATCH_SECOND_MESSAGE_BODY)
                .build();
        SendMessageBatchRequest sendMessageBatchRequest = SendMessageBatchRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .entries(Arrays.asList(sendMessageBatchRequestEntry1, sendMessageBatchRequestEntry2))
                .build();
        sqsClient.sendMessageBatch(sendMessageBatchRequest);
    }

    public void receiveMessage() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .maxNumberOfMessages(10)
                .build();
        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(receiveMessageRequest);
        System.out.println("Messages: " + gson.toJson(receiveMessageResponse.messages()));
    }

    public void receiveMessageLongPolling() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .waitTimeSeconds(20)
                .maxNumberOfMessages(10)
                .build();
        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(receiveMessageRequest);
        System.out.println("Messages: " + gson.toJson(receiveMessageResponse.messages()));
    }

    public void changeMessageVisibility() {
        sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .maxNumberOfMessages(5)
                .build()).messages().forEach(message -> {
                    ChangeMessageVisibilityRequest changeMessageVisibilityRequest = ChangeMessageVisibilityRequest.builder()
                            .queueUrl(Constants.SQS_QUEUE_URL)
                            .receiptHandle(message.receiptHandle())
                            .visibilityTimeout(100)
                            .build();
                    sqsClient.changeMessageVisibility(changeMessageVisibilityRequest);
        });
    }

    public void deleteMessage() {
        Message message = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .maxNumberOfMessages(5)
                .build()).messages().get(0);
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .receiptHandle(message.receiptHandle())
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
    }

    public void purgeQueue() {
        PurgeQueueRequest purgeQueueRequest = PurgeQueueRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .build();
        sqsClient.purgeQueue(purgeQueueRequest);
    }

}
