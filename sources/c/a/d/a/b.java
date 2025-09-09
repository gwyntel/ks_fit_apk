package c.a.d.a;

import com.alibaba.ailabs.iot.aisbase.Constants;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class b implements a {

    /* renamed from: a, reason: collision with root package name */
    public final byte[] f8116a = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 97, 98, 99, 100, 101, 102};

    /* renamed from: b, reason: collision with root package name */
    public final byte[] f8117b = new byte[128];

    public b() {
        a();
    }

    public static boolean a(char c2) {
        return c2 == '\n' || c2 == '\r' || c2 == '\t' || c2 == ' ';
    }

    @Override // c.a.d.a.a
    public int b(byte[] bArr, int i2, int i3, OutputStream outputStream) throws IOException {
        int i4 = i3 + i2;
        while (i4 > i2 && a((char) bArr[i4 - 1])) {
            i4--;
        }
        int i5 = 0;
        while (i2 < i4) {
            while (i2 < i4 && a((char) bArr[i2])) {
                i2++;
            }
            int i6 = i2 + 1;
            byte b2 = this.f8117b[bArr[i2]];
            while (i6 < i4 && a((char) bArr[i6])) {
                i6++;
            }
            int i7 = i6 + 1;
            byte b3 = this.f8117b[bArr[i6]];
            if ((b2 | b3) < 0) {
                throw new IOException("invalid characters encountered in Hex data");
            }
            outputStream.write((b2 << 4) | b3);
            i5++;
            i2 = i7;
        }
        return i5;
    }

    public void a() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.f8117b;
            if (i3 >= bArr.length) {
                break;
            }
            bArr[i3] = -1;
            i3++;
        }
        while (true) {
            byte[] bArr2 = this.f8116a;
            if (i2 >= bArr2.length) {
                byte[] bArr3 = this.f8117b;
                bArr3[65] = bArr3[97];
                bArr3[66] = bArr3[98];
                bArr3[67] = bArr3[99];
                bArr3[68] = bArr3[100];
                bArr3[69] = bArr3[101];
                bArr3[70] = bArr3[102];
                return;
            }
            this.f8117b[bArr2[i2]] = (byte) i2;
            i2++;
        }
    }

    @Override // c.a.d.a.a
    public int a(byte[] bArr, int i2, int i3, OutputStream outputStream) throws IOException {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            byte b2 = bArr[i4];
            outputStream.write(this.f8116a[(b2 & 255) >>> 4]);
            outputStream.write(this.f8116a[b2 & 15]);
        }
        return i3 * 2;
    }

    @Override // c.a.d.a.a
    public int a(String str, OutputStream outputStream) throws IOException {
        int length = str.length();
        while (length > 0 && a(str.charAt(length - 1))) {
            length--;
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            while (i2 < length && a(str.charAt(i2))) {
                i2++;
            }
            int i4 = i2 + 1;
            byte b2 = this.f8117b[str.charAt(i2)];
            while (i4 < length && a(str.charAt(i4))) {
                i4++;
            }
            int i5 = i4 + 1;
            byte b3 = this.f8117b[str.charAt(i4)];
            if ((b2 | b3) >= 0) {
                outputStream.write((b2 << 4) | b3);
                i3++;
                i2 = i5;
            } else {
                throw new IOException("invalid characters encountered in Hex string");
            }
        }
        return i3;
    }
}
