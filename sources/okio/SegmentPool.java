package okio;

import android.support.v4.media.session.PlaybackStateCompat;

/* loaded from: classes5.dex */
final class SegmentPool {

    /* renamed from: a, reason: collision with root package name */
    static Segment f26500a;

    /* renamed from: b, reason: collision with root package name */
    static long f26501b;

    private SegmentPool() {
    }

    static void a(Segment segment) {
        if (segment.f26498f != null || segment.f26499g != null) {
            throw new IllegalArgumentException();
        }
        if (segment.f26496d) {
            return;
        }
        synchronized (SegmentPool.class) {
            try {
                long j2 = f26501b;
                if (j2 + PlaybackStateCompat.ACTION_PLAY_FROM_URI > PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                    return;
                }
                f26501b = j2 + PlaybackStateCompat.ACTION_PLAY_FROM_URI;
                segment.f26498f = f26500a;
                segment.f26495c = 0;
                segment.f26494b = 0;
                f26500a = segment;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static Segment b() {
        synchronized (SegmentPool.class) {
            try {
                Segment segment = f26500a;
                if (segment == null) {
                    return new Segment();
                }
                f26500a = segment.f26498f;
                segment.f26498f = null;
                f26501b -= PlaybackStateCompat.ACTION_PLAY_FROM_URI;
                return segment;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
