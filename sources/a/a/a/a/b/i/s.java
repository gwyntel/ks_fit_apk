package a.a.a.a.b.i;

import androidx.media3.exoplayer.ExoPlayer;

/* loaded from: classes.dex */
public class s implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ u f1498a;

    public s(u uVar) {
        this.f1498a = uVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (2 > this.f1498a.f1516q) {
            this.f1498a.d();
            this.f1498a.f1513n.postDelayed(this.f1498a.f1515p, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        } else {
            this.f1498a.f1503d.onProvisioningComplete(this.f1498a.f1507h, null);
            this.f1498a.f1516q = 0;
        }
    }
}
