package com.huawei.hms.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AndroidException;
import android.util.Pair;
import com.huawei.hms.common.PackageConstants;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.PackageManagerHelper;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class HMSPackageManager {

    /* renamed from: n, reason: collision with root package name */
    private static HMSPackageManager f18194n;

    /* renamed from: o, reason: collision with root package name */
    private static final Object f18195o = new Object();

    /* renamed from: p, reason: collision with root package name */
    private static final Object f18196p = new Object();

    /* renamed from: q, reason: collision with root package name */
    private static final Object f18197q = new Object();

    /* renamed from: a, reason: collision with root package name */
    private final Context f18198a;

    /* renamed from: b, reason: collision with root package name */
    private final PackageManagerHelper f18199b;

    /* renamed from: c, reason: collision with root package name */
    private String f18200c;

    /* renamed from: d, reason: collision with root package name */
    private String f18201d;

    /* renamed from: e, reason: collision with root package name */
    private int f18202e;

    /* renamed from: f, reason: collision with root package name */
    private String f18203f;

    /* renamed from: g, reason: collision with root package name */
    private String f18204g;

    /* renamed from: h, reason: collision with root package name */
    private String f18205h;

    /* renamed from: i, reason: collision with root package name */
    private String f18206i;

    /* renamed from: j, reason: collision with root package name */
    private int f18207j;

    /* renamed from: k, reason: collision with root package name */
    private int f18208k;

    /* renamed from: l, reason: collision with root package name */
    private long f18209l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f18210m;

    public static class PackagePriorityInfo implements Comparable<PackagePriorityInfo> {

        /* renamed from: a, reason: collision with root package name */
        private String f18211a;

        /* renamed from: b, reason: collision with root package name */
        private String f18212b;

        /* renamed from: c, reason: collision with root package name */
        private String f18213c;

        /* renamed from: d, reason: collision with root package name */
        private String f18214d;

        /* renamed from: e, reason: collision with root package name */
        private String f18215e;

        /* renamed from: f, reason: collision with root package name */
        private Long f18216f;

        public PackagePriorityInfo(String str, String str2, String str3, String str4, String str5, long j2) {
            this.f18211a = str;
            this.f18212b = str2;
            this.f18213c = str3;
            this.f18214d = str4;
            this.f18215e = str5;
            this.f18216f = Long.valueOf(j2);
        }

        @Override // java.lang.Comparable
        public int compareTo(PackagePriorityInfo packagePriorityInfo) {
            return TextUtils.equals(this.f18215e, packagePriorityInfo.f18215e) ? this.f18216f.compareTo(packagePriorityInfo.f18216f) : this.f18215e.compareTo(packagePriorityInfo.f18215e);
        }
    }

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            HMSLog.i("HMSPackageManager", "enter asyncOnceCheckMDMState");
            try {
                List<ResolveInfo> listQueryIntentServices = HMSPackageManager.this.f18198a.getPackageManager().queryIntentServices(new Intent("com.huawei.hms.core.aidlservice"), 128);
                if (listQueryIntentServices == null || listQueryIntentServices.size() == 0) {
                    return;
                }
                Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
                while (it.hasNext()) {
                    if ("com.huawei.hwid".equals(it.next().serviceInfo.applicationInfo.packageName)) {
                        HMSPackageManager.this.c();
                    }
                }
                HMSLog.i("HMSPackageManager", "quit asyncOnceCheckMDMState");
            } catch (Exception e2) {
                HMSLog.e("HMSPackageManager", "asyncOnceCheckMDMState query hms action failed. " + e2.getMessage());
            }
        }
    }

    private HMSPackageManager(Context context) {
        this.f18198a = context;
        this.f18199b = new PackageManagerHelper(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int c() {
        synchronized (f18197q) {
            try {
                HMSLog.i("HMSPackageManager", "enter checkHmsIsSpoof");
                long packageFirstInstallTime = this.f18199b.getPackageFirstInstallTime("com.huawei.hwid");
                if (this.f18208k != 3 && this.f18209l == packageFirstInstallTime) {
                    HMSLog.i("HMSPackageManager", "quit checkHmsIsSpoof cached state: " + a(this.f18208k));
                    return this.f18208k;
                }
                this.f18208k = b() ? 2 : 1;
                this.f18209l = this.f18199b.getPackageFirstInstallTime("com.huawei.hwid");
                HMSLog.i("HMSPackageManager", "quit checkHmsIsSpoof state: " + a(this.f18208k));
                return this.f18208k;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void d() {
        synchronized (f18196p) {
            this.f18203f = null;
            this.f18204g = null;
            this.f18205h = null;
            this.f18206i = null;
            this.f18207j = 0;
        }
    }

    private void e() {
        synchronized (f18196p) {
            this.f18200c = null;
            this.f18201d = null;
            this.f18202e = 0;
        }
    }

    private Pair<String, String> f() throws PackageManager.NameNotFoundException {
        try {
            List<ResolveInfo> listQueryIntentServices = this.f18198a.getPackageManager().queryIntentServices(new Intent("com.huawei.hms.core.aidlservice"), 128);
            if (listQueryIntentServices == null || listQueryIntentServices.size() == 0) {
                HMSLog.e("HMSPackageManager", "query hms action, resolveInfoList is null or empty.");
                return null;
            }
            for (ResolveInfo resolveInfo : listQueryIntentServices) {
                String str = resolveInfo.serviceInfo.applicationInfo.packageName;
                String packageSignature = this.f18199b.getPackageSignature(str);
                if ("com.huawei.hwid".equals(str) && this.f18199b.getPackageVersionCode(str) < 30000000) {
                    return new Pair<>(str, packageSignature);
                }
                Bundle bundle = resolveInfo.serviceInfo.metaData;
                if (bundle == null) {
                    HMSLog.e("HMSPackageManager", "skip package " + str + " for metadata is null");
                } else if (!bundle.containsKey("hms_app_signer")) {
                    HMSLog.e("HMSPackageManager", "skip package " + str + " for no signer");
                } else if (bundle.containsKey("hms_app_cert_chain")) {
                    if (a(str + "&" + packageSignature, bundle.getString("hms_app_signer"), bundle.getString("hms_app_cert_chain"))) {
                        return new Pair<>(str, packageSignature);
                    }
                    HMSLog.e("HMSPackageManager", "checkSigner failed");
                } else {
                    HMSLog.e("HMSPackageManager", "skip package " + str + " for no cert chain");
                }
            }
            return null;
        } catch (Exception e2) {
            HMSLog.e("HMSPackageManager", "getHmsPackageName query hms action failed. " + e2.getMessage());
            return null;
        }
    }

    private Pair<String, String> g() throws PackageManager.NameNotFoundException {
        Pair<String, String> pairF = f();
        if (pairF != null) {
            HMSLog.i("HMSPackageManager", "aidlService pkgName: " + ((String) pairF.first));
            this.f18205h = "com.huawei.hms.core.aidlservice";
            this.f18206i = null;
            return pairF;
        }
        ArrayList<PackagePriorityInfo> arrayListH = h();
        if (arrayListH == null) {
            HMSLog.e("HMSPackageManager", "PackagePriorityInfo list is null");
            return null;
        }
        Iterator<PackagePriorityInfo> it = arrayListH.iterator();
        while (it.hasNext()) {
            PackagePriorityInfo next = it.next();
            String str = next.f18211a;
            String str2 = next.f18212b;
            String str3 = next.f18213c;
            String str4 = next.f18214d;
            String packageSignature = this.f18199b.getPackageSignature(str);
            if (a(str + "&" + packageSignature + "&" + str2, str3, str4)) {
                HMSLog.i("HMSPackageManager", "result: " + str + ", " + str2 + ", " + next.f18216f);
                this.f18205h = PackageConstants.GENERAL_SERVICES_ACTION;
                b(str2);
                return new Pair<>(str, packageSignature);
            }
        }
        return null;
    }

    public static HMSPackageManager getInstance(Context context) {
        synchronized (f18195o) {
            try {
                if (f18194n == null) {
                    if (context.getApplicationContext() != null) {
                        f18194n = new HMSPackageManager(context.getApplicationContext());
                    } else {
                        f18194n = new HMSPackageManager(context);
                    }
                    f18194n.j();
                    f18194n.a();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return f18194n;
    }

    private ArrayList<PackagePriorityInfo> h() throws PackageManager.NameNotFoundException {
        try {
            List<ResolveInfo> listQueryIntentServices = this.f18198a.getPackageManager().queryIntentServices(new Intent(PackageConstants.GENERAL_SERVICES_ACTION), 128);
            if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
                HMSLog.e("HMSPackageManager", "query aglite action, resolveInfoList is null or empty");
                return null;
            }
            ArrayList<PackagePriorityInfo> arrayList = new ArrayList<>();
            for (ResolveInfo resolveInfo : listQueryIntentServices) {
                String str = resolveInfo.serviceInfo.applicationInfo.packageName;
                long packageFirstInstallTime = this.f18199b.getPackageFirstInstallTime(str);
                Bundle bundle = resolveInfo.serviceInfo.metaData;
                if (bundle == null) {
                    HMSLog.e("HMSPackageManager", "package " + str + " get metaData is null");
                } else {
                    String strA = a(bundle, "hms_app_checker_config");
                    String strA2 = a(strA);
                    if (TextUtils.isEmpty(strA2)) {
                        HMSLog.i("HMSPackageManager", "get priority fail. hmsCheckerCfg: " + strA);
                    } else {
                        String strA3 = a(bundle, "hms_app_signer_v2");
                        if (TextUtils.isEmpty(strA3)) {
                            HMSLog.i("HMSPackageManager", "get signerV2 fail.");
                        } else {
                            String strA4 = a(bundle, "hms_app_cert_chain");
                            if (TextUtils.isEmpty(strA4)) {
                                HMSLog.i("HMSPackageManager", "get certChain fail.");
                            } else {
                                HMSLog.i("HMSPackageManager", "add: " + str + ", " + strA + ", " + packageFirstInstallTime);
                                arrayList.add(new PackagePriorityInfo(str, strA, strA3, strA4, strA2, packageFirstInstallTime));
                            }
                        }
                    }
                }
            }
            Collections.sort(arrayList);
            return arrayList;
        } catch (Exception e2) {
            HMSLog.e("HMSPackageManager", "query aglite action failed. " + e2.getMessage());
            return null;
        }
    }

    private void i() {
        synchronized (f18196p) {
            try {
                Pair<String, String> pairF = f();
                if (pairF == null) {
                    HMSLog.e("HMSPackageManager", "<initHmsPackageInfo> Failed to find HMS apk");
                    e();
                    return;
                }
                this.f18200c = (String) pairF.first;
                this.f18201d = (String) pairF.second;
                this.f18202e = this.f18199b.getPackageVersionCode(getHMSPackageName());
                HMSLog.i("HMSPackageManager", "<initHmsPackageInfo> Succeed to find HMS apk: " + this.f18200c + " version: " + this.f18202e);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void j() {
        synchronized (f18196p) {
            try {
                Pair<String, String> pairG = g();
                if (pairG == null) {
                    HMSLog.e("HMSPackageManager", "<initHmsPackageInfoForMultiService> Failed to find HMS apk");
                    d();
                    return;
                }
                this.f18203f = (String) pairG.first;
                this.f18204g = (String) pairG.second;
                this.f18207j = this.f18199b.getPackageVersionCode(getHMSPackageNameForMultiService());
                HMSLog.i("HMSPackageManager", "<initHmsPackageInfoForMultiService> Succeed to find HMS apk: " + this.f18203f + " version: " + this.f18207j);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private boolean k() {
        Bundle bundle;
        PackageManager packageManager = this.f18198a.getPackageManager();
        if (packageManager == null) {
            HMSLog.e("HMSPackageManager", "In isMinApkVersionEffective, Failed to get 'PackageManager' instance.");
            return true;
        }
        try {
        } catch (AndroidException unused) {
            HMSLog.e("HMSPackageManager", "In isMinApkVersionEffective, Failed to read meta data for HMSCore API level.");
        } catch (RuntimeException e2) {
            HMSLog.e("HMSPackageManager", "In isMinApkVersionEffective, Failed to read meta data for HMSCore API level.", e2);
        }
        if (TextUtils.isEmpty(this.f18205h) || (!this.f18205h.equals(PackageConstants.GENERAL_SERVICES_ACTION) && !this.f18205h.equals(PackageConstants.INTERNAL_SERVICES_ACTION))) {
            ApplicationInfo applicationInfo = packageManager.getPackageInfo(getHMSPackageName(), 128).applicationInfo;
            if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && bundle.containsKey("com.huawei.hms.kit.api_level:hmscore") && (getHmsVersionCode() >= 50000000 || getHmsVersionCode() <= 19999999)) {
                HMSLog.i("HMSPackageManager", "MinApkVersion is disabled.");
                return false;
            }
            return true;
        }
        HMSLog.i("HMSPackageManager", "action = " + this.f18205h + " exist");
        return false;
    }

    public String getHMSFingerprint() {
        String str = this.f18201d;
        return str == null ? "B92825C2BD5D6D6D1E7F39EECD17843B7D9016F611136B75441BC6F4D3F00F05" : str;
    }

    public String getHMSPackageName() {
        HMSLog.i("HMSPackageManager", "Enter getHMSPackageName");
        refresh();
        String str = this.f18200c;
        if (str != null) {
            if (PackageManagerHelper.PackageStates.NOT_INSTALLED.equals(this.f18199b.getPackageStates(str))) {
                HMSLog.i("HMSPackageManager", "The package name is not installed and needs to be refreshed again");
                i();
            }
            String str2 = this.f18200c;
            if (str2 != null) {
                return str2;
            }
        }
        if (!PackageManagerHelper.PackageStates.NOT_INSTALLED.equals(this.f18199b.getPackageStates("com.huawei.hwid"))) {
            "B92825C2BD5D6D6D1E7F39EECD17843B7D9016F611136B75441BC6F4D3F00F05".equalsIgnoreCase(this.f18199b.getPackageSignature("com.huawei.hwid"));
        }
        return "com.huawei.hwid";
    }

    public String getHMSPackageNameForMultiService() {
        HMSLog.i("HMSPackageManager", "Enter getHMSPackageNameForMultiService");
        refreshForMultiService();
        String str = this.f18203f;
        if (str == null) {
            return "com.huawei.hwid";
        }
        if (PackageManagerHelper.PackageStates.NOT_INSTALLED.equals(this.f18199b.getPackageStates(str))) {
            HMSLog.i("HMSPackageManager", "The package name is not installed and needs to be refreshed again");
            j();
        }
        String str2 = this.f18203f;
        return str2 != null ? str2 : "com.huawei.hwid";
    }

    public PackageManagerHelper.PackageStates getHMSPackageStates() {
        synchronized (f18195o) {
            try {
                refresh();
                PackageManagerHelper.PackageStates packageStates = this.f18199b.getPackageStates(this.f18200c);
                PackageManagerHelper.PackageStates packageStates2 = PackageManagerHelper.PackageStates.NOT_INSTALLED;
                if (packageStates == packageStates2) {
                    e();
                    return packageStates2;
                }
                if ("com.huawei.hwid".equals(this.f18200c) && c() == 1) {
                    return PackageManagerHelper.PackageStates.SPOOF;
                }
                return (packageStates != PackageManagerHelper.PackageStates.ENABLED || this.f18201d.equals(this.f18199b.getPackageSignature(this.f18200c))) ? packageStates : packageStates2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public PackageManagerHelper.PackageStates getHMSPackageStatesForMultiService() {
        synchronized (f18195o) {
            try {
                refreshForMultiService();
                PackageManagerHelper.PackageStates packageStates = this.f18199b.getPackageStates(this.f18203f);
                PackageManagerHelper.PackageStates packageStates2 = PackageManagerHelper.PackageStates.NOT_INSTALLED;
                if (packageStates == packageStates2) {
                    d();
                    return packageStates2;
                }
                if ("com.huawei.hwid".equals(this.f18203f) && c() == 1) {
                    return PackageManagerHelper.PackageStates.SPOOF;
                }
                return (packageStates != PackageManagerHelper.PackageStates.ENABLED || this.f18204g.equals(this.f18199b.getPackageSignature(this.f18203f))) ? packageStates : packageStates2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int getHmsMultiServiceVersion() {
        return this.f18199b.getPackageVersionCode(getHMSPackageNameForMultiService());
    }

    public int getHmsVersionCode() {
        return this.f18199b.getPackageVersionCode(getHMSPackageName());
    }

    public String getInnerServiceAction() {
        return PackageConstants.INTERNAL_SERVICES_ACTION;
    }

    public String getServiceAction() {
        return !TextUtils.isEmpty(this.f18205h) ? this.f18205h : "com.huawei.hms.core.aidlservice";
    }

    public boolean hmsVerHigherThan(int i2) throws PackageManager.NameNotFoundException {
        if (this.f18202e >= i2 || !k()) {
            return true;
        }
        int packageVersionCode = this.f18199b.getPackageVersionCode(getHMSPackageName());
        this.f18202e = packageVersionCode;
        return packageVersionCode >= i2;
    }

    public boolean isApkNeedUpdate(int i2) {
        int hmsVersionCode = getHmsVersionCode();
        HMSLog.i("HMSPackageManager", "current versionCode:" + hmsVersionCode + ", target version requirements: " + i2);
        return hmsVersionCode < i2;
    }

    public boolean isApkUpdateNecessary(int i2) {
        int hmsVersionCode = getHmsVersionCode();
        HMSLog.i("HMSPackageManager", "current versionCode:" + hmsVersionCode + ", minimum version requirements: " + i2);
        return k() && hmsVersionCode < i2;
    }

    public boolean isUseOldCertificate() {
        return this.f18210m;
    }

    public void refresh() {
        if (TextUtils.isEmpty(this.f18200c) || TextUtils.isEmpty(this.f18201d)) {
            i();
        }
    }

    public void refreshForMultiService() {
        if (TextUtils.isEmpty(this.f18203f) || TextUtils.isEmpty(this.f18204g)) {
            j();
        }
    }

    public void resetMultiServiceState() {
        d();
    }

    public void setUseOldCertificate(boolean z2) {
        this.f18210m = z2;
    }

    private String a(Bundle bundle, String str) {
        if (bundle.containsKey(str)) {
            return bundle.getString(str);
        }
        HMSLog.e("HMSPackageManager", "no " + str + " in metaData");
        return null;
    }

    private void b(String str) {
        String strA = a(str);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        this.f18206i = strA.substring(9);
    }

    private String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int iIndexOf = str.indexOf("priority=");
        if (iIndexOf == -1) {
            HMSLog.e("HMSPackageManager", "get indexOfIdentifier -1");
            return null;
        }
        int iIndexOf2 = str.indexOf(",", iIndexOf);
        if (iIndexOf2 == -1) {
            iIndexOf2 = str.length();
        }
        return str.substring(iIndexOf, iIndexOf2);
    }

    private boolean b() {
        String hmsPath = ReadApkFileUtil.getHmsPath(this.f18198a);
        if (hmsPath == null) {
            HMSLog.i("HMSPackageManager", "hmsPath is null!");
            return false;
        }
        if (!ReadApkFileUtil.isCertFound(hmsPath)) {
            HMSLog.i("HMSPackageManager", "NO huawer.cer in HMS!");
            return false;
        }
        if (!ReadApkFileUtil.checkSignature()) {
            HMSLog.i("HMSPackageManager", "checkSignature fail!");
            return false;
        }
        if (ReadApkFileUtil.verifyApkHash(hmsPath)) {
            return true;
        }
        HMSLog.i("HMSPackageManager", "verifyApkHash fail!");
        return false;
    }

    private boolean a(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            List<X509Certificate> listB = com.huawei.hms.device.a.b(str3);
            if (listB.size() == 0) {
                HMSLog.e("HMSPackageManager", "certChain is empty");
                return false;
            }
            if (!com.huawei.hms.device.a.a(com.huawei.hms.device.a.a(this.f18198a), listB)) {
                HMSLog.e("HMSPackageManager", "failed to verify cert chain");
                return false;
            }
            X509Certificate x509Certificate = listB.get(listB.size() - 1);
            if (!com.huawei.hms.device.a.a(x509Certificate, "Huawei CBG HMS")) {
                HMSLog.e("HMSPackageManager", "CN is invalid");
                return false;
            }
            if (!com.huawei.hms.device.a.b(x509Certificate, "Huawei CBG Cloud Security Signer")) {
                HMSLog.e("HMSPackageManager", "OU is invalid");
                return false;
            }
            if (com.huawei.hms.device.a.a(x509Certificate, str, str2)) {
                return true;
            }
            HMSLog.e("HMSPackageManager", "signature is invalid: " + str);
            return false;
        }
        HMSLog.e("HMSPackageManager", "args is invalid");
        return false;
    }

    private void a() {
        new Thread(new a(), "Thread-asyncOnceCheckMDMState").start();
    }

    private static String a(int i2) {
        if (i2 == 1) {
            return "SPOOFED";
        }
        if (i2 == 2) {
            return "SUCCESS";
        }
        if (i2 == 3) {
            return "UNCHECKED";
        }
        HMSLog.e("HMSPackageManager", "invalid checkMDM state: " + i2);
        return "";
    }
}
