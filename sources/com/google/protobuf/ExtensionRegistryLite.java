package com.google.protobuf;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.protobuf.GeneratedMessageLite;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ExtensionRegistryLite {

    /* renamed from: a, reason: collision with root package name */
    static final ExtensionRegistryLite f15222a = new ExtensionRegistryLite(true);
    private static boolean doFullRuntimeInheritanceCheck = true;
    private static volatile boolean eagerlyParseMessageSets = false;
    private static volatile ExtensionRegistryLite emptyRegistry;
    private final Map<ObjectIntPair, GeneratedMessageLite.GeneratedExtension<?, ?>> extensionsByNumber;

    private static class ExtensionClassHolder {

        /* renamed from: a, reason: collision with root package name */
        static final Class f15223a = a();

        private ExtensionClassHolder() {
        }

        static Class a() {
            return Extension.class;
        }
    }

    private static final class ObjectIntPair {
        private final int number;
        private final Object object;

        ObjectIntPair(Object obj, int i2) {
            this.object = obj;
            this.number = i2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ObjectIntPair)) {
                return false;
            }
            ObjectIntPair objectIntPair = (ObjectIntPair) obj;
            return this.object == objectIntPair.object && this.number == objectIntPair.number;
        }

        public int hashCode() {
            return (System.identityHashCode(this.object) * 65535) + this.number;
        }
    }

    ExtensionRegistryLite() {
        this.extensionsByNumber = new HashMap();
    }

    public static ExtensionRegistryLite getEmptyRegistry() {
        if (!doFullRuntimeInheritanceCheck) {
            return f15222a;
        }
        ExtensionRegistryLite extensionRegistryLiteCreateEmpty = emptyRegistry;
        if (extensionRegistryLiteCreateEmpty == null) {
            synchronized (ExtensionRegistryLite.class) {
                try {
                    extensionRegistryLiteCreateEmpty = emptyRegistry;
                    if (extensionRegistryLiteCreateEmpty == null) {
                        extensionRegistryLiteCreateEmpty = ExtensionRegistryFactory.createEmpty();
                        emptyRegistry = extensionRegistryLiteCreateEmpty;
                    }
                } finally {
                }
            }
        }
        return extensionRegistryLiteCreateEmpty;
    }

    public static boolean isEagerlyParseMessageSets() {
        return eagerlyParseMessageSets;
    }

    public static ExtensionRegistryLite newInstance() {
        return doFullRuntimeInheritanceCheck ? ExtensionRegistryFactory.create() : new ExtensionRegistryLite();
    }

    public static void setEagerlyParseMessageSets(boolean z2) {
        eagerlyParseMessageSets = z2;
    }

    public final void add(GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension) {
        this.extensionsByNumber.put(new ObjectIntPair(generatedExtension.getContainingTypeDefaultInstance(), generatedExtension.getNumber()), generatedExtension);
    }

    public <ContainingType extends MessageLite> GeneratedMessageLite.GeneratedExtension<ContainingType, ?> findLiteExtensionByNumber(ContainingType containingtype, int i2) {
        return (GeneratedMessageLite.GeneratedExtension) this.extensionsByNumber.get(new ObjectIntPair(containingtype, i2));
    }

    public ExtensionRegistryLite getUnmodifiable() {
        return new ExtensionRegistryLite(this);
    }

    ExtensionRegistryLite(ExtensionRegistryLite extensionRegistryLite) {
        if (extensionRegistryLite == f15222a) {
            this.extensionsByNumber = Collections.emptyMap();
        } else {
            this.extensionsByNumber = Collections.unmodifiableMap(extensionRegistryLite.extensionsByNumber);
        }
    }

    public final void add(ExtensionLite<?, ?> extensionLite) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (GeneratedMessageLite.GeneratedExtension.class.isAssignableFrom(extensionLite.getClass())) {
            add((GeneratedMessageLite.GeneratedExtension<?, ?>) extensionLite);
        }
        if (doFullRuntimeInheritanceCheck && ExtensionRegistryFactory.a(this)) {
            try {
                getClass().getMethod(TmpConstant.GROUP_OP_ADD, ExtensionClassHolder.f15223a).invoke(this, extensionLite);
            } catch (Exception e2) {
                throw new IllegalArgumentException(String.format("Could not invoke ExtensionRegistry#add for %s", extensionLite), e2);
            }
        }
    }

    ExtensionRegistryLite(boolean z2) {
        this.extensionsByNumber = Collections.emptyMap();
    }
}
