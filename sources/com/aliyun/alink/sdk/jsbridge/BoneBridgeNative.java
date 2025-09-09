package com.aliyun.alink.sdk.jsbridge;

import android.content.Context;
import android.content.Intent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class BoneBridgeNative {
    public static final String ERROR_CODE_GATEWAY = "502";
    public static final String ERROR_LOCALIZED_MESSAGE_GATEWAY = "BoneBridge 内部执行错误";
    public static final String ERROR_MESSAGE_GATEWAY = "bone bridge invoke error";
    public static final String ERROR_SUB_CODE_EXCEPTION = "502";
    public static final String ERROR_SUB_CODE_GATEWAY_NOT_MATCH_ARGUMENT_NUMBER = "405";
    public static final String ERROR_SUB_CODE_GATEWAY_NO_HANDLER = "404";
    public static final String ERROR_SUB_LOCALIZED_MESSAGE_GATEWAY_EXCEPTION = "执行时异常";
    public static final String ERROR_SUB_LOCALIZED_MESSAGE_GATEWAY_NO_HANDLER = "未能找到处理类";
    public static final String ERROR_SUB_LOCALIZED_MESSAGE_GATEWAY_NO_MATCH_ARGUMENT_NUMBER = "参数类型或者参数数量与接口不符";
    public static final String ERROR_SUB_LOCALIZED_MESSAGE_GATEWAY_NO_METHOD = "未能找到处理方法";
    public static final String ERROR_SUB_MESSAGE_GATEWAY_EXCEPTION = "runtime exception";
    public static final String ERROR_SUB_MESSAGE_GATEWAY_NO_HANDLER = "no handler";
    public static final String ERROR_SUB_MESSAGE_GATEWAY_NO_MATCH_ARGUMENT_NUMBER = "argument not match input of method";
    public static final String ERROR_SUB_MESSAGE_GATEWAY_NO_METHOD = "no method";

    /* renamed from: c, reason: collision with root package name */
    private Context f11502c;

    /* renamed from: d, reason: collision with root package name */
    private IJSBridge f11503d;

    /* renamed from: a, reason: collision with root package name */
    private boolean f11500a = false;

    /* renamed from: b, reason: collision with root package name */
    private boolean f11501b = false;

    /* renamed from: e, reason: collision with root package name */
    private Map<String, IBonePlugin> f11504e = new HashMap();

    /* renamed from: f, reason: collision with root package name */
    private a f11505f = new a();

    class a {
        private a() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IBonePlugin iBonePlugin) {
            try {
                iBonePlugin.onPause();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(IBonePlugin iBonePlugin) {
            try {
                iBonePlugin.onDestroy();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(IBonePlugin iBonePlugin) {
            try {
                iBonePlugin.destroy();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IBonePlugin iBonePlugin, Context context, IJSBridge iJSBridge) {
            try {
                iBonePlugin.onInitialize(context, iJSBridge);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IBonePlugin iBonePlugin, int i2, int i3, Intent intent) {
            try {
                iBonePlugin.onActivityResult(i2, i3, intent);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IBonePlugin iBonePlugin) {
            try {
                iBonePlugin.onResume();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public BoneBridgeNative(Context context, IJSBridge iJSBridge) {
        this.f11502c = context;
        this.f11503d = iJSBridge;
        for (Map.Entry<String, Class<? extends IBonePlugin>> entry : BonePluginRegistry.f11507a.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue().isAnnotationPresent(Preload.class)) {
                IBonePlugin iBonePluginFindAPlugin = BonePluginRegistry.findAPlugin(key);
                this.f11505f.a(iBonePluginFindAPlugin, context, iJSBridge);
                this.f11504e.put(key, iBonePluginFindAPlugin);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void call(java.lang.String r18, java.lang.String r19, java.lang.Object[] r20, com.aliyun.alink.sdk.jsbridge.BoneCallback r21) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.sdk.jsbridge.BoneBridgeNative.call(java.lang.String, java.lang.String, java.lang.Object[], com.aliyun.alink.sdk.jsbridge.BoneCallback):void");
    }

    public void destroy() {
        if (this.f11500a) {
            dispatchOnPause();
        }
        if (!this.f11501b) {
            dispatchOnDestroy();
        }
        Iterator<Map.Entry<String, IBonePlugin>> it = this.f11504e.entrySet().iterator();
        while (it.hasNext()) {
            IBonePlugin value = it.next().getValue();
            if (value != null) {
                this.f11505f.d(value);
            }
        }
        this.f11504e.clear();
        this.f11502c = null;
        this.f11503d = null;
    }

    public void dispatchOnActivityResult(int i2, int i3, Intent intent) {
        Iterator<Map.Entry<String, IBonePlugin>> it = this.f11504e.entrySet().iterator();
        while (it.hasNext()) {
            IBonePlugin value = it.next().getValue();
            if (value != null) {
                this.f11505f.a(value, i2, i3, intent);
            }
        }
    }

    public void dispatchOnDestroy() {
        this.f11501b = true;
        Iterator<Map.Entry<String, IBonePlugin>> it = this.f11504e.entrySet().iterator();
        while (it.hasNext()) {
            IBonePlugin value = it.next().getValue();
            if (value != null) {
                this.f11505f.c(value);
            }
        }
    }

    public void dispatchOnPause() {
        this.f11500a = false;
        Iterator<Map.Entry<String, IBonePlugin>> it = this.f11504e.entrySet().iterator();
        while (it.hasNext()) {
            IBonePlugin value = it.next().getValue();
            if (value != null) {
                this.f11505f.b(value);
            }
        }
    }

    public void dispatchOnResume() {
        this.f11500a = true;
        this.f11501b = false;
        Iterator<Map.Entry<String, IBonePlugin>> it = this.f11504e.entrySet().iterator();
        while (it.hasNext()) {
            IBonePlugin value = it.next().getValue();
            if (value != null) {
                this.f11505f.a(value);
            }
        }
    }
}
