package androidx.media3.datasource;

import androidx.annotation.Nullable;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import com.google.common.base.Ascii;
import com.google.common.base.Predicate;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface HttpDataSource extends DataSource {

    @UnstableApi
    public static final Predicate<String> REJECT_PAYWALL_TYPES = new Predicate() { // from class: androidx.media3.datasource.j
        @Override // com.google.common.base.Predicate
        public final boolean apply(Object obj) {
            return k.a((String) obj);
        }
    };

    @UnstableApi
    public static abstract class BaseFactory implements Factory {
        private final RequestProperties defaultRequestProperties = new RequestProperties();

        protected abstract HttpDataSource a(RequestProperties requestProperties);

        @Override // androidx.media3.datasource.HttpDataSource.Factory
        @CanIgnoreReturnValue
        public final Factory setDefaultRequestProperties(Map<String, String> map) {
            this.defaultRequestProperties.clearAndSet(map);
            return this;
        }

        @Override // androidx.media3.datasource.HttpDataSource.Factory, androidx.media3.datasource.DataSource.Factory
        public final HttpDataSource createDataSource() {
            return a(this.defaultRequestProperties);
        }
    }

    public static final class CleartextNotPermittedException extends HttpDataSourceException {
        @UnstableApi
        public CleartextNotPermittedException(IOException iOException, DataSpec dataSpec) {
            super("Cleartext HTTP traffic not permitted. See https://developer.android.com/guide/topics/media/issues/cleartext-not-permitted", iOException, dataSpec, PlaybackException.ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED, 1);
        }
    }

    public interface Factory extends DataSource.Factory {
        @Override // androidx.media3.datasource.DataSource.Factory
        @UnstableApi
        /* bridge */ /* synthetic */ DataSource createDataSource();

        @Override // androidx.media3.datasource.DataSource.Factory
        @UnstableApi
        HttpDataSource createDataSource();

        @UnstableApi
        Factory setDefaultRequestProperties(Map<String, String> map);
    }

    public static class HttpDataSourceException extends DataSourceException {
        public static final int TYPE_CLOSE = 3;
        public static final int TYPE_OPEN = 1;
        public static final int TYPE_READ = 2;

        @UnstableApi
        public final DataSpec dataSpec;
        public final int type;

        @Target({ElementType.TYPE_USE})
        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        @UnstableApi
        @Deprecated
        public HttpDataSourceException(DataSpec dataSpec, int i2) {
            this(dataSpec, 2000, i2);
        }

        private static int assignErrorCode(int i2, int i3) {
            if (i2 == 2000 && i3 == 1) {
                return 2001;
            }
            return i2;
        }

        @UnstableApi
        public static HttpDataSourceException createForIOException(IOException iOException, DataSpec dataSpec, int i2) {
            String message = iOException.getMessage();
            int i3 = iOException instanceof SocketTimeoutException ? 2002 : iOException instanceof InterruptedIOException ? 1004 : (message == null || !Ascii.toLowerCase(message).matches("cleartext.*not permitted.*")) ? 2001 : 2007;
            return i3 == 2007 ? new CleartextNotPermittedException(iOException, dataSpec) : new HttpDataSourceException(iOException, dataSpec, i3, i2);
        }

        @UnstableApi
        public HttpDataSourceException(DataSpec dataSpec, int i2, int i3) {
            super(assignErrorCode(i2, i3));
            this.dataSpec = dataSpec;
            this.type = i3;
        }

        @UnstableApi
        @Deprecated
        public HttpDataSourceException(String str, DataSpec dataSpec, int i2) {
            this(str, dataSpec, 2000, i2);
        }

        @UnstableApi
        public HttpDataSourceException(String str, DataSpec dataSpec, int i2, int i3) {
            super(str, assignErrorCode(i2, i3));
            this.dataSpec = dataSpec;
            this.type = i3;
        }

        @UnstableApi
        @Deprecated
        public HttpDataSourceException(IOException iOException, DataSpec dataSpec, int i2) {
            this(iOException, dataSpec, 2000, i2);
        }

        @UnstableApi
        public HttpDataSourceException(IOException iOException, DataSpec dataSpec, int i2, int i3) {
            super(iOException, assignErrorCode(i2, i3));
            this.dataSpec = dataSpec;
            this.type = i3;
        }

        @UnstableApi
        @Deprecated
        public HttpDataSourceException(String str, IOException iOException, DataSpec dataSpec, int i2) {
            this(str, iOException, dataSpec, 2000, i2);
        }

        @UnstableApi
        public HttpDataSourceException(String str, @Nullable IOException iOException, DataSpec dataSpec, int i2, int i3) {
            super(str, iOException, assignErrorCode(i2, i3));
            this.dataSpec = dataSpec;
            this.type = i3;
        }
    }

    public static final class InvalidContentTypeException extends HttpDataSourceException {
        public final String contentType;

        @UnstableApi
        public InvalidContentTypeException(String str, DataSpec dataSpec) {
            super("Invalid content type: " + str, dataSpec, 2003, 1);
            this.contentType = str;
        }
    }

    public static final class InvalidResponseCodeException extends HttpDataSourceException {

        @UnstableApi
        public final Map<String, List<String>> headerFields;
        public final byte[] responseBody;
        public final int responseCode;

        @Nullable
        public final String responseMessage;

        @UnstableApi
        public InvalidResponseCodeException(int i2, @Nullable String str, @Nullable IOException iOException, Map<String, List<String>> map, DataSpec dataSpec, byte[] bArr) {
            super("Response code: " + i2, iOException, dataSpec, 2004, 1);
            this.responseCode = i2;
            this.responseMessage = str;
            this.headerFields = map;
            this.responseBody = bArr;
        }
    }

    @UnstableApi
    void clearAllRequestProperties();

    @UnstableApi
    void clearRequestProperty(String str);

    @Override // androidx.media3.datasource.DataSource
    @UnstableApi
    void close() throws HttpDataSourceException;

    @UnstableApi
    int getResponseCode();

    @Override // androidx.media3.datasource.DataSource
    @UnstableApi
    Map<String, List<String>> getResponseHeaders();

    @Override // androidx.media3.datasource.DataSource
    @UnstableApi
    long open(DataSpec dataSpec) throws HttpDataSourceException;

    @Override // androidx.media3.common.DataReader
    @UnstableApi
    int read(byte[] bArr, int i2, int i3) throws HttpDataSourceException;

    @UnstableApi
    void setRequestProperty(String str, String str2);

    @UnstableApi
    public static final class RequestProperties {
        private final Map<String, String> requestProperties = new HashMap();

        @Nullable
        private Map<String, String> requestPropertiesSnapshot;

        public synchronized void clear() {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.clear();
        }

        public synchronized void clearAndSet(Map<String, String> map) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.clear();
            this.requestProperties.putAll(map);
        }

        public synchronized Map<String, String> getSnapshot() {
            try {
                if (this.requestPropertiesSnapshot == null) {
                    this.requestPropertiesSnapshot = Collections.unmodifiableMap(new HashMap(this.requestProperties));
                }
            } catch (Throwable th) {
                throw th;
            }
            return this.requestPropertiesSnapshot;
        }

        public synchronized void remove(String str) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.remove(str);
        }

        public synchronized void set(String str, String str2) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.put(str, str2);
        }

        public synchronized void set(Map<String, String> map) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.putAll(map);
        }
    }
}
