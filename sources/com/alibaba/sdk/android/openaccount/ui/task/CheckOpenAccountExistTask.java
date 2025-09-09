package com.alibaba.sdk.android.openaccount.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.model.CheckOAExistResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.callback.OpenAccountExistCallback;
import com.alibaba.sdk.android.openaccount.ui.model.CaptchaModel;
import com.alibaba.sdk.android.openaccount.ui.ui.LoginDoubleCheckWebActivity;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class CheckOpenAccountExistTask extends TaskWithDialog<Void, Void, Result<CheckOAExistResult>> {
    private Activity mActivity;
    private CaptchaModel mCaptchaModel;
    private String mLoginId;
    private OpenAccountExistCallback mOpenAccountExistCallback;

    public CheckOpenAccountExistTask(Activity activity, boolean z2, String str, CaptchaModel captchaModel, OpenAccountExistCallback openAccountExistCallback) {
        super(activity);
        this.showDialog = z2;
        this.mLoginId = str;
        this.mCaptchaModel = captchaModel;
        this.mOpenAccountExistCallback = openAccountExistCallback;
        this.mActivity = activity;
    }

    private OpenAccountExistCallback getCallback() {
        return this.mOpenAccountExistCallback;
    }

    private void handleFail(Map<String, String> map) {
        if (getCallback() != null) {
            getCallback().onFail(map);
        }
    }

    private void handleSuccess() {
        if (getCallback() != null) {
            getCallback().onSuccess();
        }
        Activity activity = this.mActivity;
        if (activity != null) {
            activity.finish();
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) {
        handleFail(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    public Result<CheckOAExistResult> asyncExecute(Void... voidArr) {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(this.mLoginId)) {
            map.put("loginId", this.mLoginId);
        }
        CaptchaModel captchaModel = this.mCaptchaModel;
        if (captchaModel != null) {
            if (!TextUtils.isEmpty(captchaModel.sig)) {
                map.put("sig", this.mCaptchaModel.sig);
            }
            if (!TextUtils.isEmpty(this.mCaptchaModel.csessionid)) {
                map.put("csessionid", this.mCaptchaModel.csessionid);
            }
            if (!TextUtils.isEmpty(this.mCaptchaModel.nctoken)) {
                map.put("nctoken", this.mCaptchaModel.nctoken);
            }
        }
        return OpenAccountUtils.parseCheckOAResult(RpcUtils.pureInvokeWithRiskControlInfo("checkAccountExistRequest", map, "checkaccountexist"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Result<CheckOAExistResult> result) {
        super.onPostExecute((CheckOpenAccountExistTask) result);
        try {
            if (result == null) {
                if (this.showDialog) {
                    ToastUtils.toastSystemError(this.context);
                }
                handleFail(null);
                return;
            }
            int i2 = result.code;
            if (i2 == 1) {
                CheckOAExistResult checkOAExistResult = result.data;
                if (checkOAExistResult == null) {
                    handleFail(null);
                    return;
                }
                if (checkOAExistResult.accountExist) {
                    handleSuccess();
                    return;
                } else {
                    if (!checkOAExistResult.havanaExist) {
                        ToastUtils.toastResource(this.context, "ali_sdk_openaccount_text_account_nonexist");
                        return;
                    }
                    HashMap map = new HashMap();
                    map.put("havanaid", result.data.havanaId);
                    handleFail(map);
                    return;
                }
            }
            if (i2 != 26053) {
                if (this.showDialog) {
                    if (TextUtils.isEmpty(result.message)) {
                        ToastUtils.toastSystemError(this.context);
                    } else {
                        ToastUtils.toast(this.context, result.message, result.code);
                    }
                }
                handleFail(null);
                return;
            }
            CheckOAExistResult checkOAExistResult2 = result.data;
            if (checkOAExistResult2 == null || checkOAExistResult2.checkCodeResult == null || TextUtils.isEmpty(checkOAExistResult2.checkCodeResult.clientVerifyData)) {
                return;
            }
            Uri.Builder builderBuildUpon = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
            builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
            Intent intent = new Intent(this.mActivity, (Class<?>) LoginDoubleCheckWebActivity.class);
            intent.putExtra("url", builderBuildUpon.toString());
            intent.putExtra("title", result.message);
            intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
            this.mActivity.startActivityForResult(intent, RequestCode.NO_CAPTCHA_REQUEST_CODE);
        } catch (Throwable th) {
            th.printStackTrace();
            handleFail(null);
        }
    }
}
