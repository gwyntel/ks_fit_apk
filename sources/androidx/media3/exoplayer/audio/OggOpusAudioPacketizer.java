package androidx.media3.exoplayer.audio;

import androidx.annotation.Nullable;
import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.extractor.OpusUtil;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public final class OggOpusAudioPacketizer {
    private static final int CHECKSUM_INDEX = 22;
    private static final int FIRST_AUDIO_SAMPLE_PAGE_SEQUENCE_NUMBER = 2;
    private static final int OGG_PACKET_HEADER_LENGTH = 28;
    private static final int SERIAL_NUMBER = 0;
    private static final byte[] OGG_DEFAULT_ID_HEADER_PAGE = {79, 103, 103, 83, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Ascii.FS, -43, -59, -9, 1, 19, 79, 112, 117, 115, 72, 101, 97, 100, 1, 2, 56, 1, Byte.MIN_VALUE, -69, 0, 0, 0, 0, 0};
    private static final byte[] OGG_DEFAULT_COMMENT_HEADER_PAGE = {79, 103, 103, 83, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 11, -103, 87, 83, 1, 16, 79, 112, 117, 115, 84, 97, 103, 115, 0, 0, 0, 0, 0, 0, 0, 0};
    private ByteBuffer outputBuffer = AudioProcessor.EMPTY_BUFFER;
    private int granulePosition = 0;
    private int pageSequenceNumber = 2;

    private ByteBuffer packetizeInternal(ByteBuffer byteBuffer, @Nullable byte[] bArr) {
        int i2;
        int iPosition = byteBuffer.position();
        int iLimit = byteBuffer.limit();
        int i3 = iLimit - iPosition;
        int i4 = (i3 + 255) / 255;
        int length = i4 + 27 + i3;
        if (this.pageSequenceNumber == 2) {
            int length2 = bArr != null ? bArr.length + 28 : OGG_DEFAULT_ID_HEADER_PAGE.length;
            length += OGG_DEFAULT_COMMENT_HEADER_PAGE.length + length2;
            i2 = length2;
        } else {
            i2 = 0;
        }
        ByteBuffer byteBufferReplaceOutputBuffer = replaceOutputBuffer(length);
        if (this.pageSequenceNumber == 2) {
            if (bArr != null) {
                writeOggIdHeaderPage(byteBufferReplaceOutputBuffer, bArr);
            } else {
                byteBufferReplaceOutputBuffer.put(OGG_DEFAULT_ID_HEADER_PAGE);
            }
            byteBufferReplaceOutputBuffer.put(OGG_DEFAULT_COMMENT_HEADER_PAGE);
        }
        int packetAudioSampleCount = this.granulePosition + OpusUtil.parsePacketAudioSampleCount(byteBuffer);
        this.granulePosition = packetAudioSampleCount;
        writeOggPacketHeader(byteBufferReplaceOutputBuffer, packetAudioSampleCount, this.pageSequenceNumber, i4, false);
        for (int i5 = 0; i5 < i4; i5++) {
            if (i3 >= 255) {
                byteBufferReplaceOutputBuffer.put((byte) -1);
                i3 -= 255;
            } else {
                byteBufferReplaceOutputBuffer.put((byte) i3);
                i3 = 0;
            }
        }
        while (iPosition < iLimit) {
            byteBufferReplaceOutputBuffer.put(byteBuffer.get(iPosition));
            iPosition++;
        }
        byteBuffer.position(byteBuffer.limit());
        byteBufferReplaceOutputBuffer.flip();
        if (this.pageSequenceNumber == 2) {
            byte[] bArrArray = byteBufferReplaceOutputBuffer.array();
            int iArrayOffset = byteBufferReplaceOutputBuffer.arrayOffset() + i2;
            byte[] bArr2 = OGG_DEFAULT_COMMENT_HEADER_PAGE;
            byteBufferReplaceOutputBuffer.putInt(i2 + bArr2.length + 22, Util.crc32(bArrArray, iArrayOffset + bArr2.length, byteBufferReplaceOutputBuffer.limit() - byteBufferReplaceOutputBuffer.position(), 0));
        } else {
            byteBufferReplaceOutputBuffer.putInt(22, Util.crc32(byteBufferReplaceOutputBuffer.array(), byteBufferReplaceOutputBuffer.arrayOffset(), byteBufferReplaceOutputBuffer.limit() - byteBufferReplaceOutputBuffer.position(), 0));
        }
        this.pageSequenceNumber++;
        return byteBufferReplaceOutputBuffer;
    }

    private ByteBuffer replaceOutputBuffer(int i2) {
        if (this.outputBuffer.capacity() < i2) {
            this.outputBuffer = ByteBuffer.allocate(i2).order(ByteOrder.LITTLE_ENDIAN);
        } else {
            this.outputBuffer.clear();
        }
        return this.outputBuffer;
    }

    private void writeOggIdHeaderPage(ByteBuffer byteBuffer, byte[] bArr) {
        writeOggPacketHeader(byteBuffer, 0L, 0, 1, true);
        byteBuffer.put(UnsignedBytes.checkedCast(bArr.length));
        byteBuffer.put(bArr);
        byteBuffer.putInt(22, Util.crc32(byteBuffer.array(), byteBuffer.arrayOffset(), bArr.length + 28, 0));
        byteBuffer.position(bArr.length + 28);
    }

    private void writeOggPacketHeader(ByteBuffer byteBuffer, long j2, int i2, int i3, boolean z2) {
        byteBuffer.put((byte) 79);
        byteBuffer.put((byte) 103);
        byteBuffer.put((byte) 103);
        byteBuffer.put((byte) 83);
        byteBuffer.put((byte) 0);
        byteBuffer.put(z2 ? (byte) 2 : (byte) 0);
        byteBuffer.putLong(j2);
        byteBuffer.putInt(0);
        byteBuffer.putInt(i2);
        byteBuffer.putInt(0);
        byteBuffer.put(UnsignedBytes.checkedCast(i3));
    }

    public void packetize(DecoderInputBuffer decoderInputBuffer, List<byte[]> list) {
        Assertions.checkNotNull(decoderInputBuffer.data);
        if (decoderInputBuffer.data.limit() - decoderInputBuffer.data.position() == 0) {
            return;
        }
        this.outputBuffer = packetizeInternal(decoderInputBuffer.data, (this.pageSequenceNumber == 2 && (list.size() == 1 || list.size() == 3)) ? list.get(0) : null);
        decoderInputBuffer.clear();
        decoderInputBuffer.ensureSpaceForWrite(this.outputBuffer.remaining());
        decoderInputBuffer.data.put(this.outputBuffer);
        decoderInputBuffer.flip();
    }

    public void reset() {
        this.outputBuffer = AudioProcessor.EMPTY_BUFFER;
        this.granulePosition = 0;
        this.pageSequenceNumber = 2;
    }
}
