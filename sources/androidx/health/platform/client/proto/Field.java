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
public final class Field extends GeneratedMessageLite<Field, Builder> implements FieldOrBuilder {
    public static final int CARDINALITY_FIELD_NUMBER = 2;
    private static final Field DEFAULT_INSTANCE;
    public static final int DEFAULT_VALUE_FIELD_NUMBER = 11;
    public static final int JSON_NAME_FIELD_NUMBER = 10;
    public static final int KIND_FIELD_NUMBER = 1;
    public static final int NAME_FIELD_NUMBER = 4;
    public static final int NUMBER_FIELD_NUMBER = 3;
    public static final int ONEOF_INDEX_FIELD_NUMBER = 7;
    public static final int OPTIONS_FIELD_NUMBER = 9;
    public static final int PACKED_FIELD_NUMBER = 8;
    private static volatile Parser<Field> PARSER = null;
    public static final int TYPE_URL_FIELD_NUMBER = 6;
    private int cardinality_;
    private int kind_;
    private int number_;
    private int oneofIndex_;
    private boolean packed_;
    private String name_ = "";
    private String typeUrl_ = "";
    private Internal.ProtobufList<Option> options_ = GeneratedMessageLite.r();
    private String jsonName_ = "";
    private String defaultValue_ = "";

