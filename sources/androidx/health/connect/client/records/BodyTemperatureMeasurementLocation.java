package androidx.health.connect.client.records;

import androidx.annotation.RestrictTo;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u000f\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00040\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Landroidx/health/connect/client/records/BodyTemperatureMeasurementLocation;", "", "()V", "ARMPIT", "", "EAR", "FINGER", "FOREHEAD", "MEASUREMENT_LOCATION_ARMPIT", "", "MEASUREMENT_LOCATION_EAR", "MEASUREMENT_LOCATION_FINGER", "MEASUREMENT_LOCATION_FOREHEAD", "MEASUREMENT_LOCATION_INT_TO_STRING_MAP", "", "MEASUREMENT_LOCATION_MOUTH", "MEASUREMENT_LOCATION_RECTUM", "MEASUREMENT_LOCATION_STRING_TO_INT_MAP", "MEASUREMENT_LOCATION_TEMPORAL_ARTERY", "MEASUREMENT_LOCATION_TOE", "MEASUREMENT_LOCATION_UNKNOWN", "MEASUREMENT_LOCATION_VAGINA", "MEASUREMENT_LOCATION_WRIST", "MOUTH", "RECTUM", "TEMPORAL_ARTERY", "TOE", "VAGINA", "WRIST", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BodyTemperatureMeasurementLocation {

    @NotNull
    public static final String ARMPIT = "armpit";

    @NotNull
    public static final String EAR = "ear";

    @NotNull
    public static final String FINGER = "finger";

    @NotNull
    public static final String FOREHEAD = "forehead";

    @NotNull
    public static final BodyTemperatureMeasurementLocation INSTANCE = new BodyTemperatureMeasurementLocation();
    public static final int MEASUREMENT_LOCATION_ARMPIT = 1;
    public static final int MEASUREMENT_LOCATION_EAR = 8;
    public static final int MEASUREMENT_LOCATION_FINGER = 2;
    public static final int MEASUREMENT_LOCATION_FOREHEAD = 3;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<Integer, String> MEASUREMENT_LOCATION_INT_TO_STRING_MAP;
    public static final int MEASUREMENT_LOCATION_MOUTH = 4;
    public static final int MEASUREMENT_LOCATION_RECTUM = 5;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<String, Integer> MEASUREMENT_LOCATION_STRING_TO_INT_MAP;
    public static final int MEASUREMENT_LOCATION_TEMPORAL_ARTERY = 6;
    public static final int MEASUREMENT_LOCATION_TOE = 7;
    public static final int MEASUREMENT_LOCATION_UNKNOWN = 0;
    public static final int MEASUREMENT_LOCATION_VAGINA = 10;
    public static final int MEASUREMENT_LOCATION_WRIST = 9;

    @NotNull
    public static final String MOUTH = "mouth";

    @NotNull
    public static final String RECTUM = "rectum";

    @NotNull
    public static final String TEMPORAL_ARTERY = "temporal_artery";

    @NotNull
    public static final String TOE = "toe";

    @NotNull
    public static final String VAGINA = "vagina";

    @NotNull
    public static final String WRIST = "wrist";

    static {
        Map<String, Integer> mapMapOf = MapsKt.mapOf(TuplesKt.to(ARMPIT, 1), TuplesKt.to(FINGER, 2), TuplesKt.to(FOREHEAD, 3), TuplesKt.to(MOUTH, 4), TuplesKt.to(RECTUM, 5), TuplesKt.to(TEMPORAL_ARTERY, 6), TuplesKt.to(TOE, 7), TuplesKt.to(EAR, 8), TuplesKt.to(WRIST, 9), TuplesKt.to(VAGINA, 10));
        MEASUREMENT_LOCATION_STRING_TO_INT_MAP = mapMapOf;
        MEASUREMENT_LOCATION_INT_TO_STRING_MAP = UtilsKt.reverse(mapMapOf);
    }

    private BodyTemperatureMeasurementLocation() {
    }
}
