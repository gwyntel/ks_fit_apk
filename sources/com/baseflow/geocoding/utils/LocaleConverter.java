package com.baseflow.geocoding.utils;

import java.util.Locale;
import java.util.StringTokenizer;

/* loaded from: classes3.dex */
public class LocaleConverter {
    private static final String LOCALE_DELIMITER = "_";

    public static Locale fromLanguageTag(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, "_", false);
        String strNextToken = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : null;
        String strNextToken2 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : null;
        String strNextToken3 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : null;
        if (strNextToken != null && strNextToken2 != null && strNextToken3 != null) {
            return new Locale(strNextToken, strNextToken2, strNextToken3);
        }
        if (strNextToken != null && strNextToken2 != null) {
            return new Locale(strNextToken, strNextToken2);
        }
        if (strNextToken != null) {
            return new Locale(strNextToken);
        }
        return null;
    }
}
