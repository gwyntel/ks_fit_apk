package androidx.media3.common;

import androidx.media3.common.util.UnstableApi;
import java.io.IOException;

@UnstableApi
/* loaded from: classes.dex */
public interface DataReader {
    int read(byte[] bArr, int i2, int i3) throws IOException;
}
