package c.a.d;

import java.security.PrivilegedAction;

/* loaded from: classes2.dex */
class k implements PrivilegedAction<String> {
    @Override // java.security.PrivilegedAction
    public String run() {
        return System.getProperty("line.separator");
    }
}
