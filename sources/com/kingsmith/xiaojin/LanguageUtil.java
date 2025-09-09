package com.kingsmith.xiaojin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.aliyun.iot.aep.sdk.framework.language.a;
import com.aliyun.iot.aep.sdk.framework.language.b;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.f;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0004J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0004J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0014\u001a\u00020\u0004H\u0002J\u0006\u0010\u001a\u001a\u00020\u0019J\u0018\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0004H\u0003R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u001c"}, d2 = {"Lcom/kingsmith/xiaojin/LanguageUtil;", "", "()V", "TAG", "", "editor", "Landroid/content/SharedPreferences$Editor;", "getEditor", "()Landroid/content/SharedPreferences$Editor;", "setEditor", "(Landroid/content/SharedPreferences$Editor;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "setSharedPreferences", "(Landroid/content/SharedPreferences;)V", "attachBaseContext", "Landroid/content/Context;", f.X, bc.N, "changeAppLanguage", "", "newLanguage", "getLocaleByLanguage", "Ljava/util/Locale;", "getSystemLocal", "updateResources", "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class LanguageUtil {

    @NotNull
    public static final LanguageUtil INSTANCE = new LanguageUtil();

    @NotNull
    private static final String TAG = "LanguageUtil";

    @Nullable
    private static SharedPreferences.Editor editor;

    @Nullable
    private static SharedPreferences sharedPreferences;

    private LanguageUtil() {
    }

    private final Locale getLocaleByLanguage(String language) {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        Intrinsics.areEqual(language, LanguageType.CHINESE.getLanguage());
        if (Intrinsics.areEqual(language, LanguageType.ENGLISH.getLanguage())) {
            locale = Locale.ENGLISH;
        }
        Intrinsics.checkNotNull(locale);
        return locale;
    }

    @TargetApi(24)
    private final Context updateResources(Context context, String language) {
        Resources resources = context.getResources();
        Locale localeByLanguage = getLocaleByLanguage(language);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(localeByLanguage);
        b.a();
        configuration.setLocales(a.a(new Locale[]{localeByLanguage}));
        Context contextCreateConfigurationContext = context.createConfigurationContext(configuration);
        Intrinsics.checkNotNullExpressionValue(contextCreateConfigurationContext, "createConfigurationContext(...)");
        return contextCreateConfigurationContext;
    }

    @NotNull
    public final Context attachBaseContext(@NotNull Context context, @NotNull String language) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(language, "language");
        return Build.VERSION.SDK_INT >= 24 ? updateResources(context, language) : context;
    }

    public final void changeAppLanguage(@NotNull Context context, @NotNull String newLanguage) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(newLanguage, "newLanguage");
        if (TextUtils.isEmpty(newLanguage)) {
            return;
        }
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale localeByLanguage = getLocaleByLanguage(newLanguage);
        Log.e("changeLanguage", localeByLanguage.getLanguage());
        if (Build.VERSION.SDK_INT >= 24) {
            b.a();
            configuration.setLocales(a.a(new Locale[]{localeByLanguage}));
            context.createConfigurationContext(configuration);
        } else {
            configuration.setLocale(localeByLanguage);
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Nullable
    public final SharedPreferences.Editor getEditor() {
        return editor;
    }

    @Nullable
    public final SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @NotNull
    public final Locale getSystemLocal() {
        if (Build.VERSION.SDK_INT >= 24) {
            Locale locale = Resources.getSystem().getConfiguration().getLocales().get(0);
            Intrinsics.checkNotNull(locale);
            return locale;
        }
        Locale locale2 = Locale.getDefault();
        Intrinsics.checkNotNull(locale2);
        return locale2;
    }

    public final void setEditor(@Nullable SharedPreferences.Editor editor2) {
        editor = editor2;
    }

    public final void setSharedPreferences(@Nullable SharedPreferences sharedPreferences2) {
        sharedPreferences = sharedPreferences2;
    }
}
