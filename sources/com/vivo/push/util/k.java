package com.vivo.push.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.vivo.push.d.r;
import com.vivo.push.model.InsideNotificationItem;
import java.util.List;

/* loaded from: classes4.dex */
public final class k extends AsyncTask<String, Void, List<Bitmap>> {

    /* renamed from: a, reason: collision with root package name */
    private Context f23247a;

    /* renamed from: b, reason: collision with root package name */
    private InsideNotificationItem f23248b;

    /* renamed from: c, reason: collision with root package name */
    private long f23249c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f23250d;

    /* renamed from: e, reason: collision with root package name */
    private int f23251e = 0;

    /* renamed from: f, reason: collision with root package name */
    private r.a f23252f;

    public k(Context context, InsideNotificationItem insideNotificationItem, long j2, boolean z2, r.a aVar) {
        this.f23247a = context;
        this.f23248b = insideNotificationItem;
        this.f23249c = j2;
        this.f23250d = z2;
        this.f23252f = aVar;
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ void onPostExecute(List<Bitmap> list) {
        List<Bitmap> list2 = list;
        super.onPostExecute(list2);
        p.c("ImageDownTask", "onPostExecute");
        com.vivo.push.m.c(new l(this, list2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:49:0x008d A[EXC_TOP_SPLITTER, PHI: r5
      0x008d: PHI (r5v5 java.io.InputStream) = (r5v4 java.io.InputStream), (r5v6 java.io.InputStream) binds: [B:26:0x008b, B:31:0x0097] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<android.graphics.Bitmap> doInBackground(java.lang.String... r10) throws java.lang.Throwable {
        /*
            r9 = this;
            com.vivo.push.model.InsideNotificationItem r0 = r9.f23248b
            int r0 = r0.getNotifyDisplayStatus()
            r9.f23251e = r0
            boolean r0 = r9.f23250d
            r1 = 0
            java.lang.String r2 = "ImageDownTask"
            if (r0 != 0) goto L15
            java.lang.String r10 = "bitmap is not display by forbid net"
            com.vivo.push.util.p.d(r2, r10)
            return r1
        L15:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3 = 0
            r4 = r3
        L1c:
            r5 = 2
            if (r4 >= r5) goto Lae
            r5 = r10[r4]
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "imgUrl="
            r6.<init>(r7)
            r6.append(r5)
            java.lang.String r7 = " i="
            r6.append(r7)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            com.vivo.push.util.p.d(r2, r6)
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto La5
            java.net.URL r6 = new java.net.URL     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            r6.<init>(r5)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            java.net.URLConnection r5 = r6.openConnection()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            r6 = 30000(0x7530, float:4.2039E-41)
            r5.setConnectTimeout(r6)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            r6 = 1
            r5.setDoInput(r6)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            r5.setUseCaches(r3)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            r5.connect()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            int r6 = r5.getResponseCode()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            java.lang.String r7 = "code="
            java.lang.String r8 = java.lang.String.valueOf(r6)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            java.lang.String r7 = r7.concat(r8)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            com.vivo.push.util.p.c(r2, r7)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            r7 = 200(0xc8, float:2.8E-43)
            if (r6 != r7) goto L7d
            java.io.InputStream r5 = r5.getInputStream()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L85 java.net.MalformedURLException -> L91
            android.graphics.Bitmap r6 = android.graphics.BitmapFactory.decodeStream(r5)     // Catch: java.lang.Throwable -> L78 java.io.IOException -> L86 java.net.MalformedURLException -> L92
            goto L7f
        L78:
            r10 = move-exception
            r1 = r5
            goto L9f
        L7b:
            r10 = move-exception
            goto L9f
        L7d:
            r5 = r1
            r6 = r5
        L7f:
            if (r5 == 0) goto L9b
            r5.close()     // Catch: java.lang.Exception -> L9b
            goto L9b
        L85:
            r5 = r1
        L86:
            java.lang.String r6 = "IOException"
            com.vivo.push.util.p.a(r2, r6)     // Catch: java.lang.Throwable -> L78
            if (r5 == 0) goto L9a
        L8d:
            r5.close()     // Catch: java.lang.Exception -> L9a
            goto L9a
        L91:
            r5 = r1
        L92:
            java.lang.String r6 = "MalformedURLException"
            com.vivo.push.util.p.a(r2, r6)     // Catch: java.lang.Throwable -> L78
            if (r5 == 0) goto L9a
            goto L8d
        L9a:
            r6 = r1
        L9b:
            r0.add(r6)
            goto Laa
        L9f:
            if (r1 == 0) goto La4
            r1.close()     // Catch: java.lang.Exception -> La4
        La4:
            throw r10
        La5:
            if (r4 != 0) goto Laa
            r0.add(r1)
        Laa:
            int r4 = r4 + 1
            goto L1c
        Lae:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vivo.push.util.k.doInBackground(java.lang.String[]):java.util.List");
    }
}
