package com.umeng.ut.a.c;

import android.content.Context;
import com.umeng.commonsdk.framework.UMFrUtils;

/* loaded from: classes4.dex */
public class c {
    public static boolean b(Context context) {
        if (context == null) {
            return true;
        }
        try {
            return UMFrUtils.isOnline(context);
        } catch (Throwable th) {
            e.a("", th, new Object[0]);
            return true;
        }
    }
}
