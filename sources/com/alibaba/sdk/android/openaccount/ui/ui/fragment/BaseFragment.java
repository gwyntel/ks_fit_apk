package com.alibaba.sdk.android.openaccount.ui.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.model.CheckCodeResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.model.SmsActionType;
import com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage;
import com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity;
import com.alibaba.sdk.android.openaccount.util.OpenAccountRiskControlContext;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.lang.reflect.Field;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected BaseAppCompatActivity mAttachedActivity;

    public interface BeforeJumpToPwdCallback {
        void onFinish();
    }

    protected class SendSMSForResetPwdTask extends TaskWithToastMessage<Void> {
        public SendSMSForResetPwdTask(Activity activity) {
            super(activity);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doFailAfterToast(Result<Void> result) {
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doSuccessAfterToast(Result<Void> result) {
            BaseFragment.this.onSendSMSForResetPwdSuccess(result);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        public Void parseData(JSONObject jSONObject) {
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<Void> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, BaseFragment.this.getMobile());
            map.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, BaseFragment.this.getLocationCode());
            map.put("riskControlInfo", OpenAccountRiskControlContext.buildRiskContext());
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("sendSmsCodeForResetPasswordRequest", map, "sendsmscodeforresetpassword"));
        }
    }

    protected class SendSmsCodeForRegisterTask extends TaskWithToastMessage<CheckCodeResult> {
        public SendSmsCodeForRegisterTask(Activity activity) {
            super(activity);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doFailAfterToast(Result<CheckCodeResult> result) {
            int i2 = result.code;
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doSuccessAfterToast(Result<CheckCodeResult> result) {
            BaseFragment.this.onSendSMSForRegisterSuccess(result);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected boolean toastMessageRequired(Result<CheckCodeResult> result) {
            return result.code != 26053;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<CheckCodeResult> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, BaseFragment.this.getMobile());
            map.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, BaseFragment.this.getLocationCode());
            map.put("smsActionType", SmsActionType.SDK_ACCOUNT_REGISTER);
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("sendSmsCodeRequest", map, "sendsmscode"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        public CheckCodeResult parseData(JSONObject jSONObject) {
            CheckCodeResult checkCodeResult = new CheckCodeResult();
            checkCodeResult.checkCodeId = jSONObject.optString("checkCodeId");
            checkCodeResult.checkCodeUrl = jSONObject.optString("checkCodeUrl");
            checkCodeResult.clientVerifyData = jSONObject.optString("clientVerifyData");
            return checkCodeResult;
        }
    }

    protected String getLocationCode() {
        return "86";
    }

    protected LoginCallback getLoginCallback() {
        LoginCallback loginCallback = OpenAccountUIServiceImpl._specialLoginCallback;
        if (loginCallback != null) {
            return loginCallback;
        }
        return null;
    }

    protected String getMobile() {
        return "";
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
        if (activity instanceof BaseAppCompatActivity) {
            this.mAttachedActivity = (BaseAppCompatActivity) activity;
        }
    }

    protected void onBackPressed() {
        BaseAppCompatActivity baseAppCompatActivity = this.mAttachedActivity;
        if (baseAppCompatActivity != null) {
            baseAppCompatActivity.onBackPressed();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        super.onDestroyView();
        try {
            Field declaredField = Fragment.class.getDeclaredField("mChildFragmentManager");
            declaredField.setAccessible(true);
            declaredField.set(this, null);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (NoSuchFieldException e3) {
            throw new RuntimeException(e3);
        }
    }

    protected void onFail(Throwable th) {
    }

    protected void onSendSMSForRegisterSuccess(Result<CheckCodeResult> result) {
    }

    protected void onSendSMSForResetPwdSuccess(Result<Void> result) {
    }

    protected void successCallback() {
        ((ExecutorService) OpenAccountSDK.getService(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment.1
            @Override // java.lang.Runnable
            public void run() {
                LoginCallback loginCallback = BaseFragment.this.getLoginCallback();
                if (loginCallback != null) {
                    loginCallback.onSuccess(((SessionManagerService) OpenAccountSDK.getService(SessionManagerService.class)).getSession());
                    BaseAppCompatActivity baseAppCompatActivity = BaseFragment.this.mAttachedActivity;
                    if (baseAppCompatActivity != null) {
                        baseAppCompatActivity.finish();
                    }
                }
            }
        });
    }
}
