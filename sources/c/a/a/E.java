package c.a.a;

import java.util.Enumeration;

/* loaded from: classes2.dex */
public class E implements Enumeration {

    /* renamed from: a, reason: collision with root package name */
    public int f7666a = 0;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ F f7667b;

    public E(F f2) {
        this.f7667b = f2;
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.f7666a < this.f7667b.f7668b.length;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        AbstractC0365o[] abstractC0365oArr = this.f7667b.f7668b;
        int i2 = this.f7666a;
        this.f7666a = i2 + 1;
        return abstractC0365oArr[i2];
    }
}
