package androidx.media3.extractor;

import androidx.media3.common.C;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.SeekMap;
import java.io.IOException;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class BinarySearchSeeker {
    private static final long MAX_SKIP_BYTES = 262144;

    /* renamed from: a, reason: collision with root package name */
    protected final BinarySearchSeekMap f5603a;

    /* renamed from: b, reason: collision with root package name */
    protected final TimestampSeeker f5604b;

    /* renamed from: c, reason: collision with root package name */
    protected SeekOperationParams f5605c;
    private final int minimumSearchRange;

    public static class BinarySearchSeekMap implements SeekMap {
        private final long approxBytesPerFrame;
        private final long ceilingBytePosition;
        private final long ceilingTimePosition;
        private final long durationUs;
        private final long floorBytePosition;
        private final long floorTimePosition;
        private final SeekTimestampConverter seekTimestampConverter;

        public BinarySearchSeekMap(SeekTimestampConverter seekTimestampConverter, long j2, long j3, long j4, long j5, long j6, long j7) {
            this.seekTimestampConverter = seekTimestampConverter;
            this.durationUs = j2;
            this.floorTimePosition = j3;
            this.ceilingTimePosition = j4;
            this.floorBytePosition = j5;
            this.ceilingBytePosition = j6;
            this.approxBytesPerFrame = j7;
        }

        @Override // androidx.media3.extractor.SeekMap
        public long getDurationUs() {
            return this.durationUs;
        }

        @Override // androidx.media3.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long j2) {
            return new SeekMap.SeekPoints(new SeekPoint(j2, SeekOperationParams.h(this.seekTimestampConverter.timeUsToTargetTime(j2), this.floorTimePosition, this.ceilingTimePosition, this.floorBytePosition, this.ceilingBytePosition, this.approxBytesPerFrame)));
        }

        @Override // androidx.media3.extractor.SeekMap
        public boolean isSeekable() {
            return true;
        }

        public long timeUsToTargetTime(long j2) {
            return this.seekTimestampConverter.timeUsToTargetTime(j2);
        }
    }

    public static final class DefaultSeekTimestampConverter implements SeekTimestampConverter {
        @Override // androidx.media3.extractor.BinarySearchSeeker.SeekTimestampConverter
        public long timeUsToTargetTime(long j2) {
            return j2;
        }
    }

    protected static class SeekOperationParams {
        private final long approxBytesPerFrame;
        private long ceilingBytePosition;
        private long ceilingTimePosition;
        private long floorBytePosition;
        private long floorTimePosition;
        private long nextSearchBytePosition;
        private final long seekTimeUs;
        private final long targetTimePosition;

        protected SeekOperationParams(long j2, long j3, long j4, long j5, long j6, long j7, long j8) {
            this.seekTimeUs = j2;
            this.targetTimePosition = j3;
            this.floorTimePosition = j4;
            this.ceilingTimePosition = j5;
            this.floorBytePosition = j6;
            this.ceilingBytePosition = j7;
            this.approxBytesPerFrame = j8;
            this.nextSearchBytePosition = h(j3, j4, j5, j6, j7, j8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getCeilingBytePosition() {
            return this.ceilingBytePosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getFloorBytePosition() {
            return this.floorBytePosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getNextSearchBytePosition() {
            return this.nextSearchBytePosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getSeekTimeUs() {
            return this.seekTimeUs;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getTargetTimePosition() {
            return this.targetTimePosition;
        }

        protected static long h(long j2, long j3, long j4, long j5, long j6, long j7) {
            if (j5 + 1 >= j6 || j3 + 1 >= j4) {
                return j5;
            }
            long j8 = (long) ((j2 - j3) * ((j6 - j5) / (j4 - j3)));
            return Util.constrainValue(((j8 + j5) - j7) - (j8 / 20), j5, j6 - 1);
        }

        private void updateNextSearchBytePosition() {
            this.nextSearchBytePosition = h(this.targetTimePosition, this.floorTimePosition, this.ceilingTimePosition, this.floorBytePosition, this.ceilingBytePosition, this.approxBytesPerFrame);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateSeekCeiling(long j2, long j3) {
            this.ceilingTimePosition = j2;
            this.ceilingBytePosition = j3;
            updateNextSearchBytePosition();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateSeekFloor(long j2, long j3) {
            this.floorTimePosition = j2;
            this.floorBytePosition = j3;
            updateNextSearchBytePosition();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public interface SeekTimestampConverter {
        long timeUsToTargetTime(long j2);
    }

    public static final class TimestampSearchResult {
        public static final TimestampSearchResult NO_TIMESTAMP_IN_RANGE_RESULT = new TimestampSearchResult(-3, C.TIME_UNSET, -1);
        public static final int TYPE_NO_TIMESTAMP = -3;
        public static final int TYPE_POSITION_OVERESTIMATED = -1;
        public static final int TYPE_POSITION_UNDERESTIMATED = -2;
        public static final int TYPE_TARGET_TIMESTAMP_FOUND = 0;
        private final long bytePositionToUpdate;
        private final long timestampToUpdate;
        private final int type;

        private TimestampSearchResult(int i2, long j2, long j3) {
            this.type = i2;
            this.timestampToUpdate = j2;
            this.bytePositionToUpdate = j3;
        }

        public static TimestampSearchResult overestimatedResult(long j2, long j3) {
            return new TimestampSearchResult(-1, j2, j3);
        }

        public static TimestampSearchResult targetFoundResult(long j2) {
            return new TimestampSearchResult(0, C.TIME_UNSET, j2);
        }

        public static TimestampSearchResult underestimatedResult(long j2, long j3) {
            return new TimestampSearchResult(-2, j2, j3);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public interface TimestampSeeker {
        void onSeekFinished();

        TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j2) throws IOException;
    }

    protected BinarySearchSeeker(SeekTimestampConverter seekTimestampConverter, TimestampSeeker timestampSeeker, long j2, long j3, long j4, long j5, long j6, long j7, int i2) {
        this.f5604b = timestampSeeker;
        this.minimumSearchRange = i2;
        this.f5603a = new BinarySearchSeekMap(seekTimestampConverter, j2, j3, j4, j5, j6, j7);
    }

    protected SeekOperationParams a(long j2) {
        return new SeekOperationParams(j2, this.f5603a.timeUsToTargetTime(j2), this.f5603a.floorTimePosition, this.f5603a.ceilingTimePosition, this.f5603a.floorBytePosition, this.f5603a.ceilingBytePosition, this.f5603a.approxBytesPerFrame);
    }

    protected final void b(boolean z2, long j2) {
        this.f5605c = null;
        this.f5604b.onSeekFinished();
        c(z2, j2);
    }

    protected void c(boolean z2, long j2) {
    }

    protected final int d(ExtractorInput extractorInput, long j2, PositionHolder positionHolder) {
        if (j2 == extractorInput.getPosition()) {
            return 0;
        }
        positionHolder.position = j2;
        return 1;
    }

    protected final boolean e(ExtractorInput extractorInput, long j2) throws IOException {
        long position = j2 - extractorInput.getPosition();
        if (position < 0 || position > 262144) {
            return false;
        }
        extractorInput.skipFully((int) position);
        return true;
    }

    public final SeekMap getSeekMap() {
        return this.f5603a;
    }

    public int handlePendingSeek(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        while (true) {
            SeekOperationParams seekOperationParams = (SeekOperationParams) Assertions.checkStateNotNull(this.f5605c);
            long floorBytePosition = seekOperationParams.getFloorBytePosition();
            long ceilingBytePosition = seekOperationParams.getCeilingBytePosition();
            long nextSearchBytePosition = seekOperationParams.getNextSearchBytePosition();
            if (ceilingBytePosition - floorBytePosition <= this.minimumSearchRange) {
                b(false, floorBytePosition);
                return d(extractorInput, floorBytePosition, positionHolder);
            }
            if (!e(extractorInput, nextSearchBytePosition)) {
                return d(extractorInput, nextSearchBytePosition, positionHolder);
            }
            extractorInput.resetPeekPosition();
            TimestampSearchResult timestampSearchResultSearchForTimestamp = this.f5604b.searchForTimestamp(extractorInput, seekOperationParams.getTargetTimePosition());
            int i2 = timestampSearchResultSearchForTimestamp.type;
            if (i2 == -3) {
                b(false, nextSearchBytePosition);
                return d(extractorInput, nextSearchBytePosition, positionHolder);
            }
            if (i2 == -2) {
                seekOperationParams.updateSeekFloor(timestampSearchResultSearchForTimestamp.timestampToUpdate, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
            } else {
                if (i2 != -1) {
                    if (i2 != 0) {
                        throw new IllegalStateException("Invalid case");
                    }
                    e(extractorInput, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
                    b(true, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
                    return d(extractorInput, timestampSearchResultSearchForTimestamp.bytePositionToUpdate, positionHolder);
                }
                seekOperationParams.updateSeekCeiling(timestampSearchResultSearchForTimestamp.timestampToUpdate, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
            }
        }
    }

    public final boolean isSeeking() {
        return this.f5605c != null;
    }

    public final void setSeekTargetUs(long j2) {
        SeekOperationParams seekOperationParams = this.f5605c;
        if (seekOperationParams == null || seekOperationParams.getSeekTimeUs() != j2) {
            this.f5605c = a(j2);
        }
    }
}
