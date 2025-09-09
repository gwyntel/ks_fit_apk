package androidx.datastore.preferences.protobuf;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* loaded from: classes.dex */
final class UnsafeUtil {
    private static final long BOOLEAN_ARRAY_BASE_OFFSET;
    private static final long BOOLEAN_ARRAY_INDEX_SCALE;
    private static final long BUFFER_ADDRESS_OFFSET;
    private static final int BYTE_ARRAY_ALIGNMENT;
    private static final long DOUBLE_ARRAY_BASE_OFFSET;
    private static final long DOUBLE_ARRAY_INDEX_SCALE;
    private static final long FLOAT_ARRAY_BASE_OFFSET;
    private static final long FLOAT_ARRAY_INDEX_SCALE;
    private static final long INT_ARRAY_BASE_OFFSET;
    private static final long INT_ARRAY_INDEX_SCALE;
    private static final long LONG_ARRAY_BASE_OFFSET;
    private static final long LONG_ARRAY_INDEX_SCALE;
    private static final long OBJECT_ARRAY_BASE_OFFSET;
    private static final long OBJECT_ARRAY_INDEX_SCALE;
    private static final int STRIDE = 8;
    private static final int STRIDE_ALIGNMENT_MASK = 7;

    /* renamed from: a, reason: collision with root package name */
    static final long f3986a;

    /* renamed from: b, reason: collision with root package name */
    static final boolean f3987b;
    private static final Logger logger = Logger.getLogger(UnsafeUtil.class.getName());
    private static final Unsafe UNSAFE = v();
    private static final Class<?> MEMORY_CLASS = Android.a();
    private static final boolean IS_ANDROID_64 = determineAndroidSupportByAddressSize(Long.TYPE);
    private static final boolean IS_ANDROID_32 = determineAndroidSupportByAddressSize(Integer.TYPE);
    private static final MemoryAccessor MEMORY_ACCESSOR = getMemoryAccessor();
    private static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS = supportsUnsafeByteBufferOperations();
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = supportsUnsafeArrayOperations();

    private static final class Android32MemoryAccessor extends MemoryAccessor {
        private static final long SMALL_ADDRESS_MASK = -1;

