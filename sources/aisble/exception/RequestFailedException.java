package aisble.exception;

import aisble.Request;

/* loaded from: classes.dex */
public final class RequestFailedException extends Exception {
    public final Request request;
    public final int status;

    public RequestFailedException(Request request, int i2) {
        super("Request failed with status " + i2);
        this.request = request;
        this.status = i2;
    }

    public Request getRequest() {
        return this.request;
    }

    public int getStatus() {
        return this.status;
    }
}
