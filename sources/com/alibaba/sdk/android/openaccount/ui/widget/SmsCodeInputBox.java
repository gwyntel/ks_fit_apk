package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.alibaba.sdk.android.openaccount.ui.R;
import com.alibaba.sdk.android.openaccount.ui.util.AttributeUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public class SmsCodeInputBox extends AbsInputBoxWrapper {
    protected Handler handler;
    protected String mSendText;
    protected Button send;
    protected View.OnClickListener sendClickListener;

    public SmsCodeInputBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.handler = new Handler(Looper.getMainLooper());
        this.mSendText = "ali_sdk_openaccount_text_send_sms_code";
        this.send = (Button) findViewById("send");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ResourceUtils.getRStyleableIntArray(context, "inputBox"));
        int rStyleable = ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_inputType");
        if (rStyleable != 0) {
            this.inputBoxWithClear.getEditText().setInputType(rStyleable);
        }
        this.inputBoxWithClear.getEditText().setTextColor(getResources().getColor(R.color.ali_user_color_primary_dark));
        this.inputBoxWithClear.getEditText().setHint(typedArrayObtainStyledAttributes.getString(ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_hint")));
        int i2 = typedArrayObtainStyledAttributes.getInt(ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_input_maxLength"), 0);
        if (i2 > 0) {
            this.inputBoxWithClear.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        }
        typedArrayObtainStyledAttributes.recycle();
        useCustomAttrs(context, attributeSet);
        this.send.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.SmsCodeInputBox.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SmsCodeInputBox smsCodeInputBox = SmsCodeInputBox.this;
                View.OnClickListener onClickListener = smsCodeInputBox.sendClickListener;
                if (onClickListener != null) {
                    onClickListener.onClick(smsCodeInputBox);
                }
            }
        });
    }

    public void addSendClickListener(View.OnClickListener onClickListener) {
        this.sendClickListener = onClickListener;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected void doUseCustomAttrs(Context context, TypedArray typedArray) {
        super.doUseCustomAttrs(context, typedArray);
        if (isInEditMode()) {
            return;
        }
        try {
            ColorStateList colorStateList = AttributeUtils.getColorStateList(context, typedArray, "ali_sdk_openaccount_attrs_send_sms_code_color");
            if (colorStateList != null) {
                this.send.setTextColor(colorStateList);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_sms_code_input_box";
    }

    public Button getSend() {
        return this.send;
    }

    public String getSendText() {
        return this.mSendText;
    }

    public void setSendText(String str) {
        this.mSendText = str;
    }

    public void startTimer(final Context context) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.SmsCodeInputBox.2
            private int counter = 60;

            static /* synthetic */ int access$010(AnonymousClass2 anonymousClass2) {
                int i2 = anonymousClass2.counter;
                anonymousClass2.counter = i2 - 1;
                return i2;
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                SmsCodeInputBox.this.handler.post(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.SmsCodeInputBox.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (AnonymousClass2.this.counter > 0) {
                            AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                            SmsCodeInputBox.this.send.setText(String.format(ResourceUtils.getString(context, "ali_sdk_openaccount_text_count_down"), Integer.valueOf(AnonymousClass2.this.counter)));
                        } else {
                            AnonymousClass2 anonymousClass22 = AnonymousClass2.this;
                            SmsCodeInputBox smsCodeInputBox = SmsCodeInputBox.this;
                            smsCodeInputBox.send.setText(ResourceUtils.getString(context, smsCodeInputBox.getSendText()));
                            SmsCodeInputBox.this.send.setEnabled(true);
                            timer.cancel();
                        }
                        AnonymousClass2.access$010(AnonymousClass2.this);
                    }
                });
            }
        }, 0L, 1000L);
        this.send.setEnabled(false);
    }
}
