package com.luck.picture.lib.permissions;

import android.content.Context;
import androidx.annotation.RequiresApi;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.utils.SdkVersionUtils;

/* loaded from: classes4.dex */
public class PermissionConfig {
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";

    @RequiresApi(api = 33)
    public static final String READ_MEDIA_AUDIO = "android.permission.READ_MEDIA_AUDIO";

    @RequiresApi(api = 33)
    public static final String READ_MEDIA_IMAGES = "android.permission.READ_MEDIA_IMAGES";

    @RequiresApi(api = 33)
    public static final String READ_MEDIA_VIDEO = "android.permission.READ_MEDIA_VIDEO";

    @RequiresApi(api = 34)
    public static final String READ_MEDIA_VISUAL_USER_SELECTED = "android.permission.READ_MEDIA_VISUAL_USER_SELECTED";
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    public static String[] CURRENT_REQUEST_PERMISSION = new String[0];
    public static final String[] CAMERA = {"android.permission.CAMERA"};

    public static String[] getReadPermissionArray(Context context, int i2) {
        if (SdkVersionUtils.isUPSIDE_DOWN_CAKE()) {
            int i3 = context.getApplicationInfo().targetSdkVersion;
            return i2 == SelectMimeType.ofImage() ? i3 >= 34 ? new String[]{READ_MEDIA_VISUAL_USER_SELECTED, READ_MEDIA_IMAGES} : i3 == 33 ? new String[]{READ_MEDIA_IMAGES} : new String[]{READ_EXTERNAL_STORAGE} : i2 == SelectMimeType.ofVideo() ? i3 >= 34 ? new String[]{READ_MEDIA_VISUAL_USER_SELECTED, READ_MEDIA_VIDEO} : i3 == 33 ? new String[]{READ_MEDIA_VIDEO} : new String[]{READ_EXTERNAL_STORAGE} : i2 == SelectMimeType.ofAudio() ? i3 >= 33 ? new String[]{READ_MEDIA_AUDIO} : new String[]{READ_EXTERNAL_STORAGE} : i3 >= 34 ? new String[]{READ_MEDIA_VISUAL_USER_SELECTED, READ_MEDIA_IMAGES, READ_MEDIA_VIDEO} : i3 == 33 ? new String[]{READ_MEDIA_IMAGES, READ_MEDIA_VIDEO} : new String[]{READ_EXTERNAL_STORAGE};
        }
        if (!SdkVersionUtils.isTIRAMISU()) {
            return new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
        }
        int i4 = context.getApplicationInfo().targetSdkVersion;
        return i2 == SelectMimeType.ofImage() ? i4 >= 33 ? new String[]{READ_MEDIA_IMAGES} : new String[]{READ_EXTERNAL_STORAGE} : i2 == SelectMimeType.ofVideo() ? i4 >= 33 ? new String[]{READ_MEDIA_VIDEO} : new String[]{READ_EXTERNAL_STORAGE} : i2 == SelectMimeType.ofAudio() ? i4 >= 33 ? new String[]{READ_MEDIA_AUDIO} : new String[]{READ_EXTERNAL_STORAGE} : i4 >= 33 ? new String[]{READ_MEDIA_IMAGES, READ_MEDIA_VIDEO} : new String[]{READ_EXTERNAL_STORAGE};
    }
}
