package androidx.media3.extractor.ogg;

import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.ExtractorInput;
import androidx.media3.extractor.FlacFrameReader;
import androidx.media3.extractor.FlacMetadataReader;
import androidx.media3.extractor.FlacSeekTableSeekMap;
import androidx.media3.extractor.FlacStreamMetadata;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.ogg.StreamReader;
import java.util.Arrays;

/* loaded from: classes2.dex */
final class FlacReader extends StreamReader {
    private static final byte AUDIO_PACKET_TYPE = -1;
    private static final int FRAME_HEADER_SAMPLE_NUMBER_OFFSET = 4;

    @Nullable
    private FlacOggSeeker flacOggSeeker;

    @Nullable
    private FlacStreamMetadata streamMetadata;

    private static final class FlacOggSeeker implements OggSeeker {
        private long firstFrameOffset = -1;
        private long pendingSeekGranule = -1;
        private FlacStreamMetadata.SeekTable seekTable;
        private FlacStreamMetadata streamMetadata;

        public FlacOggSeeker(FlacStreamMetadata flacStreamMetadata, FlacStreamMetadata.SeekTable seekTable) {
            this.streamMetadata = flacStreamMetadata;
            this.seekTable = seekTable;
        }

        @Override // androidx.media3.extractor.ogg.OggSeeker
        public SeekMap createSeekMap() {
            Assertions.checkState(this.firstFrameOffset != -1);
            return new FlacSeekTableSeekMap(this.streamMetadata, this.firstFrameOffset);
        }

        @Override // androidx.media3.extractor.ogg.OggSeeker
        public long read(ExtractorInput extractorInput) {
            long j2 = this.pendingSeekGranule;
            if (j2 < 0) {
                return -1L;
            }
            long j3 = -(j2 + 2);
            this.pendingSeekGranule = -1L;
            return j3;
        }

        public void setFirstFrameOffset(long j2) {
            this.firstFrameOffset = j2;
        }

        @Override // androidx.media3.extractor.ogg.OggSeeker
        public void startSeek(long j2) {
            long[] jArr = this.seekTable.pointSampleNumbers;
            this.pendingSeekGranule = jArr[Util.binarySearchFloor(jArr, j2, true, true)];
        }
    }

    FlacReader() {
    }

    private int getFlacFrameBlockSize(ParsableByteArray parsableByteArray) {
        int i2 = (parsableByteArray.getData()[2] & 255) >> 4;
        if (i2 == 6 || i2 == 7) {
            parsableByteArray.skipBytes(4);
            parsableByteArray.readUtf8EncodedLong();
        }
        int frameBlockSizeSamplesFromKey = FlacFrameReader.readFrameBlockSizeSamplesFromKey(parsableByteArray, i2);
        parsableByteArray.setPosition(0);
        return frameBlockSizeSamplesFromKey;
    }

    private static boolean isAudioPacket(byte[] bArr) {
        return bArr[0] == -1;
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        return parsableByteArray.bytesLeft() >= 5 && parsableByteArray.readUnsignedByte() == 127 && parsableByteArray.readUnsignedInt() == 1179402563;
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected long e(ParsableByteArray parsableByteArray) {
        if (isAudioPacket(parsableByteArray.getData())) {
            return getFlacFrameBlockSize(parsableByteArray);
        }
        return -1L;
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected boolean g(ParsableByteArray parsableByteArray, long j2, StreamReader.SetupData setupData) {
        byte[] data = parsableByteArray.getData();
        FlacStreamMetadata flacStreamMetadata = this.streamMetadata;
        if (flacStreamMetadata == null) {
            FlacStreamMetadata flacStreamMetadata2 = new FlacStreamMetadata(data, 17);
            this.streamMetadata = flacStreamMetadata2;
            setupData.f5618a = flacStreamMetadata2.getFormat(Arrays.copyOfRange(data, 9, parsableByteArray.limit()), null);
            return true;
        }
        if ((data[0] & Byte.MAX_VALUE) == 3) {
            FlacStreamMetadata.SeekTable seekTableMetadataBlock = FlacMetadataReader.readSeekTableMetadataBlock(parsableByteArray);
            FlacStreamMetadata flacStreamMetadataCopyWithSeekTable = flacStreamMetadata.copyWithSeekTable(seekTableMetadataBlock);
            this.streamMetadata = flacStreamMetadataCopyWithSeekTable;
            this.flacOggSeeker = new FlacOggSeeker(flacStreamMetadataCopyWithSeekTable, seekTableMetadataBlock);
            return true;
        }
        if (!isAudioPacket(data)) {
            return true;
        }
        FlacOggSeeker flacOggSeeker = this.flacOggSeeker;
        if (flacOggSeeker != null) {
            flacOggSeeker.setFirstFrameOffset(j2);
            setupData.f5619b = this.flacOggSeeker;
        }
        Assertions.checkNotNull(setupData.f5618a);
        return false;
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected void h(boolean z2) {
        super.h(z2);
        if (z2) {
            this.streamMetadata = null;
            this.flacOggSeeker = null;
        }
    }
}
