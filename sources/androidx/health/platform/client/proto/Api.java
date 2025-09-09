package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.Internal;
import androidx.health.platform.client.proto.Method;
import androidx.health.platform.client.proto.Mixin;
import androidx.health.platform.client.proto.Option;
import androidx.health.platform.client.proto.SourceContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class Api extends GeneratedMessageLite<Api, Builder> implements ApiOrBuilder {
    private static final Api DEFAULT_INSTANCE;
    public static final int METHODS_FIELD_NUMBER = 2;
    public static final int MIXINS_FIELD_NUMBER = 6;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    private static volatile Parser<Api> PARSER = null;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
    public static final int SYNTAX_FIELD_NUMBER = 7;
    public static final int VERSION_FIELD_NUMBER = 4;
    private SourceContext sourceContext_;
    private int syntax_;
    private String name_ = "";
    private Internal.ProtobufList<Method> methods_ = GeneratedMessageLite.r();
    private Internal.ProtobufList<Option> options_ = GeneratedMessageLite.r();
    private String version_ = "";
    private Internal.ProtobufList<Mixin> mixins_ = GeneratedMessageLite.r();

    /* renamed from: androidx.health.platform.client.proto.Api$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4393a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4393a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4393a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4393a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4393a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4393a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4393a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4393a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Api, Builder> implements ApiOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        public Builder addAllMethods(Iterable<? extends Method> iterable) {
            d();
            ((Api) this.f4444a).addAllMethods(iterable);
            return this;
        }

        public Builder addAllMixins(Iterable<? extends Mixin> iterable) {
            d();
            ((Api) this.f4444a).addAllMixins(iterable);
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            d();
            ((Api) this.f4444a).addAllOptions(iterable);
            return this;
        }

        public Builder addMethods(Method method) {
            d();
            ((Api) this.f4444a).addMethods(method);
            return this;
        }

        public Builder addMixins(Mixin mixin) {
            d();
            ((Api) this.f4444a).addMixins(mixin);
            return this;
        }

        public Builder addOptions(Option option) {
            d();
            ((Api) this.f4444a).addOptions(option);
            return this;
        }

        public Builder clearMethods() {
            d();
            ((Api) this.f4444a).clearMethods();
            return this;
        }

        public Builder clearMixins() {
            d();
            ((Api) this.f4444a).clearMixins();
            return this;
        }

        public Builder clearName() {
            d();
            ((Api) this.f4444a).clearName();
            return this;
        }

        public Builder clearOptions() {
            d();
            ((Api) this.f4444a).clearOptions();
            return this;
        }

        public Builder clearSourceContext() {
            d();
            ((Api) this.f4444a).clearSourceContext();
            return this;
        }

        public Builder clearSyntax() {
            d();
            ((Api) this.f4444a).clearSyntax();
            return this;
        }

        public Builder clearVersion() {
            d();
            ((Api) this.f4444a).clearVersion();
            return this;
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public Method getMethods(int i2) {
            return ((Api) this.f4444a).getMethods(i2);
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public int getMethodsCount() {
            return ((Api) this.f4444a).getMethodsCount();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public List<Method> getMethodsList() {
            return Collections.unmodifiableList(((Api) this.f4444a).getMethodsList());
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public Mixin getMixins(int i2) {
            return ((Api) this.f4444a).getMixins(i2);
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public int getMixinsCount() {
            return ((Api) this.f4444a).getMixinsCount();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public List<Mixin> getMixinsList() {
            return Collections.unmodifiableList(((Api) this.f4444a).getMixinsList());
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public String getName() {
            return ((Api) this.f4444a).getName();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public ByteString getNameBytes() {
            return ((Api) this.f4444a).getNameBytes();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public Option getOptions(int i2) {
            return ((Api) this.f4444a).getOptions(i2);
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public int getOptionsCount() {
            return ((Api) this.f4444a).getOptionsCount();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Api) this.f4444a).getOptionsList());
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public SourceContext getSourceContext() {
            return ((Api) this.f4444a).getSourceContext();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public Syntax getSyntax() {
            return ((Api) this.f4444a).getSyntax();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public int getSyntaxValue() {
            return ((Api) this.f4444a).getSyntaxValue();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public String getVersion() {
            return ((Api) this.f4444a).getVersion();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public ByteString getVersionBytes() {
            return ((Api) this.f4444a).getVersionBytes();
        }

        @Override // androidx.health.platform.client.proto.ApiOrBuilder
        public boolean hasSourceContext() {
            return ((Api) this.f4444a).hasSourceContext();
        }

        public Builder mergeSourceContext(SourceContext sourceContext) {
            d();
            ((Api) this.f4444a).mergeSourceContext(sourceContext);
            return this;
        }

        public Builder removeMethods(int i2) {
            d();
            ((Api) this.f4444a).removeMethods(i2);
            return this;
        }

        public Builder removeMixins(int i2) {
            d();
            ((Api) this.f4444a).removeMixins(i2);
            return this;
        }

        public Builder removeOptions(int i2) {
            d();
            ((Api) this.f4444a).removeOptions(i2);
            return this;
        }

        public Builder setMethods(int i2, Method method) {
            d();
            ((Api) this.f4444a).setMethods(i2, method);
            return this;
        }

        public Builder setMixins(int i2, Mixin mixin) {
            d();
            ((Api) this.f4444a).setMixins(i2, mixin);
            return this;
        }

        public Builder setName(String str) {
            d();
            ((Api) this.f4444a).setName(str);
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            d();
            ((Api) this.f4444a).setNameBytes(byteString);
            return this;
        }

        public Builder setOptions(int i2, Option option) {
            d();
            ((Api) this.f4444a).setOptions(i2, option);
            return this;
        }

        public Builder setSourceContext(SourceContext sourceContext) {
            d();
            ((Api) this.f4444a).setSourceContext(sourceContext);
            return this;
        }

        public Builder setSyntax(Syntax syntax) {
            d();
            ((Api) this.f4444a).setSyntax(syntax);
            return this;
        }

        public Builder setSyntaxValue(int i2) {
            d();
            ((Api) this.f4444a).setSyntaxValue(i2);
            return this;
        }

        public Builder setVersion(String str) {
            d();
            ((Api) this.f4444a).setVersion(str);
            return this;
        }

        public Builder setVersionBytes(ByteString byteString) {
            d();
            ((Api) this.f4444a).setVersionBytes(byteString);
            return this;
        }

        private Builder() {
            super(Api.DEFAULT_INSTANCE);
        }

        public Builder addMethods(int i2, Method method) {
            d();
            ((Api) this.f4444a).addMethods(i2, method);
            return this;
        }

        public Builder addMixins(int i2, Mixin mixin) {
            d();
            ((Api) this.f4444a).addMixins(i2, mixin);
            return this;
        }

        public Builder addOptions(int i2, Option option) {
            d();
            ((Api) this.f4444a).addOptions(i2, option);
            return this;
        }

        public Builder setMethods(int i2, Method.Builder builder) {
            d();
            ((Api) this.f4444a).setMethods(i2, builder.build());
            return this;
        }

        public Builder setMixins(int i2, Mixin.Builder builder) {
            d();
            ((Api) this.f4444a).setMixins(i2, builder.build());
            return this;
        }

        public Builder setOptions(int i2, Option.Builder builder) {
            d();
            ((Api) this.f4444a).setOptions(i2, builder.build());
            return this;
        }

        public Builder setSourceContext(SourceContext.Builder builder) {
            d();
            ((Api) this.f4444a).setSourceContext(builder.build());
            return this;
        }

        public Builder addMethods(Method.Builder builder) {
            d();
            ((Api) this.f4444a).addMethods(builder.build());
            return this;
        }

        public Builder addMixins(Mixin.Builder builder) {
            d();
            ((Api) this.f4444a).addMixins(builder.build());
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            d();
            ((Api) this.f4444a).addOptions(builder.build());
            return this;
        }

        public Builder addMethods(int i2, Method.Builder builder) {
            d();
            ((Api) this.f4444a).addMethods(i2, builder.build());
            return this;
        }

        public Builder addMixins(int i2, Mixin.Builder builder) {
            d();
            ((Api) this.f4444a).addMixins(i2, builder.build());
            return this;
        }

        public Builder addOptions(int i2, Option.Builder builder) {
            d();
            ((Api) this.f4444a).addOptions(i2, builder.build());
            return this;
        }
    }

    static {
        Api api = new Api();
        DEFAULT_INSTANCE = api;
        GeneratedMessageLite.T(Api.class, api);
    }

    private Api() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllMethods(Iterable<? extends Method> iterable) {
        ensureMethodsIsMutable();
        AbstractMessageLite.a(iterable, this.methods_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllMixins(Iterable<? extends Mixin> iterable) {
        ensureMixinsIsMutable();
        AbstractMessageLite.a(iterable, this.mixins_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllOptions(Iterable<? extends Option> iterable) {
        ensureOptionsIsMutable();
        AbstractMessageLite.a(iterable, this.options_);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addMethods(Method method) {
        method.getClass();
        ensureMethodsIsMutable();
        this.methods_.add(method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addMixins(Mixin mixin) {
        mixin.getClass();
        ensureMixinsIsMutable();
        this.mixins_.add(mixin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearMethods() {
        this.methods_ = GeneratedMessageLite.r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearMixins() {
        this.mixins_ = GeneratedMessageLite.r();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void clearVersion() {
        this.version_ = getDefaultInstance().getVersion();
    }

    private void ensureMethodsIsMutable() {
        Internal.ProtobufList<Method> protobufList = this.methods_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.methods_ = GeneratedMessageLite.C(protobufList);
    }

    private void ensureMixinsIsMutable() {
        Internal.ProtobufList<Mixin> protobufList = this.mixins_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.mixins_ = GeneratedMessageLite.C(protobufList);
    }

    private void ensureOptionsIsMutable() {
        Internal.ProtobufList<Option> protobufList = this.options_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.options_ = GeneratedMessageLite.C(protobufList);
    }

    public static Api getDefaultInstance() {
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

    public static Api parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Api) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
    }

    public static Api parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
    }

    public static Parser<Api> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeMethods(int i2) {
        ensureMethodsIsMutable();
        this.methods_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeMixins(int i2) {
        ensureMixinsIsMutable();
        this.mixins_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeOptions(int i2) {
        ensureOptionsIsMutable();
        this.options_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMethods(int i2, Method method) {
        method.getClass();
        ensureMethodsIsMutable();
        this.methods_.set(i2, method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMixins(int i2, Mixin mixin) {
        mixin.getClass();
        ensureMixinsIsMutable();
        this.mixins_.set(i2, mixin);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void setVersion(String str) {
        str.getClass();
        this.version_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVersionBytes(ByteString byteString) {
        AbstractMessageLite.b(byteString);
        this.version_ = byteString.toStringUtf8();
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public Method getMethods(int i2) {
        return this.methods_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public int getMethodsCount() {
        return this.methods_.size();
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public List<Method> getMethodsList() {
        return this.methods_;
    }

    public MethodOrBuilder getMethodsOrBuilder(int i2) {
        return this.methods_.get(i2);
    }

    public List<? extends MethodOrBuilder> getMethodsOrBuilderList() {
        return this.methods_;
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public Mixin getMixins(int i2) {
        return this.mixins_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public int getMixinsCount() {
        return this.mixins_.size();
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public List<Mixin> getMixinsList() {
        return this.mixins_;
    }

    public MixinOrBuilder getMixinsOrBuilder(int i2) {
        return this.mixins_.get(i2);
    }

    public List<? extends MixinOrBuilder> getMixinsOrBuilderList() {
        return this.mixins_;
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public String getName() {
        return this.name_;
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public Option getOptions(int i2) {
        return this.options_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public int getOptionsCount() {
        return this.options_.size();
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public List<Option> getOptionsList() {
        return this.options_;
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.options_.get(i2);
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public SourceContext getSourceContext() {
        SourceContext sourceContext = this.sourceContext_;
        return sourceContext == null ? SourceContext.getDefaultInstance() : sourceContext;
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public Syntax getSyntax() {
        Syntax syntaxForNumber = Syntax.forNumber(this.syntax_);
        return syntaxForNumber == null ? Syntax.UNRECOGNIZED : syntaxForNumber;
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public int getSyntaxValue() {
        return this.syntax_;
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public String getVersion() {
        return this.version_;
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public ByteString getVersionBytes() {
        return ByteString.copyFromUtf8(this.version_);
    }

    @Override // androidx.health.platform.client.proto.ApiOrBuilder
    public boolean hasSourceContext() {
        return this.sourceContext_ != null;
    }

    @Override // androidx.health.platform.client.proto.GeneratedMessageLite
    protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.f4393a[methodToInvoke.ordinal()]) {
            case 1:
                return new Api();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0003\u0000\u0001Ȉ\u0002\u001b\u0003\u001b\u0004Ȉ\u0005\t\u0006\u001b\u0007\f", new Object[]{"name_", "methods_", Method.class, "options_", Option.class, "version_", "sourceContext_", "mixins_", Mixin.class, "syntax_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<Api> defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (Api.class) {
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

    public static Builder newBuilder(Api api) {
        return (Builder) DEFAULT_INSTANCE.n(api);
    }

    public static Api parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Api) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Api parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static Api parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addMethods(int i2, Method method) {
        method.getClass();
        ensureMethodsIsMutable();
        this.methods_.add(i2, method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addMixins(int i2, Mixin mixin) {
        mixin.getClass();
        ensureMixinsIsMutable();
        this.mixins_.add(i2, mixin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(i2, option);
    }

    public static Api parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Api parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
    }

    public static Api parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Api parseFrom(InputStream inputStream) throws IOException {
        return (Api) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
    }

    public static Api parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Api) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Api parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Api) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Api parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Api) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }
}
