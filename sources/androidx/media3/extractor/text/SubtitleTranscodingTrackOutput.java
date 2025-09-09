package androidx.media3.extractor.text;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.DataReader;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Consumer;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.TrackOutput;
import androidx.media3.extractor.g;
import androidx.media3.extractor.text.SubtitleParser;
import java.io.EOFException;
import java.io.IOException;

/* loaded from: classes2.dex */
final class SubtitleTranscodingTrackOutput implements TrackOutput {
    private Format currentFormat;

    @Nullable
    private SubtitleParser currentSubtitleParser;
    private final TrackOutput delegate;
    private final SubtitleParser.Factory subtitleParserFactory;
    private final CueEncoder cueEncoder = new CueEncoder();
    private int sampleDataStart = 0;
    private int sampleDataEnd = 0;
    private byte[] sampleData = Util.EMPTY_BYTE_ARRAY;
    private final ParsableByteArray parsableScratch = new ParsableByteArray();

    public SubtitleTranscodingTrackOutput(TrackOutput trackOutput, SubtitleParser.Factory factory) {
        this.delegate = trackOutput;
        this.subtitleParserFactory = factory;
    }

    private void ensureSampleDataCapacity(int i2) {
        int length = this.sampleData.length;
        int i3 = this.sampleDataEnd;
        if (length - i3 >= i2) {
            return;
        }
        int i4 = i3 - this.sampleDataStart;
        int iMax = Math.max(i4 * 2, i2 + i4);
        byte[] bArr = this.sampleData;
        byte[] bArr2 = iMax <= bArr.length ? bArr : new byte[iMax];
        System.arraycopy(bArr, this.sampleDataStart, bArr2, 0, i4);
        this.sampleDataStart = 0;
        this.sampleDataEnd = i4;
        this.sampleData = bArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: outputSample, reason: merged with bridge method [inline-methods] */
    public void lambda$sampleMetadata$0(CuesWithTiming cuesWithTiming, long j2, int i2) {
        Assertions.checkStateNotNull(this.currentFormat);
        byte[] bArrEncode = this.cueEncoder.encode(cuesWithTiming.cues, cuesWithTiming.durationUs);
        this.parsableScratch.reset(bArrEncode);
        this.delegate.sampleData(this.parsableScratch, bArrEncode.length);
        long j3 = cuesWithTiming.startTimeUs;
        if (j3 == C.TIME_UNSET) {
            Assertions.checkState(this.currentFormat.subsampleOffsetUs == Long.MAX_VALUE);
        } else {
            long j4 = this.currentFormat.subsampleOffsetUs;
            j2 = j4 == Long.MAX_VALUE ? j2 + j3 : j3 + j4;
        }
        this.delegate.sampleMetadata(j2, i2, bArrEncode.length, 0, null);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public void format(Format format) {
        Assertions.checkNotNull(format.sampleMimeType);
        Assertions.checkArgument(MimeTypes.getTrackType(format.sampleMimeType) == 3);
        if (!format.equals(this.currentFormat)) {
            this.currentFormat = format;
            this.currentSubtitleParser = this.subtitleParserFactory.supportsFormat(format) ? this.subtitleParserFactory.create(format) : null;
        }
        if (this.currentSubtitleParser == null) {
            this.delegate.format(format);
        } else {
            this.delegate.format(format.buildUpon().setSampleMimeType(MimeTypes.APPLICATION_MEDIA3_CUES).setCodecs(format.sampleMimeType).setSubsampleOffsetUs(Long.MAX_VALUE).setCueReplacementBehavior(this.subtitleParserFactory.getCueReplacementBehavior(format)).build());
        }
    }

    public void resetSubtitleParser() {
        SubtitleParser subtitleParser = this.currentSubtitleParser;
        if (subtitleParser != null) {
            subtitleParser.reset();
        }
    }

    @Override // androidx.media3.extractor.TrackOutput
    public /* synthetic */ int sampleData(DataReader dataReader, int i2, boolean z2) {
        return g.a(this, dataReader, i2, z2);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public void sampleMetadata(final long j2, final int i2, int i3, int i4, @Nullable TrackOutput.CryptoData cryptoData) {
        if (this.currentSubtitleParser == null) {
            this.delegate.sampleMetadata(j2, i2, i3, i4, cryptoData);
            return;
        }
        Assertions.checkArgument(cryptoData == null, "DRM on subtitles is not supported");
        int i5 = (this.sampleDataEnd - i4) - i3;
        this.currentSubtitleParser.parse(this.sampleData, i5, i3, SubtitleParser.OutputOptions.allCues(), new Consumer() { // from class: androidx.media3.extractor.text.f
            @Override // androidx.media3.common.util.Consumer
            public final void accept(Object obj) {
                this.f5625a.lambda$sampleMetadata$0(j2, i2, (CuesWithTiming) obj);
            }
        });
        int i6 = i5 + i3;
        this.sampleDataStart = i6;
        if (i6 == this.sampleDataEnd) {
            this.sampleDataStart = 0;
            this.sampleDataEnd = 0;
        }
    }

    @Override // androidx.media3.extractor.TrackOutput
    public /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i2) {
        g.b(this, parsableByteArray, i2);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public int sampleData(DataReader dataReader, int i2, boolean z2, int i3) throws IOException {
        if (this.currentSubtitleParser == null) {
            return this.delegate.sampleData(dataReader, i2, z2, i3);
        }
        ensureSampleDataCapacity(i2);
        int i4 = dataReader.read(this.sampleData, this.sampleDataEnd, i2);
        if (i4 != -1) {
            this.sampleDataEnd += i4;
            return i4;
        }
        if (z2) {
            return -1;
        }
        throw new EOFException();
    }

    @Override // androidx.media3.extractor.TrackOutput
    public void sampleData(ParsableByteArray parsableByteArray, int i2, int i3) {
        if (this.currentSubtitleParser == null) {
            this.delegate.sampleData(parsableByteArray, i2, i3);
            return;
        }
        ensureSampleDataCapacity(i2);
        parsableByteArray.readBytes(this.sampleData, this.sampleDataEnd, i2);
        this.sampleDataEnd += i2;
    }
}
