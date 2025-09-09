package c.a.b.h;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes2.dex */
public class d implements ECConstants {

    /* renamed from: a, reason: collision with root package name */
    public ECCurve f8094a;

    /* renamed from: b, reason: collision with root package name */
    public byte[] f8095b;

    /* renamed from: c, reason: collision with root package name */
    public ECPoint f8096c;

    /* renamed from: d, reason: collision with root package name */
    public BigInteger f8097d;

    /* renamed from: e, reason: collision with root package name */
    public BigInteger f8098e;

    public d(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this(eCCurve, eCPoint, bigInteger, ECConstants.ONE, null);
    }

    public ECCurve a() {
        return this.f8094a;
    }

    public ECPoint b() {
        return this.f8096c;
    }

    public BigInteger c() {
        return this.f8098e;
    }

    public BigInteger d() {
        return this.f8097d;
    }

    public byte[] e() {
        return c.a.d.a.a(this.f8095b);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return this.f8094a.equals(dVar.f8094a) && this.f8096c.equals(dVar.f8096c) && this.f8097d.equals(dVar.f8097d) && this.f8098e.equals(dVar.f8098e);
    }

    public int hashCode() {
        return (((((this.f8094a.hashCode() * 37) ^ this.f8096c.hashCode()) * 37) ^ this.f8097d.hashCode()) * 37) ^ this.f8098e.hashCode();
    }

    public d(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCCurve, eCPoint, bigInteger, bigInteger2, null);
    }

    public d(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.f8094a = eCCurve;
        this.f8096c = eCPoint.normalize();
        this.f8097d = bigInteger;
        this.f8098e = bigInteger2;
        this.f8095b = bArr;
    }
}
