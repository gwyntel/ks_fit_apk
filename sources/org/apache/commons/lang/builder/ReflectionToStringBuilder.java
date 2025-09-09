package org.apache.commons.lang.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.lang.ArrayUtils;

/* loaded from: classes5.dex */
public class ReflectionToStringBuilder extends ToStringBuilder {
    private boolean appendStatics;
    private boolean appendTransients;
    private String[] excludeFieldNames;
    private Class upToClass;

    public ReflectionToStringBuilder(Object obj) {
        super(obj);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
    }

    static String[] d(Collection collection) {
        return collection == null ? ArrayUtils.EMPTY_STRING_ARRAY : e(collection.toArray());
    }

    static String[] e(Object[] objArr) {
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            if (obj != null) {
                arrayList.add(obj.toString());
            }
        }
        return (String[]) arrayList.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public static String toString(Object obj) {
        return toString(obj, null, false, false, null);
    }

    public static String toStringExclude(Object obj, String str) {
        return toStringExclude(obj, new String[]{str});
    }

    protected boolean a(Field field) {
        if (field.getName().indexOf(36) != -1) {
            return false;
        }
        if (Modifier.isTransient(field.getModifiers()) && !isAppendTransients()) {
            return false;
        }
        if (!Modifier.isStatic(field.getModifiers()) || isAppendStatics()) {
            return getExcludeFieldNames() == null || Arrays.binarySearch(getExcludeFieldNames(), field.getName()) < 0;
        }
        return false;
    }

    protected void b(Class cls) throws SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (cls.isArray()) {
            reflectionAppendArray(getObject());
            return;
        }
        Field[] declaredFields = cls.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        for (Field field : declaredFields) {
            String name = field.getName();
            if (a(field)) {
                try {
                    append(name, c(field));
                } catch (IllegalAccessException e2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Unexpected IllegalAccessException: ");
                    stringBuffer.append(e2.getMessage());
                    throw new InternalError(stringBuffer.toString());
                }
            }
        }
    }

    protected Object c(Field field) {
        return field.get(getObject());
    }

    public String[] getExcludeFieldNames() {
        return this.excludeFieldNames;
    }

    public Class getUpToClass() {
        return this.upToClass;
    }

    public boolean isAppendStatics() {
        return this.appendStatics;
    }

    public boolean isAppendTransients() {
        return this.appendTransients;
    }

    public ToStringBuilder reflectionAppendArray(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        getStyle().reflectionAppendArrayDetail(getStringBuffer(), null, obj);
        return this;
    }

    public void setAppendStatics(boolean z2) {
        this.appendStatics = z2;
    }

    public void setAppendTransients(boolean z2) {
        this.appendTransients = z2;
    }

    public ReflectionToStringBuilder setExcludeFieldNames(String[] strArr) {
        if (strArr == null) {
            this.excludeFieldNames = null;
        } else {
            String[] strArrE = e(strArr);
            this.excludeFieldNames = strArrE;
            Arrays.sort(strArrE);
        }
        return this;
    }

    public void setUpToClass(Class cls) {
        Object object;
        if (cls != null && (object = getObject()) != null && !cls.isInstance(object)) {
            throw new IllegalArgumentException("Specified class is not a superclass of the object");
        }
        this.upToClass = cls;
    }

    public static String toString(Object obj, ToStringStyle toStringStyle) {
        return toString(obj, toStringStyle, false, false, null);
    }

    public static String toStringExclude(Object obj, Collection collection) {
        return toStringExclude(obj, d(collection));
    }

    public static String toString(Object obj, ToStringStyle toStringStyle, boolean z2) {
        return toString(obj, toStringStyle, z2, false, null);
    }

    public static String toStringExclude(Object obj, String[] strArr) {
        return new ReflectionToStringBuilder(obj).setExcludeFieldNames(strArr).toString();
    }

    public static String toString(Object obj, ToStringStyle toStringStyle, boolean z2, boolean z3) {
        return toString(obj, toStringStyle, z2, z3, null);
    }

    public ReflectionToStringBuilder(Object obj, ToStringStyle toStringStyle) {
        super(obj, toStringStyle);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
    }

    public static String toString(Object obj, ToStringStyle toStringStyle, boolean z2, boolean z3, Class cls) {
        return new ReflectionToStringBuilder(obj, toStringStyle, null, cls, z2, z3).toString();
    }

    public static String toString(Object obj, ToStringStyle toStringStyle, boolean z2, Class cls) {
        return new ReflectionToStringBuilder(obj, toStringStyle, null, cls, z2).toString();
    }

    @Override // org.apache.commons.lang.builder.ToStringBuilder
    public String toString() throws SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (getObject() == null) {
            return getStyle().getNullText();
        }
        Class<?> superclass = getObject().getClass();
        b(superclass);
        while (superclass.getSuperclass() != null && superclass != getUpToClass()) {
            superclass = superclass.getSuperclass();
            b(superclass);
        }
        return super.toString();
    }

    public ReflectionToStringBuilder(Object obj, ToStringStyle toStringStyle, StringBuffer stringBuffer) {
        super(obj, toStringStyle, stringBuffer);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
    }

    public ReflectionToStringBuilder(Object obj, ToStringStyle toStringStyle, StringBuffer stringBuffer, Class cls, boolean z2) {
        super(obj, toStringStyle, stringBuffer);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
        setUpToClass(cls);
        setAppendTransients(z2);
    }

    public ReflectionToStringBuilder(Object obj, ToStringStyle toStringStyle, StringBuffer stringBuffer, Class cls, boolean z2, boolean z3) {
        super(obj, toStringStyle, stringBuffer);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
        setUpToClass(cls);
        setAppendTransients(z2);
        setAppendStatics(z3);
    }
}
