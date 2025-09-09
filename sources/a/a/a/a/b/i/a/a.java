package a.a.a.a.b.i.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.WifiProvisionUtConst;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static SharedPreferences f1415a;

    public static SharedPreferences a(Context context) {
        if (f1415a == null) {
            f1415a = context.getSharedPreferences(WifiProvisionUtConst.ARG_CONNECTION, 0);
        }
        return f1415a;
    }

    public static int a(Context context, String str, int i2) {
        return a(context).getInt(str, i2);
    }
}
