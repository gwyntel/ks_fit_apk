package com.umeng.message;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.umeng.message.api.UPushMessageHandler;
import com.umeng.message.common.UPLog;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.aw;
import java.util.Map;

/* loaded from: classes4.dex */
public class UmengNotificationClickHandler implements UPushMessageHandler {
    private static void a(Intent intent, UMessage uMessage) {
        if (intent == null || uMessage == null || uMessage.getExtra() == null) {
            return;
        }
        for (Map.Entry<String, String> entry : uMessage.getExtra().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key != null) {
                intent.putExtra(key, value);
            }
        }
    }

    public void changeBadgeNum(Context context, UMessage uMessage) {
        MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(context);
        if (TextUtils.equals(uMessage.getMsgId(), messageSharedPrefs.f())) {
            return;
        }
        messageSharedPrefs.c(uMessage.getMsgId());
        aw.b(context, -1);
    }

    public void dealWithCustomAction(Context context, UMessage uMessage) {
    }

    public void dismissNotification(Context context, UMessage uMessage) {
    }

    protected Intent getMainIntent(Context context) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntentForPackage == null) {
            return null;
        }
        launchIntentForPackage.setFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        return launchIntentForPackage;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0083 A[Catch: all -> 0x0008, TryCatch #0 {all -> 0x0008, blocks: (B:2:0x0000, B:4:0x0004, B:8:0x000b, B:10:0x0017, B:12:0x001f, B:35:0x0086, B:13:0x0023, B:15:0x002b, B:16:0x002f, B:18:0x0037, B:19:0x003b, B:21:0x0041, B:23:0x004f, B:24:0x0053, B:26:0x0059, B:28:0x0067, B:29:0x006b, B:31:0x0071, B:33:0x007f, B:34:0x0083), top: B:39:0x0000 }] */
    @Override // com.umeng.message.api.UPushMessageHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleMessage(android.content.Context r3, com.umeng.message.entity.UMessage r4) {
        /*
            r2 = this;
            boolean r0 = r4.dismiss     // Catch: java.lang.Throwable -> L8
            if (r0 == 0) goto Lb
            r2.dismissNotification(r3, r4)     // Catch: java.lang.Throwable -> L8
            return
        L8:
            r3 = move-exception
            goto L8a
        Lb:
            java.lang.String r0 = r4.getAction()     // Catch: java.lang.Throwable -> L8
            java.lang.String r1 = "go_app"
            boolean r1 = android.text.TextUtils.equals(r1, r0)     // Catch: java.lang.Throwable -> L8
            if (r1 != 0) goto L83
            java.lang.String r1 = "go_url"
            boolean r1 = android.text.TextUtils.equals(r1, r0)     // Catch: java.lang.Throwable -> L8
            if (r1 == 0) goto L23
            r2.openUrl(r3, r4)     // Catch: java.lang.Throwable -> L8
            goto L86
        L23:
            java.lang.String r1 = "go_activity"
            boolean r1 = android.text.TextUtils.equals(r1, r0)     // Catch: java.lang.Throwable -> L8
            if (r1 == 0) goto L2f
            r2.openActivity(r3, r4)     // Catch: java.lang.Throwable -> L8
            goto L86
        L2f:
            java.lang.String r1 = "go_custom"
            boolean r0 = android.text.TextUtils.equals(r1, r0)     // Catch: java.lang.Throwable -> L8
            if (r0 == 0) goto L3b
            r2.dealWithCustomAction(r3, r4)     // Catch: java.lang.Throwable -> L8
            goto L86
        L3b:
            java.lang.String r0 = r4.getDeeplink()     // Catch: java.lang.Throwable -> L8
            if (r0 == 0) goto L53
            java.lang.String r0 = r4.getDeeplink()     // Catch: java.lang.Throwable -> L8
            java.lang.String r0 = r0.trim()     // Catch: java.lang.Throwable -> L8
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L8
            if (r0 != 0) goto L53
            r2.openUrl(r3, r4)     // Catch: java.lang.Throwable -> L8
            goto L86
        L53:
            java.lang.String r0 = r4.getActivity()     // Catch: java.lang.Throwable -> L8
            if (r0 == 0) goto L6b
            java.lang.String r0 = r4.getActivity()     // Catch: java.lang.Throwable -> L8
            java.lang.String r0 = r0.trim()     // Catch: java.lang.Throwable -> L8
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L8
            if (r0 != 0) goto L6b
            r2.openActivity(r3, r4)     // Catch: java.lang.Throwable -> L8
            goto L86
        L6b:
            java.lang.String r0 = r4.getCustom()     // Catch: java.lang.Throwable -> L8
            if (r0 == 0) goto L83
            java.lang.String r0 = r4.getContent()     // Catch: java.lang.Throwable -> L8
            java.lang.String r0 = r0.trim()     // Catch: java.lang.Throwable -> L8
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L8
            if (r0 != 0) goto L83
            r2.dealWithCustomAction(r3, r4)     // Catch: java.lang.Throwable -> L8
            goto L86
        L83:
            r2.launchApp(r3, r4)     // Catch: java.lang.Throwable -> L8
        L86:
            r2.changeBadgeNum(r3, r4)     // Catch: java.lang.Throwable -> L8
            return
        L8a:
            java.lang.String r4 = "UmengNotificationClickHandler"
            com.umeng.message.common.UPLog.e(r4, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.UmengNotificationClickHandler.handleMessage(android.content.Context, com.umeng.message.entity.UMessage):void");
    }

    public void launchApp(Context context, UMessage uMessage) {
        try {
            Intent mainIntent = getMainIntent(context);
            if (mainIntent == null) {
                UPLog.e("UmengNotificationClickHandler", "can't find launchIntent:", context.getPackageName());
                return;
            }
            mainIntent.addFlags(268435456);
            a(mainIntent, uMessage);
            UPLog.d("UmengNotificationClickHandler", "open app:", context.getPackageName());
            context.startActivity(mainIntent);
        } catch (Throwable th) {
            UPLog.e("UmengNotificationClickHandler", th);
        }
    }

    public void openActivity(Context context, UMessage uMessage) {
        try {
            String activity = uMessage.getActivity();
            if (activity == null) {
                return;
            }
            String strTrim = activity.trim();
            if (TextUtils.isEmpty(strTrim)) {
                return;
            }
            UPLog.d("UmengNotificationClickHandler", "open activity:", strTrim);
            Intent intent = new Intent();
            a(intent, uMessage);
            intent.setClassName(context, strTrim);
            intent.addFlags(268435456);
            context.startActivity(intent);
        } catch (Throwable th) {
            UPLog.e("UmengNotificationClickHandler", th);
        }
    }

    public void openUrl(Context context, UMessage uMessage) {
        try {
            String deeplink = uMessage.getDeeplink();
            if (deeplink == null) {
                return;
            }
            String strTrim = deeplink.trim();
            if (TextUtils.isEmpty(strTrim)) {
                return;
            }
            UPLog.d("UmengNotificationClickHandler", "open deeplink:".concat(String.valueOf(strTrim)));
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(strTrim));
            a(intent, uMessage);
            intent.addFlags(268435456);
            context.startActivity(intent);
        } catch (Throwable th) {
            UPLog.e("UmengNotificationClickHandler", th);
        }
    }
}
