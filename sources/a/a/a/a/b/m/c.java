package a.a.a.a.b.m;

/* loaded from: classes.dex */
public class c {
    public static void a(String str, byte[] bArr) {
        if (bArr != null) {
            StringBuilder sb = new StringBuilder();
            for (byte b2 : bArr) {
                sb.append(b2 & 255);
                sb.append(" ");
            }
            a.c(str, sb.toString());
        }
    }

    public static byte[] b(String str) {
        String[] strArrSplit = str.split(":");
        if (strArrSplit.length != 6) {
            return null;
        }
        byte[] bArr = new byte[strArrSplit.length];
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            try {
                bArr[i2] = (byte) (Integer.parseInt(strArrSplit[i2], 16) & 255);
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        return bArr;
    }

    public static byte[] a(String str) {
        byte[] bArr = new byte[2];
        byte[] bArrB = b(str);
        if (bArrB == null) {
            a.b("FastProvisionUtil", "mac address is not assigned");
        } else {
            bArr[0] = bArrB[4];
            bArr[1] = bArrB[5];
        }
        return bArr;
    }
}
