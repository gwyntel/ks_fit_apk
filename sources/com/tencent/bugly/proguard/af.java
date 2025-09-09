package com.tencent.bugly.proguard;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public final class af {

    /* renamed from: a, reason: collision with root package name */
    static af f20715a;

    /* renamed from: b, reason: collision with root package name */
    protected Context f20716b;

    /* renamed from: c, reason: collision with root package name */
    public Map<String, String> f20717c = null;

    af(Context context) {
        this.f20716b = context;
    }

    private static byte[] b(HttpURLConnection httpURLConnection) {
        BufferedInputStream bufferedInputStream;
        if (httpURLConnection == null) {
            return null;
        }
        try {
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        } catch (Throwable th) {
            th = th;
            bufferedInputStream = null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = bufferedInputStream.read(bArr);
                if (i2 <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                bufferedInputStream.close();
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
            return byteArray;
        } catch (Throwable th3) {
            th = th3;
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                return null;
            } finally {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (Throwable th4) {
                        th4.printStackTrace();
                    }
                }
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:41|(10:119|51|(5:121|53|110|54|55)(4:63|125|64|65)|86|(1:88)|114|89|77|133|132)(1:50)|112|66|67|(1:69)|116|70|108|71|77|133|132) */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0151, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0152, code lost:
    
        r6 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0157, code lost:
    
        if (com.tencent.bugly.proguard.al.a(r6) == false) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0159, code lost:
    
        r6.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0165, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0167, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0168, code lost:
    
        r20 = r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0159 A[PHI: r4 r6 r7 r12 r13 r14 r20
      0x0159: PHI (r4v10 int) = (r4v9 int), (r4v16 int) binds: [B:93:0x018a, B:75:0x0157] A[DONT_GENERATE, DONT_INLINE]
      0x0159: PHI (r6v9 java.lang.Throwable) = (r6v8 java.lang.Throwable), (r6v16 java.lang.Throwable) binds: [B:93:0x018a, B:75:0x0157] A[DONT_GENERATE, DONT_INLINE]
      0x0159: PHI (r7v9 int) = (r7v8 int), (r7v16 int) binds: [B:93:0x018a, B:75:0x0157] A[DONT_GENERATE, DONT_INLINE]
      0x0159: PHI (r12v6 java.lang.String) = (r12v5 java.lang.String), (r12v11 java.lang.String) binds: [B:93:0x018a, B:75:0x0157] A[DONT_GENERATE, DONT_INLINE]
      0x0159: PHI (r13v6 int) = (r13v5 int), (r13v9 int) binds: [B:93:0x018a, B:75:0x0157] A[DONT_GENERATE, DONT_INLINE]
      0x0159: PHI (r14v7 int) = (r14v6 int), (r14v14 int) binds: [B:93:0x018a, B:75:0x0157] A[DONT_GENERATE, DONT_INLINE]
      0x0159: PHI (r20v5 long) = (r20v4 long), (r20v11 long) binds: [B:93:0x018a, B:75:0x0157] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] a(java.lang.String r23, byte[] r24, com.tencent.bugly.proguard.aj r25, java.util.Map<java.lang.String, java.lang.String> r26) {
        /*
            Method dump skipped, instructions count: 440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.af.a(java.lang.String, byte[], com.tencent.bugly.proguard.aj, java.util.Map):byte[]");
    }

    private static Map<String, String> a(HttpURLConnection httpURLConnection) {
        HashMap map = new HashMap();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List<String> list = headerFields.get(str);
            if (list.size() > 0) {
                map.put(str, list.get(0));
            }
        }
        return map;
    }

    private static HttpURLConnection a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            al.e("destUrl is null.", new Object[0]);
            return null;
        }
        HttpURLConnection httpURLConnectionA = a(str2, str);
        if (httpURLConnectionA == null) {
            al.e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            httpURLConnectionA.setRequestProperty("wup_version", "3.0");
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    httpURLConnectionA.setRequestProperty(entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8"));
                }
            }
            httpURLConnectionA.setRequestProperty("A37", URLEncoder.encode(str2, "utf-8"));
            httpURLConnectionA.setRequestProperty("A38", URLEncoder.encode(str2, "utf-8"));
            OutputStream outputStream = httpURLConnectionA.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return httpURLConnectionA;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            al.e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    private static HttpURLConnection a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(str2);
            Proxy proxy = an.f20778a;
            if (proxy != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
            } else if (str != null && str.toLowerCase(Locale.US).contains("wap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (al.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
