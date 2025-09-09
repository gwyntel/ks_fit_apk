package b.f;

/* loaded from: classes2.dex */
public class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ e f7465a;

    public b(e eVar) {
        this.f7465a = eVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f7465a.mLowerTransportLayerCallbacks.onIncompleteTimerExpired();
        this.f7465a.mIncompleteTimerStarted = false;
    }
}
