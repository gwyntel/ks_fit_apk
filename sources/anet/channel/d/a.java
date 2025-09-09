package anet.channel.d;

import anet.channel.util.ALog;
import com.taobao.tlog.adapter.AdapterForTLog;

/* loaded from: classes2.dex */
public class a implements ALog.ILog {
    private int a(char c2) {
        if (c2 == 'D') {
            return 1;
        }
        if (c2 == 'E') {
            return 4;
        }
        if (c2 == 'I') {
            return 2;
        }
        if (c2 != 'V') {
            return c2 != 'W' ? 5 : 3;
        }
        return 0;
    }

    @Override // anet.channel.util.ALog.ILog
    public void d(String str, String str2) {
        AdapterForTLog.logd(str, str2);
    }

    @Override // anet.channel.util.ALog.ILog
    public void e(String str, String str2) {
        AdapterForTLog.loge(str, str2);
    }

    @Override // anet.channel.util.ALog.ILog
    public void i(String str, String str2) {
        AdapterForTLog.logi(str, str2);
    }

    @Override // anet.channel.util.ALog.ILog
    public boolean isPrintLog(int i2) {
        return i2 >= a(AdapterForTLog.getLogLevel().charAt(0));
    }

    @Override // anet.channel.util.ALog.ILog
    public boolean isValid() {
        return AdapterForTLog.isValid();
    }

    @Override // anet.channel.util.ALog.ILog
    public void setLogLevel(int i2) {
    }

    @Override // anet.channel.util.ALog.ILog
    public void w(String str, String str2) {
        AdapterForTLog.logw(str, str2);
    }

    @Override // anet.channel.util.ALog.ILog
    public void e(String str, String str2, Throwable th) {
        AdapterForTLog.loge(str, str2, th);
    }

    @Override // anet.channel.util.ALog.ILog
    public void w(String str, String str2, Throwable th) {
        AdapterForTLog.logw(str, str2, th);
    }
}
