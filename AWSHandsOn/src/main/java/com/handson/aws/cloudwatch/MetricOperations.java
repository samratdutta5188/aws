package com.handson.aws.cloudwatch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class MetricOperations {

    private CloudWatchClient cloudWatchClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public MetricOperations(final CloudWatchClient cloudWatchClient) {
        this.cloudWatchClient = cloudWatchClient;
    }

    public void putDashboard() {
        StringBuilder dashboardBody = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(Constants.CW_DASHBOARD_BODY), StandardCharsets.UTF_8)) {
            stream.forEach(s -> dashboardBody.append(s).append("\n"));
        } catch (IOException e) {
            System.out.println("Exception occurred during Policy File parsing: " + e.getMessage());
        }
        PutDashboardRequest putDashboardRequest = PutDashboardRequest.builder()
                .dashboardName(Constants.CW_DASHBOARD_NAME)
                .dashboardBody(dashboardBody.toString())
                .build();
        cloudWatchClient.putDashboard(putDashboardRequest);
    }

    public void getDashboard() {
        GetDashboardRequest getDashboardRequest = GetDashboardRequest.builder()
                .dashboardName(Constants.CW_DASHBOARD_NAME)
                .build();
        GetDashboardResponse getDashboardResponse = cloudWatchClient.getDashboard(getDashboardRequest);
        System.out.println("Dashboard Name: " + gson.toJson(getDashboardResponse.dashboardName()));
        System.out.println("Dashboard ARN: " + gson.toJson(getDashboardResponse.dashboardArn()));
        System.out.println("Dashboard Body: " + gson.toJson(getDashboardResponse.dashboardBody()));
    }

    public void listDashboards() {
        ListDashboardsResponse listDashboardsResponse = cloudWatchClient.listDashboards();
        System.out.println("Dashboard Name: " + gson.toJson(listDashboardsResponse.dashboardEntries().get(0).dashboardName()));
        System.out.println("Dashboard ARN: " + gson.toJson(listDashboardsResponse.dashboardEntries().get(0).dashboardArn()));
    }

    public void deleteDashboard() {
        DeleteDashboardsRequest deleteDashboardsRequest = DeleteDashboardsRequest.builder()
                .dashboardNames(Constants.CW_DASHBOARD_NAME)
                .build();
        cloudWatchClient.deleteDashboards(deleteDashboardsRequest);
    }

    public void putMetricAlarm() {
        Metric metric = Metric.builder()
                .namespace("AWS/S3")
                .metricName("BucketSizeBytes")
                .dimensions(Dimension.builder().name("StorageType").value("StandardStorage").build(),
                        Dimension.builder().name("BucketName").value(Constants.S3_BUCKET).build())
                .build();
        MetricDataQuery metricDataQuery = MetricDataQuery.builder()
                .id("m1")
                .metricStat(MetricStat.builder()
                        .metric(metric)
                        .period(86400)
                        .stat("Sum")
                        .build())
                .returnData(true)
                .build();
        PutMetricAlarmRequest putMetricAlarmRequest = PutMetricAlarmRequest.builder()
                .alarmName("S3 Bucket Size Alarm")
                .comparisonOperator(ComparisonOperator.GREATER_THAN_THRESHOLD)
                .evaluationPeriods(1)
                .threshold(100.0)
                .metrics(metricDataQuery)
                .actionsEnabled(true)
                .alarmActions("arn:aws:sns:us-west-2:128684055682:Default_CloudWatch_Alarms_Topic")
                .alarmDescription("Alarm when S3 Bucket Size Bytes is greater than 100")
                .build();
        cloudWatchClient.putMetricAlarm(putMetricAlarmRequest);
    }

    public void describeAlarms() {
        DescribeAlarmsResponse describeAlarmsResponse = cloudWatchClient.describeAlarms();
        System.out.println("Alarms: " + gson.toJson(describeAlarmsResponse.metricAlarms()));
    }

    public void disableAlarmActions() {
        DisableAlarmActionsRequest disableAlarmActionsRequest = DisableAlarmActionsRequest.builder()
                .alarmNames("S3 Bucket Size Alarm")
                .build();
        cloudWatchClient.disableAlarmActions(disableAlarmActionsRequest);
    }

    public void enableAlarmActions() {
        EnableAlarmActionsRequest enableAlarmActionsRequest = EnableAlarmActionsRequest.builder()
                .alarmNames("S3 Bucket Size Alarm")
                .build();
        cloudWatchClient.enableAlarmActions(enableAlarmActionsRequest);
    }

    public void deleteAlarms() {
        DeleteAlarmsRequest deleteAlarmsRequest = DeleteAlarmsRequest.builder()
                .alarmNames("S3 Bucket Size Alarm")
                .build();
        cloudWatchClient.deleteAlarms(deleteAlarmsRequest);
    }

    public void putMetricData() {
        PutMetricDataRequest putMetricDataRequest = PutMetricDataRequest.builder()
                .namespace("SamratAWSHandsOnMetrics")
                .metricData(MetricDatum.builder()
                        .metricName("ViewMetric")
                        .value(1.0)
                        .unit(StandardUnit.COUNT)
                        .timestamp(Instant.now())
                        .build())
                .build();
        cloudWatchClient.putMetricData(putMetricDataRequest);
    }

    public void listMetrics() {
        ListMetricsRequest listMetricsRequest = ListMetricsRequest.builder()
                .namespace("AWS/S3")
                .build();
        ListMetricsResponse listMetricsResponse = cloudWatchClient.listMetrics(listMetricsRequest);
        System.out.println("S3 Metrics: " + gson.toJson(listMetricsResponse.metrics()));
    }

    public void getMetricData() {
        GetMetricDataRequest getMetricDataRequest = GetMetricDataRequest.builder()
                .metricDataQueries(MetricDataQuery.builder()
                        .id("m1")
                        .metricStat(MetricStat.builder()
                                .metric(cloudWatchClient.listMetrics(ListMetricsRequest.builder()
                                        .namespace("AWS/S3")
                                        .build()).metrics().get(0))
                                .period(86400)
                                .stat("Sum")
                                .build())
                        .returnData(true)
                        .build())
                .startTime(Instant.now().minus(Duration.ofDays(7)))
                .endTime(Instant.now())
                .build();
        GetMetricDataResponse getMetricDataResponse = cloudWatchClient.getMetricData(getMetricDataRequest);
        System.out.println("S3 Metric Data: " + gson.toJson(getMetricDataResponse.metricDataResults()));
    }

}
