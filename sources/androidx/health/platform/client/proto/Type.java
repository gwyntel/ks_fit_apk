package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.Field;
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
public final class Type extends GeneratedMessageLite<Type, Builder> implements TypeOrBuilder {
    private static final Type DEFAULT_INSTANCE;
    public static final int FIELDS_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int ONEOFS_FIELD_NUMBER = 3;
    public static final int OPTIONS_FIELD_NUMBER = 4;
    private static volatile Parser<Type> PARSER = null;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
    public static final int SYNTAX_FIELD_NUMBER = 6;
    private SourceContext sourceContext_;
    private int syntax_;
    private String name_ = "";
    private Internal.ProtobufList<Field> fields_ = GeneratedMessageLite.r();
    private Internal.ProtobufList<String> oneofs_ = GeneratedMessageLite.r();
    private Internal.ProtobufList<Option> options_ = GeneratedMessageLite.r();

    /* renamed from: androidx.health.platform.client.proto.Type$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4497a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4497a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4497a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4497a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4497a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4497a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4497a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4497a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Type, Builder> implements TypeOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        public Builder addAllFields(Iterable<? extends Field> iterable) {
            d();
            ((Type) this.f4444a).addAllFields(iterable);
            return this;
        }

        public Builder addAllOneofs(Iterable<String> iterable) {
            d();
            ((Type) this.f4444a).addAllOneofs(iterable);
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            d();
            ((Type) this.f4444a).addAllOptions(iterable);
            return this;
        }

        public Builder addFields(Field field) {
            d();
            ((Type) this.f4444a).addFields(field);
            return this;
        }

        public Builder addOneofs(String str) {
            d();
            ((Type) this.f4444a).addOneofs(str);
            return this;
        }

        public Builder addOneofsBytes(ByteString byteString) {
            d();
            ((Type) this.f4444a).addOneofsBytes(byteString);
            return this;
        }

        public Builder addOptions(Option option) {
            d();
            ((Type) this.f4444a).addOptions(option);
            return this;
        }

        public Builder clearFields() {
            d();
            ((Type) this.f4444a).clearFields();
            return this;
        }

        public Builder clearName() {
            d();
            ((Type) this.f4444a).clearName();
            return this;
        }

        public Builder clearOneofs() {
            d();
            ((Type) this.f4444a).clearOneofs();
            return this;
        }

        public Builder clearOptions() {
            d();
            ((Type) this.f4444a).clearOptions();
            return this;
        }

        public Builder clearSourceContext() {
            d();
            ((Type) this.f4444a).clearSourceContext();
            return this;
        }

        public Builder clearSyntax() {
            d();
            ((Type) this.f4444a).clearSyntax();
            return this;
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public Field getFields(int i2) {
            return ((Type) this.f4444a).getFields(i2);
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public int getFieldsCount() {
            return ((Type) this.f4444a).getFieldsCount();
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public List<Field> getFieldsList() {
            return Collections.unmodifiableList(((Type) this.f4444a).getFieldsList());
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public String getName() {
            return ((Type) this.f4444a).getName();
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public ByteString getNameBytes() {
            return ((Type) this.f4444a).getNameBytes();
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public String getOneofs(int i2) {
            return ((Type) this.f4444a).getOneofs(i2);
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public ByteString getOneofsBytes(int i2) {
            return ((Type) this.f4444a).getOneofsBytes(i2);
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public int getOneofsCount() {
            return ((Type) this.f4444a).getOneofsCount();
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public List<String> getOneofsList() {
            return Collections.unmodifiableList(((Type) this.f4444a).getOneofsList());
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public Option getOptions(int i2) {
            return ((Type) this.f4444a).getOptions(i2);
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public int getOptionsCount() {
            return ((Type) this.f4444a).getOptionsCount();
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Type) this.f4444a).getOptionsList());
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public SourceContext getSourceContext() {
            return ((Type) this.f4444a).getSourceContext();
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public Syntax getSyntax() {
            return ((Type) this.f4444a).getSyntax();
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public int getSyntaxValue() {
            return ((Type) this.f4444a).getSyntaxValue();
        }

        @Override // androidx.health.platform.client.proto.TypeOrBuilder
        public boolean hasSourceContext() {
            return ((Type) this.f4444a).hasSourceContext();
        }

        public Builder mergeSourceContext(SourceContext sourceContext) {
            d();
            ((Type) this.f4444a).mergeSourceContext(sourceContext);
            return this;
        }

        public Builder removeFields(int i2) {
            d();
            ((Type) this.f4444a).removeFields(i2);
            return this;
        }

        public Builder removeOptions(int i2) {
            d();
            ((Type) this.f4444a).removeOptions(i2);
            return this;
        }

        public Builder setFields(int i2, Field field) {
            d();
            ((Type) this.f4444a).setFields(i2, field);
            return this;
        }

        public Builder setName(String str) {
            d();
            ((Type) this.f4444a).setName(str);
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            d();
            ((Type) this.f4444a).setNameBytes(byteString);
            return this;
        }

        public Builder setOneofs(int i2, String str) {
            d();
            ((Type) this.f4444a).setOneofs(i2, str);
            return this;
        }

        public Builder setOptions(int i2, Option option) {
            d();
            ((Type) this.f4444a).setOptions(i2, option);
            return this;
        }

        public Builder setSourceContext(SourceContext sourceContext) {
            d();
            ((Type) this.f4444a).setSourceContext(sourceContext);
            return this;
        }

        public Builder setSyntax(Syntax syntax) {
            d();
            ((Type) this.f4444a).setSyntax(syntax);
            return this;
        }

        public Builder setSyntaxValue(int i2) {
            d();
            ((Type) this.f4444a).setSyntaxValue(i2);
            return this;
        }

        private Builder() {
            super(Type.DEFAULT_INSTANCE);
        }

        public Builder addFields(int i2, Field field) {
            d();
            ((Type) this.f4444a).addFields(i2, field);
            return this;
        }

        public Builder addOptions(int i2, Option option) {
            d();
            ((Type) this.f4444a).addOptions(i2, option);
            return this;
        }

        public Builder setFields(int i2, Field.Builder builder) {
            d();
            ((Type) this.f4444a).setFields(i2, builder.build());
            return this;
        }

        public Builder setOptions(int i2, Option.Builder builder) {
            d();
            ((Type) this.f4444a).setOptions(i2, builder.build());
            return this;
        }

        public Builder setSourceContext(SourceContext.Builder builder) {
            d();
            ((Type) this.f4444a).setSourceContext(builder.build());
            return this;
        }

        public Builder addFields(Field.Builder builder) {
            d();
            ((Type) this.f4444a).addFields(builder.build());
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            d();
            ((Type) this.f4444a).addOptions(builder.build());
            return this;
        }

        public Builder addFields(int i2, Field.Builder builder) {
            d();
            ((Type) this.f4444a).addFields(i2, builder.build());
            return this;
        }

        public Builder addOptions(int i2, Option.Builder builder) {
            d();
            ((Type) this.f4444a).addOptions(i2, builder.build());
            return this;
        }
    }

    static {
        Type type = new Type();
        DEFAULT_INSTANCE = type;
        GeneratedMessageLite.T(Type.class, type);
    }

    private Type() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllFields(Iterable<? extends Field> iterable) {
        ensureFieldsIsMutable();
        AbstractMessageLite.a(iterable, this.fields_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllOneofs(Iterable<String> iterable) {
        ensureOneofsIsMutable();
        AbstractMessageLite.a(iterable, this.oneofs_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllOptions(Iterable<? extends Option> iterable) {
        ensureOptionsIsMutable();
        AbstractMessageLite.a(iterable, this.options_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addFields(Field field) {
        field.getClass();
        ensureFieldsIsMutable();
        this.fields_.add(field);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOneofs(String str) {
        str.getClass();
        ensureOneofsIsMutable();
        this.oneofs_.add(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOneofsBytes(ByteString byteString) {
        AbstractMessageLite.b(byteString);
        ensureOneofsIsMutable();
        this.oneofs_.add(byteString.toStringUtf8());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearFields() {
        this.fields_ = GeneratedMessageLite.r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearName() {
        this.name_ = getDefaultInstance().getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearOneofs() {
        this.oneofs_ = GeneratedMessageLite.r();
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

    private void ensureFieldsIsMutable() {
        Internal.ProtobufList<Field> protobufList = this.fields_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.fields_ = GeneratedMessageLite.C(protobufList);
    }

    private void ensureOneofsIsMutable() {
        Internal.ProtobufList<String> protobufList = this.oneofs_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.oneofs_ = GeneratedMessageLite.C(protobufList);
    }

    private void ensureOptionsIsMutable() {
        Internal.ProtobufList<Option> protobufList = this.options_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.options_ = GeneratedMessageLite.C(protobufList);
    }

    public static Type getDefaultInstance() {
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

    public static Type parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Type) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
    }

    public static Type parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
    }

    public static Parser<Type> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFields(int i2) {
        ensureFieldsIsMutable();
        this.fields_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeOptions(int i2) {
        ensureOptionsIsMutable();
        this.options_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFields(int i2, Field field) {
        field.getClass();
        ensureFieldsIsMutable();
        this.fields_.set(i2, field);
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
    public void setOneofs(int i2, String str) {
        str.getClass();
        ensureOneofsIsMutable();
        this.oneofs_.set(i2, str);
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

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public Field getFields(int i2) {
        return this.fields_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public int getFieldsCount() {
        return this.fields_.size();
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public List<Field> getFieldsList() {
        return this.fields_;
    }

    public FieldOrBuilder getFieldsOrBuilder(int i2) {
        return this.fields_.get(i2);
    }

    public List<? extends FieldOrBuilder> getFieldsOrBuilderList() {
        return this.fields_;
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public String getName() {
        return this.name_;
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public String getOneofs(int i2) {
        return this.oneofs_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public ByteString getOneofsBytes(int i2) {
        return ByteString.copyFromUtf8(this.oneofs_.get(i2));
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public int getOneofsCount() {
        return this.oneofs_.size();
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public List<String> getOneofsList() {
        return this.oneofs_;
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public Option getOptions(int i2) {
        return this.options_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public int getOptionsCount() {
        return this.options_.size();
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public List<Option> getOptionsList() {
        return this.options_;
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.options_.get(i2);
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public SourceContext getSourceContext() {
        SourceContext sourceContext = this.sourceContext_;
        return sourceContext == null ? SourceContext.getDefaultInstance() : sourceContext;
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public Syntax getSyntax() {
        Syntax syntaxForNumber = Syntax.forNumber(this.syntax_);
        return syntaxForNumber == null ? Syntax.UNRECOGNIZED : syntaxForNumber;
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public int getSyntaxValue() {
        return this.syntax_;
    }

    @Override // androidx.health.platform.client.proto.TypeOrBuilder
    public boolean hasSourceContext() {
        return this.sourceContext_ != null;
    }

    @Override // androidx.health.platform.client.proto.GeneratedMessageLite
    protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.f4497a[methodToInvoke.ordinal()]) {
            case 1:
                return new Type();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0003\u0000\u0001Ȉ\u0002\u001b\u0003Ț\u0004\u001b\u0005\t\u0006\f", new Object[]{"name_", "fields_", Field.class, "oneofs_", "options_", Option.class, "sourceContext_", "syntax_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<Type> defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (Type.class) {
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

    public static Builder newBuilder(Type type) {
        return (Builder) DEFAULT_INSTANCE.n(type);
    }

    public static Type parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Type) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Type parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static Type parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addFields(int i2, Field field) {
        field.getClass();
        ensureFieldsIsMutable();
        this.fields_.add(i2, field);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(i2, option);
    }

    public static Type parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Type parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
    }

    public static Type parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Type parseFrom(InputStream inputStream) throws IOException {
        return (Type) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
    }

    public static Type parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Type) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Type parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Type) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Type parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Type) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }
}
