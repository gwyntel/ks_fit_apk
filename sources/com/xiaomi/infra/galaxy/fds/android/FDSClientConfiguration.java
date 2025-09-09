package com.xiaomi.infra.galaxy.fds.android;

import com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential;
import com.xiaomi.infra.galaxy.fds.android.util.Args;

/* loaded from: classes4.dex */
public class FDSClientConfiguration {
    public static final int DEFAULT_CONNECTION_TIMEOUT_MS = 50000;
    public static final int DEFAULT_MAX_RETRY_TIMES = 3;
    public static final int DEFAULT_SOCKET_TIMEOUT_MS = 50000;
    public static final int DEFAULT_THREAD_POOL_CORE_SIZE = 4;
    public static final int DEFAULT_THREAD_POOL_KEEP_ALIVE_SECS = 30;
    public static final int DEFAULT_THREAD_POOL_MAX_SIZE = 10;
    public static final int DEFAULT_UPLOAD_PART_SIZE = 4096;
    public static final int DEFAULT_WORK_QUEUE_CAPACITY = 10240;
    private static final String URI_CDN = "cdn";
    private static final String URI_CDN_SUFFIX = "fds.api.mi-img.com";
    private static final String URI_HTTPS_PREFIX = "https://";
    private static final String URI_HTTP_PREFIX = "http://";
    private static final String URI_SUFFIX = "fds.api.xiaomi.com";
    private GalaxyFDSCredential credential;
    private String endpoint;
    private int socketTimeoutMs = 50000;
    private int connectionTimeoutMs = 50000;
    private int socketSendBufferSizeHint = 0;
    private int socketReceiveBufferSizeHint = 0;
    private int maxRetryTimes = 3;
    private int uploadPartSize = 4096;
    private int threadPoolCoreSize = 4;
    private int threadPoolMaxSize = 10;
    private int threadPoolKeepAliveSecs = 30;
    private int workQueueCapacity = DEFAULT_WORK_QUEUE_CAPACITY;
    private String regionName = "cnbj0";
    private boolean enableHttps = true;
    private boolean enableCdnForUpload = false;
    private boolean enableCdnForDownload = true;
    private boolean enableUnitTestMode = false;
    private String baseUriForUnitTest = "";

    String buildBaseUri(boolean z2) {
        if (this.enableUnitTestMode) {
            return this.baseUriForUnitTest;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.enableHttps ? URI_HTTPS_PREFIX : URI_HTTP_PREFIX);
        String str = this.endpoint;
        if (str != null && !str.isEmpty()) {
            sb.append(this.endpoint);
        } else if (z2) {
            sb.append("cdn." + this.regionName + "." + URI_CDN_SUFFIX);
        } else {
            sb.append(this.regionName + "." + URI_SUFFIX);
        }
        return sb.toString();
    }

    public void enableCdnForDownload(boolean z2) {
        this.enableCdnForDownload = z2;
    }

    public void enableCdnForUpload(boolean z2) {
        this.enableCdnForUpload = z2;
    }

    public void enableHttps(boolean z2) {
        this.enableHttps = z2;
    }

    void enableUnitTestMode(boolean z2) {
        this.enableUnitTestMode = z2;
    }

    String getBaseUri() {
        return buildBaseUri(false);
    }

    String getBaseUriForUnitTest() {
        return this.baseUriForUnitTest;
    }

    String getCdnBaseUri() {
        return buildBaseUri(true);
    }

    @Deprecated
    public String getCdnServiceBaseUri() {
        return getCdnBaseUri();
    }

    public int getConnectionTimeoutMs() {
        return this.connectionTimeoutMs;
    }

    public GalaxyFDSCredential getCredential() {
        return this.credential;
    }

