package androidx.health.connect.client.records;

import androidx.health.connect.client.units.Pressure;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.jetbrains.annotations.NotNull;

@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
/* synthetic */ class BloodPressureRecord$Companion$DIASTOLIC_MAX$1 extends FunctionReferenceImpl implements Function1<Double, Pressure> {
    BloodPressureRecord$Companion$DIASTOLIC_MAX$1(Object obj) {
        super(1, obj, Pressure.Companion.class, "millimetersOfMercury", "millimetersOfMercury(D)Landroidx/health/connect/client/units/Pressure;", 0);
    }

    @NotNull
    public final Pressure invoke(double d2) {
        return ((Pressure.Companion) this.receiver).millimetersOfMercury(d2);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Pressure invoke(Double d2) {
        return invoke(d2.doubleValue());
    }
}
