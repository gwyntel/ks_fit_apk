package androidx.media3.datasource;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaLibraryInfo;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import anet.channel.request.Request;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UnstableApi
/* loaded from: classes.dex */
public final class DataSpec {
    public static final int FLAG_ALLOW_CACHE_FRAGMENTATION = 4;
    public static final int FLAG_ALLOW_GZIP = 1;
    public static final int FLAG_DONT_CACHE_IF_LENGTH_UNKNOWN = 2;
    public static final int FLAG_MIGHT_NOT_USE_FULL_NETWORK_SPEED = 8;
    public static final int HTTP_METHOD_GET = 1;
    public static final int HTTP_METHOD_HEAD = 3;
    public static final int HTTP_METHOD_POST = 2;

    @Deprecated
    public final long absoluteStreamPosition;

    @Nullable
    public final Object customData;
    public final int flags;

    @Nullable
    public final byte[] httpBody;
    public final int httpMethod;
    public final Map<String, String> httpRequestHeaders;

    @Nullable
    public final String key;
    public final long length;
    public final long position;
    public final Uri uri;
    public final long uriPositionOffset;

    public static final class Builder {

        @Nullable
        private Object customData;
        private int flags;

        @Nullable
        private byte[] httpBody;
        private int httpMethod;
        private Map<String, String> httpRequestHeaders;

        @Nullable
        private String key;
        private long length;
        private long position;

        @Nullable
        private Uri uri;
        private long uriPositionOffset;

        public DataSpec build() {
            Assertions.checkStateNotNull(this.uri, "The uri must be set.");
            return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position, this.length, this.key, this.flags, this.customData);
        }

        @CanIgnoreReturnValue
        public Builder setCustomData(@Nullable Object obj) {
            this.customData = obj;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setFlags(int i2) {
            this.flags = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setHttpBody(@Nullable byte[] bArr) {
            this.httpBody = bArr;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setHttpMethod(int i2) {
            this.httpMethod = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setHttpRequestHeaders(Map<String, String> map) {
            this.httpRequestHeaders = map;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setKey(@Nullable String str) {
            this.key = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setLength(long j2) {
            this.length = j2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPosition(long j2) {
            this.position = j2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUri(String str) {
            this.uri = Uri.parse(str);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUriPositionOffset(long j2) {
            this.uriPositionOffset = j2;
            return this;
        }

        public Builder() {
            this.httpMethod = 1;
            this.httpRequestHeaders = Collections.emptyMap();
            this.length = -1L;
        }

        @CanIgnoreReturnValue
        public Builder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }

        private Builder(DataSpec dataSpec) {
            this.uri = dataSpec.uri;
            this.uriPositionOffset = dataSpec.uriPositionOffset;
            this.httpMethod = dataSpec.httpMethod;
            this.httpBody = dataSpec.httpBody;
            this.httpRequestHeaders = dataSpec.httpRequestHeaders;
            this.position = dataSpec.position;
            this.length = dataSpec.length;
            this.key = dataSpec.key;
            this.flags = dataSpec.flags;
            this.customData = dataSpec.customData;
        }
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface HttpMethod {
    }

    static {
        MediaLibraryInfo.registerModule("media3.datasource");
    }

    public static String getStringForHttpMethod(int i2) {
        if (i2 == 1) {
            return "GET";
        }
        if (i2 == 2) {
            return "POST";
        }
        if (i2 == 3) {
            return Request.Method.HEAD;
        }
        throw new IllegalStateException();
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public final String getHttpMethodString() {
        return getStringForHttpMethod(this.httpMethod);
    }

    public boolean isFlagSet(int i2) {
        return (this.flags & i2) == i2;
    }

    public DataSpec subrange(long j2) {
        long j3 = this.length;
        return subrange(j2, j3 != -1 ? j3 - j2 : -1L);
    }

    public String toString() {
        return "DataSpec[" + getHttpMethodString() + " " + this.uri + ", " + this.position + ", " + this.length + ", " + this.key + ", " + this.flags + "]";
    }

    public DataSpec withAdditionalHeaders(Map<String, String> map) {
        HashMap map2 = new HashMap(this.httpRequestHeaders);
        map2.putAll(map);
        return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, map2, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec withRequestHeaders(Map<String, String> map) {
        return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, map, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec withUri(Uri uri) {
        return new DataSpec(uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec(Uri uri) {
        this(uri, 0L, -1L);
    }

    public DataSpec subrange(long j2, long j3) {
        return (j2 == 0 && this.length == j3) ? this : new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position + j2, j3, this.key, this.flags, this.customData);
    }

    public DataSpec(Uri uri, long j2, long j3) {
        this(uri, j2, j3, null);
    }

    @Deprecated
    public DataSpec(Uri uri, long j2, long j3, @Nullable String str) {
        this(uri, 0L, 1, null, Collections.emptyMap(), j2, j3, str, 0, null);
    }

    private DataSpec(Uri uri, long j2, int i2, @Nullable byte[] bArr, Map<String, String> map, long j3, long j4, @Nullable String str, int i3, @Nullable Object obj) {
        byte[] bArr2 = bArr;
        long j5 = j2 + j3;
        Assertions.checkArgument(j5 >= 0);
        Assertions.checkArgument(j3 >= 0);
        Assertions.checkArgument(j4 > 0 || j4 == -1);
        this.uri = (Uri) Assertions.checkNotNull(uri);
        this.uriPositionOffset = j2;
        this.httpMethod = i2;
        this.httpBody = (bArr2 == null || bArr2.length == 0) ? null : bArr2;
        this.httpRequestHeaders = Collections.unmodifiableMap(new HashMap(map));
        this.position = j3;
        this.absoluteStreamPosition = j5;
        this.length = j4;
        this.key = str;
        this.flags = i3;
        this.customData = obj;
    }
}
