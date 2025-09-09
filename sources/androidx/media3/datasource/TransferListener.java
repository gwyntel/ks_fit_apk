package androidx.media3.datasource;

import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes.dex */
public interface TransferListener {
    void onBytesTransferred(DataSource dataSource, DataSpec dataSpec, boolean z2, int i2);

    void onTransferEnd(DataSource dataSource, DataSpec dataSpec, boolean z2);

    void onTransferInitializing(DataSource dataSource, DataSpec dataSpec, boolean z2);

    void onTransferStart(DataSource dataSource, DataSpec dataSpec, boolean z2);
}
