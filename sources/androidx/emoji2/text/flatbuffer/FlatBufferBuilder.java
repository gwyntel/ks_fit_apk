package androidx.emoji2.text.flatbuffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* loaded from: classes.dex */
public class FlatBufferBuilder {

    /* renamed from: a, reason: collision with root package name */
    ByteBuffer f4041a;

    /* renamed from: b, reason: collision with root package name */
    int f4042b;

    /* renamed from: c, reason: collision with root package name */
    int f4043c;

    /* renamed from: d, reason: collision with root package name */
    int[] f4044d;

    /* renamed from: e, reason: collision with root package name */
    int f4045e;

    /* renamed from: f, reason: collision with root package name */
    boolean f4046f;

    /* renamed from: g, reason: collision with root package name */
    boolean f4047g;

    /* renamed from: h, reason: collision with root package name */
    int f4048h;

    /* renamed from: i, reason: collision with root package name */
    int[] f4049i;

    /* renamed from: j, reason: collision with root package name */
    int f4050j;

    /* renamed from: k, reason: collision with root package name */
    int f4051k;

    /* renamed from: l, reason: collision with root package name */
    boolean f4052l;

    /* renamed from: m, reason: collision with root package name */
    ByteBufferFactory f4053m;

    /* renamed from: n, reason: collision with root package name */
    final Utf8 f4054n;

    static class ByteBufferBackedInputStream extends InputStream {

        /* renamed from: a, reason: collision with root package name */
        ByteBuffer f4055a;

        public ByteBufferBackedInputStream(ByteBuffer byteBuffer) {
            this.f4055a = byteBuffer;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            try {
                return this.f4055a.get() & 255;
            } catch (BufferUnderflowException unused) {
                return -1;
            }
        }
    }

    public static abstract class ByteBufferFactory {
        public abstract ByteBuffer newByteBuffer(int i2);

        public void releaseByteBuffer(ByteBuffer byteBuffer) {
        }
    }

    public static final class HeapByteBufferFactory extends ByteBufferFactory {
        public static final HeapByteBufferFactory INSTANCE = new HeapByteBufferFactory();

        @Override // androidx.emoji2.text.flatbuffer.FlatBufferBuilder.ByteBufferFactory
        public ByteBuffer newByteBuffer(int i2) {
            return ByteBuffer.allocate(i2).order(ByteOrder.LITTLE_ENDIAN);
        }
    }

    public FlatBufferBuilder(int i2, ByteBufferFactory byteBufferFactory) {
        this(i2, byteBufferFactory, null, Utf8.getDefault());
    }

    static ByteBuffer c(ByteBuffer byteBuffer, ByteBufferFactory byteBufferFactory) {
        int iCapacity = byteBuffer.capacity();
        if (((-1073741824) & iCapacity) != 0) {
            throw new AssertionError("FlatBuffers: cannot grow buffer beyond 2 gigabytes.");
        }
        int i2 = iCapacity == 0 ? 1 : iCapacity << 1;
        byteBuffer.position(0);
        ByteBuffer byteBufferNewByteBuffer = byteBufferFactory.newByteBuffer(i2);
        byteBufferNewByteBuffer.position(byteBufferNewByteBuffer.clear().capacity() - iCapacity);
        byteBufferNewByteBuffer.put(byteBuffer);
        return byteBufferNewByteBuffer;
    }

    @Deprecated
    private int dataStart() {
        finished();
        return this.f4042b;
    }

    public static boolean isFieldPresent(Table table, int i2) {
        return table.c(i2) != 0;
    }

    public void Nested(int i2) {
        if (i2 != offset()) {
            throw new AssertionError("FlatBuffers: struct must be serialized inline.");
        }
    }

    protected void a(int i2, String str, boolean z2) {
        prep(this.f4043c, (z2 ? 4 : 0) + 8);
        if (str.length() != 4) {
            throw new AssertionError("FlatBuffers: file identifier must be length 4");
        }
        for (int i3 = 3; i3 >= 0; i3--) {
            addByte((byte) str.charAt(i3));
        }
        b(i2, z2);
    }

