package com.aliyun.iot.aep.sdk.init;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.sdk.android.push.register.OppoRegister;
import com.alibaba.sdk.android.push.register.VivoRegister;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.apiclient.adapter.APIGatewayHttpAdapterImpl;
import com.aliyun.iot.aep.sdk.framework.config.GlobalConfig;
import com.aliyun.iot.aep.sdk.framework.region.RegionManager;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKManager;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter;
import com.aliyun.iot.push.PushChannelType;
import com.aliyun.iot.push.PushInitCallback;
import com.aliyun.iot.push.PushInitConfig;
import com.aliyun.iot.push.PushManager;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.android.agoo.common.CallBack;

/* loaded from: classes3.dex */
public class PushManagerHelper {

    /* renamed from: g, reason: collision with root package name */
    private static int f11856g = 1000;

    /* renamed from: a, reason: collision with root package name */
    private String f11857a;

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f11858b;

    /* renamed from: c, reason: collision with root package name */
    private volatile String f11859c;

    /* renamed from: d, reason: collision with root package name */
    private AtomicBoolean f11860d;

    /* renamed from: e, reason: collision with root package name */
    private Application f11861e;

    /* renamed from: f, reason: collision with root package name */
    private a f11862f;

    /* renamed from: h, reason: collision with root package name */
    private AtomicInteger f11863h;

    static final class a extends Handler {
        public a() {
            super(Looper.myLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super.handleMessage(message);
            if (message == null) {
            }
            switch (message.what) {
                case 131478:
                    ALog.d("PushManagerHelper", "MSG_INIT_PUSH " + message.obj);
                    PushManagerHelper pushManagerHelper = PushManagerHelper.getInstance();
                    Application application = PushManagerHelper.getInstance().f11861e;
                    Object obj = message.obj;
                    pushManagerHelper.a(application, (String) (obj != null ? (String) obj : null), message.arg1 == 0);
                    break;
                case 131479:
                    ALog.d("PushManagerHelper", "MSG_INIT_PUSH_INTER");
                    if (PushManagerHelper.getInstance().f11861e != null) {
                        PushManager pushManager = PushManager.getInstance();
                        Application application2 = PushManagerHelper.getInstance().f11861e;
                        Object obj2 = message.obj;
                        pushManager.init(application2, obj2 != null ? (PushInitConfig) obj2 : null);
                        break;
                    }
                    break;
            }
        }
    }

    static class b {

        /* renamed from: a, reason: collision with root package name */
        private static final PushManagerHelper f11874a = new PushManagerHelper();
    }

    public static final PushManagerHelper getInstance() {
        return b.f11874a;
    }

    public void bindUser() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (SDKManager.isPushAvailable()) {
            if (!LoginBusiness.isLogin()) {
                ALog.e("PushManagerHelper", "push bindUser abort because no login");
                return;
            }
            ALog.d("PushManagerHelper", "push bindUser");
            if (GlobalConfig.getInstance().getInitConfig().getRegionType() == 1 || RegionManager.isChinaRegion()) {
                PushManager.getInstance().bindUserSafely(PushChannelType.IOT_MAINLAND_CLOUD_PUSH);
            } else {
                PushManager.getInstance().bindUserSafely(PushChannelType.IOT_OVERSEAS_CLOUD_PUSH);
            }
        }
    }

