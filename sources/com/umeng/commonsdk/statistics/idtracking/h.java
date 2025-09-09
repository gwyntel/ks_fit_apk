package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.analytics.pro.ay;
import com.umeng.commonsdk.config.FieldManager;

/* loaded from: classes4.dex */
public class h extends a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f22404a = ay.b().b(ay.f21377l);

    /* renamed from: b, reason: collision with root package name */
    public static final String f22405b = "key_umeng_sp_oaid";

    /* renamed from: c, reason: collision with root package name */
    public static final String f22406c = "key_umeng_sp_oaid_required_time";

    /* renamed from: d, reason: collision with root package name */
    private static final String f22407d = "oaid";

    /* renamed from: e, reason: collision with root package name */
    private Context f22408e;

    public h(Context context) {
        super(f22407d);
        this.f22408e = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        if (!FieldManager.allow(com.umeng.commonsdk.utils.d.G)) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = this.f22408e.getSharedPreferences(f22404a, 0);
            if (sharedPreferences != null) {
                return sharedPreferences.getString(f22405b, "");
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
