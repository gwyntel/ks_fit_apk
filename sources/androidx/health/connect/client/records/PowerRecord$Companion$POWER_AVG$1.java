package androidx.health.connect.client.records;

import androidx.health.connect.client.units.Power;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.jetbrains.annotations.NotNull;

@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
/* synthetic */ class PowerRecord$Companion$POWER_AVG$1 extends FunctionReferenceImpl implements Function1<Double, Power> {
    PowerRecord$Companion$POWER_AVG$1(Object obj) {
        super(1, obj, Power.Companion.class, "watts", "watts(D)Landroidx/health/connect/client/units/Power;", 0);
    }

    @NotNull
    public final Power invoke(double d2) {
        return ((Power.Companion) this.receiver).watts(d2);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Power invoke(Double d2) {
        return invoke(d2.doubleValue());
    }
}
