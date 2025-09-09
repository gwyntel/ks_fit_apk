package org.mozilla.javascript;

import java.lang.reflect.InvocationTargetException;

/* loaded from: classes5.dex */
public class WrappedException extends EvaluatorException {
    static final long serialVersionUID = -1551979216966520648L;
    private Throwable exception;

    public WrappedException(Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super("Wrapped " + th.toString());
        this.exception = th;
        Kit.initCause(this, th);
        int[] iArr = {0};
        String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
        int i2 = iArr[0];
        if (sourcePositionFromStack != null) {
            initSourceName(sourcePositionFromStack);
        }
        if (i2 != 0) {
            initLineNumber(i2);
        }
    }

    public Throwable getWrappedException() {
        return this.exception;
    }

    @Deprecated
    public Object unwrap() {
        return getWrappedException();
    }
}
