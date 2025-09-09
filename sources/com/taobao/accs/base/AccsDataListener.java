package com.taobao.accs.base;

import androidx.annotation.Keep;
import com.taobao.accs.base.TaoBaseService;

/* loaded from: classes4.dex */
public interface AccsDataListener {
    @Keep
    void onAntiBrush(boolean z2, TaoBaseService.ExtraInfo extraInfo);

    @Keep
    void onBind(String str, int i2, TaoBaseService.ExtraInfo extraInfo);

    @Keep
    void onConnected(TaoBaseService.ConnectInfo connectInfo);

    @Keep
    void onData(String str, String str2, String str3, byte[] bArr, TaoBaseService.ExtraInfo extraInfo);

    @Keep
    void onDisconnected(TaoBaseService.ConnectInfo connectInfo);

    @Keep
    void onResponse(String str, String str2, int i2, byte[] bArr, TaoBaseService.ExtraInfo extraInfo);

    @Keep
    void onSendData(String str, String str2, int i2, TaoBaseService.ExtraInfo extraInfo);

    @Keep
    void onUnbind(String str, int i2, TaoBaseService.ExtraInfo extraInfo);
}