    public boolean bindUserSafely() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!SDKManager.isPushAvailable()) {
            return false;
        }
        if (LoginBusiness.isLogin()) {
            ALog.d("PushManagerHelper", "push bindUser");
            return (GlobalConfig.getInstance().getInitConfig().getRegionType() == 1 || RegionManager.isChinaRegion()) ? PushManager.getInstance().bindUserSafely(PushChannelType.IOT_MAINLAND_CLOUD_PUSH) : PushManager.getInstance().bindUserSafely(PushChannelType.IOT_OVERSEAS_CLOUD_PUSH);
        }
        ALog.e("PushManagerHelper", "push bindUser abort because no login");
        return false;
    }

    public void init(Application application) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (SDKManager.isPushAvailable()) {
            try {
                ALog.d("PushManagerHelper", "push init start");
                initPush(application, RegionManager.getStoredRegionName());
                if (this.f11860d.compareAndSet(false, true)) {
                    ALog.d("PushManagerHelper", "registerReceiver local broadcast.");
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(LoginBusiness.LOGIN_CHANGE_ACTION);
                    LocalBroadcastManager.getInstance(application).registerReceiver(new BroadcastReceiver() { // from class: com.aliyun.iot.aep.sdk.init.PushManagerHelper.1
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                            if (LoginBusiness.isLogin()) {
                                ALog.e("PushManagerHelper", "init lb.registerReceiver onReceive  Push bindUser");
                                if (PushManagerHelper.this.bindUserSafely()) {
                                    ALog.d("PushManagerHelper", "bindUserSafely call success");
                                } else {
                                    ALog.d("PushManagerHelper", "bindUserSafely call fail");
                                }
                            }
                        }
                    }, intentFilter);
                }
            } catch (Exception e2) {
                ALog.e("PushManagerHelper", e2.getMessage());
            }
        }
    }

    public void initPush(Application application, String str) {
        if (SDKManager.isPushAvailable()) {
            ALog.d("PushManagerHelper", "initPush() called with: app = [" + application + "], regionName = [" + str + "]");
            this.f11861e = application;
            if (this.f11862f != null) {
                Message messageObtain = Message.obtain();
                messageObtain.what = 131478;
                messageObtain.arg1 = 1;
                messageObtain.obj = str;
                this.f11862f.removeMessages(131478);
                this.f11862f.sendMessage(messageObtain);
            }
        }
    }

    public void unbindUser(final OALoginAdapter.ActionOnBeforeLogoutResultCallback actionOnBeforeLogoutResultCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!SDKManager.isPushAvailable()) {
            if (actionOnBeforeLogoutResultCallback != null) {
                actionOnBeforeLogoutResultCallback.onResult(0);
            }
        } else if (LoginBusiness.isLogin()) {
            ALog.d("PushManagerHelper", "push unbindUser");
            PushManager.getInstance().unbindUser(new PushManager.BindUserListener() { // from class: com.aliyun.iot.aep.sdk.init.PushManagerHelper.2
                public void onFailure(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.i("PushManagerHelper", "PushManager unbind failed " + str2);
                    OALoginAdapter.ActionOnBeforeLogoutResultCallback actionOnBeforeLogoutResultCallback2 = actionOnBeforeLogoutResultCallback;
                    if (actionOnBeforeLogoutResultCallback2 != null) {
                        actionOnBeforeLogoutResultCallback2.onResult(-1);
                    }
                }

                public void onSuccess(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.i("PushManagerHelper", "PushManager unbind succ!");
                    OALoginAdapter.ActionOnBeforeLogoutResultCallback actionOnBeforeLogoutResultCallback2 = actionOnBeforeLogoutResultCallback;
                    if (actionOnBeforeLogoutResultCallback2 != null) {
                        actionOnBeforeLogoutResultCallback2.onResult(0);
                    }
                }
            });
        } else {
            ALog.e("PushManagerHelper", "push unbindUser abort because no login");
            if (actionOnBeforeLogoutResultCallback != null) {
                actionOnBeforeLogoutResultCallback.onResult(0);
            }
        }
    }

    private PushManagerHelper() {
        this.f11858b = false;
        this.f11859c = null;
        this.f11860d = new AtomicBoolean(false);
        this.f11861e = null;
        this.f11862f = new a();
        this.f11863h = new AtomicInteger(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Application application, String str, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final String str2;
        final PushChannelType pushChannelType;
        String storedPushAddress;
        try {
            ALog.d("PushManagerHelper", "regionName:" + str);
            if (!TextUtils.isEmpty(str)) {
                str2 = str;
            } else {
                if (!RegionManager.isChinaRegion()) {
                    ALog.e("PushManagerHelper", "no region, abort!");
                    return;
                }
                str2 = "china";
            }
            ALog.d("PushManagerHelper", "final regionEnglishName:" + str2 + ", mLastRegion=" + this.f11857a);
            if (str2.equalsIgnoreCase(this.f11857a)) {
                ALog.e("PushManagerHelper", "same region, do not re-init");
                return;
            }
            if (this.f11858b && z2) {
                ALog.e("PushManagerHelper", "push init is ongoing, ignore retry");
                return;
            }
            if (this.f11858b && !TextUtils.isEmpty(this.f11859c) && str2.equals(this.f11859c)) {
                ALog.e("PushManagerHelper", "push init is ongoing, ignore same region.");
                return;
            }
            boolean z3 = true;
            if (this.f11858b) {
                ALog.e("PushManagerHelper", "push init is ongoing, not retry, delay 1s to init.");
                if (this.f11862f != null) {
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 131478;
                    messageObtain.arg1 = 1;
                    messageObtain.obj = str;
                    this.f11862f.removeMessages(131478);
                    this.f11862f.sendMessageDelayed(messageObtain, 1000L);
                    return;
                }
                return;
            }
            a(true);
            if (!z2) {
                ALog.d("PushManagerHelper", "reset retry push count and delay time.");
                this.f11863h.set(0);
                f11856g = 1000;
            }
            this.f11859c = str2;
            String authCode = GlobalConfig.getInstance().getAuthCode();
            PushChannelType pushChannelType2 = PushChannelType.IOT_MAINLAND_CLOUD_PUSH;
            if (GlobalConfig.getInstance().getInitConfig().getRegionType() != 1 && !RegionManager.isChinaRegion()) {
                ALog.d("PushManagerHelper", "init overseas cloud push.");
                pushChannelType = PushChannelType.IOT_OVERSEAS_CLOUD_PUSH;
                storedPushAddress = RegionManager.getStoredPushAddress();
                if (TextUtils.isEmpty(storedPushAddress)) {
                    ALog.e("PushManagerHelper", "no domain for oversea push, warning!");
                    a(false);
                    return;
                }
            } else {
                ALog.d("PushManagerHelper", "default: init mainland cloud push.");
                pushChannelType = PushChannelType.IOT_MAINLAND_CLOUD_PUSH;
                storedPushAddress = null;
            }
            ALog.d("PushManagerHelper", "doman:" + storedPushAddress);
            if (pushChannelType == PushChannelType.IOT_OVERSEAS_CLOUD_PUSH && ("Germany".equals(str2) || "UnitedStates".equals(str2))) {
                ALog.i("PushManagerHelper", "use 80 port in germany and american. region=" + str2 + ", pre-domain=" + storedPushAddress + ", post-domain=" + storedPushAddress + ":80");
            } else {
                z3 = false;
            }
            final PushInitConfig pushInitConfigBuild = new PushInitConfig.Builder().authCode(authCode).domain(storedPushAddress).domainPort(z3 ? "80" : null).pushChannelType(pushChannelType).pushInitCallback(new PushInitCallback() { // from class: com.aliyun.iot.aep.sdk.init.PushManagerHelper.3
                public void onFailed(String str3, String str4) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e("PushManagerHelper", "onFailed() called with: errorCode = [" + str3 + "], errorMsg = [" + str4 + "]");
                    PushManagerHelper.this.a(false);
                    if (PushManagerHelper.this.f11862f != null && !PushManagerHelper.this.f11862f.hasMessages(131478) && !PushManagerHelper.this.f11858b) {
                        PushManagerHelper.this.f11863h.incrementAndGet();
                        ALog.d("PushManagerHelper", "retry init push. " + PushManagerHelper.this.f11863h.get());
                        if (PushManagerHelper.this.f11863h.get() >= 10) {
                            ALog.e("PushManagerHelper", "push init failed, stop to retry after 10 times.");
                            return;
                        }
                        Message messageObtain2 = Message.obtain();
                        messageObtain2.what = 131478;
                        messageObtain2.arg1 = 0;
                        messageObtain2.obj = str2;
                        int unused = PushManagerHelper.f11856g = PushManagerHelper.f11856g * 2 < 128000 ? PushManagerHelper.f11856g * 2 : 128000;
                        ALog.d("PushManagerHelper", "retry init push after " + PushManagerHelper.f11856g + " ms.");
                        PushManagerHelper.this.f11862f.sendMessageDelayed(messageObtain2, (long) PushManagerHelper.f11856g);
                    }
                    ALog.e("PushManagerHelper", "push init fail");
                }

                public void onSuccess(String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d("PushManagerHelper", "onSuccess() called with: response = [" + str3 + "]");
                    PushManagerHelper.this.f11857a = str2;
                    PushManagerHelper.this.a(false);
                    ALog.d("PushManagerHelper", "push init done");
                    Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.aliyun.iot.aep.sdk.init.PushManagerHelper.3.1
                        @Override // java.lang.Runnable
                        public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                            AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                            PushManagerHelper.this.a(application, pushChannelType);
                            if (LoginBusiness.isLogin()) {
                                ALog.e("PushManagerHelper", "pushInitCallback onSuccess  Push bindUser");
                                PushManagerHelper.this.bindUser();
                            }
                        }
                    });
                }
            }).build();
            ALog.d("PushManagerHelper", "pushInitConfig:" + pushInitConfigBuild.getAuthCode());
            String appKey = APIGatewayHttpAdapterImpl.getAppKey(application.getApplicationContext(), authCode);
            if (pushChannelType == PushChannelType.IOT_MAINLAND_CLOUD_PUSH) {
                PushManager.getInstance().clearAccsDiskCache(application, appKey);
            }
            a aVar = this.f11862f;
            if (aVar != null) {
                aVar.removeMessages(131479);
            }
            PushManager.getInstance().deinit(application, new CallBack() { // from class: com.aliyun.iot.aep.sdk.init.PushManagerHelper.4
                @Override // org.android.agoo.common.CallBack
                public void onFailure(String str3, String str4) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d("PushManagerHelper", "deinit --> nFailure() called with: errorCode = [" + str3 + "], errorMsg = [" + str4 + "]");
                    if (PushManagerHelper.this.f11862f != null) {
                        Message messageObtain2 = Message.obtain();
                        messageObtain2.what = 131479;
                        messageObtain2.obj = pushInitConfigBuild;
                        PushManagerHelper.this.f11862f.removeMessages(131479);
                        PushManagerHelper.this.f11862f.sendMessageDelayed(messageObtain2, 1000L);
                    }
                }

                @Override // org.android.agoo.common.CallBack
                public void onSuccess() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d("PushManagerHelper", "deinit --> onSuccess() called");
                    if (PushManagerHelper.this.f11862f != null) {
                        Message messageObtain2 = Message.obtain();
                        messageObtain2.what = 131479;
                        messageObtain2.obj = pushInitConfigBuild;
                        PushManagerHelper.this.f11862f.removeMessages(131479);
                        PushManagerHelper.this.f11862f.sendMessageDelayed(messageObtain2, 1000L);
                    }
                }
            });
        } catch (Exception e2) {
            ALog.e("PushManagerHelper", "push init fail : " + e2);
            Log.e("PushManagerHelper", "", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Application application, PushChannelType pushChannelType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("PushManagerHelper", "initThirdPush() called with: application = [" + application + "], channelType = [" + pushChannelType + "]");
        IoTSmart.PushConfig pushConfig = GlobalConfig.getInstance().getInitConfig().getPushConfig();
        try {
            if (pushChannelType == PushChannelType.IOT_OVERSEAS_CLOUD_PUSH && !TextUtils.isEmpty(pushConfig.fcmSendId) && !TextUtils.isEmpty(pushConfig.fcmApplicationId)) {
                PushManager.getInstance().initFCMPush(application, pushConfig.fcmSendId, pushConfig.fcmApplicationId, pushConfig.fcmProjectId, pushConfig.fcmApiKey);
            }
        } catch (Exception unused) {
            ALog.w("PushManagerHelper", "FCM push will not be supported.");
        }
        try {
            PushManager.getInstance().initHuaweiPush(application);
        } catch (Exception unused2) {
            ALog.w("PushManagerHelper", "Huawei push will not be supported.");
        }
        try {
            if (!TextUtils.isEmpty(pushConfig.xiaomiAppId) && !TextUtils.isEmpty(pushConfig.xiaomiAppkey)) {
                PushManager.getInstance().initMiPush(application, pushConfig.xiaomiAppId, pushConfig.xiaomiAppkey);
            }
        } catch (Exception unused3) {
            ALog.w("PushManagerHelper", "Xiaomi push will not be supported.");
        }
        try {
            VivoRegister.register(application);
        } catch (Exception unused4) {
            ALog.w("PushManagerHelper", "vivo push will not be supported.");
        }
        try {
            if (TextUtils.isEmpty(pushConfig.oppoAppKey) || TextUtils.isEmpty(pushConfig.oppoAppSecret)) {
                return;
            }
            OppoRegister.register(application, pushConfig.oppoAppKey, pushConfig.oppoAppSecret);
        } catch (Exception unused5) {
            ALog.w("PushManagerHelper", "vivo push will not be supported.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("PushManagerHelper", "setIsInitializing() called with: mIsInitializing = [" + z2 + "]");
        this.f11858b = z2;
    }
}
