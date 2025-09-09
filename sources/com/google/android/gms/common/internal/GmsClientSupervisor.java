package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.concurrent.Executor;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class GmsClientSupervisor {

    /* renamed from: a, reason: collision with root package name */
    static HandlerThread f12828a = null;
    private static final Object zzb = new Object();

    @Nullable
    private static zzs zzc = null;

    @Nullable
    private static Executor zzd = null;
    private static boolean zze = false;

    @KeepForSdk
    public static int getDefaultBindFlags() {
        return 4225;
    }

    @NonNull
    @KeepForSdk
    public static GmsClientSupervisor getInstance(@NonNull Context context) {
        synchronized (zzb) {
            try {
                if (zzc == null) {
                    zzc = new zzs(context.getApplicationContext(), zze ? getOrStartHandlerThread().getLooper() : context.getMainLooper(), zzd);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return zzc;
    }

    @NonNull
    @KeepForSdk
    public static HandlerThread getOrStartHandlerThread() {
        synchronized (zzb) {
            try {
                HandlerThread handlerThread = f12828a;
                if (handlerThread != null) {
                    return handlerThread;
                }
                HandlerThread handlerThread2 = new HandlerThread("GoogleApiHandler", 9);
                f12828a = handlerThread2;
                handlerThread2.start();
                return f12828a;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @KeepForSdk
    public static void setDefaultBindExecutor(@Nullable Executor executor) {
        synchronized (zzb) {
            try {
                zzs zzsVar = zzc;
                if (zzsVar != null) {
                    zzsVar.h(executor);
                }
                zzd = executor;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @KeepForSdk
    public static void setUseHandlerThreadForCallbacks() {
        synchronized (zzb) {
            try {
                zzs zzsVar = zzc;
                if (zzsVar != null && !zze) {
                    zzsVar.i(getOrStartHandlerThread().getLooper());
                }
                zze = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected abstract void a(zzo zzoVar, ServiceConnection serviceConnection, String str);

    protected abstract boolean b(zzo zzoVar, ServiceConnection serviceConnection, String str, Executor executor);

    @KeepForSdk
    public boolean bindService(@NonNull ComponentName componentName, @NonNull ServiceConnection serviceConnection, @NonNull String str) {
        return b(new zzo(componentName, 4225), serviceConnection, str, null);
    }

    @KeepForSdk
    public void unbindService(@NonNull ComponentName componentName, @NonNull ServiceConnection serviceConnection, @NonNull String str) {
        a(new zzo(componentName, 4225), serviceConnection, str);
    }

    public final void zzb(@NonNull String str, @NonNull String str2, int i2, @NonNull ServiceConnection serviceConnection, @NonNull String str3, boolean z2) {
        a(new zzo(str, str2, 4225, z2), serviceConnection, str3);
    }

    @KeepForSdk
    public boolean bindService(@NonNull ComponentName componentName, @NonNull ServiceConnection serviceConnection, @NonNull String str, @Nullable Executor executor) {
        return b(new zzo(componentName, 4225), serviceConnection, str, executor);
    }

    @KeepForSdk
    public void unbindService(@NonNull String str, @NonNull ServiceConnection serviceConnection, @NonNull String str2) {
        a(new zzo(str, 4225, false), serviceConnection, str2);
    }

    @NonNull
    @KeepForSdk
    public static HandlerThread getOrStartHandlerThread(int i2) {
        synchronized (zzb) {
            try {
                HandlerThread handlerThread = f12828a;
                if (handlerThread != null) {
                    return handlerThread;
                }
                HandlerThread handlerThread2 = new HandlerThread("GoogleApiHandler", i2);
                f12828a = handlerThread2;
                handlerThread2.start();
                return f12828a;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public boolean bindService(@NonNull String str, @NonNull ServiceConnection serviceConnection, @NonNull String str2) {
        return b(new zzo(str, 4225, false), serviceConnection, str2, null);
    }
}
