package com.alibaba.sdk.android.openaccount.ui.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.alibaba.sdk.android.openaccount.model.CheckCodeResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.ui.R;
import com.alibaba.sdk.android.openaccount.ui.TokenWebViewActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.SpecialLoginActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.umeng.ccg.a;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;

/* loaded from: classes2.dex */
public class MobileRegisterFragment extends BaseFragment {
    protected EditText mMobileInputBox;
    protected Button mNextButton;
    protected TextView mProtocolTV;
    protected int mScene = 0;

    private void afterViews() {
        Button button = this.mNextButton;
        if (button != null) {
            button.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.fragment.MobileRegisterFragment.1
                @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
                public void afterCheck(View view) {
                    MobileRegisterFragment.this.goNext();
                }
            });
        }
        TextView textView = this.mProtocolTV;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.fragment.MobileRegisterFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Context context = MobileRegisterFragment.this.getContext();
                    Intent intent = new Intent();
                    intent.setClass(context, MobileRegisterFragment.this.getWebViewClass());
                    intent.putExtra("url", MobileRegisterFragment.this.getProtocol());
                    context.startActivity(intent);
                }
            });
        }
        showRegisterTip();
    }

    private void goCheckCode() {
        BaseAppCompatActivity baseAppCompatActivity = this.mAttachedActivity;
        if (baseAppCompatActivity == null || !(baseAppCompatActivity instanceof SpecialLoginActivity) || baseAppCompatActivity.isFinishing()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, getMobile());
        bundle.putInt(a.f22008j, this.mScene);
        ((SpecialLoginActivity) this.mAttachedActivity).jumpToCheckCode(bundle);
    }

    private void initParams() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            try {
                this.mScene = arguments.getInt(a.f22008j);
                Log.e("TAG", "mobile scene = " + this.mScene);
            } catch (Exception unused) {
            }
        }
    }

    protected int getLayout() {
        return R.layout.ali_sdk_openaccount_fragment_mobile_register;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment
    @NonNull
    protected String getMobile() {
        EditText editText = this.mMobileInputBox;
        return editText != null ? editText.getText().toString() : "";
    }

    @NonNull
    protected String getProtocol() {
        return "https://www.taobao.com";
    }

    @NonNull
    protected Class getWebViewClass() {
        return TokenWebViewActivity.class;
    }

    protected void goNext() {
        if (TextUtils.isEmpty(getMobile())) {
            return;
        }
        new BaseFragment.SendSmsCodeForRegisterTask(this.mAttachedActivity).execute(new Void[0]);
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
        this.mProtocolTV = (TextView) viewInflate.findViewById(R.id.ali_user_register_protocol);
        initParams();
        afterViews();
        return viewInflate;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.fragment.BaseFragment
    protected void onSendSMSForRegisterSuccess(Result<CheckCodeResult> result) {
        goCheckCode();
    }

    protected void showRegisterTip() {
    }
}
