package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.utils.TypedValues;

/* loaded from: classes.dex */
public abstract /* synthetic */ class c {
    static {
        String str = TypedValues.CycleType.NAME;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:4:0x000b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(java.lang.String r2) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.c.a(java.lang.String):int");
    }

    public static int b(int i2) {
        if (i2 == 100) {
            return 2;
        }
        if (i2 == 101) {
            return 8;
        }
        if (i2 == 416) {
            return 4;
        }
        if (i2 == 420 || i2 == 421) {
            return 8;
        }
        switch (i2) {
            case 304:
            case 305:
            case 306:
            case 307:
            case 308:
            case 309:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 315:
                return 4;
            default:
                switch (i2) {
                    case 401:
                    case TypedValues.CycleType.TYPE_VISIBILITY /* 402 */:
                        return 2;
                    case 403:
                        return 4;
                    default:
                        switch (i2) {
                            case TypedValues.CycleType.TYPE_WAVE_PERIOD /* 423 */:
                            case 424:
                            case TypedValues.CycleType.TYPE_WAVE_PHASE /* 425 */:
                                return 4;
                            default:
                                return -1;
                        }
                }
        }
    }
}
