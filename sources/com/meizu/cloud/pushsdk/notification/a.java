package com.meizu.cloud.pushsdk.notification;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSettingEx;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.umeng.message.common.inter.ITagManager;
import java.util.Objects;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class a implements c {

    /* renamed from: a, reason: collision with root package name */
    protected final Context f19751a;

    /* renamed from: b, reason: collision with root package name */
    protected final PushNotificationBuilder f19752b;

    /* renamed from: c, reason: collision with root package name */
    private final NotificationManager f19753c;

    /* renamed from: d, reason: collision with root package name */
    private final Handler f19754d;

    protected a(Context context, PushNotificationBuilder pushNotificationBuilder) {
        this.f19752b = pushNotificationBuilder;
        this.f19751a = context;
        this.f19754d = new Handler(context.getMainLooper());
        this.f19753c = (NotificationManager) context.getSystemService("notification");
    }

    private Notification a(MessageV3 messageV3, PendingIntent pendingIntent, PendingIntent pendingIntent2, PendingIntent pendingIntent3) throws IllegalAccessException, PackageManager.NameNotFoundException, IllegalArgumentException {
        Notification.Builder builder = new Notification.Builder(this.f19751a);
        a(builder, messageV3, pendingIntent, pendingIntent2);
        a(builder, messageV3);
        b(builder, messageV3);
        c(builder, messageV3);
        d(builder, messageV3);
        Notification notificationBuild = MinSdkChecker.isSupportNotificationBuild() ? builder.build() : builder.getNotification();
        b(notificationBuild, messageV3);
        a(notificationBuild, messageV3);
        a(notificationBuild, messageV3, pendingIntent3);
        return notificationBuild;
    }

    @TargetApi(23)
    private Icon b(String str) {
        try {
            int identifier = this.f19751a.getPackageManager().getResourcesForApplication(str).getIdentifier(PushConstants.MZ_PUSH_NOTIFICATION_SMALL_ICON, "drawable", str);
            if (identifier != 0) {
                DebugLogger.i("AbstractPushNotification", "get " + str + " smallIcon success resId " + identifier);
                return Icon.createWithResource(str, identifier);
            }
        } catch (Exception e2) {
            DebugLogger.e("AbstractPushNotification", "cannot load smallIcon form package " + str + " Error message " + e2.getMessage());
        }
        return null;
    }

    private PendingIntent d(MessageV3 messageV3) {
        return a(messageV3, c(messageV3) ? messageV3.getPackageName() : messageV3.getUploadDataPackageName());
    }

    private PendingIntent e(MessageV3 messageV3) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_CLOSE);
        intent.setClassName(messageV3.getPackageName(), MzSystemUtils.findReceiver(this.f19751a, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getPackageName()));
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.f19751a, 0, intent, 1140850688);
    }

    private PendingIntent f(MessageV3 messageV3) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_DELETE);
        intent.setClassName(messageV3.getPackageName(), MzSystemUtils.findReceiver(this.f19751a, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getPackageName()));
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.f19751a, 0, intent, 1140850688);
    }

    private PendingIntent g(MessageV3 messageV3) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_NOTIFICATION_STATE_MESSAGE, messageV3.getNotificationMessage());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_TASK_ID, messageV3.getTaskId());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_SEQ_ID, messageV3.getSeqId());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_DEVICE_ID, messageV3.getDeviceId());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_PUSH_TIMESTAMP, messageV3.getPushTimestamp());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_SHOW_PACKAGE_NAME, messageV3.getUploadDataPackageName());
        intent.putExtra(PushConstants.MZ_PUSH_WHITE_LIST, messageV3.getWhiteList());
        intent.putExtra(PushConstants.MZ_PUSH_DELAYED_REPORT_MILLIS, messageV3.getDelayedReportMillis());
        intent.putExtra("method", "notification_state");
        intent.setClassName(messageV3.getPackageName(), MzSystemUtils.findReceiver(this.f19751a, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getPackageName()));
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.f19751a, 0, intent, 1107296256);
    }

    protected void c(Notification.Builder builder, MessageV3 messageV3) {
    }

    protected String h(MessageV3 messageV3) {
        try {
        } catch (Exception e2) {
            DebugLogger.e("AbstractPushNotification", "parse flyme notification setting error " + e2.getMessage());
        }
        String string = !TextUtils.isEmpty(messageV3.getNotificationMessage()) ? new JSONObject(messageV3.getNotificationMessage()).getJSONObject("data").getJSONObject(PushConstants.EXTRA).getString("fns") : null;
        DebugLogger.i("AbstractPushNotification", "current FlymeGreen notification setting is " + string);
        return string;
    }

    private PendingIntent a(MessageV3 messageV3, String str) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        if (MinSdkChecker.isSupportTransmitMessageValue(this.f19751a, str)) {
            intent.putExtra(PushConstants.MZ_MESSAGE_VALUE, com.meizu.cloud.pushsdk.handler.d.a(messageV3));
        } else {
            intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        }
        intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE);
        intent.putExtra("package_name", str);
        String strFindReceiver = MzSystemUtils.findReceiver(this.f19751a, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, str);
        Objects.requireNonNull(strFindReceiver);
        intent.setClassName(str, strFindReceiver);
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        intent.setFlags(32);
        return PendingIntent.getBroadcast(this.f19751a, 0, intent, 1140850688);
    }

    private String b(Context context, String str) throws PackageManager.NameNotFoundException {
        CharSequence applicationLabel;
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            if (applicationInfo != null && (applicationLabel = packageManager.getApplicationLabel(applicationInfo)) != null) {
                return (String) applicationLabel;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            DebugLogger.e("AbstractPushNotification", "can not find " + str + " application info");
        }
        return null;
    }

    private boolean c(MessageV3 messageV3) {
        if (messageV3.getAdvertisementOption() == null || TextUtils.isEmpty(messageV3.getAdvertisementOption().getAdPackage())) {
            return messageV3.getWhiteList() && !MzSystemUtils.isExistReceiver(this.f19751a, messageV3.getUploadDataPackageName(), PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        }
        return true;
    }

    private void d(Notification.Builder builder, MessageV3 messageV3) {
        String str;
        String str2;
        int i2;
        if (MinSdkChecker.isSupportNotificationChannel()) {
            AdvanceSetting advanceSetting = messageV3.getAdvanceSetting();
            AdvanceSettingEx advanceSettingEx = messageV3.getAdvanceSettingEx();
            int priorityDisplay = advanceSettingEx != null ? advanceSettingEx.getPriorityDisplay() : 0;
            if (Build.VERSION.SDK_INT >= 29 && advanceSetting.isHeadUpNotification() && advanceSettingEx.getPriorityDisplay() < 1) {
                priorityDisplay = 1;
            }
            if (priorityDisplay != -2) {
                i2 = 2;
                if (priorityDisplay == -1) {
                    str = "mz_push_notification_channel_low";
                    str2 = "MEIZUPUSHLOW";
                } else if (priorityDisplay == 1) {
                    str = "mz_push_notification_channel_high";
                    str2 = "MEIZUPUSHHIGH";
                    i2 = 4;
                } else if (priorityDisplay != 2) {
                    str = "mz_push_notification_channel";
                    str2 = "MEIZUPUSH";
                    i2 = 3;
                } else {
                    str = "mz_push_notification_channel_max";
                    str2 = "MEIZUPUSHMAX";
                    i2 = 5;
                }
            } else {
                str = "mz_push_notification_channel_min";
                str2 = "MEIZUPUSHMIN";
                i2 = 1;
            }
            Uri uriB = advanceSettingEx.getSoundTitle() != null ? com.meizu.cloud.pushsdk.notification.g.b.b(this.f19751a, advanceSettingEx.getSoundTitle()) : null;
            if (!advanceSetting.getNotifyType().isSound() && advanceSettingEx.getSoundTitle() == null) {
                str = str + "_mute";
                str2 = str2 + "_MUTE";
            } else if (uriB != null) {
                String str3 = str + OpenAccountUIConstants.UNDER_LINE + advanceSettingEx.getSoundTitle().toLowerCase();
                str2 = str2 + OpenAccountUIConstants.UNDER_LINE + advanceSettingEx.getSoundTitle().toUpperCase();
                str = str3;
            }
            DebugLogger.e("AbstractPushNotification", "notification channel builder, channelId: " + str + ", channelName: " + str2 + ", importance:" + i2 + ", sound: " + uriB);
            NotificationChannel notificationChannelA = androidx.browser.trusted.g.a(str, str2, i2);
            notificationChannelA.enableLights(true);
            notificationChannelA.setLightColor(-16711936);
            notificationChannelA.setShowBadge(true);
            notificationChannelA.enableVibration(true);
            if (!advanceSetting.getNotifyType().isSound() && advanceSettingEx.getSoundTitle() == null) {
                notificationChannelA.setSound(null, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            } else if (uriB != null) {
                notificationChannelA.setSound(uriB, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            }
            this.f19753c.createNotificationChannel(notificationChannelA);
            builder.setChannelId(str);
        }
    }

    private String b(MessageV3 messageV3) {
        if (messageV3 == null || messageV3.getAdvertisementOption() == null || TextUtils.isEmpty(messageV3.getAdvertisementOption().getAdPackage())) {
            return null;
        }
        String uploadDataPackageName = messageV3.getUploadDataPackageName();
        String adPackage = messageV3.getAdvertisementOption().getAdPackage();
        DebugLogger.e("AbstractPushNotification", "again show old ad and replace package, uploadDataPackageName:" + uploadDataPackageName + ", adPackageName:" + adPackage);
        com.meizu.cloud.pushsdk.handler.e.b.a aVarA = com.meizu.cloud.pushsdk.b.a(this.f19751a).a();
        if (aVarA != null) {
            aVarA.a();
        }
        messageV3.setUploadDataPackageName(adPackage);
        return uploadDataPackageName;
    }

    protected Bitmap a(Context context, String str) throws PackageManager.NameNotFoundException {
        BitmapDrawable bitmapDrawable;
        try {
            Drawable applicationIcon = context.getPackageManager().getApplicationIcon(str);
            Bitmap bitmap = null;
            if (Build.VERSION.SDK_INT < 26 || (applicationIcon instanceof BitmapDrawable)) {
                bitmapDrawable = (BitmapDrawable) applicationIcon;
            } else if (com.google.firebase.messaging.a.a(applicationIcon)) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                applicationIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                applicationIcon.draw(canvas);
                bitmapDrawable = null;
                bitmap = bitmapCreateBitmap;
            } else {
                bitmapDrawable = null;
            }
            return bitmap == null ? bitmapDrawable.getBitmap() : bitmap;
        } catch (Exception e2) {
            DebugLogger.i("AbstractPushNotification", "get app icon error " + e2.getMessage());
            return ((BitmapDrawable) context.getApplicationInfo().loadIcon(context.getPackageManager())).getBitmap();
        }
    }

    protected Bitmap a(String str) {
        com.meizu.cloud.pushsdk.e.b.c cVarA = com.meizu.cloud.pushsdk.e.a.a(str).a().a();
        if (!cVarA.c() || cVarA.b() == null) {
            DebugLogger.i("AbstractPushNotification", "ANRequest On other Thread down load largeIcon " + str + "image fail");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ANRequest On other Thread down load largeIcon ");
        sb.append(str);
        sb.append("image ");
        sb.append(cVarA.b() != null ? "success" : ITagManager.FAIL);
        DebugLogger.i("AbstractPushNotification", sb.toString());
        return (Bitmap) cVarA.b();
    }

    protected void b(Notification.Builder builder, MessageV3 messageV3) {
    }

    private void a(int i2, String str, MessageV3 messageV3) {
        if (messageV3 == null || messageV3.getAdvertisementOption() == null || TextUtils.isEmpty(messageV3.getAdvertisementOption().getAdPackage())) {
            return;
        }
        DebugLogger.e("AbstractPushNotification", "save ad and recovery package, uploadDataPackageName:" + str);
        com.meizu.cloud.pushsdk.handler.e.b.a aVarA = com.meizu.cloud.pushsdk.b.a(this.f19751a).a();
        if (aVarA != null) {
            int priorityValidTime = messageV3.getAdvertisementOption().getPriorityValidTime();
            aVarA.a(messageV3);
            aVarA.a(i2, a(messageV3, d(messageV3), f(messageV3), e(messageV3)), priorityValidTime);
        }
        messageV3.setUploadDataPackageName(str);
    }

    @SuppressLint({"NewApi"})
    private void b(Notification notification, MessageV3 messageV3) throws IllegalAccessException, PackageManager.NameNotFoundException, IllegalArgumentException {
        com.meizu.cloud.pushsdk.notification.g.b.a(notification, true);
        com.meizu.cloud.pushsdk.notification.g.b.a(notification, g(messageV3));
        notification.extras.putString(PushConstants.EXTRA_ORIGINAL_NOTIFICATION_PACKAGE_NAME, messageV3.getUploadDataPackageName());
        notification.extras.putString(PushConstants.EXTRA_FLYME_GREEN_NOTIFICATION_SETTING, h(messageV3));
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_TASK_ID, messageV3.getTaskId());
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_SEQ_ID, messageV3.getSeqId());
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_DEVICE_ID, messageV3.getDeviceId());
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_PUSH_TIMESTAMP, messageV3.getPushTimestamp());
        if (!TextUtils.isEmpty(this.f19752b.getAppLabel())) {
            DebugLogger.e("AbstractPushNotification", "set app label " + this.f19752b.getAppLabel());
            notification.extras.putString(PushConstants.EXTRA_SUBSTITUTE_APP_NAME, this.f19752b.getAppLabel());
            return;
        }
        String strB = b(this.f19751a, messageV3.getUploadDataPackageName());
        DebugLogger.e("AbstractPushNotification", "current package " + messageV3.getUploadDataPackageName() + " label is " + strB);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        notification.extras.putString(PushConstants.EXTRA_SUBSTITUTE_APP_NAME, strB);
    }

    @SuppressLint({"WrongConstant"})
    private void a(Notification.Builder builder, MessageV3 messageV3) {
        boolean zIsSound;
        AdvanceSetting advanceSetting = messageV3.getAdvanceSetting();
        AdvanceSettingEx advanceSettingEx = messageV3.getAdvanceSettingEx();
        if (advanceSetting == null) {
            return;
        }
        if (advanceSettingEx == null || TextUtils.isEmpty(advanceSettingEx.getSoundTitle())) {
            zIsSound = advanceSetting.getNotifyType().isSound();
        } else {
            Uri uriB = com.meizu.cloud.pushsdk.notification.g.b.b(this.f19751a, advanceSettingEx.getSoundTitle());
            if (uriB != null) {
                DebugLogger.e("AbstractPushNotification", "advance setting builder, sound:" + uriB);
                builder.setSound(uriB);
                zIsSound = false;
            } else {
                zIsSound = true;
            }
        }
        if (advanceSetting.getNotifyType() != null) {
            boolean zIsVibrate = advanceSetting.getNotifyType().isVibrate();
            boolean zIsLights = advanceSetting.getNotifyType().isLights();
            if (zIsVibrate || zIsLights || zIsSound) {
                int i2 = zIsVibrate ? 2 : 0;
                if (zIsLights) {
                    i2 |= 4;
                }
                if (zIsSound) {
                    i2 |= 1;
                }
                DebugLogger.e("AbstractPushNotification", "advance setting builder, defaults:" + i2);
                builder.setDefaults(i2);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("advance setting builder, ongoing:");
        sb.append(!advanceSetting.isClearNotification());
        DebugLogger.e("AbstractPushNotification", sb.toString());
        builder.setOngoing(!advanceSetting.isClearNotification());
        if (advanceSettingEx == null || !MinSdkChecker.isSupportNotificationBuild()) {
            return;
        }
        DebugLogger.e("AbstractPushNotification", "advance setting builder, priority:" + advanceSettingEx.getPriorityDisplay());
        builder.setPriority(advanceSettingEx.getPriorityDisplay());
    }

    private void a(Notification.Builder builder, MessageV3 messageV3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        int statusBarIcon;
        builder.setContentTitle(messageV3.getTitle());
        builder.setContentText(messageV3.getContent());
        builder.setTicker(messageV3.getTitle());
        builder.setAutoCancel(true);
        if (MinSdkChecker.isSupportSendNotification()) {
            builder.setVisibility(1);
        }
        if (!MinSdkChecker.isSupportSetDrawableSmallIcon()) {
            PushNotificationBuilder pushNotificationBuilder = this.f19752b;
            if (pushNotificationBuilder != null && pushNotificationBuilder.getStatusBarIcon() != 0) {
                statusBarIcon = this.f19752b.getStatusBarIcon();
            }
            builder.setSmallIcon(statusBarIcon);
            builder.setContentIntent(pendingIntent);
            builder.setDeleteIntent(pendingIntent2);
        }
        Icon iconB = b(messageV3.getUploadDataPackageName());
        if (iconB != null) {
            builder.setSmallIcon(iconB);
            builder.setContentIntent(pendingIntent);
            builder.setDeleteIntent(pendingIntent2);
        } else {
            DebugLogger.e("AbstractPushNotification", "cannot get " + messageV3.getUploadDataPackageName() + " smallIcon");
        }
        statusBarIcon = com.meizu.cloud.pushsdk.notification.g.c.m(this.f19751a);
        builder.setSmallIcon(statusBarIcon);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(pendingIntent2);
    }

    protected void a(Notification notification, MessageV3 messageV3) {
    }

    protected void a(Notification notification, MessageV3 messageV3, PendingIntent pendingIntent) {
    }

    @Override // com.meizu.cloud.pushsdk.notification.c
    @SuppressLint({"NewApi"})
    public void a(MessageV3 messageV3) throws IllegalAccessException, PackageManager.NameNotFoundException, IllegalArgumentException {
        String strB = (messageV3.getAdvertisementOption() == null || TextUtils.isEmpty(messageV3.getAdvertisementOption().getAdPackage())) ? null : b(messageV3);
        Notification notificationA = a(messageV3, d(messageV3), f(messageV3), e(messageV3));
        int iAbs = Math.abs((int) System.currentTimeMillis());
        com.meizu.cloud.pushsdk.notification.model.a aVarB = com.meizu.cloud.pushsdk.notification.model.a.b(messageV3);
        if (aVarB != null && aVarB.a() != 0) {
            iAbs = aVarB.a();
            DebugLogger.e("AbstractPushNotification", "server notify id " + iAbs);
            if (!TextUtils.isEmpty(aVarB.b())) {
                int iC = com.meizu.cloud.pushsdk.util.b.c(this.f19751a, messageV3.getUploadDataPackageName(), aVarB.b());
                DebugLogger.e("AbstractPushNotification", "notifyKey " + aVarB.b() + " preference notifyId is " + iC);
                if (iC != 0) {
                    DebugLogger.e("AbstractPushNotification", "use preference notifyId " + iC + " and cancel it");
                    this.f19753c.cancel(iC);
                }
                DebugLogger.e("AbstractPushNotification", "store new notifyId " + iAbs + " by notifyKey " + aVarB.b());
                com.meizu.cloud.pushsdk.util.b.b(this.f19751a, messageV3.getUploadDataPackageName(), aVarB.b(), iAbs);
            }
        }
        DebugLogger.e("AbstractPushNotification", "current notify id " + iAbs);
        if (messageV3.isDiscard()) {
            if (com.meizu.cloud.pushsdk.util.b.b(this.f19751a, messageV3.getPackageName()) == 0) {
                com.meizu.cloud.pushsdk.util.b.a(this.f19751a, messageV3.getPackageName(), iAbs);
                DebugLogger.i("AbstractPushNotification", "no notification show so put notification id " + iAbs);
            }
            if (!TextUtils.isEmpty(messageV3.getTaskId())) {
                if (com.meizu.cloud.pushsdk.util.b.c(this.f19751a, messageV3.getPackageName()) == 0) {
                    com.meizu.cloud.pushsdk.util.b.b(this.f19751a, messageV3.getPackageName(), Integer.valueOf(messageV3.getTaskId()).intValue());
                } else {
                    if (Integer.valueOf(messageV3.getTaskId()).intValue() < com.meizu.cloud.pushsdk.util.b.c(this.f19751a, messageV3.getPackageName())) {
                        DebugLogger.i("AbstractPushNotification", "current package " + messageV3.getPackageName() + " task id " + messageV3.getTaskId() + " don't show notification");
                        return;
                    }
                    com.meizu.cloud.pushsdk.util.b.b(this.f19751a, messageV3.getPackageName(), Integer.valueOf(messageV3.getTaskId()).intValue());
                    iAbs = com.meizu.cloud.pushsdk.util.b.b(this.f19751a, messageV3.getPackageName());
                }
            }
            DebugLogger.i("AbstractPushNotification", "current package " + messageV3.getPackageName() + " notificationId=" + iAbs + " taskId=" + messageV3.getTaskId());
        }
        if (messageV3.getAdvertisementOption() != null && !TextUtils.isEmpty(messageV3.getAdvertisementOption().getAdPackage())) {
            a(iAbs, strB, messageV3);
        }
        this.f19753c.notify(iAbs, notificationA);
    }

    protected boolean a() {
        return Thread.currentThread() == this.f19751a.getMainLooper().getThread();
    }
}
