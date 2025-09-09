package com.meizu.cloud.pushsdk.platform.message;

import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus.Tag;
import java.util.ArrayList;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {
    public static PushSwitchStatus a(String str) {
        DebugLogger.i("StatusSerialize", "register status serialize stringToPushSwitchStatus start, statusText=" + str);
        try {
            PushSwitchStatus pushSwitchStatus = new PushSwitchStatus();
            JSONObject jSONObject = new JSONObject(str);
            PushSwitchStatus pushSwitchStatus2 = (PushSwitchStatus) b(jSONObject, pushSwitchStatus);
            if (!jSONObject.isNull(PushConstants.REGISTER_STATUS_PUSH_ID)) {
                pushSwitchStatus2.setPushId(jSONObject.getString(PushConstants.REGISTER_STATUS_PUSH_ID));
            }
            if (!jSONObject.isNull(PushConstants.PUSH_SWITCH_STATUS_IS_NOTIFICATION_SWITCH)) {
                pushSwitchStatus2.setSwitchNotificationMessage(jSONObject.getBoolean(PushConstants.PUSH_SWITCH_STATUS_IS_NOTIFICATION_SWITCH));
            }
            if (!jSONObject.isNull(PushConstants.PUSH_SWITCH_STATUS_IS_THROUGH_SWITCH)) {
                pushSwitchStatus2.setSwitchThroughMessage(jSONObject.getBoolean(PushConstants.PUSH_SWITCH_STATUS_IS_THROUGH_SWITCH));
            }
            DebugLogger.i("StatusSerialize", "register status serialize stringToPushSwitchStatus success, PushSwitchStatus=" + pushSwitchStatus2);
            return pushSwitchStatus2;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize stringToPushSwitchStatus error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    private static BasicPushStatus b(JSONObject jSONObject, BasicPushStatus basicPushStatus) throws JSONException {
        if (jSONObject == null || basicPushStatus == null) {
            return null;
        }
        if (!jSONObject.isNull("code")) {
            basicPushStatus.setCode(jSONObject.getString("code"));
        }
        if (!jSONObject.isNull("message")) {
            basicPushStatus.setMessage(jSONObject.getString("message"));
        }
        return basicPushStatus;
    }

    public static SubAliasStatus c(String str) {
        DebugLogger.i("StatusSerialize", "register status serialize stringToSubAliasStatus start, statusText=" + str);
        try {
            SubAliasStatus subAliasStatus = new SubAliasStatus();
            JSONObject jSONObject = new JSONObject(str);
            SubAliasStatus subAliasStatus2 = (SubAliasStatus) b(jSONObject, subAliasStatus);
            if (!jSONObject.isNull(PushConstants.REGISTER_STATUS_PUSH_ID)) {
                subAliasStatus2.setPushId(jSONObject.getString(PushConstants.REGISTER_STATUS_PUSH_ID));
            }
            if (!jSONObject.isNull(PushConstants.SUB_ALIAS_STATUS_NAME)) {
                subAliasStatus2.setAlias(jSONObject.getString(PushConstants.SUB_ALIAS_STATUS_NAME));
            }
            DebugLogger.i("StatusSerialize", "register status serialize stringToSubAliasStatus success, SubAliasStatus=" + subAliasStatus2);
            return subAliasStatus2;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize stringToSubAliasStatus error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    public static SubTagsStatus d(String str) throws JSONException {
        DebugLogger.i("StatusSerialize", "register status serialize stringToSubTagsStatus start, statusText=" + str);
        try {
            SubTagsStatus subTagsStatus = new SubTagsStatus();
            JSONObject jSONObject = new JSONObject(str);
            SubTagsStatus subTagsStatus2 = (SubTagsStatus) b(jSONObject, subTagsStatus);
            if (!jSONObject.isNull(PushConstants.REGISTER_STATUS_PUSH_ID)) {
                subTagsStatus2.setPushId(jSONObject.getString(PushConstants.REGISTER_STATUS_PUSH_ID));
            }
            if (!jSONObject.isNull(PushConstants.SUB_TAGS_STATUS_LIST)) {
                JSONArray jSONArray = jSONObject.getJSONArray(PushConstants.SUB_TAGS_STATUS_LIST);
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                    Objects.requireNonNull(subTagsStatus2);
                    SubTagsStatus.Tag tag = subTagsStatus2.new Tag();
                    if (!jSONObject2.isNull(PushConstants.SUB_TAGS_STATUS_ID)) {
                        tag.setTagId(jSONObject2.getInt(PushConstants.SUB_TAGS_STATUS_ID));
                    }
                    if (!jSONObject2.isNull(PushConstants.SUB_TAGS_STATUS_NAME)) {
                        tag.setTagName(jSONObject2.getString(PushConstants.SUB_TAGS_STATUS_NAME));
                    }
                    arrayList.add(tag);
                }
                subTagsStatus2.setTagList(arrayList);
            }
            DebugLogger.i("StatusSerialize", "register status serialize stringToSubTagsStatus success, SubTagsStatus=" + subTagsStatus2);
            return subTagsStatus2;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize stringToSubTagsStatus error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    public static UnRegisterStatus e(String str) {
        DebugLogger.i("StatusSerialize", "register status serialize stringToUnregisterStatus start, statusText=" + str);
        try {
            UnRegisterStatus unRegisterStatus = new UnRegisterStatus();
            JSONObject jSONObject = new JSONObject(str);
            UnRegisterStatus unRegisterStatus2 = (UnRegisterStatus) b(jSONObject, unRegisterStatus);
            if (!jSONObject.isNull(PushConstants.UNREGISTER_STATUS_IS_SUCCESS)) {
                unRegisterStatus2.setIsUnRegisterSuccess(jSONObject.getBoolean(PushConstants.UNREGISTER_STATUS_IS_SUCCESS));
            }
            DebugLogger.i("StatusSerialize", "register status serialize stringToUnregisterStatus success, UnRegisterStatus=" + unRegisterStatus2);
            return unRegisterStatus2;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize stringToUnregisterStatus error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(PushSwitchStatus pushSwitchStatus) throws JSONException {
        DebugLogger.i("StatusSerialize", "register status serialize pushSwitchStatusToString start, PushSwitchStatus=" + pushSwitchStatus);
        try {
            JSONObject jSONObjectA = a(new JSONObject(), pushSwitchStatus);
            if (!TextUtils.isEmpty(pushSwitchStatus.getPushId())) {
                jSONObjectA.put(PushConstants.REGISTER_STATUS_PUSH_ID, pushSwitchStatus.getPushId());
            }
            jSONObjectA.put(PushConstants.PUSH_SWITCH_STATUS_IS_NOTIFICATION_SWITCH, pushSwitchStatus.isSwitchNotificationMessage());
            jSONObjectA.put(PushConstants.PUSH_SWITCH_STATUS_IS_THROUGH_SWITCH, pushSwitchStatus.isSwitchThroughMessage());
            String string = jSONObjectA.toString();
            DebugLogger.i("StatusSerialize", "register status serialize pushSwitchStatusToString success, statusText=" + string);
            return string;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize pushSwitchStatusToString error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    public static RegisterStatus b(String str) {
        DebugLogger.i("StatusSerialize", "register status serialize stringToRegisterStatus start, statusText=" + str);
        try {
            RegisterStatus registerStatus = new RegisterStatus();
            JSONObject jSONObject = new JSONObject(str);
            RegisterStatus registerStatus2 = (RegisterStatus) b(jSONObject, registerStatus);
            if (!jSONObject.isNull(PushConstants.REGISTER_STATUS_PUSH_ID)) {
                registerStatus2.setPushId(jSONObject.getString(PushConstants.REGISTER_STATUS_PUSH_ID));
            }
            if (!jSONObject.isNull(PushConstants.REGISTER_STATUS_EXPIRE_TIME)) {
                registerStatus2.setExpireTime(jSONObject.getInt(PushConstants.REGISTER_STATUS_EXPIRE_TIME));
            }
            DebugLogger.i("StatusSerialize", "register status serialize stringToRegisterStatus success, RegisterStatus=" + registerStatus2);
            return registerStatus2;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize stringToRegisterStatus error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(RegisterStatus registerStatus) throws JSONException {
        DebugLogger.i("StatusSerialize", "register status serialize registerStatusToString start, RegisterStatus=" + registerStatus);
        try {
            JSONObject jSONObjectA = a(new JSONObject(), registerStatus);
            if (!TextUtils.isEmpty(registerStatus.getPushId())) {
                jSONObjectA.put(PushConstants.REGISTER_STATUS_PUSH_ID, registerStatus.getPushId());
            }
            if (registerStatus.getExpireTime() > 0) {
                jSONObjectA.put(PushConstants.REGISTER_STATUS_EXPIRE_TIME, registerStatus.getExpireTime());
            }
            String string = jSONObjectA.toString();
            DebugLogger.i("StatusSerialize", "register status serialize registerStatusToString success, statusText=" + string);
            return string;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize registerStatusToString error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(SubAliasStatus subAliasStatus) throws JSONException {
        DebugLogger.i("StatusSerialize", "register status serialize subAliasStatusToString start, SubAliasStatus=" + subAliasStatus);
        try {
            JSONObject jSONObjectA = a(new JSONObject(), subAliasStatus);
            if (!TextUtils.isEmpty(subAliasStatus.getPushId())) {
                jSONObjectA.put(PushConstants.REGISTER_STATUS_PUSH_ID, subAliasStatus.getPushId());
            }
            jSONObjectA.put(PushConstants.SUB_ALIAS_STATUS_NAME, subAliasStatus.getAlias());
            String string = jSONObjectA.toString();
            DebugLogger.i("StatusSerialize", "register status serialize subAliasStatusToString success, statusText=" + string);
            return string;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize subAliasStatusToString error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(SubTagsStatus subTagsStatus) throws JSONException {
        DebugLogger.i("StatusSerialize", "register status serialize subTagsStatusToString start, SubTagsStatus=" + subTagsStatus);
        try {
            JSONObject jSONObjectA = a(new JSONObject(), subTagsStatus);
            if (!TextUtils.isEmpty(subTagsStatus.getPushId())) {
                jSONObjectA.put(PushConstants.REGISTER_STATUS_PUSH_ID, subTagsStatus.getPushId());
            }
            if (subTagsStatus.getTagList() != null) {
                JSONArray jSONArray = new JSONArray();
                for (int i2 = 0; i2 < subTagsStatus.getTagList().size(); i2++) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(PushConstants.SUB_TAGS_STATUS_ID, subTagsStatus.getTagList().get(i2).getTagId());
                    jSONObject.put(PushConstants.SUB_TAGS_STATUS_NAME, subTagsStatus.getTagList().get(i2).getTagName());
                    jSONArray.put(jSONObject);
                }
                jSONObjectA.put(PushConstants.SUB_TAGS_STATUS_LIST, jSONArray);
            }
            String string = jSONObjectA.toString();
            DebugLogger.i("StatusSerialize", "register status serialize subTagsStatusToString success, statusText=" + string);
            return string;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize subTagsStatusToString error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(UnRegisterStatus unRegisterStatus) throws JSONException {
        DebugLogger.i("StatusSerialize", "register status serialize unregisterStatusToString start, UnRegisterStatus=" + unRegisterStatus);
        try {
            JSONObject jSONObjectA = a(new JSONObject(), unRegisterStatus);
            jSONObjectA.put(PushConstants.UNREGISTER_STATUS_IS_SUCCESS, unRegisterStatus.isUnRegisterSuccess());
            String string = jSONObjectA.toString();
            DebugLogger.i("StatusSerialize", "register status serialize unregisterStatusToString success, statusText=" + string);
            return string;
        } catch (JSONException e2) {
            DebugLogger.e("StatusSerialize", "register status serialize unregisterStatusToString error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    private static JSONObject a(JSONObject jSONObject, BasicPushStatus basicPushStatus) throws JSONException {
        if (jSONObject == null || basicPushStatus == null) {
            return null;
        }
        jSONObject.put("code", basicPushStatus.getCode());
        jSONObject.put("message", basicPushStatus.getMessage());
        return jSONObject;
    }
}
