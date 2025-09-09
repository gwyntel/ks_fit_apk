package com.meizu.cloud.pushsdk.notification.f;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;

/* loaded from: classes4.dex */
public class c extends com.meizu.cloud.pushsdk.notification.a {
    public c(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    protected void a(Notification notification, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            RemoteViews remoteViews = new RemoteViews(this.f19751a.getPackageName(), com.meizu.cloud.pushsdk.notification.g.c.g(this.f19751a));
            remoteViews.setTextViewText(com.meizu.cloud.pushsdk.notification.g.c.f(this.f19751a), messageV3.getTitle());
            remoteViews.setTextViewText(com.meizu.cloud.pushsdk.notification.g.c.c(this.f19751a), messageV3.getContent());
            remoteViews.setLong(com.meizu.cloud.pushsdk.notification.g.c.d(this.f19751a), "setTime", System.currentTimeMillis());
            a(remoteViews, messageV3);
            remoteViews.setViewVisibility(com.meizu.cloud.pushsdk.notification.g.c.b(this.f19751a), 8);
            remoteViews.setViewVisibility(com.meizu.cloud.pushsdk.notification.g.c.a(this.f19751a), 8);
            notification.contentView = remoteViews;
        }
    }

    protected void a(RemoteViews remoteViews, MessageV3 messageV3) {
        Bitmap bitmapA;
        if (messageV3.getAppIconSetting() == null || a() || messageV3.getAppIconSetting().isDefaultLargeIcon() || (bitmapA = a(messageV3.getAppIconSetting().getLargeIconUrl())) == null) {
            remoteViews.setImageViewBitmap(com.meizu.cloud.pushsdk.notification.g.c.e(this.f19751a), a(this.f19751a, messageV3.getUploadDataPackageName()));
        } else {
            remoteViews.setImageViewBitmap(com.meizu.cloud.pushsdk.notification.g.c.e(this.f19751a), bitmapA);
        }
    }
}
