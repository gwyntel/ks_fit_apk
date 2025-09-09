package org.mozilla.javascript;

import java.lang.reflect.Field;

/* loaded from: classes5.dex */
class FieldAndMethods extends NativeJavaMethod {
    static final long serialVersionUID = -9222428244284796755L;
    Field field;
    Object javaObject;

    FieldAndMethods(Scriptable scriptable, MemberBox[] memberBoxArr, Field field) {
        super(memberBoxArr);
        this.field = field;
        setParentScope(scriptable);
        setPrototype(ScriptableObject.getFunctionPrototype(scriptable));
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) throws IllegalAccessException, IllegalArgumentException {
        if (cls == ScriptRuntime.FunctionClass) {
            return this;
        }
        try {
            Object obj = this.field.get(this.javaObject);
            Class<?> type = this.field.getType();
            Context context = Context.getContext();
            Object objWrap = context.getWrapFactory().wrap(context, this, obj, type);
            return objWrap instanceof Scriptable ? ((Scriptable) objWrap).getDefaultValue(cls) : objWrap;
        } catch (IllegalAccessException unused) {
            throw Context.reportRuntimeError1("msg.java.internal.private", this.field.getName());
        }
    }
}
