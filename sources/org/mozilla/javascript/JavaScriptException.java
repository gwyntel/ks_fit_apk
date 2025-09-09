package org.mozilla.javascript;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes5.dex */
public class JavaScriptException extends RhinoException {
    static final long serialVersionUID = -7666130513694669293L;
    private Object value;

    @Deprecated
    public JavaScriptException(Object obj) {
        this(obj, "", 0);
    }

    @Override // org.mozilla.javascript.RhinoException
    public String details() {
        Object obj = this.value;
        if (obj == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (obj instanceof NativeError) {
            return obj.toString();
        }
        try {
            return ScriptRuntime.toString(obj);
        } catch (RuntimeException unused) {
            Object obj2 = this.value;
            return obj2 instanceof Scriptable ? ScriptRuntime.defaultObjectToString((Scriptable) obj2) : obj2.toString();
        }
    }

    @Deprecated
    public int getLineNumber() {
        return lineNumber();
    }

    @Deprecated
    public String getSourceName() {
        return sourceName();
    }

    public Object getValue() {
        return this.value;
    }

    public JavaScriptException(Object obj, String str, int i2) {
        recordErrorOrigin(str, i2, null, 0);
        this.value = obj;
        if ((obj instanceof NativeError) && Context.getContext().hasFeature(10)) {
            NativeError nativeError = (NativeError) obj;
            if (!nativeError.has("fileName", nativeError)) {
                nativeError.put("fileName", nativeError, str);
            }
            if (!nativeError.has("lineNumber", nativeError)) {
                nativeError.put("lineNumber", nativeError, Integer.valueOf(i2));
            }
            nativeError.setStackProvider(this);
        }
    }
}
