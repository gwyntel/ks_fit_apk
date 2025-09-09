package com.huawei.hms.push;

import android.R;
import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import com.huawei.hms.android.HwBuildEx;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public class f {
    private static int a(Context context) {
        int i2 = context.getApplicationInfo().icon;
        if (i2 != 0) {
            return i2;
        }
        int identifier = context.getResources().getIdentifier("btn_star_big_on", "drawable", "android");
        HMSLog.d("PushSelfShowLog", "icon is btn_star_big_on ");
        if (identifier != 0) {
            return identifier;
        }
        HMSLog.d("PushSelfShowLog", "icon is sym_def_app_icon ");
        return R.drawable.sym_def_app_icon;
    }

    private static int b(Context context, m mVar) throws NoSuchFieldException, PackageManager.NameNotFoundException, NumberFormatException {
        int iA = 0;
        if (context == null || mVar == null) {
            HMSLog.i("PushSelfShowLog", "enter getSmallIconId, context or msg is null");
            return 0;
        }
        if (!TextUtils.isEmpty(mVar.m())) {
            String[] strArrSplit = mVar.m().split("/");
            if (strArrSplit.length == 3) {
                iA = q.a(context, strArrSplit[1], strArrSplit[2]);
            }
        }
        if (iA == 0) {
            iA = q.a(context, "com.huawei.messaging.default_notification_icon");
        }
        return iA != 0 ? iA : a(context);
    }

    public static Bitmap a(Context context, m mVar) {
        if (context == null || mVar == null) {
            return null;
        }
        try {
            if (HwBuildEx.VERSION.EMUI_SDK_INT >= 11) {
                HMSLog.i("PushSelfShowLog", "huawei phone, and emui5.0, need not show large icon.");
                return null;
            }
            if ("com.huawei.android.pushagent".equals(mVar.k())) {
                return null;
            }
            HMSLog.i("PushSelfShowLog", "get left bitmap from " + mVar.k());
            return ((BitmapDrawable) context.getPackageManager().getApplicationIcon(mVar.k())).getBitmap();
        } catch (PackageManager.NameNotFoundException unused) {
            HMSLog.e("PushSelfShowLog", "build left icon occur NameNotFoundException.");
            return null;
        } catch (Exception unused2) {
            HMSLog.e("PushSelfShowLog", "build left icon occur Exception.");
            return null;
        }
    }

    @TargetApi(23)
    public static void a(Context context, Notification.Builder builder, m mVar) {
        if (context != null && builder != null && mVar != null) {
            builder.setSmallIcon(b(context, mVar));
        } else {
            HMSLog.e("PushSelfShowLog", "msg is null");
        }
    }
}
