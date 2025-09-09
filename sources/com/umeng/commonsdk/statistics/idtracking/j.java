package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.commonsdk.config.FieldManager;

/* loaded from: classes4.dex */
public class j extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22410a = "utdid";

    /* renamed from: b, reason: collision with root package name */
    private Context f22411b;

    public j(Context context) {
        super("utdid");
        this.f22411b = context;
    }

    private String g() {
        try {
            return this.f22411b.getSharedPreferences("Alvin2", 0).getString("UTDID2", null);
        } catch (Throwable unused) {
            return null;
        }
    }

    private String h() {
        try {
            return this.f22411b.getSharedPreferences("um_push_ut", 0).getString("d_id", null);
        } catch (Throwable unused) {
            return null;
        }
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        try {
            if (FieldManager.allow(com.umeng.commonsdk.utils.d.f22577u)) {
                String strH = h();
                return TextUtils.isEmpty(strH) ? g() : strH;
            }
        } catch (Throwable unused) {
        }
        return null;
    }
}
