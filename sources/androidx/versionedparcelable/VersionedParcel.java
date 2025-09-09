package androidx.versionedparcelable;

import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseBooleanArray;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes2.dex */
public abstract class VersionedParcel {
    private static final int EX_BAD_PARCELABLE = -2;
    private static final int EX_ILLEGAL_ARGUMENT = -3;
    private static final int EX_ILLEGAL_STATE = -5;
    private static final int EX_NETWORK_MAIN_THREAD = -6;
    private static final int EX_NULL_POINTER = -4;
    private static final int EX_PARCELABLE = -9;
    private static final int EX_SECURITY = -1;
    private static final int EX_UNSUPPORTED_OPERATION = -7;
    private static final String TAG = "VersionedParcel";
    private static final int TYPE_BINDER = 5;
    private static final int TYPE_FLOAT = 8;
    private static final int TYPE_INTEGER = 7;
    private static final int TYPE_PARCELABLE = 2;
    private static final int TYPE_SERIALIZABLE = 3;
    private static final int TYPE_STRING = 4;
    private static final int TYPE_VERSIONED_PARCELABLE = 1;

    /* renamed from: a, reason: collision with root package name */
    protected final ArrayMap f6432a;

    /* renamed from: b, reason: collision with root package name */
    protected final ArrayMap f6433b;

    /* renamed from: c, reason: collision with root package name */
    protected final ArrayMap f6434c;

    public static class ParcelException extends RuntimeException {
        public ParcelException(Throwable th) {
            super(th);
        }
    }

    public VersionedParcel(ArrayMap<String, Method> arrayMap, ArrayMap<String, Method> arrayMap2, ArrayMap<String, Class> arrayMap3) {
        this.f6432a = arrayMap;
        this.f6433b = arrayMap2;
        this.f6434c = arrayMap3;
    }

