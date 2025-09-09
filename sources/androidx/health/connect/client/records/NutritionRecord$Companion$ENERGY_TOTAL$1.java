package androidx.health.connect.client.records;

import androidx.health.connect.client.units.Energy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.jetbrains.annotations.NotNull;

@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
/* synthetic */ class NutritionRecord$Companion$ENERGY_TOTAL$1 extends FunctionReferenceImpl implements Function1<Double, Energy> {
    NutritionRecord$Companion$ENERGY_TOTAL$1(Object obj) {
        super(1, obj, Energy.Companion.class, "kilocalories", "kilocalories(D)Landroidx/health/connect/client/units/Energy;", 0);
    }

    @NotNull
    public final Energy invoke(double d2) {
        return ((Energy.Companion) this.receiver).kilocalories(d2);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Energy invoke(Double d2) {
        return invoke(d2.doubleValue());
    }
}
