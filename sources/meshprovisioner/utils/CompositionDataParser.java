package meshprovisioner.utils;

import java.util.Locale;

/* loaded from: classes5.dex */
public class CompositionDataParser {
    public static String formatCompanyIdentifier(int i2, boolean z2) {
        if (!z2) {
            return String.format(Locale.US, "%04X", Integer.valueOf(i2));
        }
        return "0x" + String.format(Locale.US, "%04X", Integer.valueOf(i2));
    }

    public static String formatFeatures(int i2, boolean z2) {
        if (!z2) {
            return String.format(Locale.US, "%04X", Integer.valueOf(i2));
        }
        return "0x" + String.format(Locale.US, "%04X", Integer.valueOf(i2));
    }

    public static String formatModelIdentifier(int i2, boolean z2) {
        if (i2 < -32768 || i2 > 32767) {
            if (!z2) {
                return String.format(Locale.US, "%08X", Integer.valueOf(i2));
            }
            return "0x" + String.format(Locale.US, "%08X", Integer.valueOf(i2));
        }
        if (!z2) {
            return String.format(Locale.US, "%04X", Integer.valueOf(i2));
        }
        return "0x" + String.format(Locale.US, "%04X", Integer.valueOf(i2));
    }

    public static String formatProductIdentifier(int i2, boolean z2) {
        if (!z2) {
            return String.format(Locale.US, "%04X", Integer.valueOf(i2));
        }
        return "0x" + String.format(Locale.US, "%04X", Integer.valueOf(i2));
    }

    public static String formatReplayProtectionCount(int i2, boolean z2) {
        if (!z2) {
            return String.format(Locale.US, "%04X", Integer.valueOf(i2));
        }
        return "0x" + String.format(Locale.US, "%04X", Integer.valueOf(i2));
    }

    public static String formatVersionIdentifier(int i2, boolean z2) {
        if (!z2) {
            return String.format(Locale.US, "%04X", Integer.valueOf(i2));
        }
        return "0x" + String.format(Locale.US, "%04X", Integer.valueOf(i2));
    }
}
