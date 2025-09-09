package kotlin.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.health.connect.client.records.g;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.time.ComparableTimeMark;
import kotlin.time.TimeSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.9")
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\b'\u0018\u00002\u00020\u0001:\u0001\u0014B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u000f\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0006H$¢\u0006\u0004\b\t\u0010\bJ\u000f\u0010\u000b\u001a\u00020\nH\u0016¢\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u0003\u001a\u00020\u00028\u0004X\u0084\u0004¢\u0006\f\n\u0004\b\u0003\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0013\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\b¨\u0006\u0015"}, d2 = {"Lkotlin/time/AbstractLongTimeSource;", "Lkotlin/time/TimeSource$WithComparableMarks;", "Lkotlin/time/DurationUnit;", "unit", "<init>", "(Lkotlin/time/DurationUnit;)V", "", "adjustedRead", "()J", "b", "Lkotlin/time/ComparableTimeMark;", "markNow", "()Lkotlin/time/ComparableTimeMark;", "Lkotlin/time/DurationUnit;", "a", "()Lkotlin/time/DurationUnit;", "zero$delegate", "Lkotlin/Lazy;", "getZero", "zero", "LongTimeMark", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0})
@WasExperimental(markerClass = {ExperimentalTime.class})
/* loaded from: classes5.dex */
public abstract class AbstractLongTimeSource implements TimeSource.WithComparableMarks {

    @NotNull
    private final DurationUnit unit;

    /* renamed from: zero$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy zero;

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\n\u001a\u00020\u0007H\u0016ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001b\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0001H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u0018\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0007H\u0096\u0002¢\u0006\u0004\b\u0018\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0013\u0010\u0006\u001a\u00020\u0007X\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b!¨\u0006\u001c"}, d2 = {"Lkotlin/time/AbstractLongTimeSource$LongTimeMark;", "Lkotlin/time/ComparableTimeMark;", "startedAt", "", "timeSource", "Lkotlin/time/AbstractLongTimeSource;", TypedValues.CycleType.S_WAVE_OFFSET, "Lkotlin/time/Duration;", "(JLkotlin/time/AbstractLongTimeSource;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "J", "elapsedNow", "elapsedNow-UwyO8pc", "()J", "equals", "", "other", "", "hashCode", "", "minus", "minus-UwyO8pc", "(Lkotlin/time/ComparableTimeMark;)J", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/ComparableTimeMark;", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nTimeSources.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TimeSources.kt\nkotlin/time/AbstractLongTimeSource$LongTimeMark\n+ 2 longSaturatedMath.kt\nkotlin/time/LongSaturatedMathKt\n*L\n1#1,199:1\n80#2:200\n*S KotlinDebug\n*F\n+ 1 TimeSources.kt\nkotlin/time/AbstractLongTimeSource$LongTimeMark\n*L\n67#1:200\n*E\n"})
    private static final class LongTimeMark implements ComparableTimeMark {
        private final long offset;
        private final long startedAt;

        @NotNull
        private final AbstractLongTimeSource timeSource;

        public /* synthetic */ LongTimeMark(long j2, AbstractLongTimeSource abstractLongTimeSource, long j3, DefaultConstructorMarker defaultConstructorMarker) {
            this(j2, abstractLongTimeSource, j3);
        }

        @Override // kotlin.time.TimeMark
        /* renamed from: elapsedNow-UwyO8pc */
        public long mo2141elapsedNowUwyO8pc() {
            return Duration.m2186minusLRDsOJo(LongSaturatedMathKt.saturatingOriginsDiff(this.timeSource.adjustedRead(), this.startedAt, this.timeSource.getUnit()), this.offset);
        }

        @Override // kotlin.time.ComparableTimeMark
        public boolean equals(@Nullable Object other) {
            return (other instanceof LongTimeMark) && Intrinsics.areEqual(this.timeSource, ((LongTimeMark) other).timeSource) && Duration.m2156equalsimpl0(mo2143minusUwyO8pc((ComparableTimeMark) other), Duration.INSTANCE.m2254getZEROUwyO8pc());
        }

        @Override // kotlin.time.TimeMark
        public boolean hasNotPassedNow() {
            return ComparableTimeMark.DefaultImpls.hasNotPassedNow(this);
        }

        @Override // kotlin.time.TimeMark
        public boolean hasPassedNow() {
            return ComparableTimeMark.DefaultImpls.hasPassedNow(this);
        }

        @Override // kotlin.time.ComparableTimeMark
        public int hashCode() {
            return (Duration.m2179hashCodeimpl(this.offset) * 37) + g.a(this.startedAt);
        }

        @Override // kotlin.time.TimeMark
        @NotNull
        /* renamed from: minus-LRDsOJo */
        public ComparableTimeMark mo2142minusLRDsOJo(long j2) {
            return ComparableTimeMark.DefaultImpls.m2146minusLRDsOJo(this, j2);
        }

