package com.meizu.cloud.pushsdk.handler.e.m;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSettingEx;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private Context f19745a;

    /* renamed from: b, reason: collision with root package name */
    private List<Intent> f19746b;

    /* renamed from: c, reason: collision with root package name */
    private BroadcastReceiver f19747c;

    /* renamed from: com.meizu.cloud.pushsdk.handler.e.m.a$a, reason: collision with other inner class name */
    class RunnableC0164a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Intent f19748a;

        RunnableC0164a(Intent intent) {
            this.f19748a = intent;
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            try {
                Thread.sleep(1000L);
                DebugLogger.d("BrightNotification", "start bright notification service " + this.f19748a);
                a.this.f19745a.startService(this.f19748a);
            } catch (Exception e2) {
                DebugLogger.e("BrightNotification", "send bright notification error " + e2.getMessage());
            }
        }
    }

    class b extends BroadcastReceiver {
        b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equalsIgnoreCase(intent.getAction())) {
                a.this.b();
            }
        }
    }

    public a(Context context) {
        this.f19745a = context.getApplicationContext();
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        List<Intent> list = this.f19746b;
        if (list == null || list.size() == 0) {
            return;
        }
        int size = this.f19746b.size();
        Iterator<Intent> it = this.f19746b.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Intent next = it.next();
            if (i2 != size - 1) {
                a(next);
            }
            b(next);
            it.remove();
            i2++;
        }
    }

    private void a() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        if (this.f19747c == null) {
            this.f19747c = new b();
        }
        this.f19745a.registerReceiver(this.f19747c, intentFilter);
    }

    private void b(Intent intent) {
        com.meizu.cloud.pushsdk.d.m.c.a().execute(new RunnableC0164a(intent));
    }

    private void a(Intent intent) {
        MessageV3 messageV3 = (MessageV3) intent.getParcelableExtra(PushConstants.EXTRA_APP_PUSH_BRIGHT_NOTIFICATION_MESSAGE);
        if (messageV3 == null) {
            return;
        }
        AdvanceSetting advanceSetting = messageV3.getAdvanceSetting();
        AdvanceSettingEx advanceSettingEx = messageV3.getAdvanceSettingEx();
        if (advanceSetting == null || advanceSettingEx == null) {
            return;
        }
        advanceSettingEx.setSoundTitle(null);
        advanceSetting.getNotifyType().setSound(false);
        advanceSetting.getNotifyType().setLights(false);
        advanceSetting.getNotifyType().setVibrate(false);
    }

    public void a(Intent intent, String str) {
        if (intent == null || TextUtils.isEmpty(str)) {
            return;
        }
        List<Intent> list = this.f19746b;
        if (list != null) {
            Iterator<Intent> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Intent next = it.next();
                MessageV3 messageV3 = (MessageV3) next.getParcelableExtra(PushConstants.EXTRA_APP_PUSH_BRIGHT_NOTIFICATION_MESSAGE);
                if (messageV3 != null && messageV3.getUploadDataPackageName() != null && str.equalsIgnoreCase(messageV3.getUploadDataPackageName())) {
                    this.f19746b.remove(next);
                    break;
                }
            }
        } else {
            this.f19746b = new ArrayList();
        }
        this.f19746b.add(intent);
        DebugLogger.d("BrightNotification", "add bright notification intent, intent list: " + this.f19746b);
    }
}
