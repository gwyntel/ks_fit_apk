package com.luck.picture.lib.basic;

import android.app.Activity;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.luck.picture.lib.PictureOnlyCameraFragment;
import com.luck.picture.lib.R;
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
import com.luck.picture.lib.interfaces.OnCameraInterceptListener;
import com.luck.picture.lib.interfaces.OnCustomLoadingListener;
import com.luck.picture.lib.interfaces.OnPermissionDeniedListener;
import com.luck.picture.lib.interfaces.OnPermissionDescriptionListener;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.interfaces.OnRecordAudioInterceptListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.interfaces.OnSelectLimitTipsListener;
import com.luck.picture.lib.interfaces.OnVideoThumbnailEventListener;
import com.luck.picture.lib.utils.DoubleUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class PictureSelectionCameraModel {
    private final SelectorConfig selectionConfig;
    private final PictureSelector selector;

    public PictureSelectionCameraModel(PictureSelector pictureSelector, int i2) {
        this.selector = pictureSelector;
        SelectorConfig selectorConfig = new SelectorConfig();
        this.selectionConfig = selectorConfig;
        SelectorProviders.getInstance().addSelectorConfigQueue(selectorConfig);
        selectorConfig.chooseMode = i2;
        selectorConfig.isOnlyCamera = true;
        selectorConfig.isDisplayTimeAxis = false;
        selectorConfig.isPreviewFullScreenMode = false;
        selectorConfig.isPreviewZoomEffect = false;
        selectorConfig.isOpenClickSound = false;
    }

    private PictureSelectionCameraModel setMaxSelectNum(int i2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        if (selectorConfig.selectionMode == 1) {
            i2 = 1;
        }
        selectorConfig.maxSelectNum = i2;
        return this;
    }

    public PictureOnlyCameraFragment build() {
        Activity activityA = this.selector.a();
        if (activityA == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (!(activityA instanceof IBridgePictureBehavior)) {
            throw new NullPointerException("Use only build PictureOnlyCameraFragment,Activity or Fragment interface needs to be implemented " + IBridgePictureBehavior.class);
        }
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isResultListenerBack = false;
        selectorConfig.isActivityResultBack = true;
        selectorConfig.onResultCallListener = null;
        return new PictureOnlyCameraFragment();
    }

    public PictureOnlyCameraFragment buildLaunch(int i2, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        Activity activityA = this.selector.a();
        if (activityA == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (onResultCallbackListener == null) {
            throw new NullPointerException("OnResultCallbackListener cannot be null");
        }
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isResultListenerBack = true;
        selectorConfig.isActivityResultBack = false;
        selectorConfig.onResultCallListener = onResultCallbackListener;
        FragmentManager supportFragmentManager = activityA instanceof FragmentActivity ? ((FragmentActivity) activityA).getSupportFragmentManager() : null;
        if (supportFragmentManager == null) {
            throw new NullPointerException("FragmentManager cannot be null");
        }
        PictureOnlyCameraFragment pictureOnlyCameraFragment = new PictureOnlyCameraFragment();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(pictureOnlyCameraFragment.getFragmentTag());
        if (fragmentFindFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
        }
        supportFragmentManager.beginTransaction().add(i2, pictureOnlyCameraFragment, pictureOnlyCameraFragment.getFragmentTag()).addToBackStack(pictureOnlyCameraFragment.getFragmentTag()).commitAllowingStateLoss();
        return pictureOnlyCameraFragment;
    }

    public void forResult() {
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
        FragmentManager supportFragmentManager = activityA instanceof FragmentActivity ? ((FragmentActivity) activityA).getSupportFragmentManager() : null;
        if (supportFragmentManager == null) {
            throw new NullPointerException("FragmentManager cannot be null");
        }
        if (!(activityA instanceof IBridgePictureBehavior)) {
            throw new NullPointerException("Use only camera openCamera mode,Activity or Fragment interface needs to be implemented " + IBridgePictureBehavior.class);
        }
        String str = PictureOnlyCameraFragment.TAG;
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(str);
        if (fragmentFindFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
        }
        FragmentInjectManager.injectSystemRoomFragment(supportFragmentManager, str, PictureOnlyCameraFragment.newInstance());
    }

    public void forResultActivity(int i2) {
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
        Fragment fragmentB = this.selector.b();
        if (fragmentB != null) {
            fragmentB.startActivityForResult(intent, i2);
        } else {
            activityA.startActivityForResult(intent, i2);
        }
        activityA.overridePendingTransition(R.anim.ps_anim_fade_in, 0);
    }

    public PictureSelectionCameraModel isCameraAroundState(boolean z2) {
        this.selectionConfig.isCameraAroundState = z2;
        return this;
    }

    public PictureSelectionCameraModel isCameraForegroundService(boolean z2) {
        this.selectionConfig.isCameraForegroundService = z2;
        return this;
    }

    public PictureSelectionCameraModel isCameraRotateImage(boolean z2) {
        this.selectionConfig.isCameraRotateImage = z2;
        return this;
    }

    public PictureSelectionCameraModel isNewKeyBackMode(boolean z2) {
        this.selectionConfig.isNewKeyBackMode = z2;
        return this;
    }

    public PictureSelectionCameraModel isOriginalControl(boolean z2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isOriginalControl = z2;
        selectorConfig.isCheckOriginalImage = z2;
        return this;
    }

    public PictureSelectionCameraModel isOriginalSkipCompress(boolean z2) {
        this.selectionConfig.isOriginalSkipCompress = z2;
        return this;
    }

    public PictureSelectionCameraModel isQuickCapture(boolean z2) {
        this.selectionConfig.isQuickCapture = z2;
        return this;
    }

    public PictureSelectionCameraModel setAddBitmapWatermarkListener(OnBitmapWatermarkEventListener onBitmapWatermarkEventListener) {
        if (this.selectionConfig.chooseMode != SelectMimeType.ofAudio()) {
            this.selectionConfig.onBitmapWatermarkListener = onBitmapWatermarkEventListener;
        }
        return this;
    }

    public PictureSelectionCameraModel setCameraImageFormat(String str) {
        this.selectionConfig.cameraImageFormat = str;
        return this;
    }

    public PictureSelectionCameraModel setCameraImageFormatForQ(String str) {
        this.selectionConfig.cameraImageFormatForQ = str;
        return this;
    }

    public PictureSelectionCameraModel setCameraInterceptListener(OnCameraInterceptListener onCameraInterceptListener) {
        this.selectionConfig.onCameraInterceptListener = onCameraInterceptListener;
        return this;
    }

    public PictureSelectionCameraModel setCameraVideoFormat(String str) {
        this.selectionConfig.cameraVideoFormat = str;
        return this;
    }

    public PictureSelectionCameraModel setCameraVideoFormatForQ(String str) {
        this.selectionConfig.cameraVideoFormatForQ = str;
        return this;
    }

    @Deprecated
    public PictureSelectionCameraModel setCompressEngine(CompressEngine compressEngine) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.compressEngine = compressEngine;
        selectorConfig.isCompressEngine = true;
        return this;
    }

    @Deprecated
    public PictureSelectionCameraModel setCropEngine(CropEngine cropEngine) {
        this.selectionConfig.cropEngine = cropEngine;
        return this;
    }

    public PictureSelectionCameraModel setCustomLoadingListener(OnCustomLoadingListener onCustomLoadingListener) {
        this.selectionConfig.onCustomLoadingListener = onCustomLoadingListener;
        return this;
    }

    public PictureSelectionCameraModel setDefaultLanguage(int i2) {
        this.selectionConfig.defaultLanguage = i2;
        return this;
    }

    public PictureSelectionCameraModel setLanguage(int i2) {
        this.selectionConfig.language = i2;
        return this;
    }

    public PictureSelectionCameraModel setMaxVideoSelectNum(int i2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        if (selectorConfig.chooseMode == SelectMimeType.ofVideo()) {
            i2 = 0;
        }
        selectorConfig.maxVideoSelectNum = i2;
        return this;
    }

    public PictureSelectionCameraModel setOfAllCameraType(int i2) {
        this.selectionConfig.ofAllCameraType = i2;
        return this;
    }

    public PictureSelectionCameraModel setOutputAudioDir(String str) {
        this.selectionConfig.outPutAudioDir = str;
        return this;
    }

    public PictureSelectionCameraModel setOutputAudioFileName(String str) {
        this.selectionConfig.outPutAudioFileName = str;
        return this;
    }

    public PictureSelectionCameraModel setOutputCameraDir(String str) {
        this.selectionConfig.outPutCameraDir = str;
        return this;
    }

    public PictureSelectionCameraModel setOutputCameraImageFileName(String str) {
        this.selectionConfig.outPutCameraImageFileName = str;
        return this;
    }

    public PictureSelectionCameraModel setOutputCameraVideoFileName(String str) {
        this.selectionConfig.outPutCameraVideoFileName = str;
        return this;
    }

    public PictureSelectionCameraModel setPermissionDeniedListener(OnPermissionDeniedListener onPermissionDeniedListener) {
        this.selectionConfig.onPermissionDeniedListener = onPermissionDeniedListener;
        return this;
    }

    public PictureSelectionCameraModel setPermissionDescriptionListener(OnPermissionDescriptionListener onPermissionDescriptionListener) {
        this.selectionConfig.onPermissionDescriptionListener = onPermissionDescriptionListener;
        return this;
    }

    public PictureSelectionCameraModel setPermissionsInterceptListener(OnPermissionsInterceptListener onPermissionsInterceptListener) {
        this.selectionConfig.onPermissionsEventListener = onPermissionsInterceptListener;
        return this;
    }

    public PictureSelectionCameraModel setRecordAudioInterceptListener(OnRecordAudioInterceptListener onRecordAudioInterceptListener) {
        this.selectionConfig.onRecordAudioListener = onRecordAudioInterceptListener;
        return this;
    }

    public PictureSelectionCameraModel setRecordVideoMaxSecond(int i2) {
        this.selectionConfig.recordVideoMaxSecond = i2;
        return this;
    }

    public PictureSelectionCameraModel setRecordVideoMinSecond(int i2) {
        this.selectionConfig.recordVideoMinSecond = i2;
        return this;
    }

    public PictureSelectionCameraModel setRequestedOrientation(int i2) {
        this.selectionConfig.requestedOrientation = i2;
        return this;
    }

    @Deprecated
    public PictureSelectionCameraModel setSandboxFileEngine(SandboxFileEngine sandboxFileEngine) {
        if (SdkVersionUtils.isQ()) {
            SelectorConfig selectorConfig = this.selectionConfig;
            selectorConfig.sandboxFileEngine = sandboxFileEngine;
            selectorConfig.isSandboxFileEngine = true;
        } else {
            this.selectionConfig.isSandboxFileEngine = false;
        }
        return this;
    }

    public PictureSelectionCameraModel setSelectLimitTipsListener(OnSelectLimitTipsListener onSelectLimitTipsListener) {
        this.selectionConfig.onSelectLimitTipsListener = onSelectLimitTipsListener;
        return this;
    }

    public PictureSelectionCameraModel setSelectMaxDurationSecond(int i2) {
        this.selectionConfig.selectMaxDurationSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionCameraModel setSelectMaxFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.selectMaxFileSize = j2;
        } else {
            this.selectionConfig.selectMaxFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionCameraModel setSelectMinDurationSecond(int i2) {
        this.selectionConfig.selectMinDurationSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionCameraModel setSelectMinFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.selectMinFileSize = j2;
        } else {
            this.selectionConfig.selectMinFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionCameraModel setSelectedData(List<LocalMedia> list) {
        if (list == null) {
            return this;
        }
        setMaxSelectNum(list.size() + 1);
        setMaxVideoSelectNum(list.size() + 1);
        SelectorConfig selectorConfig = this.selectionConfig;
        if (selectorConfig.selectionMode == 1 && selectorConfig.isDirectReturnSingle) {
            selectorConfig.selectedResult.clear();
        } else {
            selectorConfig.addAllSelectResult(new ArrayList<>(list));
        }
        return this;
    }

    @Deprecated
    public PictureSelectionCameraModel setVideoQuality(int i2) {
        this.selectionConfig.videoQuality = i2;
        return this;
    }

    public PictureSelectionCameraModel setVideoThumbnailListener(OnVideoThumbnailEventListener onVideoThumbnailEventListener) {
        if (this.selectionConfig.chooseMode != SelectMimeType.ofAudio()) {
            this.selectionConfig.onVideoThumbnailEventListener = onVideoThumbnailEventListener;
        }
        return this;
    }

    public PictureSelectionCameraModel setCropEngine(CropFileEngine cropFileEngine) {
        this.selectionConfig.cropFileEngine = cropFileEngine;
        return this;
    }

    public PictureSelectionCameraModel setCompressEngine(CompressFileEngine compressFileEngine) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.compressFileEngine = compressFileEngine;
        selectorConfig.isCompressEngine = true;
        return this;
    }

    public PictureSelectionCameraModel setSandboxFileEngine(UriToFileTransformEngine uriToFileTransformEngine) {
        if (SdkVersionUtils.isQ()) {
            SelectorConfig selectorConfig = this.selectionConfig;
            selectorConfig.uriToFileTransformEngine = uriToFileTransformEngine;
            selectorConfig.isSandboxFileEngine = true;
        } else {
            this.selectionConfig.isSandboxFileEngine = false;
        }
        return this;
    }

    public void forResultActivity(ActivityResultLauncher<Intent> activityResultLauncher) {
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
            activityResultLauncher.launch(new Intent(activityA, (Class<?>) PictureSelectorTransparentActivity.class));
            activityA.overridePendingTransition(R.anim.ps_anim_fade_in, 0);
            return;
        }
        throw new NullPointerException("ActivityResultLauncher cannot be null");
    }

    public void forResult(OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
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
            FragmentManager supportFragmentManager = activityA instanceof FragmentActivity ? ((FragmentActivity) activityA).getSupportFragmentManager() : null;
            if (supportFragmentManager != null) {
                String str = PictureOnlyCameraFragment.TAG;
                Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(str);
                if (fragmentFindFragmentByTag != null) {
                    supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
                }
                FragmentInjectManager.injectSystemRoomFragment(supportFragmentManager, str, PictureOnlyCameraFragment.newInstance());
                return;
            }
            throw new NullPointerException("FragmentManager cannot be null");
        }
        throw new NullPointerException("OnResultCallbackListener cannot be null");
    }

    public void forResultActivity(OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
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
            activityA.startActivity(new Intent(activityA, (Class<?>) PictureSelectorTransparentActivity.class));
            activityA.overridePendingTransition(R.anim.ps_anim_fade_in, 0);
            return;
        }
        throw new NullPointerException("OnResultCallbackListener cannot be null");
    }
}
