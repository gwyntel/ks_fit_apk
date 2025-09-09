package c.a.d;

import java.security.PrivilegedAction;
import java.util.Map;

/* loaded from: classes2.dex */
class i implements PrivilegedAction {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f8122a;

    public i(String str) {
        this.f8122a = str;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        Map map = (Map) j.f8123a.get();
        return map != null ? map.get(this.f8122a) : System.getProperty(this.f8122a);
    }
}
