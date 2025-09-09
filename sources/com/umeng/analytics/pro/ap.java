package com.umeng.analytics.pro;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;

/* loaded from: classes4.dex */
public class ap {

    /* renamed from: a, reason: collision with root package name */
    private static HostnameVerifier f21333a;

    private static HostnameVerifier a() {
        if (f21333a == null) {
            f21333a = new HostnameVerifier() { // from class: com.umeng.analytics.pro.ap.1
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String str, SSLSession sSLSession) {
                    if (TextUtils.isEmpty(str)) {
                        return false;
                    }
                    return "pre-ucc.umeng.com".equalsIgnoreCase(str) || "ucc.umeng.com".equalsIgnoreCase(str) || "aspect-upush.umeng.com".equalsIgnoreCase(str) || "pre-msg.umengcloud.com".equalsIgnoreCase(str) || "ulogs.umeng.com".equalsIgnoreCase(str) || "preulogs.umeng.com".equalsIgnoreCase(str) || "cnlogs.umeng.com".equalsIgnoreCase(str);
                }
            };
        }
        return f21333a;
    }

    public static void b(String str, byte[] bArr) {
        byte[] bArrA;
        HttpsURLConnection httpsURLConnectionA = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ax.a(bArr, byteArrayOutputStream);
                bArrA = ax.a(byteArrayOutputStream.toByteArray(), UMConfigure.sAppkey.getBytes());
            } catch (Throwable unused) {
                bArrA = null;
            }
            httpsURLConnectionA = a(str, "appkey", bArrA);
            if (httpsURLConnectionA != null) {
                int responseCode = httpsURLConnectionA.getResponseCode();
                if (responseCode != 200) {
                    Log.e(UMRTLog.RTLOG_TAG, "SC event report error, http error code: " + responseCode);
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "SC event report successful.");
                }
            }
            if (httpsURLConnectionA == null) {
                return;
            }
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                if (0 == 0) {
                    return;
                }
            } catch (Throwable th2) {
                if (0 != 0) {
                    try {
                        httpsURLConnectionA.disconnect();
                    } catch (Throwable unused2) {
                    }
                }
                throw th2;
            }
        }
        try {
            httpsURLConnectionA.disconnect();
        } catch (Throwable unused3) {
        }
    }

    public static byte[] a(String str, String str2) {
        return a(str, str2.getBytes());
    }

    public static byte[] a(String str, byte[] bArr) {
        byte[] bArrA = null;
        try {
            HttpsURLConnection httpsURLConnectionA = a(str, "ak", ax.a(bArr, UMConfigure.sAppkey.getBytes()));
            if (httpsURLConnectionA != null && httpsURLConnectionA.getResponseCode() == 200) {
                InputStream inputStream = httpsURLConnectionA.getInputStream();
                try {
                    byte[] streamToByteArray = HelperUtils.readStreamToByteArray(inputStream);
                    try {
                        String headerField = httpsURLConnectionA.getHeaderField("ak");
                        if (!TextUtils.isEmpty(headerField)) {
                            bArrA = ax.a(streamToByteArray, headerField.getBytes());
                        } else {
                            UMRTLog.e(UMRTLog.RTLOG_TAG, "ccg 应答不包含ak!");
                        }
                        HelperUtils.safeClose(inputStream);
                    } catch (Throwable th) {
                        th = th;
                        HelperUtils.safeClose(inputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } catch (Throwable unused) {
        }
        return bArrA;
    }

    private static HttpsURLConnection a(String str, String str2, byte[] bArr) throws NoSuchAlgorithmException, IOException, KeyManagementException {
        HttpsURLConnection httpsURLConnection = null;
        try {
            HttpsURLConnection httpsURLConnection2 = (HttpsURLConnection) new URL(str).openConnection();
            try {
                SSLContext sSLContext = SSLContext.getInstance(SSLSocketFactoryFactory.DEFAULT_PROTOCOL);
                sSLContext.init(null, null, new SecureRandom());
                httpsURLConnection2.setSSLSocketFactory(sSLContext.getSocketFactory());
                httpsURLConnection2.setHostnameVerifier(a());
                httpsURLConnection2.setRequestMethod("POST");
                httpsURLConnection2.setConnectTimeout(15000);
                httpsURLConnection2.setDoOutput(true);
                httpsURLConnection2.setDoInput(true);
                httpsURLConnection2.setReadTimeout(15000);
                httpsURLConnection2.addRequestProperty("Content-Type", OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE);
                httpsURLConnection2.addRequestProperty(str2, UMConfigure.sAppkey);
                httpsURLConnection2.setRequestProperty("User-Agent", DeviceConfig.getCustomAgt());
                httpsURLConnection2.connect();
                OutputStream outputStream = httpsURLConnection2.getOutputStream();
                outputStream.write(bArr);
                outputStream.flush();
                outputStream.close();
                return httpsURLConnection2;
            } catch (Exception e2) {
                e = e2;
                httpsURLConnection = httpsURLConnection2;
                e.printStackTrace();
                return httpsURLConnection;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }
}
