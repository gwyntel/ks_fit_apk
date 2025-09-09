package androidx.health.platform.client.proto;

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
    static final long f4505a;

    /* renamed from: b, reason: collision with root package name */
    static final boolean f4506b;
    private static final Unsafe UNSAFE = y();
    private static final Class<?> MEMORY_CLASS = Android.a();
    private static final boolean IS_ANDROID_64 = o(Long.TYPE);
    private static final boolean IS_ANDROID_32 = o(Integer.TYPE);
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

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j2, byte[] bArr, long j3, long j4) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(Object obj, long j2) {
            return UnsafeUtil.f4506b ? UnsafeUtil.getBooleanBigEndian(obj, j2) : UnsafeUtil.getBooleanLittleEndian(obj, j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public byte getByte(Object obj, long j2) {
            return UnsafeUtil.f4506b ? UnsafeUtil.getByteBigEndian(obj, j2) : UnsafeUtil.getByteLittleEndian(obj, j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public double getDouble(Object obj, long j2) {
            return Double.longBitsToDouble(getLong(obj, j2));
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public float getFloat(Object obj, long j2) {
            return Float.intBitsToFloat(getInt(obj, j2));
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public int getInt(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public long getLong(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public Object getStaticObject(java.lang.reflect.Field field) {
            try {
                return field.get(null);
            } catch (IllegalAccessException unused) {
                return null;
            }
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putBoolean(Object obj, long j2, boolean z2) {
            if (UnsafeUtil.f4506b) {
                UnsafeUtil.putBooleanBigEndian(obj, j2, z2);
            } else {
                UnsafeUtil.putBooleanLittleEndian(obj, j2, z2);
            }
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putByte(Object obj, long j2, byte b2) {
            if (UnsafeUtil.f4506b) {
                UnsafeUtil.putByteBigEndian(obj, j2, b2);
            } else {
                UnsafeUtil.putByteLittleEndian(obj, j2, b2);
            }
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putDouble(Object obj, long j2, double d2) {
            putLong(obj, j2, Double.doubleToLongBits(d2));
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putFloat(Object obj, long j2, float f2) {
            putInt(obj, j2, Float.floatToIntBits(f2));
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putInt(long j2, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putLong(long j2, long j3) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public boolean supportsUnsafeByteBufferOperations() {
            return false;
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j2, long j3, long j4) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public byte getByte(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putByte(long j2, byte b2) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class Android64MemoryAccessor extends MemoryAccessor {
        Android64MemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j2, byte[] bArr, long j3, long j4) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(Object obj, long j2) {
            return UnsafeUtil.f4506b ? UnsafeUtil.getBooleanBigEndian(obj, j2) : UnsafeUtil.getBooleanLittleEndian(obj, j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public byte getByte(Object obj, long j2) {
            return UnsafeUtil.f4506b ? UnsafeUtil.getByteBigEndian(obj, j2) : UnsafeUtil.getByteLittleEndian(obj, j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public double getDouble(Object obj, long j2) {
            return Double.longBitsToDouble(getLong(obj, j2));
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public float getFloat(Object obj, long j2) {
            return Float.intBitsToFloat(getInt(obj, j2));
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public int getInt(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public long getLong(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public Object getStaticObject(java.lang.reflect.Field field) {
            try {
                return field.get(null);
            } catch (IllegalAccessException unused) {
                return null;
            }
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putBoolean(Object obj, long j2, boolean z2) {
            if (UnsafeUtil.f4506b) {
                UnsafeUtil.putBooleanBigEndian(obj, j2, z2);
            } else {
                UnsafeUtil.putBooleanLittleEndian(obj, j2, z2);
            }
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putByte(Object obj, long j2, byte b2) {
            if (UnsafeUtil.f4506b) {
                UnsafeUtil.putByteBigEndian(obj, j2, b2);
            } else {
                UnsafeUtil.putByteLittleEndian(obj, j2, b2);
            }
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putDouble(Object obj, long j2, double d2) {
            putLong(obj, j2, Double.doubleToLongBits(d2));
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putFloat(Object obj, long j2, float f2) {
            putInt(obj, j2, Float.floatToIntBits(f2));
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putInt(long j2, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putLong(long j2, long j3) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public boolean supportsUnsafeByteBufferOperations() {
            return false;
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j2, long j3, long j4) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public byte getByte(long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putByte(long j2, byte b2) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class JvmMemoryAccessor extends MemoryAccessor {
        JvmMemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j2, byte[] bArr, long j3, long j4) {
            this.f4507a.copyMemory((Object) null, j2, bArr, UnsafeUtil.f4505a + j3, j4);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(Object obj, long j2) {
            return this.f4507a.getBoolean(obj, j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public byte getByte(Object obj, long j2) {
            return this.f4507a.getByte(obj, j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public double getDouble(Object obj, long j2) {
            return this.f4507a.getDouble(obj, j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public float getFloat(Object obj, long j2) {
            return this.f4507a.getFloat(obj, j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public int getInt(long j2) {
            return this.f4507a.getInt(j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public long getLong(long j2) {
            return this.f4507a.getLong(j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public Object getStaticObject(java.lang.reflect.Field field) {
            return getObject(this.f4507a.staticFieldBase(field), this.f4507a.staticFieldOffset(field));
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putBoolean(Object obj, long j2, boolean z2) {
            this.f4507a.putBoolean(obj, j2, z2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putByte(Object obj, long j2, byte b2) {
            this.f4507a.putByte(obj, j2, b2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putDouble(Object obj, long j2, double d2) {
            this.f4507a.putDouble(obj, j2, d2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putFloat(Object obj, long j2, float f2) {
            this.f4507a.putFloat(obj, j2, f2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putInt(long j2, int i2) {
            this.f4507a.putInt(j2, i2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putLong(long j2, long j3) {
            this.f4507a.putLong(j2, j3);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public boolean supportsUnsafeArrayOperations() {
            if (!super.supportsUnsafeArrayOperations()) {
                return false;
            }
            try {
                Class<?> cls = this.f4507a.getClass();
                Class<?> cls2 = Long.TYPE;
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
                UnsafeUtil.logMissingMethod(th);
                return false;
            }
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public boolean supportsUnsafeByteBufferOperations() {
            if (!super.supportsUnsafeByteBufferOperations()) {
                return false;
            }
            try {
                Class<?> cls = this.f4507a.getClass();
                Class<?> cls2 = Long.TYPE;
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
                UnsafeUtil.logMissingMethod(th);
                return false;
            }
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j2, long j3, long j4) {
            this.f4507a.copyMemory(bArr, UnsafeUtil.f4505a + j2, (Object) null, j3, j4);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public byte getByte(long j2) {
            return this.f4507a.getByte(j2);
        }

        @Override // androidx.health.platform.client.proto.UnsafeUtil.MemoryAccessor
        public void putByte(long j2, byte b2) {
            this.f4507a.putByte(j2, b2);
        }
    }

    private static abstract class MemoryAccessor {

        /* renamed from: a, reason: collision with root package name */
        Unsafe f4507a;

        MemoryAccessor(Unsafe unsafe) {
            this.f4507a = unsafe;
        }

        public final int arrayBaseOffset(Class<?> cls) {
            return this.f4507a.arrayBaseOffset(cls);
        }

        public final int arrayIndexScale(Class<?> cls) {
            return this.f4507a.arrayIndexScale(cls);
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
            return this.f4507a.getInt(obj, j2);
        }

        public abstract long getLong(long j2);

        public final long getLong(Object obj, long j2) {
            return this.f4507a.getLong(obj, j2);
        }

        public final Object getObject(Object obj, long j2) {
            return this.f4507a.getObject(obj, j2);
        }

        public abstract Object getStaticObject(java.lang.reflect.Field field);

        public final long objectFieldOffset(java.lang.reflect.Field field) {
            return this.f4507a.objectFieldOffset(field);
        }

        public abstract void putBoolean(Object obj, long j2, boolean z2);

        public abstract void putByte(long j2, byte b2);

        public abstract void putByte(Object obj, long j2, byte b2);

        public abstract void putDouble(Object obj, long j2, double d2);

        public abstract void putFloat(Object obj, long j2, float f2);

        public abstract void putInt(long j2, int i2);

        public final void putInt(Object obj, long j2, int i2) {
            this.f4507a.putInt(obj, j2, i2);
        }

        public abstract void putLong(long j2, long j3);

        public final void putLong(Object obj, long j2, long j3) {
            this.f4507a.putLong(obj, j2, j3);
        }

        public final void putObject(Object obj, long j2, Object obj2) {
            this.f4507a.putObject(obj, j2, obj2);
        }

        public boolean supportsUnsafeArrayOperations() {
            Unsafe unsafe = this.f4507a;
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
                return true;
            } catch (Throwable th) {
                UnsafeUtil.logMissingMethod(th);
                return false;
            }
        }

        public boolean supportsUnsafeByteBufferOperations() {
            Unsafe unsafe = this.f4507a;
            if (unsafe == null) {
                return false;
            }
            try {
                Class<?> cls = unsafe.getClass();
                cls.getMethod("objectFieldOffset", java.lang.reflect.Field.class);
                cls.getMethod("getLong", Object.class, Long.TYPE);
                return UnsafeUtil.bufferAddressField() != null;
            } catch (Throwable th) {
                UnsafeUtil.logMissingMethod(th);
                return false;
            }
        }
    }

    static {
        long jArrayBaseOffset = arrayBaseOffset(byte[].class);
        f4505a = jArrayBaseOffset;
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
        f4506b = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private UnsafeUtil() {
    }

    static boolean A() {
        return HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    }

    static long B(java.lang.reflect.Field field) {
        return MEMORY_ACCESSOR.objectFieldOffset(field);
    }

    static void C(Object obj, long j2, boolean z2) {
        MEMORY_ACCESSOR.putBoolean(obj, j2, z2);
    }

    static void D(long j2, byte b2) {
        MEMORY_ACCESSOR.putByte(j2, b2);
    }

    static void E(byte[] bArr, long j2, byte b2) {
        MEMORY_ACCESSOR.putByte(bArr, f4505a + j2, b2);
    }

    static void F(Object obj, long j2, double d2) {
        MEMORY_ACCESSOR.putDouble(obj, j2, d2);
    }

    static void G(Object obj, long j2, float f2) {
        MEMORY_ACCESSOR.putFloat(obj, j2, f2);
    }

    static void H(Object obj, long j2, int i2) {
        MEMORY_ACCESSOR.putInt(obj, j2, i2);
    }

    static void I(Object obj, long j2, long j3) {
        MEMORY_ACCESSOR.putLong(obj, j2, j3);
    }

    static void J(Object obj, long j2, Object obj2) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.reflect.Field bufferAddressField() {
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
        return (f4506b ? Long.numberOfLeadingZeros(j2 ^ j3) : Long.numberOfTrailingZeros(j2 ^ j3)) >> 3;
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
        return (byte) ((u(obj, (-4) & j2) >>> ((int) (((~j2) & 3) << 3))) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte getByteLittleEndian(Object obj, long j2) {
        return (byte) ((u(obj, (-4) & j2) >>> ((int) ((j2 & 3) << 3))) & 255);
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

    static long k(ByteBuffer byteBuffer) {
        return MEMORY_ACCESSOR.getLong(byteBuffer, BUFFER_ADDRESS_OFFSET);
    }

    static Object l(Class cls) {
        try {
            return UNSAFE.allocateInstance(cls);
        } catch (InstantiationException e2) {
            throw new IllegalStateException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logMissingMethod(Throwable th) {
        Logger.getLogger(UnsafeUtil.class.getName()).log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + th);
    }

    static void m(long j2, byte[] bArr, long j3, long j4) {
        MEMORY_ACCESSOR.copyMemory(j2, bArr, j3, j4);
    }

    static void n(byte[] bArr, long j2, long j3, long j4) {
        MEMORY_ACCESSOR.copyMemory(bArr, j2, j3, j4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static boolean o(Class cls) {
        if (!Android.b()) {
            return false;
        }
        try {
            Class<?> cls2 = MEMORY_CLASS;
            Class cls3 = Boolean.TYPE;
            cls2.getMethod("peekLong", cls, cls3);
            cls2.getMethod("pokeLong", cls, Long.TYPE, cls3);
            Class cls4 = Integer.TYPE;
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

    static boolean p(Object obj, long j2) {
        return MEMORY_ACCESSOR.getBoolean(obj, j2);
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
        int iU = u(obj, j3);
        int i2 = ((~((int) j2)) & 3) << 3;
        H(obj, j3, ((255 & b2) << i2) | (iU & (~(255 << i2))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putByteLittleEndian(Object obj, long j2, byte b2) {
        long j3 = (-4) & j2;
        int i2 = (((int) j2) & 3) << 3;
        H(obj, j3, ((255 & b2) << i2) | (u(obj, j3) & (~(255 << i2))));
    }

    static byte q(long j2) {
        return MEMORY_ACCESSOR.getByte(j2);
    }

    static byte r(byte[] bArr, long j2) {
        return MEMORY_ACCESSOR.getByte(bArr, f4505a + j2);
    }

    static double s(Object obj, long j2) {
        return MEMORY_ACCESSOR.getDouble(obj, j2);
    }

    private static boolean supportsUnsafeArrayOperations() {
        MemoryAccessor memoryAccessor = MEMORY_ACCESSOR;
        if (memoryAccessor == null) {
            return false;
        }
        return memoryAccessor.supportsUnsafeArrayOperations();
    }

    private static boolean supportsUnsafeByteBufferOperations() {
        MemoryAccessor memoryAccessor = MEMORY_ACCESSOR;
        if (memoryAccessor == null) {
            return false;
        }
        return memoryAccessor.supportsUnsafeByteBufferOperations();
    }

    static float t(Object obj, long j2) {
        return MEMORY_ACCESSOR.getFloat(obj, j2);
    }

    static int u(Object obj, long j2) {
        return MEMORY_ACCESSOR.getInt(obj, j2);
    }

    static long v(long j2) {
        return MEMORY_ACCESSOR.getLong(j2);
    }

    static long w(Object obj, long j2) {
        return MEMORY_ACCESSOR.getLong(obj, j2);
    }

    static Object x(Object obj, long j2) {
        return MEMORY_ACCESSOR.getObject(obj, j2);
    }

    static Unsafe y() {
        try {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: androidx.health.platform.client.proto.UnsafeUtil.1
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

    static boolean z() {
        return HAS_UNSAFE_ARRAY_OPERATIONS;
    }
}
