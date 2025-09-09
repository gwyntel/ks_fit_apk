package com.alibaba.fastjson.asm;

/* loaded from: classes2.dex */
final class Item {
    int hashCode;
    int index;
    int intVal;
    long longVal;
    Item next;
    String strVal1;
    String strVal2;
    String strVal3;
    int type;

    Item() {
    }

    boolean isEqualTo(Item item) {
        int i2 = this.type;
        if (i2 != 1) {
            if (i2 != 15) {
                if (i2 == 12) {
                    return item.strVal1.equals(this.strVal1) && item.strVal2.equals(this.strVal2);
                }
                if (i2 != 13) {
                    switch (i2) {
                        case 3:
                        case 4:
                            if (item.intVal != this.intVal) {
                                break;
                            }
                            break;
                        case 5:
                        case 6:
                            break;
                        case 7:
                        case 8:
                            break;
                        default:
                            if (!item.strVal1.equals(this.strVal1) || !item.strVal2.equals(this.strVal2) || !item.strVal3.equals(this.strVal3)) {
                                break;
                            }
                            break;
                    }
                    return false;
                }
            }
            return item.longVal == this.longVal;
        }
        return item.strVal1.equals(this.strVal1);
    }

    void set(int i2, String str, String str2, String str3) {
        this.type = i2;
        this.strVal1 = str;
        this.strVal2 = str2;
        this.strVal3 = str3;
        if (i2 != 1 && i2 != 7 && i2 != 8) {
            if (i2 == 12) {
                this.hashCode = (i2 + (str.hashCode() * str2.hashCode())) & Integer.MAX_VALUE;
                return;
            } else if (i2 != 13) {
                this.hashCode = (i2 + (str.hashCode() * str2.hashCode() * str3.hashCode())) & Integer.MAX_VALUE;
                return;
            }
        }
        this.hashCode = (i2 + str.hashCode()) & Integer.MAX_VALUE;
    }

    Item(int i2, Item item) {
        this.index = i2;
        this.type = item.type;
        this.intVal = item.intVal;
        this.longVal = item.longVal;
        this.strVal1 = item.strVal1;
        this.strVal2 = item.strVal2;
        this.strVal3 = item.strVal3;
        this.hashCode = item.hashCode;
    }

    void set(int i2) {
        this.type = 3;
        this.intVal = i2;
        this.hashCode = (3 + i2) & Integer.MAX_VALUE;
    }
}
