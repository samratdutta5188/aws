package com.handson.aws.swf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.swf.SwfClient;
import software.amazon.awssdk.services.swf.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkflowOperations {

    private SwfClient swfClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public WorkflowOperations(final SwfClient swfClient) {
        this.swfClient = swfClient;
    }

    public void registerDomain() {
        swfClient.registerDomain(RegisterDomainRequest.builder()
                .name(Constants.DOMAIN)
                .workflowExecutionRetentionPeriodInDays("1").build());
    }

    public void registerWorkflowType() {
        RegisterWorkflowTypeRequest registerWorkflowTypeRequest = RegisterWorkflowTypeRequest.builder()
                .domain(Constants.DOMAIN)
                .name(Constants.SWF_WORKFLOW)
                .version(Constants.SWF_WORKFLOW_VERSION)
                .defaultChildPolicy(ChildPolicy.TERMINATE)
                .defaultTaskList(TaskList.builder().name(Constants.SWF_TASKLIST).build())
                .defaultTaskStartToCloseTimeout(Constants.SWF_WORKFLOW_TASK_TIMEOUT)
                .build();
        swfClient.registerWorkflowType(registerWorkflowTypeRequest);
    }

    public void startWorkflow() {
        WorkflowType workflowType = WorkflowType.builder()
                .name(Constants.SWF_WORKFLOW)
                .version(Constants.SWF_WORKFLOW_VERSION)
                .build();

        StartWorkflowExecutionRequest startWorkflowExecutionRequest = StartWorkflowExecutionRequest.builder()
                .domain(Constants.DOMAIN)
                .workflowType(workflowType)
                .workflowId(Constants.SWF_WORKFLOW_EXECUTION)
                .input(Constants.SWF_WORKFLOW_INPUT)
                .executionStartToCloseTimeout(Constants.SWF_WORKFLOW_EXECUTION_START_TO_CLOSE_TIMEOUT)
                .build();
        swfClient.startWorkflowExecution(startWorkflowExecutionRequest);
    }

    public void executeDecisions() {
        PollForDecisionTaskRequest taskRequest =
                PollForDecisionTaskRequest.builder()
                        .domain(Constants.DOMAIN)
                        .taskList(TaskList.builder().name(Constants.SWF_TASKLIST).build())
                        .build();

        PollForDecisionTaskResponse task = swfClient.pollForDecisionTask(taskRequest);

        String taskToken = task.taskToken();
        if (taskToken != null) {
            try {
                executeDecisionTask(taskToken, task.events());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void executeDecisionTask(final String taskToken, final List<HistoryEvent> events)
            throws Throwable {
        List<Decision> decisions = new ArrayList<Decision>();
        String workflowInput = null;
        int scheduledActivities = 0;
        int openActivities = 0;
        boolean activityCompleted = false;
        String result = null;

        System.out.println("Executing the decision task for the history events: [");
        for (HistoryEvent event : events) {
            System.out.println("  " + event);
            switch (event.eventType()) {
                case WORKFLOW_EXECUTION_STARTED:
                    workflowInput =
                            event.workflowExecutionStartedEventAttributes()
                                    .input();
                    break;
                case ACTIVITY_TASK_SCHEDULED:
                    scheduledActivities++;
                    break;
                case SCHEDULE_ACTIVITY_TASK_FAILED:
                    scheduledActivities--;
                    break;
                case ACTIVITY_TASK_STARTED:
                    scheduledActivities--;
                    openActivities++;
                    break;
                case ACTIVITY_TASK_COMPLETED:
                    openActivities--;
                    activityCompleted = true;
                    result = event.activityTaskCompletedEventAttributes()
                            .result();
                    break;
                case ACTIVITY_TASK_FAILED:
                    openActivities--;
                    break;
                case ACTIVITY_TASK_TIMED_OUT:
                    openActivities--;
                    break;
                default:
                    break;
            }
        }
        System.out.println("]");

        if (activityCompleted) {
            decisions.add(
                    Decision.builder()
                            .decisionType(DecisionType.COMPLETE_WORKFLOW_EXECUTION)
                            .completeWorkflowExecutionDecisionAttributes(
                                    CompleteWorkflowExecutionDecisionAttributes.builder()
                                            .result(result)
                                            .build())
                            .build());
        } else {
            if (openActivities == 0 && scheduledActivities == 0) {

                ScheduleActivityTaskDecisionAttributes attrs =
                        ScheduleActivityTaskDecisionAttributes.builder()
                                .activityType(ActivityType.builder()
                                        .name(Constants.SWF_ACTIVITY)
                                        .version(Constants.SWF_ACTIVITY_VERSION)
                                        .build())
                                .activityId(UUID.randomUUID().toString())
                                .input(workflowInput)
                                .build();

                decisions.add(
                        Decision.builder()
                                .decisionType(DecisionType.SCHEDULE_ACTIVITY_TASK)
                                .scheduleActivityTaskDecisionAttributes(attrs).build());
            } else {
                // an instance of HelloActivity is already scheduled or running. Do nothing, another
                // task will be scheduled once the activity completes, fails or times out
            }
        }

        System.out.println("Exiting the decision task with the decisions " + decisions);

        swfClient.respondDecisionTaskCompleted(
                RespondDecisionTaskCompletedRequest.builder()
                        .taskToken(taskToken)
                        .decisions(decisions)
                        .build());
    }

}
