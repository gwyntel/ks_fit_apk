package org.android.agoo.control;

import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import org.android.agoo.common.Config;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ byte[] f26519a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f26520b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ AgooFactory f26521c;

    a(AgooFactory agooFactory, byte[] bArr, String str) {
        this.f26521c = agooFactory;
        this.f26519a = bArr;
        this.f26520b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String str = new String(this.f26519a, "utf-8");
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            if (length == 1) {
                String string = null;
                String string2 = null;
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    if (jSONObject != null) {
                        string = jSONObject.getString("i");
                        string2 = jSONObject.getString("p");
                    }
                }
                if (ALog.isPrintLog(ALog.Level.I)) {
                    ALog.i("AgooFactory", "saveMsg msgId:" + string + ",message=" + str + ",currentPack=" + string2 + ",reportTimes=" + Config.f(AgooFactory.mContext), new Object[0]);
                }
                if (TextUtils.isEmpty(string2) || !TextUtils.equals(string2, AgooFactory.mContext.getPackageName())) {
                    return;
                }
                if (TextUtils.isEmpty(this.f26520b)) {
                    this.f26521c.messageService.a(string, str, "0");
                } else {
                    this.f26521c.messageService.a(string, str, this.f26520b);
                }
            }
        } catch (Throwable th) {
            ALog.e("AgooFactory", "saveMsg fail:" + th.toString(), new Object[0]);
        }
    }
}
