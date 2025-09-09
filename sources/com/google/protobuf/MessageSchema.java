package com.google.protobuf;

import com.google.protobuf.ArrayDecoders;
import com.google.protobuf.ByteString;
import com.google.protobuf.Internal;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.WireFormat;
import com.google.protobuf.Writer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

@CheckReturnValue
/* loaded from: classes2.dex */
final class MessageSchema<T> implements Schema<T> {
    private static final int ENFORCE_UTF8_MASK = 536870912;
    private static final int FIELD_TYPE_MASK = 267386880;
    private static final int INTS_PER_FIELD = 3;
    private static final int NO_PRESENCE_SENTINEL = 1048575;
    private static final int OFFSET_BITS = 20;
    private static final int OFFSET_MASK = 1048575;
    private static final int REQUIRED_MASK = 268435456;
    private final int[] buffer;
    private final int checkInitializedCount;
    private final MessageLite defaultInstance;
    private final ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final int[] intArray;
    private final ListFieldSchema listFieldSchema;
    private final boolean lite;
    private final MapFieldSchema mapFieldSchema;
    private final int maxFieldNumber;
    private final int minFieldNumber;
    private final NewInstanceSchema newInstanceSchema;
    private final Object[] objects;
    private final boolean proto3;
    private final int repeatedFieldOffsetStart;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;
    private final boolean useCachedSizeField;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final Unsafe UNSAFE = UnsafeUtil.z();

