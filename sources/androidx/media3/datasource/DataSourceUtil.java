package androidx.media3.datasource;

import androidx.annotation.Nullable;
import androidx.media3.common.util.UnstableApi;
import java.io.IOException;
import java.util.Arrays;

@UnstableApi
/* loaded from: classes.dex */
public final class DataSourceUtil {
    private DataSourceUtil() {
    }

    public static void closeQuietly(@Nullable DataSource dataSource) {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (IOException unused) {
            }
        }
    }

    public static byte[] readExactly(DataSource dataSource, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int i4 = dataSource.read(bArr, i3, i2 - i3);
            if (i4 == -1) {
                throw new IllegalStateException("Not enough data could be read: " + i3 + " < " + i2);
            }
            i3 += i4;
        }
        return bArr;
    }

    public static byte[] readToEnd(DataSource dataSource) throws IOException {
        byte[] bArrCopyOf = new byte[1024];
        int i2 = 0;
        int i3 = 0;
        while (i2 != -1) {
            if (i3 == bArrCopyOf.length) {
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, bArrCopyOf.length * 2);
            }
            i2 = dataSource.read(bArrCopyOf, i3, bArrCopyOf.length - i3);
            if (i2 != -1) {
                i3 += i2;
            }
        }
        return Arrays.copyOf(bArrCopyOf, i3);
    }
}
