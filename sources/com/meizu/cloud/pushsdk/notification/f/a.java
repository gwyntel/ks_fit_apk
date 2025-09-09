package com.meizu.cloud.pushsdk.notification.f;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;

/* loaded from: classes4.dex */
public class a extends c {
    public a(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    private void b(RemoteViews remoteViews, MessageV3 messageV3) {
        Bitmap bitmapA;
        if (messageV3.getNotificationStyle() == null || a()) {
            return;
        }
        if (TextUtils.isEmpty(messageV3.getNotificationStyle().getExpandableImageUrl()) || (bitmapA = a(messageV3.getNotificationStyle().getExpandableImageUrl())) == null) {
            remoteViews.setViewVisibility(com.meizu.cloud.pushsdk.notification.g.c.b(this.f19751a), 8);
        } else {
            remoteViews.setViewVisibility(com.meizu.cloud.pushsdk.notification.g.c.b(this.f19751a), 0);
            remoteViews.setImageViewBitmap(com.meizu.cloud.pushsdk.notification.g.c.b(this.f19751a), bitmapA);
        }
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    protected void a(Notification notification, MessageV3 messageV3, PendingIntent pendingIntent) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            RemoteViews remoteViews = new RemoteViews(this.f19751a.getPackageName(), com.meizu.cloud.pushsdk.notification.g.c.g(this.f19751a));
            remoteViews.setTextViewText(com.meizu.cloud.pushsdk.notification.g.c.f(this.f19751a), messageV3.getTitle());
            remoteViews.setTextViewText(com.meizu.cloud.pushsdk.notification.g.c.c(this.f19751a), messageV3.getContent());
            remoteViews.setLong(com.meizu.cloud.pushsdk.notification.g.c.d(this.f19751a), "setTime", System.currentTimeMillis());
            a(remoteViews, messageV3);
            b(remoteViews, messageV3);
            notification.bigContentView = remoteViews;
        }
    }
}
