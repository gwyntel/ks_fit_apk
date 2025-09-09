package com.xiaomi.infra.galaxy.fds.model;

import com.google.common.collect.LinkedListMultimap;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.infra.galaxy.fds.auth.signature.Utils;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class FDSObjectMetadata {
    public static final String USER_DEFINED_META_PREFIX = "x-xiaomi-meta-";
    private final Map<String, String> metadata = new HashMap();

    public enum PredefinedMetadata {
        CacheControl(Common.CACHE_CONTROL),
        ContentEncoding(Common.CONTENT_ENCODING),
        ContentLength(Common.CONTENT_LENGTH),
        ContentRange(Common.CONTENT_RANGE),
        LastModified(Common.LAST_MODIFIED),
        ContentMD5("content-md5"),
        ContentType("content-type"),
        LastChecked(Common.LAST_CHECKED),
        UploadTime(Common.UPLOAD_TIME);

        private final String header;

        PredefinedMetadata(String str) {
            this.header = str;
        }

        public String getHeader() {
            return this.header;
        }
    }

    private static void checkMetadata(String str) {
        boolean zStartsWith = str.startsWith("x-xiaomi-meta-");
        if (!zStartsWith) {
            PredefinedMetadata[] predefinedMetadataArrValues = PredefinedMetadata.values();
            int length = predefinedMetadataArrValues.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (str.equals(predefinedMetadataArrValues[i2].getHeader())) {
                    zStartsWith = true;
                    break;
                }
                i2++;
            }
        }
        if (zStartsWith) {
            return;
        }
        throw new RuntimeException("Invalid metadata: " + str, null);
    }

    public static FDSObjectMetadata parseObjectMetadata(LinkedListMultimap<String, String> linkedListMultimap) {
        FDSObjectMetadata fDSObjectMetadata = new FDSObjectMetadata();
        HashMap map = new HashMap();
        for (Map.Entry<String, String> entry : linkedListMultimap.entries()) {
            String lowerCase = entry.getKey().toLowerCase();
            String value = entry.getValue();
            if (!map.containsKey(lowerCase)) {
                map.put(lowerCase, value);
                if (lowerCase.startsWith("x-xiaomi-meta-")) {
                    fDSObjectMetadata.addHeader(lowerCase, value);
                }
            }
        }
        for (PredefinedMetadata predefinedMetadata : PredefinedMetadata.values()) {
            String str = (String) map.get(predefinedMetadata.getHeader());
            if (str != null && !str.isEmpty()) {
                fDSObjectMetadata.addHeader(predefinedMetadata.getHeader(), str);
            }
        }
        return fDSObjectMetadata;
    }

    public void addHeader(String str, String str2) {
        checkMetadata(str);
        this.metadata.put(str, str2);
    }

    public void addUserMetadata(String str, String str2) {
        checkMetadata(str);
        this.metadata.put(str, str2);
    }

    public String getCacheControl() {
        return this.metadata.get(Common.CACHE_CONTROL);
    }

    public String getContentEncoding() {
        return this.metadata.get(Common.CONTENT_ENCODING);
    }

    public long getContentLength() {
        String str = this.metadata.get(Common.CONTENT_LENGTH);
        if (str != null) {
            return Long.parseLong(str);
        }
        return -1L;
    }

    public String getContentMD5() {
        return this.metadata.get("content-md5");
    }

    public String getContentRange() {
        return this.metadata.get(Common.CONTENT_RANGE);
    }

    public String getContentType() {
        return this.metadata.get("content-type");
    }

    public String getLastChecked() {
        return this.metadata.get(Common.LAST_CHECKED);
    }

    public Date getLastModified() {
        String str = this.metadata.get(Common.LAST_MODIFIED);
        if (str != null) {
            return Utils.parseDateTimeFromString(str);
        }
        return null;
    }

    public Map<String, String> getRawMetadata() {
        return Collections.unmodifiableMap(this.metadata);
    }

    public void setCacheControl(String str) {
        this.metadata.put(Common.CACHE_CONTROL, str);
    }

    public void setContentEncoding(String str) {
        this.metadata.put(Common.CONTENT_ENCODING, str);
    }

    public void setContentLength(long j2) {
        this.metadata.put(Common.CONTENT_LENGTH, Long.toString(j2));
    }

    public void setContentMD5(String str) {
        this.metadata.put("content-md5", str);
    }

    public void setContentRange(long j2, long j3, long j4) {
        this.metadata.put(Common.CONTENT_RANGE, "bytes " + j2 + Constants.ACCEPT_TIME_SEPARATOR_SERVER + j3 + "/" + j4);
    }

    public void setContentType(String str) {
        this.metadata.put("content-type", str);
    }

    public void setLastChecked(String str) {
        this.metadata.put(Common.LAST_CHECKED, str);
    }

    public void setLastModified(Date date) {
        this.metadata.put(Common.LAST_MODIFIED, Utils.getGMTDatetime(date));
    }

    public void setUserMetadata(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            checkMetadata(entry.getKey());
            this.metadata.put(entry.getKey(), entry.getValue());
        }
    }
}
