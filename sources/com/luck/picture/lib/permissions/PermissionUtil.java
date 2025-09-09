package com.luck.picture.lib.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.luck.picture.lib.utils.SpUtils;

/* loaded from: classes4.dex */
public class PermissionUtil {
    public static final String ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION = "android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION";
    public static final int DEFAULT = 0;
    public static final int REFUSE = 2;
    public static final int REFUSE_PERMANENT = 3;
    public static final int SUCCESS = 1;

    public static int getPermissionStatus(Activity activity, String str) {
        int iCheckSelfPermission = ContextCompat.checkSelfPermission(activity, str);
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, str)) {
            return 2;
        }
        if (iCheckSelfPermission == 0) {
            return 1;
        }
        return !SpUtils.contains(activity, str) ? 0 : 3;
    }

    public static void goIntentSetting(Fragment fragment, int i2) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", fragment.getActivity().getPackageName(), null));
            fragment.startActivityForResult(intent, i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean hasPermissions(@NonNull Context context, @NonNull @Size(min = 1) String... strArr) {
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(context, str) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllGranted(Context context, String[] strArr, int[] iArr) {
        boolean z2 = true;
        boolean z3 = context.getApplicationInfo().targetSdkVersion >= 34 && ContextCompat.checkSelfPermission(context, PermissionConfig.READ_MEDIA_VISUAL_USER_SELECTED) == 0;
        if (iArr.length <= 0) {
            return false;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= iArr.length) {
                break;
            }
            if (iArr[i2] == 0) {
                i2++;
            } else if (!z3 || (!strArr[i2].equals(PermissionConfig.READ_MEDIA_IMAGES) && !strArr[i2].equals(PermissionConfig.READ_MEDIA_VIDEO))) {
                z2 = false;
            }
        }
        return z2;
    }
}
