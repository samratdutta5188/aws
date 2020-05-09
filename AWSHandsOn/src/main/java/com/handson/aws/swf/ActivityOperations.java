package com.handson.aws.swf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.swf.SwfClient;
import software.amazon.awssdk.services.swf.model.*;

public class ActivityOperations {

    private SwfClient swfClient;

    private FlightBookingActivities flightBookingActivities;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ActivityOperations(final SwfClient swfClient, final FlightBookingActivities flightBookingActivities) {
        this.swfClient = swfClient;
        this.flightBookingActivities = flightBookingActivities;
    }

    public void registerActivityType() {
        RegisterActivityTypeRequest registerActivityTypeRequest = RegisterActivityTypeRequest.builder()
                .domain(Constants.DOMAIN)
                .name(Constants.SWF_ACTIVITY)
                .version(Constants.SWF_ACTIVITY_VERSION)
                .defaultTaskList(TaskList.builder().name(Constants.SWF_TASKLIST).build())
                .defaultTaskScheduleToStartTimeout(Constants.SWF_ACTIVITY_SCHEDULED_TO_START_TIMEOUT)
                .defaultTaskStartToCloseTimeout(Constants.SWF_ACTIVITY_TASK_TIMEOUT)
                .defaultTaskScheduleToCloseTimeout(Constants.SWF_ACTIVITY_SCHEDULED_TO_CLOSE_TIMEOUT)
                .defaultTaskHeartbeatTimeout(Constants.SWF_ACTIVITY_HEARTBEAT_TIMEOUT)
                .build();
        swfClient.registerActivityType(registerActivityTypeRequest);
    }

    public void executeActivities() {
        PollForActivityTaskResponse task = swfClient.pollForActivityTask(
                PollForActivityTaskRequest.builder()
                        .domain(Constants.DOMAIN)
                        .taskList(TaskList.builder().name(Constants.SWF_TASKLIST).build())
                        .build());

        String taskToken = task.taskToken();

        if (taskToken != null) {
            String result = null;
            Throwable error = null;

            try {
                result = flightBookingActivities.gotoWebsite();
                result = result + " And " + flightBookingActivities.searchFlight();
                result = result + " And " + flightBookingActivities.selectFlight();
                result = result + " And " + flightBookingActivities.makePayment();
                result = result + " And " + flightBookingActivities.getConfirmation();
            } catch (Throwable th) {
                error = th;
            }

            if (error == null) {
                swfClient.respondActivityTaskCompleted(
                        RespondActivityTaskCompletedRequest.builder()
                                .taskToken(taskToken)
                                .result(result).build());
            } else {
                swfClient.respondActivityTaskFailed(
                        RespondActivityTaskFailedRequest.builder()
                                .taskToken(taskToken)
                                .reason(error.getClass().getSimpleName())
                                .details(error.getMessage())
                                .build());
            }
        }
    }

}
