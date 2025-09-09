package com.google.firebase.messaging;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.kingsmith.miot.KsProperty;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
class SyncTask implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    ExecutorService f15111a = new ThreadPoolExecutor(0, 1, 30, TimeUnit.SECONDS, new LinkedBlockingQueue(), new NamedThreadFactory("firebase-iid-executor"));
    private final FirebaseMessaging firebaseMessaging;
    private final long nextDelaySeconds;
    private final PowerManager.WakeLock syncWakeLock;

    @VisibleForTesting
    static class ConnectivityChangeReceiver extends BroadcastReceiver {

        @Nullable
        private SyncTask task;

        public ConnectivityChangeReceiver(SyncTask syncTask) {
            this.task = syncTask;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            SyncTask syncTask = this.task;
            if (syncTask != null && syncTask.d()) {
                if (SyncTask.c()) {
                    Log.d(Constants.TAG, "Connectivity changed. Starting background sync.");
                }
                this.task.firebaseMessaging.o(this.task, 0L);
                this.task.b().unregisterReceiver(this);
                this.task = null;
            }
        }

        public void registerReceiver() {
            if (SyncTask.c()) {
                Log.d(Constants.TAG, "Connectivity change received registered");
            }
            this.task.b().registerReceiver(this, new IntentFilter(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION));
        }
    }

    @SuppressLint({"InvalidWakeLockTag"})
    @VisibleForTesting
    public SyncTask(FirebaseMessaging firebaseMessaging, long j2) {
        this.firebaseMessaging = firebaseMessaging;
        this.nextDelaySeconds = j2;
        PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) b().getSystemService(KsProperty.Power)).newWakeLock(1, "fiid-sync");
        this.syncWakeLock = wakeLockNewWakeLock;
        wakeLockNewWakeLock.setReferenceCounted(false);
    }

    static boolean c() {
        return Log.isLoggable(Constants.TAG, 3) || (Build.VERSION.SDK_INT == 23 && Log.isLoggable(Constants.TAG, 3));
    }

    Context b() {
        return this.firebaseMessaging.p();
    }

    boolean d() {
        ConnectivityManager connectivityManager = (ConnectivityManager) b().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    boolean e() throws IOException {
        try {
            if (this.firebaseMessaging.n() == null) {
                Log.e(Constants.TAG, "Token retrieval failed: null");
                return false;
            }
            if (!Log.isLoggable(Constants.TAG, 3)) {
                return true;
            }
            Log.d(Constants.TAG, "Token successfully retrieved");
            return true;
        } catch (IOException e2) {
            if (!GmsRpc.d(e2.getMessage())) {
                if (e2.getMessage() != null) {
                    throw e2;
                }
                Log.w(Constants.TAG, "Token retrieval failed without exception message. Will retry token retrieval");
                return false;
            }
            Log.w(Constants.TAG, "Token retrieval failed: " + e2.getMessage() + ". Will retry token retrieval");
            return false;
        } catch (SecurityException unused) {
            Log.w(Constants.TAG, "Token retrieval failed with SecurityException. Will retry token retrieval");
            return false;
        }
    }

    @Override // java.lang.Runnable
    @SuppressLint({"WakelockTimeout"})
    public void run() {
        if (ServiceStarter.a().d(b())) {
            this.syncWakeLock.acquire();
        }
        try {
            try {
                this.firebaseMessaging.s(true);
            } catch (IOException e2) {
                Log.e(Constants.TAG, "Topic sync or token retrieval failed on hard failure exceptions: " + e2.getMessage() + ". Won't retry the operation.");
                this.firebaseMessaging.s(false);
                if (!ServiceStarter.a().d(b())) {
                    return;
                }
            }
            if (!this.firebaseMessaging.r()) {
                this.firebaseMessaging.s(false);
                if (ServiceStarter.a().d(b())) {
                    this.syncWakeLock.release();
                    return;
                }
                return;
            }
            if (ServiceStarter.a().c(b()) && !d()) {
                new ConnectivityChangeReceiver(this).registerReceiver();
                if (ServiceStarter.a().d(b())) {
                    this.syncWakeLock.release();
                    return;
                }
                return;
            }
            if (e()) {
                this.firebaseMessaging.s(false);
            } else {
                this.firebaseMessaging.t(this.nextDelaySeconds);
            }
            if (!ServiceStarter.a().d(b())) {
                return;
            }
            this.syncWakeLock.release();
        } catch (Throwable th) {
            if (ServiceStarter.a().d(b())) {
                this.syncWakeLock.release();
            }
            throw th;
        }
    }
}
