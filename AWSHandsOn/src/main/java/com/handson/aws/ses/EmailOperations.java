package com.handson.aws.ses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class EmailOperations {

    private SesClient sesClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public EmailOperations(final SesClient sesClient) {
        this.sesClient = sesClient;
    }

    public void sendRawEmail() throws MessagingException, IOException {
        Session session = Session.getDefaultInstance(new Properties());

        // Create a new MimeMessage object
        MimeMessage message = new MimeMessage(session);

        // Add subject, from and to lines
        message.setSubject(Constants.SES_EMAIL_SUBJECT, Constants.SES_EMAIL_SUBJECT_CHARSET);
        message.setFrom(new InternetAddress(Constants.EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Constants.SES_EMAIL_RECIPIENT));

        // Create a multipart/alternative child container
        MimeMultipart msgBody = new MimeMultipart(Constants.SES_EMAIL_MIMEMULTIPART_SUBTYPE);

        // Create a wrapper for the HTML and text parts
        MimeBodyPart wrap = new MimeBodyPart();

        // Define the text part
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(Constants.SES_EMAIL_BODY_TEXT, Constants.SES_EMAIL_BODY_TYPE_TEXT);

        // Define the HTML part
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(Constants.SES_EMAIL_BODY_HTML, Constants.SES_EMAIL_BODY_TYPE_HTML);

        // Add the text and HTML parts to the child container
        msgBody.addBodyPart(textPart);
        msgBody.addBodyPart(htmlPart);

        // Add the child container to the wrapper object
        wrap.setContent(msgBody);

        // Create a multipart/mixed parent container
        MimeMultipart msg = new MimeMultipart(Constants.SES_EMAIL_MIMEMULTIPART_PARENT_SUBTYPE);

        // Add the parent container to the message
        message.setContent(msg);

        // Add the multipart/alternative part to the message
        msg.addBodyPart(wrap);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);

        ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

        byte[] arr = new byte[buf.remaining()];
        buf.get(arr);

        SdkBytes data = SdkBytes.fromByteArray(arr);

        RawMessage rawMessage = RawMessage.builder()
                .data(data)
                .build();

        SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                .rawMessage(rawMessage)
                .build();

        sesClient.sendRawEmail(rawEmailRequest);
    }

    public void sendEmailWithAttachment() throws MessagingException, IOException {
        Session session = Session.getDefaultInstance(new Properties());

        // Create a new MimeMessage object
        MimeMessage message = new MimeMessage(session);

        // Add subject, from and to lines
        message.setSubject(Constants.SES_EMAIL_SUBJECT, Constants.SES_EMAIL_SUBJECT_CHARSET);
        message.setFrom(new InternetAddress(Constants.EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Constants.SES_EMAIL_RECIPIENT));

        // Create a multipart/alternative child container
        MimeMultipart msgBody = new MimeMultipart(Constants.SES_EMAIL_MIMEMULTIPART_SUBTYPE);

        // Create a wrapper for the HTML and text parts
        MimeBodyPart wrap = new MimeBodyPart();

        // Define the text part
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(Constants.SES_EMAIL_BODY_TEXT, Constants.SES_EMAIL_BODY_TYPE_TEXT);

        // Define the HTML part
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(Constants.SES_EMAIL_BODY_HTML, Constants.SES_EMAIL_BODY_TYPE_HTML);

        // Add the text and HTML parts to the child container
        msgBody.addBodyPart(textPart);
        msgBody.addBodyPart(htmlPart);

        // Add the child container to the wrapper object
        wrap.setContent(msgBody);

        // Create a multipart/mixed parent container
        MimeMultipart msg = new MimeMultipart(Constants.SES_EMAIL_MIMEMULTIPART_PARENT_SUBTYPE);

        // Add the parent container to the message
        message.setContent(msg);

        // Add the multipart/alternative part to the message
        msg.addBodyPart(wrap);

        // Define the attachment
        MimeBodyPart att = new MimeBodyPart();
        File theFile = new java.io.File(Constants.S3_FILE_PATH);
        byte[] fileContent = Files.readAllBytes(theFile.toPath());
        DataSource fds = new ByteArrayDataSource(fileContent, Constants.SES_EMAIL_ATTACHMENT_TYPE);
        att.setDataHandler(new DataHandler(fds));

        // Set the attachment name
        att.setFileName(Constants.SES_EMAIL_ATTACHMENT_NAME);

        // Add the attachment to the message
        msg.addBodyPart(att);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);

        ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

        byte[] arr = new byte[buf.remaining()];
        buf.get(arr);

        SdkBytes data = SdkBytes.fromByteArray(arr);

        RawMessage rawMessage = RawMessage.builder()
                .data(data)
                .build();

        SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                .rawMessage(rawMessage)
                .build();

        sesClient.sendRawEmail(rawEmailRequest);
    }

}
