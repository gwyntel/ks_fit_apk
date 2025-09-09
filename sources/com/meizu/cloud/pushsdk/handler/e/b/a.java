package com.meizu.cloud.pushsdk.handler.e.b;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSettingEx;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private Context f19684a;

    /* renamed from: b, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.d.k.a f19685b;

    /* renamed from: c, reason: collision with root package name */
    private int f19686c;

    /* renamed from: d, reason: collision with root package name */
    private Notification f19687d;

    /* renamed from: com.meizu.cloud.pushsdk.handler.e.b.a$a, reason: collision with other inner class name */
    class RunnableC0161a implements Runnable {
        RunnableC0161a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            DebugLogger.d("AdNotification", "ad priority valid time out");
            a.this.a();
        }
    }

    public a(Context context) {
        this.f19684a = context;
    }

    private void b() {
        this.f19686c = 0;
        this.f19687d = null;
        com.meizu.cloud.pushsdk.d.k.a aVar = this.f19685b;
        if (aVar != null) {
            try {
                try {
                    aVar.a();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                this.f19685b = null;
            }
        }
    }

    public void a() {
        if (this.f19686c <= 0 || this.f19687d == null) {
            return;
        }
        try {
            ((NotificationManager) this.f19684a.getSystemService("notification")).notify(this.f19686c, this.f19687d);
            DebugLogger.d("AdNotification", "again show old ad notification, notifyId:" + this.f19686c);
        } catch (Exception e2) {
            e2.printStackTrace();
            DebugLogger.e("AdNotification", "again show old ad notification error:" + e2.getMessage());
        }
        b();
    }

    private void b(int i2) {
        if (i2 <= 0) {
            return;
        }
        com.meizu.cloud.pushsdk.d.k.a aVar = this.f19685b;
        if (aVar != null) {
            try {
                try {
                    aVar.a();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                this.f19685b = null;
            }
        }
        com.meizu.cloud.pushsdk.d.k.a aVar2 = new com.meizu.cloud.pushsdk.d.k.a(this.f19684a, new RunnableC0161a(), i2 * 60000);
        this.f19685b = aVar2;
        aVar2.c();
    }

    public void a(int i2) {
        int i3;
        if (i2 <= 0 || (i3 = this.f19686c) <= 0 || i2 != i3) {
            return;
        }
        b();
        DebugLogger.d("AdNotification", "clean ad notification, notifyId:" + i2);
    }

    private void a(int i2, Notification notification) {
        this.f19686c = i2;
        this.f19687d = notification;
    }

    public void a(int i2, Notification notification, int i3) {
        if (i2 <= 0 || notification == null) {
            return;
        }
        a(i2, notification);
        b(i3);
        DebugLogger.d("AdNotification", "save ad notification, notifyId:" + i2);
    }

    public void a(MessageV3 messageV3) {
        AdvanceSetting advanceSetting = messageV3.getAdvanceSetting();
        if (advanceSetting != null) {
            advanceSetting.getNotifyType().setSound(false);
            advanceSetting.getNotifyType().setLights(false);
            advanceSetting.getNotifyType().setVibrate(false);
        }
        AdvanceSettingEx advanceSettingEx = messageV3.getAdvanceSettingEx();
        if (advanceSettingEx != null) {
            advanceSettingEx.setSoundTitle(null);
            if (Build.VERSION.SDK_INT < 29 || advanceSetting == null || !advanceSetting.isHeadUpNotification()) {
                advanceSettingEx.setPriorityDisplay(0);
            } else {
                advanceSettingEx.setPriorityDisplay(1);
            }
        }
    }
}
