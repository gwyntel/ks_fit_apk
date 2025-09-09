package com.facebook.appevents.suggestedevents;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.media3.extractor.text.ttml.TtmlNode;
import bolts.MeasurementEvent;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.InternalAppEventsLogger;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.appevents.ml.ModelManager;
import com.facebook.internal.Utility;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes3.dex */
public final class ViewOnClickListener implements View.OnClickListener {
    private static final String API_ENDPOINT = "%s/suggested_events";
    public static final String OTHER_EVENT = "other";
    private static final String TAG = "com.facebook.appevents.suggestedevents.ViewOnClickListener";
    private static final Set<Integer> viewsAttachedListener = new HashSet();
    private String activityName;

    @Nullable
    private View.OnClickListener baseListener;
    private WeakReference<View> hostViewWeakReference;
    private WeakReference<View> rootViewWeakReference;

    private ViewOnClickListener(View view, View view2, String str) {
        this.baseListener = ViewHierarchy.getExistingOnClickListener(view);
        this.hostViewWeakReference = new WeakReference<>(view);
        this.rootViewWeakReference = new WeakReference<>(view2);
        this.activityName = str.toLowerCase().replace(PushConstants.INTENT_ACTIVITY_NAME, "");
    }

    static void attachListener(View view, View view2, String str) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        int iHashCode = view.hashCode();
        Set<Integer> set = viewsAttachedListener;
        if (set.contains(Integer.valueOf(iHashCode))) {
            return;
        }
        ViewHierarchy.setOnClickListener(view, new ViewOnClickListener(view, view2, str));
        set.add(Integer.valueOf(iHashCode));
    }

    private void predictAndProcess(final String str, final String str2, final JSONObject jSONObject) {
        Utility.runOnNonUiThread(new Runnable() { // from class: com.facebook.appevents.suggestedevents.ViewOnClickListener.2
            @Override // java.lang.Runnable
            public void run() {
                String strPredict;
                try {
                    String lowerCase = Utility.getAppName(FacebookSdk.getApplicationContext()).toLowerCase();
                    float[] denseFeatures = FeatureExtractor.getDenseFeatures(jSONObject, lowerCase);
                    String textFeature = FeatureExtractor.getTextFeature(str2, ViewOnClickListener.this.activityName, lowerCase);
                    if (denseFeatures == null || (strPredict = ModelManager.predict(ModelManager.MODEL_SUGGESTED_EVENTS, denseFeatures, textFeature)) == null) {
                        return;
                    }
                    PredictionHistoryManager.addPrediction(str, strPredict);
                    if (strPredict.equals("other")) {
                        return;
                    }
                    ViewOnClickListener.processPredictedResult(strPredict, str2, denseFeatures);
                } catch (Exception unused) {
                }
            }
        });
    }

    private void process() throws JSONException {
        View view = this.rootViewWeakReference.get();
        View view2 = this.hostViewWeakReference.get();
        if (view == null || view2 == null) {
            return;
        }
        try {
            String pathID = PredictionHistoryManager.getPathID(view2);
            if (pathID == null) {
                return;
            }
            String textOfView = ViewHierarchy.getTextOfView(view2);
            if (queryHistoryAndProcess(pathID, textOfView)) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(ViewHierarchyConstants.VIEW_KEY, SuggestedEventViewHierarchy.getDictionaryOfView(view, view2));
            jSONObject.put(ViewHierarchyConstants.SCREEN_NAME_KEY, this.activityName);
            predictAndProcess(pathID, textOfView, jSONObject);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void processPredictedResult(String str, String str2, float[] fArr) throws JSONException {
        if (SuggestedEventsManager.isProductionEvents(str)) {
            new InternalAppEventsLogger(FacebookSdk.getApplicationContext()).logEventFromSE(str, str2);
        } else if (SuggestedEventsManager.isEligibleEvents(str)) {
            sendPredictedResult(str, str2, fArr);
        }
    }

    private static boolean queryHistoryAndProcess(String str, final String str2) {
        final String strQueryEvent = PredictionHistoryManager.queryEvent(str);
        if (strQueryEvent == null) {
            return false;
        }
        if (strQueryEvent.equals("other")) {
            return true;
        }
        Utility.runOnNonUiThread(new Runnable() { // from class: com.facebook.appevents.suggestedevents.ViewOnClickListener.1
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                ViewOnClickListener.processPredictedResult(strQueryEvent, str2, new float[0]);
            }
        });
        return true;
    }

    private static void sendPredictedResult(String str, String str2, float[] fArr) throws JSONException {
        Bundle bundle = new Bundle();
        try {
            bundle.putString(MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY, str);
            JSONObject jSONObject = new JSONObject();
            StringBuilder sb = new StringBuilder();
            for (float f2 : fArr) {
                sb.append(f2);
                sb.append(",");
            }
            jSONObject.put("dense", sb.toString());
            jSONObject.put("button_text", str2);
            bundle.putString(TtmlNode.TAG_METADATA, jSONObject.toString());
            GraphRequest graphRequestNewPostRequest = GraphRequest.newPostRequest(null, String.format(Locale.US, API_ENDPOINT, FacebookSdk.getApplicationId()), null, null);
            graphRequestNewPostRequest.setParameters(bundle);
            graphRequestNewPostRequest.executeAndWait();
        } catch (JSONException unused) {
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws JSONException {
        View.OnClickListener onClickListener = this.baseListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
        process();
    }
}
