package meshprovisioner.utils;

import com.facebook.internal.AnalyticsEvents;

/* loaded from: classes5.dex */
public class ParseStaticOutputOOBInformation {
    public static final int STATIC_OOB_INFO_AVAILABLE = 1;
    public static final int STATIC_OOB_INFO_UNAVAILABLE = 0;

    public static String parseStaticOOBActionInformation(int i2) {
        return i2 != 0 ? i2 != 1 ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : "Static OOB Actions available" : "Static OOB Actions unavailable";
    }
}
