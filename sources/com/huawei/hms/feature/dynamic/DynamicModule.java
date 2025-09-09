package com.huawei.hms.feature.dynamic;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.huawei.hms.common.util.Logger;
import com.huawei.hms.feature.dynamic.IDynamicInstall;
import com.huawei.hms.feature.dynamic.IDynamicLoader;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class DynamicModule {
    public static final int MODULE_INTER_ERROR = 3;
    public static final int MODULE_NEED_UPDATE = 2;
    public static final int MODULE_NORMAL = 0;
    public static final int MODULE_NOT_EXIST = 1;
    public static final int MODULE_NOT_PRESET_HSF = 5;
    public static final int MODULE_NOT_READY = 4;

    /* renamed from: c, reason: collision with root package name */
    public static final int f16066c = 256;

    /* renamed from: d, reason: collision with root package name */
    public static final int f16067d = -100;

    /* renamed from: i, reason: collision with root package name */
    public static int f16072i = 0;

    /* renamed from: j, reason: collision with root package name */
    public static final int f16073j = 1;

    /* renamed from: k, reason: collision with root package name */
    public static final int f16074k = 2;

    /* renamed from: l, reason: collision with root package name */
    public static int f16075l;

    /* renamed from: a, reason: collision with root package name */
    public Context f16078a;
    public static final VersionPolicy PREFER_REMOTE = new com.huawei.hms.feature.dynamic.e.e();
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new com.huawei.hms.feature.dynamic.e.c();
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new com.huawei.hms.feature.dynamic.e.d();
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new com.huawei.hms.feature.dynamic.e.e();

    /* renamed from: b, reason: collision with root package name */
    public static final String f16065b = DynamicModule.class.getSimpleName();

    /* renamed from: e, reason: collision with root package name */
    public static final ThreadLocal<HashMap<String, Boolean>> f16068e = new ThreadLocal<>();

    /* renamed from: f, reason: collision with root package name */
    public static final ThreadLocal<HashMap<String, String>> f16069f = new ThreadLocal<>();

    /* renamed from: g, reason: collision with root package name */
    public static final ThreadLocal<HashMap<String, IDynamicLoader>> f16070g = new ThreadLocal<>();

    /* renamed from: h, reason: collision with root package name */
    public static final ThreadLocal<HashMap<String, ClassLoader>> f16071h = new ThreadLocal<>();

    /* renamed from: m, reason: collision with root package name */
    public static HashMap<String, Boolean> f16076m = new HashMap<>();

    /* renamed from: n, reason: collision with root package name */
    public static HashMap<String, Boolean> f16077n = new HashMap<>();

    public static class DynamicLoaderClassLoader {

        /* renamed from: a, reason: collision with root package name */
        public static HashMap<String, ClassLoader> f16079a = new HashMap<>();

        public static ClassLoader getsClassLoader(String str) {
            return f16079a.get(str);
        }

        public static void setsClassLoader(String str, ClassLoader classLoader) {
            f16079a.put(str, classLoader);
        }
    }

    public static class LoadingException extends Exception {

        /* renamed from: a, reason: collision with root package name */
        public Bundle f16080a;

        public LoadingException(String str) {
            super(str);
        }

        public Bundle getBundle() {
            return this.f16080a;
        }

        public LoadingException(String str, Bundle bundle) {
            super(str);
            this.f16080a = bundle;
        }
    }

    public interface VersionPolicy {
        Bundle getModuleInfo(Context context, String str) throws LoadingException;
    }

    public static class a extends HashMap<String, ClassLoader> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f16081a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ ClassLoader f16082b;

        public a(String str, ClassLoader classLoader) {
            this.f16081a = str;
            this.f16082b = classLoader;
            put(str, classLoader);
        }
    }

    public static class b extends HashMap<String, ClassLoader> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f16083a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ ClassLoader f16084b;

        public b(String str, ClassLoader classLoader) {
            this.f16083a = str;
            this.f16084b = classLoader;
            put(str, classLoader);
        }
    }

    public static class c implements Callable<Bundle> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f16085a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Bundle f16086b;

        public c(Context context, Bundle bundle) {
            this.f16085a = context;
            this.f16086b = bundle;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public Bundle call() {
            try {
                return DynamicModule.queryHMSModuleBundle(this.f16085a, com.huawei.hms.feature.dynamic.b.f16121e, this.f16086b);
            } catch (Exception e2) {
                Logger.w(DynamicModule.f16065b, "Query provider error.", e2);
                return new Bundle();
            }
        }
    }

    public static class d extends HashMap<String, Boolean> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f16087a;

        public d(String str) {
            this.f16087a = str;
            put(str, Boolean.TRUE);
        }
    }

    public static class e extends HashMap<String, String> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f16088a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f16089b;

        public e(String str, String str2) {
            this.f16088a = str;
            this.f16089b = str2;
            put(str, str2);
        }
    }

    public static class f extends HashMap<String, IDynamicLoader> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f16090a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ IBinder f16091b;

        public f(String str, IBinder iBinder) {
            this.f16090a = str;
            this.f16091b = iBinder;
            put(str, IDynamicLoader.Stub.asInterface(iBinder));
        }
    }

    public static class g extends Exception {
        public g(String str) {
            super(str);
        }

        public /* synthetic */ g(String str, a aVar) {
            this(str);
        }
    }

    public DynamicModule(Context context) {
        this.f16078a = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x008e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0106  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(android.content.Context r9, java.lang.String r10, android.os.Bundle r11) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.feature.dynamic.DynamicModule.a(android.content.Context, java.lang.String, android.os.Bundle):int");
    }

    public static Bundle b(Context context, String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, LoadingException, InvocationTargetException {
        Method declaredMethod;
        ClassLoader classLoader;
        boolean z2 = true;
        try {
            try {
                Class<?> clsA = a(context);
                Method declaredMethod2 = clsA.getDeclaredMethod("getsClassLoader", String.class);
                declaredMethod = clsA.getDeclaredMethod("setsClassLoader", String.class, ClassLoader.class);
                classLoader = (ClassLoader) declaredMethod2.invoke(null, str);
            } catch (LoadingException e2) {
                throw e2;
            }
        } catch (Exception e3) {
            Logger.w(f16065b, "failed to load.", e3);
        }
        if (classLoader == null) {
            try {
                String str2 = f16065b;
                Logger.i(str2, "No available cached loader, query remote.");
                Bundle bundleC = c(context, str);
                synchronized (DynamicModule.class) {
                    try {
                        HashMap<String, String> map = f16069f.get();
                        Objects.requireNonNull(map);
                        String str3 = map.get(str);
                        if (TextUtils.isEmpty(str3)) {
                            return bundleC;
                        }
                        if (!com.huawei.hms.feature.dynamic.f.c.a(context, str3)) {
                            Logger.w(str2, "The loaderPath is invalid.");
                            throw new LoadingException("getHMSModuleInfo: checkPathValidity failed.");
                        }
                        com.huawei.hms.feature.dynamic.e.a aVar = new com.huawei.hms.feature.dynamic.e.a(str3, ClassLoader.getSystemClassLoader());
                        a(str, aVar);
                        declaredMethod.invoke(null, str, aVar);
                        f16068e.set(new d(str));
                        return bundleC;
                    } finally {
                    }
                }
            } catch (g unused) {
            }
        } else if (classLoader != ClassLoader.getSystemClassLoader()) {
            Logger.i(f16065b, "Cached loader is available, ready to use it.");
            try {
                a(str, classLoader);
            } catch (LoadingException e4) {
                Logger.w(f16065b, "Get loader interface failed.", e4);
            }
            HashMap<String, Boolean> map2 = new HashMap<>();
            map2.put(str, Boolean.valueOf(z2));
            f16068e.set(map2);
            return new Bundle();
        }
        z2 = false;
        HashMap<String, Boolean> map22 = new HashMap<>();
        map22.put(str, Boolean.valueOf(z2));
        f16068e.set(map22);
        return new Bundle();
    }

    public static Bundle c(Context context, String str) throws g, LoadingException {
        try {
            Bundle bundleQueryHMSModuleBundle = queryHMSModuleBundle(context, str);
            String string = bundleQueryHMSModuleBundle.getString(com.huawei.hms.feature.dynamic.b.f16134r);
            if (!TextUtils.isEmpty(string) && new File(string).exists()) {
                f16069f.set(new e(str, string));
                Logger.i(f16065b, "Query remote version by module name:" + str + " success.");
                return bundleQueryHMSModuleBundle;
            }
            Logger.w(f16065b, "The loader_path:" + string + " in query bundle is not available,change the module version to:-100");
            bundleQueryHMSModuleBundle.putInt(com.huawei.hms.feature.dynamic.b.f16127k, -100);
            return bundleQueryHMSModuleBundle;
        } catch (LoadingException e2) {
            throw e2;
        } catch (Exception unused) {
            throw new g("failed to Query remote version.", null);
        }
    }

    public static void enable3rdPhone(String str, boolean z2) {
        f16076m.put(str, Boolean.valueOf(z2));
    }

    public static void enableLowEMUI(String str, boolean z2) {
        f16077n.put(str, Boolean.valueOf(z2));
    }

    public static Set<String> getInstalledModuleInfo() {
        return com.huawei.hms.feature.dynamic.d.a().f16150a;
    }

    public static Bundle getLocalModuleInfo(Context context, String str) {
        int localVersion = getLocalVersion(context, str);
        Bundle bundle = new Bundle();
        bundle.putString(com.huawei.hms.feature.dynamic.b.f16126j, str);
        bundle.putInt(com.huawei.hms.feature.dynamic.b.f16128l, localVersion);
        return bundle;
    }

    public static int getLocalVersion(Context context, String str) {
        if (context == null || str.length() == 0 || str.length() > 256) {
            Logger.e(f16065b, "Invalid context or moduleName.");
            return 0;
        }
        try {
            String str2 = AssetLoadManager.f16044d + str + ".ModuleDescriptor";
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            return context.getClassLoader().loadClass(str2).getDeclaredField("MODULE_VERSION").getInt(null);
        } catch (ClassNotFoundException unused) {
            Logger.w(f16065b, "Cannot find the class of module descriptor for " + str);
            return 0;
        } catch (Exception e2) {
            Logger.w(f16065b, "Get local module info failed.", e2);
            return 0;
        }
    }

    public static Bundle getRemoteModuleInfo(Context context, String str) throws LoadingException {
        try {
        } catch (LoadingException e2) {
            throw e2;
        } catch (Exception e3) {
            Logger.w(f16065b, "Get remote module info for " + str + " failed.", e3);
        }
        synchronized (DynamicModule.class) {
            try {
                ThreadLocal<HashMap<String, Boolean>> threadLocal = f16068e;
                if (threadLocal.get() == null || threadLocal.get().get(str) == null || !threadLocal.get().get(str).booleanValue()) {
                    Bundle bundleB = b(context, str);
                    if (bundleB.getInt(com.huawei.hms.feature.dynamic.b.f16127k) > 0) {
                        return bundleB;
                    }
                }
                if (threadLocal.get().get(str).booleanValue()) {
                    try {
                        return c(context, str);
                    } catch (g e4) {
                        Logger.w(f16065b, "Query remote module info in HMS failed.", e4);
                    }
                }
                return new Bundle();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static int getRemoteVersion(Context context, String str) throws LoadingException {
        try {
            Bundle bundleC = c(context, str);
            if (bundleC != null && !bundleC.isEmpty()) {
                return bundleC.getInt(com.huawei.hms.feature.dynamic.b.f16127k);
            }
            Logger.w(f16065b, "Query remote module:" + str + " info failed.");
            throw new LoadingException("Query remote module info failed: null or empty.");
        } catch (g e2) {
            Logger.w(f16065b, "Query remote module:" + str + " exception:" + e2);
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void install(android.content.Context r3, int r4) throws com.huawei.hms.feature.dynamic.DynamicModule.LoadingException {
        /*
            java.lang.String r0 = com.huawei.hms.feature.dynamic.DynamicModule.f16065b
            java.lang.String r1 = "dynamic-api version: 10024300"
            com.huawei.hms.common.util.Logger.i(r0, r1)
            if (r3 != 0) goto Lf
            java.lang.String r3 = "The input context is null."
            com.huawei.hms.common.util.Logger.e(r0, r3)
            return
        Lf:
            com.huawei.hms.feature.dynamic.DynamicModule.f16075l = r4     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            r4.<init>()     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            java.lang.String r1 = "Query HMS provider timeOut is set to:"
            r4.append(r1)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            int r1 = com.huawei.hms.feature.dynamic.DynamicModule.f16075l     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            r4.append(r1)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            java.lang.String r1 = " ms."
            r4.append(r1)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            java.lang.String r4 = r4.toString()     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            com.huawei.hms.common.util.Logger.i(r0, r4)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            com.huawei.hms.feature.dynamic.IDynamicInstall r4 = b(r3)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            if (r4 == 0) goto L5c
            com.huawei.hms.feature.dynamic.IObjectWrapper r1 = com.huawei.hms.feature.dynamic.ObjectWrapper.wrap(r3)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            android.os.Bundle r2 = new android.os.Bundle     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            r2.<init>()     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            android.os.Bundle r4 = r4.install(r1, r2)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            if (r4 == 0) goto L54
            com.huawei.hms.feature.dynamic.d r1 = com.huawei.hms.feature.dynamic.d.a()     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            r1.a(r4)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            java.lang.String r4 = "Install module success."
            com.huawei.hms.common.util.Logger.i(r0, r4)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            goto Lb5
        L4e:
            r4 = move-exception
            goto L64
        L50:
            r4 = move-exception
            goto L64
        L52:
            r4 = move-exception
            goto L64
        L54:
            com.huawei.hms.feature.dynamic.DynamicModule$LoadingException r4 = new com.huawei.hms.feature.dynamic.DynamicModule$LoadingException     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            java.lang.String r0 = "Get install info failed: moduleBundle is null."
            r4.<init>(r0)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            throw r4     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
        L5c:
            com.huawei.hms.feature.dynamic.DynamicModule$LoadingException r4 = new com.huawei.hms.feature.dynamic.DynamicModule$LoadingException     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            java.lang.String r0 = "Get dynamicInstaller failed."
            r4.<init>(r0)     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
            throw r4     // Catch: java.lang.NullPointerException -> L4e android.os.RemoteException -> L50 com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L52
        L64:
            int r0 = com.huawei.hms.feature.dynamic.DynamicModule.f16072i
            r1 = 2
            if (r0 == r1) goto Lae
            java.lang.String r0 = "huawei_module_dynamicloader"
            int r0 = getLocalVersion(r3, r0)
            if (r0 <= 0) goto Lae
            java.lang.String r4 = com.huawei.hms.feature.dynamic.DynamicModule.f16065b
            java.lang.String r0 = "Ready to use local loader-fallback to retry:"
            com.huawei.hms.common.util.Logger.i(r4, r0)
            com.huawei.hms.feature.dynamic.IDynamicInstall r0 = c(r3)     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            com.huawei.hms.feature.dynamic.IObjectWrapper r3 = com.huawei.hms.feature.dynamic.ObjectWrapper.wrap(r3)     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            android.os.Bundle r1 = new android.os.Bundle     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            r1.<init>()     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            android.os.Bundle r3 = r0.install(r3, r1)     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            if (r3 == 0) goto L9e
            com.huawei.hms.feature.dynamic.d r0 = com.huawei.hms.feature.dynamic.d.a()     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            r0.a(r3)     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            java.lang.String r3 = "Retry install module with local loader-fallback success."
            com.huawei.hms.common.util.Logger.i(r4, r3)     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            goto Lb5
        L98:
            r3 = move-exception
            goto La6
        L9a:
            r3 = move-exception
            goto La6
        L9c:
            r3 = move-exception
            goto La6
        L9e:
            com.huawei.hms.feature.dynamic.DynamicModule$LoadingException r3 = new com.huawei.hms.feature.dynamic.DynamicModule$LoadingException     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            java.lang.String r4 = "Retry: get install info failed: moduleBundle is null."
            r3.<init>(r4)     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
            throw r3     // Catch: java.lang.NullPointerException -> L98 android.os.RemoteException -> L9a com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L9c
        La6:
            java.lang.String r4 = com.huawei.hms.feature.dynamic.DynamicModule.f16065b
            java.lang.String r0 = "Retry failed with local loader-fallback."
            com.huawei.hms.common.util.Logger.w(r4, r0, r3)
            goto Lb5
        Lae:
            java.lang.String r3 = com.huawei.hms.feature.dynamic.DynamicModule.f16065b
            java.lang.String r0 = "Install module failed."
            com.huawei.hms.common.util.Logger.w(r3, r0, r4)
        Lb5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.feature.dynamic.DynamicModule.install(android.content.Context, int):void");
    }

    public static DynamicModule load(Context context, VersionPolicy versionPolicy, String str) throws LoadingException {
        return b(context, versionPolicy, str, new Bundle());
    }

    public static DynamicModule loadV2(Context context, VersionPolicy versionPolicy, String str) throws LoadingException {
        Bundle bundle = new Bundle();
        bundle.putString(com.huawei.hms.feature.dynamic.b.f16135s, "v2");
        return b(context, versionPolicy, str, bundle);
    }

    public static DynamicModule loadV3(Context context, VersionPolicy versionPolicy, String str) throws LoadingException {
        return loadV3(context, versionPolicy, str, new Bundle());
    }

    public static Bundle queryHMSModuleBundle(Context context, String str) throws g, LoadingException {
        return queryHMSModuleBundle(context, str, null);
    }

    public final Context getModuleContext() {
        return this.f16078a;
    }

    public static int a(VersionPolicy versionPolicy) {
        if (versionPolicy instanceof com.huawei.hms.feature.dynamic.e.e) {
            return 1;
        }
        if (versionPolicy instanceof com.huawei.hms.feature.dynamic.e.d) {
            return 2;
        }
        return versionPolicy instanceof com.huawei.hms.feature.dynamic.e.c ? 3 : 0;
    }

    public static DynamicModule b(Context context, VersionPolicy versionPolicy, String str, Bundle bundle) throws Throwable {
        String str2 = f16065b;
        Logger.i(str2, "dynamic-api version: 10024300");
        a(context, versionPolicy, str, bundle);
        try {
            int iA = a(context, str, bundle);
            if (iA >= 10015300) {
                Logger.i(str2, "Load start in new-version-policy.");
                return a(context, str, versionPolicy, bundle);
            }
            if (iA <= 0) {
                throw new LoadingException("Cannot find a valid dynamicLoader in HMS and local.");
            }
            Logger.i(str2, "Load start in old-version-policy.");
            return a(context, str, versionPolicy);
        } catch (LoadingException e2) {
            throw e2;
        } catch (Exception e3) {
            Logger.e(f16065b, "Other exception:" + e3);
            throw new LoadingException("Load failed.");
        }
    }

    public static DynamicModule c(Context context, String str, Bundle bundle) throws LoadingException {
        Boolean bool;
        IDynamicLoader iDynamicLoader;
        try {
            synchronized (DynamicModule.class) {
                HashMap<String, Boolean> map = f16068e.get();
                Objects.requireNonNull(map);
                bool = map.get(str);
                HashMap<String, IDynamicLoader> map2 = f16070g.get();
                Objects.requireNonNull(map2);
                iDynamicLoader = map2.get(str);
            }
            if (bool == null || iDynamicLoader == null) {
                throw new LoadingException("The loader for " + str + " was not prepared.");
            }
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            Context contextA = a(context, str, bundle, iDynamicLoader);
            if (contextA != null) {
                return new DynamicModule(contextA);
            }
            throw new LoadingException("Failed to get remote module context: null");
        } catch (LoadingException e2) {
            throw e2;
        } catch (Exception unused) {
            throw new LoadingException("Load Module Error.");
        }
    }

    public static DynamicModule loadV3(Context context, VersionPolicy versionPolicy, String str, Bundle bundle) throws LoadingException {
        bundle.putString(com.huawei.hms.feature.dynamic.b.f16135s, com.huawei.hms.feature.dynamic.b.f16137u);
        return b(context, versionPolicy, str, bundle);
    }

    public static Bundle queryHMSModuleBundle(Context context, String str, Bundle bundle) throws g, LoadingException {
        a aVar = null;
        try {
            if (!com.huawei.hms.feature.dynamic.f.c.a(context)) {
                Logger.w(f16065b, "No valid HMS Core in this device.");
                throw new g("HMS Core is not installed.", aVar);
            }
            ContentResolver contentResolver = context.getContentResolver();
            if (contentResolver == null) {
                throw new g("Query remote version failed: null contentResolver.", aVar);
            }
            Bundle bundleCall = contentResolver.call(Uri.parse(com.huawei.hms.feature.dynamic.b.f16117a), str, (String) null, bundle);
            if (bundleCall == null) {
                Logger.w(f16065b, "Failed to get bundle info:null.");
                throw new g("Query remote version failed: null bundle info.", aVar);
            }
            int i2 = bundleCall.getInt(com.huawei.hms.feature.dynamic.b.f16124h);
            String string = bundleCall.getString(com.huawei.hms.feature.dynamic.b.f16134r);
            String str2 = f16065b;
            Logger.i(str2, "bundle info: errorCode:" + i2 + ", moduleVersion:" + bundleCall.getInt(com.huawei.hms.feature.dynamic.b.f16127k) + ", modulePath:" + bundleCall.getString(com.huawei.hms.feature.dynamic.b.f16130n) + ", loader_version:" + bundleCall.getInt(com.huawei.hms.feature.dynamic.b.f16133q) + ", loaderPath:" + string + ", armeabiType:" + bundleCall.getInt(com.huawei.hms.feature.dynamic.b.f16138v));
            if (i2 == 0) {
                return bundleCall;
            }
            Logger.w(str2, "Failed to get " + str + " bundle info, errcode:" + i2);
            throw new LoadingException("Query " + str + " unavailable, errorCode:" + i2, bundleCall);
        } catch (LoadingException e2) {
            throw e2;
        } catch (Exception unused) {
            throw new g("failed to get :" + str + " info from HMS Core.", aVar);
        }
    }

    public static Context a(Context context, String str, Bundle bundle, IDynamicLoader iDynamicLoader) throws LoadingException {
        try {
            IObjectWrapper iObjectWrapperLoad = iDynamicLoader.load(ObjectWrapper.wrap(context), str, bundle.getInt(com.huawei.hms.feature.dynamic.b.f16127k), ObjectWrapper.wrap(bundle));
            Object objUnwrap = ObjectWrapper.unwrap(iObjectWrapperLoad);
            if (objUnwrap == null) {
                Logger.w(f16065b, "Get remote context is null, module:" + str);
                return null;
            }
            if (objUnwrap instanceof Context) {
                Logger.i(f16065b, "Get context for module:" + str + " success.");
                return (Context) objUnwrap;
            }
            if (objUnwrap instanceof Bundle) {
                Logger.i(f16065b, "Get module info bundle for " + str);
                return a(context, str, bundle, iDynamicLoader, (Bundle) objUnwrap);
            }
            if (!objUnwrap.getClass().getName().equals(LoadingException.class.getName())) {
                Logger.w(f16065b, "Get remote context is null, module:" + str);
                return null;
            }
            Bundle bundle2 = (Bundle) ObjectWrapper.unwrap(iObjectWrapperLoad).getClass().getDeclaredMethod("getBundle", null).invoke(ObjectWrapper.unwrap(iObjectWrapperLoad), null);
            Logger.w(f16065b, "Successfully get the bundle in exception.");
            throw new LoadingException("Failed to load, please check the bundle in exception.", bundle2);
        } catch (LoadingException e2) {
            throw e2;
        } catch (Exception e3) {
            Logger.w(f16065b, "Failed to get module context for:" + str, e3);
            return null;
        }
    }

    public static DynamicModule b(Context context, String str, Bundle bundle) throws LoadingException {
        ClassLoader classLoader;
        synchronized (DynamicModule.class) {
            try {
                ThreadLocal<HashMap<String, ClassLoader>> threadLocal = f16071h;
                if (threadLocal.get() == null || threadLocal.get().get(str) == null) {
                    Logger.i(f16065b, "No available cached loader for module:" + str);
                    classLoader = null;
                } else {
                    Logger.i(f16065b, "Cached loader for module is available, ready to use it.");
                    classLoader = threadLocal.get().get(str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        IDynamicLoader iDynamicLoaderA = a(context, str, bundle.getString(com.huawei.hms.feature.dynamic.b.f16134r), classLoader);
        if (iDynamicLoaderA == null) {
            throw new LoadingException("Failed to get iDynamicLoader: null.");
        }
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        Context contextA = a(context, str, bundle, iDynamicLoaderA);
        if (contextA != null) {
            return new DynamicModule(contextA);
        }
        throw new LoadingException("New version policy: Failed to get module context: null.");
    }

    public static IDynamicInstall c(Context context) throws LoadingException {
        try {
            return (IDynamicInstall) context.getClassLoader().loadClass(com.huawei.hms.feature.dynamic.b.f16123g).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e2) {
            throw new LoadingException("getLocalLoaderFallback: failed to instantiate dynamic loader: " + e2.getMessage());
        }
    }

    public static Context a(Context context, String str, Bundle bundle, IDynamicLoader iDynamicLoader, Bundle bundle2) throws SecurityException, LoadingException {
        Object objUnwrap;
        bundle.putInt(com.huawei.hms.feature.dynamic.b.f16139w, 4);
        if (AssetLoadManager.getAssetModuleInfo(context, str).getInt(com.huawei.hms.feature.dynamic.b.f16129m, 0) <= 0) {
            Logger.i(f16065b, "No fallback module in assets.");
            throw new LoadingException("Load exception, please check the bundle in exception.", bundle2);
        }
        try {
            objUnwrap = ObjectWrapper.unwrap(iDynamicLoader.load(ObjectWrapper.wrap(context), str, bundle.getInt(com.huawei.hms.feature.dynamic.b.f16127k), ObjectWrapper.wrap(bundle)));
        } catch (RemoteException e2) {
            Logger.w(f16065b, "tryWithAssetsModule RemoteException.", e2);
            objUnwrap = null;
        }
        if (!(objUnwrap instanceof Context)) {
            Logger.w(f16065b, "tryWithAssetsModule get dynamicContext failed: null or wrong type.");
            throw new LoadingException("Load exception, please check the bundle in exception.", bundle2);
        }
        Logger.i(f16065b, "get dynamic module context for:" + str + " from assets fallback success.");
        return (Context) objUnwrap;
    }

    public static IDynamicInstall b(Context context) throws LoadingException {
        int i2;
        String string = null;
        int localVersion = 0;
        try {
            Bundle bundleA = a(context, (Bundle) null);
            string = bundleA.getString(com.huawei.hms.feature.dynamic.b.f16134r);
            i2 = bundleA.getInt(com.huawei.hms.feature.dynamic.b.f16133q);
        } catch (Exception e2) {
            Logger.w(f16065b, "Cannot get remote HMS dynamicLoader.", e2);
            i2 = 0;
        }
        try {
            localVersion = getLocalVersion(context, com.huawei.hms.feature.dynamic.b.f16121e);
        } catch (Exception e3) {
            Logger.w(f16065b, "Cannot find local dynamicLoader fallback.", e3);
        }
        String str = f16065b;
        Logger.i(str, "DynamicLoader remoteHMSVersion:" + i2 + ", hmsLoaderPath:" + string + ", localLoaderVersion:" + localVersion);
        int iMax = Math.max(i2, localVersion);
        if (iMax > 10009300) {
            if (i2 <= localVersion) {
                Logger.i(str, "Choose local dynamicLoader fallback: ");
                f16072i = 2;
                return c(context);
            }
            Logger.i(str, "Choose hms dynamicLoader: " + string);
            f16072i = 1;
            return a(context, string);
        }
        Logger.w(str, "The current version:" + iMax + " is too low.");
        throw new LoadingException("The loader version:" + iMax + " is too low to support HFF.");
    }

    public static Bundle a(Context context, Bundle bundle) throws g {
        a aVar = null;
        try {
            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
            FutureTask futureTask = new FutureTask(new c(context, bundle));
            executorServiceNewSingleThreadExecutor.execute(futureTask);
            Bundle bundle2 = (Bundle) futureTask.get(f16075l, TimeUnit.MILLISECONDS);
            String string = bundle2.getString(com.huawei.hms.feature.dynamic.b.f16134r);
            if (!TextUtils.isEmpty(string) && new File(string).exists()) {
                Logger.i(f16065b, "Query HMS module:huawei_module_dynamicloader info success.");
                return bundle2;
            }
            Logger.w(f16065b, "The loader_path:" + string + " is not available.");
            throw new g("The loader_path in queryBundle is empty or not exist.", aVar);
        } catch (TimeoutException unused) {
            Logger.w(f16065b, "Query hms provider timeout: over " + f16075l + " ms, choose the local loader fallback.");
            return new Bundle();
        } catch (Exception e2) {
            Logger.w(f16065b, "FutureTask: query provider exception.", e2);
            throw new g("failed to get :huawei_module_dynamicloader info.", aVar);
        }
    }

    public static DynamicModule a(Context context, String str, VersionPolicy versionPolicy) throws LoadingException {
        Bundle moduleInfo = versionPolicy.getModuleInfo(context, str);
        if (moduleInfo.getInt(com.huawei.hms.feature.dynamic.b.f16127k) <= 0) {
            if (moduleInfo.getInt(com.huawei.hms.feature.dynamic.b.f16128l) <= 0) {
                throw new LoadingException("Query remote version and local version failed.");
            }
            Logger.i(f16065b, "Remote version is invalid, use local context.");
            return new DynamicModule(context.getApplicationContext());
        }
        try {
            return c(context, str, moduleInfo);
        } catch (LoadingException e2) {
            Logger.w(f16065b, "Failed to load remote module.", e2);
            if (getLocalVersion(context, str) <= 0) {
                throw e2;
            }
            Logger.d(f16065b, "Local module version is valid, use local fallback.");
            return new DynamicModule(context.getApplicationContext());
        }
    }

    public static DynamicModule a(Context context, String str, VersionPolicy versionPolicy, Bundle bundle) throws LoadingException {
        int iA = a(versionPolicy);
        String string = bundle.getString(com.huawei.hms.feature.dynamic.b.f16134r);
        Logger.i(f16065b, "The loader path for module:" + str + " is:" + string + ", and versionType is:" + iA);
        if (TextUtils.isEmpty(string)) {
            throw new LoadingException("Cannot find a valid dynamic loader in HMS or local.");
        }
        Boolean bool = f16077n.get(str);
        Boolean bool2 = f16076m.get(str);
        bundle.putString(com.huawei.hms.feature.dynamic.b.f16126j, str);
        bundle.putInt(com.huawei.hms.feature.dynamic.b.f16139w, iA);
        bundle.putBoolean(com.huawei.hms.feature.dynamic.b.f16140x, bool != null ? bool.booleanValue() : false);
        bundle.putBoolean(com.huawei.hms.feature.dynamic.b.f16141y, bool2 != null ? bool2.booleanValue() : false);
        try {
            return b(context, str, bundle);
        } catch (LoadingException e2) {
            throw e2;
        } catch (Exception e3) {
            Logger.e(f16065b, "Other exception,", e3);
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            return new DynamicModule(context);
        }
    }

    public static IDynamicInstall a(Context context, String str) throws LoadingException {
        if (str != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    if (com.huawei.hms.feature.dynamic.f.c.a(context, str)) {
                        return IDynamicInstall.Stub.asInterface((IBinder) new com.huawei.hms.feature.dynamic.e.a(str, ClassLoader.getSystemClassLoader()).loadClass(com.huawei.hms.feature.dynamic.b.f16123g).getConstructor(null).newInstance(null));
                    }
                    Logger.w(f16065b, "check path failed: invalid.");
                    throw new LoadingException("getHMSDynamicInstaller: checkPathValidity failed.");
                }
            } catch (ClassNotFoundException e2) {
                e = e2;
                throw new LoadingException("getHMSDynamicInstaller: failed to instantiate dynamic loader:" + e.getMessage());
            } catch (IllegalAccessException e3) {
                e = e3;
                throw new LoadingException("getHMSDynamicInstaller: failed to instantiate dynamic loader:" + e.getMessage());
            } catch (InstantiationException e4) {
                e = e4;
                throw new LoadingException("getHMSDynamicInstaller: failed to instantiate dynamic loader:" + e.getMessage());
            } catch (NoSuchMethodException e5) {
                e = e5;
                throw new LoadingException("getHMSDynamicInstaller: failed to instantiate dynamic loader:" + e.getMessage());
            } catch (InvocationTargetException e6) {
                e = e6;
                throw new LoadingException("getHMSDynamicInstaller: failed to instantiate dynamic loader:" + e.getMessage());
            }
        }
        throw new LoadingException("Failed to get dynamicLoader path.");
    }

    public static IDynamicLoader a(Context context, String str, String str2, ClassLoader classLoader) {
        if (classLoader == null) {
            try {
                classLoader = new com.huawei.hms.feature.dynamic.e.a(str2, ClassLoader.getSystemClassLoader());
                f16071h.set(new b(str, classLoader));
            } catch (Exception e2) {
                Logger.w(f16065b, "Get iDynamicLoader failed.", e2);
                return null;
            }
        }
        return IDynamicLoader.Stub.asInterface((IBinder) classLoader.loadClass(com.huawei.hms.feature.dynamic.b.f16122f).getConstructor(null).newInstance(null));
    }

    public static Class<?> a(Context context) throws ClassNotFoundException, LoadingException {
        Class<?> clsLoadClass;
        try {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            clsLoadClass = context.getClassLoader().loadClass(DynamicLoaderClassLoader.class.getName());
        } catch (ClassNotFoundException unused) {
            Logger.w(f16065b, "ClassLoader class not found when use client context.");
            clsLoadClass = null;
        }
        if (clsLoadClass == null) {
            try {
                ClassLoader classLoader = DynamicModule.class.getClassLoader();
                Objects.requireNonNull(classLoader);
                clsLoadClass = classLoader.loadClass(DynamicLoaderClassLoader.class.getName());
                if (clsLoadClass == null) {
                    throw new LoadingException("ClassLoader class is null.");
                }
            } catch (ClassNotFoundException unused2) {
                throw new LoadingException("ClassLoader class not found when use DynamicModule's classLoader.");
            }
        }
        return clsLoadClass;
    }

    public static void a(Context context, VersionPolicy versionPolicy, String str, Bundle bundle) throws LoadingException {
        if (context == null || versionPolicy == null || str == null || str.length() == 0 || str.length() > 256 || bundle == null) {
            throw new LoadingException("Null param, please check it.");
        }
    }

    public static void a(String str, ClassLoader classLoader) throws LoadingException {
        try {
            f16070g.set(new f(str, (IBinder) classLoader.loadClass(com.huawei.hms.feature.dynamic.b.f16122f).getConstructor(null).newInstance(null)));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            throw new LoadingException("Failed to get loader interface:" + e2.getMessage());
        }
    }

    public static boolean a(String str, int i2) {
        if (i2 == 1) {
            Boolean bool = f16077n.get(str);
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        }
        if (i2 != 2) {
            return i2 == 3 || i2 == 4;
        }
        Boolean bool2 = f16076m.get(str);
        if (bool2 != null) {
            return bool2.booleanValue();
        }
        return false;
    }
}
