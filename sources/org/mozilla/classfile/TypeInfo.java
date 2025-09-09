package org.mozilla.classfile;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.io.PrintStream;
import org.apache.commons.io.IOUtils;

/* loaded from: classes5.dex */
final class TypeInfo {
    static final int DOUBLE = 3;
    static final int FLOAT = 2;
    static final int INTEGER = 1;
    static final int LONG = 4;
    static final int NULL = 5;
    static final int OBJECT_TAG = 7;
    static final int TOP = 0;
    static final int UNINITIALIZED_THIS = 6;
    static final int UNINITIALIZED_VAR_TAG = 8;

    private TypeInfo() {
    }

    static final int OBJECT(int i2) {
        return ((i2 & 65535) << 8) | 7;
    }

    static final int UNINITIALIZED_VARIABLE(int i2) {
        return ((i2 & 65535) << 8) | 8;
    }

    static final int fromType(String str, ConstantPool constantPool) {
        if (str.length() != 1) {
            return OBJECT(str, constantPool);
        }
        char cCharAt = str.charAt(0);
        if (cCharAt == 'F') {
            return 2;
        }
        if (cCharAt != 'S' && cCharAt != 'Z' && cCharAt != 'I') {
            if (cCharAt == 'J') {
                return 4;
            }
            switch (cCharAt) {
                case 'B':
                case 'C':
                    break;
                case 'D':
                    return 3;
                default:
                    throw new IllegalArgumentException("bad type");
            }
        }
        return 1;
    }

    private static Class<?> getClassFromInternalName(String str) {
        try {
            return Class.forName(str.replace(IOUtils.DIR_SEPARATOR_UNIX, '.'));
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException(e2);
        }
    }

    static final int getPayload(int i2) {
        return i2 >>> 8;
    }

    static final String getPayloadAsType(int i2, ConstantPool constantPool) {
        if (getTag(i2) == 7) {
            return (String) constantPool.getConstantData(getPayload(i2));
        }
        throw new IllegalArgumentException("expecting object type");
    }

    static final int getTag(int i2) {
        return i2 & 255;
    }

    static boolean isTwoWords(int i2) {
        return i2 == 3 || i2 == 4;
    }

    static int merge(int i2, int i3, ConstantPool constantPool) {
        int tag = getTag(i2);
        int tag2 = getTag(i3);
        boolean z2 = tag == 7;
        boolean z3 = tag2 == 7;
        if (i2 == i3 || (z2 && i3 == 5)) {
            return i2;
        }
        if (tag == 0 || tag2 == 0) {
            return 0;
        }
        if (i2 == 5 && z3) {
            return i3;
        }
        if (z2 && z3) {
            String payloadAsType = getPayloadAsType(i2, constantPool);
            String payloadAsType2 = getPayloadAsType(i3, constantPool);
            String str = (String) constantPool.getConstantData(2);
            String str2 = (String) constantPool.getConstantData(4);
            if (payloadAsType.equals(str)) {
                payloadAsType = str2;
            }
            if (payloadAsType2.equals(str)) {
                payloadAsType2 = str2;
            }
            Class<?> classFromInternalName = getClassFromInternalName(payloadAsType);
            Class<?> classFromInternalName2 = getClassFromInternalName(payloadAsType2);
            if (classFromInternalName.isAssignableFrom(classFromInternalName2)) {
                return i2;
            }
            if (classFromInternalName2.isAssignableFrom(classFromInternalName)) {
                return i3;
            }
            if (classFromInternalName2.isInterface() || classFromInternalName.isInterface()) {
                return OBJECT("java/lang/Object", constantPool);
            }
            for (Class<? super Object> superclass = classFromInternalName2.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
                if (superclass.isAssignableFrom(classFromInternalName)) {
                    return OBJECT(ClassFileWriter.getSlashedForm(superclass.getName()), constantPool);
                }
            }
        }
        throw new IllegalArgumentException("bad merge attempt between " + toString(i2, constantPool) + " and " + toString(i3, constantPool));
    }

    static void print(int[] iArr, int[] iArr2, ConstantPool constantPool) {
        print(iArr, iArr.length, iArr2, iArr2.length, constantPool);
    }

    static String toString(int i2, ConstantPool constantPool) {
        int tag = getTag(i2);
        switch (tag) {
            case 0:
                return ViewHierarchyConstants.DIMENSION_TOP_KEY;
            case 1:
                return "int";
            case 2:
                return "float";
            case 3:
                return TmpConstant.TYPE_VALUE_DOUBLE;
            case 4:
                return "long";
            case 5:
                return TmpConstant.GROUP_ROLE_UNKNOWN;
            case 6:
                return "uninitialized_this";
            default:
                if (tag == 7) {
                    return getPayloadAsType(i2, constantPool);
                }
                if (tag == 8) {
                    return "uninitialized";
                }
                throw new IllegalArgumentException("bad type");
        }
    }

    static final int OBJECT(String str, ConstantPool constantPool) {
        return OBJECT(constantPool.addClass(str));
    }

    static void print(int[] iArr, int i2, int[] iArr2, int i3, ConstantPool constantPool) {
        PrintStream printStream = System.out;
        printStream.print("locals: ");
        printStream.println(toString(iArr, i2, constantPool));
        printStream.print("stack: ");
        printStream.println(toString(iArr2, i3, constantPool));
        printStream.println();
    }

    private static String toString(int[] iArr, int i2, ConstantPool constantPool) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 > 0) {
                sb.append(", ");
            }
            sb.append(toString(iArr[i3], constantPool));
        }
        sb.append("]");
        return sb.toString();
    }
}
