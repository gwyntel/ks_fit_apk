package androidx.media3.exoplayer.drm;

/* loaded from: classes.dex */
public abstract /* synthetic */ class i {
    public static boolean a(DrmSession drmSession) {
        return false;
    }

    public static void b(DrmSession drmSession, DrmSession drmSession2) {
        if (drmSession == drmSession2) {
            return;
        }
        if (drmSession2 != null) {
            drmSession2.acquire(null);
        }
        if (drmSession != null) {
            drmSession.release(null);
        }
    }
}
