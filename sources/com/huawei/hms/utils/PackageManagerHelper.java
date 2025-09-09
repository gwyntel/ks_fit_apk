package com.huawei.hms.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.AndroidException;
import com.huawei.hms.feature.dynamic.f.e;
import com.huawei.hms.support.log.HMSLog;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/* loaded from: classes4.dex */
public class PackageManagerHelper {

    /* renamed from: a, reason: collision with root package name */
    private final PackageManager f18220a;

    public enum PackageStates {
        ENABLED,
        DISABLED,
        NOT_INSTALLED,
        SPOOF
    }

    public PackageManagerHelper(Context context) {
        this.f18220a = context.getPackageManager();
    }

    private byte[] a(String str) throws PackageManager.NameNotFoundException {
        Signature[] signatureArr;
        try {
            PackageInfo packageInfo = this.f18220a.getPackageInfo(str, 64);
            if (packageInfo != null && (signatureArr = packageInfo.signatures) != null && signatureArr.length > 0) {
                return signatureArr[0].toByteArray();
            }
        } catch (AndroidException e2) {
            HMSLog.e("PackageManagerHelper", "Failed to get application signature certificate fingerprint." + e2.getMessage());
        } catch (RuntimeException e3) {
            HMSLog.e("PackageManagerHelper", "Failed to get application signature certificate fingerprint.", e3);
        }
        HMSLog.e("PackageManagerHelper", "Failed to get application signature certificate fingerprint.");
        return new byte[0];
    }

    public String getApplicationName(String str) throws PackageManager.NameNotFoundException {
        try {
            return this.f18220a.getApplicationLabel(this.f18220a.getApplicationInfo(str, 128)).toString();
        } catch (AndroidException unused) {
            HMSLog.e("PackageManagerHelper", "Failed to get application name for " + str);
            return null;
        }
    }

    public long getPackageFirstInstallTime(String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = this.f18220a.getPackageInfo(str, 128);
            if (packageInfo != null) {
                return packageInfo.firstInstallTime;
            }
            return 0L;
        } catch (AndroidException | RuntimeException unused) {
            return 0L;
        }
    }

    public String getPackageSignature(String str) throws PackageManager.NameNotFoundException {
        byte[] bArrA = a(str);
        if (bArrA == null || bArrA.length == 0) {
            return null;
        }
        return HEX.encodeHexString(SHA256.digest(bArrA), true);
    }

    public PackageStates getPackageStates(String str) {
        if (TextUtils.isEmpty(str)) {
            return PackageStates.NOT_INSTALLED;
        }
        try {
            return this.f18220a.getApplicationInfo(str, 128).enabled ? PackageStates.ENABLED : PackageStates.DISABLED;
        } catch (AndroidException unused) {
            return PackageStates.NOT_INSTALLED;
        }
    }

    public int getPackageVersionCode(String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = this.f18220a.getPackageInfo(str, 16);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
            return 0;
        } catch (AndroidException e2) {
            HMSLog.e("PackageManagerHelper", "get PackageVersionCode failed " + e2);
            return 0;
        } catch (RuntimeException e3) {
            HMSLog.e("PackageManagerHelper", "get PackageVersionCode failed", e3);
            return 0;
        }
    }

    public String getPackageVersionName(String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = this.f18220a.getPackageInfo(str, 16);
            if (packageInfo != null) {
                String str2 = packageInfo.versionName;
                if (str2 != null) {
                    return str2;
                }
            }
            return "";
        } catch (AndroidException unused) {
            return "";
        } catch (RuntimeException e2) {
            HMSLog.e("PackageManagerHelper", "get getPackageVersionName failed", e2);
            return "";
        }
    }

    public boolean hasProvider(String str, String str2) throws PackageManager.NameNotFoundException {
        ProviderInfo[] providerInfoArr;
        try {
            PackageInfo packageInfo = this.f18220a.getPackageInfo(str, 8);
            if (packageInfo != null && (providerInfoArr = packageInfo.providers) != null) {
                for (ProviderInfo providerInfo : providerInfoArr) {
                    if (str2.equals(providerInfo.authority)) {
                        return true;
                    }
                }
            }
        } catch (AndroidException | RuntimeException unused) {
        }
        return false;
    }

    public boolean isPackageFreshInstall(String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = this.f18220a.getPackageInfo(str, 128);
            if (packageInfo != null) {
                return packageInfo.firstInstallTime == packageInfo.lastUpdateTime;
            }
            return false;
        } catch (AndroidException | RuntimeException unused) {
            return false;
        }
    }

    public boolean verifyPackageArchive(String str, String str2, String str3) throws IOException {
        PackageInfo packageArchiveInfo;
        InputStream inputStream = null;
        try {
            packageArchiveInfo = this.f18220a.getPackageArchiveInfo(str, 64);
        } catch (Exception e2) {
            HMSLog.e("PackageManagerHelper", "getPackageArchiveInfo Exception. " + e2.getMessage());
            packageArchiveInfo = null;
        }
        if (packageArchiveInfo != null && packageArchiveInfo.signatures.length > 0) {
            try {
                if (!str2.equals(packageArchiveInfo.packageName)) {
                    return false;
                }
                try {
                    inputStream = IOUtils.toInputStream(packageArchiveInfo.signatures[0].toByteArray());
                    return str3.equalsIgnoreCase(HEX.encodeHexString(SHA256.digest(CertificateFactory.getInstance(e.f16169b).generateCertificate(inputStream).getEncoded()), true));
                } catch (IOException | CertificateException e3) {
                    HMSLog.e("PackageManagerHelper", "Failed to get application signature certificate fingerprint." + e3.getMessage());
                }
            } finally {
                IOUtils.closeQuietly((InputStream) null);
            }
        }
        return false;
    }
}
