package com.aliyun.iot.aep.sdk.submodule;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.aliyun.alink.linksdk.channel.core.base.AError;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileConnectListener;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileRequestListener;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileChannel;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectState;
import com.aliyun.alink.sdk.jsbridge.BonePluginRegistry;
import com.aliyun.iot.aep.sdk.connectchannel.BoneChannel;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.listener.IoTTokenCreatedListener;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKManager;
import com.aliyun.iot.aep.sdk.framework.sdk.SimpleSDKDelegateImp;
import com.aliyun.iot.aep.sdk.framework.utils.ThreadUtils;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.login.ILoginStatusChangeListener;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class BaseSDKGlue extends SimpleSDKDelegateImp {

    /* renamed from: a, reason: collision with root package name */
    private int f12007a = 1000;

    /* renamed from: b, reason: collision with root package name */
    private int f12008b = 0;

    /* renamed from: c, reason: collision with root package name */
    private boolean f12009c = false;

    /* renamed from: d, reason: collision with root package name */
    private IntentFilter f12010d = null;

    /* renamed from: e, reason: collision with root package name */
    private final AtomicBoolean f12011e = new AtomicBoolean(false);

    /* renamed from: f, reason: collision with root package name */
    private BroadcastReceiver f12012f = new BroadcastReceiver() { // from class: com.aliyun.iot.aep.sdk.submodule.BaseSDKGlue.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ALog.d("BaseSDK", "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
            if (intent == null) {
                return;
            }
            String stringExtra = intent.getStringExtra("status");
            String stringExtra2 = intent.getStringExtra(IoTCredentialManageImpl.AUTH_IOT_TOKEN_IDENTITY_ID_KEY);
            if (IoTCredentialManageImpl.AUTH_IOT_TOKEN_STATUS_REFRESH_SUCCESS.equals(stringExtra)) {
                ALog.i("BaseSDK", "app login. userid = " + stringExtra2);
                BaseSDKGlue.this.f12009c = false;
                ALog.d("BaseSDK", "onLoginStatusChange");
                BaseSDKGlue.this.b(AApplication.getInstance());
                return;
            }
            if (IoTCredentialManageImpl.AUTH_IOT_TOKEN_STATUS_REFRESH_FAIL.equals(stringExtra)) {
                ALog.i("BaseSDK", "app logout. userid = " + stringExtra2);
                BaseSDKGlue.this.a();
            }
        }
    };

    /* renamed from: g, reason: collision with root package name */
    private IoTTokenCreatedListener f12013g = new IoTTokenCreatedListener() { // from class: com.aliyun.iot.aep.sdk.submodule.BaseSDKGlue.6
        @Override // com.aliyun.iot.aep.sdk.credential.listener.IoTTokenCreatedListener
        public void onIoTTokenCreated() {
            BaseSDKGlue.this.d(AApplication.getInstance());
        }
    };

    /* renamed from: h, reason: collision with root package name */
    private Runnable f12014h = new Runnable() { // from class: com.aliyun.iot.aep.sdk.submodule.BaseSDKGlue.7
        @Override // java.lang.Runnable
        public void run() {
            BaseSDKGlue.this.b(AApplication.getInstance());
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Application application) {
        ALog.i("BaseSDK", "mqtt bindAccountInternal");
        IoTCredentialManageImpl ioTCredentialManageImpl = IoTCredentialManageImpl.getInstance(application);
        if (ioTCredentialManageImpl == null) {
            return;
        }
        ALog.i("BaseSDK", "mqtt bindAccountInternal with iottoken");
        String ioTToken = ioTCredentialManageImpl.getIoTToken();
        if (TextUtils.isEmpty(ioTToken)) {
            return;
        }
        MobileChannel.getInstance().bindAccount(ioTToken, new IMobileRequestListener() { // from class: com.aliyun.iot.aep.sdk.submodule.BaseSDKGlue.8
            @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileRequestListener
            public void onFailure(AError aError) {
                ALog.i("BaseSDK", "mqtt bindAccountInternal onFailure aError code = " + aError.getCode());
                if (aError.getCode() == 4103 || aError.getMsg().contains("iotToken")) {
                    BaseSDKGlue.this.f12009c = true;
                }
                BaseSDKGlue.this.f12011e.set(false);
                ALog.i("BaseSDK", "mqtt bindAccountInternal onFailure aError = " + aError.getMsg());
            }

            @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileRequestListener
            public void onSuccess(String str) {
                BaseSDKGlue.this.f12011e.set(true);
                BaseSDKGlue.this.b();
                ALog.i("BaseSDK", "mqtt bindAccountInternal onSuccess");
            }
        });
    }

    @Override // com.aliyun.iot.aep.sdk.framework.sdk.ISDKDelegate
    public int init(final Application application, SDKConfigure sDKConfigure, Map<String, String> map) {
        if (SDKManager.isJsBridgeAvailable()) {
            try {
                BonePluginRegistry.register(BoneChannel.API_NAME, BoneChannel.class);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        if (SDKManager.isOAAvailable()) {
            LoginBusiness.getLoginAdapter().registerLoginListener(new ILoginStatusChangeListener() { // from class: com.aliyun.iot.aep.sdk.submodule.BaseSDKGlue.1
                @Override // com.aliyun.iot.aep.sdk.login.ILoginStatusChangeListener
                public void onLoginStatusChange() {
                    BaseSDKGlue.this.f12009c = false;
                    ALog.d("BaseSDK", "onLoginStatusChange");
                    BaseSDKGlue.this.b(application);
                }
            });
        } else {
            a(application);
        }
        MobileChannel.getInstance().registerConnectListener(true, new IMobileConnectListener() { // from class: com.aliyun.iot.aep.sdk.submodule.BaseSDKGlue.2
            @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileConnectListener
            public void onConnectStateChange(MobileConnectState mobileConnectState) {
                if (MobileConnectState.CONNECTED.equals(mobileConnectState)) {
                    BaseSDKGlue.this.f12009c = false;
                    ALog.d("BaseSDK", "onConnectStateChange");
                    BaseSDKGlue.this.b(application);
                }
            }
        });
        if (!SDKManager.isOAAvailable()) {
            return 0;
        }
        ((OALoginAdapter) LoginBusiness.getLoginAdapter()).registerCompleteBeforeLogoutListener(new OALoginAdapter.OnCompleteBeforeLogoutListener() { // from class: com.aliyun.iot.aep.sdk.submodule.a
            @Override // com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter.OnCompleteBeforeLogoutListener
            public final void doAction(OALoginAdapter.ActionOnBeforeLogoutResultCallback actionOnBeforeLogoutResultCallback) {
                this.f12035a.a(actionOnBeforeLogoutResultCallback);
            }
        });
        return 0;
    }

    private void c(Application application) {
        ALog.d("BaseSDK", "retryBindAccount hasbind:" + this.f12011e + " iotTokenFail:" + this.f12009c);
        if (this.f12011e.get() || this.f12009c) {
            return;
        }
        this.f12007a = Math.min(this.f12007a * 2, 64000);
        ALog.i("BaseSDK", "retryTime:" + this.f12007a);
        ALog.i("BaseSDK", "retryTimes:" + this.f12008b);
        int i2 = this.f12008b;
        this.f12008b = i2 + 1;
        if (i2 < 10) {
            ThreadUtils.retryInMain(this.f12014h, this.f12007a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Application application) {
        ALog.d("BaseSDK", "bindAccount");
        if (SDKManager.isOAAvailable() && !LoginBusiness.isLogin()) {
            ALog.d("BaseSDK", "not login");
            return;
        }
        IoTCredentialManageImpl ioTCredentialManageImpl = IoTCredentialManageImpl.getInstance(application);
        if (ioTCredentialManageImpl != null && TextUtils.isEmpty(ioTCredentialManageImpl.getIoTToken())) {
            ALog.d("BaseSDK", "iotToken is null");
            try {
                ioTCredentialManageImpl.unRegisterIotTokenCreatedListener(this.f12013g);
            } catch (Exception unused) {
                ALog.d("BaseSDK", "unRegisterIotTokenCreatedListener error");
            }
            try {
                ioTCredentialManageImpl.registerIotTokenCreatedListener(this.f12013g);
            } catch (Exception unused2) {
                ALog.d("BaseSDK", "registerIotTokenCreatedListener error");
            }
        } else {
            d(application);
        }
        c(application);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final OALoginAdapter.ActionOnBeforeLogoutResultCallback actionOnBeforeLogoutResultCallback) {
        this.f12011e.set(false);
        MobileChannel.getInstance().unBindAccount(new IMobileRequestListener() { // from class: com.aliyun.iot.aep.sdk.submodule.BaseSDKGlue.3
            @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileRequestListener
            public void onFailure(AError aError) {
                ALog.i("BaseSDK", "mqtt unBindAccount onFailure aError = " + aError.getMsg());
                OALoginAdapter.ActionOnBeforeLogoutResultCallback actionOnBeforeLogoutResultCallback2 = actionOnBeforeLogoutResultCallback;
                if (actionOnBeforeLogoutResultCallback2 != null) {
                    actionOnBeforeLogoutResultCallback2.onResult(-1);
                }
            }

            @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileRequestListener
            public void onSuccess(String str) {
                ALog.i("BaseSDK", "mqtt unBindAccount onSuccess : " + str);
                OALoginAdapter.ActionOnBeforeLogoutResultCallback actionOnBeforeLogoutResultCallback2 = actionOnBeforeLogoutResultCallback;
                if (actionOnBeforeLogoutResultCallback2 != null) {
                    actionOnBeforeLogoutResultCallback2.onResult(0);
                }
            }
        });
    }

    private void a(Application application) {
        ALog.d("BaseSDK", "registerIoTTokenChangeListener() called");
        if (this.f12010d == null) {
            this.f12010d = new IntentFilter("com.ilop.auth.iotToken.change");
        }
        LocalBroadcastManager.getInstance(application).registerReceiver(this.f12012f, this.f12010d);
    }

    private void c() {
        this.f12007a = 1000;
        this.f12008b = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.f12011e.set(false);
        MobileChannel.getInstance().unBindAccount(new IMobileRequestListener() { // from class: com.aliyun.iot.aep.sdk.submodule.BaseSDKGlue.5
            @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileRequestListener
            public void onFailure(AError aError) {
                ALog.i("BaseSDK", "mqtt unBindAccount onFailure aError = " + aError.getMsg());
            }

            @Override // com.aliyun.alink.linksdk.channel.mobile.api.IMobileRequestListener
            public void onSuccess(String str) {
                ALog.i("BaseSDK", "mqtt unBindAccount onSuccess : " + str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        ThreadUtils.remove(this.f12014h);
        c();
    }
}
