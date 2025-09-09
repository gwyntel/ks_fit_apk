package org.apache.commons.lang;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.apache.commons.lang.exception.Nestable;
import org.apache.commons.lang.exception.NestableDelegate;

/* loaded from: classes5.dex */
public class NotImplementedException extends UnsupportedOperationException implements Nestable {
    private static final String DEFAULT_MESSAGE = "Code is not implemented";
    private static final long serialVersionUID = -6894122266938754088L;
    private Throwable cause;
    private NestableDelegate delegate;

    public NotImplementedException() {
        super(DEFAULT_MESSAGE);
        this.delegate = new NestableDelegate(this);
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

    public NotImplementedException(String str) {
        super(str == null ? DEFAULT_MESSAGE : str);
        this.delegate = new NestableDelegate(this);
    }

    @Override // java.lang.Throwable, org.apache.commons.lang.exception.Nestable
    public void printStackTrace(PrintWriter printWriter) {
        this.delegate.printStackTrace(printWriter);
    }

    public NotImplementedException(Throwable th) {
        super(DEFAULT_MESSAGE);
        this.delegate = new NestableDelegate(this);
        this.cause = th;
    }

    @Override // org.apache.commons.lang.exception.Nestable
    public String getMessage(int i2) {
        if (i2 == 0) {
            return super.getMessage();
        }
        return this.delegate.getMessage(i2);
    }

    public NotImplementedException(String str, Throwable th) {
        super(str == null ? DEFAULT_MESSAGE : str);
        this.delegate = new NestableDelegate(this);
        this.cause = th;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public NotImplementedException(Class cls) {
        String string;
        if (cls == null) {
            string = DEFAULT_MESSAGE;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Code is not implemented in ");
            stringBuffer.append(cls);
            string = stringBuffer.toString();
        }
        super(string);
        this.delegate = new NestableDelegate(this);
    }
}
