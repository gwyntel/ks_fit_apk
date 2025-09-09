package meshprovisioner.utils;

import com.facebook.internal.AnalyticsEvents;

/* loaded from: classes5.dex */
public class ParsePublicKeyInformation {
    public static final int PUBLIC_KEY_INFORMATION_AVAILABLE = 1;
    public static final int PUBLIC_KEY_INFORMATION_UNAVAILABLE = 0;

    public static String parsePublicKeyInformation(int i2) {
        return i2 != 0 ? i2 != 1 ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : "Public key information available" : "Public key information unavailable";
    }
}
