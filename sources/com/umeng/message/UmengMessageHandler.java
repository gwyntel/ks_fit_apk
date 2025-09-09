package com.umeng.message;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.RemoteViews;
import com.facebook.share.internal.ShareConstants;
import com.kingsmith.miot.KsProperty;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.api.UPushMessageHandler;
import com.umeng.message.common.UPLog;
import com.umeng.message.common.UPushNotificationChannel;
import com.umeng.message.component.UmengNotificationClickActivity;
import com.umeng.message.component.UmengNotificationReceiver;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.ac;
import com.umeng.message.proguard.am;
import com.umeng.message.proguard.an;
import com.umeng.message.proguard.aw;
import com.umeng.message.proguard.bb;
import com.umeng.message.proguard.d;
import com.umeng.message.proguard.f;
import com.umeng.message.proguard.t;
import com.umeng.message.proguard.w;
import com.umeng.message.proguard.x;
import com.umeng.message.proguard.y;
import com.umeng.message.push.R;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class UmengMessageHandler implements UPushMessageHandler {

    /* renamed from: a, reason: collision with root package name */
    private static Date f22604a;

    /* renamed from: b, reason: collision with root package name */
    private int f22605b;

    /* JADX WARN: Multi-variable type inference failed */
    private Notification a(Context context, UMessage uMessage) {
        Bitmap bitmap;
        String str;
        String str2;
        int i2;
        Notification.Builder builder = new Notification.Builder(context);
        String category = uMessage.getCategory();
        int i3 = Build.VERSION.SDK_INT;
        if (!TextUtils.isEmpty(category)) {
            builder.setCategory(category);
        }
        if (i3 >= 26) {
            NotificationChannel notificationChannel = getNotificationChannel(context, uMessage);
            if (notificationChannel == null) {
                notificationChannel = ((uMessage.getImportance() == 1 && d.g()) || isInNoDisturbTime(context)) ? UPushNotificationChannel.getSilenceMode(context) : UPushNotificationChannel.getDefaultMode(context);
            }
            if (notificationChannel == null) {
                UPLog.e("MsgHandler", "notification channel null!");
                return null;
            }
            builder.setChannelId(notificationChannel.getId());
        }
        int smallIconId = getSmallIconId(context, uMessage);
        if (smallIconId < 0) {
            UPLog.e("MsgHandler", "notification small icon error!");
            return null;
        }
        Bitmap largeIcon = getLargeIcon(context, uMessage);
        Bitmap expandImage = getExpandImage(context, uMessage);
        String title = uMessage.getTitle();
        String content = uMessage.getContent();
        String titleColor = uMessage.getTitleColor();
        if (!TextUtils.isEmpty(titleColor) && !TextUtils.isEmpty(title)) {
            try {
                int color = Color.parseColor(titleColor);
                SpannableString spannableString = new SpannableString(title);
                spannableString.setSpan(new ForegroundColorSpan(color), 0, title.length(), 34);
                title = spannableString;
            } catch (Exception unused) {
            }
        }
        String textColor = uMessage.getTextColor();
        if (!TextUtils.isEmpty(textColor) && !TextUtils.isEmpty(content)) {
            try {
                int color2 = Color.parseColor(textColor);
                SpannableString spannableString2 = new SpannableString(content);
                spannableString2.setSpan(new ForegroundColorSpan(color2), 0, content.length(), 34);
                content = spannableString2;
            } catch (Exception unused2) {
            }
        }
        builder.setTicker(uMessage.getTicker());
        builder.setSmallIcon(smallIconId);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);
        int i4 = Build.VERSION.SDK_INT;
        Bitmap backgroundImage = (i4 < 26 || !uMessage.hasBackgroundImage()) ? null : getBackgroundImage(context, uMessage);
        if (i4 < 26 || backgroundImage == null) {
            bitmap = expandImage;
            str = titleColor;
            str2 = textColor;
            if (!TextUtils.isEmpty(uMessage.getBarImageUrl())) {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.upush_notification_banner_layout);
                remoteViews.setImageViewBitmap(R.id.upush_notification_banner, getBarImage(context, uMessage));
                if (i4 >= 24) {
                    builder.setCustomContentView(remoteViews);
                } else {
                    builder.setContent(remoteViews);
                }
            } else if (largeIcon != null) {
                builder.setLargeIcon(largeIcon);
            }
        } else {
            builder.setGroupSummary(d.h());
            builder.setGroup(uMessage.msg_id);
            int i5 = R.layout.upush_notification_shade_layout;
            int i6 = R.id.upush_notification_small_icon;
            int i7 = R.id.upush_notification_app_name;
            int i8 = R.id.upush_notification_date;
            int i9 = R.id.upush_notification_title;
            int i10 = R.id.upush_notification_content;
            int i11 = R.id.upush_notification_shade_iv;
            str2 = textColor;
            int i12 = R.id.upush_notification_large_iv;
            str = titleColor;
            bitmap = expandImage;
            RemoteViews remoteViews2 = new RemoteViews(context.getPackageName(), i5);
            remoteViews2.setImageViewResource(i6, smallIconId);
            remoteViews2.setTextViewText(i7, UMUtils.getAppName(context));
            long msgTime = uMessage.getMsgTime();
            remoteViews2.setTextViewText(i8, (f.a(msgTime) ? new SimpleDateFormat("HH:mm", Locale.getDefault()) : new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())).format(Long.valueOf(msgTime)));
            remoteViews2.setTextViewText(i9, title);
            if (TextUtils.isEmpty(content)) {
                remoteViews2.setViewVisibility(i10, 8);
                i2 = 0;
            } else {
                i2 = 0;
                remoteViews2.setViewVisibility(i10, 0);
                remoteViews2.setTextViewText(i10, content);
            }
            if (largeIcon != null) {
                remoteViews2.setViewVisibility(i12, i2);
                remoteViews2.setImageViewBitmap(i12, largeIcon);
            } else {
                remoteViews2.setViewVisibility(i12, 8);
            }
            remoteViews2.setViewVisibility(i11, i2);
            remoteViews2.setImageViewBitmap(i11, backgroundImage);
            builder.setCustomContentView(remoteViews2);
        }
        if (bitmap != null) {
            Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle();
            bigPictureStyle.bigPicture(bitmap);
            bigPictureStyle.bigLargeIcon(largeIcon);
            builder.setStyle(bigPictureStyle);
        } else if (!TextUtils.isEmpty(uMessage.getBigBody())) {
            String bigTitle = uMessage.getBigTitle();
            if (bigTitle != null && bigTitle.length() != 0) {
                title = bigTitle;
            }
            String bigBody = uMessage.getBigBody();
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(title)) {
                try {
                    int color3 = Color.parseColor(str);
                    SpannableString spannableString3 = new SpannableString(title);
                    spannableString3.setSpan(new ForegroundColorSpan(color3), 0, title.length(), 34);
                    title = spannableString3;
                } catch (Exception unused3) {
                }
            }
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(bigBody)) {
                try {
                    int color4 = Color.parseColor(str2);
                    SpannableString spannableString4 = new SpannableString(bigBody);
                    spannableString4.setSpan(new ForegroundColorSpan(color4), 0, bigBody.length(), 34);
                    bigBody = spannableString4;
                } catch (Exception unused4) {
                }
            }
            Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
            bigTextStyle.setBigContentTitle(title);
            bigTextStyle.bigText(bigBody);
            builder.setStyle(bigTextStyle);
        }
        return builder.getNotification();
    }

    public void dealWithCustomMessage(Context context, UMessage uMessage) {
    }

    public void dealWithNotificationMessage(Context context, UMessage uMessage) {
        UPLog.i("MsgHandler", "notification:", uMessage.getRaw());
        MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(context);
        boolean z2 = uMessage.getMsgTime() >= messageSharedPrefs.f22601b.b("last_msg_time", 0L);
        messageSharedPrefs.f22601b.a("last_msg_time", uMessage.getMsgTime());
        Notification notification = getNotification(context, uMessage);
        int notificationDefaults = getNotificationDefaults(context, uMessage);
        if (notification == null) {
            notification = a(context, uMessage);
        }
        if (notification == null) {
            UPLog.e("MsgHandler", "notification null");
            return;
        }
        int i2 = this.f22605b;
        if (i2 == 0) {
            this.f22605b = (int) SystemClock.elapsedRealtime();
        } else {
            this.f22605b = i2 + 1;
        }
        notification.deleteIntent = getDismissPendingIntent(context, uMessage);
        notification.contentIntent = getClickPendingIntent(context, uMessage);
        if ((notificationDefaults & 1) != 0) {
            Uri sound = getSound(context, uMessage);
            if (sound != null) {
                notification.sound = getSound(context, uMessage);
            }
            if (sound != null) {
                notificationDefaults ^= 1;
            }
        }
        notification.defaults = notificationDefaults;
        int i3 = this.f22605b;
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            if (PushAgent.getInstance(context).getNotificationOnForeground() || !t.c()) {
                int iB = MessageSharedPrefs.getInstance(context).b();
                if (iB != 1 || z2) {
                    w wVarA = w.a();
                    if (iB > 0) {
                        while (wVarA.c() >= iB) {
                            ac acVarB = wVarA.b();
                            if (acVarB != null) {
                                if (notificationManager != null) {
                                    notificationManager.cancel("um", acVarB.f22709a);
                                }
                                UTrack.getInstance().trackMsgDismissed(acVarB.f22710b);
                                am.a(acVarB);
                            }
                        }
                    }
                    ac acVar = new ac(i3, uMessage);
                    wVarA.a(acVar);
                    if (notificationManager != null) {
                        notificationManager.notify("um", i3, notification);
                        UTrack.getInstance().trackMsgShow(uMessage, notification);
                    }
                    UMessage uMessage2 = acVar.f22710b;
                    if (uMessage2 != null && acVar.f22711c == null && uMessage2.isRepost()) {
                        MessageSharedPrefs.getInstance(x.a()).f22601b.a("re_pop_cfg", uMessage2.getRepostCount());
                        acVar.f22711c = new an(acVar).a();
                    }
                } else {
                    UTrack.getInstance().trackMsgDismissed(uMessage);
                }
            } else {
                UPLog.i("MsgHandler", "foreground notification dismiss. msgId:", uMessage.getMsgId());
                UTrack.getInstance().trackMsgDismissed(uMessage);
            }
        } catch (Exception e2) {
            UPLog.e("MsgHandler", e2);
        }
        setBadgeNum(context, uMessage);
    }

    public Bitmap getBackgroundImage(Context context, UMessage uMessage) {
        try {
            String backgroundImageUrl = uMessage.getBackgroundImageUrl();
            if (TextUtils.isEmpty(backgroundImageUrl)) {
                return null;
            }
            return f.a(new File(f.g(context), UMUtils.MD5(backgroundImageUrl)), bb.a(), bb.a(64.0f));
        } catch (Throwable th) {
            UPLog.e("MsgHandler", th);
            return null;
        }
    }

    public Bitmap getBarImage(Context context, UMessage uMessage) {
        try {
            String barImageUrl = uMessage.getBarImageUrl();
            if (TextUtils.isEmpty(barImageUrl)) {
                return null;
            }
            return f.a(new File(f.g(context), UMUtils.MD5(barImageUrl)), bb.a(), bb.a(64.0f));
        } catch (Throwable th) {
            UPLog.e("MsgHandler", th);
            return null;
        }
    }

    public PendingIntent getClickPendingIntent(Context context, UMessage uMessage) {
        Intent intent = new Intent();
        intent.setFlags(335544320);
        intent.setClass(context, UmengNotificationClickActivity.class);
        intent.putExtra("MSG", uMessage.getRaw().toString());
        intent.putExtra("NOTIFICATION_ID", this.f22605b);
        return PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 335544320);
    }

    public PendingIntent getDismissPendingIntent(Context context, UMessage uMessage) {
        Intent intent = new Intent();
        intent.setClass(context, UmengNotificationReceiver.class);
        intent.putExtra("MSG", uMessage.getRaw().toString());
        intent.putExtra(ShareConstants.ACTION, 11);
        intent.putExtra("NOTIFICATION_ID", this.f22605b);
        return PendingIntent.getBroadcast(context, (int) (System.currentTimeMillis() + 1), intent, 335544320);
    }

    public Bitmap getExpandImage(Context context, UMessage uMessage) {
        try {
            String bigImage = uMessage.getBigImage();
            if (TextUtils.isEmpty(bigImage)) {
                return null;
            }
            return f.a(new File(f.g(context), UMUtils.MD5(bigImage)), bb.a(), bb.a(256.0f));
        } catch (Throwable th) {
            UPLog.e("MsgHandler", th);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005f A[Catch: all -> 0x0030, TRY_LEAVE, TryCatch #0 {all -> 0x0030, blocks: (B:3:0x0003, B:5:0x0009, B:8:0x0014, B:13:0x0035, B:15:0x003f, B:26:0x005f, B:20:0x004d, B:18:0x0049, B:23:0x0059), top: B:30:0x0003, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Bitmap getLargeIcon(android.content.Context r6, com.umeng.message.entity.UMessage r7) {
        /*
            r5 = this;
            java.lang.String r0 = "MsgHandler"
            r1 = 0
            boolean r2 = r7.isLargeIconFromInternet()     // Catch: java.lang.Throwable -> L30
            if (r2 == 0) goto L32
            java.lang.String r2 = r7.getLargeIconUrl()     // Catch: java.lang.Throwable -> L30
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Throwable -> L30
            if (r3 == 0) goto L14
            return r1
        L14:
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L30
            java.io.File r4 = com.umeng.message.proguard.f.g(r6)     // Catch: java.lang.Throwable -> L30
            java.lang.String r2 = com.umeng.commonsdk.utils.UMUtils.MD5(r2)     // Catch: java.lang.Throwable -> L30
            r3.<init>(r4, r2)     // Catch: java.lang.Throwable -> L30
            r2 = 1111490560(0x42400000, float:48.0)
            int r4 = com.umeng.message.proguard.bb.a(r2)     // Catch: java.lang.Throwable -> L30
            int r2 = com.umeng.message.proguard.bb.a(r2)     // Catch: java.lang.Throwable -> L30
            android.graphics.Bitmap r2 = com.umeng.message.proguard.f.a(r3, r4, r2)     // Catch: java.lang.Throwable -> L30
            goto L33
        L30:
            r6 = move-exception
            goto L68
        L32:
            r2 = r1
        L33:
            if (r2 != 0) goto L67
            java.lang.String r7 = r7.getLargeIconDrawableName()     // Catch: java.lang.Throwable -> L30
            boolean r3 = android.text.TextUtils.isEmpty(r7)     // Catch: java.lang.Throwable -> L30
            if (r3 != 0) goto L4d
            com.umeng.message.proguard.a r3 = com.umeng.message.proguard.a.a()     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L48
            int r7 = r3.b(r7)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L48
            goto L5d
        L48:
            r7 = move-exception
            com.umeng.message.common.UPLog.e(r0, r7)     // Catch: java.lang.Throwable -> L30
            goto L5c
        L4d:
            java.lang.String r7 = "umeng_push_notification_default_large_icon"
            com.umeng.message.proguard.a r3 = com.umeng.message.proguard.a.a()     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L58
            int r7 = r3.b(r7)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L58
            goto L5d
        L58:
            r7 = move-exception
            com.umeng.message.common.UPLog.w(r0, r7)     // Catch: java.lang.Throwable -> L30
        L5c:
            r7 = -1
        L5d:
            if (r7 <= 0) goto L67
            android.content.res.Resources r6 = r6.getResources()     // Catch: java.lang.Throwable -> L30
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeResource(r6, r7)     // Catch: java.lang.Throwable -> L30
        L67:
            return r2
        L68:
            com.umeng.message.common.UPLog.e(r0, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.UmengMessageHandler.getLargeIcon(android.content.Context, com.umeng.message.entity.UMessage):android.graphics.Bitmap");
    }

    public Notification getNotification(Context context, UMessage uMessage) {
        return null;
    }

    public NotificationChannel getNotificationChannel() {
        return null;
    }

    public int getNotificationDefaults(Context context, UMessage uMessage) {
        Calendar calendar = Calendar.getInstance();
        if (isInNoDisturbTime(context)) {
            return 0;
        }
        MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(context);
        long jG = messageSharedPrefs.g() * 1000;
        if (f22604a != null && calendar.getTimeInMillis() - f22604a.getTime() < jG) {
            return 0;
        }
        int iH = messageSharedPrefs.h();
        UPLog.i("MsgHandler", "vibrate:", Integer.valueOf(iH));
        int i2 = (iH != 1 && (iH == 2 || !uMessage.isVibrate())) ? 0 : 2;
        int i3 = messageSharedPrefs.i();
        UPLog.i("MsgHandler", "lights:", Integer.valueOf(i3));
        if (i3 == 1 || (i3 != 2 && uMessage.isLights())) {
            i2 |= 4;
        }
        int iJ = messageSharedPrefs.j();
        UPLog.i("MsgHandler", "sound:", Integer.valueOf(iJ));
        if (iJ == 1 || (iJ != 2 && uMessage.isSound())) {
            i2 |= 1;
        }
        f22604a = calendar.getTime();
        if (uMessage.isScreenOn()) {
            try {
                PowerManager powerManager = (PowerManager) context.getSystemService(KsProperty.Power);
                boolean zIsScreenOn = powerManager.isScreenOn();
                UPLog.i("MsgHandler", "screen on:".concat(String.valueOf(zIsScreenOn)));
                if (!zIsScreenOn) {
                    powerManager.newWakeLock(805306374, "UPush:NTF").acquire(10000L);
                }
            } catch (Throwable th) {
                UPLog.e("MsgHandler", th);
            }
        }
        return i2;
    }

    public int getSmallIconId(Context context, UMessage uMessage) {
        int iB = -1;
        try {
            String smallIconDrawableName = uMessage.getSmallIconDrawableName();
            if (TextUtils.isEmpty(smallIconDrawableName)) {
                try {
                    iB = com.umeng.message.proguard.a.a().b("umeng_push_notification_default_small_icon");
                } catch (Exception e2) {
                    UPLog.w("MsgHandler", e2);
                }
            } else {
                try {
                    iB = com.umeng.message.proguard.a.a().b(smallIconDrawableName);
                } catch (Exception e3) {
                    UPLog.e("MsgHandler", e3);
                }
            }
            if (iB < 0) {
                UPLog.i("MsgHandler", "no custom notification small icon! change to use app icon");
                iB = context.getApplicationInfo().icon;
            }
            if (iB < 0) {
                UPLog.e("MsgHandler", "can't find notification small icon");
            }
        } catch (Throwable th) {
            UPLog.e("MsgHandler", th);
        }
        return iB;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.net.Uri getSound(android.content.Context r5, com.umeng.message.entity.UMessage r6) {
        /*
            r4 = this;
            r0 = 0
            boolean r1 = r6.isSoundFromInternet()     // Catch: java.lang.Throwable -> L24
            if (r1 == 0) goto L22
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L24
            java.io.File r2 = com.umeng.message.proguard.f.g(r5)     // Catch: java.lang.Throwable -> L24
            java.lang.String r3 = r6.getSoundUri()     // Catch: java.lang.Throwable -> L24
            java.lang.String r3 = com.umeng.commonsdk.utils.UMUtils.MD5(r3)     // Catch: java.lang.Throwable -> L24
            r1.<init>(r2, r3)     // Catch: java.lang.Throwable -> L24
            java.lang.String r2 = r1.getPath()     // Catch: java.lang.Throwable -> L24
            boolean r1 = r1.exists()     // Catch: java.lang.Throwable -> L24
            if (r1 != 0) goto L26
        L22:
            r2 = r0
            goto L26
        L24:
            r5 = move-exception
            goto L6f
        L26:
            if (r2 != 0) goto L68
            java.lang.String r1 = r6.getSoundUri()     // Catch: java.lang.Throwable -> L24
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L24
            if (r1 != 0) goto L3f
            java.lang.String r6 = r6.getSoundUri()     // Catch: java.lang.Throwable -> L24
            com.umeng.message.proguard.a r1 = com.umeng.message.proguard.a.a()     // Catch: java.lang.Throwable -> L24
            int r6 = r1.c(r6)     // Catch: java.lang.Throwable -> L24
            goto L40
        L3f:
            r6 = -1
        L40:
            if (r6 >= 0) goto L4c
            java.lang.String r6 = "umeng_push_notification_default_sound"
            com.umeng.message.proguard.a r1 = com.umeng.message.proguard.a.a()     // Catch: java.lang.Throwable -> L24
            int r6 = r1.c(r6)     // Catch: java.lang.Throwable -> L24
        L4c:
            if (r6 <= 0) goto L68
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L24
            java.lang.String r2 = "android.resource://"
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L24
            java.lang.String r5 = r5.getPackageName()     // Catch: java.lang.Throwable -> L24
            r1.append(r5)     // Catch: java.lang.Throwable -> L24
            java.lang.String r5 = "/"
            r1.append(r5)     // Catch: java.lang.Throwable -> L24
            r1.append(r6)     // Catch: java.lang.Throwable -> L24
            java.lang.String r2 = r1.toString()     // Catch: java.lang.Throwable -> L24
        L68:
            if (r2 == 0) goto L74
            android.net.Uri r5 = android.net.Uri.parse(r2)     // Catch: java.lang.Throwable -> L24
            return r5
        L6f:
            java.lang.String r6 = "MsgHandler"
            com.umeng.message.common.UPLog.w(r6, r5)
        L74:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.UmengMessageHandler.getSound(android.content.Context, com.umeng.message.entity.UMessage):android.net.Uri");
    }

    @Override // com.umeng.message.api.UPushMessageHandler
    public void handleMessage(Context context, UMessage uMessage) {
        if ("notification".equals(uMessage.getDisplayType())) {
            dealWithNotificationMessage(context, uMessage);
            return;
        }
        if (UMessage.DISPLAY_TYPE_CUSTOM.equals(uMessage.getDisplayType())) {
            if (TextUtils.isEmpty(uMessage.getRecallMsgId())) {
                dealWithCustomMessage(context, uMessage);
                return;
            }
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                if (notificationManager == null) {
                    return;
                }
                w wVarA = w.a();
                ac acVarA = wVarA.a(uMessage.getRecallMsgId());
                if (acVarA == null) {
                    y.a().a(uMessage.getRecallMsgId(), 5);
                    return;
                }
                notificationManager.cancel("um", acVarA.f22709a);
                wVarA.b(acVarA);
                am.a(acVarA);
                y.a().a(uMessage.getRecallMsgId(), 4);
            } catch (Throwable th) {
                UPLog.e("MsgHandler", th);
            }
        }
    }

    public boolean isInNoDisturbTime(Context context) {
        Calendar calendar = Calendar.getInstance();
        int i2 = (calendar.get(11) * 60) + calendar.get(12);
        boolean z2 = i2 >= (PushAgent.getInstance(context).getNoDisturbStartHour() * 60) + PushAgent.getInstance(context).getNoDisturbStartMinute();
        boolean z3 = i2 <= (PushAgent.getInstance(context).getNoDisturbEndHour() * 60) + PushAgent.getInstance(context).getNoDisturbEndMinute();
        return (PushAgent.getInstance(context).getNoDisturbEndHour() * 60) + PushAgent.getInstance(context).getNoDisturbEndMinute() >= (PushAgent.getInstance(context).getNoDisturbStartHour() * 60) + PushAgent.getInstance(context).getNoDisturbStartMinute() ? z2 && z3 : z2 || z3;
    }

    public void setBadgeNum(Context context, UMessage uMessage) {
        if (uMessage.getBadgeSet() >= 0) {
            aw.a(context, uMessage.getBadgeSet());
            UPLog.d("MsgHandler", "setBadgeNum:", Integer.valueOf(uMessage.getBadgeSet()));
        } else if (uMessage.getBadgeAdd() != 0) {
            aw.b(context, uMessage.getBadgeAdd());
            UPLog.d("MsgHandler", "changeBadgeNum:", Integer.valueOf(uMessage.getBadgeAdd()));
        }
    }

    public NotificationChannel getNotificationChannel(Context context, UMessage uMessage) {
        return getNotificationChannel();
    }
}
