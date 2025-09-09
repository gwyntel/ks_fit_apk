package org.mozilla.javascript;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
class JavaMembers {
    private Class<?> cl;
    NativeJavaMethod ctors;
    private Map<String, FieldAndMethods> fieldAndMethods;
    private Map<String, Object> members;
    private Map<String, FieldAndMethods> staticFieldAndMethods;
    private Map<String, Object> staticMembers;

    JavaMembers(Scriptable scriptable, Class<?> cls) {
        this(scriptable, cls, false);
    }

    private static Method[] discoverAccessibleMethods(Class<?> cls, boolean z2, boolean z3) throws SecurityException {
        HashMap map = new HashMap();
        discoverAccessibleMethods(cls, map, z2, z3);
        return (Method[]) map.values().toArray(new Method[map.size()]);
    }

    private static MemberBox extractGetMethod(MemberBox[] memberBoxArr, boolean z2) {
        for (MemberBox memberBox : memberBoxArr) {
            if (memberBox.argTypes.length == 0 && (!z2 || memberBox.isStatic())) {
                if (memberBox.method().getReturnType() != Void.TYPE) {
                    return memberBox;
                }
                return null;
            }
        }
        return null;
    }

    private static MemberBox extractSetMethod(Class<?> cls, MemberBox[] memberBoxArr, boolean z2) throws RuntimeException {
        for (int i2 = 1; i2 <= 2; i2++) {
            for (MemberBox memberBox : memberBoxArr) {
                if (!z2 || memberBox.isStatic()) {
                    Class<?>[] clsArr = memberBox.argTypes;
                    if (clsArr.length != 1) {
                        continue;
                    } else if (i2 != 1) {
                        if (i2 != 2) {
                            Kit.codeBug();
                        }
                        if (clsArr[0].isAssignableFrom(cls)) {
                            return memberBox;
                        }
                    } else if (clsArr[0] == cls) {
                        return memberBox;
                    }
                }
            }
        }
        return null;
    }

    private MemberBox findExplicitFunction(String str, boolean z2) {
        MemberBox[] memberBoxArr;
        int iIndexOf = str.indexOf(40);
        if (iIndexOf < 0) {
            return null;
        }
        Map<String, Object> map = z2 ? this.staticMembers : this.members;
        if (z2 && iIndexOf == 0) {
            memberBoxArr = this.ctors.methods;
        } else {
            String strSubstring = str.substring(0, iIndexOf);
            Object obj = map.get(strSubstring);
            if (!z2 && obj == null) {
                obj = this.staticMembers.get(strSubstring);
            }
            memberBoxArr = obj instanceof NativeJavaMethod ? ((NativeJavaMethod) obj).methods : null;
        }
        if (memberBoxArr != null) {
            for (MemberBox memberBox : memberBoxArr) {
                String strLiveConnectSignature = liveConnectSignature(memberBox.argTypes);
                if (strLiveConnectSignature.length() + iIndexOf == str.length() && str.regionMatches(iIndexOf, strLiveConnectSignature, 0, strLiveConnectSignature.length())) {
                    return memberBox;
                }
            }
        }
        return null;
    }

    private MemberBox findGetter(boolean z2, Map<String, Object> map, String str, String str2) {
        String strConcat = str.concat(str2);
        if (!map.containsKey(strConcat)) {
            return null;
        }
        Object obj = map.get(strConcat);
        if (obj instanceof NativeJavaMethod) {
            return extractGetMethod(((NativeJavaMethod) obj).methods, z2);
        }
        return null;
    }

    private Constructor<?>[] getAccessibleConstructors(boolean z2) throws SecurityException {
        Class<?> cls;
        if (z2 && (cls = this.cl) != ScriptRuntime.ClassClass) {
            try {
                Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
                AccessibleObject.setAccessible(declaredConstructors, true);
                return declaredConstructors;
            } catch (SecurityException unused) {
                Context.reportWarning("Could not access constructor  of class " + this.cl.getName() + " due to lack of privileges.");
            }
        }
        return this.cl.getConstructors();
    }

