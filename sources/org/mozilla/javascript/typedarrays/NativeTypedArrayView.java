package org.mozilla.javascript.typedarrays;

import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ExternalArrayData;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeArrayIterator;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Symbol;
import org.mozilla.javascript.SymbolKey;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
public abstract class NativeTypedArrayView<T> extends NativeArrayBufferView implements List<T>, RandomAccess, ExternalArrayData {
    private static final int Id_BYTES_PER_ELEMENT = 11;
    private static final int Id_constructor = 1;
    private static final int Id_get = 2;
    private static final int Id_length = 10;
    private static final int Id_set = 3;
    private static final int Id_subarray = 4;
    private static final int MAX_INSTANCE_ID = 11;
    protected static final int MAX_PROTOTYPE_ID = 5;
    private static final int SymbolId_iterator = 5;
    protected final int length;

    protected NativeTypedArrayView() {
        this.length = 0;
    }

    private NativeTypedArrayView js_constructor(Context context, Scriptable scriptable, Object[] objArr) {
        if (!NativeArrayBufferView.isArg(objArr, 0)) {
            return construct(NativeArrayBuffer.EMPTY_BUFFER, 0, 0);
        }
        Object obj = objArr[0];
        if ((obj instanceof Number) || (obj instanceof String)) {
            int int32 = ScriptRuntime.toInt32(obj);
            return construct(makeArrayBuffer(context, scriptable, getBytesPerElement() * int32), 0, int32);
        }
        if (obj instanceof NativeTypedArrayView) {
            NativeTypedArrayView nativeTypedArrayView = (NativeTypedArrayView) obj;
            NativeTypedArrayView nativeTypedArrayViewConstruct = construct(makeArrayBuffer(context, scriptable, nativeTypedArrayView.length * getBytesPerElement()), 0, nativeTypedArrayView.length);
            while (int32 < nativeTypedArrayView.length) {
                nativeTypedArrayViewConstruct.js_set(int32, nativeTypedArrayView.js_get(int32));
                int32++;
            }
            return nativeTypedArrayViewConstruct;
        }
        if (!(obj instanceof NativeArrayBuffer)) {
            if (!(obj instanceof NativeArray)) {
                throw ScriptRuntime.constructError("Error", "invalid argument");
            }
            List list = (List) obj;
            NativeTypedArrayView nativeTypedArrayViewConstruct2 = construct(makeArrayBuffer(context, scriptable, list.size() * getBytesPerElement()), 0, list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                nativeTypedArrayViewConstruct2.js_set(int32, it.next());
                int32++;
            }
            return nativeTypedArrayViewConstruct2;
        }
        NativeArrayBuffer nativeArrayBuffer = (NativeArrayBuffer) obj;
        int32 = NativeArrayBufferView.isArg(objArr, 1) ? ScriptRuntime.toInt32(objArr[1]) : 0;
        int int322 = NativeArrayBufferView.isArg(objArr, 2) ? ScriptRuntime.toInt32(objArr[2]) * getBytesPerElement() : nativeArrayBuffer.getLength() - int32;
        if (int32 >= 0) {
            byte[] bArr = nativeArrayBuffer.buffer;
            if (int32 <= bArr.length) {
                if (int322 < 0 || int32 + int322 > bArr.length) {
                    throw ScriptRuntime.constructError("RangeError", "length out of range");
                }
                if (int32 % getBytesPerElement() != 0) {
                    throw ScriptRuntime.constructError("RangeError", "offset must be a multiple of the byte size");
                }
                if (int322 % getBytesPerElement() == 0) {
                    return construct(nativeArrayBuffer, int32, int322 / getBytesPerElement());
                }
                throw ScriptRuntime.constructError("RangeError", "offset and buffer must be a multiple of the byte size");
            }
        }
        throw ScriptRuntime.constructError("RangeError", "offset out of range");
    }

