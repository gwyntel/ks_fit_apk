package com.aliyun.iot.aep.sdk.init;

import android.app.Application;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.channel.gateway.api.GatewayChannel;
import com.aliyun.alink.linksdk.channel.gateway.api.GatewayConnectConfig;
import com.aliyun.alink.linksdk.channel.gateway.api.GatewayConnectState;
import com.aliyun.alink.linksdk.channel.gateway.api.IGatewayConnectListener;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileConnectListener;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileChannel;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectConfig;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectState;
import com.aliyun.alink.linksdk.cmp.api.ConnectSDK;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.config.AConfigure;
import com.aliyun.iot.aep.sdk.framework.config.GlobalConfig;
import com.aliyun.iot.aep.sdk.framework.network.NetworkConnectiveManager;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKManager;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.threadpool.ThreadPool;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/* loaded from: classes3.dex */
public class DownStreamHelper {

    /* renamed from: b, reason: collision with root package name */
    private static int f11837b = 2000;

    /* renamed from: a, reason: collision with root package name */
    private MobileConnectConfig f11838a;

    /* renamed from: c, reason: collision with root package name */
    private Handler f11839c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f11840d;

    /* renamed from: e, reason: collision with root package name */
    private Runnable f11841e;

    /* renamed from: f, reason: collision with root package name */
    private final AtomicBoolean f11842f;

    /* renamed from: g, reason: collision with root package name */
    private final NetworkConnectiveManager.INetworkChangeListener f11843g;

