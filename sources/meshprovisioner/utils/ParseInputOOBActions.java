package meshprovisioner.utils;

import a.a.a.a.b.m.a;
import com.facebook.internal.AnalyticsEvents;

/* loaded from: classes5.dex */
public class ParseInputOOBActions {
    public static final short INPUT_ALPHA_NUMBERIC = 8;
    public static final short INPUT_NUMBER = 4;
    public static final short NO_INPUT = 0;
    public static final short PUSH = 1;
    public static final String TAG = "ParseInputOOBActions";
    public static final short TWIST = 2;

    public static String getInputOOBActionDescription(short s2) {
        return s2 != 0 ? s2 != 1 ? s2 != 2 ? s2 != 4 ? s2 != 8 ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : "Input Alpha Numeric" : "Input Number" : "Twist" : "Push" : "Not supported";
    }

    public static int getOuputOOBActionValue(short s2) {
        if (s2 == 1) {
            return 0;
        }
        if (s2 == 2) {
            return 1;
        }
        if (s2 != 4) {
            return s2 != 8 ? -1 : 3;
        }
        return 2;
    }

    public static void parseInputActionsFromBitMask(int i2) {
        byte[] bArr = {1, 2, 4, 8};
        for (int i3 = 0; i3 < 4; i3++) {
            byte b2 = bArr[i3];
            if ((i2 & b2) == b2) {
                a.a(TAG, "Input oob action type value: " + getInputOOBActionDescription(b2));
            }
        }
    }

    public static int parseInputOOBActionValue(int i2) {
        if (i2 == 0) {
            return 0;
        }
        int i3 = 1;
        if (i2 != 1) {
            i3 = 2;
            if (i2 != 2) {
                i3 = 4;
                if (i2 != 4) {
                    i3 = 8;
                    if (i2 != 8) {
                        return -1;
                    }
                }
            }
        }
        return i3;
    }
}
