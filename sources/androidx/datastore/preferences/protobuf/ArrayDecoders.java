package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.WireFormat;
import com.google.common.base.Ascii;
import java.io.IOException;

/* loaded from: classes.dex */
final class ArrayDecoders {

    /* renamed from: androidx.datastore.preferences.protobuf.ArrayDecoders$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f3891a;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            f3891a = iArr;
            try {
                iArr[WireFormat.FieldType.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f3891a[WireFormat.FieldType.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f3891a[WireFormat.FieldType.INT64.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f3891a[WireFormat.FieldType.UINT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f3891a[WireFormat.FieldType.INT32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f3891a[WireFormat.FieldType.UINT32.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f3891a[WireFormat.FieldType.FIXED64.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f3891a[WireFormat.FieldType.SFIXED64.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f3891a[WireFormat.FieldType.FIXED32.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f3891a[WireFormat.FieldType.SFIXED32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f3891a[WireFormat.FieldType.BOOL.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f3891a[WireFormat.FieldType.SINT32.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f3891a[WireFormat.FieldType.SINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f3891a[WireFormat.FieldType.ENUM.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f3891a[WireFormat.FieldType.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f3891a[WireFormat.FieldType.STRING.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f3891a[WireFormat.FieldType.GROUP.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f3891a[WireFormat.FieldType.MESSAGE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    static final class Registers {
        public final ExtensionRegistryLite extensionRegistry;
        public int int1;
        public long long1;
        public Object object1;

        Registers(ExtensionRegistryLite extensionRegistryLite) {
            extensionRegistryLite.getClass();
            this.extensionRegistry = extensionRegistryLite;
        }
    }

    static int A(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int I = I(bArr, i3, registers);
        intArrayList.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        while (I < i4) {
            int I2 = I(bArr, I, registers);
            if (i2 != registers.int1) {
                break;
            }
            I = I(bArr, I2, registers);
            intArrayList.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        return I;
    }

    static int B(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int iL = L(bArr, i3, registers);
        longArrayList.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        while (iL < i4) {
            int I = I(bArr, iL, registers);
            if (i2 != registers.int1) {
                break;
            }
            iL = L(bArr, I, registers);
            longArrayList.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        return iL;
    }

    static int C(byte[] bArr, int i2, Registers registers) throws InvalidProtocolBufferException {
        int I = I(bArr, i2, registers);
        int i3 = registers.int1;
        if (i3 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i3 == 0) {
            registers.object1 = "";
            return I;
        }
        registers.object1 = new String(bArr, I, i3, Internal.f3944a);
        return I + i3;
    }

    static int D(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        int I = I(bArr, i3, registers);
        int i5 = registers.int1;
        if (i5 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i5 == 0) {
            protobufList.add("");
        } else {
            protobufList.add(new String(bArr, I, i5, Internal.f3944a));
            I += i5;
        }
        while (I < i4) {
            int I2 = I(bArr, I, registers);
            if (i2 != registers.int1) {
                break;
            }
            I = I(bArr, I2, registers);
            int i6 = registers.int1;
            if (i6 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (i6 == 0) {
                protobufList.add("");
            } else {
                protobufList.add(new String(bArr, I, i6, Internal.f3944a));
                I += i6;
            }
        }
        return I;
    }

    static int E(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        int I = I(bArr, i3, registers);
        int i5 = registers.int1;
        if (i5 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i5 == 0) {
            protobufList.add("");
        } else {
            int i6 = I + i5;
            if (!Utf8.isValidUtf8(bArr, I, i6)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            protobufList.add(new String(bArr, I, i5, Internal.f3944a));
            I = i6;
        }
        while (I < i4) {
            int I2 = I(bArr, I, registers);
            if (i2 != registers.int1) {
                break;
            }
            I = I(bArr, I2, registers);
            int i7 = registers.int1;
            if (i7 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (i7 == 0) {
                protobufList.add("");
            } else {
                int i8 = I + i7;
                if (!Utf8.isValidUtf8(bArr, I, i8)) {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
                protobufList.add(new String(bArr, I, i7, Internal.f3944a));
                I = i8;
            }
        }
        return I;
    }

    static int F(byte[] bArr, int i2, Registers registers) throws InvalidProtocolBufferException {
        int I = I(bArr, i2, registers);
        int i3 = registers.int1;
        if (i3 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i3 == 0) {
            registers.object1 = "";
            return I;
        }
        registers.object1 = Utf8.h(bArr, I, i3);
        return I + i3;
    }

    static int G(int i2, byte[] bArr, int i3, int i4, UnknownFieldSetLite unknownFieldSetLite, Registers registers) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(i2) == 0) {
            throw InvalidProtocolBufferException.invalidTag();
        }
        int tagWireType = WireFormat.getTagWireType(i2);
        if (tagWireType == 0) {
            int iL = L(bArr, i3, registers);
            unknownFieldSetLite.h(i2, Long.valueOf(registers.long1));
            return iL;
        }
        if (tagWireType == 1) {
            unknownFieldSetLite.h(i2, Long.valueOf(j(bArr, i3)));
            return i3 + 8;
        }
        if (tagWireType == 2) {
            int I = I(bArr, i3, registers);
            int i5 = registers.int1;
            if (i5 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (i5 > bArr.length - I) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (i5 == 0) {
                unknownFieldSetLite.h(i2, ByteString.EMPTY);
            } else {
                unknownFieldSetLite.h(i2, ByteString.copyFrom(bArr, I, i5));
            }
            return I + i5;
        }
        if (tagWireType != 3) {
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidTag();
            }
            unknownFieldSetLite.h(i2, Integer.valueOf(h(bArr, i3)));
            return i3 + 4;
        }
        UnknownFieldSetLite unknownFieldSetLiteF = UnknownFieldSetLite.f();
        int i6 = (i2 & (-8)) | 4;
        int i7 = 0;
        while (true) {
            if (i3 >= i4) {
                break;
            }
            int I2 = I(bArr, i3, registers);
            int i8 = registers.int1;
            if (i8 == i6) {
                i7 = i8;
                i3 = I2;
                break;
            }
            i7 = i8;
            i3 = G(i8, bArr, I2, i4, unknownFieldSetLiteF, registers);
        }
        if (i3 > i4 || i7 != i6) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        unknownFieldSetLite.h(i2, unknownFieldSetLiteF);
        return i3;
    }

    static int H(int i2, byte[] bArr, int i3, Registers registers) {
        int i4 = i2 & 127;
        int i5 = i3 + 1;
        byte b2 = bArr[i3];
        if (b2 >= 0) {
            registers.int1 = i4 | (b2 << 7);
            return i5;
        }
        int i6 = i4 | ((b2 & Byte.MAX_VALUE) << 7);
        int i7 = i3 + 2;
        byte b3 = bArr[i5];
        if (b3 >= 0) {
            registers.int1 = i6 | (b3 << 14);
            return i7;
        }
        int i8 = i6 | ((b3 & Byte.MAX_VALUE) << 14);
        int i9 = i3 + 3;
        byte b4 = bArr[i7];
        if (b4 >= 0) {
            registers.int1 = i8 | (b4 << Ascii.NAK);
            return i9;
        }
        int i10 = i8 | ((b4 & Byte.MAX_VALUE) << 21);
        int i11 = i3 + 4;
        byte b5 = bArr[i9];
        if (b5 >= 0) {
            registers.int1 = i10 | (b5 << Ascii.FS);
            return i11;
        }
        int i12 = i10 | ((b5 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i13 = i11 + 1;
            if (bArr[i11] >= 0) {
                registers.int1 = i12;
                return i13;
            }
            i11 = i13;
        }
    }

    static int I(byte[] bArr, int i2, Registers registers) {
        int i3 = i2 + 1;
        byte b2 = bArr[i2];
        if (b2 < 0) {
            return H(b2, bArr, i3, registers);
        }
        registers.int1 = b2;
        return i3;
    }

    static int J(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int I = I(bArr, i3, registers);
        intArrayList.addInt(registers.int1);
        while (I < i4) {
            int I2 = I(bArr, I, registers);
            if (i2 != registers.int1) {
                break;
            }
            I = I(bArr, I2, registers);
            intArrayList.addInt(registers.int1);
        }
        return I;
    }

    static int K(long j2, byte[] bArr, int i2, Registers registers) {
        int i3 = i2 + 1;
        byte b2 = bArr[i2];
        long j3 = (j2 & 127) | ((b2 & Byte.MAX_VALUE) << 7);
        int i4 = 7;
        while (b2 < 0) {
            int i5 = i3 + 1;
            byte b3 = bArr[i3];
            i4 += 7;
            j3 |= (b3 & Byte.MAX_VALUE) << i4;
            i3 = i5;
            b2 = b3;
        }
        registers.long1 = j3;
        return i3;
    }

    static int L(byte[] bArr, int i2, Registers registers) {
        int i3 = i2 + 1;
        long j2 = bArr[i2];
        if (j2 < 0) {
            return K(j2, bArr, i3, registers);
        }
        registers.long1 = j2;
        return i3;
    }

    static int M(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int iL = L(bArr, i3, registers);
        longArrayList.addLong(registers.long1);
        while (iL < i4) {
            int I = I(bArr, iL, registers);
            if (i2 != registers.int1) {
                break;
            }
            iL = L(bArr, I, registers);
            longArrayList.addLong(registers.long1);
        }
        return iL;
    }

    static int N(int i2, byte[] bArr, int i3, int i4, Registers registers) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(i2) == 0) {
            throw InvalidProtocolBufferException.invalidTag();
        }
        int tagWireType = WireFormat.getTagWireType(i2);
        if (tagWireType == 0) {
            return L(bArr, i3, registers);
        }
        if (tagWireType == 1) {
            return i3 + 8;
        }
        if (tagWireType == 2) {
            return I(bArr, i3, registers) + registers.int1;
        }
        if (tagWireType != 3) {
            if (tagWireType == 5) {
                return i3 + 4;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }
        int i5 = (i2 & (-8)) | 4;
        int i6 = 0;
        while (i3 < i4) {
            i3 = I(bArr, i3, registers);
            i6 = registers.int1;
            if (i6 == i5) {
                break;
            }
            i3 = N(i6, bArr, i3, i4, registers);
        }
        if (i3 > i4 || i6 != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        return i3;
    }

    static int a(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        BooleanArrayList booleanArrayList = (BooleanArrayList) protobufList;
        int iL = L(bArr, i3, registers);
        booleanArrayList.addBoolean(registers.long1 != 0);
        while (iL < i4) {
            int I = I(bArr, iL, registers);
            if (i2 != registers.int1) {
                break;
            }
            iL = L(bArr, I, registers);
            booleanArrayList.addBoolean(registers.long1 != 0);
        }
        return iL;
    }

    static int b(byte[] bArr, int i2, Registers registers) throws InvalidProtocolBufferException {
        int I = I(bArr, i2, registers);
        int i3 = registers.int1;
        if (i3 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i3 > bArr.length - I) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        if (i3 == 0) {
            registers.object1 = ByteString.EMPTY;
            return I;
        }
        registers.object1 = ByteString.copyFrom(bArr, I, i3);
        return I + i3;
    }

    static int c(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        int I = I(bArr, i3, registers);
        int i5 = registers.int1;
        if (i5 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i5 > bArr.length - I) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        if (i5 == 0) {
            protobufList.add(ByteString.EMPTY);
        } else {
            protobufList.add(ByteString.copyFrom(bArr, I, i5));
            I += i5;
        }
        while (I < i4) {
            int I2 = I(bArr, I, registers);
            if (i2 != registers.int1) {
                break;
            }
            I = I(bArr, I2, registers);
            int i6 = registers.int1;
            if (i6 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (i6 > bArr.length - I) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (i6 == 0) {
                protobufList.add(ByteString.EMPTY);
            } else {
                protobufList.add(ByteString.copyFrom(bArr, I, i6));
                I += i6;
            }
        }
        return I;
    }

    static double d(byte[] bArr, int i2) {
        return Double.longBitsToDouble(j(bArr, i2));
    }

    static int e(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        DoubleArrayList doubleArrayList = (DoubleArrayList) protobufList;
        doubleArrayList.addDouble(d(bArr, i3));
        int i5 = i3 + 8;
        while (i5 < i4) {
            int I = I(bArr, i5, registers);
            if (i2 != registers.int1) {
                break;
            }
            doubleArrayList.addDouble(d(bArr, I));
            i5 = I + 8;
        }
        return i5;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static int f(int i2, byte[] bArr, int i3, int i4, GeneratedMessageLite.ExtendableMessage extendableMessage, GeneratedMessageLite.GeneratedExtension generatedExtension, UnknownFieldSchema unknownFieldSchema, Registers registers) throws IOException {
        Object field;
        FieldSet<GeneratedMessageLite.ExtensionDescriptor> fieldSet = extendableMessage.extensions;
        int i5 = i2 >>> 3;
        if (generatedExtension.f3941d.isRepeated() && generatedExtension.f3941d.isPacked()) {
            switch (AnonymousClass1.f3891a[generatedExtension.getLiteType().ordinal()]) {
                case 1:
                    DoubleArrayList doubleArrayList = new DoubleArrayList();
                    int iS = s(bArr, i3, doubleArrayList, registers);
                    fieldSet.setField(generatedExtension.f3941d, doubleArrayList);
                    return iS;
                case 2:
                    FloatArrayList floatArrayList = new FloatArrayList();
                    int iV = v(bArr, i3, floatArrayList, registers);
                    fieldSet.setField(generatedExtension.f3941d, floatArrayList);
                    return iV;
                case 3:
                case 4:
                    LongArrayList longArrayList = new LongArrayList();
                    int iZ = z(bArr, i3, longArrayList, registers);
                    fieldSet.setField(generatedExtension.f3941d, longArrayList);
                    return iZ;
                case 5:
                case 6:
                    IntArrayList intArrayList = new IntArrayList();
                    int iY = y(bArr, i3, intArrayList, registers);
                    fieldSet.setField(generatedExtension.f3941d, intArrayList);
                    return iY;
                case 7:
                case 8:
                    LongArrayList longArrayList2 = new LongArrayList();
                    int iU = u(bArr, i3, longArrayList2, registers);
                    fieldSet.setField(generatedExtension.f3941d, longArrayList2);
                    return iU;
                case 9:
                case 10:
                    IntArrayList intArrayList2 = new IntArrayList();
                    int iT = t(bArr, i3, intArrayList2, registers);
                    fieldSet.setField(generatedExtension.f3941d, intArrayList2);
                    return iT;
                case 11:
                    BooleanArrayList booleanArrayList = new BooleanArrayList();
                    int iR = r(bArr, i3, booleanArrayList, registers);
                    fieldSet.setField(generatedExtension.f3941d, booleanArrayList);
                    return iR;
                case 12:
                    IntArrayList intArrayList3 = new IntArrayList();
                    int iW = w(bArr, i3, intArrayList3, registers);
                    fieldSet.setField(generatedExtension.f3941d, intArrayList3);
                    return iW;
                case 13:
                    LongArrayList longArrayList3 = new LongArrayList();
                    int iX = x(bArr, i3, longArrayList3, registers);
                    fieldSet.setField(generatedExtension.f3941d, longArrayList3);
                    return iX;
                case 14:
                    IntArrayList intArrayList4 = new IntArrayList();
                    int iY2 = y(bArr, i3, intArrayList4, registers);
                    UnknownFieldSetLite unknownFieldSetLite = extendableMessage.unknownFields;
                    UnknownFieldSetLite unknownFieldSetLite2 = (UnknownFieldSetLite) SchemaUtil.z(i5, intArrayList4, generatedExtension.f3941d.getEnumType(), unknownFieldSetLite != UnknownFieldSetLite.getDefaultInstance() ? unknownFieldSetLite : null, unknownFieldSchema);
                    if (unknownFieldSetLite2 != null) {
                        extendableMessage.unknownFields = unknownFieldSetLite2;
                    }
                    fieldSet.setField(generatedExtension.f3941d, intArrayList4);
                    return iY2;
                default:
                    throw new IllegalStateException("Type cannot be packed: " + generatedExtension.f3941d.getLiteType());
            }
        }
        if (generatedExtension.getLiteType() != WireFormat.FieldType.ENUM) {
            switch (AnonymousClass1.f3891a[generatedExtension.getLiteType().ordinal()]) {
                case 1:
                    objValueOf = Double.valueOf(d(bArr, i3));
                    i3 += 8;
                    break;
                case 2:
                    objValueOf = Float.valueOf(l(bArr, i3));
                    i3 += 4;
                    break;
                case 3:
                case 4:
                    i3 = L(bArr, i3, registers);
                    objValueOf = Long.valueOf(registers.long1);
                    break;
                case 5:
                case 6:
                    i3 = I(bArr, i3, registers);
                    objValueOf = Integer.valueOf(registers.int1);
                    break;
                case 7:
                case 8:
                    objValueOf = Long.valueOf(j(bArr, i3));
                    i3 += 8;
                    break;
                case 9:
                case 10:
                    objValueOf = Integer.valueOf(h(bArr, i3));
                    i3 += 4;
                    break;
                case 11:
                    i3 = L(bArr, i3, registers);
                    objValueOf = Boolean.valueOf(registers.long1 != 0);
                    break;
                case 12:
                    i3 = I(bArr, i3, registers);
                    objValueOf = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                    break;
                case 13:
                    i3 = L(bArr, i3, registers);
                    objValueOf = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                    break;
                case 14:
                    throw new IllegalStateException("Shouldn't reach here.");
                case 15:
                    i3 = b(bArr, i3, registers);
                    objValueOf = registers.object1;
                    break;
                case 16:
                    i3 = C(bArr, i3, registers);
                    objValueOf = registers.object1;
                    break;
                case 17:
                    i3 = n(Protobuf.getInstance().schemaFor((Class) generatedExtension.getMessageDefaultInstance().getClass()), bArr, i3, i4, (i5 << 3) | 4, registers);
                    objValueOf = registers.object1;
                    break;
                case 18:
                    i3 = p(Protobuf.getInstance().schemaFor((Class) generatedExtension.getMessageDefaultInstance().getClass()), bArr, i3, i4, registers);
                    objValueOf = registers.object1;
                    break;
            }
        } else {
            i3 = I(bArr, i3, registers);
            if (generatedExtension.f3941d.getEnumType().findValueByNumber(registers.int1) == null) {
                UnknownFieldSetLite unknownFieldSetLiteF = extendableMessage.unknownFields;
                if (unknownFieldSetLiteF == UnknownFieldSetLite.getDefaultInstance()) {
                    unknownFieldSetLiteF = UnknownFieldSetLite.f();
                    extendableMessage.unknownFields = unknownFieldSetLiteF;
                }
                SchemaUtil.F(i5, registers.int1, unknownFieldSetLiteF, unknownFieldSchema);
                return i3;
            }
            objValueOf = Integer.valueOf(registers.int1);
        }
        if (generatedExtension.isRepeated()) {
            fieldSet.addRepeatedField(generatedExtension.f3941d, objValueOf);
        } else {
            int i6 = AnonymousClass1.f3891a[generatedExtension.getLiteType().ordinal()];
            if ((i6 == 17 || i6 == 18) && (field = fieldSet.getField(generatedExtension.f3941d)) != null) {
                objValueOf = Internal.d(field, objValueOf);
            }
            fieldSet.setField(generatedExtension.f3941d, objValueOf);
        }
        return i3;
    }

    static int g(int i2, byte[] bArr, int i3, int i4, Object obj, MessageLite messageLite, UnknownFieldSchema unknownFieldSchema, Registers registers) {
        GeneratedMessageLite.GeneratedExtension generatedExtensionFindLiteExtensionByNumber = registers.extensionRegistry.findLiteExtensionByNumber(messageLite, i2 >>> 3);
        if (generatedExtensionFindLiteExtensionByNumber == null) {
            return G(i2, bArr, i3, i4, MessageSchema.a(obj), registers);
        }
        GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj;
        extendableMessage.M();
        return f(i2, bArr, i3, i4, extendableMessage, generatedExtensionFindLiteExtensionByNumber, unknownFieldSchema, registers);
    }

    static int h(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    static int i(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        intArrayList.addInt(h(bArr, i3));
        int i5 = i3 + 4;
        while (i5 < i4) {
            int I = I(bArr, i5, registers);
            if (i2 != registers.int1) {
                break;
            }
            intArrayList.addInt(h(bArr, I));
            i5 = I + 4;
        }
        return i5;
    }

    static long j(byte[] bArr, int i2) {
        return ((bArr[i2 + 7] & 255) << 56) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
    }

    static int k(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        longArrayList.addLong(j(bArr, i3));
        int i5 = i3 + 8;
        while (i5 < i4) {
            int I = I(bArr, i5, registers);
            if (i2 != registers.int1) {
                break;
            }
            longArrayList.addLong(j(bArr, I));
            i5 = I + 8;
        }
        return i5;
    }

    static float l(byte[] bArr, int i2) {
        return Float.intBitsToFloat(h(bArr, i2));
    }

    static int m(int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        FloatArrayList floatArrayList = (FloatArrayList) protobufList;
        floatArrayList.addFloat(l(bArr, i3));
        int i5 = i3 + 4;
        while (i5 < i4) {
            int I = I(bArr, i5, registers);
            if (i2 != registers.int1) {
                break;
            }
            floatArrayList.addFloat(l(bArr, I));
            i5 = I + 4;
        }
        return i5;
    }

    static int n(Schema schema, byte[] bArr, int i2, int i3, int i4, Registers registers) {
        MessageSchema messageSchema = (MessageSchema) schema;
        Object objNewInstance = messageSchema.newInstance();
        int iE = messageSchema.e(objNewInstance, bArr, i2, i3, i4, registers);
        messageSchema.makeImmutable(objNewInstance);
        registers.object1 = objNewInstance;
        return iE;
    }

    static int o(Schema schema, int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) {
        int i5 = (i2 & (-8)) | 4;
        int iN = n(schema, bArr, i3, i4, i5, registers);
        protobufList.add(registers.object1);
        while (iN < i4) {
            int I = I(bArr, iN, registers);
            if (i2 != registers.int1) {
                break;
            }
            iN = n(schema, bArr, I, i4, i5, registers);
            protobufList.add(registers.object1);
        }
        return iN;
    }

    static int p(Schema schema, byte[] bArr, int i2, int i3, Registers registers) throws IOException {
        int iH = i2 + 1;
        int i4 = bArr[i2];
        if (i4 < 0) {
            iH = H(i4, bArr, iH, registers);
            i4 = registers.int1;
        }
        int i5 = iH;
        if (i4 < 0 || i4 > i3 - i5) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        Object objNewInstance = schema.newInstance();
        int i6 = i4 + i5;
        schema.mergeFrom(objNewInstance, bArr, i5, i6, registers);
        schema.makeImmutable(objNewInstance);
        registers.object1 = objNewInstance;
        return i6;
    }

    static int q(Schema schema, int i2, byte[] bArr, int i3, int i4, Internal.ProtobufList protobufList, Registers registers) throws IOException {
        int iP = p(schema, bArr, i3, i4, registers);
        protobufList.add(registers.object1);
        while (iP < i4) {
            int I = I(bArr, iP, registers);
            if (i2 != registers.int1) {
                break;
            }
            iP = p(schema, bArr, I, i4, registers);
            protobufList.add(registers.object1);
        }
        return iP;
    }

    static int r(byte[] bArr, int i2, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        BooleanArrayList booleanArrayList = (BooleanArrayList) protobufList;
        int I = I(bArr, i2, registers);
        int i3 = registers.int1 + I;
        while (I < i3) {
            I = L(bArr, I, registers);
            booleanArrayList.addBoolean(registers.long1 != 0);
        }
        if (I == i3) {
            return I;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int s(byte[] bArr, int i2, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        DoubleArrayList doubleArrayList = (DoubleArrayList) protobufList;
        int I = I(bArr, i2, registers);
        int i3 = registers.int1 + I;
        while (I < i3) {
            doubleArrayList.addDouble(d(bArr, I));
            I += 8;
        }
        if (I == i3) {
            return I;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int t(byte[] bArr, int i2, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int I = I(bArr, i2, registers);
        int i3 = registers.int1 + I;
        while (I < i3) {
            intArrayList.addInt(h(bArr, I));
            I += 4;
        }
        if (I == i3) {
            return I;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int u(byte[] bArr, int i2, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int I = I(bArr, i2, registers);
        int i3 = registers.int1 + I;
        while (I < i3) {
            longArrayList.addLong(j(bArr, I));
            I += 8;
        }
        if (I == i3) {
            return I;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int v(byte[] bArr, int i2, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        FloatArrayList floatArrayList = (FloatArrayList) protobufList;
        int I = I(bArr, i2, registers);
        int i3 = registers.int1 + I;
        while (I < i3) {
            floatArrayList.addFloat(l(bArr, I));
            I += 4;
        }
        if (I == i3) {
            return I;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int w(byte[] bArr, int i2, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int I = I(bArr, i2, registers);
        int i3 = registers.int1 + I;
        while (I < i3) {
            I = I(bArr, I, registers);
            intArrayList.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        if (I == i3) {
            return I;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int x(byte[] bArr, int i2, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int I = I(bArr, i2, registers);
        int i3 = registers.int1 + I;
        while (I < i3) {
            I = L(bArr, I, registers);
            longArrayList.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        if (I == i3) {
            return I;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int y(byte[] bArr, int i2, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int I = I(bArr, i2, registers);
        int i3 = registers.int1 + I;
        while (I < i3) {
            I = I(bArr, I, registers);
            intArrayList.addInt(registers.int1);
        }
        if (I == i3) {
            return I;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int z(byte[] bArr, int i2, Internal.ProtobufList protobufList, Registers registers) throws InvalidProtocolBufferException {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int I = I(bArr, i2, registers);
        int i3 = registers.int1 + I;
        while (I < i3) {
            I = L(bArr, I, registers);
            longArrayList.addLong(registers.long1);
        }
        if (I == i3) {
            return I;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }
}
