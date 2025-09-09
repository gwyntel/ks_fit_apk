package org.apache.commons.io;

import java.io.Serializable;

/* loaded from: classes5.dex */
public enum IOCase implements Serializable {
    SENSITIVE("Sensitive", true),
    INSENSITIVE("Insensitive", false),
    SYSTEM("System", !FilenameUtils.a());

    private static final long serialVersionUID = -6343169151696340687L;
    private final String name;
    private final transient boolean sensitive;

    IOCase(String str, boolean z2) {
        this.name = str;
        this.sensitive = z2;
    }

    public static IOCase forName(String str) {
        for (IOCase iOCase : values()) {
            if (iOCase.getName().equals(str)) {
                return iOCase;
            }
        }
        throw new IllegalArgumentException("Invalid IOCase name: " + str);
    }

    private Object readResolve() {
        return forName(this.name);
    }

    public int checkCompareTo(String str, String str2) {
        if (str == null || str2 == null) {
            throw new NullPointerException("The strings must not be null");
        }
        return this.sensitive ? str.compareTo(str2) : str.compareToIgnoreCase(str2);
    }

    public boolean checkEndsWith(String str, String str2) {
        int length = str2.length();
        return str.regionMatches(!this.sensitive, str.length() - length, str2, 0, length);
    }

    public boolean checkEquals(String str, String str2) {
        if (str == null || str2 == null) {
            throw new NullPointerException("The strings must not be null");
        }
        return this.sensitive ? str.equals(str2) : str.equalsIgnoreCase(str2);
    }

    public int checkIndexOf(String str, int i2, String str2) {
        int length = str.length() - str2.length();
        if (length < i2) {
            return -1;
        }
        while (i2 <= length) {
            if (checkRegionMatches(str, i2, str2)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public boolean checkRegionMatches(String str, int i2, String str2) {
        return str.regionMatches(!this.sensitive, i2, str2, 0, str2.length());
    }

    public boolean checkStartsWith(String str, String str2) {
        return str.regionMatches(!this.sensitive, 0, str2, 0, str2.length());
    }

    public String getName() {
        return this.name;
    }

    public boolean isCaseSensitive() {
        return this.sensitive;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.name;
    }
}
