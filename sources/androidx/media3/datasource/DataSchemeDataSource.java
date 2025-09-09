package androidx.media3.datasource;

import android.net.Uri;
import android.util.Base64;
import androidx.annotation.Nullable;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.net.URLDecoder;

@UnstableApi
/* loaded from: classes.dex */
public final class DataSchemeDataSource extends BaseDataSource {
    public static final String SCHEME_DATA = "data";
    private int bytesRemaining;

    @Nullable
    private byte[] data;

    @Nullable
    private DataSpec dataSpec;
    private int readPosition;

    public DataSchemeDataSource() {
        super(false);
    }

    @Override // androidx.media3.datasource.DataSource
    public void close() {
        if (this.data != null) {
            this.data = null;
            b();
        }
        this.dataSpec = null;
    }

    @Override // androidx.media3.datasource.DataSource
    @Nullable
    public Uri getUri() {
        DataSpec dataSpec = this.dataSpec;
        if (dataSpec != null) {
            return dataSpec.uri;
        }
        return null;
    }

    @Override // androidx.media3.datasource.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        c(dataSpec);
        this.dataSpec = dataSpec;
        Uri uriNormalizeScheme = dataSpec.uri.normalizeScheme();
        String scheme = uriNormalizeScheme.getScheme();
        Assertions.checkArgument("data".equals(scheme), "Unsupported scheme: " + scheme);
        String[] strArrSplit = Util.split(uriNormalizeScheme.getSchemeSpecificPart(), ",");
        if (strArrSplit.length != 2) {
            throw ParserException.createForMalformedDataOfUnknownType("Unexpected URI format: " + uriNormalizeScheme, null);
        }
        String str = strArrSplit[1];
        if (strArrSplit[0].contains(";base64")) {
            try {
                this.data = Base64.decode(str, 0);
            } catch (IllegalArgumentException e2) {
                throw ParserException.createForMalformedDataOfUnknownType("Error while parsing Base64 encoded string: " + str, e2);
            }
        } else {
            this.data = Util.getUtf8Bytes(URLDecoder.decode(str, Charsets.US_ASCII.name()));
        }
        long j2 = dataSpec.position;
        byte[] bArr = this.data;
        if (j2 > bArr.length) {
            this.data = null;
            throw new DataSourceException(2008);
        }
        int i2 = (int) j2;
        this.readPosition = i2;
        int length = bArr.length - i2;
        this.bytesRemaining = length;
        long j3 = dataSpec.length;
        if (j3 != -1) {
            this.bytesRemaining = (int) Math.min(length, j3);
        }
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
        System.arraycopy(Util.castNonNull(this.data), this.readPosition, bArr, i2, iMin);
        this.readPosition += iMin;
        this.bytesRemaining -= iMin;
        a(iMin);
        return iMin;
    }
}
