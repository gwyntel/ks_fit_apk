package com.luck.picture.lib.config;

import android.content.Context;
import com.luck.picture.lib.interfaces.OnInjectLayoutResourceListener;

/* loaded from: classes4.dex */
public final class InjectResourceSource {
    public static final int ALBUM_ITEM_LAYOUT_RESOURCE = 6;
    public static final int DEFAULT_LAYOUT_RESOURCE = 0;
    public static final int MAIN_ITEM_AUDIO_LAYOUT_RESOURCE = 5;
    public static final int MAIN_ITEM_IMAGE_LAYOUT_RESOURCE = 3;
    public static final int MAIN_ITEM_VIDEO_LAYOUT_RESOURCE = 4;
    public static final int MAIN_SELECTOR_LAYOUT_RESOURCE = 1;
    public static final int PREVIEW_GALLERY_ITEM_LAYOUT_RESOURCE = 9;
    public static final int PREVIEW_ITEM_AUDIO_LAYOUT_RESOURCE = 10;
    public static final int PREVIEW_ITEM_IMAGE_LAYOUT_RESOURCE = 7;
    public static final int PREVIEW_ITEM_VIDEO_LAYOUT_RESOURCE = 8;
    public static final int PREVIEW_LAYOUT_RESOURCE = 2;

    public static int getLayoutResource(Context context, int i2, SelectorConfig selectorConfig) {
        OnInjectLayoutResourceListener onInjectLayoutResourceListener;
        if (selectorConfig == null || (onInjectLayoutResourceListener = selectorConfig.onLayoutResourceListener) == null) {
            return 0;
        }
        return onInjectLayoutResourceListener.getLayoutResourceId(context, i2);
    }
}
