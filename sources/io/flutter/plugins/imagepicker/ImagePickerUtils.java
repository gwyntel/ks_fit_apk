package io.flutter.plugins.imagepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.activity.result.contract.ActivityResultContracts;
import io.flutter.plugins.imagepicker.Messages;
import java.util.Arrays;

/* loaded from: classes4.dex */
final class ImagePickerUtils {
    ImagePickerUtils() {
    }

    static int getLimitFromOption(Messages.GeneralOptions generalOptions) {
        Long limit = generalOptions.getLimit();
        int maxItems = getMaxItems();
        return (limit == null || limit.longValue() >= ((long) maxItems)) ? maxItems : j.a(limit.longValue());
    }

    @SuppressLint({"NewApi", "ClassVerificationFailure"})
    static int getMaxItems() {
        if (ActivityResultContracts.PickVisualMedia.INSTANCE.isSystemPickerAvailable$activity_release()) {
            return MediaStore.getPickImagesMaxLimit();
        }
        return Integer.MAX_VALUE;
    }

    private static PackageInfo getPermissionsPackageInfoPreApi33(PackageManager packageManager, String str) throws PackageManager.NameNotFoundException {
        return packageManager.getPackageInfo(str, 4096);
    }

    private static boolean isPermissionPresentInManifest(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return Arrays.asList((Build.VERSION.SDK_INT >= 33 ? packageManager.getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM)) : getPermissionsPackageInfoPreApi33(packageManager, context.getPackageName())).requestedPermissions).contains(str);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    static boolean needRequestCameraPermission(Context context) {
        return isPermissionPresentInManifest(context, "android.permission.CAMERA");
    }
}
