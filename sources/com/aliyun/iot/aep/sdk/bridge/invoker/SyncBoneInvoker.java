package com.aliyun.iot.aep.sdk.bridge.invoker;

import android.app.Activity;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.bridge.R;
import com.aliyun.iot.aep.sdk.bridge.core.BoneServiceInstanceManager;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCall;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallMode;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneService;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class SyncBoneInvoker implements BoneInvoker {
    public static final String ERROR_CODE_GATEWAY = "502";
    public static final String ERROR_MESSAGE_GATEWAY = "Bad Bridge";
    public static final String ERROR_SUB_CODE_EXCEPTION = "500";
    public static final String ERROR_SUB_CODE_GATEWAY_NO_HANDLER = "404";
    public static final String ERROR_SUB_CODE_METHOD_NOT_IMPLEMENTED = "501";
    public static final String ERROR_SUB_MESSAGE_GATEWAY_EXCEPTION = "Internal Method Error";
    public static final String ERROR_SUB_MESSAGE_GATEWAY_NO_HANDLER = "Not Found";
    public static final String ERROR_SUB_MESSAGE_METHOD_NOT_IMPLEMENTED = "Not implemented";

    /* renamed from: a, reason: collision with root package name */
    private volatile boolean f11695a = false;

    /* renamed from: b, reason: collision with root package name */
    private BoneServiceInstanceManager f11696b;

    public SyncBoneInvoker(BoneServiceInstanceManager boneServiceInstanceManager) {
        if (boneServiceInstanceManager == null) {
            throw new IllegalArgumentException("serviceInstanceManager can not be null");
        }
        this.f11696b = boneServiceInstanceManager;
    }

    private void a(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) throws Exception {
        String str = boneCall.serviceId;
        Activity currentActivity = jSContext.getCurrentActivity();
        if (this.f11695a) {
            ALog.w("SyncBoneInvoker", "invoker has destroyed ");
            return;
        }
        BoneService serviceInstance = this.f11696b.getServiceInstance(str);
        if (serviceInstance == null) {
            String string = currentActivity.getString(R.string.jsbridge_core_error_message_internal_error);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("code", "404");
                jSONObject.put("message", ERROR_SUB_MESSAGE_GATEWAY_NO_HANDLER);
                jSONObject.put(AlinkConstants.KEY_LOCALIZED_MSG, currentActivity.getString(R.string.jsbridge_core_error_message_no_handler));
                boneCallback.failed("502", ERROR_MESSAGE_GATEWAY, string, jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            ALog.e("SyncBoneInvoker", "can not find handler for " + str);
            return;
        }
        try {
            if (serviceInstance.onCall(jSContext, boneCall, boneCallback)) {
                return;
            }
            String string2 = currentActivity.getString(R.string.jsbridge_core_error_message_internal_error);
            try {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", ERROR_SUB_CODE_METHOD_NOT_IMPLEMENTED);
                    jSONObject2.put("message", ERROR_SUB_MESSAGE_METHOD_NOT_IMPLEMENTED);
                    jSONObject2.put(AlinkConstants.KEY_LOCALIZED_MSG, currentActivity.getString(R.string.jsbridge_core_error_message_method_not_implemented));
                    boneCallback.failed("502", ERROR_MESSAGE_GATEWAY, string2, jSONObject2);
                } catch (RuntimeException e3) {
                    e3.printStackTrace();
                }
            } catch (JSONException e4) {
                e4.printStackTrace();
            }
            ALog.e("SyncBoneInvoker", boneCall.serviceId + " has not handle " + boneCall.methodName);
        } catch (Exception e5) {
            String string3 = currentActivity.getString(R.string.jsbridge_core_error_message_internal_error);
            try {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", ERROR_SUB_CODE_EXCEPTION);
                jSONObject3.put("message", ERROR_SUB_MESSAGE_GATEWAY_EXCEPTION);
                jSONObject3.put(AlinkConstants.KEY_LOCALIZED_MSG, currentActivity.getString(R.string.jsbridge_core_error_message_runtime_error));
                jSONObject3.put("exception", e5.getMessage());
                boneCallback.failed("502", ERROR_MESSAGE_GATEWAY, string3, jSONObject3);
            } catch (RuntimeException e6) {
                e6.printStackTrace();
            } catch (JSONException e7) {
                e7.printStackTrace();
            }
            ALog.e("SyncBoneInvoker", "exception happen when call instance.onCall");
            e5.printStackTrace();
        }
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.invoker.BoneInvoker
    public void invoke(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback, BoneCallback boneCallback2) throws Exception {
        if (this.f11695a) {
            return;
        }
        if (jSContext == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        if (jSContext.getCurrentActivity() == null) {
            ALog.d("SyncBoneInvoker", "ignore call after destroy");
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
        if (BoneCallMode.SYNC != boneCall.mode) {
            throw new IllegalArgumentException("only support sync call");
        }
        if (boneCallback == null) {
            throw new IllegalArgumentException("syncCallback can not be null");
        }
        a(jSContext, boneCall, boneCallback);
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.invoker.BoneInvoker
    public void onDestroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BoneServiceInstanceManager boneServiceInstanceManager = this.f11696b;
        if (boneServiceInstanceManager != null) {
            boneServiceInstanceManager.onDestroy();
            this.f11696b = null;
        }
        this.f11695a = true;
    }
}
