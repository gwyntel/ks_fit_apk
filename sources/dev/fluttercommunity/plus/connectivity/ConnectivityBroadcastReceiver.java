package dev.fluttercommunity.plus.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import io.flutter.plugin.common.EventChannel;

/* loaded from: classes4.dex */
public class ConnectivityBroadcastReceiver extends BroadcastReceiver implements EventChannel.StreamHandler {
    public static final String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private final Connectivity connectivity;
    private final Context context;
    private EventChannel.EventSink events;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private ConnectivityManager.NetworkCallback networkCallback;

    public ConnectivityBroadcastReceiver(Context context, Connectivity connectivity) {
        this.context = context;
        this.connectivity = connectivity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendEvent$0() {
        this.events.success(this.connectivity.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendEvent$1(String str) {
        this.events.success(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent() {
        this.mainHandler.post(new Runnable() { // from class: dev.fluttercommunity.plus.connectivity.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f25002a.lambda$sendEvent$0();
            }
        });
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
        if (Build.VERSION.SDK_INT < 24) {
            try {
                this.context.unregisterReceiver(this);
            } catch (Exception unused) {
            }
        } else if (this.networkCallback != null) {
            this.connectivity.getConnectivityManager().unregisterNetworkCallback(this.networkCallback);
            this.networkCallback = null;
        }
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        this.events = eventSink;
        if (Build.VERSION.SDK_INT < 24) {
            this.context.registerReceiver(this, new IntentFilter(CONNECTIVITY_ACTION));
        } else {
            this.networkCallback = new ConnectivityManager.NetworkCallback() { // from class: dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    ConnectivityBroadcastReceiver.this.sendEvent();
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                    ConnectivityBroadcastReceiver.this.sendEvent("none");
                }
            };
            this.connectivity.getConnectivityManager().registerDefaultNetworkCallback(this.networkCallback);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        EventChannel.EventSink eventSink = this.events;
        if (eventSink != null) {
            eventSink.success(this.connectivity.a());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent(final String str) {
        this.mainHandler.post(new Runnable() { // from class: dev.fluttercommunity.plus.connectivity.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f25003a.lambda$sendEvent$1(str);
            }
        });
    }
}
