package com.vivo.push.d;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.heytap.mcssdk.constant.IntentConstant;
import com.vivo.push.cache.ClientConfigManagerImpl;
import com.vivo.push.model.InsideNotificationItem;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class r extends z {

    public interface a {
        void a();

        void b();
    }

    r(com.vivo.push.o oVar) {
        super(oVar);
    }

    @Override // com.vivo.push.l
    protected final void a(com.vivo.push.o oVar) throws PackageManager.NameNotFoundException {
        if (oVar == null) {
            com.vivo.push.util.p.a("OnNotificationArrivedTask", "command is null");
            return;
        }
        boolean zIsEnablePush = ClientConfigManagerImpl.getInstance(this.f23178a).isEnablePush();
        com.vivo.push.b.q qVar = (com.vivo.push.b.q) oVar;
        Context context = this.f23178a;
        if (!com.vivo.push.util.t.d(context, context.getPackageName())) {
            com.vivo.push.b.x xVar = new com.vivo.push.b.x(2101L);
            HashMap<String, String> map = new HashMap<>();
            map.put(IntentConstant.MESSAGE_ID, String.valueOf(qVar.f()));
            Context context2 = this.f23178a;
            String strB = com.vivo.push.util.z.b(context2, context2.getPackageName());
            if (!TextUtils.isEmpty(strB)) {
                map.put("remoteAppId", strB);
            }
            xVar.a(map);
            com.vivo.push.e.a().a(xVar);
            return;
        }
        com.vivo.push.e.a().a(new com.vivo.push.b.h(String.valueOf(qVar.f())));
        com.vivo.push.util.p.d("OnNotificationArrivedTask", "PushMessageReceiver " + this.f23178a.getPackageName() + " isEnablePush :" + zIsEnablePush);
        if (!zIsEnablePush) {
            com.vivo.push.b.x xVar2 = new com.vivo.push.b.x(1020L);
            HashMap<String, String> map2 = new HashMap<>();
            map2.put(IntentConstant.MESSAGE_ID, String.valueOf(qVar.f()));
            Context context3 = this.f23178a;
            String strB2 = com.vivo.push.util.z.b(context3, context3.getPackageName());
            if (!TextUtils.isEmpty(strB2)) {
                map2.put("remoteAppId", strB2);
            }
            xVar2.a(map2);
            com.vivo.push.e.a().a(xVar2);
            return;
        }
        if (com.vivo.push.e.a().g() && !a(com.vivo.push.util.z.c(this.f23178a), qVar.e(), qVar.i())) {
            com.vivo.push.b.x xVar3 = new com.vivo.push.b.x(1021L);
            HashMap<String, String> map3 = new HashMap<>();
            map3.put(IntentConstant.MESSAGE_ID, String.valueOf(qVar.f()));
            Context context4 = this.f23178a;
            String strB3 = com.vivo.push.util.z.b(context4, context4.getPackageName());
            if (!TextUtils.isEmpty(strB3)) {
                map3.put("remoteAppId", strB3);
            }
            xVar3.a(map3);
            com.vivo.push.e.a().a(xVar3);
            return;
        }
        InsideNotificationItem insideNotificationItemD = qVar.d();
        if (insideNotificationItemD == null) {
            com.vivo.push.util.p.a("OnNotificationArrivedTask", "notify is null");
            com.vivo.push.util.p.c(this.f23178a, "通知内容为空，" + qVar.f());
            com.vivo.push.util.e.a(this.f23178a, qVar.f(), 1027L);
            return;
        }
        com.vivo.push.util.p.d("OnNotificationArrivedTask", "tragetType is " + insideNotificationItemD.getTargetType() + " ; target is " + insideNotificationItemD.getTragetContent());
        com.vivo.push.m.c(new s(this, insideNotificationItemD, qVar));
    }
}
