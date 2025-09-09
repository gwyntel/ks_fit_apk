package com.aliyun.alink.linksdk.channel.mobile.api;

import android.content.Context;
import com.aliyun.alink.linksdk.channel.mobile.a.a;
import com.aliyun.alink.linksdk.channel.mobile.b.b;
import com.aliyun.alink.linksdk.channel.mobile.b.d;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;

/* loaded from: classes2.dex */
public class MobileChannel implements IMobileChannel {
    private static final String TAG = "MobileChannel";
    private d mobileChannelImpl;

    private static class InstanceHolder {
        private static final IMobileChannel sInstance = new MobileChannel();

        private InstanceHolder() {
        }
    }

    public static IMobileChannel getInstance() {
        return InstanceHolder.sInstance;
    }

    public static void setOpenLog(boolean z2) {
        a.a(z2 ? 3 : 6);
        b.f11002a = z2;
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void asyncSendRequest(String str, Map<String, Object> map, Object obj, IMobileRequestListener iMobileRequestListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.asyncSendRequest(str, map, obj, iMobileRequestListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void ayncSendPublishRequest(String str, Object obj, IMobileRequestListener iMobileRequestListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.ayncSendPublishRequest(str, obj, iMobileRequestListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void bindAccount(String str, IMobileRequestListener iMobileRequestListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.bindAccount(str, iMobileRequestListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void endConnect() {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.endConnect();
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public String getClientId() {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        return this.mobileChannelImpl.getClientId();
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public MobileConnectState getMobileConnectState() {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        return this.mobileChannelImpl.getMobileConnectState();
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void registerConnectListener(boolean z2, IMobileConnectListener iMobileConnectListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.registerConnectListener(z2, iMobileConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void registerDownstreamListener(boolean z2, IMobileDownstreamListener iMobileDownstreamListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.registerDownstreamListener(z2, iMobileDownstreamListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void startConnect(Context context, MobileConnectConfig mobileConnectConfig, IMobileConnectListener iMobileConnectListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.startConnect(context, mobileConnectConfig, iMobileConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void subscrbie(String str, IMobileSubscrbieListener iMobileSubscrbieListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.subscrbie(str, iMobileSubscrbieListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void unBindAccount(IMobileRequestListener iMobileRequestListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.unBindAccount(iMobileRequestListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void unRegisterConnectListener(IMobileConnectListener iMobileConnectListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.unRegisterConnectListener(iMobileConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void unRegisterDownstreamListener(IMobileDownstreamListener iMobileDownstreamListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.unRegisterDownstreamListener(iMobileDownstreamListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void unSubscrbie(String str, IMobileSubscrbieListener iMobileSubscrbieListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.unSubscrbie(str, iMobileSubscrbieListener);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileChannel
    public void endConnect(long j2, IMqttActionListener iMqttActionListener) {
        if (this.mobileChannelImpl == null) {
            this.mobileChannelImpl = new d();
        }
        this.mobileChannelImpl.endConnect(j2, iMqttActionListener);
    }
}
