package com.huawei.hms.hmsscankit;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import com.huawei.hms.common.Preconditions;
import com.huawei.hms.feature.dynamic.DynamicModule;
import com.huawei.hms.hmsscankit.api.IRemoteCreator;
import com.huawei.hms.scankit.p.b4;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.y3;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f16573a = false;

    /* renamed from: b, reason: collision with root package name */
    public static boolean f16574b = false;

    /* renamed from: c, reason: collision with root package name */
    public static boolean f16575c = false;

    /* renamed from: d, reason: collision with root package name */
    static int f16576d = Integer.MIN_VALUE;

    /* renamed from: e, reason: collision with root package name */
    static int f16577e = Integer.MIN_VALUE;

    /* renamed from: f, reason: collision with root package name */
    private static volatile Context f16578f;

    public static int a(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getInt("huawei_module_scankit_local", Integer.MAX_VALUE);
        } catch (PackageManager.NameNotFoundException unused) {
            o4.b("exception", "NameNotFoundException");
            return Integer.MAX_VALUE;
        }
    }

    public static void b(Context context) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        context.getClassLoader().loadClass("com.huawei.hms.feature.DynamicModuleInitializer").getDeclaredMethod("initializeModule", Context.class).invoke(null, context);
    }

    static IRemoteCreator c(Context context) throws IllegalAccessException, InstantiationException {
        Preconditions.checkNotNull(context);
        try {
            Context contextE = e(context);
            if (contextE == null) {
                return null;
            }
            Object objNewInstance = contextE.getClassLoader().loadClass("com.huawei.hms.scankit.Creator").newInstance();
            if (objNewInstance instanceof IBinder) {
                return IRemoteCreator.Stub.asInterface((IBinder) objNewInstance);
            }
            return null;
        } catch (ClassNotFoundException unused) {
            o4.b("exception", "ClassNotFoundException");
            return null;
        } catch (IllegalAccessException unused2) {
            o4.b("exception", "IllegalAccessException");
            return null;
        } catch (InstantiationException unused3) {
            o4.b("exception", "InstantiationException");
            return null;
        } catch (NoSuchMethodException unused4) {
            o4.b("exception", "NoSuchMethodException");
            return null;
        } catch (InvocationTargetException unused5) {
            o4.b("exception", "InvocationTargetException");
            return null;
        }
    }

    static IRemoteCreator d(Context context) throws IllegalAccessException, InstantiationException {
        Preconditions.checkNotNull(context);
        try {
            Object objNewInstance = context.getClassLoader().loadClass("com.huawei.hms.scankit.Creator").newInstance();
            if (objNewInstance instanceof IBinder) {
                return IRemoteCreator.Stub.asInterface((IBinder) objNewInstance);
            }
        } catch (ClassNotFoundException unused) {
            o4.b("exception", "ClassNotFoundException");
        } catch (IllegalAccessException unused2) {
            o4.b("exception", "IllegalAccessException");
        } catch (InstantiationException unused3) {
            o4.b("exception", "InvocationTargetException");
        }
        return null;
    }

    public static Context e(Context context) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
        Log.i("ScankitSDK", "ScankitSDK Version: SCAN2.12.0.301");
        b(context);
        if (f16578f != null && !a()) {
            Log.i("ScankitSDK", "context has been inited");
            return f16578f;
        }
        try {
            b4.f17005a = false;
            if (f16576d == Integer.MIN_VALUE) {
                f16576d = a(context);
            }
            Context moduleContext = DynamicModule.load(context.getApplicationContext(), DynamicModule.PREFER_REMOTE, "huawei_module_scankit").getModuleContext();
            if (f16577e == Integer.MIN_VALUE) {
                f16577e = DynamicModule.getRemoteVersion(context.getApplicationContext(), "huawei_module_scankit");
            }
            if (f16576d >= 21200300) {
                f16575c = true;
            } else {
                f16575c = false;
            }
            String strB = y3.b(context);
            o4.d("ScankitSDK", "local Version: " + f16576d + " remote Version: " + f16577e);
            if (!a() && f16576d < f16577e && !strB.equals("com.huawei.scanner")) {
                f16573a = true;
                b4.f17005a = true;
                b4.f17006b = String.valueOf(f16577e);
                Log.i("ScankitSDK", "use remote scankit " + f16577e);
                f16578f = moduleContext;
                return f16578f;
            }
            o4.d("ScankitSDK", "use local Version: " + f16576d);
            b(context);
            f16573a = false;
            f16578f = null;
            return context;
        } catch (DynamicModule.LoadingException e2) {
            o4.b("ScankitSDK", "ClassNotFoundException exception " + e2.getMessage());
            b(context);
            return context;
        } catch (ClassNotFoundException unused) {
            o4.b("ScankitSDK", "ClassNotFoundException exception");
            b(context);
            return context;
        } catch (IllegalAccessException unused2) {
            o4.b("ScankitSDK", "IllegalAccessException exception");
            b(context);
            return context;
        } catch (NoSuchMethodException unused3) {
            o4.b("ScankitSDK", "NoSuchMethodException exception");
            b(context);
            return context;
        } catch (RuntimeException unused4) {
            o4.b("ScankitSDK", "other RuntimeException exception");
            b(context);
            return context;
        } catch (InvocationTargetException unused5) {
            o4.b("ScankitSDK", "InvocationTargetException exception");
            b(context);
            return context;
        } catch (Exception unused6) {
            o4.b("ScankitSDK", "Exception exception");
            b(context);
            return context;
        } catch (Throwable unused7) {
            o4.b("ScankitSDK", "Throwable exception");
            b(context);
            return context;
        }
    }

    public static boolean a() {
        return f16575c && f16573a && f16574b;
    }
}
