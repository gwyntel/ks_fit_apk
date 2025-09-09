package com.alibaba.sdk.android.openaccount.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import com.ali.user.mobile.filter.PreHandlerCallback;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIService;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.callback.OpenAccountExistCallback;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountTaobaoUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.model.CaptchaModel;
import com.alibaba.sdk.android.openaccount.ui.task.CheckOpenAccountExistTask;
import com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithClear;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.ui.widget.NextStepButtonWatcher;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ResetOATaoPasswordActivity extends NextStepActivityTemplate {
    protected InputBoxWithClear inputBoxWithClear;

    private void callbackFailToTao() {
        callbackFailToTao(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkOAExist(CaptchaModel captchaModel) {
        final String string = this.inputBoxWithClear.getEditText().getText().toString();
        if (!TextUtils.isEmpty(string) && string.length() == 11 && OpenAccountUtils.isNumeric(string)) {
            new CheckOpenAccountExistTask(this, false, this.inputBoxWithClear.getEditText().getText().toString(), captchaModel, new OpenAccountExistCallback() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.ResetOATaoPasswordActivity.2
                @Override // com.alibaba.sdk.android.openaccount.ui.callback.OpenAccountExistCallback
                public void onFail(Map<String, String> map) {
                    if (map == null || TextUtils.isEmpty(map.get("havanaid"))) {
                        return;
                    }
                    ResetOATaoPasswordActivity.this.callbackFailToTao(map.get("havanaid"));
                }

                @Override // com.alibaba.sdk.android.openaccount.ui.callback.OpenAccountExistCallback
                public void onSuccess() {
                    HashMap map = new HashMap();
                    map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, string);
                    ((OpenAccountUIService) OpenAccountSDK.getService(OpenAccountUIService.class)).showResetPassword(ResetOATaoPasswordActivity.this, map, OpenAccountUIConfigs.UnifyLoginFlow.resetPasswordActivityClass, OpenAccountTaobaoUIServiceImpl._resetOATaoPasswordCallback);
                }
            }).execute(new Void[0]);
        } else {
            callbackFailToTao();
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_reset_oa_tao_password";
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i2 != RequestCode.NO_CAPTCHA_REQUEST_CODE) {
            callbackFailToTao();
            return;
        }
        if (i3 != -1) {
            if (i3 == 10003) {
                return;
            }
            callbackFailToTao();
        } else {
            if (intent == null || !"nocaptcha".equals(intent.getStringExtra("action"))) {
                callbackFailToTao();
                return;
            }
            checkOAExist(new CaptchaModel(intent.getStringExtra("sig"), intent.getStringExtra("cSessionId"), intent.getStringExtra("nocToken")));
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.NextStepActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.inputBoxWithClear = (InputBoxWithClear) findViewById("input_box");
        NextStepButtonWatcher nextStepButtonWatcher = getNextStepButtonWatcher();
        nextStepButtonWatcher.addEditText(this.inputBoxWithClear.getEditText());
        this.inputBoxWithClear.addTextChangedListener(nextStepButtonWatcher);
        Button button = this.next;
        if (button != null) {
            nextStepButtonWatcher.setNextStepButton(button);
            this.next.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.ResetOATaoPasswordActivity.1
                @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
                public void afterCheck(View view) {
                    ResetOATaoPasswordActivity.this.checkOAExist(null);
                }
            });
        }
        try {
            String stringExtra = getIntent().getStringExtra("username");
            if (TextUtils.isEmpty(stringExtra)) {
                return;
            }
            this.inputBoxWithClear.getEditText().setText(stringExtra);
            this.next.setEnabled(true);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackFailToTao(String str) {
        WeakReference<PreHandlerCallback> weakReference = OpenAccountTaobaoUIServiceImpl._preHandlerCallback;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        String string = this.inputBoxWithClear.getEditText().getText().toString();
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(string)) {
            map.put("username", string);
        }
        if (!TextUtils.isEmpty(str)) {
            map.put("havanaid", str);
        }
        OpenAccountTaobaoUIServiceImpl._preHandlerCallback.get().onFail(1, map);
    }
}
