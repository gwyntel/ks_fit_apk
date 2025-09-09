package com.xiaomi.push;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import anet.channel.util.HttpConstant;
import com.google.common.net.HttpHeaders;
import com.huawei.hms.framework.common.ContainerUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
public class bg {

    /* renamed from: a, reason: collision with root package name */
    private static final AtomicReference<a<bj>> f23489a = new AtomicReference<>(a());

    /* renamed from: a, reason: collision with other field name */
    public static final Pattern f209a = Pattern.compile("([^\\s;]+)(.*)");

    /* renamed from: b, reason: collision with root package name */
    public static final Pattern f23490b = Pattern.compile("(.*?charset\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);

    /* renamed from: c, reason: collision with root package name */
    public static final Pattern f23491c = Pattern.compile("(\\<\\?xml\\s+.*?encoding\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);

    private static class a<T> extends FutureTask<T> {

        /* renamed from: a, reason: collision with root package name */
        private long f23492a;

        public a(Callable<T> callable) {
            super(callable);
        }

        public boolean a() {
            return j.m550a(C0472r.m684a()) || (isDone() && Math.abs(SystemClock.elapsedRealtime() - this.f23492a) > 1800000);
        }

        @Override // java.util.concurrent.FutureTask, java.util.concurrent.RunnableFuture, java.lang.Runnable
        public void run() {
            this.f23492a = SystemClock.elapsedRealtime();
            super.run();
        }
    }

    public static final class b extends FilterInputStream {

        /* renamed from: a, reason: collision with root package name */
        private boolean f23493a;

        public b(InputStream inputStream) {
            super(inputStream);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) {
            int i4;
            if (!this.f23493a && (i4 = super.read(bArr, i2, i3)) != -1) {
                return i4;
            }
            this.f23493a = true;
            return -1;
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        public int f23494a;

        /* renamed from: a, reason: collision with other field name */
        public Map<String, String> f210a;

        public String toString() {
            return String.format("resCode = %1$d, headers = %2$s", Integer.valueOf(this.f23494a), this.f210a.toString());
        }
    }

    public static InputStream a(Context context, URL url, boolean z2, String str, String str2) {
        return a(context, url, z2, str, str2, null, null);
    }

    public static void b() {
        f23489a.set(a());
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean c(android.content.Context r3) {
        /*
            java.lang.String r0 = "connectivity"
            java.lang.Object r0 = r3.getSystemService(r0)
            android.net.ConnectivityManager r0 = (android.net.ConnectivityManager) r0
            r1 = 0
            if (r0 == 0) goto L1c
            android.net.Network r2 = r0.getActiveNetwork()     // Catch: java.lang.Exception -> L1c
            android.net.NetworkCapabilities r0 = r0.getNetworkCapabilities(r2)     // Catch: java.lang.Exception -> L1c
            if (r0 == 0) goto L1c
            r2 = 16
            boolean r0 = r0.hasCapability(r2)     // Catch: java.lang.Exception -> L1c
            goto L1d
        L1c:
            r0 = r1
        L1d:
            if (r0 == 0) goto L26
            boolean r3 = d(r3)
            if (r3 == 0) goto L26
            r1 = 1
        L26:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.bg.c(android.content.Context):boolean");
    }

    public static boolean d(Context context) {
        bj bjVarM201a = m201a();
        return bjVarM201a != null && bjVarM201a.m212a();
    }

    public static boolean e(Context context) {
        bj bjVarM201a = m201a();
        return bjVarM201a != null && 1 == bjVarM201a.a();
    }

    public static boolean f(Context context) {
        bj bjVarM202a = m202a(context);
        return bjVarM202a != null && bjVarM202a.a() == 0 && 20 == bjVarM202a.b();
    }

    public static boolean g(Context context) {
        bj bjVarM202a = m202a(context);
        return bjVarM202a != null && bjVarM202a.a() == 0 && 13 == bjVarM202a.b();
    }

    public static boolean h(Context context) {
        bj bjVarM202a = m202a(context);
        if (bjVarM202a == null || bjVarM202a.a() != 0) {
            return false;
        }
        String strM213b = bjVarM202a.m213b();
        if (!"TD-SCDMA".equalsIgnoreCase(strM213b) && !"CDMA2000".equalsIgnoreCase(strM213b) && !"WCDMA".equalsIgnoreCase(strM213b)) {
            switch (bjVarM202a.b()) {
            }
            return false;
        }
        return true;
    }

    public static boolean i(Context context) {
        bj bjVarM202a = m202a(context);
        if (bjVarM202a == null || bjVarM202a.a() != 0) {
            return false;
        }
        int iB = bjVarM202a.b();
        return iB == 1 || iB == 2 || iB == 4 || iB == 7 || iB == 11;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m207a() {
        b();
    }

    public static boolean b(Context context) {
        return a(context) >= 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static Object m203a(Context context) {
        if (context == null) {
            context = C0472r.m684a();
        }
        if (context == null || j.m550a(context)) {
            return null;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkRequest networkRequestBuild = new NetworkRequest.Builder().build();
            bh bhVar = new bh();
            try {
                connectivityManager.registerNetworkCallback(networkRequestBuild, bhVar);
            } catch (Exception unused) {
            }
            return bhVar;
        } catch (Exception unused2) {
            return null;
        }
    }

    private static a<bj> a() {
        return new a<>(new bi());
    }

    /* renamed from: a, reason: collision with other method in class */
    public static bj m201a() {
        AtomicReference<a<bj>> atomicReference = f23489a;
        a<bj> aVarA = atomicReference.get();
        if (aVarA != null) {
            try {
                if (aVarA.a()) {
                    aVarA = a();
                    atomicReference.set(aVarA);
                }
                if (!aVarA.isDone()) {
                    aVarA.run();
                }
                return aVarA.get();
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static InputStream a(Context context, URL url, boolean z2, String str, String str2, Map<String, String> map, c cVar) throws IOException {
        if (context == null) {
            throw new IllegalArgumentException(com.umeng.analytics.pro.f.X);
        }
        if (url != null) {
            URL url2 = !z2 ? new URL(a(url.toString())) : url;
            try {
                HttpURLConnection.setFollowRedirects(true);
                HttpURLConnection httpURLConnectionM205a = m205a(context, url2);
                httpURLConnectionM205a.setConnectTimeout(10000);
                httpURLConnectionM205a.setReadTimeout(15000);
                if (!TextUtils.isEmpty(str)) {
                    httpURLConnectionM205a.setRequestProperty("User-Agent", str);
                }
                if (str2 != null) {
                    httpURLConnectionM205a.setRequestProperty("Cookie", str2);
                }
                if (map != null) {
                    for (String str3 : map.keySet()) {
                        httpURLConnectionM205a.setRequestProperty(str3, map.get(str3));
                    }
                }
                if (cVar != null && (url.getProtocol().equals("http") || url.getProtocol().equals("https"))) {
                    cVar.f23494a = httpURLConnectionM205a.getResponseCode();
                    if (cVar.f210a == null) {
                        cVar.f210a = new HashMap();
                    }
                    int i2 = 0;
                    while (true) {
                        String headerFieldKey = httpURLConnectionM205a.getHeaderFieldKey(i2);
                        String headerField = httpURLConnectionM205a.getHeaderField(i2);
                        if (headerFieldKey == null && headerField == null) {
                            break;
                        }
                        if (!TextUtils.isEmpty(headerFieldKey) && !TextUtils.isEmpty(headerField)) {
                            cVar.f210a.put(headerFieldKey, headerField);
                        }
                        i2++;
                    }
                }
                return new b(httpURLConnectionM205a.getInputStream());
            } catch (IOException e2) {
                throw new IOException("IOException:" + e2.getClass().getSimpleName());
            } catch (Throwable th) {
                throw new IOException(th.getMessage());
            }
        }
        throw new IllegalArgumentException("url");
    }

    public static String a(Context context, URL url) {
        return a(context, url, false, null, "UTF-8", null);
    }

    public static String a(Context context, URL url, boolean z2, String str, String str2, String str3) throws Throwable {
        InputStream inputStreamA;
        try {
            inputStreamA = a(context, url, z2, str, str3);
        } catch (Throwable th) {
            th = th;
            inputStreamA = null;
        }
        try {
            StringBuilder sb = new StringBuilder(1024);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamA, str2));
            char[] cArr = new char[4096];
            while (true) {
                int i2 = bufferedReader.read(cArr);
                if (-1 != i2) {
                    sb.append(cArr, 0, i2);
                } else {
                    x.a((Closeable) inputStreamA);
                    return sb.toString();
                }
            }
        } catch (Throwable th2) {
            th = th2;
            x.a((Closeable) inputStreamA);
            throw th;
        }
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        new String();
        return String.format("%s&key=%s", str, bo.a(String.format("%sbe988a6134bc8254465424e5a70ef037", str)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String a(String str, Map<String, String> map, File file, String str2) {
        if (!file.exists()) {
            return null;
        }
        String name = file.getName();
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection", HttpHeaders.KEEP_ALIVE);
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                httpURLConnection.setFixedLengthStreamingMode(name.length() + 77 + ((int) file.length()) + str2.length());
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes("--*****\r\n");
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + str2 + "\";filename=\"" + file.getName() + "\"" + IOUtils.LINE_SEPARATOR_WINDOWS);
                dataOutputStream.writeBytes(IOUtils.LINE_SEPARATOR_WINDOWS);
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i2 = fileInputStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        dataOutputStream.write(bArr, 0, i2);
                        dataOutputStream.flush();
                    }
                    dataOutputStream.writeBytes(IOUtils.LINE_SEPARATOR_WINDOWS);
                    dataOutputStream.writeBytes("--");
                    dataOutputStream.writeBytes("*****");
                    dataOutputStream.writeBytes("--");
                    dataOutputStream.writeBytes(IOUtils.LINE_SEPARATOR_WINDOWS);
                    dataOutputStream.flush();
                    StringBuffer stringBuffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new b(httpURLConnection.getInputStream())));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line != null) {
                                stringBuffer.append(line);
                            } else {
                                String string = stringBuffer.toString();
                                x.a((Closeable) fileInputStream);
                                x.a(bufferedReader);
                                return string;
                            }
                        } catch (IOException e2) {
                            e = e2;
                            throw new IOException("IOException:" + e.getClass().getSimpleName());
                        } catch (Throwable th) {
                            th = th;
                            throw new IOException(th.getMessage());
                        }
                    }
                } catch (IOException e3) {
                    e = e3;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e4) {
                e = e4;
            } catch (Throwable th3) {
                th = th3;
            }
        } finally {
            x.a((Closeable) null);
            x.a((Closeable) file);
        }
    }

