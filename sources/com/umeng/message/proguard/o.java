package com.umeng.message.proguard;

import anet.channel.util.ALog;
import com.taobao.accs.utl.ALog;
import com.umeng.message.common.UPLog;
import java.net.SocketTimeoutException;

/* loaded from: classes4.dex */
public final class o implements ALog.ILog, ALog.ILog {
    @Override // anet.channel.util.ALog.ILog
    public final void d(String str, String str2) {
        UPLog.d(str, str2);
    }

    @Override // anet.channel.util.ALog.ILog
    public final void e(String str, String str2) {
        UPLog.e(str, str2);
    }

    @Override // anet.channel.util.ALog.ILog
    public final void i(String str, String str2) {
        UPLog.i(str, str2);
    }

    @Override // anet.channel.util.ALog.ILog
    public final boolean isPrintLog(int i2) {
        return true;
    }

    @Override // anet.channel.util.ALog.ILog
    public final boolean isValid() {
        return true;
    }

    @Override // anet.channel.util.ALog.ILog
    public final void setLogLevel(int i2) {
    }

    @Override // anet.channel.util.ALog.ILog
    public final void w(String str, String str2) {
        UPLog.w(str, str2);
    }

    @Override // anet.channel.util.ALog.ILog
    public final void e(String str, String str2, Throwable th) {
        if (th instanceof SocketTimeoutException) {
            UPLog.i(str, str2 + " " + th.getMessage());
            return;
        }
        UPLog.d(str, str2, UPLog.getStackTrace(th));
        UPLog.e(str, str2 + " " + th.getMessage());
    }

    @Override // anet.channel.util.ALog.ILog
    public final void w(String str, String str2, Throwable th) {
        UPLog.d(str, str2, UPLog.getStackTrace(th));
        UPLog.w(str, str2 + " " + th.getMessage());
    }
}
