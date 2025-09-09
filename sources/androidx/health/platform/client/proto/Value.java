package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.ListValue;
import androidx.health.platform.client.proto.Struct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class Value extends GeneratedMessageLite<Value, Builder> implements ValueOrBuilder {
    public static final int BOOL_VALUE_FIELD_NUMBER = 4;
    private static final Value DEFAULT_INSTANCE;
    public static final int LIST_VALUE_FIELD_NUMBER = 6;
    public static final int NULL_VALUE_FIELD_NUMBER = 1;
    public static final int NUMBER_VALUE_FIELD_NUMBER = 2;
    private static volatile Parser<Value> PARSER = null;
    public static final int STRING_VALUE_FIELD_NUMBER = 3;
    public static final int STRUCT_VALUE_FIELD_NUMBER = 5;
    private int kindCase_ = 0;
    private Object kind_;

    /* renamed from: androidx.health.platform.client.proto.Value$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4508a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4508a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4508a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4508a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4508a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4508a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4508a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4508a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Value, Builder> implements ValueOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        public Builder clearBoolValue() {
            d();
            ((Value) this.f4444a).clearBoolValue();
            return this;
        }

        public Builder clearKind() {
            d();
            ((Value) this.f4444a).clearKind();
            return this;
        }

        public Builder clearListValue() {
            d();
            ((Value) this.f4444a).clearListValue();
            return this;
        }

        public Builder clearNullValue() {
            d();
            ((Value) this.f4444a).clearNullValue();
            return this;
        }

        public Builder clearNumberValue() {
            d();
            ((Value) this.f4444a).clearNumberValue();
            return this;
        }

        public Builder clearStringValue() {
            d();
            ((Value) this.f4444a).clearStringValue();
            return this;
        }

        public Builder clearStructValue() {
            d();
            ((Value) this.f4444a).clearStructValue();
            return this;
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public boolean getBoolValue() {
            return ((Value) this.f4444a).getBoolValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public KindCase getKindCase() {
            return ((Value) this.f4444a).getKindCase();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public ListValue getListValue() {
            return ((Value) this.f4444a).getListValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public NullValue getNullValue() {
            return ((Value) this.f4444a).getNullValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public int getNullValueValue() {
            return ((Value) this.f4444a).getNullValueValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public double getNumberValue() {
            return ((Value) this.f4444a).getNumberValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public String getStringValue() {
            return ((Value) this.f4444a).getStringValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public ByteString getStringValueBytes() {
            return ((Value) this.f4444a).getStringValueBytes();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public Struct getStructValue() {
            return ((Value) this.f4444a).getStructValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public boolean hasBoolValue() {
            return ((Value) this.f4444a).hasBoolValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public boolean hasListValue() {
            return ((Value) this.f4444a).hasListValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public boolean hasNullValue() {
            return ((Value) this.f4444a).hasNullValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public boolean hasNumberValue() {
            return ((Value) this.f4444a).hasNumberValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public boolean hasStringValue() {
            return ((Value) this.f4444a).hasStringValue();
        }

        @Override // androidx.health.platform.client.proto.ValueOrBuilder
        public boolean hasStructValue() {
            return ((Value) this.f4444a).hasStructValue();
        }

        public Builder mergeListValue(ListValue listValue) {
            d();
            ((Value) this.f4444a).mergeListValue(listValue);
            return this;
        }

        public Builder mergeStructValue(Struct struct) {
            d();
            ((Value) this.f4444a).mergeStructValue(struct);
            return this;
        }

        public Builder setBoolValue(boolean z2) {
            d();
            ((Value) this.f4444a).setBoolValue(z2);
            return this;
        }

        public Builder setListValue(ListValue listValue) {
            d();
            ((Value) this.f4444a).setListValue(listValue);
            return this;
        }

        public Builder setNullValue(NullValue nullValue) {
            d();
            ((Value) this.f4444a).setNullValue(nullValue);
            return this;
        }

        public Builder setNullValueValue(int i2) {
            d();
            ((Value) this.f4444a).setNullValueValue(i2);
            return this;
        }

        public Builder setNumberValue(double d2) {
            d();
            ((Value) this.f4444a).setNumberValue(d2);
            return this;
        }

        public Builder setStringValue(String str) {
            d();
            ((Value) this.f4444a).setStringValue(str);
            return this;
        }

        public Builder setStringValueBytes(ByteString byteString) {
            d();
            ((Value) this.f4444a).setStringValueBytes(byteString);
            return this;
        }

        public Builder setStructValue(Struct struct) {
            d();
            ((Value) this.f4444a).setStructValue(struct);
            return this;
        }

        private Builder() {
            super(Value.DEFAULT_INSTANCE);
        }

        public Builder setListValue(ListValue.Builder builder) {
            d();
            ((Value) this.f4444a).setListValue(builder.build());
            return this;
        }

        public Builder setStructValue(Struct.Builder builder) {
            d();
            ((Value) this.f4444a).setStructValue(builder.build());
            return this;
        }
    }

    public enum KindCase {
        NULL_VALUE(1),
        NUMBER_VALUE(2),
        STRING_VALUE(3),
        BOOL_VALUE(4),
        STRUCT_VALUE(5),
        LIST_VALUE(6),
        KIND_NOT_SET(0);

        private final int value;

        KindCase(int i2) {
            this.value = i2;
        }

        public static KindCase forNumber(int i2) {
            switch (i2) {
                case 0:
                    return KIND_NOT_SET;
                case 1:
                    return NULL_VALUE;
                case 2:
                    return NUMBER_VALUE;
                case 3:
                    return STRING_VALUE;
                case 4:
                    return BOOL_VALUE;
                case 5:
                    return STRUCT_VALUE;
                case 6:
                    return LIST_VALUE;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }

        @Deprecated
        public static KindCase valueOf(int i2) {
            return forNumber(i2);
        }
    }

    static {
        Value value = new Value();
        DEFAULT_INSTANCE = value;
        GeneratedMessageLite.T(Value.class, value);
    }

    private Value() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearBoolValue() {
        if (this.kindCase_ == 4) {
            this.kindCase_ = 0;
            this.kind_ = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearKind() {
        this.kindCase_ = 0;
        this.kind_ = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearListValue() {
        if (this.kindCase_ == 6) {
            this.kindCase_ = 0;
            this.kind_ = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearNullValue() {
        if (this.kindCase_ == 1) {
            this.kindCase_ = 0;
            this.kind_ = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearNumberValue() {
        if (this.kindCase_ == 2) {
            this.kindCase_ = 0;
            this.kind_ = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearStringValue() {
        if (this.kindCase_ == 3) {
            this.kindCase_ = 0;
            this.kind_ = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearStructValue() {
        if (this.kindCase_ == 5) {
            this.kindCase_ = 0;
            this.kind_ = null;
        }
    }

    public static Value getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeListValue(ListValue listValue) {
        listValue.getClass();
        if (this.kindCase_ != 6 || this.kind_ == ListValue.getDefaultInstance()) {
            this.kind_ = listValue;
        } else {
            this.kind_ = ListValue.newBuilder((ListValue) this.kind_).mergeFrom((ListValue.Builder) listValue).buildPartial();
        }
        this.kindCase_ = 6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeStructValue(Struct struct) {
        struct.getClass();
        if (this.kindCase_ != 5 || this.kind_ == Struct.getDefaultInstance()) {
            this.kind_ = struct;
        } else {
            this.kind_ = Struct.newBuilder((Struct) this.kind_).mergeFrom((Struct.Builder) struct).buildPartial();
        }
        this.kindCase_ = 5;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.m();
    }

    public static Value parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Value) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
    }

    public static Value parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
    }

    public static Parser<Value> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBoolValue(boolean z2) {
        this.kindCase_ = 4;
        this.kind_ = Boolean.valueOf(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setListValue(ListValue listValue) {
        listValue.getClass();
        this.kind_ = listValue;
        this.kindCase_ = 6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNullValue(NullValue nullValue) {
        this.kind_ = Integer.valueOf(nullValue.getNumber());
        this.kindCase_ = 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNullValueValue(int i2) {
        this.kindCase_ = 1;
        this.kind_ = Integer.valueOf(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNumberValue(double d2) {
        this.kindCase_ = 2;
        this.kind_ = Double.valueOf(d2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStringValue(String str) {
        str.getClass();
        this.kindCase_ = 3;
        this.kind_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStringValueBytes(ByteString byteString) {
        AbstractMessageLite.b(byteString);
        this.kind_ = byteString.toStringUtf8();
        this.kindCase_ = 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStructValue(Struct struct) {
        struct.getClass();
        this.kind_ = struct;
        this.kindCase_ = 5;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public boolean getBoolValue() {
        if (this.kindCase_ == 4) {
            return ((Boolean) this.kind_).booleanValue();
        }
        return false;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public KindCase getKindCase() {
        return KindCase.forNumber(this.kindCase_);
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public ListValue getListValue() {
        return this.kindCase_ == 6 ? (ListValue) this.kind_ : ListValue.getDefaultInstance();
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public NullValue getNullValue() {
        if (this.kindCase_ != 1) {
            return NullValue.NULL_VALUE;
        }
        NullValue nullValueForNumber = NullValue.forNumber(((Integer) this.kind_).intValue());
        return nullValueForNumber == null ? NullValue.UNRECOGNIZED : nullValueForNumber;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public int getNullValueValue() {
        if (this.kindCase_ == 1) {
            return ((Integer) this.kind_).intValue();
        }
        return 0;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public double getNumberValue() {
        if (this.kindCase_ == 2) {
            return ((Double) this.kind_).doubleValue();
        }
        return 0.0d;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public String getStringValue() {
        return this.kindCase_ == 3 ? (String) this.kind_ : "";
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public ByteString getStringValueBytes() {
        return ByteString.copyFromUtf8(this.kindCase_ == 3 ? (String) this.kind_ : "");
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public Struct getStructValue() {
        return this.kindCase_ == 5 ? (Struct) this.kind_ : Struct.getDefaultInstance();
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public boolean hasBoolValue() {
        return this.kindCase_ == 4;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public boolean hasListValue() {
        return this.kindCase_ == 6;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public boolean hasNullValue() {
        return this.kindCase_ == 1;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public boolean hasNumberValue() {
        return this.kindCase_ == 2;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public boolean hasStringValue() {
        return this.kindCase_ == 3;
    }

    @Override // androidx.health.platform.client.proto.ValueOrBuilder
    public boolean hasStructValue() {
        return this.kindCase_ == 5;
    }

    @Override // androidx.health.platform.client.proto.GeneratedMessageLite
    protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.f4508a[methodToInvoke.ordinal()]) {
            case 1:
                return new Value();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0000\u0006\u0001\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001?\u0000\u00023\u0000\u0003Ȼ\u0000\u0004:\u0000\u0005<\u0000\u0006<\u0000", new Object[]{"kind_", "kindCase_", Struct.class, ListValue.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<Value> defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (Value.class) {
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

    public static Builder newBuilder(Value value) {
        return (Builder) DEFAULT_INSTANCE.n(value);
    }

    public static Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Value parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
    }

    public static Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
    }

    public static Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Value parseFrom(InputStream inputStream) throws IOException {
        return (Value) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
    }

    public static Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Value parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Value) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }
}
