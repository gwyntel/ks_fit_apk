package androidx.emoji2.text.flatbuffer;

import androidx.emoji2.text.flatbuffer.FlexBuffers;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/* loaded from: classes.dex */
public class FlexBuffersBuilder {
    public static final int BUILDER_FLAG_NONE = 0;
    public static final int BUILDER_FLAG_SHARE_ALL = 7;
    public static final int BUILDER_FLAG_SHARE_KEYS = 1;
    public static final int BUILDER_FLAG_SHARE_KEYS_AND_STRINGS = 3;
    public static final int BUILDER_FLAG_SHARE_KEY_VECTORS = 4;
    public static final int BUILDER_FLAG_SHARE_STRINGS = 2;
    private static final int WIDTH_16 = 1;
    private static final int WIDTH_32 = 2;
    private static final int WIDTH_64 = 3;
    private static final int WIDTH_8 = 0;
    private final ReadWriteBuf bb;
    private boolean finished;
    private final int flags;
    private Comparator<Value> keyComparator;
    private final HashMap<String, Integer> keyPool;
    private final ArrayList<Value> stack;
    private final HashMap<String, Integer> stringPool;

    private static class Value {

        /* renamed from: a, reason: collision with root package name */
        final int f4062a;

        /* renamed from: b, reason: collision with root package name */
        final int f4063b;

        /* renamed from: c, reason: collision with root package name */
        final double f4064c;

        /* renamed from: d, reason: collision with root package name */
        long f4065d;

        /* renamed from: e, reason: collision with root package name */
        int f4066e;

