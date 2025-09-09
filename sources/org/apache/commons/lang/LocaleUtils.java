package org.apache.commons.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class LocaleUtils {
    private static List cAvailableLocaleList;
    private static Set cAvailableLocaleSet;
    private static final Map cLanguagesByCountry = Collections.synchronizedMap(new HashMap());
    private static final Map cCountriesByLanguage = Collections.synchronizedMap(new HashMap());

    public static List availableLocaleList() {
        if (cAvailableLocaleList == null) {
            initAvailableLocaleList();
        }
        return cAvailableLocaleList;
    }

    public static Set availableLocaleSet() {
        if (cAvailableLocaleSet == null) {
            initAvailableLocaleSet();
        }
        return cAvailableLocaleSet;
    }

    public static List countriesByLanguage(String str) {
        List listUnmodifiableList = (List) cCountriesByLanguage.get(str);
        if (listUnmodifiableList == null) {
            if (str != null) {
                ArrayList arrayList = new ArrayList();
                List listAvailableLocaleList = availableLocaleList();
                for (int i2 = 0; i2 < listAvailableLocaleList.size(); i2++) {
                    Locale locale = (Locale) listAvailableLocaleList.get(i2);
                    if (str.equals(locale.getLanguage()) && locale.getCountry().length() != 0 && locale.getVariant().length() == 0) {
                        arrayList.add(locale);
                    }
                }
                listUnmodifiableList = Collections.unmodifiableList(arrayList);
            } else {
                listUnmodifiableList = Collections.EMPTY_LIST;
            }
            cCountriesByLanguage.put(str, listUnmodifiableList);
        }
        return listUnmodifiableList;
    }

    private static synchronized void initAvailableLocaleList() {
        if (cAvailableLocaleList == null) {
            cAvailableLocaleList = Collections.unmodifiableList(Arrays.asList(Locale.getAvailableLocales()));
        }
    }

    private static synchronized void initAvailableLocaleSet() {
        if (cAvailableLocaleSet == null) {
            cAvailableLocaleSet = Collections.unmodifiableSet(new HashSet(availableLocaleList()));
        }
    }

    public static boolean isAvailableLocale(Locale locale) {
        return availableLocaleList().contains(locale);
    }

    public static List languagesByCountry(String str) {
        List listUnmodifiableList = (List) cLanguagesByCountry.get(str);
        if (listUnmodifiableList == null) {
            if (str != null) {
                ArrayList arrayList = new ArrayList();
                List listAvailableLocaleList = availableLocaleList();
                for (int i2 = 0; i2 < listAvailableLocaleList.size(); i2++) {
                    Locale locale = (Locale) listAvailableLocaleList.get(i2);
                    if (str.equals(locale.getCountry()) && locale.getVariant().length() == 0) {
                        arrayList.add(locale);
                    }
                }
                listUnmodifiableList = Collections.unmodifiableList(arrayList);
            } else {
                listUnmodifiableList = Collections.EMPTY_LIST;
            }
            cLanguagesByCountry.put(str, listUnmodifiableList);
        }
        return listUnmodifiableList;
    }

    public static List localeLookupList(Locale locale) {
        return localeLookupList(locale, locale);
    }

    public static Locale toLocale(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length != 2 && length != 5 && length < 7) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid locale format: ");
            stringBuffer.append(str);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        char cCharAt = str.charAt(0);
        char cCharAt2 = str.charAt(1);
        if (cCharAt < 'a' || cCharAt > 'z' || cCharAt2 < 'a' || cCharAt2 > 'z') {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Invalid locale format: ");
            stringBuffer2.append(str);
            throw new IllegalArgumentException(stringBuffer2.toString());
        }
        if (length == 2) {
            return new Locale(str, "");
        }
        if (str.charAt(2) != '_') {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Invalid locale format: ");
            stringBuffer3.append(str);
            throw new IllegalArgumentException(stringBuffer3.toString());
        }
        char cCharAt3 = str.charAt(3);
        if (cCharAt3 == '_') {
            return new Locale(str.substring(0, 2), "", str.substring(4));
        }
        char cCharAt4 = str.charAt(4);
        if (cCharAt3 < 'A' || cCharAt3 > 'Z' || cCharAt4 < 'A' || cCharAt4 > 'Z') {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Invalid locale format: ");
            stringBuffer4.append(str);
            throw new IllegalArgumentException(stringBuffer4.toString());
        }
        if (length == 5) {
            return new Locale(str.substring(0, 2), str.substring(3, 5));
        }
        if (str.charAt(5) == '_') {
            return new Locale(str.substring(0, 2), str.substring(3, 5), str.substring(6));
        }
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append("Invalid locale format: ");
        stringBuffer5.append(str);
        throw new IllegalArgumentException(stringBuffer5.toString());
    }

    public static List localeLookupList(Locale locale, Locale locale2) {
        ArrayList arrayList = new ArrayList(4);
        if (locale != null) {
            arrayList.add(locale);
            if (locale.getVariant().length() > 0) {
                arrayList.add(new Locale(locale.getLanguage(), locale.getCountry()));
            }
            if (locale.getCountry().length() > 0) {
                arrayList.add(new Locale(locale.getLanguage(), ""));
            }
            if (!arrayList.contains(locale2)) {
                arrayList.add(locale2);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }
}
