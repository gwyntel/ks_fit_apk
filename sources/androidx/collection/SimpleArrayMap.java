package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alipay.sdk.m.n.a;
import java.util.ConcurrentModificationException;
import java.util.Map;

/* loaded from: classes.dex */
public class SimpleArrayMap<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";

    /* renamed from: d, reason: collision with root package name */
    static Object[] f2509d;

    /* renamed from: e, reason: collision with root package name */
    static int f2510e;

    /* renamed from: f, reason: collision with root package name */
    static Object[] f2511f;

    /* renamed from: g, reason: collision with root package name */
    static int f2512g;

    /* renamed from: a, reason: collision with root package name */
    int[] f2513a;

    /* renamed from: b, reason: collision with root package name */
    Object[] f2514b;

    /* renamed from: c, reason: collision with root package name */
    int f2515c;

    public SimpleArrayMap() {
        this.f2513a = ContainerHelpers.f2500a;
        this.f2514b = ContainerHelpers.f2502c;
        this.f2515c = 0;
    }

    private void allocArrays(int i2) {
        if (i2 == 8) {
            synchronized (SimpleArrayMap.class) {
                try {
                    Object[] objArr = f2511f;
                    if (objArr != null) {
                        this.f2514b = objArr;
                        f2511f = (Object[]) objArr[0];
                        this.f2513a = (int[]) objArr[1];
                        objArr[1] = null;
                        objArr[0] = null;
                        f2512g--;
                        return;
                    }
                } finally {
                }
            }
        } else if (i2 == 4) {
            synchronized (SimpleArrayMap.class) {
                try {
                    Object[] objArr2 = f2509d;
                    if (objArr2 != null) {
                        this.f2514b = objArr2;
                        f2509d = (Object[]) objArr2[0];
                        this.f2513a = (int[]) objArr2[1];
                        objArr2[1] = null;
                        objArr2[0] = null;
                        f2510e--;
                        return;
                    }
                } finally {
                }
            }
        }
        this.f2513a = new int[i2];
        this.f2514b = new Object[i2 << 1];
    }

    private static int binarySearchHashes(int[] iArr, int i2, int i3) {
        try {
            return ContainerHelpers.a(iArr, i2, i3);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i2) {
        if (iArr.length == 8) {
            synchronized (SimpleArrayMap.class) {
                try {
                    if (f2512g < 10) {
                        objArr[0] = f2511f;
                        objArr[1] = iArr;
                        for (int i3 = (i2 << 1) - 1; i3 >= 2; i3--) {
                            objArr[i3] = null;
                        }
                        f2511f = objArr;
                        f2512g++;
                    }
                } finally {
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (SimpleArrayMap.class) {
                try {
                    if (f2510e < 10) {
                        objArr[0] = f2509d;
                        objArr[1] = iArr;
                        for (int i4 = (i2 << 1) - 1; i4 >= 2; i4--) {
                            objArr[i4] = null;
                        }
                        f2509d = objArr;
                        f2510e++;
                    }
                } finally {
                }
            }
        }
    }

    int a(Object obj, int i2) {
        int i3 = this.f2515c;
        if (i3 == 0) {
            return -1;
        }
        int iBinarySearchHashes = binarySearchHashes(this.f2513a, i3, i2);
        if (iBinarySearchHashes < 0 || obj.equals(this.f2514b[iBinarySearchHashes << 1])) {
            return iBinarySearchHashes;
        }
        int i4 = iBinarySearchHashes + 1;
        while (i4 < i3 && this.f2513a[i4] == i2) {
            if (obj.equals(this.f2514b[i4 << 1])) {
                return i4;
            }
            i4++;
        }
        for (int i5 = iBinarySearchHashes - 1; i5 >= 0 && this.f2513a[i5] == i2; i5--) {
            if (obj.equals(this.f2514b[i5 << 1])) {
                return i5;
            }
        }
        return ~i4;
    }

    int b() {
        int i2 = this.f2515c;
        if (i2 == 0) {
            return -1;
        }
        int iBinarySearchHashes = binarySearchHashes(this.f2513a, i2, 0);
        if (iBinarySearchHashes < 0 || this.f2514b[iBinarySearchHashes << 1] == null) {
            return iBinarySearchHashes;
        }
        int i3 = iBinarySearchHashes + 1;
        while (i3 < i2 && this.f2513a[i3] == 0) {
            if (this.f2514b[i3 << 1] == null) {
                return i3;
            }
            i3++;
        }
        for (int i4 = iBinarySearchHashes - 1; i4 >= 0 && this.f2513a[i4] == 0; i4--) {
            if (this.f2514b[i4 << 1] == null) {
                return i4;
            }
        }
        return ~i3;
    }

    int c(Object obj) {
        int i2 = this.f2515c * 2;
        Object[] objArr = this.f2514b;
        if (obj == null) {
            for (int i3 = 1; i3 < i2; i3 += 2) {
                if (objArr[i3] == null) {
                    return i3 >> 1;
                }
            }
            return -1;
        }
        for (int i4 = 1; i4 < i2; i4 += 2) {
            if (obj.equals(objArr[i4])) {
                return i4 >> 1;
            }
        }
        return -1;
    }

    public void clear() {
        int i2 = this.f2515c;
        if (i2 > 0) {
            int[] iArr = this.f2513a;
            Object[] objArr = this.f2514b;
            this.f2513a = ContainerHelpers.f2500a;
            this.f2514b = ContainerHelpers.f2502c;
            this.f2515c = 0;
            freeArrays(iArr, objArr, i2);
        }
        if (this.f2515c > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(@Nullable Object obj) {
        return indexOfKey(obj) >= 0;
    }

    public boolean containsValue(Object obj) {
        return c(obj) >= 0;
    }

    public void ensureCapacity(int i2) {
        int i3 = this.f2515c;
        int[] iArr = this.f2513a;
        if (iArr.length < i2) {
            Object[] objArr = this.f2514b;
            allocArrays(i2);
            if (this.f2515c > 0) {
                System.arraycopy(iArr, 0, this.f2513a, 0, i3);
                System.arraycopy(objArr, 0, this.f2514b, 0, i3 << 1);
            }
            freeArrays(iArr, objArr, i3);
        }
        if (this.f2515c != i3) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SimpleArrayMap) {
            SimpleArrayMap simpleArrayMap = (SimpleArrayMap) obj;
            if (size() != simpleArrayMap.size()) {
                return false;
            }
            for (int i2 = 0; i2 < this.f2515c; i2++) {
                K kKeyAt = keyAt(i2);
                V vValueAt = valueAt(i2);
                Object obj2 = simpleArrayMap.get(kKeyAt);
                if (vValueAt == null) {
                    if (obj2 != null || !simpleArrayMap.containsKey(kKeyAt)) {
                        return false;
                    }
                } else if (!vValueAt.equals(obj2)) {
                    return false;
                }
            }
            return true;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (size() != map.size()) {
                return false;
            }
            for (int i3 = 0; i3 < this.f2515c; i3++) {
                K kKeyAt2 = keyAt(i3);
                V vValueAt2 = valueAt(i3);
                Object obj3 = map.get(kKeyAt2);
                if (vValueAt2 == null) {
                    if (obj3 != null || !map.containsKey(kKeyAt2)) {
                        return false;
                    }
                } else if (!vValueAt2.equals(obj3)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Nullable
    public V get(Object obj) {
        return getOrDefault(obj, null);
    }

    public V getOrDefault(Object obj, V v2) {
        int iIndexOfKey = indexOfKey(obj);
        return iIndexOfKey >= 0 ? (V) this.f2514b[(iIndexOfKey << 1) + 1] : v2;
    }

    public int hashCode() {
        int[] iArr = this.f2513a;
        Object[] objArr = this.f2514b;
        int i2 = this.f2515c;
        int i3 = 1;
        int i4 = 0;
        int iHashCode = 0;
        while (i4 < i2) {
            Object obj = objArr[i3];
            iHashCode += (obj == null ? 0 : obj.hashCode()) ^ iArr[i4];
            i4++;
            i3 += 2;
        }
        return iHashCode;
    }

    public int indexOfKey(@Nullable Object obj) {
        return obj == null ? b() : a(obj, obj.hashCode());
    }

    public boolean isEmpty() {
        return this.f2515c <= 0;
    }

    public K keyAt(int i2) {
        return (K) this.f2514b[i2 << 1];
    }

    @Nullable
    public V put(K k2, V v2) {
        int i2;
        int iA;
        int i3 = this.f2515c;
        if (k2 == null) {
            iA = b();
            i2 = 0;
        } else {
            int iHashCode = k2.hashCode();
            i2 = iHashCode;
            iA = a(k2, iHashCode);
        }
        if (iA >= 0) {
            int i4 = (iA << 1) + 1;
            Object[] objArr = this.f2514b;
            V v3 = (V) objArr[i4];
            objArr[i4] = v2;
            return v3;
        }
        int i5 = ~iA;
        int[] iArr = this.f2513a;
        if (i3 >= iArr.length) {
            int i6 = 8;
            if (i3 >= 8) {
                i6 = (i3 >> 1) + i3;
            } else if (i3 < 4) {
                i6 = 4;
            }
            Object[] objArr2 = this.f2514b;
            allocArrays(i6);
            if (i3 != this.f2515c) {
                throw new ConcurrentModificationException();
            }
            int[] iArr2 = this.f2513a;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr2, 0, this.f2514b, 0, objArr2.length);
            }
            freeArrays(iArr, objArr2, i3);
        }
        if (i5 < i3) {
            int[] iArr3 = this.f2513a;
            int i7 = i5 + 1;
            System.arraycopy(iArr3, i5, iArr3, i7, i3 - i5);
            Object[] objArr3 = this.f2514b;
            System.arraycopy(objArr3, i5 << 1, objArr3, i7 << 1, (this.f2515c - i5) << 1);
        }
        int i8 = this.f2515c;
        if (i3 == i8) {
            int[] iArr4 = this.f2513a;
            if (i5 < iArr4.length) {
                iArr4[i5] = i2;
                Object[] objArr4 = this.f2514b;
                int i9 = i5 << 1;
                objArr4[i9] = k2;
                objArr4[i9 + 1] = v2;
                this.f2515c = i8 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    public void putAll(@NonNull SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        int i2 = simpleArrayMap.f2515c;
        ensureCapacity(this.f2515c + i2);
        if (this.f2515c != 0) {
            for (int i3 = 0; i3 < i2; i3++) {
                put(simpleArrayMap.keyAt(i3), simpleArrayMap.valueAt(i3));
            }
        } else if (i2 > 0) {
            System.arraycopy(simpleArrayMap.f2513a, 0, this.f2513a, 0, i2);
            System.arraycopy(simpleArrayMap.f2514b, 0, this.f2514b, 0, i2 << 1);
            this.f2515c = i2;
        }
    }

    @Nullable
    public V putIfAbsent(K k2, V v2) {
        V v3 = get(k2);
        return v3 == null ? put(k2, v2) : v3;
    }

    @Nullable
    public V remove(Object obj) {
        int iIndexOfKey = indexOfKey(obj);
        if (iIndexOfKey >= 0) {
            return removeAt(iIndexOfKey);
        }
        return null;
    }

    public V removeAt(int i2) {
        Object[] objArr = this.f2514b;
        int i3 = i2 << 1;
        V v2 = (V) objArr[i3 + 1];
        int i4 = this.f2515c;
        if (i4 <= 1) {
            clear();
        } else {
            int i5 = i4 - 1;
            int[] iArr = this.f2513a;
            if (iArr.length <= 8 || i4 >= iArr.length / 3) {
                if (i2 < i5) {
                    int i6 = i2 + 1;
                    int i7 = i5 - i2;
                    System.arraycopy(iArr, i6, iArr, i2, i7);
                    Object[] objArr2 = this.f2514b;
                    System.arraycopy(objArr2, i6 << 1, objArr2, i3, i7 << 1);
                }
                Object[] objArr3 = this.f2514b;
                int i8 = i5 << 1;
                objArr3[i8] = null;
                objArr3[i8 + 1] = null;
            } else {
                allocArrays(i4 > 8 ? i4 + (i4 >> 1) : 8);
                if (i4 != this.f2515c) {
                    throw new ConcurrentModificationException();
                }
                if (i2 > 0) {
                    System.arraycopy(iArr, 0, this.f2513a, 0, i2);
                    System.arraycopy(objArr, 0, this.f2514b, 0, i3);
                }
                if (i2 < i5) {
                    int i9 = i2 + 1;
                    int i10 = i5 - i2;
                    System.arraycopy(iArr, i9, this.f2513a, i2, i10);
                    System.arraycopy(objArr, i9 << 1, this.f2514b, i3, i10 << 1);
                }
            }
            if (i4 != this.f2515c) {
                throw new ConcurrentModificationException();
            }
            this.f2515c = i5;
        }
        return v2;
    }

    @Nullable
    public V replace(K k2, V v2) {
        int iIndexOfKey = indexOfKey(k2);
        if (iIndexOfKey >= 0) {
            return setValueAt(iIndexOfKey, v2);
        }
        return null;
    }

    public V setValueAt(int i2, V v2) {
        int i3 = (i2 << 1) + 1;
        Object[] objArr = this.f2514b;
        V v3 = (V) objArr[i3];
        objArr[i3] = v2;
        return v3;
    }

    public int size() {
        return this.f2515c;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f2515c * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.f2515c; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            K kKeyAt = keyAt(i2);
            if (kKeyAt != this) {
                sb.append(kKeyAt);
            } else {
                sb.append("(this Map)");
            }
            sb.append(a.f9565h);
            V vValueAt = valueAt(i2);
            if (vValueAt != this) {
                sb.append(vValueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public V valueAt(int i2) {
        return (V) this.f2514b[(i2 << 1) + 1];
    }

    public boolean remove(Object obj, Object obj2) {
        int iIndexOfKey = indexOfKey(obj);
        if (iIndexOfKey < 0) {
            return false;
        }
        V vValueAt = valueAt(iIndexOfKey);
        if (obj2 != vValueAt && (obj2 == null || !obj2.equals(vValueAt))) {
            return false;
        }
        removeAt(iIndexOfKey);
        return true;
    }

    public boolean replace(K k2, V v2, V v3) {
        int iIndexOfKey = indexOfKey(k2);
        if (iIndexOfKey < 0) {
            return false;
        }
        V vValueAt = valueAt(iIndexOfKey);
        if (vValueAt != v2 && (v2 == null || !v2.equals(vValueAt))) {
            return false;
        }
        setValueAt(iIndexOfKey, v3);
        return true;
    }

    public SimpleArrayMap(int i2) {
        if (i2 == 0) {
            this.f2513a = ContainerHelpers.f2500a;
            this.f2514b = ContainerHelpers.f2502c;
        } else {
            allocArrays(i2);
        }
        this.f2515c = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleArrayMap(SimpleArrayMap<K, V> simpleArrayMap) {
        this();
        if (simpleArrayMap != 0) {
            putAll(simpleArrayMap);
        }
    }
}
