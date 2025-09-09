package com.luck.picture.lib.basic;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.luck.picture.lib.R;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.Crop;
import com.luck.picture.lib.config.CustomIntentKey;
import com.luck.picture.lib.config.PermissionEvent;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.dialog.PhotoItemSelectedDialog;
import com.luck.picture.lib.dialog.PictureLoadingDialog;
import com.luck.picture.lib.dialog.RemindDialog;
import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.immersive.ImmersiveManager;
import com.luck.picture.lib.interfaces.OnCallbackIndexListener;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.interfaces.OnCustomLoadingListener;
import com.luck.picture.lib.interfaces.OnItemClickListener;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnRecordAudioInterceptListener;
import com.luck.picture.lib.interfaces.OnRequestPermissionListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.interfaces.OnSelectFilterListener;
import com.luck.picture.lib.interfaces.OnSelectLimitTipsListener;
import com.luck.picture.lib.language.PictureLanguageUtils;
import com.luck.picture.lib.loader.IBridgeMediaLoader;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.luck.picture.lib.permissions.PermissionResultCallback;
import com.luck.picture.lib.permissions.PermissionUtil;
import com.luck.picture.lib.service.ForegroundService;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.thread.PictureThreadUtils;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.luck.picture.lib.utils.BitmapUtils;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.FileDirMap;
import com.luck.picture.lib.utils.MediaStoreUtils;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import com.luck.picture.lib.utils.ToastUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class PictureCommonFragment extends Fragment implements IPictureSelectorCommonEvent {
    public static final String TAG = "PictureCommonFragment";

    /* renamed from: a, reason: collision with root package name */
    protected IBridgePictureBehavior f19024a;

    /* renamed from: b, reason: collision with root package name */
    protected int f19025b = 1;

    /* renamed from: c, reason: collision with root package name */
    protected IBridgeMediaLoader f19026c;
    private Context context;

    /* renamed from: d, reason: collision with root package name */
    protected SelectorConfig f19027d;

    /* renamed from: e, reason: collision with root package name */
    protected Dialog f19028e;
    private long enterAnimDuration;
    private Dialog mLoadingDialog;
    private PermissionResultCallback mPermissionResultCallback;
    private int soundID;
    private SoundPool soundPool;

    public static class SelectorResult {
        public int mResultCode;
        public Intent mResultData;

        public SelectorResult(int i2, Intent intent) {
            this.mResultCode = i2;
            this.mResultData = intent;
        }
    }

    private void addBitmapWatermark(final ArrayList<LocalMedia> arrayList) {
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            LocalMedia localMedia = arrayList.get(i2);
            if (!PictureMimeType.isHasAudio(localMedia.getMimeType())) {
                concurrentHashMap.put(localMedia.getAvailablePath(), localMedia);
            }
        }
        if (concurrentHashMap.size() == 0) {
            dispatchWatermarkResult(arrayList);
            return;
        }
        for (Map.Entry entry : concurrentHashMap.entrySet()) {
            this.f19027d.onBitmapWatermarkListener.onAddBitmapWatermark(i(), (String) entry.getKey(), ((LocalMedia) entry.getValue()).getMimeType(), new OnKeyValueResultCallbackListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.13
                @Override // com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
                public void onCallback(String str, String str2) {
                    if (TextUtils.isEmpty(str)) {
                        PictureCommonFragment.this.dispatchWatermarkResult(arrayList);
                        return;
                    }
                    LocalMedia localMedia2 = (LocalMedia) concurrentHashMap.get(str);
                    if (localMedia2 != null) {
                        localMedia2.setWatermarkPath(str2);
                        concurrentHashMap.remove(str);
                    }
                    if (concurrentHashMap.size() == 0) {
                        PictureCommonFragment.this.dispatchWatermarkResult(arrayList);
                    }
                }
            });
        }
    }

    private boolean checkCompleteSelectLimit() {
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.selectionMode == 2 && !selectorConfig.isOnlyCamera) {
            if (selectorConfig.isWithVideoImage) {
                ArrayList<LocalMedia> selectedResult = selectorConfig.getSelectedResult();
                int i2 = 0;
                int i3 = 0;
                for (int i4 = 0; i4 < selectedResult.size(); i4++) {
                    if (PictureMimeType.isHasVideo(selectedResult.get(i4).getMimeType())) {
                        i3++;
                    } else {
                        i2++;
                    }
                }
                SelectorConfig selectorConfig2 = this.f19027d;
                int i5 = selectorConfig2.minSelectNum;
                if (i5 > 0 && i2 < i5) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener = selectorConfig2.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener != null && onSelectLimitTipsListener.onSelectLimitTips(i(), null, this.f19027d, 5)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_min_img_num, String.valueOf(this.f19027d.minSelectNum)));
                    return true;
                }
                int i6 = selectorConfig2.minVideoSelectNum;
                if (i6 > 0 && i3 < i6) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener2 = selectorConfig2.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener2 != null && onSelectLimitTipsListener2.onSelectLimitTips(i(), null, this.f19027d, 7)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_min_video_num, String.valueOf(this.f19027d.minVideoSelectNum)));
                    return true;
                }
            } else {
                String resultFirstMimeType = selectorConfig.getResultFirstMimeType();
                if (PictureMimeType.isHasImage(resultFirstMimeType)) {
                    SelectorConfig selectorConfig3 = this.f19027d;
                    if (selectorConfig3.minSelectNum > 0) {
                        int selectCount = selectorConfig3.getSelectCount();
                        SelectorConfig selectorConfig4 = this.f19027d;
                        if (selectCount < selectorConfig4.minSelectNum) {
                            OnSelectLimitTipsListener onSelectLimitTipsListener3 = selectorConfig4.onSelectLimitTipsListener;
                            if (onSelectLimitTipsListener3 != null && onSelectLimitTipsListener3.onSelectLimitTips(i(), null, this.f19027d, 5)) {
                                return true;
                            }
                            showTipsDialog(getString(R.string.ps_min_img_num, String.valueOf(this.f19027d.minSelectNum)));
                            return true;
                        }
                    }
                }
                if (PictureMimeType.isHasVideo(resultFirstMimeType)) {
                    SelectorConfig selectorConfig5 = this.f19027d;
                    if (selectorConfig5.minVideoSelectNum > 0) {
                        int selectCount2 = selectorConfig5.getSelectCount();
                        SelectorConfig selectorConfig6 = this.f19027d;
                        if (selectCount2 < selectorConfig6.minVideoSelectNum) {
                            OnSelectLimitTipsListener onSelectLimitTipsListener4 = selectorConfig6.onSelectLimitTipsListener;
                            if (onSelectLimitTipsListener4 != null && onSelectLimitTipsListener4.onSelectLimitTips(i(), null, this.f19027d, 7)) {
                                return true;
                            }
                            showTipsDialog(getString(R.string.ps_min_video_num, String.valueOf(this.f19027d.minVideoSelectNum)));
                            return true;
                        }
                    }
                }
                if (PictureMimeType.isHasAudio(resultFirstMimeType)) {
                    SelectorConfig selectorConfig7 = this.f19027d;
                    if (selectorConfig7.minAudioSelectNum > 0) {
                        int selectCount3 = selectorConfig7.getSelectCount();
                        SelectorConfig selectorConfig8 = this.f19027d;
                        if (selectCount3 < selectorConfig8.minAudioSelectNum) {
                            OnSelectLimitTipsListener onSelectLimitTipsListener5 = selectorConfig8.onSelectLimitTipsListener;
                            if (onSelectLimitTipsListener5 != null && onSelectLimitTipsListener5.onSelectLimitTips(i(), null, this.f19027d, 12)) {
                                return true;
                            }
                            showTipsDialog(getString(R.string.ps_min_audio_num, String.valueOf(this.f19027d.minAudioSelectNum)));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Deprecated
    private void copyExternalPathToAppInDirFor29(final ArrayList<LocalMedia> arrayList) {
        showLoading();
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<ArrayList<LocalMedia>>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.15
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public ArrayList<LocalMedia> doInBackground() {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    LocalMedia localMedia = (LocalMedia) arrayList.get(i2);
                    PictureCommonFragment pictureCommonFragment = PictureCommonFragment.this;
                    pictureCommonFragment.f19027d.sandboxFileEngine.onStartSandboxFileTransform(pictureCommonFragment.i(), PictureCommonFragment.this.f19027d.isCheckOriginalImage, i2, localMedia, new OnCallbackIndexListener<LocalMedia>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.15.1
                        @Override // com.luck.picture.lib.interfaces.OnCallbackIndexListener
                        public void onCall(LocalMedia localMedia2, int i3) {
                            LocalMedia localMedia3 = (LocalMedia) arrayList.get(i3);
                            localMedia3.setSandboxPath(localMedia2.getSandboxPath());
                            if (PictureCommonFragment.this.f19027d.isCheckOriginalImage) {
                                localMedia3.setOriginalPath(localMedia2.getOriginalPath());
                                localMedia3.setOriginal(!TextUtils.isEmpty(localMedia2.getOriginalPath()));
                            }
                        }
                    });
                }
                return arrayList;
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(ArrayList<LocalMedia> arrayList2) {
                PictureThreadUtils.cancel(this);
                PictureCommonFragment.this.dispatchUriToFileTransformResult(arrayList2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void copyOutputAudioToDir() {
        String str;
        try {
            if (TextUtils.isEmpty(this.f19027d.outPutAudioDir)) {
                return;
            }
            InputStream inputStreamOpenInputStream = PictureMimeType.isContent(this.f19027d.cameraPath) ? PictureContentResolver.openInputStream(i(), Uri.parse(this.f19027d.cameraPath)) : new FileInputStream(this.f19027d.cameraPath);
            if (TextUtils.isEmpty(this.f19027d.outPutAudioFileName)) {
                str = "";
            } else {
                SelectorConfig selectorConfig = this.f19027d;
                if (selectorConfig.isOnlyCamera) {
                    str = selectorConfig.outPutAudioFileName;
                } else {
                    str = System.currentTimeMillis() + OpenAccountUIConstants.UNDER_LINE + this.f19027d.outPutAudioFileName;
                }
            }
            Context contextI = i();
            SelectorConfig selectorConfig2 = this.f19027d;
            File fileCreateCameraFile = PictureFileUtils.createCameraFile(contextI, selectorConfig2.chooseMode, str, "", selectorConfig2.outPutAudioDir);
            if (PictureFileUtils.writeFileFromIS(inputStreamOpenInputStream, new FileOutputStream(fileCreateCameraFile.getAbsolutePath()))) {
                MediaUtils.deleteUri(i(), this.f19027d.cameraPath);
                this.f19027d.cameraPath = fileCreateCameraFile.getAbsolutePath();
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void createCompressEngine() {
        PictureSelectorEngine pictureSelectorEngine;
        PictureSelectorEngine pictureSelectorEngine2;
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isCompressEngine) {
            if (selectorConfig.compressFileEngine == null && (pictureSelectorEngine2 = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
                this.f19027d.compressFileEngine = pictureSelectorEngine2.createCompressFileEngine();
            }
            if (this.f19027d.compressEngine != null || (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) == null) {
                return;
            }
            this.f19027d.compressEngine = pictureSelectorEngine.createCompressEngine();
        }
    }

    private void createImageLoaderEngine() {
        PictureSelectorEngine pictureSelectorEngine;
        if (this.f19027d.imageEngine != null || (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) == null) {
            return;
        }
        this.f19027d.imageEngine = pictureSelectorEngine.createImageLoaderEngine();
    }

    private void createLayoutResourceListener() {
        PictureSelectorEngine pictureSelectorEngine;
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isInjectLayoutResource && selectorConfig.onLayoutResourceListener == null && (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
            this.f19027d.onLayoutResourceListener = pictureSelectorEngine.createLayoutResourceListener();
        }
    }

    private void createLoaderDataEngine() {
        PictureSelectorEngine pictureSelectorEngine;
        PictureSelectorEngine pictureSelectorEngine2;
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isLoaderDataEngine && selectorConfig.loaderDataEngine == null && (pictureSelectorEngine2 = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
            this.f19027d.loaderDataEngine = pictureSelectorEngine2.createLoaderDataEngine();
        }
        SelectorConfig selectorConfig2 = this.f19027d;
        if (selectorConfig2.isLoaderFactoryEngine && selectorConfig2.loaderFactory == null && (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
            this.f19027d.loaderFactory = pictureSelectorEngine.onCreateLoader();
        }
    }

    private void createResultCallbackListener() {
        PictureSelectorEngine pictureSelectorEngine;
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isResultListenerBack && selectorConfig.onResultCallListener == null && (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
            this.f19027d.onResultCallListener = pictureSelectorEngine.getResultCallbackListener();
        }
    }

    private void createSandboxFileEngine() {
        PictureSelectorEngine pictureSelectorEngine;
        PictureSelectorEngine pictureSelectorEngine2;
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isSandboxFileEngine) {
            if (selectorConfig.uriToFileTransformEngine == null && (pictureSelectorEngine2 = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
                this.f19027d.uriToFileTransformEngine = pictureSelectorEngine2.createUriToFileTransformEngine();
            }
            if (this.f19027d.sandboxFileEngine != null || (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) == null) {
                return;
            }
            this.f19027d.sandboxFileEngine = pictureSelectorEngine.createSandboxFileEngine();
        }
    }

    private void createVideoPlayerEngine() {
        PictureSelectorEngine pictureSelectorEngine;
        if (this.f19027d.videoPlayerEngine != null || (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) == null) {
            return;
        }
        this.f19027d.videoPlayerEngine = pictureSelectorEngine.createVideoPlayerEngine();
    }

    private void dispatchHandleCamera(final Intent intent) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<LocalMedia>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.9
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public LocalMedia doInBackground() throws Throwable {
                String strJ = PictureCommonFragment.this.j(intent);
                if (!TextUtils.isEmpty(strJ)) {
                    PictureCommonFragment.this.f19027d.cameraPath = strJ;
                }
                if (TextUtils.isEmpty(PictureCommonFragment.this.f19027d.cameraPath)) {
                    return null;
                }
                if (PictureCommonFragment.this.f19027d.chooseMode == SelectMimeType.ofAudio()) {
                    PictureCommonFragment.this.copyOutputAudioToDir();
                }
                PictureCommonFragment pictureCommonFragment = PictureCommonFragment.this;
                LocalMedia localMediaG = pictureCommonFragment.g(pictureCommonFragment.f19027d.cameraPath);
                localMediaG.setCameraSource(true);
                return localMediaG;
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(LocalMedia localMedia) {
                PictureThreadUtils.cancel(this);
                if (localMedia != null) {
                    PictureCommonFragment.this.onScannerScanFile(localMedia);
                    PictureCommonFragment.this.dispatchCameraMediaResult(localMedia);
                }
                PictureCommonFragment.this.f19027d.cameraPath = "";
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUriToFileTransformResult(ArrayList<LocalMedia> arrayList) {
        showLoading();
        if (checkAddBitmapWatermark()) {
            addBitmapWatermark(arrayList);
        } else if (checkVideoThumbnail()) {
            videoThumbnail(arrayList);
        } else {
            onCallBackResult(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchWatermarkResult(ArrayList<LocalMedia> arrayList) {
        if (checkVideoThumbnail()) {
            videoThumbnail(arrayList);
        } else {
            onCallBackResult(arrayList);
        }
    }

    @SuppressLint({"StringFormatInvalid"})
    private static String getTipsMsg(Context context, String str, int i2) {
        return PictureMimeType.isHasVideo(str) ? context.getString(R.string.ps_message_video_max_num, String.valueOf(i2)) : PictureMimeType.isHasAudio(str) ? context.getString(R.string.ps_message_audio_max_num, String.valueOf(i2)) : context.getString(R.string.ps_message_max_num, String.valueOf(i2));
    }

    private void mergeOriginalImage(ArrayList<LocalMedia> arrayList) {
        if (this.f19027d.isCheckOriginalImage) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                LocalMedia localMedia = arrayList.get(i2);
                localMedia.setOriginal(true);
                localMedia.setOriginalPath(localMedia.getPath());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCallBackResult(ArrayList<LocalMedia> arrayList) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        dismissLoading();
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isActivityResultBack) {
            getActivity().setResult(-1, PictureSelector.putIntentResult(arrayList));
            p(-1, arrayList);
        } else {
            OnResultCallbackListener<LocalMedia> onResultCallbackListener = selectorConfig.onResultCallListener;
            if (onResultCallbackListener != null) {
                onResultCallbackListener.onResult(arrayList);
            }
        }
        o();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScannerScanFile(LocalMedia localMedia) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (SdkVersionUtils.isQ()) {
            if (PictureMimeType.isHasVideo(localMedia.getMimeType()) && PictureMimeType.isContent(localMedia.getPath())) {
                new PictureMediaScannerConnection(getActivity(), localMedia.getRealPath());
                return;
            }
            return;
        }
        String realPath = PictureMimeType.isContent(localMedia.getPath()) ? localMedia.getRealPath() : localMedia.getPath();
        new PictureMediaScannerConnection(getActivity(), realPath);
        if (PictureMimeType.isHasImage(localMedia.getMimeType())) {
            int dCIMLastImageId = MediaUtils.getDCIMLastImageId(i(), new File(realPath).getParent());
            if (dCIMLastImageId != -1) {
                MediaUtils.removeMedia(i(), dCIMLastImageId);
            }
        }
    }

    private void playClickEffect() {
        SoundPool soundPool = this.soundPool;
        if (soundPool == null || !this.f19027d.isOpenClickSound) {
            return;
        }
        soundPool.play(this.soundID, 0.1f, 0.5f, 0, 1, 1.0f);
    }

    private void releaseSoundPool() {
        try {
            SoundPool soundPool = this.soundPool;
            if (soundPool != null) {
                soundPool.release();
                this.soundPool = null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void setTranslucentStatusBar() {
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isPreviewFullScreenMode) {
            ImmersiveManager.translucentStatusBar(requireActivity(), selectorConfig.selectorStyle.getSelectMainStyle().isDarkStatusBarBlack());
        }
    }

    private void showTipsDialog(String str) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        try {
            Dialog dialog = this.f19028e;
            if (dialog == null || !dialog.isShowing()) {
                RemindDialog remindDialogBuildDialog = RemindDialog.buildDialog(i(), str);
                this.f19028e = remindDialogBuildDialog;
                remindDialogBuildDialog.show();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void uriToFileTransform29(final ArrayList<LocalMedia> arrayList) {
        showLoading();
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            LocalMedia localMedia = arrayList.get(i2);
            concurrentHashMap.put(localMedia.getPath(), localMedia);
        }
        if (concurrentHashMap.size() == 0) {
            dispatchUriToFileTransformResult(arrayList);
        } else {
            PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<ArrayList<LocalMedia>>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.14
                @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
                public ArrayList<LocalMedia> doInBackground() {
                    Iterator it = concurrentHashMap.entrySet().iterator();
                    while (it.hasNext()) {
                        LocalMedia localMedia2 = (LocalMedia) ((Map.Entry) it.next()).getValue();
                        if (PictureCommonFragment.this.f19027d.isCheckOriginalImage || TextUtils.isEmpty(localMedia2.getSandboxPath())) {
                            PictureCommonFragment pictureCommonFragment = PictureCommonFragment.this;
                            pictureCommonFragment.f19027d.uriToFileTransformEngine.onUriToFileAsyncTransform(pictureCommonFragment.i(), localMedia2.getPath(), localMedia2.getMimeType(), new OnKeyValueResultCallbackListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.14.1
                                @Override // com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
                                public void onCallback(String str, String str2) {
                                    LocalMedia localMedia3;
                                    if (TextUtils.isEmpty(str) || (localMedia3 = (LocalMedia) concurrentHashMap.get(str)) == null) {
                                        return;
                                    }
                                    if (TextUtils.isEmpty(localMedia3.getSandboxPath())) {
                                        localMedia3.setSandboxPath(str2);
                                    }
                                    if (PictureCommonFragment.this.f19027d.isCheckOriginalImage) {
                                        localMedia3.setOriginalPath(str2);
                                        localMedia3.setOriginal(!TextUtils.isEmpty(str2));
                                    }
                                    concurrentHashMap.remove(str);
                                }
                            });
                        }
                    }
                    return arrayList;
                }

                @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
                public void onSuccess(ArrayList<LocalMedia> arrayList2) {
                    PictureThreadUtils.cancel(this);
                    PictureCommonFragment.this.dispatchUriToFileTransformResult(arrayList2);
                }
            });
        }
    }

    private void videoThumbnail(final ArrayList<LocalMedia> arrayList) {
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            LocalMedia localMedia = arrayList.get(i2);
            String availablePath = localMedia.getAvailablePath();
            if (PictureMimeType.isHasVideo(localMedia.getMimeType()) || PictureMimeType.isUrlHasVideo(availablePath)) {
                concurrentHashMap.put(availablePath, localMedia);
            }
        }
        if (concurrentHashMap.size() == 0) {
            onCallBackResult(arrayList);
            return;
        }
        Iterator it = concurrentHashMap.entrySet().iterator();
        while (it.hasNext()) {
            this.f19027d.onVideoThumbnailEventListener.onVideoThumbnail(i(), (String) ((Map.Entry) it.next()).getKey(), new OnKeyValueResultCallbackListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.12
                @Override // com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
                public void onCallback(String str, String str2) {
                    LocalMedia localMedia2 = (LocalMedia) concurrentHashMap.get(str);
                    if (localMedia2 != null) {
                        localMedia2.setVideoThumbnailPath(str2);
                        concurrentHashMap.remove(str);
                    }
                    if (concurrentHashMap.size() == 0) {
                        PictureCommonFragment.this.onCallBackResult(arrayList);
                    }
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public boolean checkAddBitmapWatermark() {
        return this.f19027d.onBitmapWatermarkListener != null;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public boolean checkCompressValidity() {
        if (this.f19027d.compressFileEngine != null) {
            for (int i2 = 0; i2 < this.f19027d.getSelectCount(); i2++) {
                if (PictureMimeType.isHasImage(this.f19027d.getSelectedResult().get(i2).getMimeType())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public boolean checkCropValidity() {
        if (this.f19027d.cropFileEngine == null) {
            return false;
        }
        HashSet hashSet = new HashSet();
        List<String> list = this.f19027d.skipCropList;
        if (list != null && list.size() > 0) {
            hashSet.addAll(list);
        }
        if (this.f19027d.getSelectCount() == 1) {
            String resultFirstMimeType = this.f19027d.getResultFirstMimeType();
            boolean zIsHasImage = PictureMimeType.isHasImage(resultFirstMimeType);
            if (zIsHasImage && hashSet.contains(resultFirstMimeType)) {
                return false;
            }
            return zIsHasImage;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.f19027d.getSelectCount(); i3++) {
            LocalMedia localMedia = this.f19027d.getSelectedResult().get(i3);
            if (PictureMimeType.isHasImage(localMedia.getMimeType()) && hashSet.contains(localMedia.getMimeType())) {
                i2++;
            }
        }
        return i2 != this.f19027d.getSelectCount();
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public boolean checkOldCompressValidity() {
        if (this.f19027d.compressEngine != null) {
            for (int i2 = 0; i2 < this.f19027d.getSelectCount(); i2++) {
                if (PictureMimeType.isHasImage(this.f19027d.getSelectedResult().get(i2).getMimeType())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public boolean checkOldCropValidity() {
        if (this.f19027d.cropEngine == null) {
            return false;
        }
        HashSet hashSet = new HashSet();
        List<String> list = this.f19027d.skipCropList;
        if (list != null && list.size() > 0) {
            hashSet.addAll(list);
        }
        if (this.f19027d.getSelectCount() == 1) {
            String resultFirstMimeType = this.f19027d.getResultFirstMimeType();
            boolean zIsHasImage = PictureMimeType.isHasImage(resultFirstMimeType);
            if (zIsHasImage && hashSet.contains(resultFirstMimeType)) {
                return false;
            }
            return zIsHasImage;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.f19027d.getSelectCount(); i3++) {
            LocalMedia localMedia = this.f19027d.getSelectedResult().get(i3);
            if (PictureMimeType.isHasImage(localMedia.getMimeType()) && hashSet.contains(localMedia.getMimeType())) {
                i2++;
            }
        }
        return i2 != this.f19027d.getSelectCount();
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public boolean checkOldTransformSandboxFile() {
        return SdkVersionUtils.isQ() && this.f19027d.sandboxFileEngine != null;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    @SuppressLint({"StringFormatInvalid"})
    public boolean checkOnlyMimeTypeValidity(LocalMedia localMedia, boolean z2, String str, String str2, long j2, long j3) {
        if (!PictureMimeType.isMimeTypeSame(str2, str)) {
            OnSelectLimitTipsListener onSelectLimitTipsListener = this.f19027d.onSelectLimitTipsListener;
            if (onSelectLimitTipsListener != null && onSelectLimitTipsListener.onSelectLimitTips(i(), localMedia, this.f19027d, 3)) {
                return true;
            }
            showTipsDialog(getString(R.string.ps_rule));
            return true;
        }
        SelectorConfig selectorConfig = this.f19027d;
        long j4 = selectorConfig.selectMaxFileSize;
        if (j4 > 0 && j2 > j4) {
            OnSelectLimitTipsListener onSelectLimitTipsListener2 = selectorConfig.onSelectLimitTipsListener;
            if (onSelectLimitTipsListener2 != null && onSelectLimitTipsListener2.onSelectLimitTips(i(), localMedia, this.f19027d, 1)) {
                return true;
            }
            showTipsDialog(getString(R.string.ps_select_max_size, PictureFileUtils.formatFileSize(this.f19027d.selectMaxFileSize)));
            return true;
        }
        long j5 = selectorConfig.selectMinFileSize;
        if (j5 > 0 && j2 < j5) {
            OnSelectLimitTipsListener onSelectLimitTipsListener3 = selectorConfig.onSelectLimitTipsListener;
            if (onSelectLimitTipsListener3 != null && onSelectLimitTipsListener3.onSelectLimitTips(i(), localMedia, this.f19027d, 2)) {
                return true;
            }
            showTipsDialog(getString(R.string.ps_select_min_size, PictureFileUtils.formatFileSize(this.f19027d.selectMinFileSize)));
            return true;
        }
        if (PictureMimeType.isHasVideo(str)) {
            SelectorConfig selectorConfig2 = this.f19027d;
            if (selectorConfig2.selectionMode == 2) {
                int i2 = selectorConfig2.maxVideoSelectNum;
                if (i2 <= 0) {
                    i2 = selectorConfig2.maxSelectNum;
                }
                selectorConfig2.maxVideoSelectNum = i2;
                if (!z2) {
                    int selectCount = selectorConfig2.getSelectCount();
                    SelectorConfig selectorConfig3 = this.f19027d;
                    if (selectCount >= selectorConfig3.maxVideoSelectNum) {
                        OnSelectLimitTipsListener onSelectLimitTipsListener4 = selectorConfig3.onSelectLimitTipsListener;
                        if (onSelectLimitTipsListener4 != null && onSelectLimitTipsListener4.onSelectLimitTips(i(), localMedia, this.f19027d, 6)) {
                            return true;
                        }
                        showTipsDialog(getTipsMsg(i(), str, this.f19027d.maxVideoSelectNum));
                        return true;
                    }
                }
            }
            if (!z2 && this.f19027d.selectMinDurationSecond > 0) {
                long jMillisecondToSecond = DateUtils.millisecondToSecond(j3);
                SelectorConfig selectorConfig4 = this.f19027d;
                if (jMillisecondToSecond < selectorConfig4.selectMinDurationSecond) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener5 = selectorConfig4.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener5 != null && onSelectLimitTipsListener5.onSelectLimitTips(i(), localMedia, this.f19027d, 9)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_select_video_min_second, Integer.valueOf(this.f19027d.selectMinDurationSecond / 1000)));
                    return true;
                }
            }
            if (!z2 && this.f19027d.selectMaxDurationSecond > 0) {
                long jMillisecondToSecond2 = DateUtils.millisecondToSecond(j3);
                SelectorConfig selectorConfig5 = this.f19027d;
                if (jMillisecondToSecond2 > selectorConfig5.selectMaxDurationSecond) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener6 = selectorConfig5.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener6 != null && onSelectLimitTipsListener6.onSelectLimitTips(i(), localMedia, this.f19027d, 8)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_select_video_max_second, Integer.valueOf(this.f19027d.selectMaxDurationSecond / 1000)));
                    return true;
                }
            }
        } else if (PictureMimeType.isHasAudio(str)) {
            SelectorConfig selectorConfig6 = this.f19027d;
            if (selectorConfig6.selectionMode == 2 && !z2) {
                int size = selectorConfig6.getSelectedResult().size();
                SelectorConfig selectorConfig7 = this.f19027d;
                if (size >= selectorConfig7.maxSelectNum) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener7 = selectorConfig7.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener7 != null && onSelectLimitTipsListener7.onSelectLimitTips(i(), localMedia, this.f19027d, 4)) {
                        return true;
                    }
                    showTipsDialog(getTipsMsg(i(), str, this.f19027d.maxSelectNum));
                    return true;
                }
            }
            if (!z2 && this.f19027d.selectMinDurationSecond > 0) {
                long jMillisecondToSecond3 = DateUtils.millisecondToSecond(j3);
                SelectorConfig selectorConfig8 = this.f19027d;
                if (jMillisecondToSecond3 < selectorConfig8.selectMinDurationSecond) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener8 = selectorConfig8.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener8 != null && onSelectLimitTipsListener8.onSelectLimitTips(i(), localMedia, this.f19027d, 11)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_select_audio_min_second, Integer.valueOf(this.f19027d.selectMinDurationSecond / 1000)));
                    return true;
                }
            }
            if (!z2 && this.f19027d.selectMaxDurationSecond > 0) {
                long jMillisecondToSecond4 = DateUtils.millisecondToSecond(j3);
                SelectorConfig selectorConfig9 = this.f19027d;
                if (jMillisecondToSecond4 > selectorConfig9.selectMaxDurationSecond) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener9 = selectorConfig9.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener9 != null && onSelectLimitTipsListener9.onSelectLimitTips(i(), localMedia, this.f19027d, 10)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_select_audio_max_second, Integer.valueOf(this.f19027d.selectMaxDurationSecond / 1000)));
                    return true;
                }
            }
        } else {
            SelectorConfig selectorConfig10 = this.f19027d;
            if (selectorConfig10.selectionMode == 2 && !z2) {
                int size2 = selectorConfig10.getSelectedResult().size();
                SelectorConfig selectorConfig11 = this.f19027d;
                if (size2 >= selectorConfig11.maxSelectNum) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener10 = selectorConfig11.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener10 != null && onSelectLimitTipsListener10.onSelectLimitTips(i(), localMedia, this.f19027d, 4)) {
                        return true;
                    }
                    showTipsDialog(getTipsMsg(i(), str, this.f19027d.maxSelectNum));
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public boolean checkTransformSandboxFile() {
        return SdkVersionUtils.isQ() && this.f19027d.uriToFileTransformEngine != null;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public boolean checkVideoThumbnail() {
        return this.f19027d.onVideoThumbnailEventListener != null;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    @SuppressLint({"StringFormatInvalid", "StringFormatMatches"})
    public boolean checkWithMimeTypeValidity(LocalMedia localMedia, boolean z2, String str, int i2, long j2, long j3) {
        SelectorConfig selectorConfig = this.f19027d;
        long j4 = selectorConfig.selectMaxFileSize;
        if (j4 > 0 && j2 > j4) {
            OnSelectLimitTipsListener onSelectLimitTipsListener = selectorConfig.onSelectLimitTipsListener;
            if (onSelectLimitTipsListener != null && onSelectLimitTipsListener.onSelectLimitTips(i(), localMedia, this.f19027d, 1)) {
                return true;
            }
            showTipsDialog(getString(R.string.ps_select_max_size, PictureFileUtils.formatFileSize(this.f19027d.selectMaxFileSize)));
            return true;
        }
        long j5 = selectorConfig.selectMinFileSize;
        if (j5 > 0 && j2 < j5) {
            OnSelectLimitTipsListener onSelectLimitTipsListener2 = selectorConfig.onSelectLimitTipsListener;
            if (onSelectLimitTipsListener2 != null && onSelectLimitTipsListener2.onSelectLimitTips(i(), localMedia, this.f19027d, 2)) {
                return true;
            }
            showTipsDialog(getString(R.string.ps_select_min_size, PictureFileUtils.formatFileSize(this.f19027d.selectMinFileSize)));
            return true;
        }
        if (PictureMimeType.isHasVideo(str)) {
            SelectorConfig selectorConfig2 = this.f19027d;
            if (selectorConfig2.selectionMode == 2) {
                if (selectorConfig2.maxVideoSelectNum <= 0) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener3 = selectorConfig2.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener3 != null && onSelectLimitTipsListener3.onSelectLimitTips(i(), localMedia, this.f19027d, 3)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_rule));
                    return true;
                }
                if (!z2) {
                    int size = selectorConfig2.getSelectedResult().size();
                    SelectorConfig selectorConfig3 = this.f19027d;
                    if (size >= selectorConfig3.maxSelectNum) {
                        OnSelectLimitTipsListener onSelectLimitTipsListener4 = selectorConfig3.onSelectLimitTipsListener;
                        if (onSelectLimitTipsListener4 != null && onSelectLimitTipsListener4.onSelectLimitTips(i(), localMedia, this.f19027d, 4)) {
                            return true;
                        }
                        showTipsDialog(getString(R.string.ps_message_max_num, Integer.valueOf(this.f19027d.maxSelectNum)));
                        return true;
                    }
                }
                if (!z2) {
                    SelectorConfig selectorConfig4 = this.f19027d;
                    if (i2 >= selectorConfig4.maxVideoSelectNum) {
                        OnSelectLimitTipsListener onSelectLimitTipsListener5 = selectorConfig4.onSelectLimitTipsListener;
                        if (onSelectLimitTipsListener5 != null && onSelectLimitTipsListener5.onSelectLimitTips(i(), localMedia, this.f19027d, 6)) {
                            return true;
                        }
                        showTipsDialog(getTipsMsg(i(), str, this.f19027d.maxVideoSelectNum));
                        return true;
                    }
                }
            }
            if (!z2 && this.f19027d.selectMinDurationSecond > 0) {
                long jMillisecondToSecond = DateUtils.millisecondToSecond(j3);
                SelectorConfig selectorConfig5 = this.f19027d;
                if (jMillisecondToSecond < selectorConfig5.selectMinDurationSecond) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener6 = selectorConfig5.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener6 != null && onSelectLimitTipsListener6.onSelectLimitTips(i(), localMedia, this.f19027d, 9)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_select_video_min_second, Integer.valueOf(this.f19027d.selectMinDurationSecond / 1000)));
                    return true;
                }
            }
            if (!z2 && this.f19027d.selectMaxDurationSecond > 0) {
                long jMillisecondToSecond2 = DateUtils.millisecondToSecond(j3);
                SelectorConfig selectorConfig6 = this.f19027d;
                if (jMillisecondToSecond2 > selectorConfig6.selectMaxDurationSecond) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener7 = selectorConfig6.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener7 != null && onSelectLimitTipsListener7.onSelectLimitTips(i(), localMedia, this.f19027d, 8)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_select_video_max_second, Integer.valueOf(this.f19027d.selectMaxDurationSecond / 1000)));
                    return true;
                }
            }
        } else {
            SelectorConfig selectorConfig7 = this.f19027d;
            if (selectorConfig7.selectionMode == 2 && !z2) {
                int size2 = selectorConfig7.getSelectedResult().size();
                SelectorConfig selectorConfig8 = this.f19027d;
                if (size2 >= selectorConfig8.maxSelectNum) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener8 = selectorConfig8.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener8 != null && onSelectLimitTipsListener8.onSelectLimitTips(i(), localMedia, this.f19027d, 4)) {
                        return true;
                    }
                    showTipsDialog(getString(R.string.ps_message_max_num, Integer.valueOf(this.f19027d.maxSelectNum)));
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public int confirmSelect(LocalMedia localMedia, boolean z2) {
        OnSelectFilterListener onSelectFilterListener = this.f19027d.onSelectFilterListener;
        int i2 = 0;
        if (onSelectFilterListener != null && onSelectFilterListener.onSelectFilter(localMedia)) {
            OnSelectLimitTipsListener onSelectLimitTipsListener = this.f19027d.onSelectLimitTipsListener;
            if (!(onSelectLimitTipsListener != null ? onSelectLimitTipsListener.onSelectLimitTips(i(), localMedia, this.f19027d, 13) : false)) {
                ToastUtils.showToast(i(), getString(R.string.ps_select_no_support));
            }
            return -1;
        }
        if (l(localMedia, z2) != 200) {
            return -1;
        }
        ArrayList<LocalMedia> selectedResult = this.f19027d.getSelectedResult();
        if (z2) {
            selectedResult.remove(localMedia);
            i2 = 1;
        } else {
            if (this.f19027d.selectionMode == 1 && selectedResult.size() > 0) {
                sendFixedSelectedChangeEvent(selectedResult.get(0));
                selectedResult.clear();
            }
            selectedResult.add(localMedia);
            localMedia.setNum(selectedResult.size());
            playClickEffect();
        }
        sendSelectedChangeEvent(i2 ^ 1, localMedia);
        return i2;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void dismissLoading() {
        try {
            if (!ActivityCompatHelper.isDestroy(getActivity()) && this.mLoadingDialog.isShowing()) {
                this.mLoadingDialog.dismiss();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void dispatchCameraMediaResult(LocalMedia localMedia) {
    }

    protected LocalMedia g(String str) throws Throwable {
        LocalMedia localMediaGenerateLocalMedia = LocalMedia.generateLocalMedia(i(), str);
        localMediaGenerateLocalMedia.setChooseModel(this.f19027d.chooseMode);
        if (!SdkVersionUtils.isQ() || PictureMimeType.isContent(str)) {
            localMediaGenerateLocalMedia.setSandboxPath(null);
        } else {
            localMediaGenerateLocalMedia.setSandboxPath(str);
        }
        if (this.f19027d.isCameraRotateImage && PictureMimeType.isHasImage(localMediaGenerateLocalMedia.getMimeType())) {
            BitmapUtils.rotateImage(i(), str);
        }
        return localMediaGenerateLocalMedia;
    }

    public long getEnterAnimationDuration() {
        long j2 = this.enterAnimDuration;
        if (j2 > 50) {
            j2 -= 50;
        }
        if (j2 >= 0) {
            return j2;
        }
        return 0L;
    }

    public String getFragmentTag() {
        return TAG;
    }

    public int getResourceId() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void h() {
        if (!checkCompleteSelectLimit() && isAdded()) {
            ArrayList<LocalMedia> arrayList = new ArrayList<>(this.f19027d.getSelectedResult());
            if (checkCropValidity()) {
                onCrop(arrayList);
                return;
            }
            if (checkOldCropValidity()) {
                onOldCrop(arrayList);
                return;
            }
            if (checkCompressValidity()) {
                onCompress(arrayList);
            } else if (checkOldCompressValidity()) {
                onOldCompress(arrayList);
            } else {
                onResultEvent(arrayList);
            }
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void handlePermissionDenied(String[] strArr) {
        PermissionConfig.CURRENT_REQUEST_PERMISSION = strArr;
        if (this.f19027d.onPermissionDeniedListener == null) {
            PermissionUtil.goIntentSetting(this, PictureConfig.REQUEST_GO_SETTING);
        } else {
            onPermissionExplainEvent(false, strArr);
            this.f19027d.onPermissionDeniedListener.onDenied(this, strArr, PictureConfig.REQUEST_GO_SETTING, new OnCallbackListener<Boolean>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.1
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(Boolean bool) {
                    if (bool.booleanValue()) {
                        PictureCommonFragment.this.handlePermissionSettingResult(PermissionConfig.CURRENT_REQUEST_PERMISSION);
                    }
                }
            });
        }
    }

    public void handlePermissionSettingResult(String[] strArr) {
    }

    protected Context i() {
        Context context = getContext();
        if (context != null) {
            return context;
        }
        Context appContext = PictureAppMaster.getInstance().getAppContext();
        return appContext != null ? appContext : this.context;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void initAppLanguage() {
        if (this.f19027d == null) {
            this.f19027d = SelectorProviders.getInstance().getSelectorConfig();
        }
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig == null || selectorConfig.language == -2) {
            return;
        }
        FragmentActivity activity = getActivity();
        SelectorConfig selectorConfig2 = this.f19027d;
        PictureLanguageUtils.setAppLanguage(activity, selectorConfig2.language, selectorConfig2.defaultLanguage);
    }

    protected String j(Intent intent) {
        if (intent == null) {
            return null;
        }
        Uri data = (Uri) intent.getParcelableExtra("output");
        String str = this.f19027d.cameraPath;
        boolean z2 = TextUtils.isEmpty(str) || PictureMimeType.isContent(str) || new File(str).exists();
        if ((this.f19027d.chooseMode == SelectMimeType.ofAudio() || !z2) && data == null) {
            data = intent.getData();
        }
        if (data == null) {
            return null;
        }
        return PictureMimeType.isContent(data.toString()) ? data.toString() : data.getPath();
    }

    protected SelectorResult k(int i2, ArrayList arrayList) {
        return new SelectorResult(i2, arrayList != null ? PictureSelector.putIntentResult(arrayList) : null);
    }

    protected int l(LocalMedia localMedia, boolean z2) {
        String mimeType = localMedia.getMimeType();
        long duration = localMedia.getDuration();
        long size = localMedia.getSize();
        ArrayList<LocalMedia> selectedResult = this.f19027d.getSelectedResult();
        SelectorConfig selectorConfig = this.f19027d;
        if (!selectorConfig.isWithVideoImage) {
            return checkOnlyMimeTypeValidity(localMedia, z2, mimeType, selectorConfig.getResultFirstMimeType(), size, duration) ? -1 : 200;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < selectedResult.size(); i3++) {
            if (PictureMimeType.isHasVideo(selectedResult.get(i3).getMimeType())) {
                i2++;
            }
        }
        return checkWithMimeTypeValidity(localMedia, z2, mimeType, i2, size, duration) ? -1 : 200;
    }

    protected boolean m() {
        return (getActivity() instanceof PictureSelectorSupporterActivity) || (getActivity() instanceof PictureSelectorTransparentActivity);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void n() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (!isStateSaved()) {
            IBridgeViewLifecycle iBridgeViewLifecycle = this.f19027d.viewLifecycle;
            if (iBridgeViewLifecycle != null) {
                iBridgeViewLifecycle.onDestroy(this);
            }
            getActivity().getSupportFragmentManager().popBackStack();
        }
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (int i2 = 0; i2 < fragments.size(); i2++) {
            Fragment fragment = fragments.get(i2);
            if (fragment instanceof PictureCommonFragment) {
                ((PictureCommonFragment) fragment).onFragmentResume();
            }
        }
    }

    protected void o() {
        if (!ActivityCompatHelper.isDestroy(getActivity())) {
            if (m()) {
                IBridgeViewLifecycle iBridgeViewLifecycle = this.f19027d.viewLifecycle;
                if (iBridgeViewLifecycle != null) {
                    iBridgeViewLifecycle.onDestroy(this);
                }
                getActivity().finish();
            } else {
                List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
                for (int i2 = 0; i2 < fragments.size(); i2++) {
                    if (fragments.get(i2) instanceof PictureCommonFragment) {
                        n();
                    }
                }
            }
        }
        SelectorProviders.getInstance().destroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i3 == -1) {
            if (i2 == 909) {
                dispatchHandleCamera(intent);
            } else if (i2 == 696) {
                onEditMedia(intent);
            } else if (i2 == 69) {
                ArrayList<LocalMedia> selectedResult = this.f19027d.getSelectedResult();
                try {
                    if (selectedResult.size() == 1) {
                        LocalMedia localMedia = selectedResult.get(0);
                        Uri output = Crop.getOutput(intent);
                        localMedia.setCutPath(output != null ? output.getPath() : "");
                        localMedia.setCut(!TextUtils.isEmpty(localMedia.getCutPath()));
                        localMedia.setCropImageWidth(Crop.getOutputImageWidth(intent));
                        localMedia.setCropImageHeight(Crop.getOutputImageHeight(intent));
                        localMedia.setCropOffsetX(Crop.getOutputImageOffsetX(intent));
                        localMedia.setCropOffsetY(Crop.getOutputImageOffsetY(intent));
                        localMedia.setCropResultAspectRatio(Crop.getOutputCropAspectRatio(intent));
                        localMedia.setCustomData(Crop.getOutputCustomExtraData(intent));
                        localMedia.setSandboxPath(localMedia.getCutPath());
                    } else {
                        String stringExtra = intent.getStringExtra("output");
                        if (TextUtils.isEmpty(stringExtra)) {
                            stringExtra = intent.getStringExtra("com.yalantis.ucrop.OutputUri");
                        }
                        JSONArray jSONArray = new JSONArray(stringExtra);
                        if (jSONArray.length() == selectedResult.size()) {
                            for (int i4 = 0; i4 < selectedResult.size(); i4++) {
                                LocalMedia localMedia2 = selectedResult.get(i4);
                                JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i4);
                                localMedia2.setCutPath(jSONObjectOptJSONObject.optString(CustomIntentKey.EXTRA_OUT_PUT_PATH));
                                localMedia2.setCut(!TextUtils.isEmpty(localMedia2.getCutPath()));
                                localMedia2.setCropImageWidth(jSONObjectOptJSONObject.optInt(CustomIntentKey.EXTRA_IMAGE_WIDTH));
                                localMedia2.setCropImageHeight(jSONObjectOptJSONObject.optInt(CustomIntentKey.EXTRA_IMAGE_HEIGHT));
                                localMedia2.setCropOffsetX(jSONObjectOptJSONObject.optInt(CustomIntentKey.EXTRA_OFFSET_X));
                                localMedia2.setCropOffsetY(jSONObjectOptJSONObject.optInt(CustomIntentKey.EXTRA_OFFSET_Y));
                                localMedia2.setCropResultAspectRatio((float) jSONObjectOptJSONObject.optDouble(CustomIntentKey.EXTRA_ASPECT_RATIO));
                                localMedia2.setCustomData(jSONObjectOptJSONObject.optString(CustomIntentKey.EXTRA_CUSTOM_EXTRA_DATA));
                                localMedia2.setSandboxPath(localMedia2.getCutPath());
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ToastUtils.showToast(i(), e2.getMessage());
                }
                ArrayList<LocalMedia> arrayList = new ArrayList<>(selectedResult);
                if (checkCompressValidity()) {
                    onCompress(arrayList);
                } else if (checkOldCompressValidity()) {
                    onOldCompress(arrayList);
                } else {
                    onResultEvent(arrayList);
                }
            }
        } else if (i3 == 96) {
            Throwable error = intent != null ? Crop.getError(intent) : new Throwable("image crop error");
            if (error != null) {
                ToastUtils.showToast(i(), error.getMessage());
            }
        } else if (i3 == 0) {
            if (i2 == 909) {
                if (!TextUtils.isEmpty(this.f19027d.cameraPath)) {
                    MediaUtils.deleteUri(i(), this.f19027d.cameraPath);
                    this.f19027d.cameraPath = "";
                }
            } else if (i2 == 1102) {
                handlePermissionSettingResult(PermissionConfig.CURRENT_REQUEST_PERMISSION);
            }
        }
        ForegroundService.stopService(i());
    }

    public void onApplyPermissionsEvent(final int i2, String[] strArr) {
        this.f19027d.onPermissionsEventListener.requestPermission(this, strArr, new OnRequestPermissionListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.8
            @Override // com.luck.picture.lib.interfaces.OnRequestPermissionListener
            public void onCall(String[] strArr2, boolean z2) {
                if (!z2) {
                    PictureCommonFragment.this.handlePermissionDenied(strArr2);
                } else if (i2 == PermissionEvent.EVENT_VIDEO_CAMERA) {
                    PictureCommonFragment.this.s();
                } else {
                    PictureCommonFragment.this.r();
                }
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        initAppLanguage();
        onRecreateEngine();
        super.onAttach(context);
        this.context = context;
        if (getParentFragment() instanceof IBridgePictureBehavior) {
            this.f19024a = (IBridgePictureBehavior) getParentFragment();
        } else if (context instanceof IBridgePictureBehavior) {
            this.f19024a = (IBridgePictureBehavior) context;
        }
    }

    public void onCheckOriginalChange() {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onCompress(final ArrayList<LocalMedia> arrayList) {
        showLoading();
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        ArrayList<Uri> arrayList2 = new ArrayList<>();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            LocalMedia localMedia = arrayList.get(i2);
            String availablePath = localMedia.getAvailablePath();
            if (!PictureMimeType.isHasHttp(availablePath)) {
                SelectorConfig selectorConfig = this.f19027d;
                if ((!selectorConfig.isCheckOriginalImage || !selectorConfig.isOriginalSkipCompress) && PictureMimeType.isHasImage(localMedia.getMimeType())) {
                    arrayList2.add(PictureMimeType.isContent(availablePath) ? Uri.parse(availablePath) : Uri.fromFile(new File(availablePath)));
                    concurrentHashMap.put(availablePath, localMedia);
                }
            }
        }
        if (concurrentHashMap.size() == 0) {
            onResultEvent(arrayList);
        } else {
            this.f19027d.compressFileEngine.onStartCompress(i(), arrayList2, new OnKeyValueResultCallbackListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.10
                @Override // com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
                public void onCallback(String str, String str2) {
                    if (TextUtils.isEmpty(str)) {
                        PictureCommonFragment.this.onResultEvent(arrayList);
                        return;
                    }
                    LocalMedia localMedia2 = (LocalMedia) concurrentHashMap.get(str);
                    if (localMedia2 != null) {
                        if (!SdkVersionUtils.isQ()) {
                            localMedia2.setCompressPath(str2);
                            localMedia2.setCompressed(!TextUtils.isEmpty(str2));
                        } else if (!TextUtils.isEmpty(str2) && (str2.contains("Android/data/") || str2.contains("data/user/"))) {
                            localMedia2.setCompressPath(str2);
                            localMedia2.setCompressed(!TextUtils.isEmpty(str2));
                            localMedia2.setSandboxPath(localMedia2.getCompressPath());
                        }
                        concurrentHashMap.remove(str);
                    }
                    if (concurrentHashMap.size() == 0) {
                        PictureCommonFragment.this.onResultEvent(arrayList);
                    }
                }
            });
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initAppLanguage();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public Animation onCreateAnimation(int i2, boolean z2, int i3) {
        Animation animationLoadAnimation;
        PictureWindowAnimationStyle windowAnimationStyle = this.f19027d.selectorStyle.getWindowAnimationStyle();
        if (z2) {
            animationLoadAnimation = windowAnimationStyle.activityEnterAnimation != 0 ? AnimationUtils.loadAnimation(i(), windowAnimationStyle.activityEnterAnimation) : AnimationUtils.loadAnimation(i(), R.anim.ps_anim_alpha_enter);
            setEnterAnimationDuration(animationLoadAnimation.getDuration());
            onEnterFragment();
        } else {
            animationLoadAnimation = windowAnimationStyle.activityExitAnimation != 0 ? AnimationUtils.loadAnimation(i(), windowAnimationStyle.activityExitAnimation) : AnimationUtils.loadAnimation(i(), R.anim.ps_anim_alpha_exit);
            onExitFragment();
        }
        return animationLoadAnimation;
    }

    public void onCreateLoader() {
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return getResourceId() != 0 ? layoutInflater.inflate(getResourceId(), viewGroup, false) : super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onCrop(ArrayList<LocalMedia> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        Uri uriFromFile = null;
        Uri uriFromFile2 = null;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            LocalMedia localMedia = arrayList.get(i2);
            arrayList2.add(localMedia.getAvailablePath());
            if (uriFromFile == null && PictureMimeType.isHasImage(localMedia.getMimeType())) {
                String availablePath = localMedia.getAvailablePath();
                uriFromFile = (PictureMimeType.isContent(availablePath) || PictureMimeType.isHasHttp(availablePath)) ? Uri.parse(availablePath) : Uri.fromFile(new File(availablePath));
                uriFromFile2 = Uri.fromFile(new File(new File(FileDirMap.getFileDirPath(i(), 1)).getAbsolutePath(), DateUtils.getCreateFileName("CROP_") + PictureMimeType.JPG));
            }
        }
        this.f19027d.cropFileEngine.onStartCrop(this, uriFromFile, uriFromFile2, arrayList2, 69);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        releaseSoundPool();
        super.onDestroy();
    }

    public void onEditMedia(Intent intent) {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onEnterFragment() {
    }

    public void onExitFragment() {
    }

    public void onFixedSelectedChange(LocalMedia localMedia) {
    }

    public void onFragmentResume() {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onInterceptCameraEvent(int i2) {
        ForegroundService.startForegroundService(i(), this.f19027d.isCameraForegroundService);
        this.f19027d.onCameraInterceptListener.openCamera(this, i2, PictureConfig.REQUEST_CAMERA);
    }

    public void onKeyBackFragmentFinish() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isActivityResultBack) {
            getActivity().setResult(0);
            p(0, null);
        } else {
            OnResultCallbackListener<LocalMedia> onResultCallbackListener = selectorConfig.onResultCallListener;
            if (onResultCallbackListener != null) {
                onResultCallbackListener.onCancel();
            }
        }
        o();
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onOldCompress(ArrayList<LocalMedia> arrayList) {
        showLoading();
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isCheckOriginalImage && selectorConfig.isOriginalSkipCompress) {
            onResultEvent(arrayList);
        } else {
            selectorConfig.compressEngine.onStartCompress(i(), arrayList, new OnCallbackListener<ArrayList<LocalMedia>>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.11
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(ArrayList<LocalMedia> arrayList2) {
                    PictureCommonFragment.this.onResultEvent(arrayList2);
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onOldCrop(ArrayList<LocalMedia> arrayList) {
        LocalMedia localMedia;
        int i2 = 0;
        while (true) {
            if (i2 >= arrayList.size()) {
                localMedia = null;
                break;
            }
            localMedia = arrayList.get(i2);
            if (PictureMimeType.isHasImage(arrayList.get(i2).getMimeType())) {
                break;
            } else {
                i2++;
            }
        }
        this.f19027d.cropEngine.onStartCrop(this, localMedia, arrayList, 69);
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onPermissionExplainEvent(boolean z2, String[] strArr) {
        if (this.f19027d.onPermissionDescriptionListener != null) {
            if (PermissionChecker.isCheckSelfPermission(i(), strArr)) {
                this.f19027d.onPermissionDescriptionListener.onDismiss(this);
            } else if (!z2) {
                this.f19027d.onPermissionDescriptionListener.onDismiss(this);
            } else if (PermissionUtil.getPermissionStatus(requireActivity(), strArr[0]) != 3) {
                this.f19027d.onPermissionDescriptionListener.onPermissionDescription(this, strArr);
            }
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onRecreateEngine() {
        createImageLoaderEngine();
        createVideoPlayerEngine();
        createCompressEngine();
        createSandboxFileEngine();
        createLoaderDataEngine();
        createResultCallbackListener();
        createLayoutResourceListener();
    }

    @Override // androidx.fragment.app.Fragment
    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        if (this.mPermissionResultCallback != null) {
            PermissionChecker.getInstance().onRequestPermissionsResult(getContext(), strArr, iArr, this.mPermissionResultCallback);
            this.mPermissionResultCallback = null;
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onResultEvent(ArrayList<LocalMedia> arrayList) {
        if (checkTransformSandboxFile()) {
            uriToFileTransform29(arrayList);
        } else if (checkOldTransformSandboxFile()) {
            copyExternalPathToAppInDirFor29(arrayList);
        } else {
            mergeOriginalImage(arrayList);
            dispatchUriToFileTransformResult(arrayList);
        }
    }

    public void onSelectedChange(boolean z2, LocalMedia localMedia) {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onSelectedOnlyCamera() {
        PhotoItemSelectedDialog photoItemSelectedDialogNewInstance = PhotoItemSelectedDialog.newInstance();
        photoItemSelectedDialogNewInstance.setOnItemClickListener(new OnItemClickListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.4
            @Override // com.luck.picture.lib.interfaces.OnItemClickListener
            public void onItemClick(View view, int i2) {
                if (i2 == 0) {
                    PictureCommonFragment pictureCommonFragment = PictureCommonFragment.this;
                    if (pictureCommonFragment.f19027d.onCameraInterceptListener != null) {
                        pictureCommonFragment.onInterceptCameraEvent(1);
                        return;
                    } else {
                        pictureCommonFragment.openImageCamera();
                        return;
                    }
                }
                if (i2 != 1) {
                    return;
                }
                PictureCommonFragment pictureCommonFragment2 = PictureCommonFragment.this;
                if (pictureCommonFragment2.f19027d.onCameraInterceptListener != null) {
                    pictureCommonFragment2.onInterceptCameraEvent(2);
                } else {
                    pictureCommonFragment2.openVideoCamera();
                }
            }
        });
        photoItemSelectedDialogNewInstance.setOnDismissListener(new PhotoItemSelectedDialog.OnDismissListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.5
            @Override // com.luck.picture.lib.dialog.PhotoItemSelectedDialog.OnDismissListener
            public void onDismiss(boolean z2, DialogInterface dialogInterface) {
                PictureCommonFragment pictureCommonFragment = PictureCommonFragment.this;
                if (pictureCommonFragment.f19027d.isOnlyCamera && z2) {
                    pictureCommonFragment.onKeyBackFragmentFinish();
                }
            }
        });
        photoItemSelectedDialogNewInstance.show(getChildFragmentManager(), "PhotoItemSelectedDialog");
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.f19027d = SelectorProviders.getInstance().getSelectorConfig();
        FileDirMap.init(view.getContext());
        IBridgeViewLifecycle iBridgeViewLifecycle = this.f19027d.viewLifecycle;
        if (iBridgeViewLifecycle != null) {
            iBridgeViewLifecycle.onViewCreated(this, view, bundle);
        }
        OnCustomLoadingListener onCustomLoadingListener = this.f19027d.onCustomLoadingListener;
        if (onCustomLoadingListener != null) {
            this.mLoadingDialog = onCustomLoadingListener.create(i());
        } else {
            this.mLoadingDialog = new PictureLoadingDialog(i());
        }
        q();
        setTranslucentStatusBar();
        setRootViewKeyListener(requireView());
        SelectorConfig selectorConfig = this.f19027d;
        if (!selectorConfig.isOpenClickSound || selectorConfig.isOnlyCamera) {
            return;
        }
        SoundPool soundPool = new SoundPool(1, 3, 0);
        this.soundPool = soundPool;
        this.soundID = soundPool.load(i(), R.raw.ps_click_music, 1);
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void openImageCamera() {
        String[] strArr = PermissionConfig.CAMERA;
        onPermissionExplainEvent(true, strArr);
        if (this.f19027d.onPermissionsEventListener != null) {
            onApplyPermissionsEvent(PermissionEvent.EVENT_IMAGE_CAMERA, strArr);
        } else {
            PermissionChecker.getInstance().requestPermissions(this, strArr, new PermissionResultCallback() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.6
                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onDenied() {
                    PictureCommonFragment.this.handlePermissionDenied(PermissionConfig.CAMERA);
                }

                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onGranted() {
                    PictureCommonFragment.this.r();
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void openSelectedCamera() {
        SelectorConfig selectorConfig = this.f19027d;
        int i2 = selectorConfig.chooseMode;
        if (i2 == 0) {
            if (selectorConfig.ofAllCameraType == SelectMimeType.ofImage()) {
                openImageCamera();
                return;
            } else if (this.f19027d.ofAllCameraType == SelectMimeType.ofVideo()) {
                openVideoCamera();
                return;
            } else {
                onSelectedOnlyCamera();
                return;
            }
        }
        if (i2 == 1) {
            openImageCamera();
        } else if (i2 == 2) {
            openVideoCamera();
        } else {
            if (i2 != 3) {
                return;
            }
            openSoundRecording();
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void openSoundRecording() {
        if (this.f19027d.onRecordAudioListener != null) {
            ForegroundService.startForegroundService(i(), this.f19027d.isCameraForegroundService);
            this.f19027d.onRecordAudioListener.onRecordAudio(this, PictureConfig.REQUEST_CAMERA);
        } else {
            throw new NullPointerException(OnRecordAudioInterceptListener.class.getSimpleName() + " interface needs to be implemented for recording");
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void openVideoCamera() {
        String[] strArr = PermissionConfig.CAMERA;
        onPermissionExplainEvent(true, strArr);
        if (this.f19027d.onPermissionsEventListener != null) {
            onApplyPermissionsEvent(PermissionEvent.EVENT_VIDEO_CAMERA, strArr);
        } else {
            PermissionChecker.getInstance().requestPermissions(this, strArr, new PermissionResultCallback() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.7
                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onDenied() {
                    PictureCommonFragment.this.handlePermissionDenied(PermissionConfig.CAMERA);
                }

                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onGranted() {
                    PictureCommonFragment.this.s();
                }
            });
        }
    }

    protected void p(int i2, ArrayList arrayList) {
        if (this.f19024a != null) {
            this.f19024a.onSelectFinish(k(i2, arrayList));
        }
    }

    protected void q() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        getActivity().setRequestedOrientation(this.f19027d.requestedOrientation);
    }

    protected void r() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        onPermissionExplainEvent(false, null);
        if (this.f19027d.onCameraInterceptListener != null) {
            onInterceptCameraEvent(1);
            return;
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            ForegroundService.startForegroundService(i(), this.f19027d.isCameraForegroundService);
            Uri uriCreateCameraOutImageUri = MediaStoreUtils.createCameraOutImageUri(i(), this.f19027d);
            if (uriCreateCameraOutImageUri != null) {
                if (this.f19027d.isCameraAroundState) {
                    intent.putExtra(PictureConfig.CAMERA_FACING, 1);
                }
                intent.putExtra("output", uriCreateCameraOutImageUri);
                startActivityForResult(intent, PictureConfig.REQUEST_CAMERA);
            }
        }
    }

    public void reStartSavedInstance(Bundle bundle) {
    }

    protected void s() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        onPermissionExplainEvent(false, null);
        if (this.f19027d.onCameraInterceptListener != null) {
            onInterceptCameraEvent(2);
            return;
        }
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            ForegroundService.startForegroundService(i(), this.f19027d.isCameraForegroundService);
            Uri uriCreateCameraOutVideoUri = MediaStoreUtils.createCameraOutVideoUri(i(), this.f19027d);
            if (uriCreateCameraOutVideoUri != null) {
                intent.putExtra("output", uriCreateCameraOutVideoUri);
                if (this.f19027d.isCameraAroundState) {
                    intent.putExtra(PictureConfig.CAMERA_FACING, 1);
                }
                intent.putExtra(PictureConfig.EXTRA_QUICK_CAPTURE, this.f19027d.isQuickCapture);
                intent.putExtra("android.intent.extra.durationLimit", this.f19027d.recordVideoMaxSecond);
                intent.putExtra("android.intent.extra.videoQuality", this.f19027d.videoQuality);
                startActivityForResult(intent, PictureConfig.REQUEST_CAMERA);
            }
        }
    }

    public void sendChangeSubSelectPositionEvent(boolean z2) {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendFixedSelectedChangeEvent(LocalMedia localMedia) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (int i2 = 0; i2 < fragments.size(); i2++) {
            Fragment fragment = fragments.get(i2);
            if (fragment instanceof PictureCommonFragment) {
                ((PictureCommonFragment) fragment).onFixedSelectedChange(localMedia);
            }
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendSelectedChangeEvent(boolean z2, LocalMedia localMedia) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (int i2 = 0; i2 < fragments.size(); i2++) {
            Fragment fragment = fragments.get(i2);
            if (fragment instanceof PictureCommonFragment) {
                ((PictureCommonFragment) fragment).onSelectedChange(z2, localMedia);
            }
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendSelectedOriginalChangeEvent() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (int i2 = 0; i2 < fragments.size(); i2++) {
            Fragment fragment = fragments.get(i2);
            if (fragment instanceof PictureCommonFragment) {
                ((PictureCommonFragment) fragment).onCheckOriginalChange();
            }
        }
    }

    public void setEnterAnimationDuration(long j2) {
        this.enterAnimDuration = j2;
    }

    public void setPermissionsResultAction(PermissionResultCallback permissionResultCallback) {
        this.mPermissionResultCallback = permissionResultCallback;
    }

    public void setRootViewKeyListener(View view) {
        boolean z2 = true;
        if (this.f19027d.isNewKeyBackMode) {
            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(z2) { // from class: com.luck.picture.lib.basic.PictureCommonFragment.2
                @Override // androidx.activity.OnBackPressedCallback
                public void handleOnBackPressed() {
                    PictureCommonFragment.this.onKeyBackFragmentFinish();
                }
            });
            return;
        }
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.3
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view2, int i2, KeyEvent keyEvent) {
                if (i2 != 4 || keyEvent.getAction() != 1) {
                    return false;
                }
                PictureCommonFragment.this.onKeyBackFragmentFinish();
                return true;
            }
        });
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void showLoading() {
        try {
            if (ActivityCompatHelper.isDestroy(getActivity()) || this.mLoadingDialog.isShowing()) {
                return;
            }
            this.mLoadingDialog.show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