    /* renamed from: com.google.protobuf.MessageSchema$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f15307a;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            f15307a = iArr;
            try {
                iArr[WireFormat.FieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f15307a[WireFormat.FieldType.BYTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f15307a[WireFormat.FieldType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f15307a[WireFormat.FieldType.FIXED32.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f15307a[WireFormat.FieldType.SFIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f15307a[WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f15307a[WireFormat.FieldType.SFIXED64.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f15307a[WireFormat.FieldType.FLOAT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f15307a[WireFormat.FieldType.ENUM.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f15307a[WireFormat.FieldType.INT32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f15307a[WireFormat.FieldType.UINT32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f15307a[WireFormat.FieldType.INT64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f15307a[WireFormat.FieldType.UINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f15307a[WireFormat.FieldType.MESSAGE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f15307a[WireFormat.FieldType.SINT32.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f15307a[WireFormat.FieldType.SINT64.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f15307a[WireFormat.FieldType.STRING.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    private MessageSchema(int[] iArr, Object[] objArr, int i2, int i3, MessageLite messageLite, boolean z2, boolean z3, int[] iArr2, int i4, int i5, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
        this.buffer = iArr;
        this.objects = objArr;
        this.minFieldNumber = i2;
        this.maxFieldNumber = i3;
        this.lite = messageLite instanceof GeneratedMessageLite;
        this.proto3 = z2;
        this.hasExtensions = extensionSchema != null && extensionSchema.d(messageLite);
        this.useCachedSizeField = z3;
        this.intArray = iArr2;
        this.checkInitializedCount = i4;
        this.repeatedFieldOffsetStart = i5;
        this.newInstanceSchema = newInstanceSchema;
        this.listFieldSchema = listFieldSchema;
        this.unknownFieldSchema = unknownFieldSchema;
        this.extensionSchema = extensionSchema;
        this.defaultInstance = messageLite;
        this.mapFieldSchema = mapFieldSchema;
    }

    static UnknownFieldSetLite a(Object obj) {
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
        UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        if (unknownFieldSetLite != UnknownFieldSetLite.getDefaultInstance()) {
            return unknownFieldSetLite;
        }
        UnknownFieldSetLite unknownFieldSetLiteG = UnknownFieldSetLite.g();
        generatedMessageLite.unknownFields = unknownFieldSetLiteG;
        return unknownFieldSetLiteG;
    }

    private boolean arePresentForEquals(T t2, T t3, int i2) {
        return isFieldPresent(t2, i2) == isFieldPresent(t3, i2);
    }

    static MessageSchema b(Class cls, MessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MapFieldSchema mapFieldSchema) {
        return messageInfo instanceof RawMessageInfo ? d((RawMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema) : c((StructuralMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    private static <T> boolean booleanAt(T t2, long j2) {
        return UnsafeUtil.p(t2, j2);
    }

    static MessageSchema c(StructuralMessageInfo structuralMessageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MapFieldSchema mapFieldSchema) {
        int fieldNumber;
        int fieldNumber2;
        int i2;
        boolean z2 = structuralMessageInfo.getSyntax() == ProtoSyntax.PROTO3;
        FieldInfo[] fields = structuralMessageInfo.getFields();
        if (fields.length == 0) {
            fieldNumber = 0;
            fieldNumber2 = 0;
        } else {
            fieldNumber = fields[0].getFieldNumber();
            fieldNumber2 = fields[fields.length - 1].getFieldNumber();
        }
        int length = fields.length;
        int[] iArr = new int[length * 3];
        Object[] objArr = new Object[length * 2];
        int i3 = 0;
        int i4 = 0;
        for (FieldInfo fieldInfo : fields) {
            if (fieldInfo.getType() == FieldType.MAP) {
                i3++;
            } else if (fieldInfo.getType().id() >= 18 && fieldInfo.getType().id() <= 49) {
                i4++;
            }
        }
        int[] iArr2 = i3 > 0 ? new int[i3] : null;
        int[] iArr3 = i4 > 0 ? new int[i4] : null;
        int[] checkInitialized = structuralMessageInfo.getCheckInitialized();
        if (checkInitialized == null) {
            checkInitialized = EMPTY_INT_ARRAY;
        }
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i5 < fields.length) {
            FieldInfo fieldInfo2 = fields[i5];
            int fieldNumber3 = fieldInfo2.getFieldNumber();
            storeFieldData(fieldInfo2, iArr, i6, objArr);
            if (i7 < checkInitialized.length && checkInitialized[i7] == fieldNumber3) {
                checkInitialized[i7] = i6;
                i7++;
            }
            if (fieldInfo2.getType() == FieldType.MAP) {
                iArr2[i8] = i6;
                i8++;
            } else {
                if (fieldInfo2.getType().id() >= 18 && fieldInfo2.getType().id() <= 49) {
                    i2 = i6;
                    iArr3[i9] = (int) UnsafeUtil.C(fieldInfo2.getField());
                    i9++;
                }
                i5++;
                i6 = i2 + 3;
            }
            i2 = i6;
            i5++;
            i6 = i2 + 3;
        }
        if (iArr2 == null) {
            iArr2 = EMPTY_INT_ARRAY;
        }
        if (iArr3 == null) {
            iArr3 = EMPTY_INT_ARRAY;
        }
        int[] iArr4 = new int[checkInitialized.length + iArr2.length + iArr3.length];
        System.arraycopy(checkInitialized, 0, iArr4, 0, checkInitialized.length);
        System.arraycopy(iArr2, 0, iArr4, checkInitialized.length, iArr2.length);
        System.arraycopy(iArr3, 0, iArr4, checkInitialized.length + iArr2.length, iArr3.length);
        return new MessageSchema(iArr, objArr, fieldNumber, fieldNumber2, structuralMessageInfo.getDefaultInstance(), z2, true, iArr4, checkInitialized.length, checkInitialized.length + iArr2.length, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    private static void checkMutable(Object obj) {
        if (isMutable(obj)) {
            return;
        }
        throw new IllegalArgumentException("Mutating immutable message: " + obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x036d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static com.google.protobuf.MessageSchema d(com.google.protobuf.RawMessageInfo r33, com.google.protobuf.NewInstanceSchema r34, com.google.protobuf.ListFieldSchema r35, com.google.protobuf.UnknownFieldSchema r36, com.google.protobuf.ExtensionSchema r37, com.google.protobuf.MapFieldSchema r38) {
        /*
            Method dump skipped, instructions count: 994
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.d(com.google.protobuf.RawMessageInfo, com.google.protobuf.NewInstanceSchema, com.google.protobuf.ListFieldSchema, com.google.protobuf.UnknownFieldSchema, com.google.protobuf.ExtensionSchema, com.google.protobuf.MapFieldSchema):com.google.protobuf.MessageSchema");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r19v0, types: [java.util.Map, java.util.Map<K, V>] */
    /* JADX WARN: Type inference failed for: r1v10, types: [int] */
    private <K, V> int decodeMapEntry(byte[] bArr, int i2, int i3, MapEntryLite.Metadata<K, V> metadata, Map<K, V> map, ArrayDecoders.Registers registers) throws IOException {
        int iH;
        int I = ArrayDecoders.I(bArr, i2, registers);
        int i4 = registers.int1;
        if (i4 < 0 || i4 > i3 - I) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int i5 = I + i4;
        Object obj = metadata.defaultKey;
        Object obj2 = metadata.defaultValue;
        while (I < i5) {
            int i6 = I + 1;
            byte b2 = bArr[I];
            if (b2 < 0) {
                iH = ArrayDecoders.H(b2, bArr, i6, registers);
                b2 = registers.int1;
            } else {
                iH = i6;
            }
            int i7 = b2 >>> 3;
            int i8 = b2 & 7;
            if (i7 != 1) {
                if (i7 == 2 && i8 == metadata.valueType.getWireType()) {
                    I = decodeMapEntryValue(bArr, iH, i3, metadata.valueType, metadata.defaultValue.getClass(), registers);
                    obj2 = registers.object1;
                } else {
                    I = ArrayDecoders.P(b2, bArr, iH, i3, registers);
                }
            } else if (i8 == metadata.keyType.getWireType()) {
                I = decodeMapEntryValue(bArr, iH, i3, metadata.keyType, null, registers);
                obj = registers.object1;
            } else {
                I = ArrayDecoders.P(b2, bArr, iH, i3, registers);
            }
        }
        if (I != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        map.put(obj, obj2);
        return i5;
    }

    private int decodeMapEntryValue(byte[] bArr, int i2, int i3, WireFormat.FieldType fieldType, Class<?> cls, ArrayDecoders.Registers registers) throws IOException {
        switch (AnonymousClass1.f15307a[fieldType.ordinal()]) {
            case 1:
                int iL = ArrayDecoders.L(bArr, i2, registers);
                registers.object1 = Boolean.valueOf(registers.long1 != 0);
                return iL;
            case 2:
                return ArrayDecoders.b(bArr, i2, registers);
            case 3:
                registers.object1 = Double.valueOf(ArrayDecoders.d(bArr, i2));
                return i2 + 8;
            case 4:
            case 5:
                registers.object1 = Integer.valueOf(ArrayDecoders.h(bArr, i2));
                return i2 + 4;
            case 6:
            case 7:
                registers.object1 = Long.valueOf(ArrayDecoders.j(bArr, i2));
                return i2 + 8;
            case 8:
                registers.object1 = Float.valueOf(ArrayDecoders.l(bArr, i2));
                return i2 + 4;
            case 9:
            case 10:
            case 11:
                int I = ArrayDecoders.I(bArr, i2, registers);
                registers.object1 = Integer.valueOf(registers.int1);
                return I;
            case 12:
            case 13:
                int iL2 = ArrayDecoders.L(bArr, i2, registers);
                registers.object1 = Long.valueOf(registers.long1);
                return iL2;
            case 14:
                return ArrayDecoders.p(Protobuf.getInstance().schemaFor((Class) cls), bArr, i2, i3, registers);
            case 15:
                int I2 = ArrayDecoders.I(bArr, i2, registers);
                registers.object1 = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                return I2;
            case 16:
                int iL3 = ArrayDecoders.L(bArr, i2, registers);
                registers.object1 = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                return iL3;
            case 17:
                return ArrayDecoders.F(bArr, i2, registers);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static <T> double doubleAt(T t2, long j2) {
        return UnsafeUtil.s(t2, j2);
    }

    private <UT, UB> UB filterMapUnknownEnumValues(Object obj, int i2, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema, Object obj2) {
        Internal.EnumVerifier enumFieldVerifier;
        int iNumberAt = numberAt(i2);
        Object objX = UnsafeUtil.x(obj, offset(typeAndOffsetAt(i2)));
        return (objX == null || (enumFieldVerifier = getEnumFieldVerifier(i2)) == null) ? ub : (UB) filterUnknownEnumMap(i2, iNumberAt, this.mapFieldSchema.forMutableMapData(objX), enumFieldVerifier, ub, unknownFieldSchema, obj2);
    }

    private <K, V, UT, UB> UB filterUnknownEnumMap(int i2, int i3, Map<K, V> map, Internal.EnumVerifier enumVerifier, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema, Object obj) {
        MapEntryLite.Metadata<?, ?> metadataForMapMetadata = this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i2));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!enumVerifier.isInRange(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = (UB) unknownFieldSchema.f(obj);
                }
                ByteString.CodedBuilder codedBuilderNewCodedBuilder = ByteString.newCodedBuilder(MapEntryLite.a(metadataForMapMetadata, next.getKey(), next.getValue()));
                try {
                    MapEntryLite.e(codedBuilderNewCodedBuilder.getCodedOutput(), metadataForMapMetadata, next.getKey(), next.getValue());
                    unknownFieldSchema.d(ub, i3, codedBuilderNewCodedBuilder.build());
                    it.remove();
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return ub;
    }

    private static <T> float floatAt(T t2, long j2) {
        return UnsafeUtil.t(t2, j2);
    }

    private Internal.EnumVerifier getEnumFieldVerifier(int i2) {
        return (Internal.EnumVerifier) this.objects[((i2 / 3) * 2) + 1];
    }

    private Object getMapFieldDefaultEntry(int i2) {
        return this.objects[(i2 / 3) * 2];
    }

    private Schema getMessageFieldSchema(int i2) {
        int i3 = (i2 / 3) * 2;
        Schema schema = (Schema) this.objects[i3];
        if (schema != null) {
            return schema;
        }
        Schema<T> schemaSchemaFor = Protobuf.getInstance().schemaFor((Class) this.objects[i3 + 1]);
        this.objects[i3] = schemaSchemaFor;
        return schemaSchemaFor;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getSerializedSizeProto2(T t2) {
        int i2;
        int i3;
        int iComputeDoubleSize;
        int iComputeBoolSize;
        int iComputeSFixed32Size;
        boolean z2;
        int iF;
        int i4;
        int iComputeTagSize;
        int iComputeUInt32SizeNoTag;
        Unsafe unsafe = UNSAFE;
        int i5 = 1048575;
        int i6 = 1048575;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i7 < this.buffer.length) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i7);
            int iNumberAt = numberAt(i7);
            int iType = type(iTypeAndOffsetAt);
            if (iType <= 17) {
                i2 = this.buffer[i7 + 2];
                int i10 = i2 & i5;
                i3 = 1 << (i2 >>> 20);
                if (i10 != i6) {
                    i9 = unsafe.getInt(t2, i10);
                    i6 = i10;
                }
            } else {
                i2 = (!this.useCachedSizeField || iType < FieldType.DOUBLE_LIST_PACKED.id() || iType > FieldType.SINT64_LIST_PACKED.id()) ? 0 : this.buffer[i7 + 2] & i5;
                i3 = 0;
            }
            long jOffset = offset(iTypeAndOffsetAt);
            switch (iType) {
                case 0:
                    if ((i9 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i8 += iComputeDoubleSize;
                        break;
                    }
                case 1:
                    if ((i9 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i8 += iComputeDoubleSize;
                        break;
                    }
                case 2:
                    if ((i9 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeInt64Size(iNumberAt, unsafe.getLong(t2, jOffset));
                        i8 += iComputeDoubleSize;
                        break;
                    }
                case 3:
                    if ((i9 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeUInt64Size(iNumberAt, unsafe.getLong(t2, jOffset));
                        i8 += iComputeDoubleSize;
                        break;
                    }
                case 4:
                    if ((i9 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeInt32Size(iNumberAt, unsafe.getInt(t2, jOffset));
                        i8 += iComputeDoubleSize;
                        break;
                    }
                case 5:
                    if ((i9 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i8 += iComputeDoubleSize;
                        break;
                    }
                case 6:
                    if ((i9 & i3) != 0) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i8 += iComputeDoubleSize;
                        break;
                    }
                    break;
                case 7:
                    if ((i9 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 8:
                    if ((i9 & i3) != 0) {
                        Object object = unsafe.getObject(t2, jOffset);
                        iComputeBoolSize = object instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) object) : CodedOutputStream.computeStringSize(iNumberAt, (String) object);
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 9:
                    if ((i9 & i3) != 0) {
                        iComputeBoolSize = SchemaUtil.o(iNumberAt, unsafe.getObject(t2, jOffset), getMessageFieldSchema(i7));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 10:
                    if ((i9 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) unsafe.getObject(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 11:
                    if ((i9 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeUInt32Size(iNumberAt, unsafe.getInt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 12:
                    if ((i9 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeEnumSize(iNumberAt, unsafe.getInt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 13:
                    if ((i9 & i3) != 0) {
                        iComputeSFixed32Size = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 14:
                    if ((i9 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 15:
                    if ((i9 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeSInt32Size(iNumberAt, unsafe.getInt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 16:
                    if ((i9 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeSInt64Size(iNumberAt, unsafe.getLong(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 17:
                    if ((i9 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.b(iNumberAt, (MessageLite) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i7));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 18:
                    iComputeBoolSize = SchemaUtil.h(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iComputeBoolSize;
                    break;
                case 19:
                    z2 = false;
                    iF = SchemaUtil.f(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 20:
                    z2 = false;
                    iF = SchemaUtil.m(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 21:
                    z2 = false;
                    iF = SchemaUtil.x(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 22:
                    z2 = false;
                    iF = SchemaUtil.k(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 23:
                    z2 = false;
                    iF = SchemaUtil.h(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 24:
                    z2 = false;
                    iF = SchemaUtil.f(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 25:
                    z2 = false;
                    iF = SchemaUtil.a(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 26:
                    iComputeBoolSize = SchemaUtil.u(iNumberAt, (List) unsafe.getObject(t2, jOffset));
                    i8 += iComputeBoolSize;
                    break;
                case 27:
                    iComputeBoolSize = SchemaUtil.p(iNumberAt, (List) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i7));
                    i8 += iComputeBoolSize;
                    break;
                case 28:
                    iComputeBoolSize = SchemaUtil.c(iNumberAt, (List) unsafe.getObject(t2, jOffset));
                    i8 += iComputeBoolSize;
                    break;
                case 29:
                    iComputeBoolSize = SchemaUtil.v(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iComputeBoolSize;
                    break;
                case 30:
                    z2 = false;
                    iF = SchemaUtil.d(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 31:
                    z2 = false;
                    iF = SchemaUtil.f(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 32:
                    z2 = false;
                    iF = SchemaUtil.h(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 33:
                    z2 = false;
                    iF = SchemaUtil.q(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 34:
                    z2 = false;
                    iF = SchemaUtil.s(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i8 += iF;
                    break;
                case 35:
                    i4 = SchemaUtil.i((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 36:
                    i4 = SchemaUtil.g((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 37:
                    i4 = SchemaUtil.n((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 38:
                    i4 = SchemaUtil.y((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 39:
                    i4 = SchemaUtil.l((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 40:
                    i4 = SchemaUtil.i((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 41:
                    i4 = SchemaUtil.g((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 42:
                    i4 = SchemaUtil.b((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 43:
                    i4 = SchemaUtil.w((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 44:
                    i4 = SchemaUtil.e((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 45:
                    i4 = SchemaUtil.g((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 46:
                    i4 = SchemaUtil.i((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 47:
                    i4 = SchemaUtil.r((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 48:
                    i4 = SchemaUtil.t((List) unsafe.getObject(t2, jOffset));
                    if (i4 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i2, i4);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i4);
                        iComputeSFixed32Size = iComputeTagSize + iComputeUInt32SizeNoTag + i4;
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 49:
                    iComputeBoolSize = SchemaUtil.j(iNumberAt, (List) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i7));
                    i8 += iComputeBoolSize;
                    break;
                case 50:
                    iComputeBoolSize = this.mapFieldSchema.getSerializedSize(iNumberAt, unsafe.getObject(t2, jOffset), getMapFieldDefaultEntry(i7));
                    i8 += iComputeBoolSize;
                    break;
                case 51:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 52:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 53:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 54:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeUInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 55:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 56:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 57:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeSFixed32Size = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 58:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 59:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        Object object2 = unsafe.getObject(t2, jOffset);
                        iComputeBoolSize = object2 instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) object2) : CodedOutputStream.computeStringSize(iNumberAt, (String) object2);
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 60:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = SchemaUtil.o(iNumberAt, unsafe.getObject(t2, jOffset), getMessageFieldSchema(i7));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 61:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) unsafe.getObject(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 62:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeUInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 63:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeEnumSize(iNumberAt, oneofIntAt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 64:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeSFixed32Size = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i8 += iComputeSFixed32Size;
                    }
                    break;
                case 65:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 66:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeSInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 67:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.computeSInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i8 += iComputeBoolSize;
                    }
                    break;
                case 68:
                    if (isOneofPresent(t2, iNumberAt, i7)) {
                        iComputeBoolSize = CodedOutputStream.b(iNumberAt, (MessageLite) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i7));
                        i8 += iComputeBoolSize;
                    }
                    break;
            }
            i7 += 3;
            i5 = 1048575;
        }
        int unknownFieldsSerializedSize = i8 + getUnknownFieldsSerializedSize(this.unknownFieldSchema, t2);
        return this.hasExtensions ? unknownFieldsSerializedSize + this.extensionSchema.getExtensions(t2).getSerializedSize() : unknownFieldsSerializedSize;
    }

    private int getSerializedSizeProto3(T t2) {
        int iComputeDoubleSize;
        int i2;
        int iComputeTagSize;
        int iComputeUInt32SizeNoTag;
        Unsafe unsafe = UNSAFE;
        int i3 = 0;
        for (int i4 = 0; i4 < this.buffer.length; i4 += 3) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i4);
            int iType = type(iTypeAndOffsetAt);
            int iNumberAt = numberAt(i4);
            long jOffset = offset(iTypeAndOffsetAt);
            int i5 = (iType < FieldType.DOUBLE_LIST_PACKED.id() || iType > FieldType.SINT64_LIST_PACKED.id()) ? 0 : this.buffer[i4 + 2] & 1048575;
            switch (iType) {
                case 0:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeInt64Size(iNumberAt, UnsafeUtil.w(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt64Size(iNumberAt, UnsafeUtil.w(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeInt32Size(iNumberAt, UnsafeUtil.u(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (isFieldPresent(t2, i4)) {
                        Object objX = UnsafeUtil.x(t2, jOffset);
                        iComputeDoubleSize = objX instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) objX) : CodedOutputStream.computeStringSize(iNumberAt, (String) objX);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 9:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = SchemaUtil.o(iNumberAt, UnsafeUtil.x(t2, jOffset), getMessageFieldSchema(i4));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) UnsafeUtil.x(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt32Size(iNumberAt, UnsafeUtil.u(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeEnumSize(iNumberAt, UnsafeUtil.u(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeSInt32Size(iNumberAt, UnsafeUtil.u(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeSInt64Size(iNumberAt, UnsafeUtil.w(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.b(iNumberAt, (MessageLite) UnsafeUtil.x(t2, jOffset), getMessageFieldSchema(i4));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    iComputeDoubleSize = SchemaUtil.h(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 19:
                    iComputeDoubleSize = SchemaUtil.f(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 20:
                    iComputeDoubleSize = SchemaUtil.m(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 21:
                    iComputeDoubleSize = SchemaUtil.x(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 22:
                    iComputeDoubleSize = SchemaUtil.k(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 23:
                    iComputeDoubleSize = SchemaUtil.h(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 24:
                    iComputeDoubleSize = SchemaUtil.f(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 25:
                    iComputeDoubleSize = SchemaUtil.a(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 26:
                    iComputeDoubleSize = SchemaUtil.u(iNumberAt, listAt(t2, jOffset));
                    i3 += iComputeDoubleSize;
                    break;
                case 27:
                    iComputeDoubleSize = SchemaUtil.p(iNumberAt, listAt(t2, jOffset), getMessageFieldSchema(i4));
                    i3 += iComputeDoubleSize;
                    break;
                case 28:
                    iComputeDoubleSize = SchemaUtil.c(iNumberAt, listAt(t2, jOffset));
                    i3 += iComputeDoubleSize;
                    break;
                case 29:
                    iComputeDoubleSize = SchemaUtil.v(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 30:
                    iComputeDoubleSize = SchemaUtil.d(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 31:
                    iComputeDoubleSize = SchemaUtil.f(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 32:
                    iComputeDoubleSize = SchemaUtil.h(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 33:
                    iComputeDoubleSize = SchemaUtil.q(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 34:
                    iComputeDoubleSize = SchemaUtil.s(iNumberAt, listAt(t2, jOffset), false);
                    i3 += iComputeDoubleSize;
                    break;
                case 35:
                    i2 = SchemaUtil.i((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 36:
                    i2 = SchemaUtil.g((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 37:
                    i2 = SchemaUtil.n((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 38:
                    i2 = SchemaUtil.y((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 39:
                    i2 = SchemaUtil.l((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 40:
                    i2 = SchemaUtil.i((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 41:
                    i2 = SchemaUtil.g((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 42:
                    i2 = SchemaUtil.b((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 43:
                    i2 = SchemaUtil.w((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 44:
                    i2 = SchemaUtil.e((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 45:
                    i2 = SchemaUtil.g((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 46:
                    i2 = SchemaUtil.i((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 47:
                    i2 = SchemaUtil.r((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 48:
                    i2 = SchemaUtil.t((List) unsafe.getObject(t2, jOffset));
                    if (i2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t2, i5, i2);
                        }
                        iComputeTagSize = CodedOutputStream.computeTagSize(iNumberAt);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2);
                        iComputeDoubleSize = iComputeTagSize + iComputeUInt32SizeNoTag + i2;
                        i3 += iComputeDoubleSize;
                        break;
                    }
                case 49:
                    iComputeDoubleSize = SchemaUtil.j(iNumberAt, listAt(t2, jOffset), getMessageFieldSchema(i4));
                    i3 += iComputeDoubleSize;
                    break;
                case 50:
                    iComputeDoubleSize = this.mapFieldSchema.getSerializedSize(iNumberAt, UnsafeUtil.x(t2, jOffset), getMapFieldDefaultEntry(i4));
                    i3 += iComputeDoubleSize;
                    break;
                case 51:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        Object objX2 = UnsafeUtil.x(t2, jOffset);
                        iComputeDoubleSize = objX2 instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) objX2) : CodedOutputStream.computeStringSize(iNumberAt, (String) objX2);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = SchemaUtil.o(iNumberAt, UnsafeUtil.x(t2, jOffset), getMessageFieldSchema(i4));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) UnsafeUtil.x(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeEnumSize(iNumberAt, oneofIntAt(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeSInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeSInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.b(iNumberAt, (MessageLite) UnsafeUtil.x(t2, jOffset), getMessageFieldSchema(i4));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
            }
        }
        return i3 + getUnknownFieldsSerializedSize(this.unknownFieldSchema, t2);
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> unknownFieldSchema, T t2) {
        return unknownFieldSchema.h(unknownFieldSchema.g(t2));
    }

    private static <T> int intAt(T t2, long j2) {
        return UnsafeUtil.u(t2, j2);
    }

    private static boolean isEnforceUtf8(int i2) {
        return (i2 & 536870912) != 0;
    }

    private boolean isFieldPresent(T t2, int i2, int i3, int i4, int i5) {
        return i3 == 1048575 ? isFieldPresent(t2, i2) : (i4 & i5) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N> boolean isListInitialized(Object obj, int i2, int i3) {
        List list = (List) UnsafeUtil.x(obj, offset(i2));
        if (list.isEmpty()) {
            return true;
        }
        Schema messageFieldSchema = getMessageFieldSchema(i3);
        for (int i4 = 0; i4 < list.size(); i4++) {
            if (!messageFieldSchema.isInitialized(list.get(i4))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.google.protobuf.Schema] */
    private boolean isMapInitialized(T t2, int i2, int i3) {
        Map<?, ?> mapForMapData = this.mapFieldSchema.forMapData(UnsafeUtil.x(t2, offset(i2)));
        if (mapForMapData.isEmpty()) {
            return true;
        }
        if (this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i3)).valueType.getJavaType() != WireFormat.JavaType.MESSAGE) {
            return true;
        }
        ?? SchemaFor = 0;
        for (Object obj : mapForMapData.values()) {
            SchemaFor = SchemaFor;
            if (SchemaFor == 0) {
                SchemaFor = Protobuf.getInstance().schemaFor((Class) obj.getClass());
            }
            if (!SchemaFor.isInitialized(obj)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isMutable(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof GeneratedMessageLite) {
            return ((GeneratedMessageLite) obj).isMutable();
        }
        return true;
    }

    private boolean isOneofCaseEqual(T t2, T t3, int i2) {
        long jPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i2) & 1048575;
        return UnsafeUtil.u(t2, jPresenceMaskAndOffsetAt) == UnsafeUtil.u(t3, jPresenceMaskAndOffsetAt);
    }

    private boolean isOneofPresent(T t2, int i2, int i3) {
        return UnsafeUtil.u(t2, (long) (presenceMaskAndOffsetAt(i3) & 1048575)) == i2;
    }

    private static boolean isRequired(int i2) {
        return (i2 & 268435456) != 0;
    }

    private static List<?> listAt(Object obj, long j2) {
        return (List) UnsafeUtil.x(obj, j2);
    }

    private static <T> long longAt(T t2, long j2) {
        return UnsafeUtil.w(t2, j2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:244:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0094, code lost:
    
        r0 = r18.checkInitializedCount;
        r4 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0099, code lost:
    
        if (r0 >= r18.repeatedFieldOffsetStart) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009b, code lost:
    
        r4 = filterMapUnknownEnumValues(r21, r18.intArray[r0], r4, r19, r21);
        r0 = r0 + 1;
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b1, code lost:
    
        r10 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00b2, code lost:
    
        if (r4 == null) goto L244;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00b4, code lost:
    
        r7.o(r10, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00b7, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:172:0x064f A[Catch: all -> 0x0675, TRY_LEAVE, TryCatch #6 {all -> 0x0675, blocks: (B:170:0x0649, B:172:0x064f, B:184:0x0679, B:185:0x067e), top: B:214:0x0649 }] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0677  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x06b5 A[LOOP:4: B:198:0x06b1->B:200:0x06b5, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x06ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <UT, UB, ET extends com.google.protobuf.FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(com.google.protobuf.UnknownFieldSchema<UT, UB> r19, com.google.protobuf.ExtensionSchema<ET> r20, T r21, com.google.protobuf.Reader r22, com.google.protobuf.ExtensionRegistryLite r23) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1884
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.mergeFromHelper(com.google.protobuf.UnknownFieldSchema, com.google.protobuf.ExtensionSchema, java.lang.Object, com.google.protobuf.Reader, com.google.protobuf.ExtensionRegistryLite):void");
    }

    private final <K, V> void mergeMap(Object obj, int i2, Object obj2, ExtensionRegistryLite extensionRegistryLite, Reader reader) throws IOException {
        long jOffset = offset(typeAndOffsetAt(i2));
        Object objX = UnsafeUtil.x(obj, jOffset);
        if (objX == null) {
            objX = this.mapFieldSchema.newMapField(obj2);
            UnsafeUtil.K(obj, jOffset, objX);
        } else if (this.mapFieldSchema.isImmutable(objX)) {
            Object objNewMapField = this.mapFieldSchema.newMapField(obj2);
            this.mapFieldSchema.mergeFrom(objNewMapField, objX);
            UnsafeUtil.K(obj, jOffset, objNewMapField);
            objX = objNewMapField;
        }
        reader.readMap(this.mapFieldSchema.forMutableMapData(objX), this.mapFieldSchema.forMapMetadata(obj2), extensionRegistryLite);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void mergeMessage(T t2, T t3, int i2) {
        if (isFieldPresent(t3, i2)) {
            long jOffset = offset(typeAndOffsetAt(i2));
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(t3, jOffset);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + numberAt(i2) + " is present but null: " + t3);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i2);
            if (!isFieldPresent(t2, i2)) {
                if (isMutable(object)) {
                    Object objNewInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(objNewInstance, object);
                    unsafe.putObject(t2, jOffset, objNewInstance);
                } else {
                    unsafe.putObject(t2, jOffset, object);
                }
                setFieldPresent(t2, i2);
                return;
            }
            Object object2 = unsafe.getObject(t2, jOffset);
            if (!isMutable(object2)) {
                Object objNewInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(objNewInstance2, object2);
                unsafe.putObject(t2, jOffset, objNewInstance2);
                object2 = objNewInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void mergeOneofMessage(T t2, T t3, int i2) {
        int iNumberAt = numberAt(i2);
        if (isOneofPresent(t3, iNumberAt, i2)) {
            long jOffset = offset(typeAndOffsetAt(i2));
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(t3, jOffset);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + numberAt(i2) + " is present but null: " + t3);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i2);
            if (!isOneofPresent(t2, iNumberAt, i2)) {
                if (isMutable(object)) {
                    Object objNewInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(objNewInstance, object);
                    unsafe.putObject(t2, jOffset, objNewInstance);
                } else {
                    unsafe.putObject(t2, jOffset, object);
                }
                setOneofPresent(t2, iNumberAt, i2);
                return;
            }
            Object object2 = unsafe.getObject(t2, jOffset);
            if (!isMutable(object2)) {
                Object objNewInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(objNewInstance2, object2);
                unsafe.putObject(t2, jOffset, objNewInstance2);
                object2 = objNewInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    private void mergeSingleField(T t2, T t3, int i2) {
        int iTypeAndOffsetAt = typeAndOffsetAt(i2);
        long jOffset = offset(iTypeAndOffsetAt);
        int iNumberAt = numberAt(i2);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.G(t2, jOffset, UnsafeUtil.s(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 1:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.H(t2, jOffset, UnsafeUtil.t(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 2:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.J(t2, jOffset, UnsafeUtil.w(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 3:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.J(t2, jOffset, UnsafeUtil.w(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 4:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.I(t2, jOffset, UnsafeUtil.u(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 5:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.J(t2, jOffset, UnsafeUtil.w(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 6:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.I(t2, jOffset, UnsafeUtil.u(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 7:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.D(t2, jOffset, UnsafeUtil.p(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 8:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.K(t2, jOffset, UnsafeUtil.x(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 9:
                mergeMessage(t2, t3, i2);
                break;
            case 10:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.K(t2, jOffset, UnsafeUtil.x(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 11:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.I(t2, jOffset, UnsafeUtil.u(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 12:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.I(t2, jOffset, UnsafeUtil.u(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 13:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.I(t2, jOffset, UnsafeUtil.u(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 14:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.J(t2, jOffset, UnsafeUtil.w(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 15:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.I(t2, jOffset, UnsafeUtil.u(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 16:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.J(t2, jOffset, UnsafeUtil.w(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 17:
                mergeMessage(t2, t3, i2);
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
                this.listFieldSchema.d(t2, t3, jOffset);
                break;
            case 50:
                SchemaUtil.D(this.mapFieldSchema, t2, t3, jOffset);
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                if (isOneofPresent(t3, iNumberAt, i2)) {
                    UnsafeUtil.K(t2, jOffset, UnsafeUtil.x(t3, jOffset));
                    setOneofPresent(t2, iNumberAt, i2);
                    break;
                }
                break;
            case 60:
                mergeOneofMessage(t2, t3, i2);
                break;
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
                if (isOneofPresent(t3, iNumberAt, i2)) {
                    UnsafeUtil.K(t2, jOffset, UnsafeUtil.x(t3, jOffset));
                    setOneofPresent(t2, iNumberAt, i2);
                    break;
                }
                break;
            case 68:
                mergeOneofMessage(t2, t3, i2);
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Object mutableMessageFieldForMerge(T t2, int i2) {
        Schema messageFieldSchema = getMessageFieldSchema(i2);
        long jOffset = offset(typeAndOffsetAt(i2));
        if (!isFieldPresent(t2, i2)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(t2, jOffset);
        if (isMutable(object)) {
            return object;
        }
        Object objNewInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(objNewInstance, object);
        }
        return objNewInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Object mutableOneofMessageFieldForMerge(T t2, int i2, int i3) {
        Schema messageFieldSchema = getMessageFieldSchema(i3);
        if (!isOneofPresent(t2, i2, i3)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(t2, offset(typeAndOffsetAt(i3)));
        if (isMutable(object)) {
            return object;
        }
        Object objNewInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(objNewInstance, object);
        }
        return objNewInstance;
    }

    private int numberAt(int i2) {
        return this.buffer[i2];
    }

    private static long offset(int i2) {
        return i2 & 1048575;
    }

    private static <T> boolean oneofBooleanAt(T t2, long j2) {
        return ((Boolean) UnsafeUtil.x(t2, j2)).booleanValue();
    }

    private static <T> double oneofDoubleAt(T t2, long j2) {
        return ((Double) UnsafeUtil.x(t2, j2)).doubleValue();
    }

    private static <T> float oneofFloatAt(T t2, long j2) {
        return ((Float) UnsafeUtil.x(t2, j2)).floatValue();
    }

    private static <T> int oneofIntAt(T t2, long j2) {
        return ((Integer) UnsafeUtil.x(t2, j2)).intValue();
    }

    private static <T> long oneofLongAt(T t2, long j2) {
        return ((Long) UnsafeUtil.x(t2, j2)).longValue();
    }

    private <K, V> int parseMapField(T t2, byte[] bArr, int i2, int i3, int i4, long j2, ArrayDecoders.Registers registers) throws IOException {
        Unsafe unsafe = UNSAFE;
        Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i4);
        Object object = unsafe.getObject(t2, j2);
        if (this.mapFieldSchema.isImmutable(object)) {
            Object objNewMapField = this.mapFieldSchema.newMapField(mapFieldDefaultEntry);
            this.mapFieldSchema.mergeFrom(objNewMapField, object);
            unsafe.putObject(t2, j2, objNewMapField);
            object = objNewMapField;
        }
        return decodeMapEntry(bArr, i2, i3, this.mapFieldSchema.forMapMetadata(mapFieldDefaultEntry), this.mapFieldSchema.forMutableMapData(object), registers);
    }

    private int parseOneofField(T t2, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j2, int i9, ArrayDecoders.Registers registers) throws IOException {
        Unsafe unsafe = UNSAFE;
        long j3 = this.buffer[i9 + 2] & 1048575;
        switch (i8) {
            case 51:
                if (i6 == 1) {
                    unsafe.putObject(t2, j2, Double.valueOf(ArrayDecoders.d(bArr, i2)));
                    int i10 = i2 + 8;
                    unsafe.putInt(t2, j3, i5);
                    return i10;
                }
                break;
            case 52:
                if (i6 == 5) {
                    unsafe.putObject(t2, j2, Float.valueOf(ArrayDecoders.l(bArr, i2)));
                    int i11 = i2 + 4;
                    unsafe.putInt(t2, j3, i5);
                    return i11;
                }
                break;
            case 53:
            case 54:
                if (i6 == 0) {
                    int iL = ArrayDecoders.L(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Long.valueOf(registers.long1));
                    unsafe.putInt(t2, j3, i5);
                    return iL;
                }
                break;
            case 55:
            case 62:
                if (i6 == 0) {
                    int I = ArrayDecoders.I(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Integer.valueOf(registers.int1));
                    unsafe.putInt(t2, j3, i5);
                    return I;
                }
                break;
            case 56:
            case 65:
                if (i6 == 1) {
                    unsafe.putObject(t2, j2, Long.valueOf(ArrayDecoders.j(bArr, i2)));
                    int i12 = i2 + 8;
                    unsafe.putInt(t2, j3, i5);
                    return i12;
                }
                break;
            case 57:
            case 64:
                if (i6 == 5) {
                    unsafe.putObject(t2, j2, Integer.valueOf(ArrayDecoders.h(bArr, i2)));
                    int i13 = i2 + 4;
                    unsafe.putInt(t2, j3, i5);
                    return i13;
                }
                break;
            case 58:
                if (i6 == 0) {
                    int iL2 = ArrayDecoders.L(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Boolean.valueOf(registers.long1 != 0));
                    unsafe.putInt(t2, j3, i5);
                    return iL2;
                }
                break;
            case 59:
                if (i6 == 2) {
                    int I2 = ArrayDecoders.I(bArr, i2, registers);
                    int i14 = registers.int1;
                    if (i14 == 0) {
                        unsafe.putObject(t2, j2, "");
                    } else {
                        if ((i7 & 536870912) != 0 && !Utf8.n(bArr, I2, I2 + i14)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        unsafe.putObject(t2, j2, new String(bArr, I2, i14, Internal.f15296b));
                        I2 += i14;
                    }
                    unsafe.putInt(t2, j3, i5);
                    return I2;
                }
                break;
            case 60:
                if (i6 == 2) {
                    Object objMutableOneofMessageFieldForMerge = mutableOneofMessageFieldForMerge(t2, i5, i9);
                    int iO = ArrayDecoders.O(objMutableOneofMessageFieldForMerge, getMessageFieldSchema(i9), bArr, i2, i3, registers);
                    storeOneofMessageField(t2, i5, i9, objMutableOneofMessageFieldForMerge);
                    return iO;
                }
                break;
            case 61:
                if (i6 == 2) {
                    int iB = ArrayDecoders.b(bArr, i2, registers);
                    unsafe.putObject(t2, j2, registers.object1);
                    unsafe.putInt(t2, j3, i5);
                    return iB;
                }
                break;
            case 63:
                if (i6 == 0) {
                    int I3 = ArrayDecoders.I(bArr, i2, registers);
                    int i15 = registers.int1;
                    Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i9);
                    if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i15)) {
                        unsafe.putObject(t2, j2, Integer.valueOf(i15));
                        unsafe.putInt(t2, j3, i5);
                    } else {
                        a(t2).i(i4, Long.valueOf(i15));
                    }
                    return I3;
                }
                break;
            case 66:
                if (i6 == 0) {
                    int I4 = ArrayDecoders.I(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)));
                    unsafe.putInt(t2, j3, i5);
                    return I4;
                }
                break;
            case 67:
                if (i6 == 0) {
                    int iL3 = ArrayDecoders.L(bArr, i2, registers);
                    unsafe.putObject(t2, j2, Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)));
                    unsafe.putInt(t2, j3, i5);
                    return iL3;
                }
                break;
            case 68:
                if (i6 == 3) {
                    Object objMutableOneofMessageFieldForMerge2 = mutableOneofMessageFieldForMerge(t2, i5, i9);
                    int iN = ArrayDecoders.N(objMutableOneofMessageFieldForMerge2, getMessageFieldSchema(i9), bArr, i2, i3, (i4 & (-8)) | 4, registers);
                    storeOneofMessageField(t2, i5, i9, objMutableOneofMessageFieldForMerge2);
                    return iN;
                }
                break;
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x02b2, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x02e8, code lost:
    
        if (r0 != r15) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0307, code lost:
    
        if (r0 != r15) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0298, code lost:
    
        if (r0 != r10) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x029a, code lost:
    
        r15 = r29;
        r14 = r30;
        r12 = r31;
        r13 = r33;
        r11 = r34;
        r1 = r18;
        r8 = r19;
        r7 = r22;
        r6 = r26;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x0089. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12, types: [int] */
    @com.google.protobuf.CanIgnoreReturnValue
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int parseProto3Message(T r30, byte[] r31, int r32, int r33, com.google.protobuf.ArrayDecoders.Registers r34) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 876
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.parseProto3Message(java.lang.Object, byte[], int, int, com.google.protobuf.ArrayDecoders$Registers):int");
    }

    private int parseRepeatedField(T t2, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, long j2, int i8, long j3, ArrayDecoders.Registers registers) throws IOException {
        int iJ;
        Unsafe unsafe = UNSAFE;
        Internal.ProtobufList protobufListMutableCopyWithCapacity2 = (Internal.ProtobufList) unsafe.getObject(t2, j3);
        if (!protobufListMutableCopyWithCapacity2.isModifiable()) {
            int size = protobufListMutableCopyWithCapacity2.size();
            protobufListMutableCopyWithCapacity2 = protobufListMutableCopyWithCapacity2.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
            unsafe.putObject(t2, j3, protobufListMutableCopyWithCapacity2);
        }
        switch (i8) {
            case 18:
            case 35:
                if (i6 == 2) {
                    return ArrayDecoders.s(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 1) {
                    return ArrayDecoders.e(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 19:
            case 36:
                if (i6 == 2) {
                    return ArrayDecoders.v(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 5) {
                    return ArrayDecoders.m(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i6 == 2) {
                    return ArrayDecoders.z(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.M(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i6 == 2) {
                    return ArrayDecoders.y(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.J(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i6 == 2) {
                    return ArrayDecoders.u(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 1) {
                    return ArrayDecoders.k(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i6 == 2) {
                    return ArrayDecoders.t(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 5) {
                    return ArrayDecoders.i(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 25:
            case 42:
                if (i6 == 2) {
                    return ArrayDecoders.r(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.a(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 26:
                if (i6 == 2) {
                    return (j2 & 536870912) == 0 ? ArrayDecoders.D(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers) : ArrayDecoders.E(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 27:
                if (i6 == 2) {
                    return ArrayDecoders.q(getMessageFieldSchema(i7), i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 28:
                if (i6 == 2) {
                    return ArrayDecoders.c(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 30:
            case 44:
                if (i6 == 2) {
                    iJ = ArrayDecoders.y(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                } else if (i6 == 0) {
                    iJ = ArrayDecoders.J(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                SchemaUtil.A(t2, i5, protobufListMutableCopyWithCapacity2, getEnumFieldVerifier(i7), null, this.unknownFieldSchema);
                return iJ;
            case 33:
            case 47:
                if (i6 == 2) {
                    return ArrayDecoders.w(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.A(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 34:
            case 48:
                if (i6 == 2) {
                    return ArrayDecoders.x(bArr, i2, protobufListMutableCopyWithCapacity2, registers);
                }
                if (i6 == 0) {
                    return ArrayDecoders.B(i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
            case 49:
                if (i6 == 3) {
                    return ArrayDecoders.o(getMessageFieldSchema(i7), i4, bArr, i2, i3, protobufListMutableCopyWithCapacity2, registers);
                }
                break;
        }
        return i2;
    }

    private int positionForFieldNumber(int i2) {
        if (i2 < this.minFieldNumber || i2 > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(i2, 0);
    }

    private int presenceMaskAndOffsetAt(int i2) {
        return this.buffer[i2 + 2];
    }

    private <E> void readGroupList(Object obj, long j2, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        reader.readGroupList(this.listFieldSchema.e(obj, j2), schema, extensionRegistryLite);
    }

    private <E> void readMessageList(Object obj, int i2, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        reader.readMessageList(this.listFieldSchema.e(obj, offset(i2)), schema, extensionRegistryLite);
    }

    private void readString(Object obj, int i2, Reader reader) throws IOException {
        if (isEnforceUtf8(i2)) {
            UnsafeUtil.K(obj, offset(i2), reader.readStringRequireUtf8());
        } else if (this.lite) {
            UnsafeUtil.K(obj, offset(i2), reader.readString());
        } else {
            UnsafeUtil.K(obj, offset(i2), reader.readBytes());
        }
    }

    private void readStringList(Object obj, int i2, Reader reader) throws IOException {
        if (isEnforceUtf8(i2)) {
            reader.readStringListRequireUtf8(this.listFieldSchema.e(obj, offset(i2)));
        } else {
            reader.readStringList(this.listFieldSchema.e(obj, offset(i2)));
        }
    }

    private static java.lang.reflect.Field reflectField(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            java.lang.reflect.Field[] declaredFields = cls.getDeclaredFields();
            for (java.lang.reflect.Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private void setFieldPresent(T t2, int i2) {
        int iPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i2);
        long j2 = 1048575 & iPresenceMaskAndOffsetAt;
        if (j2 == 1048575) {
            return;
        }
        UnsafeUtil.I(t2, j2, (1 << (iPresenceMaskAndOffsetAt >>> 20)) | UnsafeUtil.u(t2, j2));
    }

    private void setOneofPresent(T t2, int i2, int i3) {
        UnsafeUtil.I(t2, presenceMaskAndOffsetAt(i3) & 1048575, i2);
    }

    private int slowPositionForFieldNumber(int i2, int i3) {
        int length = (this.buffer.length / 3) - 1;
        while (i3 <= length) {
            int i4 = (length + i3) >>> 1;
            int i5 = i4 * 3;
            int iNumberAt = numberAt(i5);
            if (i2 == iNumberAt) {
                return i5;
            }
            if (i2 < iNumberAt) {
                length = i4 - 1;
            } else {
                i3 = i4 + 1;
            }
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void storeFieldData(com.google.protobuf.FieldInfo r8, int[] r9, int r10, java.lang.Object[] r11) {
        /*
            com.google.protobuf.OneofInfo r0 = r8.getOneof()
            r1 = 0
            if (r0 == 0) goto L25
            com.google.protobuf.FieldType r2 = r8.getType()
            int r2 = r2.id()
            int r2 = r2 + 51
            java.lang.reflect.Field r3 = r0.getValueField()
            long r3 = com.google.protobuf.UnsafeUtil.C(r3)
            int r3 = (int) r3
            java.lang.reflect.Field r0 = r0.getCaseField()
            long r4 = com.google.protobuf.UnsafeUtil.C(r0)
        L22:
            int r0 = (int) r4
            r4 = r1
            goto L6c
        L25:
            com.google.protobuf.FieldType r0 = r8.getType()
            java.lang.reflect.Field r2 = r8.getField()
            long r2 = com.google.protobuf.UnsafeUtil.C(r2)
            int r3 = (int) r2
            int r2 = r0.id()
            boolean r4 = r0.isList()
            if (r4 != 0) goto L5a
            boolean r0 = r0.isMap()
            if (r0 != 0) goto L5a
            java.lang.reflect.Field r0 = r8.getPresenceField()
            if (r0 != 0) goto L4c
            r0 = 1048575(0xfffff, float:1.469367E-39)
            goto L51
        L4c:
            long r4 = com.google.protobuf.UnsafeUtil.C(r0)
            int r0 = (int) r4
        L51:
            int r4 = r8.getPresenceMask()
            int r4 = java.lang.Integer.numberOfTrailingZeros(r4)
            goto L6c
        L5a:
            java.lang.reflect.Field r0 = r8.getCachedSizeField()
            if (r0 != 0) goto L63
            r0 = r1
            r4 = r0
            goto L6c
        L63:
            java.lang.reflect.Field r0 = r8.getCachedSizeField()
            long r4 = com.google.protobuf.UnsafeUtil.C(r0)
            goto L22
        L6c:
            int r5 = r8.getFieldNumber()
            r9[r10] = r5
            int r5 = r10 + 1
            boolean r6 = r8.isEnforceUtf8()
            if (r6 == 0) goto L7d
            r6 = 536870912(0x20000000, float:1.0842022E-19)
            goto L7e
        L7d:
            r6 = r1
        L7e:
            boolean r7 = r8.isRequired()
            if (r7 == 0) goto L86
            r1 = 268435456(0x10000000, float:2.524355E-29)
        L86:
            r1 = r1 | r6
            int r2 = r2 << 20
            r1 = r1 | r2
            r1 = r1 | r3
            r9[r5] = r1
            int r1 = r10 + 2
            int r2 = r4 << 20
            r0 = r0 | r2
            r9[r1] = r0
            java.lang.Class r9 = r8.getMessageFieldClass()
            java.lang.Object r0 = r8.getMapDefaultEntry()
            if (r0 == 0) goto Lbe
            int r10 = r10 / 3
            int r10 = r10 * 2
            java.lang.Object r0 = r8.getMapDefaultEntry()
            r11[r10] = r0
            if (r9 == 0) goto Laf
            int r10 = r10 + 1
            r11[r10] = r9
            goto Ldb
        Laf:
            com.google.protobuf.Internal$EnumVerifier r9 = r8.getEnumVerifier()
            if (r9 == 0) goto Ldb
            int r10 = r10 + 1
            com.google.protobuf.Internal$EnumVerifier r8 = r8.getEnumVerifier()
            r11[r10] = r8
            goto Ldb
        Lbe:
            if (r9 == 0) goto Lc9
            int r10 = r10 / 3
            int r10 = r10 * 2
            int r10 = r10 + 1
            r11[r10] = r9
            goto Ldb
        Lc9:
            com.google.protobuf.Internal$EnumVerifier r9 = r8.getEnumVerifier()
            if (r9 == 0) goto Ldb
            int r10 = r10 / 3
            int r10 = r10 * 2
            int r10 = r10 + 1
            com.google.protobuf.Internal$EnumVerifier r8 = r8.getEnumVerifier()
            r11[r10] = r8
        Ldb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.storeFieldData(com.google.protobuf.FieldInfo, int[], int, java.lang.Object[]):void");
    }

    private void storeMessageField(T t2, int i2, Object obj) {
        UNSAFE.putObject(t2, offset(typeAndOffsetAt(i2)), obj);
        setFieldPresent(t2, i2);
    }

    private void storeOneofMessageField(T t2, int i2, int i3, Object obj) {
        UNSAFE.putObject(t2, offset(typeAndOffsetAt(i3)), obj);
        setOneofPresent(t2, i2, i3);
    }

    private static int type(int i2) {
        return (i2 & FIELD_TYPE_MASK) >>> 20;
    }

    private int typeAndOffsetAt(int i2) {
        return this.buffer[i2 + 1];
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInAscendingOrderProto2(T r18, com.google.protobuf.Writer r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.writeFieldsInAscendingOrderProto2(java.lang.Object, com.google.protobuf.Writer):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInAscendingOrderProto3(T r13, com.google.protobuf.Writer r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.writeFieldsInAscendingOrderProto3(java.lang.Object, com.google.protobuf.Writer):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInDescendingOrder(T r11, com.google.protobuf.Writer r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.writeFieldsInDescendingOrder(java.lang.Object, com.google.protobuf.Writer):void");
    }

    private <K, V> void writeMapHelper(Writer writer, int i2, Object obj, int i3) throws IOException {
        if (obj != null) {
            writer.writeMap(i2, this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i3)), this.mapFieldSchema.forMapData(obj));
        }
    }

    private void writeString(int i2, Object obj, Writer writer) throws IOException {
        if (obj instanceof String) {
            writer.writeString(i2, (String) obj);
        } else {
            writer.writeBytes(i2, (ByteString) obj);
        }
    }

    private <UT, UB> void writeUnknownInMessageTo(UnknownFieldSchema<UT, UB> unknownFieldSchema, T t2, Writer writer) throws IOException {
        unknownFieldSchema.t(unknownFieldSchema.g(t2), writer);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:24:0x0090. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    int e(Object obj, byte[] bArr, int i2, int i3, int i4, ArrayDecoders.Registers registers) {
        Unsafe unsafe;
        int i5;
        MessageSchema<T> messageSchema;
        int i6;
        int i7;
        int i8;
        int i9;
        Object obj2;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        byte[] bArr2;
        int iL;
        int i21;
        int i22;
        MessageSchema<T> messageSchema2 = this;
        T t2 = obj;
        byte[] bArr3 = bArr;
        int i23 = i3;
        int i24 = i4;
        ArrayDecoders.Registers registers2 = registers;
        checkMutable(obj);
        Unsafe unsafe2 = UNSAFE;
        int iG = i2;
        int i25 = 0;
        int i26 = 0;
        int i27 = 0;
        int i28 = -1;
        int i29 = 1048575;
        while (true) {
            if (iG < i23) {
                int i30 = iG + 1;
                byte b2 = bArr3[iG];
                if (b2 < 0) {
                    int iH = ArrayDecoders.H(b2, bArr3, i30, registers2);
                    i10 = registers2.int1;
                    i30 = iH;
                } else {
                    i10 = b2;
                }
                int i31 = i10 >>> 3;
                int i32 = i10 & 7;
                int iPositionForFieldNumber = i31 > i28 ? messageSchema2.positionForFieldNumber(i31, i25 / 3) : messageSchema2.positionForFieldNumber(i31);
                if (iPositionForFieldNumber == -1) {
                    i11 = i31;
                    i12 = i30;
                    i7 = i10;
                    i13 = i27;
                    i14 = i29;
                    unsafe = unsafe2;
                    i5 = i24;
                    i15 = 0;
                } else {
                    int i33 = messageSchema2.buffer[iPositionForFieldNumber + 1];
                    int iType = type(i33);
                    long jOffset = offset(i33);
                    int i34 = i10;
                    if (iType <= 17) {
                        int i35 = messageSchema2.buffer[iPositionForFieldNumber + 2];
                        int i36 = 1 << (i35 >>> 20);
                        int i37 = i35 & 1048575;
                        if (i37 != i29) {
                            if (i29 != 1048575) {
                                unsafe2.putInt(t2, i29, i27);
                            }
                            i17 = i37;
                            i16 = unsafe2.getInt(t2, i37);
                        } else {
                            i16 = i27;
                            i17 = i29;
                        }
                        switch (iType) {
                            case 0:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 1) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    UnsafeUtil.G(t2, jOffset, ArrayDecoders.d(bArr2, i30));
                                    iG = i30 + 8;
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 1:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 5) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    UnsafeUtil.H(t2, jOffset, ArrayDecoders.l(bArr2, i30));
                                    iG = i30 + 4;
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 2:
                            case 3:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 0) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    iL = ArrayDecoders.L(bArr2, i30, registers2);
                                    unsafe2.putLong(obj, jOffset, registers2.long1);
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    iG = iL;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 4:
                            case 11:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 0) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    iG = ArrayDecoders.I(bArr2, i30, registers2);
                                    unsafe2.putInt(t2, jOffset, registers2.int1);
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 5:
                            case 14:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 1) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    unsafe2.putLong(obj, jOffset, ArrayDecoders.j(bArr2, i30));
                                    iG = i30 + 8;
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 6:
                            case 13:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 5) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    unsafe2.putInt(t2, jOffset, ArrayDecoders.h(bArr2, i30));
                                    iG = i30 + 4;
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 7:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 0) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    iG = ArrayDecoders.L(bArr2, i30, registers2);
                                    UnsafeUtil.D(t2, jOffset, registers2.long1 != 0);
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 8:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 2) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    iG = (536870912 & i33) == 0 ? ArrayDecoders.C(bArr2, i30, registers2) : ArrayDecoders.F(bArr2, i30, registers2);
                                    unsafe2.putObject(t2, jOffset, registers2.object1);
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 9:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 2) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    Object objMutableMessageFieldForMerge = messageSchema2.mutableMessageFieldForMerge(t2, i20);
                                    iG = ArrayDecoders.O(objMutableMessageFieldForMerge, messageSchema2.getMessageFieldSchema(i20), bArr, i30, i3, registers);
                                    messageSchema2.storeMessageField(t2, i20, objMutableMessageFieldForMerge);
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 10:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 2) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    iG = ArrayDecoders.b(bArr2, i30, registers2);
                                    unsafe2.putObject(t2, jOffset, registers2.object1);
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 12:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 0) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    iG = ArrayDecoders.I(bArr2, i30, registers2);
                                    int i38 = registers2.int1;
                                    Internal.EnumVerifier enumFieldVerifier = messageSchema2.getEnumFieldVerifier(i20);
                                    if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i38)) {
                                        unsafe2.putInt(t2, jOffset, i38);
                                        i27 = i16 | i36;
                                        i24 = i4;
                                        i25 = i20;
                                        i26 = i19;
                                        i28 = i11;
                                        i29 = i18;
                                        bArr3 = bArr2;
                                    } else {
                                        a(obj).i(i19, Long.valueOf(i38));
                                        i25 = i20;
                                        i27 = i16;
                                        i26 = i19;
                                        i28 = i11;
                                        i29 = i18;
                                        i24 = i4;
                                        bArr3 = bArr2;
                                    }
                                }
                                break;
                            case 15:
                                bArr2 = bArr;
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 != 0) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    iG = ArrayDecoders.I(bArr2, i30, registers2);
                                    unsafe2.putInt(t2, jOffset, CodedInputStream.decodeZigZag32(registers2.int1));
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 16:
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                bArr2 = bArr;
                                if (i32 != 0) {
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    iL = ArrayDecoders.L(bArr2, i30, registers2);
                                    unsafe2.putLong(obj, jOffset, CodedInputStream.decodeZigZag64(registers2.long1));
                                    i27 = i16 | i36;
                                    i24 = i4;
                                    i25 = i20;
                                    iG = iL;
                                    i26 = i19;
                                    i28 = i11;
                                    i29 = i18;
                                    bArr3 = bArr2;
                                }
                            case 17:
                                if (i32 != 3) {
                                    i11 = i31;
                                    i18 = i17;
                                    i19 = i34;
                                    i20 = iPositionForFieldNumber;
                                    i14 = i18;
                                    i5 = i4;
                                    i12 = i30;
                                    i15 = i20;
                                    unsafe = unsafe2;
                                    i13 = i16;
                                    i7 = i19;
                                    break;
                                } else {
                                    Object objMutableMessageFieldForMerge2 = messageSchema2.mutableMessageFieldForMerge(t2, iPositionForFieldNumber);
                                    iG = ArrayDecoders.N(objMutableMessageFieldForMerge2, messageSchema2.getMessageFieldSchema(iPositionForFieldNumber), bArr, i30, i3, (i31 << 3) | 4, registers);
                                    messageSchema2.storeMessageField(t2, iPositionForFieldNumber, objMutableMessageFieldForMerge2);
                                    i27 = i16 | i36;
                                    i29 = i17;
                                    i24 = i4;
                                    i25 = iPositionForFieldNumber;
                                    i26 = i34;
                                    i28 = i31;
                                    bArr3 = bArr;
                                }
                            default:
                                i11 = i31;
                                i20 = iPositionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                i14 = i18;
                                i5 = i4;
                                i12 = i30;
                                i15 = i20;
                                unsafe = unsafe2;
                                i13 = i16;
                                i7 = i19;
                                break;
                        }
                    } else {
                        i11 = i31;
                        i14 = i29;
                        i13 = i27;
                        if (iType == 27) {
                            if (i32 == 2) {
                                Internal.ProtobufList protobufListMutableCopyWithCapacity2 = (Internal.ProtobufList) unsafe2.getObject(t2, jOffset);
                                if (!protobufListMutableCopyWithCapacity2.isModifiable()) {
                                    int size = protobufListMutableCopyWithCapacity2.size();
                                    protobufListMutableCopyWithCapacity2 = protobufListMutableCopyWithCapacity2.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
                                    unsafe2.putObject(t2, jOffset, protobufListMutableCopyWithCapacity2);
                                }
                                iG = ArrayDecoders.q(messageSchema2.getMessageFieldSchema(iPositionForFieldNumber), i34, bArr, i30, i3, protobufListMutableCopyWithCapacity2, registers);
                                i25 = iPositionForFieldNumber;
                                i26 = i34;
                                i29 = i14;
                                i27 = i13;
                                i28 = i11;
                                bArr3 = bArr;
                                i24 = i4;
                            } else {
                                i21 = i30;
                                unsafe = unsafe2;
                                i15 = iPositionForFieldNumber;
                                i22 = i34;
                                i5 = i4;
                                i12 = i21;
                            }
                        } else if (iType <= 49) {
                            int i39 = i30;
                            unsafe = unsafe2;
                            i15 = iPositionForFieldNumber;
                            i22 = i34;
                            iG = parseRepeatedField(obj, bArr, i30, i3, i34, i11, i32, iPositionForFieldNumber, i33, iType, jOffset, registers);
                            if (iG != i39) {
                                messageSchema2 = this;
                                t2 = obj;
                                bArr3 = bArr;
                                i23 = i3;
                                i24 = i4;
                                registers2 = registers;
                                i29 = i14;
                                i27 = i13;
                                i25 = i15;
                                i26 = i22;
                                i28 = i11;
                                unsafe2 = unsafe;
                            } else {
                                i5 = i4;
                                i12 = iG;
                            }
                        } else {
                            i21 = i30;
                            unsafe = unsafe2;
                            i15 = iPositionForFieldNumber;
                            i22 = i34;
                            if (iType != 50) {
                                iG = parseOneofField(obj, bArr, i21, i3, i22, i11, i32, i33, iType, jOffset, i15, registers);
                                if (iG != i21) {
                                    messageSchema2 = this;
                                    t2 = obj;
                                    bArr3 = bArr;
                                    i23 = i3;
                                    i24 = i4;
                                    registers2 = registers;
                                    i29 = i14;
                                    i27 = i13;
                                    i25 = i15;
                                    i26 = i22;
                                    i28 = i11;
                                    unsafe2 = unsafe;
                                } else {
                                    i5 = i4;
                                    i12 = iG;
                                }
                            } else if (i32 == 2) {
                                iG = parseMapField(obj, bArr, i21, i3, i15, jOffset, registers);
                                if (iG != i21) {
                                    messageSchema2 = this;
                                    t2 = obj;
                                    bArr3 = bArr;
                                    i23 = i3;
                                    i24 = i4;
                                    registers2 = registers;
                                    i29 = i14;
                                    i27 = i13;
                                    i25 = i15;
                                    i26 = i22;
                                    i28 = i11;
                                    unsafe2 = unsafe;
                                } else {
                                    i5 = i4;
                                    i12 = iG;
                                }
                            } else {
                                i5 = i4;
                                i12 = i21;
                            }
                        }
                        i7 = i22;
                    }
                }
                if (i7 != i5 || i5 == 0) {
                    iG = (!this.hasExtensions || registers.extensionRegistry == ExtensionRegistryLite.getEmptyRegistry()) ? ArrayDecoders.G(i7, bArr, i12, i3, a(obj), registers) : ArrayDecoders.g(i7, bArr, i12, i3, obj, this.defaultInstance, this.unknownFieldSchema, registers);
                    t2 = obj;
                    bArr3 = bArr;
                    i23 = i3;
                    i26 = i7;
                    messageSchema2 = this;
                    registers2 = registers;
                    i29 = i14;
                    i27 = i13;
                    i25 = i15;
                    i28 = i11;
                    unsafe2 = unsafe;
                    i24 = i5;
                } else {
                    i9 = 1048575;
                    messageSchema = this;
                    i6 = i12;
                    i8 = i14;
                    i27 = i13;
                }
            } else {
                int i40 = i29;
                unsafe = unsafe2;
                i5 = i24;
                messageSchema = messageSchema2;
                i6 = iG;
                i7 = i26;
                i8 = i40;
                i9 = 1048575;
            }
        }
        if (i8 != i9) {
            obj2 = obj;
            unsafe.putInt(obj2, i8, i27);
        } else {
            obj2 = obj;
        }
        UnknownFieldSetLite unknownFieldSetLite = null;
        for (int i41 = messageSchema.checkInitializedCount; i41 < messageSchema.repeatedFieldOffsetStart; i41++) {
            unknownFieldSetLite = (UnknownFieldSetLite) filterMapUnknownEnumValues(obj, messageSchema.intArray[i41], unknownFieldSetLite, messageSchema.unknownFieldSchema, obj);
        }
        if (unknownFieldSetLite != null) {
            messageSchema.unknownFieldSchema.o(obj2, unknownFieldSetLite);
        }
        if (i5 == 0) {
            if (i6 != i3) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        } else if (i6 > i3 || i7 != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        return i6;
    }

    @Override // com.google.protobuf.Schema
    public boolean equals(T t2, T t3) {
        int length = this.buffer.length;
        for (int i2 = 0; i2 < length; i2 += 3) {
            if (!equals(t2, t3, i2)) {
                return false;
            }
        }
        if (!this.unknownFieldSchema.g(t2).equals(this.unknownFieldSchema.g(t3))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(t2).equals(this.extensionSchema.getExtensions(t3));
        }
        return true;
    }

    @Override // com.google.protobuf.Schema
    public int getSerializedSize(T t2) {
        return this.proto3 ? getSerializedSizeProto3(t2) : getSerializedSizeProto2(t2);
    }

    @Override // com.google.protobuf.Schema
    public int hashCode(T t2) {
        int i2;
        int iHashLong;
        int length = this.buffer.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i4);
            int iNumberAt = numberAt(i4);
            long jOffset = offset(iTypeAndOffsetAt);
            int iHashCode = 37;
            switch (type(iTypeAndOffsetAt)) {
                case 0:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.s(t2, jOffset)));
                    i3 = i2 + iHashLong;
                    break;
                case 1:
                    i2 = i3 * 53;
                    iHashLong = Float.floatToIntBits(UnsafeUtil.t(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 2:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.w(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 3:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.w(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 4:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.u(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 5:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.w(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 6:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.u(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 7:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashBoolean(UnsafeUtil.p(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 8:
                    i2 = i3 * 53;
                    iHashLong = ((String) UnsafeUtil.x(t2, jOffset)).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 9:
                    Object objX = UnsafeUtil.x(t2, jOffset);
                    if (objX != null) {
                        iHashCode = objX.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 10:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.x(t2, jOffset).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 11:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.u(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 12:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.u(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 13:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.u(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 14:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.w(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 15:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.u(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 16:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.w(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 17:
                    Object objX2 = UnsafeUtil.x(t2, jOffset);
                    if (objX2 != null) {
                        iHashCode = objX2.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.x(t2, jOffset).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 50:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.x(t2, jOffset).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 51:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(Double.doubleToLongBits(oneofDoubleAt(t2, jOffset)));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Float.floatToIntBits(oneofFloatAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashBoolean(oneofBooleanAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = ((String) UnsafeUtil.x(t2, jOffset)).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = UnsafeUtil.x(t2, jOffset).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = UnsafeUtil.x(t2, jOffset).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = oneofIntAt(t2, jOffset);
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(t2, jOffset));
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = UnsafeUtil.x(t2, jOffset).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i3 * 53) + this.unknownFieldSchema.g(t2).hashCode();
        return this.hasExtensions ? (iHashCode2 * 53) + this.extensionSchema.getExtensions(t2).hashCode() : iHashCode2;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0089  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isInitialized(T r18) {
        /*
            r17 = this;
            r6 = r17
            r7 = r18
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r9 = 0
            r0 = r8
            r1 = r9
            r10 = r1
        Lb:
            int r2 = r6.checkInitializedCount
            r3 = 1
            if (r10 >= r2) goto Lb1
            int[] r2 = r6.intArray
            r11 = r2[r10]
            int r12 = r6.numberAt(r11)
            int r13 = r6.typeAndOffsetAt(r11)
            int[] r2 = r6.buffer
            int r4 = r11 + 2
            r2 = r2[r4]
            r4 = r2 & r8
            int r2 = r2 >>> 20
            int r14 = r3 << r2
            if (r4 == r0) goto L37
            if (r4 == r8) goto L33
            sun.misc.Unsafe r0 = com.google.protobuf.MessageSchema.UNSAFE
            long r1 = (long) r4
            int r1 = r0.getInt(r7, r1)
        L33:
            r16 = r1
            r15 = r4
            goto L3a
        L37:
            r15 = r0
            r16 = r1
        L3a:
            boolean r0 = isRequired(r13)
            if (r0 == 0) goto L50
            r0 = r17
            r1 = r18
            r2 = r11
            r3 = r15
            r4 = r16
            r5 = r14
            boolean r0 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L50
            return r9
        L50:
            int r0 = type(r13)
            r1 = 9
            if (r0 == r1) goto L90
            r1 = 17
            if (r0 == r1) goto L90
            r1 = 27
            if (r0 == r1) goto L89
            r1 = 60
            if (r0 == r1) goto L78
            r1 = 68
            if (r0 == r1) goto L78
            r1 = 49
            if (r0 == r1) goto L89
            r1 = 50
            if (r0 == r1) goto L71
            goto Laa
        L71:
            boolean r0 = r6.isMapInitialized(r7, r13, r11)
            if (r0 != 0) goto Laa
            return r9
        L78:
            boolean r0 = r6.isOneofPresent(r7, r12, r11)
            if (r0 == 0) goto Laa
            com.google.protobuf.Schema r0 = r6.getMessageFieldSchema(r11)
            boolean r0 = isInitialized(r7, r13, r0)
            if (r0 != 0) goto Laa
            return r9
        L89:
            boolean r0 = r6.isListInitialized(r7, r13, r11)
            if (r0 != 0) goto Laa
            return r9
        L90:
            r0 = r17
            r1 = r18
            r2 = r11
            r3 = r15
            r4 = r16
            r5 = r14
            boolean r0 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r0 == 0) goto Laa
            com.google.protobuf.Schema r0 = r6.getMessageFieldSchema(r11)
            boolean r0 = isInitialized(r7, r13, r0)
            if (r0 != 0) goto Laa
            return r9
        Laa:
            int r10 = r10 + 1
            r0 = r15
            r1 = r16
            goto Lb
        Lb1:
            boolean r0 = r6.hasExtensions
            if (r0 == 0) goto Lc2
            com.google.protobuf.ExtensionSchema<?> r0 = r6.extensionSchema
            com.google.protobuf.FieldSet r0 = r0.getExtensions(r7)
            boolean r0 = r0.isInitialized()
            if (r0 != 0) goto Lc2
            return r9
        Lc2:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.isInitialized(java.lang.Object):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0069  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void makeImmutable(T r8) {
        /*
            r7 = this;
            boolean r0 = isMutable(r8)
            if (r0 != 0) goto L7
            return
        L7:
            boolean r0 = r8 instanceof com.google.protobuf.GeneratedMessageLite
            if (r0 == 0) goto L17
            r0 = r8
            com.google.protobuf.GeneratedMessageLite r0 = (com.google.protobuf.GeneratedMessageLite) r0
            r0.clearMemoizedSerializedSize()
            r0.clearMemoizedHashCode()
            r0.markImmutable()
        L17:
            int[] r0 = r7.buffer
            int r0 = r0.length
            r1 = 0
        L1b:
            if (r1 >= r0) goto L7f
            int r2 = r7.typeAndOffsetAt(r1)
            long r3 = offset(r2)
            int r2 = type(r2)
            r5 = 9
            if (r2 == r5) goto L69
            r5 = 60
            if (r2 == r5) goto L51
            r5 = 68
            if (r2 == r5) goto L51
            switch(r2) {
                case 17: goto L69;
                case 18: goto L4b;
                case 19: goto L4b;
                case 20: goto L4b;
                case 21: goto L4b;
                case 22: goto L4b;
                case 23: goto L4b;
                case 24: goto L4b;
                case 25: goto L4b;
                case 26: goto L4b;
                case 27: goto L4b;
                case 28: goto L4b;
                case 29: goto L4b;
                case 30: goto L4b;
                case 31: goto L4b;
                case 32: goto L4b;
                case 33: goto L4b;
                case 34: goto L4b;
                case 35: goto L4b;
                case 36: goto L4b;
                case 37: goto L4b;
                case 38: goto L4b;
                case 39: goto L4b;
                case 40: goto L4b;
                case 41: goto L4b;
                case 42: goto L4b;
                case 43: goto L4b;
                case 44: goto L4b;
                case 45: goto L4b;
                case 46: goto L4b;
                case 47: goto L4b;
                case 48: goto L4b;
                case 49: goto L4b;
                case 50: goto L39;
                default: goto L38;
            }
        L38:
            goto L7c
        L39:
            sun.misc.Unsafe r2 = com.google.protobuf.MessageSchema.UNSAFE
            java.lang.Object r5 = r2.getObject(r8, r3)
            if (r5 == 0) goto L7c
            com.google.protobuf.MapFieldSchema r6 = r7.mapFieldSchema
            java.lang.Object r5 = r6.toImmutable(r5)
            r2.putObject(r8, r3, r5)
            goto L7c
        L4b:
            com.google.protobuf.ListFieldSchema r2 = r7.listFieldSchema
            r2.c(r8, r3)
            goto L7c
        L51:
            int r2 = r7.numberAt(r1)
            boolean r2 = r7.isOneofPresent(r8, r2, r1)
            if (r2 == 0) goto L7c
            com.google.protobuf.Schema r2 = r7.getMessageFieldSchema(r1)
            sun.misc.Unsafe r5 = com.google.protobuf.MessageSchema.UNSAFE
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.makeImmutable(r3)
            goto L7c
        L69:
            boolean r2 = r7.isFieldPresent(r8, r1)
            if (r2 == 0) goto L7c
            com.google.protobuf.Schema r2 = r7.getMessageFieldSchema(r1)
            sun.misc.Unsafe r5 = com.google.protobuf.MessageSchema.UNSAFE
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.makeImmutable(r3)
        L7c:
            int r1 = r1 + 3
            goto L1b
        L7f:
            com.google.protobuf.UnknownFieldSchema<?, ?> r0 = r7.unknownFieldSchema
            r0.j(r8)
            boolean r0 = r7.hasExtensions
            if (r0 == 0) goto L8d
            com.google.protobuf.ExtensionSchema<?> r0 = r7.extensionSchema
            r0.e(r8)
        L8d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.makeImmutable(java.lang.Object):void");
    }

    @Override // com.google.protobuf.Schema
    public void mergeFrom(T t2, T t3) {
        checkMutable(t2);
        t3.getClass();
        for (int i2 = 0; i2 < this.buffer.length; i2 += 3) {
            mergeSingleField(t2, t3, i2);
        }
        SchemaUtil.E(this.unknownFieldSchema, t2, t3);
        if (this.hasExtensions) {
            SchemaUtil.C(this.extensionSchema, t2, t3);
        }
    }

    @Override // com.google.protobuf.Schema
    public T newInstance() {
        return (T) this.newInstanceSchema.newInstance(this.defaultInstance);
    }

    @Override // com.google.protobuf.Schema
    public void writeTo(T t2, Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            writeFieldsInDescendingOrder(t2, writer);
        } else if (this.proto3) {
            writeFieldsInAscendingOrderProto3(t2, writer);
        } else {
            writeFieldsInAscendingOrderProto2(t2, writer);
        }
    }

    private boolean isFieldPresent(T t2, int i2) {
        int iPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i2);
        long j2 = 1048575 & iPresenceMaskAndOffsetAt;
        if (j2 != 1048575) {
            return (UnsafeUtil.u(t2, j2) & (1 << (iPresenceMaskAndOffsetAt >>> 20))) != 0;
        }
        int iTypeAndOffsetAt = typeAndOffsetAt(i2);
        long jOffset = offset(iTypeAndOffsetAt);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                return Double.doubleToRawLongBits(UnsafeUtil.s(t2, jOffset)) != 0;
            case 1:
                return Float.floatToRawIntBits(UnsafeUtil.t(t2, jOffset)) != 0;
            case 2:
                return UnsafeUtil.w(t2, jOffset) != 0;
            case 3:
                return UnsafeUtil.w(t2, jOffset) != 0;
            case 4:
                return UnsafeUtil.u(t2, jOffset) != 0;
            case 5:
                return UnsafeUtil.w(t2, jOffset) != 0;
            case 6:
                return UnsafeUtil.u(t2, jOffset) != 0;
            case 7:
                return UnsafeUtil.p(t2, jOffset);
            case 8:
                Object objX = UnsafeUtil.x(t2, jOffset);
                if (objX instanceof String) {
                    return !((String) objX).isEmpty();
                }
                if (objX instanceof ByteString) {
                    return !ByteString.EMPTY.equals(objX);
                }
                throw new IllegalArgumentException();
            case 9:
                return UnsafeUtil.x(t2, jOffset) != null;
            case 10:
                return !ByteString.EMPTY.equals(UnsafeUtil.x(t2, jOffset));
            case 11:
                return UnsafeUtil.u(t2, jOffset) != 0;
            case 12:
                return UnsafeUtil.u(t2, jOffset) != 0;
            case 13:
                return UnsafeUtil.u(t2, jOffset) != 0;
            case 14:
                return UnsafeUtil.w(t2, jOffset) != 0;
            case 15:
                return UnsafeUtil.u(t2, jOffset) != 0;
            case 16:
                return UnsafeUtil.w(t2, jOffset) != 0;
            case 17:
                return UnsafeUtil.x(t2, jOffset) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private int positionForFieldNumber(int i2, int i3) {
        if (i2 < this.minFieldNumber || i2 > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(i2, i3);
    }

    @Override // com.google.protobuf.Schema
    public void mergeFrom(T t2, Reader reader, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
        extensionRegistryLite.getClass();
        checkMutable(t2);
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, t2, reader, extensionRegistryLite);
    }

    private boolean equals(T t2, T t3, int i2) {
        int iTypeAndOffsetAt = typeAndOffsetAt(i2);
        long jOffset = offset(iTypeAndOffsetAt);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                if (arePresentForEquals(t2, t3, i2) && Double.doubleToLongBits(UnsafeUtil.s(t2, jOffset)) == Double.doubleToLongBits(UnsafeUtil.s(t3, jOffset))) {
                    break;
                }
                break;
            case 1:
                if (arePresentForEquals(t2, t3, i2) && Float.floatToIntBits(UnsafeUtil.t(t2, jOffset)) == Float.floatToIntBits(UnsafeUtil.t(t3, jOffset))) {
                    break;
                }
                break;
            case 2:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.w(t2, jOffset) == UnsafeUtil.w(t3, jOffset)) {
                    break;
                }
                break;
            case 3:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.w(t2, jOffset) == UnsafeUtil.w(t3, jOffset)) {
                    break;
                }
                break;
            case 4:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.u(t2, jOffset) == UnsafeUtil.u(t3, jOffset)) {
                    break;
                }
                break;
            case 5:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.w(t2, jOffset) == UnsafeUtil.w(t3, jOffset)) {
                    break;
                }
                break;
            case 6:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.u(t2, jOffset) == UnsafeUtil.u(t3, jOffset)) {
                    break;
                }
                break;
            case 7:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.p(t2, jOffset) == UnsafeUtil.p(t3, jOffset)) {
                    break;
                }
                break;
            case 8:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.F(UnsafeUtil.x(t2, jOffset), UnsafeUtil.x(t3, jOffset))) {
                    break;
                }
                break;
            case 9:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.F(UnsafeUtil.x(t2, jOffset), UnsafeUtil.x(t3, jOffset))) {
                    break;
                }
                break;
            case 10:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.F(UnsafeUtil.x(t2, jOffset), UnsafeUtil.x(t3, jOffset))) {
                    break;
                }
                break;
            case 11:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.u(t2, jOffset) == UnsafeUtil.u(t3, jOffset)) {
                    break;
                }
                break;
            case 12:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.u(t2, jOffset) == UnsafeUtil.u(t3, jOffset)) {
                    break;
                }
                break;
            case 13:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.u(t2, jOffset) == UnsafeUtil.u(t3, jOffset)) {
                    break;
                }
                break;
            case 14:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.w(t2, jOffset) == UnsafeUtil.w(t3, jOffset)) {
                    break;
                }
                break;
            case 15:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.u(t2, jOffset) == UnsafeUtil.u(t3, jOffset)) {
                    break;
                }
                break;
            case 16:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.w(t2, jOffset) == UnsafeUtil.w(t3, jOffset)) {
                    break;
                }
                break;
            case 17:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.F(UnsafeUtil.x(t2, jOffset), UnsafeUtil.x(t3, jOffset))) {
                    break;
                }
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                if (isOneofCaseEqual(t2, t3, i2) && SchemaUtil.F(UnsafeUtil.x(t2, jOffset), UnsafeUtil.x(t3, jOffset))) {
                    break;
                }
                break;
        }
        return true;
    }

    @Override // com.google.protobuf.Schema
    public void mergeFrom(T t2, byte[] bArr, int i2, int i3, ArrayDecoders.Registers registers) throws IOException {
        if (this.proto3) {
            parseProto3Message(t2, bArr, i2, i3, registers);
        } else {
            e(t2, bArr, i2, i3, 0, registers);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isInitialized(Object obj, int i2, Schema schema) {
        return schema.isInitialized(UnsafeUtil.x(obj, offset(i2)));
    }
}
