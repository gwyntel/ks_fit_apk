package com.alibaba.sdk.android.openaccount.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.health.connect.client.records.CervicalMucusRecord;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.util.AttributeUtils;
import com.alibaba.sdk.android.openaccount.ui.util.OpenAccountUIUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;

/* loaded from: classes2.dex */
public class InputBoxWithClear extends LinearLayoutTemplate {
    protected TextView chosedCountryNum;
    protected TextView chosedCountryNumSub;
    protected TextView clear;
    protected ImageView countryChooseButton;
    protected EditText input;
    protected TextView leftIcon;
    protected TextWatcher textWatcher;

    public InputBoxWithClear(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (isInEditMode()) {
            return;
        }
        this.input = (EditText) findViewById("input");
        this.leftIcon = (TextView) findViewById("left_icon");
        this.clear = (TextView) findViewById(CervicalMucusRecord.Appearance.CLEAR);
        Typeface defaultFont = OpenAccountUIUtils.getDefaultFont();
        this.clear.setTypeface(defaultFont);
        this.clear.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithClear.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputBoxWithClear.this.input.setText((CharSequence) null);
                InputBoxWithClear.this.clear.setVisibility(8);
            }
        });
        this.input.addTextChangedListener(new TextWatcher() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithClear.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    InputBoxWithClear.this.clear.setVisibility(8);
                } else {
                    InputBoxWithClear.this.clear.setVisibility(0);
                }
                TextWatcher textWatcher = InputBoxWithClear.this.textWatcher;
                if (textWatcher != null) {
                    textWatcher.afterTextChanged(editable);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                TextWatcher textWatcher = InputBoxWithClear.this.textWatcher;
                if (textWatcher != null) {
                    textWatcher.beforeTextChanged(charSequence, i2, i3, i4);
                }
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                TextWatcher textWatcher = InputBoxWithClear.this.textWatcher;
                if (textWatcher != null) {
                    textWatcher.onTextChanged(charSequence, i2, i3, i4);
                }
            }
        });
        this.clear.setVisibility(8);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ResourceUtils.getRStyleableIntArray(context, "inputBox"));
        if (this.leftIcon != null) {
            this.leftIcon.setText(typedArrayObtainStyledAttributes.getString(ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_leftIconText")));
            this.leftIcon.setTypeface(defaultFont);
        }
        int i2 = typedArrayObtainStyledAttributes.getInt(ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_inputType"), 0);
        if (i2 != 0) {
            this.input.setInputType(i2);
        }
        this.input.setHint(typedArrayObtainStyledAttributes.getString(ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_hint")));
        int i3 = typedArrayObtainStyledAttributes.getInt(ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_input_maxLength"), 0);
        if (i3 > 0) {
            this.input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i3)});
        }
        typedArrayObtainStyledAttributes.recycle();
        this.chosedCountryNum = (TextView) findViewById("edt_chosed_country_num");
        this.chosedCountryNumSub = (TextView) findViewById("edt_chosed_country_num_sub");
        this.countryChooseButton = (ImageView) findViewById("country_choose_btn");
        useCustomAttrs(context, attributeSet);
    }

    private void ajustInputSelection() {
        EditText editText = this.input;
        editText.setSelection(editText.getText().length());
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected void doUseCustomAttrs(Context context, TypedArray typedArray) {
        this.clear.setTextColor(AttributeUtils.getColor(context, typedArray, "ali_sdk_openaccount_attrs_input_box_clear_btn_color"));
    }

    public TextView getClearTextView() {
        return this.clear;
    }

    public EditText getEditText() {
        return this.input;
    }

    public String getEditTextContent() {
        if (this.input.getText() != null) {
            return this.input.getText().toString();
        }
        return null;
    }

    public String getInputHint() {
        if (this.input.getHint() == null) {
            return null;
        }
        return this.input.getHint().toString();
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_input_box";
    }

    public TextView getLeftIcon() {
        return this.leftIcon;
    }

    public String getMobileLocationCode() {
        TextView textView = this.chosedCountryNum;
        if (textView == null || textView.getVisibility() != 0 || this.chosedCountryNum.getText() == null) {
            return null;
        }
        return this.chosedCountryNum.getText().toString();
    }

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

    public void setInputHint(String str) {
        this.input.setHint(str);
    }

    public void setInputType(int i2) {
        this.input.setInputType(i2);
    }

    public void setPassword(boolean z2) {
        if (z2) {
            this.input.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ajustInputSelection();
        } else {
            this.input.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ajustInputSelection();
        }
    }

    public void setSupportForeignMobile(final Activity activity, final Class<? extends Activity> cls, boolean z2) {
        if (!z2) {
            setViewVisibility(8, this.chosedCountryNum, this.chosedCountryNumSub, this.countryChooseButton);
            setViewVisibility(0, this.leftIcon);
        } else {
            setViewVisibility(0, this.chosedCountryNum, this.chosedCountryNumSub, this.countryChooseButton);
            setViewVisibility(8, this.leftIcon);
            setViewOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithClear.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(activity.getBaseContext(), cls);
                    activity.startActivityForResult(intent, RequestCode.MOBILE_COUNTRY_SELECTOR_REQUEST);
                }
            }, this.chosedCountryNum, this.chosedCountryNumSub, this.countryChooseButton);
        }
    }
}
