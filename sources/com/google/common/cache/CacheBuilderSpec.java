package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.cache.LocalCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public final class CacheBuilderSpec {
    private static final Splitter KEYS_SPLITTER = Splitter.on(',').trimResults();
    private static final Splitter KEY_VALUE_SPLITTER = Splitter.on(com.alipay.sdk.m.n.a.f9565h).trimResults();
    private static final ImmutableMap<String, ValueParser> VALUE_PARSERS;

    /* renamed from: a, reason: collision with root package name */
    Integer f13709a;

    /* renamed from: b, reason: collision with root package name */
    Long f13710b;

    /* renamed from: c, reason: collision with root package name */
    Long f13711c;

    /* renamed from: d, reason: collision with root package name */
    Integer f13712d;

    /* renamed from: e, reason: collision with root package name */
    LocalCache.Strength f13713e;

    /* renamed from: f, reason: collision with root package name */
    LocalCache.Strength f13714f;

    /* renamed from: g, reason: collision with root package name */
    Boolean f13715g;

    /* renamed from: h, reason: collision with root package name */
    long f13716h;

    /* renamed from: i, reason: collision with root package name */
    TimeUnit f13717i;

    /* renamed from: j, reason: collision with root package name */
    long f13718j;

    /* renamed from: k, reason: collision with root package name */
    TimeUnit f13719k;

    /* renamed from: l, reason: collision with root package name */
    long f13720l;

    /* renamed from: m, reason: collision with root package name */
    TimeUnit f13721m;
    private final String specification;

    /* renamed from: com.google.common.cache.CacheBuilderSpec$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f13722a;

        static {
            int[] iArr = new int[LocalCache.Strength.values().length];
            f13722a = iArr;
            try {
                iArr[LocalCache.Strength.WEAK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f13722a[LocalCache.Strength.SOFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    static class AccessDurationParser extends DurationParser {
        AccessDurationParser() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.DurationParser
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j2, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.f13719k == null, "expireAfterAccess already set");
            cacheBuilderSpec.f13718j = j2;
            cacheBuilderSpec.f13719k = timeUnit;
        }
    }

    static class ConcurrencyLevelParser extends IntegerParser {
        ConcurrencyLevelParser() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.IntegerParser
        protected void a(CacheBuilderSpec cacheBuilderSpec, int i2) {
            Integer num = cacheBuilderSpec.f13712d;
            Preconditions.checkArgument(num == null, "concurrency level was already set to %s", num);
            cacheBuilderSpec.f13712d = Integer.valueOf(i2);
        }
    }

    static abstract class DurationParser implements ValueParser {
        DurationParser() {
        }

        protected abstract void a(CacheBuilderSpec cacheBuilderSpec, long j2, TimeUnit timeUnit);

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2) {
            TimeUnit timeUnit;
            if (Strings.isNullOrEmpty(str2)) {
                throw new IllegalArgumentException("value of key " + str + " omitted");
            }
            try {
                char cCharAt = str2.charAt(str2.length() - 1);
                if (cCharAt == 'd') {
                    timeUnit = TimeUnit.DAYS;
                } else if (cCharAt == 'h') {
                    timeUnit = TimeUnit.HOURS;
                } else if (cCharAt == 'm') {
                    timeUnit = TimeUnit.MINUTES;
                } else {
                    if (cCharAt != 's') {
                        throw new IllegalArgumentException(CacheBuilderSpec.format("key %s invalid unit: was %s, must end with one of [dhms]", str, str2));
                    }
                    timeUnit = TimeUnit.SECONDS;
                }
                a(cacheBuilderSpec, Long.parseLong(str2.substring(0, str2.length() - 1)), timeUnit);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", str, str2));
            }
        }
    }

    static class InitialCapacityParser extends IntegerParser {
        InitialCapacityParser() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.IntegerParser
        protected void a(CacheBuilderSpec cacheBuilderSpec, int i2) {
            Integer num = cacheBuilderSpec.f13709a;
            Preconditions.checkArgument(num == null, "initial capacity was already set to %s", num);
            cacheBuilderSpec.f13709a = Integer.valueOf(i2);
        }
    }

    static abstract class IntegerParser implements ValueParser {
        IntegerParser() {
        }

        protected abstract void a(CacheBuilderSpec cacheBuilderSpec, int i2);

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            if (!Strings.isNullOrEmpty(str2)) {
                try {
                    a(cacheBuilderSpec, Integer.parseInt(str2));
                } catch (NumberFormatException e2) {
                    throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", str, str2), e2);
                }
            } else {
                throw new IllegalArgumentException("value of key " + str + " omitted");
            }
        }
    }

    static class KeyStrengthParser implements ValueParser {
        private final LocalCache.Strength strength;

        public KeyStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2) {
            Preconditions.checkArgument(str2 == null, "key %s does not take values", str);
            LocalCache.Strength strength = cacheBuilderSpec.f13713e;
            Preconditions.checkArgument(strength == null, "%s was already set to %s", str, strength);
            cacheBuilderSpec.f13713e = this.strength;
        }
    }

    static abstract class LongParser implements ValueParser {
        LongParser() {
        }

        protected abstract void a(CacheBuilderSpec cacheBuilderSpec, long j2);

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            if (!Strings.isNullOrEmpty(str2)) {
                try {
                    a(cacheBuilderSpec, Long.parseLong(str2));
                } catch (NumberFormatException e2) {
                    throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", str, str2), e2);
                }
            } else {
                throw new IllegalArgumentException("value of key " + str + " omitted");
            }
        }
    }

    static class MaximumSizeParser extends LongParser {
        MaximumSizeParser() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.LongParser
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j2) {
            Long l2 = cacheBuilderSpec.f13710b;
            Preconditions.checkArgument(l2 == null, "maximum size was already set to %s", l2);
            Long l3 = cacheBuilderSpec.f13711c;
            Preconditions.checkArgument(l3 == null, "maximum weight was already set to %s", l3);
            cacheBuilderSpec.f13710b = Long.valueOf(j2);
        }
    }

    static class MaximumWeightParser extends LongParser {
        MaximumWeightParser() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.LongParser
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j2) {
            Long l2 = cacheBuilderSpec.f13711c;
            Preconditions.checkArgument(l2 == null, "maximum weight was already set to %s", l2);
            Long l3 = cacheBuilderSpec.f13710b;
            Preconditions.checkArgument(l3 == null, "maximum size was already set to %s", l3);
            cacheBuilderSpec.f13711c = Long.valueOf(j2);
        }
    }

    static class RecordStatsParser implements ValueParser {
        RecordStatsParser() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2) {
            Preconditions.checkArgument(str2 == null, "recordStats does not take values");
            Preconditions.checkArgument(cacheBuilderSpec.f13715g == null, "recordStats already set");
            cacheBuilderSpec.f13715g = Boolean.TRUE;
        }
    }

    static class RefreshDurationParser extends DurationParser {
        RefreshDurationParser() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.DurationParser
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j2, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.f13721m == null, "refreshAfterWrite already set");
            cacheBuilderSpec.f13720l = j2;
            cacheBuilderSpec.f13721m = timeUnit;
        }
    }

    private interface ValueParser {
        void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2);
    }

    static class ValueStrengthParser implements ValueParser {
        private final LocalCache.Strength strength;

        public ValueStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2) {
            Preconditions.checkArgument(str2 == null, "key %s does not take values", str);
            LocalCache.Strength strength = cacheBuilderSpec.f13714f;
            Preconditions.checkArgument(strength == null, "%s was already set to %s", str, strength);
            cacheBuilderSpec.f13714f = this.strength;
        }
    }

    static class WriteDurationParser extends DurationParser {
        WriteDurationParser() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.DurationParser
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j2, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.f13717i == null, "expireAfterWrite already set");
            cacheBuilderSpec.f13716h = j2;
            cacheBuilderSpec.f13717i = timeUnit;
        }
    }

    static {
        ImmutableMap.Builder builderPut = ImmutableMap.builder().put("initialCapacity", new InitialCapacityParser()).put("maximumSize", new MaximumSizeParser()).put("maximumWeight", new MaximumWeightParser()).put("concurrencyLevel", new ConcurrencyLevelParser());
        LocalCache.Strength strength = LocalCache.Strength.WEAK;
        VALUE_PARSERS = builderPut.put("weakKeys", new KeyStrengthParser(strength)).put("softValues", new ValueStrengthParser(LocalCache.Strength.SOFT)).put("weakValues", new ValueStrengthParser(strength)).put("recordStats", new RecordStatsParser()).put("expireAfterAccess", new AccessDurationParser()).put("expireAfterWrite", new WriteDurationParser()).put("refreshAfterWrite", new RefreshDurationParser()).put("refreshInterval", new RefreshDurationParser()).buildOrThrow();
    }

    private CacheBuilderSpec(String str) {
        this.specification = str;
    }

    public static CacheBuilderSpec disableCaching() {
        return parse("maximumSize=0");
    }

    @CheckForNull
    private static Long durationInNanos(long j2, @CheckForNull TimeUnit timeUnit) {
        if (timeUnit == null) {
            return null;
        }
        return Long.valueOf(timeUnit.toNanos(j2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String format(String str, Object... objArr) {
        return String.format(Locale.ROOT, str, objArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static CacheBuilderSpec parse(String str) {
        CacheBuilderSpec cacheBuilderSpec = new CacheBuilderSpec(str);
        if (!str.isEmpty()) {
            for (String str2 : KEYS_SPLITTER.split(str)) {
                ImmutableList immutableListCopyOf = ImmutableList.copyOf(KEY_VALUE_SPLITTER.split(str2));
                Preconditions.checkArgument(!immutableListCopyOf.isEmpty(), "blank key-value pair");
                Preconditions.checkArgument(immutableListCopyOf.size() <= 2, "key-value pair %s with more than one equals sign", str2);
                String str3 = (String) immutableListCopyOf.get(0);
                ValueParser valueParser = VALUE_PARSERS.get(str3);
                Preconditions.checkArgument(valueParser != null, "unknown key %s", str3);
                valueParser.parse(cacheBuilderSpec, str3, immutableListCopyOf.size() == 1 ? null : (String) immutableListCopyOf.get(1));
            }
        }
        return cacheBuilderSpec;
    }

    CacheBuilder b() {
        CacheBuilder<Object, Object> cacheBuilderNewBuilder = CacheBuilder.newBuilder();
        Integer num = this.f13709a;
        if (num != null) {
            cacheBuilderNewBuilder.initialCapacity(num.intValue());
        }
        Long l2 = this.f13710b;
        if (l2 != null) {
            cacheBuilderNewBuilder.maximumSize(l2.longValue());
        }
        Long l3 = this.f13711c;
        if (l3 != null) {
            cacheBuilderNewBuilder.maximumWeight(l3.longValue());
        }
        Integer num2 = this.f13712d;
        if (num2 != null) {
            cacheBuilderNewBuilder.concurrencyLevel(num2.intValue());
        }
        LocalCache.Strength strength = this.f13713e;
        if (strength != null) {
            if (AnonymousClass1.f13722a[strength.ordinal()] != 1) {
                throw new AssertionError();
            }
            cacheBuilderNewBuilder.weakKeys();
        }
        LocalCache.Strength strength2 = this.f13714f;
        if (strength2 != null) {
            int i2 = AnonymousClass1.f13722a[strength2.ordinal()];
            if (i2 == 1) {
                cacheBuilderNewBuilder.weakValues();
            } else {
                if (i2 != 2) {
                    throw new AssertionError();
                }
                cacheBuilderNewBuilder.softValues();
            }
        }
        Boolean bool = this.f13715g;
        if (bool != null && bool.booleanValue()) {
            cacheBuilderNewBuilder.recordStats();
        }
        TimeUnit timeUnit = this.f13717i;
        if (timeUnit != null) {
            cacheBuilderNewBuilder.expireAfterWrite(this.f13716h, timeUnit);
        }
        TimeUnit timeUnit2 = this.f13719k;
        if (timeUnit2 != null) {
            cacheBuilderNewBuilder.expireAfterAccess(this.f13718j, timeUnit2);
        }
        TimeUnit timeUnit3 = this.f13721m;
        if (timeUnit3 != null) {
            cacheBuilderNewBuilder.refreshAfterWrite(this.f13720l, timeUnit3);
        }
        return cacheBuilderNewBuilder;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CacheBuilderSpec)) {
            return false;
        }
        CacheBuilderSpec cacheBuilderSpec = (CacheBuilderSpec) obj;
        return Objects.equal(this.f13709a, cacheBuilderSpec.f13709a) && Objects.equal(this.f13710b, cacheBuilderSpec.f13710b) && Objects.equal(this.f13711c, cacheBuilderSpec.f13711c) && Objects.equal(this.f13712d, cacheBuilderSpec.f13712d) && Objects.equal(this.f13713e, cacheBuilderSpec.f13713e) && Objects.equal(this.f13714f, cacheBuilderSpec.f13714f) && Objects.equal(this.f13715g, cacheBuilderSpec.f13715g) && Objects.equal(durationInNanos(this.f13716h, this.f13717i), durationInNanos(cacheBuilderSpec.f13716h, cacheBuilderSpec.f13717i)) && Objects.equal(durationInNanos(this.f13718j, this.f13719k), durationInNanos(cacheBuilderSpec.f13718j, cacheBuilderSpec.f13719k)) && Objects.equal(durationInNanos(this.f13720l, this.f13721m), durationInNanos(cacheBuilderSpec.f13720l, cacheBuilderSpec.f13721m));
    }

    public int hashCode() {
        return Objects.hashCode(this.f13709a, this.f13710b, this.f13711c, this.f13712d, this.f13713e, this.f13714f, this.f13715g, durationInNanos(this.f13716h, this.f13717i), durationInNanos(this.f13718j, this.f13719k), durationInNanos(this.f13720l, this.f13721m));
    }

    public String toParsableString() {
        return this.specification;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(toParsableString()).toString();
    }
}
