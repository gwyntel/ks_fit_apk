package com.luck.picture.lib.config;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class Crop {
    public static final String EXTRA_ERROR = "com.yalantis.ucrop.Error";
    public static final String EXTRA_OUTPUT_CROP_ASPECT_RATIO = "com.yalantis.ucrop.CropAspectRatio";
    public static final String EXTRA_OUTPUT_IMAGE_HEIGHT = "com.yalantis.ucrop.ImageHeight";
    public static final String EXTRA_OUTPUT_IMAGE_WIDTH = "com.yalantis.ucrop.ImageWidth";
    public static final String EXTRA_OUTPUT_OFFSET_X = "com.yalantis.ucrop.OffsetX";
    public static final String EXTRA_OUTPUT_OFFSET_Y = "com.yalantis.ucrop.OffsetY";
    private static final String EXTRA_PREFIX = "com.yalantis.ucrop";
    public static final int REQUEST_CROP = 69;
    public static final int REQUEST_EDIT_CROP = 696;
    public static final int RESULT_CROP_ERROR = 96;

    @Nullable
    public static Throwable getError(@NonNull Intent intent) {
        return (Throwable) intent.getSerializableExtra("com.yalantis.ucrop.Error");
    }

    @Nullable
    public static Uri getOutput(@NonNull Intent intent) {
        Uri uri = (Uri) intent.getParcelableExtra("output");
        return uri == null ? (Uri) intent.getParcelableExtra("com.yalantis.ucrop.OutputUri") : uri;
    }

    public static float getOutputCropAspectRatio(@NonNull Intent intent) {
        return intent.getFloatExtra("com.yalantis.ucrop.CropAspectRatio", 0.0f);
    }

    public static String getOutputCustomExtraData(@NonNull Intent intent) {
        return intent.getStringExtra(CustomIntentKey.EXTRA_CUSTOM_EXTRA_DATA);
    }

    public static int getOutputImageHeight(@NonNull Intent intent) {
        return intent.getIntExtra("com.yalantis.ucrop.ImageHeight", -1);
    }

    public static int getOutputImageOffsetX(@NonNull Intent intent) {
        return intent.getIntExtra("com.yalantis.ucrop.OffsetX", 0);
    }

    public static int getOutputImageOffsetY(@NonNull Intent intent) {
        return intent.getIntExtra("com.yalantis.ucrop.OffsetY", 0);
    }

    public static int getOutputImageWidth(@NonNull Intent intent) {
        return intent.getIntExtra("com.yalantis.ucrop.ImageWidth", -1);
    }
}
