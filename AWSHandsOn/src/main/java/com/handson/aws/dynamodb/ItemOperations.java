package com.handson.aws.dynamodb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.dynamodb.model.HotelCatalogRecord;
import com.handson.aws.dynamodb.model.HotelFeature;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemOperations {

    private DynamoDbClient dynamoDbClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ItemOperations(final DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void putItem() {
        HashMap<String, AttributeValue> hotelFeature1 = new HashMap<String, AttributeValue>();
        hotelFeature1.put(HotelFeature.PARKING, AttributeValue.builder().bool(true).build());
        hotelFeature1.put(HotelFeature.SWIMMING_POOL, AttributeValue.builder().bool(true).build());
        hotelFeature1.put(HotelFeature.LIFTS, AttributeValue.builder().n("5").build());

        HashMap<String, AttributeValue> item1 = new HashMap<String, AttributeValue>();
        item1.put(HotelCatalogRecord.ATTR_HOTEL_NAME, AttributeValue.builder().s("Hotel Mystar").build());
        item1.put(HotelCatalogRecord.ATTR_CITY, AttributeValue.builder().s("London").build());
        item1.put(HotelCatalogRecord.ATTR_RATING, AttributeValue.builder().n("1").build());
        item1.put(HotelCatalogRecord.ATTR_HOTEL_FEATURE, AttributeValue.builder().m(hotelFeature1).build());

        HashMap<String, AttributeValue> hotelFeature2 = new HashMap<String, AttributeValue>();
        hotelFeature2.put(HotelFeature.PARKING, AttributeValue.builder().bool(true).build());
        hotelFeature2.put(HotelFeature.SWIMMING_POOL, AttributeValue.builder().bool(false).build());
        hotelFeature2.put(HotelFeature.LIFTS, AttributeValue.builder().n("2").build());

        HashMap<String, AttributeValue> item2 = new HashMap<String, AttributeValue>();
        item2.put(HotelCatalogRecord.ATTR_HOTEL_NAME, AttributeValue.builder().s("Hotel Life").build());
        item2.put(HotelCatalogRecord.ATTR_CITY, AttributeValue.builder().s("Sydney").build());
        item2.put(HotelCatalogRecord.ATTR_RATING, AttributeValue.builder().n("3").build());
        item2.put(HotelCatalogRecord.ATTR_HOTEL_FEATURE, AttributeValue.builder().m(hotelFeature2).build());

        List<HashMap<String, AttributeValue>> itemList = Arrays.asList(item1, item2);

        itemList.forEach(item -> {
            PutItemRequest putItemRequest = PutItemRequest.builder()
                    .tableName(Constants.DDB_TABLE_NAME)
                    .item(item)
                    .build();
            dynamoDbClient.putItem(putItemRequest);
        });
    }

    public void getItem() {
        HashMap<String, AttributeValue> itemToGet = new HashMap<String, AttributeValue>();
        itemToGet.put(HotelCatalogRecord.ATTR_HOTEL_NAME, AttributeValue.builder().s("Hilton Towers").build());
        itemToGet.put(HotelCatalogRecord.ATTR_CITY, AttributeValue.builder().s("Las Vegas").build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(Constants.DDB_TABLE_NAME)
                .key(itemToGet)
                .build();
        Map<String, AttributeValue> itemFetched = dynamoDbClient.getItem(getItemRequest).item();
        System.out.println("GetItemResponse: " + gson.toJson(itemFetched));

        System.out.println(HotelCatalogRecord.ATTR_HOTEL_NAME + ": " + itemFetched.get(HotelCatalogRecord.ATTR_HOTEL_NAME).s());
        System.out.println(HotelCatalogRecord.ATTR_CITY + ": " + itemFetched.get(HotelCatalogRecord.ATTR_CITY).s());
        System.out.println(HotelCatalogRecord.ATTR_RATING + ": " + itemFetched.get(HotelCatalogRecord.ATTR_RATING).n());
    }

    public void updateItem() {
        HashMap<String, AttributeValue> itemToUpdate = new HashMap<String, AttributeValue>();
        itemToUpdate.put(HotelCatalogRecord.ATTR_HOTEL_NAME, AttributeValue.builder().s("Hilton Towers").build());
        itemToUpdate.put(HotelCatalogRecord.ATTR_CITY, AttributeValue.builder().s("Las Vegas").build());

        HashMap<String, AttributeValueUpdate> attributeToUpdate = new HashMap<String, AttributeValueUpdate>();
        attributeToUpdate.put(HotelCatalogRecord.ATTR_RATING, AttributeValueUpdate.builder()
                .value(AttributeValue.builder().n("3").build())
                .action(AttributeAction.PUT)
                .build());

        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .tableName(Constants.DDB_TABLE_NAME)
                .key(itemToUpdate)
                .attributeUpdates(attributeToUpdate)
                .build();
        dynamoDbClient.updateItem(updateItemRequest);
    }

    public void queryItem() {
        HashMap<String, AttributeValue> attrValues = new HashMap<String, AttributeValue>();
        attrValues.put(":hotelName", AttributeValue.builder().s("Hilton Towers").build());
        attrValues.put(":city", AttributeValue.builder().s("Las Vegas").build());

        QueryRequest queryRequest = QueryRequest.builder()
                .tableName(Constants.DDB_TABLE_NAME)
                .keyConditionExpression(HotelCatalogRecord.ATTR_HOTEL_NAME + " = :hotelName AND " + HotelCatalogRecord.ATTR_CITY + " = :city")
                .expressionAttributeValues(attrValues)
                .build();
        QueryResponse queryResponse = dynamoDbClient.query(queryRequest);
        List<Map<String, AttributeValue>> itemsFetched = queryResponse.items();

        System.out.println("QueryResponse size: " + itemsFetched.size());

        System.out.println(HotelCatalogRecord.ATTR_HOTEL_NAME + ": " + itemsFetched.get(0).get(HotelCatalogRecord.ATTR_HOTEL_NAME).s());
        System.out.println(HotelCatalogRecord.ATTR_CITY + ": " + itemsFetched.get(0).get(HotelCatalogRecord.ATTR_CITY).s());
        System.out.println(HotelCatalogRecord.ATTR_RATING + ": " + itemsFetched.get(0).get(HotelCatalogRecord.ATTR_RATING).n());
    }

    public void scanItem() {
        HashMap<String, AttributeValue> attrValues = new HashMap<String, AttributeValue>();
        attrValues.put(":rating", AttributeValue.builder().n("2").build());

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(Constants.DDB_TABLE_NAME)
                .filterExpression(HotelCatalogRecord.ATTR_RATING + "< :rating")
                .expressionAttributeValues(attrValues)
                .build();
        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        List<Map<String, AttributeValue>> itemsFetched = scanResponse.items();

        System.out.println("ScanResponse size: " + itemsFetched.size());

        System.out.println(HotelCatalogRecord.ATTR_HOTEL_NAME + ": " + itemsFetched.get(0).get(HotelCatalogRecord.ATTR_HOTEL_NAME).s());
        System.out.println(HotelCatalogRecord.ATTR_CITY + ": " + itemsFetched.get(0).get(HotelCatalogRecord.ATTR_CITY).s());
        System.out.println(HotelCatalogRecord.ATTR_RATING + ": " + itemsFetched.get(0).get(HotelCatalogRecord.ATTR_RATING).n());
    }

    public void deleteItem() {
        HashMap<String, AttributeValue> itemToDelete = new HashMap<String, AttributeValue>();
        itemToDelete.put(HotelCatalogRecord.ATTR_HOTEL_NAME, AttributeValue.builder().s("Hilton Towers").build());
        itemToDelete.put(HotelCatalogRecord.ATTR_CITY, AttributeValue.builder().s("Las Vegas").build());

        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                .tableName(Constants.DDB_TABLE_NAME)
                .key(itemToDelete)
                .build();
        dynamoDbClient.deleteItem(deleteItemRequest);
    }

    public void batchGetItems() {
        HashMap<String, AttributeValue> itemToGet1 = new HashMap<String, AttributeValue>();
        itemToGet1.put(HotelCatalogRecord.ATTR_HOTEL_NAME, AttributeValue.builder().s("Hotel Life").build());
        itemToGet1.put(HotelCatalogRecord.ATTR_CITY, AttributeValue.builder().s("Sydney").build());

        HashMap<String, AttributeValue> itemToGet2 = new HashMap<String, AttributeValue>();
        itemToGet2.put(HotelCatalogRecord.ATTR_HOTEL_NAME, AttributeValue.builder().s("Hotel Mystar").build());
        itemToGet2.put(HotelCatalogRecord.ATTR_CITY, AttributeValue.builder().s("London").build());

        KeysAndAttributes keysAndAttributes = KeysAndAttributes.builder()
                .keys(itemToGet1, itemToGet2)
                .build();

        HashMap<String, KeysAndAttributes> itemsToGet = new HashMap<String, KeysAndAttributes>();
        itemsToGet.put(Constants.DDB_TABLE_NAME, keysAndAttributes);

        BatchGetItemRequest batchGetItemRequest = BatchGetItemRequest.builder()
                .requestItems(itemsToGet)
                .build();
        Map<String, List<Map<String, AttributeValue>>> batchGetResponse = dynamoDbClient.batchGetItem(batchGetItemRequest).responses();
        batchGetResponse.get(Constants.DDB_TABLE_NAME).forEach(item -> {
            System.out.println("Item: " + gson.toJson(item));

            System.out.println(HotelCatalogRecord.ATTR_HOTEL_NAME + ": " + item.get(HotelCatalogRecord.ATTR_HOTEL_NAME).s());
            System.out.println(HotelCatalogRecord.ATTR_CITY + ": " + item.get(HotelCatalogRecord.ATTR_CITY).s());
            System.out.println(HotelCatalogRecord.ATTR_RATING + ": " + item.get(HotelCatalogRecord.ATTR_RATING).n());
        });
    }

    public void batchWriteItems() {
        HashMap<String, AttributeValue> hotelFeature1 = new HashMap<String, AttributeValue>();
        hotelFeature1.put(HotelFeature.PARKING, AttributeValue.builder().bool(true).build());
        hotelFeature1.put(HotelFeature.SWIMMING_POOL, AttributeValue.builder().bool(true).build());
        hotelFeature1.put(HotelFeature.LIFTS, AttributeValue.builder().n("6").build());

        HashMap<String, AttributeValue> item1 = new HashMap<String, AttributeValue>();
        item1.put(HotelCatalogRecord.ATTR_HOTEL_NAME, AttributeValue.builder().s("Hotel Mystar").build());
        item1.put(HotelCatalogRecord.ATTR_CITY, AttributeValue.builder().s("Mumbai").build());
        item1.put(HotelCatalogRecord.ATTR_RATING, AttributeValue.builder().n("1").build());
        item1.put(HotelCatalogRecord.ATTR_HOTEL_FEATURE, AttributeValue.builder().m(hotelFeature1).build());

        PutRequest putRequest1 = PutRequest.builder().item(item1).build();
        WriteRequest writeRequest1 = WriteRequest.builder()
                .putRequest(putRequest1)
                .build();

        HashMap<String, AttributeValue> hotelFeature2 = new HashMap<String, AttributeValue>();
        hotelFeature2.put(HotelFeature.PARKING, AttributeValue.builder().bool(true).build());
        hotelFeature2.put(HotelFeature.SWIMMING_POOL, AttributeValue.builder().bool(false).build());
        hotelFeature2.put(HotelFeature.LIFTS, AttributeValue.builder().n("2").build());

        HashMap<String, AttributeValue> item2 = new HashMap<String, AttributeValue>();
        item2.put(HotelCatalogRecord.ATTR_HOTEL_NAME, AttributeValue.builder().s("Hotel Sandiva").build());
        item2.put(HotelCatalogRecord.ATTR_CITY, AttributeValue.builder().s("Seattle").build());
        item2.put(HotelCatalogRecord.ATTR_RATING, AttributeValue.builder().n("2").build());
        item2.put(HotelCatalogRecord.ATTR_HOTEL_FEATURE, AttributeValue.builder().m(hotelFeature2).build());

        PutRequest putRequest2 = PutRequest.builder().item(item2).build();
        WriteRequest writeRequest2 = WriteRequest.builder()
                .putRequest(putRequest2)
                .build();

        HashMap<String, List<WriteRequest>> itemsToWrite = new HashMap<String, List<WriteRequest>>();
        itemsToWrite.put(Constants.DDB_TABLE_NAME, Arrays.asList(writeRequest1, writeRequest2));

        BatchWriteItemRequest batchWriteItemRequest = BatchWriteItemRequest.builder()
                .requestItems(itemsToWrite)
                .build();
        dynamoDbClient.batchWriteItem(batchWriteItemRequest);
    }

}
