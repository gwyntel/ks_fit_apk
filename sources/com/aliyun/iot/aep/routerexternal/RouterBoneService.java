package com.aliyun.iot.aep.routerexternal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.alink.sdk.bone.plugins.app.ConvertUtils;
import com.aliyun.iot.aep.component.router.Router;
import com.aliyun.iot.aep.sdk.bridge.base.BaseBoneService;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.context.OnActivityResultManager;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneFactory;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneMethod;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneService;
import org.json.JSONException;
import org.json.JSONObject;

@BoneFactory(name = "RouterServiceFactory", sdkName = "BoneRouterSDK", sdkVersion = "0.0.6")
@BoneService(group = "RouterServiceFactory", name = RouterBoneService.API_NAME)
/* loaded from: classes3.dex */
public class RouterBoneService extends BaseBoneService {
    public static final String API_NAME = "BoneRouter";

    private boolean toSupportOpenH5(String str, Context context) throws ClassNotFoundException {
        if (!TextUtils.isEmpty(str) && str.length() >= 6) {
            try {
                if (str.substring(0, 4).equalsIgnoreCase("http") || str.substring(0, 5).equalsIgnoreCase("https")) {
                    Intent intent = new Intent(context.getApplicationContext(), Class.forName("com.aliyun.sdk.lighter.runtime.activity.BHARootActivity"));
                    Uri uri = Uri.parse(str.trim());
                    if (uri != null && uri.getScheme() != null) {
                        if (!uri.getScheme().equals("http")) {
                            if (uri.getScheme().equals("https")) {
                            }
                        }
                        ALog.d(API_NAME, "open http/https page.");
                        intent.setData(uri);
                        context.startActivity(intent);
                        return true;
                    }
                    return false;
                }
                return false;
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return false;
    }

    @BoneMethod
    public void open(final JSContext jSContext, String str, JSONObject jSONObject, JSONObject jSONObject2, BoneCallback boneCallback) {
        boolean url;
        Bundle bundle;
        ALog.d(API_NAME, "open() called with: jsContext = [" + jSContext + "], url = [" + str + "], params = [" + jSONObject + "], config = [" + jSONObject2 + "], boneCallback = [" + boneCallback + "]");
        Activity currentActivity = jSContext.getCurrentActivity();
        Bundle bundle2 = new Bundle();
        if (jSONObject != null) {
            try {
                bundle2.putAll(ConvertUtils.toBundle(jSONObject));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        boolean zOptBoolean = false;
        if (jSONObject2 != null && jSONObject2.optBoolean("h5Panel", false) && toSupportOpenH5(str, currentActivity)) {
            ALog.d(API_NAME, "open http or https page");
            JSONObject jSONObject3 = new JSONObject();
            try {
                jSONObject3.put("result", true);
                boneCallback.success(jSONObject3);
                return;
            } catch (JSONException e3) {
                e3.printStackTrace();
                return;
            }
        }
        if (jSONObject2 != null) {
            zOptBoolean = jSONObject2.optBoolean("expectReceiveResult", false);
            try {
                bundle = ConvertUtils.toBundle(jSONObject2);
            } catch (Exception e4) {
                e4.printStackTrace();
                bundle = null;
            }
            bundle2.putBundle("bone-mobile-config", bundle);
        }
        if (zOptBoolean && (currentActivity instanceof Activity)) {
            url = Router.getInstance().toUrlForResult(currentActivity, str, 1001, bundle2);
            jSContext.addOnActivityResultListener(new OnActivityResultManager.OnActivityResultListener() { // from class: com.aliyun.iot.aep.routerexternal.RouterBoneService.1
                @Override // com.aliyun.iot.aep.sdk.bridge.core.context.OnActivityResultManager.OnActivityResultListener
                public void onActivityResult(Activity activity, int i2, int i3, Intent intent) {
                    if (i2 == 1001) {
                        JSONObject jSONObjectFromBundle = null;
                        if (intent != null) {
                            try {
                                jSONObjectFromBundle = ConvertUtils.fromBundle(intent.getExtras());
                            } catch (Exception e5) {
                                e5.printStackTrace();
                            }
                        }
                        jSContext.emitter("BoneReceivedResult", jSONObjectFromBundle);
                    }
                    jSContext.removeOnActivityResultListener(this);
                }
            });
        } else {
            url = Router.getInstance().toUrl(currentActivity, str, bundle2);
        }
        if (!url) {
            boneCallback.failed("404", "url is validate", "");
            return;
        }
        JSONObject jSONObject4 = new JSONObject();
        try {
            jSONObject4.put("result", true);
            boneCallback.success(jSONObject4);
        } catch (JSONException e5) {
            e5.printStackTrace();
        }
    }
}
