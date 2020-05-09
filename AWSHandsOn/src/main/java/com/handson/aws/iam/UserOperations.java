package com.handson.aws.iam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.*;

public class UserOperations {

    private IamClient iamClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public UserOperations(final IamClient iamClient) {
        this.iamClient = iamClient;
    }

    public void createUser() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .build();
        iamClient.createUser(createUserRequest);
    }

    public void updateUser() {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .newUserName(Constants.IAM_USER_NAME)
                .build();
        iamClient.updateUser(updateUserRequest);
    }

    public void listUsers() {
        ListUsersResponse listUsersResponse = iamClient.listUsers();
        System.out.println("Users: " + gson.toJson(listUsersResponse.users()));
    }

    public void deleteUser() {
        DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .build();
        iamClient.deleteUser(deleteUserRequest);
    }

    public void createGroup() {
        CreateGroupRequest createGroupRequest = CreateGroupRequest.builder()
                .groupName(Constants.IAM_GROUP_NAME)
                .build();
        iamClient.createGroup(createGroupRequest);
    }

    public void listGroups() {
        ListGroupsResponse listGroupsResponse = iamClient.listGroups();
        System.out.println("Groups: " + gson.toJson(listGroupsResponse.groups()));
    }

    public void deleteGroup() {
        DeleteGroupRequest deleteGroupRequest = DeleteGroupRequest.builder()
                .groupName(Constants.IAM_GROUP_NAME)
                .build();
        iamClient.deleteGroup(deleteGroupRequest);
    }

    public void addUserToGroup() {
        AddUserToGroupRequest addUserToGroupRequest = AddUserToGroupRequest.builder()
                .groupName(Constants.IAM_GROUP_NAME)
                .userName(Constants.IAM_USER_NAME)
                .build();
        iamClient.addUserToGroup(addUserToGroupRequest);
    }

    public void listGroupsForUser() {
        ListGroupsForUserRequest listGroupsForUserRequest = ListGroupsForUserRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .build();
        ListGroupsForUserResponse listGroupsForUserResponse = iamClient.listGroupsForUser(listGroupsForUserRequest);
        System.out.println("Groups for User " + Constants.IAM_USER_NAME + ": " + gson.toJson(listGroupsForUserResponse.groups()));
    }

    public void removeUserFromGroup() {
        RemoveUserFromGroupRequest removeUserFromGroupRequest = RemoveUserFromGroupRequest.builder()
                .groupName(Constants.IAM_GROUP_NAME)
                .userName(Constants.IAM_USER_NAME)
                .build();
        iamClient.removeUserFromGroup(removeUserFromGroupRequest);
    }

    public void createAccessKey() {
        CreateAccessKeyRequest createAccessKeyRequest = CreateAccessKeyRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .build();
        iamClient.createAccessKey(createAccessKeyRequest);
    }

    public void updateAccessKey() {
        UpdateAccessKeyRequest updateAccessKeyRequest = UpdateAccessKeyRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .accessKeyId(Constants.IAM_ACCESS_KEY)
                .status(StatusType.ACTIVE)
                .build();
        iamClient.updateAccessKey(updateAccessKeyRequest);
    }

    public void listAccessKeys() {
        ListAccessKeysRequest listAccessKeysRequest = ListAccessKeysRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .build();
        ListAccessKeysResponse listAccessKeysResponse = iamClient.listAccessKeys(listAccessKeysRequest);
        System.out.println("AccessKeys: " + gson.toJson(listAccessKeysResponse.accessKeyMetadata()));
    }

    public void deleteAccessKey() {
        DeleteAccessKeyRequest deleteAccessKeyRequest = DeleteAccessKeyRequest.builder()
                .userName(Constants.IAM_USER_NAME)
                .accessKeyId(Constants.IAM_ACCESS_KEY)
                .build();
        iamClient.deleteAccessKey(deleteAccessKeyRequest);
    }

}
