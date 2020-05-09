package com.handson.aws.dynamodb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

public class TableOperations {

    private DynamoDbClient dynamoDbClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public TableOperations(final DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void createTable() {
        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName(Constants.DDB_HASH_KEY)
                                .attributeType(ScalarAttributeType.S)
                                .build(),
                        AttributeDefinition.builder()
                                .attributeName(Constants.DDB_RANGE_KEY)
                                .attributeType(ScalarAttributeType.S)
                                .build())
                .keySchema(
                        KeySchemaElement.builder()
                                .attributeName(Constants.DDB_HASH_KEY)
                                .keyType(KeyType.HASH)
                                .build(),
                        KeySchemaElement.builder()
                                .attributeName(Constants.DDB_RANGE_KEY)
                                .keyType(KeyType.RANGE)
                                .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(new Long(5))
                        .writeCapacityUnits(new Long(5))
                        .build())
                .tableName(Constants.DDB_TABLE_NAME)
                .build();
        dynamoDbClient.createTable(createTableRequest);
    }

    public void listTables() {
        ListTablesResponse listTablesResponse = dynamoDbClient.listTables();
        System.out.println("Tables: " + gson.toJson(listTablesResponse.tableNames()));
    }

    public void describeTable() {
        DescribeTableRequest describeTableRequest = DescribeTableRequest.builder()
                .tableName(Constants.DDB_TABLE_NAME)
                .build();
        DescribeTableResponse describeTableResponse = dynamoDbClient.describeTable(describeTableRequest);
        System.out.println("Table Description: " + gson.toJson(describeTableResponse.table()));
    }

    public void updateTable() {
        UpdateTableRequest updateTableRequest = UpdateTableRequest.builder()
                .tableName(Constants.DDB_TABLE_NAME)
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(new Long(10))
                        .writeCapacityUnits(new Long(10))
                        .build())
                .build();
        dynamoDbClient.updateTable(updateTableRequest);
    }

    public void deleteTable() {
        DeleteTableRequest deleteTableRequest = DeleteTableRequest.builder()
                .tableName(Constants.DDB_TABLE_NAME)
                .build();
        dynamoDbClient.deleteTable(deleteTableRequest);
    }

}