    /* renamed from: androidx.health.platform.client.proto.Field$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4433a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4433a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4433a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4433a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4433a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4433a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4433a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4433a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Field, Builder> implements FieldOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            d();
            ((Field) this.f4444a).addAllOptions(iterable);
            return this;
        }

        public Builder addOptions(Option option) {
            d();
            ((Field) this.f4444a).addOptions(option);
            return this;
        }

        public Builder clearCardinality() {
            d();
            ((Field) this.f4444a).clearCardinality();
            return this;
        }

        public Builder clearDefaultValue() {
            d();
            ((Field) this.f4444a).clearDefaultValue();
            return this;
        }

        public Builder clearJsonName() {
            d();
            ((Field) this.f4444a).clearJsonName();
            return this;
        }

        public Builder clearKind() {
            d();
            ((Field) this.f4444a).clearKind();
            return this;
        }

        public Builder clearName() {
            d();
            ((Field) this.f4444a).clearName();
            return this;
        }

        public Builder clearNumber() {
            d();
            ((Field) this.f4444a).clearNumber();
            return this;
        }

        public Builder clearOneofIndex() {
            d();
            ((Field) this.f4444a).clearOneofIndex();
            return this;
        }

        public Builder clearOptions() {
            d();
            ((Field) this.f4444a).clearOptions();
            return this;
        }

        public Builder clearPacked() {
            d();
            ((Field) this.f4444a).clearPacked();
            return this;
        }

        public Builder clearTypeUrl() {
            d();
            ((Field) this.f4444a).clearTypeUrl();
            return this;
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public Cardinality getCardinality() {
            return ((Field) this.f4444a).getCardinality();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public int getCardinalityValue() {
            return ((Field) this.f4444a).getCardinalityValue();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public String getDefaultValue() {
            return ((Field) this.f4444a).getDefaultValue();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public ByteString getDefaultValueBytes() {
            return ((Field) this.f4444a).getDefaultValueBytes();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public String getJsonName() {
            return ((Field) this.f4444a).getJsonName();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public ByteString getJsonNameBytes() {
            return ((Field) this.f4444a).getJsonNameBytes();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public Kind getKind() {
            return ((Field) this.f4444a).getKind();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public int getKindValue() {
            return ((Field) this.f4444a).getKindValue();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public String getName() {
            return ((Field) this.f4444a).getName();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public ByteString getNameBytes() {
            return ((Field) this.f4444a).getNameBytes();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public int getNumber() {
            return ((Field) this.f4444a).getNumber();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public int getOneofIndex() {
            return ((Field) this.f4444a).getOneofIndex();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public Option getOptions(int i2) {
            return ((Field) this.f4444a).getOptions(i2);
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public int getOptionsCount() {
            return ((Field) this.f4444a).getOptionsCount();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Field) this.f4444a).getOptionsList());
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public boolean getPacked() {
            return ((Field) this.f4444a).getPacked();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public String getTypeUrl() {
            return ((Field) this.f4444a).getTypeUrl();
        }

        @Override // androidx.health.platform.client.proto.FieldOrBuilder
        public ByteString getTypeUrlBytes() {
            return ((Field) this.f4444a).getTypeUrlBytes();
        }

        public Builder removeOptions(int i2) {
            d();
            ((Field) this.f4444a).removeOptions(i2);
            return this;
        }

        public Builder setCardinality(Cardinality cardinality) {
            d();
            ((Field) this.f4444a).setCardinality(cardinality);
            return this;
        }

        public Builder setCardinalityValue(int i2) {
            d();
            ((Field) this.f4444a).setCardinalityValue(i2);
            return this;
        }

        public Builder setDefaultValue(String str) {
            d();
            ((Field) this.f4444a).setDefaultValue(str);
            return this;
        }

        public Builder setDefaultValueBytes(ByteString byteString) {
            d();
            ((Field) this.f4444a).setDefaultValueBytes(byteString);
            return this;
        }

        public Builder setJsonName(String str) {
            d();
            ((Field) this.f4444a).setJsonName(str);
            return this;
        }

        public Builder setJsonNameBytes(ByteString byteString) {
            d();
            ((Field) this.f4444a).setJsonNameBytes(byteString);
            return this;
        }

        public Builder setKind(Kind kind) {
            d();
            ((Field) this.f4444a).setKind(kind);
            return this;
        }

        public Builder setKindValue(int i2) {
            d();
            ((Field) this.f4444a).setKindValue(i2);
            return this;
        }

        public Builder setName(String str) {
            d();
            ((Field) this.f4444a).setName(str);
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            d();
            ((Field) this.f4444a).setNameBytes(byteString);
            return this;
        }

        public Builder setNumber(int i2) {
            d();
            ((Field) this.f4444a).setNumber(i2);
            return this;
        }

        public Builder setOneofIndex(int i2) {
            d();
            ((Field) this.f4444a).setOneofIndex(i2);
            return this;
        }

        public Builder setOptions(int i2, Option option) {
            d();
            ((Field) this.f4444a).setOptions(i2, option);
            return this;
        }

        public Builder setPacked(boolean z2) {
            d();
            ((Field) this.f4444a).setPacked(z2);
            return this;
        }

        public Builder setTypeUrl(String str) {
            d();
            ((Field) this.f4444a).setTypeUrl(str);
            return this;
        }

        public Builder setTypeUrlBytes(ByteString byteString) {
            d();
            ((Field) this.f4444a).setTypeUrlBytes(byteString);
            return this;
        }

        private Builder() {
            super(Field.DEFAULT_INSTANCE);
        }

        public Builder addOptions(int i2, Option option) {
            d();
            ((Field) this.f4444a).addOptions(i2, option);
            return this;
        }

        public Builder setOptions(int i2, Option.Builder builder) {
            d();
            ((Field) this.f4444a).setOptions(i2, builder.build());
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            d();
            ((Field) this.f4444a).addOptions(builder.build());
            return this;
        }

        public Builder addOptions(int i2, Option.Builder builder) {
            d();
            ((Field) this.f4444a).addOptions(i2, builder.build());
            return this;
        }
    }

    public enum Cardinality implements Internal.EnumLite {
        CARDINALITY_UNKNOWN(0),
        CARDINALITY_OPTIONAL(1),
        CARDINALITY_REQUIRED(2),
        CARDINALITY_REPEATED(3),
        UNRECOGNIZED(-1);

        public static final int CARDINALITY_OPTIONAL_VALUE = 1;
        public static final int CARDINALITY_REPEATED_VALUE = 3;
        public static final int CARDINALITY_REQUIRED_VALUE = 2;
        public static final int CARDINALITY_UNKNOWN_VALUE = 0;
        private static final Internal.EnumLiteMap<Cardinality> internalValueMap = new Internal.EnumLiteMap<Cardinality>() { // from class: androidx.health.platform.client.proto.Field.Cardinality.1
            @Override // androidx.health.platform.client.proto.Internal.EnumLiteMap
            public Cardinality findValueByNumber(int i2) {
                return Cardinality.forNumber(i2);
            }
        };
        private final int value;

        private static final class CardinalityVerifier implements Internal.EnumVerifier {

            /* renamed from: a, reason: collision with root package name */
            static final Internal.EnumVerifier f4434a = new CardinalityVerifier();

            private CardinalityVerifier() {
            }

