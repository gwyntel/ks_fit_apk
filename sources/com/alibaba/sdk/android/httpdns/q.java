package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import com.huawei.hms.framework.common.ContainerUtils;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/* loaded from: classes2.dex */
class q implements Callable<String[]> {

    /* renamed from: a, reason: collision with root package name */
    private static Context f8902a;

    /* renamed from: a, reason: collision with other field name */
    private s f37a;

    /* renamed from: d, reason: collision with root package name */
    private int f8903d;

    /* renamed from: d, reason: collision with other field name */
    private long f38d;

    /* renamed from: e, reason: collision with root package name */
    private String[] f8904e;
    private Map<String, String> extra;
    private String hostName;

    /* renamed from: j, reason: collision with root package name */
    private boolean f8905j;

    /* renamed from: k, reason: collision with root package name */
    private String f8906k;

    /* renamed from: l, reason: collision with root package name */
    private String f8907l;
    private static d hostManager = d.a();

    /* renamed from: a, reason: collision with other field name */
    private static final Object f36a = new Object();

    q(String str, s sVar) {
        this.f8903d = 1;
        this.f8906k = null;
        this.f8904e = f.f20c;
        this.f8905j = false;
        this.f8907l = null;
        this.extra = new HashMap();
        this.f38d = 0L;
        this.hostName = str;
        this.f37a = sVar;
    }

    private boolean d(String str) {
        return str.matches("[a-zA-Z0-9\\-_]+");
    }

    private boolean e(String str) {
        return str.matches("[a-zA-Z0-9\\-_=]+");
    }

    private String getExtra() {
        boolean z2;
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = this.extra;
        boolean z3 = true;
        if (map != null) {
            z2 = true;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append("&sdns-");
                sb.append(entry.getKey());
                sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                if (!d(entry.getKey())) {
                    i.f("设置自定义参数失败，自定义key不合法：" + entry.getKey());
                    z3 = false;
                }
                if (!e(entry.getValue())) {
                    i.f("设置自定义参数失败，自定义value不合法：" + entry.getValue());
                    z2 = false;
                }
            }
        } else {
            z2 = true;
        }
        if (z3 && z2) {
            String string = sb.toString();
            if (string.getBytes("UTF-8").length <= 1000) {
                return string;
            }
            i.f("设置自定义参数失败，自定义参数过长");
        }
        return "";
    }

    static void setContext(Context context) {
        f8902a = context;
    }

    public void a(int i2) {
        if (i2 >= 0) {
            this.f8903d = i2;
        }
    }

    q(String str, s sVar, Map<String, String> map, String str2) {
        this.f8903d = 1;
        this.f8906k = null;
        this.f8904e = f.f20c;
        this.f8905j = false;
        this.f8907l = null;
        HashMap map2 = new HashMap();
        this.extra = map2;
        this.f38d = 0L;
        this.hostName = str;
        this.f37a = sVar;
        this.f8907l = str2;
        map2.putAll(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:83:0x03cb A[Catch: all -> 0x03d2, TryCatch #3 {all -> 0x03d2, blocks: (B:81:0x03bd, B:83:0x03cb, B:86:0x03d4), top: B:108:0x03bd }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03d4 A[Catch: all -> 0x03d2, TRY_LEAVE, TryCatch #3 {all -> 0x03d2, blocks: (B:81:0x03bd, B:83:0x03cb, B:86:0x03d4), top: B:108:0x03bd }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x03e1 A[Catch: IOException -> 0x03a9, TRY_ENTER, TryCatch #1 {IOException -> 0x03a9, blocks: (B:72:0x03a5, B:76:0x03ad, B:90:0x03e1, B:92:0x03e6), top: B:107:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x03e6 A[Catch: IOException -> 0x03a9, TRY_LEAVE, TryCatch #1 {IOException -> 0x03a9, blocks: (B:72:0x03a5, B:76:0x03ad, B:90:0x03e1, B:92:0x03e6), top: B:107:0x004a }] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v43 */
    /* JADX WARN: Type inference failed for: r3v64 */
    /* JADX WARN: Type inference failed for: r3v65 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v6 */
    @Override // java.util.concurrent.Callable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String[] call() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1034
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.q.call():java.lang.String[]");
    }
}
