package com.facebook.appevents.codeless.internal;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class ParameterComponent {
    private static final String PARAMETER_NAME_KEY = "name";
    private static final String PARAMETER_PATH_KEY = "path";
    private static final String PARAMETER_VALUE_KEY = "value";
    public final String name;
    public final List<PathComponent> path;
    public final String pathType;
    public final String value;

    public ParameterComponent(JSONObject jSONObject) throws JSONException {
        this.name = jSONObject.getString("name");
        this.value = jSONObject.optString("value");
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(PARAMETER_PATH_KEY);
        if (jSONArrayOptJSONArray != null) {
            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                arrayList.add(new PathComponent(jSONArrayOptJSONArray.getJSONObject(i2)));
            }
        }
        this.path = arrayList;
        this.pathType = jSONObject.optString(Constants.EVENT_MAPPING_PATH_TYPE_KEY, Constants.PATH_TYPE_ABSOLUTE);
    }
}
