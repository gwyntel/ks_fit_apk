package androidx.health.connect.client.records;

import androidx.health.connect.client.units.Mass;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.jetbrains.annotations.NotNull;

@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
/* synthetic */ class NutritionRecord$Companion$VITAMIN_D_TOTAL$1 extends FunctionReferenceImpl implements Function1<Double, Mass> {
    NutritionRecord$Companion$VITAMIN_D_TOTAL$1(Object obj) {
        super(1, obj, Mass.Companion.class, "grams", "grams(D)Landroidx/health/connect/client/units/Mass;", 0);
    }

    @NotNull
    public final Mass invoke(double d2) {
        return ((Mass.Companion) this.receiver).grams(d2);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Mass invoke(Double d2) {
        return invoke(d2.doubleValue());
    }
}
