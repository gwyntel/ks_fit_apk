package org.mozilla.javascript.regexp;

/* loaded from: classes5.dex */
public class SubString {
    int index;
    int length;
    String str;

    public SubString() {
    }

    public String toString() {
        String str = this.str;
        if (str == null) {
            return "";
        }
        int i2 = this.index;
        return str.substring(i2, this.length + i2);
    }

    public SubString(String str) {
        this.str = str;
        this.index = 0;
        this.length = str.length();
    }

    public SubString(String str, int i2, int i3) {
        this.str = str;
        this.index = i2;
        this.length = i3;
    }
}
