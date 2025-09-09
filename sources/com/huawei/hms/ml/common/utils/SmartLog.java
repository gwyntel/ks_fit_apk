package com.huawei.hms.ml.common.utils;

import android.text.TextUtils;
import android.util.Log;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class SmartLog {
    private static final int LEN_CONST = 2;
    private static final Pattern M_PATTERN = Pattern.compile("[0-9]*[a-z|A-Z]*[一-龥]*");
    private static final char STAR = '*';

    private static class ThrowableWrapper extends Throwable {
        private static final long serialVersionUID = 7129050843360571879L;
        private String message;
        private Throwable ownerThrowable;
        private Throwable thisCause;

        public ThrowableWrapper(Throwable th) {
            this.ownerThrowable = th;
        }

        @Override // java.lang.Throwable
        public Throwable getCause() {
            Throwable th = this.thisCause;
            if (th == this) {
                return null;
            }
            return th;
        }

        @Override // java.lang.Throwable
        public String getMessage() {
            return this.message;
        }

        public void setCause(Throwable th) {
            this.thisCause = th;
        }

        public void setMessage(String str) {
            this.message = str;
        }

        @Override // java.lang.Throwable
        public String toString() {
            Throwable th = this.ownerThrowable;
            if (th == null) {
                return "";
            }
            String name = th.getClass().getName();
            if (this.message == null) {
                return name;
            }
            String str = name + ": ";
            if (this.message.startsWith(str)) {
                return this.message;
            }
            return str + this.message;
        }
    }

    public static void d(String str, String str2) {
    }

    public static void e(String str, String str2, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.e(str, getLogMsg(str2, z2));
    }

    private static String formatLogWithStar(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int length = str.length();
        int i2 = 1;
        if (1 == length) {
            return String.valueOf(STAR);
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (M_PATTERN.matcher(String.valueOf(cCharAt)).matches()) {
                if (i2 % 2 == 0) {
                    cCharAt = '*';
                }
                i2++;
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    private static String getLogMsg(String str, boolean z2) {
        StringBuilder sb = new StringBuilder(512);
        if (!TextUtils.isEmpty(str)) {
            if (z2) {
                sb.append(formatLogWithStar(str));
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    private static Throwable getNewThrowable(Throwable th) {
        if (th == null) {
            return null;
        }
        ThrowableWrapper throwableWrapper = new ThrowableWrapper(th);
        throwableWrapper.setStackTrace(th.getStackTrace());
        throwableWrapper.setMessage(modifyExceptionMessage(th.getMessage()));
        Throwable cause = th.getCause();
        ThrowableWrapper throwableWrapper2 = throwableWrapper;
        while (cause != null) {
            ThrowableWrapper throwableWrapper3 = new ThrowableWrapper(cause);
            throwableWrapper3.setStackTrace(cause.getStackTrace());
            throwableWrapper3.setMessage(modifyExceptionMessage(cause.getMessage()));
            throwableWrapper2.setCause(throwableWrapper3);
            cause = cause.getCause();
            throwableWrapper2 = throwableWrapper3;
        }
        return throwableWrapper;
    }

    public static void i(String str, String str2, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.i(str, getLogMsg(str2, z2));
    }

    private static String modifyExceptionMessage(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (i2 % 2 == 0) {
                charArray[i2] = STAR;
            }
        }
        return new String(charArray);
    }

    public static void w(String str, String str2, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.w(str, getLogMsg(str2, z2));
    }

    public static void d(String str, String str2, String str3) {
    }

    public static void d(String str, String str2, String str3, Throwable th) {
    }

    public static void e(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.e(str, getLogMsg(str2, str3));
    }

    public static void i(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.i(str, getLogMsg(str2, str3));
    }

    public static void w(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.w(str, getLogMsg(str2, str3));
    }

    public static void d(String str, String str2, Throwable th) {
    }

    public static void d(String str, String str2, Throwable th, boolean z2) {
    }

    public static void e(String str, String str2, String str3, Throwable th) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.e(str, getLogMsg(str2, str3), getNewThrowable(th));
    }

    public static void i(String str, String str2, String str3, Throwable th) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.i(str, getLogMsg(str2, str3), getNewThrowable(th));
    }

    public static void w(String str, String str2, String str3, Throwable th) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.w(str, getLogMsg(str2, str3), getNewThrowable(th));
    }

    public static void d(String str, String str2, boolean z2) {
    }

    private static String getLogMsg(String str, String str2) {
        StringBuilder sb = new StringBuilder(512);
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            sb.append(formatLogWithStar(str2));
        }
        return sb.toString();
    }

    public static void e(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.e(str, getLogMsg(str2, false));
    }

    public static void i(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.i(str, getLogMsg(str2, false));
    }

    public static void w(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.w(str, getLogMsg(str2, false));
    }

    public static void e(String str, String str2, Throwable th, boolean z2) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.e(str, getLogMsg(str2, z2), getNewThrowable(th));
    }

    public static void i(String str, String str2, Throwable th, boolean z2) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.i(str, getLogMsg(str2, z2), getNewThrowable(th));
    }

    public static void w(String str, String str2, Throwable th, boolean z2) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.w(str, getLogMsg(str2, z2), getNewThrowable(th));
    }

    public static void e(String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.e(str, getLogMsg(str2, false), getNewThrowable(th));
    }

    public static void i(String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.i(str, getLogMsg(str2, false), getNewThrowable(th));
    }

    public static void w(String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.w(str, getLogMsg(str2, false), getNewThrowable(th));
    }
}
