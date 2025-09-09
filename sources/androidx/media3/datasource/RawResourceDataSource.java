package androidx.media3.datasource;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public final class RawResourceDataSource extends BaseDataSource {

    @Deprecated
    public static final String RAW_RESOURCE_SCHEME = "rawresource";
    private final Context applicationContext;

    @Nullable
    private AssetFileDescriptor assetFileDescriptor;
    private long bytesRemaining;

    @Nullable
    private DataSpec dataSpec;

    @Nullable
    private InputStream inputStream;
    private boolean opened;

    public static class RawResourceDataSourceException extends DataSourceException {
        @Deprecated
        public RawResourceDataSourceException(String str) {
            super(str, null, 2000);
        }

        @Deprecated
        public RawResourceDataSourceException(Throwable th) {
            super(th, 2000);
        }

        public RawResourceDataSourceException(@Nullable String str, @Nullable Throwable th, int i2) {
            super(str, th, i2);
        }
    }

    public RawResourceDataSource(Context context) {
        super(false);
        this.applicationContext = context.getApplicationContext();
    }

    @Deprecated
    public static Uri buildRawResourceUri(int i2) {
        return Uri.parse("rawresource:///" + i2);
    }

    private static AssetFileDescriptor openAssetFileDescriptor(Context context, DataSpec dataSpec) throws Resources.NotFoundException, PackageManager.NameNotFoundException, RawResourceDataSourceException {
        Resources resourcesForApplication;
        int identifier;
        Uri uriNormalizeScheme = dataSpec.uri.normalizeScheme();
        if (TextUtils.equals(RAW_RESOURCE_SCHEME, uriNormalizeScheme.getScheme())) {
            resourcesForApplication = context.getResources();
            List<String> pathSegments = uriNormalizeScheme.getPathSegments();
            if (pathSegments.size() != 1) {
                throw new RawResourceDataSourceException("rawresource:// URI must have exactly one path element, found " + pathSegments.size());
            }
            identifier = parseResourceId(pathSegments.get(0));
        } else {
            if (!TextUtils.equals("android.resource", uriNormalizeScheme.getScheme())) {
                throw new RawResourceDataSourceException("Unsupported URI scheme (" + uriNormalizeScheme.getScheme() + "). Only android.resource is supported.", null, 1004);
            }
            String strSubstring = (String) Assertions.checkNotNull(uriNormalizeScheme.getPath());
            if (strSubstring.startsWith("/")) {
                strSubstring = strSubstring.substring(1);
            }
            String packageName = TextUtils.isEmpty(uriNormalizeScheme.getHost()) ? context.getPackageName() : uriNormalizeScheme.getHost();
            if (packageName.equals(context.getPackageName())) {
                resourcesForApplication = context.getResources();
            } else {
                try {
                    resourcesForApplication = context.getPackageManager().getResourcesForApplication(packageName);
                } catch (PackageManager.NameNotFoundException e2) {
                    throw new RawResourceDataSourceException("Package in android.resource:// URI not found. Check http://g.co/dev/packagevisibility.", e2, 2005);
                }
            }
            if (strSubstring.matches("\\d+")) {
                identifier = parseResourceId(strSubstring);
            } else {
                identifier = resourcesForApplication.getIdentifier(packageName + ":" + strSubstring, "raw", null);
                if (identifier == 0) {
                    throw new RawResourceDataSourceException("Resource not found.", null, 2005);
                }
            }
        }
        try {
            AssetFileDescriptor assetFileDescriptorOpenRawResourceFd = resourcesForApplication.openRawResourceFd(identifier);
            if (assetFileDescriptorOpenRawResourceFd != null) {
                return assetFileDescriptorOpenRawResourceFd;
            }
            throw new RawResourceDataSourceException("Resource is compressed: " + uriNormalizeScheme, null, 2000);
        } catch (Resources.NotFoundException e3) {
            throw new RawResourceDataSourceException(null, e3, 2005);
        }
    }

    private static int parseResourceId(String str) throws RawResourceDataSourceException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            throw new RawResourceDataSourceException("Resource identifier must be an integer.", null, 1004);
        }
    }

    @Override // androidx.media3.datasource.DataSource
    public void close() throws RawResourceDataSourceException {
        this.dataSpec = null;
        try {
            try {
                InputStream inputStream = this.inputStream;
                if (inputStream != null) {
                    inputStream.close();
                }
                this.inputStream = null;
                try {
                    try {
                        AssetFileDescriptor assetFileDescriptor = this.assetFileDescriptor;
                        if (assetFileDescriptor != null) {
                            assetFileDescriptor.close();
                        }
                    } catch (IOException e2) {
                        throw new RawResourceDataSourceException(null, e2, 2000);
                    }
                } finally {
                    this.assetFileDescriptor = null;
                    if (this.opened) {
                        this.opened = false;
                        b();
                    }
                }
            } catch (Throwable th) {
                this.inputStream = null;
                try {
                    try {
                        AssetFileDescriptor assetFileDescriptor2 = this.assetFileDescriptor;
                        if (assetFileDescriptor2 != null) {
                            assetFileDescriptor2.close();
                        }
                        this.assetFileDescriptor = null;
                        if (this.opened) {
                            this.opened = false;
                            b();
                        }
                        throw th;
                    } finally {
                        this.assetFileDescriptor = null;
                        if (this.opened) {
                            this.opened = false;
                            b();
                        }
                    }
                } catch (IOException e3) {
                    throw new RawResourceDataSourceException(null, e3, 2000);
                }
            }
        } catch (IOException e4) {
            throw new RawResourceDataSourceException(null, e4, 2000);
        }
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
    public long open(DataSpec dataSpec) throws DataSourceException, Resources.NotFoundException, PackageManager.NameNotFoundException {
        this.dataSpec = dataSpec;
        c(dataSpec);
        AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = openAssetFileDescriptor(this.applicationContext, dataSpec);
        this.assetFileDescriptor = assetFileDescriptorOpenAssetFileDescriptor;
        long length = assetFileDescriptorOpenAssetFileDescriptor.getLength();
        FileInputStream fileInputStream = new FileInputStream(this.assetFileDescriptor.getFileDescriptor());
        this.inputStream = fileInputStream;
        if (length != -1) {
            try {
                if (dataSpec.position > length) {
                    throw new RawResourceDataSourceException(null, null, 2008);
                }
            } catch (RawResourceDataSourceException e2) {
                throw e2;
            } catch (IOException e3) {
                throw new RawResourceDataSourceException(null, e3, 2000);
            }
        }
        long startOffset = this.assetFileDescriptor.getStartOffset();
        long jSkip = fileInputStream.skip(dataSpec.position + startOffset) - startOffset;
        if (jSkip != dataSpec.position) {
            throw new RawResourceDataSourceException(null, null, 2008);
        }
        if (length == -1) {
            FileChannel channel = fileInputStream.getChannel();
            if (channel.size() == 0) {
                this.bytesRemaining = -1L;
            } else {
                long size = channel.size() - channel.position();
                this.bytesRemaining = size;
                if (size < 0) {
                    throw new RawResourceDataSourceException(null, null, 2008);
                }
            }
        } else {
            long j2 = length - jSkip;
            this.bytesRemaining = j2;
            if (j2 < 0) {
                throw new DataSourceException(2008);
            }
        }
        long jMin = dataSpec.length;
        if (jMin != -1) {
            long j3 = this.bytesRemaining;
            if (j3 != -1) {
                jMin = Math.min(j3, jMin);
            }
            this.bytesRemaining = jMin;
        }
        this.opened = true;
        d(dataSpec);
        long j4 = dataSpec.length;
        return j4 != -1 ? j4 : this.bytesRemaining;
    }

    @Override // androidx.media3.common.DataReader
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 == 0) {
            return 0;
        }
        long j2 = this.bytesRemaining;
        if (j2 == 0) {
            return -1;
        }
        if (j2 != -1) {
            try {
                i3 = (int) Math.min(j2, i3);
            } catch (IOException e2) {
                throw new RawResourceDataSourceException(null, e2, 2000);
            }
        }
        int i4 = ((InputStream) Util.castNonNull(this.inputStream)).read(bArr, i2, i3);
        if (i4 == -1) {
            if (this.bytesRemaining == -1) {
                return -1;
            }
            throw new RawResourceDataSourceException("End of stream reached having not read sufficient data.", new EOFException(), 2000);
        }
        long j3 = this.bytesRemaining;
        if (j3 != -1) {
            this.bytesRemaining = j3 - i4;
        }
        a(i4);
        return i4;
    }
}
