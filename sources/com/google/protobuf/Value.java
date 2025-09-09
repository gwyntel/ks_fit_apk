package com.google.protobuf;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.ListValue;
import com.google.protobuf.Struct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Value extends GeneratedMessageV3 implements ValueOrBuilder {
    public static final int BOOL_VALUE_FIELD_NUMBER = 4;
    public static final int LIST_VALUE_FIELD_NUMBER = 6;
    public static final int NULL_VALUE_FIELD_NUMBER = 1;
    public static final int NUMBER_VALUE_FIELD_NUMBER = 2;
    public static final int STRING_VALUE_FIELD_NUMBER = 3;
    public static final int STRUCT_VALUE_FIELD_NUMBER = 5;
    private static final long serialVersionUID = 0;
    private int kindCase_;
    private Object kind_;
    private byte memoizedIsInitialized;
    private static final Value DEFAULT_INSTANCE = new Value();
    private static final Parser<Value> PARSER = new AbstractParser<Value>() { // from class: com.google.protobuf.Value.1
        @Override // com.google.protobuf.Parser
        public Value parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            Builder builderNewBuilder = Value.newBuilder();
            try {
                builderNewBuilder.mergeFrom(codedInputStream, extensionRegistryLite);
                return builderNewBuilder.buildPartial();
            } catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(builderNewBuilder.buildPartial());
            } catch (UninitializedMessageException e3) {
                throw e3.asInvalidProtocolBufferException().setUnfinishedMessage(builderNewBuilder.buildPartial());
            } catch (IOException e4) {
                throw new InvalidProtocolBufferException(e4).setUnfinishedMessage(builderNewBuilder.buildPartial());
            }
        }
    };

    /* renamed from: com.google.protobuf.Value$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f15367a;

        static {
            int[] iArr = new int[KindCase.values().length];
            f15367a = iArr;
            try {
                iArr[KindCase.NULL_VALUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f15367a[KindCase.NUMBER_VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f15367a[KindCase.STRING_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f15367a[KindCase.BOOL_VALUE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f15367a[KindCase.STRUCT_VALUE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f15367a[KindCase.LIST_VALUE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f15367a[KindCase.KIND_NOT_SET.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public enum KindCase implements Internal.EnumLite, AbstractMessageLite.InternalOneOfEnum {
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

        @Override // com.google.protobuf.Internal.EnumLite
        public int getNumber() {
            return this.value;
        }

        @Deprecated
        public static KindCase valueOf(int i2) {
            return forNumber(i2);
        }
    }

    public static Value getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return StructProto.f15336e;
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Value parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Value) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
    }

    public static Value parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteBuffer);
    }

    public static Parser<Value> parser() {
        return PARSER;
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Value)) {
            return super.equals(obj);
        }
        Value value = (Value) obj;
        if (!getKindCase().equals(value.getKindCase())) {
            return false;
        }
        switch (this.kindCase_) {
            case 1:
                if (getNullValueValue() != value.getNullValueValue()) {
                    return false;
                }
                break;
            case 2:
                if (Double.doubleToLongBits(getNumberValue()) != Double.doubleToLongBits(value.getNumberValue())) {
                    return false;
                }
                break;
            case 3:
                if (!getStringValue().equals(value.getStringValue())) {
                    return false;
                }
                break;
            case 4:
                if (getBoolValue() != value.getBoolValue()) {
                    return false;
                }
                break;
            case 5:
                if (!getStructValue().equals(value.getStructValue())) {
                    return false;
                }
                break;
            case 6:
                if (!getListValue().equals(value.getListValue())) {
                    return false;
                }
                break;
        }
        return getUnknownFields().equals(value.getUnknownFields());
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean getBoolValue() {
        if (this.kindCase_ == 4) {
            return ((Boolean) this.kind_).booleanValue();
        }
        return false;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public KindCase getKindCase() {
        return KindCase.forNumber(this.kindCase_);
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public ListValue getListValue() {
        return this.kindCase_ == 6 ? (ListValue) this.kind_ : ListValue.getDefaultInstance();
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public ListValueOrBuilder getListValueOrBuilder() {
        return this.kindCase_ == 6 ? (ListValue) this.kind_ : ListValue.getDefaultInstance();
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public NullValue getNullValue() {
        if (this.kindCase_ != 1) {
            return NullValue.NULL_VALUE;
        }
        NullValue nullValueForNumber = NullValue.forNumber(((Integer) this.kind_).intValue());
        return nullValueForNumber == null ? NullValue.UNRECOGNIZED : nullValueForNumber;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public int getNullValueValue() {
        if (this.kindCase_ == 1) {
            return ((Integer) this.kind_).intValue();
        }
        return 0;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public double getNumberValue() {
        if (this.kindCase_ == 2) {
            return ((Double) this.kind_).doubleValue();
        }
        return 0.0d;
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Parser<Value> getParserForType() {
        return PARSER;
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
    public int getSerializedSize() {
        int i2 = this.memoizedSize;
        if (i2 != -1) {
            return i2;
        }
        int iComputeEnumSize = this.kindCase_ == 1 ? CodedOutputStream.computeEnumSize(1, ((Integer) this.kind_).intValue()) : 0;
        if (this.kindCase_ == 2) {
            iComputeEnumSize += CodedOutputStream.computeDoubleSize(2, ((Double) this.kind_).doubleValue());
        }
        if (this.kindCase_ == 3) {
            iComputeEnumSize += GeneratedMessageV3.computeStringSize(3, this.kind_);
        }
        if (this.kindCase_ == 4) {
            iComputeEnumSize += CodedOutputStream.computeBoolSize(4, ((Boolean) this.kind_).booleanValue());
        }
        if (this.kindCase_ == 5) {
            iComputeEnumSize += CodedOutputStream.computeMessageSize(5, (Struct) this.kind_);
        }
        if (this.kindCase_ == 6) {
            iComputeEnumSize += CodedOutputStream.computeMessageSize(6, (ListValue) this.kind_);
        }
        int serializedSize = iComputeEnumSize + getUnknownFields().getSerializedSize();
        this.memoizedSize = serializedSize;
        return serializedSize;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public String getStringValue() {
        String str = this.kindCase_ == 3 ? this.kind_ : "";
        if (str instanceof String) {
            return (String) str;
        }
        String stringUtf8 = ((ByteString) str).toStringUtf8();
        if (this.kindCase_ == 3) {
            this.kind_ = stringUtf8;
        }
        return stringUtf8;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public ByteString getStringValueBytes() {
        String str = this.kindCase_ == 3 ? this.kind_ : "";
        if (!(str instanceof String)) {
            return (ByteString) str;
        }
        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) str);
        if (this.kindCase_ == 3) {
            this.kind_ = byteStringCopyFromUtf8;
        }
        return byteStringCopyFromUtf8;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public Struct getStructValue() {
        return this.kindCase_ == 5 ? (Struct) this.kind_ : Struct.getDefaultInstance();
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public StructOrBuilder getStructValueOrBuilder() {
        return this.kindCase_ == 5 ? (Struct) this.kind_ : Struct.getDefaultInstance();
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean hasBoolValue() {
        return this.kindCase_ == 4;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean hasListValue() {
        return this.kindCase_ == 6;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean hasNullValue() {
        return this.kindCase_ == 1;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean hasNumberValue() {
        return this.kindCase_ == 2;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean hasStringValue() {
        return this.kindCase_ == 3;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean hasStructValue() {
        return this.kindCase_ == 5;
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
    public int hashCode() {
        int i2;
        int nullValueValue;
        int i3 = this.memoizedHashCode;
        if (i3 != 0) {
            return i3;
        }
        int iHashCode = 779 + getDescriptor().hashCode();
        switch (this.kindCase_) {
            case 1:
                i2 = ((iHashCode * 37) + 1) * 53;
                nullValueValue = getNullValueValue();
                break;
            case 2:
                i2 = ((iHashCode * 37) + 2) * 53;
                nullValueValue = Internal.hashLong(Double.doubleToLongBits(getNumberValue()));
                break;
            case 3:
                i2 = ((iHashCode * 37) + 3) * 53;
                nullValueValue = getStringValue().hashCode();
                break;
            case 4:
                i2 = ((iHashCode * 37) + 4) * 53;
                nullValueValue = Internal.hashBoolean(getBoolValue());
                break;
            case 5:
                i2 = ((iHashCode * 37) + 5) * 53;
                nullValueValue = getStructValue().hashCode();
                break;
            case 6:
                i2 = ((iHashCode * 37) + 6) * 53;
                nullValueValue = getListValue().hashCode();
                break;
            default:
                int iHashCode2 = (iHashCode * 29) + getUnknownFields().hashCode();
                this.memoizedHashCode = iHashCode2;
                return iHashCode2;
        }
        iHashCode = i2 + nullValueValue;
        int iHashCode22 = (iHashCode * 29) + getUnknownFields().hashCode();
        this.memoizedHashCode = iHashCode22;
        return iHashCode22;
    }

    @Override // com.google.protobuf.GeneratedMessageV3
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return StructProto.f15337f.ensureFieldAccessorsInitialized(Value.class, Builder.class);
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        byte b2 = this.memoizedIsInitialized;
        if (b2 == 1) {
            return true;
        }
        if (b2 == 0) {
            return false;
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // com.google.protobuf.GeneratedMessageV3
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
        return new Value();
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.kindCase_ == 1) {
            codedOutputStream.writeEnum(1, ((Integer) this.kind_).intValue());
        }
        if (this.kindCase_ == 2) {
            codedOutputStream.writeDouble(2, ((Double) this.kind_).doubleValue());
        }
        if (this.kindCase_ == 3) {
            GeneratedMessageV3.writeString(codedOutputStream, 3, this.kind_);
        }
        if (this.kindCase_ == 4) {
            codedOutputStream.writeBool(4, ((Boolean) this.kind_).booleanValue());
        }
        if (this.kindCase_ == 5) {
            codedOutputStream.writeMessage(5, (Struct) this.kind_);
        }
        if (this.kindCase_ == 6) {
            codedOutputStream.writeMessage(6, (ListValue) this.kind_);
        }
        getUnknownFields().writeTo(codedOutputStream);
    }

    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ValueOrBuilder {
        private int bitField0_;
        private int kindCase_;
        private Object kind_;
        private SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> listValueBuilder_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> structValueBuilder_;

        private void buildPartial0(Value value) {
        }

        private void buildPartialOneofs(Value value) {
            SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> singleFieldBuilderV3;
            SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> singleFieldBuilderV32;
            value.kindCase_ = this.kindCase_;
            value.kind_ = this.kind_;
            if (this.kindCase_ == 5 && (singleFieldBuilderV32 = this.structValueBuilder_) != null) {
                value.kind_ = singleFieldBuilderV32.build();
            }
            if (this.kindCase_ != 6 || (singleFieldBuilderV3 = this.listValueBuilder_) == null) {
                return;
            }
            value.kind_ = singleFieldBuilderV3.build();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return StructProto.f15336e;
        }

        private SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> getListValueFieldBuilder() {
            if (this.listValueBuilder_ == null) {
                if (this.kindCase_ != 6) {
                    this.kind_ = ListValue.getDefaultInstance();
                }
                this.listValueBuilder_ = new SingleFieldBuilderV3<>((ListValue) this.kind_, i(), m());
                this.kind_ = null;
            }
            this.kindCase_ = 6;
            p();
            return this.listValueBuilder_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getStructValueFieldBuilder() {
            if (this.structValueBuilder_ == null) {
                if (this.kindCase_ != 5) {
                    this.kind_ = Struct.getDefaultInstance();
                }
                this.structValueBuilder_ = new SingleFieldBuilderV3<>((Struct) this.kind_, i(), m());
                this.kind_ = null;
            }
            this.kindCase_ = 5;
            p();
            return this.structValueBuilder_;
        }

        public Builder clearBoolValue() {
            if (this.kindCase_ == 4) {
                this.kindCase_ = 0;
                this.kind_ = null;
                p();
            }
            return this;
        }

        public Builder clearKind() {
            this.kindCase_ = 0;
            this.kind_ = null;
            p();
            return this;
        }

        public Builder clearListValue() {
            SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> singleFieldBuilderV3 = this.listValueBuilder_;
            if (singleFieldBuilderV3 != null) {
                if (this.kindCase_ == 6) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                }
                singleFieldBuilderV3.clear();
            } else if (this.kindCase_ == 6) {
                this.kindCase_ = 0;
                this.kind_ = null;
                p();
            }
            return this;
        }

        public Builder clearNullValue() {
            if (this.kindCase_ == 1) {
                this.kindCase_ = 0;
                this.kind_ = null;
                p();
            }
            return this;
        }

        public Builder clearNumberValue() {
            if (this.kindCase_ == 2) {
                this.kindCase_ = 0;
                this.kind_ = null;
                p();
            }
            return this;
        }

        public Builder clearStringValue() {
            if (this.kindCase_ == 3) {
                this.kindCase_ = 0;
                this.kind_ = null;
                p();
            }
            return this;
        }

        public Builder clearStructValue() {
            SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> singleFieldBuilderV3 = this.structValueBuilder_;
            if (singleFieldBuilderV3 != null) {
                if (this.kindCase_ == 5) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                }
                singleFieldBuilderV3.clear();
            } else if (this.kindCase_ == 5) {
                this.kindCase_ = 0;
                this.kind_ = null;
                p();
            }
            return this;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean getBoolValue() {
            if (this.kindCase_ == 4) {
                return ((Boolean) this.kind_).booleanValue();
            }
            return false;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
        public Descriptors.Descriptor getDescriptorForType() {
            return StructProto.f15336e;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public KindCase getKindCase() {
            return KindCase.forNumber(this.kindCase_);
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public ListValue getListValue() {
            SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> singleFieldBuilderV3 = this.listValueBuilder_;
            return singleFieldBuilderV3 == null ? this.kindCase_ == 6 ? (ListValue) this.kind_ : ListValue.getDefaultInstance() : this.kindCase_ == 6 ? (ListValue) singleFieldBuilderV3.getMessage() : ListValue.getDefaultInstance();
        }

        public ListValue.Builder getListValueBuilder() {
            return (ListValue.Builder) getListValueFieldBuilder().getBuilder();
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public ListValueOrBuilder getListValueOrBuilder() {
            SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> singleFieldBuilderV3;
            int i2 = this.kindCase_;
            return (i2 != 6 || (singleFieldBuilderV3 = this.listValueBuilder_) == null) ? i2 == 6 ? (ListValue) this.kind_ : ListValue.getDefaultInstance() : (ListValueOrBuilder) singleFieldBuilderV3.getMessageOrBuilder();
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public NullValue getNullValue() {
            if (this.kindCase_ != 1) {
                return NullValue.NULL_VALUE;
            }
            NullValue nullValueForNumber = NullValue.forNumber(((Integer) this.kind_).intValue());
            return nullValueForNumber == null ? NullValue.UNRECOGNIZED : nullValueForNumber;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public int getNullValueValue() {
            if (this.kindCase_ == 1) {
                return ((Integer) this.kind_).intValue();
            }
            return 0;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public double getNumberValue() {
            if (this.kindCase_ == 2) {
                return ((Double) this.kind_).doubleValue();
            }
            return 0.0d;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public String getStringValue() {
            String str = this.kindCase_ == 3 ? this.kind_ : "";
            if (str instanceof String) {
                return (String) str;
            }
            String stringUtf8 = ((ByteString) str).toStringUtf8();
            if (this.kindCase_ == 3) {
                this.kind_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public ByteString getStringValueBytes() {
            String str = this.kindCase_ == 3 ? this.kind_ : "";
            if (!(str instanceof String)) {
                return (ByteString) str;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) str);
            if (this.kindCase_ == 3) {
                this.kind_ = byteStringCopyFromUtf8;
            }
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public Struct getStructValue() {
            SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> singleFieldBuilderV3 = this.structValueBuilder_;
            return singleFieldBuilderV3 == null ? this.kindCase_ == 5 ? (Struct) this.kind_ : Struct.getDefaultInstance() : this.kindCase_ == 5 ? (Struct) singleFieldBuilderV3.getMessage() : Struct.getDefaultInstance();
        }

        public Struct.Builder getStructValueBuilder() {
            return (Struct.Builder) getStructValueFieldBuilder().getBuilder();
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public StructOrBuilder getStructValueOrBuilder() {
            SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> singleFieldBuilderV3;
            int i2 = this.kindCase_;
            return (i2 != 5 || (singleFieldBuilderV3 = this.structValueBuilder_) == null) ? i2 == 5 ? (Struct) this.kind_ : Struct.getDefaultInstance() : (StructOrBuilder) singleFieldBuilderV3.getMessageOrBuilder();
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean hasBoolValue() {
            return this.kindCase_ == 4;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean hasListValue() {
            return this.kindCase_ == 6;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean hasNullValue() {
            return this.kindCase_ == 1;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean hasNumberValue() {
            return this.kindCase_ == 2;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean hasStringValue() {
            return this.kindCase_ == 3;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean hasStructValue() {
            return this.kindCase_ == 5;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder
        protected GeneratedMessageV3.FieldAccessorTable j() {
            return StructProto.f15337f.ensureFieldAccessorsInitialized(Value.class, Builder.class);
        }

        public Builder mergeListValue(ListValue listValue) {
            SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> singleFieldBuilderV3 = this.listValueBuilder_;
            if (singleFieldBuilderV3 == null) {
                if (this.kindCase_ != 6 || this.kind_ == ListValue.getDefaultInstance()) {
                    this.kind_ = listValue;
                } else {
                    this.kind_ = ListValue.newBuilder((ListValue) this.kind_).mergeFrom(listValue).buildPartial();
                }
                p();
            } else if (this.kindCase_ == 6) {
                singleFieldBuilderV3.mergeFrom(listValue);
            } else {
                singleFieldBuilderV3.setMessage(listValue);
            }
            this.kindCase_ = 6;
            return this;
        }

        public Builder mergeStructValue(Struct struct) {
            SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> singleFieldBuilderV3 = this.structValueBuilder_;
            if (singleFieldBuilderV3 == null) {
                if (this.kindCase_ != 5 || this.kind_ == Struct.getDefaultInstance()) {
                    this.kind_ = struct;
                } else {
                    this.kind_ = Struct.newBuilder((Struct) this.kind_).mergeFrom(struct).buildPartial();
                }
                p();
            } else if (this.kindCase_ == 5) {
                singleFieldBuilderV3.mergeFrom(struct);
            } else {
                singleFieldBuilderV3.setMessage(struct);
            }
            this.kindCase_ = 5;
            return this;
        }

        public Builder setBoolValue(boolean z2) {
            this.kindCase_ = 4;
            this.kind_ = Boolean.valueOf(z2);
            p();
            return this;
        }

        public Builder setListValue(ListValue listValue) {
            SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> singleFieldBuilderV3 = this.listValueBuilder_;
            if (singleFieldBuilderV3 == null) {
                listValue.getClass();
                this.kind_ = listValue;
                p();
            } else {
                singleFieldBuilderV3.setMessage(listValue);
            }
            this.kindCase_ = 6;
            return this;
        }

        public Builder setNullValue(NullValue nullValue) {
            nullValue.getClass();
            this.kindCase_ = 1;
            this.kind_ = Integer.valueOf(nullValue.getNumber());
            p();
            return this;
        }

        public Builder setNullValueValue(int i2) {
            this.kindCase_ = 1;
            this.kind_ = Integer.valueOf(i2);
            p();
            return this;
        }

        public Builder setNumberValue(double d2) {
            this.kindCase_ = 2;
            this.kind_ = Double.valueOf(d2);
            p();
            return this;
        }

        public Builder setStringValue(String str) {
            str.getClass();
            this.kindCase_ = 3;
            this.kind_ = str;
            p();
            return this;
        }

        public Builder setStringValueBytes(ByteString byteString) throws IllegalArgumentException {
            byteString.getClass();
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.kindCase_ = 3;
            this.kind_ = byteString;
            p();
            return this;
        }

        public Builder setStructValue(Struct struct) {
            SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> singleFieldBuilderV3 = this.structValueBuilder_;
            if (singleFieldBuilderV3 == null) {
                struct.getClass();
                this.kind_ = struct;
                p();
            } else {
                singleFieldBuilderV3.setMessage(struct);
            }
            this.kindCase_ = 5;
            return this;
        }

        private Builder() {
            this.kindCase_ = 0;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Value build() {
            Value valueBuildPartial = buildPartial();
            if (valueBuildPartial.isInitialized()) {
                return valueBuildPartial;
            }
            throw AbstractMessage.Builder.f(valueBuildPartial);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Value buildPartial() {
            Value value = new Value(this);
            if (this.bitField0_ != 0) {
                buildPartial0(value);
            }
            buildPartialOneofs(value);
            o();
            return value;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public Value getDefaultInstanceForType() {
            return Value.getDefaultInstance();
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
            return (Builder) super.setUnknownFields(unknownFieldSet);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
            return (Builder) super.mergeUnknownFields(unknownFieldSet);
        }

        private Builder(GeneratedMessageV3.BuilderParent builderParent) {
            super(builderParent);
            this.kindCase_ = 0;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder clear() {
            super.clear();
            this.bitField0_ = 0;
            SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> singleFieldBuilderV3 = this.structValueBuilder_;
            if (singleFieldBuilderV3 != null) {
                singleFieldBuilderV3.clear();
            }
            SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> singleFieldBuilderV32 = this.listValueBuilder_;
            if (singleFieldBuilderV32 != null) {
                singleFieldBuilderV32.clear();
            }
            this.kindCase_ = 0;
            this.kind_ = null;
            return this;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(Message message) {
            if (message instanceof Value) {
                return mergeFrom((Value) message);
            }
            super.mergeFrom(message);
            return this;
        }

        public Builder setListValue(ListValue.Builder builder) {
            SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> singleFieldBuilderV3 = this.listValueBuilder_;
            if (singleFieldBuilderV3 == null) {
                this.kind_ = builder.build();
                p();
            } else {
                singleFieldBuilderV3.setMessage(builder.build());
            }
            this.kindCase_ = 6;
            return this;
        }

        public Builder setStructValue(Struct.Builder builder) {
            SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> singleFieldBuilderV3 = this.structValueBuilder_;
            if (singleFieldBuilderV3 == null) {
                this.kind_ = builder.build();
                p();
            } else {
                singleFieldBuilderV3.setMessage(builder.build());
            }
            this.kindCase_ = 5;
            return this;
        }

        public Builder mergeFrom(Value value) {
            if (value == Value.getDefaultInstance()) {
                return this;
            }
            switch (AnonymousClass2.f15367a[value.getKindCase().ordinal()]) {
                case 1:
                    setNullValueValue(value.getNullValueValue());
                    break;
                case 2:
                    setNumberValue(value.getNumberValue());
                    break;
                case 3:
                    this.kindCase_ = 3;
                    this.kind_ = value.kind_;
                    p();
                    break;
                case 4:
                    setBoolValue(value.getBoolValue());
                    break;
                case 5:
                    mergeStructValue(value.getStructValue());
                    break;
                case 6:
                    mergeListValue(value.getListValue());
                    break;
            }
            mergeUnknownFields(value.getUnknownFields());
            p();
            return this;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            extensionRegistryLite.getClass();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                int i2 = codedInputStream.readEnum();
                                this.kindCase_ = 1;
                                this.kind_ = Integer.valueOf(i2);
                            } else if (tag == 17) {
                                this.kind_ = Double.valueOf(codedInputStream.readDouble());
                                this.kindCase_ = 2;
                            } else if (tag == 26) {
                                String stringRequireUtf8 = codedInputStream.readStringRequireUtf8();
                                this.kindCase_ = 3;
                                this.kind_ = stringRequireUtf8;
                            } else if (tag == 32) {
                                this.kind_ = Boolean.valueOf(codedInputStream.readBool());
                                this.kindCase_ = 4;
                            } else if (tag == 42) {
                                codedInputStream.readMessage(getStructValueFieldBuilder().getBuilder(), extensionRegistryLite);
                                this.kindCase_ = 5;
                            } else if (tag != 50) {
                                if (!super.q(codedInputStream, extensionRegistryLite, tag)) {
                                }
                            } else {
                                codedInputStream.readMessage(getListValueFieldBuilder().getBuilder(), extensionRegistryLite);
                                this.kindCase_ = 6;
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.unwrapIOException();
                    }
                } catch (Throwable th) {
                    p();
                    throw th;
                }
            }
            p();
            return this;
        }
    }

    private Value(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.kindCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
    }

    public static Builder newBuilder(Value value) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(value);
    }

    public static Value parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
    }

    public static Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
    }

    public static Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
    public Value getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
    }

    public static Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Builder newBuilderForType() {
        return newBuilder();
    }

    private Value() {
        this.kindCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
    }

    public static Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.GeneratedMessageV3
    public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
        return new Builder(builderParent);
    }

    public static Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static Value parseFrom(InputStream inputStream) throws IOException {
        return (Value) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
    }

    public static Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
    }

    public static Value parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Value) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
    }

    public static Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
    }
}
