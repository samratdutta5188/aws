package com.handson.aws.s3;

import com.handson.aws.utils.Constants;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ObjectOperations {

    private S3Client s3Client;

    public ObjectOperations(final S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void putObject() {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .key(Constants.S3_FILE_NAME)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromFile(new File(Constants.S3_FILE_PATH)));
    }

    public void listObjects() {
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .build();
        ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);
        for (S3Object object : listObjectsResponse.contents()) {
            System.out.println("Object Name: " + object.key());
            System.out.println("Object Owner: " + object.owner().displayName());
            System.out.println("Object Size: " + object.size() + " bytes");
            System.out.println("Object Last Modified Date: " + object.lastModified());
        }
    }

    public void deleteObject() {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .key(Constants.S3_FILE_NAME)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    public void downloadObject() {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .key(Constants.S3_FILE_NAME)
                .build();
        s3Client.getObject(getObjectRequest, new File(Constants.S3_DOWNLOAD_FILE_PATH).toPath());
    }

    public void copyObject() {
        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(Constants.S3_BUCKET + "/" + Constants.S3_FILE_NAME, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("URL could not be encoded: " + e.getMessage());
        }
        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .bucket(Constants.S3_BUCKET)
                .copySource(encodedUrl)
                .key(Constants.S3_COPY_FILE_NAME)
                .build();
        s3Client.copyObject(copyObjectRequest);
    }

}