        Android32MemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        private static int smallAddress(long j2) {
            return (int) j2;
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j2, byte[] bArr, long j3, long j4) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(Object obj, long j2) {
            return UnsafeUtil.f3987b ? UnsafeUtil.getBooleanBigEndian(obj, j2) : UnsafeUtil.getBooleanLittleEndian(obj, j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public double getDouble(Object obj, long j2) {
            return Double.longBitsToDouble(getLong(obj, j2));
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public float getFloat(Object obj, long j2) {
            return Float.intBitsToFloat(getInt(obj, j2));
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public int getInt(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public long getLong(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public Object getStaticObject(java.lang.reflect.Field field) {
            try {
                return field.get(null);
            } catch (IllegalAccessException unused) {
                return null;
            }
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putBoolean(Object obj, long j2, boolean z2) {
            if (UnsafeUtil.f3987b) {
                UnsafeUtil.putBooleanBigEndian(obj, j2, z2);
            } else {
                UnsafeUtil.putBooleanLittleEndian(obj, j2, z2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(long j2, byte b2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putDouble(Object obj, long j2, double d2) {
            putLong(obj, j2, Double.doubleToLongBits(d2));
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putFloat(Object obj, long j2, float f2) {
            putInt(obj, j2, Float.floatToIntBits(f2));
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putInt(long j2, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putLong(long j2, long j3) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j2, long j3, long j4) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(Object obj, long j2) {
            return UnsafeUtil.f3987b ? UnsafeUtil.getByteBigEndian(obj, j2) : UnsafeUtil.getByteLittleEndian(obj, j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(Object obj, long j2, byte b2) {
            if (UnsafeUtil.f3987b) {
                UnsafeUtil.putByteBigEndian(obj, j2, b2);
            } else {
                UnsafeUtil.putByteLittleEndian(obj, j2, b2);
            }
        }
    }

    private static final class Android64MemoryAccessor extends MemoryAccessor {
        Android64MemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j2, byte[] bArr, long j3, long j4) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(Object obj, long j2) {
            return UnsafeUtil.f3987b ? UnsafeUtil.getBooleanBigEndian(obj, j2) : UnsafeUtil.getBooleanLittleEndian(obj, j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public double getDouble(Object obj, long j2) {
            return Double.longBitsToDouble(getLong(obj, j2));
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public float getFloat(Object obj, long j2) {
            return Float.intBitsToFloat(getInt(obj, j2));
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public int getInt(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public long getLong(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public Object getStaticObject(java.lang.reflect.Field field) {
            try {
                return field.get(null);
            } catch (IllegalAccessException unused) {
                return null;
            }
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putBoolean(Object obj, long j2, boolean z2) {
            if (UnsafeUtil.f3987b) {
                UnsafeUtil.putBooleanBigEndian(obj, j2, z2);
            } else {
                UnsafeUtil.putBooleanLittleEndian(obj, j2, z2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(long j2, byte b2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putDouble(Object obj, long j2, double d2) {
            putLong(obj, j2, Double.doubleToLongBits(d2));
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putFloat(Object obj, long j2, float f2) {
            putInt(obj, j2, Float.floatToIntBits(f2));
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putInt(long j2, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putLong(long j2, long j3) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j2, long j3, long j4) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(Object obj, long j2) {
            return UnsafeUtil.f3987b ? UnsafeUtil.getByteBigEndian(obj, j2) : UnsafeUtil.getByteLittleEndian(obj, j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(Object obj, long j2, byte b2) {
            if (UnsafeUtil.f3987b) {
                UnsafeUtil.putByteBigEndian(obj, j2, b2);
            } else {
                UnsafeUtil.putByteLittleEndian(obj, j2, b2);
            }
        }
    }

    private static final class JvmMemoryAccessor extends MemoryAccessor {
        JvmMemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j2, byte[] bArr, long j3, long j4) {
            this.f3988a.copyMemory((Object) null, j2, bArr, UnsafeUtil.f3986a + j3, j4);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(Object obj, long j2) {
            return this.f3988a.getBoolean(obj, j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(long j2) {
            return this.f3988a.getByte(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public double getDouble(Object obj, long j2) {
            return this.f3988a.getDouble(obj, j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public float getFloat(Object obj, long j2) {
            return this.f3988a.getFloat(obj, j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public int getInt(long j2) {
            return this.f3988a.getInt(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public long getLong(long j2) {
            return this.f3988a.getLong(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public Object getStaticObject(java.lang.reflect.Field field) {
            return getObject(this.f3988a.staticFieldBase(field), this.f3988a.staticFieldOffset(field));
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putBoolean(Object obj, long j2, boolean z2) {
            this.f3988a.putBoolean(obj, j2, z2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(long j2, byte b2) {
            this.f3988a.putByte(j2, b2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putDouble(Object obj, long j2, double d2) {
            this.f3988a.putDouble(obj, j2, d2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putFloat(Object obj, long j2, float f2) {
            this.f3988a.putFloat(obj, j2, f2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putInt(long j2, int i2) {
            this.f3988a.putInt(j2, i2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putLong(long j2, long j3) {
            this.f3988a.putLong(j2, j3);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j2, long j3, long j4) {
            this.f3988a.copyMemory(bArr, UnsafeUtil.f3986a + j2, (Object) null, j3, j4);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(Object obj, long j2) {
            return this.f3988a.getByte(obj, j2);
        }

        @Override // androidx.datastore.preferences.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(Object obj, long j2, byte b2) {
            this.f3988a.putByte(obj, j2, b2);
        }
    }

    private static abstract class MemoryAccessor {

        /* renamed from: a, reason: collision with root package name */
        Unsafe f3988a;

        MemoryAccessor(Unsafe unsafe) {
            this.f3988a = unsafe;
        }

        public final int arrayBaseOffset(Class<?> cls) {
            return this.f3988a.arrayBaseOffset(cls);
        }

        public final int arrayIndexScale(Class<?> cls) {
            return this.f3988a.arrayIndexScale(cls);
        }

        public abstract void copyMemory(long j2, byte[] bArr, long j3, long j4);

        public abstract void copyMemory(byte[] bArr, long j2, long j3, long j4);

        public abstract boolean getBoolean(Object obj, long j2);

        public abstract byte getByte(long j2);

        public abstract byte getByte(Object obj, long j2);

        public abstract double getDouble(Object obj, long j2);

        public abstract float getFloat(Object obj, long j2);

        public abstract int getInt(long j2);

        public final int getInt(Object obj, long j2) {
            return this.f3988a.getInt(obj, j2);
        }

        public abstract long getLong(long j2);

        public final long getLong(Object obj, long j2) {
            return this.f3988a.getLong(obj, j2);
        }

        public final Object getObject(Object obj, long j2) {
            return this.f3988a.getObject(obj, j2);
        }

        public abstract Object getStaticObject(java.lang.reflect.Field field);

        public final long objectFieldOffset(java.lang.reflect.Field field) {
            return this.f3988a.objectFieldOffset(field);
        }

        public abstract void putBoolean(Object obj, long j2, boolean z2);

        public abstract void putByte(long j2, byte b2);

        public abstract void putByte(Object obj, long j2, byte b2);

        public abstract void putDouble(Object obj, long j2, double d2);

        public abstract void putFloat(Object obj, long j2, float f2);

        public abstract void putInt(long j2, int i2);

        public final void putInt(Object obj, long j2, int i2) {
            this.f3988a.putInt(obj, j2, i2);
        }

        public abstract void putLong(long j2, long j3);

        public final void putLong(Object obj, long j2, long j3) {
            this.f3988a.putLong(obj, j2, j3);
        }

        public final void putObject(Object obj, long j2, Object obj2) {
            this.f3988a.putObject(obj, j2, obj2);
        }
    }

    static {
        long jArrayBaseOffset = arrayBaseOffset(byte[].class);
        f3986a = jArrayBaseOffset;
        BOOLEAN_ARRAY_BASE_OFFSET = arrayBaseOffset(boolean[].class);
        BOOLEAN_ARRAY_INDEX_SCALE = arrayIndexScale(boolean[].class);
        INT_ARRAY_BASE_OFFSET = arrayBaseOffset(int[].class);
        INT_ARRAY_INDEX_SCALE = arrayIndexScale(int[].class);
        LONG_ARRAY_BASE_OFFSET = arrayBaseOffset(long[].class);
        LONG_ARRAY_INDEX_SCALE = arrayIndexScale(long[].class);
        FLOAT_ARRAY_BASE_OFFSET = arrayBaseOffset(float[].class);
        FLOAT_ARRAY_INDEX_SCALE = arrayIndexScale(float[].class);
        DOUBLE_ARRAY_BASE_OFFSET = arrayBaseOffset(double[].class);
        DOUBLE_ARRAY_INDEX_SCALE = arrayIndexScale(double[].class);
        OBJECT_ARRAY_BASE_OFFSET = arrayBaseOffset(Object[].class);
        OBJECT_ARRAY_INDEX_SCALE = arrayIndexScale(Object[].class);
        BUFFER_ADDRESS_OFFSET = fieldOffset(bufferAddressField());
        BYTE_ARRAY_ALIGNMENT = (int) (jArrayBaseOffset & 7);
        f3987b = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private UnsafeUtil() {
    }

    static void A(long j2, byte b2) {
        MEMORY_ACCESSOR.putByte(j2, b2);
    }

    static void B(byte[] bArr, long j2, byte b2) {
        MEMORY_ACCESSOR.putByte(bArr, f3986a + j2, b2);
    }

    static void C(Object obj, long j2, double d2) {
        MEMORY_ACCESSOR.putDouble(obj, j2, d2);
    }

    static void D(Object obj, long j2, float f2) {
        MEMORY_ACCESSOR.putFloat(obj, j2, f2);
    }

    static void E(Object obj, long j2, int i2) {
        MEMORY_ACCESSOR.putInt(obj, j2, i2);
    }

    static void F(Object obj, long j2, long j3) {
        MEMORY_ACCESSOR.putLong(obj, j2, j3);
    }

    static void G(Object obj, long j2, Object obj2) {
        MEMORY_ACCESSOR.putObject(obj, j2, obj2);
    }

    private static int arrayBaseOffset(Class<?> cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int arrayIndexScale(Class<?> cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayIndexScale(cls);
        }
        return -1;
    }

    private static java.lang.reflect.Field bufferAddressField() {
        java.lang.reflect.Field field;
        if (Android.b() && (field = field(Buffer.class, "effectiveDirectAddress")) != null) {
            return field;
        }
        java.lang.reflect.Field field2 = field(Buffer.class, "address");
        if (field2 == null || field2.getType() != Long.TYPE) {
            return null;
        }
        return field2;
    }

    private static boolean determineAndroidSupportByAddressSize(Class<?> cls) {
        if (!Android.b()) {
            return false;
        }
        try {
            Class<?> cls2 = MEMORY_CLASS;
            Class<?> cls3 = Boolean.TYPE;
            cls2.getMethod("peekLong", cls, cls3);
            cls2.getMethod("pokeLong", cls, Long.TYPE, cls3);
            Class<?> cls4 = Integer.TYPE;
            cls2.getMethod("pokeInt", cls, cls4, cls3);
            cls2.getMethod("peekInt", cls, cls3);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, cls4, cls4);
            cls2.getMethod("peekByteArray", cls, byte[].class, cls4, cls4);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static java.lang.reflect.Field field(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static long fieldOffset(java.lang.reflect.Field field) {
        MemoryAccessor memoryAccessor;
        if (field == null || (memoryAccessor = MEMORY_ACCESSOR) == null) {
            return -1L;
        }
        return memoryAccessor.objectFieldOffset(field);
    }

    private static int firstDifferingByteIndexNativeEndian(long j2, long j3) {
        return (f3987b ? Long.numberOfLeadingZeros(j2 ^ j3) : Long.numberOfTrailingZeros(j2 ^ j3)) >> 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean getBooleanBigEndian(Object obj, long j2) {
        return getByteBigEndian(obj, j2) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean getBooleanLittleEndian(Object obj, long j2) {
        return getByteLittleEndian(obj, j2) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte getByteBigEndian(Object obj, long j2) {
        return (byte) ((r(obj, (-4) & j2) >>> ((int) (((~j2) & 3) << 3))) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte getByteLittleEndian(Object obj, long j2) {
        return (byte) ((r(obj, (-4) & j2) >>> ((int) ((j2 & 3) << 3))) & 255);
    }

    private static MemoryAccessor getMemoryAccessor() {
        Unsafe unsafe = UNSAFE;
        if (unsafe == null) {
            return null;
        }
        if (!Android.b()) {
            return new JvmMemoryAccessor(unsafe);
        }
        if (IS_ANDROID_64) {
            return new Android64MemoryAccessor(unsafe);
        }
        if (IS_ANDROID_32) {
            return new Android32MemoryAccessor(unsafe);
        }
        return null;
    }

    static long i(ByteBuffer byteBuffer) {
        return MEMORY_ACCESSOR.getLong(byteBuffer, BUFFER_ADDRESS_OFFSET);
    }

    static Object j(Class cls) {
        try {
            return UNSAFE.allocateInstance(cls);
        } catch (InstantiationException e2) {
            throw new IllegalStateException(e2);
        }
    }

    static void k(long j2, byte[] bArr, long j3, long j4) {
        MEMORY_ACCESSOR.copyMemory(j2, bArr, j3, j4);
    }

    static void l(byte[] bArr, long j2, long j3, long j4) {
        MEMORY_ACCESSOR.copyMemory(bArr, j2, j3, j4);
    }

    static boolean m(Object obj, long j2) {
        return MEMORY_ACCESSOR.getBoolean(obj, j2);
    }

    static byte n(long j2) {
        return MEMORY_ACCESSOR.getByte(j2);
    }

    static byte o(byte[] bArr, long j2) {
        return MEMORY_ACCESSOR.getByte(bArr, f3986a + j2);
    }

    static double p(Object obj, long j2) {
        return MEMORY_ACCESSOR.getDouble(obj, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putBooleanBigEndian(Object obj, long j2, boolean z2) {
        putByteBigEndian(obj, j2, z2 ? (byte) 1 : (byte) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putBooleanLittleEndian(Object obj, long j2, boolean z2) {
        putByteLittleEndian(obj, j2, z2 ? (byte) 1 : (byte) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putByteBigEndian(Object obj, long j2, byte b2) {
        long j3 = (-4) & j2;
        int iR = r(obj, j3);
        int i2 = ((~((int) j2)) & 3) << 3;
        E(obj, j3, ((255 & b2) << i2) | (iR & (~(255 << i2))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putByteLittleEndian(Object obj, long j2, byte b2) {
        long j3 = (-4) & j2;
        int i2 = (((int) j2) & 3) << 3;
        E(obj, j3, ((255 & b2) << i2) | (r(obj, j3) & (~(255 << i2))));
    }

    static float q(Object obj, long j2) {
        return MEMORY_ACCESSOR.getFloat(obj, j2);
    }

    static int r(Object obj, long j2) {
        return MEMORY_ACCESSOR.getInt(obj, j2);
    }

    static long s(long j2) {
        return MEMORY_ACCESSOR.getLong(j2);
    }

    private static boolean supportsUnsafeArrayOperations() {
        Unsafe unsafe = UNSAFE;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", java.lang.reflect.Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            Class<?> cls2 = Long.TYPE;
            cls.getMethod("getInt", Object.class, cls2);
            cls.getMethod("putInt", Object.class, cls2, Integer.TYPE);
            cls.getMethod("getLong", Object.class, cls2);
            cls.getMethod("putLong", Object.class, cls2, cls2);
            cls.getMethod("getObject", Object.class, cls2);
            cls.getMethod("putObject", Object.class, cls2, Object.class);
            if (Android.b()) {
                return true;
            }
            cls.getMethod("getByte", Object.class, cls2);
            cls.getMethod("putByte", Object.class, cls2, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, cls2);
            cls.getMethod("putBoolean", Object.class, cls2, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, cls2);
            cls.getMethod("putFloat", Object.class, cls2, Float.TYPE);
            cls.getMethod("getDouble", Object.class, cls2);
            cls.getMethod("putDouble", Object.class, cls2, Double.TYPE);
            return true;
        } catch (Throwable th) {
            logger.log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + th);
            return false;
        }
    }

    private static boolean supportsUnsafeByteBufferOperations() {
        Unsafe unsafe = UNSAFE;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", java.lang.reflect.Field.class);
            Class<?> cls2 = Long.TYPE;
            cls.getMethod("getLong", Object.class, cls2);
            if (bufferAddressField() == null) {
                return false;
            }
            if (Android.b()) {
                return true;
            }
            cls.getMethod("getByte", cls2);
            cls.getMethod("putByte", cls2, Byte.TYPE);
            cls.getMethod("getInt", cls2);
            cls.getMethod("putInt", cls2, Integer.TYPE);
            cls.getMethod("getLong", cls2);
            cls.getMethod("putLong", cls2, cls2);
            cls.getMethod("copyMemory", cls2, cls2, cls2);
            cls.getMethod("copyMemory", Object.class, cls2, Object.class, cls2, cls2);
            return true;
        } catch (Throwable th) {
            logger.log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + th);
            return false;
        }
    }

    static long t(Object obj, long j2) {
        return MEMORY_ACCESSOR.getLong(obj, j2);
    }

    static Object u(Object obj, long j2) {
        return MEMORY_ACCESSOR.getObject(obj, j2);
    }

    static Unsafe v() {
        try {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: androidx.datastore.preferences.protobuf.UnsafeUtil.1
                @Override // java.security.PrivilegedExceptionAction
                public Unsafe run() throws Exception {
                    for (java.lang.reflect.Field field : Unsafe.class.getDeclaredFields()) {
                        field.setAccessible(true);
                        Object obj = field.get(null);
                        if (Unsafe.class.isInstance(obj)) {
                            return (Unsafe) Unsafe.class.cast(obj);
                        }
                    }
                    return null;
                }
            });
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean w() {
        return HAS_UNSAFE_ARRAY_OPERATIONS;
    }

    static boolean x() {
        return HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    }

    static long y(java.lang.reflect.Field field) {
        return MEMORY_ACCESSOR.objectFieldOffset(field);
    }

    static void z(Object obj, long j2, boolean z2) {
        MEMORY_ACCESSOR.putBoolean(obj, j2, z2);
    }
}
