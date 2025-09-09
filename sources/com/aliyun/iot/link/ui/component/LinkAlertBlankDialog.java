package com.aliyun.iot.link.ui.component;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/* loaded from: classes3.dex */
public class LinkAlertBlankDialog {
    Context mCtx;
    AlertDialog mDialog;
    int mDialogBackGround;
    int mDialogType;

    public static class Builder {
        int dialogBackGround;
        int dialogType;
        Context mContext;
        View view;
        boolean mCanceledOnTouchOutside = true;
        boolean mCancelable = true;

        public Builder(Context context) {
            this.mContext = context;
        }

        public LinkAlertBlankDialog create() {
            return new LinkAlertBlankDialog(this);
        }

        public Builder setCancelable(boolean z2) {
            this.mCancelable = z2;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean z2) {
            this.mCanceledOnTouchOutside = z2;
            return this;
        }

        public Builder setDialogBackGround(int i2) {
            this.dialogBackGround = i2;
            return this;
        }

        public Builder setDialogType(int i2) {
            this.dialogType = i2;
            return this;
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }
    }

    public void dismiss() {
        this.mDialog.dismiss();
    }

    public void show(int i2) {
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setBackgroundDrawable(this.mCtx.getResources().getDrawable(this.mDialogBackGround));
        WindowManager.LayoutParams attributes = window.getAttributes();
        int i3 = this.mCtx.getResources().getDisplayMetrics().widthPixels;
        if (this.mDialogType == 80) {
            attributes.gravity = 80;
        } else {
            attributes.gravity = 16;
        }
        if (i2 < 0) {
            attributes.width = (int) (i3 * 0.72d);
        } else {
            attributes.width = i3 - i2;
        }
        window.setAttributes(attributes);
    }

    private LinkAlertBlankDialog(Builder builder) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(builder.mContext).create();
        this.mDialog = alertDialogCreate;
        this.mCtx = builder.mContext;
        View view = builder.view;
        if (view != null) {
            alertDialogCreate.setView(view);
        }
        if (builder.dialogBackGround == 0) {
            this.mDialogBackGround = R.drawable.alert_dialog_bg;
        }
        this.mDialogType = builder.dialogType;
        this.mDialog.setCancelable(builder.mCancelable);
        this.mDialog.setCanceledOnTouchOutside(builder.mCanceledOnTouchOutside);
        this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        if (this.mDialogType == 80) {
            this.mDialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
            this.mDialog.getWindow().setGravity(80);
        }
    }
}
