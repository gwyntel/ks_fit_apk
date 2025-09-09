package org.mozilla.javascript;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes5.dex */
public class NativeObject extends IdScriptableObject implements Map {
    private static final int ConstructorId_assign = -15;
    private static final int ConstructorId_create = -9;
    private static final int ConstructorId_defineProperties = -8;
    private static final int ConstructorId_defineProperty = -5;
    private static final int ConstructorId_freeze = -13;
    private static final int ConstructorId_getOwnPropertyDescriptor = -4;
    private static final int ConstructorId_getOwnPropertyNames = -3;
    private static final int ConstructorId_getOwnPropertySymbols = -14;
    private static final int ConstructorId_getPrototypeOf = -1;
    private static final int ConstructorId_is = -16;
    private static final int ConstructorId_isExtensible = -6;
    private static final int ConstructorId_isFrozen = -11;
    private static final int ConstructorId_isSealed = -10;
    private static final int ConstructorId_keys = -2;
    private static final int ConstructorId_preventExtensions = -7;
    private static final int ConstructorId_seal = -12;
    private static final int Id___defineGetter__ = 9;
    private static final int Id___defineSetter__ = 10;
    private static final int Id___lookupGetter__ = 11;
    private static final int Id___lookupSetter__ = 12;
    private static final int Id_constructor = 1;
    private static final int Id_hasOwnProperty = 5;
    private static final int Id_isPrototypeOf = 7;
    private static final int Id_propertyIsEnumerable = 6;
    private static final int Id_toLocaleString = 3;
    private static final int Id_toSource = 8;
    private static final int Id_toString = 2;
    private static final int Id_valueOf = 4;
    private static final int MAX_PROTOTYPE_ID = 12;
    private static final Object OBJECT_TAG = "Object";
    static final long serialVersionUID = -6345305608474346996L;

