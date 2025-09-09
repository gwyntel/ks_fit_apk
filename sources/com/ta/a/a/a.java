package com.ta.a.a;

import android.content.Context;
import com.ta.utdid2.a.a.d;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {
    public static String a(String str) {
        Context context = com.ta.a.a.a().getContext();
        return context == null ? "" : d.e(String.format("{\"type\":\"%s\",\"timestamp\":%s,\"data\":%s}", "audid", com.ta.a.a.a().m74a(), a(str, "", "", context.getPackageName())));
    }

    private static String a(String str, String str2, String str3, String str4) {
        HashMap map = new HashMap();
        map.put("audid", str2);
        map.put("utdid", str);
        map.put("appkey", str3);
        map.put("appName", str4);
        return new JSONObject(d.a(map)).toString();
    }
}
