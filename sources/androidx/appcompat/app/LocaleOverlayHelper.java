package androidx.appcompat.app;

import androidx.annotation.RequiresApi;
import androidx.core.os.LocaleListCompat;
import java.util.LinkedHashSet;
import java.util.Locale;

@RequiresApi(24)
/* loaded from: classes.dex */
final class LocaleOverlayHelper {
    private LocaleOverlayHelper() {
    }

    static LocaleListCompat a(LocaleListCompat localeListCompat, LocaleListCompat localeListCompat2) {
        return (localeListCompat == null || localeListCompat.isEmpty()) ? LocaleListCompat.getEmptyLocaleList() : combineLocales(localeListCompat, localeListCompat2);
    }

    private static LocaleListCompat combineLocales(LocaleListCompat localeListCompat, LocaleListCompat localeListCompat2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int i2 = 0;
        while (i2 < localeListCompat.size() + localeListCompat2.size()) {
            Locale locale = i2 < localeListCompat.size() ? localeListCompat.get(i2) : localeListCompat2.get(i2 - localeListCompat.size());
            if (locale != null) {
                linkedHashSet.add(locale);
            }
            i2++;
        }
        return LocaleListCompat.create((Locale[]) linkedHashSet.toArray(new Locale[linkedHashSet.size()]));
    }
}
