package com.facebook.appevents.codeless.internal;

import bolts.MeasurementEvent;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class EventBinding {
    private final String activityName;
    private final String appVersion;
    private final String componentId;
    private final String eventName;
    private final MappingMethod method;
    private final List<ParameterComponent> parameters;
    private final List<PathComponent> path;
    private final String pathType;
    private final ActionType type;

    public enum ActionType {
        CLICK,
        SELECTED,
        TEXT_CHANGED
    }

    public enum MappingMethod {
        MANUAL,
        INFERENCE
    }

    public EventBinding(String str, MappingMethod mappingMethod, ActionType actionType, String str2, List<PathComponent> list, List<ParameterComponent> list2, String str3, String str4, String str5) {
        this.eventName = str;
        this.method = mappingMethod;
        this.type = actionType;
        this.appVersion = str2;
        this.path = list;
        this.parameters = list2;
        this.componentId = str3;
        this.pathType = str4;
        this.activityName = str5;
    }

    public static EventBinding getInstanceFromJson(JSONObject jSONObject) throws JSONException, IllegalArgumentException {
        String string = jSONObject.getString(MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY);
        String string2 = jSONObject.getString("method");
        Locale locale = Locale.ENGLISH;
        MappingMethod mappingMethodValueOf = MappingMethod.valueOf(string2.toUpperCase(locale));
        ActionType actionTypeValueOf = ActionType.valueOf(jSONObject.getString("event_type").toUpperCase(locale));
        String string3 = jSONObject.getString("app_version");
        JSONArray jSONArray = jSONObject.getJSONArray("path");
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            arrayList.add(new PathComponent(jSONArray.getJSONObject(i2)));
        }
        String strOptString = jSONObject.optString(Constants.EVENT_MAPPING_PATH_TYPE_KEY, Constants.PATH_TYPE_ABSOLUTE);
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(PushConstants.PARAMS);
        ArrayList arrayList2 = new ArrayList();
        if (jSONArrayOptJSONArray != null) {
            for (int i3 = 0; i3 < jSONArrayOptJSONArray.length(); i3++) {
                arrayList2.add(new ParameterComponent(jSONArrayOptJSONArray.getJSONObject(i3)));
            }
        }
        return new EventBinding(string, mappingMethodValueOf, actionTypeValueOf, string3, arrayList, arrayList2, jSONObject.optString("component_id"), strOptString, jSONObject.optString("activity_name"));
    }

    public static List<EventBinding> parseArray(JSONArray jSONArray) {
        int length;
        ArrayList arrayList = new ArrayList();
        if (jSONArray != null) {
            try {
                length = jSONArray.length();
            } catch (IllegalArgumentException | JSONException unused) {
            }
        } else {
            length = 0;
        }
        for (int i2 = 0; i2 < length; i2++) {
            arrayList.add(getInstanceFromJson(jSONArray.getJSONObject(i2)));
        }
        return arrayList;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public String getComponentId() {
        return this.componentId;
    }

    public String getEventName() {
        return this.eventName;
    }

    public MappingMethod getMethod() {
        return this.method;
    }

    public String getPathType() {
        return this.pathType;
    }

    public ActionType getType() {
        return this.type;
    }

    public List<ParameterComponent> getViewParameters() {
        return Collections.unmodifiableList(this.parameters);
    }

    public List<PathComponent> getViewPath() {
        return Collections.unmodifiableList(this.path);
    }
}
