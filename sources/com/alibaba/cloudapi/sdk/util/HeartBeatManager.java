package com.alibaba.cloudapi.sdk.util;

import android.util.Log;
import com.alibaba.cloudapi.sdk.client.WebSocketApiClient;

/* loaded from: classes2.dex */
public class HeartBeatManager implements Runnable {
    int heartbeatInterval;
    boolean isStop = false;
    WebSocketApiClient webSocketApiClient;

    public HeartBeatManager(WebSocketApiClient webSocketApiClient, int i2) {
        this.webSocketApiClient = webSocketApiClient;
        this.heartbeatInterval = i2;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        while (true) {
            try {
                Thread.sleep(this.heartbeatInterval);
            } catch (Exception e2) {
                Log.e("SDK", "SEND HEARTBEAT FAILED", e2);
            }
            if (this.isStop) {
                return;
            } else {
                this.webSocketApiClient.sendHeatbeart();
            }
        }
    }

    public void stop() {
        this.isStop = true;
    }
}
