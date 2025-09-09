package androidx.media3.extractor.ogg;

import androidx.media3.common.Format;
import androidx.media3.common.Metadata;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.extractor.OpusUtil;
import androidx.media3.extractor.VorbisUtil;
import androidx.media3.extractor.ogg.StreamReader;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
final class OpusReader extends StreamReader {
    private boolean firstCommentHeaderSeen;
    private static final byte[] OPUS_ID_HEADER_SIGNATURE = {79, 112, 117, 115, 72, 101, 97, 100};
    private static final byte[] OPUS_COMMENT_HEADER_SIGNATURE = {79, 112, 117, 115, 84, 97, 103, 115};

    OpusReader() {
    }

    private static boolean peekPacketStartsWith(ParsableByteArray parsableByteArray, byte[] bArr) {
        if (parsableByteArray.bytesLeft() < bArr.length) {
            return false;
        }
        int position = parsableByteArray.getPosition();
        byte[] bArr2 = new byte[bArr.length];
        parsableByteArray.readBytes(bArr2, 0, bArr.length);
        parsableByteArray.setPosition(position);
        return Arrays.equals(bArr2, bArr);
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        return peekPacketStartsWith(parsableByteArray, OPUS_ID_HEADER_SIGNATURE);
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected long e(ParsableByteArray parsableByteArray) {
        return b(OpusUtil.getPacketDurationUs(parsableByteArray.getData()));
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected boolean g(ParsableByteArray parsableByteArray, long j2, StreamReader.SetupData setupData) {
        if (peekPacketStartsWith(parsableByteArray, OPUS_ID_HEADER_SIGNATURE)) {
            byte[] bArrCopyOf = Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit());
            int channelCount = OpusUtil.getChannelCount(bArrCopyOf);
            List<byte[]> listBuildInitializationData = OpusUtil.buildInitializationData(bArrCopyOf);
            if (setupData.f5618a != null) {
                return true;
            }
            setupData.f5618a = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_OPUS).setChannelCount(channelCount).setSampleRate(OpusUtil.SAMPLE_RATE).setInitializationData(listBuildInitializationData).build();
            return true;
        }
        byte[] bArr = OPUS_COMMENT_HEADER_SIGNATURE;
        if (!peekPacketStartsWith(parsableByteArray, bArr)) {
            Assertions.checkStateNotNull(setupData.f5618a);
            return false;
        }
        Assertions.checkStateNotNull(setupData.f5618a);
        if (this.firstCommentHeaderSeen) {
            return true;
        }
        this.firstCommentHeaderSeen = true;
        parsableByteArray.skipBytes(bArr.length);
        Metadata vorbisComments = VorbisUtil.parseVorbisComments(ImmutableList.copyOf(VorbisUtil.readVorbisCommentHeader(parsableByteArray, false, false).comments));
        if (vorbisComments == null) {
            return true;
        }
        setupData.f5618a = setupData.f5618a.buildUpon().setMetadata(vorbisComments.copyWithAppendedEntriesFrom(setupData.f5618a.metadata)).build();
        return true;
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected void h(boolean z2) {
        super.h(z2);
        if (z2) {
            this.firstCommentHeaderSeen = false;
        }
    }
}
