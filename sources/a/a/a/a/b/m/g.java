package a.a.a.a.b.m;

/* loaded from: classes.dex */
public class g {
    public static byte[] a(int i2) {
        return new byte[]{(byte) ((i2 >> 24) % 256), (byte) ((i2 >> 16) % 256), (byte) ((i2 >> 8) % 256), (byte) (i2 % 256)};
    }

    public static int a(String str) {
        if (str.length() <= 0) {
            return 0;
        }
        int i2 = 0;
        for (char c2 : str.toCharArray()) {
            i2 = (i2 * 31) + c2;
        }
        return i2;
    }
}
