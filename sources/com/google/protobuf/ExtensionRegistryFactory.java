package com.google.protobuf;

/* loaded from: classes2.dex */
final class ExtensionRegistryFactory {

    /* renamed from: a, reason: collision with root package name */
    static final Class f15221a = b();

    static boolean a(ExtensionRegistryLite extensionRegistryLite) {
        Class cls = f15221a;
        return cls != null && cls.isAssignableFrom(extensionRegistryLite.getClass());
    }

    static Class b() {
        try {
            ExtensionRegistry extensionRegistry = ExtensionRegistry.f15219b;
            return ExtensionRegistry.class;
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
        return extensionRegistryLiteInvokeSubclassFactory != null ? extensionRegistryLiteInvokeSubclassFactory : ExtensionRegistryLite.f15222a;
    }

    private static final ExtensionRegistryLite invokeSubclassFactory(String str) {
        Class cls = f15221a;
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
