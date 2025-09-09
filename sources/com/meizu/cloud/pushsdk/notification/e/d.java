package com.meizu.cloud.pushsdk.notification.e;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MessageV4;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.g.e;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.io.File;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class d extends c {

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f19755a;

        a(String str) {
            this.f19755a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            for (File file : com.meizu.cloud.pushsdk.notification.g.a.b(this.f19755a, String.valueOf(System.currentTimeMillis() - 86400000))) {
                com.meizu.cloud.pushsdk.notification.g.a.a(file.getPath());
                DebugLogger.i("AbstractPushNotification", "Delete file directory " + file.getName() + "\n");
            }
        }
    }

    public d(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    protected void a(Notification notification, MessageV3 messageV3) throws JSONException {
        super.a(notification, messageV3);
        MessageV4 messageV4 = MessageV4.parse(messageV3);
        if (messageV4.getActVideoSetting() == null) {
            DebugLogger.e("AbstractPushNotification", "only wifi can download act");
            return;
        }
        if (messageV4.getActVideoSetting().isWifiDisplay() && !com.meizu.cloud.pushsdk.util.a.b(this.f19751a)) {
            DebugLogger.e("AbstractPushNotification", "only wifi can download act");
            return;
        }
        String str = MzSystemUtils.getDocumentsPath(this.f19751a) + "/pushSdkAct/" + messageV3.getUploadDataPackageName();
        String strValueOf = String.valueOf(System.currentTimeMillis());
        String actUrl = messageV4.getActVideoSetting().getActUrl();
        if (!TextUtils.isEmpty(actUrl) && com.meizu.cloud.pushsdk.e.a.a(actUrl, str, strValueOf).a().b().c()) {
            DebugLogger.i("AbstractPushNotification", "down load " + actUrl + " success");
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            String str2 = File.separator;
            sb.append(str2);
            sb.append("ACT-");
            sb.append(strValueOf);
            String string = sb.toString();
            boolean zB = new e(str + str2 + strValueOf, string).b();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("zip file ");
            sb2.append(zB);
            DebugLogger.i("AbstractPushNotification", sb2.toString());
            if (zB) {
                Bundle bundle = new Bundle();
                bundle.putString("path", string);
                Bundle bundle2 = new Bundle();
                bundle2.putBundle("big", bundle);
                if (MinSdkChecker.isSupportVideoNotification()) {
                    notification.extras.putBundle("flyme.active", bundle2);
                }
            }
        }
        com.meizu.cloud.pushsdk.f.c.h.b.a(new a(str));
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    protected void c(Notification.Builder builder, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
            bigTextStyle.setBigContentTitle(messageV3.getTitle());
            bigTextStyle.bigText(messageV3.getNotificationStyle().getExpandableText());
            builder.setStyle(bigTextStyle);
        }
    }
}
