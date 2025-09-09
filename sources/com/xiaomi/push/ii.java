package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.service.XMPushService;
import java.io.File;

/* loaded from: classes4.dex */
public class ii implements XMPushService.n {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f23985a = false;

    /* renamed from: a, reason: collision with other field name */
    private int f577a;

    /* renamed from: a, reason: collision with other field name */
    private Context f578a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f23986b;

    public ii(Context context) {
        this.f578a = context;
    }

    @Override // com.xiaomi.push.service.XMPushService.n
    /* renamed from: a, reason: collision with other method in class */
    public void mo511a() {
        a(this.f578a);
        if (this.f23986b && a()) {
            com.xiaomi.channel.commonutils.logger.b.m91a("TinyData TinyDataCacheProcessor.pingFollowUpAction ts:" + System.currentTimeMillis());
            im imVarA = il.a(this.f578a).a();
            if (a(imVarA)) {
                f23985a = true;
                ij.a(this.f578a, imVarA);
            } else {
                com.xiaomi.channel.commonutils.logger.b.m91a("TinyData TinyDataCacheProcessor.pingFollowUpAction !canUpload(uploader) ts:" + System.currentTimeMillis());
            }
        }
    }

    private void a(Context context) {
        this.f23986b = com.xiaomi.push.service.az.a(context).a(is.TinyDataUploadSwitch.a(), true);
        int iA = com.xiaomi.push.service.az.a(context).a(is.TinyDataUploadFrequency.a(), 7200);
        this.f577a = iA;
        this.f577a = Math.max(60, iA);
    }

    private boolean a() {
        return Math.abs((System.currentTimeMillis() / 1000) - this.f578a.getSharedPreferences("mipush_extra", 4).getLong("last_tiny_data_upload_timestamp", -1L)) > ((long) this.f577a);
    }

    private boolean a(im imVar) {
        if (!bg.b(this.f578a) || imVar == null || TextUtils.isEmpty(a(this.f578a.getPackageName())) || !new File(this.f578a.getFilesDir(), "tiny_data.data").exists() || f23985a) {
            return false;
        }
        return !com.xiaomi.push.service.az.a(this.f578a).a(is.ScreenOnOrChargingTinyDataUploadSwitch.a(), false) || i.m497a(this.f578a) || i.m500b(this.f578a);
    }

    private String a(String str) {
        if ("com.xiaomi.xmsf".equals(str)) {
            return "1000271";
        }
        return this.f578a.getSharedPreferences("pref_registered_pkg_names", 0).getString(str, null);
    }

    public static void a(boolean z2) {
        f23985a = z2;
    }
}
