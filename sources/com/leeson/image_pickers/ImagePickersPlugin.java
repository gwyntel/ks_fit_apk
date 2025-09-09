package com.leeson.image_pickers;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.leeson.image_pickers.activitys.PermissionActivity;
import com.leeson.image_pickers.activitys.PhotosActivity;
import com.leeson.image_pickers.activitys.SelectPicsActivity;
import com.leeson.image_pickers.activitys.VideoActivity;
import com.leeson.image_pickers.utils.Saver;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.umeng.analytics.pro.bc;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class ImagePickersPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {
    private static final int READ_IMAGE = 106;
    private static final int SAVE_IMAGE = 103;
    private static final int SAVE_IMAGE_DATA = 105;
    private static final int SAVE_VIDEO = 104;
    private static final int SELECT = 102;
    private Activity activity;
    private MethodChannel channel;
    private byte[] data;
    private FlutterPlugin.FlutterPluginBinding flutterPluginBinding;
    private PluginRegistry.ActivityResultListener listener = new PluginRegistry.ActivityResultListener() { // from class: com.leeson.image_pickers.ImagePickersPlugin.1
        @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
        public boolean onActivityResult(int i2, int i3, Intent intent) throws IOException {
            if (i2 == 102) {
                if (i3 != -1) {
                    if (ImagePickersPlugin.this.result == null) {
                        return true;
                    }
                    ImagePickersPlugin.this.result.success(new ArrayList());
                    return true;
                }
                List list = (List) intent.getSerializableExtra(SelectPicsActivity.COMPRESS_PATHS);
                if (ImagePickersPlugin.this.result == null) {
                    return true;
                }
                ImagePickersPlugin.this.result.success(list);
                return true;
            }
            if (i2 == 103) {
                if (i3 != -1) {
                    return false;
                }
                ImagePickersPlugin.this.saveImage(intent.getStringExtra("imageUrl"));
                return false;
            }
            if (i2 == 104) {
                if (i3 != -1) {
                    return false;
                }
                ImagePickersPlugin.this.saveVideo(intent.getStringExtra("videoUrl"));
                return false;
            }
            if (i2 == 105) {
                if (i3 != -1 || ImagePickersPlugin.this.data == null) {
                    return false;
                }
                ImagePickersPlugin.this.saveImageData();
                return false;
            }
            if (i2 != 106 || i3 != -1) {
                return false;
            }
            Intent intent2 = new Intent(ImagePickersPlugin.this.activity, (Class<?>) SelectPicsActivity.class);
            intent2.putExtras(intent);
            ImagePickersPlugin.this.activity.startActivityForResult(intent2, 102);
            return false;
        }
    };
    private MethodChannel.Result result;

    public static void registerWith(PluginRegistry.Registrar registrar) {
        new ImagePickersPlugin().setup(registrar, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImage(String str) throws IOException {
        new Saver(this.activity).saveImgToGallery(str, new Saver.IFinishListener() { // from class: com.leeson.image_pickers.ImagePickersPlugin.2
            @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
            public void onFailed(String str2) {
                if (ImagePickersPlugin.this.result != null) {
                    ImagePickersPlugin.this.result.error("-1", str2, str2);
                }
            }

            @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
            public void onSuccess(Saver.FileInfo fileInfo) {
                if (ImagePickersPlugin.this.result != null) {
                    ImagePickersPlugin.this.result.success(fileInfo.getPath());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImageData() {
        new Saver(this.activity).saveByteDataToGallery(this.data, new Saver.IFinishListener() { // from class: com.leeson.image_pickers.ImagePickersPlugin.4
            @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
            public void onFailed(String str) {
                if (ImagePickersPlugin.this.result != null) {
                    ImagePickersPlugin.this.result.error("-1", str, str);
                }
                ImagePickersPlugin.this.data = null;
            }

            @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
            public void onSuccess(Saver.FileInfo fileInfo) {
                if (ImagePickersPlugin.this.result != null) {
                    ImagePickersPlugin.this.result.success(fileInfo.getPath());
                }
                ImagePickersPlugin.this.data = null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveVideo(String str) throws IOException {
        new Saver(this.activity).saveVideoToGallery(str, new Saver.IFinishListener() { // from class: com.leeson.image_pickers.ImagePickersPlugin.3
            @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
            public void onFailed(String str2) {
                if (ImagePickersPlugin.this.result != null) {
                    ImagePickersPlugin.this.result.error("-1", str2, str2);
                }
            }

            @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
            public void onSuccess(Saver.FileInfo fileInfo) {
                if (ImagePickersPlugin.this.result != null) {
                    ImagePickersPlugin.this.result.success(fileInfo.getPath());
                }
            }
        });
    }

    private void setup(PluginRegistry.Registrar registrar, ActivityPluginBinding activityPluginBinding) {
        if (registrar != null) {
            this.activity = registrar.activity();
            MethodChannel methodChannel = new MethodChannel(registrar.messenger(), "flutter/image_pickers");
            this.channel = methodChannel;
            methodChannel.setMethodCallHandler(this);
            registrar.addActivityResultListener(this.listener);
            return;
        }
        this.activity = activityPluginBinding.getActivity();
        MethodChannel methodChannel2 = new MethodChannel(this.flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter/image_pickers");
        this.channel = methodChannel2;
        methodChannel2.setMethodCallHandler(this);
        activityPluginBinding.addActivityResultListener(this.listener);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        setup(null, activityPluginBinding);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.flutterPluginBinding = flutterPluginBinding;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        MethodChannel methodChannel = this.channel;
        if (methodChannel != null) {
            methodChannel.setMethodCallHandler(null);
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, @NonNull MethodChannel.Result result) throws IOException {
        this.result = result;
        if ("getPickerPaths".equals(methodCall.method)) {
            String str = (String) methodCall.argument("galleryMode");
            Boolean bool = (Boolean) methodCall.argument("showGif");
            Map map = (Map) methodCall.argument("uiColor");
            Number number = (Number) methodCall.argument("selectCount");
            Boolean bool2 = (Boolean) methodCall.argument("showCamera");
            Boolean bool3 = (Boolean) methodCall.argument("enableCrop");
            Number number2 = (Number) methodCall.argument(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
            Number number3 = (Number) methodCall.argument(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
            Number number4 = (Number) methodCall.argument("compressSize");
            String str2 = (String) methodCall.argument("cameraMimeType");
            Number number5 = (Number) methodCall.argument("videoRecordMaxSecond");
            Number number6 = (Number) methodCall.argument("videoRecordMinSecond");
            Number number7 = (Number) methodCall.argument("videoSelectMaxSecond");
            Number number8 = (Number) methodCall.argument("videoSelectMinSecond");
            String str3 = (String) methodCall.argument(bc.N);
            Intent intent = new Intent();
            intent.putExtra(SelectPicsActivity.GALLERY_MODE, str);
            intent.putExtra(SelectPicsActivity.UI_COLOR, (Serializable) map);
            intent.putExtra(SelectPicsActivity.SELECT_COUNT, number);
            intent.putExtra(SelectPicsActivity.SHOW_GIF, bool);
            intent.putExtra(SelectPicsActivity.SHOW_CAMERA, bool2);
            intent.putExtra(SelectPicsActivity.ENABLE_CROP, bool3);
            intent.putExtra(SelectPicsActivity.WIDTH, number2);
            intent.putExtra(SelectPicsActivity.HEIGHT, number3);
            intent.putExtra(SelectPicsActivity.COMPRESS_SIZE, number4);
            intent.putExtra(SelectPicsActivity.CAMERA_MIME_TYPE, str2);
            intent.putExtra(SelectPicsActivity.VIDEO_RECORD_MAX_SECOND, number5);
            intent.putExtra(SelectPicsActivity.VIDEO_RECORD_MIN_SECOND, number6);
            intent.putExtra(SelectPicsActivity.VIDEO_SELECT_MAX_SECOND, number7);
            intent.putExtra(SelectPicsActivity.VIDEO_SELECT_MIN_SECOND, number8);
            intent.putExtra(SelectPicsActivity.LANGUAGE, str3);
            if (str2 != null) {
                intent.putExtra(PermissionActivity.PERMISSIONS, new String[]{"android.permission.CAMERA"});
                intent.setClass(this.activity, PermissionActivity.class);
                this.activity.startActivityForResult(intent, 106);
                return;
            } else if (Build.VERSION.SDK_INT < 33) {
                intent.setClass(this.activity, SelectPicsActivity.class);
                this.activity.startActivityForResult(intent, 102);
                return;
            } else {
                intent.putExtra(PermissionActivity.PERMISSIONS, new String[]{PermissionConfig.READ_MEDIA_IMAGES, PermissionConfig.READ_MEDIA_VIDEO});
                intent.setClass(this.activity, PermissionActivity.class);
                this.activity.startActivityForResult(intent, 106);
                return;
            }
        }
        if ("previewImage".equals(methodCall.method)) {
            Intent intent2 = new Intent(this.activity, (Class<?>) PhotosActivity.class);
            ArrayList arrayList = new ArrayList();
            arrayList.add(methodCall.argument("path").toString());
            intent2.putExtra(PhotosActivity.IMAGES, arrayList);
            this.activity.startActivity(intent2);
            return;
        }
        if ("previewImages".equals(methodCall.method)) {
            Intent intent3 = new Intent(this.activity, (Class<?>) PhotosActivity.class);
            List list = (List) methodCall.argument("paths");
            Number number9 = (Number) methodCall.argument("initIndex");
            intent3.putExtra(PhotosActivity.IMAGES, (Serializable) list);
            intent3.putExtra(PhotosActivity.CURRENT_POSITION, number9);
            this.activity.startActivity(intent3);
            return;
        }
        if ("previewVideo".equals(methodCall.method)) {
            Intent intent4 = new Intent(this.activity, (Class<?>) VideoActivity.class);
            intent4.putExtra(VideoActivity.VIDEO_PATH, methodCall.argument("path").toString());
            intent4.putExtra(VideoActivity.THUMB_PATH, methodCall.argument("thumbPath").toString());
            this.activity.startActivity(intent4);
            return;
        }
        if ("saveImageToGallery".equals(methodCall.method)) {
            String string = methodCall.argument("path").toString();
            if (Build.VERSION.SDK_INT >= 33) {
                saveImage(string);
                return;
            }
            Intent intent5 = new Intent(this.activity, (Class<?>) PermissionActivity.class);
            intent5.putExtra(PermissionActivity.PERMISSIONS, new String[]{PermissionConfig.WRITE_EXTERNAL_STORAGE});
            intent5.putExtra("imageUrl", string);
            this.activity.startActivityForResult(intent5, 103);
            return;
        }
        if ("saveVideoToGallery".equals(methodCall.method)) {
            String string2 = methodCall.argument("path").toString();
            if (Build.VERSION.SDK_INT >= 33) {
                saveVideo(string2);
                return;
            }
            Intent intent6 = new Intent(this.activity, (Class<?>) PermissionActivity.class);
            intent6.putExtra(PermissionActivity.PERMISSIONS, new String[]{PermissionConfig.WRITE_EXTERNAL_STORAGE});
            intent6.putExtra("videoUrl", string2);
            this.activity.startActivityForResult(intent6, 104);
            return;
        }
        if (!"saveByteDataImageToGallery".equals(methodCall.method)) {
            result.notImplemented();
            return;
        }
        this.data = (byte[]) methodCall.argument("uint8List");
        if (Build.VERSION.SDK_INT >= 33) {
            saveImageData();
            return;
        }
        Intent intent7 = new Intent(this.activity, (Class<?>) PermissionActivity.class);
        intent7.putExtra(PermissionActivity.PERMISSIONS, new String[]{PermissionConfig.WRITE_EXTERNAL_STORAGE});
        this.activity.startActivityForResult(intent7, 105);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
    }
}
