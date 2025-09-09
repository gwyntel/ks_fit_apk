package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.InvalidProtocolBufferException;
import androidx.health.platform.client.proto.MapEntryLite;
import androidx.health.platform.client.proto.WireFormat;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

@CheckReturnValue
/* loaded from: classes.dex */
abstract class BinaryReader implements Reader {
    private static final int FIXED32_MULTIPLE_MASK = 3;
    private static final int FIXED64_MULTIPLE_MASK = 7;

    /* renamed from: androidx.health.platform.client.proto.BinaryReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4395a;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            f4395a = iArr;
            try {
                iArr[WireFormat.FieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4395a[WireFormat.FieldType.BYTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4395a[WireFormat.FieldType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4395a[WireFormat.FieldType.ENUM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4395a[WireFormat.FieldType.FIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4395a[WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4395a[WireFormat.FieldType.FLOAT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f4395a[WireFormat.FieldType.INT32.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f4395a[WireFormat.FieldType.INT64.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f4395a[WireFormat.FieldType.MESSAGE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f4395a[WireFormat.FieldType.SFIXED32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f4395a[WireFormat.FieldType.SFIXED64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f4395a[WireFormat.FieldType.SINT32.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f4395a[WireFormat.FieldType.SINT64.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f4395a[WireFormat.FieldType.STRING.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f4395a[WireFormat.FieldType.UINT32.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f4395a[WireFormat.FieldType.UINT64.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    private BinaryReader() {
    }

    public static BinaryReader newInstance(ByteBuffer byteBuffer, boolean z2) {
        if (byteBuffer.hasArray()) {
            return new SafeHeapReader(byteBuffer, z2);
        }
        throw new IllegalArgumentException("Direct buffers not yet supported");
    }

    public abstract int getTotalBytesRead();

    @Override // androidx.health.platform.client.proto.Reader
    public boolean shouldDiscardUnknownFields() {
        return false;
    }

    private static final class SafeHeapReader extends BinaryReader {
        private final byte[] buffer;
        private final boolean bufferIsImmutable;
        private int endGroupTag;
        private final int initialPos;
        private int limit;
        private int pos;
        private int tag;

        public SafeHeapReader(ByteBuffer byteBuffer, boolean z2) {
            super(null);
            this.bufferIsImmutable = z2;
            this.buffer = byteBuffer.array();
            int iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            this.pos = iArrayOffset;
            this.initialPos = iArrayOffset;
            this.limit = byteBuffer.arrayOffset() + byteBuffer.limit();
        }

        private boolean isAtEnd() {
            return this.pos == this.limit;
        }

        private byte readByte() throws IOException {
            int i2 = this.pos;
            if (i2 == this.limit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 1;
            return bArr[i2];
        }

        private Object readField(WireFormat.FieldType fieldType, Class<?> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            switch (AnonymousClass1.f4395a[fieldType.ordinal()]) {
                case 1:
                    return Boolean.valueOf(readBool());
                case 2:
                    return readBytes();
                case 3:
                    return Double.valueOf(readDouble());
                case 4:
                    return Integer.valueOf(readEnum());
                case 5:
                    return Integer.valueOf(readFixed32());
                case 6:
                    return Long.valueOf(readFixed64());
                case 7:
                    return Float.valueOf(readFloat());
                case 8:
                    return Integer.valueOf(readInt32());
                case 9:
                    return Long.valueOf(readInt64());
                case 10:
                    return readMessage(cls, extensionRegistryLite);
                case 11:
                    return Integer.valueOf(readSFixed32());
                case 12:
                    return Long.valueOf(readSFixed64());
                case 13:
                    return Integer.valueOf(readSInt32());
                case 14:
                    return Long.valueOf(readSInt64());
                case 15:
                    return readStringRequireUtf8();
                case 16:
                    return Integer.valueOf(readUInt32());
                case 17:
                    return Long.valueOf(readUInt64());
                default:
                    throw new RuntimeException("unsupported field type.");
            }
        }

        private int readLittleEndian32() throws IOException {
            requireBytes(4);
            return readLittleEndian32_NoCheck();
        }

        private int readLittleEndian32_NoCheck() {
            int i2 = this.pos;
            byte[] bArr = this.buffer;
            this.pos = i2 + 4;
            return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
        }

        private long readLittleEndian64() throws IOException {
            requireBytes(8);
            return readLittleEndian64_NoCheck();
        }

        private long readLittleEndian64_NoCheck() {
            int i2 = this.pos;
            byte[] bArr = this.buffer;
            this.pos = i2 + 8;
            return ((bArr[i2 + 7] & 255) << 56) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
        }

        private int readVarint32() throws IOException {
            int i2;
            int i3 = this.pos;
            int i4 = this.limit;
            if (i4 == i3) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            if (b2 >= 0) {
                this.pos = i5;
                return b2;
            }
            if (i4 - i5 < 9) {
                return (int) readVarint64SlowPath();
            }
            int i6 = i3 + 2;
            int i7 = (bArr[i5] << 7) ^ b2;
            if (i7 < 0) {
                i2 = i7 ^ (-128);
            } else {
                int i8 = i3 + 3;
                int i9 = (bArr[i6] << 14) ^ i7;
                if (i9 >= 0) {
                    i2 = i9 ^ 16256;
                } else {
                    int i10 = i3 + 4;
                    int i11 = i9 ^ (bArr[i8] << Ascii.NAK);
                    if (i11 < 0) {
                        i2 = (-2080896) ^ i11;
                    } else {
                        i8 = i3 + 5;
                        byte b3 = bArr[i10];
                        int i12 = (i11 ^ (b3 << Ascii.FS)) ^ 266354560;
                        if (b3 < 0) {
                            i10 = i3 + 6;
                            if (bArr[i8] < 0) {
                                i8 = i3 + 7;
                                if (bArr[i10] < 0) {
                                    i10 = i3 + 8;
                                    if (bArr[i8] < 0) {
                                        i8 = i3 + 9;
                                        if (bArr[i10] < 0) {
                                            int i13 = i3 + 10;
                                            if (bArr[i8] < 0) {
                                                throw InvalidProtocolBufferException.malformedVarint();
                                            }
                                            i6 = i13;
                                            i2 = i12;
                                        }
                                    }
                                }
                            }
                            i2 = i12;
                        }
                        i2 = i12;
                    }
                    i6 = i10;
                }
                i6 = i8;
            }
            this.pos = i6;
            return i2;
        }

        private long readVarint64SlowPath() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                j2 |= (r3 & Byte.MAX_VALUE) << i2;
                if ((readByte() & 128) == 0) {
                    return j2;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void requireBytes(int i2) throws IOException {
            if (i2 < 0 || i2 > this.limit - this.pos) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        private void requirePosition(int i2) throws IOException {
            if (this.pos != i2) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        private void requireWireType(int i2) throws IOException {
            if (WireFormat.getTagWireType(this.tag) != i2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }

        private void skipBytes(int i2) throws IOException {
            requireBytes(i2);
            this.pos += i2;
        }

        private void skipGroup() throws IOException {
            int i2 = this.endGroupTag;
            this.endGroupTag = WireFormat.a(WireFormat.getTagFieldNumber(this.tag), 4);
            while (getFieldNumber() != Integer.MAX_VALUE && skipField()) {
            }
            if (this.tag != this.endGroupTag) {
                throw InvalidProtocolBufferException.parseFailure();
            }
            this.endGroupTag = i2;
        }

        private void skipVarint() throws IOException {
            int i2 = this.limit;
            int i3 = this.pos;
            if (i2 - i3 >= 10) {
                byte[] bArr = this.buffer;
                int i4 = 0;
                while (i4 < 10) {
                    int i5 = i3 + 1;
                    if (bArr[i3] >= 0) {
                        this.pos = i5;
                        return;
                    } else {
                        i4++;
                        i3 = i5;
                    }
                }
            }
            skipVarintSlowPath();
        }

        private void skipVarintSlowPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                if (readByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void verifyPackedFixed32Length(int i2) throws IOException {
            requireBytes(i2);
            if ((i2 & 3) != 0) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        }

        private void verifyPackedFixed64Length(int i2) throws IOException {
            requireBytes(i2);
            if ((i2 & 7) != 0) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        }

        @Override // androidx.health.platform.client.proto.Reader
        public int getFieldNumber() throws IOException {
            if (isAtEnd()) {
                return Integer.MAX_VALUE;
            }
            int varint32 = readVarint32();
            this.tag = varint32;
            if (varint32 == this.endGroupTag) {
                return Integer.MAX_VALUE;
            }
            return WireFormat.getTagFieldNumber(varint32);
        }

        @Override // androidx.health.platform.client.proto.Reader
        public int getTag() {
            return this.tag;
        }

        @Override // androidx.health.platform.client.proto.BinaryReader
        public int getTotalBytesRead() {
            return this.pos - this.initialPos;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public <T> void mergeGroupField(T t2, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i2 = this.endGroupTag;
            this.endGroupTag = WireFormat.a(WireFormat.getTagFieldNumber(this.tag), 4);
            try {
                schema.mergeFrom(t2, this, extensionRegistryLite);
                if (this.tag == this.endGroupTag) {
                } else {
                    throw InvalidProtocolBufferException.parseFailure();
                }
            } finally {
                this.endGroupTag = i2;
            }
        }

        @Override // androidx.health.platform.client.proto.Reader
        public <T> void mergeMessageField(T t2, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int varint32 = readVarint32();
            requireBytes(varint32);
            int i2 = this.limit;
            int i3 = this.pos + varint32;
            this.limit = i3;
            try {
                schema.mergeFrom(t2, this, extensionRegistryLite);
                if (this.pos == i3) {
                } else {
                    throw InvalidProtocolBufferException.parseFailure();
                }
            } finally {
                this.limit = i2;
            }
        }

        @Override // androidx.health.platform.client.proto.Reader
        public boolean readBool() throws IOException {
            requireWireType(0);
            return readVarint32() != 0;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readBoolList(List<Boolean> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof BooleanArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType != 0) {
                    if (tagWireType != 2) {
                        throw InvalidProtocolBufferException.invalidWireType();
                    }
                    int varint32 = this.pos + readVarint32();
                    while (this.pos < varint32) {
                        list.add(Boolean.valueOf(readVarint32() != 0));
                    }
                    requirePosition(varint32);
                    return;
                }
                do {
                    list.add(Boolean.valueOf(readBool()));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            BooleanArrayList booleanArrayList = (BooleanArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 != 0) {
                if (tagWireType2 != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint322 = this.pos + readVarint32();
                while (this.pos < varint322) {
                    booleanArrayList.addBoolean(readVarint32() != 0);
                }
                requirePosition(varint322);
                return;
            }
            do {
                booleanArrayList.addBoolean(readBool());
                if (isAtEnd()) {
                    return;
                } else {
                    i3 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i3;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public ByteString readBytes() throws IOException {
            requireWireType(2);
            int varint32 = readVarint32();
            if (varint32 == 0) {
                return ByteString.EMPTY;
            }
            requireBytes(varint32);
            ByteString byteStringWrap = this.bufferIsImmutable ? ByteString.wrap(this.buffer, this.pos, varint32) : ByteString.copyFrom(this.buffer, this.pos, varint32);
            this.pos += varint32;
            return byteStringWrap;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readBytesList(List<ByteString> list) throws IOException {
            int i2;
            if (WireFormat.getTagWireType(this.tag) != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(readBytes());
                if (isAtEnd()) {
                    return;
                } else {
                    i2 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i2;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public double readDouble() throws IOException {
            requireWireType(1);
            return Double.longBitsToDouble(readLittleEndian64());
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readDoubleList(List<Double> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof DoubleArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 1) {
                    do {
                        list.add(Double.valueOf(readDouble()));
                        if (isAtEnd()) {
                            return;
                        } else {
                            i2 = this.pos;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = i2;
                    return;
                }
                if (tagWireType != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint32 = readVarint32();
                verifyPackedFixed64Length(varint32);
                int i4 = this.pos + varint32;
                while (this.pos < i4) {
                    list.add(Double.valueOf(Double.longBitsToDouble(readLittleEndian64_NoCheck())));
                }
                return;
            }
            DoubleArrayList doubleArrayList = (DoubleArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 == 1) {
                do {
                    doubleArrayList.addDouble(readDouble());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i3 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i3;
                return;
            }
            if (tagWireType2 != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int varint322 = readVarint32();
            verifyPackedFixed64Length(varint322);
            int i5 = this.pos + varint322;
            while (this.pos < i5) {
                doubleArrayList.addDouble(Double.longBitsToDouble(readLittleEndian64_NoCheck()));
            }
        }

        @Override // androidx.health.platform.client.proto.Reader
        public int readEnum() throws IOException {
            requireWireType(0);
            return readVarint32();
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readEnumList(List<Integer> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof IntArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType != 0) {
                    if (tagWireType != 2) {
                        throw InvalidProtocolBufferException.invalidWireType();
                    }
                    int varint32 = this.pos + readVarint32();
                    while (this.pos < varint32) {
                        list.add(Integer.valueOf(readVarint32()));
                    }
                    return;
                }
                do {
                    list.add(Integer.valueOf(readEnum()));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 != 0) {
                if (tagWireType2 != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint322 = this.pos + readVarint32();
                while (this.pos < varint322) {
                    intArrayList.addInt(readVarint32());
                }
                return;
            }
            do {
                intArrayList.addInt(readEnum());
                if (isAtEnd()) {
                    return;
                } else {
                    i3 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i3;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public int readFixed32() throws IOException {
            requireWireType(5);
            return readLittleEndian32();
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readFixed32List(List<Integer> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof IntArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 2) {
                    int varint32 = readVarint32();
                    verifyPackedFixed32Length(varint32);
                    int i4 = this.pos + varint32;
                    while (this.pos < i4) {
                        list.add(Integer.valueOf(readLittleEndian32_NoCheck()));
                    }
                    return;
                }
                if (tagWireType != 5) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                do {
                    list.add(Integer.valueOf(readFixed32()));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 == 2) {
                int varint322 = readVarint32();
                verifyPackedFixed32Length(varint322);
                int i5 = this.pos + varint322;
                while (this.pos < i5) {
                    intArrayList.addInt(readLittleEndian32_NoCheck());
                }
                return;
            }
            if (tagWireType2 != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                intArrayList.addInt(readFixed32());
                if (isAtEnd()) {
                    return;
                } else {
                    i3 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i3;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public long readFixed64() throws IOException {
            requireWireType(1);
            return readLittleEndian64();
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readFixed64List(List<Long> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof LongArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 1) {
                    do {
                        list.add(Long.valueOf(readFixed64()));
                        if (isAtEnd()) {
                            return;
                        } else {
                            i2 = this.pos;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = i2;
                    return;
                }
                if (tagWireType != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint32 = readVarint32();
                verifyPackedFixed64Length(varint32);
                int i4 = this.pos + varint32;
                while (this.pos < i4) {
                    list.add(Long.valueOf(readLittleEndian64_NoCheck()));
                }
                return;
            }
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 == 1) {
                do {
                    longArrayList.addLong(readFixed64());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i3 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i3;
                return;
            }
            if (tagWireType2 != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int varint322 = readVarint32();
            verifyPackedFixed64Length(varint322);
            int i5 = this.pos + varint322;
            while (this.pos < i5) {
                longArrayList.addLong(readLittleEndian64_NoCheck());
            }
        }

        @Override // androidx.health.platform.client.proto.Reader
        public float readFloat() throws IOException {
            requireWireType(5);
            return Float.intBitsToFloat(readLittleEndian32());
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readFloatList(List<Float> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof FloatArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 2) {
                    int varint32 = readVarint32();
                    verifyPackedFixed32Length(varint32);
                    int i4 = this.pos + varint32;
                    while (this.pos < i4) {
                        list.add(Float.valueOf(Float.intBitsToFloat(readLittleEndian32_NoCheck())));
                    }
                    return;
                }
                if (tagWireType != 5) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                do {
                    list.add(Float.valueOf(readFloat()));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            FloatArrayList floatArrayList = (FloatArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 == 2) {
                int varint322 = readVarint32();
                verifyPackedFixed32Length(varint322);
                int i5 = this.pos + varint322;
                while (this.pos < i5) {
                    floatArrayList.addFloat(Float.intBitsToFloat(readLittleEndian32_NoCheck()));
                }
                return;
            }
            if (tagWireType2 != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                floatArrayList.addFloat(readFloat());
                if (isAtEnd()) {
                    return;
                } else {
                    i3 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i3;
        }

        @Override // androidx.health.platform.client.proto.Reader
        @Deprecated
        public <T> T readGroup(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            requireWireType(3);
            return (T) readGroup(Protobuf.getInstance().schemaFor((Class) cls), extensionRegistryLite);
        }

        @Override // androidx.health.platform.client.proto.Reader
        @Deprecated
        public <T> T readGroupBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            requireWireType(3);
            return (T) readGroup(schema, extensionRegistryLite);
        }

        @Override // androidx.health.platform.client.proto.Reader
        @Deprecated
        public <T> void readGroupList(List<T> list, Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            readGroupList(list, Protobuf.getInstance().schemaFor((Class) cls), extensionRegistryLite);
        }

        @Override // androidx.health.platform.client.proto.Reader
        public int readInt32() throws IOException {
            requireWireType(0);
            return readVarint32();
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readInt32List(List<Integer> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof IntArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        list.add(Integer.valueOf(readInt32()));
                        if (isAtEnd()) {
                            return;
                        } else {
                            i2 = this.pos;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = i2;
                    return;
                }
                if (tagWireType != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint32 = this.pos + readVarint32();
                while (this.pos < varint32) {
                    list.add(Integer.valueOf(readVarint32()));
                }
                requirePosition(varint32);
                return;
            }
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 == 0) {
                do {
                    intArrayList.addInt(readInt32());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i3 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i3;
                return;
            }
            if (tagWireType2 != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int varint322 = this.pos + readVarint32();
            while (this.pos < varint322) {
                intArrayList.addInt(readVarint32());
            }
            requirePosition(varint322);
        }

        @Override // androidx.health.platform.client.proto.Reader
        public long readInt64() throws IOException {
            requireWireType(0);
            return readVarint64();
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readInt64List(List<Long> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof LongArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        list.add(Long.valueOf(readInt64()));
                        if (isAtEnd()) {
                            return;
                        } else {
                            i2 = this.pos;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = i2;
                    return;
                }
                if (tagWireType != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint32 = this.pos + readVarint32();
                while (this.pos < varint32) {
                    list.add(Long.valueOf(readVarint64()));
                }
                requirePosition(varint32);
                return;
            }
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 == 0) {
                do {
                    longArrayList.addLong(readInt64());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i3 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i3;
                return;
            }
            if (tagWireType2 != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int varint322 = this.pos + readVarint32();
            while (this.pos < varint322) {
                longArrayList.addLong(readVarint64());
            }
            requirePosition(varint322);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.health.platform.client.proto.Reader
        public <K, V> void readMap(Map<K, V> map, MapEntryLite.Metadata<K, V> metadata, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            requireWireType(2);
            int varint32 = readVarint32();
            requireBytes(varint32);
            int i2 = this.limit;
            this.limit = this.pos + varint32;
            try {
                Object field = metadata.defaultKey;
                Object field2 = metadata.defaultValue;
                while (true) {
                    int fieldNumber = getFieldNumber();
                    if (fieldNumber == Integer.MAX_VALUE) {
                        map.put(field, field2);
                        return;
                    }
                    if (fieldNumber == 1) {
                        field = readField(metadata.keyType, null, null);
                    } else if (fieldNumber != 2) {
                        try {
                            if (!skipField()) {
                                throw new InvalidProtocolBufferException("Unable to parse map entry.");
                            }
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused) {
                            if (!skipField()) {
                                throw new InvalidProtocolBufferException("Unable to parse map entry.");
                            }
                        }
                    } else {
                        field2 = readField(metadata.valueType, metadata.defaultValue.getClass(), extensionRegistryLite);
                    }
                }
            } finally {
                this.limit = i2;
            }
        }

        @Override // androidx.health.platform.client.proto.Reader
        public <T> T readMessage(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            requireWireType(2);
            return (T) readMessage(Protobuf.getInstance().schemaFor((Class) cls), extensionRegistryLite);
        }

        @Override // androidx.health.platform.client.proto.Reader
        public <T> T readMessageBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            requireWireType(2);
            return (T) readMessage(schema, extensionRegistryLite);
        }

        @Override // androidx.health.platform.client.proto.Reader
        public <T> void readMessageList(List<T> list, Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            readMessageList(list, Protobuf.getInstance().schemaFor((Class) cls), extensionRegistryLite);
        }

        @Override // androidx.health.platform.client.proto.Reader
        public int readSFixed32() throws IOException {
            requireWireType(5);
            return readLittleEndian32();
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readSFixed32List(List<Integer> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof IntArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 2) {
                    int varint32 = readVarint32();
                    verifyPackedFixed32Length(varint32);
                    int i4 = this.pos + varint32;
                    while (this.pos < i4) {
                        list.add(Integer.valueOf(readLittleEndian32_NoCheck()));
                    }
                    return;
                }
                if (tagWireType != 5) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                do {
                    list.add(Integer.valueOf(readSFixed32()));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 == 2) {
                int varint322 = readVarint32();
                verifyPackedFixed32Length(varint322);
                int i5 = this.pos + varint322;
                while (this.pos < i5) {
                    intArrayList.addInt(readLittleEndian32_NoCheck());
                }
                return;
            }
            if (tagWireType2 != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                intArrayList.addInt(readSFixed32());
                if (isAtEnd()) {
                    return;
                } else {
                    i3 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i3;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public long readSFixed64() throws IOException {
            requireWireType(1);
            return readLittleEndian64();
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readSFixed64List(List<Long> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof LongArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 1) {
                    do {
                        list.add(Long.valueOf(readSFixed64()));
                        if (isAtEnd()) {
                            return;
                        } else {
                            i2 = this.pos;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = i2;
                    return;
                }
                if (tagWireType != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint32 = readVarint32();
                verifyPackedFixed64Length(varint32);
                int i4 = this.pos + varint32;
                while (this.pos < i4) {
                    list.add(Long.valueOf(readLittleEndian64_NoCheck()));
                }
                return;
            }
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 == 1) {
                do {
                    longArrayList.addLong(readSFixed64());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i3 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i3;
                return;
            }
            if (tagWireType2 != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int varint322 = readVarint32();
            verifyPackedFixed64Length(varint322);
            int i5 = this.pos + varint322;
            while (this.pos < i5) {
                longArrayList.addLong(readLittleEndian64_NoCheck());
            }
        }

        @Override // androidx.health.platform.client.proto.Reader
        public int readSInt32() throws IOException {
            requireWireType(0);
            return CodedInputStream.decodeZigZag32(readVarint32());
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readSInt32List(List<Integer> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof IntArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType != 0) {
                    if (tagWireType != 2) {
                        throw InvalidProtocolBufferException.invalidWireType();
                    }
                    int varint32 = this.pos + readVarint32();
                    while (this.pos < varint32) {
                        list.add(Integer.valueOf(CodedInputStream.decodeZigZag32(readVarint32())));
                    }
                    return;
                }
                do {
                    list.add(Integer.valueOf(readSInt32()));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 != 0) {
                if (tagWireType2 != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint322 = this.pos + readVarint32();
                while (this.pos < varint322) {
                    intArrayList.addInt(CodedInputStream.decodeZigZag32(readVarint32()));
                }
                return;
            }
            do {
                intArrayList.addInt(readSInt32());
                if (isAtEnd()) {
                    return;
                } else {
                    i3 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i3;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public long readSInt64() throws IOException {
            requireWireType(0);
            return CodedInputStream.decodeZigZag64(readVarint64());
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readSInt64List(List<Long> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof LongArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType != 0) {
                    if (tagWireType != 2) {
                        throw InvalidProtocolBufferException.invalidWireType();
                    }
                    int varint32 = this.pos + readVarint32();
                    while (this.pos < varint32) {
                        list.add(Long.valueOf(CodedInputStream.decodeZigZag64(readVarint64())));
                    }
                    return;
                }
                do {
                    list.add(Long.valueOf(readSInt64()));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 != 0) {
                if (tagWireType2 != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint322 = this.pos + readVarint32();
                while (this.pos < varint322) {
                    longArrayList.addLong(CodedInputStream.decodeZigZag64(readVarint64()));
                }
                return;
            }
            do {
                longArrayList.addLong(readSInt64());
                if (isAtEnd()) {
                    return;
                } else {
                    i3 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i3;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public String readString() throws IOException {
            return readStringInternal(false);
        }

        public String readStringInternal(boolean z2) throws IOException {
            requireWireType(2);
            int varint32 = readVarint32();
            if (varint32 == 0) {
                return "";
            }
            requireBytes(varint32);
            if (z2) {
                byte[] bArr = this.buffer;
                int i2 = this.pos;
                if (!Utf8.n(bArr, i2, i2 + varint32)) {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
            }
            String str = new String(this.buffer, this.pos, varint32, Internal.f4458b);
            this.pos += varint32;
            return str;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readStringList(List<String> list) throws IOException {
            readStringListInternal(list, false);
        }

        public void readStringListInternal(List<String> list, boolean z2) throws IOException {
            int i2;
            int i3;
            if (WireFormat.getTagWireType(this.tag) != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            if (!(list instanceof LazyStringList) || z2) {
                do {
                    list.add(readStringInternal(z2));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            LazyStringList lazyStringList = (LazyStringList) list;
            do {
                lazyStringList.add(readBytes());
                if (isAtEnd()) {
                    return;
                } else {
                    i3 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i3;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readStringListRequireUtf8(List<String> list) throws IOException {
            readStringListInternal(list, true);
        }

        @Override // androidx.health.platform.client.proto.Reader
        public String readStringRequireUtf8() throws IOException {
            return readStringInternal(true);
        }

        @Override // androidx.health.platform.client.proto.Reader
        public int readUInt32() throws IOException {
            requireWireType(0);
            return readVarint32();
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readUInt32List(List<Integer> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof IntArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType != 0) {
                    if (tagWireType != 2) {
                        throw InvalidProtocolBufferException.invalidWireType();
                    }
                    int varint32 = this.pos + readVarint32();
                    while (this.pos < varint32) {
                        list.add(Integer.valueOf(readVarint32()));
                    }
                    return;
                }
                do {
                    list.add(Integer.valueOf(readUInt32()));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 != 0) {
                if (tagWireType2 != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint322 = this.pos + readVarint32();
                while (this.pos < varint322) {
                    intArrayList.addInt(readVarint32());
                }
                return;
            }
            do {
                intArrayList.addInt(readUInt32());
                if (isAtEnd()) {
                    return;
                } else {
                    i3 = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i3;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public long readUInt64() throws IOException {
            requireWireType(0);
            return readVarint64();
        }

        @Override // androidx.health.platform.client.proto.Reader
        public void readUInt64List(List<Long> list) throws IOException {
            int i2;
            int i3;
            if (!(list instanceof LongArrayList)) {
                int tagWireType = WireFormat.getTagWireType(this.tag);
                if (tagWireType == 0) {
                    do {
                        list.add(Long.valueOf(readUInt64()));
                        if (isAtEnd()) {
                            return;
                        } else {
                            i2 = this.pos;
                        }
                    } while (readVarint32() == this.tag);
                    this.pos = i2;
                    return;
                }
                if (tagWireType != 2) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                int varint32 = this.pos + readVarint32();
                while (this.pos < varint32) {
                    list.add(Long.valueOf(readVarint64()));
                }
                requirePosition(varint32);
                return;
            }
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType2 = WireFormat.getTagWireType(this.tag);
            if (tagWireType2 == 0) {
                do {
                    longArrayList.addLong(readUInt64());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i3 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i3;
                return;
            }
            if (tagWireType2 != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int varint322 = this.pos + readVarint32();
            while (this.pos < varint322) {
                longArrayList.addLong(readVarint64());
            }
            requirePosition(varint322);
        }

        public long readVarint64() throws IOException {
            long j2;
            long j3;
            long j4;
            int i2 = this.pos;
            int i3 = this.limit;
            if (i3 == i2) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            int i4 = i2 + 1;
            byte b2 = bArr[i2];
            if (b2 >= 0) {
                this.pos = i4;
                return b2;
            }
            if (i3 - i4 < 9) {
                return readVarint64SlowPath();
            }
            int i5 = i2 + 2;
            int i6 = (bArr[i4] << 7) ^ b2;
            if (i6 < 0) {
                j2 = i6 ^ (-128);
            } else {
                int i7 = i2 + 3;
                int i8 = (bArr[i5] << 14) ^ i6;
                if (i8 >= 0) {
                    j2 = i8 ^ 16256;
                    i5 = i7;
                } else {
                    int i9 = i2 + 4;
                    int i10 = i8 ^ (bArr[i7] << Ascii.NAK);
                    if (i10 < 0) {
                        long j5 = (-2080896) ^ i10;
                        i5 = i9;
                        j2 = j5;
                    } else {
                        long j6 = i10;
                        i5 = i2 + 5;
                        long j7 = j6 ^ (bArr[i9] << 28);
                        if (j7 >= 0) {
                            j4 = 266354560;
                        } else {
                            int i11 = i2 + 6;
                            long j8 = j7 ^ (bArr[i5] << 35);
                            if (j8 < 0) {
                                j3 = -34093383808L;
                            } else {
                                i5 = i2 + 7;
                                j7 = j8 ^ (bArr[i11] << 42);
                                if (j7 >= 0) {
                                    j4 = 4363953127296L;
                                } else {
                                    i11 = i2 + 8;
                                    j8 = j7 ^ (bArr[i5] << 49);
                                    if (j8 < 0) {
                                        j3 = -558586000294016L;
                                    } else {
                                        i5 = i2 + 9;
                                        long j9 = (j8 ^ (bArr[i11] << 56)) ^ 71499008037633920L;
                                        if (j9 < 0) {
                                            int i12 = i2 + 10;
                                            if (bArr[i5] < 0) {
                                                throw InvalidProtocolBufferException.malformedVarint();
                                            }
                                            i5 = i12;
                                        }
                                        j2 = j9;
                                    }
                                }
                            }
                            j2 = j8 ^ j3;
                            i5 = i11;
                        }
                        j2 = j7 ^ j4;
                    }
                }
            }
            this.pos = i5;
            return j2;
        }

        @Override // androidx.health.platform.client.proto.Reader
        public boolean skipField() throws IOException {
            int i2;
            if (isAtEnd() || (i2 = this.tag) == this.endGroupTag) {
                return false;
            }
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                skipVarint();
                return true;
            }
            if (tagWireType == 1) {
                skipBytes(8);
                return true;
            }
            if (tagWireType == 2) {
                skipBytes(readVarint32());
                return true;
            }
            if (tagWireType == 3) {
                skipGroup();
                return true;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            skipBytes(4);
            return true;
        }

        private <T> T readGroup(Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            T tNewInstance = schema.newInstance();
            mergeGroupField(tNewInstance, schema, extensionRegistryLite);
            schema.makeImmutable(tNewInstance);
            return tNewInstance;
        }

        private <T> T readMessage(Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            T tNewInstance = schema.newInstance();
            mergeMessageField(tNewInstance, schema, extensionRegistryLite);
            schema.makeImmutable(tNewInstance);
            return tNewInstance;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.health.platform.client.proto.Reader
        @Deprecated
        public <T> void readGroupList(List<T> list, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i2;
            if (WireFormat.getTagWireType(this.tag) == 3) {
                int i3 = this.tag;
                do {
                    list.add(readGroup(schema, extensionRegistryLite));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == i3);
                this.pos = i2;
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.health.platform.client.proto.Reader
        public <T> void readMessageList(List<T> list, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i2;
            if (WireFormat.getTagWireType(this.tag) == 2) {
                int i3 = this.tag;
                do {
                    list.add(readMessage(schema, extensionRegistryLite));
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == i3);
                this.pos = i2;
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    /* synthetic */ BinaryReader(AnonymousClass1 anonymousClass1) {
        this();
    }
}
