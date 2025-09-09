package com.xiaomi.clientreport.data;

import com.heytap.mcssdk.constant.IntentConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EventClientReport extends a {
    public String eventContent;
    public String eventId;
    public long eventTime;
    public int eventType;

    public static EventClientReport getBlankInstance() {
        return new EventClientReport();
    }

    @Override // com.xiaomi.clientreport.data.a
    public JSONObject toJson() throws JSONException {
        try {
            JSONObject json = super.toJson();
            if (json == null) {
                return null;
            }
            json.put(IntentConstant.EVENT_ID, this.eventId);
            json.put("eventType", this.eventType);
            json.put("eventTime", this.eventTime);
            String str = this.eventContent;
            if (str == null) {
                str = "";
            }
            json.put("eventContent", str);
            return json;
        } catch (JSONException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return null;
        }
    }

    @Override // com.xiaomi.clientreport.data.a
    public String toJsonString() {
        return super.toJsonString();
    }
}
