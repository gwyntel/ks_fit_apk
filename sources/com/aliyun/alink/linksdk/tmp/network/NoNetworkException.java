package com.aliyun.alink.linksdk.tmp.network;

import android.content.Context;
import android.widget.Toast;

/* loaded from: classes2.dex */
public class NoNetworkException extends BaseException {
    public static final String NETWORK_UNCONNECTED = "未连接网络，请检查网络设置";
    public static NoNetworkHanler mNoNetworkHanler;

    public interface NoNetworkHanler {
        boolean handle(Context context);
    }

    public NoNetworkException() {
    }

    public static void setNoNetworkHanler(NoNetworkHanler noNetworkHanler) {
        mNoNetworkHanler = noNetworkHanler;
    }

    @Override // com.aliyun.alink.linksdk.tmp.network.BaseException
    public boolean handle(Context context) {
        NoNetworkHanler noNetworkHanler = mNoNetworkHanler;
        if (noNetworkHanler != null) {
            return noNetworkHanler.handle(context);
        }
        Toast.makeText(context, NETWORK_UNCONNECTED, 0).show();
        return true;
    }

    public NoNetworkException(String str) {
        super(str);
    }

    public NoNetworkException(Throwable th) {
        super(th);
    }

    public NoNetworkException(int i2, String str, Throwable th) {
        super(i2, str, th);
    }

    public NoNetworkException(int i2, String str) {
        super(i2, str);
    }

    public NoNetworkException(String str, Throwable th) {
        super(str, th);
    }
}
