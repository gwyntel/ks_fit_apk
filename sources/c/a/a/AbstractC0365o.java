package c.a.a;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.spongycastle.util.encoders.Hex;

/* renamed from: c.a.a.o, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract class AbstractC0365o extends r implements InterfaceC0366p {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f7968a;

    public AbstractC0365o(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("string cannot be null");
        }
        this.f7968a = bArr;
    }

    public static AbstractC0365o getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0365o)) {
            return (AbstractC0365o) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(r.a((byte[]) obj));
            } catch (IOException e2) {
                throw new IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + e2.getMessage());
            }
        }
        if (obj instanceof InterfaceC0346f) {
            r aSN1Primitive = ((InterfaceC0346f) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0365o) {
                return (AbstractC0365o) aSN1Primitive;
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    @Override // c.a.a.r
    public boolean a(r rVar) {
        if (rVar instanceof AbstractC0365o) {
            return c.a.d.a.a(this.f7968a, ((AbstractC0365o) rVar).f7968a);
        }
        return false;
    }

    @Override // c.a.a.InterfaceC0366p
    public InputStream b() {
        return new ByteArrayInputStream(this.f7968a);
    }

    @Override // c.a.a.r
    public r e() {
        return new C0342ba(this.f7968a);
    }

    @Override // c.a.a.r
    public r f() {
        return new C0342ba(this.f7968a);
    }

    public byte[] g() {
        return this.f7968a;
    }

    @Override // c.a.a.AbstractC0363m
    public int hashCode() {
        return c.a.d.a.b(g());
    }

    public String toString() {
        return MqttTopic.MULTI_LEVEL_WILDCARD + c.a.d.l.b(Hex.encode(this.f7968a));
    }

    @Override // c.a.a.va
    public r a() {
        return toASN1Primitive();
    }
}
