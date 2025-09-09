package com.huawei.hms.scankit.p;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseArray;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.huawei.hms.framework.common.SystemPropUtils;
import com.huawei.hms.framework.common.hianalytics.WiseOpenHianalyticsData;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanBase;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import java.util.LinkedHashMap;

/* loaded from: classes4.dex */
abstract class u3 {

    /* renamed from: d, reason: collision with root package name */
    static String f17840d = "FORMAT_UNKNOWN";

    /* renamed from: e, reason: collision with root package name */
    static String f17841e = "OTHER";

    /* renamed from: f, reason: collision with root package name */
    static SparseArray<String> f17842f = new a();

    /* renamed from: g, reason: collision with root package name */
    static SparseArray<String> f17843g = new b();

    /* renamed from: a, reason: collision with root package name */
    Context f17844a;

    /* renamed from: b, reason: collision with root package name */
    LinkedHashMap<String, String> f17845b = new LinkedHashMap<>();

    /* renamed from: c, reason: collision with root package name */
    volatile long f17846c;

    class a extends SparseArray<String> {
        a() {
            put(HmsScanBase.AZTEC_SCAN_TYPE, "AZTEC");
            put(HmsScanBase.CODABAR_SCAN_TYPE, "CODABAR");
            put(HmsScanBase.CODE39_SCAN_TYPE, "CODE39");
            put(HmsScanBase.CODE93_SCAN_TYPE, "CODE93");
            put(HmsScanBase.CODE128_SCAN_TYPE, "CODE128");
            put(HmsScanBase.DATAMATRIX_SCAN_TYPE, "DATAMATRIX");
            put(HmsScanBase.EAN8_SCAN_TYPE, "EAN8");
            put(HmsScanBase.EAN13_SCAN_TYPE, "EAN13");
            put(HmsScanBase.ITF14_SCAN_TYPE, "ITF14");
            put(HmsScanBase.PDF417_SCAN_TYPE, "PDF417");
            put(HmsScanBase.QRCODE_SCAN_TYPE, "QRCODE");
            put(HmsScanBase.UPCCODE_A_SCAN_TYPE, "UPCCODE_A");
            put(HmsScanBase.UPCCODE_E_SCAN_TYPE, "UPCCODE_E");
            put(HmsScanBase.FORMAT_UNKNOWN, u3.f17840d);
        }
    }

    class b extends SparseArray<String> {
        b() {
            put(HmsScan.ARTICLE_NUMBER_FORM, "ARTICLE_NUMBER");
            put(HmsScan.EMAIL_CONTENT_FORM, "EMAIL_CONTENT");
            put(HmsScan.TEL_PHONE_NUMBER_FORM, "TEL_PHONE_NUMBER");
            put(HmsScan.PURE_TEXT_FORM, "PURE_TEXT");
            put(HmsScan.SMS_FORM, "SMS");
            put(HmsScan.URL_FORM, "URL");
            put(HmsScan.WIFI_CONNECT_INFO_FORM, "WIFI_CONNECT_INFO");
            put(HmsScan.EVENT_INFO_FORM, "EVENT_INFO");
            put(HmsScan.CONTACT_DETAIL_FORM, "CONTACT_DETAIL");
            put(HmsScan.DRIVER_INFO_FORM, "DRIVER_INFO");
            put(HmsScan.LOCATION_COORDINATE_FORM, "LOCATION_COORDINATE");
            put(HmsScan.ISBN_NUMBER_FORM, "ISBN_NUMBER");
            put(-1, u3.f17841e);
        }
    }

    u3(Bundle bundle, Context context) throws PackageManager.NameNotFoundException {
        this.f17844a = context;
        b(bundle);
    }

