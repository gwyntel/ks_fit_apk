package com.luck.picture.lib.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import com.luck.picture.lib.utils.SpUtils;
import java.util.Locale;

/* loaded from: classes4.dex */
public class PictureLanguageUtils {
    private static final String KEY_LOCALE = "KEY_LOCALE";
    private static final String VALUE_FOLLOW_SYSTEM = "VALUE_FOLLOW_SYSTEM";

    private PictureLanguageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static void applyLanguage(@NonNull Context context, @NonNull Locale locale) {
        applyLanguage(context, locale, false);
    }

    private static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        int length;
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || (length = charSequence.length()) != charSequence2.length()) {
            return false;
        }
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return charSequence.equals(charSequence2);
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (charSequence.charAt(i2) != charSequence2.charAt(i2)) {
                return false;
            }
        }
        return true;
    }

    public static void setAppLanguage(Context context, int i2, int i3) {
        if (i2 >= 0) {
            applyLanguage(context, LocaleTransform.getLanguage(i2));
        } else if (i3 >= 0) {
            applyLanguage(context, LocaleTransform.getLanguage(i3));
        } else {
            setDefaultLanguage(context);
        }
    }

    private static void setDefaultLanguage(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(Locale.getDefault());
        context.createConfigurationContext(configuration);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private static void updateLanguage(@NonNull Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale2 = configuration.locale;
        if (equals(locale2.getLanguage(), locale.getLanguage()) && equals(locale2.getCountry(), locale.getCountry())) {
            return;
        }
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(locale);
        context.createConfigurationContext(configuration);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private static void applyLanguage(@NonNull Context context, @NonNull Locale locale, boolean z2) {
        if (z2) {
            SpUtils.putString(context, KEY_LOCALE, VALUE_FOLLOW_SYSTEM);
        } else {
            SpUtils.putString(context, KEY_LOCALE, locale.getLanguage() + "$" + locale.getCountry());
        }
        updateLanguage(context, locale);
    }
}
