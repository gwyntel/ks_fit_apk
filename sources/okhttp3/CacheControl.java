package okhttp3;

import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public final class CacheControl {

    /* renamed from: a, reason: collision with root package name */
    String f26095a;
    private final boolean immutable;
    private final boolean isPrivate;
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean noTransform;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;
    public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
    public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();

    public static final class Builder {

        /* renamed from: a, reason: collision with root package name */
        boolean f26096a;

        /* renamed from: b, reason: collision with root package name */
        boolean f26097b;

        /* renamed from: c, reason: collision with root package name */
        int f26098c = -1;

        /* renamed from: d, reason: collision with root package name */
        int f26099d = -1;

        /* renamed from: e, reason: collision with root package name */
        int f26100e = -1;

        /* renamed from: f, reason: collision with root package name */
        boolean f26101f;

        /* renamed from: g, reason: collision with root package name */
        boolean f26102g;

        /* renamed from: h, reason: collision with root package name */
        boolean f26103h;

        public CacheControl build() {
            return new CacheControl(this);
        }

        public Builder immutable() {
            this.f26103h = true;
            return this;
        }

        public Builder maxAge(int i2, TimeUnit timeUnit) {
            if (i2 >= 0) {
                long seconds = timeUnit.toSeconds(i2);
                this.f26098c = seconds > 2147483647L ? Integer.MAX_VALUE : (int) seconds;
                return this;
            }
            throw new IllegalArgumentException("maxAge < 0: " + i2);
        }

        public Builder maxStale(int i2, TimeUnit timeUnit) {
            if (i2 >= 0) {
                long seconds = timeUnit.toSeconds(i2);
                this.f26099d = seconds > 2147483647L ? Integer.MAX_VALUE : (int) seconds;
                return this;
            }
            throw new IllegalArgumentException("maxStale < 0: " + i2);
        }

        public Builder minFresh(int i2, TimeUnit timeUnit) {
            if (i2 >= 0) {
                long seconds = timeUnit.toSeconds(i2);
                this.f26100e = seconds > 2147483647L ? Integer.MAX_VALUE : (int) seconds;
                return this;
            }
            throw new IllegalArgumentException("minFresh < 0: " + i2);
        }

        public Builder noCache() {
            this.f26096a = true;
            return this;
        }

        public Builder noStore() {
            this.f26097b = true;
            return this;
        }

        public Builder noTransform() {
            this.f26102g = true;
            return this;
        }

        public Builder onlyIfCached() {
            this.f26101f = true;
            return this;
        }
    }

    private CacheControl(boolean z2, boolean z3, int i2, int i3, boolean z4, boolean z5, boolean z6, int i4, int i5, boolean z7, boolean z8, boolean z9, @Nullable String str) {
        this.noCache = z2;
        this.noStore = z3;
        this.maxAgeSeconds = i2;
        this.sMaxAgeSeconds = i3;
        this.isPrivate = z4;
        this.isPublic = z5;
        this.mustRevalidate = z6;
        this.maxStaleSeconds = i4;
        this.minFreshSeconds = i5;
        this.onlyIfCached = z7;
        this.noTransform = z8;
        this.immutable = z9;
        this.f26095a = str;
    }

    private String headerValue() {
        StringBuilder sb = new StringBuilder();
        if (this.noCache) {
            sb.append("no-cache, ");
        }
        if (this.noStore) {
            sb.append("no-store, ");
        }
        if (this.maxAgeSeconds != -1) {
            sb.append("max-age=");
            sb.append(this.maxAgeSeconds);
            sb.append(", ");
        }
        if (this.sMaxAgeSeconds != -1) {
            sb.append("s-maxage=");
            sb.append(this.sMaxAgeSeconds);
            sb.append(", ");
        }
        if (this.isPrivate) {
            sb.append("private, ");
        }
        if (this.isPublic) {
            sb.append("public, ");
        }
        if (this.mustRevalidate) {
            sb.append("must-revalidate, ");
        }
        if (this.maxStaleSeconds != -1) {
            sb.append("max-stale=");
            sb.append(this.maxStaleSeconds);
            sb.append(", ");
        }
        if (this.minFreshSeconds != -1) {
            sb.append("min-fresh=");
            sb.append(this.minFreshSeconds);
            sb.append(", ");
        }
        if (this.onlyIfCached) {
            sb.append("only-if-cached, ");
        }
        if (this.noTransform) {
            sb.append("no-transform, ");
        }
        if (this.immutable) {
            sb.append("immutable, ");
        }
        if (sb.length() == 0) {
            return "";
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static okhttp3.CacheControl parse(okhttp3.Headers r22) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.CacheControl.parse(okhttp3.Headers):okhttp3.CacheControl");
    }

    public boolean immutable() {
        return this.immutable;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public boolean noCache() {
        return this.noCache;
    }

    public boolean noStore() {
        return this.noStore;
    }

    public boolean noTransform() {
        return this.noTransform;
    }

    public boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public String toString() {
        String str = this.f26095a;
        if (str != null) {
            return str;
        }
        String strHeaderValue = headerValue();
        this.f26095a = strHeaderValue;
        return strHeaderValue;
    }

    CacheControl(Builder builder) {
        this.noCache = builder.f26096a;
        this.noStore = builder.f26097b;
        this.maxAgeSeconds = builder.f26098c;
        this.sMaxAgeSeconds = -1;
        this.isPrivate = false;
        this.isPublic = false;
        this.mustRevalidate = false;
        this.maxStaleSeconds = builder.f26099d;
        this.minFreshSeconds = builder.f26100e;
        this.onlyIfCached = builder.f26101f;
        this.noTransform = builder.f26102g;
        this.immutable = builder.f26103h;
    }
}
