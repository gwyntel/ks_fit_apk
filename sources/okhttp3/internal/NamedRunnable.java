package okhttp3.internal;

/* loaded from: classes5.dex */
public abstract class NamedRunnable implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    protected final String f26252a;

    public NamedRunnable(String str, Object... objArr) {
        this.f26252a = Util.format(str, objArr);
    }

    protected abstract void execute();

    @Override // java.lang.Runnable
    public final void run() {
        String name = Thread.currentThread().getName();
        Thread.currentThread().setName(this.f26252a);
        try {
            execute();
        } finally {
            Thread.currentThread().setName(name);
        }
    }
}
