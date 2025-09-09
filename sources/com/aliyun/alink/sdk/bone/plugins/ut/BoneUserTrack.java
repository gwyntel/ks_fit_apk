package com.aliyun.alink.sdk.bone.plugins.ut;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import com.aliyun.iot.aep.sdk.bridge.base.BaseBoneService;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.bridge.invoker.SyncBoneInvoker;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneMethod;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneService;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.ServiceMode;
import java.util.Map;
import org.json.JSONObject;

@BoneService(mode = ServiceMode.ALWAYS_NEW, name = "BoneUserTrack")
/* loaded from: classes2.dex */
public class BoneUserTrack extends BaseBoneService {
    @BoneMethod
    public void record(String str, JSONObject jSONObject, BoneCallback boneCallback) {
        Log.d("BoneUserTrack", "do record");
        if (TextUtils.isEmpty(str)) {
            Log.e("BoneUserTrack", "eventName is null");
            boneCallback.failed(SyncBoneInvoker.ERROR_SUB_CODE_EXCEPTION, "eventName is null", "");
            return;
        }
        try {
            if (jSONObject != null) {
                com.alibaba.fastjson.JSONObject object = JSON.parseObject(jSONObject.toString());
                if (object == null) {
                    Log.e("BoneUserTrack", "to fastJsonObject null");
                    boneCallback.failed("-501", "to fastJsonObject null", "");
                    return;
                }
                Log.d("BoneUserTrack", "do AUserTrack.record  ---> data: " + object.toJSONString() + ";eventName:" + str);
                AUserTrack.record(str, (Map) JSON.toJavaObject(object, Map.class));
            } else {
                Log.d("BoneUserTrack", "do AUserTrack.record  ---> null data;eventName:" + str);
                AUserTrack.record(str, null);
            }
            boneCallback.success();
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.e("BoneUserTrack", "AUserTrack exception");
            boneCallback.failed("-500", "AUserTrack exception", "");
        }
    }
}
