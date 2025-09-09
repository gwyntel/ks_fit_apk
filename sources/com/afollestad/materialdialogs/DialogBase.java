package com.afollestad.materialdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.afollestad.materialdialogs.internal.MDRootLayout;

/* loaded from: classes2.dex */
class DialogBase extends Dialog implements DialogInterface.OnShowListener {

    /* renamed from: a, reason: collision with root package name */
    protected MDRootLayout f8163a;
    private DialogInterface.OnShowListener showListener;

    DialogBase(Context context, int i2) {
        super(context, i2);
    }

    final void a() {
        super.setOnShowListener(this);
    }

    final void b(View view) {
        super.setContentView(view);
    }

    @Override // android.app.Dialog
    public View findViewById(int i2) {
        return this.f8163a.findViewById(i2);
    }

    @Override // android.content.DialogInterface.OnShowListener
    public void onShow(DialogInterface dialogInterface) {
        DialogInterface.OnShowListener onShowListener = this.showListener;
        if (onShowListener != null) {
            onShowListener.onShow(dialogInterface);
        }
    }

    @Override // android.app.Dialog
    @Deprecated
    public void setContentView(int i2) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Override // android.app.Dialog
    public final void setOnShowListener(DialogInterface.OnShowListener onShowListener) {
        this.showListener = onShowListener;
    }

    @Override // android.app.Dialog
    @Deprecated
    public void setContentView(@NonNull View view) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Override // android.app.Dialog
    @Deprecated
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams layoutParams) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }
}
