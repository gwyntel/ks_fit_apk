package c.a.a;

import java.io.IOException;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
public class P extends AbstractC0339a {
    public P(boolean z2, int i2, byte[] bArr) {
        super(z2, i2, bArr);
    }

    @Override // c.a.a.AbstractC0339a, c.a.a.r
    public void a(C0367q c0367q) throws IOException {
        c0367q.a(this.f7692a ? 96 : 64, this.f7693b, this.f7694c);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        if (d()) {
            stringBuffer.append("CONSTRUCTED ");
        }
        stringBuffer.append("APPLICATION ");
        stringBuffer.append(Integer.toString(g()));
        stringBuffer.append("]");
        if (this.f7694c != null) {
            stringBuffer.append(" #");
            stringBuffer.append(Hex.toHexString(this.f7694c));
        } else {
            stringBuffer.append(" #null");
        }
        stringBuffer.append(" ");
        return stringBuffer.toString();
    }
}
