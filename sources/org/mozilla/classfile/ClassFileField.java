package org.mozilla.classfile;

/* loaded from: classes5.dex */
final class ClassFileField {
    private short itsAttr1;
    private short itsAttr2;
    private short itsAttr3;
    private short itsFlags;
    private boolean itsHasAttributes = false;
    private int itsIndex;
    private short itsNameIndex;
    private short itsTypeIndex;

    ClassFileField(short s2, short s3, short s4) {
        this.itsNameIndex = s2;
        this.itsTypeIndex = s3;
        this.itsFlags = s4;
    }

    int getWriteSize() {
        return !this.itsHasAttributes ? 8 : 16;
    }

    void setAttributes(short s2, short s3, short s4, int i2) {
        this.itsHasAttributes = true;
        this.itsAttr1 = s2;
        this.itsAttr2 = s3;
        this.itsAttr3 = s4;
        this.itsIndex = i2;
    }

    int write(byte[] bArr, int i2) {
        int iPutInt16 = ClassFileWriter.putInt16(this.itsTypeIndex, bArr, ClassFileWriter.putInt16(this.itsNameIndex, bArr, ClassFileWriter.putInt16(this.itsFlags, bArr, i2)));
        if (!this.itsHasAttributes) {
            return ClassFileWriter.putInt16(0, bArr, iPutInt16);
        }
        return ClassFileWriter.putInt16(this.itsIndex, bArr, ClassFileWriter.putInt16(this.itsAttr3, bArr, ClassFileWriter.putInt16(this.itsAttr2, bArr, ClassFileWriter.putInt16(this.itsAttr1, bArr, ClassFileWriter.putInt16(1, bArr, iPutInt16)))));
    }
}