    private Exception createException(int i2, String str) {
        switch (i2) {
            case -9:
                return (Exception) readParcelable();
            case -8:
            default:
                return new RuntimeException("Unknown exception code: " + i2 + " msg " + str);
            case -7:
                return new UnsupportedOperationException(str);
            case -6:
                return new NetworkOnMainThreadException();
            case -5:
                return new IllegalStateException(str);
            case -4:
                return new NullPointerException(str);
            case -3:
                return new IllegalArgumentException(str);
            case -2:
                return new BadParcelableException(str);
            case -1:
                return new SecurityException(str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Class findParcelClass(Class<? extends VersionedParcelable> cls) throws ClassNotFoundException {
        Class cls2 = (Class) this.f6434c.get(cls.getName());
        if (cls2 != null) {
            return cls2;
        }
        Class<?> cls3 = Class.forName(String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader());
        this.f6434c.put(cls.getName(), cls3);
        return cls3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Method getReadMethod(String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        Method method = (Method) this.f6432a.get(str);
        if (method != null) {
            return method;
        }
        System.currentTimeMillis();
        Method declaredMethod = Class.forName(str, true, VersionedParcel.class.getClassLoader()).getDeclaredMethod("read", VersionedParcel.class);
        this.f6432a.put(str, declaredMethod);
        return declaredMethod;
    }

    private <T> int getType(T t2) {
        if (t2 instanceof String) {
            return 4;
        }
        if (t2 instanceof Parcelable) {
            return 2;
        }
        if (t2 instanceof VersionedParcelable) {
            return 1;
        }
        if (t2 instanceof Serializable) {
            return 3;
        }
        if (t2 instanceof IBinder) {
            return 5;
        }
        if (t2 instanceof Integer) {
            return 7;
        }
        if (t2 instanceof Float) {
            return 8;
        }
        throw new IllegalArgumentException(t2.getClass().getName() + " cannot be VersionedParcelled");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Method getWriteMethod(Class cls) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException {
        Method method = (Method) this.f6433b.get(cls.getName());
        if (method != null) {
            return method;
        }
        Class clsFindParcelClass = findParcelClass(cls);
        System.currentTimeMillis();
        Method declaredMethod = clsFindParcelClass.getDeclaredMethod("write", cls, VersionedParcel.class);
        this.f6433b.put(cls.getName(), declaredMethod);
        return declaredMethod;
    }

    private <T, S extends Collection<T>> S readCollection(S s2) {
        int i2 = readInt();
        if (i2 < 0) {
            return null;
        }
        if (i2 != 0) {
            int i3 = readInt();
            if (i2 < 0) {
                return null;
            }
            if (i3 == 1) {
                while (i2 > 0) {
                    s2.add(k());
                    i2--;
                }
            } else if (i3 == 2) {
                while (i2 > 0) {
                    s2.add(readParcelable());
                    i2--;
                }
            } else if (i3 == 3) {
                while (i2 > 0) {
                    s2.add(j());
                    i2--;
                }
            } else if (i3 == 4) {
                while (i2 > 0) {
                    s2.add(readString());
                    i2--;
                }
            } else if (i3 == 5) {
                while (i2 > 0) {
                    s2.add(readStrongBinder());
                    i2--;
                }
            }
        }
        return s2;
    }

    private int readExceptionCode() {
        return readInt();
    }

    private <T> void writeCollection(Collection<T> collection, int i2) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        setOutputField(i2);
        writeCollection(collection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void writeVersionedParcelableCreator(VersionedParcelable versionedParcelable) {
        try {
            writeString(findParcelClass(versionedParcelable.getClass()).getName());
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException(versionedParcelable.getClass().getSimpleName() + " does not have a Parcelizer", e2);
        }
    }

    protected abstract VersionedParcel a();

    protected Object[] b(Object[] objArr) {
        int i2 = readInt();
        if (i2 < 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(i2);
        if (i2 != 0) {
            int i3 = readInt();
            if (i2 < 0) {
                return null;
            }
            if (i3 == 1) {
                while (i2 > 0) {
                    arrayList.add(k());
                    i2--;
                }
            } else if (i3 == 2) {
                while (i2 > 0) {
                    arrayList.add(readParcelable());
                    i2--;
                }
            } else if (i3 == 3) {
                while (i2 > 0) {
                    arrayList.add(j());
                    i2--;
                }
            } else if (i3 == 4) {
                while (i2 > 0) {
                    arrayList.add(readString());
                    i2--;
                }
            } else if (i3 == 5) {
                while (i2 > 0) {
                    arrayList.add(readStrongBinder());
                    i2--;
                }
            }
        }
        return arrayList.toArray(objArr);
    }

    protected boolean[] c() {
        int i2 = readInt();
        if (i2 < 0) {
            return null;
        }
        boolean[] zArr = new boolean[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            zArr[i3] = readInt() != 0;
        }
        return zArr;
    }

    protected abstract void closeField();

    protected abstract CharSequence d();

    protected double[] e() {
        int i2 = readInt();
        if (i2 < 0) {
            return null;
        }
        double[] dArr = new double[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            dArr[i3] = readDouble();
        }
        return dArr;
    }

    protected float[] f() {
        int i2 = readInt();
        if (i2 < 0) {
            return null;
        }
        float[] fArr = new float[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            fArr[i3] = readFloat();
        }
        return fArr;
    }

    protected VersionedParcelable g(String str, VersionedParcel versionedParcel) {
        try {
            return (VersionedParcelable) getReadMethod(str).invoke(null, versionedParcel);
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e2);
        } catch (IllegalAccessException e3) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e3);
        } catch (NoSuchMethodException e4) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e4);
        } catch (InvocationTargetException e5) {
            if (e5.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e5.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e5);
        }
    }

    protected int[] h() {
        int i2 = readInt();
        if (i2 < 0) {
            return null;
        }
        int[] iArr = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            iArr[i3] = readInt();
        }
        return iArr;
    }

    protected long[] i() {
        int i2 = readInt();
        if (i2 < 0) {
            return null;
        }
        long[] jArr = new long[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            jArr[i3] = readLong();
        }
        return jArr;
    }

    public boolean isStream() {
        return false;
    }

    protected Serializable j() {
        String string = readString();
        if (string == null) {
            return null;
        }
        try {
            return (Serializable) new ObjectInputStream(new ByteArrayInputStream(readByteArray())) { // from class: androidx.versionedparcelable.VersionedParcel.1
                @Override // java.io.ObjectInputStream
                protected Class resolveClass(ObjectStreamClass objectStreamClass) throws ClassNotFoundException {
                    Class<?> cls = Class.forName(objectStreamClass.getName(), false, getClass().getClassLoader());
                    return cls != null ? cls : super.resolveClass(objectStreamClass);
                }
            }.readObject();
        } catch (IOException e2) {
            throw new RuntimeException("VersionedParcelable encountered IOException reading a Serializable object (name = " + string + ")", e2);
        } catch (ClassNotFoundException e3) {
            throw new RuntimeException("VersionedParcelable encountered ClassNotFoundException reading a Serializable object (name = " + string + ")", e3);
        }
    }

    protected VersionedParcelable k() {
        String string = readString();
        if (string == null) {
            return null;
        }
        return g(string, a());
    }

    protected void l(Object[] objArr) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        if (objArr == null) {
            writeInt(-1);
            return;
        }
        int length = objArr.length;
        writeInt(length);
        if (length > 0) {
            int i2 = 0;
            int type = getType(objArr[0]);
            writeInt(type);
            if (type == 1) {
                while (i2 < length) {
                    u((VersionedParcelable) objArr[i2]);
                    i2++;
                }
                return;
            }
            if (type == 2) {
                while (i2 < length) {
                    writeParcelable((Parcelable) objArr[i2]);
                    i2++;
                }
                return;
            }
            if (type == 3) {
                while (i2 < length) {
                    writeSerializable((Serializable) objArr[i2]);
                    i2++;
                }
            } else if (type == 4) {
                while (i2 < length) {
                    writeString((String) objArr[i2]);
                    i2++;
                }
            } else {
                if (type != 5) {
                    return;
                }
                while (i2 < length) {
                    writeStrongBinder((IBinder) objArr[i2]);
                    i2++;
                }
            }
        }
    }

