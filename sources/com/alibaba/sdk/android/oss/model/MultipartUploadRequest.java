package com.alibaba.sdk.android.oss.model;

import android.support.v4.media.session.PlaybackStateCompat;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alibaba.sdk.android.oss.model.MultipartUploadRequest;
import java.util.Map;

/* loaded from: classes2.dex */
public class MultipartUploadRequest<T extends MultipartUploadRequest> extends OSSRequest {
    protected String bucketName;
    protected Map<String, String> callbackParam;
    protected Map<String, String> callbackVars;
    protected ObjectMetadata metadata;
    protected String objectKey;
    protected long partSize;
    protected OSSProgressCallback<T> progressCallback;
    protected String uploadFilePath;
    protected String uploadId;

    public MultipartUploadRequest(String str, String str2, String str3) {
        this(str, str2, str3, null);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public Map<String, String> getCallbackParam() {
        return this.callbackParam;
    }

    public Map<String, String> getCallbackVars() {
        return this.callbackVars;
    }

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public String getObjectKey() {
        return this.objectKey;
    }

    public long getPartSize() {
        return this.partSize;
    }

    public OSSProgressCallback<T> getProgressCallback() {
        return this.progressCallback;
    }

    public String getUploadFilePath() {
        return this.uploadFilePath;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public void setCallbackParam(Map<String, String> map) {
        this.callbackParam = map;
    }

    public void setCallbackVars(Map<String, String> map) {
        this.callbackVars = map;
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }

    public void setObjectKey(String str) {
        this.objectKey = str;
    }

    public void setPartSize(long j2) throws IllegalArgumentException {
        if (j2 < OSSConstants.MIN_PART_SIZE_LIMIT) {
            throw new IllegalArgumentException("Part size must be greater than or equal to 100KB!");
        }
        this.partSize = j2;
    }

    public void setProgressCallback(OSSProgressCallback<T> oSSProgressCallback) {
        this.progressCallback = oSSProgressCallback;
    }

    public void setUploadFilePath(String str) {
        this.uploadFilePath = str;
    }

    public void setUploadId(String str) {
        this.uploadId = str;
    }

    public MultipartUploadRequest(String str, String str2, String str3, ObjectMetadata objectMetadata) {
        this.partSize = PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        setBucketName(str);
        setObjectKey(str2);
        setUploadFilePath(str3);
        setMetadata(objectMetadata);
    }
}
