package com.meizu.cloud.pushsdk.platform.message;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PushSwitchStatus extends BasicPushStatus {
    private String pushId;
    private boolean switchNotificationMessage;
    private boolean switchThroughMessage;

    public PushSwitchStatus() {
    }

    public String getPushId() {
        return this.pushId;
    }

    public boolean isSwitchNotificationMessage() {
        return this.switchNotificationMessage;
    }

    public boolean isSwitchThroughMessage() {
        return this.switchThroughMessage;
    }

    @Override // com.meizu.cloud.pushsdk.platform.message.BasicPushStatus
    public void parseValueData(JSONObject jSONObject) throws JSONException {
        if (!jSONObject.isNull(PushConstants.KEY_PUSH_ID)) {
            setPushId(jSONObject.getString(PushConstants.KEY_PUSH_ID));
        }
        if (!jSONObject.isNull("barTypeSwitch")) {
            setSwitchNotificationMessage(jSONObject.getInt("barTypeSwitch") == 1);
        }
        if (jSONObject.isNull("directTypeSwitch")) {
            return;
        }
        setSwitchThroughMessage(jSONObject.getInt("directTypeSwitch") == 1);
    }

    public void setPushId(String str) {
        this.pushId = str;
    }

    public void setSwitchNotificationMessage(boolean z2) {
        this.switchNotificationMessage = z2;
    }

    public void setSwitchThroughMessage(boolean z2) {
        this.switchThroughMessage = z2;
    }

    @Override // com.meizu.cloud.pushsdk.platform.message.BasicPushStatus
    public String toString() {
        return super.toString() + "PushSwitchStatus{switchNotificationMessage=" + this.switchNotificationMessage + ", switchThroughMessage=" + this.switchThroughMessage + ", pushId='" + this.pushId + "'}";
    }

    public PushSwitchStatus(String str) {
        super(str);
    }
}
