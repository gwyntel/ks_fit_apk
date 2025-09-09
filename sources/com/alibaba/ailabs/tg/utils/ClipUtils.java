package com.alibaba.ailabs.tg.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.support.api.entity.core.CommonCode;

/* loaded from: classes2.dex */
public class ClipUtils {
    private static final String CLIP_LABEL = "genie_clip_label";

    public static void copyIntent(@NonNull Context context, Intent intent) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        if (clipboardManager == null) {
            return;
        }
        clipboardManager.setPrimaryClip(ClipData.newIntent(CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, intent));
    }

    public static void copyText(Context context, CharSequence charSequence) {
        ClipboardManager clipboardManager;
        if (context == null || TextUtils.isEmpty(charSequence) || (clipboardManager = (ClipboardManager) context.getSystemService("clipboard")) == null) {
            return;
        }
        clipboardManager.setPrimaryClip(ClipData.newPlainText(CLIP_LABEL, charSequence));
    }

    public static void copyUri(@NonNull Context context, Uri uri) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        if (clipboardManager == null) {
            return;
        }
        clipboardManager.setPrimaryClip(ClipData.newUri(context.getContentResolver(), ShareConstants.MEDIA_URI, uri));
    }

    public static Intent getIntent(@NonNull Context context) {
        ClipData primaryClip;
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        if (clipboardManager == null || (primaryClip = clipboardManager.getPrimaryClip()) == null || primaryClip.getItemCount() <= 0) {
            return null;
        }
        return primaryClip.getItemAt(0).getIntent();
    }

    public static CharSequence getText(@NonNull Context context) {
        ClipData primaryClip;
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        if (clipboardManager == null || (primaryClip = clipboardManager.getPrimaryClip()) == null || primaryClip.getItemCount() <= 0) {
            return null;
        }
        return primaryClip.getItemAt(0).coerceToText(context);
    }

    public static Uri getUri(@NonNull Context context) {
        ClipData primaryClip;
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        if (clipboardManager == null || (primaryClip = clipboardManager.getPrimaryClip()) == null || primaryClip.getItemCount() <= 0) {
            return null;
        }
        return primaryClip.getItemAt(0).getUri();
    }
}
