package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.Internal;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class PermissionProto {

    /* renamed from: androidx.health.platform.client.proto.PermissionProto$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4474a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4474a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4474a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4474a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4474a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4474a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4474a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4474a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public enum AccessType implements Internal.EnumLite {
        ACCESS_TYPE_UNKNOWN(0),
        ACCESS_TYPE_READ(1),
        ACCESS_TYPE_WRITE(2);

        public static final int ACCESS_TYPE_READ_VALUE = 1;
        public static final int ACCESS_TYPE_UNKNOWN_VALUE = 0;
        public static final int ACCESS_TYPE_WRITE_VALUE = 2;
        private static final Internal.EnumLiteMap<AccessType> internalValueMap = new Internal.EnumLiteMap<AccessType>() { // from class: androidx.health.platform.client.proto.PermissionProto.AccessType.1
            @Override // androidx.health.platform.client.proto.Internal.EnumLiteMap
            public AccessType findValueByNumber(int i2) {
                return AccessType.forNumber(i2);
            }
        };
        private final int value;

        private static final class AccessTypeVerifier implements Internal.EnumVerifier {

            /* renamed from: a, reason: collision with root package name */
            static final Internal.EnumVerifier f4475a = new AccessTypeVerifier();

            private AccessTypeVerifier() {
            }

            @Override // androidx.health.platform.client.proto.Internal.EnumVerifier
            public boolean isInRange(int i2) {
                return AccessType.forNumber(i2) != null;
            }
        }

        AccessType(int i2) {
            this.value = i2;
        }

        public static AccessType forNumber(int i2) {
            if (i2 == 0) {
                return ACCESS_TYPE_UNKNOWN;
            }
            if (i2 == 1) {
                return ACCESS_TYPE_READ;
            }
            if (i2 != 2) {
                return null;
            }
            return ACCESS_TYPE_WRITE;
        }

        public static Internal.EnumLiteMap<AccessType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return AccessTypeVerifier.f4475a;
        }

        @Override // androidx.health.platform.client.proto.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static AccessType valueOf(int i2) {
            return forNumber(i2);
        }
    }

    public static final class Permission extends GeneratedMessageLite<Permission, Builder> implements PermissionOrBuilder {
        public static final int ACCESS_TYPE_FIELD_NUMBER = 2;
        public static final int DATA_TYPE_FIELD_NUMBER = 1;
        private static final Permission DEFAULT_INSTANCE;
        private static volatile Parser<Permission> PARSER = null;
        public static final int PERMISSION_FIELD_NUMBER = 3;
        private int accessType_;
        private int bitField0_;
        private DataProto.DataType dataType_;
        private String permission_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<Permission, Builder> implements PermissionOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearAccessType() {
                d();
                ((Permission) this.f4444a).clearAccessType();
                return this;
            }

            public Builder clearDataType() {
                d();
                ((Permission) this.f4444a).clearDataType();
                return this;
            }

            public Builder clearPermission() {
                d();
                ((Permission) this.f4444a).clearPermission();
                return this;
            }

            @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
            public AccessType getAccessType() {
                return ((Permission) this.f4444a).getAccessType();
            }

            @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
            public DataProto.DataType getDataType() {
                return ((Permission) this.f4444a).getDataType();
            }

            @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
            public String getPermission() {
                return ((Permission) this.f4444a).getPermission();
            }

            @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
            public ByteString getPermissionBytes() {
                return ((Permission) this.f4444a).getPermissionBytes();
            }

            @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
            public boolean hasAccessType() {
                return ((Permission) this.f4444a).hasAccessType();
            }

            @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
            public boolean hasDataType() {
                return ((Permission) this.f4444a).hasDataType();
            }

            @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
            public boolean hasPermission() {
                return ((Permission) this.f4444a).hasPermission();
            }

            public Builder mergeDataType(DataProto.DataType dataType) {
                d();
                ((Permission) this.f4444a).mergeDataType(dataType);
                return this;
            }

            public Builder setAccessType(AccessType accessType) {
                d();
                ((Permission) this.f4444a).setAccessType(accessType);
                return this;
            }

            public Builder setDataType(DataProto.DataType dataType) {
                d();
                ((Permission) this.f4444a).setDataType(dataType);
                return this;
            }

            public Builder setPermission(String str) {
                d();
                ((Permission) this.f4444a).setPermission(str);
                return this;
            }

            public Builder setPermissionBytes(ByteString byteString) {
                d();
                ((Permission) this.f4444a).setPermissionBytes(byteString);
                return this;
            }

            private Builder() {
                super(Permission.DEFAULT_INSTANCE);
            }

            public Builder setDataType(DataProto.DataType.Builder builder) {
                d();
                ((Permission) this.f4444a).setDataType(builder.build());
                return this;
            }
        }

        static {
            Permission permission = new Permission();
            DEFAULT_INSTANCE = permission;
            GeneratedMessageLite.T(Permission.class, permission);
        }

        private Permission() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAccessType() {
            this.bitField0_ &= -3;
            this.accessType_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataType() {
            this.dataType_ = null;
            this.bitField0_ &= -2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPermission() {
            this.bitField0_ &= -5;
            this.permission_ = getDefaultInstance().getPermission();
        }

        public static Permission getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDataType(DataProto.DataType dataType) {
            dataType.getClass();
            DataProto.DataType dataType2 = this.dataType_;
            if (dataType2 == null || dataType2 == DataProto.DataType.getDefaultInstance()) {
                this.dataType_ = dataType;
            } else {
                this.dataType_ = DataProto.DataType.newBuilder(this.dataType_).mergeFrom((DataProto.DataType.Builder) dataType).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static Permission parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Permission) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static Permission parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Permission) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<Permission> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAccessType(AccessType accessType) {
            this.accessType_ = accessType.getNumber();
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataType(DataProto.DataType dataType) {
            dataType.getClass();
            this.dataType_ = dataType;
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPermission(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.permission_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPermissionBytes(ByteString byteString) {
            this.permission_ = byteString.toStringUtf8();
            this.bitField0_ |= 4;
        }

        @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
        public AccessType getAccessType() {
            AccessType accessTypeForNumber = AccessType.forNumber(this.accessType_);
            return accessTypeForNumber == null ? AccessType.ACCESS_TYPE_UNKNOWN : accessTypeForNumber;
        }

        @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
        public DataProto.DataType getDataType() {
            DataProto.DataType dataType = this.dataType_;
            return dataType == null ? DataProto.DataType.getDefaultInstance() : dataType;
        }

        @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
        public String getPermission() {
            return this.permission_;
        }

        @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
        public ByteString getPermissionBytes() {
            return ByteString.copyFromUtf8(this.permission_);
        }

        @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
        public boolean hasAccessType() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
        public boolean hasDataType() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.PermissionProto.PermissionOrBuilder
        public boolean hasPermission() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4474a[methodToInvoke.ordinal()]) {
                case 1:
                    return new Permission();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဌ\u0001\u0003ဈ\u0002", new Object[]{"bitField0_", "dataType_", "accessType_", AccessType.internalGetVerifier(), "permission_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Permission> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (Permission.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(Permission permission) {
            return (Builder) DEFAULT_INSTANCE.n(permission);
        }

        public static Permission parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Permission) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Permission parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Permission) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static Permission parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Permission) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static Permission parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Permission) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Permission parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Permission) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static Permission parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Permission) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Permission parseFrom(InputStream inputStream) throws IOException {
            return (Permission) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static Permission parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Permission) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Permission parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Permission) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Permission parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Permission) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PermissionOrBuilder extends MessageLiteOrBuilder {
        AccessType getAccessType();

        DataProto.DataType getDataType();

        String getPermission();

        ByteString getPermissionBytes();

        boolean hasAccessType();

        boolean hasDataType();

        boolean hasPermission();
    }

    private PermissionProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
