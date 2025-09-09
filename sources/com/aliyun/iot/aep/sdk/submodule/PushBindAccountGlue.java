package com.aliyun.iot.aep.sdk.submodule;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.listener.IoTTokenCreatedListener;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.config.GlobalConfig;
import com.aliyun.iot.aep.sdk.framework.region.RegionManager;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKManager;
import com.aliyun.iot.aep.sdk.framework.sdk.SimpleSDKDelegateImp;
import com.aliyun.iot.aep.sdk.framework.utils.ThreadUtils;
import com.aliyun.iot.aep.sdk.init.PushManagerHelper;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter;
import com.aliyun.iot.push.PushChannelType;
import com.aliyun.iot.push.PushManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class PushBindAccountGlue extends SimpleSDKDelegateImp {

    /* renamed from: a, reason: collision with root package name */
    private final AtomicBoolean f12026a = new AtomicBoolean(false);

    /* renamed from: b, reason: collision with root package name */
    private int f12027b = 1000;

    /* renamed from: c, reason: collision with root package name */
    private int f12028c = 0;

    /* renamed from: d, reason: collision with root package name */
    private Runnable f12029d = new Runnable() { // from class: com.aliyun.iot.aep.sdk.submodule.PushBindAccountGlue.3
        @Override // java.lang.Runnable
        public void run() {
            if (PushBindAccountGlue.this.f12026a.get()) {
                PushBindAccountGlue.this.f12026a.set(false);
            } else {
                PushBindAccountGlue.this.b(AApplication.getInstance());
            }
        }
    };

    private void c(Application application) {
        ALog.d("PushBindAccountGlue", "retryBindAccount :" + this.f12028c);
        int iMin = Math.min(this.f12027b * 2, 64000);
        this.f12027b = iMin;
        int i2 = this.f12028c;
        this.f12028c = i2 + 1;
        if (i2 < 10) {
            ThreadUtils.retryInMain(this.f12029d, iMin);
        }
    }

    private void d(Application application) {
        ALog.d("PushBindAccountGlue", "bindAccountInternal");
        IoTCredentialManageImpl ioTCredentialManageImpl = IoTCredentialManageImpl.getInstance(application);
        if (ioTCredentialManageImpl == null || TextUtils.isEmpty(ioTCredentialManageImpl.getIoTToken())) {
            return;
        }
        if (GlobalConfig.getInstance().getInitConfig().getRegionType() == 1 || RegionManager.isChinaRegion()) {
            a(PushChannelType.IOT_MAINLAND_CLOUD_PUSH);
        } else {
            a(PushChannelType.IOT_OVERSEAS_CLOUD_PUSH);
        }
    }

    @Override // com.aliyun.iot.aep.sdk.framework.sdk.ISDKDelegate
    public int init(final Application application, SDKConfigure sDKConfigure, Map<String, String> map) {
        if (!SDKManager.isPushAvailable()) {
            return 0;
        }
        try {
            IoTCredentialManageImpl.getInstance(application).registerIotTokenCreatedListener(new IoTTokenCreatedListener() { // from class: com.aliyun.iot.aep.sdk.submodule.PushBindAccountGlue.1
                @Override // com.aliyun.iot.aep.sdk.credential.listener.IoTTokenCreatedListener
                public void onIoTTokenCreated() {
                    ALog.d("PushBindAccountGlue", "onIoTTokenCreated");
                    PushBindAccountGlue.this.b(application);
                }
            });
        } catch (Exception unused) {
            ALog.d("PushBindAccountGlue", "registerIotTokenCreatedListener error");
        }
        if (a(application)) {
            a();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Application application) {
        IoTCredentialManageImpl ioTCredentialManageImpl;
        ALog.d("PushBindAccountGlue", "bindAccount");
        if (LoginBusiness.isLogin() && (ioTCredentialManageImpl = IoTCredentialManageImpl.getInstance(application)) != null) {
            if (!TextUtils.isEmpty(ioTCredentialManageImpl.getIoTToken())) {
                d(application);
            }
            c(application);
        }
    }

    private static boolean a(Application application) {
        boolean zEquals = application.getPackageName().equals(a(application, Process.myPid()));
        ALog.d("PushBindAccountGlue", "isMainProcess:" + zEquals);
        return zEquals;
    }

    private void c() {
        this.f12027b = 1000;
        this.f12028c = 0;
    }

    private static String a(Context context, int i2) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == i2) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        ThreadUtils.remove(this.f12029d);
        c();
    }

    private void a() {
        try {
            ALog.d("PushBindAccountGlue", "registerBeforeLogout");
            ((OALoginAdapter) LoginBusiness.getLoginAdapter()).registerCompleteBeforeLogoutListener(new OALoginAdapter.OnCompleteBeforeLogoutListener() { // from class: com.aliyun.iot.aep.sdk.submodule.PushBindAccountGlue.2
                @Override // com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter.OnCompleteBeforeLogoutListener
                public void doAction(OALoginAdapter.ActionOnBeforeLogoutResultCallback actionOnBeforeLogoutResultCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d("PushBindAccountGlue", "registerBeforeLogout doAction");
                    PushManagerHelper.getInstance().unbindUser(actionOnBeforeLogoutResultCallback);
                    PushBindAccountGlue.this.f12026a.set(false);
                }
            });
        } catch (Exception e2) {
            ALog.e("PushBindAccountGlue", "registerBeforeLogout error");
            e2.printStackTrace();
        }
    }

    private void a(PushChannelType pushChannelType) {
        try {
            PushManager.getInstance().bindUserSafely(pushChannelType, new PushManager.BindUserListener() { // from class: com.aliyun.iot.aep.sdk.submodule.PushBindAccountGlue.4
                public void onFailure(String str, String str2) {
                }

                public void onSuccess(String str) {
                    PushBindAccountGlue.this.f12026a.set(true);
                    PushBindAccountGlue.this.b();
                }
            });
        } catch (Exception e2) {
            ALog.d("PushBindAccountGlue", "bindUserSafely:" + e2.toString());
        }
    }
}
