package com.xiaomi.infra.galaxy.fds.android;

import android.util.Log;
import com.google.gson.Gson;
import com.taobao.accs.common.Constants;
import com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential;
import com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.android.model.FDSObject;
import com.xiaomi.infra.galaxy.fds.android.model.InitMultipartUploadResult;
import com.xiaomi.infra.galaxy.fds.android.model.ObjectMetadata;
import com.xiaomi.infra.galaxy.fds.android.model.ProgressListener;
import com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult;
import com.xiaomi.infra.galaxy.fds.android.model.ThumbParam;
import com.xiaomi.infra.galaxy.fds.android.model.UploadPartResultList;
import com.xiaomi.infra.galaxy.fds.android.model.UserParam;
import com.xiaomi.infra.galaxy.fds.android.util.Args;
import com.xiaomi.infra.galaxy.fds.android.util.RequestFactory;
import com.xiaomi.infra.galaxy.fds.android.util.Util;
import com.xiaomi.infra.galaxy.fds.model.HttpMethod;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.text.Typography;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

/* loaded from: classes4.dex */
public class GalaxyFDSClientImpl implements GalaxyFDSClient {
    private static final String HTTPS_SCHEME = "https";
    private static final String HTTP_SCHEME = "http";
    private static final String LOG_TAG = "GalaxyFDSClientImpl";
    private static final boolean TEST_MODE;
    private final FDSClientConfiguration config;
    private final HttpClient httpClient;
    private ThreadPoolExecutor threadPoolExecutor;

    static {
        String property = System.getProperty("java.runtime.name");
        if (property == null || !property.equals("android runtime")) {
            TEST_MODE = true;
        } else {
            TEST_MODE = false;
        }
    }

