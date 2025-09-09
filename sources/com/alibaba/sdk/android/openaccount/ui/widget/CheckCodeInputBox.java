package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.task.AbsAsyncTask;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.util.OpenAccountUIUtils;
import com.alibaba.sdk.android.openaccount.util.HttpHelper;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.alibaba.sdk.android.openaccount.util.WidgetUtils;
import com.alipay.sdk.m.x.d;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class CheckCodeInputBox extends AbsInputBoxWrapper {
    protected ImageView checkCode;
    protected String checkCodeUrl;
    protected Button refresh;

    private class RefreshCheckCodeTask extends AbsAsyncTask<String, Void, Bitmap> {
        private RefreshCheckCodeTask() {
        }

        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        protected void doFinally() {
        }

        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        protected void doWhenException(Throwable th) {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Bitmap asyncExecute(String... strArr) {
            return CheckCodeInputBox.this.getHttpBitmap(strArr[0]);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute((RefreshCheckCodeTask) bitmap);
            if (bitmap != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(CheckCodeInputBox.this.getResources(), bitmap);
                if (CheckCodeInputBox.this.checkCode.getBackground() instanceof BitmapDrawable) {
                    Bitmap bitmap2 = ((BitmapDrawable) CheckCodeInputBox.this.checkCode.getBackground()).getBitmap();
                    if (!bitmap2.isRecycled()) {
                        bitmap2.recycle();
                    }
                }
                WidgetUtils.setBackgroundDrawable(CheckCodeInputBox.this.checkCode, bitmapDrawable);
            }
        }
    }

    public CheckCodeInputBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ResourceUtils.getRStyleableIntArray(context, "inputBox"));
        int rStyleable = ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_inputType");
        if (rStyleable != 0) {
            this.inputBoxWithClear.getEditText().setInputType(rStyleable);
        }
        int i2 = typedArrayObtainStyledAttributes.getInt(ResourceUtils.getRStyleable(context, "inputBox_ali_sdk_openaccount_attrs_input_maxLength"), 0);
        if (i2 > 0) {
            this.inputBoxWithClear.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        }
        typedArrayObtainStyledAttributes.recycle();
        this.checkCode = (ImageView) findViewById("image");
        this.refresh = (Button) findViewById(d.f9880w);
        this.refresh.setTypeface(OpenAccountUIUtils.getDefaultFont());
        this.refresh.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.CheckCodeInputBox.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CheckCodeInputBox checkCodeInputBox = CheckCodeInputBox.this;
                String str = checkCodeInputBox.checkCodeUrl;
                if (str != null) {
                    checkCodeInputBox.refreshCheckCode(str);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getHttpBitmap(String str) throws Throwable {
        InputStream inputStream;
        try {
            inputStream = HttpHelper.get(str);
        } catch (Throwable th) {
            th = th;
            inputStream = null;
        }
        try {
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStream);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "getHttpBitmap error:" + e2.getMessage(), e2);
                }
            }
            return bitmapDecodeStream;
        } catch (Throwable th2) {
            th = th2;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "getHttpBitmap error:" + e3.getMessage(), e3);
                }
            }
            throw th;
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_checkcode_input_box";
    }

    public void refreshCheckCode(String str) {
        this.checkCodeUrl = str;
        new RefreshCheckCodeTask().execute(str);
    }
}
