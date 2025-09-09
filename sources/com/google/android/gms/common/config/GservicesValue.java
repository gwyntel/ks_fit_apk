package com.google.android.gms.common.config;

import android.os.Binder;
import android.os.StrictMode;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.InlineMe;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class GservicesValue<T> {
    private static final Object zzc = new Object();

    /* renamed from: a, reason: collision with root package name */
    protected final String f12786a;

    /* renamed from: b, reason: collision with root package name */
    protected final Object f12787b;

    @Nullable
    private Object zzd = null;

    protected GservicesValue(String str, Object obj) {
        this.f12786a = str;
        this.f12787b = obj;
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public static boolean isInitialized() {
        synchronized (zzc) {
        }
        return false;
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<Float> value(@NonNull String str, @NonNull Float f2) {
        return new zzd(str, f2);
    }

    protected abstract Object a(String str);

    @NonNull
    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public final T get() {
        T t2;
        T t3 = (T) this.zzd;
        if (t3 != null) {
            return t3;
        }
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        Object obj = zzc;
        synchronized (obj) {
        }
        synchronized (obj) {
            try {
            } finally {
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            }
        }
        try {
            t2 = (T) a(this.f12786a);
        } catch (SecurityException unused) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                t2 = (T) a(this.f12786a);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
        return t2;
    }

    @NonNull
    @Deprecated
    @InlineMe(replacement = "this.get()")
    @KeepForSdk
    public final T getBinderSafe() {
        return get();
    }

    @KeepForSdk
    @VisibleForTesting
    public void override(@NonNull T t2) {
        Log.w("GservicesValue", "GservicesValue.override(): test should probably call initForTests() first");
        this.zzd = t2;
        Object obj = zzc;
        synchronized (obj) {
            synchronized (obj) {
            }
        }
    }

    @KeepForSdk
    @VisibleForTesting
    public void resetOverride() {
        this.zzd = null;
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<Integer> value(@NonNull String str, @NonNull Integer num) {
        return new zzc(str, num);
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<Long> value(@NonNull String str, @NonNull Long l2) {
        return new zzb(str, l2);
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<String> value(@NonNull String str, @NonNull String str2) {
        return new zze(str, str2);
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<Boolean> value(@NonNull String str, boolean z2) {
        return new zza(str, Boolean.valueOf(z2));
    }
}
