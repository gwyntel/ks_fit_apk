package com.alibaba.sdk.android.openaccount.ui.task;

import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.device.DeviceManager;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageConstants;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.rpc.RpcService;
import com.alibaba.sdk.android.openaccount.rpc.model.RpcRequest;
import com.alibaba.sdk.android.openaccount.rpc.model.RpcResponse;
import com.alibaba.sdk.android.openaccount.task.AbsAsyncTask;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alibaba.sdk.android.openaccount.ui.callback.QrConfirmLoginCallback;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.openaccount.webview.BridgeCallbackContext;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.umeng.analytics.pro.bc;
import java.util.Map;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class QrLoginConfirmTask extends AbsAsyncTask<String, Void, Void> {
    private BridgeCallbackContext bridgeCallbackContext;

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private RpcService rpcService;

    public QrLoginConfirmTask(BridgeCallbackContext bridgeCallbackContext) {
        this.bridgeCallbackContext = bridgeCallbackContext;
    }

    private void onFailure(boolean z2, final int i2, final String str) {
        if (z2) {
            this.bridgeCallbackContext.getActivity().finish();
        }
        final QrConfirmLoginCallback qrConfirmLoginCallback = OpenAccountUIServiceImpl._qrLoginConfirmCallback;
        if (qrConfirmLoginCallback != null) {
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.task.QrLoginConfirmTask.1
                @Override // java.lang.Runnable
                public void run() {
                    qrConfirmLoginCallback.onFailure(i2, str);
                }
            });
        }
    }

    private void onSuccess() {
        this.bridgeCallbackContext.getActivity().finish();
        final QrConfirmLoginCallback qrConfirmLoginCallback = OpenAccountUIServiceImpl._qrLoginConfirmCallback;
        if (qrConfirmLoginCallback != null) {
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.task.QrLoginConfirmTask.2
                @Override // java.lang.Runnable
                public void run() {
                    qrConfirmLoginCallback.onSuccess();
                }
            });
        }
    }

    private void onUserCancelFailure(boolean z2) {
        onFailure(z2, 10003, MessageUtils.getMessageContent(10003, new Object[0]));
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doFinally() {
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) throws JSONException {
        Message messageCreateMessage = MessageUtils.createMessage(10010, th.getMessage());
        AliSDKLogger.log(OpenAccountUIConstants.LOG_TAG, messageCreateMessage, th);
        this.bridgeCallbackContext.onFailure(messageCreateMessage.code, messageCreateMessage.message);
        this.bridgeCallbackContext.getActivity().finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    public Void asyncExecute(String... strArr) {
        boolean z2;
        boolean z3 = true;
        if (strArr == null || strArr.length == 0) {
            onUserCancelFailure(true);
            return null;
        }
        try {
            RpcRequest rpcRequestCreate = RpcRequest.create(strArr[0]);
            Map map = (Map) rpcRequestCreate.params.get("qrConfirmInfo");
            String str = (String) map.get("action");
            if (TextUtils.isEmpty(str)) {
                onUserCancelFailure(true);
                return null;
            }
            if (bc.aL.equals(str)) {
                this.bridgeCallbackContext.getActivity().finish();
                z2 = false;
            } else {
                z2 = true;
            }
            try {
                map.put("utdid", this.deviceManager.getUtdid());
                RpcResponse rpcResponsePureInvokeWithRiskControlInfo = RpcUtils.pureInvokeWithRiskControlInfo(rpcRequestCreate, "qrConfirmInfo");
                if (rpcResponsePureInvokeWithRiskControlInfo == null) {
                    onFailure(z2, MessageConstants.GENERIC_RPC_ERROR, MessageUtils.getMessageContent(MessageConstants.GENERIC_RPC_ERROR, new Object[0]));
                    return null;
                }
                int i2 = rpcResponsePureInvokeWithRiskControlInfo.code;
                if (i2 != 1) {
                    onFailure(z2, i2, rpcResponsePureInvokeWithRiskControlInfo.message);
                } else if (bc.aL.equals(str)) {
                    onUserCancelFailure(z2);
                } else {
                    onSuccess();
                }
                return null;
            } catch (Exception e2) {
                e = e2;
                z3 = z2;
                AliSDKLogger.e(OpenAccountUIConstants.LOG_TAG, "fail to parse the response", e);
                onUserCancelFailure(z3);
                return null;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }
}
