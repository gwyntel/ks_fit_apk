package com.alibaba.sdk.android.openaccount.widget;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.alibaba.sdk.android.openaccount.ui.LayoutMapping;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;

/* loaded from: classes2.dex */
public class ProgressDialog extends android.app.ProgressDialog {
    private boolean indeterminate;
    private CharSequence message;
    private ProgressBar progressBar;
    private boolean progressVisiable;
    private TextView textView;

    public ProgressDialog(Context context) {
        super(context);
    }

    private void setMessageAndView() {
        this.textView.setText(this.message);
        CharSequence charSequence = this.message;
        if (charSequence == null || "".equals(charSequence)) {
            this.textView.setVisibility(8);
        }
        this.progressBar.setVisibility(this.progressVisiable ? 0 : 8);
    }

    @Override // android.app.ProgressDialog, android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Integer num = LayoutMapping.get(ProgressDialog.class);
        if (num != null) {
            setContentView(num.intValue());
        } else {
            setContentView(ResourceUtils.getIdentifier(TtmlNode.TAG_LAYOUT, "ali_sdk_openaccount_progress_dialog"));
        }
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        this.progressBar = (ProgressBar) findViewById(R.id.progress);
        this.textView = (TextView) findViewById(ResourceUtils.getIdentifier("id", "ali_sdk_openaccount_progress_dialog_message"));
        setMessageAndView();
        setIndeterminate(this.indeterminate);
    }

    @Override // android.app.ProgressDialog
    public void setIndeterminate(boolean z2) {
        ProgressBar progressBar = this.progressBar;
        if (progressBar != null) {
            progressBar.setIndeterminate(z2);
        } else {
            this.indeterminate = z2;
        }
    }

    @Override // android.app.ProgressDialog, android.app.AlertDialog
    public void setMessage(CharSequence charSequence) {
        this.message = charSequence;
    }

    public void setProgressVisiable(boolean z2) {
        this.progressVisiable = z2;
    }

    public ProgressDialog(Context context, int i2) {
        super(context, i2);
    }
}
