package com.tencent.bugly.proguard;

import android.content.Context;
import android.util.Log;
import com.tencent.bugly.BuglyStrategy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class p {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f21110a = true;

    /* renamed from: b, reason: collision with root package name */
    public static List<o> f21111b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    public static boolean f21112c;

    /* renamed from: d, reason: collision with root package name */
    private static w f21113d;

    /* renamed from: e, reason: collision with root package name */
    private static boolean f21114e;

    private static boolean a(aa aaVar) {
        List<String> list = aaVar.f20697v;
        return list != null && list.contains("bugly");
    }

    public static synchronized void a(Context context) {
        a(context, null);
    }

    public static synchronized void a(Context context, BuglyStrategy buglyStrategy) {
        if (f21114e) {
            al.d("[init] initial Multi-times, ignore this.", new Object[0]);
            return;
        }
        if (context == null) {
            Log.w(al.f20774b, "[init] context of init() is null, check it.");
            return;
        }
        aa aaVarA = aa.a(context);
        if (a(aaVarA)) {
            f21110a = false;
            return;
        }
        String strE = aaVarA.e();
        if (strE == null) {
            Log.e(al.f20774b, "[init] meta data of BUGLY_APPID in AndroidManifest.xml should be set.");
        } else {
            a(context, strE, aaVarA.D, buglyStrategy);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:85:0x0228 A[Catch: all -> 0x001a, TryCatch #2 {, blocks: (B:4:0x000d, B:6:0x0011, B:12:0x001f, B:16:0x002a, B:19:0x0033, B:21:0x0037, B:22:0x0073, B:24:0x00b4, B:27:0x00b8, B:29:0x00ca, B:31:0x00d6, B:33:0x00dc, B:34:0x00f2, B:35:0x0101, B:37:0x0109, B:39:0x0113, B:41:0x0119, B:44:0x0132, B:50:0x015a, B:55:0x016d, B:57:0x0177, B:59:0x017f, B:60:0x0195, B:61:0x01a4, B:63:0x01aa, B:65:0x01b2, B:66:0x01c8, B:67:0x01d4, B:69:0x01da, B:70:0x01e6, B:45:0x0140, B:47:0x0149, B:49:0x0153, B:52:0x0166, B:54:0x016a, B:72:0x01f3, B:82:0x0221, B:83:0x0223, B:85:0x0228, B:87:0x022f, B:79:0x0218, B:81:0x021e, B:74:0x01fb, B:76:0x020b), top: B:96:0x000d, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01fb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void a(android.content.Context r16, java.lang.String r17, boolean r18, com.tencent.bugly.BuglyStrategy r19) {
        /*
            Method dump skipped, instructions count: 584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.p.a(android.content.Context, java.lang.String, boolean, com.tencent.bugly.BuglyStrategy):void");
    }

    public static synchronized void a(o oVar) {
        if (!f21111b.contains(oVar)) {
            f21111b.add(oVar);
        }
    }
}
