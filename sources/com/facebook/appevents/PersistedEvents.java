package com.facebook.appevents;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
class PersistedEvents implements Serializable {
    private static final long serialVersionUID = 20160629001L;
    private HashMap<AccessTokenAppIdPair, List<AppEvent>> events;

    static class SerializationProxyV1 implements Serializable {
        private static final long serialVersionUID = 20160629001L;
        private final HashMap<AccessTokenAppIdPair, List<AppEvent>> proxyEvents;

        private Object readResolve() {
            return new PersistedEvents(this.proxyEvents);
        }

        private SerializationProxyV1(HashMap<AccessTokenAppIdPair, List<AppEvent>> map) {
            this.proxyEvents = map;
        }
    }

    public PersistedEvents() {
        this.events = new HashMap<>();
    }

    private Object writeReplace() {
        return new SerializationProxyV1(this.events);
    }

    public void addEvents(AccessTokenAppIdPair accessTokenAppIdPair, List<AppEvent> list) {
        if (this.events.containsKey(accessTokenAppIdPair)) {
            this.events.get(accessTokenAppIdPair).addAll(list);
        } else {
            this.events.put(accessTokenAppIdPair, list);
        }
    }

    public boolean containsKey(AccessTokenAppIdPair accessTokenAppIdPair) {
        return this.events.containsKey(accessTokenAppIdPair);
    }

    public List<AppEvent> get(AccessTokenAppIdPair accessTokenAppIdPair) {
        return this.events.get(accessTokenAppIdPair);
    }

    public Set<AccessTokenAppIdPair> keySet() {
        return this.events.keySet();
    }

    public PersistedEvents(HashMap<AccessTokenAppIdPair, List<AppEvent>> map) {
        HashMap<AccessTokenAppIdPair, List<AppEvent>> map2 = new HashMap<>();
        this.events = map2;
        map2.putAll(map);
    }
}
