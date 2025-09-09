package com.huawei.hms.framework.network.grs.f;

import android.content.Context;
import androidx.media3.common.MimeTypes;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class c extends a {
    public c(Context context, boolean z2) {
        this.f16221d = z2;
        if (a("grs_sdk_global_route_config.json", context) == 0) {
            this.f16220c = true;
        }
    }

    public List<com.huawei.hms.framework.network.grs.local.model.b> a(JSONArray jSONArray, JSONObject jSONObject) {
        return (jSONObject == null || jSONObject.length() == 0) ? new ArrayList() : a(jSONObject);
    }

    @Override // com.huawei.hms.framework.network.grs.f.a
    public int b(String str) throws JSONException {
        this.f16218a = new com.huawei.hms.framework.network.grs.local.model.a();
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject(MimeTypes.BASE_TYPE_APPLICATION);
            String string = jSONObject.getString("name");
            long j2 = jSONObject.getLong("cacheControl");
            JSONArray jSONArray = jSONObject.getJSONArray(TmpConstant.DEVICE_MODEL_SERVICES);
            this.f16218a.b(string);
            this.f16218a.a(j2);
            if (jSONArray != null) {
                if (jSONArray.length() != 0) {
                    return 0;
                }
            }
            return -1;
        } catch (JSONException e2) {
            Logger.w("LocalManagerV1", "parse appbean failed maybe json style is wrong. %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return -1;
        }
    }

    @Override // com.huawei.hms.framework.network.grs.f.a
    public int c(String str) throws JSONException {
        JSONObject jSONObject;
        this.f16219b = new ArrayList(16);
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            if (jSONObject2.has("countryOrAreaGroups")) {
                jSONObject = jSONObject2.getJSONObject("countryOrAreaGroups");
            } else if (jSONObject2.has("countryGroups")) {
                jSONObject = jSONObject2.getJSONObject("countryGroups");
            } else {
                Logger.e("LocalManagerV1", "maybe local config json is wrong because the default countryOrAreaGroups isn't config.");
                jSONObject = null;
            }
            if (jSONObject == null) {
                return -1;
            }
            if (jSONObject.length() != 0) {
                this.f16219b.addAll(a(jSONObject));
            }
            return 0;
        } catch (JSONException e2) {
            Logger.w("LocalManagerV1", "parse countrygroup failed maybe json style is wrong. %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00a5 A[Catch: JSONException -> 0x0072, TryCatch #0 {JSONException -> 0x0072, blocks: (B:3:0x000c, B:4:0x001d, B:6:0x0023, B:8:0x0039, B:10:0x0042, B:11:0x0056, B:13:0x005c, B:15:0x006d, B:24:0x0089, B:25:0x009f, B:27:0x00a5, B:29:0x00b9, B:31:0x00bf, B:33:0x00d0, B:18:0x0075, B:20:0x007b, B:22:0x0082, B:34:0x00e4, B:36:0x00ef, B:40:0x00fe, B:42:0x0108, B:44:0x010f, B:45:0x0116, B:37:0x00f4, B:39:0x00fa, B:41:0x0103), top: B:51:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x010f A[Catch: JSONException -> 0x0072, TryCatch #0 {JSONException -> 0x0072, blocks: (B:3:0x000c, B:4:0x001d, B:6:0x0023, B:8:0x0039, B:10:0x0042, B:11:0x0056, B:13:0x005c, B:15:0x006d, B:24:0x0089, B:25:0x009f, B:27:0x00a5, B:29:0x00b9, B:31:0x00bf, B:33:0x00d0, B:18:0x0075, B:20:0x007b, B:22:0x0082, B:34:0x00e4, B:36:0x00ef, B:40:0x00fe, B:42:0x0108, B:44:0x010f, B:45:0x0116, B:37:0x00f4, B:39:0x00fa, B:41:0x0103), top: B:51:0x000c }] */
    @Override // com.huawei.hms.framework.network.grs.f.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int f(java.lang.String r21) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.f.c.f(java.lang.String):int");
    }

    private List<com.huawei.hms.framework.network.grs.local.model.b> a(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray;
        try {
            ArrayList arrayList = new ArrayList(16);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                com.huawei.hms.framework.network.grs.local.model.b bVar = new com.huawei.hms.framework.network.grs.local.model.b();
                bVar.b(next);
                JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                bVar.c(jSONObject2.getString("name"));
                bVar.a(jSONObject2.getString("description"));
                if (jSONObject2.has("countriesOrAreas")) {
                    jSONArray = jSONObject2.getJSONArray("countriesOrAreas");
                } else if (jSONObject2.has("countries")) {
                    jSONArray = jSONObject2.getJSONArray("countries");
                } else {
                    Logger.w("LocalManagerV1", "current country or area group has not config countries or areas.");
                    jSONArray = null;
                }
                HashSet hashSet = new HashSet(16);
                if (jSONArray != null && jSONArray.length() != 0) {
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        hashSet.add((String) jSONArray.get(i2));
                    }
                    bVar.a(hashSet);
                    arrayList.add(bVar);
                }
                return new ArrayList();
            }
            return arrayList;
        } catch (JSONException e2) {
            Logger.w("LocalManagerV1", "parse countryGroups failed maybe json style is wrong. %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return new ArrayList();
        }
    }
}
