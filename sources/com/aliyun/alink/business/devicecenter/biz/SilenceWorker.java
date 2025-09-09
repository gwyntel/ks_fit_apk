package com.aliyun.alink.business.devicecenter.biz;

import android.net.Network;
import android.net.NetworkInfo;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.NetworkConnectiveManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class SilenceWorker {

    /* renamed from: a, reason: collision with root package name */
    public AtomicBoolean f10197a;

    /* renamed from: b, reason: collision with root package name */
    public Map<ISilenceWorker, Object> f10198b;

    /* renamed from: c, reason: collision with root package name */
    public NetworkConnectiveManager.INetworkChangeListener f10199c;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final SilenceWorker f10201a = new SilenceWorker();
    }

    public static SilenceWorker getInstance() {
        return SingletonHolder.f10201a;
    }

    public void registerWorker(ISilenceWorker iSilenceWorker, Object obj) {
        ALog.d("SilenceWorker", "registerWorker() called with: worker = [" + iSilenceWorker + "]");
        if (iSilenceWorker == null) {
            throw new IllegalArgumentException("register worker=null");
        }
        Map<ISilenceWorker, Object> map = this.f10198b;
        if (map != null) {
            map.put(iSilenceWorker, obj);
        }
    }

    public void start() {
        ALog.d("SilenceWorker", "start() called");
        if (this.f10197a.compareAndSet(false, true)) {
            ALog.d("SilenceWorker", "silenceWorkStarted started.");
            NetworkConnectiveManager.getInstance().registerConnectiveListener(this.f10199c);
        }
    }

    public void stop() {
        ALog.d("SilenceWorker", "stop() called");
        this.f10197a.set(false);
        ALog.d("SilenceWorker", "silenceWorkStarted stopped.");
        NetworkConnectiveManager.getInstance().unregisterConnectiveListener(this.f10199c);
    }

    public void unregisterWorker(ISilenceWorker iSilenceWorker) {
        ALog.d("SilenceWorker", "unregisterWorker() called with: worker = [" + iSilenceWorker + "]");
        if (iSilenceWorker == null) {
            throw new IllegalArgumentException("unregister worker=null");
        }
        Map<ISilenceWorker, Object> map = this.f10198b;
        if (map != null) {
            map.remove(iSilenceWorker);
        }
    }

    public SilenceWorker() {
        this.f10197a = new AtomicBoolean(false);
        this.f10198b = new ConcurrentHashMap();
        this.f10199c = new NetworkConnectiveManager.INetworkChangeListener() { // from class: com.aliyun.alink.business.devicecenter.biz.SilenceWorker.1
            @Override // com.aliyun.alink.business.devicecenter.utils.NetworkConnectiveManager.INetworkChangeListener
            public void onNetworkStateChange(NetworkInfo networkInfo, Network network) {
                if (networkInfo == null || !networkInfo.isConnected()) {
                    ALog.d("SilenceWorker", "silenceWorker -> onNetworkStateChange network null or not connected.");
                    return;
                }
                for (Map.Entry entry : SilenceWorker.this.f10198b.entrySet()) {
                    if (entry.getKey() != null) {
                        ((ISilenceWorker) entry.getKey()).work(entry.getValue());
                    }
                }
            }
        };
    }
}
