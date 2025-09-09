package com.alibaba.sdk.android.openaccount.ui.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import anetwork.channel.util.RequestConstant;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.ui.R;
import com.alibaba.sdk.android.openaccount.ui.TokenWebViewActivity;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage;
import com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.SpecialLoginActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.umeng.ccg.a;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ConfirmRegisterFragment extends BaseFragment implements View.OnClickListener {
    protected String mAccountExist;
    protected String mAccountHasPassword;
    protected Button mCancelButton;
    protected Button mConfirmButton;
    protected String mMobile;
    protected TextView mMobileTextView;
    protected TextView mMobileTextView2;
    protected TextView mProtocolView;
    protected String mSMSTrustToken;
    protected int mScene;

    protected class RegisterTask extends TaskWithToastMessage<LoginResult> {
        public RegisterTask(Activity activity) {
            super(activity);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doFailAfterToast(Result<LoginResult> result) {
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doSuccessAfterToast(Result<LoginResult> result) {
            SessionData sessionDataCreateSessionDataFromLoginSuccessResult = OpenAccountUtils.createSessionDataFromLoginSuccessResult(result.data.loginSuccessResult);
            if (sessionDataCreateSessionDataFromLoginSuccessResult.scenario == null) {
                sessionDataCreateSessionDataFromLoginSuccessResult.scenario = 1;
            }
            ((SessionManagerService) OpenAccountSDK.getService(SessionManagerService.class)).updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
            ConfirmRegisterFragment.this.successCallback();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<LoginResult> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            map.put("token", ConfirmRegisterFragment.this.mSMSTrustToken);
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("registerRequest", map, "register"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        public LoginResult parseData(JSONObject jSONObject) {
            return OpenAccountUtils.parseLoginResult(jSONObject);
        }
    }

    private void afterViews() {
        if (!TextUtils.isEmpty(this.mMobile) && this.mMobileTextView != null) {
            if (TextUtils.equals(this.mAccountExist, RequestConstant.FALSE)) {
                this.mMobileTextView.setText(this.mAttachedActivity.getString(R.string.ali_sdk_openaccount_text_not_register, this.mMobile));
                this.mMobileTextView2.setText(R.string.ali_sdk_openaccount_text_whether_to_register);
                this.mConfirmButton.setText(R.string.ali_sdk_openaccount_text_confirm_register);
                this.mCancelButton.setText(R.string.ali_sdk_openaccount_text_exit_register);
                String string = this.mAttachedActivity.getString(R.string.ali_sdk_openaccount_text_protocol);
                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new ClickableSpan() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.fragment.ConfirmRegisterFragment.1
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        if (view == null || view.getContext() == null) {
                            return;
                        }
                        Context context = view.getContext();
                        Intent intent = new Intent();
                        intent.setClass(context, ConfirmRegisterFragment.this.getWebViewClass());
                        intent.putExtra("url", ConfirmRegisterFragment.this.getProtocol());
                        context.startActivity(intent);
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint textPaint) {
                        super.updateDrawState(textPaint);
                        try {
                            textPaint.setColor(ConfirmRegisterFragment.this.getResources().getColor(R.color.ali_sdk_openaccount_button_bg));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        textPaint.setUnderlineText(false);
                    }
                }, 0, string.length(), 33);
                TextView textView = this.mProtocolView;
                if (textView != null) {
                    textView.setText(spannableString);
                    this.mProtocolView.setMovementMethod(LinkMovementMethod.getInstance());
                }
            } else {
                this.mMobileTextView.setText(this.mAttachedActivity.getString(R.string.ali_sdk_openaccount_text_already_register, this.mMobile));
                this.mMobileTextView2.setText(R.string.ali_sdk_openaccount_text_whether_to_login);
                this.mConfirmButton.setText(R.string.ali_sdk_openaccount_text_confirm_login);
                this.mCancelButton.setText(R.string.ali_sdk_openaccount_text_exit);
            }
        }
        Button button = this.mConfirmButton;
        if (button != null) {
            button.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.fragment.ConfirmRegisterFragment.2
                @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
                public void afterCheck(View view) {
                    ConfirmRegisterFragment.this.toRegister();
                }
            });
        }
        Button button2 = this.mCancelButton;
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
    }

    private void initParams() {
        try {
            this.mMobile = getArguments().getString(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE);
            this.mSMSTrustToken = getArguments().getString("trustToken");
            this.mScene = getArguments().getInt(a.f22008j, 0);
            this.mAccountHasPassword = getArguments().getString("accountHasPassword");
            this.mAccountExist = getArguments().getString("accountExist");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    protected void beforeJumpToPwd(BaseFragment.BeforeJumpToPwdCallback beforeJumpToPwdCallback) {
        if (beforeJumpToPwdCallback != null) {
            beforeJumpToPwdCallback.onFinish();
        }
    }

    protected void finishActivity() {
        BaseAppCompatActivity baseAppCompatActivity = this.mAttachedActivity;
        if (baseAppCompatActivity == null || !(baseAppCompatActivity instanceof SpecialLoginActivity)) {
            return;
        }
        ((SpecialLoginActivity) baseAppCompatActivity).finishCurrentActivity();
    }

    protected int getLayout() {
        return R.layout.ali_sdk_openaccount_fragment_register_confirm;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment
    protected LoginCallback getLoginCallback() {
        LoginCallback loginCallback = OpenAccountUIServiceImpl._specialLoginCallback;
        if (loginCallback != null) {
            return loginCallback;
        }
        return null;
    }

    @NonNull
    protected String getProtocol() {
        return "https://www.taobao.com";
    }

    @NonNull
    protected Class getWebViewClass() {
        return TokenWebViewActivity.class;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.ali_user_register_confirm_cancel) {
            finishActivity();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        BaseAppCompatActivity baseAppCompatActivity = this.mAttachedActivity;
        if (baseAppCompatActivity != null && baseAppCompatActivity.getSupportActionBar() != null) {
            this.mAttachedActivity.getSupportActionBar().setTitle(R.string.ali_sdk_openaccount_text_register_confirm);
        }
        View viewInflate = layoutInflater.inflate(getLayout(), viewGroup, false);
        this.mProtocolView = (TextView) viewInflate.findViewById(R.id.ali_user_register_confirm_protocol);
        if (TextUtils.equals(this.mAccountExist, "true")) {
            this.mProtocolView.setVisibility(8);
        }
        this.mMobileTextView = (TextView) viewInflate.findViewById(R.id.ali_user_register_confirm_mobile_textview);
        this.mMobileTextView2 = (TextView) viewInflate.findViewById(R.id.ali_user_confirm_textview);
        this.mConfirmButton = (Button) viewInflate.findViewById(R.id.ali_user_register_confirm_next);
        this.mCancelButton = (Button) viewInflate.findViewById(R.id.ali_user_register_confirm_cancel);
        initParams();
        afterViews();
        return viewInflate;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment
    protected void successCallback() {
        ((ExecutorService) OpenAccountSDK.getService(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.fragment.ConfirmRegisterFragment.5
            @Override // java.lang.Runnable
            public void run() {
                LoginCallback loginCallback = ConfirmRegisterFragment.this.getLoginCallback();
                if (loginCallback != null) {
                    loginCallback.onSuccess(((SessionManagerService) OpenAccountSDK.getService(SessionManagerService.class)).getSession());
                    BaseAppCompatActivity baseAppCompatActivity = ConfirmRegisterFragment.this.mAttachedActivity;
                    if (baseAppCompatActivity != null) {
                        baseAppCompatActivity.finish();
                    }
                }
            }
        });
    }

    protected void toRegister() {
        if (TextUtils.equals(this.mAccountExist, RequestConstant.FALSE)) {
            beforeJumpToPwd(new BaseFragment.BeforeJumpToPwdCallback() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.fragment.ConfirmRegisterFragment.3
                @Override // com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment.BeforeJumpToPwdCallback
                public void onFinish() {
                    BaseAppCompatActivity baseAppCompatActivity = ConfirmRegisterFragment.this.mAttachedActivity;
                    if (baseAppCompatActivity == null || !(baseAppCompatActivity instanceof SpecialLoginActivity) || baseAppCompatActivity.isFinishing()) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("token", ConfirmRegisterFragment.this.mSMSTrustToken);
                    bundle.putInt(a.f22008j, 2);
                    ((SpecialLoginActivity) ConfirmRegisterFragment.this.mAttachedActivity).goFindPwd(bundle);
                }
            });
            return;
        }
        if (!TextUtils.equals(this.mAccountHasPassword, "true")) {
            if (TextUtils.equals(this.mAccountHasPassword, RequestConstant.FALSE)) {
                beforeJumpToPwd(new BaseFragment.BeforeJumpToPwdCallback() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.fragment.ConfirmRegisterFragment.4
                    @Override // com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment.BeforeJumpToPwdCallback
                    public void onFinish() {
                        BaseAppCompatActivity baseAppCompatActivity = ConfirmRegisterFragment.this.mAttachedActivity;
                        if (baseAppCompatActivity == null || !(baseAppCompatActivity instanceof SpecialLoginActivity) || baseAppCompatActivity.isFinishing()) {
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("token", ConfirmRegisterFragment.this.mSMSTrustToken);
                        bundle.putInt(a.f22008j, ConfirmRegisterFragment.this.mScene);
                        ((SpecialLoginActivity) ConfirmRegisterFragment.this.mAttachedActivity).goFindPwd(bundle);
                    }
                });
                return;
            }
            return;
        }
        BaseAppCompatActivity baseAppCompatActivity = this.mAttachedActivity;
        if (baseAppCompatActivity == null || !(baseAppCompatActivity instanceof SpecialLoginActivity) || baseAppCompatActivity.isFinishing()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, this.mMobile);
        bundle.putString("trustToken", this.mSMSTrustToken);
        bundle.putInt(a.f22008j, this.mScene);
        ((SpecialLoginActivity) this.mAttachedActivity).goPwd(bundle);
    }
}
