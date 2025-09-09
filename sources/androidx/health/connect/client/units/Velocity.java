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

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u001a2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002\u001a\u001bB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0010\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010\u0015\u001a\u00020\u000fH\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\r\u0010\u0018\u001a\u00020\u0000H\u0000¢\u0006\u0002\b\u0019R\u0011\u0010\u0007\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\f\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\r\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Landroidx/health/connect/client/units/Velocity;", "", "value", "", "type", "Landroidx/health/connect/client/units/Velocity$Type;", "(DLandroidx/health/connect/client/units/Velocity$Type;)V", "inKilometersPerHour", "getKilometersPerHour", "()D", "inMetersPerSecond", "getMetersPerSecond", "inMilesPerHour", "getMilesPerHour", "compareTo", "", "other", "equals", "", "", TmpConstant.PROPERTY_IDENTIFIER_GET, "hashCode", "toString", "", "zero", "zero$connect_client_release", "Companion", "Type", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nVelocity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Velocity.kt\nandroidx/health/connect/client/units/Velocity\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,167:1\n9496#2,2:168\n9646#2,4:170\n*S KotlinDebug\n*F\n+ 1 Velocity.kt\nandroidx/health/connect/client/units/Velocity\n*L\n75#1:168,2\n75#1:170,4\n*E\n"})
/* loaded from: classes.dex */
public final class Velocity implements Comparable<Velocity> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Map<Type, Velocity> ZEROS;

    @NotNull
    private final Type type;
    private final double value;

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Landroidx/health/connect/client/units/Velocity$Companion;", "", "()V", "ZEROS", "", "Landroidx/health/connect/client/units/Velocity$Type;", "Landroidx/health/connect/client/units/Velocity;", "kilometersPerHour", "value", "", "metersPerSecond", "milesPerHour", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final Velocity kilometersPerHour(double value) {
            return new Velocity(value, Type.KILOMETERS_PER_HOUR, null);
        }

        @JvmStatic
        @NotNull
        public final Velocity metersPerSecond(double value) {
            return new Velocity(value, Type.METERS_PER_SECOND, null);
        }

        @JvmStatic
        @NotNull
        public final Velocity milesPerHour(double value) {
            return new Velocity(value, Type.MILES_PER_HOUR, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Landroidx/health/connect/client/units/Velocity$Type;", "", "(Ljava/lang/String;I)V", "metersPerSecondPerUnit", "", "getMetersPerSecondPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "METERS_PER_SECOND", "KILOMETERS_PER_HOUR", "MILES_PER_HOUR", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Type {
        public static final Type METERS_PER_SECOND = new METERS_PER_SECOND("METERS_PER_SECOND", 0);
        public static final Type KILOMETERS_PER_HOUR = new KILOMETERS_PER_HOUR("KILOMETERS_PER_HOUR", 1);
        public static final Type MILES_PER_HOUR = new MILES_PER_HOUR("MILES_PER_HOUR", 2);
        private static final /* synthetic */ Type[] $VALUES = $values();

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Velocity$Type$KILOMETERS_PER_HOUR;", "Landroidx/health/connect/client/units/Velocity$Type;", "metersPerSecondPerUnit", "", "getMetersPerSecondPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class KILOMETERS_PER_HOUR extends Type {
            private final double metersPerSecondPerUnit;

            @NotNull
            private final String title;

            KILOMETERS_PER_HOUR(String str, int i2) {
                super(str, i2, null);
                this.metersPerSecondPerUnit = 0.2777777777777778d;
                this.title = "km/h";
            }

            @Override // androidx.health.connect.client.units.Velocity.Type
            public double getMetersPerSecondPerUnit() {
                return this.metersPerSecondPerUnit;
            }

            @Override // androidx.health.connect.client.units.Velocity.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Velocity$Type$METERS_PER_SECOND;", "Landroidx/health/connect/client/units/Velocity$Type;", "metersPerSecondPerUnit", "", "getMetersPerSecondPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class METERS_PER_SECOND extends Type {
            private final double metersPerSecondPerUnit;

            @NotNull
            private final String title;

            METERS_PER_SECOND(String str, int i2) {
                super(str, i2, null);
                this.metersPerSecondPerUnit = 1.0d;
                this.title = "meters/sec";
            }

            @Override // androidx.health.connect.client.units.Velocity.Type
            public double getMetersPerSecondPerUnit() {
                return this.metersPerSecondPerUnit;
            }

            @Override // androidx.health.connect.client.units.Velocity.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Velocity$Type$MILES_PER_HOUR;", "Landroidx/health/connect/client/units/Velocity$Type;", "metersPerSecondPerUnit", "", "getMetersPerSecondPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class MILES_PER_HOUR extends Type {
            private final double metersPerSecondPerUnit;

            @NotNull
            private final String title;

            MILES_PER_HOUR(String str, int i2) {
                super(str, i2, null);
                this.metersPerSecondPerUnit = 0.447040357632d;
                this.title = "miles/h";
            }

            @Override // androidx.health.connect.client.units.Velocity.Type
            public double getMetersPerSecondPerUnit() {
                return this.metersPerSecondPerUnit;
            }

            @Override // androidx.health.connect.client.units.Velocity.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        private static final /* synthetic */ Type[] $values() {
            return new Type[]{METERS_PER_SECOND, KILOMETERS_PER_HOUR, MILES_PER_HOUR};
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

        public abstract double getMetersPerSecondPerUnit();

        @NotNull
        public abstract String getTitle();

        private Type(String str, int i2) {
        }
    }

    static {
        Type[] typeArrValues = Type.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(typeArrValues.length), 16));
        for (Type type : typeArrValues) {
            linkedHashMap.put(type, new Velocity(0.0d, type));
        }
        ZEROS = linkedHashMap;
    }

    public /* synthetic */ Velocity(double d2, Type type, DefaultConstructorMarker defaultConstructorMarker) {
        this(d2, type);
    }

    private final double get(Type type) {
        return this.type == type ? this.value : getMetersPerSecond() / type.getMetersPerSecondPerUnit();
    }

    @JvmStatic
    @NotNull
    public static final Velocity kilometersPerHour(double d2) {
        return INSTANCE.kilometersPerHour(d2);
    }

    @JvmStatic
    @NotNull
    public static final Velocity metersPerSecond(double d2) {
        return INSTANCE.metersPerSecond(d2);
    }

    @JvmStatic
    @NotNull
    public static final Velocity milesPerHour(double d2) {
        return INSTANCE.milesPerHour(d2);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Velocity)) {
            return false;
        }
        Velocity velocity = (Velocity) other;
        return this.type == velocity.type ? this.value == velocity.value : getMetersPerSecond() == velocity.getMetersPerSecond();
    }

    @JvmName(name = "getKilometersPerHour")
    public final double getKilometersPerHour() {
        return get(Type.KILOMETERS_PER_HOUR);
    }

    @JvmName(name = "getMetersPerSecond")
    public final double getMetersPerSecond() {
        return this.value * this.type.getMetersPerSecondPerUnit();
    }

    @JvmName(name = "getMilesPerHour")
    public final double getMilesPerHour() {
        return get(Type.MILES_PER_HOUR);
    }

    public int hashCode() {
        return c.a(getMetersPerSecond());
    }

    @NotNull
    public String toString() {
        return this.value + ' ' + this.type.getTitle();
    }

    @NotNull
    public final Velocity zero$connect_client_release() {
        return (Velocity) MapsKt.getValue(ZEROS, this.type);
    }

    private Velocity(double d2, Type type) {
        this.value = d2;
        this.type = type;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull Velocity other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return this.type == other.type ? Double.compare(this.value, other.value) : Double.compare(getMetersPerSecond(), other.getMetersPerSecond());
    }
}
