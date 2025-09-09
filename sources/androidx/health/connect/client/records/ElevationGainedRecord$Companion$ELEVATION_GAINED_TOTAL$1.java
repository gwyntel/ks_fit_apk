package androidx.health.connect.client.records;

import androidx.health.connect.client.units.Length;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.jetbrains.annotations.NotNull;

@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
/* synthetic */ class ElevationGainedRecord$Companion$ELEVATION_GAINED_TOTAL$1 extends FunctionReferenceImpl implements Function1<Double, Length> {
    ElevationGainedRecord$Companion$ELEVATION_GAINED_TOTAL$1(Object obj) {
        super(1, obj, Length.Companion.class, "meters", "meters(D)Landroidx/health/connect/client/units/Length;", 0);
    }

    @NotNull
    public final Length invoke(double d2) {
        return ((Length.Companion) this.receiver).meters(d2);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Length invoke(Double d2) {
        return invoke(d2.doubleValue());
    }
}
