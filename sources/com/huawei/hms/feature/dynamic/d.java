package com.huawei.hms.feature.dynamic;

import android.os.Bundle;
import com.huawei.hms.common.util.Logger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public final class d {

    /* renamed from: b, reason: collision with root package name */
    public static final String f16148b = "d";

    /* renamed from: c, reason: collision with root package name */
    public static final d f16149c = new d();

    /* renamed from: a, reason: collision with root package name */
    public Set<String> f16150a;

    public static d a() {
        return f16149c;
    }

    public void a(Bundle bundle) {
        ArrayList<String> stringArrayList = bundle.getStringArrayList("installed_module_name");
        if (stringArrayList == null || stringArrayList.isEmpty()) {
            Logger.w(f16148b, "Get installed module name failed.");
            this.f16150a = new HashSet();
            return;
        }
        Logger.i(f16148b, "Installed module name:" + stringArrayList);
        this.f16150a = new HashSet(stringArrayList);
    }
}
