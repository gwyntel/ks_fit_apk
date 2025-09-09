package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;

/* loaded from: classes4.dex */
public class v {

    /* renamed from: a, reason: collision with root package name */
    private static u f24629a;

    /* renamed from: a, reason: collision with other field name */
    private static a f1112a;

    public interface a {
        void a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized u m802a(Context context) {
        try {
            u uVar = f24629a;
            if (uVar != null) {
                return uVar;
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_account", 0);
            String string = sharedPreferences.getString(DeviceCommonConstants.KEY_DEVICE_ID, null);
            String string2 = sharedPreferences.getString("token", null);
            String string3 = sharedPreferences.getString(AlinkConstants.KEY_SECURITY, null);
            String string4 = sharedPreferences.getString("app_id", null);
            String string5 = sharedPreferences.getString("app_token", null);
            String string6 = sharedPreferences.getString("package_name", null);
            String string7 = sharedPreferences.getString("device_id", null);
            int i2 = sharedPreferences.getInt("env_type", 1);
            if (!TextUtils.isEmpty(string7) && com.xiaomi.push.i.a(string7)) {
                string7 = com.xiaomi.push.i.g(context);
                sharedPreferences.edit().putString("device_id", string7).commit();
            }
            if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3)) {
                return null;
            }
            String strG = com.xiaomi.push.i.g(context);
            if (!"com.xiaomi.xmsf".equals(context.getPackageName()) && !TextUtils.isEmpty(strG) && !TextUtils.isEmpty(string7) && !string7.equals(strG)) {
                com.xiaomi.channel.commonutils.logger.b.m91a("read_phone_state permission changes.");
            }
            u uVar2 = new u(string, string2, string3, string4, string5, string6, i2);
            f24629a = uVar2;
            return uVar2;
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x031c A[Catch: all -> 0x0043, TryCatch #0 {all -> 0x0043, blocks: (B:4:0x0005, B:6:0x001a, B:8:0x0022, B:10:0x0038, B:14:0x0047, B:18:0x005a, B:22:0x0066, B:26:0x0072, B:27:0x007c, B:33:0x0090, B:35:0x0099, B:37:0x00c1, B:39:0x00cd, B:40:0x00e0, B:42:0x00ea, B:44:0x00f0, B:45:0x0104, B:47:0x010a, B:48:0x010f, B:50:0x0132, B:51:0x013b, B:52:0x0172, B:54:0x0178, B:55:0x017f, B:58:0x018e, B:59:0x01bf, B:61:0x01df, B:64:0x01e6, B:66:0x01fd, B:72:0x020c, B:78:0x022a, B:80:0x0230, B:103:0x02ee, B:107:0x031c, B:109:0x0322, B:110:0x032a, B:104:0x0303, B:76:0x0213, B:30:0x0088), top: B:115:0x0005, inners: #3, #4, #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized com.xiaomi.push.service.u a(android.content.Context r17, java.lang.String r18, java.lang.String r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 819
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.v.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String):com.xiaomi.push.service.u");
    }

    private static String a(Context context, boolean z2) {
        String strA = com.xiaomi.push.service.a.a(context).a();
        String str = z2 ? "/pass/v2/register/encrypt" : "/pass/v2/register";
        if (com.xiaomi.push.aa.b()) {
            return "http://10.38.162.35:9085" + str;
        }
        if (!com.xiaomi.push.m.China.name().equals(strA)) {
            return null;
        }
        return "https://cn.register.xmpush.xiaomi.com" + str;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m805a(Context context) {
        return context.getPackageName().equals("com.xiaomi.xmsf");
    }

    private static void a(Context context, int i2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_account", 0).edit();
        editorEdit.putInt("enc_req_fail_count", i2);
        editorEdit.commit();
    }

    private static int a(Context context) {
        return context.getSharedPreferences("mipush_account", 0).getInt("enc_req_fail_count", 0);
    }

    public static void a(Context context, u uVar) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_account", 0).edit();
        editorEdit.putString(DeviceCommonConstants.KEY_DEVICE_ID, uVar.f1111a);
        editorEdit.putString(AlinkConstants.KEY_SECURITY, uVar.f24625c);
        editorEdit.putString("token", uVar.f24624b);
        editorEdit.putString("app_id", uVar.f24626d);
        editorEdit.putString("package_name", uVar.f24628f);
        editorEdit.putString("app_token", uVar.f24627e);
        editorEdit.putString("device_id", com.xiaomi.push.i.g(context));
        editorEdit.putInt("env_type", uVar.f24623a);
        editorEdit.commit();
        a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m804a(Context context) {
        context.getSharedPreferences("mipush_account", 0).edit().clear().commit();
        f24629a = null;
        a();
    }

    public static void a(a aVar) {
        f1112a = aVar;
    }

    public static void a() {
        a aVar = f1112a;
        if (aVar != null) {
            aVar.a();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m803a(Context context) {
        u uVarM802a = m802a(context);
        if (uVarM802a != null && !TextUtils.isEmpty(uVarM802a.f1111a)) {
            String[] strArrSplit = uVarM802a.f1111a.split("@");
            if (strArrSplit.length > 0) {
                return strArrSplit[0];
            }
        }
        return null;
    }
}
