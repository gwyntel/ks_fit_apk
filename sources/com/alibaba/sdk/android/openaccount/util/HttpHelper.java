package com.alibaba.sdk.android.openaccount.util;

import android.text.TextUtils;
import androidx.browser.trusted.sharing.ShareTarget;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alipay.sdk.m.n.a;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/* loaded from: classes2.dex */
public class HttpHelper {
    private static final String TAG = "HttpHelper";

    public static void closeQuietly(Closeable closeable) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception unused) {
        }
    }

    private static String decodeResponse(InputStream inputStream, String str) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int i2 = inputStream.read(bArr);
                if (i2 == -1) {
                    return new String(byteArrayOutputStream.toByteArray(), str);
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public static String encodeRequest(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        boolean z2 = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (z2) {
                try {
                    sb.append("&");
                } catch (UnsupportedEncodingException e2) {
                    AliSDKLogger.e(TAG, e2.getMessage(), e2);
                    throw new RuntimeException(e2);
                }
            } else {
                z2 = true;
            }
            sb.append(entry.getKey());
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));
        }
        return sb.toString();
    }

    private static void filterResponseCode(int i2) {
        if (i2 == 200) {
            return;
        }
        throw new RuntimeException("http request exception, response code: " + i2);
    }

    public static String get(String str, Map<String, String> map) {
        return toString(get(str + "?" + encodeRequest(map)), "utf-8");
    }

    public static String getCharset(String str) {
        if (TextUtils.isEmpty(str)) {
            return "utf-8";
        }
        for (String str2 : str.split("[;]]")) {
            if (!TextUtils.isEmpty(str2) && str2.startsWith("charset")) {
                String[] strArrSplit = str2.split(ContainerUtils.KEY_VALUE_DELIMITER);
                if (strArrSplit.length == 2) {
                    return strArrSplit[1];
                }
            }
        }
        return "utf-8";
    }

    public static int getConnectionTimeout(int i2) {
        String property = OpenAccountSDK.getProperty("httpConnectionTimeout");
        if (property == null) {
            return i2;
        }
        try {
            return Integer.parseInt(property);
        } catch (Exception unused) {
            return i2;
        }
    }

    public static int getReadTimeout(int i2) {
        String property = OpenAccountSDK.getProperty("httpReadTimeout");
        if (property == null) {
            return i2;
        }
        try {
            return Integer.parseInt(property);
        } catch (Exception unused) {
            return i2;
        }
    }

    private static void log(Map<String, String> map, String str) {
        if (AliSDKLogger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("request ");
            sb.append(str);
            sb.append('\n');
            if (map == null || map.size() == 0) {
                sb.append("with no param");
            } else {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    sb.append(entry.getKey());
                    sb.append(a.f9565h);
                    sb.append(entry.getValue());
                    sb.append('\n');
                }
            }
            AliSDKLogger.d(TAG, sb.toString());
        }
    }

    private static HttpURLConnection openConnection(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setConnectTimeout(getConnectionTimeout(5000));
            httpURLConnection.setReadTimeout(getReadTimeout(5000));
            return httpURLConnection;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String post(Map<String, String> map, String str) throws IOException {
        HttpURLConnection httpURLConnectionOpenConnection;
        byte[] bytes;
        log(map, str);
        OutputStream outputStream = null;
        try {
            bytes = encodeRequest(map).getBytes("utf-8");
            httpURLConnectionOpenConnection = openConnection(str);
        } catch (Throwable th) {
            th = th;
            httpURLConnectionOpenConnection = null;
        }
        try {
            httpURLConnectionOpenConnection.setDoInput(true);
            httpURLConnectionOpenConnection.setDoOutput(true);
            httpURLConnectionOpenConnection.setRequestMethod("POST");
            httpURLConnectionOpenConnection.setUseCaches(false);
            httpURLConnectionOpenConnection.setRequestProperty("Content-Type", ShareTarget.ENCODING_TYPE_URL_ENCODED);
            outputStream = httpURLConnectionOpenConnection.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            filterResponseCode(httpURLConnectionOpenConnection.getResponseCode());
            String strDecodeResponse = decodeResponse(httpURLConnectionOpenConnection.getInputStream(), getCharset(httpURLConnectionOpenConnection.getContentType()));
            closeQuietly(outputStream);
            try {
                httpURLConnectionOpenConnection.disconnect();
            } catch (Exception unused) {
            }
            return strDecodeResponse;
        } catch (Throwable th2) {
            th = th2;
            try {
                throw new RuntimeException(th);
            } catch (Throwable th3) {
                closeQuietly(outputStream);
                if (httpURLConnectionOpenConnection != null) {
                    try {
                        httpURLConnectionOpenConnection.disconnect();
                    } catch (Exception unused2) {
                    }
                }
                throw th3;
            }
        }
    }

    public static String toString(InputStream inputStream, String str) throws IOException {
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = inputStream.read(bArr, 0, 1024);
                    if (i2 == -1) {
                        String str2 = new String(byteArrayOutputStream.toByteArray(), "utf-8");
                        closeQuietly(inputStream);
                        return str2;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Throwable th) {
            closeQuietly(inputStream);
            throw th;
        }
    }

    public static InputStream get(String str) {
        try {
            HttpURLConnection httpURLConnectionOpenConnection = openConnection(str);
            filterResponseCode(httpURLConnectionOpenConnection.getResponseCode());
            return httpURLConnectionOpenConnection.getInputStream();
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }
}
