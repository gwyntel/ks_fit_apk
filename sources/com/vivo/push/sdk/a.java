package com.vivo.push.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import com.heytap.mcssdk.constant.IntentConstant;
import com.vivo.push.b.x;
import com.vivo.push.c.d;
import com.vivo.push.e;
import com.vivo.push.q;
import com.vivo.push.util.ContextDelegate;
import com.vivo.push.util.p;
import com.vivo.push.util.t;
import com.vivo.push.util.u;
import com.vivo.push.util.z;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public final class a extends q {

    /* renamed from: c, reason: collision with root package name */
    private static a f23207c;

    /* renamed from: e, reason: collision with root package name */
    private static final List<Integer> f23208e = Arrays.asList(3);

    /* renamed from: d, reason: collision with root package name */
    private String f23209d;

    /* renamed from: f, reason: collision with root package name */
    private String f23210f = "";

    private a() {
    }

    public static synchronized a a() {
        try {
            if (f23207c == null) {
                f23207c = new a();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f23207c;
    }

    private boolean c(Intent intent) throws PackageManager.NameNotFoundException {
        String strC = z.c(this.f23198a, "com.vivo.pushservice");
        p.d("CommandWorker", " 配置的验签参数 = ".concat(String.valueOf(strC)));
        if (!TextUtils.equals(strC, "1")) {
            return true;
        }
        String stringExtra = intent.getStringExtra("security_avoid_pull_rsa");
        String stringExtra2 = intent.getStringExtra("security_avoid_rsa_public_key");
        if (TextUtils.isEmpty(stringExtra) || TextUtils.isEmpty(stringExtra2)) {
            p.a("CommandWorker", "!decrypt.equals, so securityContent == " + stringExtra + " or publickKey isempty ");
            return false;
        }
        try {
            if (d.a(this.f23198a).a().a("com.vivo.pushservice".getBytes("UTF-8"), u.a(stringExtra2), Base64.decode(stringExtra, 2))) {
                p.d("CommandWorker", " RSA验签通过  ");
                return true;
            }
        } catch (Exception e2) {
            p.a("CommandWorker", "checkIntentIsSecurity Exception: " + e2.getMessage());
        }
        p.d("CommandWorker", " RSA验签 不通过  ");
        return false;
    }

    private int d(Intent intent) {
        if (!TextUtils.isEmpty(this.f23210f) && this.f23210f.contains("CommandService") && (intent == null || !b(intent) || !c(intent))) {
            p.a("CommandWorker", " !checkIntentIsSecurity(intent)");
            return 2151;
        }
        String packageName = this.f23198a.getPackageName();
        try {
            String stringExtra = intent.getStringExtra("command_type");
            if (!TextUtils.isEmpty(stringExtra) && stringExtra.equals("reflect_receiver")) {
                int intExtra = intent.getIntExtra("command", -1);
                if (intExtra < 0) {
                    intExtra = intent.getIntExtra("method", -1);
                }
                if (f23208e.contains(Integer.valueOf(intExtra)) && t.c(this.f23198a, packageName) && !t.c(this.f23198a)) {
                    p.a("CommandWorker", "METHOD_ON_MESSAGE is not support");
                    return 2153;
                }
                String action = intent.getAction();
                if (!TextUtils.isEmpty(this.f23209d)) {
                    return 0;
                }
                String strA = a(this.f23198a, packageName, action);
                this.f23209d = strA;
                if (!TextUtils.isEmpty(strA)) {
                    return 0;
                }
                p.d("CommandWorker", " reflectReceiver error: receiver for: " + action + " not found, package: " + packageName);
                intent.setPackage(packageName);
                this.f23198a.sendBroadcast(intent);
                return 2152;
            }
            p.a("CommandWorker", "commandTypeStr is not satisfy == ".concat(String.valueOf(stringExtra)));
            return 2151;
        } catch (Exception e2) {
            p.a("CommandWorker", e2);
            return 0;
        }
    }

    public final void b() {
        this.f23209d = null;
    }

    @Override // com.vivo.push.q
    public final void b(Message message) throws IllegalAccessException, NoSuchMethodException, PackageManager.NameNotFoundException, InstantiationException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Context context;
        Intent intent = (Intent) message.obj;
        if (intent == null || (context = this.f23198a) == null) {
            p.d("CommandWorker", " handleMessage error: intent : " + intent + ", mContext: " + this.f23198a);
            return;
        }
        String packageName = context.getPackageName();
        int iD = d(intent);
        if (iD <= 0) {
            try {
                Class<?> cls = Class.forName(this.f23209d);
                Object objNewInstance = cls.getConstructor(null).newInstance(null);
                Method method = cls.getMethod("onReceive", Context.class, Intent.class);
                intent.setClassName(packageName, this.f23209d);
                method.invoke(objNewInstance, ContextDelegate.getContext(this.f23198a).getApplicationContext(), intent);
                return;
            } catch (Exception e2) {
                p.b("CommandWorker", "reflect e: ", e2);
                return;
            }
        }
        x xVar = new x(iD);
        HashMap<String, String> map = new HashMap<>();
        Bundle extras = intent.getExtras();
        map.put(IntentConstant.MESSAGE_ID, String.valueOf(extras != null ? extras.getLong("notify_id", 404000044642424832L) : 404000044642424832L));
        String strB = z.b(this.f23198a, packageName);
        if (!TextUtils.isEmpty(strB)) {
            map.put("remoteAppId", strB);
        }
        xVar.a(map);
        e.a().a(xVar);
    }

    public final void a(String str) {
        this.f23210f = str;
    }

    public final void a(Intent intent) {
        if (intent != null && this.f23198a != null) {
            Message messageObtain = Message.obtain();
            messageObtain.obj = intent;
            a(messageObtain);
        } else {
            p.d("CommandWorker", " sendMessage error: intent : " + intent + ", mContext: " + this.f23198a);
        }
    }

    private static String a(Context context, String str, String str2) {
        List<ResolveInfo> listQueryBroadcastReceivers;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || (listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 64)) == null || listQueryBroadcastReceivers.size() <= 0) {
                return null;
            }
            return listQueryBroadcastReceivers.get(0).activityInfo.name;
        } catch (Exception e2) {
            p.a("CommandWorker", "error  " + e2.getMessage());
            return null;
        }
    }

    private boolean b(Intent intent) {
        String stringExtra = intent.getStringExtra("security_avoid_pull");
        if (!TextUtils.isEmpty(stringExtra)) {
            try {
                String strB = com.vivo.push.util.a.a(this.f23198a).b(stringExtra);
                if ("com.vivo.pushservice".equals(strB)) {
                    return true;
                }
                p.a("CommandWorker", "!decrypt.equals, so decrypt == ".concat(String.valueOf(strB)));
                return false;
            } catch (Exception e2) {
                p.a("CommandWorker", "checkIntentIsSecurity Exception: " + e2.getMessage());
                return false;
            }
        }
        p.a("CommandWorker", "checkIntentIsSecurityTextUtils.isEmpty");
        return true;
    }
}
