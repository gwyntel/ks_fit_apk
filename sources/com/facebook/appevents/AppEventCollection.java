package com.facebook.appevents;

import android.content.Context;
import com.facebook.FacebookSdk;
import com.facebook.internal.AttributionIdentifiers;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes3.dex */
class AppEventCollection {
    private final HashMap<AccessTokenAppIdPair, SessionEventsState> stateMap = new HashMap<>();

    private synchronized SessionEventsState getSessionEventsState(AccessTokenAppIdPair accessTokenAppIdPair) {
        SessionEventsState sessionEventsState;
        try {
            sessionEventsState = this.stateMap.get(accessTokenAppIdPair);
            if (sessionEventsState == null) {
                Context applicationContext = FacebookSdk.getApplicationContext();
                sessionEventsState = new SessionEventsState(AttributionIdentifiers.getAttributionIdentifiers(applicationContext), AppEventsLogger.getAnonymousAppDeviceGUID(applicationContext));
            }
            this.stateMap.put(accessTokenAppIdPair, sessionEventsState);
        } catch (Throwable th) {
            throw th;
        }
        return sessionEventsState;
    }

    public synchronized void addEvent(AccessTokenAppIdPair accessTokenAppIdPair, AppEvent appEvent) {
        getSessionEventsState(accessTokenAppIdPair).addEvent(appEvent);
    }

    public synchronized void addPersistedEvents(PersistedEvents persistedEvents) {
        if (persistedEvents == null) {
            return;
        }
        for (AccessTokenAppIdPair accessTokenAppIdPair : persistedEvents.keySet()) {
            SessionEventsState sessionEventsState = getSessionEventsState(accessTokenAppIdPair);
            Iterator<AppEvent> it = persistedEvents.get(accessTokenAppIdPair).iterator();
            while (it.hasNext()) {
                sessionEventsState.addEvent(it.next());
            }
        }
    }

    public synchronized SessionEventsState get(AccessTokenAppIdPair accessTokenAppIdPair) {
        return this.stateMap.get(accessTokenAppIdPair);
    }

    public synchronized int getEventCount() {
        int accumulatedEventCount;
        Iterator<SessionEventsState> it = this.stateMap.values().iterator();
        accumulatedEventCount = 0;
        while (it.hasNext()) {
            accumulatedEventCount += it.next().getAccumulatedEventCount();
        }
        return accumulatedEventCount;
    }

    public synchronized Set<AccessTokenAppIdPair> keySet() {
        return this.stateMap.keySet();
    }
}
