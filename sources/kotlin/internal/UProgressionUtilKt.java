package kotlin.internal;

import androidx.media3.extractor.text.ttml.TtmlNode;
import com.umeng.analytics.pro.bc;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.b;
import kotlin.c;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u0005\u0010\u0006\u001a'\u0010\u0000\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\b\u0010\t\u001a'\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001¢\u0006\u0004\b\u000f\u0010\u0006\u001a'\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001¢\u0006\u0004\b\u0011\u0010\t¨\u0006\u0012"}, d2 = {"differenceModulo", "Lkotlin/UInt;", "a", "b", bc.aL, "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", TtmlNode.END, "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class UProgressionUtilKt {
    /* renamed from: differenceModulo-WZ9TVnA, reason: not valid java name */
    private static final int m2038differenceModuloWZ9TVnA(int i2, int i3, int i4) {
        int iA = b.a(i2, i4);
        int iA2 = b.a(i3, i4);
        int iCompare = Integer.compare(iA ^ Integer.MIN_VALUE, iA2 ^ Integer.MIN_VALUE);
        int iM932constructorimpl = UInt.m932constructorimpl(iA - iA2);
        return iCompare >= 0 ? iM932constructorimpl : UInt.m932constructorimpl(iM932constructorimpl + i4);
    }

    /* renamed from: differenceModulo-sambcqE, reason: not valid java name */
    private static final long m2039differenceModulosambcqE(long j2, long j3, long j4) {
        long jA = c.a(j2, j4);
        long jA2 = c.a(j3, j4);
        int iCompare = Long.compare(jA ^ Long.MIN_VALUE, jA2 ^ Long.MIN_VALUE);
        long jM1011constructorimpl = ULong.m1011constructorimpl(jA - jA2);
        return iCompare >= 0 ? jM1011constructorimpl : ULong.m1011constructorimpl(jM1011constructorimpl + j4);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* renamed from: getProgressionLastElement-7ftBX0g, reason: not valid java name */
    public static final long m2040getProgressionLastElement7ftBX0g(long j2, long j3, long j4) {
        if (j4 > 0) {
            return Long.compare(j2 ^ Long.MIN_VALUE, j3 ^ Long.MIN_VALUE) >= 0 ? j3 : ULong.m1011constructorimpl(j3 - m2039differenceModulosambcqE(j3, j2, ULong.m1011constructorimpl(j4)));
        }
        if (j4 < 0) {
            return Long.compare(j2 ^ Long.MIN_VALUE, j3 ^ Long.MIN_VALUE) <= 0 ? j3 : ULong.m1011constructorimpl(j3 + m2039differenceModulosambcqE(j2, j3, ULong.m1011constructorimpl(-j4)));
        }
        throw new IllegalArgumentException("Step is zero.");
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* renamed from: getProgressionLastElement-Nkh28Cs, reason: not valid java name */
    public static final int m2041getProgressionLastElementNkh28Cs(int i2, int i3, int i4) {
        if (i4 > 0) {
            return Integer.compare(i2 ^ Integer.MIN_VALUE, i3 ^ Integer.MIN_VALUE) >= 0 ? i3 : UInt.m932constructorimpl(i3 - m2038differenceModuloWZ9TVnA(i3, i2, UInt.m932constructorimpl(i4)));
        }
        if (i4 < 0) {
            return Integer.compare(i2 ^ Integer.MIN_VALUE, i3 ^ Integer.MIN_VALUE) <= 0 ? i3 : UInt.m932constructorimpl(i3 + m2038differenceModuloWZ9TVnA(i2, i3, UInt.m932constructorimpl(-i4)));
        }
        throw new IllegalArgumentException("Step is zero.");
    }
}
