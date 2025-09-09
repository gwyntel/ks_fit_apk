package io.flutter.plugin.localization;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.xiaomi.mipush.sdk.Constants;
import io.flutter.embedding.engine.systemchannels.LocalizationChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class LocalizationPlugin {

    @NonNull
    private final Context context;

    @NonNull
    private final LocalizationChannel localizationChannel;

    @SuppressLint({"AppBundleLocaleChanges", "DiscouragedApi"})
    @VisibleForTesting
    final LocalizationChannel.LocalizationMessageHandler localizationMessageHandler;

    public LocalizationPlugin(@NonNull Context context, @NonNull LocalizationChannel localizationChannel) {
        LocalizationChannel.LocalizationMessageHandler localizationMessageHandler = new LocalizationChannel.LocalizationMessageHandler() { // from class: io.flutter.plugin.localization.LocalizationPlugin.1
            @Override // io.flutter.embedding.engine.systemchannels.LocalizationChannel.LocalizationMessageHandler
            public String getStringResource(@NonNull String str, @Nullable String str2) {
                Context contextCreateConfigurationContext = LocalizationPlugin.this.context;
                if (str2 != null) {
                    Locale localeLocaleFromString = LocalizationPlugin.localeFromString(str2);
                    Configuration configuration = new Configuration(LocalizationPlugin.this.context.getResources().getConfiguration());
                    configuration.setLocale(localeLocaleFromString);
                    contextCreateConfigurationContext = LocalizationPlugin.this.context.createConfigurationContext(configuration);
                }
                int identifier = contextCreateConfigurationContext.getResources().getIdentifier(str, "string", LocalizationPlugin.this.context.getPackageName());
                if (identifier != 0) {
                    return contextCreateConfigurationContext.getResources().getString(identifier);
                }
                return null;
            }
        };
        this.localizationMessageHandler = localizationMessageHandler;
        this.context = context;
        this.localizationChannel = localizationChannel;
        localizationChannel.setLocalizationMessageHandler(localizationMessageHandler);
    }

    @NonNull
    public static Locale localeFromString(@NonNull String str) {
        String str2;
        String[] strArrSplit = str.replace('_', '-').split(Constants.ACCEPT_TIME_SEPARATOR_SERVER, -1);
        String str3 = strArrSplit[0];
        String str4 = "";
        int i2 = 1;
        if (strArrSplit.length <= 1 || strArrSplit[1].length() != 4) {
            str2 = "";
        } else {
            str2 = strArrSplit[1];
            i2 = 2;
        }
        if (strArrSplit.length > i2 && strArrSplit[i2].length() >= 2 && strArrSplit[i2].length() <= 3) {
            str4 = strArrSplit[i2];
        }
        return new Locale(str3, str4, str2);
    }

    @Nullable
    public Locale resolveNativeLocale(@Nullable List<Locale> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            ArrayList arrayList = new ArrayList();
            LocaleList locales = this.context.getResources().getConfiguration().getLocales();
            int size = locales.size();
            for (int i3 = 0; i3 < size; i3++) {
                Locale locale = locales.get(i3);
                String language = locale.getLanguage();
                if (!locale.getScript().isEmpty()) {
                    language = language + Constants.ACCEPT_TIME_SEPARATOR_SERVER + locale.getScript();
                }
                if (!locale.getCountry().isEmpty()) {
                    language = language + Constants.ACCEPT_TIME_SEPARATOR_SERVER + locale.getCountry();
                }
                arrayList.add(b.a(language));
                c.a();
                arrayList.add(b.a(locale.getLanguage()));
                c.a();
                arrayList.add(b.a(locale.getLanguage() + "-*"));
            }
            Locale localeLookup = Locale.lookup(arrayList, list);
            return localeLookup != null ? localeLookup : list.get(0);
        }
        if (i2 < 24) {
            Locale locale2 = this.context.getResources().getConfiguration().locale;
            if (locale2 != null) {
                for (Locale locale3 : list) {
                    if (locale2.equals(locale3)) {
                        return locale3;
                    }
                }
                for (Locale locale4 : list) {
                    if (locale2.getLanguage().equals(locale4.toString())) {
                        return locale4;
                    }
                }
            }
            return list.get(0);
        }
        LocaleList locales2 = this.context.getResources().getConfiguration().getLocales();
        for (int i4 = 0; i4 < locales2.size(); i4++) {
            Locale locale5 = locales2.get(i4);
            for (Locale locale6 : list) {
                if (locale5.equals(locale6)) {
                    return locale6;
                }
            }
            for (Locale locale7 : list) {
                if (locale5.getLanguage().equals(locale7.toLanguageTag())) {
                    return locale7;
                }
            }
            for (Locale locale8 : list) {
                if (locale5.getLanguage().equals(locale8.getLanguage())) {
                    return locale8;
                }
            }
        }
        return list.get(0);
    }

    public void sendLocalesToFlutter(@NonNull Configuration configuration) {
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 24) {
            LocaleList locales = configuration.getLocales();
            int size = locales.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(locales.get(i2));
            }
        } else {
            arrayList.add(configuration.locale);
        }
        this.localizationChannel.sendLocales(arrayList);
    }
}
