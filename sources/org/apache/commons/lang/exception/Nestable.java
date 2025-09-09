package org.apache.commons.lang.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes5.dex */
public interface Nestable {
    Throwable getCause();

    String getMessage();

    String getMessage(int i2);

    String[] getMessages();

    Throwable getThrowable(int i2);

    int getThrowableCount();

    Throwable[] getThrowables();

    int indexOfThrowable(Class cls);

    int indexOfThrowable(Class cls, int i2);

    void printPartialStackTrace(PrintWriter printWriter);

    void printStackTrace(PrintStream printStream);

    void printStackTrace(PrintWriter printWriter);
}
