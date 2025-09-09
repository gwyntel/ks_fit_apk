package com.taobao.agoo.a.a;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.j;
import com.taobao.accs.utl.o;
import com.taobao.accs.utl.p;

/* loaded from: classes4.dex */
public class c extends b {
    public static final String JSON_CMD_REGISTER = "register";

    /* renamed from: a, reason: collision with root package name */
    public String f20429a;

    /* renamed from: b, reason: collision with root package name */
    public String f20430b;

    /* renamed from: c, reason: collision with root package name */
    public String f20431c;

    /* renamed from: d, reason: collision with root package name */
    public String f20432d = String.valueOf(221);

    /* renamed from: f, reason: collision with root package name */
    public String f20433f;

    /* renamed from: g, reason: collision with root package name */
    public String f20434g;

    /* renamed from: h, reason: collision with root package name */
    public String f20435h;

    /* renamed from: i, reason: collision with root package name */
    public String f20436i;

    /* renamed from: j, reason: collision with root package name */
    public String f20437j;

    /* renamed from: k, reason: collision with root package name */
    public String f20438k;

    /* renamed from: l, reason: collision with root package name */
    public String f20439l;

    /* renamed from: m, reason: collision with root package name */
    public String f20440m;

    /* renamed from: n, reason: collision with root package name */
    public String f20441n;

    /* renamed from: o, reason: collision with root package name */
    public String f20442o;

    /* renamed from: p, reason: collision with root package name */
    public String f20443p;

    public byte[] a() {
        try {
            String string = new p.a().a(b.JSON_CMD, this.f20428e).a("appKey", this.f20429a).a("utdid", this.f20430b).a("appVersion", this.f20431c).a("sdkVersion", this.f20432d).a(Constants.KEY_TTID, this.f20433f).a("packageName", this.f20434g).a("notifyEnable", this.f20435h).a("romInfo", this.f20436i).a("c0", this.f20437j).a("c1", this.f20438k).a("c2", this.f20439l).a("c3", this.f20440m).a("c4", this.f20441n).a("c5", this.f20442o).a("c6", this.f20443p).a().toString();
            ALog.i("RegisterDO", "buildData", "data", string);
            return string.getBytes("utf-8");
        } catch (Throwable th) {
            ALog.e("RegisterDO", "buildData", th, new Object[0]);
            return null;
        }
    }

    public static byte[] a(Context context, String str, String str2) {
        c cVar;
        String strJ;
        String packageName;
        String str3;
        try {
            strJ = UtilityImpl.j(context);
            packageName = context.getPackageName();
            str3 = GlobalClientInfo.getInstance(context).getPackageInfo().versionName;
        } catch (Throwable th) {
            th = th;
            cVar = null;
        }
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(strJ) && !TextUtils.isEmpty(str3)) {
            cVar = new c();
            try {
                cVar.f20428e = "register";
                cVar.f20429a = str;
                cVar.f20430b = strJ;
                cVar.f20431c = str3;
                cVar.f20433f = str2;
                cVar.f20434g = packageName;
                cVar.f20437j = Build.BRAND;
                cVar.f20438k = Build.MODEL;
                String strC = j.c(context);
                cVar.f20435h = strC;
                UtilityImpl.a(context, Constants.SP_CHANNEL_FILE_NAME, strC);
                cVar.f20436i = new o().a();
            } catch (Throwable th2) {
                th = th2;
                try {
                    ALog.w("RegisterDO", "buildRegister", th.getMessage());
                    if (cVar == null) {
                        return null;
                    }
                    return cVar.a();
                } finally {
                    if (cVar != null) {
                        cVar.a();
                    }
                }
            }
            return cVar.a();
        }
        ALog.e("RegisterDO", "buildRegister param null", "appKey", str, "utdid", strJ, "appVersion", str3);
        return null;
    }
}
