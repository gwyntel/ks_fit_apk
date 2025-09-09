package com.xiaomi.mipush.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.HashMap;

/* loaded from: classes4.dex */
class ap extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ao f23384a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ap(ao aoVar, Looper looper) {
        super(looper);
        this.f23384a = aoVar;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        if (message.what != 19) {
            return;
        }
        String str = (String) message.obj;
        int i2 = message.arg1;
        synchronized (af.class) {
            try {
                if (af.a(this.f23384a.f124a).m118a(str)) {
                    if (af.a(this.f23384a.f124a).a(str) < 10) {
                        String string = message.getData() != null ? message.getData().getString("third_sync_reason") : "";
                        au auVar = au.DISABLE_PUSH;
                        if (auVar.ordinal() == i2 && "syncing".equals(af.a(this.f23384a.f124a).a(auVar))) {
                            this.f23384a.a(str, auVar, true, (HashMap<String, String>) null);
                        } else {
                            au auVar2 = au.ENABLE_PUSH;
                            if (auVar2.ordinal() == i2 && "syncing".equals(af.a(this.f23384a.f124a).a(auVar2))) {
                                this.f23384a.a(str, auVar2, true, (HashMap<String, String>) null);
                            } else {
                                au auVar3 = au.UPLOAD_HUAWEI_TOKEN;
                                if (auVar3.ordinal() == i2 && "syncing".equals(af.a(this.f23384a.f124a).a(auVar3))) {
                                    HashMap<String, String> mapM159a = i.m159a(this.f23384a.f124a, e.ASSEMBLE_PUSH_HUAWEI);
                                    mapM159a.put("third_sync_reason", string);
                                    this.f23384a.a(str, auVar3, false, (HashMap<String, String>) mapM159a);
                                } else {
                                    au auVar4 = au.UPLOAD_FCM_TOKEN;
                                    if (auVar4.ordinal() == i2 && "syncing".equals(af.a(this.f23384a.f124a).a(auVar4))) {
                                        ao aoVar = this.f23384a;
                                        aoVar.a(str, auVar4, false, (HashMap<String, String>) i.m159a(aoVar.f124a, e.ASSEMBLE_PUSH_FCM));
                                    } else {
                                        au auVar5 = au.UPLOAD_COS_TOKEN;
                                        if (auVar5.ordinal() == i2 && "syncing".equals(af.a(this.f23384a.f124a).a(auVar5))) {
                                            HashMap<String, String> mapM159a2 = i.m159a(this.f23384a.f124a, e.ASSEMBLE_PUSH_COS);
                                            mapM159a2.put("third_sync_reason", string);
                                            this.f23384a.a(str, auVar5, false, (HashMap<String, String>) mapM159a2);
                                        } else {
                                            au auVar6 = au.UPLOAD_FTOS_TOKEN;
                                            if (auVar6.ordinal() == i2 && "syncing".equals(af.a(this.f23384a.f124a).a(auVar6))) {
                                                HashMap<String, String> mapM159a3 = i.m159a(this.f23384a.f124a, e.ASSEMBLE_PUSH_FTOS);
                                                mapM159a3.put("third_sync_reason", string);
                                                this.f23384a.a(str, auVar6, false, (HashMap<String, String>) mapM159a3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        af.a(this.f23384a.f124a).b(str);
                    } else {
                        af.a(this.f23384a.f124a).c(str);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
