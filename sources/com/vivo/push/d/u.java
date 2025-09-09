package com.vivo.push.d;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.heytap.mcssdk.constant.IntentConstant;
import com.vivo.push.model.InsideNotificationItem;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.util.NotifyAdapterUtil;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
final class u extends z {
    u(com.vivo.push.o oVar) {
        super(oVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Intent b(Intent intent, Map<String, String> map) {
        if (map != null && map.entrySet() != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry != null && entry.getKey() != null) {
                    intent.putExtra(entry.getKey(), entry.getValue());
                }
            }
        }
        return intent;
    }

    @Override // com.vivo.push.l
    protected final void a(com.vivo.push.o oVar) throws PackageManager.NameNotFoundException, URISyntaxException {
        Intent uri;
        String str;
        com.vivo.push.b.p pVar = (com.vivo.push.b.p) oVar;
        InsideNotificationItem insideNotificationItemF = pVar.f();
        if (insideNotificationItemF == null) {
            com.vivo.push.util.p.d("OnNotificationClickTask", "current notification item is null");
            return;
        }
        UPSNotificationMessage uPSNotificationMessageA = com.vivo.push.util.q.a(insideNotificationItemF);
        boolean zEquals = this.f23178a.getPackageName().equals(pVar.d());
        if (zEquals) {
            NotifyAdapterUtil.cancelNotify(this.f23178a);
        }
        if (zEquals) {
            com.vivo.push.b.x xVar = new com.vivo.push.b.x(1030L);
            HashMap<String, String> map = new HashMap<>();
            map.put("type", "2");
            map.put(IntentConstant.MESSAGE_ID, String.valueOf(pVar.e()));
            map.put(DispatchConstants.PLATFORM, this.f23178a.getPackageName());
            Context context = this.f23178a;
            String strB = com.vivo.push.util.z.b(context, context.getPackageName());
            if (!TextUtils.isEmpty(strB)) {
                map.put("remoteAppId", strB);
            }
            xVar.a(map);
            com.vivo.push.e.a().a(xVar);
            com.vivo.push.util.p.d("OnNotificationClickTask", "notification is clicked by skip type[" + uPSNotificationMessageA.getSkipType() + "]");
            int skipType = uPSNotificationMessageA.getSkipType();
            if (skipType == 1) {
                new Thread(new v(this, this.f23178a, uPSNotificationMessageA.getParams())).start();
                a(uPSNotificationMessageA);
                return;
            }
            if (skipType == 2) {
                String skipContent = uPSNotificationMessageA.getSkipContent();
                if (!skipContent.startsWith("http://") && !skipContent.startsWith("https://")) {
                    com.vivo.push.util.p.a("OnNotificationClickTask", "url not legal");
                } else {
                    Uri uri2 = Uri.parse(skipContent);
                    Intent intent = new Intent("android.intent.action.VIEW", uri2);
                    intent.setFlags(268435456);
                    b(intent, uPSNotificationMessageA.getParams());
                    try {
                        this.f23178a.startActivity(intent);
                    } catch (Exception unused) {
                        com.vivo.push.util.p.a("OnNotificationClickTask", "startActivity error : ".concat(String.valueOf(uri2)));
                    }
                }
                a(uPSNotificationMessageA);
                return;
            }
            if (skipType == 3) {
                a(uPSNotificationMessageA);
                return;
            }
            if (skipType != 4) {
                com.vivo.push.util.p.a("OnNotificationClickTask", "illegitmacy skip type error : " + uPSNotificationMessageA.getSkipType());
                return;
            }
            String skipContent2 = uPSNotificationMessageA.getSkipContent();
            try {
                uri = Intent.parseUri(skipContent2, 1);
                str = uri.getPackage();
            } catch (Exception e2) {
                com.vivo.push.util.p.a("OnNotificationClickTask", "open activity error : ".concat(String.valueOf(skipContent2)), e2);
            }
            if (!TextUtils.isEmpty(str) && !this.f23178a.getPackageName().equals(str)) {
                com.vivo.push.util.p.a("OnNotificationClickTask", "open activity error : local pkgName is " + this.f23178a.getPackageName() + "; but remote pkgName is " + uri.getPackage());
                return;
            }
            String packageName = uri.getComponent() == null ? null : uri.getComponent().getPackageName();
            if (!TextUtils.isEmpty(packageName) && !this.f23178a.getPackageName().equals(packageName)) {
                com.vivo.push.util.p.a("OnNotificationClickTask", "open activity component error : local pkgName is " + this.f23178a.getPackageName() + "; but remote pkgName is " + uri.getPackage());
                return;
            }
            uri.setSelector(null);
            uri.setPackage(this.f23178a.getPackageName());
            uri.addFlags(335544320);
            b(uri, uPSNotificationMessageA.getParams());
            ActivityInfo activityInfoResolveActivityInfo = uri.resolveActivityInfo(this.f23178a.getPackageManager(), 65536);
            if (activityInfoResolveActivityInfo != null && !activityInfoResolveActivityInfo.exported) {
                com.vivo.push.util.p.a("OnNotificationClickTask", "activity is not exported : " + activityInfoResolveActivityInfo.toString());
                return;
            } else {
                this.f23178a.startActivity(uri);
                a(uPSNotificationMessageA);
                return;
            }
        }
        com.vivo.push.util.p.a("OnNotificationClickTask", "notify is " + uPSNotificationMessageA + " ; isMatch is " + zEquals);
    }

    private void a(UPSNotificationMessage uPSNotificationMessage) {
        com.vivo.push.m.c(new w(this, uPSNotificationMessage));
    }
}
