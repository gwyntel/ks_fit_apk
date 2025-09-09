package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.ArrayDecoders;
import androidx.datastore.preferences.protobuf.ByteString;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.MapEntryLite;
import androidx.datastore.preferences.protobuf.WireFormat;
import androidx.datastore.preferences.protobuf.Writer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* loaded from: classes.dex */
final class MessageSchema<T> implements Schema<T> {
    private static final int ENFORCE_UTF8_MASK = 536870912;
    private static final int FIELD_TYPE_MASK = 267386880;
    private static final int INTS_PER_FIELD = 3;
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
    private static final Unsafe UNSAFE = UnsafeUtil.v();

    /* renamed from: androidx.datastore.preferences.protobuf.MessageSchema$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f3954a;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            f3954a = iArr;
            try {
                iArr[WireFormat.FieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f3954a[WireFormat.FieldType.BYTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f3954a[WireFormat.FieldType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f3954a[WireFormat.FieldType.FIXED32.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f3954a[WireFormat.FieldType.SFIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f3954a[WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f3954a[WireFormat.FieldType.SFIXED64.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f3954a[WireFormat.FieldType.FLOAT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f3954a[WireFormat.FieldType.ENUM.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f3954a[WireFormat.FieldType.INT32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f3954a[WireFormat.FieldType.UINT32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f3954a[WireFormat.FieldType.INT64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f3954a[WireFormat.FieldType.UINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f3954a[WireFormat.FieldType.MESSAGE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f3954a[WireFormat.FieldType.SINT32.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f3954a[WireFormat.FieldType.SINT64.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f3954a[WireFormat.FieldType.STRING.ordinal()] = 17;
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
        this.hasExtensions = extensionSchema != null && extensionSchema.e(messageLite);
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
        UnknownFieldSetLite unknownFieldSetLiteF = UnknownFieldSetLite.f();
        generatedMessageLite.unknownFields = unknownFieldSetLiteF;
        return unknownFieldSetLiteF;
    }

    private boolean arePresentForEquals(T t2, T t3, int i2) {
        return isFieldPresent(t2, i2) == isFieldPresent(t3, i2);
    }

    static MessageSchema b(Class cls, MessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MapFieldSchema mapFieldSchema) {
        return messageInfo instanceof RawMessageInfo ? d((RawMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema) : c((StructuralMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    private static <T> boolean booleanAt(T t2, long j2) {
        return UnsafeUtil.m(t2, j2);
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
            storeFieldData(fieldInfo2, iArr, i6, z2, objArr);
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
                    iArr3[i9] = (int) UnsafeUtil.y(fieldInfo2.getField());
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

    /* JADX WARN: Removed duplicated region for block: B:124:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x03a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static androidx.datastore.preferences.protobuf.MessageSchema d(androidx.datastore.preferences.protobuf.RawMessageInfo r35, androidx.datastore.preferences.protobuf.NewInstanceSchema r36, androidx.datastore.preferences.protobuf.ListFieldSchema r37, androidx.datastore.preferences.protobuf.UnknownFieldSchema r38, androidx.datastore.preferences.protobuf.ExtensionSchema r39, androidx.datastore.preferences.protobuf.MapFieldSchema r40) {
        /*
            Method dump skipped, instructions count: 1053
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.d(androidx.datastore.preferences.protobuf.RawMessageInfo, androidx.datastore.preferences.protobuf.NewInstanceSchema, androidx.datastore.preferences.protobuf.ListFieldSchema, androidx.datastore.preferences.protobuf.UnknownFieldSchema, androidx.datastore.preferences.protobuf.ExtensionSchema, androidx.datastore.preferences.protobuf.MapFieldSchema):androidx.datastore.preferences.protobuf.MessageSchema");
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
                    I = ArrayDecoders.N(b2, bArr, iH, i3, registers);
                }
            } else if (i8 == metadata.keyType.getWireType()) {
                I = decodeMapEntryValue(bArr, iH, i3, metadata.keyType, null, registers);
                obj = registers.object1;
            } else {
                I = ArrayDecoders.N(b2, bArr, iH, i3, registers);
            }
        }
        if (I != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        map.put(obj, obj2);
        return i5;
    }

    private int decodeMapEntryValue(byte[] bArr, int i2, int i3, WireFormat.FieldType fieldType, Class<?> cls, ArrayDecoders.Registers registers) throws IOException {
        switch (AnonymousClass1.f3954a[fieldType.ordinal()]) {
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
        return UnsafeUtil.p(t2, j2);
    }

    private final <UT, UB> UB filterMapUnknownEnumValues(Object obj, int i2, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        Internal.EnumVerifier enumFieldVerifier;
        int iNumberAt = numberAt(i2);
        Object objU = UnsafeUtil.u(obj, offset(typeAndOffsetAt(i2)));
        return (objU == null || (enumFieldVerifier = getEnumFieldVerifier(i2)) == null) ? ub : (UB) filterUnknownEnumMap(i2, iNumberAt, this.mapFieldSchema.forMutableMapData(objU), enumFieldVerifier, ub, unknownFieldSchema);
    }

    private final <K, V, UT, UB> UB filterUnknownEnumMap(int i2, int i3, Map<K, V> map, Internal.EnumVerifier enumVerifier, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        MapEntryLite.Metadata<?, ?> metadataForMapMetadata = this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i2));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!enumVerifier.isInRange(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = (UB) unknownFieldSchema.n();
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
        return UnsafeUtil.q(t2, j2);
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
        int i5 = -1;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < this.buffer.length) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i6);
            int iNumberAt = numberAt(i6);
            int iType = type(iTypeAndOffsetAt);
            if (iType <= 17) {
                i2 = this.buffer[i6 + 2];
                int i9 = OFFSET_MASK & i2;
                int i10 = 1 << (i2 >>> 20);
                if (i9 != i5) {
                    i8 = unsafe.getInt(t2, i9);
                    i5 = i9;
                }
                i3 = i10;
            } else {
                i2 = (!this.useCachedSizeField || iType < FieldType.DOUBLE_LIST_PACKED.id() || iType > FieldType.SINT64_LIST_PACKED.id()) ? 0 : this.buffer[i6 + 2] & OFFSET_MASK;
                i3 = 0;
            }
            long jOffset = offset(iTypeAndOffsetAt);
            int i11 = i5;
            switch (iType) {
                case 0:
                    if ((i8 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i7 += iComputeDoubleSize;
                        break;
                    }
                case 1:
                    if ((i8 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i7 += iComputeDoubleSize;
                        break;
                    }
                case 2:
                    if ((i8 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeInt64Size(iNumberAt, unsafe.getLong(t2, jOffset));
                        i7 += iComputeDoubleSize;
                        break;
                    }
                case 3:
                    if ((i8 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeUInt64Size(iNumberAt, unsafe.getLong(t2, jOffset));
                        i7 += iComputeDoubleSize;
                        break;
                    }
                case 4:
                    if ((i8 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeInt32Size(iNumberAt, unsafe.getInt(t2, jOffset));
                        i7 += iComputeDoubleSize;
                        break;
                    }
                case 5:
                    if ((i8 & i3) == 0) {
                        break;
                    } else {
                        iComputeDoubleSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i7 += iComputeDoubleSize;
                        break;
                    }
                case 6:
                    if ((i8 & i3) != 0) {
                        iComputeDoubleSize = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i7 += iComputeDoubleSize;
                        break;
                    }
                    break;
                case 7:
                    if ((i8 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 8:
                    if ((i8 & i3) != 0) {
                        Object object = unsafe.getObject(t2, jOffset);
                        iComputeBoolSize = object instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) object) : CodedOutputStream.computeStringSize(iNumberAt, (String) object);
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 9:
                    if ((i8 & i3) != 0) {
                        iComputeBoolSize = SchemaUtil.o(iNumberAt, unsafe.getObject(t2, jOffset), getMessageFieldSchema(i6));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 10:
                    if ((i8 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) unsafe.getObject(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 11:
                    if ((i8 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeUInt32Size(iNumberAt, unsafe.getInt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 12:
                    if ((i8 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeEnumSize(iNumberAt, unsafe.getInt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 13:
                    if ((i8 & i3) != 0) {
                        iComputeSFixed32Size = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i7 += iComputeSFixed32Size;
                    }
                    break;
                case 14:
                    if ((i8 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 15:
                    if ((i8 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeSInt32Size(iNumberAt, unsafe.getInt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 16:
                    if ((i8 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.computeSInt64Size(iNumberAt, unsafe.getLong(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 17:
                    if ((i8 & i3) != 0) {
                        iComputeBoolSize = CodedOutputStream.b(iNumberAt, (MessageLite) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i6));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 18:
                    iComputeBoolSize = SchemaUtil.h(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iComputeBoolSize;
                    break;
                case 19:
                    z2 = false;
                    iF = SchemaUtil.f(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 20:
                    z2 = false;
                    iF = SchemaUtil.m(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 21:
                    z2 = false;
                    iF = SchemaUtil.x(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 22:
                    z2 = false;
                    iF = SchemaUtil.k(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 23:
                    z2 = false;
                    iF = SchemaUtil.h(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 24:
                    z2 = false;
                    iF = SchemaUtil.f(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 25:
                    z2 = false;
                    iF = SchemaUtil.a(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 26:
                    iComputeBoolSize = SchemaUtil.u(iNumberAt, (List) unsafe.getObject(t2, jOffset));
                    i7 += iComputeBoolSize;
                    break;
                case 27:
                    iComputeBoolSize = SchemaUtil.p(iNumberAt, (List) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i6));
                    i7 += iComputeBoolSize;
                    break;
                case 28:
                    iComputeBoolSize = SchemaUtil.c(iNumberAt, (List) unsafe.getObject(t2, jOffset));
                    i7 += iComputeBoolSize;
                    break;
                case 29:
                    iComputeBoolSize = SchemaUtil.v(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iComputeBoolSize;
                    break;
                case 30:
                    z2 = false;
                    iF = SchemaUtil.d(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 31:
                    z2 = false;
                    iF = SchemaUtil.f(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 32:
                    z2 = false;
                    iF = SchemaUtil.h(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 33:
                    z2 = false;
                    iF = SchemaUtil.q(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
                    break;
                case 34:
                    z2 = false;
                    iF = SchemaUtil.s(iNumberAt, (List) unsafe.getObject(t2, jOffset), false);
                    i7 += iF;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
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
                        i7 += iComputeSFixed32Size;
                    }
                    break;
                case 49:
                    iComputeBoolSize = SchemaUtil.j(iNumberAt, (List) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i6));
                    i7 += iComputeBoolSize;
                    break;
                case 50:
                    iComputeBoolSize = this.mapFieldSchema.getSerializedSize(iNumberAt, unsafe.getObject(t2, jOffset), getMapFieldDefaultEntry(i6));
                    i7 += iComputeBoolSize;
                    break;
                case 51:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeDoubleSize(iNumberAt, 0.0d);
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 52:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeFloatSize(iNumberAt, 0.0f);
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 53:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 54:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeUInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 55:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 56:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeFixed64Size(iNumberAt, 0L);
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 57:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeSFixed32Size = CodedOutputStream.computeFixed32Size(iNumberAt, 0);
                        i7 += iComputeSFixed32Size;
                    }
                    break;
                case 58:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeBoolSize(iNumberAt, true);
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 59:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        Object object2 = unsafe.getObject(t2, jOffset);
                        iComputeBoolSize = object2 instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) object2) : CodedOutputStream.computeStringSize(iNumberAt, (String) object2);
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 60:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = SchemaUtil.o(iNumberAt, unsafe.getObject(t2, jOffset), getMessageFieldSchema(i6));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 61:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) unsafe.getObject(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 62:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeUInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 63:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeEnumSize(iNumberAt, oneofIntAt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 64:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeSFixed32Size = CodedOutputStream.computeSFixed32Size(iNumberAt, 0);
                        i7 += iComputeSFixed32Size;
                    }
                    break;
                case 65:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeSFixed64Size(iNumberAt, 0L);
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 66:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeSInt32Size(iNumberAt, oneofIntAt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 67:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.computeSInt64Size(iNumberAt, oneofLongAt(t2, jOffset));
                        i7 += iComputeBoolSize;
                    }
                    break;
                case 68:
                    if (isOneofPresent(t2, iNumberAt, i6)) {
                        iComputeBoolSize = CodedOutputStream.b(iNumberAt, (MessageLite) unsafe.getObject(t2, jOffset), getMessageFieldSchema(i6));
                        i7 += iComputeBoolSize;
                    }
                    break;
            }
            i6 += 3;
            i5 = i11;
        }
        int unknownFieldsSerializedSize = i7 + getUnknownFieldsSerializedSize(this.unknownFieldSchema, t2);
        return this.hasExtensions ? unknownFieldsSerializedSize + this.extensionSchema.c(t2).getSerializedSize() : unknownFieldsSerializedSize;
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
            int i5 = (iType < FieldType.DOUBLE_LIST_PACKED.id() || iType > FieldType.SINT64_LIST_PACKED.id()) ? 0 : this.buffer[i4 + 2] & OFFSET_MASK;
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
                        iComputeDoubleSize = CodedOutputStream.computeInt64Size(iNumberAt, UnsafeUtil.t(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt64Size(iNumberAt, UnsafeUtil.t(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeInt32Size(iNumberAt, UnsafeUtil.r(t2, jOffset));
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
                        Object objU = UnsafeUtil.u(t2, jOffset);
                        iComputeDoubleSize = objU instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) objU) : CodedOutputStream.computeStringSize(iNumberAt, (String) objU);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 9:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = SchemaUtil.o(iNumberAt, UnsafeUtil.u(t2, jOffset), getMessageFieldSchema(i4));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) UnsafeUtil.u(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeUInt32Size(iNumberAt, UnsafeUtil.r(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeEnumSize(iNumberAt, UnsafeUtil.r(t2, jOffset));
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
                        iComputeDoubleSize = CodedOutputStream.computeSInt32Size(iNumberAt, UnsafeUtil.r(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeSInt64Size(iNumberAt, UnsafeUtil.t(t2, jOffset));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (isFieldPresent(t2, i4)) {
                        iComputeDoubleSize = CodedOutputStream.b(iNumberAt, (MessageLite) UnsafeUtil.u(t2, jOffset), getMessageFieldSchema(i4));
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
                    iComputeDoubleSize = this.mapFieldSchema.getSerializedSize(iNumberAt, UnsafeUtil.u(t2, jOffset), getMapFieldDefaultEntry(i4));
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
                        Object objU2 = UnsafeUtil.u(t2, jOffset);
                        iComputeDoubleSize = objU2 instanceof ByteString ? CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) objU2) : CodedOutputStream.computeStringSize(iNumberAt, (String) objU2);
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = SchemaUtil.o(iNumberAt, UnsafeUtil.u(t2, jOffset), getMessageFieldSchema(i4));
                        i3 += iComputeDoubleSize;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        iComputeDoubleSize = CodedOutputStream.computeBytesSize(iNumberAt, (ByteString) UnsafeUtil.u(t2, jOffset));
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
                        iComputeDoubleSize = CodedOutputStream.b(iNumberAt, (MessageLite) UnsafeUtil.u(t2, jOffset), getMessageFieldSchema(i4));
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
        return UnsafeUtil.r(t2, j2);
    }

    private static boolean isEnforceUtf8(int i2) {
        return (i2 & 536870912) != 0;
    }

    private boolean isFieldPresent(T t2, int i2, int i3, int i4) {
        return this.proto3 ? isFieldPresent(t2, i2) : (i3 & i4) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N> boolean isListInitialized(Object obj, int i2, int i3) {
        List list = (List) UnsafeUtil.u(obj, offset(i2));
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
    /* JADX WARN: Type inference failed for: r5v8, types: [androidx.datastore.preferences.protobuf.Schema] */
    private boolean isMapInitialized(T t2, int i2, int i3) {
        Map<?, ?> mapForMapData = this.mapFieldSchema.forMapData(UnsafeUtil.u(t2, offset(i2)));
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

    private boolean isOneofCaseEqual(T t2, T t3, int i2) {
        long jPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i2) & OFFSET_MASK;
        return UnsafeUtil.r(t2, jPresenceMaskAndOffsetAt) == UnsafeUtil.r(t3, jPresenceMaskAndOffsetAt);
    }

