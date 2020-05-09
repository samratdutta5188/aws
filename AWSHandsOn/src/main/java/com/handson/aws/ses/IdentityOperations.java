package com.handson.aws.ses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handson.aws.utils.Constants;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

public class IdentityOperations {

    private SesClient sesClient;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public IdentityOperations(final SesClient sesClient) {
        this.sesClient = sesClient;
    }

    public void verifyEmailIdentity() {
        VerifyEmailIdentityRequest verifyEmailIdentityRequest = VerifyEmailIdentityRequest.builder()
                .emailAddress(Constants.EMAIL)
                .build();
        sesClient.verifyEmailIdentity(verifyEmailIdentityRequest);
    }

    public void verifyEmailAddress() {
        VerifyEmailAddressRequest verifyEmailAddressRequest = VerifyEmailAddressRequest.builder()
                .emailAddress(Constants.EMAIL)
                .build();
        sesClient.verifyEmailAddress(verifyEmailAddressRequest);
    }

    public void verifyDomainIdentity() {
        VerifyDomainIdentityRequest verifyDomainIdentityRequest = VerifyDomainIdentityRequest.builder()
                .domain(Constants.DOMAIN)
                .build();
        sesClient.verifyDomainIdentity(verifyDomainIdentityRequest);
    }

    public void listIdentities() {
        ListIdentitiesResponse listIdentitiesResponse = sesClient.listIdentities();
        System.out.println("Identities: " + gson.toJson(listIdentitiesResponse.identities()));
    }

    public void deleteVerifiedEmailAddress() {
        DeleteVerifiedEmailAddressRequest deleteVerifiedEmailAddressRequest = DeleteVerifiedEmailAddressRequest.builder()
                .emailAddress(Constants.EMAIL)
                .build();
        sesClient.deleteVerifiedEmailAddress(deleteVerifiedEmailAddressRequest);
    }

    public void deleteDomainIdentity() {
        DeleteIdentityRequest deleteIdentityRequest = DeleteIdentityRequest.builder()
                .identity(Constants.DOMAIN)
                .build();
        sesClient.deleteIdentity(deleteIdentityRequest);
    }

}
