package org.mozilla.javascript;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.text.Typography;
import org.mozilla.javascript.TopLevel;

/* loaded from: classes5.dex */
public class NativeArray extends IdScriptableObject implements List {
    private static final int ConstructorId_concat = -13;
    private static final int ConstructorId_every = -17;
    private static final int ConstructorId_filter = -18;
    private static final int ConstructorId_find = -22;
    private static final int ConstructorId_findIndex = -23;
    private static final int ConstructorId_forEach = -19;
    private static final int ConstructorId_indexOf = -15;
    private static final int ConstructorId_isArray = -26;
    private static final int ConstructorId_join = -5;
    private static final int ConstructorId_lastIndexOf = -16;
    private static final int ConstructorId_map = -20;
    private static final int ConstructorId_pop = -9;
    private static final int ConstructorId_push = -8;
    private static final int ConstructorId_reduce = -24;
    private static final int ConstructorId_reduceRight = -25;
    private static final int ConstructorId_reverse = -6;
    private static final int ConstructorId_shift = -10;
    private static final int ConstructorId_slice = -14;
    private static final int ConstructorId_some = -21;
    private static final int ConstructorId_sort = -7;
    private static final int ConstructorId_splice = -12;
    private static final int ConstructorId_unshift = -11;
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private static final double GROW_FACTOR = 1.5d;
    private static final int Id_concat = 13;
    private static final int Id_constructor = 1;
    private static final int Id_every = 17;
    private static final int Id_filter = 18;
    private static final int Id_find = 22;
    private static final int Id_findIndex = 23;
    private static final int Id_forEach = 19;
    private static final int Id_indexOf = 15;
    private static final int Id_join = 5;
    private static final int Id_lastIndexOf = 16;
    private static final int Id_length = 1;
    private static final int Id_map = 20;
    private static final int Id_pop = 9;
    private static final int Id_push = 8;
    private static final int Id_reduce = 24;
    private static final int Id_reduceRight = 25;
    private static final int Id_reverse = 6;
    private static final int Id_shift = 10;
    private static final int Id_slice = 14;
    private static final int Id_some = 21;
    private static final int Id_sort = 7;
    private static final int Id_splice = 12;
    private static final int Id_toLocaleString = 3;
    private static final int Id_toSource = 4;
    private static final int Id_toString = 2;
    private static final int Id_unshift = 11;
    private static final int MAX_INSTANCE_ID = 1;
    private static final int MAX_PRE_GROW_SIZE = 1431655764;
    private static final int MAX_PROTOTYPE_ID = 26;
    private static final int SymbolId_iterator = 26;
    static final long serialVersionUID = 7331366857676127338L;
    private Object[] dense;
    private boolean denseOnly;
    private long length;
    private int lengthAttr;
    private static final Object ARRAY_TAG = "Array";
    private static final Integer NEGATIVE_ONE = -1;
    private static final Comparator<Object> STRING_COMPARATOR = new StringLikeComparator();
    private static final Comparator<Object> DEFAULT_COMPARATOR = new ElementComparator();
    private static int maximumInitialCapacity = 10000;

