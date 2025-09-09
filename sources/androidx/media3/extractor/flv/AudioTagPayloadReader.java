package androidx.media3.extractor.flv;

import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.extractor.AacUtil;
import androidx.media3.extractor.TrackOutput;
import androidx.media3.extractor.flv.TagPayloadReader;
import java.util.Collections;

/* loaded from: classes2.dex */
final class AudioTagPayloadReader extends TagPayloadReader {
    private static final int AAC_PACKET_TYPE_AAC_RAW = 1;
    private static final int AAC_PACKET_TYPE_SEQUENCE_HEADER = 0;
    private static final int AUDIO_FORMAT_AAC = 10;
    private static final int AUDIO_FORMAT_ALAW = 7;
    private static final int AUDIO_FORMAT_MP3 = 2;
    private static final int AUDIO_FORMAT_ULAW = 8;
    private static final int[] AUDIO_SAMPLING_RATE_TABLE = {5512, 11025, 22050, 44100};
    private int audioFormat;
    private boolean hasOutputFormat;
    private boolean hasParsedAudioDataHeader;

    public AudioTagPayloadReader(TrackOutput trackOutput) {
        super(trackOutput);
    }

    @Override // androidx.media3.extractor.flv.TagPayloadReader
    protected boolean a(ParsableByteArray parsableByteArray) throws TagPayloadReader.UnsupportedFormatException {
        if (this.hasParsedAudioDataHeader) {
            parsableByteArray.skipBytes(1);
        } else {
            int unsignedByte = parsableByteArray.readUnsignedByte();
            int i2 = (unsignedByte >> 4) & 15;
            this.audioFormat = i2;
            if (i2 == 2) {
                this.f5609a.format(new Format.Builder().setSampleMimeType("audio/mpeg").setChannelCount(1).setSampleRate(AUDIO_SAMPLING_RATE_TABLE[(unsignedByte >> 2) & 3]).build());
                this.hasOutputFormat = true;
            } else if (i2 == 7 || i2 == 8) {
                this.f5609a.format(new Format.Builder().setSampleMimeType(i2 == 7 ? MimeTypes.AUDIO_ALAW : MimeTypes.AUDIO_MLAW).setChannelCount(1).setSampleRate(8000).build());
                this.hasOutputFormat = true;
            } else if (i2 != 10) {
                throw new TagPayloadReader.UnsupportedFormatException("Audio format not supported: " + this.audioFormat);
            }
            this.hasParsedAudioDataHeader = true;
        }
        return true;
    }

    @Override // androidx.media3.extractor.flv.TagPayloadReader
    protected boolean b(ParsableByteArray parsableByteArray, long j2) throws ParserException {
        if (this.audioFormat == 2) {
            int iBytesLeft = parsableByteArray.bytesLeft();
            this.f5609a.sampleData(parsableByteArray, iBytesLeft);
            this.f5609a.sampleMetadata(j2, 1, iBytesLeft, 0, null);
            return true;
        }
        int unsignedByte = parsableByteArray.readUnsignedByte();
        if (unsignedByte != 0 || this.hasOutputFormat) {
            if (this.audioFormat == 10 && unsignedByte != 1) {
                return false;
            }
            int iBytesLeft2 = parsableByteArray.bytesLeft();
            this.f5609a.sampleData(parsableByteArray, iBytesLeft2);
            this.f5609a.sampleMetadata(j2, 1, iBytesLeft2, 0, null);
            return true;
        }
        int iBytesLeft3 = parsableByteArray.bytesLeft();
        byte[] bArr = new byte[iBytesLeft3];
        parsableByteArray.readBytes(bArr, 0, iBytesLeft3);
        AacUtil.Config audioSpecificConfig = AacUtil.parseAudioSpecificConfig(bArr);
        this.f5609a.format(new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_AAC).setCodecs(audioSpecificConfig.codecs).setChannelCount(audioSpecificConfig.channelCount).setSampleRate(audioSpecificConfig.sampleRateHz).setInitializationData(Collections.singletonList(bArr)).build());
        this.hasOutputFormat = true;
        return false;
    }

    @Override // androidx.media3.extractor.flv.TagPayloadReader
    public void seek() {
    }
}