        Value(int i2, int i3, int i4, long j2) {
            this.f4066e = i2;
            this.f4062a = i3;
            this.f4063b = i4;
            this.f4065d = j2;
            this.f4064c = Double.MIN_VALUE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int elemWidth(int i2, int i3) {
            return elemWidth(this.f4062a, this.f4063b, this.f4065d, i2, i3);
        }

        static Value f(int i2, int i3, int i4, int i5) {
            return new Value(i2, i4, i5, i3);
        }

        static Value g(int i2, boolean z2) {
            return new Value(i2, 26, 0, z2 ? 1L : 0L);
        }

        static Value h(int i2, float f2) {
            return new Value(i2, 3, 2, f2);
        }

        static Value i(int i2, double d2) {
            return new Value(i2, 3, 3, d2);
        }

        static Value j(int i2, int i3) {
            return new Value(i2, 1, 1, i3);
        }

        static Value k(int i2, int i3) {
            return new Value(i2, 1, 2, i3);
        }

        static Value l(int i2, long j2) {
            return new Value(i2, 1, 3, j2);
        }

        static Value m(int i2, int i3) {
            return new Value(i2, 1, 0, i3);
        }

        static Value n(int i2, int i3) {
            return new Value(i2, 2, 1, i3);
        }

        static Value o(int i2, int i3) {
            return new Value(i2, 2, 2, i3);
        }

        static Value p(int i2, long j2) {
            return new Value(i2, 2, 3, j2);
        }

        private static byte packedType(int i2, int i3) {
            return (byte) (i2 | (i3 << 2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int paddingBytes(int i2, int i3) {
            return ((~i2) + 1) & (i3 - 1);
        }

        static Value q(int i2, int i3) {
            return new Value(i2, 2, 0, i3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte storedPackedType() {
            return storedPackedType(0);
        }

        private int storedWidth(int i2) {
            return FlexBuffers.g(this.f4062a) ? Math.max(this.f4063b, i2) : this.f4063b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int elemWidth(int i2, int i3, long j2, int i4, int i5) {
            if (FlexBuffers.g(i2)) {
                return i3;
            }
            for (int i6 = 1; i6 <= 32; i6 *= 2) {
                int iB = FlexBuffersBuilder.b((int) (((paddingBytes(i4, i6) + i4) + (i5 * i6)) - j2));
                if ((1 << iB) == i6) {
                    return iB;
                }
            }
            return 3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte storedPackedType(int i2) {
            return packedType(storedWidth(i2), this.f4062a);
        }

        Value(int i2, int i3, int i4, double d2) {
            this.f4066e = i2;
            this.f4062a = i3;
            this.f4063b = i4;
            this.f4064c = d2;
            this.f4065d = Long.MIN_VALUE;
        }
    }

    public FlexBuffersBuilder(int i2) {
        this(new ArrayReadWriteBuf(i2), 1);
    }

    private int align(int i2) {
        int i3 = 1 << i2;
        int iPaddingBytes = Value.paddingBytes(this.bb.writePosition(), i3);
        while (true) {
            int i4 = iPaddingBytes - 1;
            if (iPaddingBytes == 0) {
                return i3;
            }
            this.bb.put((byte) 0);
            iPaddingBytes = i4;
        }
    }

    static int b(long j2) {
        if (j2 <= FlexBuffers.Unsigned.a((byte) -1)) {
            return 0;
        }
        if (j2 <= FlexBuffers.Unsigned.c((short) -1)) {
            return 1;
        }
        return j2 <= FlexBuffers.Unsigned.b(-1) ? 2 : 3;
    }

    private Value createKeyVector(int i2, int i3) {
        long j2 = i3;
        int iMax = Math.max(0, b(j2));
        int i4 = i2;
        while (i4 < this.stack.size()) {
            i4++;
            iMax = Math.max(iMax, Value.elemWidth(4, 0, this.stack.get(i4).f4066e, this.bb.writePosition(), i4));
        }
        int iAlign = align(iMax);
        writeInt(j2, iAlign);
        int iWritePosition = this.bb.writePosition();
        while (i2 < this.stack.size()) {
            int i5 = this.stack.get(i2).f4066e;
            writeOffset(this.stack.get(i2).f4066e, iAlign);
            i2++;
        }
        return new Value(-1, FlexBuffers.j(4, 0), iMax, iWritePosition);
    }

    private Value createVector(int i2, int i3, int i4, boolean z2, boolean z3, Value value) {
        int i5;
        int iJ;
        int i6 = i4;
        long j2 = i6;
        int iMax = Math.max(0, b(j2));
        if (value != null) {
            iMax = Math.max(iMax, value.elemWidth(this.bb.writePosition(), 0));
            i5 = 3;
        } else {
            i5 = 1;
        }
        int i7 = 4;
        int iMax2 = iMax;
        for (int i8 = i3; i8 < this.stack.size(); i8++) {
            iMax2 = Math.max(iMax2, this.stack.get(i8).elemWidth(this.bb.writePosition(), i8 + i5));
            if (z2 && i8 == i3) {
                i7 = this.stack.get(i8).f4062a;
                if (!FlexBuffers.i(i7)) {
                    throw new FlexBuffers.FlexBufferException("TypedVector does not support this element type");
                }
            }
        }
        int i9 = i3;
        int iAlign = align(iMax2);
        if (value != null) {
            writeOffset(value.f4065d, iAlign);
            writeInt(1 << value.f4063b, iAlign);
        }
        if (!z3) {
            writeInt(j2, iAlign);
        }
        int iWritePosition = this.bb.writePosition();
        for (int i10 = i9; i10 < this.stack.size(); i10++) {
            writeAny(this.stack.get(i10), iAlign);
        }
        if (!z2) {
            while (i9 < this.stack.size()) {
                this.bb.put(this.stack.get(i9).storedPackedType(iMax2));
                i9++;
            }
        }
        if (value != null) {
            iJ = 9;
        } else if (z2) {
            if (!z3) {
                i6 = 0;
            }
            iJ = FlexBuffers.j(i7, i6);
        } else {
            iJ = 10;
        }
        return new Value(i2, iJ, iMax2, iWritePosition);
    }

    private int putKey(String str) {
        if (str == null) {
            return -1;
        }
        int iWritePosition = this.bb.writePosition();
        if ((this.flags & 1) == 0) {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            this.bb.put(bytes, 0, bytes.length);
            this.bb.put((byte) 0);
            this.keyPool.put(str, Integer.valueOf(iWritePosition));
            return iWritePosition;
        }
        Integer num = this.keyPool.get(str);
        if (num != null) {
            return num.intValue();
        }
        byte[] bytes2 = str.getBytes(StandardCharsets.UTF_8);
        this.bb.put(bytes2, 0, bytes2.length);
        this.bb.put((byte) 0);
        this.keyPool.put(str, Integer.valueOf(iWritePosition));
        return iWritePosition;
    }

    private void writeAny(Value value, int i2) {
        int i3 = value.f4062a;
        if (i3 != 0 && i3 != 1 && i3 != 2) {
            if (i3 == 3) {
                writeDouble(value.f4064c, i2);
                return;
            } else if (i3 != 26) {
                writeOffset(value.f4065d, i2);
                return;
            }
        }
        writeInt(value.f4065d, i2);
    }

    private Value writeBlob(int i2, byte[] bArr, int i3, boolean z2) {
        int iB = b(bArr.length);
        writeInt(bArr.length, align(iB));
        int iWritePosition = this.bb.writePosition();
        this.bb.put(bArr, 0, bArr.length);
        if (z2) {
            this.bb.put((byte) 0);
        }
        return Value.f(i2, iWritePosition, i3, iB);
    }

    private void writeDouble(double d2, int i2) {
        if (i2 == 4) {
            this.bb.putFloat((float) d2);
        } else if (i2 == 8) {
            this.bb.putDouble(d2);
        }
    }

    private void writeInt(long j2, int i2) {
        if (i2 == 1) {
            this.bb.put((byte) j2);
            return;
        }
        if (i2 == 2) {
            this.bb.putShort((short) j2);
        } else if (i2 == 4) {
            this.bb.putInt((int) j2);
        } else {
            if (i2 != 8) {
                return;
            }
            this.bb.putLong(j2);
        }
    }

    private void writeOffset(long j2, int i2) {
        writeInt((int) (this.bb.writePosition() - j2), i2);
    }

    private Value writeString(int i2, String str) {
        return writeBlob(i2, str.getBytes(StandardCharsets.UTF_8), 5, true);
    }

    public int endMap(String str, int i2) {
        int iPutKey = putKey(str);
        ArrayList<Value> arrayList = this.stack;
        Collections.sort(arrayList.subList(i2, arrayList.size()), this.keyComparator);
        Value valueCreateVector = createVector(iPutKey, i2, this.stack.size() - i2, false, false, createKeyVector(i2, this.stack.size() - i2));
        while (this.stack.size() > i2) {
            this.stack.remove(r0.size() - 1);
        }
        this.stack.add(valueCreateVector);
        return (int) valueCreateVector.f4065d;
    }

    public int endVector(String str, int i2, boolean z2, boolean z3) {
        Value valueCreateVector = createVector(putKey(str), i2, this.stack.size() - i2, z2, z3, null);
        while (this.stack.size() > i2) {
            this.stack.remove(r10.size() - 1);
        }
        this.stack.add(valueCreateVector);
        return (int) valueCreateVector.f4065d;
    }

    public ByteBuffer finish() {
        int iAlign = align(this.stack.get(0).elemWidth(this.bb.writePosition(), 0));
        writeAny(this.stack.get(0), iAlign);
        this.bb.put(this.stack.get(0).storedPackedType());
        this.bb.put((byte) iAlign);
        this.finished = true;
        return ByteBuffer.wrap(this.bb.data(), 0, this.bb.writePosition());
    }

    public ReadWriteBuf getBuffer() {
        return this.bb;
    }

    public int putBlob(byte[] bArr) {
        return putBlob(null, bArr);
    }

    public void putBoolean(boolean z2) {
        putBoolean(null, z2);
    }

    public void putFloat(float f2) {
        putFloat((String) null, f2);
    }

    public void putInt(int i2) {
        putInt((String) null, i2);
    }

    public int putString(String str) {
        return putString(null, str);
    }

    public void putUInt(int i2) {
        putUInt(null, i2);
    }

    public void putUInt64(BigInteger bigInteger) {
        putUInt64(null, bigInteger.longValue());
    }

    public int startMap() {
        return this.stack.size();
    }

    public int startVector() {
        return this.stack.size();
    }

    public FlexBuffersBuilder() {
        this(256);
    }

    private void putUInt64(String str, long j2) {
        this.stack.add(Value.p(putKey(str), j2));
    }

    public int putBlob(String str, byte[] bArr) {
        Value valueWriteBlob = writeBlob(putKey(str), bArr, 25, false);
        this.stack.add(valueWriteBlob);
        return (int) valueWriteBlob.f4065d;
    }

    public void putBoolean(String str, boolean z2) {
        this.stack.add(Value.g(putKey(str), z2));
    }

    public void putFloat(String str, float f2) {
        this.stack.add(Value.h(putKey(str), f2));
    }

    public void putInt(String str, int i2) {
        putInt(str, i2);
    }

    public int putString(String str, String str2) {
        int iPutKey = putKey(str);
        if ((this.flags & 2) == 0) {
            Value valueWriteString = writeString(iPutKey, str2);
            this.stack.add(valueWriteString);
            return (int) valueWriteString.f4065d;
        }
        Integer num = this.stringPool.get(str2);
        if (num != null) {
            this.stack.add(Value.f(iPutKey, num.intValue(), 5, b(str2.length())));
            return num.intValue();
        }
        Value valueWriteString2 = writeString(iPutKey, str2);
        this.stringPool.put(str2, Integer.valueOf((int) valueWriteString2.f4065d));
        this.stack.add(valueWriteString2);
        return (int) valueWriteString2.f4065d;
    }

    public void putUInt(long j2) {
        putUInt(null, j2);
    }

    @Deprecated
    public FlexBuffersBuilder(ByteBuffer byteBuffer, int i2) {
        this(new ArrayReadWriteBuf(byteBuffer.array()), i2);
    }

    private void putUInt(String str, long j2) {
        Value valueP;
        int iPutKey = putKey(str);
        int iB = b(j2);
        if (iB == 0) {
            valueP = Value.q(iPutKey, (int) j2);
        } else if (iB == 1) {
            valueP = Value.n(iPutKey, (int) j2);
        } else if (iB == 2) {
            valueP = Value.o(iPutKey, (int) j2);
        } else {
            valueP = Value.p(iPutKey, j2);
        }
        this.stack.add(valueP);
    }

    public void putFloat(double d2) {
        putFloat((String) null, d2);
    }

    public void putInt(String str, long j2) {
        int iPutKey = putKey(str);
        if (-128 <= j2 && j2 <= 127) {
            this.stack.add(Value.m(iPutKey, (int) j2));
            return;
        }
        if (-32768 <= j2 && j2 <= 32767) {
            this.stack.add(Value.j(iPutKey, (int) j2));
        } else if (-2147483648L <= j2 && j2 <= 2147483647L) {
            this.stack.add(Value.k(iPutKey, (int) j2));
        } else {
            this.stack.add(Value.l(iPutKey, j2));
        }
    }

    public FlexBuffersBuilder(ReadWriteBuf readWriteBuf, int i2) {
        this.stack = new ArrayList<>();
        this.keyPool = new HashMap<>();
        this.stringPool = new HashMap<>();
        this.finished = false;
        this.keyComparator = new Comparator<Value>() { // from class: androidx.emoji2.text.flatbuffer.FlexBuffersBuilder.1
            @Override // java.util.Comparator
            public int compare(Value value, Value value2) {
                byte b2;
                byte b3;
                int i3 = value.f4066e;
                int i4 = value2.f4066e;
                do {
                    b2 = FlexBuffersBuilder.this.bb.get(i3);
                    b3 = FlexBuffersBuilder.this.bb.get(i4);
                    if (b2 == 0) {
                        return b2 - b3;
                    }
                    i3++;
                    i4++;
                } while (b2 == b3);
                return b2 - b3;
            }
        };
        this.bb = readWriteBuf;
        this.flags = i2;
    }

    public void putFloat(String str, double d2) {
        this.stack.add(Value.i(putKey(str), d2));
    }

    public void putInt(long j2) {
        putInt((String) null, j2);
    }

    public FlexBuffersBuilder(ByteBuffer byteBuffer) {
        this(byteBuffer, 1);
    }
}
