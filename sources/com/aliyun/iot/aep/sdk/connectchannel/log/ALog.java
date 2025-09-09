package com.aliyun.iot.aep.sdk.connectchannel.log;

import android.util.Log;

/* loaded from: classes3.dex */
public class ALog {
    public static final byte LEVEL_DEBUG = 1;
    public static final byte LEVEL_ERROR = 4;
    public static final byte LEVEL_INFO = 2;
    public static final byte LEVEL_WARNING = 3;
    private static byte level = 3;
    private static IALogCloud sALogCloud;

    public static void configCloudLog(String str, String str2, String str3, String str4) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.configCloudLog(str, str2, str3, str4);
        }
    }

    public static void d(String str, String str2, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.d(str, str2, z2);
        } else {
            log((byte) 1, str, str2, z2);
        }
    }

    public static void e(String str, String str2, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2, z2);
        } else {
            log((byte) 4, str, str2, z2);
        }
    }

    public static byte getLevel() {
        return level;
    }

    public static void i(String str, String str2, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.i(str, str2, z2);
        } else {
            log((byte) 2, str, str2, z2);
        }
    }

    private static void log(byte b2, String str, String str2, boolean z2) {
        if (level > b2) {
            return;
        }
        if (b2 == 1) {
            Log.d(str, str2);
            return;
        }
        if (b2 == 2) {
            Log.i(str, str2);
        } else if (b2 == 3) {
            Log.w(str, str2);
        } else {
            if (b2 != 4) {
                return;
            }
            Log.e(str, str2);
        }
    }

    public static void setALogCloud(IALogCloud iALogCloud) {
        sALogCloud = iALogCloud;
        iALogCloud.setLevel(level);
    }

    public static void setLevel(byte b2) {
        level = b2;
    }

    public static void w(String str, String str2, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.w(str, str2, z2);
        } else {
            log((byte) 3, str, str2, z2);
        }
    }

    public static void d(String str, String str2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.d(str, str2);
        } else {
            log((byte) 1, str, str2);
        }
    }

    public static void e(String str, String str2, String str3, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2, str3, z2);
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (str2 == null) {
            str2 = "";
        }
        sb.append(str2);
        sb.append(" ERROR: ");
        sb.append(str3);
        log((byte) 4, str, sb.toString(), z2);
    }

    public static void i(String str, String str2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.i(str, str2);
        } else {
            log((byte) 2, str, str2);
        }
    }

    public static void w(String str, String str2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.w(str, str2);
        } else {
            log((byte) 3, str, str2);
        }
    }

    private static void log(byte b2, String str, String str2) {
        if (level > b2) {
            return;
        }
        if (b2 == 1) {
            Log.d(str, str2);
            return;
        }
        if (b2 == 2) {
            Log.i(str, str2);
        } else if (b2 == 3) {
            Log.w(str, str2);
        } else {
            if (b2 != 4) {
                return;
            }
            Log.e(str, str2);
        }
    }

    public static void e(String str, String str2, Exception exc, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2, exc, z2);
            return;
        }
        if (exc != null) {
            StringBuilder sb = new StringBuilder();
            if (str2 == null) {
                str2 = "";
            }
            sb.append(str2);
            sb.append(" EXCEPTION: ");
            sb.append(exc.getMessage());
            log((byte) 4, str, sb.toString(), z2);
            exc.printStackTrace();
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        if (str2 == null) {
            str2 = "";
        }
        sb2.append(str2);
        sb2.append(" EXCEPTION: unknown");
        log((byte) 4, str, sb2.toString(), z2);
    }

    public static void e(String str, String str2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2);
        } else {
            log((byte) 4, str, str2);
        }
    }

    public static void e(String str, String str2, String str3) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2, str3);
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (str2 == null) {
            str2 = "";
        }
        sb.append(str2);
        sb.append(" ERROR: ");
        sb.append(str3);
        log((byte) 4, str, sb.toString());
    }

    public static void e(String str, String str2, Exception exc) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2, exc);
            return;
        }
        if (exc != null) {
            StringBuilder sb = new StringBuilder();
            if (str2 == null) {
                str2 = "";
            }
            sb.append(str2);
            sb.append(" EXCEPTION: ");
            sb.append(exc.getMessage());
            log((byte) 4, str, sb.toString());
            exc.printStackTrace();
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        if (str2 == null) {
            str2 = "";
        }
        sb2.append(str2);
        sb2.append(" EXCEPTION: unknown");
        log((byte) 4, str, sb2.toString());
    }
}
