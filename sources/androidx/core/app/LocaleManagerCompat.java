package androidx.core.app;

import android.app.LocaleManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import androidx.annotation.AnyThread;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.os.LocaleListCompat;
import java.util.Locale;

/* loaded from: classes.dex */
public final class LocaleManagerCompat {

    @RequiresApi(21)
    static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        static String a(Locale locale) {
            return locale.toLanguageTag();
        }
    }

    @RequiresApi(24)
    static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        static LocaleListCompat a(Configuration configuration) {
            return LocaleListCompat.forLanguageTags(configuration.getLocales().toLanguageTags());
        }
    }

    @RequiresApi(33)
    static class Api33Impl {
        private Api33Impl() {
        }

        @DoNotInline
        static LocaleList a(Object obj) {
            return ((LocaleManager) obj).getApplicationLocales();
        }

        @DoNotInline
        static LocaleList b(Object obj) {
            return ((LocaleManager) obj).getSystemLocales();
        }
    }

    private LocaleManagerCompat() {
    }

    static LocaleListCompat a(Configuration configuration) {
        return Build.VERSION.SDK_INT >= 24 ? Api24Impl.a(configuration) : LocaleListCompat.forLanguageTags(Api21Impl.a(configuration.locale));
    }

    @NonNull
    @AnyThread
    public static LocaleListCompat getApplicationLocales(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 33) {
            return LocaleListCompat.forLanguageTags(AppLocalesStorageHelper.readLocales(context));
        }
        Object localeManagerForApplication = getLocaleManagerForApplication(context);
        return localeManagerForApplication != null ? LocaleListCompat.wrap(Api33Impl.a(localeManagerForApplication)) : LocaleListCompat.getEmptyLocaleList();
    }

    @RequiresApi(33)
    private static Object getLocaleManagerForApplication(Context context) {
        return context.getSystemService("locale");
    }

    @NonNull
    @AnyThread
    public static LocaleListCompat getSystemLocales(@NonNull Context context) {
        LocaleListCompat emptyLocaleList = LocaleListCompat.getEmptyLocaleList();
        if (Build.VERSION.SDK_INT < 33) {
            return a(Resources.getSystem().getConfiguration());
        }
        Object localeManagerForApplication = getLocaleManagerForApplication(context);
        return localeManagerForApplication != null ? LocaleListCompat.wrap(Api33Impl.b(localeManagerForApplication)) : emptyLocaleList;
    }
}
