package com.alibaba.sdk.android.openaccount.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;

/* loaded from: classes2.dex */
public class MobileInputBoxWithClear extends InputBoxWithClear {
    protected TextView chosedCountryNum;
    protected TextView chosedCountryNumSub;
    protected ImageView countryChooseButton;

    public MobileInputBoxWithClear(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (isInEditMode()) {
            return;
        }
        this.chosedCountryNum = (TextView) findViewById("edt_chosed_country_num");
        this.chosedCountryNumSub = (TextView) findViewById("edt_chosed_country_num_sub");
        this.countryChooseButton = (ImageView) findViewById("country_choose_btn");
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithClear, com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_mobile_input_box";
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithClear
    public String getMobileLocationCode() {
        TextView textView = this.chosedCountryNum;
        if (textView == null || textView.getText() == null) {
            return null;
        }
        return this.chosedCountryNum.getText().toString();
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithClear
    public boolean onActivityResult(int i2, int i3, Intent intent) {
        if (i2 != RequestCode.MOBILE_COUNTRY_SELECTOR_REQUEST) {
            return false;
        }
        if (this.chosedCountryNum == null || intent == null || intent.getStringExtra(MLApplicationSetting.BundleKeyConstants.AppInfo.COUNTRY_CODE) == null) {
            return true;
        }
        this.chosedCountryNum.setText(intent.getStringExtra(MLApplicationSetting.BundleKeyConstants.AppInfo.COUNTRY_CODE));
        return true;
    }

    public void setMobileLocationCode(String str) {
        TextView textView = this.chosedCountryNum;
        if (textView != null) {
            textView.setText(str);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithClear
    public void setSupportForeignMobile(final Activity activity, final Class<? extends Activity> cls, boolean z2) {
        if (!z2) {
            setViewVisibility(8, this.chosedCountryNum, this.chosedCountryNumSub, this.countryChooseButton);
            setViewVisibility(0, this.leftIcon);
        } else {
            setViewVisibility(0, this.chosedCountryNum, this.chosedCountryNumSub, this.countryChooseButton);
            setViewVisibility(8, this.leftIcon);
            setViewOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.MobileInputBoxWithClear.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(MobileInputBoxWithClear.this.getContext(), cls);
                    activity.startActivityForResult(intent, RequestCode.MOBILE_COUNTRY_SELECTOR_REQUEST);
                }
            }, this.chosedCountryNum, this.chosedCountryNumSub, this.countryChooseButton);
        }
    }
}
