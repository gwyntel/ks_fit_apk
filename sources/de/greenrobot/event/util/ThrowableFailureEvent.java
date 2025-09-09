package de.greenrobot.event.util;

/* loaded from: classes4.dex */
public class ThrowableFailureEvent {

    /* renamed from: a, reason: collision with root package name */
    protected final Throwable f24999a;

    /* renamed from: b, reason: collision with root package name */
    protected final boolean f25000b;

    public ThrowableFailureEvent(Throwable th) {
        this.f24999a = th;
        this.f25000b = false;
    }

    public Throwable getThrowable() {
        return this.f24999a;
    }

    public boolean isSuppressErrorUi() {
        return this.f25000b;
    }

    public ThrowableFailureEvent(Throwable th, boolean z2) {
        this.f24999a = th;
        this.f25000b = z2;
    }
}