    private Object js_subarray(Context context, Scriptable scriptable, int i2, int i3) {
        if (i2 < 0) {
            i2 += this.length;
        }
        if (i3 < 0) {
            i3 += this.length;
        }
        int iMax = Math.max(0, i2);
        int iMax2 = Math.max(0, Math.min(this.length, i3) - iMax);
        return context.newObject(scriptable, getClassName(), new Object[]{this.arrayBuffer, Integer.valueOf(Math.min(iMax * getBytesPerElement(), this.arrayBuffer.getLength())), Integer.valueOf(iMax2)});
    }

    private NativeArrayBuffer makeArrayBuffer(Context context, Scriptable scriptable, int i2) {
        return (NativeArrayBuffer) context.newObject(scriptable, NativeArrayBuffer.CLASS_NAME, new Object[]{Integer.valueOf(i2)});
    }

    private void setRange(NativeTypedArrayView nativeTypedArrayView, int i2) {
        int i3 = this.length;
        if (i2 >= i3) {
            throw ScriptRuntime.constructError("RangeError", "offset out of range");
        }
        int i4 = nativeTypedArrayView.length;
        if (i4 > i3 - i2) {
            throw ScriptRuntime.constructError("RangeError", "source array too long");
        }
        int i5 = 0;
        if (nativeTypedArrayView.arrayBuffer != this.arrayBuffer) {
            while (i5 < nativeTypedArrayView.length) {
                js_set(i5 + i2, nativeTypedArrayView.js_get(i5));
                i5++;
            }
            return;
        }
        Object[] objArr = new Object[i4];
        for (int i6 = 0; i6 < nativeTypedArrayView.length; i6++) {
            objArr[i6] = nativeTypedArrayView.js_get(i6);
        }
        while (i5 < nativeTypedArrayView.length) {
            js_set(i5 + i2, objArr[i5]);
            i5++;
        }
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(T t2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    protected boolean checkIndex(int i2) {
        return i2 < 0 || i2 >= this.length;
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException();
    }

    protected abstract NativeTypedArrayView construct(NativeArrayBuffer nativeArrayBuffer, int i2, int i3);

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void delete(int i2) {
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        try {
            NativeTypedArrayView nativeTypedArrayView = (NativeTypedArrayView) obj;
            if (this.length != nativeTypedArrayView.length) {
                return false;
            }
            for (int i2 = 0; i2 < this.length; i2++) {
                if (!js_get(i2).equals(nativeTypedArrayView.js_get(i2))) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(getClassName())) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == 1) {
            return js_constructor(context, scriptable, objArr);
        }
        if (iMethodId == 2) {
            if (objArr.length > 0) {
                return realThis(scriptable2, idFunctionObject).js_get(ScriptRuntime.toInt32(objArr[0]));
            }
            throw ScriptRuntime.constructError("Error", "invalid arguments");
        }
        if (iMethodId != 3) {
            if (iMethodId != 4) {
                if (iMethodId == 5) {
                    return new NativeArrayIterator(scriptable, scriptable2);
                }
                throw new IllegalArgumentException(String.valueOf(iMethodId));
            }
            if (objArr.length <= 0) {
                throw ScriptRuntime.constructError("Error", "invalid arguments");
            }
            NativeTypedArrayView nativeTypedArrayViewRealThis = realThis(scriptable2, idFunctionObject);
            return nativeTypedArrayViewRealThis.js_subarray(context, scriptable, ScriptRuntime.toInt32(objArr[0]), NativeArrayBufferView.isArg(objArr, 1) ? ScriptRuntime.toInt32(objArr[1]) : nativeTypedArrayViewRealThis.length);
        }
        if (objArr.length > 0) {
            NativeTypedArrayView nativeTypedArrayViewRealThis2 = realThis(scriptable2, idFunctionObject);
            Object obj = objArr[0];
            if (obj instanceof NativeTypedArrayView) {
                nativeTypedArrayViewRealThis2.setRange((NativeTypedArrayView) objArr[0], NativeArrayBufferView.isArg(objArr, 1) ? ScriptRuntime.toInt32(objArr[1]) : 0);
                return Undefined.instance;
            }
            if (obj instanceof NativeArray) {
                nativeTypedArrayViewRealThis2.setRange((NativeArray) objArr[0], NativeArrayBufferView.isArg(objArr, 1) ? ScriptRuntime.toInt32(objArr[1]) : 0);
                return Undefined.instance;
            }
            if (obj instanceof Scriptable) {
                return Undefined.instance;
            }
            if (NativeArrayBufferView.isArg(objArr, 2)) {
                return nativeTypedArrayViewRealThis2.js_set(ScriptRuntime.toInt32(objArr[0]), objArr[1]);
            }
        }
        throw ScriptRuntime.constructError("Error", "invalid arguments");
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        idFunctionObject.put("BYTES_PER_ELEMENT", idFunctionObject, ScriptRuntime.wrapInt(getBytesPerElement()));
    }

    @Override // org.mozilla.javascript.typedarrays.NativeArrayBufferView, org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        String str2;
        int i2;
        int length = str.length();
        if (length == 6) {
            str2 = SessionDescription.ATTR_LENGTH;
            i2 = 10;
        } else if (length == 17) {
            str2 = "BYTES_PER_ELEMENT";
            i2 = 11;
        } else {
            str2 = null;
            i2 = 0;
        }
        int i3 = (str2 == null || str2 == str || str2.equals(str)) ? i2 : 0;
        return i3 == 0 ? super.findInstanceIdInfo(str) : IdScriptableObject.instanceIdInfo(5, i3);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(Symbol symbol) {
        return SymbolKey.ITERATOR.equals(symbol) ? 5 : 0;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object get(int i2, Scriptable scriptable) {
        return js_get(i2);
    }

    @Override // org.mozilla.javascript.ExternalArrayData
    public Object getArrayElement(int i2) {
        return js_get(i2);
    }

    @Override // org.mozilla.javascript.ExternalArrayData
    public int getArrayLength() {
        return this.length;
    }

    public abstract int getBytesPerElement();

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object[] getIds() {
        Object[] objArr = new Object[this.length];
        for (int i2 = 0; i2 < this.length; i2++) {
            objArr[i2] = Integer.valueOf(i2);
        }
        return objArr;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeArrayBufferView, org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        return i2 != 10 ? i2 != 11 ? super.getInstanceIdName(i2) : "BYTES_PER_ELEMENT" : SessionDescription.ATTR_LENGTH;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeArrayBufferView, org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        return i2 != 10 ? i2 != 11 ? super.getInstanceIdValue(i2) : ScriptRuntime.wrapInt(getBytesPerElement()) : ScriptRuntime.wrapInt(this.length);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeArrayBufferView, org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 11;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean has(int i2, Scriptable scriptable) {
        return i2 > 0 && i2 < this.length;
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        int iHashCode = 0;
        for (int i2 = 0; i2 < this.length; i2++) {
            iHashCode += js_get(i2).hashCode();
        }
        return iHashCode;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        for (int i2 = 0; i2 < this.length; i2++) {
            if (obj.equals(js_get(i2))) {
                return i2;
            }
        }
        return -1;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        int i3;
        String str2;
        String str3;
        if (i2 == 5) {
            initPrototypeMethod(getClassName(), i2, SymbolKey.ITERATOR, "[Symbol.iterator]", 0);
            return;
        }
        if (i2 == 1) {
            str = "constructor";
        } else {
            if (i2 != 2) {
                if (i2 == 3) {
                    str3 = TmpConstant.PROPERTY_IDENTIFIER_SET;
                } else {
                    if (i2 != 4) {
                        throw new IllegalArgumentException(String.valueOf(i2));
                    }
                    str3 = "subarray";
                }
                str2 = str3;
                i3 = 2;
                initPrototypeMethod(getClassName(), i2, str2, (String) null, i3);
            }
            str = TmpConstant.PROPERTY_IDENTIFIER_GET;
        }
        i3 = 1;
        str2 = str;
        initPrototypeMethod(getClassName(), i2, str2, (String) null, i3);
    }

    @Override // org.mozilla.javascript.ScriptableObject, java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.length == 0;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return new NativeTypedArrayIterator(this, 0);
    }

    protected abstract Object js_get(int i2);

    protected abstract Object js_set(int i2, Object obj);

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        for (int i2 = this.length - 1; i2 >= 0; i2--) {
            if (obj.equals(js_get(i2))) {
                return i2;
            }
        }
        return -1;
    }

    @Override // java.util.List
    public ListIterator<T> listIterator() {
        return new NativeTypedArrayIterator(this, 0);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(int i2, Scriptable scriptable, Object obj) {
        js_set(i2, obj);
    }

    protected abstract NativeTypedArrayView realThis(Scriptable scriptable, IdFunctionObject idFunctionObject);

    @Override // java.util.List
    public T remove(int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // org.mozilla.javascript.ExternalArrayData
    public void setArrayElement(int i2, Object obj) {
        js_set(i2, obj);
    }

    @Override // org.mozilla.javascript.ScriptableObject, java.util.List, java.util.Collection
    public int size() {
        return this.length;
    }

    @Override // java.util.List
    public List<T> subList(int i2, int i3) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        Object[] objArr = new Object[this.length];
        for (int i2 = 0; i2 < this.length; i2++) {
            objArr[i2] = js_get(i2);
        }
        return objArr;
    }

    @Override // java.util.List
    public void add(int i2, T t2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i2, Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int length = str.length();
        int i2 = 1;
        if (length != 3) {
            if (length == 8) {
                str2 = "subarray";
                i2 = 4;
            } else if (length == 11) {
                str2 = "constructor";
            }
            if (str2 != null || str2 == str || str2.equals(str)) {
                return i2;
            }
            return 0;
        }
        char cCharAt = str.charAt(0);
        if (cCharAt == 'g') {
            if (str.charAt(2) == 't' && str.charAt(1) == 'e') {
                return 2;
            }
        } else if (cCharAt == 's' && str.charAt(2) == 't' && str.charAt(1) == 'e') {
            return 3;
        }
        str2 = null;
        i2 = 0;
        if (str2 != null) {
        }
        return i2;
    }

    @Override // java.util.List
    public ListIterator<T> listIterator(int i2) {
        if (checkIndex(i2)) {
            throw new IndexOutOfBoundsException();
        }
        return new NativeTypedArrayIterator(this, i2);
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    protected NativeTypedArrayView(NativeArrayBuffer nativeArrayBuffer, int i2, int i3, int i4) {
        super(nativeArrayBuffer, i2, i4);
        this.length = i3;
    }

    @Override // java.util.List, java.util.Collection
    public <U> U[] toArray(U[] uArr) {
        if (uArr.length < this.length) {
            uArr = (U[]) ((Object[]) Array.newInstance(uArr.getClass().getComponentType(), this.length));
        }
        for (int i2 = 0; i2 < this.length; i2++) {
            try {
                uArr[i2] = js_get(i2);
            } catch (ClassCastException unused) {
                throw new ArrayStoreException();
            }
        }
        return uArr;
    }

    private void setRange(NativeArray nativeArray, int i2) {
        if (i2 <= this.length) {
            if (nativeArray.size() + i2 <= this.length) {
                Iterator it = nativeArray.iterator();
                while (it.hasNext()) {
                    js_set(i2, it.next());
                    i2++;
                }
                return;
            }
            throw ScriptRuntime.constructError("RangeError", "offset + length out of range");
        }
        throw ScriptRuntime.constructError("RangeError", "offset out of range");
    }
}
