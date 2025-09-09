package com.umeng.ut.a.c;

import android.content.Context;
import com.umeng.commonsdk.utils.UMUtils;

/* loaded from: classes4.dex */
public class a {
    public static boolean a(Context context) {
        try {
            return UMUtils.isMainProgress(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }
}
