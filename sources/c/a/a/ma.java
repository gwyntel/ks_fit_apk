package c.a.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.spongycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class ma extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public static final char[] f7960a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: b, reason: collision with root package name */
    public final byte[] f7961b;

    public ma(byte[] bArr) {
        this.f7961b = c.a.d.a.a(bArr);
    }

    @Override // c.a.a.r
    public void a(C0367q c0367q) {
        c0367q.a(28, g());
    }

    @Override // c.a.a.r
    public int c() {
        return Ba.a(this.f7961b.length) + 1 + this.f7961b.length;
    }

    @Override // c.a.a.r
    public boolean d() {
        return false;
    }

    public byte[] g() {
        return c.a.d.a.a(this.f7961b);
    }

    public String h() {
        StringBuffer stringBuffer = new StringBuffer(MqttTopic.MULTI_LEVEL_WILDCARD);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new C0367q(byteArrayOutputStream).a((InterfaceC0346f) this);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            for (int i2 = 0; i2 != byteArray.length; i2++) {
                char[] cArr = f7960a;
                stringBuffer.append(cArr[(byteArray[i2] >>> 4) & 15]);
                stringBuffer.append(cArr[byteArray[i2] & 15]);
            }
            return stringBuffer.toString();
        } catch (IOException unused) {
            throw new ASN1ParsingException("internal error encoding BitString");
        }
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(this.f7961b);
    }

    public String toString() {
        return h();
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof ma) {
            return c.a.d.a.a(this.f7961b, ((ma) rVar).f7961b);
        }
        return false;
    }
}
