package com.alipay.sdk.m.u;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.webkit.ProxyConfig;
import anet.channel.util.HttpConstant;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.m.m.a;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.hms.framework.common.ContainerUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9816a = "com.alipay.android.app";

    /* renamed from: b, reason: collision with root package name */
    public static final String f9817b = "com.eg.android.AlipayGphone";

    /* renamed from: c, reason: collision with root package name */
    public static final String f9818c = "com.eg.android.AlipayGphoneRC";

    /* renamed from: d, reason: collision with root package name */
    public static final int f9819d = 99;

    /* renamed from: f, reason: collision with root package name */
    public static final int f9821f = 125;

    /* renamed from: e, reason: collision with root package name */
    public static final String[] f9820e = {"10.1.5.1013151", "10.1.5.1013148"};

    /* renamed from: g, reason: collision with root package name */
    public static final char[] f9822g = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '+', IOUtils.DIR_SEPARATOR_UNIX};

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Activity f9823a;

        public a(Activity activity) {
            this.f9823a = activity;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f9823a.finish();
        }
    }

    public static class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Runnable f9824a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ ConditionVariable f9825b;

        public b(Runnable runnable, ConditionVariable conditionVariable) {
            this.f9824a = runnable;
            this.f9825b = conditionVariable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.f9824a.run();
            } finally {
                this.f9825b.open();
            }
        }
    }

    public static String a(String str, String str2, String str3) {
        try {
            int iIndexOf = str3.indexOf(str) + str.length();
            if (iIndexOf <= str.length()) {
                return "";
            }
            int iIndexOf2 = !TextUtils.isEmpty(str2) ? str3.indexOf(str2, iIndexOf) : 0;
            return iIndexOf2 < 1 ? str3.substring(iIndexOf) : str3.substring(iIndexOf, iIndexOf2);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String b(Context context) {
        return "-1;-1";
    }

    public static String c(String str) {
        return (EnvUtils.isSandBox() && TextUtils.equals(str, f9818c)) ? "com.eg.android.AlipayGphoneRC.IAlixPay" : "com.eg.android.AlipayGphone.IAlixPay";
    }

    public static int d(String str) {
        for (int i2 = 0; i2 < 64; i2++) {
            if (str.equals(String.valueOf(f9822g[i2]))) {
                return i2;
            }
        }
        return 0;
    }

    public static String e(com.alipay.sdk.m.s.a aVar, String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e2) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.B, e2);
            return "";
        }
    }

    public static String f() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static String g(Context context) throws IOException {
        return " (" + f() + i.f9802b + e() + i.f9802b + c(context) + i.f9802b + i.f9802b + f(context) + ")(sdk android)";
    }

    public static JSONObject h(String str) {
        try {
            return new JSONObject(str);
        } catch (Throwable unused) {
            return new JSONObject();
        }
    }

    public static String i(String str) {
        try {
            Uri uri = Uri.parse(str);
            return String.format("%s%s", uri.getAuthority(), uri.getPath());
        } catch (Throwable th) {
            e.a(th);
            return Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        }
    }

    public static String b() {
        if (EnvUtils.isSandBox()) {
            return f9818c;
        }
        try {
            return com.alipay.sdk.m.j.a.f9316d.get(0).f9543a;
        } catch (Throwable unused) {
            return f9817b;
        }
    }

    public static String c(Context context) {
        return context.getResources().getConfiguration().locale.toString();
    }

    public static String f(Context context) {
        DisplayMetrics displayMetricsD = d(context);
        return displayMetricsD.widthPixels + ProxyConfig.MATCH_ALL_SCHEMES + displayMetricsD.heightPixels;
    }

    public static String d() throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/version"), 256);
            try {
                String line = bufferedReader.readLine();
                bufferedReader.close();
                Matcher matcher = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(line);
                if (!matcher.matches() || matcher.groupCount() < 4) {
                    return "Unavailable";
                }
                return matcher.group(1) + "\n" + matcher.group(2) + " " + matcher.group(3) + "\n" + matcher.group(4);
            } catch (Throwable th) {
                bufferedReader.close();
                throw th;
            }
        } catch (IOException unused) {
            return "Unavailable";
        }
    }

    public static String e() throws IOException {
        String strD = d();
        int iIndexOf = strD.indexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        if (iIndexOf != -1) {
            strD = strD.substring(0, iIndexOf);
        }
        int iIndexOf2 = strD.indexOf("\n");
        if (iIndexOf2 != -1) {
            strD = strD.substring(0, iIndexOf2);
        }
        return "Linux " + strD;
    }

    public static boolean h(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(f9816a, 128) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static Map<String, String> b(String str) {
        HashMap map = new HashMap();
        for (String str2 : str.split("&")) {
            int iIndexOf = str2.indexOf(ContainerUtils.KEY_VALUE_DELIMITER, 1);
            if (-1 != iIndexOf) {
                map.put(str2.substring(0, iIndexOf), URLDecoder.decode(str2.substring(iIndexOf + 1)));
            }
        }
        return map;
    }

    public static boolean h() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static final class c {

        /* renamed from: a, reason: collision with root package name */
        public final PackageInfo f9826a;

        /* renamed from: b, reason: collision with root package name */
        public final int f9827b;

        /* renamed from: c, reason: collision with root package name */
        public final String f9828c;

        public c(PackageInfo packageInfo, int i2, String str) {
            this.f9826a = packageInfo;
            this.f9827b = i2;
            this.f9828c = str;
        }

        public boolean a(com.alipay.sdk.m.s.a aVar) {
            Signature[] signatureArr = this.f9826a.signatures;
            if (signatureArr == null || signatureArr.length == 0) {
                return false;
            }
            for (Signature signature : signatureArr) {
                String strA = n.a(aVar, signature.toByteArray());
                if (strA != null && !TextUtils.equals(strA, this.f9828c)) {
                    com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.D, String.format("Got %s, expected %s", strA, this.f9828c));
                    return true;
                }
            }
            return false;
        }

        public boolean a() {
            return this.f9826a.versionCode < this.f9827b;
        }
    }

    public static String c(com.alipay.sdk.m.s.a aVar, String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).invoke(null, str);
        } catch (Exception e2) {
            com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9364l, "rflex", e2.getClass().getSimpleName());
            return null;
        }
    }

    public static boolean i() {
        try {
            String[] strArrSplit = com.alipay.sdk.m.m.a.z().g().split("\\|");
            String str = Build.MODEL;
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            for (String str2 : strArrSplit) {
                if (TextUtils.equals(str, str2) || TextUtils.equals(str2, "all")) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            e.a(th);
            return false;
        }
    }

    public static String a(com.alipay.sdk.m.s.a aVar, byte[] bArr) {
        BigInteger modulus;
        try {
            PublicKey publicKey = ((X509Certificate) CertificateFactory.getInstance(com.huawei.hms.feature.dynamic.f.e.f16169b).generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey();
            if (!(publicKey instanceof RSAPublicKey) || (modulus = ((RSAPublicKey) publicKey).getModulus()) == null) {
                return null;
            }
            return modulus.toString(16);
        } catch (Exception e2) {
            com.alipay.sdk.m.k.a.a(aVar, "auth", com.alipay.sdk.m.k.b.f9388x, e2);
            return null;
        }
    }

    public static boolean f(String str) {
        return Pattern.compile("^http(s)?://([a-z0-9_\\-]+\\.)*(alipaydev|alipay|taobao)\\.(com|net)(:\\d+)?(/.*)?$").matcher(str).matches();
    }

    public static String e(Context context) {
        String strB = m.b(context);
        return strB.substring(0, strB.indexOf(HttpConstant.SCHEME_SPLIT));
    }

    public static String g(String str) {
        return a(str, true);
    }

    public static int c() {
        try {
            String lowerCase = Build.BRAND.toLowerCase();
            String lowerCase2 = Build.MANUFACTURER.toLowerCase();
            if (a("huawei", lowerCase, lowerCase2)) {
                return 1;
            }
            if (a("oppo", lowerCase, lowerCase2)) {
                return 2;
            }
            if (a("vivo", lowerCase, lowerCase2)) {
                return 4;
            }
            if (a("lenovo", lowerCase, lowerCase2)) {
                return 8;
            }
            if (a("xiaomi", lowerCase, lowerCase2)) {
                return 16;
            }
            return a("oneplus", lowerCase, lowerCase2) ? 32 : 0;
        } catch (Exception unused) {
            return 61440;
        }
    }

    public static int g() {
        try {
            return Process.myUid();
        } catch (Throwable th) {
            e.a(th);
            return -200;
        }
    }

    public static Map<String, String> b(com.alipay.sdk.m.s.a aVar, String str) {
        HashMap map = new HashMap(4);
        int iIndexOf = str.indexOf(63);
        if (iIndexOf != -1 && iIndexOf < str.length() - 1) {
            for (String str2 : str.substring(iIndexOf + 1).split("&")) {
                int iIndexOf2 = str2.indexOf(61, 1);
                if (iIndexOf2 != -1 && iIndexOf2 < str2.length() - 1) {
                    map.put(str2.substring(0, iIndexOf2), e(aVar, str2.substring(iIndexOf2 + 1)));
                }
            }
        }
        return map;
    }

    public static int e(String str) {
        try {
            String strJ = com.alipay.sdk.m.m.a.z().j();
            if (TextUtils.isEmpty(strJ)) {
                return 0;
            }
            return (b(strJ, "").contains(str) ? 2 : 0) | 1;
        } catch (Throwable unused) {
            return 61440;
        }
    }

    public static DisplayMetrics d(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static c a(com.alipay.sdk.m.s.a aVar, Context context, List<a.b> list) {
        c cVarA;
        if (list == null) {
            return null;
        }
        for (a.b bVar : list) {
            if (bVar != null && (cVarA = a(aVar, context, bVar.f9543a, bVar.f9544b, bVar.f9545c)) != null && !cVarA.a(aVar) && !cVarA.a()) {
                return cVarA;
            }
        }
        return null;
    }

    public static boolean d(com.alipay.sdk.m.s.a aVar, String str) {
        try {
            int iE = e(str);
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "bindExt", "" + iE);
            return com.alipay.sdk.m.m.a.z().o() && (iE & 2) == 2;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static c a(com.alipay.sdk.m.s.a aVar, Context context, String str, int i2, String str2) {
        PackageInfo packageInfoA;
        if (EnvUtils.isSandBox() && f9817b.equals(str)) {
            str = f9818c;
        }
        try {
            packageInfoA = a(context, str);
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.b(aVar, "auth", com.alipay.sdk.m.k.b.f9384v, th.getMessage());
            packageInfoA = null;
        }
        if (a(aVar, packageInfoA)) {
            return a(packageInfoA, i2, str2);
        }
        return null;
    }

    public static String b(com.alipay.sdk.m.s.a aVar, Context context) {
        return a(aVar, context, context.getPackageName());
    }

    public static boolean a(com.alipay.sdk.m.s.a aVar, PackageInfo packageInfo) {
        String str = "";
        boolean z2 = false;
        if (packageInfo == null) {
            str = "info == null";
        } else {
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr == null) {
                str = "info.signatures == null";
            } else if (signatureArr.length <= 0) {
                str = "info.signatures.length <= 0";
            } else {
                z2 = true;
            }
        }
        if (!z2) {
            com.alipay.sdk.m.k.a.b(aVar, "auth", com.alipay.sdk.m.k.b.f9386w, str);
        }
        return z2;
    }

    public static int b(int i2) {
        return i2 / 100000;
    }

    public static String b(String str, String str2) {
        String string = Settings.Secure.getString(((Application) com.alipay.sdk.m.s.b.d().b()).getContentResolver(), str);
        return string != null ? string : str2;
    }

    public static boolean b(com.alipay.sdk.m.s.a aVar) {
        if (aVar == null || TextUtils.isEmpty(aVar.f9730g)) {
            return false;
        }
        return aVar.f9730g.toLowerCase().contains("auth");
    }

    public static PackageInfo a(Context context, String str) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(str, 192);
    }

    public static c a(PackageInfo packageInfo, int i2, String str) {
        if (packageInfo == null) {
            return null;
        }
        return new c(packageInfo, i2, str);
    }

    public static long a(String str) {
        return a(str, 6);
    }

    public static long a(String str, int i2) {
        int iPow = (int) Math.pow(2.0d, i2);
        int length = str.length();
        long j2 = 0;
        int i3 = length;
        for (int i4 = 0; i4 < length; i4++) {
            j2 += Integer.parseInt(String.valueOf(d(str.substring(i4, r5)))) * ((long) Math.pow(iPow, i3 - 1));
            i3--;
        }
        return j2;
    }

    public static int a() {
        String strC = com.alipay.sdk.m.s.b.d().c();
        if (TextUtils.isEmpty(strC)) {
            return -1;
        }
        String strReplaceAll = strC.replaceAll(ContainerUtils.KEY_VALUE_DELIMITER, "");
        if (strReplaceAll.length() >= 5) {
            strReplaceAll = strReplaceAll.substring(0, 5);
        }
        int iA = (int) (a(strReplaceAll) % 10000);
        return iA < 0 ? iA * (-1) : iA;
    }

    public static boolean a(com.alipay.sdk.m.s.a aVar, Context context, List<a.b> list, boolean z2) {
        try {
            for (a.b bVar : list) {
                if (bVar != null) {
                    String str = bVar.f9543a;
                    if (EnvUtils.isSandBox() && f9817b.equals(str)) {
                        str = f9818c;
                    }
                    try {
                        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 128);
                        if (packageInfo != null) {
                            if (!z2) {
                                return true;
                            }
                            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.X, packageInfo.packageName + "|" + packageInfo.versionName);
                            return true;
                        }
                        continue;
                    } catch (PackageManager.NameNotFoundException unused) {
                        continue;
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9356e0, th);
            return false;
        }
    }

    public static boolean a(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        try {
            String str = packageInfo.versionName;
            String[] strArr = f9820e;
            if (!TextUtils.equals(str, strArr[0])) {
                if (!TextUtils.equals(str, strArr[1])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String a(int i2) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < i2; i3++) {
            int iNextInt = random.nextInt(3);
            if (iNextInt == 0) {
                sb.append(String.valueOf((char) Math.round((Math.random() * 25.0d) + 65.0d)));
            } else if (iNextInt == 1) {
                sb.append(String.valueOf((char) Math.round((Math.random() * 25.0d) + 97.0d)));
            } else if (iNextInt == 2) {
                sb.append(String.valueOf(new Random().nextInt(10)));
            }
        }
        return sb.toString();
    }

    public static boolean a(com.alipay.sdk.m.s.a aVar, String str, Activity activity) throws NumberFormatException {
        String strSubstring;
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        if (activity == null) {
            return false;
        }
        if (!str.toLowerCase().startsWith(com.alipay.sdk.m.l.a.f9420m.toLowerCase()) && !str.toLowerCase().startsWith(com.alipay.sdk.m.l.a.f9421n.toLowerCase())) {
            if (!TextUtils.equals(str, com.alipay.sdk.m.l.a.f9423p) && !TextUtils.equals(str, a("http", com.alipay.sdk.m.l.a.f9425r))) {
                if (!str.startsWith(com.alipay.sdk.m.l.a.f9422o)) {
                    return false;
                }
                try {
                    String strSubstring2 = str.substring(str.indexOf(com.alipay.sdk.m.l.a.f9422o) + 24);
                    int i2 = Integer.parseInt(strSubstring2.substring(strSubstring2.lastIndexOf(com.alipay.sdk.m.l.a.f9426s) + 10));
                    if (i2 != com.alipay.sdk.m.j.c.SUCCEEDED.b() && i2 != com.alipay.sdk.m.j.c.PAY_WAITTING.b()) {
                        com.alipay.sdk.m.j.c cVarB = com.alipay.sdk.m.j.c.b(com.alipay.sdk.m.j.c.FAILED.b());
                        com.alipay.sdk.m.j.b.a(com.alipay.sdk.m.j.b.a(cVarB.b(), cVarB.a(), ""));
                    } else {
                        if (com.alipay.sdk.m.l.a.f9430w) {
                            StringBuilder sb = new StringBuilder();
                            String strDecode = URLDecoder.decode(str);
                            String strDecode2 = URLDecoder.decode(strDecode);
                            String str2 = strDecode2.substring(strDecode2.indexOf(com.alipay.sdk.m.l.a.f9422o) + 24, strDecode2.lastIndexOf(com.alipay.sdk.m.l.a.f9426s)).split(com.alipay.sdk.m.l.a.f9428u)[0];
                            int iIndexOf = strDecode.indexOf(com.alipay.sdk.m.l.a.f9428u) + 12;
                            sb.append(str2);
                            sb.append(com.alipay.sdk.m.l.a.f9428u);
                            sb.append(strDecode.substring(iIndexOf, strDecode.indexOf("&", iIndexOf)));
                            sb.append(strDecode.substring(strDecode.indexOf("&", iIndexOf)));
                            strSubstring = sb.toString();
                        } else {
                            String strDecode3 = URLDecoder.decode(str);
                            strSubstring = strDecode3.substring(strDecode3.indexOf(com.alipay.sdk.m.l.a.f9422o) + 24, strDecode3.lastIndexOf(com.alipay.sdk.m.l.a.f9426s));
                        }
                        com.alipay.sdk.m.j.c cVarB2 = com.alipay.sdk.m.j.c.b(i2);
                        com.alipay.sdk.m.j.b.a(com.alipay.sdk.m.j.b.a(cVarB2.b(), cVarB2.a(), strSubstring));
                    }
                } catch (Exception unused) {
                    com.alipay.sdk.m.j.b.a(com.alipay.sdk.m.j.b.e());
                }
                activity.runOnUiThread(new a(activity));
                return true;
            }
            com.alipay.sdk.m.j.b.a(com.alipay.sdk.m.j.b.a());
            activity.finish();
            return true;
        }
        try {
            c cVarA = a(aVar, activity, com.alipay.sdk.m.j.a.f9316d);
            if (cVarA != null && !cVarA.a() && !cVarA.a(aVar)) {
                if (str.startsWith("intent://platformapi/startapp")) {
                    str = str.replaceFirst("intent://platformapi/startapp\\?", com.alipay.sdk.m.l.a.f9420m);
                }
                activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            }
        } catch (Throwable unused2) {
        }
        return true;
    }

    public static String a(com.alipay.sdk.m.s.a aVar, Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 128).versionName;
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9384v, th);
            return "";
        }
    }

    public static String a(String str, boolean z2) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            if (z2 && bArrDigest.length > 16) {
                byte[] bArr = new byte[16];
                System.arraycopy(bArrDigest, 0, bArr, 0, 16);
                return a(bArr);
            }
            return a(bArrDigest);
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b2 : bArr) {
            sb.append(Character.forDigit((b2 & 240) >> 4, 16));
            sb.append(Character.forDigit(b2 & 15, 16));
        }
        return sb.toString();
    }

    public static ActivityInfo a(Context context) {
        try {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                for (ActivityInfo activityInfo : context.getPackageManager().getPackageInfo(context.getPackageName(), 1).activities) {
                    if (TextUtils.equals(activityInfo.name, activity.getClass().getName())) {
                        return activityInfo;
                    }
                }
            }
            return null;
        } catch (Throwable th) {
            e.a(th);
            return null;
        }
    }

    public static String a(com.alipay.sdk.m.s.a aVar) {
        return c(aVar, "ro.build.fingerprint");
    }

    public static <T> T a(WeakReference<T> weakReference) {
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public static boolean a(com.alipay.sdk.m.s.a aVar, String str) {
        try {
            String host = new URL(str).getHost();
            if (host.endsWith(com.alipay.sdk.m.l.a.A)) {
                return true;
            }
            return host.endsWith(com.alipay.sdk.m.l.a.B);
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "ckUrlErr", th);
            return false;
        }
    }

    public static JSONObject a(Intent intent) {
        Bundle extras;
        JSONObject jSONObject = new JSONObject();
        if (intent != null && (extras = intent.getExtras()) != null) {
            for (String str : extras.keySet()) {
                try {
                    jSONObject.put(str, String.valueOf(extras.get(str)));
                } catch (Throwable unused) {
                }
            }
        }
        return jSONObject;
    }

    public static Map<String, String> a(JSONObject jSONObject) {
        HashMap map = new HashMap();
        if (jSONObject == null) {
            return map;
        }
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            try {
                map.put(next, jSONObject.optString(next));
            } catch (Throwable th) {
                e.a(th);
            }
        }
        return map;
    }

    public static boolean a(Object obj, Object... objArr) {
        if (objArr == null || objArr.length == 0) {
            return obj == null;
        }
        for (Object obj2 : objArr) {
            if ((obj == null && obj2 == null) || (obj != null && obj.equals(obj2))) {
                return true;
            }
        }
        return false;
    }

    public static boolean a(long j2, Runnable runnable, String str) {
        if (runnable == null) {
            return false;
        }
        ConditionVariable conditionVariable = new ConditionVariable();
        Thread thread = new Thread(new b(runnable, conditionVariable));
        if (!TextUtils.isEmpty(str)) {
            thread.setName(str);
        }
        thread.start();
        if (j2 <= 0) {
            conditionVariable.block();
            return true;
        }
        return conditionVariable.block(j2);
    }

    public static String a(String str, String str2) {
        return str + str2;
    }

    public static String a(com.alipay.sdk.m.s.a aVar, Context context) {
        try {
            String strA = j.a(aVar, context, "alipay_cashier_ap_fi", "");
            if (!TextUtils.isEmpty(strA)) {
                return strA;
            }
            try {
                j.b(aVar, context, "alipay_cashier_ap_fi", com.alipay.sdk.m.h.a.a("FU", System.currentTimeMillis(), new com.alipay.sdk.m.h.d(), (short) 0, new com.alipay.sdk.m.h.f()).a());
                String strA2 = j.a(aVar, context, "alipay_cashier_ap_fi", "");
                if (!TextUtils.isEmpty(strA2)) {
                    return strA2;
                }
                com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9364l, "e_regen_empty", "");
                return "";
            } catch (Exception e2) {
                com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9364l, "e_gen", e2.getClass().getSimpleName());
                return "";
            }
        } catch (Exception e3) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "e_gen_err", e3);
            return "";
        }
    }

    public static void a(String str, String str2, Context context, com.alipay.sdk.m.s.a aVar) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || b(aVar) || !com.alipay.sdk.m.m.a.z().t()) {
            return;
        }
        try {
            Intent intent = new Intent(com.alipay.sdk.m.l.b.f9445l);
            intent.putExtra("bizType", str);
            intent.putExtra("exName", str2);
            intent.setPackage(context.getPackageName());
            context.sendBroadcast(intent);
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "AppNotify", str + "|" + str2);
        } catch (Exception unused) {
        }
    }
}
