package androidx.health.platform.client.impl.ipc.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.health.platform.client.impl.ipc.internal.ServiceConnection;
import androidx.media3.common.C;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class ConnectionManager implements Handler.Callback, ServiceConnection.Callback {
    private static final int MSG_CONNECTED = 1;
    private static final int MSG_DISCONNECTED = 2;
    private static final int MSG_EXECUTE = 3;
    private static final int MSG_REGISTER_LISTENER = 4;
    private static final int MSG_UNBIND = 6;
    private static final int MSG_UNREGISTER_LISTENER = 5;
    private static final String TAG = "ConnectionManager";
    private boolean mBindToSelfEnabled;
    private final Context mContext;
    private final Handler mHandler;
    private final Map<String, ServiceConnection> mServiceConnectionMap = new HashMap();

    private static class ListenerHolder {
        private final ListenerKey mListenerKey;
        private final QueueOperation mListenerOperation;

        ListenerHolder(ListenerKey listenerKey, QueueOperation queueOperation) {
            this.mListenerKey = listenerKey;
            this.mListenerOperation = queueOperation;
        }

        ListenerKey a() {
            return this.mListenerKey;
        }

        QueueOperation b() {
            return this.mListenerOperation;
        }
    }

    public ConnectionManager(Context context, Looper looper) {
        this.mContext = context;
        this.mHandler = new Handler(looper, this);
    }

    void a(ServiceConnection serviceConnection) {
        this.mHandler.removeMessages(6, serviceConnection);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(6, serviceConnection), C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
    }

    ServiceConnection b(ConnectionConfiguration connectionConfiguration) {
        String strC = connectionConfiguration.c();
        ServiceConnection serviceConnection = this.mServiceConnectionMap.get(strC);
        if (serviceConnection != null) {
            return serviceConnection;
        }
        ServiceConnection serviceConnection2 = new ServiceConnection(this.mContext, connectionConfiguration, new DefaultExecutionTracker(), this);
        this.mServiceConnectionMap.put(strC, serviceConnection2);
        return serviceConnection2;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(@NonNull Message message) {
        switch (message.what) {
            case 1:
                ServiceConnection serviceConnection = (ServiceConnection) message.obj;
                serviceConnection.h();
                serviceConnection.i();
                serviceConnection.f();
                a(serviceConnection);
                return true;
            case 2:
                ((ServiceConnection) message.obj).g();
                return true;
            case 3:
                QueueOperation queueOperation = (QueueOperation) message.obj;
                ServiceConnection serviceConnectionB = b(queueOperation.getConnectionConfiguration());
                serviceConnectionB.d(queueOperation);
                a(serviceConnectionB);
                return true;
            case 4:
                ListenerHolder listenerHolder = (ListenerHolder) message.obj;
                ServiceConnection serviceConnectionB2 = b(listenerHolder.b().getConnectionConfiguration());
                serviceConnectionB2.j(listenerHolder.a(), listenerHolder.b());
                a(serviceConnectionB2);
                return true;
            case 5:
                ListenerHolder listenerHolder2 = (ListenerHolder) message.obj;
                ServiceConnection serviceConnectionB3 = b(listenerHolder2.b().getConnectionConfiguration());
                serviceConnectionB3.k(listenerHolder2.a(), listenerHolder2.b());
                a(serviceConnectionB3);
                return true;
            case 6:
                ServiceConnection serviceConnection2 = (ServiceConnection) message.obj;
                if (!this.mHandler.hasMessages(3) && !this.mHandler.hasMessages(4) && !this.mHandler.hasMessages(5) && !serviceConnection2.c()) {
                    a(serviceConnection2);
                }
                return true;
            default:
                Log.e(TAG, "Received unknown message: " + message.what);
                return false;
        }
    }

    @Override // androidx.health.platform.client.impl.ipc.internal.ServiceConnection.Callback
    public boolean isBindToSelfEnabled() {
        return this.mBindToSelfEnabled;
    }

    @Override // androidx.health.platform.client.impl.ipc.internal.ServiceConnection.Callback
    public void onConnected(ServiceConnection serviceConnection) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1, serviceConnection));
    }

    @Override // androidx.health.platform.client.impl.ipc.internal.ServiceConnection.Callback
    public void onDisconnected(ServiceConnection serviceConnection, long j2) {
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(2, serviceConnection), j2);
    }

    public void registerListener(ListenerKey listenerKey, QueueOperation queueOperation) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(4, new ListenerHolder(listenerKey, queueOperation)));
    }

    public void scheduleForExecution(QueueOperation queueOperation) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(3, queueOperation));
    }

    public void setBindToSelf(boolean z2) {
        this.mBindToSelfEnabled = z2;
    }

    public void unregisterListener(ListenerKey listenerKey, QueueOperation queueOperation) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(5, new ListenerHolder(listenerKey, queueOperation)));
    }
}
