package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.LocalCache;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
public final class CacheBuilder<K, V> {
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int DEFAULT_REFRESH_NANOS = 0;

    /* renamed from: q, reason: collision with root package name */
    static final Supplier f13688q = Suppliers.ofInstance(new AbstractCache.StatsCounter() { // from class: com.google.common.cache.CacheBuilder.1
        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordEviction() {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordHits(int i2) {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordLoadException(long j2) {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordLoadSuccess(long j2) {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordMisses(int i2) {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public CacheStats snapshot() {
            return CacheBuilder.f13689r;
        }
    });

    /* renamed from: r, reason: collision with root package name */
    static final CacheStats f13689r = new CacheStats(0, 0, 0, 0, 0, 0);

    /* renamed from: s, reason: collision with root package name */
    static final Supplier f13690s = new Supplier<AbstractCache.StatsCounter>() { // from class: com.google.common.cache.CacheBuilder.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.common.base.Supplier
        public AbstractCache.StatsCounter get() {
            return new AbstractCache.SimpleStatsCounter();
        }
    };

    /* renamed from: t, reason: collision with root package name */
    static final Ticker f13691t = new Ticker() { // from class: com.google.common.cache.CacheBuilder.3
        @Override // com.google.common.base.Ticker
        public long read() {
            return 0L;
        }
    };

    /* renamed from: f, reason: collision with root package name */
    Weigher f13697f;

    /* renamed from: g, reason: collision with root package name */
    LocalCache.Strength f13698g;

    /* renamed from: h, reason: collision with root package name */
    LocalCache.Strength f13699h;

    /* renamed from: l, reason: collision with root package name */
    Equivalence f13703l;

    /* renamed from: m, reason: collision with root package name */
    Equivalence f13704m;

    /* renamed from: n, reason: collision with root package name */
    RemovalListener f13705n;

    /* renamed from: o, reason: collision with root package name */
    Ticker f13706o;

    /* renamed from: a, reason: collision with root package name */
    boolean f13692a = true;

    /* renamed from: b, reason: collision with root package name */
    int f13693b = -1;

    /* renamed from: c, reason: collision with root package name */
    int f13694c = -1;

    /* renamed from: d, reason: collision with root package name */
    long f13695d = -1;

    /* renamed from: e, reason: collision with root package name */
    long f13696e = -1;

    /* renamed from: i, reason: collision with root package name */
    long f13700i = -1;

    /* renamed from: j, reason: collision with root package name */
    long f13701j = -1;

    /* renamed from: k, reason: collision with root package name */
    long f13702k = -1;

    /* renamed from: p, reason: collision with root package name */
    Supplier f13707p = f13688q;

    private static final class LoggerHolder {

        /* renamed from: a, reason: collision with root package name */
        static final Logger f13708a = Logger.getLogger(CacheBuilder.class.getName());

        private LoggerHolder() {
        }
    }

    enum NullListener implements RemovalListener<Object, Object> {
        INSTANCE;

        @Override // com.google.common.cache.RemovalListener
        public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
        }
    }

    enum OneWeigher implements Weigher<Object, Object> {
        INSTANCE;

        @Override // com.google.common.cache.Weigher
        public int weigh(Object obj, Object obj2) {
            return 1;
        }
    }

    private CacheBuilder() {
    }

    private void checkNonLoadingCache() {
        Preconditions.checkState(this.f13702k == -1, "refreshAfterWrite requires a LoadingCache");
    }

    private void checkWeightWithWeigher() {
        if (this.f13697f == null) {
            Preconditions.checkState(this.f13696e == -1, "maximumWeight requires weigher");
        } else if (this.f13692a) {
            Preconditions.checkState(this.f13696e != -1, "weigher requires maximumWeight");
        } else if (this.f13696e == -1) {
            LoggerHolder.f13708a.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
        }
    }

    @GwtIncompatible
    public static CacheBuilder<Object, Object> from(CacheBuilderSpec cacheBuilderSpec) {
        return cacheBuilderSpec.b().p();
    }

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<>();
    }

    @IgnoreJRERequirement
    @GwtIncompatible
    private static long toNanosSaturated(Duration duration) {
        try {
            return duration.toNanos();
        } catch (ArithmeticException unused) {
            return duration.isNegative() ? Long.MIN_VALUE : Long.MAX_VALUE;
        }
    }

    int a() {
        int i2 = this.f13694c;
        if (i2 == -1) {
            return 4;
        }
        return i2;
    }

    long b() {
        long j2 = this.f13701j;
        if (j2 == -1) {
            return 0L;
        }
        return j2;
    }

    public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> cacheLoader) {
        checkWeightWithWeigher();
        return new LocalCache.LocalLoadingCache(this, cacheLoader);
    }

    long c() {
        long j2 = this.f13700i;
        if (j2 == -1) {
            return 0L;
        }
        return j2;
    }

    @CanIgnoreReturnValue
    public CacheBuilder<K, V> concurrencyLevel(int i2) {
        int i3 = this.f13694c;
        Preconditions.checkState(i3 == -1, "concurrency level was already set to %s", i3);
        Preconditions.checkArgument(i2 > 0);
        this.f13694c = i2;
        return this;
    }

    int d() {
        int i2 = this.f13693b;
        if (i2 == -1) {
            return 16;
        }
        return i2;
    }

    Equivalence e() {
        return (Equivalence) MoreObjects.firstNonNull(this.f13703l, f().defaultEquivalence());
    }

    @CanIgnoreReturnValue
    @IgnoreJRERequirement
    @GwtIncompatible
    public CacheBuilder<K, V> expireAfterAccess(Duration duration) {
        return expireAfterAccess(toNanosSaturated(duration), TimeUnit.NANOSECONDS);
    }

    @CanIgnoreReturnValue
    @IgnoreJRERequirement
    @GwtIncompatible
    public CacheBuilder<K, V> expireAfterWrite(Duration duration) {
        return expireAfterWrite(toNanosSaturated(duration), TimeUnit.NANOSECONDS);
    }

    LocalCache.Strength f() {
        return (LocalCache.Strength) MoreObjects.firstNonNull(this.f13698g, LocalCache.Strength.STRONG);
    }

    long g() {
        if (this.f13700i == 0 || this.f13701j == 0) {
            return 0L;
        }
        return this.f13697f == null ? this.f13695d : this.f13696e;
    }

    long h() {
        long j2 = this.f13702k;
        if (j2 == -1) {
            return 0L;
        }
        return j2;
    }

    RemovalListener i() {
        return (RemovalListener) MoreObjects.firstNonNull(this.f13705n, NullListener.INSTANCE);
    }

    @CanIgnoreReturnValue
    public CacheBuilder<K, V> initialCapacity(int i2) {
        int i3 = this.f13693b;
        Preconditions.checkState(i3 == -1, "initial capacity was already set to %s", i3);
        Preconditions.checkArgument(i2 >= 0);
        this.f13693b = i2;
        return this;
    }

    Supplier j() {
        return this.f13707p;
    }

    Ticker k(boolean z2) {
        Ticker ticker = this.f13706o;
        return ticker != null ? ticker : z2 ? Ticker.systemTicker() : f13691t;
    }

    Equivalence l() {
        return (Equivalence) MoreObjects.firstNonNull(this.f13704m, m().defaultEquivalence());
    }

    LocalCache.Strength m() {
        return (LocalCache.Strength) MoreObjects.firstNonNull(this.f13699h, LocalCache.Strength.STRONG);
    }

    @CanIgnoreReturnValue
    public CacheBuilder<K, V> maximumSize(long j2) {
        long j3 = this.f13695d;
        Preconditions.checkState(j3 == -1, "maximum size was already set to %s", j3);
        long j4 = this.f13696e;
        Preconditions.checkState(j4 == -1, "maximum weight was already set to %s", j4);
        Preconditions.checkState(this.f13697f == null, "maximum size can not be combined with weigher");
        Preconditions.checkArgument(j2 >= 0, "maximum size must not be negative");
        this.f13695d = j2;
        return this;
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public CacheBuilder<K, V> maximumWeight(long j2) {
        long j3 = this.f13696e;
        Preconditions.checkState(j3 == -1, "maximum weight was already set to %s", j3);
        long j4 = this.f13695d;
        Preconditions.checkState(j4 == -1, "maximum size was already set to %s", j4);
        Preconditions.checkArgument(j2 >= 0, "maximum weight must not be negative");
        this.f13696e = j2;
        return this;
    }

    Weigher n() {
        return (Weigher) MoreObjects.firstNonNull(this.f13697f, OneWeigher.INSTANCE);
    }

    CacheBuilder o(Equivalence equivalence) {
        Equivalence equivalence2 = this.f13703l;
        Preconditions.checkState(equivalence2 == null, "key equivalence was already set to %s", equivalence2);
        this.f13703l = (Equivalence) Preconditions.checkNotNull(equivalence);
        return this;
    }

    CacheBuilder p() {
        this.f13692a = false;
        return this;
    }

    CacheBuilder q(LocalCache.Strength strength) {
        LocalCache.Strength strength2 = this.f13698g;
        Preconditions.checkState(strength2 == null, "Key strength was already set to %s", strength2);
        this.f13698g = (LocalCache.Strength) Preconditions.checkNotNull(strength);
        return this;
    }

    CacheBuilder r(LocalCache.Strength strength) {
        LocalCache.Strength strength2 = this.f13699h;
        Preconditions.checkState(strength2 == null, "Value strength was already set to %s", strength2);
        this.f13699h = (LocalCache.Strength) Preconditions.checkNotNull(strength);
        return this;
    }

    @CanIgnoreReturnValue
    public CacheBuilder<K, V> recordStats() {
        this.f13707p = f13690s;
        return this;
    }

    @CanIgnoreReturnValue
    @IgnoreJRERequirement
    @GwtIncompatible
    public CacheBuilder<K, V> refreshAfterWrite(Duration duration) {
        return refreshAfterWrite(toNanosSaturated(duration), TimeUnit.NANOSECONDS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> removalListener) {
        Preconditions.checkState(this.f13705n == null);
        this.f13705n = (RemovalListener) Preconditions.checkNotNull(removalListener);
        return this;
    }

    CacheBuilder s(Equivalence equivalence) {
        Equivalence equivalence2 = this.f13704m;
        Preconditions.checkState(equivalence2 == null, "value equivalence was already set to %s", equivalence2);
        this.f13704m = (Equivalence) Preconditions.checkNotNull(equivalence);
        return this;
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public CacheBuilder<K, V> softValues() {
        return r(LocalCache.Strength.SOFT);
    }

    @CanIgnoreReturnValue
    public CacheBuilder<K, V> ticker(Ticker ticker) {
        Preconditions.checkState(this.f13706o == null);
        this.f13706o = (Ticker) Preconditions.checkNotNull(ticker);
        return this;
    }

    public String toString() {
        MoreObjects.ToStringHelper stringHelper = MoreObjects.toStringHelper(this);
        int i2 = this.f13693b;
        if (i2 != -1) {
            stringHelper.add("initialCapacity", i2);
        }
        int i3 = this.f13694c;
        if (i3 != -1) {
            stringHelper.add("concurrencyLevel", i3);
        }
        long j2 = this.f13695d;
        if (j2 != -1) {
            stringHelper.add("maximumSize", j2);
        }
        long j3 = this.f13696e;
        if (j3 != -1) {
            stringHelper.add("maximumWeight", j3);
        }
        if (this.f13700i != -1) {
            stringHelper.add("expireAfterWrite", this.f13700i + NotificationStyle.NOTIFICATION_STYLE);
        }
        if (this.f13701j != -1) {
            stringHelper.add("expireAfterAccess", this.f13701j + NotificationStyle.NOTIFICATION_STYLE);
        }
        LocalCache.Strength strength = this.f13698g;
        if (strength != null) {
            stringHelper.add("keyStrength", Ascii.toLowerCase(strength.toString()));
        }
        LocalCache.Strength strength2 = this.f13699h;
        if (strength2 != null) {
            stringHelper.add("valueStrength", Ascii.toLowerCase(strength2.toString()));
        }
        if (this.f13703l != null) {
            stringHelper.addValue("keyEquivalence");
        }
        if (this.f13704m != null) {
            stringHelper.addValue("valueEquivalence");
        }
        if (this.f13705n != null) {
            stringHelper.addValue("removalListener");
        }
        return stringHelper.toString();
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public CacheBuilder<K, V> weakKeys() {
        return q(LocalCache.Strength.WEAK);
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public CacheBuilder<K, V> weakValues() {
        return r(LocalCache.Strength.WEAK);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CanIgnoreReturnValue
    @GwtIncompatible
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher) {
        Preconditions.checkState(this.f13697f == null);
        if (this.f13692a) {
            long j2 = this.f13695d;
            Preconditions.checkState(j2 == -1, "weigher can not be combined with maximum size (%s provided)", j2);
        }
        this.f13697f = (Weigher) Preconditions.checkNotNull(weigher);
        return this;
    }

    @GwtIncompatible
    public static CacheBuilder<Object, Object> from(String str) {
        return from(CacheBuilderSpec.parse(str));
    }

    @CanIgnoreReturnValue
    public CacheBuilder<K, V> expireAfterAccess(long j2, TimeUnit timeUnit) {
        long j3 = this.f13701j;
        Preconditions.checkState(j3 == -1, "expireAfterAccess was already set to %s ns", j3);
        Preconditions.checkArgument(j2 >= 0, "duration cannot be negative: %s %s", j2, timeUnit);
        this.f13701j = timeUnit.toNanos(j2);
        return this;
    }

    @CanIgnoreReturnValue
    public CacheBuilder<K, V> expireAfterWrite(long j2, TimeUnit timeUnit) {
        long j3 = this.f13700i;
        Preconditions.checkState(j3 == -1, "expireAfterWrite was already set to %s ns", j3);
        Preconditions.checkArgument(j2 >= 0, "duration cannot be negative: %s %s", j2, timeUnit);
        this.f13700i = timeUnit.toNanos(j2);
        return this;
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public CacheBuilder<K, V> refreshAfterWrite(long j2, TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeUnit);
        long j3 = this.f13702k;
        Preconditions.checkState(j3 == -1, "refresh was already set to %s ns", j3);
        Preconditions.checkArgument(j2 > 0, "duration must be positive: %s %s", j2, timeUnit);
        this.f13702k = timeUnit.toNanos(j2);
        return this;
    }

    public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
        checkWeightWithWeigher();
        checkNonLoadingCache();
        return new LocalCache.LocalManualCache(this);
    }
}
