package org.mozilla.classfile;

/* loaded from: classes5.dex */
final class ConstantEntry {
    private int hashcode;
    private int intval;
    private long longval;
    private String str1;
    private String str2;
    private int type;

    ConstantEntry(int i2, int i3, String str, String str2) {
        this.type = i2;
        this.intval = i3;
        this.str1 = str;
        this.str2 = str2;
        this.hashcode = i2 ^ (i3 + (str.hashCode() * str2.hashCode()));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ConstantEntry)) {
            return false;
        }
        ConstantEntry constantEntry = (ConstantEntry) obj;
        int i2 = this.type;
        if (i2 != constantEntry.type) {
            return false;
        }
        if (i2 == 3 || i2 == 4) {
            return this.intval == constantEntry.intval;
        }
        if (i2 == 5 || i2 == 6) {
            return this.longval == constantEntry.longval;
        }
        if (i2 == 12) {
            return this.str1.equals(constantEntry.str1) && this.str2.equals(constantEntry.str2);
        }
        if (i2 == 18) {
            return this.intval == constantEntry.intval && this.str1.equals(constantEntry.str1) && this.str2.equals(constantEntry.str2);
        }
        throw new RuntimeException("unsupported constant type");
    }

    public int hashCode() {
        return this.hashcode;
    }
}
