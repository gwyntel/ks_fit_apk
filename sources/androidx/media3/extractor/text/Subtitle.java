package androidx.media3.extractor.text;

import androidx.media3.common.text.Cue;
import androidx.media3.common.util.UnstableApi;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public interface Subtitle {
    List<Cue> getCues(long j2);

    long getEventTime(int i2);

    int getEventTimeCount();

    int getNextEventTimeIndex(long j2);
}
