package androidx.media3.exoplayer.source;

import androidx.media3.common.Timeline;
import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class ForwardingTimeline extends Timeline {

    /* renamed from: a, reason: collision with root package name */
    protected final Timeline f5401a;

    public ForwardingTimeline(Timeline timeline) {
        this.f5401a = timeline;
    }

    @Override // androidx.media3.common.Timeline
    public int getFirstWindowIndex(boolean z2) {
        return this.f5401a.getFirstWindowIndex(z2);
    }

    @Override // androidx.media3.common.Timeline
    public int getIndexOfPeriod(Object obj) {
        return this.f5401a.getIndexOfPeriod(obj);
    }

    @Override // androidx.media3.common.Timeline
    public int getLastWindowIndex(boolean z2) {
        return this.f5401a.getLastWindowIndex(z2);
    }

    @Override // androidx.media3.common.Timeline
    public int getNextWindowIndex(int i2, int i3, boolean z2) {
        return this.f5401a.getNextWindowIndex(i2, i3, z2);
    }

    @Override // androidx.media3.common.Timeline
    public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
        return this.f5401a.getPeriod(i2, period, z2);
    }

    @Override // androidx.media3.common.Timeline
    public int getPeriodCount() {
        return this.f5401a.getPeriodCount();
    }

    @Override // androidx.media3.common.Timeline
    public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
        return this.f5401a.getPreviousWindowIndex(i2, i3, z2);
    }

    @Override // androidx.media3.common.Timeline
    public Object getUidOfPeriod(int i2) {
        return this.f5401a.getUidOfPeriod(i2);
    }

    @Override // androidx.media3.common.Timeline
    public Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
        return this.f5401a.getWindow(i2, window, j2);
    }

    @Override // androidx.media3.common.Timeline
    public int getWindowCount() {
        return this.f5401a.getWindowCount();
    }
}
