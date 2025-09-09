package org.mozilla.javascript;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes5.dex */
public class NativeJavaMethod extends BaseFunction {
    private static final int PREFERENCE_AMBIGUOUS = 3;
    private static final int PREFERENCE_EQUAL = 0;
    private static final int PREFERENCE_FIRST_ARG = 1;
    private static final int PREFERENCE_SECOND_ARG = 2;
    private static final boolean debug = false;
    static final long serialVersionUID = -3440381785576412928L;
    private String functionName;
    MemberBox[] methods;
    private transient CopyOnWriteArrayList<ResolvedOverload> overloadCache;

    NativeJavaMethod(MemberBox[] memberBoxArr) {
        this.functionName = memberBoxArr[0].getName();
        this.methods = memberBoxArr;
    }

    static int findFunction(Context context, MemberBox[] memberBoxArr, Object[] objArr) throws RuntimeException {
        int i2 = -1;
        if (memberBoxArr.length == 0) {
            return -1;
        }
        int i3 = 0;
        int i4 = 1;
        if (memberBoxArr.length == 1) {
            MemberBox memberBox = memberBoxArr[0];
            Class<?>[] clsArr = memberBox.argTypes;
            int length = clsArr.length;
            if (memberBox.vararg) {
                length--;
                if (length > objArr.length) {
                    return -1;
                }
            } else if (length != objArr.length) {
                return -1;
            }
            for (int i5 = 0; i5 != length; i5++) {
                if (!NativeJavaObject.canConvert(objArr[i5], clsArr[i5])) {
                    return -1;
                }
            }
            return 0;
        }
        int[] iArr = null;
        int i6 = -1;
        int i7 = 0;
        int i8 = 0;
        while (i7 < memberBoxArr.length) {
            MemberBox memberBox2 = memberBoxArr[i7];
            Class<?>[] clsArr2 = memberBox2.argTypes;
            int length2 = clsArr2.length;
            if (!memberBox2.vararg ? length2 == objArr.length : length2 - 1 <= objArr.length) {
                for (int i9 = i3; i9 < length2; i9++) {
                    if (!NativeJavaObject.canConvert(objArr[i9], clsArr2[i9])) {
                        break;
                    }
                }
                if (i6 >= 0) {
                    int i10 = i2;
                    int i11 = i3;
                    int i12 = i11;
                    while (i10 != i8) {
                        MemberBox memberBox3 = memberBoxArr[i10 == i2 ? i6 : iArr[i10]];
                        if (!context.hasFeature(13) || (memberBox3.member().getModifiers() & i4) == (memberBox2.member().getModifiers() & 1)) {
                            int iPreferSignature = preferSignature(objArr, clsArr2, memberBox2.vararg, memberBox3.argTypes, memberBox3.vararg);
                            if (iPreferSignature == 3) {
                                break;
                            }
                            if (iPreferSignature != 1) {
                                if (iPreferSignature != 2) {
                                    if (iPreferSignature != 0) {
                                        Kit.codeBug();
                                    }
                                    if (memberBox3.isStatic() && memberBox3.getDeclaringClass().isAssignableFrom(memberBox2.getDeclaringClass())) {
                                        if (i10 != -1) {
                                            iArr[i10] = i7;
                                        }
                                    }
                                    i4 = 1;
                                }
                                i12++;
                            }
                            i11++;
                        } else if ((memberBox3.member().getModifiers() & i4) == 0) {
                            i11++;
                        } else {
                            i12++;
                        }
                        i10++;
                        i2 = -1;
                        i4 = 1;
                    }
                    int i13 = i8 + 1;
                    if (i11 == i13) {
                        i6 = i7;
                        i4 = 1;
                        i8 = 0;
                    } else if (i12 == i13) {
                        i4 = 1;
                    } else {
                        if (iArr == null) {
                            i4 = 1;
                            iArr = new int[memberBoxArr.length - 1];
                        } else {
                            i4 = 1;
                        }
                        iArr[i8] = i7;
                        i8 = i13;
                    }
                }
                i6 = i7;
                i4 = 1;
            } else {
                break;
                i4 = 1;
            }
            i7++;
            i2 = -1;
            i3 = 0;
        }
        if (i6 < 0) {
            return -1;
        }
        if (i8 == 0) {
            return i6;
        }
        StringBuilder sb = new StringBuilder();
        int i14 = -1;
        while (i14 != i8) {
            int i15 = i14 == -1 ? i6 : iArr[i14];
            sb.append("\n    ");
            sb.append(memberBoxArr[i15].toJavaDeclaration());
            i14++;
        }
        MemberBox memberBox4 = memberBoxArr[i6];
        String name = memberBox4.getName();
        String name2 = memberBox4.getDeclaringClass().getName();
        if (memberBoxArr[0].isCtor()) {
            throw Context.reportRuntimeError3("msg.constructor.ambiguous", name, scriptSignature(objArr), sb.toString());
        }
        throw Context.reportRuntimeError4("msg.method.ambiguous", name2, name, scriptSignature(objArr), sb.toString());
    }

