package com.alibaba.sdk.android.openaccount.task;

import android.content.Context;
import android.os.AsyncTask;
import com.alibaba.sdk.android.oauth.OauthModule;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.OpenAccountService;
import com.alibaba.sdk.android.openaccount.OpenAccountSessionService;
import com.alibaba.sdk.android.openaccount.callback.InitResultCallback;
import com.alibaba.sdk.android.openaccount.config.ConfigService;
import com.alibaba.sdk.android.openaccount.config.DynamicConfigInitHandler;
import com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener;
import com.alibaba.sdk.android.openaccount.device.DeviceManager;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.executor.impl.ExecutorServiceImpl;
import com.alibaba.sdk.android.openaccount.impl.OpenAccountServiceImpl;
import com.alibaba.sdk.android.openaccount.initialization.InitializationHandler;
import com.alibaba.sdk.android.openaccount.initialization.InitializationServiceClient;
import com.alibaba.sdk.android.openaccount.initialization.impl.DefaultInitializationServiceClientImpl;
import com.alibaba.sdk.android.openaccount.model.ResultCode;
import com.alibaba.sdk.android.openaccount.rpc.RpcService;
import com.alibaba.sdk.android.openaccount.rpc.cloudapi.ApiGatewayRpcServiceImpl;
import com.alibaba.sdk.android.openaccount.rpc.mtop.MtopMtopRpcServiceImpl;
import com.alibaba.sdk.android.openaccount.security.SecurityGuardService;
import com.alibaba.sdk.android.openaccount.security.impl.SecurityGuardWrapper;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.session.impl.DefaultSessionManagerServiceImpl;
import com.alibaba.sdk.android.openaccount.session.impl.SessionServiceImpl;
import com.alibaba.sdk.android.openaccount.trace.ActionTraceLogger;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.trace.TraceLoggerManager;
import com.alibaba.sdk.android.openaccount.ut.UTConstants;
import com.alibaba.sdk.android.openaccount.ut.UserTrackerService;
import com.alibaba.sdk.android.openaccount.ut.impl.AlibabaUserTrackerService;
import com.alibaba.sdk.android.openaccount.util.ReflectionUtils;
import com.alibaba.sdk.android.openaccount.util.TraceHelper;
import com.alibaba.sdk.android.pluto.Pluto;
import com.alibaba.sdk.android.pluto.meta.ModuleInfo;
import com.alibaba.sdk.android.pluto.meta.ModuleInfoBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class InitTask implements Runnable {
    private static final ReentrantLock initLock = new ReentrantLock();
    public static volatile Boolean isInitOk;
    private InitResultCallback initResultCallback;
    private volatile boolean initialized = false;
    private CountDownLatch initializationLock = new CountDownLatch(1);

    public InitTask(Context context, final InitResultCallback initResultCallback) throws ClassNotFoundException {
        final ActionTraceLogger actionTraceLoggerBegin = TraceLoggerManager.INSTANCE.action("init_sdk", "initTask").begin();
        this.initResultCallback = new InitResultCallback() { // from class: com.alibaba.sdk.android.openaccount.task.InitTask.1
            @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
            public void onFailure(int i2, String str) {
                InitTask.this.sendInitHint(false, actionTraceLoggerBegin.getCaseTime(), str);
                actionTraceLoggerBegin.failed();
                InitResultCallback initResultCallback2 = initResultCallback;
                if (initResultCallback2 != null) {
                    initResultCallback2.onFailure(i2, str);
                }
                InitTask.this.postInitResultCallbackEvents(i2, str);
            }

            @Override // com.alibaba.sdk.android.openaccount.callback.InitResultCallback
            public void onSuccess() {
                InitTask.this.sendInitHint(true, actionTraceLoggerBegin.getCaseTime(), null);
                actionTraceLoggerBegin.success();
                InitResultCallback initResultCallback2 = initResultCallback;
                if (initResultCallback2 != null) {
                    initResultCallback2.onSuccess();
                }
                InitTask.this.postInitResultCallbackEvents(100, null);
            }
        };
        init();
    }

    private boolean asyncRun() {
        ModuleInfoBuilder moduleInfoBuilder = new ModuleInfoBuilder("system");
        moduleInfoBuilder.addBeanInfo(ConfigService.class, ConfigManager.getInstance(), "init", (Map<String, String>) null);
        moduleInfoBuilder.addBeanInfo(new Class[]{ExecutorService.class, java.util.concurrent.ExecutorService.class}, new ExecutorServiceImpl());
        moduleInfoBuilder.addBeanInfo(new Class[]{DeviceManager.class, InitializationHandler.class, EnvironmentChangeListener.class}, DeviceManager.INSTANCE, "init", (Map<String, String>) null);
        moduleInfoBuilder.addBeanInfo(new Class[]{UserTrackerService.class, EnvironmentChangeListener.class}, AlibabaUserTrackerService.class, "init", (Map<String, String>) null);
        moduleInfoBuilder.addBeanInfo(new Class[]{SessionManagerService.class, EnvironmentChangeListener.class}, DefaultSessionManagerServiceImpl.INSTANCE, "init", (Map<String, String>) null);
        moduleInfoBuilder.addBeanInfo(new Class[]{InitializationHandler.class, OpenAccountSessionService.class}, SessionServiceImpl.class, (String) null, (Map<String, String>) null);
        moduleInfoBuilder.addBeanInfo(new Class[]{SecurityGuardService.class, EnvironmentChangeListener.class}, SecurityGuardWrapper.INSTANCE, "init", (Map<String, String>) null);
        moduleInfoBuilder.addBeanInfo(InitializationServiceClient.class, DefaultInitializationServiceClientImpl.class);
        moduleInfoBuilder.addBeanInfo(InitializationHandler.class, DynamicConfigInitHandler.class, "init", (Map<String, String>) null);
        Pluto pluto = Pluto.DEFAULT_INSTANCE;
        pluto.registerModule(moduleInfoBuilder.build());
        regiterRpc();
        ModuleInfoBuilder moduleInfoBuilder2 = new ModuleInfoBuilder("openaccount");
        moduleInfoBuilder2.addBeanInfo(OpenAccountService.class, OpenAccountServiceImpl.class);
        pluto.registerModule(moduleInfoBuilder2.build());
        pluto.registerModule(OauthModule.getModuleInfo());
        ModuleInfo moduleInfo = (ModuleInfo) ReflectionUtils.invoke("com.alibaba.sdk.android.openaccount.ui.module.OpenAccountTaobaoUIModule", "getModuleInfo", (String[]) null, (Object) null, (Object[]) null);
        if (moduleInfo != null) {
            pluto.registerModule(moduleInfo);
        }
        ModuleInfo moduleInfo2 = (ModuleInfo) ReflectionUtils.invoke("com.alibaba.sdk.android.openaccount.ui.module.OpenAccountUIModule", "getModuleInfo", (String[]) null, (Object) null, (Object[]) null);
        if (moduleInfo2 != null) {
            pluto.registerModule(moduleInfo2);
        }
        ModuleInfo moduleInfo3 = (ModuleInfo) ReflectionUtils.invoke("com.alibaba.sdk.android.openaccount.ui.module.OpenAccountFaceModule", "getModuleInfo", (String[]) null, (Object) null, (Object[]) null);
        if (moduleInfo3 != null) {
            pluto.registerModule(moduleInfo3);
        }
        final List<ResultCode> listInit = pluto.init(OpenAccountSDK.getAndroidContext());
        if (listInit.size() == 0) {
            TraceHelper.init(OpenAccountSDK.getAndroidContext(), OpenAccountSDK.getProperty(OpenAccountConstants.APP_KEY), null, OpenAccountSDK.getVersion().toString());
            ExecutorServiceImpl.INSTANCE.postTask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.InitTask.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ((InitializationServiceClient) Pluto.DEFAULT_INSTANCE.getBean(InitializationServiceClient.class)).request();
                    } catch (Exception e2) {
                        AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to do the sdk init", e2);
                    }
                }
            });
            ((ExecutorService) pluto.getBean(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.InitTask.5
                @Override // java.lang.Runnable
                public void run() {
                    if (InitTask.this.initResultCallback != null) {
                        InitTask.this.initResultCallback.onSuccess();
                    }
                }
            });
            isInitOk = Boolean.TRUE;
            return true;
        }
        ExecutorServiceImpl.INSTANCE.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.InitTask.3
            @Override // java.lang.Runnable
            public void run() {
                if (InitTask.this.initResultCallback != null) {
                    ResultCode resultCode = (ResultCode) listInit.get(0);
                    InitTask.this.initResultCallback.onFailure(resultCode.code, resultCode.message);
                }
            }
        });
        for (ResultCode resultCode : listInit) {
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "init failed code = " + resultCode.code + " message = " + resultCode.message);
        }
        return false;
    }

    public static Boolean checkInitStatus() {
        try {
            ReentrantLock reentrantLock = initLock;
            reentrantLock.lock();
            Boolean bool = isInitOk;
            reentrantLock.unlock();
            return bool;
        } catch (Throwable th) {
            initLock.unlock();
            throw th;
        }
    }

    private void init() throws ClassNotFoundException {
        try {
            Class.forName(AsyncTask.class.getName());
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postInitResultCallbackEvents(final int i2, final String str) {
        ExecutorServiceImpl.INSTANCE.postTask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.InitTask.2
            @Override // java.lang.Runnable
            public void run() {
                InitResultCallback[] initResultCallbackArr = (InitResultCallback[]) Pluto.DEFAULT_INSTANCE.getBeans(InitResultCallback.class);
                if (initResultCallbackArr == null || initResultCallbackArr.length == 0) {
                    return;
                }
                for (InitResultCallback initResultCallback : initResultCallbackArr) {
                    try {
                        int i3 = i2;
                        if (i3 == 100) {
                            initResultCallback.onSuccess();
                        } else {
                            initResultCallback.onFailure(i3, str);
                        }
                    } catch (Exception e2) {
                        AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to invoke the system initresultcallback", e2);
                    }
                }
            }
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 4 */
    private void regiterRpc() {
        Class<?> cls;
        boolean z2;
        ModuleInfoBuilder moduleInfoBuilder = new ModuleInfoBuilder("rpc");
        if (!ConfigManager.getInstance().isAPIGateway()) {
            try {
                cls = Class.forName("com.alibaba.sdk.android.openaccount.ext.rpc.OpenMtopServiceImplMtop");
                z2 = true;
            } catch (Throwable unused) {
                cls = MtopMtopRpcServiceImpl.class;
            }
            ConfigManager.getInstance().setOpenMtop(z2);
            moduleInfoBuilder.addBeanInfo(new Class[]{RpcService.class, EnvironmentChangeListener.class, InitResultCallback.class}, cls, "init", (Map<String, String>) null);
            Pluto.DEFAULT_INSTANCE.registerModule(moduleInfoBuilder.build());
        }
        cls = ApiGatewayRpcServiceImpl.class;
        z2 = false;
        ConfigManager.getInstance().setOpenMtop(z2);
        moduleInfoBuilder.addBeanInfo(new Class[]{RpcService.class, EnvironmentChangeListener.class, InitResultCallback.class}, cls, "init", (Map<String, String>) null);
        Pluto.DEFAULT_INSTANCE.registerModule(moduleInfoBuilder.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendInitHint(boolean z2, long j2, String str) {
        String str2 = z2 ? UTConstants.TYPE_INIT_SUCCESS : UTConstants.TYPE_INIT_FAILED;
        HashMap map = new HashMap();
        if (str != null) {
            map.put("msg", str);
        }
        UserTrackerService userTrackerService = (UserTrackerService) Pluto.DEFAULT_INSTANCE.getBean(UserTrackerService.class, null);
        if (userTrackerService != null) {
            userTrackerService.sendCustomHit(UTConstants.E_SDK_INIT_RESULT, j2, str2, map);
            HashMap map2 = new HashMap();
            map2.put("model", "openaccount");
            map2.put("version", ConfigManager.getInstance().getSDKVersion().toString());
            userTrackerService.sendCustomHit("7", "80001", 19999, "init", j2, str2, map2);
        }
    }

    public void await() {
        try {
            this.initializationLock.await();
        } catch (InterruptedException e2) {
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, e2.getMessage(), e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void doWhenException(java.lang.Throwable r3) {
        /*
            r2 = this;
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            com.alibaba.sdk.android.openaccount.task.InitTask.isInitOk = r0
            boolean r0 = r3 instanceof com.alibaba.sdk.android.openaccount.OpenAccountSDKException
            if (r0 == 0) goto L1a
            r0 = r3
            com.alibaba.sdk.android.openaccount.OpenAccountSDKException r0 = (com.alibaba.sdk.android.openaccount.OpenAccountSDKException) r0
            com.alibaba.sdk.android.openaccount.message.Message r1 = r0.getSDKMessage()
            if (r1 == 0) goto L1a
            com.alibaba.sdk.android.openaccount.message.Message r3 = r0.getSDKMessage()
            int r0 = r3.code
            java.lang.String r3 = r3.message
            goto L20
        L1a:
            java.lang.String r3 = com.alibaba.sdk.android.openaccount.util.CommonUtils.toString(r3)
            r0 = 10010(0x271a, float:1.4027E-41)
        L20:
            com.alibaba.sdk.android.openaccount.callback.InitResultCallback r1 = r2.initResultCallback
            com.alibaba.sdk.android.openaccount.util.CommonUtils.onFailure(r1, r0, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.openaccount.task.InitTask.doWhenException(java.lang.Throwable):void");
    }

    @Override // java.lang.Runnable
    public void run() {
        ReentrantLock reentrantLock;
        try {
            reentrantLock = initLock;
            reentrantLock.lock();
            asyncRun();
        } catch (Throwable th) {
            try {
                AliSDKLogger.e(OpenAccountConstants.LOG_TAG, th.getMessage(), th);
                doWhenException(th);
                reentrantLock = initLock;
            } catch (Throwable th2) {
                initLock.unlock();
                this.initializationLock.countDown();
                throw th2;
            }
        }
        reentrantLock.unlock();
        this.initializationLock.countDown();
    }
}
