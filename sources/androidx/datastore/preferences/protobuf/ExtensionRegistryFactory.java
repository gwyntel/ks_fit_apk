package androidx.datastore.preferences.protobuf;

/* loaded from: classes.dex */
final class ExtensionRegistryFactory {

    /* renamed from: a, reason: collision with root package name */
    static final Class f3916a = b();

    static boolean a(ExtensionRegistryLite extensionRegistryLite) {
        Class cls = f3916a;
        return cls != null && cls.isAssignableFrom(extensionRegistryLite.getClass());
    }

    static Class b() {
        try {
            return Class.forName("androidx.datastore.preferences.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static ExtensionRegistryLite create() {
        if (f3916a != null) {
            try {
                return invokeSubclassFactory("newInstance");
            } catch (Exception unused) {
            }
        }
        return new ExtensionRegistryLite();
    }

    public static ExtensionRegistryLite createEmpty() {
        if (f3916a != null) {
            try {
                return invokeSubclassFactory("getEmptyRegistry");
            } catch (Exception unused) {
            }
        }
        return ExtensionRegistryLite.f3917a;
    }

    private static final ExtensionRegistryLite invokeSubclassFactory(String str) throws Exception {
        return (ExtensionRegistryLite) f3916a.getDeclaredMethod(str, null).invoke(null, null);
    }
}
