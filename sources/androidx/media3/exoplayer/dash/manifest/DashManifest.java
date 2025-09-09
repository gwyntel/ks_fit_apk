package androidx.media3.exoplayer.dash.manifest;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.StreamKey;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.offline.FilterableManifest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public class DashManifest implements FilterableManifest<DashManifest> {
    public final long availabilityStartTimeMs;
    public final long durationMs;
    public final boolean dynamic;

    @Nullable
    public final Uri location;
    public final long minBufferTimeMs;
    public final long minUpdatePeriodMs;
    private final List<Period> periods;

    @Nullable
    public final ProgramInformation programInformation;
    public final long publishTimeMs;

    @Nullable
    public final ServiceDescriptionElement serviceDescription;
    public final long suggestedPresentationDelayMs;
    public final long timeShiftBufferDepthMs;

    @Nullable
    public final UtcTimingElement utcTiming;

    public DashManifest(long j2, long j3, long j4, boolean z2, long j5, long j6, long j7, long j8, @Nullable ProgramInformation programInformation, @Nullable UtcTimingElement utcTimingElement, @Nullable ServiceDescriptionElement serviceDescriptionElement, @Nullable Uri uri, List<Period> list) {
        this.availabilityStartTimeMs = j2;
        this.durationMs = j3;
        this.minBufferTimeMs = j4;
        this.dynamic = z2;
        this.minUpdatePeriodMs = j5;
        this.timeShiftBufferDepthMs = j6;
        this.suggestedPresentationDelayMs = j7;
        this.publishTimeMs = j8;
        this.programInformation = programInformation;
        this.utcTiming = utcTimingElement;
        this.location = uri;
        this.serviceDescription = serviceDescriptionElement;
        this.periods = list == null ? Collections.emptyList() : list;
    }

    private static ArrayList<AdaptationSet> copyAdaptationSets(List<AdaptationSet> list, LinkedList<StreamKey> linkedList) {
        StreamKey streamKeyPoll = linkedList.poll();
        int i2 = streamKeyPoll.periodIndex;
        ArrayList<AdaptationSet> arrayList = new ArrayList<>();
        do {
            int i3 = streamKeyPoll.groupIndex;
            AdaptationSet adaptationSet = list.get(i3);
            List<Representation> list2 = adaptationSet.representations;
            ArrayList arrayList2 = new ArrayList();
            do {
                arrayList2.add(list2.get(streamKeyPoll.streamIndex));
                streamKeyPoll = linkedList.poll();
                if (streamKeyPoll.periodIndex != i2) {
                    break;
                }
            } while (streamKeyPoll.groupIndex == i3);
            arrayList.add(new AdaptationSet(adaptationSet.id, adaptationSet.type, arrayList2, adaptationSet.accessibilityDescriptors, adaptationSet.essentialProperties, adaptationSet.supplementalProperties));
        } while (streamKeyPoll.periodIndex == i2);
        linkedList.addFirst(streamKeyPoll);
        return arrayList;
    }

    @Override // androidx.media3.exoplayer.offline.FilterableManifest
    public /* bridge */ /* synthetic */ DashManifest copy(List list) {
        return copy((List<StreamKey>) list);
    }

    public final Period getPeriod(int i2) {
        return this.periods.get(i2);
    }

    public final int getPeriodCount() {
        return this.periods.size();
    }

    public final long getPeriodDurationMs(int i2) {
        long j2;
        long j3;
        if (i2 == this.periods.size() - 1) {
            j2 = this.durationMs;
            if (j2 == C.TIME_UNSET) {
                return C.TIME_UNSET;
            }
            j3 = this.periods.get(i2).startMs;
        } else {
            j2 = this.periods.get(i2 + 1).startMs;
            j3 = this.periods.get(i2).startMs;
        }
        return j2 - j3;
    }

    public final long getPeriodDurationUs(int i2) {
        return Util.msToUs(getPeriodDurationMs(i2));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.media3.exoplayer.offline.FilterableManifest
    public final DashManifest copy(List<StreamKey> list) {
        long j2;
        LinkedList linkedList = new LinkedList(list);
        Collections.sort(linkedList);
        linkedList.add(new StreamKey(-1, -1, -1));
        ArrayList arrayList = new ArrayList();
        long j3 = 0;
        int i2 = 0;
        while (true) {
            int periodCount = getPeriodCount();
            j2 = C.TIME_UNSET;
            if (i2 >= periodCount) {
                break;
            }
            if (((StreamKey) linkedList.peek()).periodIndex != i2) {
                long periodDurationMs = getPeriodDurationMs(i2);
                if (periodDurationMs != C.TIME_UNSET) {
                    j3 += periodDurationMs;
                }
            } else {
                Period period = getPeriod(i2);
                arrayList.add(new Period(period.id, period.startMs - j3, copyAdaptationSets(period.adaptationSets, linkedList), period.eventStreams));
            }
            i2++;
        }
        long j4 = this.durationMs;
        if (j4 != C.TIME_UNSET) {
            j2 = j4 - j3;
        }
        return new DashManifest(this.availabilityStartTimeMs, j2, this.minBufferTimeMs, this.dynamic, this.minUpdatePeriodMs, this.timeShiftBufferDepthMs, this.suggestedPresentationDelayMs, this.publishTimeMs, this.programInformation, this.utcTiming, this.serviceDescription, this.location, arrayList);
    }
}
