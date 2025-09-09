package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.internal.SourceDebugExtension;

@SinceKotlin(version = "1.9")
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010\t\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\u000b\u001a\u00020\nH\u0014¢\u0006\u0004\b\u000b\u0010\fJ\u0018\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0086\u0002¢\u0006\u0004\b\r\u0010\bR\u0016\u0010\u000f\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "<init>", "()V", "Lkotlin/time/Duration;", "duration", "", "overflow-LRDsOJo", "(J)V", "overflow", "", "b", "()J", "plusAssign-LRDsOJo", "plusAssign", "reading", "J", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0})
@WasExperimental(markerClass = {ExperimentalTime.class})
@SourceDebugExtension({"SMAP\nTimeSources.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TimeSources.kt\nkotlin/time/TestTimeSource\n+ 2 longSaturatedMath.kt\nkotlin/time/LongSaturatedMathKt\n*L\n1#1,199:1\n80#2:200\n80#2:201\n*S KotlinDebug\n*F\n+ 1 TimeSources.kt\nkotlin/time/TestTimeSource\n*L\n173#1:200\n180#1:201\n*E\n"})
/* loaded from: classes5.dex */
public final class TestTimeSource extends AbstractLongTimeSource {
    private long reading;

    public TestTimeSource() {
        super(DurationUnit.NANOSECONDS);
        markNow();
    }

    /* renamed from: overflow-LRDsOJo, reason: not valid java name */
    private final void m2286overflowLRDsOJo(long duration) {
        throw new IllegalStateException("TestTimeSource will overflow if its reading " + this.reading + DurationUnitKt__DurationUnitKt.shortName(getUnit()) + " is advanced by " + ((Object) Duration.m2200toStringimpl(duration)) + '.');
    }

    @Override // kotlin.time.AbstractLongTimeSource
    /* renamed from: b, reason: from getter */
    protected long getReading() {
        return this.reading;
    }

    /* renamed from: plusAssign-LRDsOJo, reason: not valid java name */
    public final void m2287plusAssignLRDsOJo(long duration) {
        long jM2197toLongimpl = Duration.m2197toLongimpl(duration, getUnit());
        if (((jM2197toLongimpl - 1) | 1) != Long.MAX_VALUE) {
            long j2 = this.reading;
            long j3 = j2 + jM2197toLongimpl;
            if ((jM2197toLongimpl ^ j2) >= 0 && (j2 ^ j3) < 0) {
                m2286overflowLRDsOJo(duration);
            }
            this.reading = j3;
            return;
        }
        long jM2154divUwyO8pc = Duration.m2154divUwyO8pc(duration, 2);
        if ((1 | (Duration.m2197toLongimpl(jM2154divUwyO8pc, getUnit()) - 1)) == Long.MAX_VALUE) {
            m2286overflowLRDsOJo(duration);
            return;
        }
        long j4 = this.reading;
        try {
            m2287plusAssignLRDsOJo(jM2154divUwyO8pc);
            m2287plusAssignLRDsOJo(Duration.m2186minusLRDsOJo(duration, jM2154divUwyO8pc));
        } catch (IllegalStateException e2) {
            this.reading = j4;
            throw e2;
        }
    }
}
