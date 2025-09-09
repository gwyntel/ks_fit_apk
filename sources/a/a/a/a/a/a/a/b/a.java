package a.a.a.a.a.a.a.b;

import a.a.a.a.a.g;
import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;
import com.alibaba.ailabs.iot.bleadvertise.msg.control.InexpensiveControlCmdType;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f1137a;

    /* renamed from: b, reason: collision with root package name */
    public byte f1138b;

    /* renamed from: c, reason: collision with root package name */
    public byte f1139c = g.c().b();

    /* renamed from: d, reason: collision with root package name */
    public BleAdvertiseCallback<Boolean> f1140d;

    public a(byte[] bArr, byte b2, BleAdvertiseCallback<Boolean> bleAdvertiseCallback) {
        this.f1137a = bArr;
        this.f1138b = b2;
        this.f1140d = bleAdvertiseCallback;
    }

    public final byte a(int i2, int i3) {
        return (byte) (((i2 << 4) + i3) & 255);
    }

    public BleAdvertiseCallback<Boolean> b() {
        return this.f1140d;
    }

    public byte c() {
        return this.f1139c;
    }

    public byte d() {
        return this.f1138b;
    }

    public List<byte[]> a() {
        byte[] bArr = this.f1137a;
        if (bArr == null) {
            return null;
        }
        return a(bArr, this.f1139c);
    }

    public final List<byte[]> a(byte[] bArr, byte b2) {
        int length = (bArr.length / 18) + 1;
        ArrayList arrayList = new ArrayList(length);
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 * 18;
            int iMin = Math.min(bArr.length - i3, 18);
            byte[] bArr2 = new byte[iMin + 6];
            bArr2[0] = 14;
            bArr2[1] = InexpensiveControlCmdType.SEND_CTRL_CMD.getType();
            bArr2[2] = this.f1138b;
            bArr2[3] = b2;
            i2++;
            bArr2[4] = a(length, i2);
            bArr2[5] = (byte) (iMin & 255);
            System.arraycopy(bArr, i3, bArr2, 6, iMin);
            arrayList.add(bArr2);
        }
        return arrayList;
    }
}
