package org.apache.commons.lang;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class CharSet implements Serializable {
    public static final CharSet ASCII_ALPHA;
    public static final CharSet ASCII_ALPHA_LOWER;
    public static final CharSet ASCII_ALPHA_UPPER;
    public static final CharSet ASCII_NUMERIC;
    protected static final Map COMMON;
    public static final CharSet EMPTY;
    private static final long serialVersionUID = 5947847346149275958L;
    private final Set set = Collections.synchronizedSet(new HashSet());

    static {
        CharSet charSet = new CharSet((String) null);
        EMPTY = charSet;
        CharSet charSet2 = new CharSet("a-zA-Z");
        ASCII_ALPHA = charSet2;
        CharSet charSet3 = new CharSet("a-z");
        ASCII_ALPHA_LOWER = charSet3;
        CharSet charSet4 = new CharSet("A-Z");
        ASCII_ALPHA_UPPER = charSet4;
        CharSet charSet5 = new CharSet("0-9");
        ASCII_NUMERIC = charSet5;
        Map mapSynchronizedMap = Collections.synchronizedMap(new HashMap());
        COMMON = mapSynchronizedMap;
        mapSynchronizedMap.put(null, charSet);
        mapSynchronizedMap.put("", charSet);
        mapSynchronizedMap.put("a-zA-Z", charSet2);
        mapSynchronizedMap.put("A-Za-z", charSet2);
        mapSynchronizedMap.put("a-z", charSet3);
        mapSynchronizedMap.put("A-Z", charSet4);
        mapSynchronizedMap.put("0-9", charSet5);
    }

    protected CharSet(String str) {
        add(str);
    }

    public static CharSet getInstance(String str) {
        Object obj = COMMON.get(str);
        return obj != null ? (CharSet) obj : new CharSet(str);
    }

    protected void add(String str) {
        if (str == null) {
            return;
        }
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = length - i2;
            if (i3 >= 4 && str.charAt(i2) == '^' && str.charAt(i2 + 2) == '-') {
                this.set.add(CharRange.isNotIn(str.charAt(i2 + 1), str.charAt(i2 + 3)));
                i2 += 4;
            } else if (i3 >= 3 && str.charAt(i2 + 1) == '-') {
                this.set.add(CharRange.isIn(str.charAt(i2), str.charAt(i2 + 2)));
                i2 += 3;
            } else if (i3 < 2 || str.charAt(i2) != '^') {
                this.set.add(CharRange.is(str.charAt(i2)));
                i2++;
            } else {
                this.set.add(CharRange.isNot(str.charAt(i2 + 1)));
                i2 += 2;
            }
        }
    }

    public boolean contains(char c2) {
        Iterator it = this.set.iterator();
        while (it.hasNext()) {
            if (((CharRange) it.next()).contains(c2)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CharSet) {
            return this.set.equals(((CharSet) obj).set);
        }
        return false;
    }

    public CharRange[] getCharRanges() {
        Set set = this.set;
        return (CharRange[]) set.toArray(new CharRange[set.size()]);
    }

    public int hashCode() {
        return this.set.hashCode() + 89;
    }

    public String toString() {
        return this.set.toString();
    }

    protected CharSet(String[] strArr) {
        for (String str : strArr) {
            add(str);
        }
    }

    public static CharSet getInstance(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        return new CharSet(strArr);
    }
}
