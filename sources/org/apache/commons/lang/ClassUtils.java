package org.apache.commons.lang;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.alipay.sdk.m.u.i;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.vivo.push.PushClientConstants;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class ClassUtils {
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    public static final char PACKAGE_SEPARATOR_CHAR = '.';

    /* renamed from: a, reason: collision with root package name */
    static /* synthetic */ Class f26604a;
    private static final Map abbreviationMap;

    /* renamed from: b, reason: collision with root package name */
    static /* synthetic */ Class f26605b;

    /* renamed from: c, reason: collision with root package name */
    static /* synthetic */ Class f26606c;

    /* renamed from: d, reason: collision with root package name */
    static /* synthetic */ Class f26607d;

    /* renamed from: e, reason: collision with root package name */
    static /* synthetic */ Class f26608e;

    /* renamed from: f, reason: collision with root package name */
    static /* synthetic */ Class f26609f;

    /* renamed from: g, reason: collision with root package name */
    static /* synthetic */ Class f26610g;

    /* renamed from: h, reason: collision with root package name */
    static /* synthetic */ Class f26611h;

    /* renamed from: i, reason: collision with root package name */
    static /* synthetic */ Class f26612i;
    private static final Map primitiveWrapperMap;
    private static final Map reverseAbbreviationMap;
    private static final Map wrapperPrimitiveMap;
    public static final String PACKAGE_SEPARATOR = String.valueOf('.');
    public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');

    static {
        HashMap map = new HashMap();
        primitiveWrapperMap = map;
        Class cls = Boolean.TYPE;
        Class clsA = f26604a;
        if (clsA == null) {
            clsA = a("java.lang.Boolean");
            f26604a = clsA;
        }
        map.put(cls, clsA);
        Class cls2 = Byte.TYPE;
        Class clsA2 = f26605b;
        if (clsA2 == null) {
            clsA2 = a("java.lang.Byte");
            f26605b = clsA2;
        }
        map.put(cls2, clsA2);
        Class cls3 = Character.TYPE;
        Class clsA3 = f26606c;
        if (clsA3 == null) {
            clsA3 = a("java.lang.Character");
            f26606c = clsA3;
        }
        map.put(cls3, clsA3);
        Class cls4 = Short.TYPE;
        Class clsA4 = f26607d;
        if (clsA4 == null) {
            clsA4 = a("java.lang.Short");
            f26607d = clsA4;
        }
        map.put(cls4, clsA4);
        Class cls5 = Integer.TYPE;
        Class clsA5 = f26608e;
        if (clsA5 == null) {
            clsA5 = a("java.lang.Integer");
            f26608e = clsA5;
        }
        map.put(cls5, clsA5);
        Class cls6 = Long.TYPE;
        Class clsA6 = f26609f;
        if (clsA6 == null) {
            clsA6 = a("java.lang.Long");
            f26609f = clsA6;
        }
        map.put(cls6, clsA6);
        Class cls7 = Double.TYPE;
        Class clsA7 = f26610g;
        if (clsA7 == null) {
            clsA7 = a("java.lang.Double");
            f26610g = clsA7;
        }
        map.put(cls7, clsA7);
        Class cls8 = Float.TYPE;
        Class clsA8 = f26611h;
        if (clsA8 == null) {
            clsA8 = a("java.lang.Float");
            f26611h = clsA8;
        }
        map.put(cls8, clsA8);
        Class cls9 = Void.TYPE;
        map.put(cls9, cls9);
        wrapperPrimitiveMap = new HashMap();
        for (Class cls10 : map.keySet()) {
            Class cls11 = (Class) primitiveWrapperMap.get(cls10);
            if (!cls10.equals(cls11)) {
                wrapperPrimitiveMap.put(cls11, cls10);
            }
        }
        abbreviationMap = new HashMap();
        reverseAbbreviationMap = new HashMap();
        addAbbreviation("int", "I");
        addAbbreviation(TypedValues.Custom.S_BOOLEAN, "Z");
        addAbbreviation("float", "F");
        addAbbreviation("long", "J");
        addAbbreviation("short", ExifInterface.LATITUDE_SOUTH);
        addAbbreviation("byte", "B");
        addAbbreviation(TmpConstant.TYPE_VALUE_DOUBLE, "D");
        addAbbreviation("char", "C");
    }

    static /* synthetic */ Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    private static void addAbbreviation(String str, String str2) {
        abbreviationMap.put(str, str2);
        reverseAbbreviationMap.put(str2, str);
    }

    public static List convertClassNamesToClasses(List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(Class.forName((String) it.next()));
            } catch (Exception unused) {
                arrayList.add(null);
            }
        }
        return arrayList;
    }

    public static List convertClassesToClassNames(List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Class cls = (Class) it.next();
            if (cls == null) {
                arrayList.add(null);
            } else {
                arrayList.add(cls.getName());
            }
        }
        return arrayList;
    }

    public static List getAllInterfaces(Class cls) {
        if (cls == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        getAllInterfaces(cls, arrayList);
        return arrayList;
    }

    public static List getAllSuperclasses(Class cls) {
        if (cls == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Class superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            arrayList.add(superclass);
        }
        return arrayList;
    }

    private static String getCanonicalName(String str) {
        String strDeleteWhitespace = StringUtils.deleteWhitespace(str);
        if (strDeleteWhitespace == null) {
            return null;
        }
        int i2 = 0;
        while (strDeleteWhitespace.startsWith("[")) {
            i2++;
            strDeleteWhitespace = strDeleteWhitespace.substring(1);
        }
        if (i2 < 1) {
            return strDeleteWhitespace;
        }
        if (strDeleteWhitespace.startsWith("L")) {
            strDeleteWhitespace = strDeleteWhitespace.substring(1, strDeleteWhitespace.endsWith(i.f9802b) ? strDeleteWhitespace.length() - 1 : strDeleteWhitespace.length());
        } else if (strDeleteWhitespace.length() > 0) {
            strDeleteWhitespace = (String) reverseAbbreviationMap.get(strDeleteWhitespace.substring(0, 1));
        }
        StrBuilder strBuilder = new StrBuilder(strDeleteWhitespace);
        for (int i3 = 0; i3 < i2; i3++) {
            strBuilder.append("[]");
        }
        return strBuilder.toString();
    }

    public static Class getClass(ClassLoader classLoader, String str, boolean z2) throws ClassNotFoundException {
        try {
            Map map = abbreviationMap;
            if (!map.containsKey(str)) {
                return Class.forName(toCanonicalName(str), z2, classLoader);
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[");
            stringBuffer.append(map.get(str));
            return Class.forName(stringBuffer.toString(), z2, classLoader).getComponentType();
        } catch (ClassNotFoundException e2) {
            int iLastIndexOf = str.lastIndexOf(46);
            if (iLastIndexOf != -1) {
                try {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append(str.substring(0, iLastIndexOf));
                    stringBuffer2.append('$');
                    stringBuffer2.append(str.substring(iLastIndexOf + 1));
                    return getClass(classLoader, stringBuffer2.toString(), z2);
                } catch (ClassNotFoundException unused) {
                    throw e2;
                }
            }
            throw e2;
        }
    }

    public static String getPackageCanonicalName(Object obj, String str) {
        return obj == null ? str : getPackageCanonicalName(obj.getClass().getName());
    }

    public static String getPackageName(Object obj, String str) {
        return obj == null ? str : getPackageName(obj.getClass());
    }

    public static Method getPublicMethod(Class cls, String str, Class[] clsArr) throws NoSuchMethodException, SecurityException {
        Method method = cls.getMethod(str, clsArr);
        if (Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            return method;
        }
        ArrayList<Class> arrayList = new ArrayList();
        arrayList.addAll(getAllInterfaces(cls));
        arrayList.addAll(getAllSuperclasses(cls));
        for (Class cls2 : arrayList) {
            if (Modifier.isPublic(cls2.getModifiers())) {
                try {
                    Method method2 = cls2.getMethod(str, clsArr);
                    if (Modifier.isPublic(method2.getDeclaringClass().getModifiers())) {
                        return method2;
                    }
                } catch (NoSuchMethodException unused) {
                    continue;
                }
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Can't find a public method for ");
        stringBuffer.append(str);
        stringBuffer.append(" ");
        stringBuffer.append(ArrayUtils.toString(clsArr));
        throw new NoSuchMethodException(stringBuffer.toString());
    }

    public static String getShortCanonicalName(Object obj, String str) {
        return obj == null ? str : getShortCanonicalName(obj.getClass().getName());
    }

    public static String getShortClassName(Object obj, String str) {
        return obj == null ? str : getShortClassName(obj.getClass());
    }

    public static boolean isAssignable(Class[] clsArr, Class[] clsArr2) {
        return isAssignable(clsArr, clsArr2, false);
    }

    public static boolean isInnerClass(Class cls) {
        return cls != null && cls.getName().indexOf(36) >= 0;
    }

    public static Class primitiveToWrapper(Class cls) {
        return (cls == null || !cls.isPrimitive()) ? cls : (Class) primitiveWrapperMap.get(cls);
    }

    public static Class[] primitivesToWrappers(Class[] clsArr) {
        if (clsArr == null) {
            return null;
        }
        if (clsArr.length == 0) {
            return clsArr;
        }
        Class[] clsArr2 = new Class[clsArr.length];
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            clsArr2[i2] = primitiveToWrapper(clsArr[i2]);
        }
        return clsArr2;
    }

    private static String toCanonicalName(String str) {
        String strDeleteWhitespace = StringUtils.deleteWhitespace(str);
        if (strDeleteWhitespace == null) {
            throw new NullArgumentException(PushClientConstants.TAG_CLASS_NAME);
        }
        if (!strDeleteWhitespace.endsWith("[]")) {
            return strDeleteWhitespace;
        }
        StrBuilder strBuilder = new StrBuilder();
        while (strDeleteWhitespace.endsWith("[]")) {
            strDeleteWhitespace = strDeleteWhitespace.substring(0, strDeleteWhitespace.length() - 2);
            strBuilder.append("[");
        }
        String str2 = (String) abbreviationMap.get(strDeleteWhitespace);
        if (str2 != null) {
            strBuilder.append(str2);
        } else {
            strBuilder.append("L").append(strDeleteWhitespace).append(i.f9802b);
        }
        return strBuilder.toString();
    }

    public static Class[] toClass(Object[] objArr) {
        if (objArr == null) {
            return null;
        }
        if (objArr.length == 0) {
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Class[] clsArr = new Class[objArr.length];
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            clsArr[i2] = obj == null ? null : obj.getClass();
        }
        return clsArr;
    }

    public static Class wrapperToPrimitive(Class cls) {
        return (Class) wrapperPrimitiveMap.get(cls);
    }

    public static Class[] wrappersToPrimitives(Class[] clsArr) {
        if (clsArr == null) {
            return null;
        }
        if (clsArr.length == 0) {
            return clsArr;
        }
        Class[] clsArr2 = new Class[clsArr.length];
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            clsArr2[i2] = wrapperToPrimitive(clsArr[i2]);
        }
        return clsArr2;
    }

    public static String getPackageCanonicalName(Class cls) {
        return cls == null ? "" : getPackageCanonicalName(cls.getName());
    }

    public static String getPackageName(Class cls) {
        return cls == null ? "" : getPackageName(cls.getName());
    }

    public static String getShortCanonicalName(Class cls) {
        return cls == null ? "" : getShortCanonicalName(cls.getName());
    }

    public static String getShortClassName(Class cls) {
        return cls == null ? "" : getShortClassName(cls.getName());
    }

    public static boolean isAssignable(Class[] clsArr, Class[] clsArr2, boolean z2) {
        if (!ArrayUtils.isSameLength(clsArr, clsArr2)) {
            return false;
        }
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (clsArr2 == null) {
            clsArr2 = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            if (!isAssignable(clsArr[i2], clsArr2[i2], z2)) {
                return false;
            }
        }
        return true;
    }

    private static void getAllInterfaces(Class cls, List list) {
        while (cls != null) {
            Class<?>[] interfaces = cls.getInterfaces();
            for (int i2 = 0; i2 < interfaces.length; i2++) {
                if (!list.contains(interfaces[i2])) {
                    list.add(interfaces[i2]);
                    getAllInterfaces(interfaces[i2], list);
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public static String getPackageCanonicalName(String str) {
        return getPackageName(getCanonicalName(str));
    }

    public static String getPackageName(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        while (str.charAt(0) == '[') {
            str = str.substring(1);
        }
        if (str.charAt(0) == 'L' && str.charAt(str.length() - 1) == ';') {
            str = str.substring(1);
        }
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf == -1) {
            return "";
        }
        return str.substring(0, iLastIndexOf);
    }

    public static String getShortCanonicalName(String str) {
        return getShortClassName(getCanonicalName(str));
    }

    public static String getShortClassName(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        StrBuilder strBuilder = new StrBuilder();
        if (str.startsWith("[")) {
            while (str.charAt(0) == '[') {
                str = str.substring(1);
                strBuilder.append("[]");
            }
            if (str.charAt(0) == 'L' && str.charAt(str.length() - 1) == ';') {
                str = str.substring(1, str.length() - 1);
            }
        }
        Map map = reverseAbbreviationMap;
        if (map.containsKey(str)) {
            str = (String) map.get(str);
        }
        int iLastIndexOf = str.lastIndexOf(46);
        int iIndexOf = str.indexOf(36, iLastIndexOf != -1 ? iLastIndexOf + 1 : 0);
        String strSubstring = str.substring(iLastIndexOf + 1);
        if (iIndexOf != -1) {
            strSubstring = strSubstring.replace('$', '.');
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strSubstring);
        stringBuffer.append(strBuilder);
        return stringBuffer.toString();
    }

    public static boolean isAssignable(Class cls, Class cls2) {
        return isAssignable(cls, cls2, false);
    }

    public static Class getClass(ClassLoader classLoader, String str) throws ClassNotFoundException {
        return getClass(classLoader, str, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isAssignable(Class cls, Class cls2, boolean z2) {
        if (cls2 == 0) {
            return false;
        }
        if (cls == null) {
            return !cls2.isPrimitive();
        }
        if (z2) {
            if (cls.isPrimitive() && !cls2.isPrimitive() && (cls = primitiveToWrapper(cls)) == null) {
                return false;
            }
            if (cls2.isPrimitive() && !cls.isPrimitive() && (cls = wrapperToPrimitive(cls)) == null) {
                return false;
            }
        }
        if (cls.equals(cls2)) {
            return true;
        }
        if (cls.isPrimitive()) {
            if (!cls2.isPrimitive()) {
                return false;
            }
            Class cls3 = Integer.TYPE;
            if (cls3.equals(cls)) {
                return Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            Class cls4 = Long.TYPE;
            if (cls4.equals(cls)) {
                return Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            if (Boolean.TYPE.equals(cls)) {
                return false;
            }
            Class cls5 = Double.TYPE;
            if (cls5.equals(cls)) {
                return false;
            }
            Class cls6 = Float.TYPE;
            if (cls6.equals(cls)) {
                return cls5.equals(cls2);
            }
            if (Character.TYPE.equals(cls)) {
                return cls3.equals(cls2) || cls4.equals(cls2) || cls6.equals(cls2) || cls5.equals(cls2);
            }
            Class cls7 = Short.TYPE;
            if (cls7.equals(cls)) {
                return cls3.equals(cls2) || cls4.equals(cls2) || cls6.equals(cls2) || cls5.equals(cls2);
            }
            if (Byte.TYPE.equals(cls)) {
                return cls7.equals(cls2) || cls3.equals(cls2) || cls4.equals(cls2) || cls6.equals(cls2) || cls5.equals(cls2);
            }
            return false;
        }
        return cls2.isAssignableFrom(cls);
    }

    public static Class getClass(String str) throws ClassNotFoundException {
        return getClass(str, true);
    }

    public static Class getClass(String str, boolean z2) throws ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader == null) {
            Class clsA = f26612i;
            if (clsA == null) {
                clsA = a("org.apache.commons.lang.ClassUtils");
                f26612i = clsA;
            }
            contextClassLoader = clsA.getClassLoader();
        }
        return getClass(contextClassLoader, str, z2);
    }
}
