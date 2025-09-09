package androidx.media3.datasource;

import android.net.Uri;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.text.TextUtils;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@UnstableApi
/* loaded from: classes.dex */
public final class FileDataSource extends BaseDataSource {
    private long bytesRemaining;

    @Nullable
    private RandomAccessFile file;
    private boolean opened;

    @Nullable
    private Uri uri;

    @RequiresApi(21)
    private static final class Api21 {
        private Api21() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        @DoNotInline
        public static boolean isPermissionError(@Nullable Throwable th) {
            return (th instanceof ErrnoException) && ((ErrnoException) th).errno == OsConstants.EACCES;
        }
    }

    public static final class Factory implements DataSource.Factory {

        @Nullable
        private TransferListener listener;

        @CanIgnoreReturnValue
        public Factory setListener(@Nullable TransferListener transferListener) {
            this.listener = transferListener;
            return this;
        }

        @Override // androidx.media3.datasource.DataSource.Factory
        public FileDataSource createDataSource() {
            FileDataSource fileDataSource = new FileDataSource();
            TransferListener transferListener = this.listener;
            if (transferListener != null) {
                fileDataSource.addTransferListener(transferListener);
            }
            return fileDataSource;
        }
    }

    public static class FileDataSourceException extends DataSourceException {
        @Deprecated
        public FileDataSourceException(Exception exc) {
            super(exc, 2000);
        }

        @Deprecated
        public FileDataSourceException(String str, IOException iOException) {
            super(str, iOException, 2000);
        }

        public FileDataSourceException(Throwable th, int i2) {
            super(th, i2);
        }

        public FileDataSourceException(@Nullable String str, @Nullable Throwable th, int i2) {
            super(str, th, i2);
        }
    }

    public FileDataSource() {
        super(false);
    }

    private static RandomAccessFile openLocalFile(Uri uri) throws FileDataSourceException {
        int i2 = PlaybackException.ERROR_CODE_IO_NO_PERMISSION;
        try {
            return new RandomAccessFile((String) Assertions.checkNotNull(uri.getPath()), "r");
        } catch (FileNotFoundException e2) {
            if (!TextUtils.isEmpty(uri.getQuery()) || !TextUtils.isEmpty(uri.getFragment())) {
                throw new FileDataSourceException(String.format("uri has query and/or fragment, which are not supported. Did you call Uri.parse() on a string containing '?' or '#'? Use Uri.fromFile(new File(path)) to avoid this. path=%s,query=%s,fragment=%s", uri.getPath(), uri.getQuery(), uri.getFragment()), e2, 1004);
            }
            if (Util.SDK_INT < 21 || !Api21.isPermissionError(e2.getCause())) {
                i2 = 2005;
            }
            throw new FileDataSourceException(e2, i2);
        } catch (SecurityException e3) {
            throw new FileDataSourceException(e3, PlaybackException.ERROR_CODE_IO_NO_PERMISSION);
        } catch (RuntimeException e4) {
            throw new FileDataSourceException(e4, 2000);
        }
    }

    @Override // androidx.media3.datasource.DataSource
    public void close() throws FileDataSourceException {
        this.uri = null;
        try {
            try {
                RandomAccessFile randomAccessFile = this.file;
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e2) {
                throw new FileDataSourceException(e2, 2000);
            }
        } finally {
            this.file = null;
            if (this.opened) {
                this.opened = false;
                b();
            }
        }
    }

    @Override // androidx.media3.datasource.DataSource
    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    @Override // androidx.media3.datasource.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        Uri uri = dataSpec.uri;
        this.uri = uri;
        c(dataSpec);
        RandomAccessFile randomAccessFileOpenLocalFile = openLocalFile(uri);
        this.file = randomAccessFileOpenLocalFile;
        try {
            randomAccessFileOpenLocalFile.seek(dataSpec.position);
            long length = dataSpec.length;
            if (length == -1) {
                length = this.file.length() - dataSpec.position;
            }
            this.bytesRemaining = length;
            if (length < 0) {
                throw new FileDataSourceException(null, null, 2008);
            }
            this.opened = true;
            d(dataSpec);
            return this.bytesRemaining;
        } catch (IOException e2) {
            throw new FileDataSourceException(e2, 2000);
        }
    }

    @Override // androidx.media3.common.DataReader
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 == 0) {
            return 0;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        try {
            int i4 = ((RandomAccessFile) Util.castNonNull(this.file)).read(bArr, i2, (int) Math.min(this.bytesRemaining, i3));
            if (i4 > 0) {
                this.bytesRemaining -= i4;
                a(i4);
            }
            return i4;
        } catch (IOException e2) {
            throw new FileDataSourceException(e2, 2000);
        }
    }
}