    class EntrySet extends AbstractSet<Map.Entry<Object, Object>> {
        EntrySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Object, Object>> iterator() {
            return new Iterator<Map.Entry<Object, Object>>() { // from class: org.mozilla.javascript.NativeObject.EntrySet.1
                Object[] ids;
                Object key = null;
                int index = 0;

                {
                    this.ids = NativeObject.this.getIds();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.index < this.ids.length;
                }

                @Override // java.util.Iterator
                public void remove() {
                    Object obj = this.key;
                    if (obj == null) {
                        throw new IllegalStateException();
                    }
                    NativeObject.this.remove(obj);
                    this.key = null;
                }

                @Override // java.util.Iterator
                public Map.Entry<Object, Object> next() {
                    Object[] objArr = this.ids;
                    int i2 = this.index;
                    this.index = i2 + 1;
                    final Object obj = objArr[i2];
                    this.key = obj;
                    final Object obj2 = NativeObject.this.get(obj);
                    return new Map.Entry<Object, Object>() { // from class: org.mozilla.javascript.NativeObject.EntrySet.1.1
                        @Override // java.util.Map.Entry
                        public boolean equals(Object obj3) {
                            if (!(obj3 instanceof Map.Entry)) {
                                return false;
                            }
                            Map.Entry entry = (Map.Entry) obj3;
                            Object obj4 = obj;
                            if (obj4 == null) {
                                if (entry.getKey() != null) {
                                    return false;
                                }
                            } else if (!obj4.equals(entry.getKey())) {
                                return false;
                            }
                            Object obj5 = obj2;
                            if (obj5 == null) {
                                if (entry.getValue() != null) {
                                    return false;
                                }
                            } else if (!obj5.equals(entry.getValue())) {
                                return false;
                            }
                            return true;
                        }

                        @Override // java.util.Map.Entry
                        public Object getKey() {
                            return obj;
                        }

                        @Override // java.util.Map.Entry
                        public Object getValue() {
                            return obj2;
                        }

                        @Override // java.util.Map.Entry
                        public int hashCode() {
                            Object obj3 = obj;
                            int iHashCode = obj3 == null ? 0 : obj3.hashCode();
                            Object obj4 = obj2;
                            return iHashCode ^ (obj4 != null ? obj4.hashCode() : 0);
                        }

                        @Override // java.util.Map.Entry
                        public Object setValue(Object obj3) {
                            throw new UnsupportedOperationException();
                        }

                        public String toString() {
                            return obj + ContainerUtils.KEY_VALUE_DELIMITER + obj2;
                        }
                    };
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return NativeObject.this.size();
        }
    }

    class KeySet extends AbstractSet<Object> {
        KeySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return NativeObject.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Object> iterator() {
            return new Iterator<Object>() { // from class: org.mozilla.javascript.NativeObject.KeySet.1
                Object[] ids;
                int index = 0;
                Object key;

                {
                    this.ids = NativeObject.this.getIds();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.index < this.ids.length;
                }

                @Override // java.util.Iterator
                public Object next() {
                    try {
                        Object[] objArr = this.ids;
                        int i2 = this.index;
                        this.index = i2 + 1;
                        Object obj = objArr[i2];
                        this.key = obj;
                        return obj;
                    } catch (ArrayIndexOutOfBoundsException unused) {
                        this.key = null;
                        throw new NoSuchElementException();
                    }
                }

                @Override // java.util.Iterator
                public void remove() {
                    Object obj = this.key;
                    if (obj == null) {
                        throw new IllegalStateException();
                    }
                    NativeObject.this.remove(obj);
                    this.key = null;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return NativeObject.this.size();
        }
    }

    class ValueCollection extends AbstractCollection<Object> {
        ValueCollection() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Object> iterator() {
            return new Iterator<Object>() { // from class: org.mozilla.javascript.NativeObject.ValueCollection.1
                Object[] ids;
                int index = 0;
                Object key;

                {
                    this.ids = NativeObject.this.getIds();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.index < this.ids.length;
                }

                @Override // java.util.Iterator
                public Object next() {
                    NativeObject nativeObject = NativeObject.this;
                    Object[] objArr = this.ids;
                    int i2 = this.index;
                    this.index = i2 + 1;
                    Object obj = objArr[i2];
                    this.key = obj;
                    return nativeObject.get(obj);
                }

                @Override // java.util.Iterator
                public void remove() {
                    Object obj = this.key;
                    if (obj == null) {
                        throw new IllegalStateException();
                    }
                    NativeObject.this.remove(obj);
                    this.key = null;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return NativeObject.this.size();
        }
    }

    private Scriptable getCompatibleObject(Context context, Scriptable scriptable, Object obj) {
        return context.getLanguageVersion() >= 200 ? ScriptableObject.ensureScriptable(ScriptRuntime.toObject(context, scriptable, obj)) : ScriptableObject.ensureScriptable(obj);
    }

    static void init(Scriptable scriptable, boolean z2) {
        new NativeObject().exportAsJSClass(12, scriptable, z2);
    }

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        if (obj instanceof String) {
            return has((String) obj, this);
        }
        if (obj instanceof Number) {
            return has(((Number) obj).intValue(), this);
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        for (Object obj2 : values()) {
            if (obj == obj2) {
                return true;
            }
            if (obj != null && obj.equals(obj2)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public Set<Map.Entry<Object, Object>> entrySet() {
        return new EntrySet();
    }

    /* JADX WARN: Removed duplicated region for block: B:90:0x00fc  */
    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object execIdCall(org.mozilla.javascript.IdFunctionObject r9, org.mozilla.javascript.Context r10, org.mozilla.javascript.Scriptable r11, org.mozilla.javascript.Scriptable r12, java.lang.Object[] r13) {
        /*
            Method dump skipped, instructions count: 1242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeObject.execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        Object obj = OBJECT_TAG;
        addIdFunctionProperty(idFunctionObject, obj, -1, "getPrototypeOf", 1);
        addIdFunctionProperty(idFunctionObject, obj, -2, "keys", 1);
        addIdFunctionProperty(idFunctionObject, obj, -3, "getOwnPropertyNames", 1);
        addIdFunctionProperty(idFunctionObject, obj, -14, "getOwnPropertySymbols", 1);
        addIdFunctionProperty(idFunctionObject, obj, -4, "getOwnPropertyDescriptor", 2);
        addIdFunctionProperty(idFunctionObject, obj, -5, "defineProperty", 3);
        addIdFunctionProperty(idFunctionObject, obj, -6, "isExtensible", 1);
        addIdFunctionProperty(idFunctionObject, obj, -7, "preventExtensions", 1);
        addIdFunctionProperty(idFunctionObject, obj, -8, "defineProperties", 2);
        addIdFunctionProperty(idFunctionObject, obj, -9, "create", 2);
        addIdFunctionProperty(idFunctionObject, obj, -10, "isSealed", 1);
        addIdFunctionProperty(idFunctionObject, obj, -11, "isFrozen", 1);
        addIdFunctionProperty(idFunctionObject, obj, -12, "seal", 1);
        addIdFunctionProperty(idFunctionObject, obj, -13, "freeze", 1);
        addIdFunctionProperty(idFunctionObject, obj, -15, "assign", 2);
        addIdFunctionProperty(idFunctionObject, obj, -16, "is", 2);
        super.fillConstructorProperties(idFunctionObject);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0089  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r10) {
        /*
            r9 = this;
            int r0 = r10.length()
            r1 = 7
            r2 = 0
            if (r0 == r1) goto L8c
            r3 = 3
            r4 = 116(0x74, float:1.63E-43)
            r5 = 2
            r6 = 8
            if (r0 == r6) goto L77
            r7 = 11
            if (r0 == r7) goto L73
            r8 = 16
            if (r0 == r8) goto L40
            r5 = 20
            if (r0 == r5) goto L3c
            r5 = 13
            if (r0 == r5) goto L39
            r1 = 14
            if (r0 == r1) goto L26
            goto L89
        L26:
            char r0 = r10.charAt(r2)
            r1 = 104(0x68, float:1.46E-43)
            if (r0 != r1) goto L33
            java.lang.String r0 = "hasOwnProperty"
            r1 = 5
            goto L8f
        L33:
            if (r0 != r4) goto L89
            java.lang.String r0 = "toLocaleString"
            r1 = r3
            goto L8f
        L39:
            java.lang.String r0 = "isPrototypeOf"
            goto L8f
        L3c:
            java.lang.String r0 = "propertyIsEnumerable"
            r1 = 6
            goto L8f
        L40:
            char r0 = r10.charAt(r5)
            r1 = 100
            r3 = 83
            r4 = 71
            if (r0 != r1) goto L5e
            char r0 = r10.charAt(r6)
            if (r0 != r4) goto L57
            java.lang.String r0 = "__defineGetter__"
            r1 = 9
            goto L8f
        L57:
            if (r0 != r3) goto L89
            java.lang.String r0 = "__defineSetter__"
            r1 = 10
            goto L8f
        L5e:
            r1 = 108(0x6c, float:1.51E-43)
            if (r0 != r1) goto L89
            char r0 = r10.charAt(r6)
            if (r0 != r4) goto L6c
            java.lang.String r0 = "__lookupGetter__"
            r1 = r7
            goto L8f
        L6c:
            if (r0 != r3) goto L89
            java.lang.String r0 = "__lookupSetter__"
            r1 = 12
            goto L8f
        L73:
            java.lang.String r0 = "constructor"
            r1 = 1
            goto L8f
        L77:
            char r0 = r10.charAt(r3)
            r1 = 111(0x6f, float:1.56E-43)
            if (r0 != r1) goto L83
            java.lang.String r0 = "toSource"
            r1 = r6
            goto L8f
        L83:
            if (r0 != r4) goto L89
            java.lang.String r0 = "toString"
            r1 = r5
            goto L8f
        L89:
            r0 = 0
            r1 = r2
            goto L8f
        L8c:
            java.lang.String r0 = "valueOf"
            r1 = 4
        L8f:
            if (r0 == 0) goto L9a
            if (r0 == r10) goto L9a
            boolean r10 = r0.equals(r10)
            if (r10 != 0) goto L9a
            goto L9b
        L9a:
            r2 = r1
        L9b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeObject.findPrototypeId(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Object";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        String str2;
        int i3 = 1;
        switch (i2) {
            case 1:
                str = "constructor";
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 2:
                str = "toString";
                i3 = 0;
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 3:
                str = "toLocaleString";
                i3 = 0;
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 4:
                str = "valueOf";
                i3 = 0;
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 5:
                str = "hasOwnProperty";
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 6:
                str = "propertyIsEnumerable";
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 7:
                str = "isPrototypeOf";
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 8:
                str = "toSource";
                i3 = 0;
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 9:
                str2 = "__defineGetter__";
                i3 = 2;
                str = str2;
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 10:
                str2 = "__defineSetter__";
                i3 = 2;
                str = str2;
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 11:
                str = "__lookupGetter__";
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            case 12:
                str = "__lookupSetter__";
                initPrototypeMethod(OBJECT_TAG, i2, str, i3);
                return;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
    }

    @Override // java.util.Map
    public Set<Object> keySet() {
        return new KeySet();
    }

    @Override // java.util.Map
    public Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public Object remove(Object obj) {
        Object obj2 = get(obj);
        if (obj instanceof String) {
            delete((String) obj);
        } else if (obj instanceof Number) {
            delete(((Number) obj).intValue());
        }
        return obj2;
    }

    public String toString() {
        return ScriptRuntime.defaultObjectToString(this);
    }

    @Override // java.util.Map
    public Collection<Object> values() {
        return new ValueCollection();
    }
}
