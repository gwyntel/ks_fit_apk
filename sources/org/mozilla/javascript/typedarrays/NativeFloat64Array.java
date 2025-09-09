package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
public class NativeFloat64Array extends NativeTypedArrayView<Double> {
    private static final int BYTES_PER_ELEMENT = 8;
    private static final String CLASS_NAME = "Float64Array";
    private static final long serialVersionUID = -1255405650050639335L;

    public NativeFloat64Array() {
    }

    public static void init(Context context, Scriptable scriptable, boolean z2) {
        new NativeFloat64Array().exportAsJSClass(5, scriptable, z2);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected NativeTypedArrayView construct(NativeArrayBuffer nativeArrayBuffer, int i2, int i3) {
        return new NativeFloat64Array(nativeArrayBuffer, i2, i3);
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    public int getBytesPerElement() {
        return 8;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_get(int i2) {
        return checkIndex(i2) ? Undefined.instance : Double.valueOf(Double.longBitsToDouble(ByteIo.readUint64Primitive(this.arrayBuffer.buffer, (i2 * 8) + this.offset, false)));
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_set(int i2, Object obj) {
        if (checkIndex(i2)) {
            return Undefined.instance;
        }
        ByteIo.writeUint64(this.arrayBuffer.buffer, (i2 * 8) + this.offset, Double.doubleToLongBits(ScriptRuntime.toNumber(obj)), false);
        return null;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected NativeTypedArrayView realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeFloat64Array) {
            return (NativeFloat64Array) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public NativeFloat64Array(NativeArrayBuffer nativeArrayBuffer, int i2, int i3) {
        super(nativeArrayBuffer, i2, i3, i3 * 8);
    }

    @Override // java.util.List
    public Double get(int i2) {
        if (checkIndex(i2)) {
            throw new IndexOutOfBoundsException();
        }
        return (Double) js_get(i2);
    }

    @Override // java.util.List
    public Double set(int i2, Double d2) {
        if (checkIndex(i2)) {
            throw new IndexOutOfBoundsException();
        }
        return (Double) js_set(i2, d2);
    }

    public NativeFloat64Array(int i2) {
        this(new NativeArrayBuffer(i2 * 8), 0, i2);
    }
}
