package com.vivo.push.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.database.Cursor;
import android.text.TextUtils;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class t {

    /* renamed from: a, reason: collision with root package name */
    private static Boolean f23259a;

    /* renamed from: b, reason: collision with root package name */
    private static String f23260b;

    public static com.vivo.push.model.b a(Context context) throws PackageManager.NameNotFoundException {
        com.vivo.push.model.b bVarF;
        com.vivo.push.model.b bVarF2;
        Context applicationContext = ContextDelegate.getContext(context).getApplicationContext();
        com.vivo.push.model.b bVarD = d(applicationContext);
        if (bVarD != null) {
            p.d("PushPackageUtils", "get system push info :".concat(String.valueOf(bVarD)));
            return bVarD;
        }
        List<String> listE = e(applicationContext);
        com.vivo.push.model.b bVarF3 = f(applicationContext, applicationContext.getPackageName());
        if (listE.size() <= 0) {
            if (bVarF3 != null && bVarF3.d()) {
                bVarD = bVarF3;
            }
            p.a("PushPackageUtils", "findAllPushPackages error: find no package!");
        } else {
            com.vivo.push.model.b bVar = null;
            String strA = y.b(applicationContext).a("com.vivo.push.cur_pkg", null);
            if (TextUtils.isEmpty(strA) || !a(applicationContext, strA, "com.vivo.pushservice.action.METHOD") || (bVarF = f(applicationContext, strA)) == null || !bVarF.d()) {
                bVarF = null;
            }
            if (bVarF3 == null || !bVarF3.d()) {
                bVarF3 = null;
            }
            if (bVarF == null) {
                bVarF = null;
            }
            if (bVarF3 == null || (bVarF != null && (!bVarF3.c() ? !(bVarF.c() || bVarF3.b() > bVarF.b()) : !(bVarF.c() && bVarF3.b() > bVarF.b())))) {
                bVarF3 = bVarF;
            }
            HashMap map = new HashMap();
            if (bVarF3 == null) {
                bVarF3 = null;
            } else if (bVarF3.c()) {
                bVar = bVarF3;
                bVarF3 = null;
            }
            int size = listE.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str = listE.get(i2);
                if (!TextUtils.isEmpty(str) && (bVarF2 = f(applicationContext, str)) != null) {
                    map.put(str, bVarF2);
                    if (bVarF2.d()) {
                        if (bVarF2.c()) {
                            if (bVar == null || bVarF2.b() > bVar.b()) {
                                bVar = bVarF2;
                            }
                        } else if (bVarF3 == null || bVarF2.b() > bVarF3.b()) {
                            bVarF3 = bVarF2;
                        }
                    }
                }
            }
            if (bVarF3 != null) {
                bVarD = bVarF3;
            } else {
                p.d("PushPackageUtils", "findSuitablePushPackage, all push app in balck list.");
                bVarD = bVar;
            }
        }
        if (bVarD == null) {
            p.b(applicationContext, "查找最优包为空!");
            p.d("PushPackageUtils", "finSuitablePushPackage is null");
        } else if (bVarD.c()) {
            p.a(applicationContext, "查找最优包为:" + bVarD.a() + "(" + bVarD.b() + ", Black)");
            p.d("PushPackageUtils", "finSuitablePushPackage" + bVarD.a() + "(" + bVarD.b() + ", Black)");
        } else {
            p.a(applicationContext, "查找最优包为:" + bVarD.a() + "(" + bVarD.b() + ")");
            p.d("PushPackageUtils", "finSuitablePushPackage" + bVarD.a() + "(" + bVarD.b() + ")");
        }
        return bVarD;
    }

    public static String b(Context context) {
        String string;
        Cursor cursorQuery;
        if (!TextUtils.isEmpty(f23260b)) {
            return f23260b;
        }
        Cursor cursor = null;
        try {
            try {
                try {
                    cursorQuery = context.getContentResolver().query(com.vivo.push.p.f23194a, null, null, null, null);
                } catch (Exception e2) {
                    e = e2;
                    string = null;
                }
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e3) {
            p.a("PushPackageUtils", "close", e3);
        }
        try {
            if (cursorQuery != null) {
                boolean z2 = false;
                string = null;
                while (cursorQuery.moveToNext()) {
                    try {
                        if ("pushPkgName".equals(cursorQuery.getString(cursorQuery.getColumnIndex("name")))) {
                            string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                        } else if ("pushEnable".equals(cursorQuery.getString(cursorQuery.getColumnIndex("name")))) {
                            z2 = Boolean.parseBoolean(cursorQuery.getString(cursorQuery.getColumnIndex("value")));
                        }
                    } catch (Exception e4) {
                        e = e4;
                    }
                }
                f23260b = string;
                if (TextUtils.isEmpty(string)) {
                    try {
                        cursorQuery.close();
                    } catch (Exception e5) {
                        p.a("PushPackageUtils", "close", e5);
                    }
                    return null;
                }
                if (z2) {
                    cursorQuery.close();
                    return string;
                }
                try {
                    cursorQuery.close();
                } catch (Exception e6) {
                    p.a("PushPackageUtils", "close", e6);
                }
                return null;
            }
            try {
                p.a("PushPackageUtils", "cursor is null");
                if (cursorQuery != null) {
                    try {
                        cursorQuery.close();
                    } catch (Exception e7) {
                        p.a("PushPackageUtils", "close", e7);
                    }
                }
                return null;
            } catch (Exception e8) {
                e = e8;
                string = null;
            }
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
            }
            return string;
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e9) {
                    p.a("PushPackageUtils", "close", e9);
                }
            }
            throw th;
        }
        p.a("PushPackageUtils", "getSystemPush", e);
    }

    public static boolean c(Context context, String str) {
        return a(context, str, "com.vivo.pushclient.action.RECEIVE");
    }

    private static com.vivo.push.model.b d(Context context) throws PackageManager.NameNotFoundException {
        String strB = b(context);
        ApplicationInfo applicationInfo = null;
        if (TextUtils.isEmpty(strB)) {
            return null;
        }
        com.vivo.push.model.b bVar = new com.vivo.push.model.b(strB);
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(strB, 128);
            if (packageInfo != null) {
                bVar.a(packageInfo.versionCode);
                bVar.a(packageInfo.versionName);
                applicationInfo = packageInfo.applicationInfo;
            }
            if (applicationInfo != null) {
                bVar.a(z.a(context, strB));
            }
            bVar.a(a(context, bVar.b()));
            bVar.b(a(context, strB));
            return bVar;
        } catch (Exception e2) {
            e2.printStackTrace();
            p.b("PushPackageUtils", "PackageManager NameNotFoundException is null", e2);
            return null;
        }
    }

    public static boolean e(Context context, String str) {
        return a(context, str, "com.vivo.pushservice.action.METHOD");
    }

    private static com.vivo.push.model.b f(Context context, String str) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo;
        if (!TextUtils.isEmpty(str) && (a(context, str, "com.vivo.pushservice.action.METHOD") || a(context, str, "com.vivo.pushservice.action.RECEIVE"))) {
            com.vivo.push.model.b bVar = new com.vivo.push.model.b(str);
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 128);
                if (packageInfo != null) {
                    bVar.a(packageInfo.versionCode);
                    bVar.a(packageInfo.versionName);
                    applicationInfo = packageInfo.applicationInfo;
                } else {
                    applicationInfo = null;
                }
                if (applicationInfo != null) {
                    bVar.a(z.a(context, str));
                }
                bVar.b(a(context, str));
                bVar.a(a(context, bVar.b()));
                return bVar;
            } catch (Exception e2) {
                p.a("PushPackageUtils", "getPushPackageInfo exception: ", e2);
            }
        }
        return null;
    }

    private static String g(Context context, String str) {
        if (!TextUtils.isEmpty(str) && context != null) {
            try {
                Signature[] signatureArr = context.getPackageManager().getPackageInfo(str, 64).signatures;
                byte[] bArrDigest = MessageDigest.getInstance("SHA256").digest(signatureArr[0].toByteArray());
                StringBuffer stringBuffer = new StringBuffer();
                for (byte b2 : bArrDigest) {
                    String upperCase = Integer.toHexString(b2 & 255).toUpperCase(Locale.US);
                    if (upperCase.length() == 1) {
                        stringBuffer.append("0");
                    }
                    stringBuffer.append(upperCase);
                }
                return stringBuffer.toString();
            } catch (Exception e2) {
                p.a("PushPackageUtils", " getSignatureSHA exception ".concat(String.valueOf(e2)));
            }
        }
        return null;
    }

    public static boolean c(Context context) {
        ProviderInfo providerInfoResolveContentProvider;
        Boolean bool = f23259a;
        if (bool != null) {
            return bool.booleanValue();
        }
        String str = null;
        if (context != null && !TextUtils.isEmpty("com.vivo.push.sdk.service.SystemPushConfig") && (providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider("com.vivo.push.sdk.service.SystemPushConfig", 128)) != null) {
            str = providerInfoResolveContentProvider.packageName;
        }
        boolean zEquals = "BCC35D4D3606F154F0402AB7634E8490C0B244C2675C3C6238986987024F0C02".equals(g(context, str));
        f23259a = Boolean.valueOf(zEquals);
        return zEquals;
    }

    private static List<String> e(Context context) {
        List<ResolveInfo> listQueryIntentServices;
        g.a("findAllCoreClientPush");
        ArrayList arrayList = new ArrayList();
        try {
            listQueryIntentServices = context.getPackageManager().queryIntentServices(new Intent("com.vivo.pushservice.action.PUSH_SERVICE"), 576);
        } catch (Exception unused) {
            listQueryIntentServices = null;
        }
        if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
            int size = listQueryIntentServices.size();
            for (int i2 = 0; i2 < size; i2++) {
                ResolveInfo resolveInfo = listQueryIntentServices.get(i2);
                if (resolveInfo != null) {
                    String str = resolveInfo.serviceInfo.packageName;
                    if (!TextUtils.isEmpty(str)) {
                        arrayList.add(str);
                    }
                }
            }
        }
        if (arrayList.size() <= 0) {
            p.d("PushPackageUtils", "get all push packages is null");
        }
        return arrayList;
    }

    public static boolean d(Context context, String str) {
        return a(context, str, "com.vivo.pushservice.action.RECEIVE");
    }

    public static int b(Context context, String str) {
        int i2 = a(context, str, "com.vivo.pushservice.action.RECEIVE") ? 0 : -1;
        if (a(context, str, "com.vivo.pushclient.action.RECEIVE")) {
            return 1;
        }
        return i2;
    }

    public static boolean a(Context context, String str) {
        ServiceInfo serviceInfo;
        if (!TextUtils.isEmpty(str) && context != null) {
            Intent intent = new Intent("com.vivo.pushservice.action.PUSH_SERVICE");
            intent.setPackage(str);
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 576);
            if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
                int size = listQueryIntentServices.size();
                boolean z2 = false;
                for (int i2 = 0; i2 < size; i2++) {
                    ResolveInfo resolveInfo = listQueryIntentServices.get(i2);
                    if (resolveInfo != null && (serviceInfo = resolveInfo.serviceInfo) != null) {
                        String str2 = serviceInfo.name;
                        boolean z3 = serviceInfo.exported;
                        if ("com.vivo.push.sdk.service.PushService".equals(str2) && z3) {
                            boolean z4 = resolveInfo.serviceInfo.enabled;
                            int componentEnabledSetting = packageManager.getComponentEnabledSetting(new ComponentName(str, "com.vivo.push.sdk.service.PushService"));
                            z2 = componentEnabledSetting == 1 || (componentEnabledSetting == 0 && z4);
                        }
                    }
                }
                return z2;
            }
            p.a("PushPackageUtils", "isEnablePush error: can not find push service.");
        }
        return false;
    }

    private static boolean a(Context context, long j2) {
        com.vivo.push.cache.d dVarA = com.vivo.push.cache.b.a().a(context);
        if (dVarA != null) {
            return dVarA.isInBlackList(j2);
        }
        return false;
    }

    private static boolean a(Context context, String str, String str2) {
        List<ResolveInfo> listQueryBroadcastReceivers;
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        try {
            listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 576);
        } catch (Exception unused) {
            listQueryBroadcastReceivers = null;
        }
        return listQueryBroadcastReceivers != null && listQueryBroadcastReceivers.size() > 0;
    }
}
