package com.meizu.cloud.pushsdk.c.c;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f19187a = "b";

    /* renamed from: b, reason: collision with root package name */
    private static final Object f19188b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private static b f19189c;

    private b(Context context) {
        try {
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        a.a(context);
    }

    public static b a(Context context) {
        if (f19189c == null) {
            synchronized (f19188b) {
                try {
                    if (f19189c == null) {
                        f19189c = new b(context);
                    }
                } finally {
                }
            }
        }
        return f19189c;
    }

    public c b(String str, Map<String, String> map, String str2) {
        try {
            return a(str, a(map), str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:63:0x010a A[EXC_TOP_SPLITTER, PHI: r0 r1
      0x010a: PHI (r0v4 com.meizu.cloud.pushsdk.c.c.c) = (r0v10 com.meizu.cloud.pushsdk.c.c.c), (r0v6 com.meizu.cloud.pushsdk.c.c.c) binds: [B:46:0x012b, B:42:0x0108] A[DONT_GENERATE, DONT_INLINE]
      0x010a: PHI (r1v5 java.io.InputStream) = (r1v4 java.io.InputStream), (r1v8 java.io.InputStream) binds: [B:46:0x012b, B:42:0x0108] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.meizu.cloud.pushsdk.c.c.c a(java.lang.String r7, java.util.Map<java.lang.String, java.lang.String> r8, java.lang.String r9) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.c.c.b.a(java.lang.String, java.util.Map, java.lang.String):com.meizu.cloud.pushsdk.c.c.c");
    }

    private void b(URLConnection uRLConnection) {
        try {
            String headerField = uRLConnection.getHeaderField("X-S-Key");
            DebugLogger.d(f19187a, "get x_s_key = " + headerField);
            if (TextUtils.isEmpty(headerField)) {
                return;
            }
            a.e().c(headerField);
        } catch (NullPointerException unused) {
        }
    }

    private Map<String, String> a(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>(2);
        }
        byte[] bArrG = a.e().g();
        if (bArrG == null || bArrG.length <= 0) {
            byte[] bArrF = a.e().f();
            if (bArrF != null && bArrF.length > 0) {
                String str = new String(a.e().f());
                DebugLogger.d(f19187a, "attach x_a_key: " + str);
                map.put("X-A-Key", str);
            }
        } else {
            String str2 = new String(bArrG);
            DebugLogger.d(f19187a, "attach x_s_key: " + str2);
            map.put("X-S-Key", str2);
        }
        return map;
    }

    private void a(HttpURLConnection httpURLConnection, byte[] bArr) throws Throwable {
        GZIPOutputStream gZIPOutputStream;
        try {
            gZIPOutputStream = new GZIPOutputStream(httpURLConnection.getOutputStream());
        } catch (Throwable th) {
            th = th;
            gZIPOutputStream = null;
        }
        try {
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.flush();
            try {
                gZIPOutputStream.close();
            } catch (Exception unused) {
            }
        } catch (Throwable th2) {
            th = th2;
            if (gZIPOutputStream != null) {
                try {
                    gZIPOutputStream.close();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    private void a(URLConnection uRLConnection) {
        try {
            String headerField = uRLConnection.getHeaderField("Key-Timeout");
            DebugLogger.d(f19187a, "get keyTimeout = " + headerField);
        } catch (NullPointerException unused) {
        }
    }

    private byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            try {
                int i2 = inputStream.read();
                if (i2 == -1) {
                    break;
                }
                byteArrayOutputStream.write(i2);
            } catch (Throwable th) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused) {
                }
                throw th;
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException unused2) {
        }
        return byteArray;
    }
}
