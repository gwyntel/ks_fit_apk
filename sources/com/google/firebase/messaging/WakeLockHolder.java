package com.google.firebase.messaging;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import com.google.android.gms.stats.WakeLock;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
final class WakeLockHolder {
    private static final String EXTRA_WAKEFUL_INTENT = "com.google.firebase.iid.WakeLockHolder.wakefulintent";

    /* renamed from: a, reason: collision with root package name */
    static final long f15113a = TimeUnit.MINUTES.toMillis(1);
    private static final Object syncObject = new Object();

    @GuardedBy("WakeLockHolder.syncObject")
    private static WakeLock wakeLock;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b(Intent intent) {
        synchronized (syncObject) {
            try {
                if (wakeLock != null && c(intent)) {
                    setAsWakefulIntent(intent, false);
                    wakeLock.release();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static boolean c(Intent intent) {
        return intent.getBooleanExtra(EXTRA_WAKEFUL_INTENT, false);
    }

    @GuardedBy("WakeLockHolder.syncObject")
    private static void checkAndInitWakeLock(Context context) {
        if (wakeLock == null) {
            WakeLock wakeLock2 = new WakeLock(context, 1, "wake:com.google.firebase.iid.WakeLockHolder");
            wakeLock = wakeLock2;
            wakeLock2.setReferenceCounted(true);
        }
    }

    static void d(Context context, WithinAppServiceConnection withinAppServiceConnection, final Intent intent) {
        synchronized (syncObject) {
            try {
                checkAndInitWakeLock(context);
                boolean zC = c(intent);
                setAsWakefulIntent(intent, true);
                if (!zC) {
                    wakeLock.acquire(f15113a);
                }
                withinAppServiceConnection.a(intent).addOnCompleteListener(new OnCompleteListener() { // from class: com.google.firebase.messaging.f0
                    @Override // com.google.android.gms.tasks.OnCompleteListener
                    public final void onComplete(Task task) {
                        WakeLockHolder.b(intent);
                    }
                });
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static ComponentName e(Context context, Intent intent) {
        synchronized (syncObject) {
            try {
                checkAndInitWakeLock(context);
                boolean zC = c(intent);
                setAsWakefulIntent(intent, true);
                ComponentName componentNameStartService = context.startService(intent);
                if (componentNameStartService == null) {
                    return null;
                }
                if (!zC) {
                    wakeLock.acquire(f15113a);
                }
                return componentNameStartService;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static void setAsWakefulIntent(@NonNull Intent intent, boolean z2) {
        intent.putExtra(EXTRA_WAKEFUL_INTENT, z2);
    }
}
