package com.facebook.share.widget;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.share.DeviceShareDialog;
import com.facebook.share.R;
import com.facebook.share.model.ShareContent;

/* loaded from: classes3.dex */
public final class DeviceShareButton extends FacebookButtonBase {
    private DeviceShareDialog dialog;
    private boolean enabledExplicitlySet;
    private int requestCode;
    private ShareContent shareContent;

    public DeviceShareButton(Context context) {
        this(context, null, 0);
    }

    private boolean canShare() {
        return new DeviceShareDialog(getActivity()).canShow(getShareContent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DeviceShareDialog getDialog() {
        DeviceShareDialog deviceShareDialog = this.dialog;
        if (deviceShareDialog != null) {
            return deviceShareDialog;
        }
        if (getFragment() != null) {
            this.dialog = new DeviceShareDialog(getFragment());
        } else if (getNativeFragment() != null) {
            this.dialog = new DeviceShareDialog(getNativeFragment());
        } else {
            this.dialog = new DeviceShareDialog(getActivity());
        }
        return this.dialog;
    }

    private void internalSetEnabled(boolean z2) {
        setEnabled(z2);
        this.enabledExplicitlySet = false;
    }

    private void setRequestCode(int i2) {
        if (!FacebookSdk.isFacebookRequestCode(i2)) {
            this.requestCode = i2;
            return;
        }
        throw new IllegalArgumentException("Request code " + i2 + " cannot be within the range reserved by the Facebook SDK.");
    }

    @Override // com.facebook.FacebookButtonBase
    protected void configureButton(Context context, AttributeSet attributeSet, int i2, int i3) {
        super.configureButton(context, attributeSet, i2, i3);
        setInternalOnClickListener(getShareOnClickListener());
    }

    @Override // com.facebook.FacebookButtonBase
    protected int getDefaultRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode();
    }

    @Override // com.facebook.FacebookButtonBase
    protected int getDefaultStyleResource() {
        return R.style.com_facebook_button_share;
    }

    @Override // com.facebook.FacebookButtonBase
    public int getRequestCode() {
        return this.requestCode;
    }

    public ShareContent getShareContent() {
        return this.shareContent;
    }

    protected View.OnClickListener getShareOnClickListener() {
        return new View.OnClickListener() { // from class: com.facebook.share.widget.DeviceShareButton.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws PackageManager.NameNotFoundException {
                DeviceShareButton.this.callExternalOnClickListener(view);
                DeviceShareButton.this.getDialog().show(DeviceShareButton.this.getShareContent());
            }
        };
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<DeviceShareDialog.Result> facebookCallback) {
        getDialog().registerCallback(callbackManager, facebookCallback);
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        this.enabledExplicitlySet = true;
    }

    public void setShareContent(ShareContent shareContent) {
        this.shareContent = shareContent;
        if (this.enabledExplicitlySet) {
            return;
        }
        internalSetEnabled(canShare());
    }

    public DeviceShareButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<DeviceShareDialog.Result> facebookCallback, int i2) {
        setRequestCode(i2);
        getDialog().registerCallback(callbackManager, facebookCallback, i2);
    }

    private DeviceShareButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2, 0, AnalyticsEvents.EVENT_DEVICE_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_DEVICE_SHARE_BUTTON_DID_TAP);
        this.requestCode = 0;
        this.enabledExplicitlySet = false;
        this.dialog = null;
        this.requestCode = isInEditMode() ? 0 : getDefaultRequestCode();
        internalSetEnabled(false);
    }
}
