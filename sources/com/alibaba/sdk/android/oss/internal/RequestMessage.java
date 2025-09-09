package com.alibaba.sdk.android.oss.internal;

import anet.channel.util.HttpConstant;
import com.alibaba.sdk.android.oss.common.HttpMethod;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.HttpUtil;
import com.alibaba.sdk.android.oss.common.utils.HttpdnsMini;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class RequestMessage extends HttpMessage {
    private String bucketName;
    private boolean checkCRC64;
    private OSSCredentialProvider credentialProvider;
    private URI endpoint;
    private HttpMethod method;
    private String objectKey;
    private byte[] uploadData;
    private String uploadFilePath;
    private boolean isAuthorizationRequired = true;
    private Map<String, String> parameters = new LinkedHashMap();
    private boolean httpDnsEnable = false;
    private boolean isInCustomCnameExcludeList = false;

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void addHeader(String str, String str2) {
        super.addHeader(str, str2);
    }

    public String buildCanonicalURL() {
        String ipByHostAsync;
        String str;
        OSSUtils.assertTrue(this.endpoint != null, "Endpoint haven't been set!");
        String scheme = this.endpoint.getScheme();
        String host = this.endpoint.getHost();
        if (OSSUtils.isIP(host)) {
            str = scheme + HttpConstant.SCHEME_SPLIT + host + "/" + this.bucketName;
        } else {
            if (!OSSUtils.isCname(host) && this.bucketName != null) {
                host = this.bucketName + "." + host;
            }
            if (isHttpDnsEnable()) {
                ipByHostAsync = HttpdnsMini.getInstance().getIpByHostAsync(host);
            } else {
                OSSLog.logDebug("[buildCannonicalURL], disable httpdns");
                ipByHostAsync = null;
            }
            if (ipByHostAsync == null) {
                ipByHostAsync = host;
            }
            if (OSSUtils.isCname(host) && isInCustomCnameExcludeList() && this.bucketName != null) {
                host = this.bucketName + "." + host;
            }
            addHeader("Host", host);
            str = scheme + HttpConstant.SCHEME_SPLIT + ipByHostAsync;
        }
        if (this.objectKey != null) {
            str = str + "/" + HttpUtil.urlEncode(this.objectKey, "utf-8");
        }
        String strParamToQueryString = OSSUtils.paramToQueryString(this.parameters, "utf-8");
        StringBuilder sb = new StringBuilder();
        sb.append("request---------------------\n");
        sb.append("request url=" + str + "\n");
        sb.append("request params=" + strParamToQueryString + "\n");
        for (String str2 : getHeaders().keySet()) {
            sb.append("requestHeader [" + str2 + "]: ");
            StringBuilder sb2 = new StringBuilder();
            sb2.append((String) getHeaders().get(str2));
            sb2.append("\n");
            sb.append(sb2.toString());
        }
        OSSLog.logDebug(sb.toString());
        if (OSSUtils.isEmptyString(strParamToQueryString)) {
            return str;
        }
        return str + "?" + strParamToQueryString;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void close() throws IOException {
        super.close();
    }

    public void createBucketRequestBodyMarshall(String str) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null) {
            stringBuffer.append("<CreateBucketConfiguration>");
            stringBuffer.append("<LocationConstraint>" + str + "</LocationConstraint>");
            stringBuffer.append("</CreateBucketConfiguration>");
            byte[] bytes = stringBuffer.toString().getBytes("utf-8");
            long length = (long) bytes.length;
            setContent(new ByteArrayInputStream(bytes));
            setContentLength(length);
        }
    }

    public String getBucketName() {
        return this.bucketName;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ InputStream getContent() {
        return super.getContent();
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ long getContentLength() {
        return super.getContentLength();
    }

    public OSSCredentialProvider getCredentialProvider() {
        return this.credentialProvider;
    }

    public URI getEndpoint() {
        return this.endpoint;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ Map getHeaders() {
        return super.getHeaders();
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getObjectKey() {
        return this.objectKey;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ String getStringBody() {
        return super.getStringBody();
    }

    public byte[] getUploadData() {
        return this.uploadData;
    }

    public String getUploadFilePath() {
        return this.uploadFilePath;
    }

    public boolean isAuthorizationRequired() {
        return this.isAuthorizationRequired;
    }

    public boolean isCheckCRC64() {
        return this.checkCRC64;
    }

    public boolean isHttpDnsEnable() {
        return this.httpDnsEnable;
    }

    public boolean isInCustomCnameExcludeList() {
        return this.isInCustomCnameExcludeList;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public void setCheckCRC64(boolean z2) {
        this.checkCRC64 = z2;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void setContent(InputStream inputStream) {
        super.setContent(inputStream);
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void setContentLength(long j2) {
        super.setContentLength(j2);
    }

    public void setCredentialProvider(OSSCredentialProvider oSSCredentialProvider) {
        this.credentialProvider = oSSCredentialProvider;
    }

    public void setEndpoint(URI uri) {
        this.endpoint = uri;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void setHeaders(Map map) {
        super.setHeaders(map);
    }

    public void setHttpDnsEnable(boolean z2) {
        this.httpDnsEnable = z2;
    }

    public void setIsAuthorizationRequired(boolean z2) {
        this.isAuthorizationRequired = z2;
    }

    public void setIsInCustomCnameExcludeList(boolean z2) {
        this.isInCustomCnameExcludeList = z2;
    }

    public void setMethod(HttpMethod httpMethod) {
        this.method = httpMethod;
    }

    public void setObjectKey(String str) {
        this.objectKey = str;
    }

    public void setParameters(Map<String, String> map) {
        this.parameters = map;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void setStringBody(String str) {
        super.setStringBody(str);
    }

    public void setUploadData(byte[] bArr) {
        this.uploadData = bArr;
    }

    public void setUploadFilePath(String str) {
        this.uploadFilePath = str;
    }
}
