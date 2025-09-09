package com.leeson.image_pickers;

import android.content.Intent;
import android.util.Log;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.leeson.image_pickers.activitys.PermissionActivity;
import com.leeson.image_pickers.activitys.PhotosActivity;
import com.leeson.image_pickers.activitys.SelectPicsActivity;
import com.leeson.image_pickers.activitys.VideoActivity;
import com.leeson.image_pickers.utils.Saver;
import com.luck.picture.lib.permissions.PermissionConfig;
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
public class MethodCallImpl implements MethodChannel.MethodCallHandler, PluginRegistry.ActivityResultListener {
    private static final int SAVE_IMAGE = 103;
    private static final int SAVE_IMAGE_DATA = 105;
    private static final int SELECT = 102;
    private static final int WRITE_SDCARD = 104;
    private ActivityPluginBinding activityPluginBinding;
    private byte[] data;
    private MethodChannel.Result result;

    public ActivityPluginBinding getActivityPluginBinding() {
        return this.activityPluginBinding;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i2, int i3, Intent intent) throws IOException {
        if (i2 == 102) {
            if (i3 != -1) {
                return true;
            }
            List list = (List) intent.getSerializableExtra(SelectPicsActivity.COMPRESS_PATHS);
            Log.e("onActivityResult", "onActivityResult: " + list.size() + " == " + this.result);
            MethodChannel.Result result = this.result;
            if (result == null) {
                return true;
            }
            result.success(list);
            return true;
        }
        if (i2 == 103) {
            if (i3 != -1) {
                return false;
            }
            new Saver(this.activityPluginBinding.getActivity()).saveImgToGallery(intent.getStringExtra("imageUrl"), new Saver.IFinishListener() { // from class: com.leeson.image_pickers.MethodCallImpl.1
                @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
                public void onFailed(String str) {
                    if (MethodCallImpl.this.result != null) {
                        MethodCallImpl.this.result.error("-1", str, str);
                    }
                }

                @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
                public void onSuccess(Saver.FileInfo fileInfo) {
                    if (MethodCallImpl.this.result != null) {
                        MethodCallImpl.this.result.success(fileInfo.getPath());
                    }
                }
            });
            return false;
        }
        if (i2 == 104) {
            if (i3 != -1) {
                return false;
            }
            new Saver(this.activityPluginBinding.getActivity()).saveVideoToGallery(intent.getStringExtra("videoUrl"), new Saver.IFinishListener() { // from class: com.leeson.image_pickers.MethodCallImpl.2
                @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
                public void onFailed(String str) {
                    if (MethodCallImpl.this.result != null) {
                        MethodCallImpl.this.result.error("-1", str, str);
                    }
                }

                @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
                public void onSuccess(Saver.FileInfo fileInfo) {
                    if (MethodCallImpl.this.result != null) {
                        MethodCallImpl.this.result.success(fileInfo.getPath());
                    }
                }
            });
            return false;
        }
        if (i2 != 105 || i3 != -1 || this.data == null) {
            return false;
        }
        new Saver(this.activityPluginBinding.getActivity()).saveByteDataToGallery(this.data, new Saver.IFinishListener() { // from class: com.leeson.image_pickers.MethodCallImpl.3
            @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
            public void onFailed(String str) {
                if (MethodCallImpl.this.result != null) {
                    MethodCallImpl.this.result.error("-1", str, str);
                }
                MethodCallImpl.this.data = null;
            }

            @Override // com.leeson.image_pickers.utils.Saver.IFinishListener
            public void onSuccess(Saver.FileInfo fileInfo) {
                if (MethodCallImpl.this.result != null) {
                    MethodCallImpl.this.result.success(fileInfo.getPath());
                }
                MethodCallImpl.this.data = null;
            }
        });
        return false;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        this.result = result;
        if ("getPickerPaths".equals(methodCall.method)) {
            String str = (String) methodCall.argument("galleryMode");
            Map map = (Map) methodCall.argument("uiColor");
            Number number = (Number) methodCall.argument("selectCount");
            Boolean bool = (Boolean) methodCall.argument("showCamera");
            Boolean bool2 = (Boolean) methodCall.argument("enableCrop");
            Number number2 = (Number) methodCall.argument(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
            Number number3 = (Number) methodCall.argument(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
            Number number4 = (Number) methodCall.argument("compressSize");
            String str2 = (String) methodCall.argument("cameraMimeType");
            Intent intent = new Intent(this.activityPluginBinding.getActivity(), (Class<?>) SelectPicsActivity.class);
            intent.putExtra(SelectPicsActivity.GALLERY_MODE, str);
            intent.putExtra(SelectPicsActivity.UI_COLOR, (Serializable) map);
            intent.putExtra(SelectPicsActivity.SELECT_COUNT, number);
            intent.putExtra(SelectPicsActivity.SHOW_CAMERA, bool);
            intent.putExtra(SelectPicsActivity.ENABLE_CROP, bool2);
            intent.putExtra(SelectPicsActivity.WIDTH, number2);
            intent.putExtra(SelectPicsActivity.HEIGHT, number3);
            intent.putExtra(SelectPicsActivity.COMPRESS_SIZE, number4);
            intent.putExtra(SelectPicsActivity.CAMERA_MIME_TYPE, str2);
            this.activityPluginBinding.getActivity().startActivityForResult(intent, 102);
            return;
        }
        if ("previewImage".equals(methodCall.method)) {
            Intent intent2 = new Intent(this.activityPluginBinding.getActivity(), (Class<?>) PhotosActivity.class);
            ArrayList arrayList = new ArrayList();
            arrayList.add(methodCall.argument("path").toString());
            intent2.putExtra(PhotosActivity.IMAGES, arrayList);
            this.activityPluginBinding.getActivity().startActivity(intent2);
            return;
        }
        if ("previewImages".equals(methodCall.method)) {
            Intent intent3 = new Intent(this.activityPluginBinding.getActivity(), (Class<?>) PhotosActivity.class);
            List list = (List) methodCall.argument("paths");
            Number number5 = (Number) methodCall.argument("initIndex");
            intent3.putExtra(PhotosActivity.IMAGES, (Serializable) list);
            intent3.putExtra(PhotosActivity.CURRENT_POSITION, number5);
            this.activityPluginBinding.getActivity().startActivity(intent3);
            return;
        }
        if ("previewVideo".equals(methodCall.method)) {
            Intent intent4 = new Intent(this.activityPluginBinding.getActivity(), (Class<?>) VideoActivity.class);
            intent4.putExtra(VideoActivity.VIDEO_PATH, methodCall.argument("path").toString());
            intent4.putExtra(VideoActivity.THUMB_PATH, methodCall.argument("thumbPath").toString());
            this.activityPluginBinding.getActivity().startActivity(intent4);
            return;
        }
        if ("saveImageToGallery".equals(methodCall.method)) {
            Intent intent5 = new Intent(this.activityPluginBinding.getActivity(), (Class<?>) PermissionActivity.class);
            intent5.putExtra(PermissionActivity.PERMISSIONS, new String[]{PermissionConfig.WRITE_EXTERNAL_STORAGE, PermissionConfig.READ_EXTERNAL_STORAGE});
            intent5.putExtra("imageUrl", methodCall.argument("path").toString());
            this.activityPluginBinding.getActivity().startActivityForResult(intent5, 103);
            return;
        }
        if ("saveVideoToGallery".equals(methodCall.method)) {
            Intent intent6 = new Intent(this.activityPluginBinding.getActivity(), (Class<?>) PermissionActivity.class);
            intent6.putExtra(PermissionActivity.PERMISSIONS, new String[]{PermissionConfig.WRITE_EXTERNAL_STORAGE, PermissionConfig.READ_EXTERNAL_STORAGE});
            intent6.putExtra("videoUrl", methodCall.argument("path").toString());
            this.activityPluginBinding.getActivity().startActivityForResult(intent6, 104);
            return;
        }
        if (!"saveByteDataImageToGallery".equals(methodCall.method)) {
            result.notImplemented();
            return;
        }
        Intent intent7 = new Intent(this.activityPluginBinding.getActivity(), (Class<?>) PermissionActivity.class);
        intent7.putExtra(PermissionActivity.PERMISSIONS, new String[]{PermissionConfig.WRITE_EXTERNAL_STORAGE, PermissionConfig.READ_EXTERNAL_STORAGE});
        this.data = (byte[]) methodCall.argument("uint8List");
        this.activityPluginBinding.getActivity().startActivityForResult(intent7, 105);
    }

    public void setActivityPluginBinding(ActivityPluginBinding activityPluginBinding) {
        this.activityPluginBinding = activityPluginBinding;
        activityPluginBinding.addActivityResultListener(this);
    }
}
