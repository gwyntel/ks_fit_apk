package a.a.a.a.b.m;

/* loaded from: classes.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    public static int f1586a;

    public static synchronized byte a() {
        int i2 = f1586a;
        if (i2 >= 255) {
            f1586a = 0;
            return (byte) 0;
        }
        f1586a = i2 + 1;
        return (byte) i2;
    }
}
