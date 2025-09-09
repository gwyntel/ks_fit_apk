package androidx.media3.datasource;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import java.io.IOException;

@UnstableApi
/* loaded from: classes.dex */
public final class ByteArrayDataSource extends BaseDataSource {
    private int bytesRemaining;

    @Nullable
    private byte[] data;
    private boolean opened;
    private int readPosition;

    @Nullable
    private Uri uri;
    private final UriResolver uriResolver;

    public interface UriResolver {
        byte[] resolve(Uri uri) throws IOException;
    }

    public ByteArrayDataSource(final byte[] bArr) {
        this(new UriResolver() { // from class: androidx.media3.datasource.a
            @Override // androidx.media3.datasource.ByteArrayDataSource.UriResolver
            public final byte[] resolve(Uri uri) {
                return ByteArrayDataSource.lambda$new$0(bArr, uri);
            }
        });
        Assertions.checkArgument(bArr.length > 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ byte[] lambda$new$0(byte[] bArr, Uri uri) throws IOException {
        return bArr;
    }

    @Override // androidx.media3.datasource.DataSource
    public void close() {
        if (this.opened) {
            this.opened = false;
            b();
        }
        this.uri = null;
        this.data = null;
    }

    @Override // androidx.media3.datasource.DataSource
    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    @Override // androidx.media3.datasource.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        c(dataSpec);
        Uri uri = dataSpec.uri;
        this.uri = uri;
        byte[] bArrResolve = this.uriResolver.resolve(uri);
        this.data = bArrResolve;
        long j2 = dataSpec.position;
        if (j2 > bArrResolve.length) {
            throw new DataSourceException(2008);
        }
        this.readPosition = (int) j2;
        int length = bArrResolve.length - ((int) j2);
        this.bytesRemaining = length;
        long j3 = dataSpec.length;
        if (j3 != -1) {
            this.bytesRemaining = (int) Math.min(length, j3);
        }
        this.opened = true;
        d(dataSpec);
        long j4 = dataSpec.length;
        return j4 != -1 ? j4 : this.bytesRemaining;
    }

    @Override // androidx.media3.common.DataReader
    public int read(byte[] bArr, int i2, int i3) {
        if (i3 == 0) {
            return 0;
        }
        int i4 = this.bytesRemaining;
        if (i4 == 0) {
            return -1;
        }
        int iMin = Math.min(i3, i4);
        System.arraycopy(Assertions.checkStateNotNull(this.data), this.readPosition, bArr, i2, iMin);
        this.readPosition += iMin;
        this.bytesRemaining -= iMin;
        a(iMin);
        return iMin;
    }

    public ByteArrayDataSource(UriResolver uriResolver) {
        super(false);
        this.uriResolver = (UriResolver) Assertions.checkNotNull(uriResolver);
    }
}
