package androidx.media3.extractor.text;

import androidx.media3.common.C;
import androidx.media3.common.text.Cue;
import androidx.media3.common.util.Consumer;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.text.SubtitleParser;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public class LegacySubtitleUtil {
    private LegacySubtitleUtil() {
    }

    private static int getStartIndex(Subtitle subtitle, SubtitleParser.OutputOptions outputOptions) {
        long j2 = outputOptions.startTimeUs;
        if (j2 == C.TIME_UNSET) {
            return 0;
        }
        int nextEventTimeIndex = subtitle.getNextEventTimeIndex(j2);
        return nextEventTimeIndex == -1 ? subtitle.getEventTimeCount() : (nextEventTimeIndex <= 0 || subtitle.getEventTime(nextEventTimeIndex + (-1)) != outputOptions.startTimeUs) ? nextEventTimeIndex : nextEventTimeIndex - 1;
    }

    private static void outputSubtitleEvent(Subtitle subtitle, int i2, Consumer<CuesWithTiming> consumer) {
        long eventTime = subtitle.getEventTime(i2);
        List<Cue> cues = subtitle.getCues(eventTime);
        if (cues.isEmpty()) {
            return;
        }
        if (i2 == subtitle.getEventTimeCount() - 1) {
            throw new IllegalStateException();
        }
        long eventTime2 = subtitle.getEventTime(i2 + 1) - subtitle.getEventTime(i2);
        if (eventTime2 > 0) {
            consumer.accept(new CuesWithTiming(cues, eventTime, eventTime2));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void toCuesWithTiming(androidx.media3.extractor.text.Subtitle r11, androidx.media3.extractor.text.SubtitleParser.OutputOptions r12, androidx.media3.common.util.Consumer<androidx.media3.extractor.text.CuesWithTiming> r13) {
        /*
            int r0 = r11.getEventTimeCount()
            if (r0 != 0) goto L7
            return
        L7:
            int r0 = getStartIndex(r11, r12)
            long r1 = r12.startTimeUs
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            r4 = 0
            if (r3 == 0) goto L3e
            java.util.List r6 = r11.getCues(r1)
            long r1 = r11.getEventTime(r0)
            boolean r3 = r6.isEmpty()
            if (r3 != 0) goto L3e
            int r3 = r11.getEventTimeCount()
            if (r0 >= r3) goto L3e
            long r7 = r12.startTimeUs
            int r3 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r3 >= 0) goto L3e
            androidx.media3.extractor.text.CuesWithTiming r3 = new androidx.media3.extractor.text.CuesWithTiming
            long r9 = r1 - r7
            r5 = r3
            r5.<init>(r6, r7, r9)
            r13.accept(r3)
            r1 = 1
            goto L3f
        L3e:
            r1 = r4
        L3f:
            r2 = r0
        L40:
            int r3 = r11.getEventTimeCount()
            if (r2 >= r3) goto L4c
            outputSubtitleEvent(r11, r2, r13)
            int r2 = r2 + 1
            goto L40
        L4c:
            boolean r2 = r12.outputAllCues
            if (r2 == 0) goto L79
            if (r1 == 0) goto L54
            int r0 = r0 + (-1)
        L54:
            if (r4 >= r0) goto L5c
            outputSubtitleEvent(r11, r4, r13)
            int r4 = r4 + 1
            goto L54
        L5c:
            if (r1 == 0) goto L79
            androidx.media3.extractor.text.CuesWithTiming r1 = new androidx.media3.extractor.text.CuesWithTiming
            long r2 = r12.startTimeUs
            java.util.List r6 = r11.getCues(r2)
            long r7 = r11.getEventTime(r0)
            long r2 = r12.startTimeUs
            long r11 = r11.getEventTime(r0)
            long r9 = r2 - r11
            r5 = r1
            r5.<init>(r6, r7, r9)
            r13.accept(r1)
        L79:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.extractor.text.LegacySubtitleUtil.toCuesWithTiming(androidx.media3.extractor.text.Subtitle, androidx.media3.extractor.text.SubtitleParser$OutputOptions, androidx.media3.common.util.Consumer):void");
    }
}
