package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
public class NativeUint8Array extends NativeTypedArrayView<Integer> {
    private static final String CLASS_NAME = "Uint8Array";
    private static final long serialVersionUID = -3349419704390398895L;

    public NativeUint8Array() {
    }

    public static void init(Context context, Scriptable scriptable, boolean z2) {
        new NativeUint8Array().exportAsJSClass(5, scriptable, z2);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected NativeTypedArrayView construct(NativeArrayBuffer nativeArrayBuffer, int i2, int i3) {
        return new NativeUint8Array(nativeArrayBuffer, i2, i3);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    public int getBytesPerElement() {
        return 1;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_get(int i2) {
        return checkIndex(i2) ? Undefined.instance : ByteIo.readUint8(this.arrayBuffer.buffer, i2 + this.offset);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_set(int i2, Object obj) {
        if (checkIndex(i2)) {
            return Undefined.instance;
        }
        ByteIo.writeUint8(this.arrayBuffer.buffer, i2 + this.offset, Conversions.toUint8(obj));
        return null;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected NativeTypedArrayView realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeUint8Array) {
            return (NativeUint8Array) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public NativeUint8Array(NativeArrayBuffer nativeArrayBuffer, int i2, int i3) {
        super(nativeArrayBuffer, i2, i3, i3);
    }

    @Override // java.util.List
    public Integer get(int i2) {
        if (checkIndex(i2)) {
            throw new IndexOutOfBoundsException();
        }
        return (Integer) js_get(i2);
    }

    @Override // java.util.List
    public Integer set(int i2, Integer num) {
        if (checkIndex(i2)) {
            throw new IndexOutOfBoundsException();
        }
        return (Integer) js_set(i2, num);
    }

    public NativeUint8Array(int i2) {
        this(new NativeArrayBuffer(i2), 0, i2);
    }
}