    private boolean isOneofPresent(T t2, int i2, int i3) {
        return UnsafeUtil.r(t2, (long) (presenceMaskAndOffsetAt(i3) & OFFSET_MASK)) == i2;
    }

    private static boolean isRequired(int i2) {
        return (i2 & 268435456) != 0;
    }

    private static List<?> listAt(Object obj, long j2) {
        return (List) UnsafeUtil.u(obj, j2);
    }

    private static <T> long longAt(T t2, long j2) {
        return UnsafeUtil.t(t2, j2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x007b, code lost:
    
        r0 = r16.checkInitializedCount;
     */
    /* JADX WARN: Code restructure failed: missing block: B:363:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007f, code lost:
    
        if (r0 >= r16.repeatedFieldOffsetStart) goto L359;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0081, code lost:
    
        r13 = filterMapUnknownEnumValues(r19, r16.intArray[r0], r13, r17);
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x008c, code lost:
    
        if (r13 == null) goto L363;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x008e, code lost:
    
        r17.o(r19, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0091, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <UT, UB, ET extends androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(androidx.datastore.preferences.protobuf.UnknownFieldSchema<UT, UB> r17, androidx.datastore.preferences.protobuf.ExtensionSchema<ET> r18, T r19, androidx.datastore.preferences.protobuf.Reader r20, androidx.datastore.preferences.protobuf.ExtensionRegistryLite r21) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1720
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.mergeFromHelper(androidx.datastore.preferences.protobuf.UnknownFieldSchema, androidx.datastore.preferences.protobuf.ExtensionSchema, java.lang.Object, androidx.datastore.preferences.protobuf.Reader, androidx.datastore.preferences.protobuf.ExtensionRegistryLite):void");
    }

    private final <K, V> void mergeMap(Object obj, int i2, Object obj2, ExtensionRegistryLite extensionRegistryLite, Reader reader) throws IOException {
        long jOffset = offset(typeAndOffsetAt(i2));
        Object objU = UnsafeUtil.u(obj, jOffset);
        if (objU == null) {
            objU = this.mapFieldSchema.newMapField(obj2);
            UnsafeUtil.G(obj, jOffset, objU);
        } else if (this.mapFieldSchema.isImmutable(objU)) {
            Object objNewMapField = this.mapFieldSchema.newMapField(obj2);
            this.mapFieldSchema.mergeFrom(objNewMapField, objU);
            UnsafeUtil.G(obj, jOffset, objNewMapField);
            objU = objNewMapField;
        }
        reader.readMap(this.mapFieldSchema.forMutableMapData(objU), this.mapFieldSchema.forMapMetadata(obj2), extensionRegistryLite);
    }

    private void mergeMessage(T t2, T t3, int i2) {
        long jOffset = offset(typeAndOffsetAt(i2));
        if (isFieldPresent(t3, i2)) {
            Object objU = UnsafeUtil.u(t2, jOffset);
            Object objU2 = UnsafeUtil.u(t3, jOffset);
            if (objU != null && objU2 != null) {
                UnsafeUtil.G(t2, jOffset, Internal.d(objU, objU2));
                setFieldPresent(t2, i2);
            } else if (objU2 != null) {
                UnsafeUtil.G(t2, jOffset, objU2);
                setFieldPresent(t2, i2);
            }
        }
    }

    private void mergeOneofMessage(T t2, T t3, int i2) {
        int iTypeAndOffsetAt = typeAndOffsetAt(i2);
        int iNumberAt = numberAt(i2);
        long jOffset = offset(iTypeAndOffsetAt);
        if (isOneofPresent(t3, iNumberAt, i2)) {
            Object objU = UnsafeUtil.u(t2, jOffset);
            Object objU2 = UnsafeUtil.u(t3, jOffset);
            if (objU != null && objU2 != null) {
                UnsafeUtil.G(t2, jOffset, Internal.d(objU, objU2));
                setOneofPresent(t2, iNumberAt, i2);
            } else if (objU2 != null) {
                UnsafeUtil.G(t2, jOffset, objU2);
                setOneofPresent(t2, iNumberAt, i2);
            }
        }
    }

    private void mergeSingleField(T t2, T t3, int i2) {
        int iTypeAndOffsetAt = typeAndOffsetAt(i2);
        long jOffset = offset(iTypeAndOffsetAt);
        int iNumberAt = numberAt(i2);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.C(t2, jOffset, UnsafeUtil.p(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 1:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.D(t2, jOffset, UnsafeUtil.q(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 2:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.F(t2, jOffset, UnsafeUtil.t(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 3:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.F(t2, jOffset, UnsafeUtil.t(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 4:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.E(t2, jOffset, UnsafeUtil.r(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 5:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.F(t2, jOffset, UnsafeUtil.t(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 6:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.E(t2, jOffset, UnsafeUtil.r(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 7:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.z(t2, jOffset, UnsafeUtil.m(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 8:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.G(t2, jOffset, UnsafeUtil.u(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 9:
                mergeMessage(t2, t3, i2);
                break;
            case 10:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.G(t2, jOffset, UnsafeUtil.u(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 11:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.E(t2, jOffset, UnsafeUtil.r(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 12:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.E(t2, jOffset, UnsafeUtil.r(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 13:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.E(t2, jOffset, UnsafeUtil.r(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 14:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.F(t2, jOffset, UnsafeUtil.t(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 15:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.E(t2, jOffset, UnsafeUtil.r(t3, jOffset));
                    setFieldPresent(t2, i2);
                    break;
                }
                break;
            case 16:
                if (isFieldPresent(t3, i2)) {
                    UnsafeUtil.F(t2, jOffset, UnsafeUtil.t(t3, jOffset));
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
                SchemaUtil.C(this.mapFieldSchema, t2, t3, jOffset);
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
                    UnsafeUtil.G(t2, jOffset, UnsafeUtil.u(t3, jOffset));
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
                    UnsafeUtil.G(t2, jOffset, UnsafeUtil.u(t3, jOffset));
                    setOneofPresent(t2, iNumberAt, i2);
                    break;
                }
                break;
            case 68:
                mergeOneofMessage(t2, t3, i2);
                break;
        }
    }

    private int numberAt(int i2) {
        return this.buffer[i2];
    }

    private static long offset(int i2) {
        return i2 & OFFSET_MASK;
    }

    private static <T> boolean oneofBooleanAt(T t2, long j2) {
        return ((Boolean) UnsafeUtil.u(t2, j2)).booleanValue();
    }

    private static <T> double oneofDoubleAt(T t2, long j2) {
        return ((Double) UnsafeUtil.u(t2, j2)).doubleValue();
    }

    private static <T> float oneofFloatAt(T t2, long j2) {
        return ((Float) UnsafeUtil.u(t2, j2)).floatValue();
    }

    private static <T> int oneofIntAt(T t2, long j2) {
        return ((Integer) UnsafeUtil.u(t2, j2)).intValue();
    }

    private static <T> long oneofLongAt(T t2, long j2) {
        return ((Long) UnsafeUtil.u(t2, j2)).longValue();
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
        long j3 = this.buffer[i9 + 2] & OFFSET_MASK;
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
                        if ((i7 & 536870912) != 0 && !Utf8.isValidUtf8(bArr, I2, I2 + i14)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        unsafe.putObject(t2, j2, new String(bArr, I2, i14, Internal.f3944a));
                        I2 += i14;
                    }
                    unsafe.putInt(t2, j3, i5);
                    return I2;
                }
                break;
            case 60:
                if (i6 == 2) {
                    int iP = ArrayDecoders.p(getMessageFieldSchema(i9), bArr, i2, i3, registers);
                    Object object = unsafe.getInt(t2, j3) == i5 ? unsafe.getObject(t2, j2) : null;
                    if (object == null) {
                        unsafe.putObject(t2, j2, registers.object1);
                    } else {
                        unsafe.putObject(t2, j2, Internal.d(object, registers.object1));
                    }
                    unsafe.putInt(t2, j3, i5);
                    return iP;
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
                        a(t2).h(i4, Long.valueOf(i15));
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
                    int iN = ArrayDecoders.n(getMessageFieldSchema(i9), bArr, i2, i3, (i4 & (-8)) | 4, registers);
                    Object object2 = unsafe.getInt(t2, j3) == i5 ? unsafe.getObject(t2, j2) : null;
                    if (object2 == null) {
                        unsafe.putObject(t2, j2, registers.object1);
                    } else {
                        unsafe.putObject(t2, j2, Internal.d(object2, registers.object1));
                    }
                    unsafe.putInt(t2, j3, i5);
                    return iN;
                }
                break;
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x023e, code lost:
    
        if (r0 != r15) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01dd, code lost:
    
        if (r0 != r15) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01f3, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x021f, code lost:
    
        if (r0 != r15) goto L91;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:18:0x005d. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int parseProto3Message(T r28, byte[] r29, int r30, int r31, androidx.datastore.preferences.protobuf.ArrayDecoders.Registers r32) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 642
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.parseProto3Message(java.lang.Object, byte[], int, int, androidx.datastore.preferences.protobuf.ArrayDecoders$Registers):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
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
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) t2;
                UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
                if (unknownFieldSetLite == UnknownFieldSetLite.getDefaultInstance()) {
                    unknownFieldSetLite = null;
                }
                UnknownFieldSetLite unknownFieldSetLite2 = (UnknownFieldSetLite) SchemaUtil.A(i5, protobufListMutableCopyWithCapacity2, getEnumFieldVerifier(i7), unknownFieldSetLite, this.unknownFieldSchema);
                if (unknownFieldSetLite2 != null) {
                    generatedMessageLite.unknownFields = unknownFieldSetLite2;
                }
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
            UnsafeUtil.G(obj, offset(i2), reader.readStringRequireUtf8());
        } else if (this.lite) {
            UnsafeUtil.G(obj, offset(i2), reader.readString());
        } else {
            UnsafeUtil.G(obj, offset(i2), reader.readBytes());
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
        if (this.proto3) {
            return;
        }
        int iPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i2);
        long j2 = iPresenceMaskAndOffsetAt & OFFSET_MASK;
        UnsafeUtil.E(t2, j2, UnsafeUtil.r(t2, j2) | (1 << (iPresenceMaskAndOffsetAt >>> 20)));
    }

    private void setOneofPresent(T t2, int i2, int i3) {
        UnsafeUtil.E(t2, presenceMaskAndOffsetAt(i3) & OFFSET_MASK, i2);
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void storeFieldData(androidx.datastore.preferences.protobuf.FieldInfo r8, int[] r9, int r10, boolean r11, java.lang.Object[] r12) {
        /*
            androidx.datastore.preferences.protobuf.OneofInfo r0 = r8.getOneof()
            r1 = 0
            if (r0 == 0) goto L27
            androidx.datastore.preferences.protobuf.FieldType r11 = r8.getType()
            int r11 = r11.id()
            int r11 = r11 + 51
            java.lang.reflect.Field r2 = r0.getValueField()
            long r2 = androidx.datastore.preferences.protobuf.UnsafeUtil.y(r2)
            int r2 = (int) r2
            java.lang.reflect.Field r0 = r0.getCaseField()
            long r3 = androidx.datastore.preferences.protobuf.UnsafeUtil.y(r0)
            int r0 = (int) r3
        L23:
            r3 = r2
            r2 = r0
            r0 = r1
            goto L73
        L27:
            androidx.datastore.preferences.protobuf.FieldType r0 = r8.getType()
            java.lang.reflect.Field r2 = r8.getField()
            long r2 = androidx.datastore.preferences.protobuf.UnsafeUtil.y(r2)
            int r2 = (int) r2
            int r3 = r0.id()
            if (r11 != 0) goto L5d
            boolean r11 = r0.isList()
            if (r11 != 0) goto L5d
            boolean r11 = r0.isMap()
            if (r11 != 0) goto L5d
            java.lang.reflect.Field r11 = r8.getPresenceField()
            long r4 = androidx.datastore.preferences.protobuf.UnsafeUtil.y(r11)
            int r0 = (int) r4
            int r11 = r8.getPresenceMask()
            int r11 = java.lang.Integer.numberOfTrailingZeros(r11)
            r7 = r0
            r0 = r11
            r11 = r3
            r3 = r2
            r2 = r7
            goto L73
        L5d:
            java.lang.reflect.Field r11 = r8.getCachedSizeField()
            if (r11 != 0) goto L68
            r0 = r1
            r11 = r3
            r3 = r2
            r2 = r0
            goto L73
        L68:
            java.lang.reflect.Field r11 = r8.getCachedSizeField()
            long r4 = androidx.datastore.preferences.protobuf.UnsafeUtil.y(r11)
            int r0 = (int) r4
            r11 = r3
            goto L23
        L73:
            int r4 = r8.getFieldNumber()
            r9[r10] = r4
            int r4 = r10 + 1
            boolean r5 = r8.isEnforceUtf8()
            if (r5 == 0) goto L84
            r5 = 536870912(0x20000000, float:1.0842022E-19)
            goto L85
        L84:
            r5 = r1
        L85:
            boolean r6 = r8.isRequired()
            if (r6 == 0) goto L8d
            r1 = 268435456(0x10000000, float:2.524355E-29)
        L8d:
            r1 = r1 | r5
            int r11 = r11 << 20
            r11 = r11 | r1
            r11 = r11 | r3
            r9[r4] = r11
            int r11 = r10 + 2
            int r0 = r0 << 20
            r0 = r0 | r2
            r9[r11] = r0
            java.lang.Class r9 = r8.getMessageFieldClass()
            java.lang.Object r11 = r8.getMapDefaultEntry()
            if (r11 == 0) goto Lc5
            int r10 = r10 / 3
            int r10 = r10 * 2
            java.lang.Object r11 = r8.getMapDefaultEntry()
            r12[r10] = r11
            if (r9 == 0) goto Lb6
            int r10 = r10 + 1
            r12[r10] = r9
            goto Le2
        Lb6:
            androidx.datastore.preferences.protobuf.Internal$EnumVerifier r9 = r8.getEnumVerifier()
            if (r9 == 0) goto Le2
            int r10 = r10 + 1
            androidx.datastore.preferences.protobuf.Internal$EnumVerifier r8 = r8.getEnumVerifier()
            r12[r10] = r8
            goto Le2
        Lc5:
            if (r9 == 0) goto Ld0
            int r10 = r10 / 3
            int r10 = r10 * 2
            int r10 = r10 + 1
            r12[r10] = r9
            goto Le2
        Ld0:
            androidx.datastore.preferences.protobuf.Internal$EnumVerifier r9 = r8.getEnumVerifier()
            if (r9 == 0) goto Le2
            int r10 = r10 / 3
            int r10 = r10 * 2
            int r10 = r10 + 1
            androidx.datastore.preferences.protobuf.Internal$EnumVerifier r8 = r8.getEnumVerifier()
            r12[r10] = r8
        Le2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.storeFieldData(androidx.datastore.preferences.protobuf.FieldInfo, int[], int, boolean, java.lang.Object[]):void");
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
    private void writeFieldsInAscendingOrderProto2(T r18, androidx.datastore.preferences.protobuf.Writer r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1352
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.writeFieldsInAscendingOrderProto2(java.lang.Object, androidx.datastore.preferences.protobuf.Writer):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInAscendingOrderProto3(T r13, androidx.datastore.preferences.protobuf.Writer r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.writeFieldsInAscendingOrderProto3(java.lang.Object, androidx.datastore.preferences.protobuf.Writer):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInDescendingOrder(T r11, androidx.datastore.preferences.protobuf.Writer r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.writeFieldsInDescendingOrder(java.lang.Object, androidx.datastore.preferences.protobuf.Writer):void");
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

    /* JADX WARN: Code restructure failed: missing block: B:119:0x0351, code lost:
    
        if (r0 != r11) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0353, code lost:
    
        r15 = r29;
        r14 = r30;
        r12 = r31;
        r13 = r33;
        r11 = r34;
        r9 = r35;
        r1 = r17;
        r7 = r19;
        r2 = r20;
        r6 = r22;
        r3 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x036d, code lost:
    
        r2 = r0;
        r8 = r25;
        r0 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x03a1, code lost:
    
        if (r0 != r15) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x03c4, code lost:
    
        if (r0 != r15) goto L120;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x008d. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int e(java.lang.Object r30, byte[] r31, int r32, int r33, int r34, androidx.datastore.preferences.protobuf.ArrayDecoders.Registers r35) {
        /*
            Method dump skipped, instructions count: 1168
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.e(java.lang.Object, byte[], int, int, int, androidx.datastore.preferences.protobuf.ArrayDecoders$Registers):int");
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
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
            return this.extensionSchema.c(t2).equals(this.extensionSchema.c(t3));
        }
        return true;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public int getSerializedSize(T t2) {
        return this.proto3 ? getSerializedSizeProto3(t2) : getSerializedSizeProto2(t2);
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
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
                    iHashLong = Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.p(t2, jOffset)));
                    i3 = i2 + iHashLong;
                    break;
                case 1:
                    i2 = i3 * 53;
                    iHashLong = Float.floatToIntBits(UnsafeUtil.q(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 2:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.t(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 3:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.t(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 4:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.r(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 5:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.t(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 6:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.r(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 7:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashBoolean(UnsafeUtil.m(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 8:
                    i2 = i3 * 53;
                    iHashLong = ((String) UnsafeUtil.u(t2, jOffset)).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 9:
                    Object objU = UnsafeUtil.u(t2, jOffset);
                    if (objU != null) {
                        iHashCode = objU.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 10:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.u(t2, jOffset).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 11:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.r(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 12:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.r(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 13:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.r(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 14:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.t(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 15:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.r(t2, jOffset);
                    i3 = i2 + iHashLong;
                    break;
                case 16:
                    i2 = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.t(t2, jOffset));
                    i3 = i2 + iHashLong;
                    break;
                case 17:
                    Object objU2 = UnsafeUtil.u(t2, jOffset);
                    if (objU2 != null) {
                        iHashCode = objU2.hashCode();
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
                    iHashLong = UnsafeUtil.u(t2, jOffset).hashCode();
                    i3 = i2 + iHashLong;
                    break;
                case 50:
                    i2 = i3 * 53;
                    iHashLong = UnsafeUtil.u(t2, jOffset).hashCode();
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
                        iHashLong = ((String) UnsafeUtil.u(t2, jOffset)).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = UnsafeUtil.u(t2, jOffset).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(t2, iNumberAt, i4)) {
                        i2 = i3 * 53;
                        iHashLong = UnsafeUtil.u(t2, jOffset).hashCode();
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
                        iHashLong = UnsafeUtil.u(t2, jOffset).hashCode();
                        i3 = i2 + iHashLong;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i3 * 53) + this.unknownFieldSchema.g(t2).hashCode();
        return this.hasExtensions ? (iHashCode2 * 53) + this.extensionSchema.c(t2).hashCode() : iHashCode2;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0078  */
    @Override // androidx.datastore.preferences.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isInitialized(T r13) {
        /*
            r12 = this;
            r0 = -1
            r1 = 0
            r2 = r1
            r3 = r2
        L4:
            int r4 = r12.checkInitializedCount
            r5 = 1
            if (r2 >= r4) goto L94
            int[] r4 = r12.intArray
            r4 = r4[r2]
            int r6 = r12.numberAt(r4)
            int r7 = r12.typeAndOffsetAt(r4)
            boolean r8 = r12.proto3
            if (r8 != 0) goto L31
            int[] r8 = r12.buffer
            int r9 = r4 + 2
            r8 = r8[r9]
            r9 = 1048575(0xfffff, float:1.469367E-39)
            r9 = r9 & r8
            int r8 = r8 >>> 20
            int r5 = r5 << r8
            if (r9 == r0) goto L32
            sun.misc.Unsafe r0 = androidx.datastore.preferences.protobuf.MessageSchema.UNSAFE
            long r10 = (long) r9
            int r3 = r0.getInt(r13, r10)
            r0 = r9
            goto L32
        L31:
            r5 = r1
        L32:
            boolean r8 = isRequired(r7)
            if (r8 == 0) goto L3f
            boolean r8 = r12.isFieldPresent(r13, r4, r3, r5)
            if (r8 != 0) goto L3f
            return r1
        L3f:
            int r8 = type(r7)
            r9 = 9
            if (r8 == r9) goto L7f
            r9 = 17
            if (r8 == r9) goto L7f
            r5 = 27
            if (r8 == r5) goto L78
            r5 = 60
            if (r8 == r5) goto L67
            r5 = 68
            if (r8 == r5) goto L67
            r5 = 49
            if (r8 == r5) goto L78
            r5 = 50
            if (r8 == r5) goto L60
            goto L90
        L60:
            boolean r4 = r12.isMapInitialized(r13, r7, r4)
            if (r4 != 0) goto L90
            return r1
        L67:
            boolean r5 = r12.isOneofPresent(r13, r6, r4)
            if (r5 == 0) goto L90
            androidx.datastore.preferences.protobuf.Schema r4 = r12.getMessageFieldSchema(r4)
            boolean r4 = isInitialized(r13, r7, r4)
            if (r4 != 0) goto L90
            return r1
        L78:
            boolean r4 = r12.isListInitialized(r13, r7, r4)
            if (r4 != 0) goto L90
            return r1
        L7f:
            boolean r5 = r12.isFieldPresent(r13, r4, r3, r5)
            if (r5 == 0) goto L90
            androidx.datastore.preferences.protobuf.Schema r4 = r12.getMessageFieldSchema(r4)
            boolean r4 = isInitialized(r13, r7, r4)
            if (r4 != 0) goto L90
            return r1
        L90:
            int r2 = r2 + 1
            goto L4
        L94:
            boolean r0 = r12.hasExtensions
            if (r0 == 0) goto La5
            androidx.datastore.preferences.protobuf.ExtensionSchema<?> r0 = r12.extensionSchema
            androidx.datastore.preferences.protobuf.FieldSet r13 = r0.c(r13)
            boolean r13 = r13.isInitialized()
            if (r13 != 0) goto La5
            return r1
        La5:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.isInitialized(java.lang.Object):boolean");
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public void makeImmutable(T t2) {
        int i2;
        int i3 = this.checkInitializedCount;
        while (true) {
            i2 = this.repeatedFieldOffsetStart;
            if (i3 >= i2) {
                break;
            }
            long jOffset = offset(typeAndOffsetAt(this.intArray[i3]));
            Object objU = UnsafeUtil.u(t2, jOffset);
            if (objU != null) {
                UnsafeUtil.G(t2, jOffset, this.mapFieldSchema.toImmutable(objU));
            }
            i3++;
        }
        int length = this.intArray.length;
        while (i2 < length) {
            this.listFieldSchema.c(t2, this.intArray[i2]);
            i2++;
        }
        this.unknownFieldSchema.j(t2);
        if (this.hasExtensions) {
            this.extensionSchema.f(t2);
        }
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public void mergeFrom(T t2, T t3) {
        t3.getClass();
        for (int i2 = 0; i2 < this.buffer.length; i2 += 3) {
            mergeSingleField(t2, t3, i2);
        }
        if (this.proto3) {
            return;
        }
        SchemaUtil.D(this.unknownFieldSchema, t2, t3);
        if (this.hasExtensions) {
            SchemaUtil.B(this.extensionSchema, t2, t3);
        }
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public T newInstance() {
        return (T) this.newInstanceSchema.newInstance(this.defaultInstance);
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
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
        if (this.proto3) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i2);
            long jOffset = offset(iTypeAndOffsetAt);
            switch (type(iTypeAndOffsetAt)) {
                case 0:
                    return UnsafeUtil.p(t2, jOffset) != 0.0d;
                case 1:
                    return UnsafeUtil.q(t2, jOffset) != 0.0f;
                case 2:
                    return UnsafeUtil.t(t2, jOffset) != 0;
                case 3:
                    return UnsafeUtil.t(t2, jOffset) != 0;
                case 4:
                    return UnsafeUtil.r(t2, jOffset) != 0;
                case 5:
                    return UnsafeUtil.t(t2, jOffset) != 0;
                case 6:
                    return UnsafeUtil.r(t2, jOffset) != 0;
                case 7:
                    return UnsafeUtil.m(t2, jOffset);
                case 8:
                    Object objU = UnsafeUtil.u(t2, jOffset);
                    if (objU instanceof String) {
                        return !((String) objU).isEmpty();
                    }
                    if (objU instanceof ByteString) {
                        return !ByteString.EMPTY.equals(objU);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return UnsafeUtil.u(t2, jOffset) != null;
                case 10:
                    return !ByteString.EMPTY.equals(UnsafeUtil.u(t2, jOffset));
                case 11:
                    return UnsafeUtil.r(t2, jOffset) != 0;
                case 12:
                    return UnsafeUtil.r(t2, jOffset) != 0;
                case 13:
                    return UnsafeUtil.r(t2, jOffset) != 0;
                case 14:
                    return UnsafeUtil.t(t2, jOffset) != 0;
                case 15:
                    return UnsafeUtil.r(t2, jOffset) != 0;
                case 16:
                    return UnsafeUtil.t(t2, jOffset) != 0;
                case 17:
                    return UnsafeUtil.u(t2, jOffset) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        int iPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i2);
        return (UnsafeUtil.r(t2, (long) (iPresenceMaskAndOffsetAt & OFFSET_MASK)) & (1 << (iPresenceMaskAndOffsetAt >>> 20))) != 0;
    }

    private int positionForFieldNumber(int i2, int i3) {
        if (i2 < this.minFieldNumber || i2 > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(i2, i3);
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public void mergeFrom(T t2, Reader reader, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        extensionRegistryLite.getClass();
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, t2, reader, extensionRegistryLite);
    }

    private boolean equals(T t2, T t3, int i2) {
        int iTypeAndOffsetAt = typeAndOffsetAt(i2);
        long jOffset = offset(iTypeAndOffsetAt);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                if (arePresentForEquals(t2, t3, i2) && Double.doubleToLongBits(UnsafeUtil.p(t2, jOffset)) == Double.doubleToLongBits(UnsafeUtil.p(t3, jOffset))) {
                    break;
                }
                break;
            case 1:
                if (arePresentForEquals(t2, t3, i2) && Float.floatToIntBits(UnsafeUtil.q(t2, jOffset)) == Float.floatToIntBits(UnsafeUtil.q(t3, jOffset))) {
                    break;
                }
                break;
            case 2:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.t(t2, jOffset) == UnsafeUtil.t(t3, jOffset)) {
                    break;
                }
                break;
            case 3:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.t(t2, jOffset) == UnsafeUtil.t(t3, jOffset)) {
                    break;
                }
                break;
            case 4:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.r(t2, jOffset) == UnsafeUtil.r(t3, jOffset)) {
                    break;
                }
                break;
            case 5:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.t(t2, jOffset) == UnsafeUtil.t(t3, jOffset)) {
                    break;
                }
                break;
            case 6:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.r(t2, jOffset) == UnsafeUtil.r(t3, jOffset)) {
                    break;
                }
                break;
            case 7:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.m(t2, jOffset) == UnsafeUtil.m(t3, jOffset)) {
                    break;
                }
                break;
            case 8:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.E(UnsafeUtil.u(t2, jOffset), UnsafeUtil.u(t3, jOffset))) {
                    break;
                }
                break;
            case 9:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.E(UnsafeUtil.u(t2, jOffset), UnsafeUtil.u(t3, jOffset))) {
                    break;
                }
                break;
            case 10:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.E(UnsafeUtil.u(t2, jOffset), UnsafeUtil.u(t3, jOffset))) {
                    break;
                }
                break;
            case 11:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.r(t2, jOffset) == UnsafeUtil.r(t3, jOffset)) {
                    break;
                }
                break;
            case 12:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.r(t2, jOffset) == UnsafeUtil.r(t3, jOffset)) {
                    break;
                }
                break;
            case 13:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.r(t2, jOffset) == UnsafeUtil.r(t3, jOffset)) {
                    break;
                }
                break;
            case 14:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.t(t2, jOffset) == UnsafeUtil.t(t3, jOffset)) {
                    break;
                }
                break;
            case 15:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.r(t2, jOffset) == UnsafeUtil.r(t3, jOffset)) {
                    break;
                }
                break;
            case 16:
                if (arePresentForEquals(t2, t3, i2) && UnsafeUtil.t(t2, jOffset) == UnsafeUtil.t(t3, jOffset)) {
                    break;
                }
                break;
            case 17:
                if (arePresentForEquals(t2, t3, i2) && SchemaUtil.E(UnsafeUtil.u(t2, jOffset), UnsafeUtil.u(t3, jOffset))) {
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
                if (isOneofCaseEqual(t2, t3, i2) && SchemaUtil.E(UnsafeUtil.u(t2, jOffset), UnsafeUtil.u(t3, jOffset))) {
                    break;
                }
                break;
        }
        return true;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public void mergeFrom(T t2, byte[] bArr, int i2, int i3, ArrayDecoders.Registers registers) throws IOException {
        if (this.proto3) {
            parseProto3Message(t2, bArr, i2, i3, registers);
        } else {
            e(t2, bArr, i2, i3, 0, registers);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isInitialized(Object obj, int i2, Schema schema) {
        return schema.isInitialized(UnsafeUtil.u(obj, offset(i2)));
    }
}
