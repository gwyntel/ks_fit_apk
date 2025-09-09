package com.meizu.cloud.pushsdk.e.d;

import android.net.TrafficStats;
import anet.channel.request.Request;
import com.meizu.cloud.pushsdk.e.d.k;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes4.dex */
public class e implements com.meizu.cloud.pushsdk.e.d.a {

    class a extends l {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ HttpURLConnection f19386a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ com.meizu.cloud.pushsdk.e.h.d f19387b;

        a(HttpURLConnection httpURLConnection, com.meizu.cloud.pushsdk.e.h.d dVar) {
            this.f19386a = httpURLConnection;
            this.f19387b = dVar;
        }

        @Override // com.meizu.cloud.pushsdk.e.d.l
        public com.meizu.cloud.pushsdk.e.h.d f() {
            return this.f19387b;
        }
    }

    private HttpURLConnection b(i iVar) throws IOException {
        URL url = new URL(iVar.e().toString());
        if (MinSdkChecker.isSupportNotificationChannel()) {
            TrafficStats.setThreadStatsTag(2006537699);
        }
        HttpURLConnection httpURLConnectionA = a(url);
        httpURLConnectionA.setConnectTimeout(60000);
        httpURLConnectionA.setReadTimeout(60000);
        httpURLConnectionA.setUseCaches(false);
        httpURLConnectionA.setDoInput(true);
        return httpURLConnectionA;
    }

    @Override // com.meizu.cloud.pushsdk.e.d.a
    public k a(i iVar) throws IOException {
        HttpURLConnection httpURLConnectionB = b(iVar);
        for (String str : iVar.c().a()) {
            String strA = iVar.a(str);
            com.meizu.cloud.pushsdk.e.b.a.b("current header name " + str + " value " + strA);
            httpURLConnectionB.addRequestProperty(str, strA);
        }
        b(httpURLConnectionB, iVar);
        return new k.b().a(httpURLConnectionB.getResponseCode()).a(iVar.c()).a(httpURLConnectionB.getResponseMessage()).a(iVar).a(a(httpURLConnectionB)).a();
    }

    private static l a(HttpURLConnection httpURLConnection) throws IOException {
        if (httpURLConnection.getDoInput()) {
            return new a(httpURLConnection, com.meizu.cloud.pushsdk.e.h.g.a(com.meizu.cloud.pushsdk.e.h.g.a(a(httpURLConnection.getResponseCode()) ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream())));
        }
        return null;
    }

    private static void b(HttpURLConnection httpURLConnection, i iVar) throws IOException {
        String str;
        String str2;
        int iB = iVar.b();
        if (iB != 0) {
            if (iB == 1) {
                str2 = "POST";
            } else if (iB == 2) {
                str2 = Request.Method.PUT;
            } else if (iB == 3) {
                str = "DELETE";
            } else if (iB == 4) {
                str = Request.Method.HEAD;
            } else {
                if (iB != 5) {
                    throw new IllegalStateException("Unknown method type.");
                }
                str2 = "PATCH";
            }
            httpURLConnection.setRequestMethod(str2);
            a(httpURLConnection, iVar);
            return;
        }
        str = "GET";
        httpURLConnection.setRequestMethod(str);
    }

    protected HttpURLConnection a(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        return httpURLConnection;
    }

    private static void a(HttpURLConnection httpURLConnection, i iVar) throws IOException {
        j jVarA = iVar.a();
        if (jVarA != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", jVarA.b().toString());
            com.meizu.cloud.pushsdk.e.h.c cVarA = com.meizu.cloud.pushsdk.e.h.g.a(com.meizu.cloud.pushsdk.e.h.g.a(httpURLConnection.getOutputStream()));
            jVarA.a(cVarA);
            cVarA.close();
        }
    }

    protected static boolean a(int i2) {
        return i2 >= 200 && i2 < 300;
    }
}
