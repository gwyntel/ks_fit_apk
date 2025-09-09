package org.android.spdy;

/* loaded from: classes5.dex */
public class SpdyErrorException extends RuntimeException {
    private static final long serialVersionUID = 4422888579699220045L;
    private int error;

    public SpdyErrorException(int i2) {
        this.error = i2;
    }

    public int SpdyErrorGetCode() {
        return this.error;
    }

    public SpdyErrorException(String str, int i2) {
        super(str);
        this.error = i2;
    }

    public SpdyErrorException(String str, Throwable th, int i2) {
        super(str, th);
        this.error = i2;
    }

    public SpdyErrorException(Throwable th, int i2) {
        super(th);
        this.error = i2;
    }
}
