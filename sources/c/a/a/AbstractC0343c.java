package c.a.a;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.spongycastle.asn1.ASN1ParsingException;

/* renamed from: c.a.a.c, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract class AbstractC0343c extends r implements InterfaceC0373x {

    /* renamed from: a, reason: collision with root package name */
    public static final char[] f7730a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: b, reason: collision with root package name */
    public final byte[] f7731b;

    /* renamed from: c, reason: collision with root package name */
    public final int f7732c;

    public AbstractC0343c(byte[] bArr, int i2) {
        if (bArr == null) {
            throw new NullPointerException("data cannot be null");
        }
        if (bArr.length == 0 && i2 != 0) {
            throw new IllegalArgumentException("zero length data with non-zero pad bits");
        }
        if (i2 > 7 || i2 < 0) {
            throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        }
        this.f7731b = c.a.d.a.a(bArr);
        this.f7732c = i2;
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (!(rVar instanceof AbstractC0343c)) {
            return false;
        }
        AbstractC0343c abstractC0343c = (AbstractC0343c) rVar;
        return this.f7732c == abstractC0343c.f7732c && c.a.d.a.a(g(), abstractC0343c.g());
    }

    @Override // c.a.a.r
    public r e() {
        return new S(this.f7731b, this.f7732c);
    }

    @Override // c.a.a.r
    public r f() {
        return new pa(this.f7731b, this.f7732c);
    }

    public byte[] g() {
        return a(this.f7731b, this.f7732c);
    }

    public int h() {
        return this.f7732c;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return this.f7732c ^ c.a.d.a.b(g());
    }

    public String i() {
        StringBuffer stringBuffer = new StringBuffer(MqttTopic.MULTI_LEVEL_WILDCARD);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new C0367q(byteArrayOutputStream).a((InterfaceC0346f) this);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            for (int i2 = 0; i2 != byteArray.length; i2++) {
                char[] cArr = f7730a;
                stringBuffer.append(cArr[(byteArray[i2] >>> 4) & 15]);
                stringBuffer.append(cArr[byteArray[i2] & 15]);
            }
            return stringBuffer.toString();
        } catch (IOException e2) {
            throw new ASN1ParsingException("Internal error encoding BitString: " + e2.getMessage(), e2);
        }
    }

    public String toString() {
        return i();
    }

    public static byte[] a(byte[] bArr, int i2) {
        byte[] bArrA = c.a.d.a.a(bArr);
        if (i2 > 0) {
            int length = bArr.length - 1;
            bArrA[length] = (byte) ((255 << i2) & bArrA[length]);
        }
        return bArrA;
    }

    public static AbstractC0343c a(int i2, InputStream inputStream) throws IOException {
        if (i2 >= 1) {
            int i3 = inputStream.read();
            int i4 = i2 - 1;
            byte[] bArr = new byte[i4];
            if (i4 != 0) {
                if (c.a.d.b.a.a(inputStream, bArr) != i4) {
                    throw new EOFException("EOF encountered in middle of BIT STRING");
                }
                if (i3 > 0 && i3 < 8) {
                    byte b2 = bArr[i2 - 2];
                    if (b2 != ((byte) ((255 << i3) & b2))) {
                        return new pa(bArr, i3);
                    }
                }
            }
            return new S(bArr, i3);
        }
        throw new IllegalArgumentException("truncated BIT STRING detected");
    }
}