    public GalaxyFDSClientImpl(FDSClientConfiguration fDSClientConfiguration) {
        this.config = fDSClientConfiguration;
        this.httpClient = createHttpClient(fDSClientConfiguration);
        this.threadPoolExecutor = new ThreadPoolExecutor(fDSClientConfiguration.getThreadPoolCoreSize(), fDSClientConfiguration.getThreadPoolMaxSize(), fDSClientConfiguration.getThreadPoolKeepAliveSecs(), TimeUnit.SECONDS, new ArrayBlockingQueue(fDSClientConfiguration.getWorkQueueCapacity(), true), new ThreadFactory() { // from class: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "FDS-multipart-upload-thread");
            }
        });
    }

    private void abortMultipartUpload(String str, String str2, String str3) throws IOException, GalaxyFDSClientException {
        String str4 = this.config.getUploadBaseUri() + "/" + str + "/" + str2 + "?uploadId=" + str3;
        InputStream content = null;
        try {
            try {
                HttpResponse httpResponseExecute = this.httpClient.execute(RequestFactory.createRequest(str4, this.config.getCredential(), HttpMethod.DELETE, null));
                content = httpResponseExecute.getEntity().getContent();
                if (httpResponseExecute.getStatusLine().getStatusCode() == 200) {
                    if (content != null) {
                        try {
                            content.close();
                            return;
                        } catch (IOException unused) {
                            return;
                        }
                    }
                    return;
                }
                throw new GalaxyFDSClientException("Unable to upload object[" + str + "/" + str2 + "] to URI :" + str4 + ". Fail to abort multipart upload: " + httpResponseExecute.getStatusLine().toString());
            } catch (IOException e2) {
                throw new GalaxyFDSClientException("Fail to abort multipart upload. URI:" + str4, e2);
            }
        } catch (Throwable th) {
            if (content != null) {
                try {
                    content.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    private PutObjectResult completeMultipartUpload(String str, String str2, String str3, ObjectMetadata objectMetadata, UploadPartResultList uploadPartResultList, List<UserParam> list) throws IOException, GalaxyFDSClientException {
        HashMap map;
        StringBuilder sb = new StringBuilder();
        sb.append(this.config.getUploadBaseUri() + "/" + str2 + "/" + str3 + "?uploadId=" + str);
        if (list != null) {
            for (UserParam userParam : list) {
                sb.append(Typography.amp);
                sb.append(userParam.toString());
            }
        }
        String string = sb.toString();
        InputStream inputStream = null;
        try {
            if (objectMetadata != null) {
                try {
                    map = new HashMap();
                    for (Map.Entry<String, String> entry : objectMetadata.getAllMetadata().entrySet()) {
                        map.put(entry.getKey().toLowerCase(), entry.getValue());
                    }
                } catch (IOException e2) {
                    throw new GalaxyFDSClientException("Fail to complete multipart upload. URI:" + string, e2);
                }
            } else {
                map = null;
            }
            HttpUriRequest httpUriRequestCreateRequest = RequestFactory.createRequest(string, this.config.getCredential(), HttpMethod.PUT, map);
            ((HttpPut) httpUriRequestCreateRequest).setEntity(new StringEntity(new Gson().toJson(uploadPartResultList)));
            HttpResponse httpResponseExecute = this.httpClient.execute(httpUriRequestCreateRequest);
            InputStream content = httpResponseExecute.getEntity().getContent();
            if (httpResponseExecute.getStatusLine().getStatusCode() != 200) {
                throw new GalaxyFDSClientException("Unable to upload object[" + str2 + "/" + str3 + "] to URI :" + string + ". Fail to complete multipart upload: " + httpResponseExecute.getStatusLine().toString());
            }
            PutObjectResult putObjectResult = (PutObjectResult) new Gson().fromJson((Reader) new InputStreamReader(content), PutObjectResult.class);
            if (putObjectResult != null && putObjectResult.getAccessKeyId() != null && putObjectResult.getSignature() != null && putObjectResult.getExpires() != 0) {
                putObjectResult.setFdsServiceBaseUri(this.config.getBaseUri());
                putObjectResult.setCdnServiceBaseUri(this.config.getCdnBaseUri());
                if (content != null) {
                    try {
                        content.close();
                    } catch (IOException unused) {
                    }
                }
                return putObjectResult;
            }
            throw new GalaxyFDSClientException("Fail to parse the result of completing multipart upload. bucket name:" + str2 + ", object name:" + str3 + ", upload ID:" + str);
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    inputStream.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    private HttpClient createHttpClient(FDSClientConfiguration fDSClientConfiguration) {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, fDSClientConfiguration.getConnectionTimeoutMs());
        HttpConnectionParams.setSoTimeout(basicHttpParams, fDSClientConfiguration.getSocketTimeoutMs());
        HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, true);
        HttpConnectionParams.setTcpNoDelay(basicHttpParams, true);
        int i2 = fDSClientConfiguration.getSocketBufferSizeHints()[0];
        int i3 = fDSClientConfiguration.getSocketBufferSizeHints()[1];
        if (i2 > 0 || i3 > 0) {
            HttpConnectionParams.setSocketBufferSize(basicHttpParams, Math.max(i2, i3));
        }
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        if (fDSClientConfiguration.isHttpsEnabled()) {
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            schemeRegistry.register(new Scheme("https", socketFactory, Constants.PORT));
        }
        return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
    }

    private InitMultipartUploadResult initMultipartUpload(String str, String str2, long j2) throws IOException, GalaxyFDSClientException {
        StringBuilder sb = new StringBuilder();
        sb.append(this.config.getUploadBaseUri());
        sb.append("/");
        sb.append(str);
        sb.append("/");
        sb.append(str2 == null ? "" : str2);
        sb.append("?uploads");
        String string = sb.toString();
        InputStream inputStream = null;
        try {
            try {
                HashMap map = new HashMap();
                map.put("x-xiaomi-estimated-object-size", Long.toString(j2));
                HttpResponse httpResponseExecute = this.httpClient.execute(RequestFactory.createRequest(string, this.config.getCredential(), str2 == null ? HttpMethod.POST : HttpMethod.PUT, map));
                InputStream content = httpResponseExecute.getEntity().getContent();
                if (httpResponseExecute.getStatusLine().getStatusCode() != 200) {
                    throw new GalaxyFDSClientException("Unable to upload object[" + str + "/" + str2 + "] to URI :" + string + ". Fail to initiate multipart upload: " + httpResponseExecute.getStatusLine().toString());
                }
                InitMultipartUploadResult initMultipartUploadResult = (InitMultipartUploadResult) new Gson().fromJson((Reader) new InputStreamReader(content), InitMultipartUploadResult.class);
                if (initMultipartUploadResult != null && initMultipartUploadResult.getUploadId() != null && initMultipartUploadResult.getObjectName() != null && initMultipartUploadResult.getBucketName() != null) {
                    if (content != null) {
                        try {
                            content.close();
                        } catch (IOException unused) {
                        }
                    }
                    return initMultipartUploadResult;
                }
                throw new GalaxyFDSClientException("Fail to parse the result of init multipart upload. bucket name:" + str + ", object name:" + str2);
            } catch (IOException e2) {
                throw new GalaxyFDSClientException("Fail to initiate multipart upload. URI:" + string, e2);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    inputStream.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    private boolean isGetThumbnail(List<UserParam> list) {
        if (list != null) {
            Iterator<UserParam> it = list.iterator();
            while (it.hasNext()) {
                if (it.next() instanceof ThumbParam) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0117, code lost:
    
        throw new com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException("Fail to parse the result of uploading part. bucket name:" + r19 + ", object name:" + r20 + ", upload ID:" + r18);
     */
    /* JADX WARN: Removed duplicated region for block: B:50:0x017c A[Catch: all -> 0x00e3, TryCatch #3 {all -> 0x00e3, blocks: (B:17:0x0088, B:18:0x009f, B:20:0x00b9, B:22:0x00cd, B:24:0x00d3, B:37:0x00f1, B:38:0x0117, B:48:0x0174, B:50:0x017c, B:52:0x0180, B:56:0x01c6, B:45:0x015b, B:46:0x0171, B:43:0x011c, B:44:0x015a), top: B:69:0x0088 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01c6 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.infra.galaxy.fds.android.model.UploadPartResult uploadPart(java.lang.String r18, java.lang.String r19, java.lang.String r20, int r21, com.xiaomi.infra.galaxy.fds.android.util.ObjectInputStream r22, long r23) throws java.io.IOException, com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException {
        /*
            Method dump skipped, instructions count: 461
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.uploadPart(java.lang.String, java.lang.String, java.lang.String, int, com.xiaomi.infra.galaxy.fds.android.util.ObjectInputStream, long):com.xiaomi.infra.galaxy.fds.android.model.UploadPartResult");
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public boolean doesObjectExist(String str, String str2) throws IOException, GalaxyFDSClientException {
        Args.notNull(str, "bucket name");
        Args.notEmpty(str, "bucket name");
        Args.notNull(str2, "object name");
        Args.notEmpty(str2, "object name");
        String str3 = this.config.getBaseUri() + "/" + str + "/" + str2;
        try {
            HttpResponse httpResponseExecute = this.httpClient.execute(RequestFactory.createRequest(str3, this.config.getCredential(), HttpMethod.HEAD, null));
            int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return true;
            }
            if (statusCode == 404) {
                return false;
            }
            throw new GalaxyFDSClientException("Unable to head object[" + str + "/" + str2 + "] from URI :" + str3 + ". Cause:" + httpResponseExecute.getStatusLine().toString());
        } catch (IOException e2) {
            throw new GalaxyFDSClientException("Unable to head object[" + str + "/" + str2 + "] from URI :" + str3 + " Exception:" + e2.getMessage(), e2);
        }
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public FDSObject getObject(String str, String str2) throws GalaxyFDSClientException {
        return getObject(str, str2, 0L, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, File file) throws GalaxyFDSClientException {
        return putObject(str, str2, file, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public FDSObject getObject(String str, String str2, long j2, List<UserParam> list) throws GalaxyFDSClientException {
        return getObject(str, str2, j2, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, File file, List<UserParam> list) throws GalaxyFDSClientException {
        return putObject(str, str2, file, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public FDSObject getObject(String str, String str2, long j2, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        Args.notNull(str, "bucket name");
        Args.notEmpty(str, "bucket name");
        Args.notNull(str2, "object name");
        Args.notEmpty(str2, "object name");
        StringBuilder sb = new StringBuilder();
        sb.append(this.config.getDownloadBaseUri());
        sb.append("/" + str + "/" + str2);
        return getObject(sb.toString(), j2, list, progressListener);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, File file, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        Args.notNull(file, "file");
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.length());
            objectMetadata.setContentType(Util.getMimeType(file));
            objectMetadata.setLastModified(new Date(file.lastModified()));
            return putObject(str, str2, bufferedInputStream, objectMetadata, list, progressListener);
        } catch (FileNotFoundException e2) {
            throw new GalaxyFDSClientException("Unable to find the file to be uploaded:" + file.getAbsolutePath(), e2);
        }
    }

    @Deprecated
    public GalaxyFDSClientImpl(String str, GalaxyFDSCredential galaxyFDSCredential, FDSClientConfiguration fDSClientConfiguration) {
        this.config = fDSClientConfiguration;
        fDSClientConfiguration.setCredential(galaxyFDSCredential);
        this.httpClient = createHttpClient(fDSClientConfiguration);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    @Deprecated
    public FDSObject getObject(String str, String str2, long j2, List<UserParam> list, ProgressListener progressListener, boolean z2) throws GalaxyFDSClientException {
        return getObject(str, str2, j2, list, progressListener);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, InputStream inputStream, ObjectMetadata objectMetadata) throws GalaxyFDSClientException {
        return putObject(str, str2, inputStream, objectMetadata, (List<UserParam>) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0150 A[ADDED_TO_REGION] */
    /* JADX WARN: Type inference failed for: r13v6, types: [com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r14v10, types: [com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r14v6 */
    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.infra.galaxy.fds.android.model.FDSObject getObject(java.lang.String r11, long r12, java.util.List<com.xiaomi.infra.galaxy.fds.android.model.UserParam> r14, com.xiaomi.infra.galaxy.fds.android.model.ProgressListener r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 375
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.getObject(java.lang.String, long, java.util.List, com.xiaomi.infra.galaxy.fds.android.model.ProgressListener):com.xiaomi.infra.galaxy.fds.android.model.FDSObject");
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, InputStream inputStream, ObjectMetadata objectMetadata, List<UserParam> list) throws GalaxyFDSClientException {
        return putObject(str, str2, inputStream, objectMetadata, list, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ff  */
    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult putObject(final java.lang.String r22, java.lang.String r23, java.io.InputStream r24, final com.xiaomi.infra.galaxy.fds.android.model.ObjectMetadata r25, java.util.List<com.xiaomi.infra.galaxy.fds.android.model.UserParam> r26, com.xiaomi.infra.galaxy.fds.android.model.ProgressListener r27) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.putObject(java.lang.String, java.lang.String, java.io.InputStream, com.xiaomi.infra.galaxy.fds.android.model.ObjectMetadata, java.util.List, com.xiaomi.infra.galaxy.fds.android.model.ProgressListener):com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult");
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, File file) throws GalaxyFDSClientException {
        return putObject(str, file, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, File file, List<UserParam> list) throws GalaxyFDSClientException {
        return putObject(str, file, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public ObjectMetadata getObject(String str, String str2, File file) throws GalaxyFDSClientException {
        return getObject(str, str2, file, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, File file, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        return putObject(str, (String) null, file, list, progressListener);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public ObjectMetadata getObject(String str, String str2, File file, List<UserParam> list) throws GalaxyFDSClientException {
        return getObject(str, str2, file, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, InputStream inputStream, ObjectMetadata objectMetadata) throws GalaxyFDSClientException {
        return putObject(str, inputStream, objectMetadata, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public ObjectMetadata getObject(String str, String str2, File file, List<UserParam> list, ProgressListener progressListener) throws Throwable {
        long length;
        Args.notNull(file, "Destination file");
        boolean zIsGetThumbnail = isGetThumbnail(list);
        int i2 = 0;
        while (true) {
            boolean z2 = (i2 == 0 || zIsGetThumbnail) ? false : true;
            if (z2) {
                try {
                    length = file.length();
                } catch (GalaxyFDSClientException e2) {
                    i2++;
                    if (i2 < this.config.getMaxRetryTimes()) {
                        if (!TEST_MODE) {
                            Log.i(LOG_TAG, "Retry the download of object:" + str2 + " bucket:" + str + " to file: " + file.getAbsolutePath() + " cause:" + Util.getStackTrace(e2));
                        }
                    } else {
                        throw e2;
                    }
                }
            } else {
                length = 0;
            }
            FDSObject object = getObject(str, str2, length, list, progressListener);
            Util.downloadObjectToFile(object, file, z2);
            return object.getObjectMetadata();
        }
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, InputStream inputStream, ObjectMetadata objectMetadata, List<UserParam> list) throws GalaxyFDSClientException {
        return putObject(str, inputStream, objectMetadata, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, InputStream inputStream, ObjectMetadata objectMetadata, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        return putObject(str, null, inputStream, objectMetadata, list, progressListener);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    @Deprecated
    public ObjectMetadata getObject(String str, String str2, File file, List<UserParam> list, ProgressListener progressListener, boolean z2) throws GalaxyFDSClientException {
        return getObject(str, str2, file, list, progressListener);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public ObjectMetadata getObject(String str, File file, List<UserParam> list, ProgressListener progressListener) throws Throwable {
        long length;
        Args.notNull(file, "Destination file");
        boolean zIsGetThumbnail = isGetThumbnail(list);
        int i2 = 0;
        while (true) {
            boolean z2 = (i2 == 0 || zIsGetThumbnail) ? false : true;
            if (z2) {
                try {
                    length = file.length();
                } catch (GalaxyFDSClientException e2) {
                    i2++;
                    if (i2 < this.config.getMaxRetryTimes()) {
                        if (!TEST_MODE) {
                            Log.i(LOG_TAG, "Retry the download of object:" + str + " to file: " + file.getAbsolutePath() + " cause:" + Util.getStackTrace(e2));
                        }
                    } else {
                        throw e2;
                    }
                }
            } else {
                length = 0;
            }
            FDSObject object = getObject(str, length, list, progressListener);
            Util.downloadObjectToFile(object, file, z2);
            return object.getObjectMetadata();
        }
    }
}
