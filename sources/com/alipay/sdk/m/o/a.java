package com.alipay.sdk.m.o;

import android.content.Context;
import android.net.NetworkInfo;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9589a = "msp";

    /* renamed from: b, reason: collision with root package name */
    public static final String f9590b = "application/octet-stream;binary/octet-stream";

    /* renamed from: c, reason: collision with root package name */
    public static final CookieManager f9591c = new CookieManager();

    /* renamed from: com.alipay.sdk.m.o.a$a, reason: collision with other inner class name */
    public static final class C0051a {

        /* renamed from: a, reason: collision with root package name */
        public final String f9592a;

        /* renamed from: b, reason: collision with root package name */
        public final byte[] f9593b;

        /* renamed from: c, reason: collision with root package name */
        public final Map<String, String> f9594c;

        public C0051a(String str, Map<String, String> map, byte[] bArr) {
            this.f9592a = str;
            this.f9593b = bArr;
            this.f9594c = map;
        }

        public String toString() {
            return String.format("<UrlConnectionConfigure url=%s headers=%s>", this.f9592a, this.f9594c);
        }
    }

    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        public final Map<String, List<String>> f9595a;

        /* renamed from: b, reason: collision with root package name */
        public final String f9596b;

        /* renamed from: c, reason: collision with root package name */
        public final byte[] f9597c;

        public b(Map<String, List<String>> map, String str, byte[] bArr) {
            this.f9595a = map;
            this.f9596b = str;
            this.f9597c = bArr;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01a3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01a8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x019e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.alipay.sdk.m.o.a.b a(android.content.Context r11, com.alipay.sdk.m.o.a.C0051a r12) {
        /*
            Method dump skipped, instructions count: 445
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.o.a.a(android.content.Context, com.alipay.sdk.m.o.a$a):com.alipay.sdk.m.o.a$b");
    }

    public static Proxy b(Context context) {
        String strA = a(context);
        if (strA != null && !strA.contains("wap")) {
            return null;
        }
        try {
            String property = System.getProperty("https.proxyHost");
            String property2 = System.getProperty("https.proxyPort");
            if (TextUtils.isEmpty(property)) {
                return null;
            }
            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(property, Integer.parseInt(property2)));
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String a(Context context) {
        try {
            NetworkInfo networkInfoA = com.alipay.sdk.m.w.b.a(null, context);
            if (networkInfoA != null && networkInfoA.isAvailable()) {
                return networkInfoA.getType() == 1 ? "wifi" : networkInfoA.getExtraInfo().toLowerCase();
            }
        } catch (Exception unused) {
        }
        return "none";
    }

    public static byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i2 = inputStream.read(bArr, 0, 1024);
            if (i2 != -1) {
                byteArrayOutputStream.write(bArr, 0, i2);
            } else {
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }
}
