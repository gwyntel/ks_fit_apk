package androidx.media3.extractor;

import androidx.media3.common.DataReader;
import androidx.media3.common.util.UnstableApi;
import java.io.IOException;

@UnstableApi
/* loaded from: classes2.dex */
public interface ExtractorInput extends DataReader {
    void advancePeekPosition(int i2) throws IOException;

    boolean advancePeekPosition(int i2, boolean z2) throws IOException;

    long getLength();

    long getPeekPosition();

    long getPosition();

    int peek(byte[] bArr, int i2, int i3) throws IOException;

    void peekFully(byte[] bArr, int i2, int i3) throws IOException;

    boolean peekFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException;

    @Override // androidx.media3.common.DataReader
    int read(byte[] bArr, int i2, int i3) throws IOException;

    void readFully(byte[] bArr, int i2, int i3) throws IOException;

    boolean readFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException;

    void resetPeekPosition();

    <E extends Throwable> void setRetryPosition(long j2, E e2) throws Throwable;

    int skip(int i2) throws IOException;

    void skipFully(int i2) throws IOException;

    boolean skipFully(int i2, boolean z2) throws IOException;
}
