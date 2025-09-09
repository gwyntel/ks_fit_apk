package androidx.media3.extractor.ts;

import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableBitArray;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.TrackOutput;
import androidx.media3.extractor.ts.MpeghUtil;
import androidx.media3.extractor.ts.TsPayloadReader;
import com.google.common.collect.ImmutableList;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

@UnstableApi
/* loaded from: classes2.dex */
public final class MpeghReader implements ElementaryStreamReader {
    private static final int MAX_MHAS_PACKET_HEADER_SIZE = 15;
    private static final int MHAS_SYNC_WORD_LENGTH = 3;
    private static final int MIN_MHAS_PACKET_HEADER_SIZE = 2;
    private static final int STATE_FINDING_SYNC = 0;
    private static final int STATE_READING_PACKET_HEADER = 1;
    private static final int STATE_READING_PACKET_PAYLOAD = 2;
    private boolean configFound;
    private boolean dataPending;
    private int flags;
    private String formatId;
    private int frameBytes;
    private TrackOutput output;
    private int payloadBytesRead;
    private int syncBytes;
    private int truncationSamples;
    private int state = 0;
    private final ParsableByteArray headerScratchBytes = new ParsableByteArray(new byte[15], 2);
    private final ParsableBitArray headerScratchBits = new ParsableBitArray();
    private final ParsableByteArray dataScratchBytes = new ParsableByteArray();
    private MpeghUtil.MhasPacketHeader header = new MpeghUtil.MhasPacketHeader();
    private int samplingRate = C.RATE_UNSET_INT;
    private int standardFrameLength = -1;
    private long mainStreamLabel = -1;
    private boolean rapPending = true;
    private boolean headerDataFinished = true;
    private double timeUs = -9.223372036854776E18d;
    private double timeUsPending = -9.223372036854776E18d;

    private void copyData(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z2) {
        int position = parsableByteArray.getPosition();
        int iMin = Math.min(parsableByteArray.bytesLeft(), parsableByteArray2.bytesLeft());
        parsableByteArray.readBytes(parsableByteArray2.getData(), parsableByteArray2.getPosition(), iMin);
        parsableByteArray2.skipBytes(iMin);
        if (z2) {
            parsableByteArray.setPosition(position);
        }
    }

    @RequiresNonNull({"output"})
    private void finalizeFrame() {
        int i2;
        if (this.configFound) {
            this.rapPending = false;
            i2 = 1;
        } else {
            i2 = 0;
        }
        double d2 = ((this.standardFrameLength - this.truncationSamples) * 1000000.0d) / this.samplingRate;
        long jRound = Math.round(this.timeUs);
        if (this.dataPending) {
            this.dataPending = false;
            this.timeUs = this.timeUsPending;
        } else {
            this.timeUs += d2;
        }
        this.output.sampleMetadata(jRound, i2, this.frameBytes, 0, null);
        this.configFound = false;
        this.truncationSamples = 0;
        this.frameBytes = 0;
    }

    @RequiresNonNull({"output"})
    private void parseConfig(ParsableBitArray parsableBitArray) throws ParserException {
        MpeghUtil.Mpegh3daConfig mpegh3daConfig = MpeghUtil.parseMpegh3daConfig(parsableBitArray);
        this.samplingRate = mpegh3daConfig.samplingFrequency;
        this.standardFrameLength = mpegh3daConfig.standardFrameLength;
        long j2 = this.mainStreamLabel;
        long j3 = this.header.packetLabel;
        if (j2 != j3) {
            this.mainStreamLabel = j3;
            String str = "mhm1";
            if (mpegh3daConfig.profileLevelIndication != -1) {
                str = "mhm1" + String.format(".%02X", Integer.valueOf(mpegh3daConfig.profileLevelIndication));
            }
            byte[] bArr = mpegh3daConfig.compatibleProfileLevelSet;
            this.output.format(new Format.Builder().setId(this.formatId).setSampleMimeType(MimeTypes.AUDIO_MPEGH_MHM1).setSampleRate(this.samplingRate).setCodecs(str).setInitializationData((bArr == null || bArr.length <= 0) ? null : ImmutableList.of(Util.EMPTY_BYTE_ARRAY, bArr)).build());
        }
        this.configFound = true;
    }

    private boolean parseHeader() throws ParserException {
        int iLimit = this.headerScratchBytes.limit();
        this.headerScratchBits.reset(this.headerScratchBytes.getData(), iLimit);
        boolean mhasPacketHeader = MpeghUtil.parseMhasPacketHeader(this.headerScratchBits, this.header);
        if (mhasPacketHeader) {
            this.payloadBytesRead = 0;
            this.frameBytes += this.header.packetLength + iLimit;
        }
        return mhasPacketHeader;
    }