    public static final class StringLikeComparator implements Comparator<Object> {
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ScriptRuntime.toString(obj).compareTo(ScriptRuntime.toString(obj2));
        }
    }

    public NativeArray(long j2) {
        this.lengthAttr = 6;
        boolean z2 = j2 <= ((long) maximumInitialCapacity);
        this.denseOnly = z2;
        if (z2) {
            int i2 = (int) j2;
            Object[] objArr = new Object[i2 < 10 ? 10 : i2];
            this.dense = objArr;
            Arrays.fill(objArr, Scriptable.NOT_FOUND);
        }
        this.length = j2;
    }

    private ScriptableObject defaultIndexPropertyDescriptor(Object obj) {
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        NativeObject nativeObject = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(nativeObject, parentScope, TopLevel.Builtins.Object);
        nativeObject.defineProperty("value", obj, 0);
        Boolean bool = Boolean.TRUE;
        nativeObject.defineProperty("writable", bool, 0);
        nativeObject.defineProperty("enumerable", bool, 0);
        nativeObject.defineProperty("configurable", bool, 0);
        return nativeObject;
    }

    private static void defineElem(Context context, Scriptable scriptable, long j2, Object obj) {
        if (j2 > 2147483647L) {
            scriptable.put(Long.toString(j2), scriptable, obj);
        } else {
            scriptable.put((int) j2, scriptable, obj);
        }
    }

    private static void deleteElem(Scriptable scriptable, long j2) {
        int i2 = (int) j2;
        if (i2 == j2) {
            scriptable.delete(i2);
        } else {
            scriptable.delete(Long.toString(j2));
        }
    }

    private boolean ensureCapacity(int i2) {
        if (i2 <= this.dense.length) {
            return true;
        }
        if (i2 > MAX_PRE_GROW_SIZE) {
            this.denseOnly = false;
            return false;
        }
        int iMax = Math.max(i2, (int) (r0.length * GROW_FACTOR));
        Object[] objArr = new Object[iMax];
        Object[] objArr2 = this.dense;
        System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
        Arrays.fill(objArr, this.dense.length, iMax, Scriptable.NOT_FOUND);
        this.dense = objArr;
        return true;
    }

    private static Object getElem(Context context, Scriptable scriptable, long j2) {
        Object rawElem = getRawElem(scriptable, j2);
        return rawElem != Scriptable.NOT_FOUND ? rawElem : Undefined.instance;
    }

    static long getLengthProperty(Context context, Scriptable scriptable) {
        if (scriptable instanceof NativeString) {
            return ((NativeString) scriptable).getLength();
        }
        if (scriptable instanceof NativeArray) {
            return ((NativeArray) scriptable).getLength();
        }
        Object property = ScriptableObject.getProperty(scriptable, SessionDescription.ATTR_LENGTH);
        if (property == Scriptable.NOT_FOUND) {
            return 0L;
        }
        return ScriptRuntime.toUint32(property);
    }

    static int getMaximumInitialCapacity() {
        return maximumInitialCapacity;
    }

    private static Object getRawElem(Scriptable scriptable, long j2) {
        return j2 > 2147483647L ? ScriptableObject.getProperty(scriptable, Long.toString(j2)) : ScriptableObject.getProperty(scriptable, (int) j2);
    }

    static void init(Scriptable scriptable, boolean z2) {
        new NativeArray(0L).exportAsJSClass(26, scriptable, z2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:98:0x00e2, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x00e2 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.Object iterativeMethod(org.mozilla.javascript.Context r22, org.mozilla.javascript.IdFunctionObject r23, org.mozilla.javascript.Scriptable r24, org.mozilla.javascript.Scriptable r25, java.lang.Object[] r26) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.iterativeMethod(org.mozilla.javascript.Context, org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    private static Object jsConstructor(Context context, Scriptable scriptable, Object[] objArr) {
        if (objArr.length == 0) {
            return new NativeArray(0L);
        }
        if (context.getLanguageVersion() == 120) {
            return new NativeArray(objArr);
        }
        Object obj = objArr[0];
        if (objArr.length > 1 || !(obj instanceof Number)) {
            return new NativeArray(objArr);
        }
        long uint32 = ScriptRuntime.toUint32(obj);
        if (uint32 == ((Number) obj).doubleValue()) {
            return new NativeArray(uint32);
        }
        throw ScriptRuntime.constructError("RangeError", ScriptRuntime.getMessage0("msg.arraylength.bad"));
    }

    private static Scriptable js_concat(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        long j2;
        int i2 = 0;
        Scriptable scriptableNewArray = context.newArray(ScriptableObject.getTopLevelScope(scriptable), 0);
        if ((scriptable2 instanceof NativeArray) && (scriptableNewArray instanceof NativeArray)) {
            NativeArray nativeArray = (NativeArray) scriptable2;
            NativeArray nativeArray2 = (NativeArray) scriptableNewArray;
            if (nativeArray.denseOnly && nativeArray2.denseOnly) {
                int i3 = (int) nativeArray.length;
                boolean z2 = true;
                for (int i4 = 0; i4 < objArr.length && z2; i4++) {
                    Object obj = objArr[i4];
                    if (obj instanceof NativeArray) {
                        NativeArray nativeArray3 = (NativeArray) obj;
                        z2 = nativeArray3.denseOnly;
                        i3 = (int) (i3 + nativeArray3.length);
                    } else {
                        i3++;
                    }
                }
                if (z2 && nativeArray2.ensureCapacity(i3)) {
                    System.arraycopy(nativeArray.dense, 0, nativeArray2.dense, 0, (int) nativeArray.length);
                    int i5 = (int) nativeArray.length;
                    for (int i6 = 0; i6 < objArr.length && z2; i6++) {
                        Object obj2 = objArr[i6];
                        if (obj2 instanceof NativeArray) {
                            NativeArray nativeArray4 = (NativeArray) obj2;
                            System.arraycopy(nativeArray4.dense, 0, nativeArray2.dense, i5, (int) nativeArray4.length);
                            i5 += (int) nativeArray4.length;
                        } else {
                            nativeArray2.dense[i5] = obj2;
                            i5++;
                        }
                    }
                    nativeArray2.length = i3;
                    return scriptableNewArray;
                }
            }
        }
        long j3 = 0;
        if (js_isArray(scriptable2)) {
            long lengthProperty = getLengthProperty(context, scriptable2);
            j2 = 0;
            while (j2 < lengthProperty) {
                Object rawElem = getRawElem(scriptable2, j2);
                if (rawElem != Scriptable.NOT_FOUND) {
                    defineElem(context, scriptableNewArray, j2, rawElem);
                }
                j2++;
            }
        } else {
            defineElem(context, scriptableNewArray, 0L, scriptable2);
            j2 = 1;
        }
        while (i2 < objArr.length) {
            if (js_isArray(objArr[i2])) {
                Scriptable scriptable3 = (Scriptable) objArr[i2];
                long lengthProperty2 = getLengthProperty(context, scriptable3);
                long j4 = j3;
                while (j4 < lengthProperty2) {
                    Object rawElem2 = getRawElem(scriptable3, j4);
                    if (rawElem2 != Scriptable.NOT_FOUND) {
                        defineElem(context, scriptableNewArray, j2, rawElem2);
                    }
                    j4++;
                    j2++;
                }
            } else {
                defineElem(context, scriptableNewArray, j2, objArr[i2]);
                j2++;
            }
            i2++;
            j3 = 0;
        }
        setLengthProperty(context, scriptableNewArray, j2);
        return scriptableNewArray;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0028 A[PHI: r8
      0x0028: PHI (r8v2 long) = (r8v1 long), (r8v4 long) binds: [B:10:0x0020, B:12:0x0025] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.Object js_indexOf(org.mozilla.javascript.Context r10, org.mozilla.javascript.Scriptable r11, java.lang.Object[] r12) {
        /*
            int r0 = r12.length
            if (r0 <= 0) goto L7
            r0 = 0
            r0 = r12[r0]
            goto L9
        L7:
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
        L9:
            long r1 = getLengthProperty(r10, r11)
            int r10 = r12.length
            r3 = 2
            r4 = 1
            r6 = 0
            if (r10 >= r3) goto L16
            goto L32
        L16:
            r10 = 1
            r10 = r12[r10]
            double r8 = org.mozilla.javascript.ScriptRuntime.toInteger(r10)
            long r8 = (long) r8
            int r10 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r10 >= 0) goto L28
            long r8 = r8 + r1
            int r10 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r10 >= 0) goto L28
            goto L29
        L28:
            r6 = r8
        L29:
            long r8 = r1 - r4
            int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r10 <= 0) goto L32
            java.lang.Integer r10 = org.mozilla.javascript.NativeArray.NEGATIVE_ONE
            return r10
        L32:
            boolean r10 = r11 instanceof org.mozilla.javascript.NativeArray
            if (r10 == 0) goto L68
            r10 = r11
            org.mozilla.javascript.NativeArray r10 = (org.mozilla.javascript.NativeArray) r10
            boolean r12 = r10.denseOnly
            if (r12 == 0) goto L68
            org.mozilla.javascript.Scriptable r11 = r10.getPrototype()
            int r12 = (int) r6
        L42:
            long r3 = (long) r12
            int r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r5 >= 0) goto L65
            java.lang.Object[] r5 = r10.dense
            r5 = r5[r12]
            java.lang.Object r6 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r5 != r6) goto L55
            if (r11 == 0) goto L55
            java.lang.Object r5 = org.mozilla.javascript.ScriptableObject.getProperty(r11, r12)
        L55:
            if (r5 == r6) goto L62
            boolean r5 = org.mozilla.javascript.ScriptRuntime.shallowEq(r5, r0)
            if (r5 == 0) goto L62
            java.lang.Long r10 = java.lang.Long.valueOf(r3)
            return r10
        L62:
            int r12 = r12 + 1
            goto L42
        L65:
            java.lang.Integer r10 = org.mozilla.javascript.NativeArray.NEGATIVE_ONE
            return r10
        L68:
            int r10 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r10 >= 0) goto L81
            java.lang.Object r10 = getRawElem(r11, r6)
            java.lang.Object r12 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r10 == r12) goto L7f
            boolean r10 = org.mozilla.javascript.ScriptRuntime.shallowEq(r10, r0)
            if (r10 == 0) goto L7f
            java.lang.Long r10 = java.lang.Long.valueOf(r6)
            return r10
        L7f:
            long r6 = r6 + r4
            goto L68
        L81:
            java.lang.Integer r10 = org.mozilla.javascript.NativeArray.NEGATIVE_ONE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.js_indexOf(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    private static boolean js_isArray(Object obj) {
        if (obj instanceof Scriptable) {
            return "Array".equals(((Scriptable) obj).getClassName());
        }
        return false;
    }

    private static String js_join(Context context, Scriptable scriptable, Object[] objArr) {
        Object obj;
        Object obj2;
        long lengthProperty = getLengthProperty(context, scriptable);
        int i2 = (int) lengthProperty;
        if (lengthProperty != i2) {
            throw Context.reportRuntimeError1("msg.arraylength.too.big", String.valueOf(lengthProperty));
        }
        int i3 = 0;
        String string = (objArr.length < 1 || (obj2 = objArr[0]) == Undefined.instance) ? "," : ScriptRuntime.toString(obj2);
        if (scriptable instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) scriptable;
            if (nativeArray.denseOnly) {
                StringBuilder sb = new StringBuilder();
                while (i3 < i2) {
                    if (i3 != 0) {
                        sb.append(string);
                    }
                    Object[] objArr2 = nativeArray.dense;
                    if (i3 < objArr2.length && (obj = objArr2[i3]) != null && obj != Undefined.instance && obj != Scriptable.NOT_FOUND) {
                        sb.append(ScriptRuntime.toString(obj));
                    }
                    i3++;
                }
                return sb.toString();
            }
        }
        if (i2 == 0) {
            return "";
        }
        String[] strArr = new String[i2];
        int length = 0;
        for (int i4 = 0; i4 != i2; i4++) {
            Object elem = getElem(context, scriptable, i4);
            if (elem != null && elem != Undefined.instance) {
                String string2 = ScriptRuntime.toString(elem);
                length += string2.length();
                strArr[i4] = string2;
            }
        }
        StringBuilder sb2 = new StringBuilder(length + ((i2 - 1) * string.length()));
        while (i3 != i2) {
            if (i3 != 0) {
                sb2.append(string);
            }
            String str = strArr[i3];
            if (str != null) {
                sb2.append(str);
            }
            i3++;
        }
        return sb2.toString();
    }

    private static Object js_lastIndexOf(Context context, Scriptable scriptable, Object[] objArr) {
        long j2;
        Object obj = objArr.length > 0 ? objArr[0] : Undefined.instance;
        long lengthProperty = getLengthProperty(context, scriptable);
        if (objArr.length < 2) {
            j2 = lengthProperty - 1;
        } else {
            long integer = (long) ScriptRuntime.toInteger(objArr[1]);
            if (integer >= lengthProperty) {
                j2 = lengthProperty - 1;
            } else {
                if (integer < 0) {
                    integer += lengthProperty;
                }
                j2 = integer;
            }
            if (j2 < 0) {
                return NEGATIVE_ONE;
            }
        }
        if (scriptable instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) scriptable;
            if (nativeArray.denseOnly) {
                Scriptable prototype = nativeArray.getPrototype();
                for (int i2 = (int) j2; i2 >= 0; i2--) {
                    Object property = nativeArray.dense[i2];
                    Object obj2 = Scriptable.NOT_FOUND;
                    if (property == obj2 && prototype != null) {
                        property = ScriptableObject.getProperty(prototype, i2);
                    }
                    if (property != obj2 && ScriptRuntime.shallowEq(property, obj)) {
                        return Long.valueOf(i2);
                    }
                }
                return NEGATIVE_ONE;
            }
        }
        while (j2 >= 0) {
            Object rawElem = getRawElem(scriptable, j2);
            if (rawElem != Scriptable.NOT_FOUND && ScriptRuntime.shallowEq(rawElem, obj)) {
                return Long.valueOf(j2);
            }
            j2--;
        }
        return NEGATIVE_ONE;
    }

    private static Object js_pop(Context context, Scriptable scriptable, Object[] objArr) {
        Object elem;
        if (scriptable instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) scriptable;
            if (nativeArray.denseOnly) {
                long j2 = nativeArray.length;
                if (j2 > 0) {
                    long j3 = j2 - 1;
                    nativeArray.length = j3;
                    Object[] objArr2 = nativeArray.dense;
                    Object obj = objArr2[(int) j3];
                    objArr2[(int) j3] = Scriptable.NOT_FOUND;
                    return obj;
                }
            }
        }
        long lengthProperty = getLengthProperty(context, scriptable);
        if (lengthProperty > 0) {
            lengthProperty--;
            elem = getElem(context, scriptable, lengthProperty);
            deleteElem(scriptable, lengthProperty);
        } else {
            elem = Undefined.instance;
        }
        setLengthProperty(context, scriptable, lengthProperty);
        return elem;
    }

    private static Object js_push(Context context, Scriptable scriptable, Object[] objArr) {
        int i2 = 0;
        if (scriptable instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) scriptable;
            if (nativeArray.denseOnly && nativeArray.ensureCapacity(((int) nativeArray.length) + objArr.length)) {
                while (i2 < objArr.length) {
                    Object[] objArr2 = nativeArray.dense;
                    long j2 = nativeArray.length;
                    nativeArray.length = 1 + j2;
                    objArr2[(int) j2] = objArr[i2];
                    i2++;
                }
                return ScriptRuntime.wrapNumber(nativeArray.length);
            }
        }
        long lengthProperty = getLengthProperty(context, scriptable);
        while (i2 < objArr.length) {
            setElem(context, scriptable, i2 + lengthProperty, objArr[i2]);
            i2++;
        }
        return context.getLanguageVersion() == 120 ? objArr.length == 0 ? Undefined.instance : objArr[objArr.length - 1] : setLengthProperty(context, scriptable, lengthProperty + objArr.length);
    }

    private static Scriptable js_reverse(Context context, Scriptable scriptable, Object[] objArr) {
        if (scriptable instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) scriptable;
            if (nativeArray.denseOnly) {
                int i2 = 0;
                for (int i3 = ((int) nativeArray.length) - 1; i2 < i3; i3--) {
                    Object[] objArr2 = nativeArray.dense;
                    Object obj = objArr2[i2];
                    objArr2[i2] = objArr2[i3];
                    objArr2[i3] = obj;
                    i2++;
                }
                return scriptable;
            }
        }
        long lengthProperty = getLengthProperty(context, scriptable);
        long j2 = lengthProperty / 2;
        for (long j3 = 0; j3 < j2; j3++) {
            long j4 = (lengthProperty - j3) - 1;
            Object rawElem = getRawElem(scriptable, j3);
            setRawElem(context, scriptable, j3, getRawElem(scriptable, j4));
            setRawElem(context, scriptable, j4, rawElem);
        }
        return scriptable;
    }

    private static Object js_shift(Context context, Scriptable scriptable, Object[] objArr) {
        Object elem;
        if (scriptable instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) scriptable;
            if (nativeArray.denseOnly) {
                long j2 = nativeArray.length;
                if (j2 > 0) {
                    long j3 = j2 - 1;
                    nativeArray.length = j3;
                    Object[] objArr2 = nativeArray.dense;
                    Object obj = objArr2[0];
                    System.arraycopy(objArr2, 1, objArr2, 0, (int) j3);
                    Object[] objArr3 = nativeArray.dense;
                    int i2 = (int) nativeArray.length;
                    Object obj2 = Scriptable.NOT_FOUND;
                    objArr3[i2] = obj2;
                    return obj == obj2 ? Undefined.instance : obj;
                }
            }
        }
        long lengthProperty = getLengthProperty(context, scriptable);
        if (lengthProperty > 0) {
            lengthProperty--;
            elem = getElem(context, scriptable, 0L);
            if (lengthProperty > 0) {
                for (long j4 = 1; j4 <= lengthProperty; j4++) {
                    setRawElem(context, scriptable, j4 - 1, getRawElem(scriptable, j4));
                }
            }
            deleteElem(scriptable, lengthProperty);
        } else {
            elem = Undefined.instance;
        }
        setLengthProperty(context, scriptable, lengthProperty);
        return elem;
    }

    private Scriptable js_slice(Context context, Scriptable scriptable, Object[] objArr) {
        long sliceIndex;
        Object obj;
        Scriptable scriptableNewArray = context.newArray(ScriptableObject.getTopLevelScope(this), 0);
        long lengthProperty = getLengthProperty(context, scriptable);
        if (objArr.length == 0) {
            sliceIndex = 0;
        } else {
            sliceIndex = toSliceIndex(ScriptRuntime.toInteger(objArr[0]), lengthProperty);
            if (objArr.length != 1 && (obj = objArr[1]) != Undefined.instance) {
                lengthProperty = toSliceIndex(ScriptRuntime.toInteger(obj), lengthProperty);
            }
        }
        for (long j2 = sliceIndex; j2 < lengthProperty; j2++) {
            Object rawElem = getRawElem(scriptable, j2);
            if (rawElem != Scriptable.NOT_FOUND) {
                defineElem(context, scriptableNewArray, j2 - sliceIndex, rawElem);
            }
        }
        setLengthProperty(context, scriptableNewArray, Math.max(0L, lengthProperty - sliceIndex));
        return scriptableNewArray;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static org.mozilla.javascript.Scriptable js_sort(final org.mozilla.javascript.Context r8, final org.mozilla.javascript.Scriptable r9, org.mozilla.javascript.Scriptable r10, java.lang.Object[] r11) {
        /*
            int r0 = r11.length
            r1 = 0
            if (r0 <= 0) goto L23
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            r11 = r11[r1]
            if (r0 == r11) goto L23
            org.mozilla.javascript.Callable r4 = org.mozilla.javascript.ScriptRuntime.getValueFunctionAndThis(r11, r8)
            org.mozilla.javascript.Scriptable r7 = org.mozilla.javascript.ScriptRuntime.lastStoredScriptable(r8)
            r11 = 2
            java.lang.Object[] r3 = new java.lang.Object[r11]
            org.mozilla.javascript.NativeArray$ElementComparator r11 = new org.mozilla.javascript.NativeArray$ElementComparator
            org.mozilla.javascript.NativeArray$1 r0 = new org.mozilla.javascript.NativeArray$1
            r2 = r0
            r5 = r8
            r6 = r9
            r2.<init>()
            r11.<init>(r0)
            goto L25
        L23:
            java.util.Comparator<java.lang.Object> r11 = org.mozilla.javascript.NativeArray.DEFAULT_COMPARATOR
        L25:
            long r2 = getLengthProperty(r8, r10)
            int r9 = (int) r2
            long r4 = (long) r9
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 != 0) goto L4d
            java.lang.Object[] r0 = new java.lang.Object[r9]
            r2 = r1
        L32:
            if (r2 == r9) goto L3e
            long r3 = (long) r2
            java.lang.Object r3 = getRawElem(r10, r3)
            r0[r2] = r3
            int r2 = r2 + 1
            goto L32
        L3e:
            org.mozilla.javascript.Sorting.hybridSort(r0, r11)
        L41:
            if (r1 >= r9) goto L4c
            long r2 = (long) r1
            r11 = r0[r1]
            setRawElem(r8, r10, r2, r11)
            int r1 = r1 + 1
            goto L41
        L4c:
            return r10
        L4d:
            java.lang.String r8 = "msg.arraylength.too.big"
            java.lang.String r9 = java.lang.String.valueOf(r2)
            org.mozilla.javascript.EvaluatorException r8 = org.mozilla.javascript.Context.reportRuntimeError1(r8, r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.js_sort(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):org.mozilla.javascript.Scriptable");
    }

    private static Object js_splice(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        NativeArray nativeArray;
        boolean z2;
        boolean z3;
        long j2;
        long j3;
        long j4;
        Object objNewArray;
        Scriptable scriptable3;
        Object elem;
        Scriptable scriptable4 = scriptable2;
        if (scriptable4 instanceof NativeArray) {
            nativeArray = (NativeArray) scriptable4;
            z2 = nativeArray.denseOnly;
        } else {
            nativeArray = null;
            z2 = false;
        }
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        int length = objArr.length;
        if (length == 0) {
            return context.newArray(topLevelScope, 0);
        }
        long lengthProperty = getLengthProperty(context, scriptable4);
        long sliceIndex = toSliceIndex(ScriptRuntime.toInteger(objArr[0]), lengthProperty);
        int i2 = length - 1;
        if (objArr.length == 1) {
            j2 = lengthProperty - sliceIndex;
            z3 = z2;
        } else {
            double integer = ScriptRuntime.toInteger(objArr[1]);
            if (integer < 0.0d) {
                z3 = z2;
                j2 = 0;
            } else {
                z3 = z2;
                long j5 = lengthProperty - sliceIndex;
                if (integer <= j5) {
                    j5 = (long) integer;
                }
                j2 = j5;
            }
            i2 = length - 2;
        }
        long j6 = j2;
        long j7 = sliceIndex + j6;
        if (j6 != 0) {
            if (j6 == 1 && context.getLanguageVersion() == 120) {
                j3 = lengthProperty;
                j4 = j7;
                elem = getElem(context, scriptable4, sliceIndex);
            } else if (z3) {
                j3 = lengthProperty;
                int i3 = (int) (j7 - sliceIndex);
                Object[] objArr2 = new Object[i3];
                System.arraycopy(nativeArray.dense, (int) sliceIndex, objArr2, 0, i3);
                j4 = j7;
                objNewArray = context.newArray(topLevelScope, objArr2);
            } else {
                j3 = lengthProperty;
                Scriptable scriptableNewArray = context.newArray(topLevelScope, 0);
                j4 = j7;
                long j8 = sliceIndex;
                while (j8 != j4) {
                    Object rawElem = getRawElem(scriptable4, j8);
                    if (rawElem != Scriptable.NOT_FOUND) {
                        setElem(context, scriptableNewArray, j8 - sliceIndex, rawElem);
                    }
                    j8++;
                    scriptable4 = scriptable2;
                }
                setLengthProperty(context, scriptableNewArray, j4 - sliceIndex);
                elem = scriptableNewArray;
            }
            objNewArray = elem;
        } else {
            j3 = lengthProperty;
            j4 = j7;
            if (context.getLanguageVersion() == 120) {
                elem = Undefined.instance;
                objNewArray = elem;
            } else {
                objNewArray = context.newArray(topLevelScope, 0);
            }
        }
        long j9 = i2;
        long j10 = j9 - j6;
        if (z3) {
            long j11 = j3 + j10;
            if (j11 < 2147483647L) {
                int i4 = (int) j11;
                if (nativeArray.ensureCapacity(i4)) {
                    Object[] objArr3 = nativeArray.dense;
                    System.arraycopy(objArr3, (int) j4, objArr3, (int) (j9 + sliceIndex), (int) (j3 - j4));
                    if (i2 > 0) {
                        System.arraycopy(objArr, 2, nativeArray.dense, (int) sliceIndex, i2);
                    }
                    if (j10 < 0) {
                        Arrays.fill(nativeArray.dense, i4, (int) j3, Scriptable.NOT_FOUND);
                    }
                    nativeArray.length = j11;
                    return objNewArray;
                }
            }
        }
        long j12 = j3;
        if (j10 > 0) {
            long j13 = j12 - 1;
            while (j13 >= j4) {
                setRawElem(context, scriptable2, j13 + j10, getRawElem(scriptable2, j13));
                j13--;
                j4 = j4;
            }
            scriptable3 = scriptable2;
        } else {
            scriptable3 = scriptable2;
            long j14 = j4;
            if (j10 < 0) {
                for (long j15 = j14; j15 < j12; j15++) {
                    setRawElem(context, scriptable3, j15 + j10, getRawElem(scriptable3, j15));
                }
                for (long j16 = j12 + j10; j16 < j12; j16++) {
                    deleteElem(scriptable3, j16);
                }
            }
        }
        int length2 = objArr.length - i2;
        for (int i5 = 0; i5 < i2; i5++) {
            setElem(context, scriptable3, i5 + sliceIndex, objArr[i5 + length2]);
        }
        setLengthProperty(context, scriptable3, j12 + j10);
        return objNewArray;
    }

    private static Object js_unshift(Context context, Scriptable scriptable, Object[] objArr) {
        int i2 = 0;
        if (scriptable instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) scriptable;
            if (nativeArray.denseOnly && nativeArray.ensureCapacity(((int) nativeArray.length) + objArr.length)) {
                Object[] objArr2 = nativeArray.dense;
                System.arraycopy(objArr2, 0, objArr2, objArr.length, (int) nativeArray.length);
                while (i2 < objArr.length) {
                    nativeArray.dense[i2] = objArr[i2];
                    i2++;
                }
                long length = nativeArray.length + objArr.length;
                nativeArray.length = length;
                return ScriptRuntime.wrapNumber(length);
            }
        }
        long lengthProperty = getLengthProperty(context, scriptable);
        int length2 = objArr.length;
        if (objArr.length > 0) {
            if (lengthProperty > 0) {
                for (long j2 = lengthProperty - 1; j2 >= 0; j2--) {
                    setRawElem(context, scriptable, length2 + j2, getRawElem(scriptable, j2));
                }
            }
            while (i2 < objArr.length) {
                setElem(context, scriptable, i2, objArr[i2]);
                i2++;
            }
        }
        return setLengthProperty(context, scriptable, lengthProperty + objArr.length);
    }

    private static Object reduceMethod(Context context, int i2, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        long lengthProperty = getLengthProperty(context, scriptable2);
        Object obj = objArr.length > 0 ? objArr[0] : Undefined.instance;
        if (obj == null || !(obj instanceof Function)) {
            throw ScriptRuntime.notFunctionError(obj);
        }
        Function function = (Function) obj;
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(function);
        boolean z2 = i2 == 24;
        Object objCall = objArr.length > 1 ? objArr[1] : Scriptable.NOT_FOUND;
        for (long j2 = 0; j2 < lengthProperty; j2++) {
            long j3 = z2 ? j2 : (lengthProperty - 1) - j2;
            Object rawElem = getRawElem(scriptable2, j3);
            Object obj2 = Scriptable.NOT_FOUND;
            if (rawElem != obj2) {
                objCall = objCall == obj2 ? rawElem : function.call(context, topLevelScope, topLevelScope, new Object[]{objCall, rawElem, Long.valueOf(j3), scriptable2});
            }
        }
        if (objCall != Scriptable.NOT_FOUND) {
            return objCall;
        }
        throw ScriptRuntime.typeError0("msg.empty.array.reduce");
    }

    private static void setElem(Context context, Scriptable scriptable, long j2, Object obj) {
        if (j2 > 2147483647L) {
            ScriptableObject.putProperty(scriptable, Long.toString(j2), obj);
        } else {
            ScriptableObject.putProperty(scriptable, (int) j2, obj);
        }
    }

    private void setLength(Object obj) {
        if ((this.lengthAttr & 1) != 0) {
            return;
        }
        double number = ScriptRuntime.toNumber(obj);
        long uint32 = ScriptRuntime.toUint32(number);
        double d2 = uint32;
        if (d2 != number) {
            throw ScriptRuntime.constructError("RangeError", ScriptRuntime.getMessage0("msg.arraylength.bad"));
        }
        if (this.denseOnly) {
            long j2 = this.length;
            if (uint32 < j2) {
                Object[] objArr = this.dense;
                Arrays.fill(objArr, (int) uint32, objArr.length, Scriptable.NOT_FOUND);
                this.length = uint32;
                return;
            } else {
                if (uint32 < 1431655764 && d2 < j2 * GROW_FACTOR && ensureCapacity((int) uint32)) {
                    this.length = uint32;
                    return;
                }
                this.denseOnly = false;
            }
        }
        long j3 = this.length;
        if (uint32 < j3) {
            if (j3 - uint32 > PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) {
                for (Object obj2 : getIds()) {
                    if (obj2 instanceof String) {
                        String str = (String) obj2;
                        if (toArrayIndex(str) >= uint32) {
                            delete(str);
                        }
                    } else {
                        int iIntValue = ((Integer) obj2).intValue();
                        if (iIntValue >= uint32) {
                            delete(iIntValue);
                        }
                    }
                }
            } else {
                for (long j4 = uint32; j4 < this.length; j4++) {
                    deleteElem(this, j4);
                }
            }
        }
        this.length = uint32;
    }

    private static Object setLengthProperty(Context context, Scriptable scriptable, long j2) {
        Number numberWrapNumber = ScriptRuntime.wrapNumber(j2);
        ScriptableObject.putProperty(scriptable, SessionDescription.ATTR_LENGTH, numberWrapNumber);
        return numberWrapNumber;
    }

    static void setMaximumInitialCapacity(int i2) {
        maximumInitialCapacity = i2;
    }

    private static void setRawElem(Context context, Scriptable scriptable, long j2, Object obj) {
        if (obj == Scriptable.NOT_FOUND) {
            deleteElem(scriptable, j2);
        } else {
            setElem(context, scriptable, j2, obj);
        }
    }

    private static long toArrayIndex(Object obj) {
        if (obj instanceof String) {
            return toArrayIndex((String) obj);
        }
        if (obj instanceof Number) {
            return toArrayIndex(((Number) obj).doubleValue());
        }
        return -1L;
    }

    private static int toDenseIndex(Object obj) {
        long arrayIndex = toArrayIndex(obj);
        if (0 > arrayIndex || arrayIndex >= 2147483647L) {
            return -1;
        }
        return (int) arrayIndex;
    }

    private static long toSliceIndex(double d2, long j2) {
        if (d2 < 0.0d) {
            d2 += j2;
            if (d2 < 0.0d) {
                return 0L;
            }
        } else if (d2 > j2) {
            return j2;
        }
        return (long) d2;
    }

    private static String toStringHelper(Context context, Scriptable scriptable, Scriptable scriptable2, boolean z2, boolean z3) {
        String str;
        boolean zHas;
        boolean z4;
        long j2;
        boolean z5;
        long lengthProperty = getLengthProperty(context, scriptable2);
        StringBuilder sb = new StringBuilder(256);
        if (z2) {
            sb.append('[');
            str = ", ";
        } else {
            str = ",";
        }
        ObjToIntMap objToIntMap = context.iterating;
        if (objToIntMap == null) {
            context.iterating = new ObjToIntMap(31);
            zHas = false;
            z4 = true;
        } else {
            zHas = objToIntMap.has(scriptable2);
            z4 = false;
        }
        long j3 = 0;
        if (zHas) {
            z5 = false;
            j2 = 0;
        } else {
            try {
                context.iterating.put(scriptable2, 0);
                boolean z6 = !z2 || context.getLanguageVersion() < 150;
                boolean z7 = false;
                j2 = 0;
                while (j2 < lengthProperty) {
                    if (j2 > j3) {
                        sb.append(str);
                    }
                    Object rawElem = getRawElem(scriptable2, j2);
                    if (rawElem == Scriptable.NOT_FOUND || (z6 && (rawElem == null || rawElem == Undefined.instance))) {
                        z7 = false;
                    } else {
                        if (z2) {
                            sb.append(ScriptRuntime.uneval(context, scriptable, rawElem));
                        } else if (rawElem instanceof String) {
                            String str2 = (String) rawElem;
                            if (z2) {
                                sb.append(Typography.quote);
                                sb.append(ScriptRuntime.escapeString(str2));
                                sb.append(Typography.quote);
                            } else {
                                sb.append(str2);
                            }
                        } else {
                            if (z3) {
                                rawElem = ScriptRuntime.getPropFunctionAndThis(rawElem, "toLocaleString", context, scriptable).call(context, scriptable, ScriptRuntime.lastStoredScriptable(context), ScriptRuntime.emptyArgs);
                            }
                            sb.append(ScriptRuntime.toString(rawElem));
                        }
                        z7 = true;
                    }
                    j2++;
                    j3 = 0;
                }
                z5 = z7;
            } catch (Throwable th) {
                if (z4) {
                    context.iterating = null;
                }
                throw th;
            }
        }
        if (z4) {
            context.iterating = null;
        }
        if (z2) {
            if (z5 || j2 <= 0) {
                sb.append(']');
            } else {
                sb.append(", ]");
            }
        }
        return sb.toString();
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return indexOf(obj) > -1;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // org.mozilla.javascript.ScriptableObject
    protected void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject, boolean z2) {
        Object[] objArr = this.dense;
        if (objArr != null) {
            this.dense = null;
            this.denseOnly = false;
            for (int i2 = 0; i2 < objArr.length; i2++) {
                Object obj2 = objArr[i2];
                if (obj2 != Scriptable.NOT_FOUND) {
                    put(i2, this, obj2);
                }
            }
        }
        long arrayIndex = toArrayIndex(obj);
        if (arrayIndex >= this.length) {
            this.length = arrayIndex + 1;
        }
        super.defineOwnProperty(context, obj, scriptableObject, z2);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void delete(int i2) {
        Object[] objArr = this.dense;
        if (objArr == null || i2 < 0 || i2 >= objArr.length || isSealed() || (!this.denseOnly && isGetterOrSetter(null, i2, true))) {
            super.delete(i2);
        } else {
            this.dense[i2] = Scriptable.NOT_FOUND;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:200)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public java.lang.Object execIdCall(org.mozilla.javascript.IdFunctionObject r7, org.mozilla.javascript.Context r8, org.mozilla.javascript.Scriptable r9, org.mozilla.javascript.Scriptable r10, java.lang.Object[] r11) {
        /*
            r6 = this;
            java.lang.Object r0 = org.mozilla.javascript.NativeArray.ARRAY_TAG
            boolean r0 = r7.hasTag(r0)
            if (r0 != 0) goto Ld
            java.lang.Object r7 = super.execIdCall(r7, r8, r9, r10, r11)
            return r7
        Ld:
            int r0 = r7.methodId()
        L11:
            r1 = 1
            r2 = 0
            switch(r0) {
                case -26: goto Lbb;
                case -25: goto La0;
                case -24: goto La0;
                case -23: goto La0;
                case -22: goto La0;
                case -21: goto La0;
                case -20: goto La0;
                case -19: goto La0;
                case -18: goto La0;
                case -17: goto La0;
                case -16: goto La0;
                case -15: goto La0;
                case -14: goto La0;
                case -13: goto La0;
                case -12: goto La0;
                case -11: goto La0;
                case -10: goto La0;
                case -9: goto La0;
                case -8: goto La0;
                case -7: goto La0;
                case -6: goto La0;
                case -5: goto La0;
                default: goto L16;
            }
        L16:
            switch(r0) {
                case 1: goto L94;
                case 2: goto L8a;
                case 3: goto L85;
                case 4: goto L80;
                case 5: goto L7b;
                case 6: goto L76;
                case 7: goto L71;
                case 8: goto L6c;
                case 9: goto L67;
                case 10: goto L62;
                case 11: goto L5d;
                case 12: goto L58;
                case 13: goto L53;
                case 14: goto L4e;
                case 15: goto L49;
                case 16: goto L44;
                case 17: goto L3f;
                case 18: goto L3f;
                case 19: goto L3f;
                case 20: goto L3f;
                case 21: goto L3f;
                case 22: goto L3f;
                case 23: goto L3f;
                case 24: goto L3a;
                case 25: goto L3a;
                case 26: goto L34;
                default: goto L19;
            }
        L19:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Array.prototype has no method: "
            r9.append(r10)
            java.lang.String r7 = r7.getFunctionName()
            r9.append(r7)
            java.lang.String r7 = r9.toString()
            r8.<init>(r7)
            throw r8
        L34:
            org.mozilla.javascript.NativeArrayIterator r7 = new org.mozilla.javascript.NativeArrayIterator
            r7.<init>(r9, r10)
            return r7
        L3a:
            java.lang.Object r7 = reduceMethod(r8, r0, r9, r10, r11)
            return r7
        L3f:
            java.lang.Object r7 = iterativeMethod(r8, r7, r9, r10, r11)
            return r7
        L44:
            java.lang.Object r7 = js_lastIndexOf(r8, r10, r11)
            return r7
        L49:
            java.lang.Object r7 = js_indexOf(r8, r10, r11)
            return r7
        L4e:
            org.mozilla.javascript.Scriptable r7 = r6.js_slice(r8, r10, r11)
            return r7
        L53:
            org.mozilla.javascript.Scriptable r7 = js_concat(r8, r9, r10, r11)
            return r7
        L58:
            java.lang.Object r7 = js_splice(r8, r9, r10, r11)
            return r7
        L5d:
            java.lang.Object r7 = js_unshift(r8, r10, r11)
            return r7
        L62:
            java.lang.Object r7 = js_shift(r8, r10, r11)
            return r7
        L67:
            java.lang.Object r7 = js_pop(r8, r10, r11)
            return r7
        L6c:
            java.lang.Object r7 = js_push(r8, r10, r11)
            return r7
        L71:
            org.mozilla.javascript.Scriptable r7 = js_sort(r8, r9, r10, r11)
            return r7
        L76:
            org.mozilla.javascript.Scriptable r7 = js_reverse(r8, r10, r11)
            return r7
        L7b:
            java.lang.String r7 = js_join(r8, r10, r11)
            return r7
        L80:
            java.lang.String r7 = toStringHelper(r8, r9, r10, r1, r2)
            return r7
        L85:
            java.lang.String r7 = toStringHelper(r8, r9, r10, r2, r1)
            return r7
        L8a:
            r7 = 4
            boolean r7 = r8.hasFeature(r7)
            java.lang.String r7 = toStringHelper(r8, r9, r10, r7, r2)
            return r7
        L94:
            if (r10 != 0) goto L9b
            java.lang.Object r7 = jsConstructor(r8, r9, r11)
            return r7
        L9b:
            org.mozilla.javascript.Scriptable r7 = r7.construct(r8, r9, r11)
            return r7
        La0:
            int r3 = r11.length
            if (r3 <= 0) goto Lb8
            r10 = r11[r2]
            org.mozilla.javascript.Scriptable r10 = org.mozilla.javascript.ScriptRuntime.toObject(r8, r9, r10)
            int r3 = r11.length
            int r3 = r3 - r1
            java.lang.Object[] r1 = new java.lang.Object[r3]
        Lad:
            if (r2 >= r3) goto Lb7
            int r4 = r2 + 1
            r5 = r11[r4]
            r1[r2] = r5
            r2 = r4
            goto Lad
        Lb7:
            r11 = r1
        Lb8:
            int r0 = -r0
            goto L11
        Lbb:
            int r7 = r11.length
            if (r7 <= 0) goto Lc7
            r7 = r11[r2]
            boolean r7 = js_isArray(r7)
            if (r7 == 0) goto Lc7
            goto Lc8
        Lc7:
            r1 = r2
        Lc8:
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r1)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        Object obj = ARRAY_TAG;
        addIdFunctionProperty(idFunctionObject, obj, -5, "join", 1);
        addIdFunctionProperty(idFunctionObject, obj, -6, "reverse", 0);
        addIdFunctionProperty(idFunctionObject, obj, -7, "sort", 1);
        addIdFunctionProperty(idFunctionObject, obj, -8, "push", 1);
        addIdFunctionProperty(idFunctionObject, obj, -9, "pop", 0);
        addIdFunctionProperty(idFunctionObject, obj, -10, "shift", 0);
        addIdFunctionProperty(idFunctionObject, obj, -11, "unshift", 1);
        addIdFunctionProperty(idFunctionObject, obj, -12, "splice", 2);
        addIdFunctionProperty(idFunctionObject, obj, -13, "concat", 1);
        addIdFunctionProperty(idFunctionObject, obj, -14, "slice", 2);
        addIdFunctionProperty(idFunctionObject, obj, -15, "indexOf", 1);
        addIdFunctionProperty(idFunctionObject, obj, -16, "lastIndexOf", 1);
        addIdFunctionProperty(idFunctionObject, obj, -17, "every", 1);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_filter, "filter", 1);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_forEach, "forEach", 1);
        addIdFunctionProperty(idFunctionObject, obj, -20, "map", 1);
        addIdFunctionProperty(idFunctionObject, obj, -21, "some", 1);
        addIdFunctionProperty(idFunctionObject, obj, -22, "find", 1);
        addIdFunctionProperty(idFunctionObject, obj, -23, "findIndex", 1);
        addIdFunctionProperty(idFunctionObject, obj, -24, "reduce", 1);
        addIdFunctionProperty(idFunctionObject, obj, -25, "reduceRight", 1);
        addIdFunctionProperty(idFunctionObject, obj, -26, "isArray", 1);
        super.fillConstructorProperties(idFunctionObject);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        return str.equals(SessionDescription.ATTR_LENGTH) ? IdScriptableObject.instanceIdInfo(this.lengthAttr, 1) : super.findInstanceIdInfo(str);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(Symbol symbol) {
        return SymbolKey.ITERATOR.equals(symbol) ? 26 : 0;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object get(int i2, Scriptable scriptable) {
        if (!this.denseOnly && isGetterOrSetter(null, i2, false)) {
            return super.get(i2, scriptable);
        }
        Object[] objArr = this.dense;
        return (objArr == null || i2 < 0 || i2 >= objArr.length) ? super.get(i2, scriptable) : objArr[i2];
    }

    @Override // org.mozilla.javascript.ScriptableObject
    public int getAttributes(int i2) {
        Object[] objArr = this.dense;
        if (objArr == null || i2 < 0 || i2 >= objArr.length || objArr[i2] == Scriptable.NOT_FOUND) {
            return super.getAttributes(i2);
        }
        return 0;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Array";
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        return (cls == ScriptRuntime.NumberClass && Context.getContext().getLanguageVersion() == 120) ? Long.valueOf(this.length) : super.getDefaultValue(cls);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject
    public Object[] getIds(boolean z2, boolean z3) {
        Object[] ids = super.getIds(z2, z3);
        Object[] objArr = this.dense;
        if (objArr == null) {
            return ids;
        }
        int length = objArr.length;
        long j2 = this.length;
        if (length > j2) {
            length = (int) j2;
        }
        if (length == 0) {
            return ids;
        }
        int length2 = ids.length;
        Object[] objArr2 = new Object[length + length2];
        int i2 = 0;
        for (int i3 = 0; i3 != length; i3++) {
            if (this.dense[i3] != Scriptable.NOT_FOUND) {
                objArr2[i2] = Integer.valueOf(i3);
                i2++;
            }
        }
        if (i2 != length) {
            Object[] objArr3 = new Object[i2 + length2];
            System.arraycopy(objArr2, 0, objArr3, 0, i2);
            objArr2 = objArr3;
        }
        System.arraycopy(ids, 0, objArr2, i2, length2);
        return objArr2;
    }

    public Integer[] getIndexIds() {
        Object[] ids = getIds();
        ArrayList arrayList = new ArrayList(ids.length);
        for (Object obj : ids) {
            int int32 = ScriptRuntime.toInt32(obj);
            if (int32 >= 0 && ScriptRuntime.toString(int32).equals(ScriptRuntime.toString(obj))) {
                arrayList.add(Integer.valueOf(int32));
            }
        }
        return (Integer[]) arrayList.toArray(new Integer[arrayList.size()]);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        return i2 == 1 ? SessionDescription.ATTR_LENGTH : super.getInstanceIdName(i2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        return i2 == 1 ? ScriptRuntime.wrapNumber(this.length) : super.getInstanceIdValue(i2);
    }

    public long getLength() {
        return this.length;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 1;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject
    protected ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        int denseIndex;
        Object obj2;
        if (this.dense != null && (denseIndex = toDenseIndex(obj)) >= 0) {
            Object[] objArr = this.dense;
            if (denseIndex < objArr.length && (obj2 = objArr[denseIndex]) != Scriptable.NOT_FOUND) {
                return defaultIndexPropertyDescriptor(obj2);
            }
        }
        return super.getOwnPropertyDescriptor(context, obj);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean has(int i2, Scriptable scriptable) {
        if (!this.denseOnly && isGetterOrSetter(null, i2, false)) {
            return super.has(i2, scriptable);
        }
        Object[] objArr = this.dense;
        return (objArr == null || i2 < 0 || i2 >= objArr.length) ? super.has(i2, scriptable) : objArr[i2] != Scriptable.NOT_FOUND;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        long j2 = this.length;
        if (j2 > 2147483647L) {
            throw new IllegalStateException();
        }
        int i2 = (int) j2;
        int i3 = 0;
        if (obj == null) {
            while (i3 < i2) {
                if (get(i3) == null) {
                    return i3;
                }
                i3++;
            }
            return -1;
        }
        while (i3 < i2) {
            if (obj.equals(get(i3))) {
                return i3;
            }
            i3++;
        }
        return -1;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        String str2;
        String str3;
        int i3;
        String str4;
        if (i2 == 26) {
            initPrototypeMethod(ARRAY_TAG, i2, SymbolKey.ITERATOR, "[Symbol.iterator]", 0);
            return;
        }
        switch (i2) {
            case 1:
                str = "constructor";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 2:
                str2 = "toString";
                str3 = str2;
                i3 = 0;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 3:
                str2 = "toLocaleString";
                str3 = str2;
                i3 = 0;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 4:
                str2 = "toSource";
                str3 = str2;
                i3 = 0;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 5:
                str = "join";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 6:
                str2 = "reverse";
                str3 = str2;
                i3 = 0;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 7:
                str = "sort";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 8:
                str = "push";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 9:
                str2 = "pop";
                str3 = str2;
                i3 = 0;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 10:
                str2 = "shift";
                str3 = str2;
                i3 = 0;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 11:
                str = "unshift";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 12:
                str4 = "splice";
                i3 = 2;
                str3 = str4;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 13:
                str = "concat";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 14:
                str4 = "slice";
                i3 = 2;
                str3 = str4;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 15:
                str = "indexOf";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 16:
                str = "lastIndexOf";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 17:
                str = "every";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 18:
                str = "filter";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 19:
                str = "forEach";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 20:
                str = "map";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 21:
                str = "some";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 22:
                str = "find";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 23:
                str = "findIndex";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 24:
                str = "reduce";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            case 25:
                str = "reduceRight";
                str3 = str;
                i3 = 1;
                initPrototypeMethod(ARRAY_TAG, i2, str3, (String) null, i3);
                return;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
    }

    @Override // org.mozilla.javascript.ScriptableObject, java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.length == 0;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return listIterator(0);
    }

    @Deprecated
    public long jsGet_length() {
        return getLength();
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        long j2 = this.length;
        if (j2 > 2147483647L) {
            throw new IllegalStateException();
        }
        int i2 = (int) j2;
        if (obj == null) {
            for (int i3 = i2 - 1; i3 >= 0; i3--) {
                if (get(i3) == null) {
                    return i3;
                }
            }
            return -1;
        }
        for (int i4 = i2 - 1; i4 >= 0; i4--) {
            if (obj.equals(get(i4))) {
                return i4;
            }
        }
        return -1;
    }

    @Override // java.util.List
    public ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(String str, Scriptable scriptable, Object obj) {
        super.put(str, scriptable, obj);
        if (scriptable == this) {
            long arrayIndex = toArrayIndex(str);
            if (arrayIndex >= this.length) {
                this.length = arrayIndex + 1;
                this.denseOnly = false;
            }
        }
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public Object set(int i2, Object obj) {
        throw new UnsupportedOperationException();
    }

    void setDenseOnly(boolean z2) {
        if (z2 && !this.denseOnly) {
            throw new IllegalArgumentException();
        }
        this.denseOnly = z2;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdAttributes(int i2, int i3) {
        if (i2 == 1) {
            this.lengthAttr = i3;
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdValue(int i2, Object obj) {
        if (i2 == 1) {
            setLength(obj);
        } else {
            super.setInstanceIdValue(i2, obj);
        }
    }

    @Override // org.mozilla.javascript.ScriptableObject, java.util.List, java.util.Collection
    public int size() {
        long j2 = this.length;
        if (j2 <= 2147483647L) {
            return (int) j2;
        }
        throw new IllegalStateException();
    }

    @Override // java.util.List
    public List subList(int i2, int i3) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return toArray(ScriptRuntime.emptyArgs);
    }

    public static final class ElementComparator implements Comparator<Object> {
        private final Comparator<Object> child;

        public ElementComparator() {
            this.child = NativeArray.STRING_COMPARATOR;
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            Object obj3 = Undefined.instance;
            if (obj == obj3) {
                if (obj2 == obj3) {
                    return 0;
                }
                return obj2 == Scriptable.NOT_FOUND ? -1 : 1;
            }
            Object obj4 = Scriptable.NOT_FOUND;
            if (obj == obj4) {
                return obj2 == obj4 ? 0 : 1;
            }
            if (obj2 == obj4 || obj2 == obj3) {
                return -1;
            }
            return this.child.compare(obj, obj2);
        }

        public ElementComparator(Comparator<Object> comparator) {
            this.child = comparator;
        }
    }

    @Override // java.util.List
    public void add(int i2, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i2, Collection collection) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:79:0x011b  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.findPrototypeId(java.lang.String):int");
    }

    @Override // java.util.List
    public ListIterator listIterator(int i2) {
        long j2 = this.length;
        if (j2 > 2147483647L) {
            throw new IllegalStateException();
        }
        int i3 = (int) j2;
        if (i2 >= 0 && i2 <= i3) {
            return new ListIterator(i2, i3) { // from class: org.mozilla.javascript.NativeArray.2
                int cursor;
                final /* synthetic */ int val$len;
                final /* synthetic */ int val$start;

                {
                    this.val$start = i2;
                    this.val$len = i3;
                    this.cursor = i2;
                }

                @Override // java.util.ListIterator
                public void add(Object obj) {
                    throw new UnsupportedOperationException();
                }

                @Override // java.util.ListIterator, java.util.Iterator
                public boolean hasNext() {
                    return this.cursor < this.val$len;
                }

                @Override // java.util.ListIterator
                public boolean hasPrevious() {
                    return this.cursor > 0;
                }

                @Override // java.util.ListIterator, java.util.Iterator
                public Object next() {
                    int i4 = this.cursor;
                    if (i4 == this.val$len) {
                        throw new NoSuchElementException();
                    }
                    NativeArray nativeArray = NativeArray.this;
                    this.cursor = i4 + 1;
                    return nativeArray.get(i4);
                }

                @Override // java.util.ListIterator
                public int nextIndex() {
                    return this.cursor;
                }

                @Override // java.util.ListIterator
                public Object previous() {
                    int i4 = this.cursor;
                    if (i4 == 0) {
                        throw new NoSuchElementException();
                    }
                    NativeArray nativeArray = NativeArray.this;
                    int i5 = i4 - 1;
                    this.cursor = i5;
                    return nativeArray.get(i5);
                }

                @Override // java.util.ListIterator
                public int previousIndex() {
                    return this.cursor - 1;
                }

                @Override // java.util.ListIterator, java.util.Iterator
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                @Override // java.util.ListIterator
                public void set(Object obj) {
                    throw new UnsupportedOperationException();
                }
            };
        }
        throw new IndexOutOfBoundsException("Index: " + i2);
    }

    @Override // java.util.List
    public Object remove(int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray(Object[] objArr) {
        long j2 = this.length;
        if (j2 > 2147483647L) {
            throw new IllegalStateException();
        }
        int i2 = (int) j2;
        if (objArr.length < i2) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i2);
        }
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = get(i3);
        }
        return objArr;
    }

    private static long toArrayIndex(String str) {
        long arrayIndex = toArrayIndex(ScriptRuntime.toNumber(str));
        if (Long.toString(arrayIndex).equals(str)) {
            return arrayIndex;
        }
        return -1L;
    }

    public Object get(long j2) {
        if (j2 >= 0 && j2 < this.length) {
            Object rawElem = getRawElem(this, j2);
            if (rawElem == Scriptable.NOT_FOUND || rawElem == Undefined.instance) {
                return null;
            }
            return rawElem instanceof Wrapper ? ((Wrapper) rawElem).unwrap() : rawElem;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(int i2, Scriptable scriptable, Object obj) {
        if (scriptable == this && !isSealed() && this.dense != null && i2 >= 0 && (this.denseOnly || !isGetterOrSetter(null, i2, true))) {
            if (!isExtensible() && this.length <= i2) {
                return;
            }
            Object[] objArr = this.dense;
            if (i2 < objArr.length) {
                objArr[i2] = obj;
                long j2 = i2;
                if (this.length <= j2) {
                    this.length = j2 + 1;
                    return;
                }
                return;
            }
            if (this.denseOnly && i2 < objArr.length * GROW_FACTOR && ensureCapacity(i2 + 1)) {
                this.dense[i2] = obj;
                this.length = i2 + 1;
                return;
            }
            this.denseOnly = false;
        }
        super.put(i2, scriptable, obj);
        if (scriptable == this && (this.lengthAttr & 1) == 0) {
            long j3 = i2;
            if (this.length <= j3) {
                this.length = j3 + 1;
            }
        }
    }

    public NativeArray(Object[] objArr) {
        this.lengthAttr = 6;
        this.denseOnly = true;
        this.dense = objArr;
        this.length = objArr.length;
    }

    private static long toArrayIndex(double d2) {
        if (d2 != d2) {
            return -1L;
        }
        long uint32 = ScriptRuntime.toUint32(d2);
        if (uint32 != d2 || uint32 == 4294967295L) {
            return -1L;
        }
        return uint32;
    }

    @Override // java.util.List
    public Object get(int i2) {
        return get(i2);
    }
}
