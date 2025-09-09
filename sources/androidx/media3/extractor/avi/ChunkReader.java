package androidx.media3.extractor.avi;

import androidx.media3.common.DataReader;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.ExtractorInput;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.SeekPoint;
import androidx.media3.extractor.TrackOutput;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes2.dex */
final class ChunkReader {
    private static final int CHUNK_TYPE_AUDIO = 1651965952;
    private static final int CHUNK_TYPE_VIDEO_COMPRESSED = 1667497984;
    private static final int CHUNK_TYPE_VIDEO_UNCOMPRESSED = 1650720768;
    private static final int INITIAL_INDEX_SIZE = 512;

    /* renamed from: a, reason: collision with root package name */
    protected final TrackOutput f5607a;
    private final int alternativeChunkId;
    private int bytesRemainingInCurrentChunk;
    private final int chunkId;
    private int currentChunkIndex;
    private int currentChunkSize;
    private final long durationUs;
    private int indexChunkCount;
    private int indexSize;
    private int[] keyFrameIndices;
    private long[] keyFrameOffsets;
    private final int streamHeaderChunkCount;

    public ChunkReader(int i2, int i3, long j2, int i4, TrackOutput trackOutput) {
        boolean z2 = true;
        if (i3 != 1 && i3 != 2) {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        this.durationUs = j2;
        this.streamHeaderChunkCount = i4;
        this.f5607a = trackOutput;
        this.chunkId = getChunkIdFourCc(i2, i3 == 2 ? CHUNK_TYPE_VIDEO_COMPRESSED : CHUNK_TYPE_AUDIO);
        this.alternativeChunkId = i3 == 2 ? getChunkIdFourCc(i2, CHUNK_TYPE_VIDEO_UNCOMPRESSED) : -1;
        this.keyFrameOffsets = new long[512];
        this.keyFrameIndices = new int[512];
    }

    private static int getChunkIdFourCc(int i2, int i3) {
        return (((i2 % 10) + 48) << 8) | ((i2 / 10) + 48) | i3;
    }

    private long getChunkTimestampUs(int i2) {
        return (this.durationUs * i2) / this.streamHeaderChunkCount;
    }

    private SeekPoint getSeekPoint(int i2) {
        return new SeekPoint(this.keyFrameIndices[i2] * getFrameDurationUs(), this.keyFrameOffsets[i2]);
    }

    public void advanceCurrentChunk() {
        this.currentChunkIndex++;
    }

    public void appendKeyFrameToIndex(long j2) {
        if (this.indexSize == this.keyFrameIndices.length) {
            long[] jArr = this.keyFrameOffsets;
            this.keyFrameOffsets = Arrays.copyOf(jArr, (jArr.length * 3) / 2);
            int[] iArr = this.keyFrameIndices;
            this.keyFrameIndices = Arrays.copyOf(iArr, (iArr.length * 3) / 2);
        }
        long[] jArr2 = this.keyFrameOffsets;
        int i2 = this.indexSize;
        jArr2[i2] = j2;
        this.keyFrameIndices[i2] = this.indexChunkCount;
        this.indexSize = i2 + 1;
    }

    public void compactIndex() {
        this.keyFrameOffsets = Arrays.copyOf(this.keyFrameOffsets, this.indexSize);
        this.keyFrameIndices = Arrays.copyOf(this.keyFrameIndices, this.indexSize);
    }

    public long getCurrentChunkTimestampUs() {
        return getChunkTimestampUs(this.currentChunkIndex);
    }

    public long getFrameDurationUs() {
        return getChunkTimestampUs(1);
    }

    public SeekMap.SeekPoints getSeekPoints(long j2) {
        int frameDurationUs = (int) (j2 / getFrameDurationUs());
        int iBinarySearchFloor = Util.binarySearchFloor(this.keyFrameIndices, frameDurationUs, true, true);
        if (this.keyFrameIndices[iBinarySearchFloor] == frameDurationUs) {
            return new SeekMap.SeekPoints(getSeekPoint(iBinarySearchFloor));
        }
        SeekPoint seekPoint = getSeekPoint(iBinarySearchFloor);
        int i2 = iBinarySearchFloor + 1;
        return i2 < this.keyFrameOffsets.length ? new SeekMap.SeekPoints(seekPoint, getSeekPoint(i2)) : new SeekMap.SeekPoints(seekPoint);
    }

    public boolean handlesChunkId(int i2) {
        return this.chunkId == i2 || this.alternativeChunkId == i2;
    }

    public void incrementIndexChunkCount() {
        this.indexChunkCount++;
    }

    public boolean isAudio() {
        return (this.chunkId & CHUNK_TYPE_AUDIO) == CHUNK_TYPE_AUDIO;
    }

    public boolean isCurrentFrameAKeyFrame() {
        return Arrays.binarySearch(this.keyFrameIndices, this.currentChunkIndex) >= 0;
    }

    public boolean isVideo() {
        return (this.chunkId & CHUNK_TYPE_VIDEO_COMPRESSED) == CHUNK_TYPE_VIDEO_COMPRESSED;
    }

    public boolean onChunkData(ExtractorInput extractorInput) throws IOException {
        int i2 = this.bytesRemainingInCurrentChunk;
        int iSampleData = i2 - this.f5607a.sampleData((DataReader) extractorInput, i2, false);
        this.bytesRemainingInCurrentChunk = iSampleData;
        boolean z2 = iSampleData == 0;
        if (z2) {
            if (this.currentChunkSize > 0) {
                this.f5607a.sampleMetadata(getCurrentChunkTimestampUs(), isCurrentFrameAKeyFrame() ? 1 : 0, this.currentChunkSize, 0, null);
            }
            advanceCurrentChunk();
        }
        return z2;
    }

    public void onChunkStart(int i2) {
        this.currentChunkSize = i2;
        this.bytesRemainingInCurrentChunk = i2;
    }

    public void seekToPosition(long j2) {
        if (this.indexSize == 0) {
            this.currentChunkIndex = 0;
        } else {
            this.currentChunkIndex = this.keyFrameIndices[Util.binarySearchFloor(this.keyFrameOffsets, j2, true, true)];
        }
    }
}
