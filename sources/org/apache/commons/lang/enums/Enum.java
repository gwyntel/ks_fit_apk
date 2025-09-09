package org.apache.commons.lang.enums;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

/* loaded from: classes5.dex */
public abstract class Enum implements Comparable, Serializable {
    private static final Map EMPTY_MAP = Collections.unmodifiableMap(new HashMap(0));
    private static Map cEnumClasses = new WeakHashMap();
    static /* synthetic */ Class class$org$apache$commons$lang$enums$Enum = null;
    static /* synthetic */ Class class$org$apache$commons$lang$enums$ValuedEnum = null;
    private static final long serialVersionUID = -487045951170455942L;
    private final transient int iHashCode;
    private final String iName;
    protected transient String iToString = null;

    private static class Entry {

        /* renamed from: a, reason: collision with root package name */
        final Map f26630a;

        /* renamed from: b, reason: collision with root package name */
        final Map f26631b;

        /* renamed from: c, reason: collision with root package name */
        final List f26632c;

        /* renamed from: d, reason: collision with root package name */
        final List f26633d;

        protected Entry() {
            HashMap map = new HashMap();
            this.f26630a = map;
            this.f26631b = Collections.unmodifiableMap(map);
            ArrayList arrayList = new ArrayList(25);
            this.f26632c = arrayList;
            this.f26633d = Collections.unmodifiableList(arrayList);
        }
    }

