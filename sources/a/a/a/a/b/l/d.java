package a.a.a.a.b.l;

import com.alibaba.ailabs.iot.mesh.ut.IUserTracker;
import com.alibaba.fastjson.JSON;
import com.ut.mini.UTAnalytics;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class d implements IUserTracker {
    @Override // com.alibaba.ailabs.iot.mesh.ut.IUserTracker
    public void customHit(String str, String str2, Map<String, String> map, String str3) {
        if (map == null) {
            map = new HashMap<>(8);
        }
        Map<String, String> map2 = map;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("customHit: eventName");
            sb.append(str2);
            sb.append(", ");
            sb.append(JSON.toJSONString(map2));
            a.a.a.a.b.m.a.c("GenieUtImpl", sb.toString());
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(str, 19999, str2, (String) null, (String) null, map2);
            uTOriginalCustomHitBuilder.setProperty("spm", str3);
            UTAnalytics.getInstance().getDefaultTracker().send(uTOriginalCustomHitBuilder.build());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
