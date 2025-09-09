package com.umeng.message.proguard;

import android.os.SystemClock;
import android.text.TextUtils;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.umeng.message.common.UPLog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class g {
    public static JSONObject a(JSONObject jSONObject, String str, String str2, boolean z2) throws Exception {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        URL url = new URL(str);
        try {
            JSONObject jSONObject2 = new JSONObject(a(jSONObject.toString(), (HttpURLConnection) url.openConnection(), str2));
            if (UPLog.isEnable() && z2) {
                UPLog.d("Net", "req:", url, "\n", jSONObject, "\nresp:\n", jSONObject2, "\nconsume:", Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
            }
            return jSONObject2;
        } catch (Throwable th) {
            if (UPLog.isEnable() && z2) {
                UPLog.d("Net", "req:", url, "\n", jSONObject, "\nresp:\n", null, "\nconsume:", Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void b(JSONObject jSONObject, String str, String str2) throws Exception {
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        String string = jSONObject.toString();
        byte[] bytes = str2.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bc.a(string.getBytes(), byteArrayOutputStream);
        byte[] bArrA = ay.a(byteArrayOutputStream.toByteArray(), bytes);
        SystemClock.elapsedRealtime();
        InputStream inputStream2 = null;
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setRequestMethod("POST");
                httpURLConnection2.setReadTimeout(60000);
                httpURLConnection2.setConnectTimeout(60000);
                httpURLConnection2.addRequestProperty("Content-Type", OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE);
                httpURLConnection2.addRequestProperty("Connection", "close");
                httpURLConnection2.addRequestProperty("appkey", str2);
                httpURLConnection2.setFixedLengthStreamingMode(bArrA.length);
                httpURLConnection2.setDoOutput(true);
                OutputStream outputStream = httpURLConnection2.getOutputStream();
                try {
                    outputStream.write(bArrA);
                    int responseCode = httpURLConnection2.getResponseCode();
                    inputStream2 = responseCode < 400 ? httpURLConnection2.getInputStream() : httpURLConnection2.getErrorStream();
                    byteArrayOutputStream.reset();
                    if (inputStream2 != null) {
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int i2 = inputStream2.read(bArr);
                            if (i2 == -1) {
                                break;
                            } else {
                                byteArrayOutputStream.write(bArr, 0, i2);
                            }
                        }
                    }
                    f.a(outputStream);
                    f.a(inputStream2);
                    try {
                        httpURLConnection2.disconnect();
                    } catch (Throwable unused) {
                    }
                    if (responseCode == 200) {
                        byte[] bArrA2 = ay.a(byteArrayOutputStream.toByteArray(), bytes);
                        byteArrayOutputStream.reset();
                        bc.b(bArrA2, byteArrayOutputStream);
                    }
                    if (responseCode != 200) {
                        throw new Exception("response code:".concat(String.valueOf(responseCode)));
                    }
                } catch (Throwable th) {
                    th = th;
                    httpURLConnection = httpURLConnection2;
                    inputStream = inputStream2;
                    inputStream2 = outputStream;
                    f.a(inputStream2);
                    f.a(inputStream);
                    if (httpURLConnection != null) {
                        try {
                            httpURLConnection.disconnect();
                        } catch (Throwable unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                httpURLConnection = httpURLConnection2;
                inputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            httpURLConnection = null;
        }
    }

    public static JSONObject a(JSONObject jSONObject, String str, String str2) throws Exception {
        try {
            return a(jSONObject, str, str2, true);
        } catch (UnknownHostException unused) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            String host = new URL(str).getHost();
            String strA = au.a("174658", host);
            if (strA == null) {
                return null;
            }
            URL url = new URL(str.replaceFirst(host, strA));
            String string = jSONObject.toString();
            try {
                final HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Host", host);
                if (httpURLConnection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(new HostnameVerifier() { // from class: com.umeng.message.proguard.g.1
                        @Override // javax.net.ssl.HostnameVerifier
                        public final boolean verify(String str3, SSLSession sSLSession) {
                            String requestProperty = httpURLConnection.getRequestProperty("Host");
                            if (requestProperty == null) {
                                requestProperty = httpURLConnection.getURL().getHost();
                            }
                            return HttpsURLConnection.getDefaultHostnameVerifier().verify(requestProperty, sSLSession);
                        }
                    });
                }
                JSONObject jSONObject2 = new JSONObject(a(string, httpURLConnection, str2));
                if (UPLog.isEnable()) {
                    UPLog.d("Net", "req:", url, "\n", jSONObject, "\nresp:\n", jSONObject2, "\nconsume:", Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
                }
                return jSONObject2;
            } catch (Throwable th) {
                if (UPLog.isEnable()) {
                    UPLog.d("Net", "req:", url, "\n", jSONObject, "\nresp:\n", null, "\nconsume:", Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
                }
                throw th;
            }
        }
    }

    private static String a(String str, HttpURLConnection httpURLConnection, String str2) throws Exception {
        OutputStream outputStream;
        InputStream errorStream;
        byte[] bytes = str2.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bc.a(str.getBytes(), byteArrayOutputStream);
        byte[] bArrA = ay.a(byteArrayOutputStream.toByteArray(), bytes);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setReadTimeout(30000);
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.addRequestProperty("Content-Type", OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE);
        httpURLConnection.addRequestProperty("Content-Encoding", "xgzip");
        httpURLConnection.addRequestProperty("Connection", "close");
        httpURLConnection.addRequestProperty("appkey", str2);
        httpURLConnection.setFixedLengthStreamingMode(bArrA.length);
        httpURLConnection.setDoOutput(true);
        try {
            outputStream = httpURLConnection.getOutputStream();
        } catch (Throwable th) {
            th = th;
            outputStream = null;
        }
        try {
            outputStream.write(bArrA);
            f.a(outputStream);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode < 400) {
                errorStream = httpURLConnection.getInputStream();
            } else {
                errorStream = httpURLConnection.getErrorStream();
            }
            byteArrayOutputStream.reset();
            if (errorStream != null) {
                byte[] bArr = new byte[8192];
                while (true) {
                    try {
                        int i2 = errorStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, i2);
                    } finally {
                        f.a(errorStream);
                    }
                }
            }
            try {
                httpURLConnection.disconnect();
            } catch (Throwable unused) {
            }
            if (responseCode == 200 && TextUtils.equals("xgzip", httpURLConnection.getHeaderField("Content-Encoding"))) {
                byte[] bArrA2 = ay.a(byteArrayOutputStream.toByteArray(), bytes);
                byteArrayOutputStream.reset();
                bc.b(bArrA2, byteArrayOutputStream);
            }
            String string = byteArrayOutputStream.toString();
            if (responseCode == 200) {
                return string;
            }
            throw new IOException("code:" + responseCode + "msg:" + string);
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    public static JSONObject a(JSONObject jSONObject, String str, String str2, File file) throws Exception {
        Throwable th;
        InputStream inputStream;
        OutputStream outputStream;
        HttpURLConnection httpURLConnection;
        OutputStream outputStream2;
        byte[] bArr;
        int responseCode;
        InputStream errorStream;
        InputStream inputStream2;
        String string = jSONObject.toString();
        byte[] bytes = str2.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bc.a(string.getBytes(), byteArrayOutputStream);
        byte[] bArrA = ay.a(byteArrayOutputStream.toByteArray(), bytes);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        byte[] bytes2 = "--".getBytes();
        byte[] bytes3 = IOUtils.LINE_SEPARATOR_WINDOWS.getBytes();
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setReadTimeout(60000);
                httpURLConnection.setConnectTimeout(60000);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.addRequestProperty("Content-Type", "multipart/form-data;boundary=".concat("----WebKitFormBoundary7MA4YWxkTrZu0gW"));
                httpURLConnection.addRequestProperty("appkey", str2);
                httpURLConnection.addRequestProperty("Connection", "close");
                httpURLConnection.setDoOutput(true);
                outputStream2 = httpURLConnection.getOutputStream();
                try {
                    outputStream2.write(bytes2);
                    outputStream2.write("----WebKitFormBoundary7MA4YWxkTrZu0gW".getBytes());
                    outputStream2.write(bytes3);
                    outputStream2.write("Content-Disposition: form-data; name=\"msg\"".getBytes());
                    outputStream2.write(bytes3);
                    outputStream2.write(bytes3);
                    outputStream2.write(as.b(bArrA));
                    outputStream2.write(bytes3);
                    outputStream2.write(bytes2);
                    outputStream2.write("----WebKitFormBoundary7MA4YWxkTrZu0gW".getBytes());
                    outputStream2.write(bytes3);
                    outputStream2.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"").getBytes());
                    outputStream2.write(bytes3);
                    outputStream2.write("Content-Type: application/octet-stream".getBytes());
                    outputStream2.write(bytes3);
                    outputStream2.write(bytes3);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    bArr = new byte[8192];
                    while (true) {
                        int i2 = fileInputStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        outputStream2.write(bArr, 0, i2);
                    }
                    fileInputStream.close();
                    outputStream2.write(bytes3);
                    outputStream2.write(bytes2);
                    outputStream2.write("----WebKitFormBoundary7MA4YWxkTrZu0gW".getBytes());
                    outputStream2.write(bytes2);
                    outputStream2.write(bytes3);
                    responseCode = httpURLConnection.getResponseCode();
                    if (responseCode < 400) {
                        errorStream = httpURLConnection.getInputStream();
                    } else {
                        errorStream = httpURLConnection.getErrorStream();
                    }
                    inputStream2 = errorStream;
                } catch (Throwable th2) {
                    th = th2;
                    outputStream = outputStream2;
                    inputStream = null;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
                outputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            outputStream = null;
            httpURLConnection = null;
        }
        try {
            byteArrayOutputStream.reset();
            if (inputStream2 != null) {
                while (true) {
                    int i3 = inputStream2.read(bArr);
                    if (i3 == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i3);
                }
            }
            f.a(outputStream2);
            f.a(inputStream2);
            try {
                httpURLConnection.disconnect();
            } catch (Throwable unused) {
            }
            if (responseCode == 200 && TextUtils.equals("xgzip", httpURLConnection.getHeaderField("Content-Encoding"))) {
                byte[] bArrA2 = ay.a(byteArrayOutputStream.toByteArray(), bytes);
                byteArrayOutputStream.reset();
                bc.b(bArrA2, byteArrayOutputStream);
            }
            String string2 = byteArrayOutputStream.toString();
            if (UPLog.isEnable()) {
                UPLog.d("Net", "req:", str, "\n", string, "\nresp:\n", string2, "\nconsume:", Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
            }
            if (responseCode == 200) {
                return new JSONObject(string2);
            }
            throw new Exception("response code:".concat(String.valueOf(responseCode)));
        } catch (Throwable th5) {
            th = th5;
            inputStream = inputStream2;
            outputStream = outputStream2;
            f.a(outputStream);
            f.a(inputStream);
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                    throw th;
                } catch (Throwable unused2) {
                    throw th;
                }
            }
            throw th;
        }
    }
}