    public void addBoolean(boolean z2) {
        prep(1, 0);
        putBoolean(z2);
    }

    public void addByte(byte b2) {
        prep(1, 0);
        putByte(b2);
    }

    public void addDouble(double d2) {
        prep(8, 0);
        putDouble(d2);
    }

    public void addFloat(float f2) {
        prep(4, 0);
        putFloat(f2);
    }

    public void addInt(int i2) {
        prep(4, 0);
        putInt(i2);
    }

    public void addLong(long j2) {
        prep(8, 0);
        putLong(j2);
    }

    public void addOffset(int i2) {
        prep(4, 0);
        putInt((offset() - i2) + 4);
    }

    public void addShort(short s2) {
        prep(2, 0);
        putShort(s2);
    }

    public void addStruct(int i2, int i3, int i4) {
        if (i3 != i4) {
            Nested(i3);
            slot(i2);
        }
    }

    protected void b(int i2, boolean z2) {
        prep(this.f4043c, (z2 ? 4 : 0) + 4);
        addOffset(i2);
        if (z2) {
            addInt(this.f4041a.capacity() - this.f4042b);
        }
        this.f4041a.position(this.f4042b);
        this.f4047g = true;
    }

    public void clear() {
        this.f4042b = this.f4041a.capacity();
        this.f4041a.clear();
        this.f4043c = 1;
        while (true) {
            int i2 = this.f4045e;
            if (i2 <= 0) {
                this.f4045e = 0;
                this.f4046f = false;
                this.f4047g = false;
                this.f4048h = 0;
                this.f4050j = 0;
                this.f4051k = 0;
                return;
            }
            int[] iArr = this.f4044d;
            int i3 = i2 - 1;
            this.f4045e = i3;
            iArr[i3] = 0;
        }
    }

    public int createByteVector(byte[] bArr) {
        int length = bArr.length;
        startVector(1, length, 1);
        ByteBuffer byteBuffer = this.f4041a;
        int i2 = this.f4042b - length;
        this.f4042b = i2;
        byteBuffer.position(i2);
        this.f4041a.put(bArr);
        return endVector();
    }

    public <T extends Table> int createSortedVectorOfTables(T t2, int[] iArr) {
        t2.m(iArr, this.f4041a);
        return createVectorOfTables(iArr);
    }

    public int createString(CharSequence charSequence) {
        int iEncodedLength = this.f4054n.encodedLength(charSequence);
        addByte((byte) 0);
        startVector(1, iEncodedLength, 1);
        ByteBuffer byteBuffer = this.f4041a;
        int i2 = this.f4042b - iEncodedLength;
        this.f4042b = i2;
        byteBuffer.position(i2);
        this.f4054n.encodeUtf8(charSequence, this.f4041a);
        return endVector();
    }

    public ByteBuffer createUnintializedVector(int i2, int i3, int i4) {
        int i5 = i2 * i3;
        startVector(i2, i3, i4);
        ByteBuffer byteBuffer = this.f4041a;
        int i6 = this.f4042b - i5;
        this.f4042b = i6;
        byteBuffer.position(i6);
        ByteBuffer byteBufferOrder = this.f4041a.slice().order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.limit(i5);
        return byteBufferOrder;
    }

