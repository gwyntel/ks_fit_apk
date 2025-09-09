package com.aliyun.iot.aep.sdk.apiclient.adapter;

import com.aliyun.iot.aep.sdk.apiclient.emuns.Env;
import javax.net.SocketFactory;
import okhttp3.EventListener;

/* loaded from: classes3.dex */
public class IoTHttpClientAdapterConfig {
    public static final long DEFAULT_TIMEOUT = 10000;

    /* renamed from: a, reason: collision with root package name */
    public String f11592a;

    /* renamed from: b, reason: collision with root package name */
    public Env f11593b;

    /* renamed from: c, reason: collision with root package name */
    public String f11594c;

    /* renamed from: d, reason: collision with root package name */
    public String f11595d;

    /* renamed from: e, reason: collision with root package name */
    public long f11596e;

    /* renamed from: f, reason: collision with root package name */
    public long f11597f;

    /* renamed from: g, reason: collision with root package name */
    public long f11598g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f11599h = false;

    /* renamed from: i, reason: collision with root package name */
    public SocketFactory f11600i;

    /* renamed from: j, reason: collision with root package name */
    public EventListener f11601j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f11602k;

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        public String f11603a;

        /* renamed from: b, reason: collision with root package name */
        public Env f11604b;

        /* renamed from: c, reason: collision with root package name */
        public String f11605c;

        /* renamed from: d, reason: collision with root package name */
        public String f11606d;

        /* renamed from: e, reason: collision with root package name */
        public long f11607e;

        /* renamed from: f, reason: collision with root package name */
        public long f11608f;

        /* renamed from: g, reason: collision with root package name */
        public long f11609g;

        /* renamed from: h, reason: collision with root package name */
        public SocketFactory f11610h;

        /* renamed from: i, reason: collision with root package name */
        public EventListener f11611i;

        /* renamed from: j, reason: collision with root package name */
        public boolean f11612j = true;

        /* renamed from: k, reason: collision with root package name */
        public boolean f11613k = false;

        public IoTHttpClientAdapterConfig build() {
            IoTHttpClientAdapterConfig ioTHttpClientAdapterConfig = new IoTHttpClientAdapterConfig();
            ioTHttpClientAdapterConfig.f11593b = this.f11604b;
            ioTHttpClientAdapterConfig.f11592a = this.f11603a;
            ioTHttpClientAdapterConfig.f11594c = this.f11605c;
            ioTHttpClientAdapterConfig.f11595d = this.f11606d;
            if (this.f11607e <= 0) {
                this.f11607e = 10000L;
            }
            if (this.f11608f <= 0) {
                this.f11608f = 10000L;
            }
            if (this.f11609g <= 0) {
                this.f11609g = 10000L;
            }
            ioTHttpClientAdapterConfig.f11596e = this.f11607e;
            ioTHttpClientAdapterConfig.f11597f = this.f11608f;
            ioTHttpClientAdapterConfig.f11598g = this.f11609g;
            if (this.f11610h == null) {
                this.f11610h = SocketFactory.getDefault();
            }
            ioTHttpClientAdapterConfig.f11600i = this.f11610h;
            ioTHttpClientAdapterConfig.f11601j = this.f11611i;
            ioTHttpClientAdapterConfig.f11602k = this.f11612j;
            ioTHttpClientAdapterConfig.f11599h = this.f11613k;
            return ioTHttpClientAdapterConfig;
        }

        public Builder setApiEnv(Env env) {
            this.f11604b = env;
            return this;
        }

        public Builder setAppKey(String str) {
            this.f11605c = str;
            return this;
        }

        public Builder setAuthCode(String str) {
            this.f11606d = str;
            return this;
        }

        public Builder setConnectTimeout(long j2) {
            if (j2 <= 0) {
                j2 = 10000;
            }
            this.f11607e = j2;
            return this;
        }

        public Builder setDebug(boolean z2) {
            this.f11613k = z2;
            return this;
        }

        public Builder setDefaultHost(String str) {
            this.f11603a = str;
            return this;
        }

        public Builder setEventListener(EventListener eventListener) {
            this.f11611i = eventListener;
            return this;
        }

        public Builder setHttpConnectionRetry(boolean z2) {
            this.f11612j = z2;
            return this;
        }

        public Builder setReadTimeout(long j2) {
            if (j2 <= 0) {
                j2 = 10000;
            }
            this.f11608f = j2;
            return this;
        }

        public Builder setSocketFactory(SocketFactory socketFactory) {
            this.f11610h = socketFactory;
            return this;
        }

        public Builder setWriteTimeout(long j2) {
            if (j2 <= 0) {
                j2 = 10000;
            }
            this.f11609g = j2;
            return this;
        }
    }

    public Env getApiEnv() {
        return this.f11593b;
    }

    public String getAppKey() {
        return this.f11594c;
    }

    public String getAuthCode() {
        return this.f11595d;
    }

    public long getConnectTimeout() {
        return this.f11596e;
    }

    public String getDefaultHost() {
        return this.f11592a;
    }

    public EventListener getEventListener() {
        return this.f11601j;
    }

    public long getReadTimeout() {
        return this.f11597f;
    }

    public SocketFactory getSocketFactory() {
        return this.f11600i;
    }

    public long getWriteTimeout() {
        return this.f11598g;
    }

    public boolean isDebug() {
        return this.f11599h;
    }

    public boolean isHttpConnectionRetry() {
        return this.f11602k;
    }

    public void setDefaultHost(String str) {
        this.f11592a = str;
    }
}
