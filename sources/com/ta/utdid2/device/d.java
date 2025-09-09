package com.ta.utdid2.device;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;
import com.ta.a.c.f;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static d f20041a;

    /* renamed from: a, reason: collision with other field name */
    private com.ta.utdid2.b.a.a f65a;

    /* renamed from: d, reason: collision with root package name */
    private String f20046d = null;
    private Context mContext;

    /* renamed from: b, reason: collision with root package name */
    private static Pattern f20042b = Pattern.compile("[^0-9a-zA-Z=/+]+");

    /* renamed from: a, reason: collision with other field name */
    private static final Object f64a = new Object();

    /* renamed from: f, reason: collision with root package name */
    private static final String f20044f = ".UTSystemConfig" + File.separator + "Global";

    /* renamed from: e, reason: collision with root package name */
    private static int f20043e = 0;

    /* renamed from: g, reason: collision with root package name */
    private static String f20045g = "";

    private d(Context context) {
        this.f65a = null;
        this.mContext = context;
        com.ta.a.a.a().a(context);
        this.f65a = new com.ta.utdid2.b.a.a(context, f20044f, "Alvin2");
    }

    public static d a(Context context) {
        if (context != null && f20041a == null) {
            synchronized (f64a) {
                try {
                    if (f20041a == null) {
                        f20041a = new d(context);
                    }
                } finally {
                }
            }
        }
        return f20041a;
    }

    private static String b(byte[] bArr) throws Exception {
        byte[] bArr2 = {69, 114, 116, -33, 125, -54, -31, 86, -11, 11, -78, -96, -17, -99, 64, Ascii.ETB, -95, -126, -82, -64, 113, 116, -16, -103, Constants.CMD_TYPE.CMD_STATUS_REPORT, -30, 9, -39, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, -80, -68, -78, -117, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Ascii.RS, -122, 64, -104, 74, -49, 106, 85, -38, -93};
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(com.ta.a.c.e.b(bArr2), mac.getAlgorithm()));
        return com.ta.utdid2.a.a.a.encodeToString(mac.doFinal(bArr), 2);
    }

    private void c(String str) {
        if (m80c(str)) {
            f20043e = 6;
            f.m77a("UTUtdid", "utdid type:", 6);
            this.f65a.a(str, f20043e);
        }
    }

    private String p() {
        String strQ = q();
        if (m80c(strQ)) {
            if (TextUtils.isEmpty(strQ) || !strQ.endsWith("\n")) {
                this.f20046d = strQ;
            } else {
                this.f20046d = strQ.substring(0, strQ.length() - 1);
            }
            return this.f20046d;
        }
        try {
            byte[] bArrA = a();
            if (bArrA == null) {
                return null;
            }
            String strEncodeToString = com.ta.utdid2.a.a.a.encodeToString(bArrA, 2);
            this.f20046d = strEncodeToString;
            f20043e = 6;
            c(strEncodeToString);
            return this.f20046d;
        } catch (Exception e2) {
            f.a("", e2, new Object[0]);
            return null;
        }
    }

    private String q() {
        String strK = this.f65a.k();
        if (!m80c(strK)) {
            f.m77a("UTUtdid", "read utdid is null");
            Log.d("UTUtdid", "read utdid is null");
            return null;
        }
        int iA = this.f65a.a();
        if (iA == 0) {
            f20043e = 1;
        } else {
            f20043e = iA;
        }
        f.m77a("UTUtdid", "get utdid from sp. type", Integer.valueOf(f20043e));
        return strK;
    }

    public static void setExtendFactor(String str) {
        f20045g = str;
    }

    static void setType(int i2) {
        f20043e = i2;
    }

    public synchronized String getValue() {
        String str = this.f20046d;
        if (str != null) {
            return str;
        }
        return p();
    }

    /* renamed from: c, reason: collision with other method in class */
    static boolean m80c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }
        if (24 == str.length()) {
            return !f20042b.matcher(str).find();
        }
        return false;
    }

    private byte[] a() throws Exception {
        String str;
        f.m77a("UTUtdid", "generateUtdid");
        Log.d("UTUtdid", "generateUtdid");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNextInt = new Random().nextInt();
        byte[] bytes = com.ta.utdid2.a.a.b.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = com.ta.utdid2.a.a.b.getBytes(iNextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            str = f20045g + com.ta.utdid2.a.a.c.b(this.mContext);
        } catch (Exception unused) {
            str = f20045g + new Random().nextInt();
        }
        byteArrayOutputStream.write(com.ta.utdid2.a.a.b.getBytes(com.ta.utdid2.a.a.d.a(str)), 0, 4);
        byteArrayOutputStream.write(com.ta.utdid2.a.a.b.getBytes(com.ta.utdid2.a.a.d.a(b(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }
}
