package androidx.media3.exoplayer.source.chunk;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.Format;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.StatsDataSource;
import androidx.media3.exoplayer.source.LoadEventInfo;
import androidx.media3.exoplayer.upstream.Loader;
import java.util.List;
import java.util.Map;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class Chunk implements Loader.Loadable {

    /* renamed from: a, reason: collision with root package name */
    protected final StatsDataSource f5439a;
    public final DataSpec dataSpec;
    public final long endTimeUs;
    public final long loadTaskId = LoadEventInfo.getNewId();
    public final long startTimeUs;
    public final Format trackFormat;

    @Nullable
    public final Object trackSelectionData;
    public final int trackSelectionReason;
    public final int type;

    public Chunk(DataSource dataSource, DataSpec dataSpec, int i2, Format format, int i3, @Nullable Object obj, long j2, long j3) {
        this.f5439a = new StatsDataSource(dataSource);
        this.dataSpec = (DataSpec) Assertions.checkNotNull(dataSpec);
        this.type = i2;
        this.trackFormat = format;
        this.trackSelectionReason = i3;
        this.trackSelectionData = obj;
        this.startTimeUs = j2;
        this.endTimeUs = j3;
    }

    public final long bytesLoaded() {
        return this.f5439a.getBytesRead();
    }

    public final long getDurationUs() {
        return this.endTimeUs - this.startTimeUs;
    }

    public final Map<String, List<String>> getResponseHeaders() {
        return this.f5439a.getLastResponseHeaders();
    }

    public final Uri getUri() {
        return this.f5439a.getLastOpenedUri();
    }
}
