package com.handson.aws;

import com.handson.aws.clients.*;
import com.handson.aws.cloudwatch.EventOperations;
import com.handson.aws.cloudwatch.LogOperations;
import com.handson.aws.cloudwatch.MetricOperations;
import com.handson.aws.dynamodb.ItemOperations;
import com.handson.aws.dynamodb.TableOperations;
import com.handson.aws.iam.PermissionOperations;
import com.handson.aws.iam.UserOperations;
import com.handson.aws.s3.BucketOperations;
import com.handson.aws.s3.ObjectOperations;
import com.handson.aws.ses.EmailOperations;
import com.handson.aws.ses.IdentityOperations;
import com.handson.aws.sns.SubscribeOperations;
import com.handson.aws.sns.TopicOperations;
import com.handson.aws.sqs.MessageOperations;
import com.handson.aws.sqs.QueueOperations;
import com.handson.aws.swf.ActivityOperations;
import com.handson.aws.swf.FlightBookingActivities;
import com.handson.aws.swf.WorkflowOperations;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatchevents.CloudWatchEventsClient;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.swf.SwfClient;

public class AWSHandsOnMain {

    public static void main(final String []args) {
        //s3Operations();
        //dynamoDbOperations();
        //sqsOperations();
        //snsOperations();
        //sesOperations();
        //swfOperations();
        //iamOperations();
        cloudWatchOperations();
    }

    public static void s3Operations() {
        MyS3Client myS3Client = new MyS3Client();
        S3Client s3Client = myS3Client.initialize();

        BucketOperations bucketOperations = new BucketOperations(s3Client);
        //bucketOperations.createBucket();
        //bucketOperations.listBuckets();
        //bucketOperations.deleteBucket();
        //bucketOperations.putBucketPolicy();
        //bucketOperations.getBucketPolicy();
        //bucketOperations.deleteBucketPolicy();

        ObjectOperations objectOperations = new ObjectOperations(s3Client);
        //objectOperations.putObject();
        //objectOperations.listObjects();
        //objectOperations.deleteObject();
        //objectOperations.downloadObject();
        //objectOperations.copyObject();
    }

    public static void dynamoDbOperations() {
        MyDynamoDbClient myDynamoDbClient = new MyDynamoDbClient();
        DynamoDbClient dynamoDbClient = myDynamoDbClient.initialize();

        TableOperations tableOperations = new TableOperations(dynamoDbClient);
        //tableOperations.createTable();
        //tableOperations.listTables();
        //tableOperations.describeTable();
        //tableOperations.updateTable();
        //tableOperations.deleteTable();

        ItemOperations itemOperations = new ItemOperations(dynamoDbClient);
        //itemOperations.putItem();
        //itemOperations.getItem();
        //itemOperations.updateItem();
        //itemOperations.queryItem();
        //itemOperations.scanItem();
        //itemOperations.deleteItem();
        //itemOperations.batchGetItems();
        //itemOperations.batchWriteItems();
    }

    public static void sqsOperations() {
        MySQSClient mySQSClient = new MySQSClient();
        SqsClient sqsClient = mySQSClient.initialize();

        QueueOperations queueOperations = new QueueOperations(sqsClient);
        //queueOperations.createQueue();
        //queueOperations.createQueueWithLongPolling();
        //queueOperations.getQueue();
        //queueOperations.listQueues();
        //queueOperations.deleteQueue();
        //queueOperations.purgeQueue();
        //queueOperations.attachDeadLetterQueue();
        //queueOperations.addPermission();

        MessageOperations messageOperations = new MessageOperations(sqsClient);
        //messageOperations.sendMessage();
        //messageOperations.batchSendMessage();
        //messageOperations.receiveMessage();
        //messageOperations.changeMessageVisibility();
        //messageOperations.deleteMessage();
    }

    public static void snsOperations() {
        MySNSClient mySNSClient = new MySNSClient();
        SnsClient snsClient = mySNSClient.initialize();

        TopicOperations topicOperations = new TopicOperations(snsClient);
        //topicOperations.createTopic();
        //topicOperations.listTopics();
        //topicOperations.deleteTopic();
        //topicOperations.listSubscriptionsByTopic();

        SubscribeOperations subscribeOperations = new SubscribeOperations(snsClient);
        //subscribeOperations.subscribeEmail();
        //subscribeOperations.subscribeQueue();
        //subscribeOperations.confirmSubscription();
        //subscribeOperations.publishMessage();
        //subscribeOperations.unsubscribe();
    }

