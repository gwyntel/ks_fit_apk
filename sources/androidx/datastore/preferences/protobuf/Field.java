package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.Option;
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
    private Internal.ProtobufList<Option> options_ = GeneratedMessageLite.n();
    private String jsonName_ = "";
    private String defaultValue_ = "";

    /* renamed from: androidx.datastore.preferences.protobuf.Field$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f3919a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f3919a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f3919a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f3919a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f3919a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f3919a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f3919a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f3919a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
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
            ((Field) this.f3930a).addAllOptions(iterable);
            return this;
        }

        public Builder addOptions(Option option) {
            d();
            ((Field) this.f3930a).addOptions(option);
            return this;
        }

        public Builder clearCardinality() {
            d();
            ((Field) this.f3930a).clearCardinality();
            return this;
        }

        public Builder clearDefaultValue() {
            d();
            ((Field) this.f3930a).clearDefaultValue();
            return this;
        }

        public Builder clearJsonName() {
            d();
            ((Field) this.f3930a).clearJsonName();
            return this;
        }

        public Builder clearKind() {
            d();
            ((Field) this.f3930a).clearKind();
            return this;
        }

        public Builder clearName() {
            d();
            ((Field) this.f3930a).clearName();
            return this;
        }

        public Builder clearNumber() {
            d();
            ((Field) this.f3930a).clearNumber();
            return this;
        }

        public Builder clearOneofIndex() {
            d();
            ((Field) this.f3930a).clearOneofIndex();
            return this;
        }

        public Builder clearOptions() {
            d();
            ((Field) this.f3930a).clearOptions();
            return this;
        }

        public Builder clearPacked() {
            d();
            ((Field) this.f3930a).clearPacked();
            return this;
        }

        public Builder clearTypeUrl() {
            d();
            ((Field) this.f3930a).clearTypeUrl();
            return this;
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public Cardinality getCardinality() {
            return ((Field) this.f3930a).getCardinality();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public int getCardinalityValue() {
            return ((Field) this.f3930a).getCardinalityValue();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public String getDefaultValue() {
            return ((Field) this.f3930a).getDefaultValue();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public ByteString getDefaultValueBytes() {
            return ((Field) this.f3930a).getDefaultValueBytes();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public String getJsonName() {
            return ((Field) this.f3930a).getJsonName();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public ByteString getJsonNameBytes() {
            return ((Field) this.f3930a).getJsonNameBytes();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public Kind getKind() {
            return ((Field) this.f3930a).getKind();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public int getKindValue() {
            return ((Field) this.f3930a).getKindValue();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public String getName() {
            return ((Field) this.f3930a).getName();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public ByteString getNameBytes() {
            return ((Field) this.f3930a).getNameBytes();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public int getNumber() {
            return ((Field) this.f3930a).getNumber();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public int getOneofIndex() {
            return ((Field) this.f3930a).getOneofIndex();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public Option getOptions(int i2) {
            return ((Field) this.f3930a).getOptions(i2);
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public int getOptionsCount() {
            return ((Field) this.f3930a).getOptionsCount();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Field) this.f3930a).getOptionsList());
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public boolean getPacked() {
            return ((Field) this.f3930a).getPacked();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public String getTypeUrl() {
            return ((Field) this.f3930a).getTypeUrl();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
        public ByteString getTypeUrlBytes() {
            return ((Field) this.f3930a).getTypeUrlBytes();
        }

        public Builder removeOptions(int i2) {
            d();
            ((Field) this.f3930a).removeOptions(i2);
            return this;
        }

        public Builder setCardinality(Cardinality cardinality) {
            d();
            ((Field) this.f3930a).setCardinality(cardinality);
            return this;
        }

        public Builder setCardinalityValue(int i2) {
            d();
            ((Field) this.f3930a).setCardinalityValue(i2);
            return this;
        }

        public Builder setDefaultValue(String str) {
            d();
            ((Field) this.f3930a).setDefaultValue(str);
            return this;
        }

        public Builder setDefaultValueBytes(ByteString byteString) {
            d();
            ((Field) this.f3930a).setDefaultValueBytes(byteString);
            return this;
        }

        public Builder setJsonName(String str) {
            d();
            ((Field) this.f3930a).setJsonName(str);
            return this;
        }

        public Builder setJsonNameBytes(ByteString byteString) {
            d();
            ((Field) this.f3930a).setJsonNameBytes(byteString);
            return this;
        }

        public Builder setKind(Kind kind) {
            d();
            ((Field) this.f3930a).setKind(kind);
            return this;
        }

        public Builder setKindValue(int i2) {
            d();
            ((Field) this.f3930a).setKindValue(i2);
            return this;
        }

        public Builder setName(String str) {
            d();
            ((Field) this.f3930a).setName(str);
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            d();
            ((Field) this.f3930a).setNameBytes(byteString);
            return this;
        }

        public Builder setNumber(int i2) {
            d();
            ((Field) this.f3930a).setNumber(i2);
            return this;
        }

        public Builder setOneofIndex(int i2) {
            d();
            ((Field) this.f3930a).setOneofIndex(i2);
            return this;
        }

        public Builder setOptions(int i2, Option option) {
            d();
            ((Field) this.f3930a).setOptions(i2, option);
            return this;
        }

        public Builder setPacked(boolean z2) {
            d();
            ((Field) this.f3930a).setPacked(z2);
            return this;
        }

        public Builder setTypeUrl(String str) {
            d();
            ((Field) this.f3930a).setTypeUrl(str);
            return this;
        }

        public Builder setTypeUrlBytes(ByteString byteString) {
            d();
            ((Field) this.f3930a).setTypeUrlBytes(byteString);
            return this;
        }

        private Builder() {
            super(Field.DEFAULT_INSTANCE);
        }

        public Builder addOptions(int i2, Option option) {
            d();
            ((Field) this.f3930a).addOptions(i2, option);
            return this;
        }

        public Builder setOptions(int i2, Option.Builder builder) {
            d();
            ((Field) this.f3930a).setOptions(i2, builder);
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            d();
            ((Field) this.f3930a).addOptions(builder);
            return this;
        }

        public Builder addOptions(int i2, Option.Builder builder) {
            d();
            ((Field) this.f3930a).addOptions(i2, builder);
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
        private static final Internal.EnumLiteMap<Cardinality> internalValueMap = new Internal.EnumLiteMap<Cardinality>() { // from class: androidx.datastore.preferences.protobuf.Field.Cardinality.1
            @Override // androidx.datastore.preferences.protobuf.Internal.EnumLiteMap
            public Cardinality findValueByNumber(int i2) {
                return Cardinality.forNumber(i2);
            }
        };
        private final int value;

        private static final class CardinalityVerifier implements Internal.EnumVerifier {

            /* renamed from: a, reason: collision with root package name */
            static final Internal.EnumVerifier f3920a = new CardinalityVerifier();

            private CardinalityVerifier() {
            }

            @Override // androidx.datastore.preferences.protobuf.Internal.EnumVerifier
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
            return CardinalityVerifier.f3920a;
        }

        @Override // androidx.datastore.preferences.protobuf.Internal.EnumLite
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
        private static final Internal.EnumLiteMap<Kind> internalValueMap = new Internal.EnumLiteMap<Kind>() { // from class: androidx.datastore.preferences.protobuf.Field.Kind.1
            @Override // androidx.datastore.preferences.protobuf.Internal.EnumLiteMap
            public Kind findValueByNumber(int i2) {
                return Kind.forNumber(i2);
            }
        };
        private final int value;

        private static final class KindVerifier implements Internal.EnumVerifier {

            /* renamed from: a, reason: collision with root package name */
            static final Internal.EnumVerifier f3921a = new KindVerifier();

            private KindVerifier() {
            }

            @Override // androidx.datastore.preferences.protobuf.Internal.EnumVerifier
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
            return KindVerifier.f3921a;
        }

        @Override // androidx.datastore.preferences.protobuf.Internal.EnumLite
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
        GeneratedMessageLite.L(Field.class, field);
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
        this.options_ = GeneratedMessageLite.n();
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
        if (this.options_.isModifiable()) {
            return;
        }
        this.options_ = GeneratedMessageLite.u(this.options_);
    }

    public static Field getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.i();
    }

    public static Field parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Field) GeneratedMessageLite.w(DEFAULT_INSTANCE, inputStream);
    }

    public static Field parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.E(DEFAULT_INSTANCE, byteBuffer);
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
        cardinality.getClass();
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
        byteString.getClass();
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
        byteString.getClass();
        AbstractMessageLite.b(byteString);
        this.jsonName_ = byteString.toStringUtf8();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setKind(Kind kind) {
        kind.getClass();
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
        byteString.getClass();
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
        byteString.getClass();
        AbstractMessageLite.b(byteString);
        this.typeUrl_ = byteString.toStringUtf8();
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public Cardinality getCardinality() {
        Cardinality cardinalityForNumber = Cardinality.forNumber(this.cardinality_);
        return cardinalityForNumber == null ? Cardinality.UNRECOGNIZED : cardinalityForNumber;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public int getCardinalityValue() {
        return this.cardinality_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public String getDefaultValue() {
        return this.defaultValue_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public ByteString getDefaultValueBytes() {
        return ByteString.copyFromUtf8(this.defaultValue_);
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public String getJsonName() {
        return this.jsonName_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public ByteString getJsonNameBytes() {
        return ByteString.copyFromUtf8(this.jsonName_);
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public Kind getKind() {
        Kind kindForNumber = Kind.forNumber(this.kind_);
        return kindForNumber == null ? Kind.UNRECOGNIZED : kindForNumber;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public int getKindValue() {
        return this.kind_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public String getName() {
        return this.name_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public int getNumber() {
        return this.number_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public int getOneofIndex() {
        return this.oneofIndex_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public Option getOptions(int i2) {
        return this.options_.get(i2);
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public int getOptionsCount() {
        return this.options_.size();
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public List<Option> getOptionsList() {
        return this.options_;
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.options_.get(i2);
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public boolean getPacked() {
        return this.packed_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public String getTypeUrl() {
        return this.typeUrl_;
    }

    @Override // androidx.datastore.preferences.protobuf.FieldOrBuilder
    public ByteString getTypeUrlBytes() {
        return ByteString.copyFromUtf8(this.typeUrl_);
    }

    @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite
    protected final Object m(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.f3919a[methodToInvoke.ordinal()]) {
            case 1:
                return new Field();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return GeneratedMessageLite.v(DEFAULT_INSTANCE, "\u0000\n\u0000\u0000\u0001\u000b\n\u0000\u0001\u0000\u0001\f\u0002\f\u0003\u0004\u0004Ȉ\u0006Ȉ\u0007\u0004\b\u0007\t\u001b\nȈ\u000bȈ", new Object[]{"kind_", "cardinality_", "number_", "name_", "typeUrl_", "oneofIndex_", "packed_", "options_", Option.class, "jsonName_", "defaultValue_"});
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
        return (Builder) DEFAULT_INSTANCE.j(field);
    }

    public static Field parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Field) GeneratedMessageLite.x(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Field parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.F(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
    }

    public static Field parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.y(DEFAULT_INSTANCE, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option option) {
        option.getClass();
        ensureOptionsIsMutable();
        this.options_.add(i2, option);
    }

    public static Field parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.z(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOptions(int i2, Option.Builder builder) {
        ensureOptionsIsMutable();
        this.options_.set(i2, builder.build());
    }

    public static Field parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.G(DEFAULT_INSTANCE, bArr);
    }

    public static Field parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.H(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(Option.Builder builder) {
        ensureOptionsIsMutable();
        this.options_.add(builder.build());
    }

    public static Field parseFrom(InputStream inputStream) throws IOException {
        return (Field) GeneratedMessageLite.C(DEFAULT_INSTANCE, inputStream);
    }

    public static Field parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Field) GeneratedMessageLite.D(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addOptions(int i2, Option.Builder builder) {
        ensureOptionsIsMutable();
        this.options_.add(i2, builder.build());
    }

    public static Field parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Field) GeneratedMessageLite.A(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Field parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Field) GeneratedMessageLite.B(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }
}
