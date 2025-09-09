package androidx.media3.exoplayer.source.chunk;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSourceUtil;
import androidx.media3.datasource.DataSpec;
import java.io.IOException;
import java.util.Arrays;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class DataChunk extends Chunk {
    private static final int READ_GRANULARITY = 16384;
    private byte[] data;
    private volatile boolean loadCanceled;

    public DataChunk(DataSource dataSource, DataSpec dataSpec, int i2, Format format, int i3, @Nullable Object obj, @Nullable byte[] bArr) {
        DataChunk dataChunk;
        byte[] bArr2;
        super(dataSource, dataSpec, i2, format, i3, obj, C.TIME_UNSET, C.TIME_UNSET);
        if (bArr == null) {
            bArr2 = Util.EMPTY_BYTE_ARRAY;
            dataChunk = this;
        } else {
            dataChunk = this;
            bArr2 = bArr;
        }
        dataChunk.data = bArr2;
    }

    private void maybeExpandData(int i2) {
        byte[] bArr = this.data;
        if (bArr.length < i2 + 16384) {
            this.data = Arrays.copyOf(bArr, bArr.length + 16384);
        }
    }

    protected abstract void a(byte[] bArr, int i2);

    @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
    public final void cancelLoad() {
        this.loadCanceled = true;
    }

    public byte[] getDataHolder() {
        return this.data;
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
    public final void load() throws IOException {
        try {
            this.f5439a.open(this.dataSpec);
            int i2 = 0;
            int i3 = 0;
            while (i2 != -1 && !this.loadCanceled) {
                maybeExpandData(i3);
                i2 = this.f5439a.read(this.data, i3, 16384);
                if (i2 != -1) {
                    i3 += i2;
                }
            }
            if (!this.loadCanceled) {
                a(this.data, i3);
            }
            DataSourceUtil.closeQuietly(this.f5439a);
        } catch (Throwable th) {
            DataSourceUtil.closeQuietly(this.f5439a);
            throw th;
        }
    }
}
