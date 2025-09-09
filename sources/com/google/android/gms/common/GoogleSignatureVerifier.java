package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.RestrictedInheritance;
import java.util.Set;
import javax.annotation.Nullable;

@CheckReturnValue
@ShowFirstParty
@KeepForSdk
@RestrictedInheritance(allowedOnPath = ".*java.*/com/google/android/gms/common/testing/.*", explanation = "Sub classing of GMS Core's APIs are restricted to testing fakes.", link = "go/gmscore-restrictedinheritance")
/* loaded from: classes3.dex */
public class GoogleSignatureVerifier {

    @Nullable
    private static GoogleSignatureVerifier zza;

    @Nullable
    private static volatile Set zzb;
    private final Context zzc;
    private volatile String zzd;

    public GoogleSignatureVerifier(@NonNull Context context) {
        this.zzc = context.getApplicationContext();
    }

    static final zzj a(PackageInfo packageInfo, zzj... zzjVarArr) {
        Signature[] signatureArr = packageInfo.signatures;
        if (signatureArr != null) {
            if (signatureArr.length != 1) {
                Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
                return null;
            }
            zzk zzkVar = new zzk(packageInfo.signatures[0].toByteArray());
            for (int i2 = 0; i2 < zzjVarArr.length; i2++) {
                if (zzjVarArr[i2].equals(zzkVar)) {
                    return zzjVarArr[i2];
                }
            }
        }
        return null;
    }

    @NonNull
    @KeepForSdk
    public static GoogleSignatureVerifier getInstance(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        synchronized (GoogleSignatureVerifier.class) {
            try {
                if (zza == null) {
                    zzn.e(context);
                    zza = new GoogleSignatureVerifier(context);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return zza;
    }

    public static final boolean zzb(@NonNull PackageInfo packageInfo, boolean z2) {
        PackageInfo packageInfo2;
        if (!z2) {
            packageInfo2 = packageInfo;
        } else if (packageInfo != null) {
            if ("com.android.vending".equals(packageInfo.packageName) || "com.google.android.gms".equals(packageInfo.packageName)) {
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                z2 = (applicationInfo == null || (applicationInfo.flags & 129) == 0) ? false : true;
            }
            packageInfo2 = packageInfo;
        } else {
            packageInfo2 = null;
        }
        if (packageInfo != null && packageInfo2.signatures != null) {
            if ((z2 ? a(packageInfo2, zzm.f12902a) : a(packageInfo2, zzm.f12902a[0])) != null) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint({"PackageManagerGetSignatures"})
    private final zzx zzc(@Nullable String str, boolean z2, boolean z3) throws PackageManager.NameNotFoundException {
        zzx zzxVarC;
        ApplicationInfo applicationInfo;
        if (str == null) {
            return zzx.c("null pkg");
        }
        if (str.equals(this.zzd)) {
            return zzx.b();
        }
        if (zzn.g()) {
            zzxVarC = zzn.b(str, GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzc), false, false);
        } else {
            try {
                PackageInfo packageInfo = this.zzc.getPackageManager().getPackageInfo(str, 64);
                boolean zHonorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzc);
                if (packageInfo == null) {
                    zzxVarC = zzx.c("null pkg");
                } else {
                    Signature[] signatureArr = packageInfo.signatures;
                    if (signatureArr == null || signatureArr.length != 1) {
                        zzxVarC = zzx.c("single cert required");
                    } else {
                        zzk zzkVar = new zzk(packageInfo.signatures[0].toByteArray());
                        String str2 = packageInfo.packageName;
                        zzx zzxVarA = zzn.a(str2, zzkVar, zHonorsDebugCertificates, false);
                        zzxVarC = (!zzxVarA.f12907a || (applicationInfo = packageInfo.applicationInfo) == null || (applicationInfo.flags & 2) == 0 || !zzn.a(str2, zzkVar, false, true).f12907a) ? zzxVarA : zzx.c("debuggable release cert app rejected");
                    }
                }
            } catch (PackageManager.NameNotFoundException e2) {
                return zzx.d("no pkg ".concat(str), e2);
            }
        }
        if (zzxVarC.f12907a) {
            this.zzd = str;
        }
        return zzxVarC;
    }

    @KeepForSdk
    public boolean isGooglePublicSignedPackage(@NonNull PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zzb(packageInfo, false)) {
            return true;
        }
        if (zzb(packageInfo, true)) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzc)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isPackageGoogleSigned(@Nullable String str) throws PackageManager.NameNotFoundException {
        zzx zzxVarZzc = zzc(str, false, false);
        zzxVarZzc.e();
        return zzxVarZzc.f12907a;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isUidGoogleSigned(int i2) throws PackageManager.NameNotFoundException {
        zzx zzxVarC;
        int length;
        String[] packagesForUid = this.zzc.getPackageManager().getPackagesForUid(i2);
        if (packagesForUid != null && (length = packagesForUid.length) != 0) {
            zzxVarC = null;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    Preconditions.checkNotNull(zzxVarC);
                    break;
                }
                zzxVarC = zzc(packagesForUid[i3], false, false);
                if (zzxVarC.f12907a) {
                    break;
                }
                i3++;
            }
        } else {
            zzxVarC = zzx.c("no pkgs");
        }
        zzxVarC.e();
        return zzxVarC.f12907a;
    }
}
