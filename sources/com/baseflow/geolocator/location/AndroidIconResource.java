package com.baseflow.geolocator.location;

import java.util.Map;

/* loaded from: classes3.dex */
public class AndroidIconResource {
    private final String defType;
    private final String name;

    private AndroidIconResource(String str, String str2) {
        this.name = str;
        this.defType = str2;
    }

    public static AndroidIconResource parseArguments(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        return new AndroidIconResource((String) map.get("name"), (String) map.get("defType"));
    }

    public String getDefType() {
        return this.defType;
    }

    public String getName() {
        return this.name;
    }
}
