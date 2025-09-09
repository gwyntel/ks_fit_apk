package com.luck.picture.lib.basic;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.luck.picture.lib.PictureSelectorFragment;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.engine.CompressEngine;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.engine.CropEngine;
import com.luck.picture.lib.engine.CropFileEngine;
import com.luck.picture.lib.engine.ExtendLoaderEngine;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.engine.SandboxFileEngine;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.engine.VideoPlayerEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnBitmapWatermarkEventListener;
import com.luck.picture.lib.interfaces.OnCameraInterceptListener;
import com.luck.picture.lib.interfaces.OnCustomLoadingListener;
import com.luck.picture.lib.interfaces.OnGridItemSelectAnimListener;
import com.luck.picture.lib.interfaces.OnInjectLayoutResourceListener;
import com.luck.picture.lib.interfaces.OnMediaEditInterceptListener;
import com.luck.picture.lib.interfaces.OnPermissionDeniedListener;
import com.luck.picture.lib.interfaces.OnPermissionDescriptionListener;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.interfaces.OnPreviewInterceptListener;
import com.luck.picture.lib.interfaces.OnQueryFilterListener;
import com.luck.picture.lib.interfaces.OnRecordAudioInterceptListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.interfaces.OnSelectAnimListener;
import com.luck.picture.lib.interfaces.OnSelectFilterListener;
import com.luck.picture.lib.interfaces.OnSelectLimitTipsListener;
import com.luck.picture.lib.interfaces.OnVideoThumbnailEventListener;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.utils.DoubleUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public final class PictureSelectionModel {
    private final SelectorConfig selectionConfig;
    private final PictureSelector selector;

    public PictureSelectionModel(PictureSelector pictureSelector, int i2) {
        this.selector = pictureSelector;
        SelectorConfig selectorConfig = new SelectorConfig();
        this.selectionConfig = selectorConfig;
        SelectorProviders.getInstance().addSelectorConfigQueue(selectorConfig);
        selectorConfig.chooseMode = i2;
        setMaxVideoSelectNum(selectorConfig.maxVideoSelectNum);
    }

    public PictureSelectorFragment build() {
        Activity activityA = this.selector.a();
        if (activityA == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (!(activityA instanceof IBridgePictureBehavior)) {
            throw new NullPointerException("Use only build PictureSelectorFragment,Activity or Fragment interface needs to be implemented " + IBridgePictureBehavior.class);
        }
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isResultListenerBack = false;
        selectorConfig.isActivityResultBack = true;
        selectorConfig.onResultCallListener = null;
        return new PictureSelectorFragment();
    }

    public PictureSelectorFragment buildLaunch(int i2, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
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
        PictureSelectorFragment pictureSelectorFragment = new PictureSelectorFragment();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(pictureSelectorFragment.getFragmentTag());
        if (fragmentFindFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
        }
        supportFragmentManager.beginTransaction().add(i2, pictureSelectorFragment, pictureSelectorFragment.getFragmentTag()).addToBackStack(pictureSelectorFragment.getFragmentTag()).commitAllowingStateLoss();
        return pictureSelectorFragment;
    }

    public void forResult(OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
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
        selectorConfig.isResultListenerBack = true;
        selectorConfig.isActivityResultBack = false;
        selectorConfig.onResultCallListener = onResultCallbackListener;
        if (selectorConfig.imageEngine == null && selectorConfig.chooseMode != SelectMimeType.ofAudio()) {
            throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
        }
        activityA.startActivity(new Intent(activityA, (Class<?>) PictureSelectorSupporterActivity.class));
        activityA.overridePendingTransition(this.selectionConfig.selectorStyle.getWindowAnimationStyle().activityEnterAnimation, R.anim.ps_anim_fade_in);
    }

    public PictureSelectionModel isAutoVideoPlay(boolean z2) {
        this.selectionConfig.isAutoVideoPlay = z2;
        return this;
    }

    public PictureSelectionModel isAutomaticTitleRecyclerTop(boolean z2) {
        this.selectionConfig.isAutomaticTitleRecyclerTop = z2;
        return this;
    }

    public PictureSelectionModel isBmp(boolean z2) {
        this.selectionConfig.isBmp = z2;
        return this;
    }

    public PictureSelectionModel isCameraAroundState(boolean z2) {
        this.selectionConfig.isCameraAroundState = z2;
        return this;
    }

    public PictureSelectionModel isCameraForegroundService(boolean z2) {
        this.selectionConfig.isCameraForegroundService = z2;
        return this;
    }

    public PictureSelectionModel isCameraRotateImage(boolean z2) {
        this.selectionConfig.isCameraRotateImage = z2;
        return this;
    }

    public PictureSelectionModel isDirectReturnSingle(boolean z2) {
        boolean z3 = false;
        if (z2) {
            this.selectionConfig.isFastSlidingSelect = false;
        }
        SelectorConfig selectorConfig = this.selectionConfig;
        if (selectorConfig.selectionMode == 1 && z2) {
            z3 = true;
        }
        selectorConfig.isDirectReturnSingle = z3;
        return this;
    }

    public PictureSelectionModel isDisplayCamera(boolean z2) {
        this.selectionConfig.isDisplayCamera = z2;
        return this;
    }

    public PictureSelectionModel isDisplayTimeAxis(boolean z2) {
        this.selectionConfig.isDisplayTimeAxis = z2;
        return this;
    }

    public PictureSelectionModel isEmptyResultReturn(boolean z2) {
        this.selectionConfig.isEmptyResultReturn = z2;
        return this;
    }

    @Deprecated
    public PictureSelectionModel isEnableVideoSize(boolean z2) {
        this.selectionConfig.isSyncWidthAndHeight = z2;
        return this;
    }

    public PictureSelectionModel isFastSlidingSelect(boolean z2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        if (selectorConfig.isDirectReturnSingle) {
            selectorConfig.isFastSlidingSelect = false;
        } else {
            selectorConfig.isFastSlidingSelect = z2;
        }
        return this;
    }

    public PictureSelectionModel isFilterSizeDuration(boolean z2) {
        this.selectionConfig.isFilterSizeDuration = z2;
        return this;
    }

    public PictureSelectionModel isGif(boolean z2) {
        this.selectionConfig.isGif = z2;
        return this;
    }

    public PictureSelectionModel isHeic(boolean z2) {
        this.selectionConfig.isHeic = z2;
        return this;
    }

    public PictureSelectionModel isLoopAutoVideoPlay(boolean z2) {
        this.selectionConfig.isLoopAutoPlay = z2;
        return this;
    }

    public PictureSelectionModel isMaxSelectEnabledMask(boolean z2) {
        this.selectionConfig.isMaxSelectEnabledMask = z2;
        return this;
    }

    public PictureSelectionModel isNewKeyBackMode(boolean z2) {
        this.selectionConfig.isNewKeyBackMode = z2;
        return this;
    }

    public PictureSelectionModel isOnlyObtainSandboxDir(boolean z2) {
        this.selectionConfig.isOnlySandboxDir = z2;
        return this;
    }

    public PictureSelectionModel isOpenClickSound(boolean z2) {
        this.selectionConfig.isOpenClickSound = z2;
        return this;
    }

    public PictureSelectionModel isOriginalControl(boolean z2) {
        this.selectionConfig.isOriginalControl = z2;
        return this;
    }

    public PictureSelectionModel isOriginalSkipCompress(boolean z2) {
        this.selectionConfig.isOriginalSkipCompress = z2;
        return this;
    }

    public PictureSelectionModel isPageStrategy(boolean z2) {
        this.selectionConfig.isPageStrategy = z2;
        return this;
    }

    public PictureSelectionModel isPageSyncAlbumCount(boolean z2) {
        this.selectionConfig.isPageSyncAsCount = z2;
        return this;
    }

    public PictureSelectionModel isPreloadFirst(boolean z2) {
        this.selectionConfig.isPreloadFirst = z2;
        return this;
    }

    public PictureSelectionModel isPreviewAudio(boolean z2) {
        this.selectionConfig.isEnablePreviewAudio = z2;
        return this;
    }

    public PictureSelectionModel isPreviewFullScreenMode(boolean z2) {
        this.selectionConfig.isPreviewFullScreenMode = z2;
        return this;
    }

    public PictureSelectionModel isPreviewImage(boolean z2) {
        this.selectionConfig.isEnablePreviewImage = z2;
        return this;
    }

    public PictureSelectionModel isPreviewVideo(boolean z2) {
        this.selectionConfig.isEnablePreviewVideo = z2;
        return this;
    }

    public PictureSelectionModel isPreviewZoomEffect(boolean z2) {
        if (this.selectionConfig.chooseMode == SelectMimeType.ofAudio()) {
            this.selectionConfig.isPreviewZoomEffect = false;
        } else {
            this.selectionConfig.isPreviewZoomEffect = z2;
        }
        return this;
    }

    public PictureSelectionModel isQuickCapture(boolean z2) {
        this.selectionConfig.isQuickCapture = z2;
        return this;
    }

    public PictureSelectionModel isSelectZoomAnim(boolean z2) {
        this.selectionConfig.isSelectZoomAnim = z2;
        return this;
    }

    public PictureSelectionModel isSyncCover(boolean z2) {
        this.selectionConfig.isSyncCover = z2;
        return this;
    }

    public PictureSelectionModel isSyncWidthAndHeight(boolean z2) {
        this.selectionConfig.isSyncWidthAndHeight = z2;
        return this;
    }

    public PictureSelectionModel isUseSystemVideoPlayer(boolean z2) {
        this.selectionConfig.isUseSystemVideoPlayer = z2;
        return this;
    }

    public PictureSelectionModel isVideoPauseResumePlay(boolean z2) {
        this.selectionConfig.isPauseResumePlay = z2;
        return this;
    }

    public PictureSelectionModel isWebp(boolean z2) {
        this.selectionConfig.isWebp = z2;
        return this;
    }

    public PictureSelectionModel isWithSelectVideoImage(boolean z2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isWithVideoImage = selectorConfig.chooseMode == SelectMimeType.ofAll() && z2;
        return this;
    }

    public PictureSelectionModel setAddBitmapWatermarkListener(OnBitmapWatermarkEventListener onBitmapWatermarkEventListener) {
        if (this.selectionConfig.chooseMode != SelectMimeType.ofAudio()) {
            this.selectionConfig.onBitmapWatermarkListener = onBitmapWatermarkEventListener;
        }
        return this;
    }

    public PictureSelectionModel setAttachViewLifecycle(IBridgeViewLifecycle iBridgeViewLifecycle) {
        this.selectionConfig.viewLifecycle = iBridgeViewLifecycle;
        return this;
    }

    public PictureSelectionModel setCameraImageFormat(String str) {
        this.selectionConfig.cameraImageFormat = str;
        return this;
    }

    public PictureSelectionModel setCameraImageFormatForQ(String str) {
        this.selectionConfig.cameraImageFormatForQ = str;
        return this;
    }

    public PictureSelectionModel setCameraInterceptListener(OnCameraInterceptListener onCameraInterceptListener) {
        this.selectionConfig.onCameraInterceptListener = onCameraInterceptListener;
        return this;
    }

    public PictureSelectionModel setCameraVideoFormat(String str) {
        this.selectionConfig.cameraVideoFormat = str;
        return this;
    }

    public PictureSelectionModel setCameraVideoFormatForQ(String str) {
        this.selectionConfig.cameraVideoFormatForQ = str;
        return this;
    }

    @Deprecated
    public PictureSelectionModel setCompressEngine(CompressEngine compressEngine) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.compressEngine = compressEngine;
        selectorConfig.isCompressEngine = true;
        return this;
    }

    @Deprecated
    public PictureSelectionModel setCropEngine(CropEngine cropEngine) {
        this.selectionConfig.cropEngine = cropEngine;
        return this;
    }

    public PictureSelectionModel setCustomLoadingListener(OnCustomLoadingListener onCustomLoadingListener) {
        this.selectionConfig.onCustomLoadingListener = onCustomLoadingListener;
        return this;
    }

    public PictureSelectionModel setDefaultAlbumName(String str) {
        this.selectionConfig.defaultAlbumName = str;
        return this;
    }

    public PictureSelectionModel setDefaultLanguage(int i2) {
        this.selectionConfig.defaultLanguage = i2;
        return this;
    }

    public PictureSelectionModel setEditMediaInterceptListener(OnMediaEditInterceptListener onMediaEditInterceptListener) {
        this.selectionConfig.onEditMediaEventListener = onMediaEditInterceptListener;
        return this;
    }

    @Deprecated
    public PictureSelectionModel setExtendLoaderEngine(ExtendLoaderEngine extendLoaderEngine) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.loaderDataEngine = extendLoaderEngine;
        selectorConfig.isLoaderDataEngine = true;
        return this;
    }

    public PictureSelectionModel setFilterMaxFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.filterMaxFileSize = j2;
        } else {
            this.selectionConfig.filterMaxFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionModel setFilterMinFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.filterMinFileSize = j2;
        } else {
            this.selectionConfig.filterMinFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionModel setFilterVideoMaxSecond(int i2) {
        this.selectionConfig.filterVideoMaxSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionModel setFilterVideoMinSecond(int i2) {
        this.selectionConfig.filterVideoMinSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionModel setGridItemSelectAnimListener(OnGridItemSelectAnimListener onGridItemSelectAnimListener) {
        this.selectionConfig.onItemSelectAnimListener = onGridItemSelectAnimListener;
        return this;
    }

    public PictureSelectionModel setImageEngine(ImageEngine imageEngine) {
        this.selectionConfig.imageEngine = imageEngine;
        return this;
    }

    public PictureSelectionModel setImageSpanCount(int i2) {
        this.selectionConfig.imageSpanCount = i2;
        return this;
    }

    public PictureSelectionModel setInjectLayoutResourceListener(OnInjectLayoutResourceListener onInjectLayoutResourceListener) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isInjectLayoutResource = onInjectLayoutResourceListener != null;
        selectorConfig.onLayoutResourceListener = onInjectLayoutResourceListener;
        return this;
    }

    public PictureSelectionModel setLanguage(int i2) {
        this.selectionConfig.language = i2;
        return this;
    }

    public PictureSelectionModel setLoaderFactoryEngine(IBridgeLoaderFactory iBridgeLoaderFactory) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.loaderFactory = iBridgeLoaderFactory;
        selectorConfig.isLoaderFactoryEngine = true;
        return this;
    }

    public PictureSelectionModel setMagicalEffectInterpolator(InterpolatorFactory interpolatorFactory) {
        this.selectionConfig.interpolatorFactory = interpolatorFactory;
        return this;
    }

    public PictureSelectionModel setMaxSelectNum(int i2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        if (selectorConfig.selectionMode == 1) {
            i2 = 1;
        }
        selectorConfig.maxSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setMaxVideoSelectNum(int i2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        if (selectorConfig.chooseMode == SelectMimeType.ofVideo()) {
            i2 = 0;
        }
        selectorConfig.maxVideoSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setMinAudioSelectNum(int i2) {
        this.selectionConfig.minAudioSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setMinSelectNum(int i2) {
        this.selectionConfig.minSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setMinVideoSelectNum(int i2) {
        this.selectionConfig.minVideoSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setOfAllCameraType(int i2) {
        this.selectionConfig.ofAllCameraType = i2;
        return this;
    }

    public PictureSelectionModel setOutputAudioDir(String str) {
        this.selectionConfig.outPutAudioDir = str;
        return this;
    }

    public PictureSelectionModel setOutputAudioFileName(String str) {
        this.selectionConfig.outPutAudioFileName = str;
        return this;
    }

    public PictureSelectionModel setOutputCameraDir(String str) {
        this.selectionConfig.outPutCameraDir = str;
        return this;
    }

    public PictureSelectionModel setOutputCameraImageFileName(String str) {
        this.selectionConfig.outPutCameraImageFileName = str;
        return this;
    }

    public PictureSelectionModel setOutputCameraVideoFileName(String str) {
        this.selectionConfig.outPutCameraVideoFileName = str;
        return this;
    }

    public PictureSelectionModel setPermissionDeniedListener(OnPermissionDeniedListener onPermissionDeniedListener) {
        this.selectionConfig.onPermissionDeniedListener = onPermissionDeniedListener;
        return this;
    }

    public PictureSelectionModel setPermissionDescriptionListener(OnPermissionDescriptionListener onPermissionDescriptionListener) {
        this.selectionConfig.onPermissionDescriptionListener = onPermissionDescriptionListener;
        return this;
    }

    public PictureSelectionModel setPermissionsInterceptListener(OnPermissionsInterceptListener onPermissionsInterceptListener) {
        this.selectionConfig.onPermissionsEventListener = onPermissionsInterceptListener;
        return this;
    }

    public PictureSelectionModel setPreviewInterceptListener(OnPreviewInterceptListener onPreviewInterceptListener) {
        this.selectionConfig.onPreviewInterceptListener = onPreviewInterceptListener;
        return this;
    }

    public PictureSelectionModel setQueryFilterListener(OnQueryFilterListener onQueryFilterListener) {
        this.selectionConfig.onQueryFilterListener = onQueryFilterListener;
        return this;
    }

    public PictureSelectionModel setQueryOnlyMimeType(String... strArr) {
        for (String str : strArr) {
            if (PictureMimeType.isHasImage(str)) {
                if (!this.selectionConfig.queryOnlyImageList.contains(str)) {
                    this.selectionConfig.queryOnlyImageList.add(str);
                }
            } else if (PictureMimeType.isHasVideo(str)) {
                if (!this.selectionConfig.queryOnlyVideoList.contains(str)) {
                    this.selectionConfig.queryOnlyVideoList.add(str);
                }
            } else if (PictureMimeType.isHasAudio(str) && !this.selectionConfig.queryOnlyAudioList.contains(str)) {
                this.selectionConfig.queryOnlyAudioList.add(str);
            }
        }
        return this;
    }

    public PictureSelectionModel setQuerySandboxDir(String str) {
        this.selectionConfig.sandboxDir = str;
        return this;
    }

    public PictureSelectionModel setQuerySortOrder(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.selectionConfig.sortOrder = str;
        }
        return this;
    }

    public PictureSelectionModel setRecordAudioInterceptListener(OnRecordAudioInterceptListener onRecordAudioInterceptListener) {
        this.selectionConfig.onRecordAudioListener = onRecordAudioInterceptListener;
        return this;
    }

    public PictureSelectionModel setRecordVideoMaxSecond(int i2) {
        this.selectionConfig.recordVideoMaxSecond = i2;
        return this;
    }

    public PictureSelectionModel setRecordVideoMinSecond(int i2) {
        this.selectionConfig.recordVideoMinSecond = i2;
        return this;
    }

    public PictureSelectionModel setRecyclerAnimationMode(int i2) {
        this.selectionConfig.animationMode = i2;
        return this;
    }

    public PictureSelectionModel setRequestedOrientation(int i2) {
        this.selectionConfig.requestedOrientation = i2;
        return this;
    }

    @Deprecated
    public PictureSelectionModel setSandboxFileEngine(SandboxFileEngine sandboxFileEngine) {
        if (SdkVersionUtils.isQ()) {
            SelectorConfig selectorConfig = this.selectionConfig;
            selectorConfig.sandboxFileEngine = sandboxFileEngine;
            selectorConfig.isSandboxFileEngine = true;
        } else {
            this.selectionConfig.isSandboxFileEngine = false;
        }
        return this;
    }

    public PictureSelectionModel setSelectAnimListener(OnSelectAnimListener onSelectAnimListener) {
        this.selectionConfig.onSelectAnimListener = onSelectAnimListener;
        return this;
    }

    public PictureSelectionModel setSelectFilterListener(OnSelectFilterListener onSelectFilterListener) {
        this.selectionConfig.onSelectFilterListener = onSelectFilterListener;
        return this;
    }

    public PictureSelectionModel setSelectLimitTipsListener(OnSelectLimitTipsListener onSelectLimitTipsListener) {
        this.selectionConfig.onSelectLimitTipsListener = onSelectLimitTipsListener;
        return this;
    }

    public PictureSelectionModel setSelectMaxDurationSecond(int i2) {
        this.selectionConfig.selectMaxDurationSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionModel setSelectMaxFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.selectMaxFileSize = j2;
        } else {
            this.selectionConfig.selectMaxFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionModel setSelectMinDurationSecond(int i2) {
        this.selectionConfig.selectMinDurationSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionModel setSelectMinFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.selectMinFileSize = j2;
        } else {
            this.selectionConfig.selectMinFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionModel setSelectedData(List<LocalMedia> list) {
        if (list == null) {
            return this;
        }
        SelectorConfig selectorConfig = this.selectionConfig;
        if (selectorConfig.selectionMode == 1 && selectorConfig.isDirectReturnSingle) {
            selectorConfig.selectedResult.clear();
        } else {
            selectorConfig.addAllSelectResult(new ArrayList<>(list));
        }
        return this;
    }

    public PictureSelectionModel setSelectionMode(int i2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.selectionMode = i2;
        selectorConfig.maxSelectNum = i2 != 1 ? selectorConfig.maxSelectNum : 1;
        return this;
    }

    public PictureSelectionModel setSelectorUIStyle(PictureSelectorStyle pictureSelectorStyle) {
        if (pictureSelectorStyle != null) {
            this.selectionConfig.selectorStyle = pictureSelectorStyle;
        }
        return this;
    }

    public PictureSelectionModel setSkipCropMimeType(String... strArr) {
        if (strArr != null && strArr.length > 0) {
            this.selectionConfig.skipCropList.addAll(Arrays.asList(strArr));
        }
        return this;
    }

    public PictureSelectionModel setVideoPlayerEngine(VideoPlayerEngine videoPlayerEngine) {
        this.selectionConfig.videoPlayerEngine = videoPlayerEngine;
        return this;
    }

    @Deprecated
    public PictureSelectionModel setVideoQuality(int i2) {
        this.selectionConfig.videoQuality = i2;
        return this;
    }

    public PictureSelectionModel setVideoThumbnailListener(OnVideoThumbnailEventListener onVideoThumbnailEventListener) {
        if (this.selectionConfig.chooseMode != SelectMimeType.ofAudio()) {
            this.selectionConfig.onVideoThumbnailEventListener = onVideoThumbnailEventListener;
        }
        return this;
    }

    public PictureSelectionModel isPageStrategy(boolean z2, int i2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isPageStrategy = z2;
        if (i2 < 10) {
            i2 = 60;
        }
        selectorConfig.pageSize = i2;
        return this;
    }

    public PictureSelectionModel setCropEngine(CropFileEngine cropFileEngine) {
        this.selectionConfig.cropFileEngine = cropFileEngine;
        return this;
    }

    public PictureSelectionModel setCompressEngine(CompressFileEngine compressFileEngine) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.compressFileEngine = compressFileEngine;
        selectorConfig.isCompressEngine = true;
        return this;
    }

    @Deprecated
    public PictureSelectionModel isPageStrategy(boolean z2, boolean z3) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isPageStrategy = z2;
        selectorConfig.isFilterInvalidFile = z3;
        return this;
    }

    public PictureSelectionModel setSandboxFileEngine(UriToFileTransformEngine uriToFileTransformEngine) {
        if (SdkVersionUtils.isQ()) {
            SelectorConfig selectorConfig = this.selectionConfig;
            selectorConfig.uriToFileTransformEngine = uriToFileTransformEngine;
            selectorConfig.isSandboxFileEngine = true;
        } else {
            this.selectionConfig.isSandboxFileEngine = false;
        }
        return this;
    }

    @Deprecated
    public PictureSelectionModel isPageStrategy(boolean z2, int i2, boolean z3) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isPageStrategy = z2;
        if (i2 < 10) {
            i2 = 60;
        }
        selectorConfig.pageSize = i2;
        selectorConfig.isFilterInvalidFile = z3;
        return this;
    }

    public void forResult(int i2) {
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activityA = this.selector.a();
        if (activityA != null) {
            SelectorConfig selectorConfig = this.selectionConfig;
            selectorConfig.isResultListenerBack = false;
            selectorConfig.isActivityResultBack = true;
            if (selectorConfig.imageEngine == null && selectorConfig.chooseMode != SelectMimeType.ofAudio()) {
                throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
            }
            Intent intent = new Intent(activityA, (Class<?>) PictureSelectorSupporterActivity.class);
            Fragment fragmentB = this.selector.b();
            if (fragmentB != null) {
                fragmentB.startActivityForResult(intent, i2);
            } else {
                activityA.startActivityForResult(intent, i2);
            }
            activityA.overridePendingTransition(this.selectionConfig.selectorStyle.getWindowAnimationStyle().activityEnterAnimation, R.anim.ps_anim_fade_in);
            return;
        }
        throw new NullPointerException("Activity cannot be null");
    }

    public void forResult(ActivityResultLauncher<Intent> activityResultLauncher) {
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
            if (selectorConfig.imageEngine == null && selectorConfig.chooseMode != SelectMimeType.ofAudio()) {
                throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
            }
            activityResultLauncher.launch(new Intent(activityA, (Class<?>) PictureSelectorSupporterActivity.class));
            activityA.overridePendingTransition(this.selectionConfig.selectorStyle.getWindowAnimationStyle().activityEnterAnimation, R.anim.ps_anim_fade_in);
            return;
        }
        throw new NullPointerException("ActivityResultLauncher cannot be null");
    }
}
