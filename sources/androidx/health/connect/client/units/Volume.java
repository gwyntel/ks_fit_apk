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

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u001a2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002\u001a\u001bB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0010\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010\u0015\u001a\u00020\u000fH\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\r\u0010\u0018\u001a\u00020\u0000H\u0000¢\u0006\u0002\b\u0019R\u0011\u0010\u0007\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\f\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\r\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Landroidx/health/connect/client/units/Volume;", "", "value", "", "type", "Landroidx/health/connect/client/units/Volume$Type;", "(DLandroidx/health/connect/client/units/Volume$Type;)V", "inFluidOuncesUs", "getFluidOuncesUs", "()D", "inLiters", "getLiters", "inMilliliters", "getMilliliters", "compareTo", "", "other", "equals", "", "", TmpConstant.PROPERTY_IDENTIFIER_GET, "hashCode", "toString", "", "zero", "zero$connect_client_release", "Companion", "Type", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nVolume.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Volume.kt\nandroidx/health/connect/client/units/Volume\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,165:1\n9496#2,2:166\n9646#2,4:168\n*S KotlinDebug\n*F\n+ 1 Volume.kt\nandroidx/health/connect/client/units/Volume\n*L\n75#1:166,2\n75#1:168,4\n*E\n"})
/* loaded from: classes.dex */
public final class Volume implements Comparable<Volume> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Map<Type, Volume> ZEROS;

    @NotNull
    private final Type type;
    private final double value;

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Landroidx/health/connect/client/units/Volume$Companion;", "", "()V", "ZEROS", "", "Landroidx/health/connect/client/units/Volume$Type;", "Landroidx/health/connect/client/units/Volume;", "fluidOuncesUs", "value", "", "liters", "milliliters", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final Volume fluidOuncesUs(double value) {
            return new Volume(value, Type.FLUID_OUNCES_US, null);
        }

        @JvmStatic
        @NotNull
        public final Volume liters(double value) {
            return new Volume(value, Type.LITERS, null);
        }

        @JvmStatic
        @NotNull
        public final Volume milliliters(double value) {
            return new Volume(value, Type.MILLILITERS, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Landroidx/health/connect/client/units/Volume$Type;", "", "(Ljava/lang/String;I)V", "litersPerUnit", "", "getLitersPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "LITERS", "MILLILITERS", "FLUID_OUNCES_US", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Type {
        public static final Type LITERS = new LITERS("LITERS", 0);
        public static final Type MILLILITERS = new MILLILITERS("MILLILITERS", 1);
        public static final Type FLUID_OUNCES_US = new FLUID_OUNCES_US("FLUID_OUNCES_US", 2);
        private static final /* synthetic */ Type[] $VALUES = $values();

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Volume$Type$FLUID_OUNCES_US;", "Landroidx/health/connect/client/units/Volume$Type;", "litersPerUnit", "", "getLitersPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class FLUID_OUNCES_US extends Type {
            private final double litersPerUnit;

            @NotNull
            private final String title;

            FLUID_OUNCES_US(String str, int i2) {
                super(str, i2, null);
                this.litersPerUnit = 0.02957353d;
                this.title = "fl. oz (US)";
            }

            @Override // androidx.health.connect.client.units.Volume.Type
            public double getLitersPerUnit() {
                return this.litersPerUnit;
            }

            @Override // androidx.health.connect.client.units.Volume.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Volume$Type$LITERS;", "Landroidx/health/connect/client/units/Volume$Type;", "litersPerUnit", "", "getLitersPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class LITERS extends Type {
            private final double litersPerUnit;

            @NotNull
            private final String title;

            LITERS(String str, int i2) {
                super(str, i2, null);
                this.litersPerUnit = 1.0d;
                this.title = "L";
            }

            @Override // androidx.health.connect.client.units.Volume.Type
            public double getLitersPerUnit() {
                return this.litersPerUnit;
            }

            @Override // androidx.health.connect.client.units.Volume.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Volume$Type$MILLILITERS;", "Landroidx/health/connect/client/units/Volume$Type;", "litersPerUnit", "", "getLitersPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class MILLILITERS extends Type {
            private final double litersPerUnit;

            @NotNull
            private final String title;

            MILLILITERS(String str, int i2) {
                super(str, i2, null);
                this.litersPerUnit = 0.001d;
                this.title = "mL";
            }

            @Override // androidx.health.connect.client.units.Volume.Type
            public double getLitersPerUnit() {
                return this.litersPerUnit;
            }

            @Override // androidx.health.connect.client.units.Volume.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        private static final /* synthetic */ Type[] $values() {
            return new Type[]{LITERS, MILLILITERS, FLUID_OUNCES_US};
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

        public abstract double getLitersPerUnit();

        @NotNull
        public abstract String getTitle();

        private Type(String str, int i2) {
        }
    }

    static {
        Type[] typeArrValues = Type.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(typeArrValues.length), 16));
        for (Type type : typeArrValues) {
            linkedHashMap.put(type, new Volume(0.0d, type));
        }
        ZEROS = linkedHashMap;
    }

    public /* synthetic */ Volume(double d2, Type type, DefaultConstructorMarker defaultConstructorMarker) {
        this(d2, type);
    }

    @JvmStatic
    @NotNull
    public static final Volume fluidOuncesUs(double d2) {
        return INSTANCE.fluidOuncesUs(d2);
    }

    private final double get(Type type) {
        return this.type == type ? this.value : getLiters() / type.getLitersPerUnit();
    }

    @JvmStatic
    @NotNull
    public static final Volume liters(double d2) {
        return INSTANCE.liters(d2);
    }

    @JvmStatic
    @NotNull
    public static final Volume milliliters(double d2) {
        return INSTANCE.milliliters(d2);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Volume)) {
            return false;
        }
        Volume volume = (Volume) other;
        return this.type == volume.type ? this.value == volume.value : getLiters() == volume.getLiters();
    }

    @JvmName(name = "getFluidOuncesUs")
    public final double getFluidOuncesUs() {
        return get(Type.FLUID_OUNCES_US);
    }

    @JvmName(name = "getLiters")
    public final double getLiters() {
        return this.value * this.type.getLitersPerUnit();
    }

    @JvmName(name = "getMilliliters")
    public final double getMilliliters() {
        return get(Type.MILLILITERS);
    }

    public int hashCode() {
        return c.a(getLiters());
    }

    @NotNull
    public String toString() {
        return this.value + ' ' + this.type.getTitle();
    }

    @NotNull
    public final Volume zero$connect_client_release() {
        return (Volume) MapsKt.getValue(ZEROS, this.type);
    }

    private Volume(double d2, Type type) {
        this.value = d2;
        this.type = type;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull Volume other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return this.type == other.type ? Double.compare(this.value, other.value) : Double.compare(getLiters(), other.getLiters());
    }
}
