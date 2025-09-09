package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class ez {
    public static void a(Context context, String str, int i2, String str2) {
        ah.a(context).a(new fa(context, str, i2, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, String str, int i2, String str2) {
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        try {
            HashMap map = new HashMap();
            map.put("awake_info", str);
            map.put("event_type", String.valueOf(i2));
            map.put("description", str2);
            int iA = fd.a(context).a();
            if (iA == 1) {
                a(context, map);
            } else if (iA == 2) {
                c(context, map);
            } else if (iA == 3) {
                a(context, map);
                c(context, map);
            }
            b(context, map);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    private static void a(Context context, HashMap<String, String> map) {
        fh fhVarM401a = fd.a(context).m401a();
        if (fhVarM401a != null) {
            fhVarM401a.a(context, map);
        }
    }

    private static void b(Context context, HashMap<String, String> map) {
        fh fhVarM401a = fd.a(context).m401a();
        if (fhVarM401a != null) {
            fhVarM401a.c(context, map);
        }
    }

    private static void c(Context context, HashMap<String, String> map) {
        fh fhVarM401a = fd.a(context).m401a();
        if (fhVarM401a != null) {
            fhVarM401a.b(context, map);
        }
    }
}
