package androidx.media3.extractor.ogg;

import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import androidx.media3.extractor.ExtractorInput;
import androidx.media3.extractor.ExtractorUtil;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.SeekPoint;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes2.dex */
final class DefaultOggSeeker implements OggSeeker {
    private static final int DEFAULT_OFFSET = 30000;
    private static final int MATCH_BYTE_RANGE = 100000;
    private static final int MATCH_RANGE = 72000;
    private static final int STATE_IDLE = 4;
    private static final int STATE_READ_LAST_PAGE = 1;
    private static final int STATE_SEEK = 2;
    private static final int STATE_SEEK_TO_END = 0;
    private static final int STATE_SKIP = 3;
    private long end;
    private long endGranule;
    private final OggPageHeader pageHeader;
    private final long payloadEndPosition;
    private final long payloadStartPosition;
    private long positionBeforeSeekToEnd;
    private long start;
    private long startGranule;
    private int state;
    private final StreamReader streamReader;
    private long targetGranule;
    private long totalGranules;

    private final class OggSeekMap implements SeekMap {
        private OggSeekMap() {
        }

        @Override // androidx.media3.extractor.SeekMap
        public long getDurationUs() {
            return DefaultOggSeeker.this.streamReader.a(DefaultOggSeeker.this.totalGranules);
        }

        @Override // androidx.media3.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long j2) {
            return new SeekMap.SeekPoints(new SeekPoint(j2, Util.constrainValue((DefaultOggSeeker.this.payloadStartPosition + BigInteger.valueOf(DefaultOggSeeker.this.streamReader.b(j2)).multiply(BigInteger.valueOf(DefaultOggSeeker.this.payloadEndPosition - DefaultOggSeeker.this.payloadStartPosition)).divide(BigInteger.valueOf(DefaultOggSeeker.this.totalGranules)).longValue()) - 30000, DefaultOggSeeker.this.payloadStartPosition, DefaultOggSeeker.this.payloadEndPosition - 1)));
        }

        @Override // androidx.media3.extractor.SeekMap
        public boolean isSeekable() {
            return true;
        }
    }

    public DefaultOggSeeker(StreamReader streamReader, long j2, long j3, long j4, long j5, boolean z2) {
        Assertions.checkArgument(j2 >= 0 && j3 > j2);
        this.streamReader = streamReader;
        this.payloadStartPosition = j2;
        this.payloadEndPosition = j3;
        if (j4 == j3 - j2 || z2) {
            this.totalGranules = j5;
            this.state = 4;
        } else {
            this.state = 0;
        }
        this.pageHeader = new OggPageHeader();
    }

    private long getNextSeekPosition(ExtractorInput extractorInput) throws IOException {
        if (this.start == this.end) {
            return -1L;
        }
        long position = extractorInput.getPosition();
        if (!this.pageHeader.skipToNextPage(extractorInput, this.end)) {
            long j2 = this.start;
            if (j2 != position) {
                return j2;
            }
            throw new IOException("No ogg page can be found.");
        }
        this.pageHeader.populate(extractorInput, false);
        extractorInput.resetPeekPosition();
        long j3 = this.targetGranule;
        OggPageHeader oggPageHeader = this.pageHeader;
        long j4 = oggPageHeader.granulePosition;
        long j5 = j3 - j4;
        int i2 = oggPageHeader.headerSize + oggPageHeader.bodySize;
        if (0 <= j5 && j5 < 72000) {
            return -1L;
        }
        if (j5 < 0) {
            this.end = position;
            this.endGranule = j4;
        } else {
            this.start = extractorInput.getPosition() + i2;
            this.startGranule = this.pageHeader.granulePosition;
        }
        long j6 = this.end;
        long j7 = this.start;
        if (j6 - j7 < SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US) {
            this.end = j7;
            return j7;
        }
        long position2 = extractorInput.getPosition() - (i2 * (j5 <= 0 ? 2L : 1L));
        long j8 = this.end;
        long j9 = this.start;
        return Util.constrainValue(position2 + ((j5 * (j8 - j9)) / (this.endGranule - this.startGranule)), j9, j8 - 1);
    }

    private void skipToPageOfTargetGranule(ExtractorInput extractorInput) throws IOException {
        while (true) {
            this.pageHeader.skipToNextPage(extractorInput);
            this.pageHeader.populate(extractorInput, false);
            OggPageHeader oggPageHeader = this.pageHeader;
            if (oggPageHeader.granulePosition > this.targetGranule) {
                extractorInput.resetPeekPosition();
                return;
            } else {
                extractorInput.skipFully(oggPageHeader.headerSize + oggPageHeader.bodySize);
                this.start = extractorInput.getPosition();
                this.startGranule = this.pageHeader.granulePosition;
            }
        }
    }

    long e(ExtractorInput extractorInput) throws IOException {
        this.pageHeader.reset();
        if (!this.pageHeader.skipToNextPage(extractorInput)) {
            throw new EOFException();
        }
        this.pageHeader.populate(extractorInput, false);
        OggPageHeader oggPageHeader = this.pageHeader;
        extractorInput.skipFully(oggPageHeader.headerSize + oggPageHeader.bodySize);
        long j2 = this.pageHeader.granulePosition;
        while (true) {
            OggPageHeader oggPageHeader2 = this.pageHeader;
            if ((oggPageHeader2.type & 4) == 4 || !oggPageHeader2.skipToNextPage(extractorInput) || extractorInput.getPosition() >= this.payloadEndPosition || !this.pageHeader.populate(extractorInput, true)) {
                break;
            }
            OggPageHeader oggPageHeader3 = this.pageHeader;
            if (!ExtractorUtil.skipFullyQuietly(extractorInput, oggPageHeader3.headerSize + oggPageHeader3.bodySize)) {
                break;
            }
            j2 = this.pageHeader.granulePosition;
        }
        return j2;
    }

    @Override // androidx.media3.extractor.ogg.OggSeeker
    public long read(ExtractorInput extractorInput) throws IOException {
        int i2 = this.state;
        if (i2 == 0) {
            long position = extractorInput.getPosition();
            this.positionBeforeSeekToEnd = position;
            this.state = 1;
            long j2 = this.payloadEndPosition - 65307;
            if (j2 > position) {
                return j2;
            }
        } else if (i2 != 1) {
            if (i2 == 2) {
                long nextSeekPosition = getNextSeekPosition(extractorInput);
                if (nextSeekPosition != -1) {
                    return nextSeekPosition;
                }
                this.state = 3;
            } else if (i2 != 3) {
                if (i2 == 4) {
                    return -1L;
                }
                throw new IllegalStateException();
            }
            skipToPageOfTargetGranule(extractorInput);
            this.state = 4;
            return -(this.startGranule + 2);
        }
        this.totalGranules = e(extractorInput);
        this.state = 4;
        return this.positionBeforeSeekToEnd;
    }

    @Override // androidx.media3.extractor.ogg.OggSeeker
    public void startSeek(long j2) {
        this.targetGranule = Util.constrainValue(j2, 0L, this.totalGranules - 1);
        this.state = 2;
        this.start = this.payloadStartPosition;
        this.end = this.payloadEndPosition;
        this.startGranule = 0L;
        this.endGranule = this.totalGranules;
    }

    @Override // androidx.media3.extractor.ogg.OggSeeker
    @Nullable
    public OggSeekMap createSeekMap() {
        if (this.totalGranules != 0) {
            return new OggSeekMap();
        }
        return null;
    }
}