    private void b(Bundle bundle) throws PackageManager.NameNotFoundException {
        try {
            String packageName = this.f17844a.getPackageName();
            this.f17845b.put("package", packageName);
            if (bundle == null || !bundle.containsKey("appid")) {
                this.f17845b.put("appid", packageName);
            } else {
                this.f17845b.put("appid", bundle.getString("appid"));
            }
            PackageManager packageManager = this.f17844a.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 128);
            this.f17845b.put("appName", applicationInfo.loadLabel(packageManager).toString());
            this.f17845b.put("version", a(applicationInfo.metaData));
            String strD = d();
            this.f17845b.put("hmscoreVersion", strD);
            this.f17845b.put("isHMSCore", "unknown".equals(strD) ? "0" : "1");
        } catch (PackageManager.NameNotFoundException unused) {
            o4.b("HaLog", "PackageManager.NameNotFoundException");
        } catch (Exception unused2) {
            o4.b("HaLog", "initValue Exception");
        }
        try {
            this.f17845b.put("sdkName", "scankit");
            this.f17845b.put("algopt", b());
            this.f17845b.put("isFullSdk", "FULLSDK");
            this.f17845b.put(WiseOpenHianalyticsData.UNION_APP_VERSION, c());
            if (b4.f17005a) {
                this.f17845b.put("apkVersion", b4.f17006b);
            } else {
                this.f17845b.put("apkVersion", "unknown");
            }
            this.f17845b.put("service", "com.huawei.hms.scankit");
            this.f17845b.put(AuthorizeActivityBase.KEY_OPERATOR, b4.b(this.f17844a));
            this.f17845b.put("networkType", b4.a(this.f17844a));
            this.f17845b.put(MLApplicationSetting.BundleKeyConstants.AppInfo.COUNTRY_CODE, b4.a(this.f17844a, false));
            this.f17845b.put("deviceType", b4.c());
            this.f17845b.put("emuiVersion", b4.d());
            this.f17845b.put("androidVersion", b4.a());
            this.f17845b.put("deviceCategory", b4.b());
        } catch (RuntimeException unused3) {
            o4.b("HaLog", "initValue RuntimeException");
        } catch (Exception unused4) {
            o4.b("HaLog", "initValue Exception");
        }
    }

    private String c() {
        try {
            return this.f17844a.getPackageManager().getPackageInfo(this.f17844a.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException | Exception unused) {
            return "unknown";
        }
    }

    private String d() {
        try {
            return this.f17844a.getPackageManager().getPackageInfo("com.huawei.hwid", 0).versionName;
        } catch (PackageManager.NameNotFoundException | Exception unused) {
            return "unknown";
        }
    }

    private boolean e() {
        try {
            String property = SystemPropUtils.getProperty(TmpConstant.PROPERTY_IDENTIFIER_GET, "ro.hw.country", "android.os.SystemProperties", "UNKNOWN");
            TelephonyManager telephonyManager = (TelephonyManager) this.f17844a.getApplicationContext().getSystemService("phone");
            String networkCountryIso = telephonyManager.getNetworkCountryIso();
            String simCountryIso = telephonyManager.getSimCountryIso();
            if ("CN".equalsIgnoreCase(property) && "CN".equalsIgnoreCase(networkCountryIso)) {
                return "CN".equalsIgnoreCase(simCountryIso);
            }
            return false;
        } catch (RuntimeException | Exception unused) {
            return false;
        }
    }

    private boolean f() {
        return true;
    }

    boolean a() {
        if (w7.c(this.f17844a)) {
            Log.i("HaLog", "allowLog: forbidLog ");
            return false;
        }
        Log.i("HaLog", "allowLog: allowLog ");
        try {
            if (!f() && !e()) {
                if (Settings.Secure.getInt(this.f17844a.getContentResolver(), "hw_app_analytics_state", 0) != 1) {
                    return false;
                }
            }
            return true;
        } catch (RuntimeException | Exception unused) {
            return false;
        }
    }

    void g() {
        String str;
        o4.d("HaLog", "update HiAnalyticsLogUtils.apk_mode " + b4.f17005a + " HiAnalyticsLogUtils.apkVersion " + b4.f17006b);
        if (!b4.f17005a || (str = b4.f17006b) == null) {
            return;
        }
        this.f17845b.put("apkVersion", str);
    }

    static String a(int i2) {
        return f17842f.get(i2, f17840d);
    }

    private String a(Bundle bundle) {
        String[] strArr = {"huawei_module_scankit_sdk_version", "com.huawei.hms.client.service.name:scan", "com.huawei.hms.client.service.name:scanplus", "com.huawei.hms.client.service.name:scankit"};
        if (bundle != null) {
            for (int i2 = 0; i2 < 4; i2++) {
                String str = strArr[i2];
                if (bundle.getString(str) != null) {
                    return bundle.getString(str);
                }
            }
            return "scankit:1.0.2.300";
        }
        return "scankit:1.0.2.300";
    }

    private String b() {
        if (Build.VERSION.SDK_INT == 24) {
            return "full-noso";
        }
        return MessengerShareContentUtility.WEBVIEW_RATIO_FULL;
    }

    static String b(int i2) {
        return f17843g.get(i2, f17841e);
    }
}
