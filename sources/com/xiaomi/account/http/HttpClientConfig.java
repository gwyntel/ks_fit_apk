package com.xiaomi.account.http;

import android.net.Network;
import androidx.media3.common.C;

/* loaded from: classes4.dex */
public class HttpClientConfig {
    private static volatile long defaultConnectTimeoutMs = 10000;
    private static volatile long defaultWaitCellularTimeoutMs = 5000;
    public final long connectTimeoutMs;
    public final int netWorkSlotId;
    public final Network network;
    public final long readTimeoutMs;
    public final long waitCellularTimeoutMs;
    public final long writeTimeoutMs;

    public static class Builder {
        int netWorkSlotId = -1;
        Network network;

        public HttpClientConfig build() {
            return new HttpClientConfig(this);
        }

        public Builder network(Network network) {
            this.network = network;
            return this;
        }

        public Builder useDataNetWork(int i2) {
            this.netWorkSlotId = i2;
            return this;
        }
    }

    public static void setDefaultConnectTimeoutMs(long j2) {
        defaultConnectTimeoutMs = j2;
    }

    public static void setDefaultWaitCellularTimeoutMs(long j2) {
        defaultWaitCellularTimeoutMs = j2;
    }

    private HttpClientConfig(Builder builder) {
        this.network = builder.network;
        this.netWorkSlotId = builder.netWorkSlotId;
        this.connectTimeoutMs = defaultConnectTimeoutMs;
        this.waitCellularTimeoutMs = defaultWaitCellularTimeoutMs;
        this.readTimeoutMs = C.DEFAULT_SEEK_FORWARD_INCREMENT_MS;
        this.writeTimeoutMs = C.DEFAULT_SEEK_FORWARD_INCREMENT_MS;
    }
}
