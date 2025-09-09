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
public final class Method extends GeneratedMessageLite<Method, Builder> implements MethodOrBuilder {
    private static final Method DEFAULT_INSTANCE;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 6;
    private static volatile Parser<Method> PARSER = null;
    public static final int REQUEST_STREAMING_FIELD_NUMBER = 3;
    public static final int REQUEST_TYPE_URL_FIELD_NUMBER = 2;
    public static final int RESPONSE_STREAMING_FIELD_NUMBER = 5;
    public static final int RESPONSE_TYPE_URL_FIELD_NUMBER = 4;
    public static final int SYNTAX_FIELD_NUMBER = 7;
    private boolean requestStreaming_;
    private boolean responseStreaming_;
    private int syntax_;
    private String name_ = "";
    private String requestTypeUrl_ = "";
    private String responseTypeUrl_ = "";
    private Internal.ProtobufList<Option> options_ = GeneratedMessageLite.r();

    /* renamed from: androidx.health.platform.client.proto.Method$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4469a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4469a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4469a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4469a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4469a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4469a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4469a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4469a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Method, Builder> implements MethodOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            d();
            ((Method) this.f4444a).addAllOptions(iterable);
            return this;
        }

        public Builder addOptions(Option option) {
            d();
            ((Method) this.f4444a).addOptions(option);
            return this;
        }

        public Builder clearName() {
            d();
            ((Method) this.f4444a).clearName();
            return this;
        }

        public Builder clearOptions() {
            d();
            ((Method) this.f4444a).clearOptions();
            return this;
        }

        public Builder clearRequestStreaming() {
            d();
            ((Method) this.f4444a).clearRequestStreaming();
            return this;
        }

        public Builder clearRequestTypeUrl() {
            d();
            ((Method) this.f4444a).clearRequestTypeUrl();
            return this;
        }

        public Builder clearResponseStreaming() {
            d();
            ((Method) this.f4444a).clearResponseStreaming();
            return this;
        }

        public Builder clearResponseTypeUrl() {
            d();
            ((Method) this.f4444a).clearResponseTypeUrl();
            return this;
        }

        public Builder clearSyntax() {
            d();
            ((Method) this.f4444a).clearSyntax();
            return this;
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public String getName() {
            return ((Method) this.f4444a).getName();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public ByteString getNameBytes() {
            return ((Method) this.f4444a).getNameBytes();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public Option getOptions(int i2) {
            return ((Method) this.f4444a).getOptions(i2);
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public int getOptionsCount() {
            return ((Method) this.f4444a).getOptionsCount();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Method) this.f4444a).getOptionsList());
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public boolean getRequestStreaming() {
            return ((Method) this.f4444a).getRequestStreaming();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public String getRequestTypeUrl() {
            return ((Method) this.f4444a).getRequestTypeUrl();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public ByteString getRequestTypeUrlBytes() {
            return ((Method) this.f4444a).getRequestTypeUrlBytes();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public boolean getResponseStreaming() {
            return ((Method) this.f4444a).getResponseStreaming();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public String getResponseTypeUrl() {
            return ((Method) this.f4444a).getResponseTypeUrl();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public ByteString getResponseTypeUrlBytes() {
            return ((Method) this.f4444a).getResponseTypeUrlBytes();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public Syntax getSyntax() {
            return ((Method) this.f4444a).getSyntax();
        }

        @Override // androidx.health.platform.client.proto.MethodOrBuilder
        public int getSyntaxValue() {
            return ((Method) this.f4444a).getSyntaxValue();
        }

        public Builder removeOptions(int i2) {
            d();
            ((Method) this.f4444a).removeOptions(i2);
            return this;
        }

        public Builder setName(String str) {
            d();
            ((Method) this.f4444a).setName(str);
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            d();
            ((Method) this.f4444a).setNameBytes(byteString);
            return this;
        }

        public Builder setOptions(int i2, Option option) {
            d();
            ((Method) this.f4444a).setOptions(i2, option);
            return this;
        }

        public Builder setRequestStreaming(boolean z2) {
            d();
            ((Method) this.f4444a).setRequestStreaming(z2);
            return this;
        }

        public Builder setRequestTypeUrl(String str) {
            d();
            ((Method) this.f4444a).setRequestTypeUrl(str);
            return this;
        }

        public Builder setRequestTypeUrlBytes(ByteString byteString) {
            d();
            ((Method) this.f4444a).setRequestTypeUrlBytes(byteString);
            return this;
        }

        public Builder setResponseStreaming(boolean z2) {
            d();
            ((Method) this.f4444a).setResponseStreaming(z2);
            return this;
        }

        public Builder setResponseTypeUrl(String str) {
            d();
            ((Method) this.f4444a).setResponseTypeUrl(str);
            return this;
        }

        public Builder setResponseTypeUrlBytes(ByteString byteString) {
            d();
            ((Method) this.f4444a).setResponseTypeUrlBytes(byteString);
            return this;
        }

        public Builder setSyntax(Syntax syntax) {
            d();
            ((Method) this.f4444a).setSyntax(syntax);
            return this;
        }

        public Builder setSyntaxValue(int i2) {
            d();
            ((Method) this.f4444a).setSyntaxValue(i2);
            return this;
        }

        private Builder() {
            super(Method.DEFAULT_INSTANCE);
        }

        public Builder addOptions(int i2, Option option) {
            d();
            ((Method) this.f4444a).addOptions(i2, option);
            return this;
        }

        public Builder setOptions(int i2, Option.Builder builder) {
            d();
            ((Method) this.f4444a).setOptions(i2, builder.build());
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            d();
            ((Method) this.f4444a).addOptions(builder.build());
            return this;
        }

        public Builder addOptions(int i2, Option.Builder builder) {
            d();
            ((Method) this.f4444a).addOptions(i2, builder.build());
            return this;
        }
    }

    static {
        Method method = new Method();
        DEFAULT_INSTANCE = method;
        GeneratedMessageLite.T(Method.class, method);
    }

    private Method() {
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
    public void clearOptions() {
        this.options_ = GeneratedMessageLite.r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearRequestStreaming() {
        this.requestStreaming_ = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearRequestTypeUrl() {
        this.requestTypeUrl_ = getDefaultInstance().getRequestTypeUrl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearResponseStreaming() {
        this.responseStreaming_ = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearResponseTypeUrl() {
        this.responseTypeUrl_ = getDefaultInstance().getResponseTypeUrl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSyntax() {
        this.syntax_ = 0;
    }

    private void ensureOptionsIsMutable() {
        Internal.ProtobufList<Option> protobufList = this.options_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.options_ = GeneratedMessageLite.C(protobufList);
    }

    public static Method getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.m();
    }

    public static Method parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Method) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
    }

    public static Method parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
    }

    public static Parser<Method> parser() {
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
    public void setOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.set(i2, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRequestStreaming(boolean z2) {
        this.requestStreaming_ = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRequestTypeUrl(String str) {
        str.getClass();
        this.requestTypeUrl_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRequestTypeUrlBytes(ByteString byteString) {
        AbstractMessageLite.b(byteString);
        this.requestTypeUrl_ = byteString.toStringUtf8();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setResponseStreaming(boolean z2) {
        this.responseStreaming_ = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setResponseTypeUrl(String str) {
        str.getClass();
        this.responseTypeUrl_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setResponseTypeUrlBytes(ByteString byteString) {
        AbstractMessageLite.b(byteString);
        this.responseTypeUrl_ = byteString.toStringUtf8();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSyntax(Syntax syntax) {
        this.syntax_ = syntax.getNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSyntaxValue(int i2) {
        this.syntax_ = i2;
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public String getName() {
        return this.name_;
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public Option getOptions(int i2) {
        return this.options_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public int getOptionsCount() {
        return this.options_.size();
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public List<Option> getOptionsList() {
        return this.options_;
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.options_.get(i2);
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public boolean getRequestStreaming() {
        return this.requestStreaming_;
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public String getRequestTypeUrl() {
        return this.requestTypeUrl_;
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public ByteString getRequestTypeUrlBytes() {
        return ByteString.copyFromUtf8(this.requestTypeUrl_);
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public boolean getResponseStreaming() {
        return this.responseStreaming_;
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public String getResponseTypeUrl() {
        return this.responseTypeUrl_;
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public ByteString getResponseTypeUrlBytes() {
        return ByteString.copyFromUtf8(this.responseTypeUrl_);
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public Syntax getSyntax() {
        Syntax syntaxForNumber = Syntax.forNumber(this.syntax_);
        return syntaxForNumber == null ? Syntax.UNRECOGNIZED : syntaxForNumber;
    }

    @Override // androidx.health.platform.client.proto.MethodOrBuilder
    public int getSyntaxValue() {
        return this.syntax_;
    }

    @Override // androidx.health.platform.client.proto.GeneratedMessageLite
    protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.f4469a[methodToInvoke.ordinal()]) {
            case 1:
                return new Method();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0001\u0000\u0001Ȉ\u0002Ȉ\u0003\u0007\u0004Ȉ\u0005\u0007\u0006\u001b\u0007\f", new Object[]{"name_", "requestTypeUrl_", "requestStreaming_", "responseTypeUrl_", "responseStreaming_", "options_", Option.class, "syntax_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<Method> defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (Method.class) {
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

    public static Builder newBuilder(Method method) {
        return (Builder) DEFAULT_INSTANCE.n(method);
    }

    public static Method parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Method) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Method parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static Method parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(i2, option);
    }

    public static Method parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Method parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
    }

    public static Method parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Method parseFrom(InputStream inputStream) throws IOException {
        return (Method) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
    }

    public static Method parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Method) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Method parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Method) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Method parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Method) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }
}
