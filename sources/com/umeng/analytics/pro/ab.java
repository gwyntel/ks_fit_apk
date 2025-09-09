package com.umeng.analytics.pro;

import android.text.TextUtils;
import com.umeng.ccg.ActionInfo;
import com.umeng.ccg.CcgAgent;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.util.ArrayList;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ab implements aj {

    /* renamed from: a, reason: collision with root package name */
    private String f21281a;

    /* renamed from: b, reason: collision with root package name */
    private ArrayList<ac> f21282b;

    /* renamed from: c, reason: collision with root package name */
    private String f21283c;

    /* renamed from: d, reason: collision with root package name */
    private String f21284d;

    /* renamed from: e, reason: collision with root package name */
    private String f21285e;

    /* renamed from: f, reason: collision with root package name */
    private String f21286f;

    public ab(String str, ArrayList<ac> arrayList) {
        this.f21281a = null;
        new ArrayList();
        this.f21283c = "";
        this.f21284d = "";
        this.f21285e = "";
        this.f21286f = "";
        this.f21281a = str;
        this.f21282b = arrayList;
    }

    public String a() {
        return this.f21281a;
    }

    @Override // com.umeng.analytics.pro.aj
    public void b(String str, JSONObject jSONObject) {
    }

    public String c() {
        return this.f21284d;
    }

    private String c(String str) {
        String[] strArrSplit = str.split(",");
        String str2 = "";
        if (strArrSplit.length <= 0) {
            return "";
        }
        ArrayList<String> forbidSdkArray = CcgAgent.getForbidSdkArray(this.f21281a);
        if (forbidSdkArray != null && forbidSdkArray.size() > 0) {
            this.f21286f = forbidSdkArray.toString();
            for (String str3 : strArrSplit) {
                if (CcgAgent.getActionInfo(str3) != null && !forbidSdkArray.contains(str3)) {
                    return str3;
                }
            }
            return "";
        }
        for (String str4 : strArrSplit) {
            ActionInfo actionInfo = CcgAgent.getActionInfo(str4);
            if (actionInfo != null) {
                String[] supportAction = actionInfo.getSupportAction(UMGlobalContext.getAppContext());
                if (supportAction.length > 0) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= supportAction.length) {
                            break;
                        }
                        if (this.f21281a.equals(supportAction[i2])) {
                            str2 = str4;
                            break;
                        }
                        i2++;
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        return str2;
                    }
                } else {
                    continue;
                }
            }
        }
        return str2;
    }

    public void a(String str) {
        this.f21283c = str;
    }

    public String b() {
        return this.f21283c;
    }

    @Override // com.umeng.analytics.pro.aj
    public JSONObject a(String str, JSONObject jSONObject) {
        try {
            int size = this.f21282b.size();
            if (size == 0) {
                return null;
            }
            for (int i2 = 0; i2 < size; i2++) {
                if (this.f21282b.get(i2).b()) {
                    return null;
                }
            }
            if (CcgAgent.hasRegistedActionInfo() && !TextUtils.isEmpty(this.f21284d)) {
                String strC = c(this.f21284d);
                this.f21285e = strC;
                if (TextUtils.isEmpty(strC)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "采集项：" + this.f21281a + "; 未选中可用Module ; sdk: " + this.f21284d);
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "采集项：" + this.f21281a + "; 选中Module: " + this.f21285e + "; sdk: " + this.f21284d);
                }
            }
            ac acVar = this.f21282b.get(size - 1);
            if (acVar == null || !(acVar instanceof af)) {
                return null;
            }
            long jC = acVar.c();
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("actionName", this.f21281a);
                jSONObject2.put("sdk", this.f21284d);
                jSONObject2.put(com.umeng.ccg.a.f22016r, this.f21283c);
                jSONObject2.put("delay", jC);
                jSONObject2.put(com.umeng.ccg.a.f22017s, this.f21285e);
                jSONObject2.put(com.umeng.ccg.a.f22018t, this.f21286f);
            } catch (Throwable unused) {
            }
            return jSONObject2;
        } catch (Throwable unused2) {
            return null;
        }
    }

    public void b(String str) {
        this.f21284d = str;
    }
}
