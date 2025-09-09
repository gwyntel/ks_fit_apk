package com.facebook.share.widget;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;

/* loaded from: classes3.dex */
public abstract class ShareButtonBase extends FacebookButtonBase {
    private boolean enabledExplicitlySet;
    private int requestCode;
    private ShareContent shareContent;

    protected ShareButtonBase(Context context, AttributeSet attributeSet, int i2, String str, String str2) {
        super(context, attributeSet, i2, 0, str, str2);
        this.requestCode = 0;
        this.enabledExplicitlySet = false;
        this.requestCode = isInEditMode() ? 0 : getDefaultRequestCode();
        internalSetEnabled(false);
    }

    private void internalSetEnabled(boolean z2) {
        setEnabled(z2);
        this.enabledExplicitlySet = false;
    }

    protected boolean canShare() {
        return getDialog().canShow(getShareContent());
    }

    @Override // com.facebook.FacebookButtonBase
    protected void configureButton(Context context, AttributeSet attributeSet, int i2, int i3) {
        super.configureButton(context, attributeSet, i2, i3);
        setInternalOnClickListener(getShareOnClickListener());
    }

    protected abstract FacebookDialogBase<ShareContent, Sharer.Result> getDialog();

    @Override // com.facebook.FacebookButtonBase
    public int getRequestCode() {
        return this.requestCode;
    }

    public ShareContent getShareContent() {
        return this.shareContent;
    }

    protected View.OnClickListener getShareOnClickListener() {
        return new View.OnClickListener() { // from class: com.facebook.share.widget.ShareButtonBase.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws PackageManager.NameNotFoundException {
                ShareButtonBase.this.callExternalOnClickListener(view);
                ShareButtonBase.this.getDialog().show(ShareButtonBase.this.getShareContent());
            }
        };
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<Sharer.Result> facebookCallback) {
        ShareInternalUtility.registerSharerCallback(getRequestCode(), callbackManager, facebookCallback);
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        this.enabledExplicitlySet = true;
    }

    protected void setRequestCode(int i2) {
        if (!FacebookSdk.isFacebookRequestCode(i2)) {
            this.requestCode = i2;
            return;
        }
        throw new IllegalArgumentException("Request code " + i2 + " cannot be within the range reserved by the Facebook SDK.");
    }

    public void setShareContent(ShareContent shareContent) {
        this.shareContent = shareContent;
        if (this.enabledExplicitlySet) {
            return;
        }
        internalSetEnabled(canShare());
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<Sharer.Result> facebookCallback, int i2) {
        setRequestCode(i2);
        registerCallback(callbackManager, facebookCallback);
    }
}
