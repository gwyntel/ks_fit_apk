package com.xiaomi.push.service;

import com.xiaomi.push.ja;
import java.util.Map;

/* loaded from: classes4.dex */
public class bs {
    public static ja a(ja jaVar) {
        Map<String, String> map;
        if (jaVar != null && (map = jaVar.f660b) != null) {
            map.remove("score_info");
        }
        return jaVar;
    }
}
