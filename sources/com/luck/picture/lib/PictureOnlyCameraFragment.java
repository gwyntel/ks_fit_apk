package com.luck.picture.lib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.luck.picture.lib.basic.PictureCommonFragment;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.luck.picture.lib.utils.SdkVersionUtils;
import com.luck.picture.lib.utils.ToastUtils;

/* loaded from: classes4.dex */
public class PictureOnlyCameraFragment extends PictureCommonFragment {
    public static final String TAG = "PictureOnlyCameraFragment";

    public static PictureOnlyCameraFragment newInstance() {
        return new PictureOnlyCameraFragment();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void dispatchCameraMediaResult(LocalMedia localMedia) {
        if (confirmSelect(localMedia, false) == 0) {
            h();
        } else {
            onKeyBackFragmentFinish();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment
    public String getFragmentTag() {
        return TAG;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public int getResourceId() {
        return R.layout.ps_empty;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void handlePermissionSettingResult(String[] strArr) {
        boolean zIsCheckCamera;
        onPermissionExplainEvent(false, null);
        OnPermissionsInterceptListener onPermissionsInterceptListener = this.f19027d.onPermissionsEventListener;
        if (onPermissionsInterceptListener != null) {
            zIsCheckCamera = onPermissionsInterceptListener.hasPermissions(this, strArr);
        } else {
            zIsCheckCamera = PermissionChecker.isCheckCamera(getContext());
            if (!SdkVersionUtils.isQ()) {
                zIsCheckCamera = PermissionChecker.isCheckWriteExternalStorage(getContext());
            }
        }
        if (zIsCheckCamera) {
            openSelectedCamera();
        } else {
            if (!PermissionChecker.isCheckCamera(getContext())) {
                ToastUtils.showToast(getContext(), getString(R.string.ps_camera));
            } else if (!PermissionChecker.isCheckWriteExternalStorage(getContext())) {
                ToastUtils.showToast(getContext(), getString(R.string.ps_jurisdiction));
            }
            onKeyBackFragmentFinish();
        }
        PermissionConfig.CURRENT_REQUEST_PERMISSION = new String[0];
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i3 == 0) {
            onKeyBackFragmentFinish();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle == null) {
            openSelectedCamera();
        }
    }
}
