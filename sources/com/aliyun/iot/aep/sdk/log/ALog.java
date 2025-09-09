package com.aliyun.iot.aep.sdk.log;

import android.content.Context;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.aliyun.iot.aep.sdk.log.util.ALogUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class ALog {
    public static final byte LEVEL_DEBUG = 2;
    public static final byte LEVEL_ERROR = 5;
    public static final byte LEVEL_INFO = 3;
    public static final byte LEVEL_VERBOSE = 1;
    public static final byte LEVEL_WARNING = 4;
    public static byte level = 1;
    private static IALogCloud sALogCloud = null;
    private static String sAppKey = "";
    private static String sEndPoint = null;
    static String sPostALogUrl = "http://iot-alog.aliyun.test:8080/jlog";
    private static String sProjectName = null;
    private static String sSource = "";

    public static void configALog(Context context, String str) {
        sSource = DeviceUtils.getUniqueId(context);
        sAppKey = str;
    }

    public static void configCloudLog(Context context, String str, String str2, String str3, String str4) {
        sSource = DeviceUtils.getUniqueId(context);
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.configCloudLog(str, str2, str3, str4);
            sEndPoint = str;
            sProjectName = str4;
            sSource = DeviceUtils.getUniqueId(context);
        }
    }

    public static void d(String str, String str2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.d(str, str2);
        } else {
            log((byte) 2, str, str2);
        }
    }

    public static void d_src_info(String str, String str2, String str3, String str4) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.d(str3, str4);
        } else {
            log(str, str2, (byte) 2, str3, str4);
        }
    }

    public static void e(String str, String str2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2);
        } else {
            log((byte) 5, str, str2);
        }
    }

    public static void e_src_info(String str, String str2, String str3, String str4) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str3, str4);
        } else {
            log(str, str2, (byte) 5, str3, str4);
        }
    }

    public static FormBody.Builder getCommoFormBodyBuilder() {
        return new FormBody.Builder().add("__source", sSource).add("__userkey", sAppKey);
    }

    public static String getCommonParam() {
        return "__source=" + sSource + "&__userkey=" + sAppKey + "&";
    }

    public static byte getLevel() {
        return level;
    }

    public static String getLevelStr() {
        byte b2 = level;
        return b2 != 1 ? b2 != 2 ? b2 != 3 ? b2 != 4 ? b2 != 5 ? "" : ExifInterface.LONGITUDE_EAST : ExifInterface.LONGITUDE_WEST : "I" : "D" : ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
    }

    public static String getSource() {
        return sSource;
    }

    public static String getUserKey() {
        return sAppKey;
    }

    public static void i(String str, String str2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.i(str, str2);
        } else {
            log((byte) 3, str, str2);
        }
    }

    public static void i_src_info(String str, String str2, String str3, String str4) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.i(str3, str4);
        } else {
            log(str, str2, (byte) 3, str3, str4);
        }
    }

    private static void log(byte b2, String str, String str2) {
        String str3;
        if (level > b2) {
            return;
        }
        if (b2 == 1) {
            Log.v(str, str2);
            str3 = ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        } else if (b2 == 2) {
            Log.d(str, str2);
            str3 = "D";
        } else if (b2 == 3) {
            Log.i(str, str2);
            str3 = "I";
        } else if (b2 == 4) {
            Log.w(str, str2);
            str3 = ExifInterface.LONGITUDE_WEST;
        } else if (b2 != 5) {
            str3 = "";
        } else {
            Log.e(str, str2);
            str3 = ExifInterface.LONGITUDE_EAST;
        }
        uploadToCloud(str, str2, str3);
    }

    public static void setALogCloud(IALogCloud iALogCloud) {
        sALogCloud = iALogCloud;
        iALogCloud.setLevel(level);
    }

    public static void setLevel(byte b2) {
        level = b2;
    }

    private static void uploadToCloud(String str, String str2, String str3) {
        try {
            if (sAppKey.length() != 10) {
                return;
            }
            ALogUtils.getHttpClient().newCall(new Request.Builder().url(sPostALogUrl).post(getCommoFormBodyBuilder().add(FirebaseAnalytics.Param.LEVEL, str3).add("tag", str).add("jlog", str2).build()).build()).enqueue(new Callback() { // from class: com.aliyun.iot.aep.sdk.log.ALog.1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    iOException.printStackTrace();
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) {
                    System.out.println("异步线程,线程Id为:" + Thread.currentThread().getId());
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void v(String str, String str2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.d(str, str2);
        } else {
            log((byte) 1, str, str2);
        }
    }

    public static void v_src_info(String str, String str2, String str3, String str4) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.d(str3, str4);
        } else {
            log(str, str2, (byte) 1, str3, str4);
        }
    }

    public static void w(String str, String str2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.w(str, str2);
        } else {
            log((byte) 4, str, str2);
        }
    }

    public static void w_src_info(String str, String str2, String str3, String str4) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.w(str3, str4);
        } else {
            log(str, str2, (byte) 4, str3, str4);
        }
    }

    public static void d(String str, String str2, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.d(str, str2, z2);
        } else {
            log((byte) 2, str, str2, z2);
        }
    }

    public static void e(String str, String str2, Exception exc) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2, exc);
            return;
        }
        if (exc == null) {
            StringBuilder sb = new StringBuilder();
            if (str2 == null) {
                str2 = "";
            }
            sb.append(str2);
            sb.append(" EXCEPTION: unknown");
            log((byte) 5, str, sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        if (str2 == null) {
            str2 = "";
        }
        sb2.append(str2);
        sb2.append(" EXCEPTION: ");
        sb2.append(exc.getMessage());
        log((byte) 5, str, sb2.toString());
        exc.printStackTrace();
    }

    public static void e_src_info(String str, String str2, String str3, String str4, Exception exc) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str3, str4, exc);
            return;
        }
        if (exc == null) {
            StringBuilder sb = new StringBuilder();
            if (str4 == null) {
                str4 = "";
            }
            sb.append(str4);
            sb.append(" EXCEPTION: unknown");
            log(str, str2, (byte) 5, str3, sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        if (str4 == null) {
            str4 = "";
        }
        sb2.append(str4);
        sb2.append(" EXCEPTION: ");
        sb2.append(exc.getMessage());
        log(str, str2, (byte) 5, str3, sb2.toString());
        exc.printStackTrace();
    }

    public static void i(String str, String str2, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.i(str, str2, z2);
        } else {
            log((byte) 3, str, str2, z2);
        }
    }

    private static void log(byte b2, String str, String str2, boolean z2) {
    }

    private static void uploadToCloud(String str, String str2, String str3, String str4, String str5) {
        try {
            if (sAppKey.length() != 10) {
                return;
            }
            ALogUtils.getHttpClient().newCall(new Request.Builder().url(sPostALogUrl).post(getCommoFormBodyBuilder().add("__filename", str).add("__linenum", str2).add(FirebaseAnalytics.Param.LEVEL, str3).add("tag", str4).add("jlog", str5).build()).build()).enqueue(new Callback() { // from class: com.aliyun.iot.aep.sdk.log.ALog.2
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    iOException.printStackTrace();
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) {
                    System.out.println("异步线程,线程Id为:" + Thread.currentThread().getId());
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void w(String str, String str2, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.w(str, str2, z2);
        } else {
            log((byte) 4, str, str2, z2);
        }
    }

    public static void e(String str, String str2, Exception exc, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2, exc, z2);
            return;
        }
        if (exc == null) {
            StringBuilder sb = new StringBuilder();
            if (str2 == null) {
                str2 = "";
            }
            sb.append(str2);
            sb.append(" EXCEPTION: unknown");
            log((byte) 5, str, sb.toString(), z2);
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        if (str2 == null) {
            str2 = "";
        }
        sb2.append(str2);
        sb2.append(" EXCEPTION: ");
        sb2.append(exc.getMessage());
        log((byte) 5, str, sb2.toString(), z2);
        exc.printStackTrace();
    }

    public static void e_src_info(String str, String str2, String str3, String str4, String str5) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str3, str4, str5);
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (str4 == null) {
            str4 = "";
        }
        sb.append(str4);
        sb.append(" ERROR: ");
        sb.append(str5);
        log(str, str2, (byte) 5, str3, sb.toString());
    }

    private static void log(String str, String str2, byte b2, String str3, String str4) {
        String str5;
        if (level > b2) {
            return;
        }
        if (b2 == 1) {
            Log.v(str3, str4);
            str5 = ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        } else if (b2 == 2) {
            Log.d(str3, str4);
            str5 = "D";
        } else if (b2 == 3) {
            Log.i(str3, str4);
            str5 = "I";
        } else if (b2 == 4) {
            Log.w(str3, str4);
            str5 = ExifInterface.LONGITUDE_WEST;
        } else if (b2 != 5) {
            str5 = "";
        } else {
            Log.e(str3, str4);
            str5 = ExifInterface.LONGITUDE_EAST;
        }
        uploadToCloud(str, str2, str5, str3, str4);
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
        log((byte) 5, str, sb.toString());
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
        log((byte) 5, str, sb.toString(), z2);
    }

    public static void e(String str, String str2, boolean z2) {
        IALogCloud iALogCloud = sALogCloud;
        if (iALogCloud != null) {
            iALogCloud.e(str, str2, z2);
        } else {
            log((byte) 5, str, str2, z2);
        }
    }
}
