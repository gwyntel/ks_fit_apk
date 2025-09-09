package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.analytics.pro.ay;
import com.umeng.commonsdk.config.FieldManager;

/* loaded from: classes4.dex */
public class c extends a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f22382a = ay.b().b(ay.f21377l);

    /* renamed from: b, reason: collision with root package name */
    public static final String f22383b = "key_umeng_sp_honor_oaid";

    /* renamed from: c, reason: collision with root package name */
    private static final String f22384c = "honor_oaid";

    /* renamed from: d, reason: collision with root package name */
    private Context f22385d;

    public c(Context context) {
        super(f22384c);
        this.f22385d = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        if (!FieldManager.allow(com.umeng.commonsdk.utils.d.G)) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = this.f22385d.getSharedPreferences(f22382a, 0);
            if (sharedPreferences != null) {
                return sharedPreferences.getString(f22383b, "");
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
