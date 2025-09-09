package org.mozilla.javascript;

/* loaded from: classes5.dex */
public class ContinuationPending extends RuntimeException {
    private static final long serialVersionUID = 4956008116771118856L;
    private Object applicationState;
    private NativeContinuation continuationState;

    protected ContinuationPending(NativeContinuation nativeContinuation) {
        this.continuationState = nativeContinuation;
    }

    public Object getApplicationState() {
        return this.applicationState;
    }

    public Object getContinuation() {
        return this.continuationState;
    }

    NativeContinuation getContinuationState() {
        return this.continuationState;
    }

    public void setApplicationState(Object obj) {
        this.applicationState = obj;
    }

    public void setContinuation(NativeContinuation nativeContinuation) {
        this.continuationState = nativeContinuation;
    }
}