    public static int a(Context context) {
        bj bjVarM201a = m201a();
        if (bjVarM201a == null) {
            return -1;
        }
        return bjVarM201a.a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m208a(Context context) {
        bj bjVarM201a;
        if (!"CN".equalsIgnoreCase(((TelephonyManager) context.getSystemService("phone")).getSimCountryIso()) || (bjVarM201a = m201a()) == null) {
            return false;
        }
        String strC = bjVarM201a.c();
        return !TextUtils.isEmpty(strC) && strC.length() >= 3 && strC.contains("ctwap");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static HttpURLConnection m205a(Context context, URL url) {
        if (!"http".equals(url.getProtocol())) {
            return (HttpURLConnection) url.openConnection();
        }
        if (m208a(context)) {
            return (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.200", 80)));
        }
        return (HttpURLConnection) url.openConnection();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static bj m202a(Context context) {
        return m201a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m204a(Context context) {
        if (e(context)) {
            return "wifi";
        }
        bj bjVarM201a = m201a();
        if (bjVarM201a == null) {
            return "";
        }
        return (bjVarM201a.m211a() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + bjVarM201a.m213b() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + bjVarM201a.c()).toLowerCase();
    }

    public static be a(Context context, String str, Map<String, String> map) {
        return a(context, str, "POST", (Map<String, String>) null, a(map));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static be a(Context context, String str, String str2, Map<String, String> map, String str3) {
        boolean zEqualsIgnoreCase;
        BufferedReader bufferedReader;
        OutputStream outputStream;
        be beVar = new be();
        try {
            try {
                try {
                    HttpURLConnection httpURLConnectionM205a = m205a(context, m206a(str));
                    httpURLConnectionM205a.setConnectTimeout(10000);
                    httpURLConnectionM205a.setReadTimeout(15000);
                    String str4 = str2;
                    if (str2 == 0) {
                        str4 = "GET";
                    }
                    httpURLConnectionM205a.setRequestMethod(str4);
                    int i2 = 0;
                    if (map != null) {
                        zEqualsIgnoreCase = HttpConstant.GZIP.equalsIgnoreCase(map.get("Content-Encoding"));
                        for (String str5 : map.keySet()) {
                            httpURLConnectionM205a.setRequestProperty(str5, map.get(str5));
                        }
                    } else {
                        zEqualsIgnoreCase = false;
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        httpURLConnectionM205a.setDoOutput(true);
                        byte[] bytes = str3.getBytes();
                        if (zEqualsIgnoreCase) {
                            outputStream = new GZIPOutputStream(httpURLConnectionM205a.getOutputStream());
                        } else {
                            outputStream = httpURLConnectionM205a.getOutputStream();
                        }
                        try {
                            outputStream.write(bytes, 0, bytes.length);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e2) {
                            e = e2;
                            throw new IOException("err while request " + str + ":" + e.getClass().getSimpleName());
                        } catch (Throwable th) {
                            th = th;
                            throw new IOException(th.getMessage());
                        }
                    }
                    beVar.f23488a = httpURLConnectionM205a.getResponseCode();
                    com.xiaomi.channel.commonutils.logger.b.m91a("Http POST Response Code: " + beVar.f23488a);
                    while (true) {
                        String headerFieldKey = httpURLConnectionM205a.getHeaderFieldKey(i2);
                        String headerField = httpURLConnectionM205a.getHeaderField(i2);
                        if (headerFieldKey == null && headerField == null) {
                            try {
                                break;
                            } catch (IOException unused) {
                                bufferedReader = new BufferedReader(new InputStreamReader(new b(httpURLConnectionM205a.getErrorStream())));
                            }
                        } else {
                            beVar.f208a.put(headerFieldKey, headerField);
                            i2 += 2;
                        }
                    }
                    bufferedReader = new BufferedReader(new InputStreamReader(new b(httpURLConnectionM205a.getInputStream())));
                    try {
                        StringBuffer stringBuffer = new StringBuffer();
                        String property = System.getProperty("line.separator");
                        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                            stringBuffer.append(line);
                            stringBuffer.append(property);
                        }
                        beVar.f207a = stringBuffer.toString();
                        bufferedReader.close();
                        x.a((Closeable) null);
                        x.a((Closeable) null);
                        return beVar;
                    } catch (IOException e3) {
                        e = e3;
                        throw new IOException("err while request " + str + ":" + e.getClass().getSimpleName());
                    } catch (Throwable th2) {
                        th = th2;
                        throw new IOException(th.getMessage());
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (IOException e4) {
                e = e4;
            }
        } finally {
            x.a((Closeable) null);
            x.a((Closeable) str2);
        }
    }

    public static String a(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                try {
                    stringBuffer.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
                    stringBuffer.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    stringBuffer.append("&");
                } catch (UnsupportedEncodingException e2) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("Failed to convert from params map to string: " + e2);
                    com.xiaomi.channel.commonutils.logger.b.m91a("map: " + map.toString());
                    return null;
                }
            }
        }
        if (stringBuffer.length() > 0) {
            stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    private static URL m206a(String str) {
        return new URL(str);
    }
}
