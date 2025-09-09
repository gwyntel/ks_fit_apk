package org.mozilla.classfile;

/* loaded from: classes5.dex */
final class ClassFileMethod {
    private byte[] itsCodeAttribute;
    private short itsFlags;
    private String itsName;
    private short itsNameIndex;
    private String itsType;
    private short itsTypeIndex;

    ClassFileMethod(String str, short s2, String str2, short s3, short s4) {
        this.itsName = str;
        this.itsNameIndex = s2;
        this.itsType = str2;
        this.itsTypeIndex = s3;
        this.itsFlags = s4;
    }

    short getFlags() {
        return this.itsFlags;
    }

    String getName() {
        return this.itsName;
    }

    String getType() {
        return this.itsType;
    }

    int getWriteSize() {
        return this.itsCodeAttribute.length + 8;
    }

    void setCodeAttribute(byte[] bArr) {
        this.itsCodeAttribute = bArr;
    }

    int write(byte[] bArr, int i2) {
        int iPutInt16 = ClassFileWriter.putInt16(1, bArr, ClassFileWriter.putInt16(this.itsTypeIndex, bArr, ClassFileWriter.putInt16(this.itsNameIndex, bArr, ClassFileWriter.putInt16(this.itsFlags, bArr, i2))));
        byte[] bArr2 = this.itsCodeAttribute;
        System.arraycopy(bArr2, 0, bArr, iPutInt16, bArr2.length);
        return iPutInt16 + this.itsCodeAttribute.length;
    }
}
