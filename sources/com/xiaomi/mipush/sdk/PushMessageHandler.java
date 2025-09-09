package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.MessageHandleService;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.push.C0472r;
import com.xiaomi.push.fo;
import com.xiaomi.push.fy;
import com.xiaomi.push.ir;
import com.xiaomi.push.jx;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.message.MessageService;

/* loaded from: classes4.dex */
public class PushMessageHandler extends BaseService {

    /* renamed from: a, reason: collision with root package name */
    private static List<MiPushClient.ICallbackResult> f23361a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    private static List<MiPushClient.MiPushClientCallback> f23362b = new ArrayList();

    /* renamed from: a, reason: collision with other field name */
    private static ThreadPoolExecutor f112a = new ThreadPoolExecutor(1, 1, 15, TimeUnit.SECONDS, new LinkedBlockingQueue());

    interface a extends Serializable {
    }

    public static void a(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, (Class<?>) PushMessageHandler.class));
        try {
            context.startService(intent);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m92a("PushMessageHandler", e2.getMessage());
        }
    }

    protected static void b() {
        synchronized (f23361a) {
            f23361a.clear();
        }
    }

    private static void c(Context context, Intent intent) {
        if (intent != null && !f112a.isShutdown()) {
            f112a.execute(new al(context, intent));
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("-->scheduleJob() fail, case");
        sb.append(intent == null ? "0" : "1");
        com.xiaomi.channel.commonutils.logger.b.c("PushMessageHandler", sb.toString());
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public void onStart(Intent intent, int i2) {
        super.onStart(intent, i2);
        c(getApplicationContext(), intent);
    }

    protected static void b(Context context, Intent intent) {
        boolean booleanExtra;
        try {
            booleanExtra = intent.getBooleanExtra("is_clicked_activity_call", false);
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.m92a("PushMessageHandler", "intent unparcel error:" + th);
            booleanExtra = false;
        }
        try {
            com.xiaomi.channel.commonutils.logger.b.m93a("PushMessageHandler", "-->onHandleIntent(): action=", intent.getAction());
            ResolveInfo resolveInfo = null;
            if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
                o.a(context, intent, null);
            } else if ("com.xiaomi.mipush.SEND_TINYDATA".equals(intent.getAction())) {
                ir irVar = new ir();
                jx.a(irVar, intent.getByteArrayExtra("mipush_payload"));
                com.xiaomi.channel.commonutils.logger.b.m97b("PushMessageHandler", "PushMessageHandler.onHandleIntent " + irVar.d());
                MiTinyDataClient.upload(context, irVar);
            } else if (1 == PushMessageHelper.getPushMode(context)) {
                if (m115b()) {
                    com.xiaomi.channel.commonutils.logger.b.m94a("PushMessageHandler", "receive a message before application calling initialize");
                    if (booleanExtra) {
                        b(context);
                        return;
                    }
                    return;
                }
                a aVarA = am.a(context).a(intent);
                if (aVarA != null) {
                    a(context, aVarA);
                }
            } else if (!"com.xiaomi.mipush.sdk.SYNC_LOG".equals(intent.getAction())) {
                Intent intent2 = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
                intent2.setPackage(context.getPackageName());
                intent2.putExtras(intent);
                try {
                    List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent2, 32);
                    if (listQueryBroadcastReceivers != null) {
                        Iterator<ResolveInfo> it = listQueryBroadcastReceivers.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            ResolveInfo next = it.next();
                            ActivityInfo activityInfo = next.activityInfo;
                            if (activityInfo != null && activityInfo.packageName.equals(context.getPackageName()) && PushMessageReceiver.class.isAssignableFrom(C0472r.a(context, next.activityInfo.name))) {
                                resolveInfo = next;
                                break;
                            }
                        }
                    }
                    if (resolveInfo != null) {
                        a(context, intent2, resolveInfo, booleanExtra);
                    } else {
                        com.xiaomi.channel.commonutils.logger.b.m94a("PushMessageHandler", "cannot find the receiver to handler this message, check your manifest");
                        fo.a(context).a(context.getPackageName(), intent, AgooConstants.ACK_BODY_NULL);
                    }
                } catch (Exception e2) {
                    com.xiaomi.channel.commonutils.logger.b.a("PushMessageHandler", e2);
                    fo.a(context).a(context.getPackageName(), intent, MessageService.MSG_ACCS_NOTIFY_DISMISS);
                }
            }
        } catch (Throwable th2) {
            try {
                com.xiaomi.channel.commonutils.logger.b.a("PushMessageHandler", th2);
                fo.a(context).a(context.getPackageName(), intent, AgooConstants.ACK_REMOVE_PACKAGE);
                if (!booleanExtra) {
                }
            } finally {
                if (booleanExtra) {
                    b(context);
                }
            }
        }
    }

    public static void a(Context context, Intent intent) {
        com.xiaomi.channel.commonutils.logger.b.m97b("PushMessageHandler", "addjob PushMessageHandler " + intent);
        if (intent != null) {
            c(context, intent);
            a(context);
        }
    }

    protected static void a(MiPushClient.MiPushClientCallback miPushClientCallback) {
        synchronized (f23362b) {
            try {
                if (!f23362b.contains(miPushClientCallback)) {
                    f23362b.add(miPushClientCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected static void a(MiPushClient.ICallbackResult iCallbackResult) {
        synchronized (f23361a) {
            try {
                if (!f23361a.contains(iCallbackResult)) {
                    f23361a.add(iCallbackResult);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected static void a() {
        synchronized (f23362b) {
            f23362b.clear();
        }
    }

    private static void a(Context context, Intent intent, ResolveInfo resolveInfo, boolean z2) {
        try {
            MessageHandleService.a aVar = new MessageHandleService.a(intent, (PushMessageReceiver) C0472r.a(context, resolveInfo.activityInfo.name).newInstance());
            if (z2) {
                MessageHandleService.a(context.getApplicationContext(), aVar);
            } else {
                MessageHandleService.addJob(context.getApplicationContext(), aVar);
            }
            MessageHandleService.a(context, new Intent(context.getApplicationContext(), (Class<?>) MessageHandleService.class));
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
        }
    }

    @Override // com.xiaomi.mipush.sdk.BaseService
    /* renamed from: a, reason: collision with other method in class */
    protected boolean mo116a() {
        ThreadPoolExecutor threadPoolExecutor = f112a;
        return (threadPoolExecutor == null || threadPoolExecutor.getQueue() == null || f112a.getQueue().size() <= 0) ? false : true;
    }

    public static void a(Context context, a aVar) {
        if (aVar instanceof MiPushMessage) {
            a(context, (MiPushMessage) aVar);
            return;
        }
        if (aVar instanceof MiPushCommandMessage) {
            MiPushCommandMessage miPushCommandMessage = (MiPushCommandMessage) aVar;
            String command = miPushCommandMessage.getCommand();
            String str = null;
            if (fy.COMMAND_REGISTER.f444a.equals(command)) {
                List<String> commandArguments = miPushCommandMessage.getCommandArguments();
                if (commandArguments != null && !commandArguments.isEmpty()) {
                    str = commandArguments.get(0);
                }
                a(miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
                return;
            }
            if (!fy.COMMAND_SET_ALIAS.f444a.equals(command) && !fy.COMMAND_UNSET_ALIAS.f444a.equals(command) && !fy.COMMAND_SET_ACCEPT_TIME.f444a.equals(command)) {
                if (fy.COMMAND_SUBSCRIBE_TOPIC.f444a.equals(command)) {
                    List<String> commandArguments2 = miPushCommandMessage.getCommandArguments();
                    if (commandArguments2 != null && !commandArguments2.isEmpty()) {
                        str = commandArguments2.get(0);
                    }
                    a(context, miPushCommandMessage.getCategory(), miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
                    return;
                }
                if (fy.COMMAND_UNSUBSCRIBE_TOPIC.f444a.equals(command)) {
                    List<String> commandArguments3 = miPushCommandMessage.getCommandArguments();
                    if (commandArguments3 != null && !commandArguments3.isEmpty()) {
                        str = commandArguments3.get(0);
                    }
                    b(context, miPushCommandMessage.getCategory(), miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
                    return;
                }
                return;
            }
            a(context, miPushCommandMessage.getCategory(), command, miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), miPushCommandMessage.getCommandArguments());
        }
    }

    private static void b(Context context) {
        try {
            Intent intent = new Intent();
            intent.setPackage(context.getPackageName());
            intent.setAction("action_clicked_activity_finish");
            context.sendBroadcast(intent, d.a(context));
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m92a("PushMessageHandler", "callback sync error" + e2);
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m115b() {
        return f23362b.isEmpty();
    }

    protected static void b(Context context, String str, long j2, String str2, String str3) {
        synchronized (f23362b) {
            try {
                for (MiPushClient.MiPushClientCallback miPushClientCallback : f23362b) {
                    if (a(str, miPushClientCallback.getCategory())) {
                        miPushClientCallback.onUnsubscribeResult(j2, str2, str3);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void a(Context context, MiPushMessage miPushMessage) {
        synchronized (f23362b) {
            try {
                for (MiPushClient.MiPushClientCallback miPushClientCallback : f23362b) {
                    if (a(miPushMessage.getCategory(), miPushClientCallback.getCategory())) {
                        miPushClientCallback.onReceiveMessage(miPushMessage.getContent(), miPushMessage.getAlias(), miPushMessage.getTopic(), miPushMessage.isNotified());
                        miPushClientCallback.onReceiveMessage(miPushMessage);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void a(long j2, String str, String str2) {
        synchronized (f23362b) {
            try {
                Iterator<MiPushClient.MiPushClientCallback> it = f23362b.iterator();
                while (it.hasNext()) {
                    it.next().onInitializeResult(j2, str, str2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected static void a(Context context, String str, long j2, String str2, String str3) {
        synchronized (f23362b) {
            try {
                for (MiPushClient.MiPushClientCallback miPushClientCallback : f23362b) {
                    if (a(str, miPushClientCallback.getCategory())) {
                        miPushClientCallback.onSubscribeResult(j2, str2, str3);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected static void a(Context context, String str, String str2, long j2, String str3, List<String> list) {
        synchronized (f23362b) {
            try {
                for (MiPushClient.MiPushClientCallback miPushClientCallback : f23362b) {
                    if (a(str, miPushClientCallback.getCategory())) {
                        miPushClientCallback.onCommandResult(str2, j2, str3, list);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected static boolean a(String str, String str2) {
        return (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) || TextUtils.equals(str, str2);
    }

    protected static void a(Context context, MiPushCommandMessage miPushCommandMessage) {
        synchronized (f23361a) {
            try {
                for (MiPushClient.ICallbackResult iCallbackResult : f23361a) {
                    if (iCallbackResult instanceof MiPushClient.UPSRegisterCallBack) {
                        MiPushClient.TokenResult tokenResult = new MiPushClient.TokenResult();
                        if (miPushCommandMessage != null && miPushCommandMessage.getCommandArguments() != null && miPushCommandMessage.getCommandArguments().size() > 0) {
                            tokenResult.setResultCode(miPushCommandMessage.getResultCode());
                            tokenResult.setToken(miPushCommandMessage.getCommandArguments().get(0));
                        }
                        iCallbackResult.onResult(tokenResult);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
