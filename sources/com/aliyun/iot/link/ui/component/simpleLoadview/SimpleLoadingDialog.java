package com.aliyun.iot.link.ui.component.simpleLoadview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.aliyun.iot.link.ui.component.R;

/* loaded from: classes3.dex */
public class SimpleLoadingDialog extends Dialog {
    private String TAG;
    private Context context;
    private LinkSimpleLoadView mSIloadView;

    public SimpleLoadingDialog(Context context) {
        this(context, 0, 0);
    }

    private void setFixedHeight() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 17;
        attributes.height = getContext().getResources().getDisplayMetrics().heightPixels;
        getWindow().setAttributes(attributes);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        LinkSimpleLoadView linkSimpleLoadView = this.mSIloadView;
        if (linkSimpleLoadView != null) {
            linkSimpleLoadView.hide();
        }
        super.dismiss();
    }

    @Override // android.app.Dialog
    @TargetApi(17)
    public void onBackPressed() {
        Context context = this.context;
        if (context == null) {
            Log.e(this.TAG, "context is null");
            return;
        }
        if (!Activity.class.isInstance(context)) {
            dismiss();
            Log.e(this.TAG, "context is not an activity!");
            return;
        }
        Activity activity = (Activity) this.context;
        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        if (isShowing()) {
            dismiss();
        }
        activity.onBackPressed();
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        setLoadingViewStyle(R.style.SimpleLoadViewStyle);
        setCanceledOnTouchOutside(false);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismiss();
    }

    public void setLoadViewLoacation(float f2) {
        LinkSimpleLoadView linkSimpleLoadView = this.mSIloadView;
        if (linkSimpleLoadView != null) {
            linkSimpleLoadView.setLoadViewLoacation(f2);
        }
    }

    public void setLoadViewRootBgColor(int i2) {
        this.mSIloadView.setLoadViewRootBgColor(i2);
    }

    public void setLoadViewRootBgDrawbleRes(int i2) {
        this.mSIloadView.setLoadViewRootBgDrawbleRes(i2);
    }

    public void setLoadingViewStyle(int i2) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = this.context.obtainStyledAttributes(i2, R.styleable.LinkSimpleLoadView);
        this.mSIloadView.applyStyle(typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setTipViewLoacation(float f2) {
        LinkSimpleLoadView linkSimpleLoadView = this.mSIloadView;
        if (linkSimpleLoadView != null) {
            linkSimpleLoadView.setTipViewLoacation(f2);
        }
    }

    public void setTopbarClickable(View view) {
        if (view == null) {
            return;
        }
        this.mSIloadView.setTopbarClickableArea(view, new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.simpleLoadview.SimpleLoadingDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                SimpleLoadingDialog.this.onBackPressed();
            }
        });
    }

    @Override // android.app.Dialog
    public final void show() {
        if (isShowing()) {
            return;
        }
        super.show();
    }

    public void showError(String str, Drawable drawable) {
        show();
        this.mSIloadView.showError(str, drawable);
    }

    public void showLoading(String str, Drawable drawable) {
        show();
        setFixedHeight();
        this.mSIloadView.showLoading(str, drawable);
    }

    public SimpleLoadingDialog(Context context, int i2, int i3) {
        super(context, R.style.link_loading_fulldialog);
        this.TAG = "SimpleLoadingDialog";
        this.mSIloadView = new LinkSimpleLoadView(context);
        this.context = context;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        if (i2 == 0 || i3 == 0) {
            getWindow().setLayout(-1, -1);
            layoutParams.width = getContext().getResources().getDisplayMetrics().widthPixels;
        } else {
            getWindow().setLayout(i2, i3);
            layoutParams.width = i2;
            layoutParams.height = i3;
        }
        getWindow().setFlags(8, 8);
        getWindow().setGravity(17);
        setContentView(this.mSIloadView, layoutParams);
    }

    public void showError(String str) {
        show();
        this.mSIloadView.showError(str);
    }

    public void showLoading(String str) {
        show();
        setFixedHeight();
        this.mSIloadView.showLoading(str);
    }
}
