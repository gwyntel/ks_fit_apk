package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
public class NativeInt16Array extends NativeTypedArrayView<Short> {
    private static final int BYTES_PER_ELEMENT = 2;
    private static final String CLASS_NAME = "Int16Array";
    private static final long serialVersionUID = -8592870435287581398L;

    public NativeInt16Array() {
    }

    public static void init(Context context, Scriptable scriptable, boolean z2) {
        new NativeInt16Array().exportAsJSClass(5, scriptable, z2);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected NativeTypedArrayView construct(NativeArrayBuffer nativeArrayBuffer, int i2, int i3) {
        return new NativeInt16Array(nativeArrayBuffer, i2, i3);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    public int getBytesPerElement() {
        return 2;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_get(int i2) {
        return checkIndex(i2) ? Undefined.instance : ByteIo.readInt16(this.arrayBuffer.buffer, (i2 * 2) + this.offset, false);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_set(int i2, Object obj) {
        if (checkIndex(i2)) {
            return Undefined.instance;
        }
        ByteIo.writeInt16(this.arrayBuffer.buffer, (i2 * 2) + this.offset, Conversions.toInt16(obj), false);
        return null;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected NativeTypedArrayView realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeInt16Array) {
            return (NativeInt16Array) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public NativeInt16Array(NativeArrayBuffer nativeArrayBuffer, int i2, int i3) {
        super(nativeArrayBuffer, i2, i3, i3 * 2);
    }

    @Override // java.util.List
    public Short get(int i2) {
        if (checkIndex(i2)) {
            throw new IndexOutOfBoundsException();
        }
        return (Short) js_get(i2);
    }

    @Override // java.util.List
    public Short set(int i2, Short sh) {
        if (checkIndex(i2)) {
            throw new IndexOutOfBoundsException();
        }
        return (Short) js_set(i2, sh);
    }

    public NativeInt16Array(int i2) {
        this(new NativeArrayBuffer(i2 * 2), 0, i2);
    }
}
