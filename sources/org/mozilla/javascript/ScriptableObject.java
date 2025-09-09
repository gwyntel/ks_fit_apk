package org.mozilla.javascript;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;
import org.mozilla.javascript.annotations.JSSetter;
import org.mozilla.javascript.annotations.JSStaticFunction;
import org.mozilla.javascript.debug.DebuggableObject;

/* loaded from: classes5.dex */
public abstract class ScriptableObject implements Scriptable, SymbolScriptable, Serializable, DebuggableObject, ConstProperties {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONST = 13;
    public static final int DONTENUM = 2;
    public static final int EMPTY = 0;
    private static final Method GET_ARRAY_LENGTH;
    private static final Comparator<Object> KEY_COMPARATOR;
    public static final int PERMANENT = 4;
    public static final int READONLY = 1;
    public static final int UNINITIALIZED_CONST = 8;
    static final long serialVersionUID = 2829861078851942586L;
    private volatile Map<Object, Object> associatedValues;
    private transient ExternalArrayData externalData;
    private boolean isExtensible;
    private boolean isSealed;
    private Scriptable parentScopeObject;
    private Scriptable prototypeObject;
    private transient SlotMapContainer slotMap;

    static final class GetterSlot extends Slot {
        static final long serialVersionUID = -4900574849788797588L;
        Object getter;
        Object setter;

        GetterSlot(Object obj, int i2, int i3) {
            super(obj, i2, i3);
        }

        @Override // org.mozilla.javascript.ScriptableObject.Slot
        ScriptableObject getPropertyDescriptor(Context context, Scriptable scriptable) {
            int attributes = getAttributes();
            NativeObject nativeObject = new NativeObject();
            ScriptRuntime.setBuiltinProtoAndParent(nativeObject, scriptable, TopLevel.Builtins.Object);
            nativeObject.defineProperty("enumerable", Boolean.valueOf((attributes & 2) == 0), 0);
            nativeObject.defineProperty("configurable", Boolean.valueOf((attributes & 4) == 0), 0);
            if (this.getter == null && this.setter == null) {
                nativeObject.defineProperty("writable", Boolean.valueOf((attributes & 1) == 0), 0);
            }
            Object obj = this.getter;
            if (obj != null) {
                if (obj instanceof MemberBox) {
                    nativeObject.defineProperty(TmpConstant.PROPERTY_IDENTIFIER_GET, new FunctionObject("f", ((MemberBox) this.getter).member(), scriptable), 0);
                } else if (obj instanceof Member) {
                    nativeObject.defineProperty(TmpConstant.PROPERTY_IDENTIFIER_GET, new FunctionObject("f", (Member) this.getter, scriptable), 0);
                } else {
                    nativeObject.defineProperty(TmpConstant.PROPERTY_IDENTIFIER_GET, obj, 0);
                }
            }
            Object obj2 = this.setter;
            if (obj2 != null) {
                if (obj2 instanceof MemberBox) {
                    nativeObject.defineProperty(TmpConstant.PROPERTY_IDENTIFIER_SET, new FunctionObject("f", ((MemberBox) this.setter).member(), scriptable), 0);
                } else if (obj2 instanceof Member) {
                    nativeObject.defineProperty(TmpConstant.PROPERTY_IDENTIFIER_SET, new FunctionObject("f", (Member) this.setter, scriptable), 0);
                } else {
                    nativeObject.defineProperty(TmpConstant.PROPERTY_IDENTIFIER_SET, obj2, 0);
                }
            }
            return nativeObject;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.mozilla.javascript.ScriptableObject.Slot
        Object getValue(Scriptable scriptable) {
            Object[] objArr;
            Object obj = this.getter;
            if (obj != null) {
                if (obj instanceof MemberBox) {
                    MemberBox memberBox = (MemberBox) obj;
                    Object obj2 = memberBox.delegateTo;
                    if (obj2 == 0) {
                        objArr = ScriptRuntime.emptyArgs;
                    } else {
                        Object[] objArr2 = {scriptable};
                        scriptable = obj2;
                        objArr = objArr2;
                    }
                    return memberBox.invoke(scriptable, objArr);
                }
                if (obj instanceof Function) {
                    Function function = (Function) obj;
                    return function.call(Context.getContext(), function.getParentScope(), scriptable, ScriptRuntime.emptyArgs);
                }
            }
            Object obj3 = this.value;
            if (!(obj3 instanceof LazilyLoadedCtor)) {
                return obj3;
            }
            LazilyLoadedCtor lazilyLoadedCtor = (LazilyLoadedCtor) obj3;
            try {
                lazilyLoadedCtor.init();
                return lazilyLoadedCtor.getValue();
            } finally {
                this.value = lazilyLoadedCtor.getValue();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.mozilla.javascript.ScriptableObject.Slot
        boolean setValue(Object obj, Scriptable scriptable, Scriptable scriptable2) {
            Object[] objArr;
            if (this.setter == null) {
                if (this.getter == null) {
                    return super.setValue(obj, scriptable, scriptable2);
                }
                Context context = Context.getContext();
                if (context.isStrictMode() || context.hasFeature(11)) {
                    throw ScriptRuntime.typeError1("msg.set.prop.no.setter", this.name);
                }
                return true;
            }
            Context context2 = Context.getContext();
            Object obj2 = this.setter;
            if (obj2 instanceof MemberBox) {
                MemberBox memberBox = (MemberBox) obj2;
                Class<?>[] clsArr = memberBox.argTypes;
                Object objConvertArg = FunctionObject.convertArg(context2, scriptable2, obj, FunctionObject.getTypeTag(clsArr[clsArr.length - 1]));
                Object obj3 = memberBox.delegateTo;
                if (obj3 == 0) {
                    objArr = new Object[]{objConvertArg};
                } else {
                    Object[] objArr2 = {scriptable2, objConvertArg};
                    scriptable2 = obj3;
                    objArr = objArr2;
                }
                memberBox.invoke(scriptable2, objArr);
            } else if (obj2 instanceof Function) {
                Function function = (Function) obj2;
                function.call(context2, function.getParentScope(), scriptable2, new Object[]{obj});
            }
            return true;
        }
    }

    public static final class KeyComparator implements Comparator<Object> {
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            int iIntValue;
            int iIntValue2;
            if (!(obj instanceof Integer)) {
                return obj2 instanceof Integer ? 1 : 0;
            }
            if (!(obj2 instanceof Integer) || (iIntValue = ((Integer) obj).intValue()) < (iIntValue2 = ((Integer) obj2).intValue())) {
                return -1;
            }
            return iIntValue > iIntValue2 ? 1 : 0;
        }
    }

    static class Slot implements Serializable {
        private static final long serialVersionUID = -6090581677123995491L;
        private short attributes;
        int indexOrHash;
        Object name;
        transient Slot next;
        transient Slot orderedNext;
        Object value;

        Slot(Object obj, int i2, int i3) {
            this.name = obj;
            this.indexOrHash = i2;
            this.attributes = (short) i3;
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            Object obj = this.name;
            if (obj != null) {
                this.indexOrHash = obj.hashCode();
            }
        }

        int getAttributes() {
            return this.attributes;
        }

        ScriptableObject getPropertyDescriptor(Context context, Scriptable scriptable) {
            return ScriptableObject.buildDataDescriptor(scriptable, this.value, this.attributes);
        }

        Object getValue(Scriptable scriptable) {
            return this.value;
        }

        synchronized void setAttributes(int i2) {
            ScriptableObject.checkValidAttributes(i2);
            this.attributes = (short) i2;
        }

        boolean setValue(Object obj, Scriptable scriptable, Scriptable scriptable2) {
            if ((this.attributes & 1) != 0) {
                if (Context.getContext().isStrictMode()) {
                    throw ScriptRuntime.typeError1("msg.modify.readonly", this.name);
                }
                return true;
            }
            if (scriptable != scriptable2) {
                return false;
            }
            this.value = obj;
            return true;
        }
    }

    enum SlotAccess {
        QUERY,
        MODIFY,
        MODIFY_CONST,
        MODIFY_GETTER_SETTER,
        CONVERT_ACCESSOR_TO_DATA
    }

    static {
        try {
            GET_ARRAY_LENGTH = ScriptableObject.class.getMethod("getExternalArrayLength", null);
            KEY_COMPARATOR = new KeyComparator();
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        }
    }

    public ScriptableObject() {
        this.isExtensible = true;
        this.isSealed = false;
        this.slotMap = createSlotMap(0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:137:0x0208, code lost:
    
        if (r4 == null) goto L109;
     */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x02af A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static <T extends org.mozilla.javascript.Scriptable> org.mozilla.javascript.BaseFunction buildClassCtor(org.mozilla.javascript.Scriptable r24, java.lang.Class<T> r25, boolean r26, boolean r27) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 751
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptableObject.buildClassCtor(org.mozilla.javascript.Scriptable, java.lang.Class, boolean, boolean):org.mozilla.javascript.BaseFunction");
    }

    protected static ScriptableObject buildDataDescriptor(Scriptable scriptable, Object obj, int i2) {
        NativeObject nativeObject = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(nativeObject, scriptable, TopLevel.Builtins.Object);
        nativeObject.defineProperty("value", obj, 0);
        nativeObject.defineProperty("writable", Boolean.valueOf((i2 & 1) == 0), 0);
        nativeObject.defineProperty("enumerable", Boolean.valueOf((i2 & 2) == 0), 0);
        nativeObject.defineProperty("configurable", Boolean.valueOf((i2 & 4) == 0), 0);
        return nativeObject;
    }

    public static Object callMethod(Scriptable scriptable, String str, Object[] objArr) {
        return callMethod(null, scriptable, str, objArr);
    }

    private void checkNotSealed(Object obj, int i2) {
        if (isSealed()) {
            throw Context.reportRuntimeError1("msg.modify.sealed", obj != null ? obj.toString() : Integer.toString(i2));
        }
    }

    static void checkValidAttributes(int i2) {
        if ((i2 & (-16)) != 0) {
            throw new IllegalArgumentException(String.valueOf(i2));
        }
    }

    private SlotMapContainer createSlotMap(int i2) {
        Context currentContext = Context.getCurrentContext();
        return (currentContext == null || !currentContext.hasFeature(17)) ? new SlotMapContainer(i2) : new ThreadSafeSlotMapContainer(i2);
    }

    public static <T extends Scriptable> void defineClass(Scriptable scriptable, Class<T> cls) throws IllegalAccessException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        defineClass(scriptable, cls, false, false);
    }

    public static void defineConstProperty(Scriptable scriptable, String str) {
        if (scriptable instanceof ConstProperties) {
            ((ConstProperties) scriptable).defineConst(str, scriptable);
        } else {
            defineProperty(scriptable, str, Undefined.instance, 13);
        }
    }

    public static boolean deleteProperty(Scriptable scriptable, String str) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            return true;
        }
        base.delete(str);
        return !base.has(str, scriptable);
    }

