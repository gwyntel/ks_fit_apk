package androidx.media3.exoplayer.dash.manifest;

import androidx.annotation.Nullable;
import androidx.media3.common.util.UnstableApi;
import java.util.Collections;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public class Period {
    public final List<AdaptationSet> adaptationSets;

    @Nullable
    public final Descriptor assetIdentifier;
    public final List<EventStream> eventStreams;

    @Nullable
    public final String id;
    public final long startMs;

    public Period(@Nullable String str, long j2, List<AdaptationSet> list) {
        this(str, j2, list, Collections.emptyList(), null);
    }

    public int getAdaptationSetIndex(int i2) {
        int size = this.adaptationSets.size();
        for (int i3 = 0; i3 < size; i3++) {
            if (this.adaptationSets.get(i3).type == i2) {
                return i3;
            }
        }
        return -1;
    }

    public Period(@Nullable String str, long j2, List<AdaptationSet> list, List<EventStream> list2) {
        this(str, j2, list, list2, null);
    }

    public Period(@Nullable String str, long j2, List<AdaptationSet> list, List<EventStream> list2, @Nullable Descriptor descriptor) {
        this.id = str;
        this.startMs = j2;
        this.adaptationSets = Collections.unmodifiableList(list);
        this.eventStreams = Collections.unmodifiableList(list2);
        this.assetIdentifier = descriptor;
    }
}
