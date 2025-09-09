package a.a.a.a.b.m;

/* loaded from: classes.dex */
public class j {
    public static boolean a(String str) throws ClassNotFoundException {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException e2) {
            a.b("ReflectUtils", "hasClss=" + e2);
            return false;
        } catch (Exception e3) {
            a.b("ReflectUtils", "hasClssEx=" + e3);
            return false;
        }
    }
}
