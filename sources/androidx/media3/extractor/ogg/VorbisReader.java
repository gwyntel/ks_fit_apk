package androidx.media3.extractor.ogg;

import androidx.annotation.Nullable;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.extractor.VorbisUtil;
import androidx.media3.extractor.ogg.StreamReader;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes2.dex */
final class VorbisReader extends StreamReader {

    @Nullable
    private VorbisUtil.CommentHeader commentHeader;
    private int previousPacketBlockSize;
    private boolean seenFirstAudioPacket;

    @Nullable
    private VorbisUtil.VorbisIdHeader vorbisIdHeader;

    @Nullable
    private VorbisSetup vorbisSetup;

    static final class VorbisSetup {
        public final VorbisUtil.CommentHeader commentHeader;
        public final int iLogModes;
        public final VorbisUtil.VorbisIdHeader idHeader;
        public final VorbisUtil.Mode[] modes;
        public final byte[] setupHeaderData;

        public VorbisSetup(VorbisUtil.VorbisIdHeader vorbisIdHeader, VorbisUtil.CommentHeader commentHeader, byte[] bArr, VorbisUtil.Mode[] modeArr, int i2) {
            this.idHeader = vorbisIdHeader;
            this.commentHeader = commentHeader;
            this.setupHeaderData = bArr;
            this.modes = modeArr;
            this.iLogModes = i2;
        }
    }

    VorbisReader() {
    }

    private static int decodeBlockSize(byte b2, VorbisSetup vorbisSetup) {
        return !vorbisSetup.modes[k(b2, vorbisSetup.iLogModes, 1)].blockFlag ? vorbisSetup.idHeader.blockSize0 : vorbisSetup.idHeader.blockSize1;
    }

    static void j(ParsableByteArray parsableByteArray, long j2) {
        if (parsableByteArray.capacity() < parsableByteArray.limit() + 4) {
            parsableByteArray.reset(Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit() + 4));
        } else {
            parsableByteArray.setLimit(parsableByteArray.limit() + 4);
        }
        byte[] data = parsableByteArray.getData();
        data[parsableByteArray.limit() - 4] = (byte) (j2 & 255);
        data[parsableByteArray.limit() - 3] = (byte) ((j2 >>> 8) & 255);
        data[parsableByteArray.limit() - 2] = (byte) ((j2 >>> 16) & 255);
        data[parsableByteArray.limit() - 1] = (byte) ((j2 >>> 24) & 255);
    }

    static int k(byte b2, int i2, int i3) {
        return (b2 >> i3) & (255 >>> (8 - i2));
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        try {
            return VorbisUtil.verifyVorbisHeaderCapturePattern(1, parsableByteArray, true);
        } catch (ParserException unused) {
            return false;
        }
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected void d(long j2) {
        super.d(j2);
        this.seenFirstAudioPacket = j2 != 0;
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.vorbisIdHeader;
        this.previousPacketBlockSize = vorbisIdHeader != null ? vorbisIdHeader.blockSize0 : 0;
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected long e(ParsableByteArray parsableByteArray) {
        if ((parsableByteArray.getData()[0] & 1) == 1) {
            return -1L;
        }
        int iDecodeBlockSize = decodeBlockSize(parsableByteArray.getData()[0], (VorbisSetup) Assertions.checkStateNotNull(this.vorbisSetup));
        long j2 = this.seenFirstAudioPacket ? (this.previousPacketBlockSize + iDecodeBlockSize) / 4 : 0;
        j(parsableByteArray, j2);
        this.seenFirstAudioPacket = true;
        this.previousPacketBlockSize = iDecodeBlockSize;
        return j2;
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected boolean g(ParsableByteArray parsableByteArray, long j2, StreamReader.SetupData setupData) throws ParserException {
        if (this.vorbisSetup != null) {
            Assertions.checkNotNull(setupData.f5618a);
            return false;
        }
        VorbisSetup vorbisSetupL = l(parsableByteArray);
        this.vorbisSetup = vorbisSetupL;
        if (vorbisSetupL == null) {
            return true;
        }
        VorbisUtil.VorbisIdHeader vorbisIdHeader = vorbisSetupL.idHeader;
        ArrayList arrayList = new ArrayList();
        arrayList.add(vorbisIdHeader.data);
        arrayList.add(vorbisSetupL.setupHeaderData);
        setupData.f5618a = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_VORBIS).setAverageBitrate(vorbisIdHeader.bitrateNominal).setPeakBitrate(vorbisIdHeader.bitrateMaximum).setChannelCount(vorbisIdHeader.channels).setSampleRate(vorbisIdHeader.sampleRate).setInitializationData(arrayList).setMetadata(VorbisUtil.parseVorbisComments(ImmutableList.copyOf(vorbisSetupL.commentHeader.comments))).build();
        return true;
    }

    @Override // androidx.media3.extractor.ogg.StreamReader
    protected void h(boolean z2) {
        super.h(z2);
        if (z2) {
            this.vorbisSetup = null;
            this.vorbisIdHeader = null;
            this.commentHeader = null;
        }
        this.previousPacketBlockSize = 0;
        this.seenFirstAudioPacket = false;
    }

    VorbisSetup l(ParsableByteArray parsableByteArray) throws ParserException {
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.vorbisIdHeader;
        if (vorbisIdHeader == null) {
            this.vorbisIdHeader = VorbisUtil.readVorbisIdentificationHeader(parsableByteArray);
            return null;
        }
        VorbisUtil.CommentHeader commentHeader = this.commentHeader;
        if (commentHeader == null) {
            this.commentHeader = VorbisUtil.readVorbisCommentHeader(parsableByteArray);
            return null;
        }
        byte[] bArr = new byte[parsableByteArray.limit()];
        System.arraycopy(parsableByteArray.getData(), 0, bArr, 0, parsableByteArray.limit());
        return new VorbisSetup(vorbisIdHeader, commentHeader, bArr, VorbisUtil.readVorbisModes(parsableByteArray, vorbisIdHeader.channels), VorbisUtil.iLog(r4.length - 1));
    }
}
