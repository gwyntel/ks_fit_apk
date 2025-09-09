package androidx.media3.extractor.ts;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.DtsUtil;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.TrackOutput;
import androidx.media3.extractor.ts.TsPayloadReader;
import com.google.common.primitives.Ints;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

@UnstableApi
/* loaded from: classes2.dex */
public final class DtsReader implements ElementaryStreamReader {
    private static final int CORE_HEADER_SIZE = 18;
    private static final int STATE_FINDING_EXTSS_HEADER_SIZE = 2;
    private static final int STATE_FINDING_SYNC = 0;
    private static final int STATE_FINDING_UHD_HEADER_SIZE = 4;
    private static final int STATE_READING_CORE_HEADER = 1;
    private static final int STATE_READING_EXTSS_HEADER = 3;
    private static final int STATE_READING_SAMPLE = 6;
    private static final int STATE_READING_UHD_HEADER = 5;
    private int bytesRead;
    private Format format;
    private String formatId;
    private int frameType;
    private final ParsableByteArray headerScratchBytes;

    @Nullable
    private final String language;
    private TrackOutput output;
    private final int roleFlags;
    private long sampleDurationUs;
    private int sampleSize;
    private int syncBytes;
    private int state = 0;
    private long timeUs = C.TIME_UNSET;
    private final AtomicInteger uhdAudioChunkId = new AtomicInteger();
    private int extensionSubstreamHeaderSize = -1;
    private int uhdHeaderSize = -1;

    public DtsReader(@Nullable String str, int i2, int i3) {
        this.headerScratchBytes = new ParsableByteArray(new byte[i3]);
        this.language = str;
        this.roleFlags = i2;
    }

    private boolean continueRead(ParsableByteArray parsableByteArray, byte[] bArr, int i2) {
        int iMin = Math.min(parsableByteArray.bytesLeft(), i2 - this.bytesRead);
        parsableByteArray.readBytes(bArr, this.bytesRead, iMin);
        int i3 = this.bytesRead + iMin;
        this.bytesRead = i3;
        return i3 == i2;
    }

    @RequiresNonNull({"output"})
    private void parseCoreHeader() {
        byte[] data = this.headerScratchBytes.getData();
        if (this.format == null) {
            Format dtsFormat = DtsUtil.parseDtsFormat(data, this.formatId, this.language, this.roleFlags, null);
            this.format = dtsFormat;
            this.output.format(dtsFormat);
        }
        this.sampleSize = DtsUtil.getDtsFrameSize(data);
        this.sampleDurationUs = Ints.checkedCast(Util.sampleCountToDurationUs(DtsUtil.parseDtsAudioSampleCount(data), this.format.sampleRate));
    }

    @RequiresNonNull({"output"})
    private void parseExtensionSubstreamHeader() throws ParserException {
        DtsUtil.DtsHeader dtsHdHeader = DtsUtil.parseDtsHdHeader(this.headerScratchBytes.getData());
        updateFormatWithDtsHeaderInfo(dtsHdHeader);
        this.sampleSize = dtsHdHeader.frameSize;
        long j2 = dtsHdHeader.frameDurationUs;
        if (j2 == C.TIME_UNSET) {
            j2 = 0;
        }
        this.sampleDurationUs = j2;
    }

    @RequiresNonNull({"output"})
    private void parseUhdHeader() throws ParserException {
        DtsUtil.DtsHeader dtsUhdHeader = DtsUtil.parseDtsUhdHeader(this.headerScratchBytes.getData(), this.uhdAudioChunkId);
        if (this.frameType == 3) {
            updateFormatWithDtsHeaderInfo(dtsUhdHeader);
        }
        this.sampleSize = dtsUhdHeader.frameSize;
        long j2 = dtsUhdHeader.frameDurationUs;
        if (j2 == C.TIME_UNSET) {
            j2 = 0;
        }
        this.sampleDurationUs = j2;
    }

    private boolean skipToNextSyncWord(ParsableByteArray parsableByteArray) {
        while (parsableByteArray.bytesLeft() > 0) {
            int i2 = this.syncBytes << 8;
            this.syncBytes = i2;
            int unsignedByte = i2 | parsableByteArray.readUnsignedByte();
            this.syncBytes = unsignedByte;
            int frameType = DtsUtil.getFrameType(unsignedByte);
            this.frameType = frameType;
            if (frameType != 0) {
                byte[] data = this.headerScratchBytes.getData();
                int i3 = this.syncBytes;
                data[0] = (byte) ((i3 >> 24) & 255);
                data[1] = (byte) ((i3 >> 16) & 255);
                data[2] = (byte) ((i3 >> 8) & 255);
                data[3] = (byte) (i3 & 255);
                this.bytesRead = 4;
                this.syncBytes = 0;
                return true;
            }
        }
        return false;
    }

