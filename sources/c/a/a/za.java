package c.a.a;

import java.io.InputStream;

/* loaded from: classes2.dex */
public abstract class za extends InputStream {

    /* renamed from: a, reason: collision with root package name */
    public final InputStream f7996a;

    /* renamed from: b, reason: collision with root package name */
    public int f7997b;

    public za(InputStream inputStream, int i2) {
        this.f7996a = inputStream;
        this.f7997b = i2;
    }

    public int a() {
        return this.f7997b;
    }

    public void a(boolean z2) {
        InputStream inputStream = this.f7996a;
        if (inputStream instanceof wa) {
            ((wa) inputStream).b(z2);
        }
    }
}
