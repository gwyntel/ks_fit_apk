package com.alibaba.sdk.android.openaccount.ui.bridge;

import com.alibaba.sdk.android.openaccount.ui.task.LoginByQrCodeTask;
import com.alibaba.sdk.android.openaccount.ui.task.QrLoginConfirmTask;
import com.alibaba.sdk.android.openaccount.webview.BridgeCallbackContext;
import com.alibaba.sdk.android.openaccount.webview.BridgeMethod;

/* loaded from: classes2.dex */
public class LoginBridge {
    @BridgeMethod
    public void openAccountQrLoginConfirm(BridgeCallbackContext bridgeCallbackContext, String str) {
        new QrLoginConfirmTask(bridgeCallbackContext).execute(str);
    }

    @BridgeMethod
    public void qrLoginSuccess(BridgeCallbackContext bridgeCallbackContext, String str) {
        new LoginByQrCodeTask(bridgeCallbackContext).execute(str);
    }
}
