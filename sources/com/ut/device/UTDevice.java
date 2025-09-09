package com.ut.device;

import android.content.Context;
import com.ta.utdid2.device.d;

/* loaded from: classes4.dex */
public class UTDevice {
    public static String getUtdid(Context context) {
        return com.ta.utdid2.device.UTDevice.getUtdid(context);
    }

    public static void setExtendFactor(String str) {
        d.setExtendFactor(str);
    }
}
