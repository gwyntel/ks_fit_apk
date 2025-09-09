package com.umeng.message.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.ccg.ActionInfo;

/* loaded from: classes4.dex */
public class r implements ActionInfo {
    @Override // com.umeng.ccg.ActionInfo
    public String getModule(Context context) {
        return "push";
    }

    @Override // com.umeng.ccg.ActionInfo
    public String[] getSupportAction(Context context) {
        return new String[]{com.umeng.ccg.a.f22003e};
    }

    @Override // com.umeng.ccg.ActionInfo
    public boolean getSwitchState(Context context, String str) {
        if (TextUtils.equals(str, com.umeng.ccg.a.f22003e)) {
            return f.f22835a;
        }
        return false;
    }

    @Override // com.umeng.ccg.ActionInfo
    public void onCommand(Context context, String str, Object obj) {
    }
}
