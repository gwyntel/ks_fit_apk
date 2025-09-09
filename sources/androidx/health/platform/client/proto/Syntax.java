package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.Internal;

/* loaded from: classes.dex */
public enum Syntax implements Internal.EnumLite {
    SYNTAX_PROTO2(0),
    SYNTAX_PROTO3(1),
    UNRECOGNIZED(-1);

    public static final int SYNTAX_PROTO2_VALUE = 0;
    public static final int SYNTAX_PROTO3_VALUE = 1;
    private static final Internal.EnumLiteMap<Syntax> internalValueMap = new Internal.EnumLiteMap<Syntax>() { // from class: androidx.health.platform.client.proto.Syntax.1
        @Override // androidx.health.platform.client.proto.Internal.EnumLiteMap
        public Syntax findValueByNumber(int i2) {
            return Syntax.forNumber(i2);
        }
    };
    private final int value;

    private static final class SyntaxVerifier implements Internal.EnumVerifier {

        /* renamed from: a, reason: collision with root package name */
        static final Internal.EnumVerifier f4492a = new SyntaxVerifier();

        private SyntaxVerifier() {
        }

        @Override // androidx.health.platform.client.proto.Internal.EnumVerifier
        public boolean isInRange(int i2) {
            return Syntax.forNumber(i2) != null;
        }
    }

    Syntax(int i2) {
        this.value = i2;
    }

    public static Syntax forNumber(int i2) {
        if (i2 == 0) {
            return SYNTAX_PROTO2;
        }
        if (i2 != 1) {
            return null;
        }
        return SYNTAX_PROTO3;
    }

    public static Internal.EnumLiteMap<Syntax> internalGetValueMap() {
        return internalValueMap;
    }

    public static Internal.EnumVerifier internalGetVerifier() {
        return SyntaxVerifier.f4492a;
    }

    @Override // androidx.health.platform.client.proto.Internal.EnumLite
    public final int getNumber() {
        if (this != UNRECOGNIZED) {
            return this.value;
        }
        throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
    }

    @Deprecated
    public static Syntax valueOf(int i2) {
        return forNumber(i2);
    }
}
