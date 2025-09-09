package androidx.media3.extractor.text.cea;

import androidx.media3.common.text.Cue;
import androidx.media3.common.util.Assertions;
import androidx.media3.extractor.text.Subtitle;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
final class CeaSubtitle implements Subtitle {
    private final List<Cue> cues;

    public CeaSubtitle(List<Cue> list) {
        this.cues = list;
    }

    @Override // androidx.media3.extractor.text.Subtitle
    public List<Cue> getCues(long j2) {
        return j2 >= 0 ? this.cues : Collections.emptyList();
    }

    @Override // androidx.media3.extractor.text.Subtitle
    public long getEventTime(int i2) {
        Assertions.checkArgument(i2 == 0);
        return 0L;
    }

    @Override // androidx.media3.extractor.text.Subtitle
    public int getEventTimeCount() {
        return 1;
    }

    @Override // androidx.media3.extractor.text.Subtitle
    public int getNextEventTimeIndex(long j2) {
        return j2 < 0 ? 0 : -1;
    }
}
