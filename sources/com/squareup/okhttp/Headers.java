package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HttpDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes4.dex */
public final class Headers {
    private final String[] namesAndValues;

    public static Headers of(String... strArr) {
        if (strArr == null || strArr.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values");
        }
        String[] strArr2 = (String[]) strArr.clone();
        for (int i2 = 0; i2 < strArr2.length; i2++) {
            String str = strArr2[i2];
            if (str == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            strArr2[i2] = str.trim();
        }
        for (int i3 = 0; i3 < strArr2.length; i3 += 2) {
            String str2 = strArr2[i3];
            String str3 = strArr2[i3 + 1];
            if (str2.length() == 0 || str2.indexOf(0) != -1 || str3.indexOf(0) != -1) {
                throw new IllegalArgumentException("Unexpected header: " + str2 + ": " + str3);
            }
        }
        return new Headers(strArr2);
    }

    public String get(String str) {
        return get(this.namesAndValues, str);
    }

    public Date getDate(String str) {
        String str2 = get(str);
        if (str2 != null) {
            return HttpDate.parse(str2);
        }
        return null;
    }

    public String name(int i2) {
        int i3 = i2 * 2;
        if (i3 < 0) {
            return null;
        }
        String[] strArr = this.namesAndValues;
        if (i3 >= strArr.length) {
            return null;
        }
        return strArr[i3];
    }

    public Set<String> names() {
        TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            treeSet.add(name(i2));
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        Collections.addAll(builder.namesAndValues, this.namesAndValues);
        return builder;
    }

    public int size() {
        return this.namesAndValues.length / 2;
    }

    public Map<String, List<String>> toMultimap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            String strName = name(i2);
            List arrayList = (List) linkedHashMap.get(strName);
            if (arrayList == null) {
                arrayList = new ArrayList(2);
                linkedHashMap.put(strName, arrayList);
            }
            arrayList.add(value(i2));
        }
        return linkedHashMap;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            sb.append(name(i2));
            sb.append(": ");
            sb.append(value(i2));
            sb.append("\n");
        }
        return sb.toString();
    }

    public String value(int i2) {
        int i3 = (i2 * 2) + 1;
        if (i3 < 0) {
            return null;
        }
        String[] strArr = this.namesAndValues;
        if (i3 >= strArr.length) {
            return null;
        }
        return strArr[i3];
    }

    public List<String> values(String str) {
        int size = size();
        ArrayList arrayList = null;
        for (int i2 = 0; i2 < size; i2++) {
            if (str.equalsIgnoreCase(name(i2))) {
                if (arrayList == null) {
                    arrayList = new ArrayList(2);
                }
                arrayList.add(value(i2));
            }
        }
        return arrayList != null ? Collections.unmodifiableList(arrayList) : Collections.emptyList();
    }

    private Headers(Builder builder) {
        this.namesAndValues = (String[]) builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    private static String get(String[] strArr, String str) {
        for (int length = strArr.length - 2; length >= 0; length -= 2) {
            if (str.equalsIgnoreCase(strArr[length])) {
                return strArr[length + 1];
            }
        }
        return null;
    }

    public static final class Builder {
        private final List<String> namesAndValues = new ArrayList(20);

        private void checkNameAndValue(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (str.isEmpty()) {
                throw new IllegalArgumentException("name is empty");
            }
            int length = str.length();
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = str.charAt(i2);
                if (cCharAt <= 31 || cCharAt >= 127) {
                    throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(cCharAt), Integer.valueOf(i2), str));
                }
            }
            if (str2 == null) {
                throw new IllegalArgumentException("value == null");
            }
            int length2 = str2.length();
            for (int i3 = 0; i3 < length2; i3++) {
                char cCharAt2 = str2.charAt(i3);
                if (cCharAt2 <= 31 || cCharAt2 >= 127) {
                    throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header value: %s", Integer.valueOf(cCharAt2), Integer.valueOf(i3), str2));
                }
            }
        }

        public Builder add(String str) {
            int iIndexOf = str.indexOf(":");
            if (iIndexOf != -1) {
                return add(str.substring(0, iIndexOf).trim(), str.substring(iIndexOf + 1));
            }
            throw new IllegalArgumentException("Unexpected header: " + str);
        }

        Builder b(String str) {
            int iIndexOf = str.indexOf(":", 1);
            return iIndexOf != -1 ? c(str.substring(0, iIndexOf), str.substring(iIndexOf + 1)) : str.startsWith(":") ? c("", str.substring(1)) : c("", str);
        }

        public Headers build() {
            return new Headers(this);
        }

        Builder c(String str, String str2) {
            this.namesAndValues.add(str);
            this.namesAndValues.add(str2.trim());
            return this;
        }

        public String get(String str) {
            for (int size = this.namesAndValues.size() - 2; size >= 0; size -= 2) {
                if (str.equalsIgnoreCase(this.namesAndValues.get(size))) {
                    return this.namesAndValues.get(size + 1);
                }
            }
            return null;
        }

        public Builder removeAll(String str) {
            int i2 = 0;
            while (i2 < this.namesAndValues.size()) {
                if (str.equalsIgnoreCase(this.namesAndValues.get(i2))) {
                    this.namesAndValues.remove(i2);
                    this.namesAndValues.remove(i2);
                    i2 -= 2;
                }
                i2 += 2;
            }
            return this;
        }

        public Builder set(String str, String str2) {
            checkNameAndValue(str, str2);
            removeAll(str);
            c(str, str2);
            return this;
        }

        public Builder add(String str, String str2) {
            checkNameAndValue(str, str2);
            return c(str, str2);
        }
    }

    private Headers(String[] strArr) {
        this.namesAndValues = strArr;
    }

    public static Headers of(Map<String, String> map) {
        if (map != null) {
            String[] strArr = new String[map.size() * 2];
            int i2 = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    String strTrim = entry.getKey().trim();
                    String strTrim2 = entry.getValue().trim();
                    if (strTrim.length() != 0 && strTrim.indexOf(0) == -1 && strTrim2.indexOf(0) == -1) {
                        strArr[i2] = strTrim;
                        strArr[i2 + 1] = strTrim2;
                        i2 += 2;
                    } else {
                        throw new IllegalArgumentException("Unexpected header: " + strTrim + ": " + strTrim2);
                    }
                } else {
                    throw new IllegalArgumentException("Headers cannot be null");
                }
            }
            return new Headers(strArr);
        }
        throw new IllegalArgumentException("Expected map with header names and values");
    }
}
