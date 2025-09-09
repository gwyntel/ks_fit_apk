package com.alibaba.sdk.android.openaccount.ui.task;

import android.content.Context;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.User;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class UpdateLoginIdTask extends TaskWithToastMessage<JSONObject> {
    boolean canUpdateOnlyOnce;
    private String loginid;

    @Autowired
    protected SessionManagerService sessionManagerService;

    public UpdateLoginIdTask(Context context, String str, boolean z2) {
        super(context);
        this.loginid = str;
        this.canUpdateOnlyOnce = z2;
    }

    private LoginCallback getLoginCallback() {
        return OpenAccountUIServiceImpl._updateLoginIdCallback;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
    protected void doFailAfterToast(Result<JSONObject> result) {
        if (getLoginCallback() != null) {
            getLoginCallback().onFailure(result.code, result.message);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
    protected void doSuccessAfterToast(Result<JSONObject> result) {
        result.toString();
        User userFromJSONObject = OpenAccountUtils.parseUserFromJSONObject(result.data);
        LoginCallback loginCallback = getLoginCallback();
        if (userFromJSONObject == null) {
            CommonUtils.onFailure(getLoginCallback(), MessageUtils.createMessage(10010, new Object[0]));
            return;
        }
        this.sessionManagerService.updateUser(userFromJSONObject);
        if (loginCallback != null) {
            loginCallback.onSuccess(this.sessionManagerService.getSession());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    public Result<JSONObject> asyncExecute(Void... voidArr) {
        HashMap map = new HashMap();
        map.put("indexType", 4);
        map.put("indexValue", this.loginid);
        return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("accountIndexRequest", map, "updateaccountindex"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
    public JSONObject parseData(JSONObject jSONObject) {
        return jSONObject.optJSONObject("openAccount");
    }
}
