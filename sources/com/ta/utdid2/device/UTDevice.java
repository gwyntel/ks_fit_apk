package com.ta.utdid2.device;

import android.content.Context;

/* loaded from: classes4.dex */
public class UTDevice {
    @Deprecated
    public static String getUtdid(Context context) {
        if (context == null) {
            return "ffffffffffffffffffffffff";
        }
        com.ta.a.a.a().a(context);
        return a.a().getUtdid(context);
    }
}
