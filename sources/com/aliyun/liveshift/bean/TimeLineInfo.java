package com.aliyun.liveshift.bean;

import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.utils.e;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class TimeLineInfo {
    private static final String TAG = "TimeLineInfo";
    public long end;
    public long start;

    public static List<TimeLineInfo> getInfoArrayFromJson(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                TimeLineInfo infoFromJson = getInfoFromJson(jSONArray.getJSONObject(i2));
                if (infoFromJson != null) {
                    arrayList.add(infoFromJson);
                }
            } catch (JSONException unused) {
            }
        }
        return arrayList;
    }

    private static TimeLineInfo getInfoFromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        TimeLineInfo timeLineInfo = new TimeLineInfo();
        timeLineInfo.start = e.b(jSONObject, "start");
        timeLineInfo.end = e.b(jSONObject, TtmlNode.END);
        return timeLineInfo;
    }
}
