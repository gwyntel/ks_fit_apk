package com.aliyun.alink.sdk.bone.plugins.alog;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.logextra.upload.Log2Cloud;
import com.aliyun.alink.linksdk.logextra.upload.OSSManager;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.bridge.base.BaseBoneService;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.bridge.invoker.SyncBoneInvoker;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneMethod;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneService;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.ServiceMode;
import com.umeng.message.common.inter.ITagManager;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONException;
import org.json.JSONObject;

@BoneService(mode = ServiceMode.ALWAYS_NEW, name = BoneALog.API_NAME)
/* loaded from: classes2.dex */
public class BoneALog extends BaseBoneService {
    public static final String API_NAME = "BoneLog";

    @BoneMethod
    public void log(String str, String str2, String str3, BoneCallback boneCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        str.hashCode();
        switch (str) {
            case "Warning":
                ALog.w("BonePlugin", str3);
                break;
            case "Info":
                ALog.i("BonePlugin", str3);
                break;
            case "Debug":
                ALog.d("BonePlugin", str3);
                break;
            case "Error":
                ALog.e("BonePlugin", str3);
                break;
            case "Verbose":
                ALog.d("BonePlugin", str3);
                break;
        }
        boneCallback.success(new JSONObject());
    }

    @BoneMethod
    public void uploadLog(JSONObject jSONObject, final BoneCallback boneCallback) {
        try {
            Log2Cloud.getInstance();
            Log2Cloud.getInstance().uploadLog(jSONObject, new OSSManager.OSSUploadCallback() { // from class: com.aliyun.alink.sdk.bone.plugins.alog.BoneALog.1
                public void onUploadFailed(int i2, String str) {
                    boneCallback.failed(String.valueOf(i2), str, "");
                }

                public void onUploadSuccess(String str, String str2) throws JSONException {
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("code", 200);
                        jSONObject2.put("msg", ITagManager.SUCCESS);
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("name", str);
                        if (!TextUtils.isEmpty(str2)) {
                            jSONObject3.put("deviceLogFileId", str2);
                        }
                        jSONObject2.put("data", jSONObject3);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    boneCallback.success(jSONObject2);
                }
            });
        } catch (Throwable unused) {
            boneCallback.failed(SyncBoneInvoker.ERROR_SUB_CODE_EXCEPTION, "Log2Cloud not exist", "");
        }
    }
}
