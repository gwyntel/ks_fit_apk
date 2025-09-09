package org.apache.commons.lang.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes5.dex */
public class NestableException extends Exception implements Nestable {
    private static final long serialVersionUID = 1;
    private Throwable cause;
    protected NestableDelegate delegate;

    public NestableException() {
        this.delegate = new NestableDelegate(this);
        this.cause = null;
    }

    @Override // java.lang.Throwable, org.apache.commons.lang.exception.Nestable
    public Throwable getCause() {
        return this.cause;
    }

    @Override // java.lang.Throwable, org.apache.commons.lang.exception.Nestable
    public String getMessage() {
        if (super.getMessage() != null) {
            return super.getMessage();
        }
        Throwable th = this.cause;
        if (th != null) {
            return th.toString();
        }
        return null;
    }

    @Override // org.apache.commons.lang.exception.Nestable
    public String[] getMessages() {
        return this.delegate.getMessages();
    }

    @Override // org.apache.commons.lang.exception.Nestable
    public Throwable getThrowable(int i2) {
        return this.delegate.getThrowable(i2);
    }

    @Override // org.apache.commons.lang.exception.Nestable
    public int getThrowableCount() {
        return this.delegate.getThrowableCount();
    }

    @Override // org.apache.commons.lang.exception.Nestable
    public Throwable[] getThrowables() {
        return this.delegate.getThrowables();
    }

    @Override // org.apache.commons.lang.exception.Nestable
    public int indexOfThrowable(Class cls) {
        return this.delegate.indexOfThrowable(cls, 0);
    }

    @Override // org.apache.commons.lang.exception.Nestable
    public final void printPartialStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        this.delegate.printStackTrace();
    }

    @Override // org.apache.commons.lang.exception.Nestable
    public int indexOfThrowable(Class cls, int i2) {
        return this.delegate.indexOfThrowable(cls, i2);
    }

    @Override // java.lang.Throwable, org.apache.commons.lang.exception.Nestable
    public void printStackTrace(PrintStream printStream) {
        this.delegate.printStackTrace(printStream);
    }

    @Override // java.lang.Throwable, org.apache.commons.lang.exception.Nestable
    public void printStackTrace(PrintWriter printWriter) {
        this.delegate.printStackTrace(printWriter);
    }

    public NestableException(String str) {
        super(str);
        this.delegate = new NestableDelegate(this);
        this.cause = null;
    }

    @Override // org.apache.commons.lang.exception.Nestable
    public String getMessage(int i2) {
        if (i2 == 0) {
            return super.getMessage();
        }
        return this.delegate.getMessage(i2);
    }

    public NestableException(Throwable th) {
        this.delegate = new NestableDelegate(this);
        this.cause = th;
    }

    public NestableException(String str, Throwable th) {
        super(str);
        this.delegate = new NestableDelegate(this);
        this.cause = th;
    }
}
