package com.aliyun.alink.apiclient;

import com.aliyun.alink.apiclient.constants.Constants;
import com.aliyun.alink.apiclient.utils.ParameterHelper;

/* loaded from: classes2.dex */
public class CommonPoPRequest extends CommonRequest {
    private String format = "JSON";
    private String regionId = Constants.IOT_REGION_ID_DEFAULT;
    private String signatureMethod = "HMAC-SHA1";
    private String signatureNonce = ParameterHelper.getUniqueNonce();
    private String signatureVersion = "1.0";
    private String timestamp = ParameterHelper.getISO8601Time(null);
    private String version = Constants.IOT_VERSION_DEFAULT;

    public String getFormat() {
        return this.format;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public String getSignatureMethod() {
        return this.signatureMethod;
    }

    public String getSignatureNonce() {
        return this.signatureNonce;
    }

    public String getSignatureVersion() {
        return this.signatureVersion;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getVersion() {
        return this.version;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    public void setRegionId(String str) {
        this.regionId = str;
    }

    public void setSignatureMethod(String str) {
        this.signatureMethod = str;
    }

    public void setSignatureNonce(String str) {
        this.signatureNonce = str;
    }

    public void setSignatureVersion(String str) {
        this.signatureVersion = str;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}
