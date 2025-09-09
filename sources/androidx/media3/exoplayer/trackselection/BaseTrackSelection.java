package androidx.media3.exoplayer.trackselection;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.media3.common.Format;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.source.chunk.Chunk;
import androidx.media3.exoplayer.source.chunk.MediaChunk;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class BaseTrackSelection implements ExoTrackSelection {

    /* renamed from: a, reason: collision with root package name */
    protected final TrackGroup f5503a;

    /* renamed from: b, reason: collision with root package name */
    protected final int f5504b;

    /* renamed from: c, reason: collision with root package name */
    protected final int[] f5505c;
    private final long[] excludeUntilTimes;
    private final Format[] formats;
    private int hashCode;
    private final int type;

    public BaseTrackSelection(TrackGroup trackGroup, int... iArr) {
        this(trackGroup, iArr, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$0(Format format, Format format2) {
        return format2.bitrate - format.bitrate;
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public void disable() {
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public void enable() {
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaseTrackSelection baseTrackSelection = (BaseTrackSelection) obj;
        return this.f5503a.equals(baseTrackSelection.f5503a) && Arrays.equals(this.f5505c, baseTrackSelection.f5505c);
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public int evaluateQueueSize(long j2, List<? extends MediaChunk> list) {
        return list.size();
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public boolean excludeTrack(int i2, long j2) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        boolean zIsTrackExcluded = isTrackExcluded(i2, jElapsedRealtime);
        int i3 = 0;
        while (i3 < this.f5504b && !zIsTrackExcluded) {
            zIsTrackExcluded = (i3 == i2 || isTrackExcluded(i3, jElapsedRealtime)) ? false : true;
            i3++;
        }
        if (!zIsTrackExcluded) {
            return false;
        }
        long[] jArr = this.excludeUntilTimes;
        jArr[i2] = Math.max(jArr[i2], Util.addWithOverflowDefault(jElapsedRealtime, j2, Long.MAX_VALUE));
        return true;
    }

    @Override // androidx.media3.exoplayer.trackselection.TrackSelection
    public final Format getFormat(int i2) {
        return this.formats[i2];
    }

    @Override // androidx.media3.exoplayer.trackselection.TrackSelection
    public final int getIndexInTrackGroup(int i2) {
        return this.f5505c[i2];
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public /* synthetic */ long getLatestBitrateEstimate() {
        return y.a(this);
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public final Format getSelectedFormat() {
        return this.formats[getSelectedIndex()];
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public final int getSelectedIndexInTrackGroup() {
        return this.f5505c[getSelectedIndex()];
    }

    @Override // androidx.media3.exoplayer.trackselection.TrackSelection
    public final TrackGroup getTrackGroup() {
        return this.f5503a;
    }

    @Override // androidx.media3.exoplayer.trackselection.TrackSelection
    public final int getType() {
        return this.type;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = (System.identityHashCode(this.f5503a) * 31) + Arrays.hashCode(this.f5505c);
        }
        return this.hashCode;
    }

    @Override // androidx.media3.exoplayer.trackselection.TrackSelection
    public final int indexOf(Format format) {
        for (int i2 = 0; i2 < this.f5504b; i2++) {
            if (this.formats[i2] == format) {
                return i2;
            }
        }
        return -1;
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public boolean isTrackExcluded(int i2, long j2) {
        return this.excludeUntilTimes[i2] > j2;
    }

    @Override // androidx.media3.exoplayer.trackselection.TrackSelection
    public final int length() {
        return this.f5505c.length;
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public /* synthetic */ void onDiscontinuity() {
        y.b(this);
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public /* synthetic */ void onPlayWhenReadyChanged(boolean z2) {
        y.c(this, z2);
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public void onPlaybackSpeed(float f2) {
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public /* synthetic */ void onRebuffer() {
        y.d(this);
    }

    @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
    public /* synthetic */ boolean shouldCancelChunkLoad(long j2, Chunk chunk, List list) {
        return y.e(this, j2, chunk, list);
    }

    public BaseTrackSelection(TrackGroup trackGroup, int[] iArr, int i2) {
        int i3 = 0;
        Assertions.checkState(iArr.length > 0);
        this.type = i2;
        this.f5503a = (TrackGroup) Assertions.checkNotNull(trackGroup);
        int length = iArr.length;
        this.f5504b = length;
        this.formats = new Format[length];
        for (int i4 = 0; i4 < iArr.length; i4++) {
            this.formats[i4] = trackGroup.getFormat(iArr[i4]);
        }
        Arrays.sort(this.formats, new Comparator() { // from class: androidx.media3.exoplayer.trackselection.a
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return BaseTrackSelection.lambda$new$0((Format) obj, (Format) obj2);
            }
        });
        this.f5505c = new int[this.f5504b];
        while (true) {
            int i5 = this.f5504b;
            if (i3 >= i5) {
                this.excludeUntilTimes = new long[i5];
                return;
            } else {
                this.f5505c[i3] = trackGroup.indexOf(this.formats[i3]);
                i3++;
            }
        }
    }

    @Override // androidx.media3.exoplayer.trackselection.TrackSelection
    public final int indexOf(int i2) {
        for (int i3 = 0; i3 < this.f5504b; i3++) {
            if (this.f5505c[i3] == i2) {
                return i3;
            }
        }
        return -1;
    }
}
