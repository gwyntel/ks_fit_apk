package com.aliyun.alink.linksdk.channel.core.persistent;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.channel.core.base.ARequest;
import com.aliyun.alink.linksdk.channel.core.base.ASend;
import com.aliyun.alink.linksdk.channel.core.base.IOnCallListener;
import com.aliyun.alink.linksdk.channel.core.persistent.a.a;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.b;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.PersisitentNetParams;

/* loaded from: classes2.dex */
public class PersistentNet implements IPersisitentNet {

    /* renamed from: a, reason: collision with root package name */
    private static String f10855a = "PersistentNet";

    /* renamed from: b, reason: collision with root package name */
    private static String f10856b = "MQTT";

    /* renamed from: c, reason: collision with root package name */
    private static String f10857c = "ACCS";

    /* renamed from: d, reason: collision with root package name */
    private static String f10858d = "0.7.7";

    /* renamed from: g, reason: collision with root package name */
    public static final /* synthetic */ int f10859g = 0;

    /* renamed from: e, reason: collision with root package name */
    private String f10860e = f10856b;

    /* renamed from: f, reason: collision with root package name */
    private IPersisitentNet f10861f = null;

    private static class InstanceHolder {

        /* renamed from: a, reason: collision with root package name */
        private static final PersistentNet f10862a = new PersistentNet();

        private InstanceHolder() {
        }
    }

    private IPersisitentNet a() {
        if (this.f10861f == null) {
            if (f10857c.equals(this.f10860e)) {
                this.f10861f = a.a();
            } else {
                this.f10861f = b.a();
            }
        }
        return this.f10861f;
    }

    public static PersistentNet getInstance() {
        return InstanceHolder.f10862a;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.INet
    public ASend asyncSend(ARequest aRequest, IOnCallListener iOnCallListener) {
        return a().asyncSend((PersistentRequest) aRequest, iOnCallListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void destroy() {
        a().destroy();
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void dynamicRegister(Context context, PersistentInitParams persistentInitParams, IOnCallListener iOnCallListener) {
        a().dynamicRegister(context, persistentInitParams, iOnCallListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public PersistentConnectState getConnectState() {
        return a().getConnectState();
    }

    public String getDefaultProtocol() {
        return this.f10860e;
    }

    public PersistentInitParams getInitParams() {
        IPersisitentNet iPersisitentNetA = a();
        if (iPersisitentNetA instanceof b) {
            return ((b) iPersisitentNetA).b();
        }
        return null;
    }

    public String getSDKVersion() {
        return f10858d;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void init(Context context, PersistentInitParams persistentInitParams) {
        com.aliyun.alink.linksdk.channel.core.b.a.a(f10855a, "init(), SDK Ver = " + f10858d);
        a().init(context, persistentInitParams);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public boolean isDeiniting() {
        return a().isDeiniting();
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void openLog(boolean z2) {
        a().openLog(z2);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void reconnect() {
        a().reconnect();
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.INet
    public void retry(ASend aSend) {
        a().retry(aSend);
    }

    public void setDefaultProtocol(String str) {
        if (str == null || str.equals(f10856b)) {
            this.f10860e = f10856b;
            this.f10861f = b.a();
        } else if (str.equals(f10857c)) {
            this.f10860e = f10857c;
            this.f10861f = a.a();
        }
    }

    protected void setReportVersion(String str) {
        com.aliyun.alink.linksdk.channel.core.b.a.a(f10855a, "setReportVersion() called with: version = [" + str + "], moduleVersion=0.7.7-5da1706");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        f10858d = str;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void subscribe(String str, IOnSubscribeListener iOnSubscribeListener) {
        a().subscribe(str, iOnSubscribeListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void subscribeRrpc(String str, IOnSubscribeRrpcListener iOnSubscribeRrpcListener) {
        a().subscribeRrpc(str, iOnSubscribeRrpcListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void unSubscribe(String str, IOnSubscribeListener iOnSubscribeListener) {
        a().unSubscribe(str, iOnSubscribeListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void destroy(long j2, Object obj, Object obj2) {
        if ((a() instanceof b) && com.aliyun.alink.linksdk.channel.core.b.b.a("org.eclipse.paho.client.mqttv3.IMqttActionListener")) {
            a().destroy(j2, obj, obj2);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void subscribe(String str, PersisitentNetParams persisitentNetParams, IOnSubscribeListener iOnSubscribeListener) {
        a().subscribe(str, persisitentNetParams, iOnSubscribeListener);
    }
}
