package com.xiaomi.infra.galaxy.fds;

import com.facebook.internal.AnalyticsEvents;

/* loaded from: classes4.dex */
public enum FDSError {
    BucketAccessDenied("Bucket Access Denied", 403),
    BucketAlreadyExists("Bucket Already Exists", Common.HTTP_STATUS_CONFLICT),
    BucketNotFound("Bucket Not Found", Common.HTTP_STATUS_NOT_FOUND),
    ObjectAccessDenied("Object Access Denied", 403),
    ObjectAlreadyExists("Object Already Exists", Common.HTTP_STATUS_CONFLICT),
    ObjectNotFound("Object Not Found", Common.HTTP_STATUS_NOT_FOUND),
    BrokenObject("Object Data Broken", 500),
    InternalServerError("Internal Server Error", 500),
    RequestTimeout("Request Timeout", 400),
    InvalidRequest("Invalid Request", 400),
    SignatureDoesNotMatch("Signature Does Not Match", 403),
    RequestTimeTooSkewed("Request Time Too Skewed", 403),
    RequestExpired("Request Expired", 403),
    InvalidOAuthParameters("Invalid OAuth Parameters", 400),
    VerifyOAuthAccessTokenError("Verify OAuth Access Token Error", 400),
    QuotaExceeded("Quota Exceeded", 400),
    ChecksumDoesNotMatch("Checksum Does Not Match", 400),
    RequestNotSupported("Request Not Supported", 501),
    InvalidRequestRange("Request Out of Range", 416),
    AuthenticationFailed("Authentication Failed", 400),
    ServerTooBusy("Server Too Busy", 500),
    TooManyRequests("Too Many Requests, Try Later", Common.HTTP_STATUS_TOO_MANY_REQUESTS),
    InvalidBucketName("Invalid Bucket Name", 400),
    DomainMappingAccessDenied("Domain Mapping Access Denied", 403),
    Unknown(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN, 400),
    Success("Success", 200);

    private final String description;
    private final int status;

    FDSError(String str, int i2) {
        this.description = str;
        this.status = i2;
    }

    public String description() {
        return this.description;
    }

    public int status() {
        return this.status;
    }
}
