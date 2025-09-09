package com.leeson.image_pickers.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/* loaded from: classes4.dex */
public class ImageLoaderUtils {
    public static boolean assertValidRequest(Context context) {
        if (context instanceof Activity) {
            return !isDestroy((Activity) context);
        }
        if (context instanceof ContextWrapper) {
            if (((ContextWrapper) context).getBaseContext() instanceof Activity) {
                return !isDestroy((Activity) r2.getBaseContext());
            }
        }
        return true;
    }

    private static boolean isDestroy(Activity activity) {
        return activity == null || activity.isFinishing() || activity.isDestroyed();
    }
}
