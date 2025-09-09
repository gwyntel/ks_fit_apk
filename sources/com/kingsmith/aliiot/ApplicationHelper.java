package com.kingsmith.aliiot;

import android.content.Context;
import com.aliyun.iot.aep.sdk.framework.AApplication;

/* loaded from: classes4.dex */
public class ApplicationHelper extends AApplication {
    @Override // androidx.multidex.MultiDexApplication, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // com.aliyun.iot.aep.sdk.framework.AApplication, android.app.Application
    public void onCreate() {
        super.onCreate();
    }
}
