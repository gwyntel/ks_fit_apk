package com.taobao.accs.base;

import androidx.annotation.Keep;
import com.taobao.accs.base.TaoBaseService;

@Keep
/* loaded from: classes4.dex */
public interface AccsConnectStateListener {
    @Keep
    void onConnected(TaoBaseService.ConnectInfo connectInfo);

    @Keep
    void onDisconnected(TaoBaseService.ConnectInfo connectInfo);
}