    public int createVectorOfTables(int[] iArr) {
        notNested();
        startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            addOffset(iArr[length]);
        }
        return endVector();
    }

    public ByteBuffer dataBuffer() {
        finished();
        return this.f4041a;
    }

    public int endTable() {
        int i2;
        if (this.f4044d == null || !this.f4046f) {
            throw new AssertionError("FlatBuffers: endTable called without startTable");
        }
        addInt(0);
        int iOffset = offset();
        int i3 = this.f4045e - 1;
        while (i3 >= 0 && this.f4044d[i3] == 0) {
            i3--;
        }
        for (int i4 = i3; i4 >= 0; i4--) {
            int i5 = this.f4044d[i4];
            addShort((short) (i5 != 0 ? iOffset - i5 : 0));
        }
        addShort((short) (iOffset - this.f4048h));
        addShort((short) ((i3 + 3) * 2));
        int i6 = 0;
        loop2: while (true) {
            if (i6 >= this.f4050j) {
                i2 = 0;
                break;
            }
            int iCapacity = this.f4041a.capacity() - this.f4049i[i6];
            int i7 = this.f4042b;
            short s2 = this.f4041a.getShort(iCapacity);
            if (s2 == this.f4041a.getShort(i7)) {
                for (int i8 = 2; i8 < s2; i8 += 2) {
                    if (this.f4041a.getShort(iCapacity + i8) != this.f4041a.getShort(i7 + i8)) {
                        break;
                    }
                }
                i2 = this.f4049i[i6];
                break loop2;
            }
            i6++;
        }
        if (i2 != 0) {
            int iCapacity2 = this.f4041a.capacity() - iOffset;
            this.f4042b = iCapacity2;
            this.f4041a.putInt(iCapacity2, i2 - iOffset);
        } else {
            int i9 = this.f4050j;
            int[] iArr = this.f4049i;
            if (i9 == iArr.length) {
                this.f4049i = Arrays.copyOf(iArr, i9 * 2);
            }
            int[] iArr2 = this.f4049i;
            int i10 = this.f4050j;
            this.f4050j = i10 + 1;
            iArr2[i10] = offset();
            ByteBuffer byteBuffer = this.f4041a;
            byteBuffer.putInt(byteBuffer.capacity() - iOffset, offset() - iOffset);
        }
        this.f4046f = false;
        return iOffset;
    }

    public int endVector() {
        if (!this.f4046f) {
            throw new AssertionError("FlatBuffers: endVector called without startVector");
        }
        this.f4046f = false;
        putInt(this.f4051k);
        return offset();
    }

    public void finish(int i2) {
        b(i2, false);
    }

    public void finishSizePrefixed(int i2) {
        b(i2, true);
    }

    public void finished() {
        if (!this.f4047g) {
            throw new AssertionError("FlatBuffers: you can only access the serialized buffer after it has been finished by FlatBufferBuilder.finish().");
        }
    }

    public FlatBufferBuilder forceDefaults(boolean z2) {
        this.f4052l = z2;
        return this;
    }

    public FlatBufferBuilder init(ByteBuffer byteBuffer, ByteBufferFactory byteBufferFactory) {
        this.f4053m = byteBufferFactory;
        this.f4041a = byteBuffer;
        byteBuffer.clear();
        this.f4041a.order(ByteOrder.LITTLE_ENDIAN);
        this.f4043c = 1;
        this.f4042b = this.f4041a.capacity();
        this.f4045e = 0;
        this.f4046f = false;
        this.f4047g = false;
        this.f4048h = 0;
        this.f4050j = 0;
        this.f4051k = 0;
        return this;
    }

    public void notNested() {
        if (this.f4046f) {
            throw new AssertionError("FlatBuffers: object serialization must not be nested.");
        }
    }

    public int offset() {
        return this.f4041a.capacity() - this.f4042b;
    }

    public void pad(int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            ByteBuffer byteBuffer = this.f4041a;
            int i4 = this.f4042b - 1;
            this.f4042b = i4;
            byteBuffer.put(i4, (byte) 0);
        }
    }

    public void prep(int i2, int i3) {
        if (i2 > this.f4043c) {
            this.f4043c = i2;
        }
        int i4 = ((~((this.f4041a.capacity() - this.f4042b) + i3)) + 1) & (i2 - 1);
        while (this.f4042b < i4 + i2 + i3) {
            int iCapacity = this.f4041a.capacity();
            ByteBuffer byteBuffer = this.f4041a;
            ByteBuffer byteBufferC = c(byteBuffer, this.f4053m);
            this.f4041a = byteBufferC;
            if (byteBuffer != byteBufferC) {
                this.f4053m.releaseByteBuffer(byteBuffer);
            }
            this.f4042b += this.f4041a.capacity() - iCapacity;
        }
        pad(i4);
    }

    public void putBoolean(boolean z2) {
        ByteBuffer byteBuffer = this.f4041a;
        int i2 = this.f4042b - 1;
        this.f4042b = i2;
        byteBuffer.put(i2, z2 ? (byte) 1 : (byte) 0);
    }

    public void putByte(byte b2) {
        ByteBuffer byteBuffer = this.f4041a;
        int i2 = this.f4042b - 1;
        this.f4042b = i2;
        byteBuffer.put(i2, b2);
    }

    public void putDouble(double d2) {
        ByteBuffer byteBuffer = this.f4041a;
        int i2 = this.f4042b - 8;
        this.f4042b = i2;
        byteBuffer.putDouble(i2, d2);
    }

    public void putFloat(float f2) {
        ByteBuffer byteBuffer = this.f4041a;
        int i2 = this.f4042b - 4;
        this.f4042b = i2;
        byteBuffer.putFloat(i2, f2);
    }

    public void putInt(int i2) {
        ByteBuffer byteBuffer = this.f4041a;
        int i3 = this.f4042b - 4;
        this.f4042b = i3;
        byteBuffer.putInt(i3, i2);
    }

    public void putLong(long j2) {
        ByteBuffer byteBuffer = this.f4041a;
        int i2 = this.f4042b - 8;
        this.f4042b = i2;
        byteBuffer.putLong(i2, j2);
    }

    public void putShort(short s2) {
        ByteBuffer byteBuffer = this.f4041a;
        int i2 = this.f4042b - 2;
        this.f4042b = i2;
        byteBuffer.putShort(i2, s2);
    }

    public void required(int i2, int i3) {
        int iCapacity = this.f4041a.capacity() - i2;
        if (this.f4041a.getShort((iCapacity - this.f4041a.getInt(iCapacity)) + i3) != 0) {
            return;
        }
        throw new AssertionError("FlatBuffers: field " + i3 + " must be set");
    }

    public byte[] sizedByteArray(int i2, int i3) {
        finished();
        byte[] bArr = new byte[i3];
        this.f4041a.position(i2);
        this.f4041a.get(bArr);
        return bArr;
    }

    public InputStream sizedInputStream() {
        finished();
        ByteBuffer byteBufferDuplicate = this.f4041a.duplicate();
        byteBufferDuplicate.position(this.f4042b);
        byteBufferDuplicate.limit(this.f4041a.capacity());
        return new ByteBufferBackedInputStream(byteBufferDuplicate);
    }

    public void slot(int i2) {
        this.f4044d[i2] = offset();
    }

    public void startTable(int i2) {
        notNested();
        int[] iArr = this.f4044d;
        if (iArr == null || iArr.length < i2) {
            this.f4044d = new int[i2];
        }
        this.f4045e = i2;
        Arrays.fill(this.f4044d, 0, i2, 0);
        this.f4046f = true;
        this.f4048h = offset();
    }

    public void startVector(int i2, int i3, int i4) {
        notNested();
        this.f4051k = i3;
        int i5 = i2 * i3;
        prep(4, i5);
        prep(i4, i5);
        this.f4046f = true;
    }

    public FlatBufferBuilder(int i2, ByteBufferFactory byteBufferFactory, ByteBuffer byteBuffer, Utf8 utf8) {
        this.f4043c = 1;
        this.f4044d = null;
        this.f4045e = 0;
        this.f4046f = false;
        this.f4047g = false;
        this.f4049i = new int[16];
        this.f4050j = 0;
        this.f4051k = 0;
        this.f4052l = false;
        i2 = i2 <= 0 ? 1 : i2;
        this.f4053m = byteBufferFactory;
        if (byteBuffer != null) {
            this.f4041a = byteBuffer;
            byteBuffer.clear();
            this.f4041a.order(ByteOrder.LITTLE_ENDIAN);
        } else {
            this.f4041a = byteBufferFactory.newByteBuffer(i2);
        }
        this.f4054n = utf8;
        this.f4042b = this.f4041a.capacity();
    }

    public void addBoolean(int i2, boolean z2, boolean z3) {
        if (this.f4052l || z2 != z3) {
            addBoolean(z2);
            slot(i2);
        }
    }

    public void addByte(int i2, byte b2, int i3) {
        if (this.f4052l || b2 != i3) {
            addByte(b2);
            slot(i2);
        }
    }

    public void addDouble(int i2, double d2, double d3) {
        if (this.f4052l || d2 != d3) {
            addDouble(d2);
            slot(i2);
        }
    }

    public void addFloat(int i2, float f2, double d2) {
        if (this.f4052l || f2 != d2) {
            addFloat(f2);
            slot(i2);
        }
    }

    public void addInt(int i2, int i3, int i4) {
        if (this.f4052l || i3 != i4) {
            addInt(i3);
            slot(i2);
        }
    }

    public void addLong(int i2, long j2, long j3) {
        if (this.f4052l || j2 != j3) {
            addLong(j2);
            slot(i2);
        }
    }

    public void addShort(int i2, short s2, int i3) {
        if (this.f4052l || s2 != i3) {
            addShort(s2);
            slot(i2);
        }
    }

    public void finish(int i2, String str) {
        a(i2, str, false);
    }

    public void finishSizePrefixed(int i2, String str) {
        a(i2, str, true);
    }

    public void addOffset(int i2, int i3, int i4) {
        if (this.f4052l || i3 != i4) {
            addOffset(i3);
            slot(i2);
        }
    }

    public byte[] sizedByteArray() {
        return sizedByteArray(this.f4042b, this.f4041a.capacity() - this.f4042b);
    }

    public int createByteVector(byte[] bArr, int i2, int i3) {
        startVector(1, i3, 1);
        ByteBuffer byteBuffer = this.f4041a;
        int i4 = this.f4042b - i3;
        this.f4042b = i4;
        byteBuffer.position(i4);
        this.f4041a.put(bArr, i2, i3);
        return endVector();
    }

    public int createString(ByteBuffer byteBuffer) {
        int iRemaining = byteBuffer.remaining();
        addByte((byte) 0);
        startVector(1, iRemaining, 1);
        ByteBuffer byteBuffer2 = this.f4041a;
        int i2 = this.f4042b - iRemaining;
        this.f4042b = i2;
        byteBuffer2.position(i2);
        this.f4041a.put(byteBuffer);
        return endVector();
    }

    public int createByteVector(ByteBuffer byteBuffer) {
        int iRemaining = byteBuffer.remaining();
        startVector(1, iRemaining, 1);
        ByteBuffer byteBuffer2 = this.f4041a;
        int i2 = this.f4042b - iRemaining;
        this.f4042b = i2;
        byteBuffer2.position(i2);
        this.f4041a.put(byteBuffer);
        return endVector();
    }

    public FlatBufferBuilder(int i2) {
        this(i2, HeapByteBufferFactory.INSTANCE, null, Utf8.getDefault());
    }

    public FlatBufferBuilder() {
        this(1024);
    }

    public FlatBufferBuilder(ByteBuffer byteBuffer, ByteBufferFactory byteBufferFactory) {
        this(byteBuffer.capacity(), byteBufferFactory, byteBuffer, Utf8.getDefault());
    }

    public FlatBufferBuilder(ByteBuffer byteBuffer) {
        this(byteBuffer, new HeapByteBufferFactory());
    }
}
