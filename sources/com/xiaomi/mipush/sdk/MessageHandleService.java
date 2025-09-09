package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.PushMessageHandler;
import com.xiaomi.push.fo;
import com.xiaomi.push.fy;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class MessageHandleService extends BaseService {

    /* renamed from: a, reason: collision with root package name */
    private static ConcurrentLinkedQueue<a> f23356a = new ConcurrentLinkedQueue<>();

    /* renamed from: a, reason: collision with other field name */
    private static ExecutorService f100a = new ThreadPoolExecutor(1, 1, 15, TimeUnit.SECONDS, new LinkedBlockingQueue());

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private Intent f23357a;

        /* renamed from: a, reason: collision with other field name */
        private PushMessageReceiver f101a;

        public a(Intent intent, PushMessageReceiver pushMessageReceiver) {
            this.f101a = pushMessageReceiver;
            this.f23357a = intent;
        }

        /* renamed from: a, reason: collision with other method in class */
        public PushMessageReceiver m110a() {
            return this.f101a;
        }

        public Intent a() {
            return this.f23357a;
        }
    }

    public static void addJob(Context context, a aVar) {
        if (aVar != null) {
            f23356a.add(aVar);
            b(context);
            startService(context);
        }
    }

    private static void b(Context context) {
        if (f100a.isShutdown()) {
            return;
        }
        f100a.execute(new z(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context) {
        try {
            a(context, f23356a.poll());
        } catch (RuntimeException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    public static void startService(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, (Class<?>) MessageHandleService.class));
        com.xiaomi.push.ah.a(context).a(new y(context, intent));
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public void onStart(Intent intent, int i2) {
        super.onStart(intent, i2);
    }

    protected static void a(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        b(context);
    }

    static void a(Context context, a aVar) {
        String[] stringArrayExtra;
        if (aVar == null) {
            return;
        }
        try {
            PushMessageReceiver pushMessageReceiverM110a = aVar.m110a();
            Intent intentA = aVar.a();
            int intExtra = intentA.getIntExtra("message_type", 1);
            if (intExtra != 1) {
                if (intExtra != 3) {
                    if (intExtra == 5 && PushMessageHelper.ERROR_TYPE_NEED_PERMISSION.equals(intentA.getStringExtra("error_type")) && (stringArrayExtra = intentA.getStringArrayExtra("error_message")) != null) {
                        com.xiaomi.channel.commonutils.logger.b.e("begin execute onRequirePermissions, lack of necessary permissions");
                        pushMessageReceiverM110a.onRequirePermissions(context, stringArrayExtra);
                        return;
                    }
                    return;
                }
                MiPushCommandMessage miPushCommandMessage = (MiPushCommandMessage) intentA.getSerializableExtra(PushMessageHelper.KEY_COMMAND);
                com.xiaomi.channel.commonutils.logger.b.e("(Local) begin execute onCommandResult, command=" + miPushCommandMessage.getCommand() + ", resultCode=" + miPushCommandMessage.getResultCode() + ", reason=" + miPushCommandMessage.getReason());
                pushMessageReceiverM110a.onCommandResult(context, miPushCommandMessage);
                if (TextUtils.equals(miPushCommandMessage.getCommand(), fy.COMMAND_REGISTER.f444a)) {
                    pushMessageReceiverM110a.onReceiveRegisterResult(context, miPushCommandMessage);
                    PushMessageHandler.a(context, miPushCommandMessage);
                    if (miPushCommandMessage.getResultCode() == 0) {
                        i.b(context);
                        return;
                    }
                    return;
                }
                return;
            }
            PushMessageHandler.a aVarA = am.a(context).a(intentA);
            int intExtra2 = intentA.getIntExtra("eventMessageType", -1);
            if (aVarA == null) {
                com.xiaomi.channel.commonutils.logger.b.c("MessageHandleService", "no message from raw for receiver");
                return;
            }
            if (aVarA instanceof MiPushMessage) {
                MiPushMessage miPushMessage = (MiPushMessage) aVarA;
                if (!miPushMessage.isArrivedMessage()) {
                    pushMessageReceiverM110a.onReceiveMessage(context, miPushMessage);
                }
                if (miPushMessage.getPassThrough() == 1) {
                    fo.a(context.getApplicationContext()).a(context.getPackageName(), intentA, 2004, (String) null);
                    com.xiaomi.channel.commonutils.logger.b.c("MessageHandleService", "begin execute onReceivePassThroughMessage from " + miPushMessage.getMessageId());
                    pushMessageReceiverM110a.onReceivePassThroughMessage(context, miPushMessage);
                    return;
                }
                if (miPushMessage.isNotified()) {
                    if (intExtra2 == 1000) {
                        fo.a(context.getApplicationContext()).a(context.getPackageName(), intentA, 1007, (String) null);
                    } else {
                        fo.a(context.getApplicationContext()).a(context.getPackageName(), intentA, 3007, (String) null);
                    }
                    com.xiaomi.channel.commonutils.logger.b.c("MessageHandleService", "begin execute onNotificationMessageClicked from\u3000" + miPushMessage.getMessageId());
                    pushMessageReceiverM110a.onNotificationMessageClicked(context, miPushMessage);
                    return;
                }
                com.xiaomi.channel.commonutils.logger.b.c("MessageHandleService", "begin execute onNotificationMessageArrived from " + miPushMessage.getMessageId());
                pushMessageReceiverM110a.onNotificationMessageArrived(context, miPushMessage);
                return;
            }
            if (aVarA instanceof MiPushCommandMessage) {
                MiPushCommandMessage miPushCommandMessage2 = (MiPushCommandMessage) aVarA;
                com.xiaomi.channel.commonutils.logger.b.c("MessageHandleService", "begin execute onCommandResult, command=" + miPushCommandMessage2.getCommand() + ", resultCode=" + miPushCommandMessage2.getResultCode() + ", reason=" + miPushCommandMessage2.getReason());
                pushMessageReceiverM110a.onCommandResult(context, miPushCommandMessage2);
                if (TextUtils.equals(miPushCommandMessage2.getCommand(), fy.COMMAND_REGISTER.f444a)) {
                    pushMessageReceiverM110a.onReceiveRegisterResult(context, miPushCommandMessage2);
                    PushMessageHandler.a(context, miPushCommandMessage2);
                    if (miPushCommandMessage2.getResultCode() == 0) {
                        i.b(context);
                        return;
                    }
                    return;
                }
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.c("MessageHandleService", "unknown raw message: " + aVarA);
        } catch (RuntimeException e2) {
            com.xiaomi.channel.commonutils.logger.b.a("MessageHandleService", e2);
        }
    }

    @Override // com.xiaomi.mipush.sdk.BaseService
    /* renamed from: a */
    protected boolean mo116a() {
        ConcurrentLinkedQueue<a> concurrentLinkedQueue = f23356a;
        return concurrentLinkedQueue != null && concurrentLinkedQueue.size() > 0;
    }
}
