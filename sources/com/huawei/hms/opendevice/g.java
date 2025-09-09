package com.huawei.hms.opendevice;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.secure.android.common.ssl.SecureSSLSocketFactory;
import com.huawei.secure.android.common.util.IOUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes4.dex */
public abstract class g {

    private enum a {
        GET("GET"),
        POST("POST");


        /* renamed from: a, reason: collision with root package name */
        private String f16590a;

        a(String str) {
            this.f16590a = str;
        }

        public String a() {
            return this.f16590a;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [java.util.Map, java.util.Map<java.lang.String, java.lang.String>] */
    /* JADX WARN: Type inference failed for: r10v11, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v24 */
    /* JADX WARN: Type inference failed for: r10v25 */
    /* JADX WARN: Type inference failed for: r10v26 */
    /* JADX WARN: Type inference failed for: r7v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r7v15, types: [javax.net.ssl.HttpsURLConnection] */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v16, types: [java.io.BufferedOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v18 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v10, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v30 */
    /* JADX WARN: Type inference failed for: r9v31 */
    /* JADX WARN: Type inference failed for: r9v32 */
    public static String a(Context context, String str, String str2, Map<String, String> map) throws Throwable {
        HttpsURLConnection httpsURLConnection;
        InputStream errorStream;
        InputStream inputStream;
        HttpsURLConnection httpsURLConnectionA;
        Throwable th;
        ?? bufferedOutputStream;
        BufferedInputStream bufferedInputStream;
        InputStream inputStream2 = null;
        if (str2 == 0 || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        int responseCode = -1;
        try {
            try {
                httpsURLConnectionA = a((Context) context, (String) str, (Map<String, String>) map, a.POST.a());
                if (httpsURLConnectionA == null) {
                    IOUtil.closeSecure((OutputStream) null);
                    IOUtil.closeSecure((InputStream) null);
                    IOUtil.closeSecure((InputStream) null);
                    p.a(httpsURLConnectionA);
                    HMSLog.i("PushHttpsClient", "close connection");
                    return null;
                }
                try {
                    bufferedOutputStream = new BufferedOutputStream(httpsURLConnectionA.getOutputStream());
                    try {
                        bufferedOutputStream.write(str2.getBytes("UTF-8"));
                        bufferedOutputStream.flush();
                        responseCode = httpsURLConnectionA.getResponseCode();
                        HMSLog.d("PushHttpsClient", "https post response code: " + responseCode);
                        errorStream = responseCode >= 400 ? httpsURLConnectionA.getErrorStream() : httpsURLConnectionA.getInputStream();
                        try {
                            bufferedInputStream = new BufferedInputStream(errorStream);
                        } catch (IOException unused) {
                            bufferedInputStream = null;
                            HMSLog.w("PushHttpsClient", "https execute encounter IOException - http code:" + responseCode);
                            context = httpsURLConnectionA;
                            str = bufferedOutputStream;
                            str2 = errorStream;
                            map = bufferedInputStream;
                            IOUtil.closeSecure((OutputStream) str);
                            IOUtil.closeSecure((InputStream) str2);
                            IOUtil.closeSecure((InputStream) map);
                            p.a((HttpsURLConnection) context);
                            HMSLog.i("PushHttpsClient", "close connection");
                            return null;
                        } catch (RuntimeException unused2) {
                            bufferedInputStream = null;
                            HMSLog.w("PushHttpsClient", "https execute encounter RuntimeException - http code:" + responseCode);
                            context = httpsURLConnectionA;
                            str = bufferedOutputStream;
                            str2 = errorStream;
                            map = bufferedInputStream;
                            IOUtil.closeSecure((OutputStream) str);
                            IOUtil.closeSecure((InputStream) str2);
                            IOUtil.closeSecure((InputStream) map);
                            p.a((HttpsURLConnection) context);
                            HMSLog.i("PushHttpsClient", "close connection");
                            return null;
                        } catch (Exception unused3) {
                            bufferedInputStream = null;
                            HMSLog.w("PushHttpsClient", "https execute encounter unknown exception - http code:" + responseCode);
                            context = httpsURLConnectionA;
                            str = bufferedOutputStream;
                            str2 = errorStream;
                            map = bufferedInputStream;
                            IOUtil.closeSecure((OutputStream) str);
                            IOUtil.closeSecure((InputStream) str2);
                            IOUtil.closeSecure((InputStream) map);
                            p.a((HttpsURLConnection) context);
                            HMSLog.i("PushHttpsClient", "close connection");
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                        try {
                            String strA = p.a((InputStream) bufferedInputStream);
                            IOUtil.closeSecure((OutputStream) bufferedOutputStream);
                            IOUtil.closeSecure(errorStream);
                            IOUtil.closeSecure((InputStream) bufferedInputStream);
                            p.a(httpsURLConnectionA);
                            HMSLog.i("PushHttpsClient", "close connection");
                            return strA;
                        } catch (IOException unused4) {
                            HMSLog.w("PushHttpsClient", "https execute encounter IOException - http code:" + responseCode);
                            context = httpsURLConnectionA;
                            str = bufferedOutputStream;
                            str2 = errorStream;
                            map = bufferedInputStream;
                            IOUtil.closeSecure((OutputStream) str);
                            IOUtil.closeSecure((InputStream) str2);
                            IOUtil.closeSecure((InputStream) map);
                            p.a((HttpsURLConnection) context);
                            HMSLog.i("PushHttpsClient", "close connection");
                            return null;
                        } catch (RuntimeException unused5) {
                            HMSLog.w("PushHttpsClient", "https execute encounter RuntimeException - http code:" + responseCode);
                            context = httpsURLConnectionA;
                            str = bufferedOutputStream;
                            str2 = errorStream;
                            map = bufferedInputStream;
                            IOUtil.closeSecure((OutputStream) str);
                            IOUtil.closeSecure((InputStream) str2);
                            IOUtil.closeSecure((InputStream) map);
                            p.a((HttpsURLConnection) context);
                            HMSLog.i("PushHttpsClient", "close connection");
                            return null;
                        } catch (Exception unused6) {
                            HMSLog.w("PushHttpsClient", "https execute encounter unknown exception - http code:" + responseCode);
                            context = httpsURLConnectionA;
                            str = bufferedOutputStream;
                            str2 = errorStream;
                            map = bufferedInputStream;
                            IOUtil.closeSecure((OutputStream) str);
                            IOUtil.closeSecure((InputStream) str2);
                            IOUtil.closeSecure((InputStream) map);
                            p.a((HttpsURLConnection) context);
                            HMSLog.i("PushHttpsClient", "close connection");
                            return null;
                        } catch (Throwable th3) {
                            inputStream2 = bufferedInputStream;
                            th = th3;
                            IOUtil.closeSecure((OutputStream) bufferedOutputStream);
                            IOUtil.closeSecure(errorStream);
                            IOUtil.closeSecure(inputStream2);
                            p.a(httpsURLConnectionA);
                            HMSLog.i("PushHttpsClient", "close connection");
                            throw th;
                        }
                    } catch (IOException unused7) {
                        errorStream = null;
                    } catch (RuntimeException unused8) {
                        errorStream = null;
                    } catch (Exception unused9) {
                        errorStream = null;
                    } catch (Throwable th4) {
                        th = th4;
                        errorStream = null;
                    }
                } catch (IOException unused10) {
                    bufferedOutputStream = 0;
                    errorStream = null;
                    bufferedInputStream = null;
                    HMSLog.w("PushHttpsClient", "https execute encounter IOException - http code:" + responseCode);
                    context = httpsURLConnectionA;
                    str = bufferedOutputStream;
                    str2 = errorStream;
                    map = bufferedInputStream;
                    IOUtil.closeSecure((OutputStream) str);
                    IOUtil.closeSecure((InputStream) str2);
                    IOUtil.closeSecure((InputStream) map);
                    p.a((HttpsURLConnection) context);
                    HMSLog.i("PushHttpsClient", "close connection");
                    return null;
                } catch (RuntimeException unused11) {
                    bufferedOutputStream = 0;
                    errorStream = null;
                    bufferedInputStream = null;
                    HMSLog.w("PushHttpsClient", "https execute encounter RuntimeException - http code:" + responseCode);
                    context = httpsURLConnectionA;
                    str = bufferedOutputStream;
                    str2 = errorStream;
                    map = bufferedInputStream;
                    IOUtil.closeSecure((OutputStream) str);
                    IOUtil.closeSecure((InputStream) str2);
                    IOUtil.closeSecure((InputStream) map);
                    p.a((HttpsURLConnection) context);
                    HMSLog.i("PushHttpsClient", "close connection");
                    return null;
                } catch (Exception unused12) {
                    bufferedOutputStream = 0;
                    errorStream = null;
                    bufferedInputStream = null;
                    HMSLog.w("PushHttpsClient", "https execute encounter unknown exception - http code:" + responseCode);
                    context = httpsURLConnectionA;
                    str = bufferedOutputStream;
                    str2 = errorStream;
                    map = bufferedInputStream;
                    IOUtil.closeSecure((OutputStream) str);
                    IOUtil.closeSecure((InputStream) str2);
                    IOUtil.closeSecure((InputStream) map);
                    p.a((HttpsURLConnection) context);
                    HMSLog.i("PushHttpsClient", "close connection");
                    return null;
                } catch (Throwable th5) {
                    errorStream = null;
                    inputStream = null;
                    httpsURLConnection = httpsURLConnectionA;
                    th = th5;
                    InputStream inputStream3 = inputStream;
                    th = th;
                    httpsURLConnectionA = httpsURLConnection;
                    bufferedOutputStream = inputStream2;
                    inputStream2 = inputStream3;
                    IOUtil.closeSecure((OutputStream) bufferedOutputStream);
                    IOUtil.closeSecure(errorStream);
                    IOUtil.closeSecure(inputStream2);
                    p.a(httpsURLConnectionA);
                    HMSLog.i("PushHttpsClient", "close connection");
                    throw th;
                }
            } catch (IOException unused13) {
                httpsURLConnectionA = null;
            } catch (RuntimeException unused14) {
                httpsURLConnectionA = null;
            } catch (Exception unused15) {
                httpsURLConnectionA = null;
            } catch (Throwable th6) {
                th = th6;
                httpsURLConnection = null;
                errorStream = null;
                inputStream = null;
            }
        } catch (Throwable th7) {
            InputStream inputStream4 = str;
            httpsURLConnection = context;
            th = th7;
            inputStream2 = inputStream4;
            errorStream = str2;
            inputStream = map;
        }
    }

    private static HttpsURLConnection a(Context context, String str, Map<String, String> map, String str2) throws Exception {
        URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
        if (uRLConnectionOpenConnection == null) {
            HMSLog.e("PushHttpsClient", "urlConnection is null");
            return null;
        }
        if (uRLConnectionOpenConnection instanceof HttpsURLConnection) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRLConnectionOpenConnection;
            try {
                SecureSSLSocketFactory secureSSLSocketFactory = SecureSSLSocketFactory.getInstance(context);
                if (secureSSLSocketFactory != null) {
                    httpsURLConnection.setSSLSocketFactory(secureSSLSocketFactory);
                    httpsURLConnection.setHostnameVerifier(SecureSSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                    httpsURLConnection.setRequestMethod(str2);
                    httpsURLConnection.setConnectTimeout(15000);
                    httpsURLConnection.setReadTimeout(15000);
                    httpsURLConnection.setDoOutput(true);
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
                    httpsURLConnection.setRequestProperty("Connection", "close");
                    if (map != null && map.size() >= 1) {
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            String key = entry.getKey();
                            if (!TextUtils.isEmpty(key)) {
                                httpsURLConnection.setRequestProperty(key, URLEncoder.encode(entry.getValue() == null ? "" : entry.getValue(), "UTF-8"));
                            }
                        }
                    }
                    return httpsURLConnection;
                }
                HMSLog.e("PushHttpsClient", "No ssl socket factory set.");
                return null;
            } catch (IOException | IllegalAccessException | IllegalArgumentException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException e2) {
                HMSLog.e("PushHttpsClient", "Failed to new TLSSocketFactory instance." + e2.getMessage());
                throw new IOException("Failed to create SSLSocketFactory.");
            }
        }
        HMSLog.e("PushHttpsClient", "current request is http not allow connection");
        return null;
    }
}
