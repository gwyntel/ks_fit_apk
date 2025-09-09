package com.facebook.appevents;

import android.content.Context;
import android.os.Bundle;
import com.facebook.GraphRequest;
import com.facebook.appevents.eventdeactivation.EventDeactivationManager;
import com.facebook.appevents.internal.AppEventsLoggerUtility;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Utility;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
class SessionEventsState {
    private String anonymousAppDeviceGUID;
    private AttributionIdentifiers attributionIdentifiers;
    private int numSkippedEventsDueToFullBuffer;
    private List<AppEvent> accumulatedEvents = new ArrayList();
    private List<AppEvent> inFlightEvents = new ArrayList();
    private final int MAX_ACCUMULATED_LOG_EVENTS = 1000;

    public SessionEventsState(AttributionIdentifiers attributionIdentifiers, String str) {
        this.attributionIdentifiers = attributionIdentifiers;
        this.anonymousAppDeviceGUID = str;
    }

    public synchronized void accumulatePersistedEvents(List<AppEvent> list) {
        this.accumulatedEvents.addAll(list);
    }

    public synchronized void addEvent(AppEvent appEvent) {
        try {
            if (this.accumulatedEvents.size() + this.inFlightEvents.size() >= 1000) {
                this.numSkippedEventsDueToFullBuffer++;
            } else {
                this.accumulatedEvents.add(appEvent);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void clearInFlightAndStats(boolean z2) {
        if (z2) {
            try {
                this.accumulatedEvents.addAll(this.inFlightEvents);
            } catch (Throwable th) {
                throw th;
            }
        }
        this.inFlightEvents.clear();
        this.numSkippedEventsDueToFullBuffer = 0;
    }

    public synchronized int getAccumulatedEventCount() {
        return this.accumulatedEvents.size();
    }

    public synchronized List<AppEvent> getEventsToPersist() {
        List<AppEvent> list;
        list = this.accumulatedEvents;
        this.accumulatedEvents = new ArrayList();
        return list;
    }

    public int populateRequest(GraphRequest graphRequest, Context context, boolean z2, boolean z3) throws JSONException {
        synchronized (this) {
            try {
                int i2 = this.numSkippedEventsDueToFullBuffer;
                EventDeactivationManager.processEvents(this.inFlightEvents);
                this.inFlightEvents.addAll(this.accumulatedEvents);
                this.accumulatedEvents.clear();
                JSONArray jSONArray = new JSONArray();
                for (AppEvent appEvent : this.inFlightEvents) {
                    if (!appEvent.isChecksumValid()) {
                        Utility.logd("Event with invalid checksum: %s", appEvent.toString());
                    } else if (z2 || !appEvent.getIsImplicit()) {
                        jSONArray.put(appEvent.getJSONObject());
                    }
                }
                if (jSONArray.length() == 0) {
                    return 0;
                }
                populateRequest(graphRequest, context, i2, jSONArray, z3);
                return jSONArray.length();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void populateRequest(GraphRequest graphRequest, Context context, int i2, JSONArray jSONArray, boolean z2) throws JSONException {
        JSONObject jSONObject;
        try {
            jSONObject = AppEventsLoggerUtility.getJSONObjectForGraphAPICall(AppEventsLoggerUtility.GraphAPIActivityType.CUSTOM_APP_EVENTS, this.attributionIdentifiers, this.anonymousAppDeviceGUID, z2, context);
            if (this.numSkippedEventsDueToFullBuffer > 0) {
                jSONObject.put("num_skipped_events", i2);
            }
        } catch (JSONException unused) {
            jSONObject = new JSONObject();
        }
        graphRequest.setGraphObject(jSONObject);
        Bundle parameters = graphRequest.getParameters();
        if (parameters == null) {
            parameters = new Bundle();
        }
        String string = jSONArray.toString();
        if (string != null) {
            parameters.putString("custom_events", string);
            graphRequest.setTag(string);
        }
        graphRequest.setParameters(parameters);
    }
}
