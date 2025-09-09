package com.umeng.analytics.pro;

import android.text.TextUtils;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class aq implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public static final String f21334a = "https://aspect-upush.umeng.com/occa/v1/event/report";

    /* renamed from: b, reason: collision with root package name */
    public static final String f21335b = "https://cnlogs.umeng.com/ext_event";

    /* renamed from: c, reason: collision with root package name */
    private String f21336c;

    /* renamed from: d, reason: collision with root package name */
    private String f21337d;

    public aq(String str, JSONObject jSONObject) {
        this.f21336c = str;
        this.f21337d = jSONObject.toString();
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (TextUtils.isEmpty(this.f21337d)) {
                return;
            }
            ap.b(this.f21336c, this.f21337d.getBytes());
        } catch (Throwable unused) {
        }
    }
}
