package com.aliyun.iot.aep.sdk.bridge.invoker;

import android.app.Activity;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.bridge.core.context.ActivityLifeCircleManager;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.context.OnActivityResultManager;
import com.aliyun.iot.aep.sdk.bridge.core.context.OnNewIntentManager;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCall;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallMode;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class AsyncBoneInvoker implements BoneInvoker {

    /* renamed from: b, reason: collision with root package name */
    ExecutorService f11682b;

    /* renamed from: c, reason: collision with root package name */
    BoneInvoker f11683c;

    /* renamed from: a, reason: collision with root package name */
    BlockingQueue f11681a = new LinkedBlockingQueue();

    /* renamed from: d, reason: collision with root package name */
    private volatile boolean f11684d = false;

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        JSContext f11685a;

        /* renamed from: b, reason: collision with root package name */
        BoneCall f11686b;

        /* renamed from: c, reason: collision with root package name */
        BoneCallback f11687c;

        public a(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) {
            this.f11685a = jSContext;
            this.f11686b = boneCall;
            this.f11687c = boneCallback;
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d("BoneInvoker", "" + Thread.currentThread().getName());
            if (AsyncBoneInvoker.this.f11684d) {
                return;
            }
            BoneCall boneCall = this.f11686b;
            boneCall.mode = BoneCallMode.SYNC;
            AsyncBoneInvoker.this.f11683c.invoke(this.f11685a, boneCall, this.f11687c, null);
        }
    }

    static class b implements JSContext {

        /* renamed from: a, reason: collision with root package name */
        private WeakReference<JSContext> f11689a;

        /* renamed from: b, reason: collision with root package name */
        private String f11690b;

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.ActivityLifeCircleManager
        public void addActivityLifeCircleListener(ActivityLifeCircleManager.ActivityLifeCircleListener activityLifeCircleListener) {
            if (this.f11689a.get() != null) {
                this.f11689a.get().addActivityLifeCircleListener(activityLifeCircleListener);
            }
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.OnActivityResultManager
        public void addOnActivityResultListener(OnActivityResultManager.OnActivityResultListener onActivityResultListener) {
            if (this.f11689a.get() != null) {
                this.f11689a.get().addOnActivityResultListener(onActivityResultListener);
            }
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.OnNewIntentManager
        public void addOnNewIntentListener(OnNewIntentManager.OnNewIntentListener onNewIntentListener) {
            if (this.f11689a.get() != null) {
                this.f11689a.get().addOnNewIntentListener(onNewIntentListener);
            }
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.JSContext
        public void emitter(String str, JSONObject jSONObject) {
            if (this.f11689a.get() != null) {
                this.f11689a.get().emitter(str, jSONObject);
            }
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.JSContext
        public Activity getCurrentActivity() {
            if (this.f11689a.get() == null) {
                return null;
            }
            return this.f11689a.get().getCurrentActivity();
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.JSContext
        public String getCurrentUrl() {
            return this.f11690b;
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.JSContext
        public String getId() {
            if (this.f11689a.get() == null) {
                return null;
            }
            return this.f11689a.get().getId();
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.JSContext
        public void reload() {
            final Activity currentActivity = getCurrentActivity();
            if (currentActivity == null) {
                return;
            }
            currentActivity.runOnUiThread(new Runnable() { // from class: com.aliyun.iot.aep.sdk.bridge.invoker.AsyncBoneInvoker.b.1
                @Override // java.lang.Runnable
                public void run() {
                    if (b.this.f11689a.get() == null || currentActivity == null) {
                        return;
                    }
                    ((JSContext) b.this.f11689a.get()).reload();
                }
            });
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.ActivityLifeCircleManager
        public void removeActivityLifeCircleListener(ActivityLifeCircleManager.ActivityLifeCircleListener activityLifeCircleListener) {
            if (this.f11689a.get() != null) {
                this.f11689a.get().removeActivityLifeCircleListener(activityLifeCircleListener);
            }
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.OnActivityResultManager
        public void removeOnActivityResultListener(OnActivityResultManager.OnActivityResultListener onActivityResultListener) {
            if (this.f11689a.get() != null) {
                this.f11689a.get().removeOnActivityResultListener(onActivityResultListener);
            }
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.context.OnNewIntentManager
        public void removeOnNewIntentListener(OnNewIntentManager.OnNewIntentListener onNewIntentListener) {
            if (this.f11689a.get() != null) {
                this.f11689a.get().removeOnNewIntentListener(onNewIntentListener);
            }
        }

        private b(JSContext jSContext) {
            this.f11689a = new WeakReference<>(jSContext);
            this.f11690b = jSContext.getCurrentUrl();
        }
    }

    public AsyncBoneInvoker(BoneInvoker boneInvoker) {
        if (boneInvoker == null) {
            throw new IllegalArgumentException("invoker can not be null");
        }
        this.f11683c = boneInvoker;
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        this.f11682b = new ThreadPoolExecutor(iAvailableProcessors, (iAvailableProcessors * 2) + 1, 8L, TimeUnit.SECONDS, this.f11681a);
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.invoker.BoneInvoker
    public void invoke(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback, BoneCallback boneCallback2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f11684d) {
            return;
        }
        if (jSContext == null) {
            throw new IllegalArgumentException("jsContext can not be null");
        }
        if (jSContext.getCurrentActivity() == null) {
            ALog.d("AsyncBoneInvoker", "ignore call after destroy");
            return;
        }
        if (TextUtils.isEmpty(jSContext.getCurrentUrl())) {
            throw new IllegalArgumentException("jsContext.getCurrentUrl can not be empty");
        }
        if (boneCall == null) {
            throw new IllegalArgumentException("call can not be null");
        }
        if (TextUtils.isEmpty(boneCall.serviceId)) {
            throw new IllegalArgumentException("call.serviceId can not be empty");
        }
        if (TextUtils.isEmpty(boneCall.methodName)) {
            throw new IllegalArgumentException("call.methodName can not be empty");
        }
        if (BoneCallMode.ASYNC != boneCall.mode) {
            throw new IllegalArgumentException("only support async call");
        }
        if (boneCallback2 == null) {
            throw new IllegalArgumentException("asyncCallback can not be null");
        }
        ExecutorService executorService = this.f11682b;
        if (executorService == null || executorService.isShutdown()) {
            throw new IllegalStateException("can not invoke after onDestroy has been called");
        }
        this.f11682b.execute(new a(new b(jSContext), boneCall, boneCallback2));
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.invoker.BoneInvoker
    public void onDestroy() {
        this.f11681a.clear();
        ExecutorService executorService = this.f11682b;
        if (executorService != null && !executorService.isShutdown()) {
            this.f11682b.shutdown();
        }
        this.f11682b = null;
        this.f11684d = true;
    }
}