    @RequiresNonNull({"output"})
    private void updateFormatWithDtsHeaderInfo(DtsUtil.DtsHeader dtsHeader) {
        int i2;
        int i3 = dtsHeader.sampleRate;
        if (i3 == -2147483647 || (i2 = dtsHeader.channelCount) == -1) {
            return;
        }
        Format format = this.format;
        if (format != null && i2 == format.channelCount && i3 == format.sampleRate && Util.areEqual(dtsHeader.mimeType, format.sampleMimeType)) {
            return;
        }
        Format format2 = this.format;
        Format formatBuild = (format2 == null ? new Format.Builder() : format2.buildUpon()).setId(this.formatId).setSampleMimeType(dtsHeader.mimeType).setChannelCount(dtsHeader.channelCount).setSampleRate(dtsHeader.sampleRate).setLanguage(this.language).setRoleFlags(this.roleFlags).build();
        this.format = formatBuild;
        this.output.format(formatBuild);
    }

    @Override // androidx.media3.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) throws ParserException {
        Assertions.checkStateNotNull(this.output);
        while (parsableByteArray.bytesLeft() > 0) {
            switch (this.state) {
                case 0:
                    if (!skipToNextSyncWord(parsableByteArray)) {
                        break;
                    } else {
                        int i2 = this.frameType;
                        if (i2 != 3 && i2 != 4) {
                            if (i2 != 1) {
                                this.state = 2;
                                break;
                            } else {
                                this.state = 1;
                                break;
                            }
                        } else {
                            this.state = 4;
                            break;
                        }
                    }
                    break;
                case 1:
                    if (!continueRead(parsableByteArray, this.headerScratchBytes.getData(), 18)) {
                        break;
                    } else {
                        parseCoreHeader();
                        this.headerScratchBytes.setPosition(0);
                        this.output.sampleData(this.headerScratchBytes, 18);
                        this.state = 6;
                        break;
                    }
                case 2:
                    if (!continueRead(parsableByteArray, this.headerScratchBytes.getData(), 7)) {
                        break;
                    } else {
                        this.extensionSubstreamHeaderSize = DtsUtil.parseDtsHdHeaderSize(this.headerScratchBytes.getData());
                        this.state = 3;
                        break;
                    }
                case 3:
                    if (!continueRead(parsableByteArray, this.headerScratchBytes.getData(), this.extensionSubstreamHeaderSize)) {
                        break;
                    } else {
                        parseExtensionSubstreamHeader();
                        this.headerScratchBytes.setPosition(0);
                        this.output.sampleData(this.headerScratchBytes, this.extensionSubstreamHeaderSize);
                        this.state = 6;
                        break;
                    }
                case 4:
                    if (!continueRead(parsableByteArray, this.headerScratchBytes.getData(), 6)) {
                        break;
                    } else {
                        int dtsUhdHeaderSize = DtsUtil.parseDtsUhdHeaderSize(this.headerScratchBytes.getData());
                        this.uhdHeaderSize = dtsUhdHeaderSize;
                        int i3 = this.bytesRead;
                        if (i3 > dtsUhdHeaderSize) {
                            int i4 = i3 - dtsUhdHeaderSize;
                            this.bytesRead = i3 - i4;
                            parsableByteArray.setPosition(parsableByteArray.getPosition() - i4);
                        }
                        this.state = 5;
                        break;
                    }
                case 5:
                    if (!continueRead(parsableByteArray, this.headerScratchBytes.getData(), this.uhdHeaderSize)) {
                        break;
                    } else {
                        parseUhdHeader();
                        this.headerScratchBytes.setPosition(0);
                        this.output.sampleData(this.headerScratchBytes, this.uhdHeaderSize);
                        this.state = 6;
                        break;
                    }
                case 6:
                    int iMin = Math.min(parsableByteArray.bytesLeft(), this.sampleSize - this.bytesRead);
                    this.output.sampleData(parsableByteArray, iMin);
                    int i5 = this.bytesRead + iMin;
                    this.bytesRead = i5;
                    if (i5 == this.sampleSize) {
                        Assertions.checkState(this.timeUs != C.TIME_UNSET);
                        this.output.sampleMetadata(this.timeUs, this.frameType == 4 ? 0 : 1, this.sampleSize, 0, null);
                        this.timeUs += this.sampleDurationUs;
                        this.state = 0;
                        break;
                    } else {
                        break;
                    }
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override // androidx.media3.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.formatId = trackIdGenerator.getFormatId();
        this.output = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    @Override // androidx.media3.extractor.ts.ElementaryStreamReader
    public void packetFinished(boolean z2) {
    }

    @Override // androidx.media3.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j2, int i2) {
        this.timeUs = j2;
    }

    @Override // androidx.media3.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.state = 0;
        this.bytesRead = 0;
        this.syncBytes = 0;
        this.timeUs = C.TIME_UNSET;
        this.uhdAudioChunkId.set(0);
    }
}
