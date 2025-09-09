package androidx.health.connect.client.units;

import androidx.health.connect.client.records.c;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.fitness.data.Field;
import com.kingsmith.miot.KsProperty;
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

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u001c2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002\u001c\u001dB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0012\u001a\u0004\u0018\u00010\u0015H\u0096\u0002J\u0010\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010\u0017\u001a\u00020\u0011H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\r\u0010\u001a\u001a\u00020\u0000H\u0000¢\u0006\u0002\b\u001bR\u0011\u0010\u0007\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\f\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\r\u0010\tR\u0011\u0010\u000e\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Landroidx/health/connect/client/units/Energy;", "", "value", "", "type", "Landroidx/health/connect/client/units/Energy$Type;", "(DLandroidx/health/connect/client/units/Energy$Type;)V", "inCalories", "getCalories", "()D", "inJoules", "getJoules", "inKilocalories", "getKilocalories", "inKilojoules", "getKilojoules", "compareTo", "", "other", "equals", "", "", TmpConstant.PROPERTY_IDENTIFIER_GET, "hashCode", "toString", "", "zero", "zero$connect_client_release", "Companion", "Type", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nEnergy.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Energy.kt\nandroidx/health/connect/client/units/Energy\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,198:1\n9496#2,2:199\n9646#2,4:201\n*S KotlinDebug\n*F\n+ 1 Energy.kt\nandroidx/health/connect/client/units/Energy\n*L\n81#1:199,2\n81#1:201,4\n*E\n"})
/* loaded from: classes.dex */
public final class Energy implements Comparable<Energy> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Map<Type, Energy> ZEROS;

    @NotNull
    private final Type type;
    private final double value;

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Landroidx/health/connect/client/units/Energy$Companion;", "", "()V", "ZEROS", "", "Landroidx/health/connect/client/units/Energy$Type;", "Landroidx/health/connect/client/units/Energy;", Field.NUTRIENT_CALORIES, "value", "", "joules", "kilocalories", "kilojoules", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final Energy calories(double value) {
            return new Energy(value, Type.CALORIES, null);
        }

        @JvmStatic
        @NotNull
        public final Energy joules(double value) {
            return new Energy(value, Type.JOULES, null);
        }

        @JvmStatic
        @NotNull
        public final Energy kilocalories(double value) {
            return new Energy(value, Type.KILOCALORIES, null);
        }

        @JvmStatic
        @NotNull
        public final Energy kilojoules(double value) {
            return new Energy(value, Type.KILOJOULES, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"}, d2 = {"Landroidx/health/connect/client/units/Energy$Type;", "", "(Ljava/lang/String;I)V", "caloriesPerUnit", "", "getCaloriesPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "CALORIES", "KILOCALORIES", "JOULES", "KILOJOULES", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Type {
        public static final Type CALORIES = new CALORIES("CALORIES", 0);
        public static final Type KILOCALORIES = new KILOCALORIES("KILOCALORIES", 1);
        public static final Type JOULES = new JOULES("JOULES", 2);
        public static final Type KILOJOULES = new KILOJOULES("KILOJOULES", 3);
        private static final /* synthetic */ Type[] $VALUES = $values();

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Energy$Type$CALORIES;", "Landroidx/health/connect/client/units/Energy$Type;", "caloriesPerUnit", "", "getCaloriesPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class CALORIES extends Type {
            private final double caloriesPerUnit;

            @NotNull
            private final String title;

            CALORIES(String str, int i2) {
                super(str, i2, null);
                this.caloriesPerUnit = 1.0d;
                this.title = KsProperty.Cal;
            }

            @Override // androidx.health.connect.client.units.Energy.Type
            public double getCaloriesPerUnit() {
                return this.caloriesPerUnit;
            }

            @Override // androidx.health.connect.client.units.Energy.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Energy$Type$JOULES;", "Landroidx/health/connect/client/units/Energy$Type;", "caloriesPerUnit", "", "getCaloriesPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class JOULES extends Type {
            private final double caloriesPerUnit;

            @NotNull
            private final String title;

            JOULES(String str, int i2) {
                super(str, i2, null);
                this.caloriesPerUnit = 0.2390057361d;
                this.title = "J";
            }

            @Override // androidx.health.connect.client.units.Energy.Type
            public double getCaloriesPerUnit() {
                return this.caloriesPerUnit;
            }

            @Override // androidx.health.connect.client.units.Energy.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Energy$Type$KILOCALORIES;", "Landroidx/health/connect/client/units/Energy$Type;", "caloriesPerUnit", "", "getCaloriesPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class KILOCALORIES extends Type {
            private final double caloriesPerUnit;

            @NotNull
            private final String title;

            KILOCALORIES(String str, int i2) {
                super(str, i2, null);
                this.caloriesPerUnit = 1000.0d;
                this.title = "kcal";
            }

            @Override // androidx.health.connect.client.units.Energy.Type
            public double getCaloriesPerUnit() {
                return this.caloriesPerUnit;
            }

            @Override // androidx.health.connect.client.units.Energy.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/units/Energy$Type$KILOJOULES;", "Landroidx/health/connect/client/units/Energy$Type;", "caloriesPerUnit", "", "getCaloriesPerUnit", "()D", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class KILOJOULES extends Type {
            private final double caloriesPerUnit;

            @NotNull
            private final String title;

            KILOJOULES(String str, int i2) {
                super(str, i2, null);
                this.caloriesPerUnit = 239.0057361d;
                this.title = "kJ";
            }

            @Override // androidx.health.connect.client.units.Energy.Type
            public double getCaloriesPerUnit() {
                return this.caloriesPerUnit;
            }

            @Override // androidx.health.connect.client.units.Energy.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        private static final /* synthetic */ Type[] $values() {
            return new Type[]{CALORIES, KILOCALORIES, JOULES, KILOJOULES};
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

        public abstract double getCaloriesPerUnit();

        @NotNull
        public abstract String getTitle();

        private Type(String str, int i2) {
        }
    }

    static {
        Type[] typeArrValues = Type.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(typeArrValues.length), 16));
        for (Type type : typeArrValues) {
            linkedHashMap.put(type, new Energy(0.0d, type));
        }
        ZEROS = linkedHashMap;
    }

    public /* synthetic */ Energy(double d2, Type type, DefaultConstructorMarker defaultConstructorMarker) {
        this(d2, type);
    }

    @JvmStatic
    @NotNull
    public static final Energy calories(double d2) {
        return INSTANCE.calories(d2);
    }

    private final double get(Type type) {
        return this.type == type ? this.value : getCalories() / type.getCaloriesPerUnit();
    }

    @JvmStatic
    @NotNull
    public static final Energy joules(double d2) {
        return INSTANCE.joules(d2);
    }

    @JvmStatic
    @NotNull
    public static final Energy kilocalories(double d2) {
        return INSTANCE.kilocalories(d2);
    }

    @JvmStatic
    @NotNull
    public static final Energy kilojoules(double d2) {
        return INSTANCE.kilojoules(d2);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Energy)) {
            return false;
        }
        Energy energy = (Energy) other;
        return this.type == energy.type ? this.value == energy.value : getCalories() == energy.getCalories();
    }

    @JvmName(name = "getCalories")
    public final double getCalories() {
        return this.value * this.type.getCaloriesPerUnit();
    }

    @JvmName(name = "getJoules")
    public final double getJoules() {
        return get(Type.JOULES);
    }

    @JvmName(name = "getKilocalories")
    public final double getKilocalories() {
        return get(Type.KILOCALORIES);
    }

    @JvmName(name = "getKilojoules")
    public final double getKilojoules() {
        return get(Type.KILOJOULES);
    }

    public int hashCode() {
        return c.a(getCalories());
    }

    @NotNull
    public String toString() {
        return this.value + ' ' + this.type.getTitle();
    }

    @NotNull
    public final Energy zero$connect_client_release() {
        return (Energy) MapsKt.getValue(ZEROS, this.type);
    }

    private Energy(double d2, Type type) {
        this.value = d2;
        this.type = type;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull Energy other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return this.type == other.type ? Double.compare(this.value, other.value) : Double.compare(getCalories(), other.getCalories());
    }
}
