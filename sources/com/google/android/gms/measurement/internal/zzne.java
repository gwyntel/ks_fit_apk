package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ext.SdkExtensions;
import android.text.TextUtils;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.core.app.NotificationCompat;
import androidx.privacysandbox.ads.adservices.java.measurement.MeasurementManagerFutures;
import androidx.webkit.ProxyConfig;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzrp;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.feature.dynamic.f.e;
import com.umeng.analytics.pro.bg;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import javax.security.auth.x500.X500Principal;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzne extends zzid {
    private static final String[] zza = {"firebase_", "google_", "ga_"};
    private static final String[] zzb = {"_err"};
    private SecureRandom zzc;
    private final AtomicLong zzd;
    private int zze;
    private MeasurementManagerFutures zzf;
    private Boolean zzg;
    private Integer zzh;

    zzne(zzhc zzhcVar) {
        super(zzhcVar);
        this.zzh = null;
        this.zzd = new AtomicLong(0L);
    }

    static byte[] A(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelable.writeToParcel(parcelObtain, 0);
            return parcelObtain.marshall();
        } finally {
            parcelObtain.recycle();
        }
    }

    static Bundle[] F(Object obj) {
        if (obj instanceof Bundle) {
            return new Bundle[]{(Bundle) obj};
        }
        if (obj instanceof Parcelable[]) {
            Parcelable[] parcelableArr = (Parcelable[]) obj;
            return (Bundle[]) Arrays.copyOf(parcelableArr, parcelableArr.length, Bundle[].class);
        }
        if (!(obj instanceof ArrayList)) {
            return null;
        }
        ArrayList arrayList = (ArrayList) obj;
        return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
    }

    static int G() {
        if (Build.VERSION.SDK_INT < 30 || SdkExtensions.getExtensionVersion(30) <= 3) {
            return 0;
        }
        return SdkExtensions.getExtensionVersion(1000000);
    }

    static boolean K(String str) {
        String strZza = zzbi.zzay.zza(null);
        return strZza.equals(ProxyConfig.MATCH_ALL_SCHEMES) || Arrays.asList(strZza.split(",")).contains(str);
    }

    static boolean N(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(OpenAccountUIConstants.UNDER_LINE);
    }

    static boolean O(String str) {
        Preconditions.checkNotEmpty(str);
        return str.charAt(0) != '_' || str.equals("_ep");
    }

    static MessageDigest R() throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        for (int i2 = 0; i2 < 2; i2++) {
            try {
                messageDigest = MessageDigest.getInstance(Utils.MD5);
            } catch (NoSuchAlgorithmException unused) {
            }
            if (messageDigest != null) {
                return messageDigest;
            }
        }
        return null;
    }

    static long f(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        int i2 = 0;
        Preconditions.checkState(bArr.length > 0);
        long j2 = 0;
        for (int length = bArr.length - 1; length >= 0 && length >= bArr.length - 8; length--) {
            j2 += (bArr[length] & 255) << i2;
            i2 += 8;
        }
        return j2;
    }

    static void m(zznf zznfVar, String str, int i2, String str2, String str3, int i3) {
        Bundle bundle = new Bundle();
        zzb(bundle, i2);
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            bundle.putString(str2, str3);
        }
        if (i2 == 6 || i2 == 7 || i2 == 2) {
            bundle.putLong("_el", i3);
        }
        zznfVar.zza(str, "_err", bundle);
    }

    static boolean o(Context context) {
        ActivityInfo receiverInfo;
        Preconditions.checkNotNull(context);
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && (receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementReceiver"), 0)) != null) {
                if (receiverInfo.enabled) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    static boolean p(Context context, boolean z2) {
        Preconditions.checkNotNull(context);
        return Build.VERSION.SDK_INT >= 24 ? zzc(context, "com.google.android.gms.measurement.AppMeasurementJobService") : zzc(context, "com.google.android.gms.measurement.AppMeasurementService");
    }

    static boolean q(Intent intent) {
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return "android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra) || "https://www.google.com".equals(stringExtra) || "android-app://com.google.appcrawler".equals(stringExtra);
    }

    static boolean r(Bundle bundle, int i2) {
        int i3 = 0;
        if (bundle.size() <= i2) {
            return false;
        }
        for (String str : new TreeSet(bundle.keySet())) {
            i3++;
            if (i3 > i2) {
                bundle.remove(str);
            }
        }
        return true;
    }

    static boolean s(Object obj) {
        return (obj instanceof Parcelable[]) || (obj instanceof ArrayList) || (obj instanceof Bundle);
    }

    static boolean x(String str, String str2, String str3, String str4) {
        boolean zIsEmpty = TextUtils.isEmpty(str);
        boolean zIsEmpty2 = TextUtils.isEmpty(str2);
        if (!zIsEmpty && !zIsEmpty2) {
            Preconditions.checkNotNull(str);
            return !str.equals(str2);
        }
        if (zIsEmpty && zIsEmpty2) {
            return (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) ? !TextUtils.isEmpty(str4) : !str3.equals(str4);
        }
        if (zIsEmpty || !zIsEmpty2) {
            return TextUtils.isEmpty(str3) || !str3.equals(str4);
        }
        if (TextUtils.isEmpty(str4)) {
            return false;
        }
        return TextUtils.isEmpty(str3) || !str3.equals(str4);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x008a  */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int zza(java.lang.String r14, java.lang.String r15, java.lang.String r16, java.lang.Object r17, android.os.Bundle r18, java.util.List<java.lang.String> r19, boolean r20, boolean r21) throws java.lang.IllegalStateException {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzne.zza(java.lang.String, java.lang.String, java.lang.String, java.lang.Object, android.os.Bundle, java.util.List, boolean, boolean):int");
    }

    private static boolean zzc(Context context, String str) {
        ServiceInfo serviceInfo;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && (serviceInfo = packageManager.getServiceInfo(new ComponentName(context, str), 0)) != null) {
                if (serviceInfo.enabled) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    private final int zzk(String str) {
        if (!E("event param", str)) {
            return 3;
        }
        if (y("event param", null, str)) {
            return !u("event param", 40, str) ? 3 : 0;
        }
        return 14;
    }

    private final int zzl(String str) {
        if (!J("event param", str)) {
            return 3;
        }
        if (y("event param", null, str)) {
            return !u("event param", 40, str) ? 3 : 0;
        }
        return 14;
    }

    private static int zzm(String str) {
        if ("_ldl".equals(str)) {
            return 2048;
        }
        if (bg.f21483d.equals(str)) {
            return 256;
        }
        return ("_lgclid".equals(str) || "_gbraid".equals(str)) ? 100 : 36;
    }

    @VisibleForTesting
    private static boolean zzn(String str) {
        Preconditions.checkNotNull(str);
        return str.matches("^(1:\\d+:android:[a-f0-9]+|ca-app-pub-.*)$");
    }

    @TargetApi(30)
    @WorkerThread
    private final boolean zzy() throws ExecutionException, InterruptedException, TimeoutException {
        Integer num;
        if (this.zzg == null) {
            MeasurementManagerFutures measurementManagerFuturesP = P();
            boolean z2 = false;
            if (measurementManagerFuturesP == null) {
                return false;
            }
            Integer num2 = null;
            try {
                num = measurementManagerFuturesP.getMeasurementApiStatusAsync().get(10000L, TimeUnit.MILLISECONDS);
                if (num != null) {
                    try {
                        if (num.intValue() == 1) {
                            z2 = true;
                        }
                    } catch (InterruptedException e2) {
                        e = e2;
                        num2 = num;
                        zzj().zzu().zza("Measurement manager api exception", e);
                        this.zzg = Boolean.FALSE;
                        num = num2;
                        zzj().zzp().zza("Measurement manager api status result", num);
                        return this.zzg.booleanValue();
                    } catch (CancellationException e3) {
                        e = e3;
                        num2 = num;
                        zzj().zzu().zza("Measurement manager api exception", e);
                        this.zzg = Boolean.FALSE;
                        num = num2;
                        zzj().zzp().zza("Measurement manager api status result", num);
                        return this.zzg.booleanValue();
                    } catch (ExecutionException e4) {
                        e = e4;
                        num2 = num;
                        zzj().zzu().zza("Measurement manager api exception", e);
                        this.zzg = Boolean.FALSE;
                        num = num2;
                        zzj().zzp().zza("Measurement manager api status result", num);
                        return this.zzg.booleanValue();
                    } catch (TimeoutException e5) {
                        e = e5;
                        num2 = num;
                        zzj().zzu().zza("Measurement manager api exception", e);
                        this.zzg = Boolean.FALSE;
                        num = num2;
                        zzj().zzp().zza("Measurement manager api status result", num);
                        return this.zzg.booleanValue();
                    }
                }
                this.zzg = Boolean.valueOf(z2);
            } catch (InterruptedException e6) {
                e = e6;
            } catch (CancellationException e7) {
                e = e7;
            } catch (ExecutionException e8) {
                e = e8;
            } catch (TimeoutException e9) {
                e = e9;
            }
            zzj().zzp().zza("Measurement manager api status result", num);
        }
        return this.zzg.booleanValue();
    }

    final int B(String str) {
        if (!E("user property", str)) {
            return 6;
        }
        if (y("user property", zzik.zza, str)) {
            return !u("user property", 24, str) ? 6 : 0;
        }
        return 15;
    }

    final Bundle C(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object objD = D(str, bundle.get(str));
                if (objD == null) {
                    zzj().zzv().zza("Param value can't be null", zzi().d(str));
                } else {
                    k(bundle2, str, objD);
                }
            }
        }
        return bundle2;
    }

    final Object D(String str, Object obj) {
        if ("_ev".equals(str)) {
            return zza(zze().e(this.f13286a.zzh().f()), obj, true, true);
        }
        return zza(N(str) ? zze().e(this.f13286a.zzh().f()) : zze().c(this.f13286a.zzh().f()), obj, false, true);
    }

    final boolean E(String str, String str2) {
        if (str2 == null) {
            zzj().zzh().zza("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.length() == 0) {
            zzj().zzh().zza("Name is required and can't be empty. Type", str);
            return false;
        }
        int iCodePointAt = str2.codePointAt(0);
        if (!Character.isLetter(iCodePointAt) && iCodePointAt != 95) {
            zzj().zzh().zza("Name must start with a letter or _ (underscore). Type, name", str, str2);
            return false;
        }
        int length = str2.length();
        int iCharCount = Character.charCount(iCodePointAt);
        while (iCharCount < length) {
            int iCodePointAt2 = str2.codePointAt(iCharCount);
            if (iCodePointAt2 != 95 && !Character.isLetterOrDigit(iCodePointAt2)) {
                zzj().zzh().zza("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt2);
        }
        return true;
    }

    final long H(String str) {
        if (zza().getPackageManager() == null) {
            return 0L;
        }
        int i2 = 0;
        try {
            ApplicationInfo applicationInfo = Wrappers.packageManager(zza()).getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                i2 = applicationInfo.targetSdkVersion;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            zzj().zzn().zza("PackageManager failed to find running app: app_id", str);
        }
        return i2;
    }

    final Object I(String str, Object obj) {
        return "_ldl".equals(str) ? zza(zzm(str), obj, true, false) : zza(zzm(str), obj, false, false);
    }

    final boolean J(String str, String str2) {
        if (str2 == null) {
            zzj().zzh().zza("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.length() == 0) {
            zzj().zzh().zza("Name is required and can't be empty. Type", str);
            return false;
        }
        int iCodePointAt = str2.codePointAt(0);
        if (!Character.isLetter(iCodePointAt)) {
            zzj().zzh().zza("Name must start with a letter. Type, name", str, str2);
            return false;
        }
        int length = str2.length();
        int iCharCount = Character.charCount(iCodePointAt);
        while (iCharCount < length) {
            int iCodePointAt2 = str2.codePointAt(iCharCount);
            if (iCodePointAt2 != 95 && !Character.isLetterOrDigit(iCodePointAt2)) {
                zzj().zzh().zza("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt2);
        }
        return true;
    }

    final boolean L(String str) {
        zzt();
        if (Wrappers.packageManager(zza()).checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        zzj().zzc().zza("Permission not granted", str);
        return false;
    }

    final boolean M(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return zze().zzn().equals(str);
    }

    final MeasurementManagerFutures P() {
        if (this.zzf == null) {
            this.zzf = MeasurementManagerFutures.from(zza());
        }
        return this.zzf;
    }

    final String Q() {
        byte[] bArr = new byte[16];
        S().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new BigInteger(1, bArr));
    }

    final SecureRandom S() {
        zzt();
        if (this.zzc == null) {
            this.zzc = new SecureRandom();
        }
        return this.zzc;
    }

    final boolean T() {
        zzt();
        return zzh() == 1;
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    protected final boolean a() {
        return true;
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    protected final void b() {
        zzt();
        SecureRandom secureRandom = new SecureRandom();
        long jNextLong = secureRandom.nextLong();
        if (jNextLong == 0) {
            jNextLong = secureRandom.nextLong();
            if (jNextLong == 0) {
                zzj().zzu().zza("Utils falling back to Random for random id");
            }
        }
        this.zzd.set(jNextLong);
    }

    final int c(String str) {
        if (!E(NotificationCompat.CATEGORY_EVENT, str)) {
            return 2;
        }
        if (z(NotificationCompat.CATEGORY_EVENT, zzii.zza, zzii.zzb, str)) {
            return !u(NotificationCompat.CATEGORY_EVENT, 40, str) ? 2 : 0;
        }
        return 13;
    }

    final int d(String str, Object obj) {
        return "_ldl".equals(str) ? w("user property referrer", str, zzm(str), obj) : w("user property", str, zzm(str), obj) ? 0 : 7;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0073  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0066 -> B:19:0x0073). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final long e(android.content.Context r7, java.lang.String r8) throws java.security.NoSuchAlgorithmException {
        /*
            r6 = this;
            r6.zzt()
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r8)
            android.content.pm.PackageManager r0 = r7.getPackageManager()
            java.security.MessageDigest r1 = R()
            r2 = -1
            if (r1 != 0) goto L23
            com.google.android.gms.measurement.internal.zzfs r7 = r6.zzj()
            com.google.android.gms.measurement.internal.zzfu r7 = r7.zzg()
            java.lang.String r8 = "Could not get MD5 instance"
            r7.zza(r8)
            goto L74
        L23:
            r4 = 0
            if (r0 == 0) goto L73
            boolean r8 = r6.zzb(r7, r8)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            if (r8 != 0) goto L73
            com.google.android.gms.common.wrappers.PackageManagerWrapper r7 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            android.content.Context r8 = r6.zza()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            java.lang.String r8 = r8.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            r0 = 64
            android.content.pm.PackageInfo r7 = r7.getPackageInfo(r8, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            android.content.pm.Signature[] r7 = r7.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            if (r7 == 0) goto L58
            int r8 = r7.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            if (r8 <= 0) goto L58
            r8 = 0
            r7 = r7[r8]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            byte[] r7 = r7.toByteArray()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            byte[] r7 = r1.digest(r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            long r2 = f(r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            goto L74
        L56:
            r7 = move-exception
            goto L66
        L58:
            com.google.android.gms.measurement.internal.zzfs r7 = r6.zzj()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            com.google.android.gms.measurement.internal.zzfu r7 = r7.zzu()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            java.lang.String r8 = "Could not get signatures"
            r7.zza(r8)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L56
            goto L74
        L66:
            com.google.android.gms.measurement.internal.zzfs r8 = r6.zzj()
            com.google.android.gms.measurement.internal.zzfu r8 = r8.zzg()
            java.lang.String r0 = "Package name not found"
            r8.zza(r0, r7)
        L73:
            r2 = r4
        L74:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzne.e(android.content.Context, java.lang.String):long");
    }

    final Bundle g(Uri uri, boolean z2, boolean z3) {
        String queryParameter;
        String queryParameter2;
        String queryParameter3;
        String queryParameter4;
        String queryParameter5;
        String queryParameter6;
        String queryParameter7;
        String queryParameter8;
        String queryParameter9;
        String str;
        if (uri == null) {
            return null;
        }
        try {
            if (uri.isHierarchical()) {
                queryParameter = uri.getQueryParameter("utm_campaign");
                queryParameter2 = uri.getQueryParameter("utm_source");
                queryParameter3 = uri.getQueryParameter("utm_medium");
                queryParameter4 = uri.getQueryParameter("gclid");
                queryParameter5 = z3 ? uri.getQueryParameter("gbraid") : null;
                queryParameter6 = uri.getQueryParameter("utm_id");
                queryParameter7 = uri.getQueryParameter("dclid");
                queryParameter8 = uri.getQueryParameter("srsltid");
                queryParameter9 = z2 ? uri.getQueryParameter("sfmc_id") : null;
            } else {
                queryParameter = null;
                queryParameter2 = null;
                queryParameter3 = null;
                queryParameter4 = null;
                queryParameter5 = null;
                queryParameter6 = null;
                queryParameter7 = null;
                queryParameter8 = null;
                queryParameter9 = null;
            }
            if (TextUtils.isEmpty(queryParameter) && TextUtils.isEmpty(queryParameter2) && TextUtils.isEmpty(queryParameter3) && TextUtils.isEmpty(queryParameter4) && ((!z3 || TextUtils.isEmpty(queryParameter5)) && TextUtils.isEmpty(queryParameter6) && TextUtils.isEmpty(queryParameter7) && TextUtils.isEmpty(queryParameter8) && (!z2 || TextUtils.isEmpty(queryParameter9)))) {
                return null;
            }
            Bundle bundle = new Bundle();
            if (TextUtils.isEmpty(queryParameter)) {
                str = "sfmc_id";
            } else {
                str = "sfmc_id";
                bundle.putString("campaign", queryParameter);
            }
            if (!TextUtils.isEmpty(queryParameter2)) {
                bundle.putString("source", queryParameter2);
            }
            if (!TextUtils.isEmpty(queryParameter3)) {
                bundle.putString("medium", queryParameter3);
            }
            if (!TextUtils.isEmpty(queryParameter4)) {
                bundle.putString("gclid", queryParameter4);
            }
            if (z3 && !TextUtils.isEmpty(queryParameter5)) {
                bundle.putString("gbraid", queryParameter5);
            }
            String queryParameter10 = uri.getQueryParameter("utm_term");
            if (!TextUtils.isEmpty(queryParameter10)) {
                bundle.putString(FirebaseAnalytics.Param.TERM, queryParameter10);
            }
            String queryParameter11 = uri.getQueryParameter("utm_content");
            if (!TextUtils.isEmpty(queryParameter11)) {
                bundle.putString("content", queryParameter11);
            }
            String queryParameter12 = uri.getQueryParameter(FirebaseAnalytics.Param.ACLID);
            if (!TextUtils.isEmpty(queryParameter12)) {
                bundle.putString(FirebaseAnalytics.Param.ACLID, queryParameter12);
            }
            String queryParameter13 = uri.getQueryParameter(FirebaseAnalytics.Param.CP1);
            if (!TextUtils.isEmpty(queryParameter13)) {
                bundle.putString(FirebaseAnalytics.Param.CP1, queryParameter13);
            }
            String queryParameter14 = uri.getQueryParameter("anid");
            if (!TextUtils.isEmpty(queryParameter14)) {
                bundle.putString("anid", queryParameter14);
            }
            if (!TextUtils.isEmpty(queryParameter6)) {
                bundle.putString(FirebaseAnalytics.Param.CAMPAIGN_ID, queryParameter6);
            }
            if (!TextUtils.isEmpty(queryParameter7)) {
                bundle.putString("dclid", queryParameter7);
            }
            String queryParameter15 = uri.getQueryParameter("utm_source_platform");
            if (!TextUtils.isEmpty(queryParameter15)) {
                bundle.putString(FirebaseAnalytics.Param.SOURCE_PLATFORM, queryParameter15);
            }
            String queryParameter16 = uri.getQueryParameter("utm_creative_format");
            if (!TextUtils.isEmpty(queryParameter16)) {
                bundle.putString(FirebaseAnalytics.Param.CREATIVE_FORMAT, queryParameter16);
            }
            String queryParameter17 = uri.getQueryParameter("utm_marketing_tactic");
            if (!TextUtils.isEmpty(queryParameter17)) {
                bundle.putString(FirebaseAnalytics.Param.MARKETING_TACTIC, queryParameter17);
            }
            if (!TextUtils.isEmpty(queryParameter8)) {
                bundle.putString("srsltid", queryParameter8);
            }
            if (z2 && !TextUtils.isEmpty(queryParameter9)) {
                bundle.putString(str, queryParameter9);
            }
            return bundle;
        } catch (UnsupportedOperationException e2) {
            zzj().zzu().zza("Install referrer url isn't a hierarchical URI", e2);
            return null;
        }
    }

    final Bundle h(String str, String str2, Bundle bundle, List list, boolean z2) {
        int iZzl;
        int i2;
        zzne zzneVar = this;
        boolean zZza = zza(str2, zzii.zzd);
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        int iZzg = zze().zzg();
        int i3 = 0;
        for (String str3 : new TreeSet(bundle.keySet())) {
            if (list == null || !list.contains(str3)) {
                iZzl = !z2 ? zzneVar.zzl(str3) : 0;
                if (iZzl == 0) {
                    iZzl = zzneVar.zzk(str3);
                }
            } else {
                iZzl = 0;
            }
            if (iZzl != 0) {
                zza(bundle2, iZzl, str3, str3, iZzl == 3 ? str3 : null);
                bundle2.remove(str3);
                i2 = iZzg;
            } else {
                i2 = iZzg;
                int iZza = zza(str, str2, str3, bundle.get(str3), bundle2, list, z2, zZza);
                if (iZza == 17) {
                    zza(bundle2, iZza, str3, str3, Boolean.FALSE);
                } else if (iZza != 0 && !"_ev".equals(str3)) {
                    zza(bundle2, iZza, iZza == 21 ? str2 : str3, str3, bundle.get(str3));
                    bundle2.remove(str3);
                }
                if (O(str3)) {
                    int i4 = i3 + 1;
                    if (i4 > i2) {
                        zzj().zzh().zza("Event can't contain more than " + i2 + " params", zzi().c(str2), zzi().a(bundle));
                        zzb(bundle2, 5);
                        bundle2.remove(str3);
                    }
                    i3 = i4;
                }
            }
            iZzg = i2;
            zzneVar = this;
        }
        return bundle2;
    }

    final zzbg i(String str, String str2, Bundle bundle, String str3, long j2, boolean z2, boolean z3) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        if (c(str2) != 0) {
            zzj().zzg().zza("Invalid conditional property event name", zzi().e(str2));
            throw new IllegalArgumentException();
        }
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putString("_o", str3);
        Bundle bundleH = h(str, str2, bundle2, CollectionUtils.listOf("_o"), true);
        if (z2) {
            bundleH = C(bundleH);
        }
        Preconditions.checkNotNull(bundleH);
        return new zzbg(str2, new zzbb(bundleH), str3, j2);
    }

    final void j(Bundle bundle, Bundle bundle2) {
        if (bundle2 == null) {
            return;
        }
        for (String str : bundle2.keySet()) {
            if (!bundle.containsKey(str)) {
                zzq().k(bundle, str, bundle2.get(str));
            }
        }
    }

    final void k(Bundle bundle, String str, Object obj) {
        if (bundle == null) {
            return;
        }
        if (obj instanceof Long) {
            bundle.putLong(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof String) {
            bundle.putString(str, String.valueOf(obj));
            return;
        }
        if (obj instanceof Double) {
            bundle.putDouble(str, ((Double) obj).doubleValue());
        } else if (obj instanceof Bundle[]) {
            bundle.putParcelableArray(str, (Bundle[]) obj);
        } else if (str != null) {
            zzj().zzv().zza("Not putting event parameter. Invalid value type. name, type", zzi().d(str), obj != null ? obj.getClass().getSimpleName() : null);
        }
    }

    final void l(zzfw zzfwVar, int i2) {
        int i3 = 0;
        for (String str : new TreeSet(zzfwVar.zzb.keySet())) {
            if (O(str) && (i3 = i3 + 1) > i2) {
                zzj().zzh().zza("Event can't contain more than " + i2 + " params", zzi().c(zzfwVar.zza), zzi().a(zzfwVar.zzb));
                zzb(zzfwVar.zzb, 5);
                zzfwVar.zzb.remove(str);
            }
        }
    }

    final void n(Parcelable[] parcelableArr, int i2, boolean z2) {
        Preconditions.checkNotNull(parcelableArr);
        for (Parcelable parcelable : parcelableArr) {
            Bundle bundle = (Bundle) parcelable;
            int i3 = 0;
            for (String str : new TreeSet(bundle.keySet())) {
                if (O(str) && !zza(str, zzih.zzd) && (i3 = i3 + 1) > i2) {
                    if (z2) {
                        zzj().zzh().zza("Param can't contain more than " + i2 + " item-scoped custom parameters", zzi().d(str), zzi().a(bundle));
                        zzb(bundle, 28);
                    } else {
                        zzj().zzh().zza("Param cannot contain item-scoped custom parameters", zzi().d(str), zzi().a(bundle));
                        zzb(bundle, 23);
                    }
                    bundle.remove(str);
                }
            }
        }
    }

    final boolean t(String str, double d2) {
        try {
            SharedPreferences.Editor editorEdit = zza().getSharedPreferences("google.analytics.deferred.deeplink.prefs", 0).edit();
            editorEdit.putString("deeplink", str);
            editorEdit.putLong(com.alipay.sdk.m.t.a.f9743k, Double.doubleToRawLongBits(d2));
            return editorEdit.commit();
        } catch (RuntimeException e2) {
            zzj().zzg().zza("Failed to persist Deferred Deep Link. exception", e2);
            return false;
        }
    }

    final boolean u(String str, int i2, String str2) throws IllegalStateException {
        if (str2 == null) {
            zzj().zzh().zza("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.codePointCount(0, str2.length()) <= i2) {
            return true;
        }
        zzj().zzh().zza("Name is too long. Type, maximum supported length, name", str, Integer.valueOf(i2), str2);
        return false;
    }

    final boolean v(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (zzn(str)) {
                return true;
            }
            if (this.f13286a.zzae()) {
                zzj().zzh().zza("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", zzfs.d(str));
            }
            return false;
        }
        if (TextUtils.isEmpty(str2)) {
            if (this.f13286a.zzae()) {
                zzj().zzh().zza("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
            }
            return false;
        }
        if (zzn(str2)) {
            return true;
        }
        zzj().zzh().zza("Invalid admob_app_id. Analytics disabled.", zzfs.d(str2));
        return false;
    }

    final boolean w(String str, String str2, int i2, Object obj) throws IllegalStateException {
        if (obj != null && !(obj instanceof Long) && !(obj instanceof Float) && !(obj instanceof Integer) && !(obj instanceof Byte) && !(obj instanceof Short) && !(obj instanceof Boolean) && !(obj instanceof Double)) {
            if (!(obj instanceof String) && !(obj instanceof Character) && !(obj instanceof CharSequence)) {
                return false;
            }
            String strValueOf = String.valueOf(obj);
            if (strValueOf.codePointCount(0, strValueOf.length()) > i2) {
                zzj().zzv().zza("Value is too long; discarded. Value kind, name, value length", str, str2, Integer.valueOf(strValueOf.length()));
                return false;
            }
        }
        return true;
    }

    final boolean y(String str, String[] strArr, String str2) {
        return z(str, strArr, null, str2);
    }

    final boolean z(String str, String[] strArr, String[] strArr2, String str2) {
        if (str2 == null) {
            zzj().zzh().zza("Name is required and can't be null. Type", str);
            return false;
        }
        Preconditions.checkNotNull(str2);
        for (String str3 : zza) {
            if (str2.startsWith(str3)) {
                zzj().zzh().zza("Name starts with reserved prefix. Type, name", str, str2);
                return false;
            }
        }
        if (strArr == null || !zza(str2, strArr)) {
            return true;
        }
        if (strArr2 != null && zza(str2, strArr2)) {
            return true;
        }
        zzj().zzh().zza("Name is reserved. Type, name", str, str2);
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzae zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzaf zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    @EnsuresNonNull({"this.apkVersion"})
    public final int zzg() {
        if (this.zzh == null) {
            this.zzh = Integer.valueOf(GoogleApiAvailabilityLight.getInstance().getApkVersion(zza()) / 1000);
        }
        return this.zzh.intValue();
    }

    final long zzh() {
        zzt();
        if (!K(this.f13286a.zzh().f())) {
            return 0L;
        }
        long j2 = Build.VERSION.SDK_INT < 30 ? 4L : SdkExtensions.getExtensionVersion(30) < 4 ? 8L : G() < zzbi.zzau.zza(null).intValue() ? 16L : 0L;
        if (!L("android.permission.ACCESS_ADSERVICES_ATTRIBUTION")) {
            j2 |= 2;
        }
        if (j2 == 0 && !zzy()) {
            j2 |= 64;
        }
        if (j2 == 0) {
            return 1L;
        }
        return j2;
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzfn zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzfs zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    public final boolean zzx() throws ClassNotFoundException {
        try {
            zza().getClassLoader().loadClass("com.google.firebase.remoteconfig.FirebaseRemoteConfig");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static ArrayList<Bundle> zzb(List<zzad> list) {
        if (list == null) {
            return new ArrayList<>(0);
        }
        ArrayList<Bundle> arrayList = new ArrayList<>(list.size());
        for (zzad zzadVar : list) {
            Bundle bundle = new Bundle();
            bundle.putString("app_id", zzadVar.zza);
            bundle.putString("origin", zzadVar.zzb);
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, zzadVar.zzd);
            bundle.putString("name", zzadVar.zzc.zza);
            zzif.zza(bundle, Preconditions.checkNotNull(zzadVar.zzc.zza()));
            bundle.putBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, zzadVar.zze);
            String str = zzadVar.zzf;
            if (str != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, str);
            }
            zzbg zzbgVar = zzadVar.zzg;
            if (zzbgVar != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, zzbgVar.zza);
                zzbb zzbbVar = zzbgVar.zzb;
                if (zzbbVar != null) {
                    bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, zzbbVar.zzb());
                }
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, zzadVar.zzh);
            zzbg zzbgVar2 = zzadVar.zzi;
            if (zzbgVar2 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, zzbgVar2.zza);
                zzbb zzbbVar2 = zzbgVar2.zzb;
                if (zzbbVar2 != null) {
                    bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, zzbbVar2.zzb());
                }
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, zzadVar.zzc.zzb);
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, zzadVar.zzj);
            zzbg zzbgVar3 = zzadVar.zzk;
            if (zzbgVar3 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, zzbgVar3.zza);
                zzbb zzbbVar3 = zzbgVar3.zzb;
                if (zzbbVar3 != null) {
                    bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, zzbbVar3.zzb());
                }
            }
            arrayList.add(bundle);
        }
        return arrayList;
    }

    public static boolean zzj(String str) {
        for (String str2 : zzb) {
            if (str2.equals(str)) {
                return false;
            }
        }
        return true;
    }

    public final boolean zzi(String str) {
        List<ResolveInfo> listQueryIntentActivities;
        return (TextUtils.isEmpty(str) || (listQueryIntentActivities = zza().getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(str)), 0)) == null || listQueryIntentActivities.isEmpty()) ? false : true;
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzge zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzgz zzl() {
        return super.zzl();
    }

    public final long zzm() {
        long andIncrement;
        long j2;
        if (this.zzd.get() == 0) {
            synchronized (this.zzd) {
                long jNextLong = new Random(System.nanoTime() ^ zzb().currentTimeMillis()).nextLong();
                int i2 = this.zze + 1;
                this.zze = i2;
                j2 = jNextLong + i2;
            }
            return j2;
        }
        synchronized (this.zzd) {
            this.zzd.compareAndSet(-1L, 1L);
            andIncrement = this.zzd.getAndIncrement();
        }
        return andIncrement;
    }

    private static boolean zzb(Bundle bundle, int i2) {
        if (bundle == null || bundle.getLong("_err") != 0) {
            return false;
        }
        bundle.putLong("_err", i2);
        return true;
    }

    @VisibleForTesting
    private final boolean zzb(Context context, String str) {
        Signature[] signatureArr;
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(str, 64);
            if (packageInfo == null || (signatureArr = packageInfo.signatures) == null || signatureArr.length <= 0) {
                return true;
            }
            return ((X509Certificate) CertificateFactory.getInstance(e.f16169b).generateCertificate(new ByteArrayInputStream(signatureArr[0].toByteArray()))).getSubjectX500Principal().equals(x500Principal);
        } catch (PackageManager.NameNotFoundException e2) {
            zzj().zzg().zza("Package name not found", e2);
            return true;
        } catch (CertificateException e3) {
            zzj().zzg().zza("Error obtaining certificate", e3);
            return true;
        }
    }

    public final int zza(int i2) {
        return GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(zza(), 12451000);
    }

    public static long zza(zzbb zzbbVar) {
        long length = 0;
        if (zzbbVar == null) {
            return 0L;
        }
        Iterator<String> it = zzbbVar.iterator();
        while (it.hasNext()) {
            if (zzbbVar.d(it.next()) instanceof Parcelable[]) {
                length += ((Parcelable[]) r3).length;
            }
        }
        return length;
    }

    public static long zza(long j2, long j3) {
        return (j2 + (j3 * 60000)) / 86400000;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    public static Bundle zza(List<zzmz> list) {
        Bundle bundle = new Bundle();
        if (list == null) {
            return bundle;
        }
        for (zzmz zzmzVar : list) {
            String str = zzmzVar.zzd;
            if (str != null) {
                bundle.putString(zzmzVar.zza, str);
            } else {
                Long l2 = zzmzVar.zzc;
                if (l2 != null) {
                    bundle.putLong(zzmzVar.zza, l2.longValue());
                } else {
                    Double d2 = zzmzVar.zzf;
                    if (d2 != null) {
                        bundle.putDouble(zzmzVar.zza, d2.doubleValue());
                    }
                }
            }
        }
        return bundle;
    }

    public static Bundle zza(Bundle bundle) {
        if (bundle == null) {
            return new Bundle();
        }
        Bundle bundle2 = new Bundle(bundle);
        for (String str : bundle2.keySet()) {
            Object obj = bundle2.get(str);
            if (obj instanceof Bundle) {
                bundle2.putBundle(str, new Bundle((Bundle) obj));
            } else {
                int i2 = 0;
                if (obj instanceof Parcelable[]) {
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    while (i2 < parcelableArr.length) {
                        if (parcelableArr[i2] instanceof Bundle) {
                            parcelableArr[i2] = new Bundle((Bundle) parcelableArr[i2]);
                        }
                        i2++;
                    }
                } else if (obj instanceof List) {
                    List list = (List) obj;
                    while (i2 < list.size()) {
                        Object obj2 = list.get(i2);
                        if (obj2 instanceof Bundle) {
                            list.set(i2, new Bundle((Bundle) obj2));
                        }
                        i2++;
                    }
                }
            }
        }
        return bundle2;
    }

    private final Object zza(int i2, Object obj, boolean z2, boolean z3) {
        Bundle bundleC;
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            return obj;
        }
        if (obj instanceof Integer) {
            return Long.valueOf(((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return Long.valueOf(((Byte) obj).byteValue());
        }
        if (obj instanceof Short) {
            return Long.valueOf(((Short) obj).shortValue());
        }
        if (obj instanceof Boolean) {
            return Long.valueOf(((Boolean) obj).booleanValue() ? 1L : 0L);
        }
        if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        }
        if (!(obj instanceof String) && !(obj instanceof Character) && !(obj instanceof CharSequence)) {
            if (!z3 || (!(obj instanceof Bundle[]) && !(obj instanceof Parcelable[]))) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Parcelable parcelable : (Parcelable[]) obj) {
                if ((parcelable instanceof Bundle) && (bundleC = C((Bundle) parcelable)) != null && !bundleC.isEmpty()) {
                    arrayList.add(bundleC);
                }
            }
            return arrayList.toArray(new Bundle[arrayList.size()]);
        }
        return zza(String.valueOf(obj), i2, z2);
    }

    public static String zza(String str, int i2, boolean z2) {
        if (str == null) {
            return null;
        }
        if (str.codePointCount(0, str.length()) <= i2) {
            return str;
        }
        if (!z2) {
            return null;
        }
        return str.substring(0, str.offsetByCodePoints(0, i2)) + "...";
    }

    public final URL zza(long j2, String str, String str2, long j3, String str3) {
        try {
            Preconditions.checkNotEmpty(str2);
            Preconditions.checkNotEmpty(str);
            String strConcat = String.format("https://www.googleadservices.com/pagead/conversion/app/deeplink?id_type=adid&sdk_version=%s&rdid=%s&bundleid=%s&retry=%s", String.format("v%s.%s", Long.valueOf(j2), Integer.valueOf(zzg())), str2, str, Long.valueOf(j3));
            if (str.equals(zze().zzo())) {
                strConcat = strConcat.concat("&ddl_test=1");
            }
            if (!str3.isEmpty()) {
                if (str3.charAt(0) != '&') {
                    strConcat = strConcat.concat("&");
                }
                strConcat = strConcat.concat(str3);
            }
            return new URL(strConcat);
        } catch (IllegalArgumentException e2) {
            e = e2;
            zzj().zzg().zza("Failed to create BOW URL for Deferred Deep Link. exception", e.getMessage());
            return null;
        } catch (MalformedURLException e3) {
            e = e3;
            zzj().zzg().zza("Failed to create BOW URL for Deferred Deep Link. exception", e.getMessage());
            return null;
        }
    }

    final void zza(Bundle bundle, long j2) {
        long j3 = bundle.getLong("_et");
        if (j3 != 0) {
            zzj().zzu().zza("Params already contained engagement", Long.valueOf(j3));
        }
        bundle.putLong("_et", j2 + j3);
    }

    private static void zza(Bundle bundle, int i2, String str, String str2, Object obj) {
        if (zzb(bundle, i2)) {
            bundle.putString("_ev", zza(str, 40, true));
            if (obj != null) {
                Preconditions.checkNotNull(bundle);
                if ((obj instanceof String) || (obj instanceof CharSequence)) {
                    bundle.putLong("_el", String.valueOf(obj).length());
                }
            }
        }
    }

    @WorkerThread
    public static void zza(zzkf zzkfVar, Bundle bundle, boolean z2) {
        if (bundle == null || zzkfVar == null || (bundle.containsKey("_sc") && !z2)) {
            if (bundle != null && zzkfVar == null && z2) {
                bundle.remove("_sn");
                bundle.remove("_sc");
                bundle.remove("_si");
                return;
            }
            return;
        }
        String str = zzkfVar.zza;
        if (str != null) {
            bundle.putString("_sn", str);
        } else {
            bundle.remove("_sn");
        }
        String str2 = zzkfVar.zzb;
        if (str2 != null) {
            bundle.putString("_sc", str2);
        } else {
            bundle.remove("_sc");
        }
        bundle.putLong("_si", zzkfVar.zzc);
    }

    public static void zza(zznf zznfVar, int i2, String str, String str2, int i3) {
        m(zznfVar, null, i2, str, str2, i3);
    }

    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, boolean z2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("r", z2);
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e2) {
            this.f13286a.zzj().zzu().zza("Error returning boolean value to wrapper", e2);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, ArrayList<Bundle> arrayList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("r", arrayList);
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e2) {
            this.f13286a.zzj().zzu().zza("Error returning bundle list to wrapper", e2);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, Bundle bundle) {
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e2) {
            this.f13286a.zzj().zzu().zza("Error returning bundle value to wrapper", e2);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, byte[] bArr) {
        Bundle bundle = new Bundle();
        bundle.putByteArray("r", bArr);
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e2) {
            this.f13286a.zzj().zzu().zza("Error returning byte array to wrapper", e2);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, int i2) {
        Bundle bundle = new Bundle();
        bundle.putInt("r", i2);
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e2) {
            this.f13286a.zzj().zzu().zza("Error returning int value to wrapper", e2);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, long j2) {
        Bundle bundle = new Bundle();
        bundle.putLong("r", j2);
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e2) {
            this.f13286a.zzj().zzu().zza("Error returning long value to wrapper", e2);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("r", str);
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e2) {
            this.f13286a.zzj().zzu().zza("Error returning string value to wrapper", e2);
        }
    }

    private final void zza(String str, String str2, String str3, Bundle bundle, List<String> list, boolean z2) throws IllegalStateException {
        int iZzl;
        String str4;
        int iZza;
        if (bundle == null) {
            return;
        }
        int iD = zze().d();
        int i2 = 0;
        for (String str5 : new TreeSet(bundle.keySet())) {
            if (list == null || !list.contains(str5)) {
                iZzl = !z2 ? zzl(str5) : 0;
                if (iZzl == 0) {
                    iZzl = zzk(str5);
                }
            } else {
                iZzl = 0;
            }
            if (iZzl != 0) {
                zza(bundle, iZzl, str5, str5, iZzl == 3 ? str5 : null);
                bundle.remove(str5);
            } else {
                if (s(bundle.get(str5))) {
                    zzj().zzv().zza("Nested Bundle parameters are not allowed; discarded. event name, param name, child param name", str2, str3, str5);
                    iZza = 22;
                    str4 = str5;
                } else {
                    str4 = str5;
                    iZza = zza(str, str2, str5, bundle.get(str5), bundle, list, z2, false);
                }
                if (iZza != 0 && !"_ev".equals(str4)) {
                    zza(bundle, iZza, str4, str4, bundle.get(str4));
                    bundle.remove(str4);
                } else if (O(str4) && !zza(str4, zzih.zzd)) {
                    int i3 = i2 + 1;
                    if (!zza(231100000, true)) {
                        zzj().zzh().zza("Item array not supported on client's version of Google Play Services (Android Only)", zzi().c(str2), zzi().a(bundle));
                        zzb(bundle, 23);
                        bundle.remove(str4);
                    } else if (i3 > iD) {
                        if (zzrp.zzb() && zze().zza(zzbi.zzcc)) {
                            zzj().zzh().zza("Item can't contain more than " + iD + " item-scoped custom params", zzi().c(str2), zzi().a(bundle));
                            zzb(bundle, 28);
                            bundle.remove(str4);
                        } else {
                            zzj().zzh().zza("Item cannot contain custom parameters", zzi().c(str2), zzi().a(bundle));
                            zzb(bundle, 23);
                            bundle.remove(str4);
                        }
                    }
                    i2 = i3;
                }
            }
        }
    }

    private static boolean zza(String str, String[] strArr) {
        Preconditions.checkNotNull(strArr);
        for (String str2 : strArr) {
            if (zznd.zza(str, str2)) {
                return true;
            }
        }
        return false;
    }

    public final boolean zza(int i2, boolean z2) {
        Boolean boolS = this.f13286a.zzr().s();
        if (zzg() < i2 / 1000) {
            return (boolS == null || boolS.booleanValue()) ? false : true;
        }
        return true;
    }
}