    protected Enum(String str) {
        init(str);
        this.iName = str;
        this.iHashCode = getEnumClass().hashCode() + 7 + (str.hashCode() * 3);
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    private static Entry createEntry(Class cls) {
        Entry entry = new Entry();
        Class superclass = cls.getSuperclass();
        while (true) {
            if (superclass == null) {
                break;
            }
            Class clsClass$ = class$org$apache$commons$lang$enums$Enum;
            if (clsClass$ == null) {
                clsClass$ = class$("org.apache.commons.lang.enums.Enum");
                class$org$apache$commons$lang$enums$Enum = clsClass$;
            }
            if (superclass == clsClass$) {
                break;
            }
            Class clsClass$2 = class$org$apache$commons$lang$enums$ValuedEnum;
            if (clsClass$2 == null) {
                clsClass$2 = class$("org.apache.commons.lang.enums.ValuedEnum");
                class$org$apache$commons$lang$enums$ValuedEnum = clsClass$2;
            }
            if (superclass == clsClass$2) {
                break;
            }
            Entry entry2 = (Entry) cEnumClasses.get(superclass);
            if (entry2 != null) {
                entry.f26632c.addAll(entry2.f26632c);
                entry.f26630a.putAll(entry2.f26630a);
                break;
            }
            superclass = superclass.getSuperclass();
        }
        return entry;
    }

    private static Entry getEntry(Class cls) throws ClassNotFoundException {
        if (cls == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        Class clsClass$ = class$org$apache$commons$lang$enums$Enum;
        if (clsClass$ == null) {
            clsClass$ = class$("org.apache.commons.lang.enums.Enum");
            class$org$apache$commons$lang$enums$Enum = clsClass$;
        }
        if (!clsClass$.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("The Class must be a subclass of Enum");
        }
        Entry entry = (Entry) cEnumClasses.get(cls);
        if (entry != null) {
            return entry;
        }
        try {
            Class.forName(cls.getName(), true, cls.getClassLoader());
            return (Entry) cEnumClasses.get(cls);
        } catch (Exception unused) {
            return entry;
        }
    }

    protected static Enum getEnum(Class cls, String str) throws ClassNotFoundException {
        Entry entry = getEntry(cls);
        if (entry == null) {
            return null;
        }
        return (Enum) entry.f26630a.get(str);
    }

    protected static List getEnumList(Class cls) throws ClassNotFoundException {
        Entry entry = getEntry(cls);
        return entry == null ? Collections.EMPTY_LIST : entry.f26633d;
    }

    protected static Map getEnumMap(Class cls) throws ClassNotFoundException {
        Entry entry = getEntry(cls);
        return entry == null ? EMPTY_MAP : entry.f26631b;
    }

    private String getNameInOtherClassLoader(Object obj) {
        try {
            return (String) obj.getClass().getMethod("getName", null).invoke(obj, null);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            throw new IllegalStateException("This should not happen");
        }
    }

    private void init(String str) {
        Entry entryCreateEntry;
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException("The Enum name must not be empty or null");
        }
        Class<?> enumClass = getEnumClass();
        if (enumClass == null) {
            throw new IllegalArgumentException("getEnumClass() must not be null");
        }
        for (Class<?> superclass = getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            Class<?> clsClass$ = class$org$apache$commons$lang$enums$Enum;
            if (clsClass$ == null) {
                clsClass$ = class$("org.apache.commons.lang.enums.Enum");
                class$org$apache$commons$lang$enums$Enum = clsClass$;
            }
            if (superclass == clsClass$) {
                break;
            }
            Class<?> clsClass$2 = class$org$apache$commons$lang$enums$ValuedEnum;
            if (clsClass$2 == null) {
                clsClass$2 = class$("org.apache.commons.lang.enums.ValuedEnum");
                class$org$apache$commons$lang$enums$ValuedEnum = clsClass$2;
            }
            if (superclass == clsClass$2) {
                break;
            }
            if (superclass == enumClass) {
                Class clsClass$3 = class$org$apache$commons$lang$enums$Enum;
                if (clsClass$3 == null) {
                    clsClass$3 = class$("org.apache.commons.lang.enums.Enum");
                    class$org$apache$commons$lang$enums$Enum = clsClass$3;
                }
                synchronized (clsClass$3) {
                    try {
                        entryCreateEntry = (Entry) cEnumClasses.get(enumClass);
                        if (entryCreateEntry == null) {
                            entryCreateEntry = createEntry(enumClass);
                            WeakHashMap weakHashMap = new WeakHashMap();
                            weakHashMap.putAll(cEnumClasses);
                            weakHashMap.put(enumClass, entryCreateEntry);
                            cEnumClasses = weakHashMap;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (!entryCreateEntry.f26630a.containsKey(str)) {
                    entryCreateEntry.f26630a.put(str, this);
                    entryCreateEntry.f26632c.add(this);
                    return;
                } else {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("The Enum name must be unique, '");
                    stringBuffer.append(str);
                    stringBuffer.append("' has already been added");
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
            }
        }
        throw new IllegalArgumentException("getEnumClass() must return a superclass of this class");
    }

    protected static Iterator iterator(Class cls) {
        return getEnumList(cls).iterator();
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj == this) {
            return 0;
        }
        if (obj.getClass() == getClass()) {
            return this.iName.compareTo(((Enum) obj).iName);
        }
        if (obj.getClass().getName().equals(getClass().getName())) {
            return this.iName.compareTo(getNameInOtherClassLoader(obj));
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Different enum class '");
        stringBuffer.append(ClassUtils.getShortClassName(obj.getClass()));
        stringBuffer.append("'");
        throw new ClassCastException(stringBuffer.toString());
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == getClass()) {
            return this.iName.equals(((Enum) obj).iName);
        }
        if (obj.getClass().getName().equals(getClass().getName())) {
            return this.iName.equals(getNameInOtherClassLoader(obj));
        }
        return false;
    }

    public Class getEnumClass() {
        return getClass();
    }

    public final String getName() {
        return this.iName;
    }

    public final int hashCode() {
        return this.iHashCode;
    }

    protected Object readResolve() {
        Entry entry = (Entry) cEnumClasses.get(getEnumClass());
        if (entry == null) {
            return null;
        }
        return entry.f26630a.get(getName());
    }

    public String toString() {
        if (this.iToString == null) {
            String shortClassName = ClassUtils.getShortClassName(getEnumClass());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(shortClassName);
            stringBuffer.append("[");
            stringBuffer.append(getName());
            stringBuffer.append("]");
            this.iToString = stringBuffer.toString();
        }
        return this.iToString;
    }
}
