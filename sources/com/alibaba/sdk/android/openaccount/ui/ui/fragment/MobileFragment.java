package com.alibaba.sdk.android.openaccount.ui.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import anetwork.channel.util.RequestConstant;
import com.alibaba.sdk.android.openaccount.model.CheckCodeResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.rpc.RpcServerBizConstants;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.ui.R;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.model.CheckAccountExistResult;
import com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.LoginDoubleCheckWebActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.LoginIVWebActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.SpecialLoginActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.util.JSONUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.umeng.ccg.a;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MobileFragment extends BaseFragment {
    protected String mAccountExist;
    protected EditText mMobileInputBox;
    protected Button mNextButton;
    protected int mScene = 0;

    protected class CheckAccountExistTask extends TaskWithDialog<Void, Void, Result<CheckAccountExistResult>> {
        private String cSessionId;
        private String loginId;
        private String nocToken;
        private String sig;

        public CheckAccountExistTask(Activity activity, String str, String str2, String str3, String str4) {
            super(activity);
            this.loginId = str;
            this.sig = str2;
            this.nocToken = str3;
            this.cSessionId = str4;
        }

        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        protected void doWhenException(Throwable th) {
            MobileFragment.this.onFail(th);
        }

        protected Result<CheckAccountExistResult> parseJsonResult(Result<JSONObject> result) {
            JSONObject jSONObject = result.data;
            if (jSONObject == null) {
                return Result.result(result.code, result.message);
            }
            JSONObject jSONObject2 = jSONObject;
            CheckAccountExistResult checkAccountExistResult = new CheckAccountExistResult();
            checkAccountExistResult.accountExist = jSONObject2.optString("accountExist");
            checkAccountExistResult.accountHasPassword = jSONObject2.optString("accountHasPassword");
            checkAccountExistResult.havanaExist = jSONObject2.optString("havanaExist");
            JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("checkCodeResult");
            if (jSONObjectOptJSONObject != null) {
                CheckCodeResult checkCodeResult = new CheckCodeResult();
                checkAccountExistResult.checkCodeResult = checkCodeResult;
                checkCodeResult.checkCodeId = JSONUtils.optString(jSONObjectOptJSONObject, "checkCodeId");
                checkCodeResult.checkCodeUrl = JSONUtils.optString(jSONObjectOptJSONObject, "checkCodeUrl");
                checkCodeResult.clientVerifyData = JSONUtils.optString(jSONObjectOptJSONObject, "clientVerifyData");
            }
            return Result.result(result.code, result.message, checkAccountExistResult);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<CheckAccountExistResult> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            map.put("loginId", this.loginId);
            String str = this.sig;
            if (str != null) {
                map.put("sig", str);
            }
            if (!TextUtils.isEmpty(this.cSessionId)) {
                map.put("csessionid", this.cSessionId);
            }
            if (!TextUtils.isEmpty(this.nocToken)) {
                map.put("nctoken", this.nocToken);
            }
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("checkAccountExistRequest", map, "checkaccountexist"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Result<CheckAccountExistResult> result) {
            if (result == null) {
                ToastUtils.toastSystemError(this.context);
                return;
            }
            int i2 = result.code;
            if (i2 == 1) {
                if (TextUtils.equals(result.data.accountHasPassword, "true")) {
                    MobileFragment.this.goPassword();
                    return;
                } else {
                    CheckAccountExistResult checkAccountExistResult = result.data;
                    MobileFragment.this.sendSmsCode(checkAccountExistResult != null ? checkAccountExistResult.accountExist : "");
                    return;
                }
            }
            if (i2 == 26053) {
                CheckAccountExistResult checkAccountExistResult2 = result.data;
                if (checkAccountExistResult2 == null || checkAccountExistResult2.checkCodeResult == null || TextUtils.isEmpty(checkAccountExistResult2.checkCodeResult.clientVerifyData)) {
                    return;
                }
                Uri.Builder builderBuildUpon = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
                builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                Intent intent = new Intent(MobileFragment.this.getActivity(), (Class<?>) LoginDoubleCheckWebActivity.class);
                intent.putExtra("url", builderBuildUpon.toString());
                intent.putExtra("title", result.message);
                intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
                MobileFragment.this.startActivityForResult(intent, RequestCode.NO_CAPTCHA_REQUEST_CODE);
                return;
            }
            if (i2 != 26152) {
                if (TextUtils.equals(result.type, RpcServerBizConstants.ACTION_TYPE_CALLBACK) && MobileFragment.this.getLoginCallback() != null) {
                    MobileFragment.this.getLoginCallback().onFailure(result.code, result.message);
                    return;
                } else if (TextUtils.isEmpty(result.message)) {
                    ToastUtils.toastSystemError(this.context);
                    return;
                } else {
                    ToastUtils.toast(this.context, result.message, result.code);
                    return;
                }
            }
            CheckAccountExistResult checkAccountExistResult3 = result.data;
            if (checkAccountExistResult3 == null || checkAccountExistResult3.checkCodeResult == null || TextUtils.isEmpty(checkAccountExistResult3.checkCodeResult.clientVerifyData)) {
                return;
            }
            Uri.Builder builderBuildUpon2 = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
            builderBuildUpon2.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
            Intent intent2 = new Intent(MobileFragment.this.getActivity(), (Class<?>) LoginIVWebActivity.class);
            intent2.putExtra("url", builderBuildUpon2.toString());
            intent2.putExtra("title", result.message);
            intent2.putExtra("callback", "https://www.alipay.com/webviewbridge");
            MobileFragment.this.startActivityForResult(intent2, RequestCode.RISK_IV_REQUEST_CODE);
        }
    }

    private void afterViews() {
        Button button = this.mNextButton;
        if (button != null) {
            button.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.fragment.MobileFragment.1
                @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
                public void afterCheck(View view) {
                    MobileFragment.this.goNext();
                }
            });
        }
    }

    private void goCheckCode(String str) {
        BaseAppCompatActivity baseAppCompatActivity = this.mAttachedActivity;
        if (baseAppCompatActivity == null || !(baseAppCompatActivity instanceof SpecialLoginActivity) || baseAppCompatActivity.isFinishing()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, getMobile());
        bundle.putString("accountExist", str);
        bundle.putInt(a.f22008j, this.mScene);
        ((SpecialLoginActivity) this.mAttachedActivity).jumpToCheckCode(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goPassword() {
        BaseAppCompatActivity baseAppCompatActivity = this.mAttachedActivity;
        if (baseAppCompatActivity == null || !(baseAppCompatActivity instanceof SpecialLoginActivity) || baseAppCompatActivity.isFinishing()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, getMobile());
        bundle.putInt(a.f22008j, this.mScene);
        ((SpecialLoginActivity) this.mAttachedActivity).goPwd(bundle);
    }

    private void initParams() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            try {
                this.mScene = arguments.getInt(a.f22008j);
            } catch (Exception unused) {
            }
        }
    }

    protected CheckAccountExistTask createCheckAccountExistTask(String str, String str2, String str3) {
        String mobile;
        if (this.mMobileInputBox == null || (mobile = getMobile()) == null || mobile.length() <= 0) {
            return null;
        }
        return new CheckAccountExistTask(this.mActivity, mobile, str, str2, str3);
    }

    protected int getLayout() {
        return R.layout.ali_sdk_openaccount_fragment_mobile;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment
    @NonNull
    protected String getMobile() {
        EditText editText = this.mMobileInputBox;
        return editText != null ? editText.getText().toString() : "";
    }

    protected void goNext() {
        CheckAccountExistTask checkAccountExistTaskCreateCheckAccountExistTask;
        if (TextUtils.isEmpty(getMobile()) || this.mScene != 0 || (checkAccountExistTaskCreateCheckAccountExistTask = createCheckAccountExistTask(null, null, null)) == null) {
            return;
        }
        checkAccountExistTaskCreateCheckAccountExistTask.execute(new Void[0]);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, Intent intent) {
        CheckAccountExistTask checkAccountExistTaskCreateCheckAccountExistTask;
        super.onActivityResult(i2, i3, intent);
        if (i2 != RequestCode.NO_CAPTCHA_REQUEST_CODE || i3 != -1) {
            if (i2 == RequestCode.RISK_IV_REQUEST_CODE && i3 == -1 && (checkAccountExistTaskCreateCheckAccountExistTask = createCheckAccountExistTask(null, null, null)) != null) {
                checkAccountExistTaskCreateCheckAccountExistTask.execute(new Void[0]);
                return;
            }
            return;
        }
        if (intent == null || !"nocaptcha".equals(intent.getStringExtra("action"))) {
            return;
        }
        CheckAccountExistTask checkAccountExistTaskCreateCheckAccountExistTask2 = createCheckAccountExistTask(intent.getStringExtra("sig"), intent.getStringExtra("nocToken"), intent.getStringExtra("cSessionId"));
        if (checkAccountExistTaskCreateCheckAccountExistTask2 != null) {
            checkAccountExistTaskCreateCheckAccountExistTask2.execute(new Void[0]);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        BaseAppCompatActivity baseAppCompatActivity = this.mAttachedActivity;
        if (baseAppCompatActivity != null && baseAppCompatActivity.getSupportActionBar() != null) {
            this.mAttachedActivity.getSupportActionBar().setTitle(R.string.ali_sdk_openaccount_text_enter_mobile);
        }
        View viewInflate = layoutInflater.inflate(getLayout(), viewGroup, false);
        this.mMobileInputBox = (EditText) viewInflate.findViewById(R.id.ali_user_mobile_input_box);
        this.mNextButton = (Button) viewInflate.findViewById(R.id.ali_user_mobile_next);
        initParams();
        afterViews();
        return viewInflate;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment
    protected void onSendSMSForRegisterSuccess(Result<CheckCodeResult> result) {
        goCheckCode(this.mAccountExist);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment
    protected void onSendSMSForResetPwdSuccess(Result<Void> result) {
        goCheckCode(this.mAccountExist);
    }

    protected void sendSmsCode(String str) {
        this.mAccountExist = str;
        if (TextUtils.equals(str, RequestConstant.FALSE)) {
            new BaseFragment.SendSmsCodeForRegisterTask(this.mAttachedActivity).execute(new Void[0]);
        } else {
            new BaseFragment.SendSMSForResetPwdTask(this.mAttachedActivity).execute(new Void[0]);
        }
    }
}
