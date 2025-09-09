package androidx.health.connect.client.records;

import androidx.health.connect.client.units.Velocity;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.jetbrains.annotations.NotNull;

@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
/* synthetic */ class SpeedRecord$Companion$SPEED_AVG$1 extends FunctionReferenceImpl implements Function1<Double, Velocity> {
    SpeedRecord$Companion$SPEED_AVG$1(Object obj) {
        super(1, obj, Velocity.Companion.class, "metersPerSecond", "metersPerSecond(D)Landroidx/health/connect/client/units/Velocity;", 0);
    }

    @NotNull
    public final Velocity invoke(double d2) {
        return ((Velocity.Companion) this.receiver).metersPerSecond(d2);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Velocity invoke(Double d2) {
        return invoke(d2.doubleValue());
    }
}