    public static void sesOperations() {
        MySESClient mySESClient = new MySESClient();
        SesClient sesClient = mySESClient.initialize();

        IdentityOperations identityOperations = new IdentityOperations(sesClient);
        //identityOperations.verifyEmailAddress();
        //identityOperations.verifyEmailIdentity();
        //identityOperations.verifyDomainIdentity();
        //identityOperations.listIdentities();
        //identityOperations.deleteVerifiedEmailAddress();
        //identityOperations.deleteDomainIdentity();

        EmailOperations emailOperations = new EmailOperations(sesClient);
        /*try {
            //emailOperations.sendRawEmail();
            //emailOperations.sendEmailWithAttachment();
        } catch (MessagingException | IOException e) {
            System.out.println("Some error occurred");
        }*/
    }

    public static void swfOperations() {
        MySWFClient mySWFClient = new MySWFClient();
        SwfClient swfClient = mySWFClient.initialize();

        WorkflowOperations workflowOperations = new WorkflowOperations(swfClient);
        ActivityOperations activityOperations = new ActivityOperations(swfClient, new FlightBookingActivities());

        //workflowOperations.registerDomain();
        //workflowOperations.registerWorkflowType();
        //activityOperations.registerActivityType();

        //workflowOperations.startWorkflow();
        //workflowOperations.executeDecisions();
        //activityOperations.executeActivities();
    }

    public static void iamOperations() {
        MyIAMClient myIAMClient = new MyIAMClient();
        IamClient iamClient = myIAMClient.initialize();

        UserOperations userOperations = new UserOperations(iamClient);
        //userOperations.createUser();
        //userOperations.listUsers();
        //userOperations.updateUser();
        //userOperations.deleteUser();
        //userOperations.createAccessKey();
        //userOperations.listAccessKeys();
        //userOperations.updateAccessKey();
        //userOperations.deleteAccessKey();
        //userOperations.createGroup();
        //userOperations.listGroups();
        //userOperations.deleteGroup();
        //userOperations.addUserToGroup();
        //userOperations.listGroupsForUser();
        //userOperations.removeUserFromGroup();

        PermissionOperations permissionOperations = new PermissionOperations(iamClient);
        //permissionOperations.createPolicy();
        //permissionOperations.getPolicy();
        //permissionOperations.deletePolicy();
        //permissionOperations.createRole();
        //permissionOperations.getRole();
        //permissionOperations.deleteRole();
        //permissionOperations.attachRolePolicy();
        //permissionOperations.listRolePolicy();
        //permissionOperations.detachRolePolicy();
        //permissionOperations.attachUserPolicy();
        //permissionOperations.listUserPolicies();
        //permissionOperations.detachUserPolicy();
        //permissionOperations.attachGroupPolicy();
        //permissionOperations.listGroupPolicies();
        //permissionOperations.detachGroupPolicy();
    }

    public static void cloudWatchOperations() {
        MyCloudWatchClient myCloudWatchClient = new MyCloudWatchClient();
        MyCloudWatchLogsClient myCloudWatchLogsClient = new MyCloudWatchLogsClient();
        MyCloudWatchEventsClient myCloudWatchEventsClient = new MyCloudWatchEventsClient();
        CloudWatchClient cloudWatchClient = myCloudWatchClient.initialize();
        CloudWatchLogsClient cloudWatchLogsClient = myCloudWatchLogsClient.initialize();
        CloudWatchEventsClient cloudWatchEventsClient = myCloudWatchEventsClient.initialize();

        MetricOperations metricOperations = new MetricOperations(cloudWatchClient);
        //metricOperations.putDashboard();
        //metricOperations.getDashboard();
        //metricOperations.listDashboards();
        //metricOperations.deleteDashboard();
        //metricOperations.putMetricAlarm();
        //metricOperations.describeAlarms();
        //metricOperations.disableAlarmActions();
        //metricOperations.enableAlarmActions();
        //metricOperations.deleteAlarms();
        //metricOperations.putMetricData();
        //metricOperations.listMetrics();
        //metricOperations.getMetricData();

        LogOperations logOperations = new LogOperations(cloudWatchLogsClient);
        //logOperations.createLogGroup();
        //logOperations.describeLogGroup();
        //logOperations.createLogStream();
        //logOperations.describeLogStream();
        //logOperations.deleteLogStream();
        //logOperations.deleteLogGroup();
        //logOperations.putLogEvents();
        //logOperations.getLogEvents();
        //logOperations.query();

        EventOperations eventOperations = new EventOperations(cloudWatchEventsClient);
        eventOperations.putRule();
    }

}
