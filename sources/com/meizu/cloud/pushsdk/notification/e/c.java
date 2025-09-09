package com.meizu.cloud.pushsdk.notification.e;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;

/* loaded from: classes4.dex */
public class c extends com.meizu.cloud.pushsdk.notification.a {
    public c(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    protected void b(Notification.Builder builder, MessageV3 messageV3) throws PackageManager.NameNotFoundException {
        AppIconSetting appIconSetting;
        Bitmap bitmapA;
        String str;
        if ((!MzSystemUtils.isInternational() || MzSystemUtils.isMeizuAndFlyme()) && (appIconSetting = messageV3.getAppIconSetting()) != null) {
            if (appIconSetting.isDefaultLargeIcon()) {
                PushNotificationBuilder pushNotificationBuilder = this.f19752b;
                if (pushNotificationBuilder == null || pushNotificationBuilder.getLargeIcon() == 0) {
                    PushNotificationBuilder pushNotificationBuilder2 = this.f19752b;
                    if (pushNotificationBuilder2 == null || pushNotificationBuilder2.getAppLargeIcon() == null) {
                        bitmapA = a(this.f19751a, messageV3.getUploadDataPackageName());
                        str = "set largeIcon by package default large icon";
                    } else {
                        bitmapA = this.f19752b.getAppLargeIcon();
                        str = "set largeIcon by bitmap provided by user setting";
                    }
                } else {
                    bitmapA = BitmapFactory.decodeResource(this.f19751a.getResources(), this.f19752b.getLargeIcon());
                    str = "set largeIcon by resource id";
                }
                DebugLogger.i("AbstractPushNotification", str);
            } else {
                if (Thread.currentThread() == this.f19751a.getMainLooper().getThread()) {
                    return;
                }
                Bitmap bitmapA2 = a(appIconSetting.getLargeIconUrl());
                if (bitmapA2 != null) {
                    DebugLogger.i("AbstractPushNotification", "On other Thread down load largeIcon image success");
                    builder.setLargeIcon(bitmapA2);
                    return;
                }
                bitmapA = a(this.f19751a, messageV3.getUploadDataPackageName());
            }
            builder.setLargeIcon(bitmapA);
        }
    }
}
