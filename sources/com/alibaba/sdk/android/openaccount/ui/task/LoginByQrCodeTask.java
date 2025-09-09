package com.alibaba.sdk.android.openaccount.ui.task;

import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.device.DeviceManager;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.rpc.model.RpcRequest;
import com.alibaba.sdk.android.openaccount.rpc.model.RpcResponse;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.task.AbsAsyncTask;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.openaccount.webview.BridgeCallbackContext;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.util.Map;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class LoginByQrCodeTask extends AbsAsyncTask<String, Void, Void> {
    private BridgeCallbackContext bridgeCallbackContext;

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private SessionManagerService sessionManagerService;

    public LoginByQrCodeTask(BridgeCallbackContext bridgeCallbackContext) {
        this.bridgeCallbackContext = bridgeCallbackContext;
    }

    private void onFailure(final int i2, final String str) {
        final LoginCallback loginCallback = OpenAccountUIServiceImpl._qrLoginCallback;
        if (loginCallback != null) {
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.task.LoginByQrCodeTask.1
                @Override // java.lang.Runnable
                public void run() {
                    loginCallback.onFailure(i2, str);
                }
            });
        }
    }

    private void onSuccess() {
        final LoginCallback loginCallback = OpenAccountUIServiceImpl._qrLoginCallback;
        if (loginCallback != null) {
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.task.LoginByQrCodeTask.2
                @Override // java.lang.Runnable
                public void run() {
                    loginCallback.onSuccess(LoginByQrCodeTask.this.sessionManagerService.getSession());
                }
            });
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doFinally() {
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) throws JSONException {
        Message messageCreateMessage = MessageUtils.createMessage(10010, th == null ? "" : th.getMessage());
        AliSDKLogger.log(OpenAccountUIConstants.LOG_TAG, messageCreateMessage, th);
        this.bridgeCallbackContext.onFailure(messageCreateMessage.code, messageCreateMessage.message);
        onFailure(messageCreateMessage.code, messageCreateMessage.message);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    public Void asyncExecute(String... strArr) {
        LoginResult loginResult;
        RpcRequest rpcRequestCreate = RpcRequest.create(strArr[0]);
        Map map = (Map) rpcRequestCreate.params.get("qrCodeInfo");
        rpcRequestCreate.target = "qrcodepolling";
        map.put("utdid", this.deviceManager.getUtdid());
        RpcResponse rpcResponsePureInvokeWithRiskControlInfo = RpcUtils.pureInvokeWithRiskControlInfo(rpcRequestCreate, "qrCodeInfo");
        Result<LoginResult> loginResult2 = OpenAccountUtils.toLoginResult(rpcResponsePureInvokeWithRiskControlInfo);
        int i2 = loginResult2.code;
        if (!loginResult2.isSuccess() || (loginResult = loginResult2.data) == null || loginResult.loginSuccessResult == null) {
            this.bridgeCallbackContext.success(RpcUtils.toRawRpcJsonString(rpcResponsePureInvokeWithRiskControlInfo));
            AliSDKLogger.e(OpenAccountUIConstants.LOG_TAG, loginResult2.toString());
            return null;
        }
        SessionData sessionDataCreateSessionDataFromLoginSuccessResult = OpenAccountUtils.createSessionDataFromLoginSuccessResult(loginResult.loginSuccessResult);
        if (sessionDataCreateSessionDataFromLoginSuccessResult.scenario == null) {
            sessionDataCreateSessionDataFromLoginSuccessResult.scenario = 1;
        }
        this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
        onSuccess();
        this.bridgeCallbackContext.getActivity().finish();
        return null;
    }
}
