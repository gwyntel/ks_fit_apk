package com.aliyun.iot.aep.sdk.framework.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import androidx.appcompat.view.ContextThemeWrapper;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.sdk.bone.plugins.config.BoneConfig;
import com.aliyun.iot.aep.oa.OALanguageHelper;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.R;
import com.aliyun.iot.aep.sdk.framework.config.GlobalConfig;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKManager;
import com.aliyun.iot.aep.sdk.init.PushManagerHelper;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.notification.model.AdvertisementOption;
import com.umeng.analytics.pro.bc;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Locale;

/* loaded from: classes3.dex */
public class LanguageManager {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f11775a = false;

    public static Locale GetAppLocal() {
        Locale locale;
        String language = GlobalConfig.getInstance().getLanguage();
        locale = Locale.getDefault();
        language.hashCode();
        switch (language) {
            case "de-DE":
                return Locale.GERMANY;
            case "en-US":
                return Locale.US;
            case "es-ES":
                return new Locale("es", "ES");
            case "fr-FR":
                return Locale.FRANCE;
            case "ja-JP":
                return Locale.JAPAN;
            case "ko-KR":
                return Locale.KOREA;
            case "nl-NL":
                return new Locale("nl", "NL");
            case "pl-PL":
                return new Locale(bc.aF, "PL");
            case "pt-PT":
                return new Locale(AdvertisementOption.PRIORITY_VALID_TIME, "PT");
            case "ru-RU":
                return new Locale("ru", "RU");
            case "zh-CN":
                return Locale.SIMPLIFIED_CHINESE;
            default:
                return locale;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Locale GetSysLocale() {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.iot.aep.sdk.framework.language.LanguageManager.GetSysLocale():java.util.Locale");
    }

    private static boolean a(String str) {
        String[] strArrSplit;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            strArrSplit = str.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            if (strArrSplit.length == 1) {
                strArrSplit = str.split(OpenAccountUIConstants.UNDER_LINE);
            }
        } catch (Exception unused) {
        }
        return strArrSplit.length == 2;
    }

    public static String getDefaultLanguage() {
        return makeLanguageString(GetSysLocale());
    }

    public static void handleLanguageChanged() {
        a();
    }

    public static void initAppLanguage() {
        f11775a = true;
        a();
    }

    public static String loadAppLanguage() {
        String language = IoTSmart.getLanguage();
        ALog.d("LanguageManager", "load language success, language:" + language);
        return language;
    }

    public static String[] loadLanguageInfo(String str) {
        String[] strArrSplit = str.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        return strArrSplit.length == 1 ? str.split(OpenAccountUIConstants.UNDER_LINE) : strArrSplit;
    }

    public static String makeLanguageString(Locale locale) {
        return locale.getLanguage() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + locale.getCountry();
    }

    public static boolean needSwitchLanguage() {
        return f11775a;
    }

    public static Context replaceLanguage(Context context) {
        if (!f11775a || Build.VERSION.SDK_INT < 24) {
            return context;
        }
        final Configuration configuration = context.getResources().getConfiguration();
        String[] strArrLoadLanguageInfo = LanguageHelper.LoadLanguageInfo(loadAppLanguage());
        Locale locale = AApplication.getInstance().getResources().getConfiguration().locale;
        try {
            locale = new Locale(strArrLoadLanguageInfo[0], strArrLoadLanguageInfo[1]);
        } catch (Exception unused) {
        }
        b.a();
        configuration.setLocales(a.a(new Locale[]{locale}));
        return new ContextThemeWrapper(context.createConfigurationContext(configuration), R.style.Theme_AppCompat_Empty) { // from class: com.aliyun.iot.aep.sdk.framework.language.LanguageManager.1
            @Override // androidx.appcompat.view.ContextThemeWrapper
            public void applyOverrideConfiguration(Configuration configuration2) {
                if (configuration2 != null) {
                    configuration2.setTo(configuration);
                }
                super.applyOverrideConfiguration(configuration2);
            }
        };
    }

    public static void setLanguage(String str) {
        try {
            if (!a(str)) {
                ALog.e("LanguageManager", "set wrong language, the format is like 'zh-CN' or 'en-US'");
                return;
            }
            String[] strArrLoadLanguageInfo = loadLanguageInfo(str);
            Locale locale = new Locale(strArrLoadLanguageInfo[0], strArrLoadLanguageInfo[1]);
            try {
                try {
                    if (SDKManager.isOAAvailable()) {
                        OALanguageHelper.setLanguageCode(locale);
                    } else {
                        Resources resources = AApplication.getInstance().getResources();
                        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                        Configuration configuration = resources.getConfiguration();
                        configuration.setLocale(locale);
                        resources.updateConfiguration(configuration, displayMetrics);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                    ALog.e("LanguageManager", "set oa language throw = " + th);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                ALog.e("LanguageManager", "set oa language error:" + e2.toString());
            }
            if (str.equals(GlobalConfig.getInstance().getLanguage())) {
                ALog.e("LanguageManager", "same language, ignore");
                return;
            }
            try {
                try {
                    IoTAPIClientImpl.getInstance().setLanguage(makeLanguageString(locale));
                } catch (Throwable th2) {
                    th2.printStackTrace();
                    ALog.e("LanguageManager", "set apiclient language throe:" + th2);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                ALog.e("LanguageManager", "set apiclient language error:" + e3.toString());
            }
            if (SDKManager.isRNAvailable()) {
                BoneConfig.set(bc.N, makeLanguageString(locale));
            }
            ALog.e("LanguageManager", "set language Push bindUser");
            try {
                PushManagerHelper.getInstance().bindUserSafely();
            } catch (Throwable th3) {
                th3.printStackTrace();
                ALog.e("LanguageManager", "PushManagerHelper.getInstance().bindUser throw:" + th3);
            }
            GlobalConfig.getInstance().setLanguage(str);
        } catch (Exception e4) {
            e4.printStackTrace();
            ALog.e("LanguageManager", "set language error:" + e4.toString());
        }
    }

    public static void updateApplicationLanguage(Resources resources) {
        if (f11775a) {
            a(resources);
        }
    }

    private static void a() {
        if (f11775a) {
            a(AApplication.getInstance().getResources());
        }
    }

    private static void a(Resources resources) {
        if (f11775a && resources != null) {
            String strLoadAppLanguage = loadAppLanguage();
            if (!TextUtils.isEmpty(strLoadAppLanguage)) {
                try {
                    String[] strArrLoadLanguageInfo = LanguageHelper.LoadLanguageInfo(strLoadAppLanguage);
                    a(resources, new Locale(strArrLoadLanguageInfo[0], strArrLoadLanguageInfo[1]));
                    return;
                } catch (Exception unused) {
                    ALog.e("LanguageManager", "use sharedPreference language failed, will use default language");
                }
            }
            String language = resources.getConfiguration().locale.getLanguage();
            if ("zh".equalsIgnoreCase(language)) {
                a(resources, Locale.SIMPLIFIED_CHINESE);
                return;
            }
            if ("fr".equalsIgnoreCase(language)) {
                a(resources, Locale.FRANCE);
                return;
            }
            if ("de".equalsIgnoreCase(language)) {
                a(resources, Locale.GERMANY);
                return;
            }
            if ("en".equalsIgnoreCase(language)) {
                a(resources, Locale.US);
                return;
            }
            if ("ja".equalsIgnoreCase(language)) {
                a(resources, Locale.JAPAN);
                return;
            }
            if ("ko".equalsIgnoreCase(language)) {
                a(resources, Locale.KOREA);
                return;
            }
            if ("es".equalsIgnoreCase(language)) {
                a(resources, new Locale("es", "ES"));
                return;
            }
            if ("ru".equalsIgnoreCase(language)) {
                a(resources, new Locale("ru", "RU"));
                return;
            }
            if ("hi".equalsIgnoreCase(language)) {
                a(resources, new Locale("hi", "IN"));
                return;
            }
            if (AdvanceSetting.NETWORK_TYPE.equalsIgnoreCase(language)) {
                a(resources, new Locale(AdvanceSetting.NETWORK_TYPE, "IT"));
                return;
            }
            if (AdvertisementOption.PRIORITY_VALID_TIME.equalsIgnoreCase(language)) {
                a(resources, new Locale(AdvertisementOption.PRIORITY_VALID_TIME, "PT"));
                return;
            }
            if ("nl".equalsIgnoreCase(language)) {
                a(resources, new Locale("nl", "NL"));
            } else if (bc.aF.equalsIgnoreCase(language)) {
                a(resources, new Locale(bc.aF, "PL"));
            } else {
                a(resources, Locale.US);
            }
        }
    }

    private static void a(Resources resources, Locale locale) {
        if (resources == null || locale == null) {
            return;
        }
        Configuration configuration = resources.getConfiguration();
        if (configuration == null) {
            ALog.e("LanguageManager", "configuration is null");
            return;
        }
        if (configuration.locale.equals(locale)) {
            return;
        }
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        ALog.d("LanguageManager", "current language = " + LanguageHelper.MakeLanguageString(locale));
    }
}
