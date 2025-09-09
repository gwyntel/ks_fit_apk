package meshprovisioner.utils;

import com.facebook.internal.AnalyticsEvents;

/* loaded from: classes5.dex */
public class AlgorithmInformationParser {
    public static final short FIPS_P_256_ELLIPTIC_CURVE = 1;
    public static final short NONE = 0;

    public static String parseAlgorithm(short s2) {
        return s2 != 1 ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : "FIPS P-256 Elliptic Curve";
    }
}
