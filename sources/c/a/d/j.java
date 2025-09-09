package c.a.d;

import java.security.AccessControlException;
import java.security.AccessController;

/* loaded from: classes2.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    public static final ThreadLocal f8123a = new ThreadLocal();

    public static boolean b(String str) {
        try {
            String strA = a(str);
            if (strA != null) {
                return "true".equals(l.a(strA));
            }
        } catch (AccessControlException unused) {
        }
        return false;
    }

    public static String a(String str) {
        return (String) AccessController.doPrivileged(new i(str));
    }
}
