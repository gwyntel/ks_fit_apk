package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
public class NativeInt8Array extends NativeTypedArrayView<Byte> {
    private static final String CLASS_NAME = "Int8Array";
    private static final long serialVersionUID = -3349419704390398895L;

    public NativeInt8Array() {
    }

    public static void init(Context context, Scriptable scriptable, boolean z2) {
        new NativeInt8Array().exportAsJSClass(5, scriptable, z2);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected NativeTypedArrayView construct(NativeArrayBuffer nativeArrayBuffer, int i2, int i3) {
        return new NativeInt8Array(nativeArrayBuffer, i2, i3);
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
        return checkIndex(i2) ? Undefined.instance : ByteIo.readInt8(this.arrayBuffer.buffer, i2 + this.offset);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_set(int i2, Object obj) {
        if (checkIndex(i2)) {
            return Undefined.instance;
        }
        ByteIo.writeInt8(this.arrayBuffer.buffer, i2 + this.offset, Conversions.toInt8(obj));
        return null;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected NativeTypedArrayView realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeInt8Array) {
            return (NativeInt8Array) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public NativeInt8Array(NativeArrayBuffer nativeArrayBuffer, int i2, int i3) {
        super(nativeArrayBuffer, i2, i3, i3);
    }

    @Override // java.util.List
    public Byte get(int i2) {
        if (checkIndex(i2)) {
            throw new IndexOutOfBoundsException();
        }
        return (Byte) js_get(i2);
    }

    @Override // java.util.List
    public Byte set(int i2, Byte b2) {
        if (checkIndex(i2)) {
            throw new IndexOutOfBoundsException();
        }
        return (Byte) js_set(i2, b2);
    }

    public NativeInt8Array(int i2) {
        this(new NativeArrayBuffer(i2), 0, i2);
    }
}
