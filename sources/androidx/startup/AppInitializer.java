package androidx.startup;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.tracing.Trace;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public final class AppInitializer {
    private static final String SECTION_NAME = "Startup";
    private static volatile AppInitializer sInstance;
    private static final Object sLock = new Object();

    /* renamed from: c, reason: collision with root package name */
    final Context f6201c;

    /* renamed from: b, reason: collision with root package name */
    final Set f6200b = new HashSet();

    /* renamed from: a, reason: collision with root package name */
    final Map f6199a = new HashMap();

    AppInitializer(Context context) {
        this.f6201c = context.getApplicationContext();
    }

    @NonNull
    private <T> T doInitialize(@NonNull Class<? extends Initializer<?>> cls, @NonNull Set<Class<?>> set) {
        T t2;
        if (Trace.isEnabled()) {
            try {
                Trace.beginSection(cls.getSimpleName());
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        }
        if (set.contains(cls)) {
            throw new IllegalStateException(String.format("Cannot initialize %s. Cycle detected.", cls.getName()));
        }
        if (this.f6199a.containsKey(cls)) {
            t2 = (T) this.f6199a.get(cls);
        } else {
            set.add(cls);
            try {
                Initializer<?> initializerNewInstance = cls.getDeclaredConstructor(null).newInstance(null);
                List<Class<? extends Initializer<?>>> listDependencies = initializerNewInstance.dependencies();
                if (!listDependencies.isEmpty()) {
                    for (Class<? extends Initializer<?>> cls2 : listDependencies) {
                        if (!this.f6199a.containsKey(cls2)) {
                            doInitialize(cls2, set);
                        }
                    }
                }
                t2 = (T) initializerNewInstance.create(this.f6201c);
                set.remove(cls);
                this.f6199a.put(cls, t2);
            } catch (Throwable th2) {
                throw new StartupException(th2);
            }
        }
        Trace.endSection();
        return t2;
    }

    @NonNull
    public static AppInitializer getInstance(@NonNull Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                try {
                    if (sInstance == null) {
                        sInstance = new AppInitializer(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    void a() {
        try {
            try {
                Trace.beginSection(SECTION_NAME);
                b(this.f6201c.getPackageManager().getProviderInfo(new ComponentName(this.f6201c.getPackageName(), InitializationProvider.class.getName()), 128).metaData);
            } catch (PackageManager.NameNotFoundException e2) {
                throw new StartupException(e2);
            }
        } finally {
            Trace.endSection();
        }
    }

    void b(Bundle bundle) throws ClassNotFoundException {
        String string = this.f6201c.getString(R.string.androidx_startup);
        if (bundle != null) {
            try {
                HashSet hashSet = new HashSet();
                for (String str : bundle.keySet()) {
                    if (string.equals(bundle.getString(str, null))) {
                        Class<?> cls = Class.forName(str);
                        if (Initializer.class.isAssignableFrom(cls)) {
                            this.f6200b.add(cls);
                        }
                    }
                }
                Iterator it = this.f6200b.iterator();
                while (it.hasNext()) {
                    doInitialize((Class) it.next(), hashSet);
                }
            } catch (ClassNotFoundException e2) {
                throw new StartupException(e2);
            }
        }
    }

    Object c(Class cls) {
        Object objDoInitialize;
        synchronized (sLock) {
            try {
                objDoInitialize = this.f6199a.get(cls);
                if (objDoInitialize == null) {
                    objDoInitialize = doInitialize(cls, new HashSet());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return objDoInitialize;
    }

    @NonNull
    public <T> T initializeComponent(@NonNull Class<? extends Initializer<T>> cls) {
        return (T) c(cls);
    }

    public boolean isEagerlyInitialized(@NonNull Class<? extends Initializer<?>> cls) {
        return this.f6200b.contains(cls);
    }
}