    protected void m(boolean[] zArr) {
        if (zArr == null) {
            writeInt(-1);
            return;
        }
        writeInt(zArr.length);
        for (boolean z2 : zArr) {
            writeInt(z2 ? 1 : 0);
        }
    }

    protected abstract void n(CharSequence charSequence);

    protected void o(double[] dArr) {
        if (dArr == null) {
            writeInt(-1);
            return;
        }
        writeInt(dArr.length);
        for (double d2 : dArr) {
            writeDouble(d2);
        }
    }

    protected void p(float[] fArr) {
        if (fArr == null) {
            writeInt(-1);
            return;
        }
        writeInt(fArr.length);
        for (float f2 : fArr) {
            writeFloat(f2);
        }
    }

    protected void q(int[] iArr) {
        if (iArr == null) {
            writeInt(-1);
            return;
        }
        writeInt(iArr.length);
        for (int i2 : iArr) {
            writeInt(i2);
        }
    }

    protected void r(long[] jArr) {
        if (jArr == null) {
            writeInt(-1);
            return;
        }
        writeInt(jArr.length);
        for (long j2 : jArr) {
            writeLong(j2);
        }
    }

    public <T> T[] readArray(T[] tArr, int i2) {
        return !readField(i2) ? tArr : (T[]) b(tArr);
    }

    protected abstract boolean readBoolean();

    public boolean readBoolean(boolean z2, int i2) {
        return !readField(i2) ? z2 : readBoolean();
    }

    public boolean[] readBooleanArray(boolean[] zArr, int i2) {
        return !readField(i2) ? zArr : c();
    }

    protected abstract Bundle readBundle();

    public Bundle readBundle(Bundle bundle, int i2) {
        return !readField(i2) ? bundle : readBundle();
    }

    public byte readByte(byte b2, int i2) {
        return !readField(i2) ? b2 : (byte) (readInt() & 255);
    }

    protected abstract byte[] readByteArray();

    public byte[] readByteArray(byte[] bArr, int i2) {
        return !readField(i2) ? bArr : readByteArray();
    }

    public char[] readCharArray(char[] cArr, int i2) {
        if (!readField(i2)) {
            return cArr;
        }
        int i3 = readInt();
        if (i3 < 0) {
            return null;
        }
        char[] cArr2 = new char[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            cArr2[i4] = (char) readInt();
        }
        return cArr2;
    }

    public CharSequence readCharSequence(CharSequence charSequence, int i2) {
        return !readField(i2) ? charSequence : d();
    }

