package org.apache.commons.lang.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

/* loaded from: classes5.dex */
public class ExceptionUtils {
    private static final Method THROWABLE_CAUSE_METHOD;
    private static final Method THROWABLE_INITCAUSE_METHOD;

    /* renamed from: a, reason: collision with root package name */
    static /* synthetic */ Class f26634a;
    private static final Object CAUSE_METHOD_NAMES_LOCK = new Object();
    private static String[] CAUSE_METHOD_NAMES = {"getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable"};

    static {
        Method method;
        Method method2 = null;
        try {
            Class clsA = f26634a;
            if (clsA == null) {
                clsA = a("java.lang.Throwable");
                f26634a = clsA;
            }
            method = clsA.getMethod("getCause", null);
        } catch (Exception unused) {
            method = null;
        }
        THROWABLE_CAUSE_METHOD = method;
        try {
            Class clsA2 = f26634a;
            if (clsA2 == null) {
                clsA2 = a("java.lang.Throwable");
                f26634a = clsA2;
            }
            Class<?> clsA3 = f26634a;
            if (clsA3 == null) {
                clsA3 = a("java.lang.Throwable");
                f26634a = clsA3;
            }
            method2 = clsA2.getMethod("initCause", clsA3);
        } catch (Exception unused2) {
        }
        THROWABLE_INITCAUSE_METHOD = method2;
    }

    static /* synthetic */ Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static void addCauseMethodName(String str) {
        if (!StringUtils.isNotEmpty(str) || isCauseMethodName(str)) {
            return;
        }
        ArrayList causeMethodNameList = getCauseMethodNameList();
        if (causeMethodNameList.add(str)) {
            synchronized (CAUSE_METHOD_NAMES_LOCK) {
                CAUSE_METHOD_NAMES = toArray(causeMethodNameList);
            }
        }
    }