    String getDownloadBaseUri() {
        return buildBaseUri(this.enableCdnForDownload);
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    @Deprecated
    public String getFdsServiceBaseUri() {
        return getBaseUri();
    }

    public int getMaxRetryTimes() {
        return this.maxRetryTimes;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public int[] getSocketBufferSizeHints() {
        return new int[]{this.socketSendBufferSizeHint, this.socketReceiveBufferSizeHint};
    }

    public int getSocketTimeoutMs() {
        return this.socketTimeoutMs;
    }

    public int getThreadPoolCoreSize() {
        return this.threadPoolCoreSize;
    }

    public int getThreadPoolKeepAliveSecs() {
        return this.threadPoolKeepAliveSecs;
    }

    public int getThreadPoolMaxSize() {
        return this.threadPoolMaxSize;
    }

    String getUploadBaseUri() {
        return buildBaseUri(this.enableCdnForUpload);
    }

    public int getUploadPartSize() {
        return this.uploadPartSize;
    }

    public int getWorkQueueCapacity() {
        return this.workQueueCapacity;
    }

    public boolean isCdnEnabledForDownload() {
        return this.enableCdnForDownload;
    }

    public boolean isCdnEnabledForUpload() {
        return this.enableCdnForUpload;
    }

    boolean isEnabledUnitTestMode() {
        return this.enableUnitTestMode;
    }

    public boolean isHttpsEnabled() {
        return this.enableHttps;
    }

    void setBaseUriForUnitTest(String str) {
        this.baseUriForUnitTest = str;
    }

    @Deprecated
    public void setCdnServiceBaseUri(String str) {
    }

    public void setConnectionTimeoutMs(int i2) {
        this.connectionTimeoutMs = i2;
    }

    public void setCredential(GalaxyFDSCredential galaxyFDSCredential) {
        Args.notNull(galaxyFDSCredential, "credential");
        this.credential = galaxyFDSCredential;
    }

    public void setEndpoint(String str) {
        this.endpoint = str;
    }

    @Deprecated
    public void setFdsServiceBaseUri(String str) {
    }

    public void setMaxRetryTimes(int i2) {
        Args.notNegative(i2, "max retry times");
        this.maxRetryTimes = i2;
    }

    public void setRegionName(String str) {
        this.regionName = str;
    }

    public void setSocketBufferSizeHints(int i2, int i3) {
        this.socketSendBufferSizeHint = i2;
        this.socketReceiveBufferSizeHint = i3;
    }

    public void setSocketTimeoutMs(int i2) {
        this.socketTimeoutMs = i2;
    }

    public void setThreadPoolCoreSize(int i2) {
        this.threadPoolCoreSize = i2;
    }

    public void setThreadPoolKeepAliveSecs(int i2) {
        this.threadPoolKeepAliveSecs = i2;
    }

    public void setThreadPoolMaxSize(int i2) {
        this.threadPoolMaxSize = i2;
    }

    public void setUploadPartSize(int i2) {
        Args.positive(i2, "upload part size");
        this.uploadPartSize = i2;
    }

    public void setWorkQueueCapacity(int i2) {
        this.workQueueCapacity = i2;
    }

    FDSClientConfiguration withBaseUriForUnitTest(String str) {
        setBaseUriForUnitTest(str);
        return this;
    }

    public FDSClientConfiguration withCdnForDownload(boolean z2) {
        enableCdnForDownload(z2);
        return this;
    }

    public FDSClientConfiguration withCdnForUpload(boolean z2) {
        enableCdnForUpload(z2);
        return this;
    }

    @Deprecated
    public FDSClientConfiguration withCdnServiceBaseUri(String str) {
        return this;
    }

    public FDSClientConfiguration withConnectionTimeoutMs(int i2) {
        setConnectionTimeoutMs(i2);
        return this;
    }

    public FDSClientConfiguration withCredential(GalaxyFDSCredential galaxyFDSCredential) {
        setCredential(galaxyFDSCredential);
        return this;
    }

    @Deprecated
    public FDSClientConfiguration withFdsServiceBaseUri(String str) {
        return this;
    }

    public FDSClientConfiguration withHttps(boolean z2) {
        enableHttps(z2);
        return this;
    }

    public FDSClientConfiguration withMaxRetryTimes(int i2) {
        setMaxRetryTimes(i2);
        return this;
    }

    public FDSClientConfiguration withRegionName(String str) {
        setRegionName(str);
        return this;
    }

    public FDSClientConfiguration withSocketBufferSizeHints(int i2, int i3) {
        setSocketBufferSizeHints(i2, i3);
        return this;
    }

    public FDSClientConfiguration withSocketTimeoutMs(int i2) {
        setSocketTimeoutMs(i2);
        return this;
    }

    public FDSClientConfiguration withThreadPoolCoreSize(int i2) {
        setThreadPoolCoreSize(i2);
        return this;
    }

    public FDSClientConfiguration withThreadPoolKeepAliveSecs(int i2) {
        setThreadPoolKeepAliveSecs(i2);
        return this;
    }

    public FDSClientConfiguration withThreadPoolMaxSize(int i2) {
        setThreadPoolMaxSize(i2);
        return this;
    }

    FDSClientConfiguration withUnitTestMode(boolean z2) {
        enableUnitTestMode(z2);
        return this;
    }

    public FDSClientConfiguration withUploadPartSize(int i2) {
        setUploadPartSize(i2);
        return this;
    }

    public FDSClientConfiguration withWorkQueueCapacity(int i2) {
        setWorkQueueCapacity(i2);
        return this;
    }
}
