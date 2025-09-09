package androidx.health.connect.client.units;

import com.google.android.gms.fitness.data.Field;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\n\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0004\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\f\u0010\b\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\f\u0010\n\"\u0015\u0010\r\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0004\"\u0015\u0010\r\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006\"\u0015\u0010\r\u001a\u00020\u0001*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\b\"\u0015\u0010\r\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\n\"\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0004\"\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006\"\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\b\"\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\n¨\u0006\u0011"}, d2 = {Field.NUTRIENT_CALORIES, "Landroidx/health/connect/client/units/Energy;", "", "getCalories", "(D)Landroidx/health/connect/client/units/Energy;", "", "(F)Landroidx/health/connect/client/units/Energy;", "", "(I)Landroidx/health/connect/client/units/Energy;", "", "(J)Landroidx/health/connect/client/units/Energy;", "joules", "getJoules", "kilocalories", "getKilocalories", "kilojoules", "getKilojoules", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class EnergyKt {
    public static final /* synthetic */ Energy getCalories(double d2) {
        return Energy.INSTANCE.calories(d2);
    }

    public static final /* synthetic */ Energy getJoules(double d2) {
        return Energy.INSTANCE.joules(d2);
    }

    public static final /* synthetic */ Energy getKilocalories(double d2) {
        return Energy.INSTANCE.kilocalories(d2);
    }

    public static final /* synthetic */ Energy getKilojoules(double d2) {
        return Energy.INSTANCE.kilojoules(d2);
    }
}