    static List b(Throwable th) {
        StringTokenizer stringTokenizer = new StringTokenizer(getStackTrace(th), SystemUtils.LINE_SEPARATOR);
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            int iIndexOf = strNextToken.indexOf("at");
            if (iIndexOf != -1 && strNextToken.substring(0, iIndexOf).trim().length() == 0) {
                arrayList.add(strNextToken);
                z2 = true;
            } else if (z2) {
                break;
            }
        }
        return arrayList;
    }

    static String[] c(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, SystemUtils.LINE_SEPARATOR);
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return toArray(arrayList);
    }

    public static Throwable getCause(Throwable th) {
        Throwable cause;
        synchronized (CAUSE_METHOD_NAMES_LOCK) {
            cause = getCause(th, CAUSE_METHOD_NAMES);
        }
        return cause;
    }

    private static ArrayList getCauseMethodNameList() {
        ArrayList arrayList;
        synchronized (CAUSE_METHOD_NAMES_LOCK) {
            arrayList = new ArrayList(Arrays.asList(CAUSE_METHOD_NAMES));
        }
        return arrayList;
    }

    private static Throwable getCauseUsingFieldName(Throwable th, String str) throws NoSuchFieldException {
        Field field;
        try {
            field = th.getClass().getField(str);
        } catch (NoSuchFieldException | SecurityException unused) {
            field = null;
        }
        if (field != null) {
            Class clsA = f26634a;
            if (clsA == null) {
                clsA = a("java.lang.Throwable");
                f26634a = clsA;
            }
            if (clsA.isAssignableFrom(field.getType())) {
                try {
                    return (Throwable) field.get(th);
                } catch (IllegalAccessException | IllegalArgumentException unused2) {
                }
            }
        }
        return null;
    }

    private static Throwable getCauseUsingMethodName(Throwable th, String str) throws NoSuchMethodException, SecurityException {
        Method method;
        try {
            method = th.getClass().getMethod(str, null);
        } catch (NoSuchMethodException | SecurityException unused) {
            method = null;
        }
        if (method != null) {
            Class clsA = f26634a;
            if (clsA == null) {
                clsA = a("java.lang.Throwable");
                f26634a = clsA;
            }
            if (clsA.isAssignableFrom(method.getReturnType())) {
                try {
                    return (Throwable) method.invoke(th, ArrayUtils.EMPTY_OBJECT_ARRAY);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused2) {
                }
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Throwable getCauseUsingWellKnownTypes(Throwable th) {
        if (th instanceof Nestable) {
            return ((Nestable) th).getCause();
        }
        if (th instanceof SQLException) {
            return ((SQLException) th).getNextException();
        }
        if (th instanceof InvocationTargetException) {
            return ((InvocationTargetException) th).getTargetException();
        }
        return null;
    }

    public static String getFullStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter((Writer) stringWriter, true);
        Throwable[] throwables = getThrowables(th);
        for (int i2 = 0; i2 < throwables.length; i2++) {
            throwables[i2].printStackTrace(printWriter);
            if (isNestedThrowable(throwables[i2])) {
                break;
            }
        }
        return stringWriter.getBuffer().toString();
    }

    public static String getMessage(Throwable th) {
        if (th == null) {
            return "";
        }
        String shortClassName = ClassUtils.getShortClassName(th, null);
        String message = th.getMessage();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(shortClassName);
        stringBuffer.append(": ");
        stringBuffer.append(StringUtils.defaultString(message));
        return stringBuffer.toString();
    }

    public static Throwable getRootCause(Throwable th) {
        List throwableList = getThrowableList(th);
        if (throwableList.size() < 2) {
            return null;
        }
        return (Throwable) throwableList.get(throwableList.size() - 1);
    }

    public static String getRootCauseMessage(Throwable th) {
        Throwable rootCause = getRootCause(th);
        if (rootCause != null) {
            th = rootCause;
        }
        return getMessage(th);
    }

    public static String[] getRootCauseStackTrace(Throwable th) {
        List listB;
        if (th == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        Throwable[] throwables = getThrowables(th);
        int length = throwables.length;
        ArrayList arrayList = new ArrayList();
        int i2 = length - 1;
        List listB2 = b(throwables[i2]);
        while (true) {
            int i3 = length - 1;
            if (i3 < 0) {
                return (String[]) arrayList.toArray(new String[0]);
            }
            if (i3 != 0) {
                listB = b(throwables[length - 2]);
                removeCommonFrames(listB2, listB);
            } else {
                listB = listB2;
            }
            if (i3 == i2) {
                arrayList.add(throwables[i3].toString());
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(" [wrapped] ");
                stringBuffer.append(throwables[i3].toString());
                arrayList.add(stringBuffer.toString());
            }
            for (int i4 = 0; i4 < listB2.size(); i4++) {
                arrayList.add(listB2.get(i4));
            }
            listB2 = listB;
            length = i3;
        }
    }

    public static String[] getStackFrames(Throwable th) {
        return th == null ? ArrayUtils.EMPTY_STRING_ARRAY : c(getStackTrace(th));
    }

    public static String getStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter((Writer) stringWriter, true));
        return stringWriter.getBuffer().toString();
    }

    public static int getThrowableCount(Throwable th) {
        return getThrowableList(th).size();
    }

    public static List getThrowableList(Throwable th) {
        ArrayList arrayList = new ArrayList();
        while (th != null && !arrayList.contains(th)) {
            arrayList.add(th);
            th = getCause(th);
        }
        return arrayList;
    }

    public static Throwable[] getThrowables(Throwable th) {
        List throwableList = getThrowableList(th);
        return (Throwable[]) throwableList.toArray(new Throwable[throwableList.size()]);
    }

    private static int indexOf(Throwable th, Class cls, int i2, boolean z2) {
        if (th != null && cls != null) {
            if (i2 < 0) {
                i2 = 0;
            }
            Throwable[] throwables = getThrowables(th);
            if (i2 >= throwables.length) {
                return -1;
            }
            if (z2) {
                while (i2 < throwables.length) {
                    if (cls.isAssignableFrom(throwables[i2].getClass())) {
                        return i2;
                    }
                    i2++;
                }
            } else {
                while (i2 < throwables.length) {
                    if (cls.equals(throwables[i2].getClass())) {
                        return i2;
                    }
                    i2++;
                }
            }
        }
        return -1;
    }

    public static int indexOfThrowable(Throwable th, Class cls) {
        return indexOf(th, cls, 0, false);
    }

    public static int indexOfType(Throwable th, Class cls) {
        return indexOf(th, cls, 0, true);
    }

    public static boolean isCauseMethodName(String str) {
        boolean z2;
        synchronized (CAUSE_METHOD_NAMES_LOCK) {
            z2 = ArrayUtils.indexOf(CAUSE_METHOD_NAMES, str) >= 0;
        }
        return z2;
    }

    public static boolean isNestedThrowable(Throwable th) {
        if (th == null) {
            return false;
        }
        if ((th instanceof Nestable) || (th instanceof SQLException) || (th instanceof InvocationTargetException) || isThrowableNested()) {
            return true;
        }
        Class<?> cls = th.getClass();
        synchronized (CAUSE_METHOD_NAMES_LOCK) {
            try {
                int length = CAUSE_METHOD_NAMES.length;
                for (int i2 = 0; i2 < length; i2++) {
                    try {
                        Method method = cls.getMethod(CAUSE_METHOD_NAMES[i2], null);
                        if (method == null) {
                            continue;
                        } else {
                            Class clsA = f26634a;
                            if (clsA == null) {
                                clsA = a("java.lang.Throwable");
                                f26634a = clsA;
                            }
                            if (clsA.isAssignableFrom(method.getReturnType())) {
                                return true;
                            }
                        }
                    } catch (NoSuchMethodException | SecurityException unused) {
                    }
                }
                return cls.getField("detail") != null;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public static boolean isThrowableNested() {
        return THROWABLE_CAUSE_METHOD != null;
    }

    public static void printRootCauseStackTrace(Throwable th) {
        printRootCauseStackTrace(th, System.err);
    }

    public static void removeCauseMethodName(String str) {
        if (StringUtils.isNotEmpty(str)) {
            ArrayList causeMethodNameList = getCauseMethodNameList();
            if (causeMethodNameList.remove(str)) {
                synchronized (CAUSE_METHOD_NAMES_LOCK) {
                    CAUSE_METHOD_NAMES = toArray(causeMethodNameList);
                }
            }
        }
    }

    public static void removeCommonFrames(List list, List list2) {
        if (list == null || list2 == null) {
            throw new IllegalArgumentException("The List must not be null");
        }
        int size = list.size() - 1;
        for (int size2 = list2.size() - 1; size >= 0 && size2 >= 0; size2--) {
            if (((String) list.get(size)).equals((String) list2.get(size2))) {
                list.remove(size);
            }
            size--;
        }
    }

    public static boolean setCause(Throwable th, Throwable th2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2;
        if (th == null) {
            throw new NullArgumentException("target");
        }
        Object[] objArr = {th2};
        Method method = THROWABLE_INITCAUSE_METHOD;
        if (method != null) {
            try {
                method.invoke(th, objArr);
                z2 = true;
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        } else {
            z2 = false;
        }
        try {
            Class<?> cls = th.getClass();
            Class<?> clsA = f26634a;
            if (clsA == null) {
                clsA = a("java.lang.Throwable");
                f26634a = clsA;
            }
            cls.getMethod("setCause", clsA).invoke(th, objArr);
            return true;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused2) {
            return z2;
        }
    }

    private static String[] toArray(List list) {
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static int indexOfThrowable(Throwable th, Class cls, int i2) {
        return indexOf(th, cls, i2, false);
    }

    public static int indexOfType(Throwable th, Class cls, int i2) {
        return indexOf(th, cls, i2, true);
    }

    public static void printRootCauseStackTrace(Throwable th, PrintStream printStream) {
        if (th == null) {
            return;
        }
        if (printStream == null) {
            throw new IllegalArgumentException("The PrintStream must not be null");
        }
        for (String str : getRootCauseStackTrace(th)) {
            printStream.println(str);
        }
        printStream.flush();
    }

    public static Throwable getCause(Throwable th, String[] strArr) {
        String str;
        if (th == null) {
            return null;
        }
        Throwable causeUsingWellKnownTypes = getCauseUsingWellKnownTypes(th);
        if (causeUsingWellKnownTypes != null) {
            return causeUsingWellKnownTypes;
        }
        if (strArr == null) {
            synchronized (CAUSE_METHOD_NAMES_LOCK) {
                strArr = CAUSE_METHOD_NAMES;
            }
        }
        for (int i2 = 0; i2 < strArr.length && ((str = strArr[i2]) == null || (causeUsingWellKnownTypes = getCauseUsingMethodName(th, str)) == null); i2++) {
        }
        return causeUsingWellKnownTypes == null ? getCauseUsingFieldName(th, "detail") : causeUsingWellKnownTypes;
    }

    public static void printRootCauseStackTrace(Throwable th, PrintWriter printWriter) {
        if (th == null) {
            return;
        }
        if (printWriter != null) {
            for (String str : getRootCauseStackTrace(th)) {
                printWriter.println(str);
            }
            printWriter.flush();
            return;
        }
        throw new IllegalArgumentException("The PrintWriter must not be null");
    }
}
