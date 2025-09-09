package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.Internal;
import androidx.health.platform.client.proto.Option;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class EnumValue extends GeneratedMessageLite<EnumValue, Builder> implements EnumValueOrBuilder {
    private static final EnumValue DEFAULT_INSTANCE;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int NUMBER_FIELD_NUMBER = 2;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    private static volatile Parser<EnumValue> PARSER;
    private int number_;
    private String name_ = "";
    private Internal.ProtobufList<Option> options_ = GeneratedMessageLite.r();

    /* renamed from: androidx.health.platform.client.proto.EnumValue$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4427a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4427a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4427a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4427a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4427a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4427a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4427a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4427a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<EnumValue, Builder> implements EnumValueOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            d();
            ((EnumValue) this.f4444a).addAllOptions(iterable);
            return this;
        }

        public Builder addOptions(Option option) {
            d();
            ((EnumValue) this.f4444a).addOptions(option);
            return this;
        }

        public Builder clearName() {
            d();
            ((EnumValue) this.f4444a).clearName();
            return this;
        }

        public Builder clearNumber() {
            d();
            ((EnumValue) this.f4444a).clearNumber();
            return this;
        }

        public Builder clearOptions() {
            d();
            ((EnumValue) this.f4444a).clearOptions();
            return this;
        }

        @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
        public String getName() {
            return ((EnumValue) this.f4444a).getName();
        }

        @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
        public ByteString getNameBytes() {
            return ((EnumValue) this.f4444a).getNameBytes();
        }

        @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
        public int getNumber() {
            return ((EnumValue) this.f4444a).getNumber();
        }

        @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
        public Option getOptions(int i2) {
            return ((EnumValue) this.f4444a).getOptions(i2);
        }

        @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
        public int getOptionsCount() {
            return ((EnumValue) this.f4444a).getOptionsCount();
        }

        @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((EnumValue) this.f4444a).getOptionsList());
        }

        public Builder removeOptions(int i2) {
            d();
            ((EnumValue) this.f4444a).removeOptions(i2);
            return this;
        }

        public Builder setName(String str) {
            d();
            ((EnumValue) this.f4444a).setName(str);
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            d();
            ((EnumValue) this.f4444a).setNameBytes(byteString);
            return this;
        }

        public Builder setNumber(int i2) {
            d();
            ((EnumValue) this.f4444a).setNumber(i2);
            return this;
        }

        public Builder setOptions(int i2, Option option) {
            d();
            ((EnumValue) this.f4444a).setOptions(i2, option);
            return this;
        }

        private Builder() {
            super(EnumValue.DEFAULT_INSTANCE);
        }

        public Builder addOptions(int i2, Option option) {
            d();
            ((EnumValue) this.f4444a).addOptions(i2, option);
            return this;
        }

        public Builder setOptions(int i2, Option.Builder builder) {
            d();
            ((EnumValue) this.f4444a).setOptions(i2, builder.build());
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            d();
            ((EnumValue) this.f4444a).addOptions(builder.build());
            return this;
        }

        public Builder addOptions(int i2, Option.Builder builder) {
            d();
            ((EnumValue) this.f4444a).addOptions(i2, builder.build());
            return this;
        }
    }

    static {
        EnumValue enumValue = new EnumValue();
        DEFAULT_INSTANCE = enumValue;
        GeneratedMessageLite.T(EnumValue.class, enumValue);
    }

    private EnumValue() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllOptions(Iterable<? extends Option> iterable) {
        ensureOptionsIsMutable();
        AbstractMessageLite.a(iterable, this.options_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearName() {
        this.name_ = getDefaultInstance().getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearNumber() {
        this.number_ = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearOptions() {
        this.options_ = GeneratedMessageLite.r();
    }

    private void ensureOptionsIsMutable() {
        Internal.ProtobufList<Option> protobufList = this.options_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.options_ = GeneratedMessageLite.C(protobufList);
    }

    public static EnumValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.m();
    }

    public static EnumValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (EnumValue) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
    }

    public static EnumValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
    }

    public static Parser<EnumValue> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeOptions(int i2) {
        ensureOptionsIsMutable();
        this.options_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setName(String str) {
        str.getClass();
        this.name_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNameBytes(ByteString byteString) {
        AbstractMessageLite.b(byteString);
        this.name_ = byteString.toStringUtf8();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNumber(int i2) {
        this.number_ = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.set(i2, option);
    }

    @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
    public String getName() {
        return this.name_;
    }

    @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
    public int getNumber() {
        return this.number_;
    }

    @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
    public Option getOptions(int i2) {
        return this.options_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
    public int getOptionsCount() {
        return this.options_.size();
    }

    @Override // androidx.health.platform.client.proto.EnumValueOrBuilder
    public List<Option> getOptionsList() {
        return this.options_;
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.options_.get(i2);
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    @Override // androidx.health.platform.client.proto.GeneratedMessageLite
    protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.f4427a[methodToInvoke.ordinal()]) {
            case 1:
                return new EnumValue();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0001\u0000\u0001Ȉ\u0002\u0004\u0003\u001b", new Object[]{"name_", "number_", "options_", Option.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<EnumValue> defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (EnumValue.class) {
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

    public static Builder newBuilder(EnumValue enumValue) {
        return (Builder) DEFAULT_INSTANCE.n(enumValue);
    }

    public static EnumValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (EnumValue) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static EnumValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static EnumValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(i2, option);
    }

    public static EnumValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static EnumValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
    }

    public static EnumValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static EnumValue parseFrom(InputStream inputStream) throws IOException {
        return (EnumValue) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
    }

    public static EnumValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (EnumValue) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static EnumValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (EnumValue) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
    }

    public static EnumValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (EnumValue) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }
}
