package a.a.a.a.a.a.b.a;

import a.a.a.a.a.h;
import com.alibaba.ailabs.iot.bleadvertise.msg.provision.InexpensiveProvisionType;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;

/* loaded from: classes.dex */
public class d extends b {

    /* renamed from: c, reason: collision with root package name */
    public boolean f1150c;

    public d(byte[] bArr, byte b2, byte[] bArr2, byte b3, byte[] bArr3, String str) throws UnsupportedEncodingException {
        super(InexpensiveProvisionType.PROVISION_DATA, new byte[22]);
        this.f1150c = true;
        a.a.a.a.b.m.a.c("SendProvisionDataMsg", "macBytes = " + ConvertUtils.bytes2HexString(bArr) + ", networkKey = " + ConvertUtils.bytes2HexString(bArr2) + ", unicastAddress = " + ConvertUtils.bytes2HexString(bArr3) + ", confirmationCloud = " + str);
        int i2 = 0;
        if (bArr.length >= 2) {
            System.arraycopy(bArr, 0, this.f1146b, 0, 2);
        }
        try {
            byte[] bytes = (str + "SessionKey").getBytes("ASCII");
            StringBuilder sb = new StringBuilder();
            sb.append("confirmationBytes: ");
            sb.append(ConvertUtils.bytes2HexString(bytes));
            a.a.a.a.b.m.a.c("SendProvisionDataMsg", sb.toString());
            byte[] bArrA = h.a(bytes);
            if (bArrA == null) {
                this.f1150c = false;
                return;
            }
            a.a.a.a.b.m.a.c("SendProvisionDataMsg", "sessionKey: " + ConvertUtils.bytes2HexString(bArrA));
            byte[] bArr4 = this.f1146b;
            System.arraycopy(bArrA, 0, bArr4, 0, bArr4.length);
            byte[] bArrA2 = a(bArr, b2, bArr2, b3, bArr3);
            a.a.a.a.b.m.a.c("SendProvisionDataMsg", "plainData = " + ConvertUtils.bytes2HexString(bArrA2));
            if (bArrA2 == null || bArrA2.length != this.f1146b.length) {
                a.a.a.a.b.m.a.b("SendProvisionDataMsg", "provisionData length is not equal provision Data");
                this.f1150c = false;
                return;
            }
            while (true) {
                byte[] bArr5 = this.f1146b;
                if (i2 >= bArr5.length) {
                    a.a.a.a.b.m.a.c("SendProvisionDataMsg", "plainData = " + ConvertUtils.bytes2HexString(this.f1146b));
                    return;
                }
                bArr5[i2] = (byte) (bArr5[i2] ^ bArrA2[i2]);
                i2++;
            }
        } catch (UnsupportedEncodingException | UnsupportedCharsetException e2) {
            e2.printStackTrace();
            this.f1150c = false;
        }
    }

    public final byte[] a(byte[] bArr, byte b2, byte[] bArr2, byte b3, byte[] bArr3) {
        if (bArr2 == null || bArr2.length != 16 || bArr3 == null || bArr3.length != 2) {
            return null;
        }
        byte[] bArr4 = new byte[22];
        System.arraycopy(bArr, bArr.length - 2, bArr4, 0, 2);
        bArr4[2] = b2;
        System.arraycopy(bArr2, 0, bArr4, 3, bArr2.length);
        bArr4[19] = b3;
        System.arraycopy(bArr3, 0, bArr4, 20, bArr3.length);
        return bArr4;
    }

    public boolean b() {
        return this.f1150c;
    }
}