            @Override // androidx.health.platform.client.proto.Internal.EnumVerifier
            public boolean isInRange(int i2) {
                return Cardinality.forNumber(i2) != null;
            }
        }

        Cardinality(int i2) {
            this.value = i2;
        }

        public static Cardinality forNumber(int i2) {
            if (i2 == 0) {
                return CARDINALITY_UNKNOWN;
            }
            if (i2 == 1) {
                return CARDINALITY_OPTIONAL;
            }
            if (i2 == 2) {
                return CARDINALITY_REQUIRED;
            }
            if (i2 != 3) {
                return null;
            }
            return CARDINALITY_REPEATED;
        }

        public static Internal.EnumLiteMap<Cardinality> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return CardinalityVerifier.f4434a;
        }

        @Override // androidx.health.platform.client.proto.Internal.EnumLite
        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static Cardinality valueOf(int i2) {
            return forNumber(i2);
        }
    }

    public enum Kind implements Internal.EnumLite {
        TYPE_UNKNOWN(0),
        TYPE_DOUBLE(1),
        TYPE_FLOAT(2),
        TYPE_INT64(3),
        TYPE_UINT64(4),
        TYPE_INT32(5),
        TYPE_FIXED64(6),
        TYPE_FIXED32(7),
        TYPE_BOOL(8),
        TYPE_STRING(9),
        TYPE_GROUP(10),
        TYPE_MESSAGE(11),
        TYPE_BYTES(12),
        TYPE_UINT32(13),
        TYPE_ENUM(14),
        TYPE_SFIXED32(15),
        TYPE_SFIXED64(16),
        TYPE_SINT32(17),
        TYPE_SINT64(18),
        UNRECOGNIZED(-1);

        public static final int TYPE_BOOL_VALUE = 8;
        public static final int TYPE_BYTES_VALUE = 12;
        public static final int TYPE_DOUBLE_VALUE = 1;
        public static final int TYPE_ENUM_VALUE = 14;
        public static final int TYPE_FIXED32_VALUE = 7;
        public static final int TYPE_FIXED64_VALUE = 6;
        public static final int TYPE_FLOAT_VALUE = 2;
        public static final int TYPE_GROUP_VALUE = 10;
        public static final int TYPE_INT32_VALUE = 5;
        public static final int TYPE_INT64_VALUE = 3;
        public static final int TYPE_MESSAGE_VALUE = 11;
        public static final int TYPE_SFIXED32_VALUE = 15;
        public static final int TYPE_SFIXED64_VALUE = 16;
        public static final int TYPE_SINT32_VALUE = 17;
        public static final int TYPE_SINT64_VALUE = 18;
        public static final int TYPE_STRING_VALUE = 9;
        public static final int TYPE_UINT32_VALUE = 13;
        public static final int TYPE_UINT64_VALUE = 4;
        public static final int TYPE_UNKNOWN_VALUE = 0;
        private static final Internal.EnumLiteMap<Kind> internalValueMap = new Internal.EnumLiteMap<Kind>() { // from class: androidx.health.platform.client.proto.Field.Kind.1
            @Override // androidx.health.platform.client.proto.Internal.EnumLiteMap
            public Kind findValueByNumber(int i2) {
                return Kind.forNumber(i2);
            }
        };
        private final int value;

        private static final class KindVerifier implements Internal.EnumVerifier {

            /* renamed from: a, reason: collision with root package name */
            static final Internal.EnumVerifier f4435a = new KindVerifier();

            private KindVerifier() {
            }

            @Override // androidx.health.platform.client.proto.Internal.EnumVerifier
            public boolean isInRange(int i2) {
                return Kind.forNumber(i2) != null;
            }
        }

        Kind(int i2) {
            this.value = i2;
        }

        public static Kind forNumber(int i2) {
            switch (i2) {
                case 0:
                    return TYPE_UNKNOWN;
                case 1:
                    return TYPE_DOUBLE;
                case 2:
                    return TYPE_FLOAT;
                case 3:
                    return TYPE_INT64;
                case 4:
                    return TYPE_UINT64;
                case 5:
                    return TYPE_INT32;
                case 6:
                    return TYPE_FIXED64;
                case 7:
                    return TYPE_FIXED32;
                case 8:
                    return TYPE_BOOL;
                case 9:
                    return TYPE_STRING;
                case 10:
                    return TYPE_GROUP;
                case 11:
                    return TYPE_MESSAGE;
                case 12:
                    return TYPE_BYTES;
                case 13:
                    return TYPE_UINT32;
                case 14:
                    return TYPE_ENUM;
                case 15:
                    return TYPE_SFIXED32;
                case 16:
                    return TYPE_SFIXED64;
                case 17:
                    return TYPE_SINT32;
                case 18:
                    return TYPE_SINT64;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<Kind> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return KindVerifier.f4435a;
        }

        @Override // androidx.health.platform.client.proto.Internal.EnumLite
        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static Kind valueOf(int i2) {
            return forNumber(i2);
        }
    }

    static {
        Field field = new Field();
        DEFAULT_INSTANCE = field;
        GeneratedMessageLite.T(Field.class, field);
    }

    private Field() {
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
    public void clearCardinality() {
        this.cardinality_ = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDefaultValue() {
        this.defaultValue_ = getDefaultInstance().getDefaultValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearJsonName() {
        this.jsonName_ = getDefaultInstance().getJsonName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearKind() {
        this.kind_ = 0;
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
    public void clearOneofIndex() {
        this.oneofIndex_ = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearOptions() {
        this.options_ = GeneratedMessageLite.r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearPacked() {
        this.packed_ = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearTypeUrl() {
        this.typeUrl_ = getDefaultInstance().getTypeUrl();
    }

    private void ensureOptionsIsMutable() {
        Internal.ProtobufList<Option> protobufList = this.options_;
        if (protobufList.isModifiable()) {
            return;
        }
        this.options_ = GeneratedMessageLite.C(protobufList);
    }

    public static Field getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.m();
    }

    public static Field parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Field) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
    }

    public static Field parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
    }

    public static Parser<Field> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeOptions(int i2) {
        ensureOptionsIsMutable();
        this.options_.remove(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCardinality(Cardinality cardinality) {
        this.cardinality_ = cardinality.getNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCardinalityValue(int i2) {
        this.cardinality_ = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDefaultValue(String str) {
        str.getClass();
        this.defaultValue_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDefaultValueBytes(ByteString byteString) {
        AbstractMessageLite.b(byteString);
        this.defaultValue_ = byteString.toStringUtf8();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setJsonName(String str) {
        str.getClass();
        this.jsonName_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setJsonNameBytes(ByteString byteString) {
        AbstractMessageLite.b(byteString);
        this.jsonName_ = byteString.toStringUtf8();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setKind(Kind kind) {
        this.kind_ = kind.getNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setKindValue(int i2) {
        this.kind_ = i2;
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
    public void setOneofIndex(int i2) {
        this.oneofIndex_ = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.set(i2, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPacked(boolean z2) {
        this.packed_ = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTypeUrl(String str) {
        str.getClass();
        this.typeUrl_ = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTypeUrlBytes(ByteString byteString) {
        AbstractMessageLite.b(byteString);
        this.typeUrl_ = byteString.toStringUtf8();
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public Cardinality getCardinality() {
        Cardinality cardinalityForNumber = Cardinality.forNumber(this.cardinality_);
        return cardinalityForNumber == null ? Cardinality.UNRECOGNIZED : cardinalityForNumber;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public int getCardinalityValue() {
        return this.cardinality_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public String getDefaultValue() {
        return this.defaultValue_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public ByteString getDefaultValueBytes() {
        return ByteString.copyFromUtf8(this.defaultValue_);
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public String getJsonName() {
        return this.jsonName_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public ByteString getJsonNameBytes() {
        return ByteString.copyFromUtf8(this.jsonName_);
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public Kind getKind() {
        Kind kindForNumber = Kind.forNumber(this.kind_);
        return kindForNumber == null ? Kind.UNRECOGNIZED : kindForNumber;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public int getKindValue() {
        return this.kind_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public String getName() {
        return this.name_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public int getNumber() {
        return this.number_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public int getOneofIndex() {
        return this.oneofIndex_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public Option getOptions(int i2) {
        return this.options_.get(i2);
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public int getOptionsCount() {
        return this.options_.size();
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public List<Option> getOptionsList() {
        return this.options_;
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.options_.get(i2);
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public boolean getPacked() {
        return this.packed_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public String getTypeUrl() {
        return this.typeUrl_;
    }

    @Override // androidx.health.platform.client.proto.FieldOrBuilder
    public ByteString getTypeUrlBytes() {
        return ByteString.copyFromUtf8(this.typeUrl_);
    }

    @Override // androidx.health.platform.client.proto.GeneratedMessageLite
    protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.f4433a[methodToInvoke.ordinal()]) {
            case 1:
                return new Field();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0000\n\u0000\u0000\u0001\u000b\n\u0000\u0001\u0000\u0001\f\u0002\f\u0003\u0004\u0004Ȉ\u0006Ȉ\u0007\u0004\b\u0007\t\u001b\nȈ\u000bȈ", new Object[]{"kind_", "cardinality_", "number_", "name_", "typeUrl_", "oneofIndex_", "packed_", "options_", Option.class, "jsonName_", "defaultValue_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<Field> defaultInstanceBasedParser = PARSER;
                if (defaultInstanceBasedParser == null) {
                    synchronized (Field.class) {
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

    public static Builder newBuilder(Field field) {
        return (Builder) DEFAULT_INSTANCE.n(field);
    }

    public static Field parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Field) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Field parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static Field parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(i2, option);
    }

    public static Field parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Field parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
    }

    public static Field parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Field parseFrom(InputStream inputStream) throws IOException {
        return (Field) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
    }

    public static Field parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Field) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Field parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Field) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Field parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Field) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }
}
