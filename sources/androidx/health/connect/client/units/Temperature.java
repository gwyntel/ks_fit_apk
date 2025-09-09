package androidx.health.connect.client.units;

import androidx.core.text.util.LocalePreferences;
import androidx.health.connect.client.records.c;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002\u0015\u0016B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u000e\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\rH\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0007\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Landroidx/health/connect/client/units/Temperature;", "", "value", "", "type", "Landroidx/health/connect/client/units/Temperature$Type;", "(DLandroidx/health/connect/client/units/Temperature$Type;)V", "inCelsius", "getCelsius", "()D", "inFahrenheit", "getFahrenheit", "compareTo", "", "other", "equals", "", "", "hashCode", "toString", "", "Companion", "Type", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Temperature implements Comparable<Temperature> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final Type type;
    private final double value;

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\b"}, d2 = {"Landroidx/health/connect/client/units/Temperature$Companion;", "", "()V", LocalePreferences.TemperatureUnit.CELSIUS, "Landroidx/health/connect/client/units/Temperature;", "value", "", "fahrenheit", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final Temperature celsius(double value) {
            return new Temperature(value, Type.CELSIUS, null);
        }

        @JvmStatic
        @NotNull
        public final Temperature fahrenheit(double value) {
            return new Temperature(value, Type.FAHRENHEIT, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Landroidx/health/connect/client/units/Temperature$Type;", "", "(Ljava/lang/String;I)V", "title", "", "getTitle", "()Ljava/lang/String;", "CELSIUS", "FAHRENHEIT", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Type {
        public static final Type CELSIUS = new CELSIUS("CELSIUS", 0);
        public static final Type FAHRENHEIT = new FAHRENHEIT("FAHRENHEIT", 1);
        private static final /* synthetic */ Type[] $VALUES = $values();

        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Landroidx/health/connect/client/units/Temperature$Type$CELSIUS;", "Landroidx/health/connect/client/units/Temperature$Type;", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class CELSIUS extends Type {

            @NotNull
            private final String title;

            CELSIUS(String str, int i2) {
                super(str, i2, null);
                this.title = "Celsius";
            }

            @Override // androidx.health.connect.client.units.Temperature.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Landroidx/health/connect/client/units/Temperature$Type$FAHRENHEIT;", "Landroidx/health/connect/client/units/Temperature$Type;", "title", "", "getTitle", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        static final class FAHRENHEIT extends Type {

            @NotNull
            private final String title;

            FAHRENHEIT(String str, int i2) {
                super(str, i2, null);
                this.title = "Fahrenheit";
            }

            @Override // androidx.health.connect.client.units.Temperature.Type
            @NotNull
            public String getTitle() {
                return this.title;
            }
        }

        private static final /* synthetic */ Type[] $values() {
            return new Type[]{CELSIUS, FAHRENHEIT};
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

        private Type(String str, int i2) {
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Type.values().length];
            try {
                iArr[Type.CELSIUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Type.FAHRENHEIT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ Temperature(double d2, Type type, DefaultConstructorMarker defaultConstructorMarker) {
        this(d2, type);
    }

    @JvmStatic
    @NotNull
    public static final Temperature celsius(double d2) {
        return INSTANCE.celsius(d2);
    }

    @JvmStatic
    @NotNull
    public static final Temperature fahrenheit(double d2) {
        return INSTANCE.fahrenheit(d2);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Temperature)) {
            return false;
        }
        Temperature temperature = (Temperature) other;
        return this.type == temperature.type ? this.value == temperature.value : getCelsius() == temperature.getCelsius();
    }

    @JvmName(name = "getCelsius")
    public final double getCelsius() {
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
        if (i2 == 1) {
            return this.value;
        }
        if (i2 == 2) {
            return (this.value - 32.0d) / 1.8d;
        }
        throw new NoWhenBranchMatchedException();
    }

    @JvmName(name = "getFahrenheit")
    public final double getFahrenheit() {
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
        if (i2 == 1) {
            return (this.value * 1.8d) + 32.0d;
        }
        if (i2 == 2) {
            return this.value;
        }
        throw new NoWhenBranchMatchedException();
    }

    public int hashCode() {
        return c.a(getCelsius());
    }

    @NotNull
    public String toString() {
        return this.value + ' ' + this.type.getTitle();
    }

    private Temperature(double d2, Type type) {
        this.value = d2;
        this.type = type;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull Temperature other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return this.type == other.type ? Double.compare(this.value, other.value) : Double.compare(getCelsius(), other.getCelsius());
    }
}
