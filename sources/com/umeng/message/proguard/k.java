package com.umeng.message.proguard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.taobao.accs.common.Constants;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.message.PushAgent;
import com.umeng.message.common.UPLog;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class k {
    private static void a(final Context context, final String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.umeng.message.proguard.k.1
            @Override // java.lang.Runnable
            public final void run() {
                UPLog.e("SelfCheck", str);
                Toast.makeText(context, str, 1).show();
            }
        });
    }

    @SuppressLint({"QueryPermissionsNeeded"})
    public static boolean a(Context context) {
        Class<UMEnvelopeBuild> cls;
        boolean z2;
        boolean z3;
        boolean z4;
        Method declaredMethod = null;
        try {
            cls = UMEnvelopeBuild.class;
            boolean z5 = UMEnvelopeBuild.transmissionSendFlag;
        } catch (Throwable unused) {
            cls = null;
        }
        if (cls == null) {
            UPLog.e("SelfCheck", "--->>> common sdk版本不匹配，请确认！<<<--- ");
        } else {
            try {
                declaredMethod = cls.getDeclaredMethod("buildEnvelopeWithExtHeader", Context.class, JSONObject.class, JSONObject.class, String.class, String.class, String.class);
            } catch (Throwable unused2) {
            }
            if (declaredMethod == null) {
                UPLog.e("SelfCheck", "--->>> common sdk版本不匹配，请确认！<<<--- ");
            }
        }
        try {
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (TextUtils.isEmpty(PushAgent.getInstance(context).getMessageAppkey())) {
            a(context, "please set umeng appkey!");
            return false;
        }
        if (TextUtils.isEmpty(PushAgent.getInstance(context).getMessageSecret())) {
            a(context, "please set umeng message secret!");
            return false;
        }
        Intent intent = new Intent();
        intent.setPackage(context.getPackageName());
        intent.setAction(Constants.ACTION_START_SERVICE);
        Iterator<ResolveInfo> it = context.getPackageManager().queryIntentServices(intent, 64).iterator();
        while (it.hasNext()) {
            if (it.next().serviceInfo.name.equals(com.taobao.accs.utl.j.channelService)) {
                Intent intent2 = new Intent();
                intent2.setPackage(context.getPackageName());
                intent2.setAction("com.taobao.accs.intent.action.ELECTION");
                Iterator<ResolveInfo> it2 = context.getPackageManager().queryIntentServices(intent2, 64).iterator();
                while (it2.hasNext()) {
                    if (it2.next().serviceInfo.name.equals(com.taobao.accs.utl.j.channelService)) {
                        Intent intent3 = new Intent();
                        intent3.setPackage(context.getPackageName());
                        intent3.setAction(Constants.ACTION_RECEIVE);
                        Iterator<ResolveInfo> it3 = context.getPackageManager().queryIntentServices(intent3, 64).iterator();
                        while (it3.hasNext()) {
                            if (it3.next().serviceInfo.name.equals(com.taobao.accs.utl.j.msgService)) {
                                Intent intent4 = new Intent();
                                intent4.setPackage(context.getPackageName());
                                intent4.setAction(Constants.ACTION_RECEIVE);
                                Iterator<ResolveInfo> it4 = context.getPackageManager().queryIntentServices(intent4, 64).iterator();
                                while (it4.hasNext()) {
                                    if (it4.next().serviceInfo.name.equals("org.android.agoo.accs.AgooService")) {
                                        Intent intent5 = new Intent();
                                        intent5.setPackage(context.getPackageName());
                                        intent5.setAction(AgooConstants.INTENT_FROM_AGOO_MESSAGE);
                                        Iterator<ResolveInfo> it5 = context.getPackageManager().queryIntentServices(intent5, 64).iterator();
                                        while (it5.hasNext()) {
                                            if (it5.next().serviceInfo.name.equals("com.umeng.message.component.UmengIntentService")) {
                                                Intent intent6 = new Intent();
                                                intent6.setClassName(context.getPackageName(), "com.umeng.message.component.UmengNotificationReceiver");
                                                for (ResolveInfo resolveInfo : context.getPackageManager().queryBroadcastReceivers(intent6, 65536)) {
                                                    if (resolveInfo.activityInfo.name.equals("com.umeng.message.component.UmengNotificationReceiver") && TextUtils.equals(resolveInfo.activityInfo.processName, context.getPackageName())) {
                                                        Intent intent7 = new Intent("com.umeng.message.action");
                                                        intent7.setPackage(context.getPackageName());
                                                        for (ResolveInfo resolveInfo2 : context.getPackageManager().queryIntentServices(intent7, 64)) {
                                                            if (resolveInfo2.serviceInfo.name.equals("com.umeng.message.component.UmengMessageHandlerService") && TextUtils.equals(context.getPackageName(), resolveInfo2.serviceInfo.processName)) {
                                                                Intent intent8 = new Intent();
                                                                intent8.setPackage(context.getPackageName());
                                                                intent8.setAction(AgooConstants.BINDER_MSGRECEIVER_ACTION);
                                                                Iterator<ResolveInfo> it6 = context.getPackageManager().queryIntentServices(intent8, 64).iterator();
                                                                while (it6.hasNext()) {
                                                                    if (it6.next().serviceInfo.name.equals("com.umeng.message.component.UmengMessageReceiverService")) {
                                                                        String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
                                                                        if (strArr != null) {
                                                                            z2 = false;
                                                                            z3 = false;
                                                                            z4 = false;
                                                                            for (String str : strArr) {
                                                                                if ("android.permission.INTERNET".equals(str)) {
                                                                                    z3 = true;
                                                                                } else if ("android.permission.ACCESS_WIFI_STATE".equals(str)) {
                                                                                    z4 = true;
                                                                                } else if ("android.permission.ACCESS_NETWORK_STATE".equals(str)) {
                                                                                    z2 = true;
                                                                                }
                                                                            }
                                                                        } else {
                                                                            z2 = false;
                                                                            z3 = false;
                                                                            z4 = false;
                                                                        }
                                                                        if (z3 && z4 && z2) {
                                                                            return true;
                                                                        }
                                                                        a(context, "please add required permission in AndroidManifest!");
                                                                        return false;
                                                                    }
                                                                }
                                                                a(context, "please check UmengMessageReceiverService in AndroidManifest!");
                                                                return false;
                                                            }
                                                        }
                                                        a(context, "please check UmengMessageHandlerService in AndroidManifest!");
                                                        return false;
                                                    }
                                                }
                                                a(context, "please check UmengNotificationReceiver in AndroidManifest!");
                                                return false;
                                            }
                                        }
                                        a(context, "Please check UmengIntentService in AndroidManifest!");
                                        return false;
                                    }
                                }
                                a(context, "Please check AgooService in AndroidManifest!");
                                return false;
                            }
                        }
                        a(context, "please check MsgDistributeService in AndroidManifest!");
                        return false;
                    }
                }
                a(context, "please check ChannelService in AndroidManifest!");
                return false;
            }
        }
        a(context, "please check ChannelService in AndroidManifest!");
        return false;
    }
}
