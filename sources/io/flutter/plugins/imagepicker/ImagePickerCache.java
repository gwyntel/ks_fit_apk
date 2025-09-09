package io.flutter.plugins.imagepicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import io.flutter.plugins.imagepicker.Messages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
class ImagePickerCache {
    private static final String FLUTTER_IMAGE_PICKER_IMAGE_PATH_KEY = "flutter_image_picker_image_path";
    static final String MAP_KEY_ERROR = "error";
    static final String MAP_KEY_IMAGE_QUALITY = "imageQuality";
    static final String MAP_KEY_MAX_HEIGHT = "maxHeight";
    static final String MAP_KEY_MAX_WIDTH = "maxWidth";
    static final String MAP_KEY_PATH_LIST = "pathList";
    static final String MAP_KEY_TYPE = "type";
    private static final String MAP_TYPE_VALUE_IMAGE = "image";
    private static final String MAP_TYPE_VALUE_VIDEO = "video";

    @VisibleForTesting
    static final String SHARED_PREFERENCES_NAME = "flutter_image_picker_shared_preference";
    private static final String SHARED_PREFERENCE_ERROR_CODE_KEY = "flutter_image_picker_error_code";
    private static final String SHARED_PREFERENCE_ERROR_MESSAGE_KEY = "flutter_image_picker_error_message";
    private static final String SHARED_PREFERENCE_IMAGE_QUALITY_KEY = "flutter_image_picker_image_quality";
    private static final String SHARED_PREFERENCE_MAX_HEIGHT_KEY = "flutter_image_picker_max_height";
    private static final String SHARED_PREFERENCE_MAX_WIDTH_KEY = "flutter_image_picker_max_width";
    private static final String SHARED_PREFERENCE_PENDING_IMAGE_URI_PATH_KEY = "flutter_image_picker_pending_image_uri";
    private static final String SHARED_PREFERENCE_TYPE_KEY = "flutter_image_picker_type";

    @NonNull
    private final Context context;

    /* renamed from: io.flutter.plugins.imagepicker.ImagePickerCache$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$flutter$plugins$imagepicker$ImagePickerCache$CacheType;

        static {
            int[] iArr = new int[CacheType.values().length];
            $SwitchMap$io$flutter$plugins$imagepicker$ImagePickerCache$CacheType = iArr;
            try {
                iArr[CacheType.IMAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$flutter$plugins$imagepicker$ImagePickerCache$CacheType[CacheType.VIDEO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum CacheType {
        IMAGE,
        VIDEO
    }

    ImagePickerCache(@NonNull Context context) {
        this.context = context;
    }

    private void setType(String str) {
        this.context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit().putString(SHARED_PREFERENCE_TYPE_KEY, str).apply();
    }

    void clear() {
        this.context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit().clear().apply();
    }

    Map<String, Object> getCacheMap() {
        Set<String> stringSet;
        HashMap map = new HashMap();
        boolean z2 = false;
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
        boolean z3 = true;
        if (sharedPreferences.contains(FLUTTER_IMAGE_PICKER_IMAGE_PATH_KEY) && (stringSet = sharedPreferences.getStringSet(FLUTTER_IMAGE_PICKER_IMAGE_PATH_KEY, null)) != null) {
            map.put(MAP_KEY_PATH_LIST, new ArrayList(stringSet));
            z2 = true;
        }
        if (sharedPreferences.contains(SHARED_PREFERENCE_ERROR_CODE_KEY)) {
            Messages.CacheRetrievalError.Builder builder = new Messages.CacheRetrievalError.Builder();
            builder.setCode(sharedPreferences.getString(SHARED_PREFERENCE_ERROR_CODE_KEY, ""));
            if (sharedPreferences.contains(SHARED_PREFERENCE_ERROR_MESSAGE_KEY)) {
                builder.setMessage(sharedPreferences.getString(SHARED_PREFERENCE_ERROR_MESSAGE_KEY, ""));
            }
            map.put("error", builder.build());
        } else {
            z3 = z2;
        }
        if (z3) {
            if (sharedPreferences.contains(SHARED_PREFERENCE_TYPE_KEY)) {
                map.put("type", sharedPreferences.getString(SHARED_PREFERENCE_TYPE_KEY, "").equals("video") ? Messages.CacheRetrievalType.VIDEO : Messages.CacheRetrievalType.IMAGE);
            }
            if (sharedPreferences.contains(SHARED_PREFERENCE_MAX_WIDTH_KEY)) {
                map.put(MAP_KEY_MAX_WIDTH, Double.valueOf(Double.longBitsToDouble(sharedPreferences.getLong(SHARED_PREFERENCE_MAX_WIDTH_KEY, 0L))));
            }
            if (sharedPreferences.contains(SHARED_PREFERENCE_MAX_HEIGHT_KEY)) {
                map.put(MAP_KEY_MAX_HEIGHT, Double.valueOf(Double.longBitsToDouble(sharedPreferences.getLong(SHARED_PREFERENCE_MAX_HEIGHT_KEY, 0L))));
            }
            map.put(MAP_KEY_IMAGE_QUALITY, Integer.valueOf(sharedPreferences.getInt(SHARED_PREFERENCE_IMAGE_QUALITY_KEY, 100)));
        }
        return map;
    }

    String retrievePendingCameraMediaUriPath() {
        return this.context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).getString(SHARED_PREFERENCE_PENDING_IMAGE_URI_PATH_KEY, "");
    }

    void saveDimensionWithOutputOptions(Messages.ImageSelectionOptions imageSelectionOptions) {
        SharedPreferences.Editor editorEdit = this.context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit();
        if (imageSelectionOptions.getMaxWidth() != null) {
            editorEdit.putLong(SHARED_PREFERENCE_MAX_WIDTH_KEY, Double.doubleToRawLongBits(imageSelectionOptions.getMaxWidth().doubleValue()));
        }
        if (imageSelectionOptions.getMaxHeight() != null) {
            editorEdit.putLong(SHARED_PREFERENCE_MAX_HEIGHT_KEY, Double.doubleToRawLongBits(imageSelectionOptions.getMaxHeight().doubleValue()));
        }
        editorEdit.putInt(SHARED_PREFERENCE_IMAGE_QUALITY_KEY, imageSelectionOptions.getQuality().intValue());
        editorEdit.apply();
    }

    void savePendingCameraMediaUriPath(Uri uri) {
        this.context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit().putString(SHARED_PREFERENCE_PENDING_IMAGE_URI_PATH_KEY, uri.getPath()).apply();
    }

    void saveResult(@Nullable ArrayList<String> arrayList, @Nullable String str, @Nullable String str2) {
        SharedPreferences.Editor editorEdit = this.context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit();
        if (arrayList != null) {
            editorEdit.putStringSet(FLUTTER_IMAGE_PICKER_IMAGE_PATH_KEY, new HashSet(arrayList));
        }
        if (str != null) {
            editorEdit.putString(SHARED_PREFERENCE_ERROR_CODE_KEY, str);
        }
        if (str2 != null) {
            editorEdit.putString(SHARED_PREFERENCE_ERROR_MESSAGE_KEY, str2);
        }
        editorEdit.apply();
    }

    void saveType(CacheType cacheType) {
        int i2 = AnonymousClass1.$SwitchMap$io$flutter$plugins$imagepicker$ImagePickerCache$CacheType[cacheType.ordinal()];
        if (i2 == 1) {
            setType("image");
        } else {
            if (i2 != 2) {
                return;
            }
            setType("video");
        }
    }
}
