package org.apache.commons.lang.text;

import java.util.Map;

/* loaded from: classes5.dex */
public abstract class StrLookup {
    private static final StrLookup NONE_LOOKUP = new MapStrLookup(null);
    private static final StrLookup SYSTEM_PROPERTIES_LOOKUP;

    static class MapStrLookup extends StrLookup {
        private final Map map;

        MapStrLookup(Map map) {
            this.map = map;
        }

        @Override // org.apache.commons.lang.text.StrLookup
        public String lookup(String str) {
            Object obj;
            Map map = this.map;
            if (map == null || (obj = map.get(str)) == null) {
                return null;
            }
            return obj.toString();
        }
    }

    static {
        StrLookup mapStrLookup;
        try {
            mapStrLookup = new MapStrLookup(System.getProperties());
        } catch (SecurityException unused) {
            mapStrLookup = NONE_LOOKUP;
        }
        SYSTEM_PROPERTIES_LOOKUP = mapStrLookup;
    }

    protected StrLookup() {
    }

    public static StrLookup mapLookup(Map map) {
        return new MapStrLookup(map);
    }

    public static StrLookup noneLookup() {
        return NONE_LOOKUP;
    }

    public static StrLookup systemPropertiesLookup() {
        return SYSTEM_PROPERTIES_LOOKUP;
    }

    public abstract String lookup(String str);
}
