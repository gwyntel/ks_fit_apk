package com.umeng.ut.b.b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;
import com.taobao.accs.common.Constants;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.android.agoo.common.Config;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static d f23012a;

    /* renamed from: a, reason: collision with other field name */
    private Context f75a;

    /* renamed from: c, reason: collision with root package name */
    private String f23014c = null;

    /* renamed from: d, reason: collision with root package name */
    private long f23015d = -1;

    /* renamed from: b, reason: collision with root package name */
    private static Pattern f23013b = Pattern.compile("[^0-9a-zA-Z=/+]+");

    /* renamed from: a, reason: collision with other field name */
    private static final Object f74a = new Object();

    private d(Context context) {
        this.f75a = context.getApplicationContext();
        com.umeng.ut.a.a.a().a(this.f75a);
    }

    public static d a(Context context) {
        if (context != null && f23012a == null) {
            synchronized (f74a) {
                try {
                    if (f23012a == null) {
                        f23012a = new d(context);
                    }
                } finally {
                }
            }
        }
        return f23012a;
    }

    private boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }
        if (24 == str.length()) {
            return !f23013b.matcher(str).find();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0018 A[PHI: r2
      0x0018: PHI (r2v3 long) = (r2v0 long), (r2v1 long) binds: [B:6:0x0016, B:9:0x001f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private long c() {
        /*
            r5 = this;
            java.lang.String r0 = "um_push_ut"
            android.content.SharedPreferences r0 = r5.a(r0)     // Catch: java.lang.Throwable -> L10
            java.lang.String r1 = "v_i"
            r2 = 604800(0x93a80, double:2.98811E-318)
            long r0 = r0.getLong(r1, r2)     // Catch: java.lang.Throwable -> L10
            goto L12
        L10:
            r0 = 0
        L12:
            r2 = 600(0x258, double:2.964E-321)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto L1a
        L18:
            r0 = r2
            goto L22
        L1a:
            r2 = 7776000(0x76a700, double:3.8418545E-317)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 <= 0) goto L22
            goto L18
        L22:
            r2 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 * r2
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.ut.b.b.d.c():long");
    }

    @SuppressLint({"ApplySharedPref"})
    private void e() {
        try {
            a("um_push_ut").edit().remove("v_r").commit();
        } catch (Throwable unused) {
        }
    }

    private synchronized String j() {
        try {
            boolean z2 = m88c() || !com.umeng.ut.a.c.a.a(this.f75a);
            if (z2) {
                String strL = l();
                this.f23014c = strL;
                if (!com.umeng.ut.b.a.a.d.isEmpty(strL) && b(this.f23014c)) {
                    return this.f23014c;
                }
                String strK = k();
                this.f23014c = strK;
                if (!com.umeng.ut.b.a.a.d.isEmpty(strK) && b(this.f23014c)) {
                    a(this.f23014c, false);
                    return this.f23014c;
                }
            }
            try {
                byte[] bArrA = a();
                if (bArrA != null) {
                    String strA = com.umeng.ut.b.a.a.a.a(bArrA, 2);
                    this.f23014c = strA;
                    a(strA, true);
                    if (!z2) {
                        e();
                    }
                    return this.f23014c;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        } catch (Throwable th) {
            throw th;
        }
    }

    private String k() {
        try {
            SharedPreferences sharedPreferencesA = a(Config.PREFERENCES);
            String string = sharedPreferencesA.getString("deviceId", null);
            if (string == null || string.length() <= 0) {
                return null;
            }
            String string2 = sharedPreferencesA.getString("utdid", null);
            if (string2 != null) {
                try {
                    if (string2.length() == 0) {
                    }
                } catch (Throwable unused) {
                }
                return string2;
            }
            return a(Constants.SP_FILE_NAME).getString("utdid", null);
        } catch (Throwable unused2) {
            return null;
        }
    }

    private String l() {
        try {
            return a("um_push_ut").getString("d_id", null);
        } catch (Throwable unused) {
            return null;
        }
    }

    boolean d() {
        try {
            return a("um_push_ut").getBoolean("t_f", false);
        } catch (Throwable unused) {
            return false;
        }
    }

    @SuppressLint({"ApplySharedPref"})
    void f() {
        try {
            a("um_push_ut").edit().remove("t_id").remove("t_f").commit();
        } catch (Throwable unused) {
        }
    }

    synchronized String getValue() {
        String str = this.f23014c;
        if (str != null) {
            return str;
        }
        return j();
    }

    String m() {
        String string;
        try {
            string = a("um_push_ut").getString("t_id", null);
        } catch (Throwable unused) {
            string = null;
        }
        if (string != null && !"-1".equals(string) && string.length() >= 2 && string.length() <= 128) {
            return string;
        }
        return null;
    }

    /* renamed from: c, reason: collision with other method in class */
    private boolean m88c() {
        try {
            return a("um_push_ut").getBoolean("v_r", true);
        } catch (Throwable unused) {
            return true;
        }
    }

    private void a(String str, boolean z2) {
        if (b(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.length() == 24) {
                b(str, z2);
            }
        }
    }

    @SuppressLint({"ApplySharedPref"})
    public boolean b() {
        try {
            SharedPreferences sharedPreferencesA = a("um_push_ut");
            if (this.f23015d == -1) {
                this.f23015d = sharedPreferencesA.getLong("v_ts", 0L);
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (Math.abs(jCurrentTimeMillis - this.f23015d) >= c()) {
                this.f23015d = jCurrentTimeMillis;
                sharedPreferencesA.edit().putLong("v_ts", jCurrentTimeMillis).commit();
                com.umeng.ut.a.c.e.m85a("UTUtdid", "req valid");
                return true;
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    private byte[] a() throws Exception {
        String strF;
        com.umeng.ut.a.c.e.m85a("UTUtdid", "generate UTDid");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNextInt = new Random().nextInt();
        byte[] bytes = com.umeng.ut.b.a.a.b.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = com.umeng.ut.b.a.a.b.getBytes(iNextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            strF = com.umeng.ut.b.a.a.c.f();
        } catch (Exception unused) {
            strF = "" + new Random().nextInt();
        }
        byteArrayOutputStream.write(com.umeng.ut.b.a.a.b.getBytes(com.umeng.ut.b.a.a.d.a(strF)), 0, 4);
        byteArrayOutputStream.write(com.umeng.ut.b.a.a.b.getBytes(com.umeng.ut.b.a.a.d.a(a(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    @SuppressLint({"ApplySharedPref"})
    private void b(String str, boolean z2) {
        try {
            a("um_push_ut").edit().putString("d_id", str).putBoolean("t_f", z2).commit();
        } catch (Throwable unused) {
        }
    }

    private static String a(byte[] bArr) throws Exception {
        byte[] bArr2 = {69, 114, 116, -33, 125, -54, -31, 86, -11, 11, -78, -96, -17, -99, 64, Ascii.ETB, -95, -126, -82, -64, 113, 116, -16, -103, Constants.CMD_TYPE.CMD_STATUS_REPORT, -30, 9, -39, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, -80, -68, -78, -117, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Ascii.RS, -122, 64, -104, 74, -49, 106, 85, -38, -93};
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(com.umeng.ut.a.c.d.b(bArr2), mac.getAlgorithm()));
        return com.umeng.ut.b.a.a.a.a(mac.doFinal(bArr), 2);
    }

    @SuppressLint({"ApplySharedPref"})
    void a(String str, long j2) {
        boolean z2;
        try {
            SharedPreferences.Editor editorEdit = a("um_push_ut").edit();
            boolean z3 = true;
            if (j2 > 0) {
                editorEdit.putLong("v_i", j2);
                editorEdit.putLong("v_ts", System.currentTimeMillis());
                z2 = true;
            } else {
                z2 = false;
            }
            if (str == null || "-1".equals(str) || str.length() < 2 || str.length() > 128) {
                z3 = z2;
            } else {
                editorEdit.putString("t_id", str);
                editorEdit.putBoolean("v_r", false);
            }
            if (z3) {
                editorEdit.commit();
            }
        } catch (Throwable unused) {
        }
    }

    private SharedPreferences a(String str) {
        return this.f75a.getSharedPreferences(str, 0);
    }
}
