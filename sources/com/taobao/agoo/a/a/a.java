package com.taobao.agoo.a.a;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.p;

/* loaded from: classes4.dex */
public class a extends b {
    public static final String JSON_CMD_REMOVEALIAS = "removeAlias";
    public static final String JSON_CMD_REMOVEALLALIAS = "unbindAllAlias";
    public static final String JSON_CMD_SETALIAS = "setAlias";
    public static final String JSON_PUSH_USER_TOKEN = "pushAliasToken";

    /* renamed from: a, reason: collision with root package name */
    public String f20424a;

    /* renamed from: b, reason: collision with root package name */
    public String f20425b;

    /* renamed from: c, reason: collision with root package name */
    public String f20426c;

    /* renamed from: d, reason: collision with root package name */
    public String f20427d;

    public static byte[] b(String str, String str2, String str3) {
        a aVar = new a();
        aVar.f20424a = str;
        aVar.f20425b = str2;
        aVar.f20427d = str3;
        aVar.f20428e = JSON_CMD_REMOVEALIAS;
        return aVar.a();
    }

    public static byte[] c(String str, String str2, String str3) {
        a aVar = new a();
        aVar.f20424a = str;
        aVar.f20425b = str2;
        aVar.f20426c = str3;
        aVar.f20428e = JSON_CMD_REMOVEALIAS;
        return aVar.a();
    }

    public byte[] a() {
        try {
            String string = new p.a().a(b.JSON_CMD, this.f20428e).a("appKey", this.f20424a).a("deviceId", this.f20425b).a(PushConstants.SUB_ALIAS_STATUS_NAME, this.f20426c).a(JSON_PUSH_USER_TOKEN, this.f20427d).a().toString();
            ALog.i("AliasDO", "buildData", "data", string);
            return string.getBytes("utf-8");
        } catch (Throwable th) {
            ALog.e("AliasDO", "buildData", th, new Object[0]);
            return null;
        }
    }

    public static byte[] a(String str, String str2, String str3) {
        a aVar = new a();
        aVar.f20424a = str;
        aVar.f20425b = str2;
        aVar.f20426c = str3;
        aVar.f20428e = JSON_CMD_SETALIAS;
        return aVar.a();
    }

    public static byte[] a(String str, String str2) {
        a aVar = new a();
        aVar.f20424a = str;
        aVar.f20425b = str2;
        aVar.f20428e = JSON_CMD_REMOVEALLALIAS;
        return aVar.a();
    }
}
