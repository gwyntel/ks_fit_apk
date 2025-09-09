package org.mozilla.classfile;

import kotlin.UShort;
import org.mozilla.classfile.ClassFileWriter;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.UintMap;

/* loaded from: classes5.dex */
final class ConstantPool {
    static final byte CONSTANT_Class = 7;
    static final byte CONSTANT_Double = 6;
    static final byte CONSTANT_Fieldref = 9;
    static final byte CONSTANT_Float = 4;
    static final byte CONSTANT_Integer = 3;
    static final byte CONSTANT_InterfaceMethodref = 11;
    static final byte CONSTANT_InvokeDynamic = 18;
    static final byte CONSTANT_Long = 5;
    static final byte CONSTANT_MethodHandle = 15;
    static final byte CONSTANT_MethodType = 16;
    static final byte CONSTANT_Methodref = 10;
    static final byte CONSTANT_NameAndType = 12;
    static final byte CONSTANT_String = 8;
    static final byte CONSTANT_Utf8 = 1;
    private static final int ConstantPoolSize = 256;
    private static final int MAX_UTF_ENCODING_SIZE = 65535;
    private ClassFileWriter cfw;
    private UintMap itsStringConstHash = new UintMap();
    private ObjToIntMap itsUtf8Hash = new ObjToIntMap();
    private ObjToIntMap itsFieldRefHash = new ObjToIntMap();
    private ObjToIntMap itsMethodRefHash = new ObjToIntMap();
    private ObjToIntMap itsClassHash = new ObjToIntMap();
    private ObjToIntMap itsConstantHash = new ObjToIntMap();
    private UintMap itsConstantData = new UintMap();
    private UintMap itsPoolTypes = new UintMap();
    private int itsTopIndex = 1;
    private byte[] itsPool = new byte[256];
    private int itsTop = 0;

    ConstantPool(ClassFileWriter classFileWriter) {
        this.cfw = classFileWriter;
    }

    private short addNameAndType(String str, String str2) throws RuntimeException {
        short sAddUtf8 = addUtf8(str);
        short sAddUtf82 = addUtf8(str2);
        ensure(5);
        byte[] bArr = this.itsPool;
        int i2 = this.itsTop;
        int i3 = i2 + 1;
        this.itsTop = i3;
        bArr[i2] = 12;
        int iPutInt16 = ClassFileWriter.putInt16(sAddUtf8, bArr, i3);
        this.itsTop = iPutInt16;
        this.itsTop = ClassFileWriter.putInt16(sAddUtf82, this.itsPool, iPutInt16);
        this.itsPoolTypes.put(this.itsTopIndex, 12);
        int i4 = this.itsTopIndex;
        this.itsTopIndex = i4 + 1;
        return (short) i4;
    }

