package com.baseflow.geocoding.utils;

import java.util.StringTokenizer;

/* loaded from: classes3.dex */
public class AddressLineParser {
    private static final String ADDRESS_LINE_DELIMITER = ",";

    public static String getStreet(String str) {
        if (str != null && !str.isEmpty()) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, ",", false);
            if (stringTokenizer.hasMoreTokens()) {
                return stringTokenizer.nextToken();
            }
        }
        return null;
    }
}
