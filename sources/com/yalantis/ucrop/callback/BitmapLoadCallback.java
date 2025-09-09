package com.yalantis.ucrop.callback;

import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yalantis.ucrop.model.ExifInfo;

/* loaded from: classes4.dex */
public interface BitmapLoadCallback {
    void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull Uri uri, @Nullable Uri uri2);

    void onFailure(@NonNull Exception exc);
}
