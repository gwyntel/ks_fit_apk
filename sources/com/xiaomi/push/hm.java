package com.xiaomi.push;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public class hm extends Exception {

    /* renamed from: a, reason: collision with root package name */
    private hv f23889a;

    /* renamed from: a, reason: collision with other field name */
    private hw f541a;

    /* renamed from: a, reason: collision with other field name */
    private Throwable f542a;

    public hm() {
        this.f23889a = null;
        this.f541a = null;
        this.f542a = null;
    }

    public Throwable a() {
        return this.f542a;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        hv hvVar;
        hw hwVar;
        String message = super.getMessage();
        return (message != null || (hwVar = this.f541a) == null) ? (message != null || (hvVar = this.f23889a) == null) ? message : hvVar.toString() : hwVar.toString();
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String message = super.getMessage();
        if (message != null) {
            sb.append(message);
            sb.append(": ");
        }
        hw hwVar = this.f541a;
        if (hwVar != null) {
            sb.append(hwVar);
        }
        hv hvVar = this.f23889a;
        if (hvVar != null) {
            sb.append(hvVar);
        }
        if (this.f542a != null) {
            sb.append("\n  -- caused by: ");
            sb.append(this.f542a);
        }
        return sb.toString();
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        if (this.f542a != null) {
            printStream.println("Nested Exception: ");
            this.f542a.printStackTrace(printStream);
        }
    }

    public hm(String str) {
        super(str);
        this.f23889a = null;
        this.f541a = null;
        this.f542a = null;
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        if (this.f542a != null) {
            printWriter.println("Nested Exception: ");
            this.f542a.printStackTrace(printWriter);
        }
    }

    public hm(Throwable th) {
        this.f23889a = null;
        this.f541a = null;
        this.f542a = th;
    }

    public hm(hv hvVar) {
        this.f541a = null;
        this.f542a = null;
        this.f23889a = hvVar;
    }

    public hm(String str, Throwable th) {
        super(str);
        this.f23889a = null;
        this.f541a = null;
        this.f542a = th;
    }
}
