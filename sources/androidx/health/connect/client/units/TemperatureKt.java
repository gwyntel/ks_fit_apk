package androidx.health.connect.client.units;

import androidx.core.text.util.LocalePreferences;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\n\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0004\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\f\u0010\b\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006\r"}, d2 = {LocalePreferences.TemperatureUnit.CELSIUS, "Landroidx/health/connect/client/units/Temperature;", "", "getCelsius", "(D)Landroidx/health/connect/client/units/Temperature;", "", "(F)Landroidx/health/connect/client/units/Temperature;", "", "(I)Landroidx/health/connect/client/units/Temperature;", "", "(J)Landroidx/health/connect/client/units/Temperature;", "fahrenheit", "getFahrenheit", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class TemperatureKt {
    public static final /* synthetic */ Temperature getCelsius(double d2) {
        return Temperature.INSTANCE.celsius(d2);
    }

    public static final /* synthetic */ Temperature getFahrenheit(double d2) {
        return Temperature.INSTANCE.fahrenheit(d2);
    }
}
