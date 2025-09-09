package io.flutter.plugin.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class StandardMessageCodec implements MessageCodec<Object> {
    private static final byte BIGINT = 5;
    private static final byte BYTE_ARRAY = 8;
    private static final byte DOUBLE = 6;
    private static final byte DOUBLE_ARRAY = 11;
    private static final byte FALSE = 2;
    private static final byte FLOAT_ARRAY = 14;
    public static final StandardMessageCodec INSTANCE = new StandardMessageCodec();
    private static final byte INT = 3;
    private static final byte INT_ARRAY = 9;
    private static final byte LIST = 12;
    private static final boolean LITTLE_ENDIAN;
    private static final byte LONG = 4;
    private static final byte LONG_ARRAY = 10;
    private static final byte MAP = 13;
    private static final byte NULL = 0;
    private static final byte STRING = 7;
    private static final String TAG = "StandardMessageCodec#";
    private static final byte TRUE = 1;
    private static final Charset UTF8;

    static final class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        ExposedByteArrayOutputStream() {
        }

        byte[] buffer() {
            return ((ByteArrayOutputStream) this).buf;
        }
    }

    static {
        LITTLE_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
        UTF8 = Charset.forName("UTF8");
    }

    protected static final void readAlignment(@NonNull ByteBuffer byteBuffer, int i2) {
        int iPosition = byteBuffer.position() % i2;
        if (iPosition != 0) {
            byteBuffer.position((byteBuffer.position() + i2) - iPosition);
        }
    }

    @NonNull
    protected static final byte[] readBytes(@NonNull ByteBuffer byteBuffer) {
        byte[] bArr = new byte[readSize(byteBuffer)];
        byteBuffer.get(bArr);
        return bArr;
    }

    protected static final int readSize(@NonNull ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining()) {
            throw new IllegalArgumentException("Message corrupted");
        }
        int i2 = byteBuffer.get() & 255;
        return i2 < 254 ? i2 : i2 == 254 ? byteBuffer.getChar() : byteBuffer.getInt();
    }

    protected static final void writeAlignment(@NonNull ByteArrayOutputStream byteArrayOutputStream, int i2) {
        int size = byteArrayOutputStream.size() % i2;
        if (size != 0) {
            for (int i3 = 0; i3 < i2 - size; i3++) {
                byteArrayOutputStream.write(0);
            }
        }
    }

    protected static final void writeBytes(@NonNull ByteArrayOutputStream byteArrayOutputStream, @NonNull byte[] bArr) {
        writeSize(byteArrayOutputStream, bArr.length);
        byteArrayOutputStream.write(bArr, 0, bArr.length);
    }

    protected static final void writeChar(@NonNull ByteArrayOutputStream byteArrayOutputStream, int i2) {
        if (LITTLE_ENDIAN) {
            byteArrayOutputStream.write(i2);
            byteArrayOutputStream.write(i2 >>> 8);
        } else {
            byteArrayOutputStream.write(i2 >>> 8);
            byteArrayOutputStream.write(i2);
        }
    }

    protected static final void writeDouble(@NonNull ByteArrayOutputStream byteArrayOutputStream, double d2) {
        writeLong(byteArrayOutputStream, Double.doubleToLongBits(d2));
    }

    protected static final void writeFloat(@NonNull ByteArrayOutputStream byteArrayOutputStream, float f2) {
        writeInt(byteArrayOutputStream, Float.floatToIntBits(f2));
    }

    protected static final void writeInt(@NonNull ByteArrayOutputStream byteArrayOutputStream, int i2) {
        if (LITTLE_ENDIAN) {
            byteArrayOutputStream.write(i2);
            byteArrayOutputStream.write(i2 >>> 8);
            byteArrayOutputStream.write(i2 >>> 16);
            byteArrayOutputStream.write(i2 >>> 24);
            return;
        }
        byteArrayOutputStream.write(i2 >>> 24);
        byteArrayOutputStream.write(i2 >>> 16);
        byteArrayOutputStream.write(i2 >>> 8);
        byteArrayOutputStream.write(i2);
    }

    protected static final void writeLong(@NonNull ByteArrayOutputStream byteArrayOutputStream, long j2) {
        if (LITTLE_ENDIAN) {
            byteArrayOutputStream.write((byte) j2);
            byteArrayOutputStream.write((byte) (j2 >>> 8));
            byteArrayOutputStream.write((byte) (j2 >>> 16));
            byteArrayOutputStream.write((byte) (j2 >>> 24));
            byteArrayOutputStream.write((byte) (j2 >>> 32));
            byteArrayOutputStream.write((byte) (j2 >>> 40));
            byteArrayOutputStream.write((byte) (j2 >>> 48));
            byteArrayOutputStream.write((byte) (j2 >>> 56));
            return;
        }
        byteArrayOutputStream.write((byte) (j2 >>> 56));
        byteArrayOutputStream.write((byte) (j2 >>> 48));
        byteArrayOutputStream.write((byte) (j2 >>> 40));
        byteArrayOutputStream.write((byte) (j2 >>> 32));
        byteArrayOutputStream.write((byte) (j2 >>> 24));
        byteArrayOutputStream.write((byte) (j2 >>> 16));
        byteArrayOutputStream.write((byte) (j2 >>> 8));
        byteArrayOutputStream.write((byte) j2);
    }

    protected static final void writeSize(@NonNull ByteArrayOutputStream byteArrayOutputStream, int i2) {
        if (i2 < 254) {
            byteArrayOutputStream.write(i2);
        } else if (i2 <= 65535) {
            byteArrayOutputStream.write(254);
            writeChar(byteArrayOutputStream, i2);
        } else {
            byteArrayOutputStream.write(255);
            writeInt(byteArrayOutputStream, i2);
        }
    }

    @Override // io.flutter.plugin.common.MessageCodec
    @Nullable
    public Object decodeMessage(@Nullable ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        byteBuffer.order(ByteOrder.nativeOrder());
        Object value = readValue(byteBuffer);
        if (byteBuffer.hasRemaining()) {
            throw new IllegalArgumentException("Message corrupted");
        }
        return value;
    }

    @Override // io.flutter.plugin.common.MessageCodec
    @Nullable
    public ByteBuffer encodeMessage(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        ExposedByteArrayOutputStream exposedByteArrayOutputStream = new ExposedByteArrayOutputStream();
        writeValue(exposedByteArrayOutputStream, obj);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(exposedByteArrayOutputStream.size());
        byteBufferAllocateDirect.put(exposedByteArrayOutputStream.buffer(), 0, exposedByteArrayOutputStream.size());
        return byteBufferAllocateDirect;
    }

    @NonNull
    protected final Object readValue(@NonNull ByteBuffer byteBuffer) {
        if (byteBuffer.hasRemaining()) {
            return readValueOfType(byteBuffer.get(), byteBuffer);
        }
        throw new IllegalArgumentException("Message corrupted");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.util.HashMap, java.util.Map] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.ArrayList, java.util.List] */
    /* JADX WARN: Type inference failed for: r1v9 */
    @Nullable
    protected Object readValueOfType(byte b2, @NonNull ByteBuffer byteBuffer) {
        Object arrayList;
        int i2 = 0;
        switch (b2) {
            case 0:
                return null;
            case 1:
                return Boolean.TRUE;
            case 2:
                return Boolean.FALSE;
            case 3:
                return Integer.valueOf(byteBuffer.getInt());
            case 4:
                return Long.valueOf(byteBuffer.getLong());
            case 5:
                return new BigInteger(new String(readBytes(byteBuffer), UTF8), 16);
            case 6:
                readAlignment(byteBuffer, 8);
                return Double.valueOf(byteBuffer.getDouble());
            case 7:
                return new String(readBytes(byteBuffer), UTF8);
            case 8:
                return readBytes(byteBuffer);
            case 9:
                int size = readSize(byteBuffer);
                int[] iArr = new int[size];
                readAlignment(byteBuffer, 4);
                byteBuffer.asIntBuffer().get(iArr);
                byteBuffer.position(byteBuffer.position() + (size * 4));
                return iArr;
            case 10:
                int size2 = readSize(byteBuffer);
                long[] jArr = new long[size2];
                readAlignment(byteBuffer, 8);
                byteBuffer.asLongBuffer().get(jArr);
                byteBuffer.position(byteBuffer.position() + (size2 * 8));
                return jArr;
            case 11:
                int size3 = readSize(byteBuffer);
                double[] dArr = new double[size3];
                readAlignment(byteBuffer, 8);
                byteBuffer.asDoubleBuffer().get(dArr);
                byteBuffer.position(byteBuffer.position() + (size3 * 8));
                return dArr;
            case 12:
                int size4 = readSize(byteBuffer);
                arrayList = new ArrayList(size4);
                while (i2 < size4) {
                    arrayList.add(readValue(byteBuffer));
                    i2++;
                }
                break;
            case 13:
                int size5 = readSize(byteBuffer);
                arrayList = new HashMap();
                while (i2 < size5) {
                    arrayList.put(readValue(byteBuffer), readValue(byteBuffer));
                    i2++;
                }
                break;
            case 14:
                int size6 = readSize(byteBuffer);
                float[] fArr = new float[size6];
                readAlignment(byteBuffer, 4);
                byteBuffer.asFloatBuffer().get(fArr);
                byteBuffer.position(byteBuffer.position() + (size6 * 4));
                return fArr;
            default:
                throw new IllegalArgumentException("Message corrupted");
        }
        return arrayList;
    }

    protected void writeValue(@NonNull ByteArrayOutputStream byteArrayOutputStream, @Nullable Object obj) {
        int i2 = 0;
        if (obj == null || obj.equals(null)) {
            byteArrayOutputStream.write(0);
            return;
        }
        if (obj instanceof Boolean) {
            byteArrayOutputStream.write(((Boolean) obj).booleanValue() ? 1 : 2);
            return;
        }
        if (obj instanceof Number) {
            if ((obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte)) {
                byteArrayOutputStream.write(3);
                writeInt(byteArrayOutputStream, ((Number) obj).intValue());
                return;
            }
            if (obj instanceof Long) {
                byteArrayOutputStream.write(4);
                writeLong(byteArrayOutputStream, ((Long) obj).longValue());
                return;
            }
            if ((obj instanceof Float) || (obj instanceof Double)) {
                byteArrayOutputStream.write(6);
                writeAlignment(byteArrayOutputStream, 8);
                writeDouble(byteArrayOutputStream, ((Number) obj).doubleValue());
                return;
            } else {
                if (!(obj instanceof BigInteger)) {
                    throw new IllegalArgumentException("Unsupported Number type: " + obj.getClass());
                }
                byteArrayOutputStream.write(5);
                writeBytes(byteArrayOutputStream, ((BigInteger) obj).toString(16).getBytes(UTF8));
                return;
            }
        }
        if (obj instanceof CharSequence) {
            byteArrayOutputStream.write(7);
            writeBytes(byteArrayOutputStream, obj.toString().getBytes(UTF8));
            return;
        }
        if (obj instanceof byte[]) {
            byteArrayOutputStream.write(8);
            writeBytes(byteArrayOutputStream, (byte[]) obj);
            return;
        }
        if (obj instanceof int[]) {
            byteArrayOutputStream.write(9);
            int[] iArr = (int[]) obj;
            writeSize(byteArrayOutputStream, iArr.length);
            writeAlignment(byteArrayOutputStream, 4);
            int length = iArr.length;
            while (i2 < length) {
                writeInt(byteArrayOutputStream, iArr[i2]);
                i2++;
            }
            return;
        }
        if (obj instanceof long[]) {
            byteArrayOutputStream.write(10);
            long[] jArr = (long[]) obj;
            writeSize(byteArrayOutputStream, jArr.length);
            writeAlignment(byteArrayOutputStream, 8);
            int length2 = jArr.length;
            while (i2 < length2) {
                writeLong(byteArrayOutputStream, jArr[i2]);
                i2++;
            }
            return;
        }
        if (obj instanceof double[]) {
            byteArrayOutputStream.write(11);
            double[] dArr = (double[]) obj;
            writeSize(byteArrayOutputStream, dArr.length);
            writeAlignment(byteArrayOutputStream, 8);
            int length3 = dArr.length;
            while (i2 < length3) {
                writeDouble(byteArrayOutputStream, dArr[i2]);
                i2++;
            }
            return;
        }
        if (obj instanceof List) {
            byteArrayOutputStream.write(12);
            List list = (List) obj;
            writeSize(byteArrayOutputStream, list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                writeValue(byteArrayOutputStream, it.next());
            }
            return;
        }
        if (obj instanceof Map) {
            byteArrayOutputStream.write(13);
            Map map = (Map) obj;
            writeSize(byteArrayOutputStream, map.size());
            for (Map.Entry entry : map.entrySet()) {
                writeValue(byteArrayOutputStream, entry.getKey());
                writeValue(byteArrayOutputStream, entry.getValue());
            }
            return;
        }
        if (!(obj instanceof float[])) {
            throw new IllegalArgumentException("Unsupported value: '" + obj + "' of type '" + obj.getClass() + "'");
        }
        byteArrayOutputStream.write(14);
        float[] fArr = (float[]) obj;
        writeSize(byteArrayOutputStream, fArr.length);
        writeAlignment(byteArrayOutputStream, 4);
        int length4 = fArr.length;
        while (i2 < length4) {
            writeFloat(byteArrayOutputStream, fArr[i2]);
            i2++;
        }
    }
}
