package com.xiaomi.push.service;

import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/* loaded from: classes4.dex */
public class an {

    /* renamed from: a, reason: collision with root package name */
    private static Object f24464a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private static Map<String, Queue<String>> f1010a = new HashMap();

    public static boolean a(XMPushService xMPushService, String str, String str2) {
        synchronized (f24464a) {
            try {
                SharedPreferences sharedPreferences = xMPushService.getSharedPreferences("push_message_ids", 0);
                Queue<String> queue = f1010a.get(str);
                if (queue == null) {
                    String[] strArrSplit = sharedPreferences.getString(str, "").split(",");
                    LinkedList linkedList = new LinkedList();
                    for (String str3 : strArrSplit) {
                        linkedList.add(str3);
                    }
                    f1010a.put(str, linkedList);
                    queue = linkedList;
                }
                if (queue.contains(str2)) {
                    return true;
                }
                queue.add(str2);
                if (queue.size() > 25) {
                    queue.poll();
                }
                String strA = com.xiaomi.push.bp.a(queue, ",");
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putString(str, strA);
                editorEdit.commit();
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
