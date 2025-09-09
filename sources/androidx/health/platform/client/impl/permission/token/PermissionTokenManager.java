package androidx.health.platform.client.impl.permission.token;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public final class PermissionTokenManager {
    private static final String KEY_TOKEN = "token";
    private static final String PREFERENCES_FILE_NAME = "PermissionTokenManager.healthdata";

    private PermissionTokenManager() {
    }

    @Nullable
    public static String getCurrentToken(@NonNull Context context) {
        return getSharedPreferences(context).getString("token", null);
    }

    private static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
    }

    public static void setCurrentToken(@NonNull Context context, @Nullable String str) {
        getSharedPreferences(context).edit().putString("token", str).commit();
    }
}
