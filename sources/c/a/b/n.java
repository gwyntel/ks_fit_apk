package c.a.b;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;

/* loaded from: classes2.dex */
public abstract class n implements d, o {

    /* renamed from: a, reason: collision with root package name */
    public final d f8111a;

    public n(d dVar) {
        this.f8111a = dVar;
    }

    public abstract byte a(byte b2);

    public int a(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5 = i2 + i3;
        if (i5 > bArr.length) {
            throw new DataLengthException("input buffer too small");
        }
        if (i4 + i3 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        while (i2 < i5) {
            bArr2[i4] = a(bArr[i2]);
            i4++;
            i2++;
        }
        return i3;
    }
}
