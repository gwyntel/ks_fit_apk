package com.umeng.message.entity;

import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.share.internal.ShareConstants;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.f;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class UInAppMessage {
    public static final int CARD_A = 2;
    public static final int CARD_B = 3;
    public static final String CLOSE = "go_app";
    public static final int CUSTOM_CARD = 4;
    public static final String GO_ACTIVITY = "go_activity";
    public static final String GO_URL = "go_url";
    public static final String NONE = "none";
    public static final int PLAIN_TAXT_A = 5;
    public static final int PLAIN_TAXT_B = 6;
    public static final int SPLASH_A = 0;
    public static final int SPLASH_B = 1;
    public static final int VIEW_BOTTOM_IMAGE = 17;
    public static final int VIEW_CUSTOM_BUTTON = 19;
    public static final int VIEW_IMAGE = 16;
    public static final int VIEW_PLAIN_TEXT = 18;
    public String action_activity;
    public String action_type;
    public String action_url;
    public String bottom_action_activity;
    public String bottom_action_type;
    public String bottom_action_url;
    public int bottom_height;
    public String bottom_image_url;
    public int bottom_width;
    public String button_text;
    public String content;
    public String customButtonActionType;
    public String customButtonActivity;
    public String customButtonUrl;
    public boolean display_button;
    public String display_name;
    public int display_time;
    public String expire_time;
    public int height;
    public String image_url;
    public String msg_id;
    public int msg_type;
    public String plainTextActionType;
    public String plainTextActivity;
    public String plainTextUrl;
    private final JSONObject rawJson;
    public int show_times;
    public int show_type;
    public String start_time;
    public String title;
    public int width;

    public UInAppMessage(JSONObject jSONObject) throws JSONException {
        this.rawJson = jSONObject;
        this.msg_id = jSONObject.getString("msg_id");
        this.msg_type = jSONObject.getInt("msg_type");
        JSONObject jSONObject2 = jSONObject.getJSONObject("msg_info");
        this.display_button = jSONObject2.optBoolean("display_button");
        this.display_name = jSONObject2.optString(bc.f21420s, "");
        this.display_time = jSONObject2.optInt("display_time", 5);
        int i2 = this.msg_type;
        if (i2 == 5 || i2 == 6) {
            JSONObject jSONObject3 = jSONObject2.getJSONObject("plain_text");
            this.title = jSONObject3.getString("title");
            this.content = jSONObject3.getString("content");
            this.button_text = jSONObject3.getString("button_text");
            this.plainTextActionType = jSONObject3.optString(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE);
            this.plainTextActivity = jSONObject3.optString(PushConstants.INTENT_ACTIVITY_NAME, "");
            this.plainTextUrl = jSONObject3.optString("url", "");
        }
        if (jSONObject2.has("image")) {
            JSONObject jSONObject4 = jSONObject2.getJSONObject("image");
            this.image_url = jSONObject4.getString("imageurl");
            this.width = jSONObject4.getInt(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
            this.height = jSONObject4.getInt(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
            this.action_type = jSONObject4.optString(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE);
            this.action_activity = jSONObject4.optString(PushConstants.INTENT_ACTIVITY_NAME, "");
            this.action_url = jSONObject4.optString("url", "");
        }
        if (jSONObject2.has("bottom_image")) {
            JSONObject jSONObject5 = jSONObject2.getJSONObject("bottom_image");
            this.bottom_image_url = jSONObject5.getString("imageurl");
            this.bottom_width = jSONObject5.getInt(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
            this.bottom_height = jSONObject5.getInt(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
            this.bottom_action_type = jSONObject5.optString(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE);
            this.bottom_action_activity = jSONObject5.optString(PushConstants.INTENT_ACTIVITY_NAME, "");
            this.bottom_action_url = jSONObject5.optString("url", "");
        }
        if (jSONObject2.has("custom_button")) {
            JSONObject jSONObject6 = jSONObject2.getJSONObject("custom_button");
            this.customButtonActionType = jSONObject6.optString(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE);
            this.customButtonActivity = jSONObject6.optString(PushConstants.INTENT_ACTIVITY_NAME, "");
            this.customButtonUrl = jSONObject6.optString("url", "");
        }
        JSONObject jSONObject7 = jSONObject.getJSONObject(bc.by);
        this.show_type = jSONObject7.getInt("show_type");
        this.show_times = jSONObject7.getInt("show_times");
        this.start_time = jSONObject7.getString(f.f21694p);
        this.expire_time = jSONObject7.getString(PushConstants.REGISTER_STATUS_EXPIRE_TIME);
    }

    public JSONObject getRaw() {
        return this.rawJson;
    }
}
