package com.alibaba.sdk.android.openaccount.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.CheckBindedHidCallback;
import com.alibaba.sdk.android.openaccount.executor.impl.ExecutorServiceImpl;
import com.alibaba.sdk.android.openaccount.rpc.model.RpcResponse;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.ui.TokenWebViewActivity;
import com.alibaba.sdk.android.openaccount.ut.UTConstants;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.facebook.share.internal.ShareConstants;
import java.util.HashMap;
import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CheckHidBindedTask extends InitWaitTask {
    public CheckHidBindedTask(final Context context, final Long l2, final String str, final String str2, final CheckBindedHidCallback checkBindedHidCallback) {
        super(context, checkBindedHidCallback, new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.CheckHidBindedTask.1
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                HashMap map = new HashMap();
                SessionManagerService sessionManagerService = (SessionManagerService) OpenAccountSDK.getService(SessionManagerService.class);
                if (sessionManagerService != null) {
                    map.put(DeviceCommonConstants.REDIRECTURL, "https://www.taobao.com");
                    map.put("havanaId", l2);
                    HashMap map2 = new HashMap();
                    if (!TextUtils.isEmpty(str)) {
                        map2.put("ivToken", str);
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        map2.put("actionType", str2);
                    }
                    map.put(AgooConstants.MESSAGE_EXT, map2);
                    try {
                        map.put("openAccountId", Long.valueOf(Long.parseLong(sessionManagerService.getSession().getUserId())));
                    } catch (Exception unused) {
                    }
                    RpcResponse rpcResponsePureInvokeWithRiskControlInfo = RpcUtils.pureInvokeWithRiskControlInfo(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, map, "havanassologin");
                    final JSONObject jSONObject = rpcResponsePureInvokeWithRiskControlInfo.data;
                    if (jSONObject == null) {
                        CommonUtils.onFailure(checkBindedHidCallback, rpcResponsePureInvokeWithRiskControlInfo.code, rpcResponsePureInvokeWithRiskControlInfo.message);
                        return;
                    }
                    int i2 = rpcResponsePureInvokeWithRiskControlInfo.code;
                    if (i2 == 1) {
                        if (checkBindedHidCallback != null) {
                            ExecutorServiceImpl.INSTANCE.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.CheckHidBindedTask.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    checkBindedHidCallback.onSuccess(jSONObject.optString("ssoToken"));
                                }
                            });
                            return;
                        }
                        return;
                    }
                    if (i2 != 26152) {
                        CommonUtils.onFailure(checkBindedHidCallback, i2, rpcResponsePureInvokeWithRiskControlInfo.message);
                        return;
                    }
                    try {
                        JSONObject jSONObject2 = jSONObject.getJSONObject(AgooConstants.MESSAGE_EXT);
                        if (jSONObject2 == null || TextUtils.isEmpty(jSONObject2.getString("clientVerifyData"))) {
                            return;
                        }
                        Uri.Builder builderBuildUpon = Uri.parse(jSONObject2.getString("clientVerifyData")).buildUpon();
                        builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                        Intent intent = new Intent(context, (Class<?>) TokenWebViewActivity.class);
                        intent.putExtra("url", builderBuildUpon.toString());
                        intent.putExtra("title", rpcResponsePureInvokeWithRiskControlInfo.message);
                        intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
                        intent.putExtra(OpenAccountConstants.HAVANA_ID, l2);
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                    } catch (Exception unused2) {
                        CommonUtils.onFailure(checkBindedHidCallback, rpcResponsePureInvokeWithRiskControlInfo.code, rpcResponsePureInvokeWithRiskControlInfo.message);
                    }
                }
            }
        }, UTConstants.E_SSO_TAO);
    }
}
