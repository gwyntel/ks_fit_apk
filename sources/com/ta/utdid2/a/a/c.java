package com.ta.utdid2.a.a;

import android.content.Context;
import android.text.TextUtils;
import java.util.Random;

/* loaded from: classes4.dex */
public class c {
    public static String b(Context context) {
        String strI = d.b(null) ? i() : null;
        return d.b(strI) ? h() : strI;
    }

    public static String h() {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNanoTime = (int) System.nanoTime();
        int iNextInt = new Random().nextInt();
        int iNextInt2 = new Random().nextInt();
        byte[] bytes = b.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = b.getBytes(iNanoTime);
        byte[] bytes3 = b.getBytes(iNextInt);
        byte[] bytes4 = b.getBytes(iNextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return a.encodeToString(bArr, 2);
    }

    private static String i() throws ClassNotFoundException {
        String str = e.get("ro.aliyun.clouduuid", "");
        if (TextUtils.isEmpty(str)) {
            str = e.get("ro.sys.aliyun.clouduuid", "");
        }
        return TextUtils.isEmpty(str) ? j() : str;
    }

    private static String j() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", null).invoke(null, null);
        } catch (Exception unused) {
            return "";
        }
    }
}
