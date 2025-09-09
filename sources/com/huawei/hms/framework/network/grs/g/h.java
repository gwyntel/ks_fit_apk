package com.huawei.hms.framework.network.grs.g;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class h {
    public static String a(String str, String str2) {
        return !str.equals(str2) ? b(str, str2) : str;
    }

    private static String b(String str, String str2) throws JSONException {
        HashSet hashSet = new HashSet();
        if (!TextUtils.isEmpty(str)) {
            JSONArray jSONArray = new JSONObject(str).getJSONArray(TmpConstant.DEVICE_MODEL_SERVICES);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                hashSet.add(jSONArray.getString(i2));
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            JSONArray jSONArray2 = new JSONObject(str2).getJSONArray(TmpConstant.DEVICE_MODEL_SERVICES);
            for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                hashSet.add(jSONArray2.getString(i3));
            }
        }
        if (hashSet.isEmpty()) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray3 = new JSONArray();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            jSONArray3.put((String) it.next());
        }
        jSONObject.put(TmpConstant.DEVICE_MODEL_SERVICES, jSONArray3);
        return jSONObject.toString();
    }
}
