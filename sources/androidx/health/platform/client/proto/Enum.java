package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.EnumValue;
import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.Internal;
import androidx.health.platform.client.proto.Option;
import androidx.health.platform.client.proto.SourceContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class Enum extends GeneratedMessageLite<Enum, Builder> implements EnumOrBuilder {
    private static final Enum DEFAULT_INSTANCE;
    public static final int ENUMVALUE_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    private static volatile Parser<Enum> PARSER = null;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 4;
    public static final int SYNTAX_FIELD_NUMBER = 5;
    private SourceContext sourceContext_;
    private int syntax_;
    private String name_ = "";
    private Internal.ProtobufList<EnumValue> enumvalue_ = GeneratedMessageLite.r();
    private Internal.ProtobufList<Option> options_ = GeneratedMessageLite.r();

    /* renamed from: androidx.health.platform.client.proto.Enum$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4426a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4426a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4426a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4426a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4426a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4426a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4426a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4426a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Enum, Builder> implements EnumOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        public Builder addAllEnumvalue(Iterable<? extends EnumValue> iterable) {
            d();
            ((Enum) this.f4444a).addAllEnumvalue(iterable);
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            d();
            ((Enum) this.f4444a).addAllOptions(iterable);
            return this;
        }

        public Builder addEnumvalue(EnumValue enumValue) {
            d();
            ((Enum) this.f4444a).addEnumvalue(enumValue);
            return this;
        }

        public Builder addOptions(Option option) {
            d();
            ((Enum) this.f4444a).addOptions(option);
            return this;
        }

        public Builder clearEnumvalue() {
            d();
            ((Enum) this.f4444a).clearEnumvalue();
            return this;
        }

        public Builder clearName() {
            d();
            ((Enum) this.f4444a).clearName();
            return this;
        }

        public Builder clearOptions() {
            d();
            ((Enum) this.f4444a).clearOptions();
            return this;
        }

        public Builder clearSourceContext() {
            d();
            ((Enum) this.f4444a).clearSourceContext();
            return this;
        }

        public Builder clearSyntax() {
            d();
            ((Enum) this.f4444a).clearSyntax();
            return this;
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public EnumValue getEnumvalue(int i2) {
            return ((Enum) this.f4444a).getEnumvalue(i2);
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public int getEnumvalueCount() {
            return ((Enum) this.f4444a).getEnumvalueCount();
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public List<EnumValue> getEnumvalueList() {
            return Collections.unmodifiableList(((Enum) this.f4444a).getEnumvalueList());
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public String getName() {
            return ((Enum) this.f4444a).getName();
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public ByteString getNameBytes() {
            return ((Enum) this.f4444a).getNameBytes();
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public Option getOptions(int i2) {
            return ((Enum) this.f4444a).getOptions(i2);
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public int getOptionsCount() {
            return ((Enum) this.f4444a).getOptionsCount();
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Enum) this.f4444a).getOptionsList());
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public SourceContext getSourceContext() {
            return ((Enum) this.f4444a).getSourceContext();
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public Syntax getSyntax() {
            return ((Enum) this.f4444a).getSyntax();
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public int getSyntaxValue() {
            return ((Enum) this.f4444a).getSyntaxValue();
        }

        @Override // androidx.health.platform.client.proto.EnumOrBuilder
        public boolean hasSourceContext() {
            return ((Enum) this.f4444a).hasSourceContext();
        }

        public Builder mergeSourceContext(SourceContext sourceContext) {
            d();
            ((Enum) this.f4444a).mergeSourceContext(sourceContext);
            return this;
        }

        public Builder removeEnumvalue(int i2) {
            d();
            ((Enum) this.f4444a).removeEnumvalue(i2);
            return this;
        }

        public Builder removeOptions(int i2) {
            d();
            ((Enum) this.f4444a).removeOptions(i2);
            return this;
        }

        public Builder setEnumvalue(int i2, EnumValue enumValue) {
            d();
            ((Enum) this.f4444a).setEnumvalue(i2, enumValue);
            return this;
        }

        public Builder setName(String str) {
            d();
            ((Enum) this.f4444a).setName(str);
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            d();
            ((Enum) this.f4444a).setNameBytes(byteString);
            return this;
        }

        public Builder setOptions(int i2, Option option) {
            d();
            ((Enum) this.f4444a).setOptions(i2, option);
            return this;
        }

        public Builder setSourceContext(SourceContext sourceContext) {
            d();
            ((Enum) this.f4444a).setSourceContext(sourceContext);
            return this;
        }

        public Builder setSyntax(Syntax syntax) {
            d();
            ((Enum) this.f4444a).setSyntax(syntax);
            return this;
        }

        public Builder setSyntaxValue(int i2) {
            d();
            ((Enum) this.f4444a).setSyntaxValue(i2);
            return this;
        }

        private Builder() {
            super(Enum.DEFAULT_INSTANCE);
        }

        public Builder addEnumvalue(int i2, EnumValue enumValue) {
            d();
            ((Enum) this.f4444a).addEnumvalue(i2, enumValue);
            return this;
        }

        public Builder addOptions(int i2, Option option) {
            d();
            ((Enum) this.f4444a).addOptions(i2, option);
            return this;
        }

        public Builder setEnumvalue(int i2, EnumValue.Builder builder) {
            d();
            ((Enum) this.f4444a).setEnumvalue(i2, builder.build());
            return this;
        }

        public Builder setOptions(int i2, Option.Builder builder) {
            d();
            ((Enum) this.f4444a).setOptions(i2, builder.build());
            return this;
        }

        public Builder setSourceContext(SourceContext.Builder builder) {
            d();
            ((Enum) this.f4444a).setSourceContext(builder.build());
            return this;
        }

        public Builder addEnumvalue(EnumValue.Builder builder) {
            d();
            ((Enum) this.f4444a).addEnumvalue(builder.build());
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            d();
            ((Enum) this.f4444a).addOptions(builder.build());
            return this;
        }

        public Builder addEnumvalue(int i2, EnumValue.Builder builder) {
            d();
            ((Enum) this.f4444a).addEnumvalue(i2, builder.build());
            return this;
        }

        public Builder addOptions(int i2, Option.Builder builder) {
            d();
            ((Enum) this.f4444a).addOptions(i2, builder.build());
            return this;
        }
    }

    static {
        Enum r02 = new Enum();
        DEFAULT_INSTANCE = r02;
        GeneratedMessageLite.T(Enum.class, r02);
    }

    private Enum() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllEnumvalue(Iterable<? extends EnumValue> iterable) {
        ensureEnumvalueIsMutable();
        AbstractMessageLite.a(iterable, this.enumvalue_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllOptions(Iterable<? extends Option> iterable) {
        ensureOptionsIsMutable();
        AbstractMessageLite.a(iterable, this.options_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEnumvalue(EnumValue enumValue) {
        enumValue.getClass();
        ensureEnumvalueIsMutable();
        this.enumvalue_.add(enumValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearEnumvalue() {
        this.enumvalue_ = GeneratedMessageLite.r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearName() {
        this.name_ = getDefaultInstance().getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearOptions() {
        this.options_ = GeneratedMessageLite.r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSourceContext() {
        this.sourceContext_ = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSyntax() {
        this.syntax_ = 0;
    }

    private void ensureEnumvalueIsMutable() {
        Internal.ProtobufList<EnumValue> protobufList = this.enumvalue_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.enumvalue_ = GeneratedMessageLite.C(protobufList);
    }

    private void ensureOptionsIsMutable() {
        Internal.ProtobufList<Option> protobufList = this.options_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.options_ = GeneratedMessageLite.C(protobufList);
    }

    public static Enum getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeSourceContext(SourceContext sourceContext) {
        sourceContext.getClass();
        SourceContext sourceContext2 = this.sourceContext_;
        if (sourceContext2 == null || sourceContext2 == SourceContext.getDefaultInstance()) {
            this.sourceContext_ = sourceContext;
        } else {
            this.sourceContext_ = SourceContext.newBuilder(this.sourceContext_).mergeFrom((SourceContext.Builder) sourceContext).buildPartial();
        }
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.m();
    }

    public static Enum parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Enum) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
    }

    public static Enum parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
    }

    public static Parser<Enum> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeEnumvalue(int i2) {
        ensureEnumvalueIsMutable();
        this.enumvalue_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeOptions(int i2) {
        ensureOptionsIsMutable();
        this.options_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEnumvalue(int i2, EnumValue enumValue) {
        enumValue.getClass();
        ensureEnumvalueIsMutable();
        this.enumvalue_.set(i2, enumValue);
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
    public void setOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.set(i2, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSourceContext(SourceContext sourceContext) {
        sourceContext.getClass();
        this.sourceContext_ = sourceContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSyntax(Syntax syntax) {
        this.syntax_ = syntax.getNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSyntaxValue(int i2) {
        this.syntax_ = i2;
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public EnumValue getEnumvalue(int i2) {
        return this.enumvalue_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public int getEnumvalueCount() {
        return this.enumvalue_.size();
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public List<EnumValue> getEnumvalueList() {
        return this.enumvalue_;
    }

    public EnumValueOrBuilder getEnumvalueOrBuilder(int i2) {
        return this.enumvalue_.get(i2);
    }

    public List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList() {
        return this.enumvalue_;
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public String getName() {
        return this.name_;
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public Option getOptions(int i2) {
        return this.options_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public int getOptionsCount() {
        return this.options_.size();
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public List<Option> getOptionsList() {
        return this.options_;
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.options_.get(i2);
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public SourceContext getSourceContext() {
        SourceContext sourceContext = this.sourceContext_;
        return sourceContext == null ? SourceContext.getDefaultInstance() : sourceContext;
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public Syntax getSyntax() {
        Syntax syntaxForNumber = Syntax.forNumber(this.syntax_);
        return syntaxForNumber == null ? Syntax.UNRECOGNIZED : syntaxForNumber;
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public int getSyntaxValue() {
        return this.syntax_;
    }

    @Override // androidx.health.platform.client.proto.EnumOrBuilder
    public boolean hasSourceContext() {
        return this.sourceContext_ != null;
    }

    @Override // androidx.health.platform.client.proto.GeneratedMessageLite
    protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.f4426a[methodToInvoke.ordinal()]) {
            case 1:
                return new Enum();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0002\u0000\u0001Ȉ\u0002\u001b\u0003\u001b\u0004\t\u0005\f", new Object[]{"name_", "enumvalue_", EnumValue.class, "options_", Option.class, "sourceContext_", "syntax_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<Enum> defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (Enum.class) {
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

    public static Builder newBuilder(Enum r1) {
        return (Builder) DEFAULT_INSTANCE.n(r1);
    }

    public static Enum parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Enum) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Enum parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static Enum parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEnumvalue(int i2, EnumValue enumValue) {
        enumValue.getClass();
        ensureEnumvalueIsMutable();
        this.enumvalue_.add(i2, enumValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(i2, option);
    }

    public static Enum parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Enum parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
    }

    public static Enum parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Enum parseFrom(InputStream inputStream) throws IOException {
        return (Enum) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
    }

    public static Enum parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Enum) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Enum parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Enum) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Enum parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Enum) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }
}
