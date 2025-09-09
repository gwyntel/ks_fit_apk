package org.apache.commons.lang.exception;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class NestableDelegate implements Serializable {
    private static final transient String MUST_BE_THROWABLE = "The Nestable implementation passed to the NestableDelegate(Nestable) constructor must extend java.lang.Throwable";
    static /* synthetic */ Class class$org$apache$commons$lang$exception$Nestable = null;
    public static boolean matchSubclasses = true;
    private static final long serialVersionUID = 1;
    public static boolean topDown = true;
    public static boolean trimStackFrames = true;
    private Throwable nestable;

    /* JADX WARN: Multi-variable type inference failed */
    public NestableDelegate(Nestable nestable) {
        this.nestable = null;
        if (!(nestable instanceof Throwable)) {
            throw new IllegalArgumentException(MUST_BE_THROWABLE);
        }
        this.nestable = (Throwable) nestable;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getMessage(int i2) {
        Throwable throwable = getThrowable(i2);
        Class cls = class$org$apache$commons$lang$exception$Nestable;
        Class cls2 = cls;
        if (cls == null) {
            Class clsClass$ = class$("org.apache.commons.lang.exception.Nestable");
            class$org$apache$commons$lang$exception$Nestable = clsClass$;
            cls2 = clsClass$;
        }
        return cls2.isInstance(throwable) ? ((Nestable) throwable).getMessage(0) : throwable.getMessage();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String[] getMessages() {
        Throwable[] throwables = getThrowables();
        String[] strArr = new String[throwables.length];
        for (int i2 = 0; i2 < throwables.length; i2++) {
            Class cls = class$org$apache$commons$lang$exception$Nestable;
            Class cls2 = cls;
            if (cls == null) {
                Class clsClass$ = class$("org.apache.commons.lang.exception.Nestable");
                class$org$apache$commons$lang$exception$Nestable = clsClass$;
                cls2 = clsClass$;
            }
            strArr[i2] = cls2.isInstance(throwables[i2]) ? ((Nestable) throwables[i2]).getMessage(0) : throwables[i2].getMessage();
        }
        return strArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected String[] getStackFrames(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter((Writer) stringWriter, true);
        if (th instanceof Nestable) {
            ((Nestable) th).printPartialStackTrace(printWriter);
        } else {
            th.printStackTrace(printWriter);
        }
        return ExceptionUtils.c(stringWriter.getBuffer().toString());
    }

    public Throwable getThrowable(int i2) {
        return i2 == 0 ? this.nestable : getThrowables()[i2];
    }

    public int getThrowableCount() {
        return ExceptionUtils.getThrowableCount(this.nestable);
    }

    public Throwable[] getThrowables() {
        return ExceptionUtils.getThrowables(this.nestable);
    }

    public int indexOfThrowable(Class cls, int i2) {
        if (cls == null) {
            return -1;
        }
        if (i2 < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("The start index was out of bounds: ");
            stringBuffer.append(i2);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        Throwable[] throwables = ExceptionUtils.getThrowables(this.nestable);
        if (i2 >= throwables.length) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("The start index was out of bounds: ");
            stringBuffer2.append(i2);
            stringBuffer2.append(" >= ");
            stringBuffer2.append(throwables.length);
            throw new IndexOutOfBoundsException(stringBuffer2.toString());
        }
        if (matchSubclasses) {
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
        return -1;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    protected void trimStackFrames(List list) {
        for (int size = list.size() - 1; size > 0; size--) {
            String[] strArr = (String[]) list.get(size);
            String[] strArr2 = (String[]) list.get(size - 1);
            ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
            ExceptionUtils.removeCommonFrames(arrayList, new ArrayList(Arrays.asList(strArr2)));
            int length = strArr.length - arrayList.size();
            if (length > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("\t... ");
                stringBuffer.append(length);
                stringBuffer.append(" more");
                arrayList.add(stringBuffer.toString());
                list.set(size, arrayList.toArray(new String[arrayList.size()]));
            }
        }
    }

    public void printStackTrace(PrintStream printStream) {
        synchronized (printStream) {
            PrintWriter printWriter = new PrintWriter((OutputStream) printStream, false);
            printStackTrace(printWriter);
            printWriter.flush();
        }
    }

    public String getMessage(String str) {
        Throwable cause = ExceptionUtils.getCause(this.nestable);
        String message = cause == null ? null : cause.getMessage();
        if (cause == null || message == null) {
            return str;
        }
        if (str == null) {
            return message;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(": ");
        stringBuffer.append(message);
        return stringBuffer.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void printStackTrace(PrintWriter printWriter) {
        Throwable th = this.nestable;
        if (ExceptionUtils.isThrowableNested()) {
            if (th instanceof Nestable) {
                ((Nestable) th).printPartialStackTrace(printWriter);
                return;
            } else {
                th.printStackTrace(printWriter);
                return;
            }
        }
        ArrayList arrayList = new ArrayList();
        for (Throwable cause = th; cause != null; cause = ExceptionUtils.getCause(cause)) {
            arrayList.add(getStackFrames(cause));
        }
        String str = "Caused by: ";
        if (!topDown) {
            str = "Rethrown as: ";
            Collections.reverse(arrayList);
        }
        if (trimStackFrames) {
            trimStackFrames(arrayList);
        }
        synchronized (printWriter) {
            try {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    for (String str2 : (String[]) it.next()) {
                        printWriter.println(str2);
                    }
                    if (it.hasNext()) {
                        printWriter.print(str);
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
