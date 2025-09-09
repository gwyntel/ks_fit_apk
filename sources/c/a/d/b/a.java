package c.a.d.b;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static int f8119a = 4096;

    public static byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static int a(InputStream inputStream, byte[] bArr) {
        return a(inputStream, bArr, 0, bArr.length);
    }

    public static int a(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        int i4 = 0;
        while (i4 < i3) {
            int i5 = inputStream.read(bArr, i2 + i4, i3 - i4);
            if (i5 < 0) {
                break;
            }
            i4 += i5;
        }
        return i4;
    }

    public static void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        int i2 = f8119a;
        byte[] bArr = new byte[i2];
        while (true) {
            int i3 = inputStream.read(bArr, 0, i2);
            if (i3 < 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i3);
            }
        }
    }
}
