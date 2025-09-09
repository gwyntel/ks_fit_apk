package com.hihonor.push.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.hihonor.push.framework.aidl.entity.RequestHeader;
import com.hihonor.push.sdk.bean.RemoteServiceBean;
import com.hihonor.push.sdk.common.data.ApiException;
import com.hihonor.push.sdk.internal.HonorPushErrorEnum;
import com.hihonor.push.sdk.ipc.HonorApiAvailability$PackageStates;
import com.xiaomi.mipush.sdk.Constants;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public class b {
    public static String a(byte[] bArr) {
        if (bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static int b(Context context) throws PackageManager.NameNotFoundException {
        HonorApiAvailability$PackageStates honorApiAvailability$PackageStates;
        if (context == null) {
            throw new NullPointerException("context must not be null.");
        }
        RemoteServiceBean remoteServiceBeanA = a(context);
        String packageName = remoteServiceBeanA.getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            honorApiAvailability$PackageStates = HonorApiAvailability$PackageStates.NOT_INSTALLED;
        } else {
            try {
                honorApiAvailability$PackageStates = context.getPackageManager().getApplicationInfo(packageName, 0).enabled ? HonorApiAvailability$PackageStates.ENABLED : HonorApiAvailability$PackageStates.DISABLED;
            } catch (PackageManager.NameNotFoundException unused) {
                honorApiAvailability$PackageStates = HonorApiAvailability$PackageStates.NOT_INSTALLED;
            }
        }
        if (HonorApiAvailability$PackageStates.NOT_INSTALLED.equals(honorApiAvailability$PackageStates)) {
            Log.i("HonorApiAvailability", "push service is not installed");
            return 8002008;
        }
        if (HonorApiAvailability$PackageStates.DISABLED.equals(honorApiAvailability$PackageStates)) {
            Log.i("HonorApiAvailability", "push service is disabled");
            return 8002007;
        }
        if (!TextUtils.equals(packageName, "android") || TextUtils.isEmpty(remoteServiceBeanA.getPackageSignature())) {
            return 8002006;
        }
        return HonorPushErrorEnum.SUCCESS.statusCode;
    }

    public static byte[] a(String str) {
        if (TextUtils.isEmpty(str)) {
            return new byte[0];
        }
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        int length = upperCase.length() / 2;
        byte[] bArr = new byte[length];
        try {
            byte[] bytes = upperCase.getBytes(StandardCharsets.UTF_8);
            for (int i2 = 0; i2 < length; i2++) {
                StringBuilder sb = new StringBuilder();
                sb.append("0x");
                int i3 = i2 * 2;
                byte[] bArr2 = {bytes[i3]};
                Charset charset = StandardCharsets.UTF_8;
                sb.append(new String(bArr2, charset));
                bArr[i2] = (byte) (((byte) (Byte.decode(sb.toString()).byteValue() << 4)) ^ Byte.decode("0x" + new String(new byte[]{bytes[i3 + 1]}, charset)).byteValue());
            }
        } catch (NumberFormatException e2) {
            e2.getMessage();
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr, int i2) {
        if (bArr == null) {
            return bArr;
        }
        for (int i3 = 0; i3 < bArr.length; i3++) {
            if (i2 < 0) {
                bArr[i3] = (byte) (bArr[i3] << (-i2));
            } else {
                bArr[i3] = (byte) (bArr[i3] >> i2);
            }
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = null;
        if (bArr != null) {
            int length = bArr.length;
            if (bArr2.length != length) {
                return null;
            }
            bArr3 = new byte[length];
            for (int i2 = 0; i2 < length; i2++) {
                bArr3[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
            }
        }
        return bArr3;
    }

    public static void a(Closeable closeable) throws IOException {
        try {
            closeable.close();
        } catch (Exception e2) {
            c.a("DeflateUtil", "close", e2);
        }
    }

    public static <TResult> a1 a(Callable<TResult> callable) {
        ExecutorService executorService = o0.f15524c.f15526b;
        n0 n0Var = new n0();
        try {
            executorService.execute(new z0(n0Var, callable));
        } catch (Exception e2) {
            n0Var.a(e2);
        }
        return n0Var.f15522a;
    }

    public static void a(Handler handler) {
        if (Looper.myLooper() != handler.getLooper()) {
            throw new IllegalStateException("Must be called on the handler thread");
        }
    }

    public static <TResult> TResult a(a1 a1Var) throws ExecutionException, InterruptedException {
        boolean z2;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            synchronized (a1Var.f15461a) {
                z2 = a1Var.f15462b;
            }
            if (!z2) {
                y0 y0Var = new y0();
                o0 o0Var = o0.f15524c;
                a1Var.a(new x0(o0Var.f15525a, y0Var)).a(new v0(o0Var.f15525a, y0Var)).a(new r0(o0Var.f15525a, y0Var));
                y0Var.f15568a.await();
            }
            if (a1Var.e()) {
                return (TResult) a1Var.c();
            }
            throw new ExecutionException(a1Var.b());
        }
        throw new IllegalStateException("await must not be called on the UI thread");
    }

    public static RequestHeader a() throws PackageManager.NameNotFoundException, ApiException {
        String string;
        Context contextA = l.f15511e.a();
        String strValueOf = null;
        try {
            Object obj = contextA.getPackageManager().getApplicationInfo(contextA.getPackageName(), 128).metaData.get("com.hihonor.push.app_id");
            if (obj != null) {
                strValueOf = String.valueOf(obj);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            c.a("ConfigUtils", "getPushAppId", e2);
        }
        if (!TextUtils.isEmpty(strValueOf)) {
            String strA = a(contextA, contextA.getPackageName());
            if (!TextUtils.isEmpty(strA)) {
                RequestHeader requestHeader = new RequestHeader();
                requestHeader.setPackageName(contextA.getPackageName());
                requestHeader.setAppId(strValueOf);
                requestHeader.setCertificateFingerprint(strA);
                d dVar = d.f15472b;
                requestHeader.setPushToken(dVar.b(contextA));
                synchronized (dVar) {
                    dVar.a(contextA);
                    SharedPreferences sharedPreferences = d.f15471a.f15502a;
                    string = sharedPreferences != null ? sharedPreferences.getString("key_aaid", "") : "";
                    if (TextUtils.isEmpty(string)) {
                        string = UUID.randomUUID().toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
                        d.f15471a.a("key_aaid", string);
                    }
                }
                requestHeader.setAAID(string);
                requestHeader.setSdkVersion(70041301);
                return requestHeader;
            }
            c.a("checkPushConfig Parameter is missing.");
            throw HonorPushErrorEnum.ERROR_CERT_FINGERPRINT_EMPTY.toApiException();
        }
        c.a("checkPushConfig Parameter is missing");
        throw HonorPushErrorEnum.ERROR_NO_APPID.toApiException();
    }

    public static ApiException a(Exception exc) {
        if (exc.getCause() instanceof ApiException) {
            return (ApiException) exc.getCause();
        }
        if (exc instanceof ApiException) {
            return (ApiException) exc;
        }
        return new ApiException(-1, exc.getMessage());
    }

    public static RemoteServiceBean a(Context context) throws PackageManager.NameNotFoundException {
        RemoteServiceBean remoteServiceBean = new RemoteServiceBean();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("android", "com.hihonor.android.pushagentproxy.HiPushService"));
        List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(intent, 128);
        if (listQueryIntentServices.size() > 0) {
            Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
            if (it.hasNext()) {
                ResolveInfo next = it.next();
                String str = next.serviceInfo.applicationInfo.packageName;
                String strA = a(context, str);
                remoteServiceBean.setPackageName(str);
                remoteServiceBean.setPackageServiceName(next.serviceInfo.name);
                remoteServiceBean.setPackageSignature(strA);
            }
        }
        return remoteServiceBean;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0039  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0039 -> B:18:0x003a). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r11, java.lang.String r12) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            android.content.pm.PackageManager r11 = r11.getPackageManager()
            r1 = 0
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            r3 = 30
            if (r2 < r3) goto L2e
            r2 = 134217728(0x8000000, float:3.85186E-34)
            android.content.pm.PackageInfo r11 = r11.getPackageInfo(r12, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            if (r11 == 0) goto L39
            android.content.pm.SigningInfo r11 = androidx.core.content.pm.a.a(r11)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            if (r11 == 0) goto L39
            boolean r12 = d0.a.a(r11)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            if (r12 == 0) goto L29
            android.content.pm.Signature[] r11 = d0.b.a(r11)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            goto L3a
        L29:
            android.content.pm.Signature[] r11 = d0.c.a(r11)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            goto L3a
        L2e:
            r2 = 64
            android.content.pm.PackageInfo r11 = r11.getPackageInfo(r12, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            if (r11 == 0) goto L39
            android.content.pm.Signature[] r11 = r11.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            goto L3a
        L39:
            r11 = r1
        L3a:
            r12 = 0
            if (r11 == 0) goto L8c
            int r2 = r11.length
            if (r2 <= 0) goto L8c
            int r2 = r11.length
            r3 = r12
        L42:
            if (r3 >= r2) goto L8c
            r4 = r11[r3]
            byte[] r4 = r4.toByteArray()
            java.lang.String r5 = "SHA256"
            java.security.MessageDigest r5 = java.security.MessageDigest.getInstance(r5)     // Catch: java.security.NoSuchAlgorithmException -> L82
            byte[] r4 = r5.digest(r4)     // Catch: java.security.NoSuchAlgorithmException -> L82
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.security.NoSuchAlgorithmException -> L82
            r5.<init>()     // Catch: java.security.NoSuchAlgorithmException -> L82
            int r6 = r4.length     // Catch: java.security.NoSuchAlgorithmException -> L82
            r7 = r12
        L5b:
            if (r7 >= r6) goto L7d
            r8 = r4[r7]     // Catch: java.security.NoSuchAlgorithmException -> L82
            r8 = r8 & 255(0xff, float:3.57E-43)
            java.lang.String r8 = java.lang.Integer.toHexString(r8)     // Catch: java.security.NoSuchAlgorithmException -> L82
            java.util.Locale r9 = java.util.Locale.ENGLISH     // Catch: java.security.NoSuchAlgorithmException -> L82
            java.lang.String r8 = r8.toUpperCase(r9)     // Catch: java.security.NoSuchAlgorithmException -> L82
            int r9 = r8.length()     // Catch: java.security.NoSuchAlgorithmException -> L82
            r10 = 1
            if (r9 != r10) goto L77
            java.lang.String r9 = "0"
            r5.append(r9)     // Catch: java.security.NoSuchAlgorithmException -> L82
        L77:
            r5.append(r8)     // Catch: java.security.NoSuchAlgorithmException -> L82
            int r7 = r7 + 1
            goto L5b
        L7d:
            java.lang.String r4 = r5.toString()     // Catch: java.security.NoSuchAlgorithmException -> L82
            goto L83
        L82:
            r4 = r1
        L83:
            if (r4 == 0) goto L89
            r0.add(r4)
            goto L8c
        L89:
            int r3 = r3 + 1
            goto L42
        L8c:
            boolean r11 = r0.isEmpty()
            if (r11 == 0) goto L93
            goto L9a
        L93:
            java.lang.Object r11 = r0.get(r12)
            r1 = r11
            java.lang.String r1 = (java.lang.String) r1
        L9a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hihonor.push.sdk.b.a(android.content.Context, java.lang.String):java.lang.String");
    }
}