    private Field[] getAccessibleFields(boolean z2, boolean z3) throws SecurityException {
        if (z3 || z2) {
            try {
                ArrayList arrayList = new ArrayList();
                for (Class<?> superclass = this.cl; superclass != null; superclass = superclass.getSuperclass()) {
                    for (Field field : superclass.getDeclaredFields()) {
                        int modifiers = field.getModifiers();
                        if (z3 || Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                            if (!field.isAccessible()) {
                                field.setAccessible(true);
                            }
                            arrayList.add(field);
                        }
                    }
                }
                return (Field[]) arrayList.toArray(new Field[arrayList.size()]);
            } catch (SecurityException unused) {
            }
        }
        return this.cl.getFields();
    }

    private Object getExplicitFunction(Scriptable scriptable, String str, Object obj, boolean z2) {
        Map<String, Object> map = z2 ? this.staticMembers : this.members;
        MemberBox memberBoxFindExplicitFunction = findExplicitFunction(str, z2);
        if (memberBoxFindExplicitFunction == null) {
            return null;
        }
        Scriptable functionPrototype = ScriptableObject.getFunctionPrototype(scriptable);
        if (memberBoxFindExplicitFunction.isCtor()) {
            NativeJavaConstructor nativeJavaConstructor = new NativeJavaConstructor(memberBoxFindExplicitFunction);
            nativeJavaConstructor.setPrototype(functionPrototype);
            map.put(str, nativeJavaConstructor);
            return nativeJavaConstructor;
        }
        Object obj2 = map.get(memberBoxFindExplicitFunction.getName());
        if (!(obj2 instanceof NativeJavaMethod) || ((NativeJavaMethod) obj2).methods.length <= 1) {
            return obj2;
        }
        NativeJavaMethod nativeJavaMethod = new NativeJavaMethod(memberBoxFindExplicitFunction, str);
        nativeJavaMethod.setPrototype(functionPrototype);
        map.put(str, nativeJavaMethod);
        return nativeJavaMethod;
    }

    static String javaSignature(Class<?> cls) {
        if (!cls.isArray()) {
            return cls.getName();
        }
        int i2 = 0;
        do {
            i2++;
            cls = cls.getComponentType();
        } while (cls.isArray());
        String name = cls.getName();
        if (i2 == 1) {
            return name.concat("[]");
        }
        StringBuilder sb = new StringBuilder(name.length() + (i2 * 2));
        sb.append(name);
        while (i2 != 0) {
            i2--;
            sb.append("[]");
        }
        return sb.toString();
    }

