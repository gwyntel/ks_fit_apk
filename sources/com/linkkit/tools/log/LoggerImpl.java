package com.linkkit.tools.log;

import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;

/* loaded from: classes4.dex */
public class LoggerImpl implements ILogger {
    private int logLevel = 3;
    private final String preTag;

    public LoggerImpl(String str) {
        this.preTag = str;
    }

    private void log(int i2, String str, String str2, Throwable th) {
        if (i2 >= this.logLevel) {
            Log.println(i2, this.preTag + str, str2 + "\n" + Log.getStackTraceString(th));
        }
    }

    @Override // com.linkkit.tools.log.ILogger
    public void d(String str, String str2) {
        log(3, str, str2, null);
    }

    @Override // com.linkkit.tools.log.ILogger
    public void e(String str, String str2) {
        log(6, str, str2, null);
    }

    @Override // com.linkkit.tools.log.ILogger
    public void i(String str, String str2) {
        log(4, str, str2, null);
    }

    @Override // com.linkkit.tools.log.ILogger
    public void llog(int i2, String str, String str2, Throwable th) {
        if (str2 != null) {
            int length = str2.length();
            int i3 = 0;
            while (i3 < length - 900) {
                int i4 = i3 + TypedValues.Custom.TYPE_INT;
                log(i2, str, str2.substring(i3, i4), null);
                i3 = i4;
            }
            log(i2, str, str2.substring(i3), null);
        }
    }

    @Override // com.linkkit.tools.log.ILogger
    public void setLogLevel(int i2) {
        if (i2 < 4) {
            this.logLevel = i2;
        } else if (i2 > 6) {
            this.logLevel = 6;
        } else {
            this.logLevel = i2;
        }
    }

    @Override // com.linkkit.tools.log.ILogger
    public void w(String str, String str2) {
        log(5, str, str2, null);
    }

    @Override // com.linkkit.tools.log.ILogger
    public void e(String str, String str2, Throwable th) {
        log(6, str, str2, th);
    }

    @Override // com.linkkit.tools.log.ILogger
    public void w(String str, String str2, Throwable th) {
        log(5, str, str2, th);
    }
}