    private static int preferSignature(Object[] objArr, Class<?>[] clsArr, boolean z2, Class<?>[] clsArr2, boolean z3) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < objArr.length) {
            int i4 = 1;
            Class<?> cls = (!z2 || i2 < clsArr.length) ? clsArr[i2] : clsArr[clsArr.length - 1];
            Class<?> cls2 = (!z3 || i2 < clsArr2.length) ? clsArr2[i2] : clsArr2[clsArr2.length - 1];
            if (cls != cls2) {
                Object obj = objArr[i2];
                int conversionWeight = NativeJavaObject.getConversionWeight(obj, cls);
                int conversionWeight2 = NativeJavaObject.getConversionWeight(obj, cls2);
                if (conversionWeight >= conversionWeight2) {
                    if (conversionWeight <= conversionWeight2) {
                        if (conversionWeight == 0) {
                            if (!cls.isAssignableFrom(cls2)) {
                                if (!cls2.isAssignableFrom(cls)) {
                                }
                            }
                        }
                        i4 = 3;
                    }
                    i4 = 2;
                }
                i3 |= i4;
                if (i3 == 3) {
                    break;
                }
            }
            i2++;
        }
        return i3;
    }

    private static void printDebug(String str, MemberBox memberBox, Object[] objArr) {
    }

    static String scriptSignature(Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 != objArr.length; i2++) {
            Object obj = objArr[i2];
            String name = obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj instanceof Boolean ? TypedValues.Custom.S_BOOLEAN : obj instanceof String ? "string" : obj instanceof Number ? "number" : obj instanceof Scriptable ? obj instanceof Undefined ? "undefined" : obj instanceof Wrapper ? ((Wrapper) obj).unwrap().getClass().getName() : obj instanceof Function ? "function" : "object" : JavaMembers.javaSignature(obj.getClass());
            if (i2 != 0) {
                sb.append(',');
            }
            sb.append(name);
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object[] objArr2;
        Object obj;
        Object objJsToJava;
        if (this.methods.length == 0) {
            throw new RuntimeException("No methods defined for call");
        }
        int iFindCachedFunction = findCachedFunction(context, objArr);
        int i2 = 0;
        if (iFindCachedFunction < 0) {
            throw Context.reportRuntimeError1("msg.java.no_such_method", this.methods[0].method().getDeclaringClass().getName() + '.' + getFunctionName() + '(' + scriptSignature(objArr) + ')');
        }
        MemberBox memberBox = this.methods[iFindCachedFunction];
        Class<?>[] clsArr = memberBox.argTypes;
        if (memberBox.vararg) {
            objArr2 = new Object[clsArr.length];
            for (int i3 = 0; i3 < clsArr.length - 1; i3++) {
                objArr2[i3] = Context.jsToJava(objArr[i3], clsArr[i3]);
            }
            if (objArr.length == clsArr.length && (objArr[objArr.length - 1] == null || (objArr[objArr.length - 1] instanceof NativeArray) || (objArr[objArr.length - 1] instanceof NativeJavaArray))) {
                objJsToJava = Context.jsToJava(objArr[objArr.length - 1], clsArr[clsArr.length - 1]);
            } else {
                Class<?> componentType = clsArr[clsArr.length - 1].getComponentType();
                Object objNewInstance = Array.newInstance(componentType, (objArr.length - clsArr.length) + 1);
                while (i2 < Array.getLength(objNewInstance)) {
                    Array.set(objNewInstance, i2, Context.jsToJava(objArr[(clsArr.length - 1) + i2], componentType));
                    i2++;
                }
                objJsToJava = objNewInstance;
            }
            objArr2[clsArr.length - 1] = objJsToJava;
        } else {
            objArr2 = objArr;
            while (i2 < objArr2.length) {
                Object obj2 = objArr2[i2];
                Object objJsToJava2 = Context.jsToJava(obj2, clsArr[i2]);
                if (objJsToJava2 != obj2) {
                    if (objArr == objArr2) {
                        objArr2 = (Object[]) objArr2.clone();
                    }
                    objArr2[i2] = objJsToJava2;
                }
                i2++;
            }
        }
        if (!memberBox.isStatic()) {
            Class<?> declaringClass = memberBox.getDeclaringClass();
            for (Scriptable prototype = scriptable2; prototype != null; prototype = prototype.getPrototype()) {
                if (prototype instanceof Wrapper) {
                    Object objUnwrap = ((Wrapper) prototype).unwrap();
                    if (declaringClass.isInstance(objUnwrap)) {
                        obj = objUnwrap;
                    }
                }
            }
            throw Context.reportRuntimeError3("msg.nonjava.method", getFunctionName(), ScriptRuntime.toString(scriptable2), declaringClass.getName());
        }
        obj = null;
        Object objInvoke = memberBox.invoke(obj, objArr2);
        Class<?> returnType = memberBox.method().getReturnType();
        Object objWrap = context.getWrapFactory().wrap(context, scriptable, objInvoke, returnType);
        return (objWrap == null && returnType == Void.TYPE) ? Undefined.instance : objWrap;
    }

    @Override // org.mozilla.javascript.BaseFunction
    String decompile(int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        boolean z2 = (i3 & 1) != 0;
        if (!z2) {
            sb.append("function ");
            sb.append(getFunctionName());
            sb.append("() {");
        }
        sb.append("/*\n");
        sb.append(toString());
        sb.append(z2 ? "*/\n" : "*/}\n");
        return sb.toString();
    }

    int findCachedFunction(Context context, Object[] objArr) throws RuntimeException {
        MemberBox[] memberBoxArr = this.methods;
        if (memberBoxArr.length <= 1) {
            return findFunction(context, memberBoxArr, objArr);
        }
        CopyOnWriteArrayList<ResolvedOverload> copyOnWriteArrayList = this.overloadCache;
        if (copyOnWriteArrayList != null) {
            Iterator<ResolvedOverload> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                ResolvedOverload next = it.next();
                if (next.matches(objArr)) {
                    return next.index;
                }
            }
        } else {
            this.overloadCache = new CopyOnWriteArrayList<>();
        }
        int iFindFunction = findFunction(context, this.methods, objArr);
        if (this.overloadCache.size() < this.methods.length * 2) {
            synchronized (this.overloadCache) {
                try {
                    ResolvedOverload resolvedOverload = new ResolvedOverload(objArr, iFindFunction);
                    if (!this.overloadCache.contains(resolvedOverload)) {
                        this.overloadCache.add(0, resolvedOverload);
                    }
                } finally {
                }
            }
        }
        return iFindFunction;
    }

    @Override // org.mozilla.javascript.BaseFunction
    public String getFunctionName() {
        return this.functionName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int length = this.methods.length;
        for (int i2 = 0; i2 != length; i2++) {
            if (this.methods[i2].isMethod()) {
                Method method = this.methods[i2].method();
                sb.append(JavaMembers.javaSignature(method.getReturnType()));
                sb.append(' ');
                sb.append(method.getName());
            } else {
                sb.append(this.methods[i2].getName());
            }
            sb.append(JavaMembers.liveConnectSignature(this.methods[i2].argTypes));
            sb.append('\n');
        }
        return sb.toString();
    }

    NativeJavaMethod(MemberBox[] memberBoxArr, String str) {
        this.functionName = str;
        this.methods = memberBoxArr;
    }

    NativeJavaMethod(MemberBox memberBox, String str) {
        this.functionName = str;
        this.methods = new MemberBox[]{memberBox};
    }

    public NativeJavaMethod(Method method, String str) {
        this(new MemberBox(method), str);
    }
}
