package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
public class NativeArrayBuffer extends IdScriptableObject {
    public static final String CLASS_NAME = "ArrayBuffer";
    private static final int ConstructorId_isView = -3;
    private static final byte[] EMPTY_BUF = new byte[0];
    public static final NativeArrayBuffer EMPTY_BUFFER = new NativeArrayBuffer();
    private static final int Id_byteLength = 1;
    private static final int Id_constructor = 1;
    private static final int Id_isView = 3;
    private static final int Id_slice = 2;
    private static final int MAX_INSTANCE_ID = 1;
    private static final int MAX_PROTOTYPE_ID = 3;
    private static final long serialVersionUID = 3110411773054879549L;
    final byte[] buffer;

    public NativeArrayBuffer() {
        this.buffer = EMPTY_BUF;
    }

    public static void init(Context context, Scriptable scriptable, boolean z2) {
        new NativeArrayBuffer().exportAsJSClass(3, scriptable, z2);
    }

    private static boolean isArg(Object[] objArr, int i2) {
        return objArr.length > i2 && !Undefined.instance.equals(objArr[i2]);
    }

    private static NativeArrayBuffer realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeArrayBuffer) {
            return (NativeArrayBuffer) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(CLASS_NAME)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == -3) {
            return Boolean.valueOf(isArg(objArr, 0) && (objArr[0] instanceof NativeArrayBufferView));
        }
        if (iMethodId == 1) {
            return new NativeArrayBuffer(isArg(objArr, 0) ? ScriptRuntime.toInt32(objArr[0]) : 0);
        }
        if (iMethodId != 2) {
            throw new IllegalArgumentException(String.valueOf(iMethodId));
        }
        NativeArrayBuffer nativeArrayBufferRealThis = realThis(scriptable2, idFunctionObject);
        return nativeArrayBufferRealThis.slice(isArg(objArr, 0) ? ScriptRuntime.toInt32(objArr[0]) : 0, isArg(objArr, 1) ? ScriptRuntime.toInt32(objArr[1]) : nativeArrayBufferRealThis.buffer.length);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        addIdFunctionProperty(idFunctionObject, CLASS_NAME, -3, "isView", 1);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        return "byteLength".equals(str) ? IdScriptableObject.instanceIdInfo(5, 1) : super.findInstanceIdInfo(str);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int i2;
        int length = str.length();
        if (length == 5) {
            str2 = "slice";
            i2 = 2;
        } else if (length == 6) {
            str2 = "isView";
            i2 = 3;
        } else if (length == 11) {
            str2 = "constructor";
            i2 = 1;
        } else {
            str2 = null;
            i2 = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i2;
        }
        return 0;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        return i2 == 1 ? "byteLength" : super.getInstanceIdName(i2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        return i2 == 1 ? ScriptRuntime.wrapInt(this.buffer.length) : super.getInstanceIdValue(i2);
    }

    public int getLength() {
        return this.buffer.length;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 1;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        if (i2 == 1) {
            str = "constructor";
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException(String.valueOf(i2));
            }
            str = "slice";
        }
        initPrototypeMethod(CLASS_NAME, i2, str, 1);
    }

    public NativeArrayBuffer slice(int i2, int i3) {
        byte[] bArr = this.buffer;
        int length = bArr.length;
        if (i3 < 0) {
            i3 += bArr.length;
        }
        int iMax = Math.max(0, Math.min(length, i3));
        if (i2 < 0) {
            i2 += this.buffer.length;
        }
        int iMin = Math.min(iMax, Math.max(0, i2));
        int i4 = iMax - iMin;
        NativeArrayBuffer nativeArrayBuffer = new NativeArrayBuffer(i4);
        System.arraycopy(this.buffer, iMin, nativeArrayBuffer.buffer, 0, i4);
        return nativeArrayBuffer;
    }

    public NativeArrayBuffer(int i2) {
        if (i2 < 0) {
            throw ScriptRuntime.constructError("RangeError", "Negative array length " + i2);
        }
        if (i2 == 0) {
            this.buffer = EMPTY_BUF;
        } else {
            this.buffer = new byte[i2];
        }
    }
}