    static String liveConnectSignature(Class<?>[] clsArr) {
        int length = clsArr.length;
        if (length == 0) {
            return "()";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (int i2 = 0; i2 != length; i2++) {
            if (i2 != 0) {
                sb.append(',');
            }
            sb.append(javaSignature(clsArr[i2]));
        }
        sb.append(')');
        return sb.toString();
    }

    static JavaMembers lookupClass(Scriptable scriptable, Class<?> cls, Class<?> cls2, boolean z2) {
        ClassCache classCache = ClassCache.get(scriptable);
        Map<Class<?>, JavaMembers> classCacheMap = classCache.getClassCacheMap();
        Class<?> cls3 = cls;
        while (true) {
            JavaMembers javaMembers = classCacheMap.get(cls3);
            if (javaMembers != null) {
                if (cls3 != cls) {
                    classCacheMap.put(cls, javaMembers);
                }
                return javaMembers;
            }
            try {
                JavaMembers javaMembers2 = new JavaMembers(classCache.getAssociatedScope(), cls3, z2);
                if (classCache.isCachingEnabled()) {
                    classCacheMap.put(cls3, javaMembers2);
                    if (cls3 != cls) {
                        classCacheMap.put(cls, javaMembers2);
                    }
                }
                return javaMembers2;
            } catch (SecurityException e2) {
                if (cls2 == null || !cls2.isInterface()) {
                    Class<?> superclass = cls3.getSuperclass();
                    if (superclass == null) {
                        if (!cls3.isInterface()) {
                            throw e2;
                        }
                        superclass = ScriptRuntime.ObjectClass;
                    }
                    cls3 = superclass;
                } else {
                    cls3 = cls2;
                    cls2 = null;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0242  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void reflect(org.mozilla.javascript.Scriptable r17, boolean r18, boolean r19) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 659
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.JavaMembers.reflect(org.mozilla.javascript.Scriptable, boolean, boolean):void");
    }

    Object get(Scriptable scriptable, String str, Object obj, boolean z2) throws IllegalAccessException, IllegalArgumentException {
        Object objInvoke;
        Class<?> type;
        Object explicitFunction = (z2 ? this.staticMembers : this.members).get(str);
        if (!z2 && explicitFunction == null) {
            explicitFunction = this.staticMembers.get(str);
        }
        if (explicitFunction == null && (explicitFunction = getExplicitFunction(scriptable, str, obj, z2)) == null) {
            return Scriptable.NOT_FOUND;
        }
        if (explicitFunction instanceof Scriptable) {
            return explicitFunction;
        }
        Context context = Context.getContext();
        try {
            if (explicitFunction instanceof BeanProperty) {
                BeanProperty beanProperty = (BeanProperty) explicitFunction;
                MemberBox memberBox = beanProperty.getter;
                if (memberBox == null) {
                    return Scriptable.NOT_FOUND;
                }
                objInvoke = memberBox.invoke(obj, Context.emptyArgs);
                type = beanProperty.getter.method().getReturnType();
            } else {
                Field field = (Field) explicitFunction;
                if (z2) {
                    obj = null;
                }
                objInvoke = field.get(obj);
                type = field.getType();
            }
            return context.getWrapFactory().wrap(context, ScriptableObject.getTopLevelScope(scriptable), objInvoke, type);
        } catch (Exception e2) {
            throw Context.throwAsScriptRuntimeEx(e2);
        }
    }

    Map<String, FieldAndMethods> getFieldAndMethodsObjects(Scriptable scriptable, Object obj, boolean z2) {
        Map<String, FieldAndMethods> map = z2 ? this.staticFieldAndMethods : this.fieldAndMethods;
        if (map == null) {
            return null;
        }
        HashMap map2 = new HashMap(map.size());
        for (FieldAndMethods fieldAndMethods : map.values()) {
            FieldAndMethods fieldAndMethods2 = new FieldAndMethods(scriptable, fieldAndMethods.methods, fieldAndMethods.field);
            fieldAndMethods2.javaObject = obj;
            map2.put(fieldAndMethods.field.getName(), fieldAndMethods2);
        }
        return map2;
    }

    Object[] getIds(boolean z2) {
        Map<String, Object> map = z2 ? this.staticMembers : this.members;
        return map.keySet().toArray(new Object[map.size()]);
    }

    boolean has(String str, boolean z2) {
        return ((z2 ? this.staticMembers : this.members).get(str) == null && findExplicitFunction(str, z2) == null) ? false : true;
    }

    void put(Scriptable scriptable, String str, Object obj, Object obj2, boolean z2) throws IllegalAccessException, IllegalArgumentException {
        Map<String, Object> map = z2 ? this.staticMembers : this.members;
        Object obj3 = map.get(str);
        if (!z2 && obj3 == null) {
            obj3 = this.staticMembers.get(str);
        }
        if (obj3 == null) {
            throw reportMemberNotFound(str);
        }
        if (obj3 instanceof FieldAndMethods) {
            obj3 = ((FieldAndMethods) map.get(str)).field;
        }
        if (!(obj3 instanceof BeanProperty)) {
            if (!(obj3 instanceof Field)) {
                throw Context.reportRuntimeError1(obj3 == null ? "msg.java.internal.private" : "msg.java.method.assign", str);
            }
            Field field = (Field) obj3;
            try {
                field.set(obj, Context.jsToJava(obj2, field.getType()));
                return;
            } catch (IllegalAccessException e2) {
                if ((field.getModifiers() & 16) == 0) {
                    throw Context.throwAsScriptRuntimeEx(e2);
                }
                return;
            } catch (IllegalArgumentException unused) {
                throw Context.reportRuntimeError3("msg.java.internal.field.type", obj2.getClass().getName(), field, obj.getClass().getName());
            }
        }
        BeanProperty beanProperty = (BeanProperty) obj3;
        MemberBox memberBox = beanProperty.setter;
        if (memberBox == null) {
            throw reportMemberNotFound(str);
        }
        NativeJavaMethod nativeJavaMethod = beanProperty.setters;
        if (nativeJavaMethod != null && obj2 != null) {
            nativeJavaMethod.call(Context.getContext(), ScriptableObject.getTopLevelScope(scriptable), scriptable, new Object[]{obj2});
            return;
        }
        try {
            beanProperty.setter.invoke(obj, new Object[]{Context.jsToJava(obj2, memberBox.argTypes[0])});
        } catch (Exception e3) {
            throw Context.throwAsScriptRuntimeEx(e3);
        }
    }

    RuntimeException reportMemberNotFound(String str) {
        return Context.reportRuntimeError2("msg.java.member.not.found", this.cl.getName(), str);
    }

    JavaMembers(Scriptable scriptable, Class<?> cls, boolean z2) throws RuntimeException {
        try {
            Context contextEnterContext = ContextFactory.getGlobal().enterContext();
            ClassShutter classShutter = contextEnterContext.getClassShutter();
            if (classShutter != null && !classShutter.visibleToScripts(cls.getName())) {
                throw Context.reportRuntimeError1("msg.access.prohibited", cls.getName());
            }
            this.members = new HashMap();
            this.staticMembers = new HashMap();
            this.cl = cls;
            reflect(scriptable, z2, contextEnterContext.hasFeature(13));
            Context.exit();
        } catch (Throwable th) {
            Context.exit();
            throw th;
        }
    }

    private static final class MethodSignature {
        private final Class<?>[] args;
        private final String name;

        private MethodSignature(String str, Class<?>[] clsArr) {
            this.name = str;
            this.args = clsArr;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MethodSignature)) {
                return false;
            }
            MethodSignature methodSignature = (MethodSignature) obj;
            return methodSignature.name.equals(this.name) && Arrays.equals(this.args, methodSignature.args);
        }

        public int hashCode() {
            return this.name.hashCode() ^ this.args.length;
        }

        MethodSignature(Method method) {
            this(method.getName(), method.getParameterTypes());
        }
    }

    private static void discoverAccessibleMethods(Class<?> cls, Map<MethodSignature, Method> map, boolean z2, boolean z3) throws SecurityException {
        if (Modifier.isPublic(cls.getModifiers()) || z3) {
            try {
                if (!z2 && !z3) {
                    for (Method method : cls.getMethods()) {
                        MethodSignature methodSignature = new MethodSignature(method);
                        if (!map.containsKey(methodSignature)) {
                            map.put(methodSignature, method);
                        }
                    }
                    return;
                }
                while (cls != null) {
                    try {
                        for (Method method2 : cls.getDeclaredMethods()) {
                            int modifiers = method2.getModifiers();
                            if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers) || z3) {
                                MethodSignature methodSignature2 = new MethodSignature(method2);
                                if (!map.containsKey(methodSignature2)) {
                                    if (z3 && !method2.isAccessible()) {
                                        method2.setAccessible(true);
                                    }
                                    map.put(methodSignature2, method2);
                                }
                            }
                        }
                        for (Class<?> cls2 : cls.getInterfaces()) {
                            discoverAccessibleMethods(cls2, map, z2, z3);
                        }
                        cls = cls.getSuperclass();
                    } catch (SecurityException unused) {
                        for (Method method3 : cls.getMethods()) {
                            MethodSignature methodSignature3 = new MethodSignature(method3);
                            if (!map.containsKey(methodSignature3)) {
                                map.put(methodSignature3, method3);
                            }
                        }
                        return;
                    }
                }
                return;
            } catch (SecurityException unused2) {
                Context.reportWarning("Could not discover accessible methods of class " + cls.getName() + " due to lack of privileges, attemping superclasses/interfaces.");
            }
        }
        for (Class<?> cls3 : cls.getInterfaces()) {
            discoverAccessibleMethods(cls3, map, z2, z3);
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass != null) {
            discoverAccessibleMethods(superclass, map, z2, z3);
        }
    }

    private static MemberBox extractSetMethod(MemberBox[] memberBoxArr, boolean z2) {
        for (MemberBox memberBox : memberBoxArr) {
            if ((!z2 || memberBox.isStatic()) && memberBox.method().getReturnType() == Void.TYPE && memberBox.argTypes.length == 1) {
                return memberBox;
            }
        }
        return null;
    }
}
