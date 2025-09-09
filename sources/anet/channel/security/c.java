package anet.channel.security;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ISecurityFactory f6896a;

    public static ISecurityFactory a() {
        if (f6896a == null) {
            f6896a = new d();
        }
        return f6896a;
    }
}