    static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final DownStreamHelper f11852a = new DownStreamHelper();
    }

    public static final DownStreamHelper getInstance() {
        return a.f11852a;
    }

    public void initBreeze(Application application) {
        ALog.i("DownStreamHelper", "initGateWayChannel app：" + application);
        try {
            String clientId = MobileChannel.getInstance().getClientId();
            if (TextUtils.isEmpty(clientId)) {
                ALog.w("DownStreamHelper", "请检查长连接通道是否初始化成功");
                return;
            }
            String str = clientId.split("&")[1];
            String str2 = clientId.split("&")[0];
            if (SDKManager.isGatewayConnectAvailable()) {
                GatewayChannel.getInstance().startConnect(application, new GatewayConnectConfig(str, str2, ""), new IGatewayConnectListener() { // from class: com.aliyun.iot.aep.sdk.init.DownStreamHelper.5
                    @Override // com.aliyun.alink.linksdk.channel.gateway.api.IGatewayConnectListener
                    public void onConnectStateChange(GatewayConnectState gatewayConnectState) {
                        ALog.d("DownStreamHelper", "IGatewayConnectListener onConnectStateChange(), state = " + gatewayConnectState.toString());
                        if (gatewayConnectState == GatewayConnectState.CONNECTED) {
                            ALog.d("DownStreamHelper", "网关建联成功");
                        } else if (gatewayConnectState == GatewayConnectState.CONNECTFAIL) {
                            ALog.i("DownStreamHelper", "Gateway connect failed");
                        }
                    }
                });
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.e("DownStreamHelper", "breeze sdk missing");
        }
    }

    public void startMqtt(String str) {
        if (this.f11838a == null) {
            return;
        }
        a();
        f11837b = 2000;
        MobileConnectConfig mobileConnectConfig = this.f11838a;
        mobileConnectConfig.channelHost = str;
        startMqttOne(mobileConnectConfig);
    }

    public void startMqttOne(MobileConnectConfig mobileConnectConfig) {
        a(AApplication.getInstance());
        startMqtt(mobileConnectConfig);
    }

    private DownStreamHelper() {
        this.f11840d = false;
        this.f11841e = new Runnable() { // from class: com.aliyun.iot.aep.sdk.init.DownStreamHelper.1
            @Override // java.lang.Runnable
            public void run() {
                if (MobileChannel.getInstance().getMobileConnectState() != MobileConnectState.CONNECTED) {
                    ALog.d("DownStreamHelper", "do re-init mqtt:" + JSON.toJSONString(DownStreamHelper.this.f11838a));
                    DownStreamHelper downStreamHelper = DownStreamHelper.this;
                    downStreamHelper.startMqtt(downStreamHelper.f11838a);
                }
            }
        };
        this.f11842f = new AtomicBoolean(false);
        this.f11843g = new NetworkConnectiveManager.INetworkChangeListener() { // from class: com.aliyun.iot.aep.sdk.init.DownStreamHelper.2
            @Override // com.aliyun.iot.aep.sdk.framework.network.NetworkConnectiveManager.INetworkChangeListener
            public void onNetworkStateChange(NetworkInfo networkInfo, Network network) {
                ALog.d("DownStreamHelper", "onNetworkStateChange() called with: networkInfo = [" + networkInfo + "]");
                if (networkInfo == null || !networkInfo.isConnected()) {
                    return;
                }
                try {
                    if (MobileChannel.getInstance().getMobileConnectState() != MobileConnectState.CONNECTED) {
                        ALog.w("DownStreamHelper", "网络状态变化，重新初始化长连接通道");
                        DownStreamHelper downStreamHelper = DownStreamHelper.this;
                        downStreamHelper.startMqtt(downStreamHelper.f11838a);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ALog.e("DownStreamHelper", e2.toString());
                }
            }
        };
        this.f11838a = new MobileConnectConfig();
        this.f11839c = new Handler(Looper.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Application application) {
        try {
            this.f11840d = false;
            ALog.d("DownStreamHelper", "unregisterNetworkListener");
            NetworkConnectiveManager.getInstance().unregisterConnectiveListener(this.f11843g);
            NetworkConnectiveManager.getInstance().deinitNetworkConnectiveManager();
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.e("DownStreamHelper", e2.toString());
        }
    }

    private void a(Application application) {
        try {
            if (this.f11840d) {
                return;
            }
            ALog.d("DownStreamHelper", "registerNetworkListener");
            NetworkConnectiveManager.getInstance().initNetworkConnectiveManager(application.getApplicationContext());
            NetworkConnectiveManager.getInstance().registerConnectiveListener(this.f11843g);
            this.f11840d = true;
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.e("DownStreamHelper", e2.toString());
        }
    }

    public void startMqtt(final MobileConnectConfig mobileConnectConfig) {
        this.f11838a = mobileConnectConfig;
        GlobalConfig.getInstance().getInitConfig().getRegionType();
        if (this.f11842f.get()) {
            ALog.e("DownStreamHelper", "ending connect ongoing, update config and do nothing then");
            return;
        }
        this.f11842f.set(true);
        ThreadPool.DefaultThreadPool.getInstance().submit(new Runnable() { // from class: com.aliyun.iot.aep.sdk.init.DownStreamHelper.3
            @Override // java.lang.Runnable
            public void run() {
                ALog.d("DownStreamHelper", "startMqtt stateNow：" + MobileChannel.getInstance().getMobileConnectState());
                MobileChannel.getInstance().endConnect(10000L, new IMqttActionListener() { // from class: com.aliyun.iot.aep.sdk.init.DownStreamHelper.3.1
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                        ALog.d("DownStreamHelper", "mqtt endConnect fail");
                        DownStreamHelper.this.f11842f.set(false);
                        AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                        DownStreamHelper.this.a(mobileConnectConfig);
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken iMqttToken) {
                        ALog.d("DownStreamHelper", "mqtt endConnect success");
                        DownStreamHelper.this.f11842f.set(false);
                        AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                        DownStreamHelper.this.a(mobileConnectConfig);
                    }
                });
            }
        });
        a(AApplication.getInstance());
    }

    private void b() {
        try {
            a();
            f11837b = Math.min(f11837b * 2, 64000);
            ALog.d("DownStreamHelper", "startReInit delay=" + f11837b);
            this.f11839c.postDelayed(this.f11841e, (long) f11837b);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MobileConnectConfig mobileConnectConfig) {
        ALog.d("DownStreamHelper", "startMqttInternal:" + JSON.toJSONString(mobileConnectConfig));
        if (mobileConnectConfig == null) {
            return;
        }
        final AApplication aApplication = AApplication.getInstance();
        MobileChannel.getInstance().startConnect(aApplication, mobileConnectConfig, new IMobileConnectListener() { // from class: com.aliyun.iot.aep.sdk.init.DownStreamHelper.4
            @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileConnectListener
            public void onConnectStateChange(MobileConnectState mobileConnectState) {
                ALog.d("DownStreamHelper", "IMobileConnectListener onConnectStateChange(), state = " + mobileConnectState.toString());
                AConfigure.getInstance().putConfig("KEY_TRACE_ID", MobileChannel.getInstance().getClientId());
                if (MobileConnectState.CONNECTED.equals(mobileConnectState)) {
                    DownStreamHelper.this.b(aApplication);
                    DownStreamHelper.this.initBreeze(aApplication);
                    ConnectSDK.getInstance().init(aApplication);
                }
            }
        });
        b();
    }

    private void a() {
        ALog.d("DownStreamHelper", "stopReInit");
        try {
            this.f11839c.removeCallbacks(this.f11841e);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
