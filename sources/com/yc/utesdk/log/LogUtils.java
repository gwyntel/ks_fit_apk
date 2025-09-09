package com.yc.utesdk.log;

import android.text.TextUtils;
import android.util.Log;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class LogUtils {
    private static boolean DEBUG = true;
    private static boolean PRINT = false;
    private static final String TAG = "LogUtils";
    private static int curDay = 0;
    private static String fileName = null;
    private static int lastDay = 0;
    private static String mRootPath = "";

    public class utendo implements Runnable {
        public final /* synthetic */ Object utendo;
        public final /* synthetic */ String utenif;

        public utendo(Object obj, String str) {
            this.utendo = obj;
            this.utenif = str;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            if (TextUtils.isEmpty(LogUtils.fileName)) {
                String unused = LogUtils.fileName = LogUtils.getLogPath() + "/" + CalendarUtils.getCalendar(0) + ".log";
                int unused2 = LogUtils.lastDay = Calendar.getInstance().get(6);
            } else if (LogUtils.utenfor()) {
                String unused3 = LogUtils.fileName = LogUtils.getLogPath() + "/" + CalendarUtils.getCalendar(0) + ".log";
            }
            Object obj = this.utendo;
            String str = this.utenif + ": " + (obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
            if (TextUtils.isEmpty(LogUtils.fileName)) {
                return;
            }
            LogUtils.utenif(LogUtils.fileName, str);
        }
    }

    public static void d(Object obj) {
        if (DEBUG) {
            Log.d(TAG, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(TAG, obj);
    }

    public static void e(Object obj) {
        if (DEBUG) {
            Log.e(TAG, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(TAG, obj);
    }

    public static boolean getLogEnable() {
        return DEBUG;
    }

    public static String getLogPath() {
        return mRootPath + "/LogOther";
    }

    public static boolean getPrintEnable() {
        return PRINT;
    }

    public static String getRootPath() {
        return mRootPath;
    }

    public static void i(Object obj) {
        if (DEBUG) {
            Log.i(TAG, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(TAG, obj);
    }

    public static void initializeLog(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("You should Call ");
        sb.append(obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        sb.append(" first!");
        Log.e(TAG, sb.toString());
        utendo(TAG, obj);
    }

    public static void setLogEnable(boolean z2) {
        DEBUG = z2;
    }

    public static void setPrintEnable(boolean z2) {
        setPrintEnable(z2, mRootPath);
    }

    public static boolean utenfor() {
        int i2 = Calendar.getInstance().get(6);
        curDay = i2;
        if (i2 == lastDay) {
            return false;
        }
        lastDay = i2;
        return true;
    }

    public static void v(Object obj) {
        if (DEBUG) {
            Log.v(TAG, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(TAG, obj);
    }

    public static void w(Object obj) {
        if (DEBUG) {
            Log.w(TAG, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(TAG, obj);
    }

    public static void d(String str, Object obj) {
        if (DEBUG) {
            Log.d(str, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(str, obj);
    }

    public static void e(String str, Object obj) {
        if (DEBUG) {
            Log.e(str, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(str, obj);
    }

    public static void i(String str, Object obj) {
        if (DEBUG) {
            Log.i(str, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(str, obj);
    }

    public static void setPrintEnable(boolean z2, String str) {
        PRINT = z2;
        if (z2) {
            if (TextUtils.isEmpty(str)) {
                mRootPath = UteBleClient.getContext().getExternalCacheDir() + "/utesdk/logs/AllLogs";
            } else {
                mRootPath = str;
            }
            LogShareUtils.getInstance();
        }
    }

    public static void utenfor(String str, String str2) throws IOException {
        try {
            String str3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.US).format(new Date());
            FileWriter fileWriter = new FileWriter(str, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(str3 + " " + str2);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException unused) {
        }
    }

    public static void utenif(String str, String str2) throws IOException {
        utenfor(str, str2);
    }

    public static void v(String str, Object obj) {
        if (DEBUG) {
            Log.v(str, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(str, obj);
    }

    public static void w(String str, Object obj) {
        if (DEBUG) {
            Log.w(str, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
        utendo(str, obj);
    }

    public static void setPrintEnable(boolean z2, String str, String str2) {
        PRINT = z2;
        if (z2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                mRootPath = UteBleClient.getContext().getExternalCacheDir() + "/utesdk/logs/AllLogs";
            } else {
                mRootPath = str;
                fileName = str + "/" + str2;
            }
            LogShareUtils.getInstance();
        }
    }

    public static void utendo(String str, Object obj) {
        if (PRINT) {
            new Thread(new utendo(obj, str)).start();
        }
    }
}
