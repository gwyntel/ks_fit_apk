package androidx.health.platform.client.proto;

/* loaded from: classes.dex */
final class ExtensionRegistryFactory {

    /* renamed from: a, reason: collision with root package name */
    static final Class f4429a = b();

    static boolean a(ExtensionRegistryLite extensionRegistryLite) {
        Class cls = f4429a;
        return cls != null && cls.isAssignableFrom(extensionRegistryLite.getClass());
    }

    static Class b() {
        try {
            return Class.forName("androidx.health.platform.client.proto.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static ExtensionRegistryLite create() {
        ExtensionRegistryLite extensionRegistryLiteInvokeSubclassFactory = invokeSubclassFactory("newInstance");
        return extensionRegistryLiteInvokeSubclassFactory != null ? extensionRegistryLiteInvokeSubclassFactory : new ExtensionRegistryLite();
    }

    public static ExtensionRegistryLite createEmpty() {
        ExtensionRegistryLite extensionRegistryLiteInvokeSubclassFactory = invokeSubclassFactory("getEmptyRegistry");
        return extensionRegistryLiteInvokeSubclassFactory != null ? extensionRegistryLiteInvokeSubclassFactory : ExtensionRegistryLite.f4430a;
    }

    private static final ExtensionRegistryLite invokeSubclassFactory(String str) {
        Class cls = f4429a;
        if (cls == null) {
            return null;
        }
        try {
            return (ExtensionRegistryLite) cls.getDeclaredMethod(str, null).invoke(null, null);
        } catch (Exception unused) {
            return null;
        }
    }
}