    private void ensure(int i2) {
        int i3 = this.itsTop;
        int i4 = i3 + i2;
        byte[] bArr = this.itsPool;
        if (i4 > bArr.length) {
            int length = bArr.length * 2;
            if (i3 + i2 > length) {
                length = i3 + i2;
            }
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, i3);
            this.itsPool = bArr2;
        }
    }

    short addClass(String str) {
        String str2;
        int i2 = this.itsClassHash.get(str, -1);
        if (i2 == -1) {
            if (str.indexOf(46) > 0) {
                String slashedForm = ClassFileWriter.getSlashedForm(str);
                int i3 = this.itsClassHash.get(slashedForm, -1);
                if (i3 != -1) {
                    this.itsClassHash.put(str, i3);
                }
                str2 = slashedForm;
                i2 = i3;
            } else {
                str2 = str;
            }
            if (i2 == -1) {
                short sAddUtf8 = addUtf8(str2);
                ensure(3);
                byte[] bArr = this.itsPool;
                int i4 = this.itsTop;
                int i5 = i4 + 1;
                this.itsTop = i5;
                bArr[i4] = 7;
                this.itsTop = ClassFileWriter.putInt16(sAddUtf8, bArr, i5);
                i2 = this.itsTopIndex;
                this.itsTopIndex = i2 + 1;
                this.itsClassHash.put(str2, i2);
                if (!str.equals(str2)) {
                    this.itsClassHash.put(str, i2);
                }
            }
        }
        setConstantData(i2, str);
        this.itsPoolTypes.put(i2, 7);
        return (short) i2;
    }

    int addConstant(int i2) throws RuntimeException {
        ensure(5);
        byte[] bArr = this.itsPool;
        int i3 = this.itsTop;
        int i4 = i3 + 1;
        this.itsTop = i4;
        bArr[i3] = 3;
        this.itsTop = ClassFileWriter.putInt32(i2, bArr, i4);
        this.itsPoolTypes.put(this.itsTopIndex, 3);
        int i5 = this.itsTopIndex;
        this.itsTopIndex = i5 + 1;
        return (short) i5;
    }

    short addFieldRef(String str, String str2, String str3) {
        FieldOrMethodRef fieldOrMethodRef = new FieldOrMethodRef(str, str2, str3);
        int i2 = this.itsFieldRefHash.get(fieldOrMethodRef, -1);
        if (i2 == -1) {
            short sAddNameAndType = addNameAndType(str2, str3);
            short sAddClass = addClass(str);
            ensure(5);
            byte[] bArr = this.itsPool;
            int i3 = this.itsTop;
            int i4 = i3 + 1;
            this.itsTop = i4;
            bArr[i3] = 9;
            int iPutInt16 = ClassFileWriter.putInt16(sAddClass, bArr, i4);
            this.itsTop = iPutInt16;
            this.itsTop = ClassFileWriter.putInt16(sAddNameAndType, this.itsPool, iPutInt16);
            i2 = this.itsTopIndex;
            this.itsTopIndex = i2 + 1;
            this.itsFieldRefHash.put(fieldOrMethodRef, i2);
        }
        setConstantData(i2, fieldOrMethodRef);
        this.itsPoolTypes.put(i2, 9);
        return (short) i2;
    }

    short addInterfaceMethodRef(String str, String str2, String str3) throws RuntimeException {
        short sAddNameAndType = addNameAndType(str2, str3);
        short sAddClass = addClass(str);
        ensure(5);
        byte[] bArr = this.itsPool;
        int i2 = this.itsTop;
        int i3 = i2 + 1;
        this.itsTop = i3;
        bArr[i2] = 11;
        int iPutInt16 = ClassFileWriter.putInt16(sAddClass, bArr, i3);
        this.itsTop = iPutInt16;
        this.itsTop = ClassFileWriter.putInt16(sAddNameAndType, this.itsPool, iPutInt16);
        setConstantData(this.itsTopIndex, new FieldOrMethodRef(str, str2, str3));
        this.itsPoolTypes.put(this.itsTopIndex, 11);
        int i4 = this.itsTopIndex;
        this.itsTopIndex = i4 + 1;
        return (short) i4;
    }

    short addInvokeDynamic(String str, String str2, int i2) {
        ConstantEntry constantEntry = new ConstantEntry(18, i2, str, str2);
        int i3 = this.itsConstantHash.get(constantEntry, -1);
        if (i3 == -1) {
            short sAddNameAndType = addNameAndType(str, str2);
            ensure(5);
            byte[] bArr = this.itsPool;
            int i4 = this.itsTop;
            int i5 = i4 + 1;
            this.itsTop = i5;
            bArr[i4] = 18;
            int iPutInt16 = ClassFileWriter.putInt16(i2, bArr, i5);
            this.itsTop = iPutInt16;
            this.itsTop = ClassFileWriter.putInt16(sAddNameAndType, this.itsPool, iPutInt16);
            i3 = this.itsTopIndex;
            this.itsTopIndex = i3 + 1;
            this.itsConstantHash.put(constantEntry, i3);
            setConstantData(i3, str2);
            this.itsPoolTypes.put(i3, 18);
        }
        return (short) i3;
    }

    short addMethodHandle(ClassFileWriter.MHandle mHandle) {
        int i2 = this.itsConstantHash.get(mHandle, -1);
        if (i2 == -1) {
            byte b2 = mHandle.tag;
            short sAddFieldRef = b2 <= 4 ? addFieldRef(mHandle.owner, mHandle.name, mHandle.desc) : b2 == 9 ? addInterfaceMethodRef(mHandle.owner, mHandle.name, mHandle.desc) : addMethodRef(mHandle.owner, mHandle.name, mHandle.desc);
            ensure(4);
            byte[] bArr = this.itsPool;
            int i3 = this.itsTop;
            int i4 = i3 + 1;
            this.itsTop = i4;
            bArr[i3] = 15;
            int i5 = i3 + 2;
            this.itsTop = i5;
            bArr[i4] = mHandle.tag;
            this.itsTop = ClassFileWriter.putInt16(sAddFieldRef, bArr, i5);
            i2 = this.itsTopIndex;
            this.itsTopIndex = i2 + 1;
            this.itsConstantHash.put(mHandle, i2);
            this.itsPoolTypes.put(i2, 15);
        }
        return (short) i2;
    }

    short addMethodRef(String str, String str2, String str3) throws RuntimeException {
        FieldOrMethodRef fieldOrMethodRef = new FieldOrMethodRef(str, str2, str3);
        int i2 = this.itsMethodRefHash.get(fieldOrMethodRef, -1);
        if (i2 == -1) {
            short sAddNameAndType = addNameAndType(str2, str3);
            short sAddClass = addClass(str);
            ensure(5);
            byte[] bArr = this.itsPool;
            int i3 = this.itsTop;
            int i4 = i3 + 1;
            this.itsTop = i4;
            bArr[i3] = 10;
            int iPutInt16 = ClassFileWriter.putInt16(sAddClass, bArr, i4);
            this.itsTop = iPutInt16;
            this.itsTop = ClassFileWriter.putInt16(sAddNameAndType, this.itsPool, iPutInt16);
            i2 = this.itsTopIndex;
            this.itsTopIndex = i2 + 1;
            this.itsMethodRefHash.put(fieldOrMethodRef, i2);
        }
        setConstantData(i2, fieldOrMethodRef);
        this.itsPoolTypes.put(i2, 10);
        return (short) i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    short addUtf8(java.lang.String r14) {
        /*
            r13 = this;
            org.mozilla.javascript.ObjToIntMap r0 = r13.itsUtf8Hash
            r1 = -1
            int r0 = r0.get(r14, r1)
            r2 = 1
            if (r0 != r1) goto Lad
            int r1 = r14.length()
            r3 = 65535(0xffff, float:9.1834E-41)
            if (r1 <= r3) goto L16
        L13:
            r6 = r2
            goto La2
        L16:
            int r4 = r1 * 3
            int r4 = r4 + 3
            r13.ensure(r4)
            int r4 = r13.itsTop
            byte[] r5 = r13.itsPool
            r5[r4] = r2
            int r4 = r4 + 3
            org.mozilla.classfile.ClassFileWriter r5 = r13.cfw
            char[] r5 = r5.getCharBuffer(r1)
            r6 = 0
            r14.getChars(r6, r1, r5, r6)
            r7 = r6
        L30:
            if (r7 == r1) goto L7e
            char r8 = r5[r7]
            if (r8 == 0) goto L43
            r9 = 127(0x7f, float:1.78E-43)
            if (r8 > r9) goto L43
            byte[] r9 = r13.itsPool
            int r10 = r4 + 1
            byte r8 = (byte) r8
            r9[r4] = r8
            r4 = r10
            goto L7b
        L43:
            r9 = 2047(0x7ff, float:2.868E-42)
            if (r8 <= r9) goto L67
            byte[] r9 = r13.itsPool
            int r10 = r4 + 1
            int r11 = r8 >> 12
            r11 = r11 | 224(0xe0, float:3.14E-43)
            byte r11 = (byte) r11
            r9[r4] = r11
            int r11 = r4 + 2
            int r12 = r8 >> 6
            r12 = r12 & 63
            r12 = r12 | 128(0x80, float:1.8E-43)
            byte r12 = (byte) r12
            r9[r10] = r12
            int r4 = r4 + 3
            r8 = r8 & 63
            r8 = r8 | 128(0x80, float:1.8E-43)
            byte r8 = (byte) r8
            r9[r11] = r8
            goto L7b
        L67:
            byte[] r9 = r13.itsPool
            int r10 = r4 + 1
            int r11 = r8 >> 6
            r11 = r11 | 192(0xc0, float:2.69E-43)
            byte r11 = (byte) r11
            r9[r4] = r11
            int r4 = r4 + 2
            r8 = r8 & 63
            r8 = r8 | 128(0x80, float:1.8E-43)
            byte r8 = (byte) r8
            r9[r10] = r8
        L7b:
            int r7 = r7 + 1
            goto L30
        L7e:
            int r1 = r13.itsTop
            int r5 = r1 + 3
            int r5 = r4 - r5
            if (r5 <= r3) goto L87
            goto L13
        L87:
            byte[] r0 = r13.itsPool
            int r3 = r1 + 1
            int r7 = r5 >>> 8
            byte r7 = (byte) r7
            r0[r3] = r7
            int r1 = r1 + 2
            byte r3 = (byte) r5
            r0[r1] = r3
            r13.itsTop = r4
            int r0 = r13.itsTopIndex
            int r1 = r0 + 1
            r13.itsTopIndex = r1
            org.mozilla.javascript.ObjToIntMap r1 = r13.itsUtf8Hash
            r1.put(r14, r0)
        La2:
            if (r6 != 0) goto La5
            goto Lad
        La5:
            java.lang.IllegalArgumentException r14 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Too big string"
            r14.<init>(r0)
            throw r14
        Lad:
            r13.setConstantData(r0, r14)
            org.mozilla.javascript.UintMap r14 = r13.itsPoolTypes
            r14.put(r0, r2)
            short r14 = (short) r0
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.classfile.ConstantPool.addUtf8(java.lang.String):short");
    }

    Object getConstantData(int i2) {
        return this.itsConstantData.getObject(i2);
    }

    byte getConstantType(int i2) {
        return (byte) this.itsPoolTypes.getInt(i2, 0);
    }

    int getUtfEncodingLimit(String str, int i2, int i3) {
        int i4 = 65535;
        if ((i3 - i2) * 3 <= 65535) {
            return i3;
        }
        while (i2 != i3) {
            char cCharAt = str.charAt(i2);
            i4 = (cCharAt == 0 || cCharAt > 127) ? cCharAt < 2047 ? i4 - 2 : i4 - 3 : i4 - 1;
            if (i4 < 0) {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    int getWriteSize() {
        return this.itsTop + 2;
    }

    boolean isUnderUtfEncodingLimit(String str) {
        int length = str.length();
        if (length * 3 <= 65535) {
            return true;
        }
        return length <= 65535 && length == getUtfEncodingLimit(str, 0, length);
    }

    void setConstantData(int i2, Object obj) throws RuntimeException {
        this.itsConstantData.put(i2, obj);
    }

    int write(byte[] bArr, int i2) {
        int iPutInt16 = ClassFileWriter.putInt16((short) this.itsTopIndex, bArr, i2);
        System.arraycopy(this.itsPool, 0, bArr, iPutInt16, this.itsTop);
        return iPutInt16 + this.itsTop;
    }

    int addConstant(long j2) throws RuntimeException {
        ensure(9);
        byte[] bArr = this.itsPool;
        int i2 = this.itsTop;
        int i3 = i2 + 1;
        this.itsTop = i3;
        bArr[i2] = 5;
        this.itsTop = ClassFileWriter.putInt64(j2, bArr, i3);
        int i4 = this.itsTopIndex;
        this.itsTopIndex = i4 + 2;
        this.itsPoolTypes.put(i4, 5);
        return i4;
    }

    int addConstant(float f2) throws RuntimeException {
        ensure(5);
        byte[] bArr = this.itsPool;
        int i2 = this.itsTop;
        this.itsTop = i2 + 1;
        bArr[i2] = 4;
        this.itsTop = ClassFileWriter.putInt32(Float.floatToIntBits(f2), this.itsPool, this.itsTop);
        this.itsPoolTypes.put(this.itsTopIndex, 4);
        int i3 = this.itsTopIndex;
        this.itsTopIndex = i3 + 1;
        return i3;
    }

    int addConstant(double d2) throws RuntimeException {
        ensure(9);
        byte[] bArr = this.itsPool;
        int i2 = this.itsTop;
        this.itsTop = i2 + 1;
        bArr[i2] = 6;
        this.itsTop = ClassFileWriter.putInt64(Double.doubleToLongBits(d2), this.itsPool, this.itsTop);
        int i3 = this.itsTopIndex;
        this.itsTopIndex = i3 + 2;
        this.itsPoolTypes.put(i3, 6);
        return i3;
    }

    int addConstant(String str) throws RuntimeException {
        int iAddUtf8 = addUtf8(str) & UShort.MAX_VALUE;
        int i2 = this.itsStringConstHash.getInt(iAddUtf8, -1);
        if (i2 == -1) {
            i2 = this.itsTopIndex;
            this.itsTopIndex = i2 + 1;
            ensure(3);
            byte[] bArr = this.itsPool;
            int i3 = this.itsTop;
            int i4 = i3 + 1;
            this.itsTop = i4;
            bArr[i3] = 8;
            this.itsTop = ClassFileWriter.putInt16(iAddUtf8, bArr, i4);
            this.itsStringConstHash.put(iAddUtf8, i2);
        }
        this.itsPoolTypes.put(i2, 8);
        return i2;
    }

    int addConstant(Object obj) {
        if (!(obj instanceof Integer) && !(obj instanceof Byte) && !(obj instanceof Short)) {
            if (obj instanceof Character) {
                return addConstant((int) ((Character) obj).charValue());
            }
            if (obj instanceof Boolean) {
                return addConstant(((Boolean) obj).booleanValue() ? 1 : 0);
            }
            if (obj instanceof Float) {
                return addConstant(((Float) obj).floatValue());
            }
            if (obj instanceof Long) {
                return addConstant(((Long) obj).longValue());
            }
            if (obj instanceof Double) {
                return addConstant(((Double) obj).doubleValue());
            }
            if (obj instanceof String) {
                return addConstant((String) obj);
            }
            if (obj instanceof ClassFileWriter.MHandle) {
                return addMethodHandle((ClassFileWriter.MHandle) obj);
            }
            throw new IllegalArgumentException("value " + obj);
        }
        return addConstant(((Number) obj).intValue());
    }
}
