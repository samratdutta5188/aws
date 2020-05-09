package com.handson.aws.cloudwatch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class LogOperations {

    private CloudWatchLogsClient cloudWatchLogsClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public LogOperations(final CloudWatchLogsClient cloudWatchLogsClient) {
        this.cloudWatchLogsClient = cloudWatchLogsClient;
    }

    public void createLogGroup() {
        CreateLogGroupRequest createLogGroupRequest = CreateLogGroupRequest.builder()
                .logGroupName("TestLogGroup")
                .build();
        cloudWatchLogsClient.createLogGroup(createLogGroupRequest);
    }

    public void describeLogGroup() {
        DescribeLogGroupsRequest describeLogGroupsRequest = DescribeLogGroupsRequest.builder()
                .logGroupNamePrefix("TestLogGroup")
                .build();
        DescribeLogGroupsResponse describeLogGroupsResponse = cloudWatchLogsClient.describeLogGroups(describeLogGroupsRequest);
        System.out.println("LogGroups: " + gson.toJson(describeLogGroupsResponse.logGroups()));
    }

    public void deleteLogGroup() {
        DeleteLogGroupRequest deleteLogGroupRequest = DeleteLogGroupRequest.builder()
                .logGroupName("TestLogGroup")
                .build();
        cloudWatchLogsClient.deleteLogGroup(deleteLogGroupRequest);
    }

    public void createLogStream() {
        CreateLogStreamRequest createLogStreamRequest = CreateLogStreamRequest.builder()
                .logGroupName("TestLogGroup")
                .logStreamName("TestLogStream")
                .build();
        cloudWatchLogsClient.createLogStream(createLogStreamRequest);
    }

    public void describeLogStream() {
        DescribeLogStreamsRequest describeLogStreamsRequest = DescribeLogStreamsRequest.builder()
                .logGroupName("TestLogGroup")
                .logStreamNamePrefix("TestLogStream")
                .build();
        DescribeLogStreamsResponse describeLogStreamsResponse = cloudWatchLogsClient.describeLogStreams(describeLogStreamsRequest);
        System.out.println("LogStream: " + gson.toJson(describeLogStreamsResponse.logStreams()));
    }

    public void deleteLogStream() {
        DeleteLogStreamRequest deleteLogStreamRequest = DeleteLogStreamRequest.builder()
                .logGroupName("TestLogGroup")
                .logStreamName("TestLogStream")
                .build();
        cloudWatchLogsClient.deleteLogStream(deleteLogStreamRequest);
    }

    public void putLogEvents() {
        DescribeLogStreamsRequest logStreamRequest = DescribeLogStreamsRequest.builder()
                .logGroupName("SamratTestServiceLogs")
                .logStreamNamePrefix("SamratTestServiceLogStream")
                .build();
        DescribeLogStreamsResponse describeLogStreamsResponse = cloudWatchLogsClient.describeLogStreams(logStreamRequest);
        String sequenceToken = describeLogStreamsResponse.logStreams().get(0).uploadSequenceToken();

        InputLogEvent inputLogEvent = InputLogEvent.builder()
                .message("This is a sample log message: { \"key1\": \"value1\", \"key2\": \"value2\" }")
                .timestamp(System.currentTimeMillis())
                .build();

        PutLogEventsRequest putLogEventsRequest = PutLogEventsRequest.builder()
                .logEvents(Arrays.asList(inputLogEvent))
                .logGroupName("SamratTestServiceLogs")
                .logStreamName("SamratTestServiceLogStream")
                .sequenceToken(sequenceToken)
                .build();
        cloudWatchLogsClient.putLogEvents(putLogEventsRequest);
    }

    public void getLogEvents() {
        GetLogEventsRequest getLogEventsRequest = GetLogEventsRequest.builder()
                .logGroupName("SamratTestServiceLogs")
                .logStreamName("SamratTestServiceLogStream")
                .startFromHead(true)
                .build();
        GetLogEventsResponse getLogEventsResponse = cloudWatchLogsClient.getLogEvents(getLogEventsRequest);
        getLogEventsResponse.events().forEach(event -> {
            System.out.println(gson.toJson(event));
        });
    }

    public void query() {
        StartQueryRequest startQueryRequest = StartQueryRequest.builder()
                .startTime(Instant.now().minus(Duration.ofHours(5)).toEpochMilli())
                .endTime(Instant.now().toEpochMilli())
                .logGroupName("SamratTestServiceLogs")
                .queryString("fields @timestamp, @message\n" +
                        "| filter @message like /sample/")
                .build();
        String queryId = cloudWatchLogsClient.startQuery(startQueryRequest).queryId();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Some exception occurred");
        }
        GetQueryResultsRequest getQueryResultsRequest = GetQueryResultsRequest.builder()
                .queryId(queryId)
                .build();
        GetQueryResultsResponse getQueryResultsResponse = cloudWatchLogsClient.getQueryResults(getQueryResultsRequest);
        System.out.println(gson.toJson(getQueryResultsResponse.results()));
    }
}
