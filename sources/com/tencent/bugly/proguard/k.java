package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class k {

    /* renamed from: a, reason: collision with root package name */
    protected String f21102a = "GBK";

    /* renamed from: b, reason: collision with root package name */
    private ByteBuffer f21103b;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public byte f21104a;

        /* renamed from: b, reason: collision with root package name */
        public int f21105b;
    }

    public k() {
    }

    private boolean b(int i2) {
        int i3;
        try {
            a aVar = new a();
            while (true) {
                int iA = a(aVar, this.f21103b.duplicate());
                i3 = aVar.f21105b;
                if (i2 <= i3 || aVar.f21104a == 11) {
                    break;
                }
                a(iA);
                a(aVar.f21104a);
            }
        } catch (h | BufferUnderflowException unused) {
        }
        return i2 == i3;
    }

    private boolean[] d(int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        a(aVar);
        if (aVar.f21104a != 9) {
            throw new h("type mismatch.");
        }
        int iA = a(0, 0, true);
        if (iA < 0) {
            throw new h("size invalid: ".concat(String.valueOf(iA)));
        }
        boolean[] zArr = new boolean[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            zArr[i3] = a(0, true);
        }
        return zArr;
    }

    private short[] e(int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        a(aVar);
        if (aVar.f21104a != 9) {
            throw new h("type mismatch.");
        }
        int iA = a(0, 0, true);
        if (iA < 0) {
            throw new h("size invalid: ".concat(String.valueOf(iA)));
        }
        short[] sArr = new short[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            sArr[i3] = a(sArr[0], 0, true);
        }
        return sArr;
    }

    private int[] f(int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        a(aVar);
        if (aVar.f21104a != 9) {
            throw new h("type mismatch.");
        }
        int iA = a(0, 0, true);
        if (iA < 0) {
            throw new h("size invalid: ".concat(String.valueOf(iA)));
        }
        int[] iArr = new int[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            iArr[i3] = a(iArr[0], 0, true);
        }
        return iArr;
    }

    private long[] g(int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        a(aVar);
        if (aVar.f21104a != 9) {
            throw new h("type mismatch.");
        }
        int iA = a(0, 0, true);
        if (iA < 0) {
            throw new h("size invalid: ".concat(String.valueOf(iA)));
        }
        long[] jArr = new long[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            jArr[i3] = a(jArr[0], 0, true);
        }
        return jArr;
    }

    private float[] h(int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        a(aVar);
        if (aVar.f21104a != 9) {
            throw new h("type mismatch.");
        }
        int iA = a(0, 0, true);
        if (iA < 0) {
            throw new h("size invalid: ".concat(String.valueOf(iA)));
        }
        float[] fArr = new float[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            fArr[i3] = a(fArr[0], 0, true);
        }
        return fArr;
    }

    private double[] i(int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        a(aVar);
        if (aVar.f21104a != 9) {
            throw new h("type mismatch.");
        }
        int iA = a(0, 0, true);
        if (iA < 0) {
            throw new h("size invalid: ".concat(String.valueOf(iA)));
        }
        double[] dArr = new double[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            dArr[i3] = a(dArr[0], 0, true);
        }
        return dArr;
    }

    public final void a(byte[] bArr) {
        ByteBuffer byteBuffer = this.f21103b;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
        this.f21103b = ByteBuffer.wrap(bArr);
    }

    public final byte[] c(int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        a(aVar);
        byte b2 = aVar.f21104a;
        if (b2 == 9) {
            int iA = a(0, 0, true);
            if (iA < 0) {
                throw new h("size invalid: ".concat(String.valueOf(iA)));
            }
            byte[] bArr = new byte[iA];
            for (int i3 = 0; i3 < iA; i3++) {
                bArr[i3] = a(bArr[0], 0, true);
            }
            return bArr;
        }
        if (b2 != 13) {
            throw new h("type mismatch.");
        }
        a aVar2 = new a();
        a(aVar2);
        if (aVar2.f21104a != 0) {
            throw new h("type mismatch, tag: " + i2 + ", type: " + ((int) aVar.f21104a) + ", " + ((int) aVar2.f21104a));
        }
        int iA2 = a(0, 0, true);
        if (iA2 >= 0) {
            byte[] bArr2 = new byte[iA2];
            this.f21103b.get(bArr2);
            return bArr2;
        }
        throw new h("invalid size, tag: " + i2 + ", type: " + ((int) aVar.f21104a) + ", " + ((int) aVar2.f21104a) + ", size: " + iA2);
    }

    public k(byte[] bArr) {
        this.f21103b = ByteBuffer.wrap(bArr);
    }

    private static int a(a aVar, ByteBuffer byteBuffer) {
        byte b2 = byteBuffer.get();
        aVar.f21104a = (byte) (b2 & 15);
        int i2 = (b2 & 240) >> 4;
        aVar.f21105b = i2;
        if (i2 != 15) {
            return 1;
        }
        aVar.f21105b = byteBuffer.get();
        return 2;
    }

    public k(byte[] bArr, byte b2) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        this.f21103b = byteBufferWrap;
        byteBufferWrap.position(4);
    }

    private void b() {
        a aVar = new a();
        a(aVar);
        a(aVar.f21104a);
    }

    private void a(a aVar) {
        a(aVar, this.f21103b);
    }

    private void a(int i2) {
        ByteBuffer byteBuffer = this.f21103b;
        byteBuffer.position(byteBuffer.position() + i2);
    }

    public final String b(int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        a(aVar);
        byte b2 = aVar.f21104a;
        if (b2 == 6) {
            int i3 = this.f21103b.get();
            if (i3 < 0) {
                i3 += 256;
            }
            byte[] bArr = new byte[i3];
            this.f21103b.get(bArr);
            try {
                return new String(bArr, this.f21102a);
            } catch (UnsupportedEncodingException unused) {
                return new String(bArr);
            }
        }
        if (b2 == 7) {
            int i4 = this.f21103b.getInt();
            if (i4 <= 104857600 && i4 >= 0) {
                byte[] bArr2 = new byte[i4];
                this.f21103b.get(bArr2);
                try {
                    return new String(bArr2, this.f21102a);
                } catch (UnsupportedEncodingException unused2) {
                    return new String(bArr2);
                }
            }
            throw new h("String too long: ".concat(String.valueOf(i4)));
        }
        throw new h("type mismatch.");
    }

    private void a() {
        a aVar = new a();
        do {
            a(aVar);
            a(aVar.f21104a);
        } while (aVar.f21104a != 11);
    }

    private void a(byte b2) {
        int i2 = 0;
        switch (b2) {
            case 0:
                a(1);
                return;
            case 1:
                a(2);
                return;
            case 2:
                a(4);
                return;
            case 3:
                a(8);
                return;
            case 4:
                a(4);
                return;
            case 5:
                a(8);
                return;
            case 6:
                int i3 = this.f21103b.get();
                if (i3 < 0) {
                    i3 += 256;
                }
                a(i3);
                return;
            case 7:
                a(this.f21103b.getInt());
                return;
            case 8:
                int iA = a(0, 0, true);
                while (i2 < iA * 2) {
                    b();
                    i2++;
                }
                return;
            case 9:
                int iA2 = a(0, 0, true);
                while (i2 < iA2) {
                    b();
                    i2++;
                }
                return;
            case 10:
                a();
                return;
            case 11:
            case 12:
                return;
            case 13:
                a aVar = new a();
                a(aVar);
                if (aVar.f21104a == 0) {
                    a(a(0, 0, true));
                    return;
                }
                throw new h("skipField with invalid type, type value: " + ((int) b2) + ", " + ((int) aVar.f21104a));
            default:
                throw new h("invalid type.");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T[] b(T t2, int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        a(aVar);
        if (aVar.f21104a == 9) {
            int iA = a(0, 0, true);
            if (iA >= 0) {
                T[] tArr = (T[]) ((Object[]) Array.newInstance(t2.getClass(), iA));
                for (int i3 = 0; i3 < iA; i3++) {
                    tArr[i3] = a((k) t2, 0, true);
                }
                return tArr;
            }
            throw new h("size invalid: ".concat(String.valueOf(iA)));
        }
        throw new h("type mismatch.");
    }

    public final boolean a(int i2, boolean z2) {
        return a((byte) 0, i2, z2) != 0;
    }

    public final byte a(byte b2, int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return b2;
        }
        a aVar = new a();
        a(aVar);
        byte b3 = aVar.f21104a;
        if (b3 == 0) {
            return this.f21103b.get();
        }
        if (b3 == 12) {
            return (byte) 0;
        }
        throw new h("type mismatch.");
    }

    public final short a(short s2, int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return s2;
        }
        a aVar = new a();
        a(aVar);
        byte b2 = aVar.f21104a;
        if (b2 == 0) {
            return this.f21103b.get();
        }
        if (b2 == 1) {
            return this.f21103b.getShort();
        }
        if (b2 == 12) {
            return (short) 0;
        }
        throw new h("type mismatch.");
    }

    public final int a(int i2, int i3, boolean z2) {
        if (!b(i3)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return i2;
        }
        a aVar = new a();
        a(aVar);
        byte b2 = aVar.f21104a;
        if (b2 == 0) {
            return this.f21103b.get();
        }
        if (b2 == 1) {
            return this.f21103b.getShort();
        }
        if (b2 == 2) {
            return this.f21103b.getInt();
        }
        if (b2 == 12) {
            return 0;
        }
        throw new h("type mismatch.");
    }

    public final long a(long j2, int i2, boolean z2) {
        int i3;
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return j2;
        }
        a aVar = new a();
        a(aVar);
        byte b2 = aVar.f21104a;
        if (b2 == 0) {
            i3 = this.f21103b.get();
        } else if (b2 == 1) {
            i3 = this.f21103b.getShort();
        } else {
            if (b2 != 2) {
                if (b2 == 3) {
                    return this.f21103b.getLong();
                }
                if (b2 == 12) {
                    return 0L;
                }
                throw new h("type mismatch.");
            }
            i3 = this.f21103b.getInt();
        }
        return i3;
    }

    private float a(float f2, int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return f2;
        }
        a aVar = new a();
        a(aVar);
        byte b2 = aVar.f21104a;
        if (b2 == 4) {
            return this.f21103b.getFloat();
        }
        if (b2 == 12) {
            return 0.0f;
        }
        throw new h("type mismatch.");
    }

    private double a(double d2, int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return d2;
        }
        a aVar = new a();
        a(aVar);
        byte b2 = aVar.f21104a;
        if (b2 == 4) {
            return this.f21103b.getFloat();
        }
        if (b2 == 5) {
            return this.f21103b.getDouble();
        }
        if (b2 == 12) {
            return 0.0d;
        }
        throw new h("type mismatch.");
    }

    public final <K, V> HashMap<K, V> a(Map<K, V> map, int i2, boolean z2) {
        return (HashMap) a(new HashMap(), map, i2, z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <K, V> Map<K, V> a(Map<K, V> map, Map<K, V> map2, int i2, boolean z2) {
        if (map2 != null && !map2.isEmpty()) {
            Map.Entry<K, V> next = map2.entrySet().iterator().next();
            K key = next.getKey();
            V value = next.getValue();
            if (b(i2)) {
                a aVar = new a();
                a(aVar);
                if (aVar.f21104a == 8) {
                    int iA = a(0, 0, true);
                    if (iA < 0) {
                        throw new h("size invalid: ".concat(String.valueOf(iA)));
                    }
                    for (int i3 = 0; i3 < iA; i3++) {
                        map.put(a((k) key, 0, true), a((k) value, 1, true));
                    }
                } else {
                    throw new h("type mismatch.");
                }
            } else if (z2) {
                throw new h("require field not exist.");
            }
            return map;
        }
        return new HashMap();
    }

    private <T> T[] a(T[] tArr, int i2, boolean z2) {
        if (tArr != null && tArr.length != 0) {
            return (T[]) b(tArr[0], i2, z2);
        }
        throw new h("unable to get type of key and value.");
    }

    private <T> List<T> a(List<T> list, int i2, boolean z2) {
        if (list != null && !list.isEmpty()) {
            Object[] objArrB = b(list.get(0), i2, z2);
            if (objArrB == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : objArrB) {
                arrayList.add(obj);
            }
            return arrayList;
        }
        return new ArrayList();
    }

    public final m a(m mVar, int i2, boolean z2) {
        if (!b(i2)) {
            if (z2) {
                throw new h("require field not exist.");
            }
            return null;
        }
        try {
            m mVar2 = (m) mVar.getClass().newInstance();
            a aVar = new a();
            a(aVar);
            if (aVar.f21104a == 10) {
                mVar2.a(this);
                a();
                return mVar2;
            }
            throw new h("type mismatch.");
        } catch (Exception e2) {
            throw new h(e2.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> Object a(T t2, int i2, boolean z2) {
        if (t2 instanceof Byte) {
            return Byte.valueOf(a((byte) 0, i2, z2));
        }
        if (t2 instanceof Boolean) {
            return Boolean.valueOf(a(i2, z2));
        }
        if (t2 instanceof Short) {
            return Short.valueOf(a((short) 0, i2, z2));
        }
        if (t2 instanceof Integer) {
            return Integer.valueOf(a(0, i2, z2));
        }
        if (t2 instanceof Long) {
            return Long.valueOf(a(0L, i2, z2));
        }
        if (t2 instanceof Float) {
            return Float.valueOf(a(0.0f, i2, z2));
        }
        if (t2 instanceof Double) {
            return Double.valueOf(a(0.0d, i2, z2));
        }
        if (t2 instanceof String) {
            return String.valueOf(b(i2, z2));
        }
        if (t2 instanceof Map) {
            return a((Map) t2, i2, z2);
        }
        if (t2 instanceof List) {
            return a((List) t2, i2, z2);
        }
        if (t2 instanceof m) {
            return a((m) t2, i2, z2);
        }
        if (t2.getClass().isArray()) {
            if (!(t2 instanceof byte[]) && !(t2 instanceof Byte[])) {
                if (t2 instanceof boolean[]) {
                    return d(i2, z2);
                }
                if (t2 instanceof short[]) {
                    return e(i2, z2);
                }
                if (t2 instanceof int[]) {
                    return f(i2, z2);
                }
                if (t2 instanceof long[]) {
                    return g(i2, z2);
                }
                if (t2 instanceof float[]) {
                    return h(i2, z2);
                }
                if (t2 instanceof double[]) {
                    return i(i2, z2);
                }
                return a((Object[]) t2, i2, z2);
            }
            return c(i2, z2);
        }
        throw new h("read object error: unsupport type.");
    }

    public final int a(String str) {
        this.f21102a = str;
        return 0;
    }
}
