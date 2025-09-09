package com.umeng.ccg;

import android.content.Context;

/* loaded from: classes4.dex */
public interface ActionInfo {
    String getModule(Context context);

    String[] getSupportAction(Context context);

    boolean getSwitchState(Context context, String str);

    void onCommand(Context context, String str, Object obj);
}
