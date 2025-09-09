package vn.hunghd.flutter.plugins.imagecropper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import androidx.media3.extractor.text.ttml.TtmlNode;
import androidx.preference.PreferenceManager;
import com.luck.picture.lib.config.PictureMimeType;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/* loaded from: classes5.dex */
public class ImageCropperDelegate implements PluginRegistry.ActivityResultListener {
    private final Activity activity;
    private final FileUtils fileUtils = new FileUtils();
    private MethodChannel.Result pendingResult;
    private final SharedPreferences preferences;

    public ImageCropperDelegate(Activity activity) {
        this.activity = activity;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
    }

    private void cacheImage(String str) {
        SharedPreferences.Editor editorEdit = this.preferences.edit();
        editorEdit.putString("imagecropper.FILENAME_CACHE_KEY", str);
        editorEdit.apply();
    }

    private void clearMethodCallAndResult() {
        this.pendingResult = null;
    }

    private int darkenColor(int i2) {
        float[] fArr = new float[3];
        Color.colorToHSV(i2, fArr);
        fArr[2] = fArr[2] * 0.8f;
        return Color.HSVToColor(fArr);
    }

    private void finishWithError(String str, String str2, Throwable th) {
        MethodChannel.Result result = this.pendingResult;
        if (result != null) {
            result.error(str, str2, th);
            clearMethodCallAndResult();
        }
    }

    private void finishWithSuccess(String str) {
        MethodChannel.Result result = this.pendingResult;
        if (result != null) {
            result.success(str);
            clearMethodCallAndResult();
        }
    }

    private String getAndClearCachedImage() {
        if (!this.preferences.contains("imagecropper.FILENAME_CACHE_KEY")) {
            return null;
        }
        String string = this.preferences.getString("imagecropper.FILENAME_CACHE_KEY", "");
        SharedPreferences.Editor editorEdit = this.preferences.edit();
        editorEdit.remove("imagecropper.FILENAME_CACHE_KEY");
        editorEdit.apply();
        return string;
    }

    private AspectRatio parseAspectRatio(Map<?, ?> map) {
        String string = map.containsKey("name") ? map.get("name").toString() : null;
        Object obj = map.containsKey("data") ? map.get("data") : null;
        boolean z2 = obj instanceof Map;
        return ("original".equals(string) || (z2 ? Integer.valueOf(Integer.parseInt(((Map) obj).get("ratio_x").toString())) : null) == null) ? new AspectRatio(this.activity.getString(com.yalantis.ucrop.R.string.ucrop_label_original), 0.0f, 1.0f) : new AspectRatio(string, r3.intValue() * 1.0f, (z2 ? Integer.valueOf(Integer.parseInt(((Map) obj).get("ratio_y").toString())) : null).intValue() * 1.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0121  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.yalantis.ucrop.UCrop.Options setupUiCustomizedOptions(com.yalantis.ucrop.UCrop.Options r19, io.flutter.plugin.common.MethodCall r20) {
        /*
            Method dump skipped, instructions count: 297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: vn.hunghd.flutter.plugins.imagecropper.ImageCropperDelegate.setupUiCustomizedOptions(com.yalantis.ucrop.UCrop$Options, io.flutter.plugin.common.MethodCall):com.yalantis.ucrop.UCrop$Options");
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i2, int i3, Intent intent) {
        if (i2 != 69) {
            return false;
        }
        if (i3 == -1) {
            String strA = this.fileUtils.a(this.activity, UCrop.getOutput(intent));
            cacheImage(strA);
            finishWithSuccess(strA);
            return true;
        }
        if (i3 == 96) {
            Throwable error = UCrop.getError(intent);
            finishWithError("crop_error", error.getLocalizedMessage(), error);
            return true;
        }
        MethodChannel.Result result = this.pendingResult;
        if (result == null) {
            return false;
        }
        result.success(null);
        clearMethodCallAndResult();
        return true;
    }

    public void recoverImage(MethodCall methodCall, MethodChannel.Result result) {
        result.success(getAndClearCachedImage());
    }

    public void startCrop(MethodCall methodCall, MethodChannel.Result result) {
        Integer num;
        Double d2;
        Double d3;
        File file;
        String str = (String) methodCall.argument("source_path");
        Integer num2 = (Integer) methodCall.argument("max_width");
        Integer num3 = (Integer) methodCall.argument("max_height");
        Double d4 = (Double) methodCall.argument("ratio_x");
        Double d5 = (Double) methodCall.argument("ratio_y");
        String str2 = (String) methodCall.argument("compress_format");
        Integer num4 = (Integer) methodCall.argument("compress_quality");
        ArrayList arrayList = (ArrayList) methodCall.argument("android.aspect_ratio_presets");
        String str3 = (String) methodCall.argument("android.crop_style");
        String str4 = (String) methodCall.argument("android.init_aspect_ratio");
        this.pendingResult = result;
        File cacheDir = this.activity.getCacheDir();
        if ("png".equals(str2)) {
            d3 = d5;
            StringBuilder sb = new StringBuilder();
            sb.append("image_cropper_");
            num = num3;
            d2 = d4;
            sb.append(new Date().getTime());
            sb.append(PictureMimeType.PNG);
            file = new File(cacheDir, sb.toString());
        } else {
            num = num3;
            d2 = d4;
            d3 = d5;
            file = new File(cacheDir, "image_cropper_" + new Date().getTime() + PictureMimeType.JPG);
        }
        Uri uriFromFile = Uri.fromFile(new File(str));
        Uri uriFromFile2 = Uri.fromFile(file);
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat("png".equals(str2) ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(num4 != null ? num4.intValue() : 90);
        options.setMaxBitmapSize(10000);
        if (TtmlNode.TEXT_EMPHASIS_MARK_CIRCLE.equals(str3)) {
            options.setCircleDimmedLayer(true);
        }
        setupUiCustomizedOptions(options, methodCall);
        if (arrayList != null && str4 != null) {
            ArrayList arrayList2 = new ArrayList();
            int i2 = 0;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                Map<?, ?> map = (Map) arrayList.get(i3);
                if (map != null) {
                    AspectRatio aspectRatio = parseAspectRatio(map);
                    String aspectRatioTitle = aspectRatio.getAspectRatioTitle();
                    arrayList2.add(aspectRatio);
                    if (str4.equals(aspectRatioTitle)) {
                        i2 = i3;
                    }
                }
            }
            options.setAspectRatioOptions(i2, (AspectRatio[]) arrayList2.toArray(new AspectRatio[0]));
        }
        UCrop uCropWithOptions = UCrop.of(uriFromFile, uriFromFile2).withOptions(options);
        if (num2 != null && num != null) {
            uCropWithOptions.withMaxResultSize(num2.intValue(), num.intValue());
        }
        if (d2 != null && d3 != null) {
            uCropWithOptions.withAspectRatio(d2.floatValue(), d3.floatValue());
        }
        Activity activity = this.activity;
        activity.startActivityForResult(uCropWithOptions.getIntent(activity), 69);
    }
}
