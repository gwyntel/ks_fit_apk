package a.a.a.a.b.m;

import android.os.Process;

/* loaded from: classes.dex */
public class h extends Thread {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ i f1587a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public h(i iVar, Runnable runnable, String str) {
        super(runnable, str);
        this.f1587a = iVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws SecurityException, IllegalArgumentException {
        Process.setThreadPriority(this.f1587a.f1588a);
        super.run();
    }
}
