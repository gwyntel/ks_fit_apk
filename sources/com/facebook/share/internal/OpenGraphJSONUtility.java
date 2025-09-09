package com.facebook.share.internal;

import androidx.annotation.Nullable;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class OpenGraphJSONUtility {

    public interface PhotoJSONProcessor {
        JSONObject toJSONObject(SharePhoto sharePhoto);
    }

    private OpenGraphJSONUtility() {
    }

    private static JSONArray toJSONArray(List list, PhotoJSONProcessor photoJSONProcessor) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(toJSONValue(it.next(), photoJSONProcessor));
        }
        return jSONArray;
    }

    public static JSONObject toJSONObject(ShareOpenGraphAction shareOpenGraphAction, PhotoJSONProcessor photoJSONProcessor) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : shareOpenGraphAction.keySet()) {
            jSONObject.put(str, toJSONValue(shareOpenGraphAction.get(str), photoJSONProcessor));
        }
        return jSONObject;
    }

    public static Object toJSONValue(@Nullable Object obj, PhotoJSONProcessor photoJSONProcessor) throws JSONException {
        if (obj == null) {
            return JSONObject.NULL;
        }
        if ((obj instanceof String) || (obj instanceof Boolean) || (obj instanceof Double) || (obj instanceof Float) || (obj instanceof Integer) || (obj instanceof Long)) {
            return obj;
        }
        if (obj instanceof SharePhoto) {
            if (photoJSONProcessor != null) {
                return photoJSONProcessor.toJSONObject((SharePhoto) obj);
            }
            return null;
        }
        if (obj instanceof ShareOpenGraphObject) {
            return toJSONObject((ShareOpenGraphObject) obj, photoJSONProcessor);
        }
        if (obj instanceof List) {
            return toJSONArray((List) obj, photoJSONProcessor);
        }
        throw new IllegalArgumentException("Invalid object found for JSON serialization: " + obj.toString());
    }

    private static JSONObject toJSONObject(ShareOpenGraphObject shareOpenGraphObject, PhotoJSONProcessor photoJSONProcessor) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : shareOpenGraphObject.keySet()) {
            jSONObject.put(str, toJSONValue(shareOpenGraphObject.get(str), photoJSONProcessor));
        }
        return jSONObject;
    }
}
