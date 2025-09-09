package androidx.media3.exoplayer.rtsp;

import androidx.annotation.Nullable;
import androidx.media3.datasource.DataSource;
import androidx.media3.exoplayer.rtsp.RtspMessageChannel;
import java.io.IOException;

/* loaded from: classes.dex */
interface RtpDataChannel extends DataSource {

    public interface Factory {
        RtpDataChannel createAndOpenDataChannel(int i2) throws IOException;

        @Nullable
        Factory createFallbackDataChannelFactory();
    }

    @Nullable
    RtspMessageChannel.InterleavedBinaryDataListener getInterleavedBinaryDataListener();

    int getLocalPort();

    String getTransport();

    boolean needsClosingOnLoadCompletion();
}
