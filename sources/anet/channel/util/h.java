package anet.channel.util;

import anet.channel.request.Request;
import anet.channel.thread.ThreadPoolExecutorFactory;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private static Map<String, Integer> f7093a;

    static {
        HashMap map = new HashMap();
        f7093a = map;
        map.put("tpatch", 3);
        f7093a.put("so", 3);
        f7093a.put("json", 3);
        f7093a.put("html", 4);
        f7093a.put("htm", 4);
        f7093a.put("css", 5);
        f7093a.put("js", 5);
        f7093a.put("webp", 6);
        f7093a.put("png", 6);
        f7093a.put("jpg", 6);
        f7093a.put("do", 6);
        f7093a.put("zip", Integer.valueOf(ThreadPoolExecutorFactory.Priority.LOW));
        f7093a.put("bin", Integer.valueOf(ThreadPoolExecutorFactory.Priority.LOW));
        f7093a.put("apk", Integer.valueOf(ThreadPoolExecutorFactory.Priority.LOW));
    }

    public static int a(Request request) {
        Integer num;
        if (request == null) {
            throw new NullPointerException("url is null!");
        }
        if (request.getHeaders().containsKey(HttpConstant.X_PV)) {
            return 1;
        }
        String strTrySolveFileExtFromUrlPath = HttpHelper.trySolveFileExtFromUrlPath(request.getHttpUrl().path());
        if (strTrySolveFileExtFromUrlPath == null || (num = f7093a.get(strTrySolveFileExtFromUrlPath)) == null) {
            return 6;
        }
        return num.intValue();
    }
}
