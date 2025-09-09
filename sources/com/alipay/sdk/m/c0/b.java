package com.alipay.sdk.m.c0;

import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public File f9218a;

    /* renamed from: b, reason: collision with root package name */
    public com.alipay.sdk.m.g0.a f9219b;

    public b(String str, com.alipay.sdk.m.g0.a aVar) {
        this.f9218a = null;
        this.f9219b = null;
        this.f9218a = new File(str);
        this.f9219b = aVar;
    }

    public static String a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "id");
            jSONObject.put("error", str);
            return jSONObject.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b() {
        try {
            File file = this.f9218a;
            if (file == null) {
                return;
            }
            if (file.exists() && this.f9218a.isDirectory() && this.f9218a.list().length != 0) {
                ArrayList arrayList = new ArrayList();
                for (String str : this.f9218a.list()) {
                    arrayList.add(str);
                }
                Collections.sort(arrayList);
                String str2 = (String) arrayList.get(arrayList.size() - 1);
                int size = arrayList.size();
                if (str2.equals(new SimpleDateFormat(CalendarUtils.yyyyMMdd).format(Calendar.getInstance().getTime()) + ".log")) {
                    if (arrayList.size() < 2) {
                        return;
                    }
                    str2 = (String) arrayList.get(arrayList.size() - 2);
                    size--;
                }
                if (!this.f9219b.logCollect(a(com.alipay.sdk.m.z.b.a(this.f9218a.getAbsolutePath(), str2)))) {
                    size--;
                }
                for (int i2 = 0; i2 < size; i2++) {
                    new File(this.f9218a, (String) arrayList.get(i2)).delete();
                }
            }
        } finally {
        }
    }

    public final void a() {
        new Thread(new c(this)).start();
    }
}
