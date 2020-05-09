package com.handson.aws.cloudwatch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.amazon.awssdk.services.cloudwatchevents.CloudWatchEventsClient;
import software.amazon.awssdk.services.cloudwatchevents.model.PutRuleRequest;
import software.amazon.awssdk.services.cloudwatchevents.model.RuleState;

public class EventOperations {

    private CloudWatchEventsClient cloudWatchEventsClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public EventOperations(final CloudWatchEventsClient cloudWatchEventsClient) {
        this.cloudWatchEventsClient = cloudWatchEventsClient;
    }

    public void putRule() {
        PutRuleRequest putRuleRequest = PutRuleRequest.builder()
                .name("TestRule")
                .scheduleExpression("rate(5 minutes)")
                .state(RuleState.ENABLED)
                .build();
        cloudWatchEventsClient.putRule(putRuleRequest);
    }
}
