package androidx.health.connect.client.units;

import androidx.health.connect.client.records.c;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002\u0018\u0019B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u000e\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010\u0013\u001a\u00020\rH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\r\u0010\u0016\u001a\u00020\u0000H\u0000¢\u0006\u0002\b\u0017R\u0011\u0010\u0007\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Landroidx/health/connect/client/units/Power;", "", "value", "", "type", "Landroidx/health/connect/client/units/Power$Type;", "(DLandroidx/health/connect/client/units/Power$Type;)V", "inKilocaloriesPerDay", "getKilocaloriesPerDay", "()D", "inWatts", "getWatts", "compareTo", "", "other", "equals", "", "", TmpConstant.PROPERTY_IDENTIFIER_GET, "hashCode", "toString", "", "zero", "zero$connect_client_release", "Companion", "Type", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPower.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Power.kt\nandroidx/health/connect/client/units/Power\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,133:1\n9496#2,2:134\n9646#2,4:136\n*S KotlinDebug\n*F\n+ 1 Power.kt\nandroidx/health/connect/client/units/Power\n*L\n69#1:134,2\n69#1:136,4\n*E\n"})
/* loaded from: classes.dex */
public final class Power implements Comparable<Power> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Map<Type, Power> ZEROS;

    @NotNull
    private final Type type;
    private final double value;

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Landroidx/health/connect/client/units/Power$Companion;", "", "()V", "ZEROS", "", "Landroidx/health/connect/client/units/Power$Type;", "Landroidx/health/connect/client/units/Power;", "kilocaloriesPerDay", "value", "", "watts", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final Power kilocaloriesPerDay(double value) {
            return new Power(value, Type.KILOCALORIES_PER_DAY, null);
        }

        @JvmStatic
        @NotNull
        public final Power watts(double value) {
            return new Power(value, Type.WATTS, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0005\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Landroidx/health/connect/client/units/Power$Type;", "", "(Ljava/lang/String;I)V", "title", "", "getTitle", "()Ljava/lang/String;", "wattsPerUnit", "", "getWattsPerUnit", "()D", "WATTS", "KILOCALORIES_PER_DAY", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Type {
        public static final Type WATTS = new WATTS("WATTS", 0);
        public static final Type KILOCALORIES_PER_DAY = new KILOCALORIES_PER_DAY("KILOCALORIES_PER_DAY", 1);
        private static final /* synthetic */ Type[] $VALUES = $values();

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Power$Type$KILOCALORIES_PER_DAY;", "Landroidx/health/connect/client/units/Power$Type;", "title", "", "getTitle", "()Ljava/lang/String;", "wattsPerUnit", "", "getWattsPerUnit", "()D", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class KILOCALORIES_PER_DAY extends Type {

            @NotNull
            private final String title;
            private final double wattsPerUnit;

            KILOCALORIES_PER_DAY(String str, int i2) {
                super(str, i2, null);
                this.wattsPerUnit = 0.0484259259d;
                this.title = "kcal/day";
            }

            @Override // androidx.health.connect.client.units.Power.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }

            @Override // androidx.health.connect.client.units.Power.Type
            public double getWattsPerUnit() {
                return this.wattsPerUnit;
            }
        }

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Power$Type$WATTS;", "Landroidx/health/connect/client/units/Power$Type;", "title", "", "getTitle", "()Ljava/lang/String;", "wattsPerUnit", "", "getWattsPerUnit", "()D", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class WATTS extends Type {

            @NotNull
            private final String title;
            private final double wattsPerUnit;

            WATTS(String str, int i2) {
                super(str, i2, null);
                this.wattsPerUnit = 1.0d;
                this.title = "Watts";
            }

            @Override // androidx.health.connect.client.units.Power.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }

            @Override // androidx.health.connect.client.units.Power.Type
            public double getWattsPerUnit() {
                return this.wattsPerUnit;
            }
        }

        private static final /* synthetic */ Type[] $values() {
            return new Type[]{WATTS, KILOCALORIES_PER_DAY};
        }

        public /* synthetic */ Type(String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i2);
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }

        @NotNull
        public abstract String getTitle();

        public abstract double getWattsPerUnit();

        private Type(String str, int i2) {
        }
    }

    static {
        Type[] typeArrValues = Type.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(typeArrValues.length), 16));
        for (Type type : typeArrValues) {
            linkedHashMap.put(type, new Power(0.0d, type));
        }
        ZEROS = linkedHashMap;
    }

    public /* synthetic */ Power(double d2, Type type, DefaultConstructorMarker defaultConstructorMarker) {
        this(d2, type);
    }

    private final double get(Type type) {
        return this.type == type ? this.value : getWatts() / type.getWattsPerUnit();
    }

    @JvmStatic
    @NotNull
    public static final Power kilocaloriesPerDay(double d2) {
        return INSTANCE.kilocaloriesPerDay(d2);
    }

    @JvmStatic
    @NotNull
    public static final Power watts(double d2) {
        return INSTANCE.watts(d2);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Power)) {
            return false;
        }
        Power power = (Power) other;
        return this.type == power.type ? this.value == power.value : getWatts() == power.getWatts();
    }

    @JvmName(name = "getKilocaloriesPerDay")
    public final double getKilocaloriesPerDay() {
        return get(Type.KILOCALORIES_PER_DAY);
    }

    @JvmName(name = "getWatts")
    public final double getWatts() {
        return this.value * this.type.getWattsPerUnit();
    }

    public int hashCode() {
        return c.a(getWatts());
    }

    @NotNull
    public String toString() {
        return this.value + ' ' + this.type.getTitle();
    }

    @NotNull
    public final Power zero$connect_client_release() {
        return (Power) MapsKt.getValue(ZEROS, this.type);
    }

    private Power(double d2, Type type) {
        this.value = d2;
        this.type = type;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull Power other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return this.type == other.type ? Double.compare(this.value, other.value) : Double.compare(getWatts(), other.getWatts());
    }
}
