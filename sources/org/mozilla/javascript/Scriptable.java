package org.mozilla.javascript;

/* loaded from: classes5.dex */
public interface Scriptable {
    public static final Object NOT_FOUND = UniqueTag.NOT_FOUND;

    void delete(int i2);

    void delete(String str);

    Object get(int i2, Scriptable scriptable);

    Object get(String str, Scriptable scriptable);

    String getClassName();

    Object getDefaultValue(Class<?> cls);

    Object[] getIds();

    Scriptable getParentScope();

    Scriptable getPrototype();

    boolean has(int i2, Scriptable scriptable);

    boolean has(String str, Scriptable scriptable);

    boolean hasInstance(Scriptable scriptable);

    void put(int i2, Scriptable scriptable, Object obj);

    void put(String str, Scriptable scriptable, Object obj);

    void setParentScope(Scriptable scriptable);

    void setPrototype(Scriptable scriptable);
}
