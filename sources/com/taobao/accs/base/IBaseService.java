package com.taobao.accs.base;

import android.content.Intent;
import android.os.IBinder;

/* loaded from: classes4.dex */
public interface IBaseService {
    IBinder onBind(Intent intent);

    void onCreate();

    void onDestroy();

    int onStartCommand(Intent intent, int i2, int i3);

    boolean onUnbind(Intent intent);
}
