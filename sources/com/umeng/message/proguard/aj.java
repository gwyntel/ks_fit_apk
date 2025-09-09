package com.umeng.message.proguard;

import android.app.Activity;
import android.content.Intent;
import com.umeng.message.api.UPushMessageNotifyApi;
import com.umeng.message.common.UPLog;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class aj implements UPushMessageNotifyApi {

    /* renamed from: c, reason: collision with root package name */
    private static volatile aj f22759c;

    /* renamed from: a, reason: collision with root package name */
    public final ak f22760a = new ak();

    /* renamed from: b, reason: collision with root package name */
    public volatile boolean f22761b;

    private aj() {
    }

    public static aj a() {
        if (f22759c == null) {
            synchronized (aj.class) {
                try {
                    if (f22759c == null) {
                        f22759c = new aj();
                    }
                } finally {
                }
            }
        }
        return f22759c;
    }

    public final void b() {
        if (this.f22761b) {
            return;
        }
        this.f22761b = true;
        this.f22760a.a();
    }

    @Override // com.umeng.message.api.UPushMessageNotifyApi
    public boolean isEnabled() {
        return this.f22760a.f22763b.a();
    }

    @Override // com.umeng.message.api.UPushMessageNotifyApi
    public void setCallback(UPushMessageNotifyApi.Callback callback) {
        ak akVar = this.f22760a;
        akVar.f22762a = callback;
        if (callback == null || !akVar.f22764c) {
            return;
        }
        try {
            callback.onNotified();
            akVar.f22764c = false;
        } catch (Throwable unused) {
        }
    }

    @Override // com.umeng.message.api.UPushMessageNotifyApi
    public void setEnable(final boolean z2) {
        final ak akVar = this.f22760a;
        if (akVar.f22763b.a() != z2) {
            akVar.f22763b.f22774a.a("e_u", z2);
            b.b(new Runnable() { // from class: com.umeng.message.proguard.ak.4
                @Override // java.lang.Runnable
                public final void run() {
                    ak.this.f22763b.b(true);
                    boolean zA = false;
                    try {
                        if (d.h(x.a())) {
                            zA = ak.a(z2);
                        }
                    } catch (Throwable th) {
                        UPLog.e("Notify", th);
                    }
                    ak.this.f22763b.b(!zA);
                }
            });
        }
    }

    public final void a(JSONObject jSONObject) {
        this.f22760a.a(jSONObject);
    }

    public final void a(Activity activity, Intent intent) {
        this.f22760a.a(activity, intent);
    }
}
