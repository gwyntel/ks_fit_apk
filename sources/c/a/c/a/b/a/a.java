package c.a.c.a.b.a;

import java.security.PrivilegedAction;

/* loaded from: classes2.dex */
class a implements PrivilegedAction {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f8112a;

    public a(String str) {
        this.f8112a = str;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        try {
            return Class.forName(this.f8112a);
        } catch (Exception unused) {
            return null;
        }
    }
}
