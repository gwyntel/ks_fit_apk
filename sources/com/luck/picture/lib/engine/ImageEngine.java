package com.luck.picture.lib.engine;

import android.content.Context;
import android.widget.ImageView;

/* loaded from: classes4.dex */
public interface ImageEngine {
    void loadAlbumCover(Context context, String str, ImageView imageView);

    void loadGridImage(Context context, String str, ImageView imageView);

    void loadImage(Context context, ImageView imageView, String str, int i2, int i3);

    void loadImage(Context context, String str, ImageView imageView);

    void pauseRequests(Context context);

    void resumeRequests(Context context);
}
