package com.leeson.image_pickers.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.util.LocalePreferences;
import com.facebook.internal.AnalyticsEvents;
import com.leeson.image_pickers.AppPath;
import com.leeson.image_pickers.utils.CommonUtils;
import com.leeson.image_pickers.utils.GlideEngine;
import com.leeson.image_pickers.utils.ImageCompressEngine;
import com.leeson.image_pickers.utils.ImageCropEngine;
import com.leeson.image_pickers.utils.MeSandboxFileEngine;
import com.leeson.image_pickers.utils.PictureStyleUtil;
import com.luck.picture.lib.R;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.dialog.RemindDialog;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.StyleUtils;
import com.yalantis.ucrop.UCrop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class SelectPicsActivity extends BaseActivity {
    public static final String CAMERA_MIME_TYPE = "CAMERA_MIME_TYPE";
    public static final String COMPRESS_PATHS = "COMPRESS_PATHS";
    public static final String COMPRESS_SIZE = "COMPRESS_SIZE";
    public static final String ENABLE_CROP = "ENABLE_CROP";
    public static final String GALLERY_MODE = "GALLERY_MODE";
    public static final String HEIGHT = "HEIGHT";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String SELECT_COUNT = "SELECT_COUNT";
    public static final String SHOW_CAMERA = "SHOW_CAMERA";
    public static final String SHOW_GIF = "SHOW_GIF";
    public static final String UI_COLOR = "UI_COLOR";
    public static final String VIDEO_RECORD_MAX_SECOND = "VIDEO_RECORD_MAX_SECOND";
    public static final String VIDEO_RECORD_MIN_SECOND = "VIDEO_RECORD_MIN_SECOND";
    public static final String VIDEO_SELECT_MAX_SECOND = "VIDEO_SELECT_MAX_SECOND";
    public static final String VIDEO_SELECT_MIN_SECOND = "VIDEO_SELECT_MIN_SECOND";
    public static final String WIDTH = "WIDTH";
    private static final int WRITE_SDCARD = 101;

    private UCrop.Options buildOptions(PictureSelectorStyle pictureSelectorStyle) {
        UCrop.Options options = new UCrop.Options();
        if (pictureSelectorStyle != null && pictureSelectorStyle.getSelectMainStyle().getStatusBarColor() != 0) {
            SelectMainStyle selectMainStyle = pictureSelectorStyle.getSelectMainStyle();
            boolean zIsDarkStatusBarBlack = selectMainStyle.isDarkStatusBarBlack();
            int statusBarColor = selectMainStyle.getStatusBarColor();
            options.isDarkStatusBarBlack(zIsDarkStatusBarBlack);
            options.setSkipCropMimeType(new String[]{PictureMimeType.ofGIF(), PictureMimeType.ofWEBP()});
            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                options.setStatusBarColor(statusBarColor);
                options.setToolbarColor(statusBarColor);
            }
            TitleBarStyle titleBarStyle = pictureSelectorStyle.getTitleBarStyle();
            if (StyleUtils.checkStyleValidity(titleBarStyle.getTitleTextColor())) {
                options.setToolbarWidgetColor(titleBarStyle.getTitleTextColor());
            }
        }
        return options;
    }

    private int getLang(String str) {
        if (LocalePreferences.CalendarType.CHINESE.equals(str)) {
            return 0;
        }
        if ("traditional_chinese".equals(str)) {
            return 1;
        }
        if ("english".equals(str)) {
            return 2;
        }
        if ("japanese".equals(str)) {
            return 6;
        }
        if ("france".equals(str)) {
            return 5;
        }
        if ("german".equals(str)) {
            return 4;
        }
        if ("russian".equals(str)) {
            return 11;
        }
        if ("vietnamese".equals(str)) {
            return 7;
        }
        if ("korean".equals(str)) {
            return 3;
        }
        if ("portuguese".equals(str)) {
            return 9;
        }
        if ("spanish".equals(str)) {
            return 8;
        }
        return "arabic".equals(str) ? 10 : -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlerResult(ArrayList<LocalMedia> arrayList) throws IOException {
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            LocalMedia localMedia = arrayList.get(i2);
            if (localMedia.getMimeType().contains("image")) {
                String availablePath = localMedia.getAvailablePath();
                if (localMedia.isCut()) {
                    availablePath = localMedia.getCutPath();
                }
                HashMap map = new HashMap();
                map.put("thumbPath", availablePath);
                map.put("path", availablePath);
                arrayList2.add(map);
            } else {
                if (localMedia.getAvailablePath() == null) {
                    break;
                }
                String strSaveBitmap = CommonUtils.saveBitmap(this, new AppPath(this).getAppImgDirPath(), ThumbnailUtils.createVideoThumbnail(localMedia.getAvailablePath(), 2));
                HashMap map2 = new HashMap();
                map2.put("thumbPath", strSaveBitmap);
                map2.put("path", localMedia.getAvailablePath());
                arrayList2.add(map2);
            }
        }
        Intent intent = new Intent();
        intent.putExtra(COMPRESS_PATHS, arrayList2);
        setResult(-1, intent);
        finish();
    }

    private void startSel() {
        String stringExtra = getIntent().getStringExtra(GALLERY_MODE);
        Map<String, Number> map = (Map) getIntent().getSerializableExtra(UI_COLOR);
        int intExtra = getIntent().getIntExtra(SELECT_COUNT, 9);
        boolean booleanExtra = getIntent().getBooleanExtra(SHOW_GIF, true);
        boolean booleanExtra2 = getIntent().getBooleanExtra(SHOW_CAMERA, false);
        boolean booleanExtra3 = getIntent().getBooleanExtra(ENABLE_CROP, false);
        int intExtra2 = getIntent().getIntExtra(WIDTH, 1);
        int intExtra3 = getIntent().getIntExtra(HEIGHT, 1);
        int intExtra4 = getIntent().getIntExtra(COMPRESS_SIZE, 500);
        final String stringExtra2 = getIntent().getStringExtra(CAMERA_MIME_TYPE);
        int intExtra5 = getIntent().getIntExtra(VIDEO_RECORD_MAX_SECOND, 120);
        final Integer numValueOf = Integer.valueOf(intExtra5);
        int intExtra6 = getIntent().getIntExtra(VIDEO_RECORD_MIN_SECOND, 1);
        final Integer numValueOf2 = Integer.valueOf(intExtra6);
        int intExtra7 = getIntent().getIntExtra(VIDEO_SELECT_MAX_SECOND, 120);
        int intExtra8 = getIntent().getIntExtra(VIDEO_SELECT_MIN_SECOND, 1);
        String stringExtra3 = getIntent().getStringExtra(LANGUAGE);
        PictureStyleUtil pictureStyleUtil = new PictureStyleUtil(this);
        pictureStyleUtil.setStyle(map);
        PictureSelectorStyle selectorStyle = pictureStyleUtil.getSelectorStyle();
        PictureSelector.create((AppCompatActivity) this);
        if (stringExtra2 != null) {
            PictureSelector.create((AppCompatActivity) this).openCamera(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_PHOTO.equals(stringExtra2) ? SelectMimeType.ofImage() : SelectMimeType.ofVideo()).setRecordVideoMaxSecond(intExtra5).setRecordVideoMinSecond(intExtra6).setLanguage(getLang(stringExtra3)).setOutputCameraDir(new AppPath(this).getAppVideoDirPath()).setCropEngine(booleanExtra3 ? new ImageCropEngine(this, buildOptions(selectorStyle), intExtra2, intExtra3) : null).setCompressEngine(new ImageCompressEngine(intExtra4)).setSandboxFileEngine(new MeSandboxFileEngine()).forResult(new OnResultCallbackListener<LocalMedia>() { // from class: com.leeson.image_pickers.activitys.SelectPicsActivity.1
                @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
                public void onCancel() {
                    Intent intent = new Intent();
                    intent.putExtra(SelectPicsActivity.COMPRESS_PATHS, new ArrayList());
                    SelectPicsActivity.this.setResult(-1, intent);
                    SelectPicsActivity.this.finish();
                }

                @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
                public void onResult(ArrayList<LocalMedia> arrayList) throws IOException {
                    if (arrayList == null || arrayList.size() <= 0) {
                        Intent intent = new Intent();
                        intent.putExtra(SelectPicsActivity.COMPRESS_PATHS, new ArrayList());
                        SelectPicsActivity.this.setResult(-1, intent);
                        SelectPicsActivity.this.finish();
                        return;
                    }
                    LocalMedia localMedia = arrayList.get(0);
                    if (!"video".equals(stringExtra2)) {
                        SelectPicsActivity.this.handlerResult(arrayList);
                        return;
                    }
                    long duration = localMedia.getDuration() / 1000;
                    if (duration >= numValueOf2.intValue() && duration <= numValueOf.intValue()) {
                        SelectPicsActivity.this.handlerResult(arrayList);
                        return;
                    }
                    RemindDialog remindDialogBuildDialog = RemindDialog.buildDialog(SelectPicsActivity.this, duration < ((long) numValueOf2.intValue()) ? SelectPicsActivity.this.getString(R.string.ps_select_video_min_second, Integer.valueOf(numValueOf2.intValue())) : duration > ((long) numValueOf.intValue()) ? SelectPicsActivity.this.getString(R.string.ps_select_video_max_second, Integer.valueOf(numValueOf.intValue())) : "");
                    remindDialogBuildDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.leeson.image_pickers.activitys.SelectPicsActivity.1.1
                        @Override // android.content.DialogInterface.OnDismissListener
                        public void onDismiss(DialogInterface dialogInterface) {
                            Intent intent2 = new Intent();
                            intent2.putExtra(SelectPicsActivity.COMPRESS_PATHS, new ArrayList());
                            SelectPicsActivity.this.setResult(-1, intent2);
                            SelectPicsActivity.this.finish();
                        }
                    });
                    remindDialogBuildDialog.show();
                }
            });
        } else {
            SelectMimeType.ofImage();
            PictureSelector.create((AppCompatActivity) this).openGallery("image".equals(stringExtra) ? SelectMimeType.ofImage() : "video".equals(stringExtra) ? SelectMimeType.ofVideo() : SelectMimeType.ofAll()).setImageEngine(GlideEngine.createGlideEngine()).setSelectorUIStyle(pictureStyleUtil.getSelectorStyle()).setRequestedOrientation(1).setRecordVideoMaxSecond(intExtra5).setRecordVideoMinSecond(intExtra6).setLanguage(getLang(stringExtra3)).setOutputCameraDir(new AppPath(this).getAppVideoDirPath()).setCropEngine(booleanExtra3 ? new ImageCropEngine(this, buildOptions(selectorStyle), intExtra2, intExtra3) : null).setCompressEngine(new ImageCompressEngine(intExtra4)).setSandboxFileEngine(new MeSandboxFileEngine()).isDisplayCamera(booleanExtra2).isGif(booleanExtra).setSelectMaxDurationSecond(intExtra7).setSelectMinDurationSecond(intExtra8).setFilterVideoMaxSecond(intExtra7).setFilterVideoMinSecond(intExtra8).setMaxSelectNum(intExtra).setMaxVideoSelectNum(intExtra).isWithSelectVideoImage(true).setImageSpanCount(4).setSelectionMode(intExtra == 1 ? 1 : 2).isDirectReturnSingle(true).setSkipCropMimeType(PictureMimeType.ofGIF(), PictureMimeType.ofWEBP()).isPreviewImage(true).isPreviewVideo(true).forResult(new OnResultCallbackListener<LocalMedia>() { // from class: com.leeson.image_pickers.activitys.SelectPicsActivity.2
                @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
                public void onCancel() {
                    Intent intent = new Intent();
                    intent.putExtra(SelectPicsActivity.COMPRESS_PATHS, new ArrayList());
                    SelectPicsActivity.this.setResult(-1, intent);
                    SelectPicsActivity.this.finish();
                }

                @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
                public void onResult(ArrayList<LocalMedia> arrayList) throws IOException {
                    SelectPicsActivity.this.handlerResult(arrayList);
                }
            });
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(com.leeson.image_pickers.R.layout.activity_select_pics);
        startSel();
    }
}
