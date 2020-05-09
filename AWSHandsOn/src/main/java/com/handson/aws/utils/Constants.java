package com.handson.aws.utils;

public class Constants {

    public static final String ACCOUNT_ID = "128684055682";

    public static final String S3_BUCKET = "samrat-aws-handson";

    public static final String S3_FILE_NAME = "FileToUpload.txt";

    public static final String S3_COPY_FILE_NAME = "FileToCopy.txt";

    public static final String S3_FILE_PATH = "/Users/samdutta/workspace/samdutta/SamGit/aws/AWSHandsOn/src/main/resources/s3/FileToUpload.txt";

    public static final String S3_DOWNLOAD_FILE_PATH = "/Users/samdutta/workspace/samdutta/FileDownloaded.txt";

    public static final String S3_BUCKET_POLICY =
            "/Users/samdutta/workspace/samdutta/SamGit/aws/AWSHandsOn/src/main/resources/s3/S3Policy.json";

    public static final String DDB_TABLE_NAME = "HotelCatalog";

    public static final String DDB_HASH_KEY = "HotelName";

    public static final String DDB_RANGE_KEY = "City";

    public static final String SQS_QUEUE_NAME = "SamratAwsHandsOn";

    public static final String SQS_DLQ_NAME = "SamratAwsHandsOnDLQ";

    public static final String SQS_QUEUE_NAME_PREFIX = "Samrat";

    public static final String SQS_QUEUE_URL = "https://sqs.us-west-2.amazonaws.com/128684055682/SamratAwsHandsOn";

    public static final String SQS_QUEUE_ARN = "arn:aws:sqs:us-west-2:128684055682:SamratAwsHandsOn";

    public static final String SQS_SEND_MESSAGE_BODY = "My awesome message";

    public static final String SQS_SEND_BATCH_FIRST_MESSAGE_BODY = "My first batch message";

    public static final String SQS_SEND_BATCH_FIRST_MESSAGE_ID = "Batch1_ID1";

    public static final String SQS_SEND_BATCH_SECOND_MESSAGE_BODY = "My second batch  message";

    public static final String SQS_SEND_BATCH_SECOND_MESSAGE_ID = "Batch1_ID2";

    public static final Integer SQS_SEND_MESSAGE_DELAY = 5;

    public static final String SQS_ADD_PERMISSION_ACTION = "SendMessage";

    public static final String SNS_TOPIC_NAME = "SamratAwsHandsOnTopic";

    public static final String SNS_TOPIC_ARN = "arn:aws:sns:us-west-2:128684055682:SamratAwsHandsOnTopic";

    public static final String SNS_SUBSCRIBE_EMAIL_PROTOCOL = "email";

    public static final String SNS_SUBSCRIBE_SQS_PROTOCOL = "sqs";

    public static final String SNS_PUBLISH_MESSAGE = "My awesome SNS message";

    public static final String SNS_EMAIL_SUBSCRIPTION_ARN =
            "arn:aws:sns:us-west-2:128684055682:SamratAwsHandsOnTopic:9f41c600-6fb4-424a-8c58-f1828393b0dd";

    public static final String SNS_CONFIRM_SUBSCRIPTION_TOKEN = "*** YOUR TOKEN ***";

    public static final String EMAIL = "samrat.dutta501@gmail.com";

    public static final String DOMAIN = "samratawshandson.com";

    public static final String SES_EMAIL_SUBJECT = "Hello from me";

    public static final String SES_EMAIL_RECIPIENT = "samrat.dutta501@gmail.com";

    public static final String SES_EMAIL_BODY_TEXT = "This is the text part";

    public static final String SES_EMAIL_BODY_HTML = "<html>" + "<head></head>" + "<body>" + "<h1>Hello!</h1>" +
            "<p>This is a sample email from me</p>" + "</body>" + "</html>";

    public static final String SES_EMAIL_SUBJECT_CHARSET = "UTF-8";

    public static final String SES_EMAIL_MIMEMULTIPART_SUBTYPE = "alternative";

    public static final String SES_EMAIL_BODY_TYPE_TEXT = "text/plain; charset=UTF-8";

    public static final String SES_EMAIL_BODY_TYPE_HTML = "text/html; charset=UTF-8";

    public static final String SES_EMAIL_MIMEMULTIPART_PARENT_SUBTYPE = "parent";

    public static final String SES_EMAIL_ATTACHMENT_TYPE = "application/txt";

    public static final String SES_EMAIL_ATTACHMENT_NAME = "FileToEmail.txt";

    public static final String SWF_TASKLIST = "HelloTasklist";

    public static final String SWF_WORKFLOW = "HelloWorkflow";

    public static final String SWF_WORKFLOW_VERSION = "1.0";

    public static final String SWF_WORKFLOW_TASK_TIMEOUT = "30";

    public static final String SWF_WORKFLOW_EXECUTION = "HelloWorldWorkflowExecution";

    public static final String SWF_WORKFLOW_EXECUTION_START_TO_CLOSE_TIMEOUT = "180";

    public static final String SWF_WORKFLOW_INPUT = "Amazon SWF";

    public static final String SWF_ACTIVITY = "HelloActivity";

    public static final String SWF_ACTIVITY_VERSION = "1.0";

    public static final String SWF_ACTIVITY_TASK_TIMEOUT = "600";

    public static final String SWF_ACTIVITY_SCHEDULED_TO_START_TIMEOUT = "60";

    public static final String SWF_ACTIVITY_SCHEDULED_TO_CLOSE_TIMEOUT = "630";

    public static final String SWF_ACTIVITY_HEARTBEAT_TIMEOUT = "10";

    public static final String IAM_USER_NAME = "TestUser";

    public static final String IAM_ACCESS_KEY = "AKIAR35RK3SBHNKJRR6Q";

    public static final String IAM_S3_POLICY = "SamratPolicyTestReadWritePolicy";

    public static final String IAM_S3_POLICY_FILE =
            "/Users/samdutta/workspace/samdutta/SamGit/aws/AWSHandsOn/src/main/resources/s3/SamratPolicyTestReadWritePolicy.json";

    public static final String IAM_S3_POLICY_ARN = "arn:aws:iam::128684055682:policy/SamratPolicyTestReadWritePolicy";

    public static final String IAM_ROLE_NAME = "SamratTestRole";

    public static final String IAM_GROUP_NAME = "SamratTestGroup";

    public static final String IAM_ASSUMED_ROLE_POLICY_FILE =
            "/Users/samdutta/workspace/samdutta/SamGit/aws/AWSHandsOn/src/main/resources/iam/SamratAssumedRolePolicy.json";

    public static final String CW_DASHBOARD_NAME = "SamratAwsHandsOnDashboard";

    public static final String CW_DASHBOARD_BODY =
            "/Users/samdutta/workspace/samdutta/SamGit/aws/AWSHandsOn/src/main/resources/cloudwatch/Dashboard.json";

}
