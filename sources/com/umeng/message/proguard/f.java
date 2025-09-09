package com.umeng.message.proguard;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import com.umeng.ccg.ActionInfo;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.common.UPLog;
import com.umeng.ut.device.UTDevice;
import java.io.Closeable;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class f {

    /* renamed from: d, reason: collision with root package name */
    private static String f22838d;

    /* renamed from: e, reason: collision with root package name */
    private static Boolean f22839e;

    /* renamed from: f, reason: collision with root package name */
    private static Boolean f22840f;

    /* renamed from: c, reason: collision with root package name */
    private static final AtomicInteger f22837c = new AtomicInteger(1);

    /* renamed from: a, reason: collision with root package name */
    public static boolean f22835a = true;

    /* renamed from: b, reason: collision with root package name */
    public static boolean f22836b = true;

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }

    public static boolean b(Context context) {
        Boolean bool = f22839e;
        if (bool != null) {
            return bool.booleanValue();
        }
        String packageName = context.getPackageName();
        boolean z2 = !TextUtils.isEmpty(packageName) && TextUtils.equals(a(context), packageName);
        f22839e = Boolean.valueOf(z2);
        return z2;
    }

    public static boolean c(Context context) {
        Boolean bool = null;
        try {
            bool = (Boolean) ba.a(ba.a(UTDevice.class.getName(), "isNewDid", (Class<?>[]) new Class[]{Context.class}), (Object) null, new Object[]{context});
        } catch (Throwable th) {
            UPLog.e("Helper", th);
        }
        return Boolean.TRUE.equals(bool);
    }

    public static String d(Context context) {
        try {
            return (String) ba.a(ba.a(UTDevice.class.getName(), "getTid", (Class<?>[]) new Class[]{Context.class}), (Object) null, new Object[]{context});
        } catch (Throwable th) {
            UPLog.e("Helper", th);
            return null;
        }
    }

    public static void e(Context context) {
        try {
            ba.a(ba.a(UTDevice.class.getName(), "removeTid", (Class<?>[]) new Class[]{Context.class}), (Object) null, new Object[]{context});
        } catch (Throwable th) {
            UPLog.e("Helper", th);
        }
    }

    public static boolean f(Context context) {
        return UMUtils.checkPermission(context, "android.permission.QUERY_ALL_PACKAGES");
    }

    public static File g(Context context) {
        File file = new File(context.getCacheDir(), "umeng_push");
        if (!file.exists()) {
            file.mkdirs();
        } else if (!file.isDirectory()) {
            file.delete();
            file.mkdirs();
        }
        return file;
    }

    public static void a(Context context, Class<?> cls) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && packageManager.getApplicationEnabledSetting(context.getPackageName()) >= 0) {
                ComponentName componentName = new ComponentName(context, cls);
                if (a(packageManager, componentName)) {
                    return;
                }
                packageManager.setComponentEnabledSetting(componentName, 1, 1);
            }
        } catch (Throwable th) {
            UPLog.e("Helper", th);
        }
    }

    public static boolean b() {
        Boolean bool = f22840f;
        if (bool != null) {
            return bool.booleanValue();
        }
        Boolean bool2 = null;
        try {
            bool2 = (Boolean) ba.a(ba.a(UMConfigure.class.getName(), "isSilentMode", (Class<?>[]) new Class[0]), (Object) null, (Object[]) null);
        } catch (Throwable unused) {
        }
        boolean zBooleanValue = bool2 != null ? bool2.booleanValue() : false;
        f22840f = Boolean.valueOf(zBooleanValue);
        return zBooleanValue;
    }

    public static void c() {
        try {
            Method methodA = ba.a(UMConfigure.class.getName(), "registerActionInfo", (Class<?>[]) new Class[]{ActionInfo.class});
            if (methodA != null) {
                ba.a(methodA, (Object) null, new Object[]{ba.a(r.class.getName(), (Class<?>[]) null, (Object[]) null)});
            }
        } catch (Throwable unused) {
        }
    }

    private static boolean a(PackageManager packageManager, ComponentName componentName) {
        try {
            int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
            return componentEnabledSetting == 1 || componentEnabledSetting == 0;
        } catch (Throwable th) {
            UPLog.e("Helper", th);
            return false;
        }
    }

    public static String a(Context context) {
        if (!TextUtils.isEmpty(f22838d)) {
            return f22838d;
        }
        String currentProcessName = null;
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                currentProcessName = Application.getProcessName();
            }
        } catch (Throwable th) {
            UPLog.e("Helper", th);
        }
        if (TextUtils.isEmpty(currentProcessName)) {
            currentProcessName = UMFrUtils.getCurrentProcessName(context);
        }
        f22838d = currentProcessName;
        return currentProcessName;
    }

    public static String a(Context context, String str) {
        String str2 = context.getCacheDir() + "/umeng_push_inapp/";
        if (str == null) {
            return str2;
        }
        return str2 + str + "/";
    }

    public static int a() {
        return View.generateViewId();
    }

    public static void a(Context context, String str, long j2) {
        try {
            ba.a(ba.a(UTDevice.class.getName(), "resetDid", (Class<?>[]) new Class[]{Context.class, String.class, Long.TYPE}), (Object) null, new Object[]{context, str, Long.valueOf(j2)});
        } catch (Throwable th) {
            UPLog.e("Helper", th);
        }
    }

    public static Object a(Object obj, String str) {
        try {
            return ((PackageManager) obj).getPackageInfo(str, 0);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static boolean a(long j2) {
        Calendar calendar = Calendar.getInstance();
        int i2 = calendar.get(1);
        int i3 = calendar.get(6);
        calendar.setTimeInMillis(j2);
        return i3 == calendar.get(6) && i2 == calendar.get(1);
    }

    public static Bitmap a(File file, int i2, int i3) {
        try {
            if (i3 != 0 && i2 != 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), options);
                float f2 = i2 >= i3 ? options.outWidth / i2 : options.outHeight / i3;
                if (f2 < 1.0f) {
                    f2 = 1.0f;
                }
                options.inJustDecodeBounds = false;
                options.inSampleSize = (int) f2;
                return BitmapFactory.decodeFile(file.getPath(), options);
            }
            return BitmapFactory.decodeFile(file.getPath(), null);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "unknown";
        }
        str.hashCode();
        switch (str) {
            case "HW_TOKEN":
                return "huawei";
            case "MI_TOKEN":
                return "xiaomi";
            case "gcm":
                return "fcm";
            case "HONOR_TOKEN":
                return "honor";
            case "OPPO_TOKEN":
                return "oppo";
            case "VIVO_TOKEN":
                return "vivo";
            case "MZ_TOKEN":
                return "meizu";
            default:
                return str;
        }
    }
}
