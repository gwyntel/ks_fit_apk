package com.alipay.sdk.m.n0;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: j, reason: collision with root package name */
    public static d f9579j;

    /* renamed from: a, reason: collision with root package name */
    public Context f9581a;

    /* renamed from: c, reason: collision with root package name */
    public e f9583c;

    /* renamed from: d, reason: collision with root package name */
    public String f9584d;

    /* renamed from: e, reason: collision with root package name */
    public String f9585e;

    /* renamed from: f, reason: collision with root package name */
    public com.alipay.sdk.m.m0.a f9586f;

    /* renamed from: g, reason: collision with root package name */
    public com.alipay.sdk.m.m0.a f9587g;

    /* renamed from: i, reason: collision with root package name */
    public static final Object f9578i = new Object();

    /* renamed from: k, reason: collision with root package name */
    public static final String f9580k = ".UTSystemConfig" + File.separator + "Global";

    /* renamed from: b, reason: collision with root package name */
    public String f9582b = null;

    /* renamed from: h, reason: collision with root package name */
    public Pattern f9588h = Pattern.compile("[^0-9a-zA-Z=/+]+");

    public d(Context context) {
        this.f9581a = null;
        this.f9583c = null;
        this.f9584d = "xx_utdid_key";
        this.f9585e = "xx_utdid_domain";
        this.f9586f = null;
        this.f9587g = null;
        this.f9581a = context;
        this.f9587g = new com.alipay.sdk.m.m0.a(context, f9580k, "Alvin2", false, true);
        this.f9586f = new com.alipay.sdk.m.m0.a(context, ".DataStorage", "ContextData", false, true);
        this.f9583c = new e();
        this.f9584d = String.format("K_%d", Integer.valueOf(com.alipay.sdk.m.l0.f.a(this.f9584d)));
        this.f9585e = String.format("D_%d", Integer.valueOf(com.alipay.sdk.m.l0.f.a(this.f9585e)));
    }

    public static d a(Context context) {
        if (context != null && f9579j == null) {
            synchronized (f9578i) {
                try {
                    if (f9579j == null) {
                        d dVar = new d(context);
                        f9579j = dVar;
                        dVar.d();
                    }
                } finally {
                }
            }
        }
        return f9579j;
    }

    private void b(String str) {
        com.alipay.sdk.m.m0.a aVar;
        if (a(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.length() != 24 || (aVar = this.f9587g) == null) {
                return;
            }
            aVar.a("UTDID2", str);
            this.f9587g.a();
        }
    }

    private void c(String str) {
        com.alipay.sdk.m.m0.a aVar;
        if (str == null || (aVar = this.f9586f) == null || str.equals(aVar.a(this.f9584d))) {
            return;
        }
        this.f9586f.a(this.f9584d, str);
        this.f9586f.a();
    }

    private void d() {
        boolean z2;
        com.alipay.sdk.m.m0.a aVar = this.f9587g;
        if (aVar != null) {
            if (com.alipay.sdk.m.l0.f.m56a(aVar.a("UTDID2"))) {
                String strA = this.f9587g.a("UTDID");
                if (!com.alipay.sdk.m.l0.f.m56a(strA)) {
                    b(strA);
                }
            }
            boolean z3 = true;
            if (com.alipay.sdk.m.l0.f.m56a(this.f9587g.a("DID"))) {
                z2 = false;
            } else {
                this.f9587g.b("DID");
                z2 = true;
            }
            if (!com.alipay.sdk.m.l0.f.m56a(this.f9587g.a("EI"))) {
                this.f9587g.b("EI");
                z2 = true;
            }
            if (com.alipay.sdk.m.l0.f.m56a(this.f9587g.a("SI"))) {
                z3 = z2;
            } else {
                this.f9587g.b("SI");
            }
            if (z3) {
                this.f9587g.a();
            }
        }
    }

    private byte[] e() throws Exception {
        String strA;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNextInt = new Random().nextInt();
        byte[] bArrA = com.alipay.sdk.m.l0.c.a(iCurrentTimeMillis);
        byte[] bArrA2 = com.alipay.sdk.m.l0.c.a(iNextInt);
        byteArrayOutputStream.write(bArrA, 0, 4);
        byteArrayOutputStream.write(bArrA2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            strA = com.alipay.sdk.m.l0.d.a(this.f9581a);
        } catch (Exception unused) {
            strA = "" + new Random().nextInt();
        }
        byteArrayOutputStream.write(com.alipay.sdk.m.l0.c.a(com.alipay.sdk.m.l0.f.a(strA)), 0, 4);
        byteArrayOutputStream.write(com.alipay.sdk.m.l0.c.a(com.alipay.sdk.m.l0.f.a(a(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    private String f() {
        com.alipay.sdk.m.m0.a aVar = this.f9587g;
        if (aVar == null) {
            return null;
        }
        String strA = aVar.a("UTDID2");
        if (com.alipay.sdk.m.l0.f.m56a(strA) || this.f9583c.a(strA) == null) {
            return null;
        }
        return strA;
    }

    public synchronized String c() {
        String strF = f();
        if (a(strF)) {
            c(this.f9583c.a(strF));
            this.f9582b = strF;
            return strF;
        }
        String strA = this.f9586f.a(this.f9584d);
        if (!com.alipay.sdk.m.l0.f.m56a(strA)) {
            String strA2 = new f().a(strA);
            if (!a(strA2)) {
                strA2 = this.f9583c.b(strA);
            }
            if (a(strA2) && !com.alipay.sdk.m.l0.f.m56a(strA2)) {
                this.f9582b = strA2;
                b(strA2);
                return this.f9582b;
            }
        }
        return null;
    }

    private boolean a(String str) {
        if (str != null) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (24 == str.length() && !this.f9588h.matcher(str).find()) {
                return true;
            }
        }
        return false;
    }

    public synchronized String b() {
        String str = this.f9582b;
        if (str != null) {
            return str;
        }
        return a();
    }

    public synchronized String a() {
        String strC = c();
        this.f9582b = strC;
        if (!TextUtils.isEmpty(strC)) {
            return this.f9582b;
        }
        try {
            byte[] bArrE = e();
            if (bArrE != null) {
                String strC2 = com.alipay.sdk.m.l0.b.c(bArrE, 2);
                this.f9582b = strC2;
                b(strC2);
                String strA = this.f9583c.a(bArrE);
                if (strA != null) {
                    c(strA);
                }
                return this.f9582b;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public static String a(byte[] bArr) throws Exception {
        byte[] bArr2 = {69, 114, 116, -33, 125, -54, -31, 86, -11, 11, -78, -96, -17, -99, 64, Ascii.ETB, -95, -126, -82, -64, 113, 116, -16, -103, Constants.CMD_TYPE.CMD_STATUS_REPORT, -30, 9, -39, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, -80, -68, -78, -117, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Ascii.RS, -122, 64, -104, 74, -49, 106, 85, -38, -93};
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(com.alipay.sdk.m.l0.e.a(bArr2), mac.getAlgorithm()));
        return com.alipay.sdk.m.l0.b.c(mac.doFinal(bArr), 2);
    }
}
