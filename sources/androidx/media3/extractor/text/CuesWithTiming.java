package androidx.media3.extractor.text;

import androidx.media3.common.C;
import androidx.media3.common.text.Cue;
import androidx.media3.common.util.UnstableApi;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public class CuesWithTiming {
    public final ImmutableList<Cue> cues;
    public final long durationUs;
    public final long endTimeUs;
    public final long startTimeUs;

    public CuesWithTiming(List<Cue> list, long j2, long j3) {
        this.cues = ImmutableList.copyOf((Collection) list);
        this.startTimeUs = j2;
        this.durationUs = j3;
        long j4 = C.TIME_UNSET;
        if (j2 != C.TIME_UNSET && j3 != C.TIME_UNSET) {
            j4 = j2 + j3;
        }
        this.endTimeUs = j4;
    }
}
