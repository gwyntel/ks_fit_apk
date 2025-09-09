package androidx.media3.exoplayer.source;

import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.upstream.Allocator;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class WrappingMediaSource extends CompositeMediaSource<Void> {
    private static final Void CHILD_SOURCE_ID = null;

    /* renamed from: a, reason: collision with root package name */
    protected final MediaSource f5413a;

    protected WrappingMediaSource(MediaSource mediaSource) {
        this.f5413a = mediaSource;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: A, reason: merged with bridge method [inline-methods] */
    public final int r(Void r1, int i2) {
        return z(i2);
    }

    protected void B(Timeline timeline) {
        k(timeline);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: C, reason: merged with bridge method [inline-methods] */
    public final void lambda$prepareChildSource$0(Void r1, MediaSource mediaSource, Timeline timeline) {
        B(timeline);
    }

    protected final void D() {
        t(CHILD_SOURCE_ID, this.f5413a);
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public boolean canUpdateMediaItem(MediaItem mediaItem) {
        return this.f5413a.canUpdateMediaItem(mediaItem);
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        return this.f5413a.createPeriod(mediaPeriodId, allocator, j2);
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    @Nullable
    public Timeline getInitialTimeline() {
        return this.f5413a.getInitialTimeline();
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public MediaItem getMediaItem() {
        return this.f5413a.getMediaItem();
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public boolean isSingleWindow() {
        return this.f5413a.isSingleWindow();
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.BaseMediaSource
    protected final void j(TransferListener transferListener) {
        super.j(transferListener);
        prepareSourceInternal();
    }

    protected void prepareSourceInternal() {
        D();
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.f5413a.releasePeriod(mediaPeriod);
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public void updateMediaItem(MediaItem mediaItem) {
        this.f5413a.updateMediaItem(mediaItem);
    }

    protected MediaSource.MediaPeriodId v(MediaSource.MediaPeriodId mediaPeriodId) {
        return mediaPeriodId;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: w, reason: merged with bridge method [inline-methods] */
    public final MediaSource.MediaPeriodId p(Void r1, MediaSource.MediaPeriodId mediaPeriodId) {
        return v(mediaPeriodId);
    }

    protected long x(long j2, MediaSource.MediaPeriodId mediaPeriodId) {
        return j2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: y, reason: merged with bridge method [inline-methods] */
    public final long q(Void r1, long j2, MediaSource.MediaPeriodId mediaPeriodId) {
        return x(j2, mediaPeriodId);
    }

    protected int z(int i2) {
        return i2;
    }
}
