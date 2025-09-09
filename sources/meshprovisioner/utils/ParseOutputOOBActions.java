package meshprovisioner.utils;

import a.a.a.a.b.m.a;
import com.facebook.internal.AnalyticsEvents;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ParseOutputOOBActions {
    public static final short BEEP = 2;
    public static final short BLINK = 1;
    public static final short NO_OUTPUT = 0;
    public static final short OUTPUT_ALPHA_NUMERIC = 16;
    public static final short OUTPUT_NUMERIC = 8;
    public static final String TAG = "ParseOutputOOBActions";
    public static final short VIBRATE = 4;

    public static String getOuputOOBActionDescription(short s2) {
        return s2 != 0 ? s2 != 1 ? s2 != 2 ? s2 != 4 ? s2 != 8 ? s2 != 16 ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : "Output Alpha Numeric" : "Output Numeric" : "Vibrate" : "Beep" : "Blink" : "Not Supported";
    }

    public static int getOuputOOBActionValue(short s2) {
        if (s2 == 1) {
            return 0;
        }
        if (s2 == 2) {
            return 1;
        }
        if (s2 == 4) {
            return 2;
        }
        if (s2 != 8) {
            return s2 != 16 ? 0 : 4;
        }
        return 3;
    }

    public static int parseOuputOOBActionValue(int i2) {
        if (i2 == 0) {
            return 0;
        }
        int i3 = 1;
        if (i2 != 1) {
            i3 = 2;
            if (i2 != 2) {
                if (i2 == 4) {
                    return 3;
                }
                if (i2 != 8) {
                    return i2 != 16 ? -1 : 10;
                }
                return 4;
            }
        }
        return i3;
    }

    public static void parseOutputActionsFromBitMask(int i2) {
        byte[] bArr = {1, 2, 4, 8, 16};
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < 5; i3++) {
            byte b2 = bArr[i3];
            if ((i2 & b2) == b2) {
                arrayList.add(Byte.valueOf(b2));
                a.a(TAG, "Supported output oob action type: " + getOuputOOBActionDescription(b2));
            }
        }
    }

    public static short selectOutputActionsFromBitMask(int i2) {
        byte[] bArr = {1, 2, 4, 8, 16};
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < 5; i3++) {
            byte b2 = bArr[i3];
            if ((i2 & b2) == b2) {
                arrayList.add(Byte.valueOf(b2));
                a.a(TAG, "Supported output oob action type: " + getOuputOOBActionDescription(b2));
            }
        }
        if (arrayList.isEmpty()) {
            return (short) 0;
        }
        return ((Byte) arrayList.get(0)).byteValue();
    }
}
