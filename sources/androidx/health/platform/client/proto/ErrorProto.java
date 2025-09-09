package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.Internal;
import androidx.health.platform.client.proto.PermissionProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class ErrorProto {

    /* renamed from: androidx.health.platform.client.proto.ErrorProto$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4428a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4428a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4428a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4428a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4428a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4428a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4428a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4428a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class ErrorStatus extends GeneratedMessageLite<ErrorStatus, Builder> implements ErrorStatusOrBuilder {
        public static final int CODE_FIELD_NUMBER = 1;
        private static final ErrorStatus DEFAULT_INSTANCE;
        public static final int MESSAGE_FIELD_NUMBER = 2;
        private static volatile Parser<ErrorStatus> PARSER = null;
        public static final int PERMISSION_FIELD_NUMBER = 3;
        private int bitField0_;
        private int code_;
        private String message_ = "";
        private Internal.ProtobufList<PermissionProto.Permission> permission_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<ErrorStatus, Builder> implements ErrorStatusOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllPermission(Iterable<? extends PermissionProto.Permission> iterable) {
                d();
                ((ErrorStatus) this.f4444a).addAllPermission(iterable);
                return this;
            }

            public Builder addPermission(PermissionProto.Permission permission) {
                d();
                ((ErrorStatus) this.f4444a).addPermission(permission);
                return this;
            }

            public Builder clearCode() {
                d();
                ((ErrorStatus) this.f4444a).clearCode();
                return this;
            }

            public Builder clearMessage() {
                d();
                ((ErrorStatus) this.f4444a).clearMessage();
                return this;
            }

            public Builder clearPermission() {
                d();
                ((ErrorStatus) this.f4444a).clearPermission();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
            public int getCode() {
                return ((ErrorStatus) this.f4444a).getCode();
            }

            @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
            public String getMessage() {
                return ((ErrorStatus) this.f4444a).getMessage();
            }

            @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
            public ByteString getMessageBytes() {
                return ((ErrorStatus) this.f4444a).getMessageBytes();
            }

            @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
            public PermissionProto.Permission getPermission(int i2) {
                return ((ErrorStatus) this.f4444a).getPermission(i2);
            }

            @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
            public int getPermissionCount() {
                return ((ErrorStatus) this.f4444a).getPermissionCount();
            }

            @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
            public List<PermissionProto.Permission> getPermissionList() {
                return Collections.unmodifiableList(((ErrorStatus) this.f4444a).getPermissionList());
            }

            @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
            public boolean hasCode() {
                return ((ErrorStatus) this.f4444a).hasCode();
            }

            @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
            public boolean hasMessage() {
                return ((ErrorStatus) this.f4444a).hasMessage();
            }

            public Builder removePermission(int i2) {
                d();
                ((ErrorStatus) this.f4444a).removePermission(i2);
                return this;
            }

            public Builder setCode(int i2) {
                d();
                ((ErrorStatus) this.f4444a).setCode(i2);
                return this;
            }

            public Builder setMessage(String str) {
                d();
                ((ErrorStatus) this.f4444a).setMessage(str);
                return this;
            }

            public Builder setMessageBytes(ByteString byteString) {
                d();
                ((ErrorStatus) this.f4444a).setMessageBytes(byteString);
                return this;
            }

            public Builder setPermission(int i2, PermissionProto.Permission permission) {
                d();
                ((ErrorStatus) this.f4444a).setPermission(i2, permission);
                return this;
            }

            private Builder() {
                super(ErrorStatus.DEFAULT_INSTANCE);
            }

            public Builder addPermission(int i2, PermissionProto.Permission permission) {
                d();
                ((ErrorStatus) this.f4444a).addPermission(i2, permission);
                return this;
            }

            public Builder setPermission(int i2, PermissionProto.Permission.Builder builder) {
                d();
                ((ErrorStatus) this.f4444a).setPermission(i2, builder.build());
                return this;
            }

            public Builder addPermission(PermissionProto.Permission.Builder builder) {
                d();
                ((ErrorStatus) this.f4444a).addPermission(builder.build());
                return this;
            }

            public Builder addPermission(int i2, PermissionProto.Permission.Builder builder) {
                d();
                ((ErrorStatus) this.f4444a).addPermission(i2, builder.build());
                return this;
            }
        }

        static {
            ErrorStatus errorStatus = new ErrorStatus();
            DEFAULT_INSTANCE = errorStatus;
            GeneratedMessageLite.T(ErrorStatus.class, errorStatus);
        }

        private ErrorStatus() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllPermission(Iterable<? extends PermissionProto.Permission> iterable) {
            ensurePermissionIsMutable();
            AbstractMessageLite.a(iterable, this.permission_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addPermission(PermissionProto.Permission permission) {
            permission.getClass();
            ensurePermissionIsMutable();
            this.permission_.add(permission);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCode() {
            this.bitField0_ &= -2;
            this.code_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMessage() {
            this.bitField0_ &= -3;
            this.message_ = getDefaultInstance().getMessage();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPermission() {
            this.permission_ = GeneratedMessageLite.r();
        }

        private void ensurePermissionIsMutable() {
            Internal.ProtobufList<PermissionProto.Permission> protobufList = this.permission_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.permission_ = GeneratedMessageLite.C(protobufList);
        }

        public static ErrorStatus getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static ErrorStatus parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ErrorStatus) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ErrorStatus parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ErrorStatus) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ErrorStatus> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removePermission(int i2) {
            ensurePermissionIsMutable();
            this.permission_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCode(int i2) {
            this.bitField0_ |= 1;
            this.code_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMessage(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.message_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMessageBytes(ByteString byteString) {
            this.message_ = byteString.toStringUtf8();
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPermission(int i2, PermissionProto.Permission permission) {
            permission.getClass();
            ensurePermissionIsMutable();
            this.permission_.set(i2, permission);
        }

        @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
        public int getCode() {
            return this.code_;
        }

        @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
        public String getMessage() {
            return this.message_;
        }

        @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
        public ByteString getMessageBytes() {
            return ByteString.copyFromUtf8(this.message_);
        }

        @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
        public PermissionProto.Permission getPermission(int i2) {
            return this.permission_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
        public int getPermissionCount() {
            return this.permission_.size();
        }

        @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
        public List<PermissionProto.Permission> getPermissionList() {
            return this.permission_;
        }

        public PermissionProto.PermissionOrBuilder getPermissionOrBuilder(int i2) {
            return this.permission_.get(i2);
        }

        public List<? extends PermissionProto.PermissionOrBuilder> getPermissionOrBuilderList() {
            return this.permission_;
        }

        @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
        public boolean hasCode() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.ErrorProto.ErrorStatusOrBuilder
        public boolean hasMessage() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4428a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ErrorStatus();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001င\u0000\u0002ဈ\u0001\u0003\u001b", new Object[]{"bitField0_", "code_", "message_", "permission_", PermissionProto.Permission.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ErrorStatus> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ErrorStatus.class) {
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

        public static Builder newBuilder(ErrorStatus errorStatus) {
            return (Builder) DEFAULT_INSTANCE.n(errorStatus);
        }

        public static ErrorStatus parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ErrorStatus) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ErrorStatus parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ErrorStatus) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ErrorStatus parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ErrorStatus) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addPermission(int i2, PermissionProto.Permission permission) {
            permission.getClass();
            ensurePermissionIsMutable();
            this.permission_.add(i2, permission);
        }

        public static ErrorStatus parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ErrorStatus) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ErrorStatus parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ErrorStatus) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ErrorStatus parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ErrorStatus) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ErrorStatus parseFrom(InputStream inputStream) throws IOException {
            return (ErrorStatus) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ErrorStatus parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ErrorStatus) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ErrorStatus parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ErrorStatus) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ErrorStatus parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ErrorStatus) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ErrorStatusOrBuilder extends MessageLiteOrBuilder {
        int getCode();

        String getMessage();

        ByteString getMessageBytes();

        PermissionProto.Permission getPermission(int i2);

        int getPermissionCount();

        List<PermissionProto.Permission> getPermissionList();

        boolean hasCode();

        boolean hasMessage();
    }

    private ErrorProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
