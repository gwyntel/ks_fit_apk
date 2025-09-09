package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.CheckForNull;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Throwables {

    @J2ktIncompatible
    @GwtIncompatible
    private static final String JAVA_LANG_ACCESS_CLASSNAME = "sun.misc.JavaLangAccess";

    @J2ktIncompatible
    @CheckForNull
    @GwtIncompatible
    private static final Method getStackTraceDepthMethod;

    @J2ktIncompatible
    @CheckForNull
    @GwtIncompatible
    private static final Method getStackTraceElementMethod;

    @J2ktIncompatible
    @CheckForNull
    @GwtIncompatible
    private static final Object jla;

    static {
        Object jla2 = getJLA();
        jla = jla2;
        getStackTraceElementMethod = jla2 == null ? null : getGetMethod();
        getStackTraceDepthMethod = jla2 != null ? getSizeMethod(jla2) : null;
    }

    private Throwables() {
    }

    public static List<Throwable> getCausalChain(Throwable th) {
        Preconditions.checkNotNull(th);
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(th);
        boolean z2 = false;
        Throwable cause = th;
        while (true) {
            th = th.getCause();
            if (th == null) {
                return Collections.unmodifiableList(arrayList);
            }
            arrayList.add(th);
            if (th == cause) {
                throw new IllegalArgumentException("Loop in causal chain detected.", th);
            }
            if (z2) {
                cause = cause.getCause();
            }
            z2 = !z2;
        }
    }

    @CheckForNull
    @GwtIncompatible
    public static <X extends Throwable> X getCauseAs(Throwable th, Class<X> cls) {
        try {
            return cls.cast(th.getCause());
        } catch (ClassCastException e2) {
            e2.initCause(th);
            throw e2;
        }
    }

    @J2ktIncompatible
    @CheckForNull
    @GwtIncompatible
    private static Method getGetMethod() {
        return getJlaMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
    }

    @J2ktIncompatible
    @CheckForNull
    @GwtIncompatible
    private static Object getJLA() {
        try {
            return Class.forName("sun.misc.SharedSecrets", false, null).getMethod("getJavaLangAccess", null).invoke(null, null);
        } catch (ThreadDeath e2) {
            throw e2;
        } catch (Throwable unused) {
            return null;
        }
    }

    @CheckForNull
    @GwtIncompatible
    @J2ktIncompatible
    private static Method getJlaMethod(String str, Class<?>... clsArr) throws ThreadDeath {
        try {
            return Class.forName(JAVA_LANG_ACCESS_CLASSNAME, false, null).getMethod(str, clsArr);
        } catch (ThreadDeath e2) {
            throw e2;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Throwable getRootCause(Throwable th) {
        boolean z2 = false;
        Throwable cause = th;
        while (true) {
            Throwable cause2 = th.getCause();
            if (cause2 == null) {
                return th;
            }
            if (cause2 == cause) {
                throw new IllegalArgumentException("Loop in causal chain detected.", cause2);
            }
            if (z2) {
                cause = cause.getCause();
            }
            z2 = !z2;
            th = cause2;
        }
    }

    @J2ktIncompatible
    @CheckForNull
    @GwtIncompatible
    private static Method getSizeMethod(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Method jlaMethod = getJlaMethod("getStackTraceDepth", Throwable.class);
            if (jlaMethod == null) {
                return null;
            }
            jlaMethod.invoke(obj, new Throwable());
            return jlaMethod;
        } catch (IllegalAccessException | UnsupportedOperationException | InvocationTargetException unused) {
            return null;
        }
    }

    @GwtIncompatible
    public static String getStackTraceAsString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @J2ktIncompatible
    @GwtIncompatible
    public static Object invokeAccessibleNonThrowingMethod(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw propagate(e3.getCause());
        }
    }

    @J2ktIncompatible
    @GwtIncompatible
    private static List<StackTraceElement> jlaStackTrace(final Throwable th) {
        Preconditions.checkNotNull(th);
        return new AbstractList<StackTraceElement>() { // from class: com.google.common.base.Throwables.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                Method method = Throwables.getStackTraceDepthMethod;
                java.util.Objects.requireNonNull(method);
                Object obj = Throwables.jla;
                java.util.Objects.requireNonNull(obj);
                return ((Integer) Throwables.invokeAccessibleNonThrowingMethod(method, obj, th)).intValue();
            }

            @Override // java.util.AbstractList, java.util.List
            public StackTraceElement get(int i2) {
                Method method = Throwables.getStackTraceElementMethod;
                java.util.Objects.requireNonNull(method);
                Object obj = Throwables.jla;
                java.util.Objects.requireNonNull(obj);
                return (StackTraceElement) Throwables.invokeAccessibleNonThrowingMethod(method, obj, th, Integer.valueOf(i2));
            }
        };
    }

    @J2ktIncompatible
    @GwtIncompatible
    @Deprecated
    public static List<StackTraceElement> lazyStackTrace(Throwable th) {
        return lazyStackTraceIsLazy() ? jlaStackTrace(th) : Collections.unmodifiableList(Arrays.asList(th.getStackTrace()));
    }

    @J2ktIncompatible
    @GwtIncompatible
    @Deprecated
    public static boolean lazyStackTraceIsLazy() {
        return (getStackTraceElementMethod == null || getStackTraceDepthMethod == null) ? false : true;
    }

    @GwtIncompatible
    @Deprecated
    @CanIgnoreReturnValue
    @J2ktIncompatible
    public static RuntimeException propagate(Throwable th) {
        throwIfUnchecked(th);
        throw new RuntimeException(th);
    }

    @GwtIncompatible
    @Deprecated
    @J2ktIncompatible
    public static <X extends Throwable> void propagateIfInstanceOf(@CheckForNull Throwable th, Class<X> cls) throws Throwable {
        if (th != null) {
            throwIfInstanceOf(th, cls);
        }
    }

    @J2ktIncompatible
    @GwtIncompatible
    @Deprecated
    public static void propagateIfPossible(@CheckForNull Throwable th) {
        if (th != null) {
            throwIfUnchecked(th);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: X extends java.lang.Throwable */
    @GwtIncompatible
    public static <X extends Throwable> void throwIfInstanceOf(Throwable th, Class<X> cls) throws Throwable {
        Preconditions.checkNotNull(th);
        if (cls.isInstance(th)) {
            throw cls.cast(th);
        }
    }

    public static void throwIfUnchecked(Throwable th) {
        Preconditions.checkNotNull(th);
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        }
        if (th instanceof Error) {
            throw ((Error) th);
        }
    }

    @GwtIncompatible
    @Deprecated
    @J2ktIncompatible
    public static <X extends Throwable> void propagateIfPossible(@CheckForNull Throwable th, Class<X> cls) throws Throwable {
        propagateIfInstanceOf(th, cls);
        propagateIfPossible(th);
    }

    @GwtIncompatible
    @Deprecated
    @J2ktIncompatible
    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@CheckForNull Throwable th, Class<X1> cls, Class<X2> cls2) throws Throwable {
        Preconditions.checkNotNull(cls2);
        propagateIfInstanceOf(th, cls);
        propagateIfPossible(th, cls2);
    }
}
