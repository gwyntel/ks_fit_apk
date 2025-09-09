package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.units.BloodGlucose;
import android.health.connect.datatypes.units.Length;
import android.health.connect.datatypes.units.Percentage;
import android.health.connect.datatypes.units.Power;
import android.health.connect.datatypes.units.Pressure;
import android.health.connect.datatypes.units.Temperature;
import android.health.connect.datatypes.units.Velocity;
import android.health.connect.datatypes.units.Volume;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.health.connect.client.units.Energy;
import androidx.health.connect.client.units.Mass;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0094\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a\u0012\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00060\u0002j\u0002`\u0003H\u0000\u001a\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00060\u0006j\u0002`\u0007H\u0000\u001a\u0010\u0010\b\u001a\u00060\tj\u0002`\n*\u00020\u000bH\u0000\u001a\u0010\u0010\f\u001a\u00060\u0002j\u0002`\u0003*\u00020\u0001H\u0000\u001a\u0010\u0010\r\u001a\u00060\u000ej\u0002`\u000f*\u00020\u0010H\u0000\u001a\u0010\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00020\u0005H\u0000\u001a\u0010\u0010\u0012\u001a\u00060\u0013j\u0002`\u0014*\u00020\u0015H\u0000\u001a\u0010\u0010\u0016\u001a\u00060\u0017j\u0002`\u0018*\u00020\u0019H\u0000\u001a\u0010\u0010\u001a\u001a\u00060\u001bj\u0002`\u001c*\u00020\u001dH\u0000\u001a\u0010\u0010\u001e\u001a\u00060\u001fj\u0002` *\u00020!H\u0000\u001a\u0010\u0010\"\u001a\u00060#j\u0002`$*\u00020%H\u0000\u001a\u0010\u0010&\u001a\u00060'j\u0002`(*\u00020)H\u0000\u001a\u0010\u0010*\u001a\u00020\u000b*\u00060\tj\u0002`\nH\u0000\u001a\u0010\u0010+\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003H\u0000\u001a\u0010\u0010,\u001a\u00020\u0010*\u00060\u000ej\u0002`\u000fH\u0000\u001a\u0010\u0010-\u001a\u00020\u0005*\u00060\u0006j\u0002`\u0007H\u0000\u001a\u0010\u0010.\u001a\u00020\u0015*\u00060\u0013j\u0002`\u0014H\u0000\u001a\u0010\u0010/\u001a\u00020\u0019*\u00060\u0017j\u0002`\u0018H\u0000\u001a\u0010\u00100\u001a\u00020\u001d*\u00060\u001bj\u0002`\u001cH\u0000\u001a\u0010\u00101\u001a\u00020!*\u00060\u001fj\u0002` H\u0000\u001a\u0010\u00102\u001a\u00020%*\u00060#j\u0002`$H\u0000\u001a\u0010\u00103\u001a\u00020)*\u00060'j\u0002`(H\u0000¨\u00064"}, d2 = {"toNonDefaultSdkEnergy", "Landroidx/health/connect/client/units/Energy;", "Landroid/health/connect/datatypes/units/Energy;", "Landroidx/health/connect/client/impl/platform/records/PlatformEnergy;", "toNonDefaultSdkMass", "Landroidx/health/connect/client/units/Mass;", "Landroid/health/connect/datatypes/units/Mass;", "Landroidx/health/connect/client/impl/platform/records/PlatformMass;", "toPlatformBloodGlucose", "Landroid/health/connect/datatypes/units/BloodGlucose;", "Landroidx/health/connect/client/impl/platform/records/PlatformBloodGlucose;", "Landroidx/health/connect/client/units/BloodGlucose;", "toPlatformEnergy", "toPlatformLength", "Landroid/health/connect/datatypes/units/Length;", "Landroidx/health/connect/client/impl/platform/records/PlatformLength;", "Landroidx/health/connect/client/units/Length;", "toPlatformMass", "toPlatformPercentage", "Landroid/health/connect/datatypes/units/Percentage;", "Landroidx/health/connect/client/impl/platform/records/PlatformPercentage;", "Landroidx/health/connect/client/units/Percentage;", "toPlatformPower", "Landroid/health/connect/datatypes/units/Power;", "Landroidx/health/connect/client/impl/platform/records/PlatformPower;", "Landroidx/health/connect/client/units/Power;", "toPlatformPressure", "Landroid/health/connect/datatypes/units/Pressure;", "Landroidx/health/connect/client/impl/platform/records/PlatformPressure;", "Landroidx/health/connect/client/units/Pressure;", "toPlatformTemperature", "Landroid/health/connect/datatypes/units/Temperature;", "Landroidx/health/connect/client/impl/platform/records/PlatformTemperature;", "Landroidx/health/connect/client/units/Temperature;", "toPlatformVelocity", "Landroid/health/connect/datatypes/units/Velocity;", "Landroidx/health/connect/client/impl/platform/records/PlatformVelocity;", "Landroidx/health/connect/client/units/Velocity;", "toPlatformVolume", "Landroid/health/connect/datatypes/units/Volume;", "Landroidx/health/connect/client/impl/platform/records/PlatformVolume;", "Landroidx/health/connect/client/units/Volume;", "toSdkBloodGlucose", "toSdkEnergy", "toSdkLength", "toSdkMass", "toSdkPercentage", "toSdkPower", "toSdkPressure", "toSdkTemperature", "toSdkVelocity", "toSdkVolume", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RequiresApi(api = 34)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nUnitConverters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UnitConverters.kt\nandroidx/health/connect/client/impl/platform/records/UnitConvertersKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,121:1\n1#2:122\n*E\n"})
/* loaded from: classes.dex */
public final class UnitConvertersKt {
    @Nullable
    public static final Energy toNonDefaultSdkEnergy(@NotNull android.health.connect.datatypes.units.Energy energy) {
        Intrinsics.checkNotNullParameter(energy, "<this>");
        if (!(!(energy.getInCalories() == Double.MIN_VALUE))) {
            energy = null;
        }
        if (energy != null) {
            return toSdkEnergy(energy);
        }
        return null;
    }

    @Nullable
    public static final Mass toNonDefaultSdkMass(@NotNull android.health.connect.datatypes.units.Mass mass) {
        Intrinsics.checkNotNullParameter(mass, "<this>");
        if (!(!(mass.getInGrams() == Double.MIN_VALUE))) {
            mass = null;
        }
        if (mass != null) {
            return toSdkMass(mass);
        }
        return null;
    }

    @NotNull
    public static final BloodGlucose toPlatformBloodGlucose(@NotNull androidx.health.connect.client.units.BloodGlucose bloodGlucose) {
        Intrinsics.checkNotNullParameter(bloodGlucose, "<this>");
        BloodGlucose bloodGlucoseFromMillimolesPerLiter = BloodGlucose.fromMillimolesPerLiter(bloodGlucose.getMillimolesPerLiter());
        Intrinsics.checkNotNullExpressionValue(bloodGlucoseFromMillimolesPerLiter, "fromMillimolesPerLiter(inMillimolesPerLiter)");
        return bloodGlucoseFromMillimolesPerLiter;
    }

    @NotNull
    public static final android.health.connect.datatypes.units.Energy toPlatformEnergy(@NotNull Energy energy) {
        Intrinsics.checkNotNullParameter(energy, "<this>");
        android.health.connect.datatypes.units.Energy energyFromCalories = android.health.connect.datatypes.units.Energy.fromCalories(energy.getCalories());
        Intrinsics.checkNotNullExpressionValue(energyFromCalories, "fromCalories(inCalories)");
        return energyFromCalories;
    }

    @NotNull
    public static final Length toPlatformLength(@NotNull androidx.health.connect.client.units.Length length) {
        Intrinsics.checkNotNullParameter(length, "<this>");
        Length lengthFromMeters = Length.fromMeters(length.getMeters());
        Intrinsics.checkNotNullExpressionValue(lengthFromMeters, "fromMeters(inMeters)");
        return lengthFromMeters;
    }

    @NotNull
    public static final android.health.connect.datatypes.units.Mass toPlatformMass(@NotNull Mass mass) {
        Intrinsics.checkNotNullParameter(mass, "<this>");
        android.health.connect.datatypes.units.Mass massFromGrams = android.health.connect.datatypes.units.Mass.fromGrams(mass.getGrams());
        Intrinsics.checkNotNullExpressionValue(massFromGrams, "fromGrams(inGrams)");
        return massFromGrams;
    }

    @NotNull
    public static final Percentage toPlatformPercentage(@NotNull androidx.health.connect.client.units.Percentage percentage) {
        Intrinsics.checkNotNullParameter(percentage, "<this>");
        Percentage percentageFromValue = Percentage.fromValue(percentage.getValue());
        Intrinsics.checkNotNullExpressionValue(percentageFromValue, "fromValue(value)");
        return percentageFromValue;
    }

    @NotNull
    public static final Power toPlatformPower(@NotNull androidx.health.connect.client.units.Power power) {
        Intrinsics.checkNotNullParameter(power, "<this>");
        Power powerFromWatts = Power.fromWatts(power.getWatts());
        Intrinsics.checkNotNullExpressionValue(powerFromWatts, "fromWatts(inWatts)");
        return powerFromWatts;
    }

    @NotNull
    public static final Pressure toPlatformPressure(@NotNull androidx.health.connect.client.units.Pressure pressure) {
        Intrinsics.checkNotNullParameter(pressure, "<this>");
        Pressure pressureFromMillimetersOfMercury = Pressure.fromMillimetersOfMercury(pressure.getValue());
        Intrinsics.checkNotNullExpressionValue(pressureFromMillimetersOfMercury, "fromMillimetersOfMercury(inMillimetersOfMercury)");
        return pressureFromMillimetersOfMercury;
    }

    @NotNull
    public static final Temperature toPlatformTemperature(@NotNull androidx.health.connect.client.units.Temperature temperature) {
        Intrinsics.checkNotNullParameter(temperature, "<this>");
        Temperature temperatureFromCelsius = Temperature.fromCelsius(temperature.getCelsius());
        Intrinsics.checkNotNullExpressionValue(temperatureFromCelsius, "fromCelsius(inCelsius)");
        return temperatureFromCelsius;
    }

    @NotNull
    public static final Velocity toPlatformVelocity(@NotNull androidx.health.connect.client.units.Velocity velocity) {
        Intrinsics.checkNotNullParameter(velocity, "<this>");
        Velocity velocityFromMetersPerSecond = Velocity.fromMetersPerSecond(velocity.getMetersPerSecond());
        Intrinsics.checkNotNullExpressionValue(velocityFromMetersPerSecond, "fromMetersPerSecond(inMetersPerSecond)");
        return velocityFromMetersPerSecond;
    }

    @NotNull
    public static final Volume toPlatformVolume(@NotNull androidx.health.connect.client.units.Volume volume) {
        Intrinsics.checkNotNullParameter(volume, "<this>");
        Volume volumeFromLiters = Volume.fromLiters(volume.getLiters());
        Intrinsics.checkNotNullExpressionValue(volumeFromLiters, "fromLiters(inLiters)");
        return volumeFromLiters;
    }

    @NotNull
    public static final androidx.health.connect.client.units.BloodGlucose toSdkBloodGlucose(@NotNull BloodGlucose bloodGlucose) {
        Intrinsics.checkNotNullParameter(bloodGlucose, "<this>");
        return androidx.health.connect.client.units.BloodGlucose.INSTANCE.millimolesPerLiter(bloodGlucose.getInMillimolesPerLiter());
    }

    @NotNull
    public static final Energy toSdkEnergy(@NotNull android.health.connect.datatypes.units.Energy energy) {
        Intrinsics.checkNotNullParameter(energy, "<this>");
        return Energy.INSTANCE.calories(energy.getInCalories());
    }

    @NotNull
    public static final androidx.health.connect.client.units.Length toSdkLength(@NotNull Length length) {
        Intrinsics.checkNotNullParameter(length, "<this>");
        return androidx.health.connect.client.units.Length.INSTANCE.meters(length.getInMeters());
    }

    @NotNull
    public static final Mass toSdkMass(@NotNull android.health.connect.datatypes.units.Mass mass) {
        Intrinsics.checkNotNullParameter(mass, "<this>");
        return Mass.INSTANCE.grams(mass.getInGrams());
    }

    @NotNull
    public static final androidx.health.connect.client.units.Percentage toSdkPercentage(@NotNull Percentage percentage) {
        Intrinsics.checkNotNullParameter(percentage, "<this>");
        return new androidx.health.connect.client.units.Percentage(percentage.getValue());
    }

    @NotNull
    public static final androidx.health.connect.client.units.Power toSdkPower(@NotNull Power power) {
        Intrinsics.checkNotNullParameter(power, "<this>");
        return androidx.health.connect.client.units.Power.INSTANCE.watts(power.getInWatts());
    }

    @NotNull
    public static final androidx.health.connect.client.units.Pressure toSdkPressure(@NotNull Pressure pressure) {
        Intrinsics.checkNotNullParameter(pressure, "<this>");
        return androidx.health.connect.client.units.Pressure.INSTANCE.millimetersOfMercury(pressure.getInMillimetersOfMercury());
    }

    @NotNull
    public static final androidx.health.connect.client.units.Temperature toSdkTemperature(@NotNull Temperature temperature) {
        Intrinsics.checkNotNullParameter(temperature, "<this>");
        return androidx.health.connect.client.units.Temperature.INSTANCE.celsius(temperature.getInCelsius());
    }

    @NotNull
    public static final androidx.health.connect.client.units.Velocity toSdkVelocity(@NotNull Velocity velocity) {
        Intrinsics.checkNotNullParameter(velocity, "<this>");
        return androidx.health.connect.client.units.Velocity.INSTANCE.metersPerSecond(velocity.getInMetersPerSecond());
    }

    @NotNull
    public static final androidx.health.connect.client.units.Volume toSdkVolume(@NotNull Volume volume) {
        Intrinsics.checkNotNullParameter(volume, "<this>");
        return androidx.health.connect.client.units.Volume.INSTANCE.liters(volume.getInLiters());
    }
}
