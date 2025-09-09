package com.luck.picture.lib.basic;

import android.app.Activity;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.luck.picture.lib.PictureSelectorSystemFragment;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.engine.CompressEngine;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.engine.CropEngine;
import com.luck.picture.lib.engine.CropFileEngine;
import com.luck.picture.lib.engine.SandboxFileEngine;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnBitmapWatermarkEventListener;
import com.luck.picture.lib.interfaces.OnCustomLoadingListener;
import com.luck.picture.lib.interfaces.OnPermissionDeniedListener;
import com.luck.picture.lib.interfaces.OnPermissionDescriptionListener;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.interfaces.OnSelectFilterListener;
import com.luck.picture.lib.interfaces.OnSelectLimitTipsListener;
import com.luck.picture.lib.interfaces.OnVideoThumbnailEventListener;
import com.luck.picture.lib.utils.DoubleUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class PictureSelectionSystemModel {
    private final SelectorConfig selectionConfig;
    private final PictureSelector selector;

    public PictureSelectionSystemModel(PictureSelector pictureSelector, int i2) {
        this.selector = pictureSelector;
        SelectorConfig selectorConfig = new SelectorConfig();
        this.selectionConfig = selectorConfig;
        SelectorProviders.getInstance().addSelectorConfigQueue(selectorConfig);
        selectorConfig.chooseMode = i2;
        selectorConfig.isPreviewFullScreenMode = false;
        selectorConfig.isPreviewZoomEffect = false;
    }

    public void forSystemResult(OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activityA = this.selector.a();
        if (activityA == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (onResultCallbackListener == null) {
            throw new NullPointerException("OnResultCallbackListener cannot be null");
        }
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.onResultCallListener = onResultCallbackListener;
        selectorConfig.isResultListenerBack = true;
        selectorConfig.isActivityResultBack = false;
        FragmentManager supportFragmentManager = activityA instanceof FragmentActivity ? ((FragmentActivity) activityA).getSupportFragmentManager() : null;
        if (supportFragmentManager == null) {
            throw new NullPointerException("FragmentManager cannot be null");
        }
        String str = PictureSelectorSystemFragment.TAG;
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(str);
        if (fragmentFindFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
        }
        FragmentInjectManager.injectSystemRoomFragment(supportFragmentManager, str, PictureSelectorSystemFragment.newInstance());
    }

    public void forSystemResultActivity(int i2) {
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activityA = this.selector.a();
        if (activityA == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isResultListenerBack = false;
        selectorConfig.isActivityResultBack = true;
        Intent intent = new Intent(activityA, (Class<?>) PictureSelectorTransparentActivity.class);
        intent.putExtra(PictureConfig.EXTRA_MODE_TYPE_SOURCE, 1);
        Fragment fragmentB = this.selector.b();
        if (fragmentB != null) {
            fragmentB.startActivityForResult(intent, i2);
        } else {
            activityA.startActivityForResult(intent, i2);
        }
        activityA.overridePendingTransition(R.anim.ps_anim_fade_in, 0);
    }

    public PictureSelectionSystemModel isNewKeyBackMode(boolean z2) {
        this.selectionConfig.isNewKeyBackMode = z2;
        return this;
    }

    public PictureSelectionSystemModel isOriginalControl(boolean z2) {
        this.selectionConfig.isCheckOriginalImage = z2;
        return this;
    }

    public PictureSelectionSystemModel isOriginalSkipCompress(boolean z2) {
        this.selectionConfig.isOriginalSkipCompress = z2;
        return this;
    }

    public PictureSelectionSystemModel setAddBitmapWatermarkListener(OnBitmapWatermarkEventListener onBitmapWatermarkEventListener) {
        if (this.selectionConfig.chooseMode != SelectMimeType.ofAudio()) {
            this.selectionConfig.onBitmapWatermarkListener = onBitmapWatermarkEventListener;
        }
        return this;
    }

    @Deprecated
    public PictureSelectionSystemModel setCompressEngine(CompressEngine compressEngine) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.compressEngine = compressEngine;
        selectorConfig.isCompressEngine = true;
        return this;
    }

    @Deprecated
    public PictureSelectionSystemModel setCropEngine(CropEngine cropEngine) {
        this.selectionConfig.cropEngine = cropEngine;
        return this;
    }

    public PictureSelectionSystemModel setCustomLoadingListener(OnCustomLoadingListener onCustomLoadingListener) {
        this.selectionConfig.onCustomLoadingListener = onCustomLoadingListener;
        return this;
    }

    public PictureSelectionSystemModel setPermissionDeniedListener(OnPermissionDeniedListener onPermissionDeniedListener) {
        this.selectionConfig.onPermissionDeniedListener = onPermissionDeniedListener;
        return this;
    }

    public PictureSelectionSystemModel setPermissionDescriptionListener(OnPermissionDescriptionListener onPermissionDescriptionListener) {
        this.selectionConfig.onPermissionDescriptionListener = onPermissionDescriptionListener;
        return this;
    }

    public PictureSelectionSystemModel setPermissionsInterceptListener(OnPermissionsInterceptListener onPermissionsInterceptListener) {
        this.selectionConfig.onPermissionsEventListener = onPermissionsInterceptListener;
        return this;
    }

    @Deprecated
    public PictureSelectionSystemModel setSandboxFileEngine(SandboxFileEngine sandboxFileEngine) {
        if (SdkVersionUtils.isQ()) {
            SelectorConfig selectorConfig = this.selectionConfig;
            selectorConfig.sandboxFileEngine = sandboxFileEngine;
            selectorConfig.isSandboxFileEngine = true;
        } else {
            this.selectionConfig.isSandboxFileEngine = false;
        }
        return this;
    }

    public PictureSelectionSystemModel setSelectFilterListener(OnSelectFilterListener onSelectFilterListener) {
        this.selectionConfig.onSelectFilterListener = onSelectFilterListener;
        return this;
    }

    public PictureSelectionSystemModel setSelectLimitTipsListener(OnSelectLimitTipsListener onSelectLimitTipsListener) {
        this.selectionConfig.onSelectLimitTipsListener = onSelectLimitTipsListener;
        return this;
    }

    public PictureSelectionSystemModel setSelectMaxDurationSecond(int i2) {
        this.selectionConfig.selectMaxDurationSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionSystemModel setSelectMaxFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.selectMaxFileSize = j2;
        } else {
            this.selectionConfig.selectMaxFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionSystemModel setSelectMinDurationSecond(int i2) {
        this.selectionConfig.selectMinDurationSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionSystemModel setSelectMinFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.selectMinFileSize = j2;
        } else {
            this.selectionConfig.selectMinFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionSystemModel setSelectionMode(int i2) {
        this.selectionConfig.selectionMode = i2;
        return this;
    }

    public PictureSelectionSystemModel setSkipCropMimeType(String... strArr) {
        if (strArr != null && strArr.length > 0) {
            this.selectionConfig.skipCropList.addAll(Arrays.asList(strArr));
        }
        return this;
    }

    public PictureSelectionSystemModel setVideoThumbnailListener(OnVideoThumbnailEventListener onVideoThumbnailEventListener) {
        if (this.selectionConfig.chooseMode != SelectMimeType.ofAudio()) {
            this.selectionConfig.onVideoThumbnailEventListener = onVideoThumbnailEventListener;
        }
        return this;
    }

    public PictureSelectionSystemModel setCropEngine(CropFileEngine cropFileEngine) {
        this.selectionConfig.cropFileEngine = cropFileEngine;
        return this;
    }

    public PictureSelectionSystemModel setCompressEngine(CompressFileEngine compressFileEngine) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.compressFileEngine = compressFileEngine;
        selectorConfig.isCompressEngine = true;
        return this;
    }

    public PictureSelectionSystemModel setSandboxFileEngine(UriToFileTransformEngine uriToFileTransformEngine) {
        if (SdkVersionUtils.isQ()) {
            SelectorConfig selectorConfig = this.selectionConfig;
            selectorConfig.uriToFileTransformEngine = uriToFileTransformEngine;
            selectorConfig.isSandboxFileEngine = true;
        } else {
            this.selectionConfig.isSandboxFileEngine = false;
        }
        return this;
    }

    public void forSystemResultActivity(ActivityResultLauncher<Intent> activityResultLauncher) {
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activityA = this.selector.a();
        if (activityA == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (activityResultLauncher != null) {
            SelectorConfig selectorConfig = this.selectionConfig;
            selectorConfig.isResultListenerBack = false;
            selectorConfig.isActivityResultBack = true;
            Intent intent = new Intent(activityA, (Class<?>) PictureSelectorTransparentActivity.class);
            intent.putExtra(PictureConfig.EXTRA_MODE_TYPE_SOURCE, 1);
            activityResultLauncher.launch(intent);
            activityA.overridePendingTransition(R.anim.ps_anim_fade_in, 0);
            return;
        }
        throw new NullPointerException("ActivityResultLauncher cannot be null");
    }

    public void forSystemResult() {
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activityA = this.selector.a();
        if (activityA != null) {
            if (activityA instanceof IBridgePictureBehavior) {
                SelectorConfig selectorConfig = this.selectionConfig;
                selectorConfig.isActivityResultBack = true;
                selectorConfig.onResultCallListener = null;
                selectorConfig.isResultListenerBack = false;
                FragmentManager supportFragmentManager = activityA instanceof FragmentActivity ? ((FragmentActivity) activityA).getSupportFragmentManager() : null;
                if (supportFragmentManager != null) {
                    String str = PictureSelectorSystemFragment.TAG;
                    Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(str);
                    if (fragmentFindFragmentByTag != null) {
                        supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
                    }
                    FragmentInjectManager.injectSystemRoomFragment(supportFragmentManager, str, PictureSelectorSystemFragment.newInstance());
                    return;
                }
                throw new NullPointerException("FragmentManager cannot be null");
            }
            throw new NullPointerException("Use only forSystemResult();,Activity or Fragment interface needs to be implemented " + IBridgePictureBehavior.class);
        }
        throw new NullPointerException("Activity cannot be null");
    }

    public void forSystemResultActivity(OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activityA = this.selector.a();
        if (activityA == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (onResultCallbackListener != null) {
            SelectorConfig selectorConfig = this.selectionConfig;
            selectorConfig.isResultListenerBack = true;
            selectorConfig.isActivityResultBack = false;
            selectorConfig.onResultCallListener = onResultCallbackListener;
            Intent intent = new Intent(activityA, (Class<?>) PictureSelectorTransparentActivity.class);
            intent.putExtra(PictureConfig.EXTRA_MODE_TYPE_SOURCE, 1);
            activityA.startActivity(intent);
            activityA.overridePendingTransition(R.anim.ps_anim_fade_in, 0);
            return;
        }
        throw new NullPointerException("OnResultCallbackListener cannot be null");
    }
}