    protected static Scriptable ensureScriptable(Object obj) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(obj));
    }

    protected static ScriptableObject ensureScriptableObject(Object obj) {
        if (obj instanceof ScriptableObject) {
            return (ScriptableObject) obj;
        }
        throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(obj));
    }

    protected static SymbolScriptable ensureSymbolScriptable(Object obj) {
        if (obj instanceof SymbolScriptable) {
            return (SymbolScriptable) obj;
        }
        throw ScriptRuntime.typeError1("msg.object.not.symbolscriptable", ScriptRuntime.typeof(obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T extends Scriptable> Class<T> extendsScriptable(Class<?> cls) {
        if (ScriptRuntime.ScriptableClass.isAssignableFrom(cls)) {
            return cls;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Member findAnnotatedMember(AccessibleObject[] accessibleObjectArr, Class<? extends Annotation> cls) {
        for (Constructor constructor : accessibleObjectArr) {
            if (constructor.isAnnotationPresent(cls)) {
                return constructor;
            }
        }
        return null;
    }

    private Slot findAttributeSlot(String str, int i2, SlotAccess slotAccess) {
        Slot slot = this.slotMap.get(str, i2, slotAccess);
        if (slot != null) {
            return slot;
        }
        if (str == null) {
            str = Integer.toString(i2);
        }
        throw Context.reportRuntimeError1("msg.prop.not.found", str);
    }

    private static Method findSetterMethod(Method[] methodArr, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(TmpConstant.PROPERTY_IDENTIFIER_SET);
        sb.append(Character.toUpperCase(str.charAt(0)));
        sb.append(str.substring(1));
        String string = sb.toString();
        for (Method method : methodArr) {
            JSSetter jSSetter = (JSSetter) method.getAnnotation(JSSetter.class);
            if (jSSetter != null && (str.equals(jSSetter.value()) || ("".equals(jSSetter.value()) && string.equals(method.getName())))) {
                return method;
            }
        }
        String str3 = str2 + str;
        for (Method method2 : methodArr) {
            if (str3.equals(method2.getName())) {
                return method2;
            }
        }
        return null;
    }

    public static Scriptable getArrayPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.Array);
    }

    private static Scriptable getBase(Scriptable scriptable, String str) {
        while (!scriptable.has(str, scriptable) && (scriptable = scriptable.getPrototype()) != null) {
        }
        return scriptable;
    }

    public static Scriptable getClassPrototype(Scriptable scriptable, String str) {
        Object prototypeProperty;
        Object property = getProperty(getTopLevelScope(scriptable), str);
        if (!(property instanceof BaseFunction)) {
            if (property instanceof Scriptable) {
                Scriptable scriptable2 = (Scriptable) property;
                prototypeProperty = scriptable2.get("prototype", scriptable2);
            }
            return null;
        }
        prototypeProperty = ((BaseFunction) property).getPrototypeProperty();
        if (prototypeProperty instanceof Scriptable) {
            return (Scriptable) prototypeProperty;
        }
        return null;
    }

    public static Scriptable getFunctionPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.Function);
    }

    public static Scriptable getObjectPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.Object);
    }

    public static Object getProperty(Scriptable scriptable, String str) {
        Object obj;
        Scriptable prototype = scriptable;
        do {
            obj = prototype.get(str, scriptable);
            if (obj != Scriptable.NOT_FOUND) {
                break;
            }
            prototype = prototype.getPrototype();
        } while (prototype != null);
        return obj;
    }

    public static Object[] getPropertyIds(Scriptable scriptable) {
        if (scriptable == null) {
            return ScriptRuntime.emptyArgs;
        }
        Object[] ids = scriptable.getIds();
        ObjToIntMap objToIntMap = null;
        while (true) {
            scriptable = scriptable.getPrototype();
            if (scriptable == null) {
                break;
            }
            Object[] ids2 = scriptable.getIds();
            if (ids2.length != 0) {
                if (objToIntMap == null) {
                    if (ids.length == 0) {
                        ids = ids2;
                    } else {
                        objToIntMap = new ObjToIntMap(ids.length + ids2.length);
                        for (int i2 = 0; i2 != ids.length; i2++) {
                            objToIntMap.intern(ids[i2]);
                        }
                        ids = null;
                    }
                }
                for (int i3 = 0; i3 != ids2.length; i3++) {
                    objToIntMap.intern(ids2[i3]);
                }
            }
        }
        return objToIntMap != null ? objToIntMap.getKeys() : ids;
    }

    private static String getPropertyName(String str, String str2, Annotation annotation) {
        String strValue;
        if (str2 != null) {
            return str.substring(str2.length());
        }
        if (annotation instanceof JSGetter) {
            strValue = ((JSGetter) annotation).value();
            if ((strValue == null || strValue.length() == 0) && str.length() > 3 && str.startsWith(TmpConstant.PROPERTY_IDENTIFIER_GET)) {
                strValue = str.substring(3);
                if (Character.isUpperCase(strValue.charAt(0))) {
                    if (strValue.length() == 1) {
                        strValue = strValue.toLowerCase();
                    } else if (!Character.isUpperCase(strValue.charAt(1))) {
                        strValue = Character.toLowerCase(strValue.charAt(0)) + strValue.substring(1);
                    }
                }
            }
        } else {
            strValue = annotation instanceof JSFunction ? ((JSFunction) annotation).value() : annotation instanceof JSStaticFunction ? ((JSStaticFunction) annotation).value() : null;
        }
        return (strValue == null || strValue.length() == 0) ? str : strValue;
    }

    public static Scriptable getTopLevelScope(Scriptable scriptable) {
        while (true) {
            Scriptable parentScope = scriptable.getParentScope();
            if (parentScope == null) {
                return scriptable;
            }
            scriptable = parentScope;
        }
    }

    public static Object getTopScopeValue(Scriptable scriptable, Object obj) {
        Object associatedValue;
        Scriptable topLevelScope = getTopLevelScope(scriptable);
        do {
            if ((topLevelScope instanceof ScriptableObject) && (associatedValue = ((ScriptableObject) topLevelScope).getAssociatedValue(obj)) != null) {
                return associatedValue;
            }
            topLevelScope = topLevelScope.getPrototype();
        } while (topLevelScope != null);
        return null;
    }

    public static <T> T getTypedProperty(Scriptable scriptable, int i2, Class<T> cls) {
        Object property = getProperty(scriptable, i2);
        if (property == Scriptable.NOT_FOUND) {
            property = null;
        }
        return cls.cast(Context.jsToJava(property, cls));
    }

    public static boolean hasProperty(Scriptable scriptable, String str) {
        return getBase(scriptable, str) != null;
    }

    protected static boolean isFalse(Object obj) {
        return !isTrue(obj);
    }

    protected static boolean isTrue(Object obj) {
        return obj != Scriptable.NOT_FOUND && ScriptRuntime.toBoolean(obj);
    }

    private boolean putConstImpl(String str, int i2, Scriptable scriptable, Object obj, int i3) {
        Slot slotQuery;
        if (!this.isExtensible && Context.getContext().isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.not.extensible");
        }
        if (this != scriptable) {
            slotQuery = this.slotMap.query(str, i2);
            if (slotQuery == null) {
                return false;
            }
        } else {
            if (isExtensible()) {
                checkNotSealed(str, i2);
                Slot slot = this.slotMap.get(str, i2, SlotAccess.MODIFY_CONST);
                int attributes = slot.getAttributes();
                if ((attributes & 1) == 0) {
                    throw Context.reportRuntimeError1("msg.var.redecl", str);
                }
                if ((attributes & 8) != 0) {
                    slot.value = obj;
                    if (i3 != 8) {
                        slot.setAttributes(attributes & (-9));
                    }
                }
                return true;
            }
            slotQuery = this.slotMap.query(str, i2);
            if (slotQuery == null) {
                return true;
            }
        }
        return slotQuery.setValue(obj, this, scriptable);
    }

    public static void putConstProperty(Scriptable scriptable, String str, Object obj) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            base = scriptable;
        }
        if (base instanceof ConstProperties) {
            ((ConstProperties) base).putConst(str, scriptable, obj);
        }
    }

    private boolean putImpl(Object obj, int i2, Scriptable scriptable, Object obj2) {
        Slot slotQuery;
        if (!this.isExtensible && Context.getContext().isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.not.extensible");
        }
        if (this != scriptable) {
            slotQuery = this.slotMap.query(obj, i2);
            if (slotQuery == null) {
                return false;
            }
        } else if (this.isExtensible) {
            if (this.isSealed) {
                checkNotSealed(obj, i2);
            }
            slotQuery = this.slotMap.get(obj, i2, SlotAccess.MODIFY);
        } else {
            slotQuery = this.slotMap.query(obj, i2);
            if (slotQuery == null) {
                return true;
            }
        }
        return slotQuery.setValue(obj2, this, scriptable);
    }

    public static void putProperty(Scriptable scriptable, String str, Object obj) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            base = scriptable;
        }
        base.put(str, scriptable, obj);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i2 = objectInputStream.readInt();
        this.slotMap = createSlotMap(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            this.slotMap.addSlot((Slot) objectInputStream.readObject());
        }
    }

    public static void redefineProperty(Scriptable scriptable, String str, boolean z2) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            return;
        }
        if ((base instanceof ConstProperties) && ((ConstProperties) base).isConst(str)) {
            throw ScriptRuntime.typeError1("msg.const.redecl", str);
        }
        if (z2) {
            throw ScriptRuntime.typeError1("msg.var.redecl", str);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        long lock = this.slotMap.readLock();
        try {
            int iDirtySize = this.slotMap.dirtySize();
            if (iDirtySize == 0) {
                objectOutputStream.writeInt(0);
            } else {
                objectOutputStream.writeInt(iDirtySize);
                Iterator<Slot> it = this.slotMap.iterator();
                while (it.hasNext()) {
                    objectOutputStream.writeObject(it.next());
                }
            }
            this.slotMap.unlockRead(lock);
        } catch (Throwable th) {
            this.slotMap.unlockRead(lock);
            throw th;
        }
    }

    void addLazilyInitializedValue(String str, int i2, LazilyLoadedCtor lazilyLoadedCtor, int i3) {
        if (str != null && i2 != 0) {
            throw new IllegalArgumentException(str);
        }
        checkNotSealed(str, i2);
        GetterSlot getterSlot = (GetterSlot) this.slotMap.get(str, i2, SlotAccess.MODIFY_GETTER_SETTER);
        getterSlot.setAttributes(i3);
        getterSlot.getter = null;
        getterSlot.setter = null;
        getterSlot.value = lazilyLoadedCtor;
    }

    protected int applyDescriptorToAttributeBitset(int i2, ScriptableObject scriptableObject) {
        Object property = getProperty(scriptableObject, "enumerable");
        Object obj = Scriptable.NOT_FOUND;
        if (property != obj) {
            i2 = ScriptRuntime.toBoolean(property) ? i2 & (-3) : i2 | 2;
        }
        Object property2 = getProperty(scriptableObject, "writable");
        if (property2 != obj) {
            i2 = ScriptRuntime.toBoolean(property2) ? i2 & (-2) : i2 | 1;
        }
        Object property3 = getProperty(scriptableObject, "configurable");
        return property3 != obj ? ScriptRuntime.toBoolean(property3) ? i2 & (-5) : i2 | 4 : i2;
    }

    public final synchronized Object associateValue(Object obj, Object obj2) {
        Map map;
        try {
            if (obj2 == null) {
                throw new IllegalArgumentException();
            }
            map = this.associatedValues;
            if (map == null) {
                map = new HashMap();
                this.associatedValues = map;
            }
        } catch (Throwable th) {
            throw th;
        }
        return Kit.initHash(map, obj, obj2);
    }

    public boolean avoidObjectDetection() {
        return false;
    }

    protected void checkPropertyChange(Object obj, ScriptableObject scriptableObject, ScriptableObject scriptableObject2) {
        if (scriptableObject == null) {
            if (!isExtensible()) {
                throw ScriptRuntime.typeError0("msg.not.extensible");
            }
            return;
        }
        if (isFalse(scriptableObject.get("configurable", scriptableObject))) {
            if (isTrue(getProperty(scriptableObject2, "configurable"))) {
                throw ScriptRuntime.typeError1("msg.change.configurable.false.to.true", obj);
            }
            if (isTrue(scriptableObject.get("enumerable", scriptableObject)) != isTrue(getProperty(scriptableObject2, "enumerable"))) {
                throw ScriptRuntime.typeError1("msg.change.enumerable.with.configurable.false", obj);
            }
            boolean zIsDataDescriptor = isDataDescriptor(scriptableObject2);
            boolean zIsAccessorDescriptor = isAccessorDescriptor(scriptableObject2);
            if (zIsDataDescriptor || zIsAccessorDescriptor) {
                if (zIsDataDescriptor && isDataDescriptor(scriptableObject)) {
                    if (isFalse(scriptableObject.get("writable", scriptableObject))) {
                        if (isTrue(getProperty(scriptableObject2, "writable"))) {
                            throw ScriptRuntime.typeError1("msg.change.writable.false.to.true.with.configurable.false", obj);
                        }
                        if (!sameValue(getProperty(scriptableObject2, "value"), scriptableObject.get("value", scriptableObject))) {
                            throw ScriptRuntime.typeError1("msg.change.value.with.writable.false", obj);
                        }
                        return;
                    }
                    return;
                }
                if (!zIsAccessorDescriptor || !isAccessorDescriptor(scriptableObject)) {
                    if (!isDataDescriptor(scriptableObject)) {
                        throw ScriptRuntime.typeError1("msg.change.property.accessor.to.data.with.configurable.false", obj);
                    }
                    throw ScriptRuntime.typeError1("msg.change.property.data.to.accessor.with.configurable.false", obj);
                }
                if (!sameValue(getProperty(scriptableObject2, TmpConstant.PROPERTY_IDENTIFIER_SET), scriptableObject.get(TmpConstant.PROPERTY_IDENTIFIER_SET, scriptableObject))) {
                    throw ScriptRuntime.typeError1("msg.change.setter.with.configurable.false", obj);
                }
                if (!sameValue(getProperty(scriptableObject2, TmpConstant.PROPERTY_IDENTIFIER_GET), scriptableObject.get(TmpConstant.PROPERTY_IDENTIFIER_GET, scriptableObject))) {
                    throw ScriptRuntime.typeError1("msg.change.getter.with.configurable.false", obj);
                }
            }
        }
    }

    protected void checkPropertyDefinition(ScriptableObject scriptableObject) {
        Object property = getProperty(scriptableObject, TmpConstant.PROPERTY_IDENTIFIER_GET);
        Object obj = Scriptable.NOT_FOUND;
        if (property != obj && property != Undefined.instance && !(property instanceof Callable)) {
            throw ScriptRuntime.notFunctionError(property);
        }
        Object property2 = getProperty(scriptableObject, TmpConstant.PROPERTY_IDENTIFIER_SET);
        if (property2 != obj && property2 != Undefined.instance && !(property2 instanceof Callable)) {
            throw ScriptRuntime.notFunctionError(property2);
        }
        if (isDataDescriptor(scriptableObject) && isAccessorDescriptor(scriptableObject)) {
            throw ScriptRuntime.typeError0("msg.both.data.and.accessor.desc");
        }
    }

    @Override // org.mozilla.javascript.ConstProperties
    public void defineConst(String str, Scriptable scriptable) {
        if (putConstImpl(str, 0, scriptable, Undefined.instance, 8)) {
            return;
        }
        if (scriptable == this) {
            throw Kit.codeBug();
        }
        if (scriptable instanceof ConstProperties) {
            ((ConstProperties) scriptable).defineConst(str, scriptable);
        }
    }

    public void defineFunctionProperties(String[] strArr, Class<?> cls, int i2) {
        Method[] methodList = FunctionObject.getMethodList(cls);
        for (String str : strArr) {
            Method methodFindSingleMethod = FunctionObject.findSingleMethod(methodList, str);
            if (methodFindSingleMethod == null) {
                throw Context.reportRuntimeError2("msg.method.not.found", str, cls.getName());
            }
            defineProperty(str, new FunctionObject(str, methodFindSingleMethod, this), i2);
        }
    }

    public void defineOwnProperties(Context context, ScriptableObject scriptableObject) {
        Object[] ids = scriptableObject.getIds(false, true);
        ScriptableObject[] scriptableObjectArr = new ScriptableObject[ids.length];
        int length = ids.length;
        for (int i2 = 0; i2 < length; i2++) {
            ScriptableObject scriptableObjectEnsureScriptableObject = ensureScriptableObject(ScriptRuntime.getObjectElem((Scriptable) scriptableObject, ids[i2], context));
            checkPropertyDefinition(scriptableObjectEnsureScriptableObject);
            scriptableObjectArr[i2] = scriptableObjectEnsureScriptableObject;
        }
        int length2 = ids.length;
        for (int i3 = 0; i3 < length2; i3++) {
            defineOwnProperty(context, ids[i3], scriptableObjectArr[i3]);
        }
    }

    public void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject) {
        checkPropertyDefinition(scriptableObject);
        defineOwnProperty(context, obj, scriptableObject, true);
    }

    public void defineProperty(String str, Object obj, int i2) {
        checkNotSealed(str, 0);
        put(str, this, obj);
        setAttributes(str, i2);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void delete(String str) {
        checkNotSealed(str, 0);
        this.slotMap.remove(str, 0);
    }

    protected Object equivalentValues(Object obj) {
        return this == obj ? Boolean.TRUE : Scriptable.NOT_FOUND;
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object get(String str, Scriptable scriptable) {
        Slot slotQuery = this.slotMap.query(str, 0);
        return slotQuery == null ? Scriptable.NOT_FOUND : slotQuery.getValue(scriptable);
    }

    @Override // org.mozilla.javascript.debug.DebuggableObject
    public Object[] getAllIds() {
        return getIds(true, false);
    }

    public final Object getAssociatedValue(Object obj) {
        Map<Object, Object> map = this.associatedValues;
        if (map == null) {
            return null;
        }
        return map.get(obj);
    }

    @Deprecated
    public final int getAttributes(String str, Scriptable scriptable) {
        return getAttributes(str);
    }

    @Override // org.mozilla.javascript.Scriptable
    public abstract String getClassName();

    @Override // org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        return getDefaultValue(this, cls);
    }

    public ExternalArrayData getExternalArrayData() {
        return this.externalData;
    }

    public Object getExternalArrayLength() {
        ExternalArrayData externalArrayData = this.externalData;
        return Integer.valueOf(externalArrayData == null ? 0 : externalArrayData.getArrayLength());
    }

    public Object getGetterOrSetter(String str, int i2, boolean z2) {
        if (str != null && i2 != 0) {
            throw new IllegalArgumentException(str);
        }
        Slot slotQuery = this.slotMap.query(str, i2);
        if (slotQuery == null) {
            return null;
        }
        if (!(slotQuery instanceof GetterSlot)) {
            return Undefined.instance;
        }
        GetterSlot getterSlot = (GetterSlot) slotQuery;
        Object obj = z2 ? getterSlot.setter : getterSlot.getter;
        return obj != null ? obj : Undefined.instance;
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object[] getIds() {
        return getIds(false, false);
    }

    protected ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        Slot slot = getSlot(context, obj, SlotAccess.QUERY);
        if (slot == null) {
            return null;
        }
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        return slot.getPropertyDescriptor(context, parentScope);
    }

    @Override // org.mozilla.javascript.Scriptable
    public Scriptable getParentScope() {
        return this.parentScopeObject;
    }

    @Override // org.mozilla.javascript.Scriptable
    public Scriptable getPrototype() {
        return this.prototypeObject;
    }

    protected Slot getSlot(Context context, Object obj, SlotAccess slotAccess) {
        if (obj instanceof Symbol) {
            return this.slotMap.get(obj, 0, slotAccess);
        }
        String stringIdOrIndex = ScriptRuntime.toStringIdOrIndex(context, obj);
        return stringIdOrIndex == null ? this.slotMap.get(null, ScriptRuntime.lastIndexResult(context), slotAccess) : this.slotMap.get(stringIdOrIndex, 0, slotAccess);
    }

    public String getTypeOf() {
        return avoidObjectDetection() ? "undefined" : "object";
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean has(String str, Scriptable scriptable) {
        return this.slotMap.query(str, 0) != null;
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean hasInstance(Scriptable scriptable) {
        return ScriptRuntime.jsDelegatesTo(scriptable, this);
    }

    protected boolean isAccessorDescriptor(ScriptableObject scriptableObject) {
        return hasProperty(scriptableObject, TmpConstant.PROPERTY_IDENTIFIER_GET) || hasProperty(scriptableObject, TmpConstant.PROPERTY_IDENTIFIER_SET);
    }

    @Override // org.mozilla.javascript.ConstProperties
    public boolean isConst(String str) {
        Slot slotQuery = this.slotMap.query(str, 0);
        return slotQuery != null && (slotQuery.getAttributes() & 5) == 5;
    }

    protected boolean isDataDescriptor(ScriptableObject scriptableObject) {
        return hasProperty(scriptableObject, "value") || hasProperty(scriptableObject, "writable");
    }

    public boolean isEmpty() {
        return this.slotMap.isEmpty();
    }

    public boolean isExtensible() {
        return this.isExtensible;
    }

    protected boolean isGenericDescriptor(ScriptableObject scriptableObject) {
        return (isDataDescriptor(scriptableObject) || isAccessorDescriptor(scriptableObject)) ? false : true;
    }

    protected boolean isGetterOrSetter(String str, int i2, boolean z2) {
        Slot slotQuery = this.slotMap.query(str, i2);
        if (!(slotQuery instanceof GetterSlot)) {
            return false;
        }
        if (!z2 || ((GetterSlot) slotQuery).setter == null) {
            return (z2 || ((GetterSlot) slotQuery).getter == null) ? false : true;
        }
        return true;
    }

    public final boolean isSealed() {
        return this.isSealed;
    }

    public void preventExtensions() {
        this.isExtensible = false;
    }

    @Override // org.mozilla.javascript.Scriptable
    public void put(String str, Scriptable scriptable, Object obj) {
        if (putImpl(str, 0, scriptable, obj)) {
            return;
        }
        if (scriptable == this) {
            throw Kit.codeBug();
        }
        scriptable.put(str, scriptable, obj);
    }

    @Override // org.mozilla.javascript.ConstProperties
    public void putConst(String str, Scriptable scriptable, Object obj) {
        if (putConstImpl(str, 0, scriptable, obj, 1)) {
            return;
        }
        if (scriptable == this) {
            throw Kit.codeBug();
        }
        if (scriptable instanceof ConstProperties) {
            ((ConstProperties) scriptable).putConst(str, scriptable, obj);
        } else {
            scriptable.put(str, scriptable, obj);
        }
    }

    protected boolean sameValue(Object obj, Object obj2) {
        Object obj3 = Scriptable.NOT_FOUND;
        if (obj == obj3) {
            return true;
        }
        if (obj2 == obj3) {
            obj2 = Undefined.instance;
        }
        if ((obj2 instanceof Number) && (obj instanceof Number)) {
            double dDoubleValue = ((Number) obj2).doubleValue();
            double dDoubleValue2 = ((Number) obj).doubleValue();
            if (Double.isNaN(dDoubleValue) && Double.isNaN(dDoubleValue2)) {
                return true;
            }
            if (dDoubleValue == 0.0d && Double.doubleToLongBits(dDoubleValue) != Double.doubleToLongBits(dDoubleValue2)) {
                return false;
            }
        }
        return ScriptRuntime.shallowEq(obj2, obj);
    }

    public void sealObject() {
        if (this.isSealed) {
            return;
        }
        long lock = this.slotMap.readLock();
        try {
            Iterator<Slot> it = this.slotMap.iterator();
            while (it.hasNext()) {
                Slot next = it.next();
                Object obj = next.value;
                if (obj instanceof LazilyLoadedCtor) {
                    LazilyLoadedCtor lazilyLoadedCtor = (LazilyLoadedCtor) obj;
                    try {
                        lazilyLoadedCtor.init();
                        next.value = lazilyLoadedCtor.getValue();
                    } catch (Throwable th) {
                        next.value = lazilyLoadedCtor.getValue();
                        throw th;
                    }
                }
            }
            this.isSealed = true;
            this.slotMap.unlockRead(lock);
        } catch (Throwable th2) {
            this.slotMap.unlockRead(lock);
            throw th2;
        }
    }

    @Deprecated
    public final void setAttributes(String str, Scriptable scriptable, int i2) {
        setAttributes(str, i2);
    }

    public void setExternalArrayData(ExternalArrayData externalArrayData) {
        this.externalData = externalArrayData;
        if (externalArrayData == null) {
            delete(SessionDescription.ATTR_LENGTH);
        } else {
            defineProperty(SessionDescription.ATTR_LENGTH, null, GET_ARRAY_LENGTH, null, 3);
        }
    }

    public void setGetterOrSetter(String str, int i2, Callable callable, boolean z2) {
        setGetterOrSetter(str, i2, callable, z2, false);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void setParentScope(Scriptable scriptable) {
        this.parentScopeObject = scriptable;
    }

    @Override // org.mozilla.javascript.Scriptable
    public void setPrototype(Scriptable scriptable) {
        this.prototypeObject = scriptable;
    }

    public int size() {
        return this.slotMap.size();
    }

    public static Object callMethod(Context context, Scriptable scriptable, String str, Object[] objArr) {
        Object property = getProperty(scriptable, str);
        if (!(property instanceof Function)) {
            throw ScriptRuntime.notFunctionError(scriptable, str);
        }
        Function function = (Function) property;
        Scriptable topLevelScope = getTopLevelScope(scriptable);
        return context != null ? function.call(context, topLevelScope, scriptable, objArr) : Context.call(null, function, topLevelScope, scriptable, objArr);
    }

    public static <T extends Scriptable> void defineClass(Scriptable scriptable, Class<T> cls, boolean z2) throws IllegalAccessException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        defineClass(scriptable, cls, z2, false);
    }

    public static Object getDefaultValue(Scriptable scriptable, Class<?> cls) {
        String str;
        Object[] objArr;
        String str2;
        Object objCall;
        Context context = null;
        int i2 = 0;
        while (true) {
            str = "undefined";
            if (i2 >= 2) {
                throw ScriptRuntime.typeError1("msg.default.value", cls != null ? cls.getName() : "undefined");
            }
            Class<?> cls2 = ScriptRuntime.StringClass;
            boolean z2 = cls != cls2 ? i2 == 1 : i2 == 0;
            if (z2) {
                objArr = ScriptRuntime.emptyArgs;
                str2 = "toString";
            } else {
                if (cls != null) {
                    if (cls == cls2) {
                        str = "string";
                    } else if (cls == ScriptRuntime.ScriptableClass) {
                        str = "object";
                    } else if (cls == ScriptRuntime.FunctionClass) {
                        str = "function";
                    } else if (cls == ScriptRuntime.BooleanClass || cls == Boolean.TYPE) {
                        str = TypedValues.Custom.S_BOOLEAN;
                    } else {
                        if (cls != ScriptRuntime.NumberClass && cls != ScriptRuntime.ByteClass && cls != Byte.TYPE && cls != ScriptRuntime.ShortClass && cls != Short.TYPE && cls != ScriptRuntime.IntegerClass && cls != Integer.TYPE && cls != ScriptRuntime.FloatClass && cls != Float.TYPE && cls != ScriptRuntime.DoubleClass && cls != Double.TYPE) {
                            throw Context.reportRuntimeError1("msg.invalid.type", cls.toString());
                        }
                        str = "number";
                    }
                }
                objArr = new Object[]{str};
                str2 = "valueOf";
            }
            Object property = getProperty(scriptable, str2);
            if (property instanceof Function) {
                Function function = (Function) property;
                if (context == null) {
                    context = Context.getContext();
                }
                objCall = function.call(context, function.getParentScope(), scriptable, objArr);
                if (objCall == null) {
                    continue;
                } else {
                    if (!(objCall instanceof Scriptable)) {
                        return objCall;
                    }
                    if (cls == ScriptRuntime.ScriptableClass || cls == ScriptRuntime.FunctionClass) {
                        break;
                    }
                    if (z2 && (objCall instanceof Wrapper)) {
                        objCall = ((Wrapper) objCall).unwrap();
                        if (objCall instanceof String) {
                            break;
                        }
                    }
                }
            }
            i2++;
        }
        return objCall;
    }

    public static boolean hasProperty(Scriptable scriptable, int i2) {
        return getBase(scriptable, i2) != null;
    }

    private void setGetterOrSetter(String str, int i2, Callable callable, boolean z2, boolean z3) {
        GetterSlot getterSlot;
        if (str != null && i2 != 0) {
            throw new IllegalArgumentException(str);
        }
        if (!z3) {
            checkNotSealed(str, i2);
        }
        if (isExtensible()) {
            getterSlot = (GetterSlot) this.slotMap.get(str, i2, SlotAccess.MODIFY_GETTER_SETTER);
        } else {
            Slot slotQuery = this.slotMap.query(str, i2);
            if (!(slotQuery instanceof GetterSlot)) {
                return;
            } else {
                getterSlot = (GetterSlot) slotQuery;
            }
        }
        if (!z3 && (getterSlot.getAttributes() & 1) != 0) {
            throw Context.reportRuntimeError1("msg.modify.readonly", str);
        }
        if (z2) {
            getterSlot.setter = callable;
        } else {
            getterSlot.getter = callable;
        }
        getterSlot.value = Undefined.instance;
    }

    @Deprecated
    public final int getAttributes(int i2, Scriptable scriptable) {
        return getAttributes(i2);
    }

    Object[] getIds(boolean z2, boolean z3) {
        Object[] objArr;
        ExternalArrayData externalArrayData = this.externalData;
        int arrayLength = externalArrayData == null ? 0 : externalArrayData.getArrayLength();
        if (arrayLength == 0) {
            objArr = ScriptRuntime.emptyArgs;
        } else {
            objArr = new Object[arrayLength];
            for (int i2 = 0; i2 < arrayLength; i2++) {
                objArr[i2] = Integer.valueOf(i2);
            }
        }
        if (this.slotMap.isEmpty()) {
            return objArr;
        }
        long lock = this.slotMap.readLock();
        try {
            Iterator<Slot> it = this.slotMap.iterator();
            int i3 = arrayLength;
            while (it.hasNext()) {
                Slot next = it.next();
                if (z2 || (next.getAttributes() & 2) == 0) {
                    if (z3 || !(next.name instanceof Symbol)) {
                        if (i3 == arrayLength) {
                            Object[] objArr2 = new Object[this.slotMap.dirtySize() + arrayLength];
                            if (objArr != null) {
                                System.arraycopy(objArr, 0, objArr2, 0, arrayLength);
                            }
                            objArr = objArr2;
                        }
                        int i4 = i3 + 1;
                        Object objValueOf = next.name;
                        if (objValueOf == null) {
                            objValueOf = Integer.valueOf(next.indexOrHash);
                        }
                        objArr[i3] = objValueOf;
                        i3 = i4;
                    }
                }
            }
            this.slotMap.unlockRead(lock);
            if (i3 != objArr.length + arrayLength) {
                Object[] objArr3 = new Object[i3];
                System.arraycopy(objArr, 0, objArr3, 0, i3);
                objArr = objArr3;
            }
            Context currentContext = Context.getCurrentContext();
            if (currentContext != null && currentContext.hasFeature(16)) {
                Arrays.sort(objArr, KEY_COMPARATOR);
            }
            return objArr;
        } catch (Throwable th) {
            this.slotMap.unlockRead(lock);
            throw th;
        }
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean has(int i2, Scriptable scriptable) {
        ExternalArrayData externalArrayData = this.externalData;
        return externalArrayData != null ? i2 < externalArrayData.getArrayLength() : this.slotMap.query(null, i2) != null;
    }

    @Deprecated
    public void setAttributes(int i2, Scriptable scriptable, int i3) {
        setAttributes(i2, i3);
    }

    public static <T extends Scriptable> String defineClass(Scriptable scriptable, Class<T> cls, boolean z2, boolean z3) throws IllegalAccessException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        BaseFunction baseFunctionBuildClassCtor = buildClassCtor(scriptable, cls, z2, z3);
        if (baseFunctionBuildClassCtor == null) {
            return null;
        }
        String className = baseFunctionBuildClassCtor.getClassPrototype().getClassName();
        defineProperty(scriptable, className, baseFunctionBuildClassCtor, 2);
        return className;
    }

    private static Scriptable getBase(Scriptable scriptable, int i2) {
        while (!scriptable.has(i2, scriptable) && (scriptable = scriptable.getPrototype()) != null) {
        }
        return scriptable;
    }

    public static boolean hasProperty(Scriptable scriptable, Symbol symbol) {
        return getBase(scriptable, symbol) != null;
    }

    public static void putProperty(Scriptable scriptable, Symbol symbol, Object obj) {
        Scriptable base = getBase(scriptable, symbol);
        if (base == null) {
            base = scriptable;
        }
        ensureSymbolScriptable(base).put(symbol, scriptable, obj);
    }

    protected void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject, boolean z2) {
        int iApplyDescriptorToAttributeBitset;
        Slot slot = getSlot(context, obj, SlotAccess.QUERY);
        boolean z3 = slot == null;
        if (z2) {
            checkPropertyChange(obj, slot == null ? null : slot.getPropertyDescriptor(context, this), scriptableObject);
        }
        boolean zIsAccessorDescriptor = isAccessorDescriptor(scriptableObject);
        if (slot == null) {
            slot = getSlot(context, obj, zIsAccessorDescriptor ? SlotAccess.MODIFY_GETTER_SETTER : SlotAccess.MODIFY);
            iApplyDescriptorToAttributeBitset = applyDescriptorToAttributeBitset(7, scriptableObject);
        } else {
            iApplyDescriptorToAttributeBitset = applyDescriptorToAttributeBitset(slot.getAttributes(), scriptableObject);
        }
        if (zIsAccessorDescriptor) {
            if (!(slot instanceof GetterSlot)) {
                slot = getSlot(context, obj, SlotAccess.MODIFY_GETTER_SETTER);
            }
            GetterSlot getterSlot = (GetterSlot) slot;
            Object property = getProperty(scriptableObject, TmpConstant.PROPERTY_IDENTIFIER_GET);
            Object obj2 = Scriptable.NOT_FOUND;
            if (property != obj2) {
                getterSlot.getter = property;
            }
            Object property2 = getProperty(scriptableObject, TmpConstant.PROPERTY_IDENTIFIER_SET);
            if (property2 != obj2) {
                getterSlot.setter = property2;
            }
            getterSlot.value = Undefined.instance;
            getterSlot.setAttributes(iApplyDescriptorToAttributeBitset);
            return;
        }
        if ((slot instanceof GetterSlot) && isDataDescriptor(scriptableObject)) {
            slot = getSlot(context, obj, SlotAccess.CONVERT_ACCESSOR_TO_DATA);
        }
        Object property3 = getProperty(scriptableObject, "value");
        if (property3 != Scriptable.NOT_FOUND) {
            slot.value = property3;
        } else if (z3) {
            slot.value = Undefined.instance;
        }
        slot.setAttributes(iApplyDescriptorToAttributeBitset);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void delete(int i2) {
        checkNotSealed(null, i2);
        this.slotMap.remove(null, i2);
    }

    public int getAttributes(String str) {
        return findAttributeSlot(str, 0, SlotAccess.QUERY).getAttributes();
    }

    public void setAttributes(String str, int i2) {
        checkNotSealed(str, 0);
        findAttributeSlot(str, 0, SlotAccess.MODIFY).setAttributes(i2);
    }

    public static boolean deleteProperty(Scriptable scriptable, int i2) {
        Scriptable base = getBase(scriptable, i2);
        if (base == null) {
            return true;
        }
        base.delete(i2);
        return !base.has(i2, scriptable);
    }

    private Slot findAttributeSlot(Symbol symbol, SlotAccess slotAccess) {
        Slot slot = this.slotMap.get(symbol, 0, slotAccess);
        if (slot != null) {
            return slot;
        }
        throw Context.reportRuntimeError1("msg.prop.not.found", symbol);
    }

    public static Object getProperty(Scriptable scriptable, Symbol symbol) {
        Object obj;
        Scriptable prototype = scriptable;
        do {
            obj = ensureSymbolScriptable(prototype).get(symbol, scriptable);
            if (obj != Scriptable.NOT_FOUND) {
                break;
            }
            prototype = prototype.getPrototype();
        } while (prototype != null);
        return obj;
    }

    public static <T> T getTypedProperty(Scriptable scriptable, String str, Class<T> cls) {
        Object property = getProperty(scriptable, str);
        if (property == Scriptable.NOT_FOUND) {
            property = null;
        }
        return cls.cast(Context.jsToJava(property, cls));
    }

    public void defineProperty(Symbol symbol, Object obj, int i2) {
        checkNotSealed(symbol, 0);
        put(symbol, this, obj);
        setAttributes(symbol, i2);
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object get(int i2, Scriptable scriptable) {
        ExternalArrayData externalArrayData = this.externalData;
        if (externalArrayData != null) {
            if (i2 < externalArrayData.getArrayLength()) {
                return this.externalData.getArrayElement(i2);
            }
            return Scriptable.NOT_FOUND;
        }
        Slot slotQuery = this.slotMap.query(null, i2);
        if (slotQuery == null) {
            return Scriptable.NOT_FOUND;
        }
        return slotQuery.getValue(scriptable);
    }

    public int getAttributes(int i2) {
        return findAttributeSlot(null, i2, SlotAccess.QUERY).getAttributes();
    }

    @Override // org.mozilla.javascript.Scriptable
    public void put(int i2, Scriptable scriptable, Object obj) {
        ExternalArrayData externalArrayData = this.externalData;
        if (externalArrayData != null) {
            if (i2 < externalArrayData.getArrayLength()) {
                this.externalData.setArrayElement(i2, obj);
                return;
            }
            throw new JavaScriptException(ScriptRuntime.newNativeError(Context.getCurrentContext(), this, TopLevel.NativeErrors.RangeError, new Object[]{"External array index out of bounds "}), null, 0);
        }
        if (putImpl(null, i2, scriptable, obj)) {
            return;
        }
        if (scriptable != this) {
            scriptable.put(i2, scriptable, obj);
            return;
        }
        throw Kit.codeBug();
    }

    public ScriptableObject(Scriptable scriptable, Scriptable scriptable2) {
        this.isExtensible = true;
        this.isSealed = false;
        if (scriptable != null) {
            this.parentScopeObject = scriptable;
            this.prototypeObject = scriptable2;
            this.slotMap = createSlotMap(0);
            return;
        }
        throw new IllegalArgumentException();
    }

    private static Scriptable getBase(Scriptable scriptable, Symbol symbol) {
        while (!ensureSymbolScriptable(scriptable).has(symbol, scriptable) && (scriptable = scriptable.getPrototype()) != null) {
        }
        return scriptable;
    }

    public static void putProperty(Scriptable scriptable, int i2, Object obj) {
        Scriptable base = getBase(scriptable, i2);
        if (base == null) {
            base = scriptable;
        }
        base.put(i2, scriptable, obj);
    }

    public void delete(Symbol symbol) {
        checkNotSealed(symbol, 0);
        this.slotMap.remove(symbol, 0);
    }

    public int getAttributes(Symbol symbol) {
        return findAttributeSlot(symbol, SlotAccess.QUERY).getAttributes();
    }

    public boolean has(Symbol symbol, Scriptable scriptable) {
        return this.slotMap.query(symbol, 0) != null;
    }

    public void setAttributes(int i2, int i3) {
        checkNotSealed(null, i2);
        findAttributeSlot(null, i2, SlotAccess.MODIFY).setAttributes(i3);
    }

    public static void defineProperty(Scriptable scriptable, String str, Object obj, int i2) {
        if (!(scriptable instanceof ScriptableObject)) {
            scriptable.put(str, scriptable, obj);
        } else {
            ((ScriptableObject) scriptable).defineProperty(str, obj, i2);
        }
    }

    public static Object getProperty(Scriptable scriptable, int i2) {
        Object obj;
        Scriptable prototype = scriptable;
        do {
            obj = prototype.get(i2, scriptable);
            if (obj != Scriptable.NOT_FOUND) {
                break;
            }
            prototype = prototype.getPrototype();
        } while (prototype != null);
        return obj;
    }

    public void setAttributes(Symbol symbol, int i2) {
        checkNotSealed(symbol, 0);
        findAttributeSlot(symbol, SlotAccess.MODIFY).setAttributes(i2);
    }

    public void defineProperty(String str, Class<?> cls, int i2) {
        int length = str.length();
        if (length != 0) {
            char[] cArr = new char[length + 3];
            str.getChars(0, length, cArr, 3);
            cArr[3] = Character.toUpperCase(cArr[3]);
            cArr[0] = 'g';
            cArr[1] = 'e';
            cArr[2] = 't';
            String str2 = new String(cArr);
            cArr[0] = 's';
            String str3 = new String(cArr);
            Method[] methodList = FunctionObject.getMethodList(cls);
            Method methodFindSingleMethod = FunctionObject.findSingleMethod(methodList, str2);
            Method methodFindSingleMethod2 = FunctionObject.findSingleMethod(methodList, str3);
            if (methodFindSingleMethod2 == null) {
                i2 |= 1;
            }
            int i3 = i2;
            if (methodFindSingleMethod2 == null) {
                methodFindSingleMethod2 = null;
            }
            defineProperty(str, null, methodFindSingleMethod, methodFindSingleMethod2, i3);
            return;
        }
        throw new IllegalArgumentException();
    }

    public Object get(Symbol symbol, Scriptable scriptable) {
        Slot slotQuery = this.slotMap.query(symbol, 0);
        if (slotQuery == null) {
            return Scriptable.NOT_FOUND;
        }
        return slotQuery.getValue(scriptable);
    }

    public void put(Symbol symbol, Scriptable scriptable, Object obj) {
        if (putImpl(symbol, 0, scriptable, obj)) {
            return;
        }
        if (scriptable != this) {
            ensureSymbolScriptable(scriptable).put(symbol, scriptable, obj);
            return;
        }
        throw Kit.codeBug();
    }

    public Object get(Object obj) {
        Object obj2;
        if (obj instanceof String) {
            obj2 = get((String) obj, this);
        } else if (obj instanceof Symbol) {
            obj2 = get((Symbol) obj, this);
        } else {
            obj2 = obj instanceof Number ? get(((Number) obj).intValue(), this) : null;
        }
        if (obj2 == Scriptable.NOT_FOUND || obj2 == Undefined.instance) {
            return null;
        }
        return obj2 instanceof Wrapper ? ((Wrapper) obj2).unwrap() : obj2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void defineProperty(java.lang.String r9, java.lang.Object r10, java.lang.reflect.Method r11, java.lang.reflect.Method r12, int r13) {
        /*
            r8 = this;
            r0 = 0
            r1 = 1
            r2 = 0
            if (r11 == 0) goto L4e
            org.mozilla.javascript.MemberBox r3 = new org.mozilla.javascript.MemberBox
            r3.<init>(r11)
            int r4 = r11.getModifiers()
            boolean r4 = java.lang.reflect.Modifier.isStatic(r4)
            if (r4 != 0) goto L1c
            if (r10 == 0) goto L18
            r4 = r1
            goto L19
        L18:
            r4 = r0
        L19:
            r3.delegateTo = r10
            goto L21
        L1c:
            java.lang.Class r4 = java.lang.Void.TYPE
            r3.delegateTo = r4
            r4 = r1
        L21:
            java.lang.Class[] r5 = r11.getParameterTypes()
            int r6 = r5.length
            if (r6 != 0) goto L2f
            if (r4 == 0) goto L2d
            java.lang.String r4 = "msg.obj.getter.parms"
            goto L42
        L2d:
            r4 = r2
            goto L42
        L2f:
            int r6 = r5.length
            java.lang.String r7 = "msg.bad.getter.parms"
            if (r6 != r1) goto L41
            r5 = r5[r0]
            java.lang.Class<org.mozilla.javascript.Scriptable> r6 = org.mozilla.javascript.ScriptRuntime.ScriptableClass
            if (r5 == r6) goto L3f
            java.lang.Class<?> r6 = org.mozilla.javascript.ScriptRuntime.ScriptableObjectClass
            if (r5 == r6) goto L3f
            goto L41
        L3f:
            if (r4 != 0) goto L2d
        L41:
            r4 = r7
        L42:
            if (r4 != 0) goto L45
            goto L4f
        L45:
            java.lang.String r9 = r11.toString()
            org.mozilla.javascript.EvaluatorException r9 = org.mozilla.javascript.Context.reportRuntimeError1(r4, r9)
            throw r9
        L4e:
            r3 = r2
        L4f:
            if (r12 == 0) goto Laf
            java.lang.Class r11 = r12.getReturnType()
            java.lang.Class r4 = java.lang.Void.TYPE
            if (r11 != r4) goto La4
            org.mozilla.javascript.MemberBox r11 = new org.mozilla.javascript.MemberBox
            r11.<init>(r12)
            int r5 = r12.getModifiers()
            boolean r5 = java.lang.reflect.Modifier.isStatic(r5)
            if (r5 != 0) goto L70
            if (r10 == 0) goto L6c
            r4 = r1
            goto L6d
        L6c:
            r4 = r0
        L6d:
            r11.delegateTo = r10
            goto L73
        L70:
            r11.delegateTo = r4
            r4 = r1
        L73:
            java.lang.Class[] r10 = r12.getParameterTypes()
            int r5 = r10.length
            if (r5 != r1) goto L7f
            if (r4 == 0) goto L97
            java.lang.String r2 = "msg.setter2.expected"
            goto L97
        L7f:
            int r1 = r10.length
            r5 = 2
            if (r1 != r5) goto L95
            r10 = r10[r0]
            java.lang.Class<org.mozilla.javascript.Scriptable> r1 = org.mozilla.javascript.ScriptRuntime.ScriptableClass
            if (r10 == r1) goto L90
            java.lang.Class<?> r1 = org.mozilla.javascript.ScriptRuntime.ScriptableObjectClass
            if (r10 == r1) goto L90
            java.lang.String r2 = "msg.setter2.parms"
            goto L97
        L90:
            if (r4 != 0) goto L97
            java.lang.String r2 = "msg.setter1.parms"
            goto L97
        L95:
            java.lang.String r2 = "msg.setter.parms"
        L97:
            if (r2 != 0) goto L9b
            r2 = r11
            goto Laf
        L9b:
            java.lang.String r9 = r12.toString()
            org.mozilla.javascript.EvaluatorException r9 = org.mozilla.javascript.Context.reportRuntimeError1(r2, r9)
            throw r9
        La4:
            java.lang.String r9 = "msg.setter.return"
            java.lang.String r10 = r12.toString()
            org.mozilla.javascript.EvaluatorException r9 = org.mozilla.javascript.Context.reportRuntimeError1(r9, r10)
            throw r9
        Laf:
            org.mozilla.javascript.SlotMapContainer r10 = r8.slotMap
            org.mozilla.javascript.ScriptableObject$SlotAccess r11 = org.mozilla.javascript.ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER
            org.mozilla.javascript.ScriptableObject$Slot r9 = r10.get(r9, r0, r11)
            org.mozilla.javascript.ScriptableObject$GetterSlot r9 = (org.mozilla.javascript.ScriptableObject.GetterSlot) r9
            r9.setAttributes(r13)
            r9.getter = r3
            r9.setter = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptableObject.defineProperty(java.lang.String, java.lang.Object, java.lang.reflect.Method, java.lang.reflect.Method, int):void");
    }
}