    private boolean shouldParsePacket(int i2) {
        return i2 == 1 || i2 == 17;
    }

    private boolean skipToNextSync(ParsableByteArray parsableByteArray) {
        int i2 = this.flags;
        if ((i2 & 2) == 0) {
            parsableByteArray.setPosition(parsableByteArray.limit());
            return false;
        }
        if ((i2 & 4) != 0) {
            return true;
        }
        while (parsableByteArray.bytesLeft() > 0) {
            int i3 = this.syncBytes << 8;
            this.syncBytes = i3;
            int unsignedByte = i3 | parsableByteArray.readUnsignedByte();
            this.syncBytes = unsignedByte;
            if (MpeghUtil.isSyncWord(unsignedByte)) {
                parsableByteArray.setPosition(parsableByteArray.getPosition() - 3);
                this.syncBytes = 0;
                return true;
            }
        }
        return false;
    }

    @RequiresNonNull({"output"})
    private void writeSampleData(ParsableByteArray parsableByteArray) {
        int iMin = Math.min(parsableByteArray.bytesLeft(), this.header.packetLength - this.payloadBytesRead);
        this.output.sampleData(parsableByteArray, iMin);
        this.payloadBytesRead += iMin;
    }

    @Override // androidx.media3.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) throws ParserException {
        Assertions.checkStateNotNull(this.output);
        while (parsableByteArray.bytesLeft() > 0) {
            int i2 = this.state;
            if (i2 != 0) {
                if (i2 == 1) {
                    copyData(parsableByteArray, this.headerScratchBytes, false);
                    if (this.headerScratchBytes.bytesLeft() != 0) {
                        this.headerDataFinished = false;
                    } else if (parseHeader()) {
                        this.headerScratchBytes.setPosition(0);
                        TrackOutput trackOutput = this.output;
                        ParsableByteArray parsableByteArray2 = this.headerScratchBytes;
                        trackOutput.sampleData(parsableByteArray2, parsableByteArray2.limit());
                        this.headerScratchBytes.reset(2);
                        this.dataScratchBytes.reset(this.header.packetLength);
                        this.headerDataFinished = true;
                        this.state = 2;
                    } else if (this.headerScratchBytes.limit() < 15) {
                        ParsableByteArray parsableByteArray3 = this.headerScratchBytes;
                        parsableByteArray3.setLimit(parsableByteArray3.limit() + 1);
                        this.headerDataFinished = false;
                    }
                } else {
                    if (i2 != 2) {
                        throw new IllegalStateException();
                    }
                    if (shouldParsePacket(this.header.packetType)) {
                        copyData(parsableByteArray, this.dataScratchBytes, true);
                    }
                    writeSampleData(parsableByteArray);
                    int i3 = this.payloadBytesRead;
                    MpeghUtil.MhasPacketHeader mhasPacketHeader = this.header;
                    if (i3 == mhasPacketHeader.packetLength) {
                        int i4 = mhasPacketHeader.packetType;
                        if (i4 == 1) {
                            parseConfig(new ParsableBitArray(this.dataScratchBytes.getData()));
                        } else if (i4 == 17) {
                            this.truncationSamples = MpeghUtil.parseAudioTruncationInfo(new ParsableBitArray(this.dataScratchBytes.getData()));
                        } else if (i4 == 2) {
                            finalizeFrame();
                        }
                        this.state = 1;
                    }
                }
            } else if (skipToNextSync(parsableByteArray)) {
                this.state = 1;
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
        this.flags = i2;
        if (!this.rapPending && (this.frameBytes != 0 || !this.headerDataFinished)) {
            this.dataPending = true;
        }
        if (j2 != C.TIME_UNSET) {
            if (this.dataPending) {
                this.timeUsPending = j2;
            } else {
                this.timeUs = j2;
            }
        }
    }

    @Override // androidx.media3.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.state = 0;
        this.syncBytes = 0;
        this.headerScratchBytes.reset(2);
        this.payloadBytesRead = 0;
        this.frameBytes = 0;
        this.samplingRate = C.RATE_UNSET_INT;
        this.standardFrameLength = -1;
        this.truncationSamples = 0;
        this.mainStreamLabel = -1L;
        this.configFound = false;
        this.dataPending = false;
        this.headerDataFinished = true;
        this.rapPending = true;
        this.timeUs = -9.223372036854776E18d;
        this.timeUsPending = -9.223372036854776E18d;
    }
}
