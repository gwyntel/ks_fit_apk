package com.alibaba.sdk.android.openaccount.trace;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.sdk.android.openaccount.message.Message;

/* loaded from: classes2.dex */
public class AliSDKLogger {
    public static void d(String str, String str2) {
        TraceLoggerManager.INSTANCE.log(str, 3, null, null, str2);
    }

    public static void e(String str, String str2, String str3, String str4) {
        TraceLoggerManager.INSTANCE.log(str, 6, str2, str3, str4);
    }

    public static void i(String str, String str2) {
        TraceLoggerManager.INSTANCE.log(str, 4, null, null, str2);
    }

    public static boolean isDebugEnabled() {
        return TraceLoggerManager.INSTANCE.getLogLevel() <= 3;
    }

    public static void log(String str, Message message, Throwable th) {
        if (isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("***********************************************************\n");
            sb.append("错误编码 = ");
            sb.append(message.code);
            sb.append("\n");
            sb.append("错误消息 = ");
            sb.append(message.message);
            sb.append("\n");
            sb.append("解决建议 = ");
            sb.append(message.action);
            sb.append("\n");
            if (th != null) {
                sb.append("错误堆栈 = ");
                sb.append(Log.getStackTraceString(th));
                sb.append("\n");
            }
            sb.append("***********************************************************\n");
            String str2 = message.type;
            if ("D".equals(str2)) {
                d(str, sb.toString());
                return;
            }
            if (ExifInterface.LONGITUDE_EAST.equals(str2)) {
                e(str, sb.toString());
            } else if (ExifInterface.LONGITUDE_WEST.equals(str2)) {
                w(str, sb.toString());
            } else {
                i(str, sb.toString());
            }
        }
    }

    public static void printStackTraceAndMore(Throwable th) {
        TraceLoggerManager.INSTANCE.log(6, null, null, Log.getStackTraceString(th));
    }

    public static void w(String str, String str2) {
        TraceLoggerManager.INSTANCE.log(str, 5, null, null, str2);
    }

    public static void d(String str, String str2, String str3, String str4) {
        TraceLoggerManager.INSTANCE.log(str, 3, str2, str3, str4);
    }

    public static void e(String str, String str2, Throwable th) {
        TraceLoggerManager.INSTANCE.log(str, 6, null, null, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static void w(String str, String str2, String str3, String str4) {
        TraceLoggerManager.INSTANCE.log(str, 5, str2, str3, str4);
    }

    public static void e(String str, String str2) {
        TraceLoggerManager.INSTANCE.log(str, 6, null, null, str2);
    }

    public static void log(String str, Message message) {
        log(str, message, null);
    }
}
