package com.google.firebase.messaging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.kingsmith.miot.KsProperty;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@KeepForSdk
/* loaded from: classes3.dex */
public class FcmBroadcastProcessor {
    private static final String EXTRA_BINARY_DATA = "rawData";
    private static final String EXTRA_BINARY_DATA_BASE_64 = "gcm.rawData64";

    @GuardedBy(KsProperty.Lock)
    private static WithinAppServiceConnection fcmServiceConn;
    private static final Object lock = new Object();
    private final Context context;
    private final Executor executor;

    public FcmBroadcastProcessor(Context context) {
        this.context = context;
        this.executor = new androidx.media3.exoplayer.dash.offline.a();
    }

    private static Task<Integer> bindToMessagingService(Context context, Intent intent, boolean z2) {
        if (Log.isLoggable(Constants.TAG, 3)) {
            Log.d(Constants.TAG, "Binding to service");
        }
        WithinAppServiceConnection serviceConnection = getServiceConnection(context, "com.google.firebase.MESSAGING_EVENT");
        if (!z2) {
            return serviceConnection.a(intent).continueWith(new androidx.media3.exoplayer.dash.offline.a(), new Continuation() { // from class: com.google.firebase.messaging.g
                @Override // com.google.android.gms.tasks.Continuation
                public final Object then(Task task) {
                    return FcmBroadcastProcessor.lambda$bindToMessagingService$3(task);
                }
            });
        }
        if (ServiceStarter.a().d(context)) {
            WakeLockHolder.d(context, serviceConnection, intent);
        } else {
            serviceConnection.a(intent);
        }
        return Tasks.forResult(-1);
    }

    private static WithinAppServiceConnection getServiceConnection(Context context, String str) {
        WithinAppServiceConnection withinAppServiceConnection;
        synchronized (lock) {
            try {
                if (fcmServiceConn == null) {
                    fcmServiceConn = new WithinAppServiceConnection(context, str);
                }
                withinAppServiceConnection = fcmServiceConn;
            } catch (Throwable th) {
                throw th;
            }
        }
        return withinAppServiceConnection;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$bindToMessagingService$3(Task task) throws Exception {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$startMessagingService$0(Context context, Intent intent) throws Exception {
        return Integer.valueOf(ServiceStarter.a().startMessagingService(context, intent));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$startMessagingService$1(Task task) throws Exception {
        return 403;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Task lambda$startMessagingService$2(Context context, Intent intent, boolean z2, Task task) throws Exception {
        return (PlatformVersion.isAtLeastO() && ((Integer) task.getResult()).intValue() == 402) ? bindToMessagingService(context, intent, z2).continueWith(new androidx.media3.exoplayer.dash.offline.a(), new Continuation() { // from class: com.google.firebase.messaging.d
            @Override // com.google.android.gms.tasks.Continuation
            public final Object then(Task task2) {
                return FcmBroadcastProcessor.lambda$startMessagingService$1(task2);
            }
        }) : task;
    }

    @VisibleForTesting
    public static void reset() {
        synchronized (lock) {
            fcmServiceConn = null;
        }
    }

    @VisibleForTesting
    public static void setServiceConnection(WithinAppServiceConnection withinAppServiceConnection) {
        synchronized (lock) {
            fcmServiceConn = withinAppServiceConnection;
        }
    }

    @KeepForSdk
    public Task<Integer> process(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_BINARY_DATA_BASE_64);
        if (stringExtra != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra, 0));
            intent.removeExtra(EXTRA_BINARY_DATA_BASE_64);
        }
        return startMessagingService(this.context, intent);
    }

    @SuppressLint({"InlinedApi"})
    public Task<Integer> startMessagingService(final Context context, final Intent intent) {
        boolean z2 = PlatformVersion.isAtLeastO() && context.getApplicationInfo().targetSdkVersion >= 26;
        final boolean z3 = (intent.getFlags() & 268435456) != 0;
        return (!z2 || z3) ? Tasks.call(this.executor, new Callable() { // from class: com.google.firebase.messaging.e
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return FcmBroadcastProcessor.lambda$startMessagingService$0(context, intent);
            }
        }).continueWithTask(this.executor, new Continuation() { // from class: com.google.firebase.messaging.f
            @Override // com.google.android.gms.tasks.Continuation
            public final Object then(Task task) {
                return FcmBroadcastProcessor.lambda$startMessagingService$2(context, intent, z3, task);
            }
        }) : bindToMessagingService(context, intent, z3);
    }

    public FcmBroadcastProcessor(Context context, ExecutorService executorService) {
        this.context = context;
        this.executor = executorService;
    }
}
