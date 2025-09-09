package com.taobao.agoo.a.a;

import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.p;

/* loaded from: classes4.dex */
public class d extends b {
    public static final String JSON_CMD_DISABLEPUSH = "disablePush";
    public static final String JSON_CMD_ENABLEPUSH = "enablePush";

    /* renamed from: a, reason: collision with root package name */
    public String f20444a;

    /* renamed from: b, reason: collision with root package name */
    public String f20445b;

    /* renamed from: c, reason: collision with root package name */
    public String f20446c;

    public byte[] a() {
        try {
            p.a aVar = new p.a();
            aVar.a(b.JSON_CMD, this.f20428e).a("appKey", this.f20444a);
            if (TextUtils.isEmpty(this.f20445b)) {
                aVar.a("utdid", this.f20446c);
            } else {
                aVar.a("deviceId", this.f20445b);
            }
            String string = aVar.a().toString();
            ALog.i("SwitchDO", "buildData", "data", string);
            return string.getBytes("utf-8");
        } catch (Throwable th) {
            ALog.e("SwitchDO", "buildData", th, new Object[0]);
            return null;
        }
    }

    public static byte[] a(String str, String str2, String str3, boolean z2) {
        d dVar = new d();
        dVar.f20444a = str;
        dVar.f20445b = str2;
        dVar.f20446c = str3;
        if (z2) {
            dVar.f20428e = JSON_CMD_ENABLEPUSH;
        } else {
            dVar.f20428e = JSON_CMD_DISABLEPUSH;
        }
        return dVar.a();
    }
}