        @Override // kotlin.time.ComparableTimeMark
        /* renamed from: minus-UwyO8pc */
        public long mo2143minusUwyO8pc(@NotNull ComparableTimeMark other) {
            Intrinsics.checkNotNullParameter(other, "other");
            if (other instanceof LongTimeMark) {
                LongTimeMark longTimeMark = (LongTimeMark) other;
                if (Intrinsics.areEqual(this.timeSource, longTimeMark.timeSource)) {
                    return Duration.m2187plusLRDsOJo(LongSaturatedMathKt.saturatingOriginsDiff(this.startedAt, longTimeMark.startedAt, this.timeSource.getUnit()), Duration.m2186minusLRDsOJo(this.offset, longTimeMark.offset));
                }
            }
            throw new IllegalArgumentException("Subtracting or comparing time marks from different time sources is not possible: " + this + " and " + other);
        }

        @NotNull
        public String toString() {
            return "LongTimeMark(" + this.startedAt + DurationUnitKt__DurationUnitKt.shortName(this.timeSource.getUnit()) + " + " + ((Object) Duration.m2200toStringimpl(this.offset)) + ", " + this.timeSource + ')';
        }

        private LongTimeMark(long j2, AbstractLongTimeSource timeSource, long j3) {
            Intrinsics.checkNotNullParameter(timeSource, "timeSource");
            this.startedAt = j2;
            this.timeSource = timeSource;
            this.offset = j3;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NotNull ComparableTimeMark comparableTimeMark) {
            return ComparableTimeMark.DefaultImpls.compareTo(this, comparableTimeMark);
        }

        @Override // kotlin.time.TimeMark
        @NotNull
        /* renamed from: plus-LRDsOJo */
        public ComparableTimeMark mo2144plusLRDsOJo(long duration) {
            DurationUnit unit = this.timeSource.getUnit();
            if (Duration.m2183isInfiniteimpl(duration)) {
                return new LongTimeMark(LongSaturatedMathKt.m2280saturatingAddNuflL3o(this.startedAt, unit, duration), this.timeSource, Duration.INSTANCE.m2254getZEROUwyO8pc(), null);
            }
            long jM2203truncateToUwyO8pc$kotlin_stdlib = Duration.m2203truncateToUwyO8pc$kotlin_stdlib(duration, unit);
            long jM2187plusLRDsOJo = Duration.m2187plusLRDsOJo(Duration.m2186minusLRDsOJo(duration, jM2203truncateToUwyO8pc$kotlin_stdlib), this.offset);
            long jM2280saturatingAddNuflL3o = LongSaturatedMathKt.m2280saturatingAddNuflL3o(this.startedAt, unit, jM2203truncateToUwyO8pc$kotlin_stdlib);
            long jM2203truncateToUwyO8pc$kotlin_stdlib2 = Duration.m2203truncateToUwyO8pc$kotlin_stdlib(jM2187plusLRDsOJo, unit);
            long jM2280saturatingAddNuflL3o2 = LongSaturatedMathKt.m2280saturatingAddNuflL3o(jM2280saturatingAddNuflL3o, unit, jM2203truncateToUwyO8pc$kotlin_stdlib2);
            long jM2186minusLRDsOJo = Duration.m2186minusLRDsOJo(jM2187plusLRDsOJo, jM2203truncateToUwyO8pc$kotlin_stdlib2);
            long jM2171getInWholeNanosecondsimpl = Duration.m2171getInWholeNanosecondsimpl(jM2186minusLRDsOJo);
            if (jM2280saturatingAddNuflL3o2 != 0 && jM2171getInWholeNanosecondsimpl != 0 && (jM2280saturatingAddNuflL3o2 ^ jM2171getInWholeNanosecondsimpl) < 0) {
                long duration2 = DurationKt.toDuration(MathKt.getSign(jM2171getInWholeNanosecondsimpl), unit);
                jM2280saturatingAddNuflL3o2 = LongSaturatedMathKt.m2280saturatingAddNuflL3o(jM2280saturatingAddNuflL3o2, unit, duration2);
                jM2186minusLRDsOJo = Duration.m2186minusLRDsOJo(jM2186minusLRDsOJo, duration2);
            }
            if ((1 | (jM2280saturatingAddNuflL3o2 - 1)) == Long.MAX_VALUE) {
                jM2186minusLRDsOJo = Duration.INSTANCE.m2254getZEROUwyO8pc();
            }
            return new LongTimeMark(jM2280saturatingAddNuflL3o2, this.timeSource, jM2186minusLRDsOJo, null);
        }
    }

    public AbstractLongTimeSource(@NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        this.unit = unit;
        this.zero = LazyKt.lazy(new Function0<Long>() { // from class: kotlin.time.AbstractLongTimeSource$zero$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Long invoke() {
                return Long.valueOf(this.this$0.b());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long adjustedRead() {
        return b() - getZero();
    }

    private final long getZero() {
        return ((Number) this.zero.getValue()).longValue();
    }

    /* renamed from: a, reason: from getter */
    protected final DurationUnit getUnit() {
        return this.unit;
    }

    protected abstract long b();

    @Override // kotlin.time.TimeSource
    @NotNull
    public ComparableTimeMark markNow() {
        return new LongTimeMark(adjustedRead(), this, Duration.INSTANCE.m2254getZEROUwyO8pc(), null);
    }
}
