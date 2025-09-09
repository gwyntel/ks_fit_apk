package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.MessageLite;
import com.google.common.base.Ascii;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class CodedInputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 100;
    private static final int DEFAULT_SIZE_LIMIT = Integer.MAX_VALUE;

    /* renamed from: a, reason: collision with root package name */
    int f3899a;

    /* renamed from: b, reason: collision with root package name */
    int f3900b;

    /* renamed from: c, reason: collision with root package name */
    int f3901c;

    /* renamed from: d, reason: collision with root package name */
    CodedInputStreamReader f3902d;
    private boolean shouldDiscardUnknownFields;

    private static final class ArrayDecoder extends CodedInputStream {
        private final byte[] buffer;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private boolean enableAliasing;
        private final boolean immutable;
        private int lastTag;
        private int limit;
        private int pos;
        private int startPos;

        private void recomputeBufferSizeAfterLimit() {
            int i2 = this.limit + this.bufferSizeAfterLimit;
            this.limit = i2;
            int i3 = i2 - this.startPos;
            int i4 = this.currentLimit;
            if (i3 <= i4) {
                this.bufferSizeAfterLimit = 0;
                return;
            }
            int i5 = i3 - i4;
            this.bufferSizeAfterLimit = i5;
            this.limit = i2 - i5;
        }

        private void skipRawVarint() throws IOException {
            if (this.limit - this.pos >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                byte[] bArr = this.buffer;
                int i3 = this.pos;
                this.pos = i3 + 1;
                if (bArr[i3] >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void checkLastTagWas(int i2) throws InvalidProtocolBufferException {
            if (this.lastTag != i2) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        long e() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                j2 |= (r3 & Byte.MAX_VALUE) << i2;
                if ((readRawByte() & 128) == 0) {
                    return j2;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void enableAliasing(boolean z2) {
            this.enableAliasing = z2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i2 = this.currentLimit;
            if (i2 == Integer.MAX_VALUE) {
                return -1;
            }
            return i2 - getTotalBytesRead();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return this.pos - this.startPos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return this.pos == this.limit;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void popLimit(int i2) {
            this.currentLimit = i2;
            recomputeBufferSizeAfterLimit();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int pushLimit(int i2) throws InvalidProtocolBufferException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int totalBytesRead = i2 + getTotalBytesRead();
            int i3 = this.currentLimit;
            if (totalBytesRead > i3) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = totalBytesRead;
            recomputeBufferSizeAfterLimit();
            return i3;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.limit;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    ByteBuffer byteBufferWrap = (this.immutable || !this.enableAliasing) ? ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, i3, i3 + rawVarint32)) : ByteBuffer.wrap(this.buffer, i3, rawVarint32).slice();
                    this.pos += rawVarint32;
                    return byteBufferWrap;
                }
            }
            if (rawVarint32 == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.limit;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    ByteString byteStringWrap = (this.immutable && this.enableAliasing) ? ByteString.wrap(this.buffer, i3, rawVarint32) : ByteString.copyFrom(this.buffer, i3, rawVarint32);
                    this.pos += rawVarint32;
                    return byteStringWrap;
                }
            }
            return rawVarint32 == 0 ? ByteString.EMPTY : ByteString.wrap(readRawBytes(rawVarint32));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.f3899a;
            if (i3 >= this.f3900b) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            this.f3899a = i3 + 1;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(WireFormat.a(i2, 4));
            this.f3899a--;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.f3899a >= this.f3900b) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            int iPushLimit = pushLimit(rawVarint32);
            this.f3899a++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.f3899a--;
            popLimit(iPushLimit);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            int i2 = this.pos;
            if (i2 == this.limit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 1;
            return bArr[i2];
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readRawBytes(int i2) throws IOException {
            if (i2 > 0) {
                int i3 = this.limit;
                int i4 = this.pos;
                if (i2 <= i3 - i4) {
                    int i5 = i2 + i4;
                    this.pos = i5;
                    return Arrays.copyOfRange(this.buffer, i4, i5);
                }
            }
            if (i2 > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (i2 == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            int i2 = this.pos;
            if (this.limit - i2 < 4) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 4;
            return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            int i2 = this.pos;
            if (this.limit - i2 < 8) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 8;
            return ((bArr[i2 + 7] & 255) << 56) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawVarint32() throws IOException {
            int i2;
            int i3 = this.pos;
            int i4 = this.limit;
            if (i4 != i3) {
                byte[] bArr = this.buffer;
                int i5 = i3 + 1;
                byte b2 = bArr[i3];
                if (b2 >= 0) {
                    this.pos = i5;
                    return b2;
                }
                if (i4 - i5 >= 9) {
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
                                                    if (bArr[i8] >= 0) {
                                                        i6 = i13;
                                                        i2 = i12;
                                                    }
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
            }
            return (int) e();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j2;
            long j3;
            long j4;
            int i2 = this.pos;
            int i3 = this.limit;
            if (i3 != i2) {
                byte[] bArr = this.buffer;
                int i4 = i2 + 1;
                byte b2 = bArr[i2];
                if (b2 >= 0) {
                    this.pos = i4;
                    return b2;
                }
                if (i3 - i4 >= 9) {
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
                                                    if (bArr[i5] >= 0) {
                                                        i5 = i12;
                                                    }
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
            }
            return e();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(readRawVarint64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readString() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.limit;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    String str = new String(this.buffer, i3, rawVarint32, Internal.f3944a);
                    this.pos += rawVarint32;
                    return str;
                }
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.limit;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    String strH = Utf8.h(this.buffer, i3, rawVarint32);
                    this.pos += rawVarint32;
                    return strH;
                }
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            int rawVarint32 = readRawVarint32();
            this.lastTag = rawVarint32;
            if (WireFormat.getTagFieldNumber(rawVarint32) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException {
            readGroup(i2, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.startPos = this.pos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            }
            if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            }
            if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            }
            if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.a(WireFormat.getTagFieldNumber(i2), 4));
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            skipRawBytes(4);
            return true;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipRawBytes(int i2) throws IOException {
            if (i2 >= 0) {
                int i3 = this.limit;
                int i4 = this.pos;
                if (i2 <= i3 - i4) {
                    this.pos = i4 + i2;
                    return;
                }
            }
            if (i2 >= 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        private ArrayDecoder(byte[] bArr, int i2, int i3, boolean z2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = bArr;
            this.limit = i3 + i2;
            this.pos = i2;
            this.startPos = i2;
            this.immutable = z2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, codedOutputStream));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.f3899a;
            if (i3 < this.f3900b) {
                this.f3899a = i3 + 1;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i2, 4));
                this.f3899a--;
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.f3899a < this.f3900b) {
                int iPushLimit = pushLimit(rawVarint32);
                this.f3899a++;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.f3899a--;
                popLimit(iPushLimit);
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                long int64 = readInt64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeUInt64NoTag(int64);
                return true;
            }
            if (tagWireType == 1) {
                long rawLittleEndian64 = readRawLittleEndian64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed64NoTag(rawLittleEndian64);
                return true;
            }
            if (tagWireType == 2) {
                ByteString bytes = readBytes();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeBytesNoTag(bytes);
                return true;
            }
            if (tagWireType == 3) {
                codedOutputStream.writeRawVarint32(i2);
                skipMessage(codedOutputStream);
                int iA = WireFormat.a(WireFormat.getTagFieldNumber(i2), 4);
                checkLastTagWas(iA);
                codedOutputStream.writeRawVarint32(iA);
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType == 5) {
                int rawLittleEndian32 = readRawLittleEndian32();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed32NoTag(rawLittleEndian32);
                return true;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    private static final class IterableDirectByteBufferDecoder extends CodedInputStream {
        private int bufferSizeAfterCurrentLimit;
        private long currentAddress;
        private ByteBuffer currentByteBuffer;
        private long currentByteBufferLimit;
        private long currentByteBufferPos;
        private long currentByteBufferStartPos;
        private int currentLimit;
        private boolean enableAliasing;
        private boolean immutable;
        private Iterable<ByteBuffer> input;
        private Iterator<ByteBuffer> iterator;
        private int lastTag;
        private int startOffset;
        private int totalBufferSize;
        private int totalBytesRead;

        private long currentRemaining() {
            return this.currentByteBufferLimit - this.currentByteBufferPos;
        }

        private void getNextByteBuffer() throws InvalidProtocolBufferException {
            if (!this.iterator.hasNext()) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            tryGetNextByteBuffer();
        }

        private void readRawBytesTo(byte[] bArr, int i2, int i3) throws IOException {
            if (i3 < 0 || i3 > remaining()) {
                if (i3 > 0) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                if (i3 != 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                return;
            }
            int i4 = i3;
            while (i4 > 0) {
                if (currentRemaining() == 0) {
                    getNextByteBuffer();
                }
                int iMin = Math.min(i4, (int) currentRemaining());
                long j2 = iMin;
                UnsafeUtil.k(this.currentByteBufferPos, bArr, (i3 - i4) + i2, j2);
                i4 -= iMin;
                this.currentByteBufferPos += j2;
            }
        }

        private void recomputeBufferSizeAfterLimit() {
            int i2 = this.totalBufferSize + this.bufferSizeAfterCurrentLimit;
            this.totalBufferSize = i2;
            int i3 = i2 - this.startOffset;
            int i4 = this.currentLimit;
            if (i3 <= i4) {
                this.bufferSizeAfterCurrentLimit = 0;
                return;
            }
            int i5 = i3 - i4;
            this.bufferSizeAfterCurrentLimit = i5;
            this.totalBufferSize = i2 - i5;
        }

        private int remaining() {
            return (int) (((this.totalBufferSize - this.totalBytesRead) - this.currentByteBufferPos) + this.currentByteBufferStartPos);
        }

        private void skipRawVarint() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private ByteBuffer slice(int i2, int i3) throws IOException {
            int iPosition = this.currentByteBuffer.position();
            int iLimit = this.currentByteBuffer.limit();
            try {
                try {
                    this.currentByteBuffer.position(i2);
                    this.currentByteBuffer.limit(i3);
                    return this.currentByteBuffer.slice();
                } catch (IllegalArgumentException unused) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
            } finally {
                this.currentByteBuffer.position(iPosition);
                this.currentByteBuffer.limit(iLimit);
            }
        }

        private void tryGetNextByteBuffer() {
            ByteBuffer next = this.iterator.next();
            this.currentByteBuffer = next;
            this.totalBytesRead += (int) (this.currentByteBufferPos - this.currentByteBufferStartPos);
            long jPosition = next.position();
            this.currentByteBufferPos = jPosition;
            this.currentByteBufferStartPos = jPosition;
            this.currentByteBufferLimit = this.currentByteBuffer.limit();
            long jI = UnsafeUtil.i(this.currentByteBuffer);
            this.currentAddress = jI;
            this.currentByteBufferPos += jI;
            this.currentByteBufferStartPos += jI;
            this.currentByteBufferLimit += jI;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void checkLastTagWas(int i2) throws InvalidProtocolBufferException {
            if (this.lastTag != i2) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        long e() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                j2 |= (r3 & Byte.MAX_VALUE) << i2;
                if ((readRawByte() & 128) == 0) {
                    return j2;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void enableAliasing(boolean z2) {
            this.enableAliasing = z2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i2 = this.currentLimit;
            if (i2 == Integer.MAX_VALUE) {
                return -1;
            }
            return i2 - getTotalBytesRead();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return (int) (((this.totalBytesRead - this.startOffset) + this.currentByteBufferPos) - this.currentByteBufferStartPos);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return (((long) this.totalBytesRead) + this.currentByteBufferPos) - this.currentByteBufferStartPos == ((long) this.totalBufferSize);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void popLimit(int i2) {
            this.currentLimit = i2;
            recomputeBufferSizeAfterLimit();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int pushLimit(int i2) throws InvalidProtocolBufferException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int totalBytesRead = i2 + getTotalBytesRead();
            int i3 = this.currentLimit;
            if (totalBytesRead > i3) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = totalBytesRead;
            recomputeBufferSizeAfterLimit();
            return i3;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                long j2 = rawVarint32;
                if (j2 <= currentRemaining()) {
                    if (this.immutable || !this.enableAliasing) {
                        byte[] bArr = new byte[rawVarint32];
                        UnsafeUtil.k(this.currentByteBufferPos, bArr, 0L, j2);
                        this.currentByteBufferPos += j2;
                        return ByteBuffer.wrap(bArr);
                    }
                    long j3 = this.currentByteBufferPos + j2;
                    this.currentByteBufferPos = j3;
                    long j4 = this.currentAddress;
                    return slice((int) ((j3 - j4) - j2), (int) (j3 - j4));
                }
            }
            if (rawVarint32 > 0 && rawVarint32 <= remaining()) {
                byte[] bArr2 = new byte[rawVarint32];
                readRawBytesTo(bArr2, 0, rawVarint32);
                return ByteBuffer.wrap(bArr2);
            }
            if (rawVarint32 == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                long j2 = rawVarint32;
                long j3 = this.currentByteBufferLimit;
                long j4 = this.currentByteBufferPos;
                if (j2 <= j3 - j4) {
                    if (this.immutable && this.enableAliasing) {
                        int i2 = (int) (j4 - this.currentAddress);
                        ByteString byteStringWrap = ByteString.wrap(slice(i2, rawVarint32 + i2));
                        this.currentByteBufferPos += j2;
                        return byteStringWrap;
                    }
                    byte[] bArr = new byte[rawVarint32];
                    UnsafeUtil.k(j4, bArr, 0L, j2);
                    this.currentByteBufferPos += j2;
                    return ByteString.wrap(bArr);
                }
            }
            if (rawVarint32 > 0 && rawVarint32 <= remaining()) {
                byte[] bArr2 = new byte[rawVarint32];
                readRawBytesTo(bArr2, 0, rawVarint32);
                return ByteString.wrap(bArr2);
            }
            if (rawVarint32 == 0) {
                return ByteString.EMPTY;
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.f3899a;
            if (i3 >= this.f3900b) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            this.f3899a = i3 + 1;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(WireFormat.a(i2, 4));
            this.f3899a--;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.f3899a >= this.f3900b) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            int iPushLimit = pushLimit(rawVarint32);
            this.f3899a++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.f3899a--;
            popLimit(iPushLimit);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            if (currentRemaining() == 0) {
                getNextByteBuffer();
            }
            long j2 = this.currentByteBufferPos;
            this.currentByteBufferPos = 1 + j2;
            return UnsafeUtil.n(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readRawBytes(int i2) throws IOException {
            if (i2 >= 0) {
                long j2 = i2;
                if (j2 <= currentRemaining()) {
                    byte[] bArr = new byte[i2];
                    UnsafeUtil.k(this.currentByteBufferPos, bArr, 0L, j2);
                    this.currentByteBufferPos += j2;
                    return bArr;
                }
            }
            if (i2 >= 0 && i2 <= remaining()) {
                byte[] bArr2 = new byte[i2];
                readRawBytesTo(bArr2, 0, i2);
                return bArr2;
            }
            if (i2 > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (i2 == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            if (currentRemaining() < 4) {
                return (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24);
            }
            long j2 = this.currentByteBufferPos;
            this.currentByteBufferPos = 4 + j2;
            return ((UnsafeUtil.n(j2 + 3) & 255) << 24) | (UnsafeUtil.n(j2) & 255) | ((UnsafeUtil.n(1 + j2) & 255) << 8) | ((UnsafeUtil.n(2 + j2) & 255) << 16);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            long rawByte;
            byte rawByte2;
            if (currentRemaining() >= 8) {
                long j2 = this.currentByteBufferPos;
                this.currentByteBufferPos = 8 + j2;
                rawByte = (UnsafeUtil.n(j2) & 255) | ((UnsafeUtil.n(1 + j2) & 255) << 8) | ((UnsafeUtil.n(2 + j2) & 255) << 16) | ((UnsafeUtil.n(3 + j2) & 255) << 24) | ((UnsafeUtil.n(4 + j2) & 255) << 32) | ((UnsafeUtil.n(5 + j2) & 255) << 40) | ((UnsafeUtil.n(6 + j2) & 255) << 48);
                rawByte2 = UnsafeUtil.n(j2 + 7);
            } else {
                rawByte = (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24) | ((readRawByte() & 255) << 32) | ((readRawByte() & 255) << 40) | ((readRawByte() & 255) << 48);
                rawByte2 = readRawByte();
            }
            return ((rawByte2 & 255) << 56) | rawByte;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawVarint32() throws IOException {
            int i2;
            long j2 = this.currentByteBufferPos;
            if (this.currentByteBufferLimit != j2) {
                long j3 = j2 + 1;
                byte bN = UnsafeUtil.n(j2);
                if (bN >= 0) {
                    this.currentByteBufferPos++;
                    return bN;
                }
                if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10) {
                    long j4 = 2 + j2;
                    int iN = (UnsafeUtil.n(j3) << 7) ^ bN;
                    if (iN < 0) {
                        i2 = iN ^ (-128);
                    } else {
                        long j5 = 3 + j2;
                        int iN2 = (UnsafeUtil.n(j4) << 14) ^ iN;
                        if (iN2 >= 0) {
                            i2 = iN2 ^ 16256;
                        } else {
                            long j6 = 4 + j2;
                            int iN3 = iN2 ^ (UnsafeUtil.n(j5) << Ascii.NAK);
                            if (iN3 < 0) {
                                i2 = (-2080896) ^ iN3;
                            } else {
                                j5 = 5 + j2;
                                byte bN2 = UnsafeUtil.n(j6);
                                int i3 = (iN3 ^ (bN2 << Ascii.FS)) ^ 266354560;
                                if (bN2 < 0) {
                                    j6 = 6 + j2;
                                    if (UnsafeUtil.n(j5) < 0) {
                                        j5 = 7 + j2;
                                        if (UnsafeUtil.n(j6) < 0) {
                                            j6 = 8 + j2;
                                            if (UnsafeUtil.n(j5) < 0) {
                                                j5 = 9 + j2;
                                                if (UnsafeUtil.n(j6) < 0) {
                                                    long j7 = j2 + 10;
                                                    if (UnsafeUtil.n(j5) >= 0) {
                                                        i2 = i3;
                                                        j4 = j7;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    i2 = i3;
                                }
                                i2 = i3;
                            }
                            j4 = j6;
                        }
                        j4 = j5;
                    }
                    this.currentByteBufferPos = j4;
                    return i2;
                }
            }
            return (int) e();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j2;
            long j3;
            long j4;
            long j5 = this.currentByteBufferPos;
            if (this.currentByteBufferLimit != j5) {
                long j6 = j5 + 1;
                byte bN = UnsafeUtil.n(j5);
                if (bN >= 0) {
                    this.currentByteBufferPos++;
                    return bN;
                }
                if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10) {
                    long j7 = 2 + j5;
                    int iN = (UnsafeUtil.n(j6) << 7) ^ bN;
                    if (iN < 0) {
                        j2 = iN ^ (-128);
                    } else {
                        long j8 = 3 + j5;
                        int iN2 = (UnsafeUtil.n(j7) << 14) ^ iN;
                        if (iN2 >= 0) {
                            j2 = iN2 ^ 16256;
                            j7 = j8;
                        } else {
                            long j9 = 4 + j5;
                            int iN3 = iN2 ^ (UnsafeUtil.n(j8) << Ascii.NAK);
                            if (iN3 < 0) {
                                j2 = (-2080896) ^ iN3;
                                j7 = j9;
                            } else {
                                long j10 = 5 + j5;
                                long jN = (UnsafeUtil.n(j9) << 28) ^ iN3;
                                if (jN >= 0) {
                                    j4 = 266354560;
                                } else {
                                    long j11 = 6 + j5;
                                    long jN2 = jN ^ (UnsafeUtil.n(j10) << 35);
                                    if (jN2 < 0) {
                                        j3 = -34093383808L;
                                    } else {
                                        j10 = 7 + j5;
                                        jN = jN2 ^ (UnsafeUtil.n(j11) << 42);
                                        if (jN >= 0) {
                                            j4 = 4363953127296L;
                                        } else {
                                            j11 = 8 + j5;
                                            jN2 = jN ^ (UnsafeUtil.n(j10) << 49);
                                            if (jN2 < 0) {
                                                j3 = -558586000294016L;
                                            } else {
                                                j10 = 9 + j5;
                                                long jN3 = (jN2 ^ (UnsafeUtil.n(j11) << 56)) ^ 71499008037633920L;
                                                if (jN3 < 0) {
                                                    long j12 = j5 + 10;
                                                    if (UnsafeUtil.n(j10) >= 0) {
                                                        j2 = jN3;
                                                        j7 = j12;
                                                    }
                                                } else {
                                                    j2 = jN3;
                                                    j7 = j10;
                                                }
                                            }
                                        }
                                    }
                                    j2 = j3 ^ jN2;
                                    j7 = j11;
                                }
                                j2 = j4 ^ jN;
                                j7 = j10;
                            }
                        }
                    }
                    this.currentByteBufferPos = j7;
                    return j2;
                }
            }
            return e();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(readRawVarint64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readString() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                long j2 = rawVarint32;
                long j3 = this.currentByteBufferLimit;
                long j4 = this.currentByteBufferPos;
                if (j2 <= j3 - j4) {
                    byte[] bArr = new byte[rawVarint32];
                    UnsafeUtil.k(j4, bArr, 0L, j2);
                    String str = new String(bArr, Internal.f3944a);
                    this.currentByteBufferPos += j2;
                    return str;
                }
            }
            if (rawVarint32 > 0 && rawVarint32 <= remaining()) {
                byte[] bArr2 = new byte[rawVarint32];
                readRawBytesTo(bArr2, 0, rawVarint32);
                return new String(bArr2, Internal.f3944a);
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                long j2 = rawVarint32;
                long j3 = this.currentByteBufferLimit;
                long j4 = this.currentByteBufferPos;
                if (j2 <= j3 - j4) {
                    String strG = Utf8.g(this.currentByteBuffer, (int) (j4 - this.currentByteBufferStartPos), rawVarint32);
                    this.currentByteBufferPos += j2;
                    return strG;
                }
            }
            if (rawVarint32 >= 0 && rawVarint32 <= remaining()) {
                byte[] bArr = new byte[rawVarint32];
                readRawBytesTo(bArr, 0, rawVarint32);
                return Utf8.h(bArr, 0, rawVarint32);
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            int rawVarint32 = readRawVarint32();
            this.lastTag = rawVarint32;
            if (WireFormat.getTagFieldNumber(rawVarint32) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException {
            readGroup(i2, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.startOffset = (int) ((this.totalBytesRead + this.currentByteBufferPos) - this.currentByteBufferStartPos);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            }
            if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            }
            if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            }
            if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.a(WireFormat.getTagFieldNumber(i2), 4));
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            skipRawBytes(4);
            return true;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipRawBytes(int i2) throws IOException {
            if (i2 < 0 || i2 > ((this.totalBufferSize - this.totalBytesRead) - this.currentByteBufferPos) + this.currentByteBufferStartPos) {
                if (i2 >= 0) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                throw InvalidProtocolBufferException.negativeSize();
            }
            while (i2 > 0) {
                if (currentRemaining() == 0) {
                    getNextByteBuffer();
                }
                int iMin = Math.min(i2, (int) currentRemaining());
                i2 -= iMin;
                this.currentByteBufferPos += iMin;
            }
        }

        private IterableDirectByteBufferDecoder(Iterable<ByteBuffer> iterable, int i2, boolean z2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.totalBufferSize = i2;
            this.input = iterable;
            this.iterator = iterable.iterator();
            this.immutable = z2;
            this.totalBytesRead = 0;
            this.startOffset = 0;
            if (i2 != 0) {
                tryGetNextByteBuffer();
                return;
            }
            this.currentByteBuffer = Internal.EMPTY_BYTE_BUFFER;
            this.currentByteBufferPos = 0L;
            this.currentByteBufferStartPos = 0L;
            this.currentByteBufferLimit = 0L;
            this.currentAddress = 0L;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, codedOutputStream));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.f3899a;
            if (i3 < this.f3900b) {
                this.f3899a = i3 + 1;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i2, 4));
                this.f3899a--;
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.f3899a < this.f3900b) {
                int iPushLimit = pushLimit(rawVarint32);
                this.f3899a++;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.f3899a--;
                popLimit(iPushLimit);
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                long int64 = readInt64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeUInt64NoTag(int64);
                return true;
            }
            if (tagWireType == 1) {
                long rawLittleEndian64 = readRawLittleEndian64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed64NoTag(rawLittleEndian64);
                return true;
            }
            if (tagWireType == 2) {
                ByteString bytes = readBytes();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeBytesNoTag(bytes);
                return true;
            }
            if (tagWireType == 3) {
                codedOutputStream.writeRawVarint32(i2);
                skipMessage(codedOutputStream);
                int iA = WireFormat.a(WireFormat.getTagFieldNumber(i2), 4);
                checkLastTagWas(iA);
                codedOutputStream.writeRawVarint32(iA);
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType == 5) {
                int rawLittleEndian32 = readRawLittleEndian32();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed32NoTag(rawLittleEndian32);
                return true;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    private static final class StreamDecoder extends CodedInputStream {
        private final byte[] buffer;
        private int bufferSize;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private final InputStream input;
        private int lastTag;
        private int pos;
        private RefillCallback refillCallback;
        private int totalBytesRetired;

        private interface RefillCallback {
            void onRefill();
        }

        private class SkippedDataSink implements RefillCallback {
            private ByteArrayOutputStream byteArrayStream;
            private int lastPos;

            private SkippedDataSink() {
                this.lastPos = StreamDecoder.this.pos;
            }

            @Override // androidx.datastore.preferences.protobuf.CodedInputStream.StreamDecoder.RefillCallback
            public void onRefill() {
                if (this.byteArrayStream == null) {
                    this.byteArrayStream = new ByteArrayOutputStream();
                }
                this.byteArrayStream.write(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos - this.lastPos);
                this.lastPos = 0;
            }
        }

        private ByteString readBytesSlowPath(int i2) throws IOException {
            byte[] rawBytesSlowPathOneChunk = readRawBytesSlowPathOneChunk(i2);
            if (rawBytesSlowPathOneChunk != null) {
                return ByteString.copyFrom(rawBytesSlowPathOneChunk);
            }
            int i3 = this.pos;
            int i4 = this.bufferSize;
            int length = i4 - i3;
            this.totalBytesRetired += i4;
            this.pos = 0;
            this.bufferSize = 0;
            List<byte[]> rawBytesSlowPathRemainingChunks = readRawBytesSlowPathRemainingChunks(i2 - length);
            byte[] bArr = new byte[i2];
            System.arraycopy(this.buffer, i3, bArr, 0, length);
            for (byte[] bArr2 : rawBytesSlowPathRemainingChunks) {
                System.arraycopy(bArr2, 0, bArr, length, bArr2.length);
                length += bArr2.length;
            }
            return ByteString.wrap(bArr);
        }

        private byte[] readRawBytesSlowPath(int i2, boolean z2) throws IOException {
            byte[] rawBytesSlowPathOneChunk = readRawBytesSlowPathOneChunk(i2);
            if (rawBytesSlowPathOneChunk != null) {
                return z2 ? (byte[]) rawBytesSlowPathOneChunk.clone() : rawBytesSlowPathOneChunk;
            }
            int i3 = this.pos;
            int i4 = this.bufferSize;
            int length = i4 - i3;
            this.totalBytesRetired += i4;
            this.pos = 0;
            this.bufferSize = 0;
            List<byte[]> rawBytesSlowPathRemainingChunks = readRawBytesSlowPathRemainingChunks(i2 - length);
            byte[] bArr = new byte[i2];
            System.arraycopy(this.buffer, i3, bArr, 0, length);
            for (byte[] bArr2 : rawBytesSlowPathRemainingChunks) {
                System.arraycopy(bArr2, 0, bArr, length, bArr2.length);
                length += bArr2.length;
            }
            return bArr;
        }

        private byte[] readRawBytesSlowPathOneChunk(int i2) throws IOException {
            if (i2 == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int i3 = this.totalBytesRetired;
            int i4 = this.pos;
            int i5 = i3 + i4 + i2;
            if (i5 - this.f3901c > 0) {
                throw InvalidProtocolBufferException.sizeLimitExceeded();
            }
            int i6 = this.currentLimit;
            if (i5 > i6) {
                skipRawBytes((i6 - i3) - i4);
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            int i7 = this.bufferSize - i4;
            int i8 = i2 - i7;
            if (i8 >= 4096 && i8 > this.input.available()) {
                return null;
            }
            byte[] bArr = new byte[i2];
            System.arraycopy(this.buffer, this.pos, bArr, 0, i7);
            this.totalBytesRetired += this.bufferSize;
            this.pos = 0;
            this.bufferSize = 0;
            while (i7 < i2) {
                int i9 = this.input.read(bArr, i7, i2 - i7);
                if (i9 == -1) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                this.totalBytesRetired += i9;
                i7 += i9;
            }
            return bArr;
        }

        private List<byte[]> readRawBytesSlowPathRemainingChunks(int i2) throws IOException {
            ArrayList arrayList = new ArrayList();
            while (i2 > 0) {
                int iMin = Math.min(i2, 4096);
                byte[] bArr = new byte[iMin];
                int i3 = 0;
                while (i3 < iMin) {
                    int i4 = this.input.read(bArr, i3, iMin - i3);
                    if (i4 == -1) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += i4;
                    i3 += i4;
                }
                i2 -= iMin;
                arrayList.add(bArr);
            }
            return arrayList;
        }

        private void recomputeBufferSizeAfterLimit() {
            int i2 = this.bufferSize + this.bufferSizeAfterLimit;
            this.bufferSize = i2;
            int i3 = this.totalBytesRetired + i2;
            int i4 = this.currentLimit;
            if (i3 <= i4) {
                this.bufferSizeAfterLimit = 0;
                return;
            }
            int i5 = i3 - i4;
            this.bufferSizeAfterLimit = i5;
            this.bufferSize = i2 - i5;
        }

        private void refillBuffer(int i2) throws IOException {
            if (tryRefillBuffer(i2)) {
                return;
            }
            if (i2 <= (this.f3901c - this.totalBytesRetired) - this.pos) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            throw InvalidProtocolBufferException.sizeLimitExceeded();
        }

        private void skipRawBytesSlowPath(int i2) throws IOException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int i3 = this.totalBytesRetired;
            int i4 = this.pos;
            int i5 = i3 + i4 + i2;
            int i6 = this.currentLimit;
            if (i5 > i6) {
                skipRawBytes((i6 - i3) - i4);
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            int i7 = 0;
            if (this.refillCallback == null) {
                this.totalBytesRetired = i3 + i4;
                int i8 = this.bufferSize - i4;
                this.bufferSize = 0;
                this.pos = 0;
                i7 = i8;
                while (i7 < i2) {
                    try {
                        long j2 = i2 - i7;
                        long jSkip = this.input.skip(j2);
                        if (jSkip < 0 || jSkip > j2) {
                            throw new IllegalStateException(this.input.getClass() + "#skip returned invalid result: " + jSkip + "\nThe InputStream implementation is buggy.");
                        }
                        if (jSkip == 0) {
                            break;
                        } else {
                            i7 += (int) jSkip;
                        }
                    } finally {
                        this.totalBytesRetired += i7;
                        recomputeBufferSizeAfterLimit();
                    }
                }
            }
            if (i7 >= i2) {
                return;
            }
            int i9 = this.bufferSize;
            int i10 = i9 - this.pos;
            this.pos = i9;
            refillBuffer(1);
            while (true) {
                int i11 = i2 - i10;
                int i12 = this.bufferSize;
                if (i11 <= i12) {
                    this.pos = i11;
                    return;
                } else {
                    i10 += i12;
                    this.pos = i12;
                    refillBuffer(1);
                }
            }
        }

        private void skipRawVarint() throws IOException {
            if (this.bufferSize - this.pos >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                byte[] bArr = this.buffer;
                int i3 = this.pos;
                this.pos = i3 + 1;
                if (bArr[i3] >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private boolean tryRefillBuffer(int i2) throws IOException {
            int i3 = this.pos;
            if (i3 + i2 <= this.bufferSize) {
                throw new IllegalStateException("refillBuffer() called when " + i2 + " bytes were already available in buffer");
            }
            int i4 = this.f3901c;
            int i5 = this.totalBytesRetired;
            if (i2 > (i4 - i5) - i3 || i5 + i3 + i2 > this.currentLimit) {
                return false;
            }
            RefillCallback refillCallback = this.refillCallback;
            if (refillCallback != null) {
                refillCallback.onRefill();
            }
            int i6 = this.pos;
            if (i6 > 0) {
                int i7 = this.bufferSize;
                if (i7 > i6) {
                    byte[] bArr = this.buffer;
                    System.arraycopy(bArr, i6, bArr, 0, i7 - i6);
                }
                this.totalBytesRetired += i6;
                this.bufferSize -= i6;
                this.pos = 0;
            }
            InputStream inputStream = this.input;
            byte[] bArr2 = this.buffer;
            int i8 = this.bufferSize;
            int i9 = inputStream.read(bArr2, i8, Math.min(bArr2.length - i8, (this.f3901c - this.totalBytesRetired) - i8));
            if (i9 == 0 || i9 < -1 || i9 > this.buffer.length) {
                throw new IllegalStateException(this.input.getClass() + "#read(byte[]) returned invalid result: " + i9 + "\nThe InputStream implementation is buggy.");
            }
            if (i9 <= 0) {
                return false;
            }
            this.bufferSize += i9;
            recomputeBufferSizeAfterLimit();
            if (this.bufferSize >= i2) {
                return true;
            }
            return tryRefillBuffer(i2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void checkLastTagWas(int i2) throws InvalidProtocolBufferException {
            if (this.lastTag != i2) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void enableAliasing(boolean z2) {
        }

        long g() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                j2 |= (r3 & Byte.MAX_VALUE) << i2;
                if ((readRawByte() & 128) == 0) {
                    return j2;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i2 = this.currentLimit;
            if (i2 == Integer.MAX_VALUE) {
                return -1;
            }
            return i2 - (this.totalBytesRetired + this.pos);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return this.totalBytesRetired + this.pos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return this.pos == this.bufferSize && !tryRefillBuffer(1);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void popLimit(int i2) {
            this.currentLimit = i2;
            recomputeBufferSizeAfterLimit();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int pushLimit(int i2) throws InvalidProtocolBufferException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int i3 = i2 + this.totalBytesRetired + this.pos;
            int i4 = this.currentLimit;
            if (i3 > i4) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = i3;
            recomputeBufferSizeAfterLimit();
            return i4;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            int rawVarint32 = readRawVarint32();
            int i2 = this.bufferSize;
            int i3 = this.pos;
            if (rawVarint32 > i2 - i3 || rawVarint32 <= 0) {
                return readRawBytesSlowPath(rawVarint32, false);
            }
            byte[] bArrCopyOfRange = Arrays.copyOfRange(this.buffer, i3, i3 + rawVarint32);
            this.pos += rawVarint32;
            return bArrCopyOfRange;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int rawVarint32 = readRawVarint32();
            int i2 = this.bufferSize;
            int i3 = this.pos;
            if (rawVarint32 > i2 - i3 || rawVarint32 <= 0) {
                return rawVarint32 == 0 ? Internal.EMPTY_BYTE_BUFFER : ByteBuffer.wrap(readRawBytesSlowPath(rawVarint32, true));
            }
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, i3, i3 + rawVarint32));
            this.pos += rawVarint32;
            return byteBufferWrap;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int rawVarint32 = readRawVarint32();
            int i2 = this.bufferSize;
            int i3 = this.pos;
            if (rawVarint32 > i2 - i3 || rawVarint32 <= 0) {
                return rawVarint32 == 0 ? ByteString.EMPTY : readBytesSlowPath(rawVarint32);
            }
            ByteString byteStringCopyFrom = ByteString.copyFrom(this.buffer, i3, rawVarint32);
            this.pos += rawVarint32;
            return byteStringCopyFrom;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.f3899a;
            if (i3 >= this.f3900b) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            this.f3899a = i3 + 1;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(WireFormat.a(i2, 4));
            this.f3899a--;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.f3899a >= this.f3900b) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            int iPushLimit = pushLimit(rawVarint32);
            this.f3899a++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.f3899a--;
            popLimit(iPushLimit);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            if (this.pos == this.bufferSize) {
                refillBuffer(1);
            }
            byte[] bArr = this.buffer;
            int i2 = this.pos;
            this.pos = i2 + 1;
            return bArr[i2];
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readRawBytes(int i2) throws IOException {
            int i3 = this.pos;
            if (i2 > this.bufferSize - i3 || i2 <= 0) {
                return readRawBytesSlowPath(i2, false);
            }
            int i4 = i2 + i3;
            this.pos = i4;
            return Arrays.copyOfRange(this.buffer, i3, i4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            int i2 = this.pos;
            if (this.bufferSize - i2 < 4) {
                refillBuffer(4);
                i2 = this.pos;
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 4;
            return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            int i2 = this.pos;
            if (this.bufferSize - i2 < 8) {
                refillBuffer(8);
                i2 = this.pos;
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 8;
            return ((bArr[i2 + 7] & 255) << 56) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawVarint32() throws IOException {
            int i2;
            int i3 = this.pos;
            int i4 = this.bufferSize;
            if (i4 != i3) {
                byte[] bArr = this.buffer;
                int i5 = i3 + 1;
                byte b2 = bArr[i3];
                if (b2 >= 0) {
                    this.pos = i5;
                    return b2;
                }
                if (i4 - i5 >= 9) {
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
                                                    if (bArr[i8] >= 0) {
                                                        i6 = i13;
                                                        i2 = i12;
                                                    }
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
            }
            return (int) g();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j2;
            long j3;
            long j4;
            int i2 = this.pos;
            int i3 = this.bufferSize;
            if (i3 != i2) {
                byte[] bArr = this.buffer;
                int i4 = i2 + 1;
                byte b2 = bArr[i2];
                if (b2 >= 0) {
                    this.pos = i4;
                    return b2;
                }
                if (i3 - i4 >= 9) {
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
                                                    if (bArr[i5] >= 0) {
                                                        i5 = i12;
                                                    }
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
            }
            return g();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(readRawVarint64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readString() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.bufferSize;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    String str = new String(this.buffer, i3, rawVarint32, Internal.f3944a);
                    this.pos += rawVarint32;
                    return str;
                }
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 > this.bufferSize) {
                return new String(readRawBytesSlowPath(rawVarint32, false), Internal.f3944a);
            }
            refillBuffer(rawVarint32);
            String str2 = new String(this.buffer, this.pos, rawVarint32, Internal.f3944a);
            this.pos += rawVarint32;
            return str2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            byte[] rawBytesSlowPath;
            int rawVarint32 = readRawVarint32();
            int i2 = this.pos;
            int i3 = this.bufferSize;
            if (rawVarint32 <= i3 - i2 && rawVarint32 > 0) {
                rawBytesSlowPath = this.buffer;
                this.pos = i2 + rawVarint32;
            } else {
                if (rawVarint32 == 0) {
                    return "";
                }
                i2 = 0;
                if (rawVarint32 <= i3) {
                    refillBuffer(rawVarint32);
                    rawBytesSlowPath = this.buffer;
                    this.pos = rawVarint32;
                } else {
                    rawBytesSlowPath = readRawBytesSlowPath(rawVarint32, false);
                }
            }
            return Utf8.h(rawBytesSlowPath, i2, rawVarint32);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            int rawVarint32 = readRawVarint32();
            this.lastTag = rawVarint32;
            if (WireFormat.getTagFieldNumber(rawVarint32) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException {
            readGroup(i2, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.totalBytesRetired = -this.pos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            }
            if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            }
            if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            }
            if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.a(WireFormat.getTagFieldNumber(i2), 4));
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            skipRawBytes(4);
            return true;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipRawBytes(int i2) throws IOException {
            int i3 = this.bufferSize;
            int i4 = this.pos;
            if (i2 > i3 - i4 || i2 < 0) {
                skipRawBytesSlowPath(i2);
            } else {
                this.pos = i4 + i2;
            }
        }

        private StreamDecoder(InputStream inputStream, int i2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.refillCallback = null;
            Internal.b(inputStream, "input");
            this.input = inputStream;
            this.buffer = new byte[i2];
            this.bufferSize = 0;
            this.pos = 0;
            this.totalBytesRetired = 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, codedOutputStream));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.f3899a;
            if (i3 < this.f3900b) {
                this.f3899a = i3 + 1;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i2, 4));
                this.f3899a--;
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.f3899a < this.f3900b) {
                int iPushLimit = pushLimit(rawVarint32);
                this.f3899a++;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.f3899a--;
                popLimit(iPushLimit);
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                long int64 = readInt64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeUInt64NoTag(int64);
                return true;
            }
            if (tagWireType == 1) {
                long rawLittleEndian64 = readRawLittleEndian64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed64NoTag(rawLittleEndian64);
                return true;
            }
            if (tagWireType == 2) {
                ByteString bytes = readBytes();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeBytesNoTag(bytes);
                return true;
            }
            if (tagWireType == 3) {
                codedOutputStream.writeRawVarint32(i2);
                skipMessage(codedOutputStream);
                int iA = WireFormat.a(WireFormat.getTagFieldNumber(i2), 4);
                checkLastTagWas(iA);
                codedOutputStream.writeRawVarint32(iA);
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType == 5) {
                int rawLittleEndian32 = readRawLittleEndian32();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed32NoTag(rawLittleEndian32);
                return true;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    private static final class UnsafeDirectNioDecoder extends CodedInputStream {
        private final long address;
        private final ByteBuffer buffer;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private boolean enableAliasing;
        private final boolean immutable;
        private int lastTag;
        private long limit;
        private long pos;
        private long startPos;

        private int bufferPos(long j2) {
            return (int) (j2 - this.address);
        }

        static boolean e() {
            return UnsafeUtil.x();
        }

        private void recomputeBufferSizeAfterLimit() {
            long j2 = this.limit + this.bufferSizeAfterLimit;
            this.limit = j2;
            int i2 = (int) (j2 - this.startPos);
            int i3 = this.currentLimit;
            if (i2 <= i3) {
                this.bufferSizeAfterLimit = 0;
                return;
            }
            int i4 = i2 - i3;
            this.bufferSizeAfterLimit = i4;
            this.limit = j2 - i4;
        }

        private int remaining() {
            return (int) (this.limit - this.pos);
        }

        private void skipRawVarint() throws IOException {
            if (remaining() >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                long j2 = this.pos;
                this.pos = 1 + j2;
                if (UnsafeUtil.n(j2) >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private ByteBuffer slice(long j2, long j3) throws IOException {
            int iPosition = this.buffer.position();
            int iLimit = this.buffer.limit();
            try {
                try {
                    this.buffer.position(bufferPos(j2));
                    this.buffer.limit(bufferPos(j3));
                    return this.buffer.slice();
                } catch (IllegalArgumentException unused) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
            } finally {
                this.buffer.position(iPosition);
                this.buffer.limit(iLimit);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void checkLastTagWas(int i2) throws InvalidProtocolBufferException {
            if (this.lastTag != i2) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void enableAliasing(boolean z2) {
            this.enableAliasing = z2;
        }

        long f() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                j2 |= (r3 & Byte.MAX_VALUE) << i2;
                if ((readRawByte() & 128) == 0) {
                    return j2;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i2 = this.currentLimit;
            if (i2 == Integer.MAX_VALUE) {
                return -1;
            }
            return i2 - getTotalBytesRead();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return (int) (this.pos - this.startPos);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return this.pos == this.limit;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void popLimit(int i2) {
            this.currentLimit = i2;
            recomputeBufferSizeAfterLimit();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int pushLimit(int i2) throws InvalidProtocolBufferException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int totalBytesRead = i2 + getTotalBytesRead();
            int i3 = this.currentLimit;
            if (totalBytesRead > i3) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = totalBytesRead;
            recomputeBufferSizeAfterLimit();
            return i3;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 <= 0 || rawVarint32 > remaining()) {
                if (rawVarint32 == 0) {
                    return Internal.EMPTY_BYTE_BUFFER;
                }
                if (rawVarint32 < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (this.immutable || !this.enableAliasing) {
                byte[] bArr = new byte[rawVarint32];
                long j2 = rawVarint32;
                UnsafeUtil.k(this.pos, bArr, 0L, j2);
                this.pos += j2;
                return ByteBuffer.wrap(bArr);
            }
            long j3 = this.pos;
            long j4 = rawVarint32;
            ByteBuffer byteBufferSlice = slice(j3, j3 + j4);
            this.pos += j4;
            return byteBufferSlice;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 <= 0 || rawVarint32 > remaining()) {
                if (rawVarint32 == 0) {
                    return ByteString.EMPTY;
                }
                if (rawVarint32 < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (this.immutable && this.enableAliasing) {
                long j2 = this.pos;
                long j3 = rawVarint32;
                ByteBuffer byteBufferSlice = slice(j2, j2 + j3);
                this.pos += j3;
                return ByteString.wrap(byteBufferSlice);
            }
            byte[] bArr = new byte[rawVarint32];
            long j4 = rawVarint32;
            UnsafeUtil.k(this.pos, bArr, 0L, j4);
            this.pos += j4;
            return ByteString.wrap(bArr);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.f3899a;
            if (i3 >= this.f3900b) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            this.f3899a = i3 + 1;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(WireFormat.a(i2, 4));
            this.f3899a--;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.f3899a >= this.f3900b) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            int iPushLimit = pushLimit(rawVarint32);
            this.f3899a++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.f3899a--;
            popLimit(iPushLimit);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            long j2 = this.pos;
            if (j2 == this.limit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = 1 + j2;
            return UnsafeUtil.n(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readRawBytes(int i2) throws IOException {
            if (i2 < 0 || i2 > remaining()) {
                if (i2 > 0) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                if (i2 == 0) {
                    return Internal.EMPTY_BYTE_ARRAY;
                }
                throw InvalidProtocolBufferException.negativeSize();
            }
            byte[] bArr = new byte[i2];
            long j2 = this.pos;
            long j3 = i2;
            slice(j2, j2 + j3).get(bArr);
            this.pos += j3;
            return bArr;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            long j2 = this.pos;
            if (this.limit - j2 < 4) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = 4 + j2;
            return ((UnsafeUtil.n(j2 + 3) & 255) << 24) | (UnsafeUtil.n(j2) & 255) | ((UnsafeUtil.n(1 + j2) & 255) << 8) | ((UnsafeUtil.n(2 + j2) & 255) << 16);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            long j2 = this.pos;
            if (this.limit - j2 < 8) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = 8 + j2;
            return ((UnsafeUtil.n(j2 + 7) & 255) << 56) | (UnsafeUtil.n(j2) & 255) | ((UnsafeUtil.n(1 + j2) & 255) << 8) | ((UnsafeUtil.n(2 + j2) & 255) << 16) | ((UnsafeUtil.n(3 + j2) & 255) << 24) | ((UnsafeUtil.n(4 + j2) & 255) << 32) | ((UnsafeUtil.n(5 + j2) & 255) << 40) | ((UnsafeUtil.n(6 + j2) & 255) << 48);
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x008c, code lost:
        
            if (androidx.datastore.preferences.protobuf.UnsafeUtil.n(r3) < 0) goto L34;
         */
        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r9 = this;
                long r0 = r9.pos
                long r2 = r9.limit
                int r2 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r2 != 0) goto La
                goto L8e
            La:
                r2 = 1
                long r2 = r2 + r0
                byte r4 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r0)
                if (r4 < 0) goto L16
                r9.pos = r2
                return r4
            L16:
                long r5 = r9.limit
                long r5 = r5 - r2
                r7 = 9
                int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r5 >= 0) goto L21
                goto L8e
            L21:
                r5 = 2
                long r5 = r5 + r0
                byte r2 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r2)
                int r2 = r2 << 7
                r2 = r2 ^ r4
                if (r2 >= 0) goto L31
                r0 = r2 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L98
            L31:
                r3 = 3
                long r3 = r3 + r0
                byte r5 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r5)
                int r5 = r5 << 14
                r2 = r2 ^ r5
                if (r2 < 0) goto L41
                r0 = r2 ^ 16256(0x3f80, float:2.278E-41)
            L3f:
                r5 = r3
                goto L98
            L41:
                r5 = 4
                long r5 = r5 + r0
                byte r3 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r3)
                int r3 = r3 << 21
                r2 = r2 ^ r3
                if (r2 >= 0) goto L52
                r0 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r2
                goto L98
            L52:
                r3 = 5
                long r3 = r3 + r0
                byte r5 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r5)
                int r6 = r5 << 28
                r2 = r2 ^ r6
                r6 = 266354560(0xfe03f80, float:2.2112565E-29)
                r2 = r2 ^ r6
                if (r5 >= 0) goto L96
                r5 = 6
                long r5 = r5 + r0
                byte r3 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r3)
                if (r3 >= 0) goto L94
                r3 = 7
                long r3 = r3 + r0
                byte r5 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r5)
                if (r5 >= 0) goto L96
                r5 = 8
                long r5 = r5 + r0
                byte r3 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r3)
                if (r3 >= 0) goto L94
                long r3 = r0 + r7
                byte r5 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r5)
                if (r5 >= 0) goto L96
                r5 = 10
                long r5 = r5 + r0
                byte r0 = androidx.datastore.preferences.protobuf.UnsafeUtil.n(r3)
                if (r0 >= 0) goto L94
            L8e:
                long r0 = r9.f()
                int r0 = (int) r0
                return r0
            L94:
                r0 = r2
                goto L98
            L96:
                r0 = r2
                goto L3f
            L98:
                r9.pos = r5
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.CodedInputStream.UnsafeDirectNioDecoder.readRawVarint32():int");
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j2;
            long j3;
            long j4;
            int i2;
            long j5 = this.pos;
            if (this.limit != j5) {
                long j6 = 1 + j5;
                byte bN = UnsafeUtil.n(j5);
                if (bN >= 0) {
                    this.pos = j6;
                    return bN;
                }
                if (this.limit - j6 >= 9) {
                    long j7 = 2 + j5;
                    int iN = (UnsafeUtil.n(j6) << 7) ^ bN;
                    if (iN >= 0) {
                        long j8 = 3 + j5;
                        int iN2 = iN ^ (UnsafeUtil.n(j7) << 14);
                        if (iN2 >= 0) {
                            j2 = iN2 ^ 16256;
                            j7 = j8;
                        } else {
                            j7 = 4 + j5;
                            int iN3 = iN2 ^ (UnsafeUtil.n(j8) << Ascii.NAK);
                            if (iN3 < 0) {
                                i2 = (-2080896) ^ iN3;
                            } else {
                                long j9 = 5 + j5;
                                long jN = iN3 ^ (UnsafeUtil.n(j7) << 28);
                                if (jN >= 0) {
                                    j4 = 266354560;
                                } else {
                                    long j10 = 6 + j5;
                                    long jN2 = jN ^ (UnsafeUtil.n(j9) << 35);
                                    if (jN2 < 0) {
                                        j3 = -34093383808L;
                                    } else {
                                        j9 = 7 + j5;
                                        jN = jN2 ^ (UnsafeUtil.n(j10) << 42);
                                        if (jN >= 0) {
                                            j4 = 4363953127296L;
                                        } else {
                                            j10 = 8 + j5;
                                            jN2 = jN ^ (UnsafeUtil.n(j9) << 49);
                                            if (jN2 < 0) {
                                                j3 = -558586000294016L;
                                            } else {
                                                long j11 = j5 + 9;
                                                long jN3 = (jN2 ^ (UnsafeUtil.n(j10) << 56)) ^ 71499008037633920L;
                                                if (jN3 < 0) {
                                                    long j12 = j5 + 10;
                                                    if (UnsafeUtil.n(j11) >= 0) {
                                                        j7 = j12;
                                                        j2 = jN3;
                                                    }
                                                } else {
                                                    j2 = jN3;
                                                    j7 = j11;
                                                }
                                            }
                                        }
                                    }
                                    j2 = j3 ^ jN2;
                                    j7 = j10;
                                }
                                j2 = j4 ^ jN;
                                j7 = j9;
                            }
                        }
                        this.pos = j7;
                        return j2;
                    }
                    i2 = iN ^ (-128);
                    j2 = i2;
                    this.pos = j7;
                    return j2;
                }
            }
            return f();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(readRawVarint64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readString() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 <= 0 || rawVarint32 > remaining()) {
                if (rawVarint32 == 0) {
                    return "";
                }
                if (rawVarint32 < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = new byte[rawVarint32];
            long j2 = rawVarint32;
            UnsafeUtil.k(this.pos, bArr, 0L, j2);
            String str = new String(bArr, Internal.f3944a);
            this.pos += j2;
            return str;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0 && rawVarint32 <= remaining()) {
                String strG = Utf8.g(this.buffer, bufferPos(this.pos), rawVarint32);
                this.pos += rawVarint32;
                return strG;
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            int rawVarint32 = readRawVarint32();
            this.lastTag = rawVarint32;
            if (WireFormat.getTagFieldNumber(rawVarint32) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException {
            readGroup(i2, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.startPos = this.pos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            }
            if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            }
            if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            }
            if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.a(WireFormat.getTagFieldNumber(i2), 4));
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            skipRawBytes(4);
            return true;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipRawBytes(int i2) throws IOException {
            if (i2 >= 0 && i2 <= remaining()) {
                this.pos += i2;
            } else {
                if (i2 >= 0) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                throw InvalidProtocolBufferException.negativeSize();
            }
        }

        private UnsafeDirectNioDecoder(ByteBuffer byteBuffer, boolean z2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = byteBuffer;
            long jI = UnsafeUtil.i(byteBuffer);
            this.address = jI;
            this.limit = byteBuffer.limit() + jI;
            long jPosition = jI + byteBuffer.position();
            this.pos = jPosition;
            this.startPos = jPosition;
            this.immutable = z2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, codedOutputStream));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.f3899a;
            if (i3 < this.f3900b) {
                this.f3899a = i3 + 1;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i2, 4));
                this.f3899a--;
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.f3899a < this.f3900b) {
                int iPushLimit = pushLimit(rawVarint32);
                this.f3899a++;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.f3899a--;
                popLimit(iPushLimit);
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                long int64 = readInt64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeUInt64NoTag(int64);
                return true;
            }
            if (tagWireType == 1) {
                long rawLittleEndian64 = readRawLittleEndian64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed64NoTag(rawLittleEndian64);
                return true;
            }
            if (tagWireType == 2) {
                ByteString bytes = readBytes();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeBytesNoTag(bytes);
                return true;
            }
            if (tagWireType == 3) {
                codedOutputStream.writeRawVarint32(i2);
                skipMessage(codedOutputStream);
                int iA = WireFormat.a(WireFormat.getTagFieldNumber(i2), 4);
                checkLastTagWas(iA);
                codedOutputStream.writeRawVarint32(iA);
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType == 5) {
                int rawLittleEndian32 = readRawLittleEndian32();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed32NoTag(rawLittleEndian32);
                return true;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    static CodedInputStream a(Iterable iterable, boolean z2) {
        Iterator it = iterable.iterator();
        int i2 = 0;
        int iRemaining = 0;
        while (it.hasNext()) {
            ByteBuffer byteBuffer = (ByteBuffer) it.next();
            iRemaining += byteBuffer.remaining();
            i2 = byteBuffer.hasArray() ? i2 | 1 : byteBuffer.isDirect() ? i2 | 2 : i2 | 4;
        }
        return i2 == 2 ? new IterableDirectByteBufferDecoder(iterable, iRemaining, z2) : newInstance(new IterableByteBufferInputStream(iterable));
    }

    static CodedInputStream b(ByteBuffer byteBuffer, boolean z2) {
        if (byteBuffer.hasArray()) {
            return c(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining(), z2);
        }
        if (byteBuffer.isDirect() && UnsafeDirectNioDecoder.e()) {
            return new UnsafeDirectNioDecoder(byteBuffer, z2);
        }
        int iRemaining = byteBuffer.remaining();
        byte[] bArr = new byte[iRemaining];
        byteBuffer.duplicate().get(bArr);
        return c(bArr, 0, iRemaining, true);
    }

    static CodedInputStream c(byte[] bArr, int i2, int i3, boolean z2) {
        ArrayDecoder arrayDecoder = new ArrayDecoder(bArr, i2, i3, z2);
        try {
            arrayDecoder.pushLimit(i3);
            return arrayDecoder;
        } catch (InvalidProtocolBufferException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static int decodeZigZag32(int i2) {
        return (-(i2 & 1)) ^ (i2 >>> 1);
    }

    public static long decodeZigZag64(long j2) {
        return (-(j2 & 1)) ^ (j2 >>> 1);
    }

    public static CodedInputStream newInstance(InputStream inputStream) {
        return newInstance(inputStream, 4096);
    }

    public static int readRawVarint32(int i2, InputStream inputStream) throws IOException {
        if ((i2 & 128) == 0) {
            return i2;
        }
        int i3 = i2 & 127;
        int i4 = 7;
        while (i4 < 32) {
            int i5 = inputStream.read();
            if (i5 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            i3 |= (i5 & 127) << i4;
            if ((i5 & 128) == 0) {
                return i3;
            }
            i4 += 7;
        }
        while (i4 < 64) {
            int i6 = inputStream.read();
            if (i6 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if ((i6 & 128) == 0) {
                return i3;
            }
            i4 += 7;
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public abstract void checkLastTagWas(int i2) throws InvalidProtocolBufferException;

    final boolean d() {
        return this.shouldDiscardUnknownFields;
    }

    public abstract void enableAliasing(boolean z2);

    public abstract int getBytesUntilLimit();

    public abstract int getLastTag();

    public abstract int getTotalBytesRead();

    public abstract boolean isAtEnd() throws IOException;

    public abstract void popLimit(int i2);

    public abstract int pushLimit(int i2) throws InvalidProtocolBufferException;

    public abstract boolean readBool() throws IOException;

    public abstract byte[] readByteArray() throws IOException;

    public abstract ByteBuffer readByteBuffer() throws IOException;

    public abstract ByteString readBytes() throws IOException;

    public abstract double readDouble() throws IOException;

    public abstract int readEnum() throws IOException;

    public abstract int readFixed32() throws IOException;

    public abstract long readFixed64() throws IOException;

    public abstract float readFloat() throws IOException;

    public abstract <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract int readInt32() throws IOException;

    public abstract long readInt64() throws IOException;

    public abstract <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract byte readRawByte() throws IOException;

    public abstract byte[] readRawBytes(int i2) throws IOException;

    public abstract int readRawLittleEndian32() throws IOException;

    public abstract long readRawLittleEndian64() throws IOException;

    public abstract int readRawVarint32() throws IOException;

    public abstract long readRawVarint64() throws IOException;

    public abstract int readSFixed32() throws IOException;

    public abstract long readSFixed64() throws IOException;

    public abstract int readSInt32() throws IOException;

    public abstract long readSInt64() throws IOException;

    public abstract String readString() throws IOException;

    public abstract String readStringRequireUtf8() throws IOException;

    public abstract int readTag() throws IOException;

    public abstract int readUInt32() throws IOException;

    public abstract long readUInt64() throws IOException;

    @Deprecated
    public abstract void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException;

    public abstract void resetSizeCounter();

    public final int setRecursionLimit(int i2) {
        if (i2 >= 0) {
            int i3 = this.f3900b;
            this.f3900b = i2;
            return i3;
        }
        throw new IllegalArgumentException("Recursion limit cannot be negative: " + i2);
    }

    public final int setSizeLimit(int i2) {
        if (i2 >= 0) {
            int i3 = this.f3901c;
            this.f3901c = i2;
            return i3;
        }
        throw new IllegalArgumentException("Size limit cannot be negative: " + i2);
    }

    public abstract boolean skipField(int i2) throws IOException;

    @Deprecated
    public abstract boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException;

    public abstract void skipMessage() throws IOException;

    public abstract void skipMessage(CodedOutputStream codedOutputStream) throws IOException;

    public abstract void skipRawBytes(int i2) throws IOException;

    private CodedInputStream() {
        this.f3900b = 100;
        this.f3901c = Integer.MAX_VALUE;
        this.shouldDiscardUnknownFields = false;
    }

    public static CodedInputStream newInstance(InputStream inputStream, int i2) {
        if (i2 > 0) {
            return inputStream == null ? newInstance(Internal.EMPTY_BYTE_ARRAY) : new StreamDecoder(inputStream, i2);
        }
        throw new IllegalArgumentException("bufferSize must be > 0");
    }

    public static CodedInputStream newInstance(Iterable<ByteBuffer> iterable) {
        if (!UnsafeDirectNioDecoder.e()) {
            return newInstance(new IterableByteBufferInputStream(iterable));
        }
        return a(iterable, false);
    }

    public static CodedInputStream newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public static CodedInputStream newInstance(byte[] bArr, int i2, int i3) {
        return c(bArr, i2, i3, false);
    }

    public static CodedInputStream newInstance(ByteBuffer byteBuffer) {
        return b(byteBuffer, false);
    }
}
