package com.facebook.appevents.suggestedevents;

import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
class SuggestedEventViewHierarchy {
    static final String TAG = "com.facebook.appevents.suggestedevents.SuggestedEventViewHierarchy";
    private static final List<Class<? extends View>> blacklistedViews = new ArrayList(Arrays.asList(Switch.class, Spinner.class, DatePicker.class, TimePicker.class, RadioGroup.class, RatingBar.class, EditText.class, AdapterView.class));

    SuggestedEventViewHierarchy() {
    }

    static List<View> getAllClickableViews(View view) {
        ArrayList arrayList = new ArrayList();
        Iterator<Class<? extends View>> it = blacklistedViews.iterator();
        while (it.hasNext()) {
            if (it.next().isInstance(view)) {
                return arrayList;
            }
        }
        if (view.isClickable()) {
            arrayList.add(view);
        }
        Iterator<View> it2 = ViewHierarchy.getChildrenOfView(view).iterator();
        while (it2.hasNext()) {
            arrayList.addAll(getAllClickableViews(it2.next()));
        }
        return arrayList;
    }

    static JSONObject getDictionaryOfView(View view, View view2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (view == view2) {
            try {
                jSONObject.put(ViewHierarchyConstants.IS_INTERACTED_KEY, true);
            } catch (JSONException unused) {
            }
        }
        updateBasicInfo(view, jSONObject);
        JSONArray jSONArray = new JSONArray();
        List<View> childrenOfView = ViewHierarchy.getChildrenOfView(view);
        for (int i2 = 0; i2 < childrenOfView.size(); i2++) {
            jSONArray.put(getDictionaryOfView(childrenOfView.get(i2), view2));
        }
        jSONObject.put(ViewHierarchyConstants.CHILDREN_VIEW_KEY, jSONArray);
        return jSONObject;
    }

    static void updateBasicInfo(View view, JSONObject jSONObject) throws JSONException {
        try {
            String textOfView = ViewHierarchy.getTextOfView(view);
            String hintOfView = ViewHierarchy.getHintOfView(view);
            jSONObject.put(ViewHierarchyConstants.CLASS_NAME_KEY, view.getClass().getSimpleName());
            jSONObject.put(ViewHierarchyConstants.CLASS_TYPE_BITMASK_KEY, ViewHierarchy.getClassTypeBitmask(view));
            if (!textOfView.isEmpty()) {
                jSONObject.put("text", textOfView);
            }
            if (!hintOfView.isEmpty()) {
                jSONObject.put(ViewHierarchyConstants.HINT_KEY, hintOfView);
            }
            if (view instanceof EditText) {
                jSONObject.put(ViewHierarchyConstants.INPUT_TYPE_KEY, ((EditText) view).getInputType());
            }
        } catch (JSONException unused) {
        }
    }
}
