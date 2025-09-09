package androidx.health.connect.client.records;

import androidx.annotation.RestrictTo;
import androidx.exifinterface.media.ExifInterface;
import com.kingsmith.miot.KsProperty;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\b\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u0005H\u0000\u001a9\u0010\u0007\u001a\u00020\u0001\"\u000e\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t*\u0002H\b2\u0006\u0010\n\u001a\u0002H\b2\u0006\u0010\u000b\u001a\u0002H\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¢\u0006\u0002\u0010\f\u001a1\u0010\r\u001a\u00020\u0001\"\u000e\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t*\u0002H\b2\u0006\u0010\u000e\u001a\u0002H\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¢\u0006\u0002\u0010\u000f\u001a1\u0010\u0010\u001a\u00020\u0001\"\u000e\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t*\u0002H\b2\u0006\u0010\u000e\u001a\u0002H\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¢\u0006\u0002\u0010\u000f\u001a$\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00050\u0012*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00130\u0012H\u0000¨\u0006\u0014"}, d2 = {"requireNonNegative", "", "value", "", "name", "", "", "requireInRange", ExifInterface.GPS_DIRECTION_TRUE, "", "min", KsProperty.Max, "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/String;)V", "requireNotLess", "other", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/String;)V", "requireNotMore", "reverse", "", "", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Utils.kt\nandroidx/health/connect/client/records/UtilsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,47:1\n1#2:48\n1208#3,2:49\n1238#3,4:51\n*S KotlinDebug\n*F\n+ 1 Utils.kt\nandroidx/health/connect/client/records/UtilsKt\n*L\n40#1:49,2\n40#1:51,4\n*E\n"})
/* loaded from: classes.dex */
public final class UtilsKt {
    public static final <T extends Comparable<? super T>> void requireInRange(@NotNull T t2, @NotNull T min, @NotNull T max, @NotNull String name) {
        Intrinsics.checkNotNullParameter(t2, "<this>");
        Intrinsics.checkNotNullParameter(min, "min");
        Intrinsics.checkNotNullParameter(max, "max");
        Intrinsics.checkNotNullParameter(name, "name");
        requireNotLess(t2, min, name);
        requireNotMore(t2, max, name);
    }

    public static final void requireNonNegative(long j2, @NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (j2 >= 0) {
            return;
        }
        throw new IllegalArgumentException((name + " must not be negative").toString());
    }

    public static final <T extends Comparable<? super T>> void requireNotLess(@NotNull T t2, @NotNull T other, @NotNull String name) {
        Intrinsics.checkNotNullParameter(t2, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(name, "name");
        if (t2.compareTo(other) >= 0) {
            return;
        }
        throw new IllegalArgumentException((name + " must not be less than " + other + ", currently " + t2 + '.').toString());
    }

    public static final <T extends Comparable<? super T>> void requireNotMore(@NotNull T t2, @NotNull T other, @NotNull String name) {
        Intrinsics.checkNotNullParameter(t2, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(name, "name");
        if (t2.compareTo(other) <= 0) {
            return;
        }
        throw new IllegalArgumentException((name + " must not be more than " + other + ", currently " + t2 + '.').toString());
    }

    @NotNull
    public static final Map<Integer, String> reverse(@NotNull Map<String, Integer> map) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Set<Map.Entry<String, Integer>> setEntrySet = map.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(Integer.valueOf(((Number) entry.getValue()).intValue()), (String) entry.getKey());
        }
        return linkedHashMap;
    }

    public static final void requireNonNegative(double d2, @NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (d2 >= 0.0d) {
            return;
        }
        throw new IllegalArgumentException((name + " must not be negative").toString());
    }
}
