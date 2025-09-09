package org.mozilla.javascript;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.tekartik.sqflite.Constant;

/* loaded from: classes5.dex */
public class ArrowFunction extends BaseFunction {
    static final long serialVersionUID = -7377989503697220633L;
    private final Scriptable boundThis;
    private final Callable targetFunction;

    public ArrowFunction(Context context, Scriptable scriptable, Callable callable, Scriptable scriptable2) {
        this.targetFunction = callable;
        this.boundThis = scriptable2;
        ScriptRuntime.setFunctionProtoAndParent(this, scriptable);
        Object objTypeErrorThrower = ScriptRuntime.typeErrorThrower();
        NativeObject nativeObject = new NativeObject();
        nativeObject.put(TmpConstant.PROPERTY_IDENTIFIER_GET, nativeObject, objTypeErrorThrower);
        nativeObject.put(TmpConstant.PROPERTY_IDENTIFIER_SET, nativeObject, objTypeErrorThrower);
        Object obj = Boolean.FALSE;
        nativeObject.put("enumerable", nativeObject, obj);
        nativeObject.put("configurable", nativeObject, obj);
        nativeObject.preventExtensions();
        defineOwnProperty(context, "caller", nativeObject, false);
        defineOwnProperty(context, Constant.PARAM_SQL_ARGUMENTS, nativeObject, false);
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Scriptable topCallScope = this.boundThis;
        if (topCallScope == null) {
            topCallScope = ScriptRuntime.getTopCallScope(context);
        }
        return this.targetFunction.call(context, scriptable, topCallScope, objArr);
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function
    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        throw ScriptRuntime.typeError1("msg.not.ctor", decompile(0, 0));
    }

    @Override // org.mozilla.javascript.BaseFunction
    String decompile(int i2, int i3) {
        Callable callable = this.targetFunction;
        return callable instanceof BaseFunction ? ((BaseFunction) callable).decompile(i2, i3) : super.decompile(i2, i3);
    }

    @Override // org.mozilla.javascript.BaseFunction
    public int getLength() {
        Callable callable = this.targetFunction;
        if (callable instanceof BaseFunction) {
            return ((BaseFunction) callable).getLength();
        }
        return 0;
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean hasInstance(Scriptable scriptable) {
        Callable callable = this.targetFunction;
        if (callable instanceof Function) {
            return ((Function) callable).hasInstance(scriptable);
        }
        throw ScriptRuntime.typeError0("msg.not.ctor");
    }
}
