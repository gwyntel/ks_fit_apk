package a.a.a.a.a.a.b.a;

import a.a.a.a.a.h;
import com.alibaba.ailabs.iot.bleadvertise.msg.provision.InexpensiveProvisionType;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;

/* loaded from: classes.dex */
public class a extends b {

    /* renamed from: c, reason: collision with root package name */
    public final String f1143c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f1144d;

    public a(byte[] bArr, byte b2, byte[] bArr2, String str) throws UnsupportedEncodingException {
        super(InexpensiveProvisionType.PROVISIONING_ADD_APPKEY, new byte[19]);
        String str2 = "InexpensiveMesh" + a.class.getSimpleName();
        this.f1143c = str2;
        this.f1144d = true;
        int i2 = 0;
        try {
            byte[] bytes = (str + "SessionKey").getBytes("ASCII");
            StringBuilder sb = new StringBuilder();
            sb.append("confirmationBytes: ");
            sb.append(ConvertUtils.bytes2HexString(bytes));
            a.a.a.a.b.m.a.c(str2, sb.toString());
            byte[] bArrA = h.a(bytes);
            if (bArrA == null) {
                this.f1144d = false;
                return;
            }
            byte[] bArr3 = this.f1146b;
            System.arraycopy(bArrA, 0, bArr3, 0, bArr3.length);
            byte[] bArr4 = new byte[19];
            if (bArr.length >= 2) {
                System.arraycopy(bArr, 0, bArr4, 0, 2);
            }
            bArr4[2] = b2;
            System.arraycopy(bArr2, 0, bArr4, 3, bArr2.length);
            a.a.a.a.b.m.a.c(str2, "plainData = " + ConvertUtils.bytes2HexString(bArr4));
            if (19 != this.f1146b.length) {
                a.a.a.a.b.m.a.b(str2, "provisionData length is not equal provision Data");
                this.f1144d = false;
                return;
            }
            while (true) {
                byte[] bArr5 = this.f1146b;
                if (i2 >= bArr5.length) {
                    a.a.a.a.b.m.a.c(this.f1143c, "plainData = " + ConvertUtils.bytes2HexString(this.f1146b));
                    return;
                }
                bArr5[i2] = (byte) (bArr5[i2] ^ bArr4[i2]);
                i2++;
            }
        } catch (UnsupportedEncodingException | UnsupportedCharsetException e2) {
            e2.printStackTrace();
            this.f1144d = false;
        }
    }
}
