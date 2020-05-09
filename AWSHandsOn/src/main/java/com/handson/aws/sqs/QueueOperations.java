package com.handson.aws.sqs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueueOperations {

    private SqsClient sqsClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public QueueOperations(final SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void createQueue() {
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(Constants.SQS_QUEUE_NAME)
                .build();
        sqsClient.createQueue(createQueueRequest);
    }

    public void createQueueWithLongPolling() {
        HashMap<QueueAttributeName, String> attributes = new HashMap<QueueAttributeName, String>();
        attributes.put(QueueAttributeName.RECEIVE_MESSAGE_WAIT_TIME_SECONDS, "10");
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(Constants.SQS_QUEUE_NAME)
                .attributes(attributes)
                .build();
        sqsClient.createQueue(createQueueRequest);
    }

    public void getQueue() {
        GetQueueUrlRequest getQueueUrlRequest = GetQueueUrlRequest.builder()
                .queueName(Constants.SQS_QUEUE_NAME)
                .build();
        String queueUrl = sqsClient.getQueueUrl(getQueueUrlRequest).queueUrl();
        System.out.println("Queue URL: " + queueUrl);
    }

    public void listQueues() {
        List<String> queueUrls = sqsClient.listQueues().queueUrls();
        System.out.println("Queue URLs: " + queueUrls);

        ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder()
                .queueNamePrefix(Constants.SQS_QUEUE_NAME_PREFIX)
                .build();
        queueUrls = sqsClient.listQueues(listQueuesRequest).queueUrls();
        System.out.println("Queue URLs: " + queueUrls);
    }

    public void deleteQueue() {
        DeleteQueueRequest deleteQueueRequest = DeleteQueueRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .build();
        sqsClient.deleteQueue(deleteQueueRequest);
    }

    public void purgeQueue() {
        PurgeQueueRequest purgeQueueRequest = PurgeQueueRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .build();
        sqsClient.purgeQueue(purgeQueueRequest);
    }

    public void addPermission() {
        AddPermissionRequest addPermissionRequest = AddPermissionRequest.builder()
                .queueUrl(Constants.SQS_QUEUE_URL)
                .label(Constants.SNS_TOPIC_ARN + "_" + Constants.SQS_ADD_PERMISSION_ACTION)
                .actions(Constants.SQS_ADD_PERMISSION_ACTION)
                .awsAccountIds(Constants.ACCOUNT_ID)
                .build();
        sqsClient.addPermission(addPermissionRequest);
    }

    public void attachDeadLetterQueue() {
        /*CreateQueueRequest createDLQRequest = CreateQueueRequest.builder()
                .queueName(Constants.SQS_DLQ_NAME)
                .build();
        sqsClient.createQueue(createDLQRequest);*/

        GetQueueUrlRequest getDLQUrlRequest = GetQueueUrlRequest.builder()
                .queueName(Constants.SQS_DLQ_NAME)
                .build();
        String dlqUrl = sqsClient.getQueueUrl(getDLQUrlRequest).queueUrl();

        GetQueueUrlRequest getSrcQueueUrlRequest = GetQueueUrlRequest.builder()
                .queueName(Constants.SQS_QUEUE_NAME)
                .build();
        String srcQueueUrl = sqsClient.getQueueUrl(getSrcQueueUrlRequest).queueUrl();

        GetQueueAttributesResponse getQueueAttributesResponse = sqsClient.getQueueAttributes(GetQueueAttributesRequest.builder()
                .queueUrl(dlqUrl)
                .attributeNames(QueueAttributeName.QUEUE_ARN)
                .build());

        String dlqArn = getQueueAttributesResponse.attributes().get(QueueAttributeName.QUEUE_ARN);

        Map<QueueAttributeName, String> attributes = new HashMap<>();
        attributes.put(QueueAttributeName.REDRIVE_POLICY, "{\"maxReceiveCount\":\"5\", \"deadLetterTargetArn\":\"" + dlqArn + "\"}");

        SetQueueAttributesRequest setQueueAttributesRequest = SetQueueAttributesRequest.builder()
                .queueUrl(srcQueueUrl)
                .attributes(attributes)
                .build();

        sqsClient.setQueueAttributes(setQueueAttributesRequest);
    }

}
