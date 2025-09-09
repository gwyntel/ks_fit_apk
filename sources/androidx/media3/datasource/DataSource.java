package androidx.media3.datasource;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.DataReader;
import androidx.media3.common.util.UnstableApi;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface DataSource extends DataReader {

    public interface Factory {
        @UnstableApi
        DataSource createDataSource();
    }

    @UnstableApi
    void addTransferListener(TransferListener transferListener);

    @UnstableApi
    void close() throws IOException;

    @UnstableApi
    Map<String, List<String>> getResponseHeaders();

    @Nullable
    @UnstableApi
    Uri getUri();

    @UnstableApi
    long open(DataSpec dataSpec) throws IOException;
}
