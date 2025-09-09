package com.aliyun.iot.link.ui.component.progress_dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.aliyun.iot.link.ui.component.R;
import com.aliyun.iot.link.ui.component.progress_dialog.BGAProgressBar;

/* loaded from: classes3.dex */
public class BGAProgressLoadingDialog extends Dialog {
    BGAProgressBar bgaProgressBar;

    public BGAProgressLoadingDialog(@NonNull Context context) {
        this(context, 0);
    }

    private void init() {
        Window window = getWindow();
        window.requestFeature(1);
        setContentView(R.layout.dialog_loading);
        int i2 = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.35f);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = i2;
        attributes.height = i2;
        attributes.gravity = 17;
        attributes.dimAmount = 0.0f;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(attributes);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        this.bgaProgressBar = (BGAProgressBar) findViewById(R.id.bga_progress_bar);
    }

    public void isCapRounded(boolean z2) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.isCapRounded(z2);
        }
    }

    public void setBgaProgressBarProgress(int i2) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setProgress(i2);
        }
    }

    public void setMode(BGAProgressBar.Mode mode) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setMode(mode);
        }
    }

    public void setReachedColor(String str) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setReachedColor(str);
        }
    }

    public void setReachedWidth(int i2) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setReachedWidth(i2);
        }
    }

    public void setStartAngle(int i2) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setStartAngle(i2);
        }
    }

    public void setTextColor(String str) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setTextColor(str);
        }
    }

    public void setTextMargin(int i2) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setTextMargin(i2);
        }
    }

    public void setTextSize(int i2) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setTextSize(i2);
        }
    }

    public void setUnReachedColor(String str) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setUnReachedColor(str);
        }
    }

    public void setUnReachedWidth(int i2) {
        BGAProgressBar bGAProgressBar = this.bgaProgressBar;
        if (bGAProgressBar != null) {
            bGAProgressBar.setUnReachedWidth(i2);
        }
    }

    public BGAProgressLoadingDialog(@NonNull Context context, int i2) {
        super(context, i2);
        this.bgaProgressBar = null;
        init();
    }
}
