package org.mozilla.classfile;

/* loaded from: classes5.dex */
final class ExceptionTableEntry {
    short itsCatchType;
    int itsEndLabel;
    int itsHandlerLabel;
    int itsStartLabel;

    ExceptionTableEntry(int i2, int i3, int i4, short s2) {
        this.itsStartLabel = i2;
        this.itsEndLabel = i3;
        this.itsHandlerLabel = i4;
        this.itsCatchType = s2;
    }
}
