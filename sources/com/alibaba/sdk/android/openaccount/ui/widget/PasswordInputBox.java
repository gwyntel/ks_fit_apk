package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.alibaba.sdk.android.openaccount.ui.util.OpenAccountUIUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;

/* loaded from: classes2.dex */
public class PasswordInputBox extends AbsInputBoxWrapper {
    private boolean isPassword;

    public PasswordInputBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isPassword = true;
        if (isInEditMode()) {
            return;
        }
        Typeface defaultFont = OpenAccountUIUtils.getDefaultFont();
        final Button button = (Button) findViewById("open_eye");
        button.setTypeface(defaultFont);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ResourceUtils.getRStyleableIntArray(context, "inputBox"));
        this.inputBoxWithClear.getEditText().setInputType(129);
        this.inputBoxWithClear.getEditText().setHint(typedArrayObtainStyledAttributes.getString(ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_hint")));
        int i2 = typedArrayObtainStyledAttributes.getInt(ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_input_maxLength"), 0);
        if (i2 > 0) {
            this.inputBoxWithClear.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        }
        typedArrayObtainStyledAttributes.recycle();
        button.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.PasswordInputBox.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PasswordInputBox.this.isPassword = !r0.isPassword;
                PasswordInputBox passwordInputBox = PasswordInputBox.this;
                passwordInputBox.inputBoxWithClear.setPassword(passwordInputBox.isPassword);
                if (PasswordInputBox.this.isPassword) {
                    button.setText(ResourceUtils.getString(view.getContext(), "ali_sdk_openaccount_dynamic_icon_eye"));
                } else {
                    button.setText(ResourceUtils.getString(view.getContext(), "ali_sdk_openaccount_dynamic_icon_eye_open"));
                }
            }
        });
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_password_input_box";
    }

    public void setUsePasswordMasking(boolean z2) {
        this.isPassword = z2;
        ((Button) findViewById("open_eye")).setText(ResourceUtils.getString(getContext(), z2 ? "ali_sdk_openaccount_dynamic_icon_eye" : "ali_sdk_openaccount_dynamic_icon_eye_open"));
        this.inputBoxWithClear.setPassword(z2);
    }
}
