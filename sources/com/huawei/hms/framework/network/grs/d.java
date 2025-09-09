package com.huawei.hms.framework.network.grs;

import android.content.Context;
import com.huawei.hms.framework.common.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static final Map<String, c> f16206a = new ConcurrentHashMap(16);

    /* renamed from: b, reason: collision with root package name */
    private static final Object f16207b = new Object();

    public static c a(GrsBaseInfo grsBaseInfo, Context context) {
        synchronized (f16207b) {
            try {
                int iUniqueCode = grsBaseInfo.uniqueCode();
                Map<String, c> map = f16206a;
                c cVar = map.get(context.getPackageName() + iUniqueCode);
                if (cVar == null) {
                    Logger.i("GrsClientManager", "grsClientImpl == null, and new GrsClientImpl");
                    c cVar2 = new c(context, grsBaseInfo);
                    map.put(context.getPackageName() + iUniqueCode, cVar2);
                    return cVar2;
                }
                if (cVar.a((Object) new c(grsBaseInfo))) {
                    return cVar;
                }
                Logger.i("GrsClientManager", "The app_name, ser_country, reg_country and issue_country is equal, but other not.");
                c cVar3 = new c(context, grsBaseInfo);
                map.put(context.getPackageName() + iUniqueCode, cVar3);
                return cVar3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
