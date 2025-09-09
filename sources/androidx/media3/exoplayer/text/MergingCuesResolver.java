package androidx.media3.exoplayer.text;

import androidx.media3.common.C;
import androidx.media3.common.text.Cue;
import androidx.media3.common.util.Assertions;
import androidx.media3.extractor.text.CuesWithTiming;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
final class MergingCuesResolver implements CuesResolver {
    private static final Ordering<CuesWithTiming> CUES_DISPLAY_PRIORITY_COMPARATOR = Ordering.natural().onResultOf(new Function() { // from class: androidx.media3.exoplayer.text.a
        @Override // com.google.common.base.Function
        public final Object apply(Object obj) {
            return MergingCuesResolver.lambda$static$0((CuesWithTiming) obj);
        }
    }).compound(Ordering.natural().reverse().onResultOf(new Function() { // from class: androidx.media3.exoplayer.text.b
        @Override // com.google.common.base.Function
        public final Object apply(Object obj) {
            return MergingCuesResolver.lambda$static$1((CuesWithTiming) obj);
        }
    }));
    private final List<CuesWithTiming> cuesWithTimingList = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Long lambda$static$0(CuesWithTiming cuesWithTiming) {
        return Long.valueOf(cuesWithTiming.startTimeUs);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Long lambda$static$1(CuesWithTiming cuesWithTiming) {
        return Long.valueOf(cuesWithTiming.durationUs);
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public boolean addCues(CuesWithTiming cuesWithTiming, long j2) {
        Assertions.checkArgument(cuesWithTiming.startTimeUs != C.TIME_UNSET);
        Assertions.checkArgument(cuesWithTiming.durationUs != C.TIME_UNSET);
        boolean z2 = cuesWithTiming.startTimeUs <= j2 && j2 < cuesWithTiming.endTimeUs;
        for (int size = this.cuesWithTimingList.size() - 1; size >= 0; size--) {
            if (cuesWithTiming.startTimeUs >= this.cuesWithTimingList.get(size).startTimeUs) {
                this.cuesWithTimingList.add(size + 1, cuesWithTiming);
                return z2;
            }
        }
        this.cuesWithTimingList.add(0, cuesWithTiming);
        return z2;
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public void clear() {
        this.cuesWithTimingList.clear();
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public void discardCuesBeforeTimeUs(long j2) {
        int i2 = 0;
        while (i2 < this.cuesWithTimingList.size()) {
            long j3 = this.cuesWithTimingList.get(i2).startTimeUs;
            if (j2 > j3 && j2 > this.cuesWithTimingList.get(i2).endTimeUs) {
                this.cuesWithTimingList.remove(i2);
                i2--;
            } else if (j2 < j3) {
                return;
            }
            i2++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.media3.exoplayer.text.CuesResolver
    public ImmutableList<Cue> getCuesAtTimeUs(long j2) {
        if (!this.cuesWithTimingList.isEmpty()) {
            if (j2 >= this.cuesWithTimingList.get(0).startTimeUs) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < this.cuesWithTimingList.size(); i2++) {
                    CuesWithTiming cuesWithTiming = this.cuesWithTimingList.get(i2);
                    if (j2 >= cuesWithTiming.startTimeUs && j2 < cuesWithTiming.endTimeUs) {
                        arrayList.add(cuesWithTiming);
                    }
                    if (j2 < cuesWithTiming.startTimeUs) {
                        break;
                    }
                }
                ImmutableList immutableListSortedCopyOf = ImmutableList.sortedCopyOf(CUES_DISPLAY_PRIORITY_COMPARATOR, arrayList);
                ImmutableList.Builder builder = ImmutableList.builder();
                for (int i3 = 0; i3 < immutableListSortedCopyOf.size(); i3++) {
                    builder.addAll((Iterable) ((CuesWithTiming) immutableListSortedCopyOf.get(i3)).cues);
                }
                return builder.build();
            }
        }
        return ImmutableList.of();
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public long getNextCueChangeTimeUs(long j2) {
        int i2 = 0;
        long jMin = -9223372036854775807L;
        while (true) {
            if (i2 >= this.cuesWithTimingList.size()) {
                break;
            }
            long j3 = this.cuesWithTimingList.get(i2).startTimeUs;
            long j4 = this.cuesWithTimingList.get(i2).endTimeUs;
            if (j2 < j3) {
                jMin = jMin == C.TIME_UNSET ? j3 : Math.min(jMin, j3);
            } else {
                if (j2 < j4) {
                    jMin = jMin == C.TIME_UNSET ? j4 : Math.min(jMin, j4);
                }
                i2++;
            }
        }
        if (jMin != C.TIME_UNSET) {
            return jMin;
        }
        return Long.MIN_VALUE;
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public long getPreviousCueChangeTimeUs(long j2) {
        if (this.cuesWithTimingList.isEmpty()) {
            return C.TIME_UNSET;
        }
        if (j2 < this.cuesWithTimingList.get(0).startTimeUs) {
            return C.TIME_UNSET;
        }
        long jMax = this.cuesWithTimingList.get(0).startTimeUs;
        for (int i2 = 0; i2 < this.cuesWithTimingList.size(); i2++) {
            long j3 = this.cuesWithTimingList.get(i2).startTimeUs;
            long j4 = this.cuesWithTimingList.get(i2).endTimeUs;
            if (j4 > j2) {
                if (j3 > j2) {
                    break;
                }
                jMax = Math.max(jMax, j3);
            } else {
                jMax = Math.max(jMax, j4);
            }
        }
        return jMax;
    }
}
