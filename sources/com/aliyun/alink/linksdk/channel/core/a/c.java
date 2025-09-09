package com.aliyun.alink.linksdk.channel.core.a;

import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.id2.Id2Itls;
import com.aliyun.alink.linksdk.tools.ALog;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.NetworkModule;

/* loaded from: classes2.dex */
public class c implements NetworkModule {

    /* renamed from: a, reason: collision with root package name */
    private a f10846a;

    /* renamed from: b, reason: collision with root package name */
    private Id2Itls f10847b;

    /* renamed from: c, reason: collision with root package name */
    private long f10848c = 0;

    /* renamed from: d, reason: collision with root package name */
    private boolean f10849d = true;

    /* renamed from: e, reason: collision with root package name */
    private AtomicBoolean f10850e = new AtomicBoolean(false);

    public c(a aVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f10846a = null;
        this.f10847b = null;
        this.f10846a = aVar;
        this.f10847b = new Id2Itls();
        int i2 = MqttConfigure.itlsLogLevel;
        int level = (ALog.getLevel() & 255) + 2;
        com.aliyun.alink.linksdk.channel.core.b.a.a("ITLSNetworkModule", "itlsDebugLevel = " + i2 + "， jniLevel=" + level);
        this.f10847b.setItlsDebugLevel(i2);
        this.f10847b.setJniDebugLevel(level);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public InputStream getInputStream() throws IOException {
        if (this.f10849d) {
            throw new IOException("ITLS Channel Closed.");
        }
        return new b(this.f10847b, this.f10848c);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public OutputStream getOutputStream() throws IOException {
        if (this.f10849d) {
            throw new IOException("ITLS Channel Closed.");
        }
        return new d(this.f10847b, this.f10848c);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public String getServerURI() {
        return "ssl://" + this.f10846a.f10836a + ":" + this.f10846a.f10837b;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void start() throws MqttException {
        com.aliyun.alink.linksdk.channel.core.b.a.b("ITLSNetworkModule", "start");
        this.f10849d = false;
        try {
            Id2Itls id2Itls = this.f10847b;
            a aVar = this.f10846a;
            this.f10848c = id2Itls.establishItls(aVar.f10836a, aVar.f10837b, aVar.f10838c, aVar.f10839d);
        } catch (Exception e2) {
            e2.printStackTrace();
            this.f10849d = true;
        }
        this.f10850e.set(true);
        com.aliyun.alink.linksdk.channel.core.b.a.a("ITLSNetworkModule", "handleId=" + this.f10848c);
        if (this.f10848c != 0) {
            return;
        }
        com.aliyun.alink.linksdk.channel.core.b.a.d("ITLSNetworkModule", "establishItls failed.");
        this.f10849d = true;
        throw new MqttException(this.f10847b.getAlertType());
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void stop() {
        com.aliyun.alink.linksdk.channel.core.b.a.b("ITLSNetworkModule", "stop");
        try {
            this.f10849d = true;
            if (this.f10850e.compareAndSet(true, false)) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("ITLSNetworkModule", "stop itls destroy.");
                this.f10847b.destroyItls(this.f10848c);
                this.f10850e.set(false);
            } else {
                com.aliyun.alink.linksdk.channel.core.b.a.a("ITLSNetworkModule", "stop itls already destroyed.");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
