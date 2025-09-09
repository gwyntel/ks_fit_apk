package com.alibaba.sdk.android.oauth;

import android.content.Context;
import com.alibaba.sdk.android.oauth.callback.OauthQueryCallback;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.model.OpenAccountLink;
import com.alibaba.sdk.android.openaccount.rpc.model.RpcResponse;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class OauthQueryTask extends TaskWithDialog<Void, Void, Void> {

    @Autowired
    private ExecutorService executorService;
    private OauthQueryCallback mCallback;
    private int plateform;

    public OauthQueryTask(Context context, int i2, OauthQueryCallback oauthQueryCallback) {
        super(context);
        this.mCallback = oauthQueryCallback;
        this.plateform = i2;
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    public Void asyncExecute(Void[] voidArr) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map2.put("outerPlatform", String.valueOf(this.plateform));
        map2.put("type", 1);
        map.put("accountLinkFilterCondition", map2);
        final RpcResponse rpcResponsePureInvokeWithRiskControlInfo = RpcUtils.pureInvokeWithRiskControlInfo("accountLinkListRequest", map, "queryaccountlinklist");
        if (rpcResponsePureInvokeWithRiskControlInfo == null) {
            return null;
        }
        JSONObject jSONObject = rpcResponsePureInvokeWithRiskControlInfo.data;
        if (jSONObject == null) {
            if (this.mCallback == null) {
                return null;
            }
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.oauth.OauthQueryTask.3
                @Override // java.lang.Runnable
                public void run() {
                    OauthQueryCallback oauthQueryCallback = OauthQueryTask.this.mCallback;
                    RpcResponse rpcResponse = rpcResponsePureInvokeWithRiskControlInfo;
                    oauthQueryCallback.onFailure(rpcResponse.code, rpcResponse.message);
                }
            });
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(jSONObject.optString("openAccountLinkList"));
            final ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject2 = (JSONObject) jSONArray.get(i2);
                OpenAccountLink openAccountLink = new OpenAccountLink();
                openAccountLink.deviceId = jSONObject2.optString("deviceId");
                openAccountLink.nickName = jSONObject2.optString("nickName");
                openAccountLink.openAccountId = jSONObject2.optString("openAccountId");
                openAccountLink.outerId = jSONObject2.optString("outerId");
                openAccountLink.outerPlatform = jSONObject2.optString("outerPlatform");
                openAccountLink.type = jSONObject2.optString("type");
                openAccountLink.useLogin = Boolean.valueOf(Boolean.parseBoolean(jSONObject2.optString("useLogin")));
                openAccountLink.gender = Integer.valueOf(Integer.parseInt(jSONObject2.optString("gender")));
                openAccountLink.avatarUrl = jSONObject2.optString("avatarUrl");
                arrayList.add(openAccountLink);
            }
            if (this.mCallback == null) {
                return null;
            }
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.oauth.OauthQueryTask.1
                @Override // java.lang.Runnable
                public void run() {
                    OauthQueryTask.this.mCallback.onSuccess(arrayList);
                }
            });
            return null;
        } catch (Exception unused) {
            if (this.mCallback == null) {
                return null;
            }
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.oauth.OauthQueryTask.2
                @Override // java.lang.Runnable
                public void run() {
                    OauthQueryTask.this.mCallback.onSuccess(null);
                }
            });
            return null;
        }
    }
}
