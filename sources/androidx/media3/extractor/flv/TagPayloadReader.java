package androidx.media3.extractor.flv;

import androidx.media3.common.ParserException;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.extractor.TrackOutput;

/* loaded from: classes2.dex */
abstract class TagPayloadReader {

    /* renamed from: a, reason: collision with root package name */
    protected final TrackOutput f5609a;

    public static final class UnsupportedFormatException extends ParserException {
        public UnsupportedFormatException(String str) {
            super(str, null, false, 1);
        }
    }

    protected TagPayloadReader(TrackOutput trackOutput) {
        this.f5609a = trackOutput;
    }

    protected abstract boolean a(ParsableByteArray parsableByteArray);

    protected abstract boolean b(ParsableByteArray parsableByteArray, long j2);

    public final boolean consume(ParsableByteArray parsableByteArray, long j2) throws ParserException {
        return a(parsableByteArray) && b(parsableByteArray, j2);
    }

    public abstract void seek();
}
