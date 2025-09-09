package anet.channel.session;

import android.os.Build;
import android.util.Pair;
import anet.channel.RequestCb;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.bytes.a;
import anet.channel.request.Request;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.StringUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes2.dex */
public class b {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public int f6906a;

        /* renamed from: b, reason: collision with root package name */
        public byte[] f6907b;

        /* renamed from: c, reason: collision with root package name */
        public Map<String, List<String>> f6908c;

        /* renamed from: d, reason: collision with root package name */
        public int f6909d;

        /* renamed from: e, reason: collision with root package name */
        public boolean f6910e;
    }

    private b() {
    }

    /* JADX WARN: Removed duplicated region for block: B:200:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x02f0 A[Catch: all -> 0x0090, IOException -> 0x009e, CancellationException -> 0x00a4, ConnectException -> 0x00bd, ConnectTimeoutException -> 0x00c3, SocketTimeoutException -> 0x00c9, UnknownHostException -> 0x00cf, Exception -> 0x021c, SSLException -> 0x0222, SSLHandshakeException -> 0x0226, TryCatch #14 {SSLHandshakeException -> 0x0226, blocks: (B:112:0x013b, B:115:0x017e, B:118:0x0192, B:120:0x019d, B:122:0x01a7, B:124:0x01ad, B:127:0x01c6, B:160:0x024d, B:163:0x025e, B:183:0x027d, B:185:0x029f, B:194:0x02b2, B:196:0x02cd, B:198:0x02db, B:199:0x02e2, B:203:0x02ff, B:206:0x0316, B:208:0x032f, B:201:0x02f0, B:202:0x02f7), top: B:388:0x013b }] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0316 A[Catch: all -> 0x0090, IOException -> 0x009e, CancellationException -> 0x00a4, ConnectException -> 0x00bd, ConnectTimeoutException -> 0x00c3, SocketTimeoutException -> 0x00c9, UnknownHostException -> 0x00cf, Exception -> 0x021c, SSLException -> 0x0222, SSLHandshakeException -> 0x0226, TRY_ENTER, TryCatch #14 {SSLHandshakeException -> 0x0226, blocks: (B:112:0x013b, B:115:0x017e, B:118:0x0192, B:120:0x019d, B:122:0x01a7, B:124:0x01ad, B:127:0x01c6, B:160:0x024d, B:163:0x025e, B:183:0x027d, B:185:0x029f, B:194:0x02b2, B:196:0x02cd, B:198:0x02db, B:199:0x02e2, B:203:0x02ff, B:206:0x0316, B:208:0x032f, B:201:0x02f0, B:202:0x02f7), top: B:388:0x013b }] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x032f A[Catch: all -> 0x0090, IOException -> 0x009e, CancellationException -> 0x00a4, ConnectException -> 0x00bd, ConnectTimeoutException -> 0x00c3, SocketTimeoutException -> 0x00c9, UnknownHostException -> 0x00cf, Exception -> 0x021c, SSLException -> 0x0222, SSLHandshakeException -> 0x0226, TRY_LEAVE, TryCatch #14 {SSLHandshakeException -> 0x0226, blocks: (B:112:0x013b, B:115:0x017e, B:118:0x0192, B:120:0x019d, B:122:0x01a7, B:124:0x01ad, B:127:0x01c6, B:160:0x024d, B:163:0x025e, B:183:0x027d, B:185:0x029f, B:194:0x02b2, B:196:0x02cd, B:198:0x02db, B:199:0x02e2, B:203:0x02ff, B:206:0x0316, B:208:0x032f, B:201:0x02f0, B:202:0x02f7), top: B:388:0x013b }] */
    /* JADX WARN: Removed duplicated region for block: B:263:0x03ae A[Catch: all -> 0x03a0, TryCatch #55 {all -> 0x03a0, blocks: (B:11:0x0035, B:261:0x03a8, B:263:0x03ae, B:265:0x03b5, B:267:0x03bd, B:269:0x03d1, B:268:0x03cc, B:278:0x03f2, B:287:0x0427, B:341:0x0534, B:350:0x055c, B:359:0x0582, B:368:0x05a8), top: B:403:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:264:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x03bd A[Catch: all -> 0x03a0, TryCatch #55 {all -> 0x03a0, blocks: (B:11:0x0035, B:261:0x03a8, B:263:0x03ae, B:265:0x03b5, B:267:0x03bd, B:269:0x03d1, B:268:0x03cc, B:278:0x03f2, B:287:0x0427, B:341:0x0534, B:350:0x055c, B:359:0x0582, B:368:0x05a8), top: B:403:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x03cc A[Catch: all -> 0x03a0, TryCatch #55 {all -> 0x03a0, blocks: (B:11:0x0035, B:261:0x03a8, B:263:0x03ae, B:265:0x03b5, B:267:0x03bd, B:269:0x03d1, B:268:0x03cc, B:278:0x03f2, B:287:0x0427, B:341:0x0534, B:350:0x055c, B:359:0x0582, B:368:0x05a8), top: B:403:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:300:0x047c A[Catch: all -> 0x0090, TRY_LEAVE, TryCatch #49 {all -> 0x0090, blocks: (B:12:0x0039, B:14:0x003f, B:16:0x0057, B:19:0x0070, B:21:0x008b, B:110:0x0129, B:112:0x013b, B:115:0x017e, B:118:0x0192, B:120:0x019d, B:122:0x01a7, B:124:0x01ad, B:127:0x01c6, B:160:0x024d, B:163:0x025e, B:183:0x027d, B:185:0x029f, B:194:0x02b2, B:196:0x02cd, B:198:0x02db, B:199:0x02e2, B:203:0x02ff, B:206:0x0316, B:208:0x032f, B:201:0x02f0, B:202:0x02f7, B:295:0x0451, B:298:0x0475, B:300:0x047c, B:304:0x049f), top: B:402:0x0039 }] */
    /* JADX WARN: Removed duplicated region for block: B:322:0x04ef A[Catch: all -> 0x0514, TRY_LEAVE, TryCatch #109 {all -> 0x0514, blocks: (B:317:0x04c4, B:320:0x04e8, B:322:0x04ef, B:325:0x0510), top: B:419:0x04c4 }] */
    /* JADX WARN: Removed duplicated region for block: B:386:0x05d9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:390:0x03df A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:392:0x0413 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:396:0x04a8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:398:0x0596 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:400:0x05c9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:406:0x0570 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:413:0x051e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:415:0x0548 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:417:0x0438 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:451:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static anet.channel.session.b.a a(anet.channel.request.Request r25, anet.channel.RequestCb r26) {
        /*
            Method dump skipped, instructions count: 1528
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.session.b.a(anet.channel.request.Request, anet.channel.RequestCb):anet.channel.session.b$a");
    }

    private static void a(Request request, a aVar, RequestCb requestCb, int i2, Throwable th) {
        String errMsg = ErrorConstant.getErrMsg(i2);
        ALog.e("awcn.HttpConnector", "onException", request.getSeq(), "errorCode", Integer.valueOf(i2), AlinkConstants.KEY_ERR_MSG, errMsg, "url", request.getUrlString(), "host", request.getHost());
        if (aVar != null) {
            aVar.f6906a = i2;
        }
        if (!request.f6850a.isDone.get()) {
            request.f6850a.statusCode = i2;
            request.f6850a.msg = errMsg;
            request.f6850a.rspEnd = System.currentTimeMillis();
            if (i2 != -204) {
                AppMonitor.getInstance().commitStat(new ExceptionStatistic(i2, errMsg, request.f6850a, th));
            }
        }
        if (requestCb != null) {
            requestCb.onFinish(i2, errMsg, request.f6850a);
        }
    }

    private static HttpURLConnection a(Request request) throws IOException {
        HttpURLConnection httpURLConnection;
        Pair<String, Integer> wifiProxy = NetworkStatusHelper.getWifiProxy();
        Proxy proxy = wifiProxy != null ? new Proxy(Proxy.Type.HTTP, new InetSocketAddress((String) wifiProxy.first, ((Integer) wifiProxy.second).intValue())) : null;
        anet.channel.util.g gVarA = anet.channel.util.g.a();
        if (NetworkStatusHelper.getStatus().isMobile() && gVarA != null) {
            proxy = gVarA.b();
        }
        URL url = request.getUrl();
        if (proxy != null) {
            httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
        } else {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        httpURLConnection.setConnectTimeout(request.getConnectTimeout());
        httpURLConnection.setReadTimeout(request.getReadTimeout());
        httpURLConnection.setRequestMethod(request.getMethod());
        if (request.containsBody()) {
            httpURLConnection.setDoOutput(true);
        }
        Map<String, String> headers = request.getHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpURLConnection.addRequestProperty(entry.getKey(), entry.getValue());
        }
        String host = headers.get("Host");
        if (host == null) {
            host = request.getHost();
        }
        String strConcatString = request.getHttpUrl().containsNonDefaultPort() ? StringUtils.concatString(host, ":", String.valueOf(request.getHttpUrl().getPort())) : host;
        httpURLConnection.setRequestProperty("Host", strConcatString);
        if (NetworkStatusHelper.getApn().equals("cmwap")) {
            httpURLConnection.setRequestProperty(HttpConstant.X_ONLINE_HOST, strConcatString);
        }
        if (!headers.containsKey("Accept-Encoding")) {
            httpURLConnection.setRequestProperty("Accept-Encoding", HttpConstant.GZIP);
        }
        if (gVarA != null) {
            httpURLConnection.setRequestProperty("Authorization", gVarA.c());
        }
        if (url.getProtocol().equalsIgnoreCase("https")) {
            a(httpURLConnection, request, host);
        }
        httpURLConnection.setInstanceFollowRedirects(false);
        return httpURLConnection;
    }

    private static void a(HttpURLConnection httpURLConnection, Request request, String str) {
        if (Integer.parseInt(Build.VERSION.SDK) < 8) {
            ALog.e("awcn.HttpConnector", "supportHttps", "[supportHttps]Froyo 以下版本不支持https", new Object[0]);
            return;
        }
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
        if (request.getSslSocketFactory() != null) {
            httpsURLConnection.setSSLSocketFactory(request.getSslSocketFactory());
        } else {
            SSLSocketFactory sSLSocketFactory = anet.channel.util.b.f7077a;
            if (sSLSocketFactory != null) {
                httpsURLConnection.setSSLSocketFactory(sSLSocketFactory);
                if (ALog.isPrintLog(2)) {
                    ALog.i("awcn.HttpConnector", "HttpSslUtil", request.getSeq(), "SslSocketFactory", anet.channel.util.b.f7077a);
                }
            }
        }
        if (request.getHostnameVerifier() != null) {
            httpsURLConnection.setHostnameVerifier(request.getHostnameVerifier());
            return;
        }
        HostnameVerifier hostnameVerifier = anet.channel.util.b.f7078b;
        if (hostnameVerifier != null) {
            httpsURLConnection.setHostnameVerifier(hostnameVerifier);
            if (ALog.isPrintLog(2)) {
                ALog.i("awcn.HttpConnector", "HttpSslUtil", request.getSeq(), "HostnameVerifier", anet.channel.util.b.f7078b);
                return;
            }
            return;
        }
        httpsURLConnection.setHostnameVerifier(new c(str));
    }

    private static int a(HttpURLConnection httpURLConnection, Request request) throws IOException {
        int i2 = 0;
        if (request.containsBody()) {
            OutputStream outputStream = null;
            try {
                try {
                    outputStream = httpURLConnection.getOutputStream();
                    int iPostBody = request.postBody(outputStream);
                    if (outputStream != null) {
                        try {
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e2) {
                            ALog.e("awcn.HttpConnector", "postData", request.getSeq(), e2, new Object[0]);
                        }
                    }
                    i2 = iPostBody;
                } catch (Throwable th) {
                    if (outputStream != null) {
                        try {
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e3) {
                            ALog.e("awcn.HttpConnector", "postData", request.getSeq(), e3, new Object[0]);
                        }
                    }
                    throw th;
                }
            } catch (Exception e4) {
                ALog.e("awcn.HttpConnector", "postData error", request.getSeq(), e4, new Object[0]);
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e5) {
                        ALog.e("awcn.HttpConnector", "postData", request.getSeq(), e5, new Object[0]);
                    }
                }
            }
            long j2 = i2;
            request.f6850a.reqBodyInflateSize = j2;
            request.f6850a.reqBodyDeflateSize = j2;
            request.f6850a.sendDataSize = j2;
        }
        return i2;
    }

    private static void a(HttpURLConnection httpURLConnection, Request request, a aVar, RequestCb requestCb) throws Throwable {
        InputStream errorStream;
        ByteArrayOutputStream byteArrayOutputStream;
        httpURLConnection.getURL().toString();
        anet.channel.util.a aVar2 = null;
        try {
            errorStream = httpURLConnection.getInputStream();
        } catch (IOException e2) {
            if (e2 instanceof FileNotFoundException) {
                ALog.w("awcn.HttpConnector", "File not found", request.getSeq(), "url", request.getUrlString());
            }
            try {
                errorStream = httpURLConnection.getErrorStream();
            } catch (Exception e3) {
                ALog.e("awcn.HttpConnector", "get error stream failed.", request.getSeq(), e3, new Object[0]);
                errorStream = null;
            }
        }
        if (errorStream == null) {
            a(request, aVar, requestCb, ErrorConstant.ERROR_IO_EXCEPTION, null);
            return;
        }
        if (requestCb == null) {
            int i2 = aVar.f6909d;
            if (i2 <= 0) {
                i2 = 1024;
            } else if (aVar.f6910e) {
                i2 *= 2;
            }
            byteArrayOutputStream = new ByteArrayOutputStream(i2);
        } else {
            byteArrayOutputStream = null;
        }
        try {
            anet.channel.util.a aVar3 = new anet.channel.util.a(errorStream);
            try {
                InputStream gZIPInputStream = aVar.f6910e ? new GZIPInputStream(aVar3) : aVar3;
                ByteArray byteArrayA = null;
                while (!Thread.currentThread().isInterrupted()) {
                    if (byteArrayA == null) {
                        byteArrayA = a.C0009a.f6699a.a(2048);
                    }
                    int from = byteArrayA.readFrom(gZIPInputStream);
                    if (from != -1) {
                        if (byteArrayOutputStream != null) {
                            byteArrayA.writeTo(byteArrayOutputStream);
                        } else {
                            requestCb.onDataReceive(byteArrayA, false);
                            byteArrayA = null;
                        }
                        long j2 = from;
                        request.f6850a.recDataSize += j2;
                        request.f6850a.rspBodyInflateSize += j2;
                    } else {
                        if (byteArrayOutputStream != null) {
                            byteArrayA.recycle();
                        } else {
                            requestCb.onDataReceive(byteArrayA, true);
                        }
                        if (byteArrayOutputStream != null) {
                            aVar.f6907b = byteArrayOutputStream.toByteArray();
                        }
                        request.f6850a.recDataTime = System.currentTimeMillis() - request.f6850a.rspStart;
                        request.f6850a.rspBodyDeflateSize = aVar3.a();
                        try {
                            gZIPInputStream.close();
                            return;
                        } catch (IOException unused) {
                            return;
                        }
                    }
                }
                throw new CancellationException("task cancelled");
            } catch (Throwable th) {
                th = th;
                aVar2 = aVar3;
                request.f6850a.recDataTime = System.currentTimeMillis() - request.f6850a.rspStart;
                request.f6850a.rspBodyDeflateSize = aVar2.a();
                try {
                    errorStream.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
