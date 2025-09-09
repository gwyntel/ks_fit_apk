package androidx.media3.exoplayer.dash.manifest;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.common.math.BigIntegerMath;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public abstract class SegmentBase {

    /* renamed from: a, reason: collision with root package name */
    final RangedUri f5173a;

    /* renamed from: b, reason: collision with root package name */
    final long f5174b;

    /* renamed from: c, reason: collision with root package name */
    final long f5175c;

    public static abstract class MultiSegmentBase extends SegmentBase {

        /* renamed from: d, reason: collision with root package name */
        final long f5176d;

        /* renamed from: e, reason: collision with root package name */
        final long f5177e;

        /* renamed from: f, reason: collision with root package name */
        final List f5178f;

        /* renamed from: g, reason: collision with root package name */
        final long f5179g;
        private final long periodStartUnixTimeUs;
        private final long timeShiftBufferDepthUs;

        public MultiSegmentBase(@Nullable RangedUri rangedUri, long j2, long j3, long j4, long j5, @Nullable List<SegmentTimelineElement> list, long j6, long j7, long j8) {
            super(rangedUri, j2, j3);
            this.f5176d = j4;
            this.f5177e = j5;
            this.f5178f = list;
            this.f5179g = j6;
            this.timeShiftBufferDepthUs = j7;
            this.periodStartUnixTimeUs = j8;
        }

        public long getAvailableSegmentCount(long j2, long j3) {
            long segmentCount = getSegmentCount(j2);
            return segmentCount != -1 ? segmentCount : (int) (getSegmentNum((j3 - this.periodStartUnixTimeUs) + this.f5179g, j2) - getFirstAvailableSegmentNum(j2, j3));
        }

        public long getFirstAvailableSegmentNum(long j2, long j3) {
            if (getSegmentCount(j2) == -1) {
                long j4 = this.timeShiftBufferDepthUs;
                if (j4 != C.TIME_UNSET) {
                    return Math.max(getFirstSegmentNum(), getSegmentNum((j3 - this.periodStartUnixTimeUs) - j4, j2));
                }
            }
            return getFirstSegmentNum();
        }

        public long getFirstSegmentNum() {
            return this.f5176d;
        }

        public long getNextSegmentAvailableTimeUs(long j2, long j3) {
            if (this.f5178f != null) {
                return C.TIME_UNSET;
            }
            long firstAvailableSegmentNum = getFirstAvailableSegmentNum(j2, j3) + getAvailableSegmentCount(j2, j3);
            return (getSegmentTimeUs(firstAvailableSegmentNum) + getSegmentDurationUs(firstAvailableSegmentNum, j2)) - this.f5179g;
        }

        public abstract long getSegmentCount(long j2);

        public final long getSegmentDurationUs(long j2, long j3) {
            List list = this.f5178f;
            if (list != null) {
                return (((SegmentTimelineElement) list.get((int) (j2 - this.f5176d))).f5185b * 1000000) / this.f5174b;
            }
            long segmentCount = getSegmentCount(j3);
            return (segmentCount == -1 || j2 != (getFirstSegmentNum() + segmentCount) - 1) ? (this.f5177e * 1000000) / this.f5174b : j3 - getSegmentTimeUs(j2);
        }

        public long getSegmentNum(long j2, long j3) {
            long firstSegmentNum = getFirstSegmentNum();
            long segmentCount = getSegmentCount(j3);
            if (segmentCount == 0) {
                return firstSegmentNum;
            }
            if (this.f5178f == null) {
                long j4 = this.f5176d + (j2 / ((this.f5177e * 1000000) / this.f5174b));
                return j4 < firstSegmentNum ? firstSegmentNum : segmentCount == -1 ? j4 : Math.min(j4, (firstSegmentNum + segmentCount) - 1);
            }
            long j5 = (segmentCount + firstSegmentNum) - 1;
            long j6 = firstSegmentNum;
            while (j6 <= j5) {
                long j7 = ((j5 - j6) / 2) + j6;
                long segmentTimeUs = getSegmentTimeUs(j7);
                if (segmentTimeUs < j2) {
                    j6 = j7 + 1;
                } else {
                    if (segmentTimeUs <= j2) {
                        return j7;
                    }
                    j5 = j7 - 1;
                }
            }
            return j6 == firstSegmentNum ? j6 : j5;
        }

        public final long getSegmentTimeUs(long j2) {
            List list = this.f5178f;
            return Util.scaleLargeTimestamp(list != null ? ((SegmentTimelineElement) list.get((int) (j2 - this.f5176d))).f5184a - this.f5175c : (j2 - this.f5176d) * this.f5177e, 1000000L, this.f5174b);
        }

        public abstract RangedUri getSegmentUrl(Representation representation, long j2);

        public boolean isExplicit() {
            return this.f5178f != null;
        }
    }

    public static final class SegmentList extends MultiSegmentBase {

        /* renamed from: h, reason: collision with root package name */
        final List f5180h;

        public SegmentList(RangedUri rangedUri, long j2, long j3, long j4, long j5, @Nullable List<SegmentTimelineElement> list, long j6, @Nullable List<RangedUri> list2, long j7, long j8) {
            super(rangedUri, j2, j3, j4, j5, list, j6, j7, j8);
            this.f5180h = list2;
        }

        @Override // androidx.media3.exoplayer.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long j2) {
            return this.f5180h.size();
        }

        @Override // androidx.media3.exoplayer.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long j2) {
            return (RangedUri) this.f5180h.get((int) (j2 - this.f5176d));
        }

        @Override // androidx.media3.exoplayer.dash.manifest.SegmentBase.MultiSegmentBase
        public boolean isExplicit() {
            return true;
        }
    }

    public static final class SegmentTemplate extends MultiSegmentBase {

        /* renamed from: h, reason: collision with root package name */
        final UrlTemplate f5181h;

        /* renamed from: i, reason: collision with root package name */
        final UrlTemplate f5182i;

        /* renamed from: j, reason: collision with root package name */
        final long f5183j;

        public SegmentTemplate(RangedUri rangedUri, long j2, long j3, long j4, long j5, long j6, @Nullable List<SegmentTimelineElement> list, long j7, @Nullable UrlTemplate urlTemplate, @Nullable UrlTemplate urlTemplate2, long j8, long j9) {
            super(rangedUri, j2, j3, j4, j6, list, j7, j8, j9);
            this.f5181h = urlTemplate;
            this.f5182i = urlTemplate2;
            this.f5183j = j5;
        }

        @Override // androidx.media3.exoplayer.dash.manifest.SegmentBase
        @Nullable
        public RangedUri getInitialization(Representation representation) {
            UrlTemplate urlTemplate = this.f5181h;
            if (urlTemplate == null) {
                return super.getInitialization(representation);
            }
            Format format = representation.format;
            return new RangedUri(urlTemplate.buildUri(format.id, 0L, format.bitrate, 0L), 0L, -1L);
        }

        @Override // androidx.media3.exoplayer.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long j2) {
            if (this.f5178f != null) {
                return r0.size();
            }
            long j3 = this.f5183j;
            if (j3 != -1) {
                return (j3 - this.f5176d) + 1;
            }
            if (j2 != C.TIME_UNSET) {
                return BigIntegerMath.divide(BigInteger.valueOf(j2).multiply(BigInteger.valueOf(this.f5174b)), BigInteger.valueOf(this.f5177e).multiply(BigInteger.valueOf(1000000L)), RoundingMode.CEILING).longValue();
            }
            return -1L;
        }

        @Override // androidx.media3.exoplayer.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long j2) {
            List list = this.f5178f;
            long j3 = list != null ? ((SegmentTimelineElement) list.get((int) (j2 - this.f5176d))).f5184a : (j2 - this.f5176d) * this.f5177e;
            UrlTemplate urlTemplate = this.f5182i;
            Format format = representation.format;
            return new RangedUri(urlTemplate.buildUri(format.id, j2, format.bitrate, j3), 0L, -1L);
        }
    }

    public static final class SegmentTimelineElement {

        /* renamed from: a, reason: collision with root package name */
        final long f5184a;

        /* renamed from: b, reason: collision with root package name */
        final long f5185b;

        public SegmentTimelineElement(long j2, long j3) {
            this.f5184a = j2;
            this.f5185b = j3;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || SegmentTimelineElement.class != obj.getClass()) {
                return false;
            }
            SegmentTimelineElement segmentTimelineElement = (SegmentTimelineElement) obj;
            return this.f5184a == segmentTimelineElement.f5184a && this.f5185b == segmentTimelineElement.f5185b;
        }

        public int hashCode() {
            return (((int) this.f5184a) * 31) + ((int) this.f5185b);
        }
    }

    public SegmentBase(@Nullable RangedUri rangedUri, long j2, long j3) {
        this.f5173a = rangedUri;
        this.f5174b = j2;
        this.f5175c = j3;
    }

    @Nullable
    public RangedUri getInitialization(Representation representation) {
        return this.f5173a;
    }

    public long getPresentationTimeOffsetUs() {
        return Util.scaleLargeTimestamp(this.f5175c, 1000000L, this.f5174b);
    }

    public static class SingleSegmentBase extends SegmentBase {

        /* renamed from: d, reason: collision with root package name */
        final long f5186d;

        /* renamed from: e, reason: collision with root package name */
        final long f5187e;

        public SingleSegmentBase(@Nullable RangedUri rangedUri, long j2, long j3, long j4, long j5) {
            super(rangedUri, j2, j3);
            this.f5186d = j4;
            this.f5187e = j5;
        }

        @Nullable
        public RangedUri getIndex() {
            long j2 = this.f5187e;
            if (j2 <= 0) {
                return null;
            }
            return new RangedUri(null, this.f5186d, j2);
        }

        public SingleSegmentBase() {
            this(null, 1L, 0L, 0L, 0L);
        }
    }
}
