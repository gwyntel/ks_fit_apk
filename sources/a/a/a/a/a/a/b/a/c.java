package a.a.a.a.a.a.b.a;

import com.alibaba.ailabs.iot.bleadvertise.msg.provision.InexpensiveProvisionType;

/* loaded from: classes.dex */
public class c extends b {

    /* renamed from: c, reason: collision with root package name */
    public byte[] f1147c;

    /* renamed from: d, reason: collision with root package name */
    public byte[] f1148d;

    /* renamed from: e, reason: collision with root package name */
    public byte[] f1149e;

    public c(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        super(InexpensiveProvisionType.PROVISION_RANDOM, new byte[18]);
        byte[] bArr4 = new byte[2];
        this.f1147c = bArr4;
        if (bArr.length >= 2) {
            System.arraycopy(bArr, bArr.length - bArr4.length, bArr4, 0, bArr4.length);
        }
        byte[] bArr5 = new byte[8];
        this.f1148d = bArr5;
        System.arraycopy(bArr2, 0, bArr5, 0, bArr5.length);
        byte[] bArr6 = new byte[8];
        this.f1149e = bArr6;
        System.arraycopy(bArr3, 0, bArr6, 0, bArr6.length);
        byte[] bArr7 = this.f1147c;
        System.arraycopy(bArr7, 0, this.f1146b, 0, bArr7.length);
        byte[] bArr8 = this.f1148d;
        System.arraycopy(bArr8, 0, this.f1146b, this.f1147c.length, bArr8.length);
        byte[] bArr9 = this.f1149e;
        System.arraycopy(bArr9, 0, this.f1146b, this.f1147c.length + this.f1148d.length, bArr9.length);
    }
}