    protected abstract double readDouble();

    public double readDouble(double d2, int i2) {
        return !readField(i2) ? d2 : readDouble();
    }

    public double[] readDoubleArray(double[] dArr, int i2) {
        return !readField(i2) ? dArr : e();
    }

    public Exception readException(Exception exc, int i2) {
        int exceptionCode;
        return (readField(i2) && (exceptionCode = readExceptionCode()) != 0) ? readException(exceptionCode, readString()) : exc;
    }

    protected abstract boolean readField(int i2);

    protected abstract float readFloat();

    public float readFloat(float f2, int i2) {
        return !readField(i2) ? f2 : readFloat();
    }

    public float[] readFloatArray(float[] fArr, int i2) {
        return !readField(i2) ? fArr : f();
    }

    protected abstract int readInt();

    public int readInt(int i2, int i3) {
        return !readField(i3) ? i2 : readInt();
    }

    public int[] readIntArray(int[] iArr, int i2) {
        return !readField(i2) ? iArr : h();
    }

    public <T> List<T> readList(List<T> list, int i2) {
        return !readField(i2) ? list : (List) readCollection(new ArrayList());
    }

    protected abstract long readLong();

    public long readLong(long j2, int i2) {
        return !readField(i2) ? j2 : readLong();
    }

    public long[] readLongArray(long[] jArr, int i2) {
        return !readField(i2) ? jArr : i();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <K, V> Map<K, V> readMap(Map<K, V> map, int i2) {
        if (!readField(i2)) {
            return map;
        }
        int i3 = readInt();
        if (i3 < 0) {
            return null;
        }
        ArrayMap arrayMap = new ArrayMap();
        if (i3 == 0) {
            return arrayMap;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        readCollection(arrayList);
        readCollection(arrayList2);
        for (int i4 = 0; i4 < i3; i4++) {
            arrayMap.put(arrayList.get(i4), arrayList2.get(i4));
        }
        return arrayMap;
    }

    protected abstract Parcelable readParcelable();

    public <T extends Parcelable> T readParcelable(T t2, int i2) {
        return !readField(i2) ? t2 : (T) readParcelable();
    }

    public <T> Set<T> readSet(Set<T> set, int i2) {
        return !readField(i2) ? set : (Set) readCollection(new ArraySet());
    }

    @RequiresApi(api = 21)
    public Size readSize(Size size, int i2) {
        if (!readField(i2)) {
            return size;
        }
        if (readBoolean()) {
            return new Size(readInt(), readInt());
        }
        return null;
    }

    @RequiresApi(api = 21)
    public SizeF readSizeF(SizeF sizeF, int i2) {
        if (!readField(i2)) {
            return sizeF;
        }
        if (readBoolean()) {
            return new SizeF(readFloat(), readFloat());
        }
        return null;
    }

    public SparseBooleanArray readSparseBooleanArray(SparseBooleanArray sparseBooleanArray, int i2) {
        if (!readField(i2)) {
            return sparseBooleanArray;
        }
        int i3 = readInt();
        if (i3 < 0) {
            return null;
        }
        SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseBooleanArray2.put(readInt(), readBoolean());
        }
        return sparseBooleanArray2;
    }

    protected abstract String readString();

    public String readString(String str, int i2) {
        return !readField(i2) ? str : readString();
    }

    protected abstract IBinder readStrongBinder();

    public IBinder readStrongBinder(IBinder iBinder, int i2) {
        return !readField(i2) ? iBinder : readStrongBinder();
    }

    public <T extends VersionedParcelable> T readVersionedParcelable(T t2, int i2) {
        return !readField(i2) ? t2 : (T) k();
    }

    protected void s() {
        writeInt(0);
    }

    protected abstract void setOutputField(int i2);

    public void setSerializationFlags(boolean z2, boolean z3) {
    }

    protected void t(VersionedParcelable versionedParcelable, VersionedParcel versionedParcel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            getWriteMethod(versionedParcelable.getClass()).invoke(null, versionedParcelable, versionedParcel);
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e2);
        } catch (IllegalAccessException e3) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e3);
        } catch (NoSuchMethodException e4) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e4);
        } catch (InvocationTargetException e5) {
            if (!(e5.getCause() instanceof RuntimeException)) {
                throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e5);
            }
            throw ((RuntimeException) e5.getCause());
        }
    }

    protected void u(VersionedParcelable versionedParcelable) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (versionedParcelable == null) {
            writeString(null);
            return;
        }
        writeVersionedParcelableCreator(versionedParcelable);
        VersionedParcel versionedParcelA = a();
        t(versionedParcelable, versionedParcelA);
        versionedParcelA.closeField();
    }

    public <T> void writeArray(T[] tArr, int i2) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        setOutputField(i2);
        l(tArr);
    }

    protected abstract void writeBoolean(boolean z2);

    public void writeBoolean(boolean z2, int i2) {
        setOutputField(i2);
        writeBoolean(z2);
    }

    public void writeBooleanArray(boolean[] zArr, int i2) {
        setOutputField(i2);
        m(zArr);
    }

    protected abstract void writeBundle(Bundle bundle);

    public void writeBundle(Bundle bundle, int i2) {
        setOutputField(i2);
        writeBundle(bundle);
    }

    public void writeByte(byte b2, int i2) {
        setOutputField(i2);
        writeInt(b2);
    }

    protected abstract void writeByteArray(byte[] bArr);

    public void writeByteArray(byte[] bArr, int i2) {
        setOutputField(i2);
        writeByteArray(bArr);
    }

    protected abstract void writeByteArray(byte[] bArr, int i2, int i3);

    public void writeCharArray(char[] cArr, int i2) {
        setOutputField(i2);
        if (cArr == null) {
            writeInt(-1);
            return;
        }
        writeInt(cArr.length);
        for (char c2 : cArr) {
            writeInt(c2);
        }
    }

    public void writeCharSequence(CharSequence charSequence, int i2) {
        setOutputField(i2);
        n(charSequence);
    }

    protected abstract void writeDouble(double d2);

    public void writeDouble(double d2, int i2) {
        setOutputField(i2);
        writeDouble(d2);
    }

    public void writeDoubleArray(double[] dArr, int i2) {
        setOutputField(i2);
        o(dArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void writeException(Exception exc, int i2) {
        setOutputField(i2);
        if (exc == 0) {
            s();
            return;
        }
        int i3 = ((exc instanceof Parcelable) && exc.getClass().getClassLoader() == Parcelable.class.getClassLoader()) ? -9 : exc instanceof SecurityException ? -1 : exc instanceof BadParcelableException ? -2 : exc instanceof IllegalArgumentException ? -3 : exc instanceof NullPointerException ? -4 : exc instanceof IllegalStateException ? -5 : exc instanceof NetworkOnMainThreadException ? -6 : exc instanceof UnsupportedOperationException ? -7 : 0;
        writeInt(i3);
        if (i3 == 0) {
            if (!(exc instanceof RuntimeException)) {
                throw new RuntimeException(exc);
            }
            throw ((RuntimeException) exc);
        }
        writeString(exc.getMessage());
        if (i3 != -9) {
            return;
        }
        writeParcelable((Parcelable) exc);
    }

    protected abstract void writeFloat(float f2);

    public void writeFloat(float f2, int i2) {
        setOutputField(i2);
        writeFloat(f2);
    }

    public void writeFloatArray(float[] fArr, int i2) {
        setOutputField(i2);
        p(fArr);
    }

    protected abstract void writeInt(int i2);

    public void writeInt(int i2, int i3) {
        setOutputField(i3);
        writeInt(i2);
    }

    public void writeIntArray(int[] iArr, int i2) {
        setOutputField(i2);
        q(iArr);
    }

    public <T> void writeList(List<T> list, int i2) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        writeCollection(list, i2);
    }

    protected abstract void writeLong(long j2);

    public void writeLong(long j2, int i2) {
        setOutputField(i2);
        writeLong(j2);
    }

    public void writeLongArray(long[] jArr, int i2) {
        setOutputField(i2);
        r(jArr);
    }

    public <K, V> void writeMap(Map<K, V> map, int i2) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        setOutputField(i2);
        if (map == null) {
            writeInt(-1);
            return;
        }
        int size = map.size();
        writeInt(size);
        if (size == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        writeCollection(arrayList);
        writeCollection(arrayList2);
    }

    protected abstract void writeParcelable(Parcelable parcelable);

    public void writeParcelable(Parcelable parcelable, int i2) {
        setOutputField(i2);
        writeParcelable(parcelable);
    }

    public void writeSerializable(Serializable serializable, int i2) throws IOException {
        setOutputField(i2);
        writeSerializable(serializable);
    }

    public <T> void writeSet(Set<T> set, int i2) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        writeCollection(set, i2);
    }

    @RequiresApi(api = 21)
    public void writeSize(Size size, int i2) {
        setOutputField(i2);
        writeBoolean(size != null);
        if (size != null) {
            writeInt(size.getWidth());
            writeInt(size.getHeight());
        }
    }

    @RequiresApi(api = 21)
    public void writeSizeF(SizeF sizeF, int i2) {
        setOutputField(i2);
        writeBoolean(sizeF != null);
        if (sizeF != null) {
            writeFloat(sizeF.getWidth());
            writeFloat(sizeF.getHeight());
        }
    }

    public void writeSparseBooleanArray(SparseBooleanArray sparseBooleanArray, int i2) {
        setOutputField(i2);
        if (sparseBooleanArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseBooleanArray.size();
        writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            writeInt(sparseBooleanArray.keyAt(i3));
            writeBoolean(sparseBooleanArray.valueAt(i3));
        }
    }

    protected abstract void writeString(String str);

    public void writeString(String str, int i2) {
        setOutputField(i2);
        writeString(str);
    }

    protected abstract void writeStrongBinder(IBinder iBinder);

    public void writeStrongBinder(IBinder iBinder, int i2) {
        setOutputField(i2);
        writeStrongBinder(iBinder);
    }

    protected abstract void writeStrongInterface(IInterface iInterface);

    public void writeStrongInterface(IInterface iInterface, int i2) {
        setOutputField(i2);
        writeStrongInterface(iInterface);
    }

    public void writeVersionedParcelable(VersionedParcelable versionedParcelable, int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setOutputField(i2);
        u(versionedParcelable);
    }

    private <T> void writeCollection(Collection<T> collection) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        if (collection == null) {
            writeInt(-1);
        }
        int size = collection.size();
        writeInt(size);
        if (size > 0) {
            int type = getType(collection.iterator().next());
            writeInt(type);
            switch (type) {
                case 1:
                    Iterator<T> it = collection.iterator();
                    while (it.hasNext()) {
                        u((VersionedParcelable) it.next());
                    }
                    break;
                case 2:
                    Iterator<T> it2 = collection.iterator();
                    while (it2.hasNext()) {
                        writeParcelable((Parcelable) it2.next());
                    }
                    break;
                case 3:
                    Iterator<T> it3 = collection.iterator();
                    while (it3.hasNext()) {
                        writeSerializable((Serializable) it3.next());
                    }
                    break;
                case 4:
                    Iterator<T> it4 = collection.iterator();
                    while (it4.hasNext()) {
                        writeString((String) it4.next());
                    }
                    break;
                case 5:
                    Iterator<T> it5 = collection.iterator();
                    while (it5.hasNext()) {
                        writeStrongBinder((IBinder) it5.next());
                    }
                    break;
                case 7:
                    Iterator<T> it6 = collection.iterator();
                    while (it6.hasNext()) {
                        writeInt(((Integer) it6.next()).intValue());
                    }
                    break;
                case 8:
                    Iterator<T> it7 = collection.iterator();
                    while (it7.hasNext()) {
                        writeFloat(((Float) it7.next()).floatValue());
                    }
                    break;
            }
        }
    }

    private void writeSerializable(Serializable serializable) throws IOException {
        if (serializable == null) {
            writeString(null);
            return;
        }
        String name = serializable.getClass().getName();
        writeString(name);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            writeByteArray(byteArrayOutputStream.toByteArray());
        } catch (IOException e2) {
            throw new RuntimeException("VersionedParcelable encountered IOException writing serializable object (name = " + name + ")", e2);
        }
    }

    public void writeByteArray(byte[] bArr, int i2, int i3, int i4) {
        setOutputField(i4);
        writeByteArray(bArr, i2, i3);
    }

    private Exception readException(int i2, String str) {
        return createException(i2, str);
    }
}
