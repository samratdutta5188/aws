package com.handson.aws.iam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PermissionOperations {

    private IamClient iamClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public PermissionOperations(final IamClient iamClient) {
        this.iamClient = iamClient;
    }

    public void createPolicy() {
        StringBuilder policy = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(Constants.IAM_S3_POLICY_FILE), StandardCharsets.UTF_8)) {
            stream.forEach(s -> policy.append(s).append("\n"));
        } catch (IOException e) {
            System.out.println("Exception occurred during Policy File parsing: " + e.getMessage());
        }
        CreatePolicyRequest createPolicyRequest = CreatePolicyRequest.builder()
                .policyName(Constants.IAM_S3_POLICY)
                .policyDocument(policy.toString())
                .build();
        iamClient.createPolicy(createPolicyRequest);
    }

    public void getPolicy() {
        GetPolicyRequest getPolicyRequest = GetPolicyRequest.builder()
                .policyArn(Constants.IAM_S3_POLICY_ARN)
                .build();
        GetPolicyResponse getPolicyResponse = iamClient.getPolicy(getPolicyRequest);
        System.out.println("Policy: " + gson.toJson(getPolicyResponse.policy()));
    }

    public void deletePolicy() {
        DeletePolicyRequest deletePolicyRequest = DeletePolicyRequest.builder()
                .policyArn(Constants.IAM_S3_POLICY_ARN)
                .build();
        iamClient.deletePolicy(deletePolicyRequest);
    }

    public void createRole() {
        StringBuilder assumedRolePolicy = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(Constants.IAM_ASSUMED_ROLE_POLICY_FILE), StandardCharsets.UTF_8)) {
            stream.forEach(s -> assumedRolePolicy.append(s).append("\n"));
        } catch (IOException e) {
            System.out.println("Exception occurred during Policy File parsing: " + e.getMessage());
        }
        CreateRoleRequest createRoleRequest = CreateRoleRequest.builder()
                .roleName(Constants.IAM_ROLE_NAME)
                .assumeRolePolicyDocument(assumedRolePolicy.toString())
                .build();
        iamClient.createRole(createRoleRequest);
    }

    public void getRole() {
        GetRoleRequest getRoleRequest = GetRoleRequest.builder()
                .roleName(Constants.IAM_ROLE_NAME)
                .build();
        GetRoleResponse getRoleResponse = iamClient.getRole(getRoleRequest);
        System.out.println("Role: " + gson.toJson(getRoleResponse.role()));
    }

    public void deleteRole() {
        DeleteRoleRequest deleteRoleRequest = DeleteRoleRequest.builder()
                .roleName(Constants.IAM_ROLE_NAME)
                .build();
        iamClient.deleteRole(deleteRoleRequest);
    }

    public void attachRolePolicy() {
        AttachRolePolicyRequest attachRolePolicyRequest = AttachRolePolicyRequest.builder()
                .roleName(Constants.IAM_ROLE_NAME)
                .policyArn(Constants.IAM_S3_POLICY_ARN)
                .build();
        iamClient.attachRolePolicy(attachRolePolicyRequest);
    }

    public void listRolePolicy() {
        ListRolePoliciesRequest listRolePoliciesRequest = ListRolePoliciesRequest.builder()
                .roleName(Constants.IAM_ROLE_NAME)
                .build();
        ListRolePoliciesResponse listRolePoliciesResponse = iamClient.listRolePolicies(listRolePoliciesRequest);
        System.out.println("Role Policies: " + gson.toJson(listRolePoliciesResponse.policyNames()));
    }

    public void detachRolePolicy() {
        DetachRolePolicyRequest detachRolePolicyRequest = DetachRolePolicyRequest.builder()
                .roleName(Constants.IAM_ROLE_NAME)
                .policyArn(Constants.IAM_S3_POLICY_ARN)
                .build();
        iamClient.detachRolePolicy(detachRolePolicyRequest);
    }

    public void attachUserPolicy() {
        AttachUserPolicyRequest attachUserPolicyRequest = AttachUserPolicyRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .policyArn(Constants.IAM_S3_POLICY_ARN)
                .build();
        iamClient.attachUserPolicy(attachUserPolicyRequest);
    }

    public void listUserPolicies() {
        ListUserPoliciesRequest listUserPoliciesRequest = ListUserPoliciesRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .build();
        ListUserPoliciesResponse listUserPoliciesResponse = iamClient.listUserPolicies(listUserPoliciesRequest);
        System.out.println("User Policies: " + gson.toJson(listUserPoliciesResponse.policyNames()));
    }

    public void detachUserPolicy() {
        DetachUserPolicyRequest detachUserPolicyRequest = DetachUserPolicyRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .policyArn(Constants.IAM_S3_POLICY_ARN)
                .build();
        iamClient.detachUserPolicy(detachUserPolicyRequest);
    }

    public void attachGroupPolicy() {
        AttachGroupPolicyRequest attachGroupPolicyRequest = AttachGroupPolicyRequest.builder()
                .groupName(Constants.IAM_GROUP_NAME)
                .policyArn(Constants.IAM_S3_POLICY_ARN)
                .build();
        iamClient.attachGroupPolicy(attachGroupPolicyRequest);
    }

    public void listGroupPolicies() {
        ListGroupPoliciesRequest listGroupPoliciesRequest = ListGroupPoliciesRequest.builder()
                .groupName(Constants.IAM_GROUP_NAME)
                .build();
        ListGroupPoliciesResponse listGroupPoliciesResponse = iamClient.listGroupPolicies(listGroupPoliciesRequest);
        System.out.println("Group Policies: " + gson.toJson(listGroupPoliciesResponse.policyNames()));
    }

    public void detachGroupPolicy() {
        DetachGroupPolicyRequest detachGroupPolicyRequest = DetachGroupPolicyRequest.builder()
                .groupName(Constants.IAM_GROUP_NAME)
                .policyArn(Constants.IAM_S3_POLICY_ARN)
                .build();
        iamClient.detachGroupPolicy(detachGroupPolicyRequest);
    }

}
