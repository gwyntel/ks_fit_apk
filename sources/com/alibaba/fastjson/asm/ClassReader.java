package com.alibaba.fastjson.asm;

import com.google.common.base.Ascii;

/* loaded from: classes2.dex */
public class ClassReader {

    /* renamed from: b, reason: collision with root package name */
    public final byte[] f8778b;
    public final int header;
    private final int[] items;
    private final int maxStringLength;
    private boolean readAnnotations;
    private final String[] strings;

    /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ClassReader(java.io.InputStream r9, boolean r10) throws java.io.IOException {
        /*
            r8 = this;
            r8.<init>()
            r8.readAnnotations = r10
            java.io.ByteArrayOutputStream r10 = new java.io.ByteArrayOutputStream
            r10.<init>()
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
        Le:
            int r1 = r9.read(r0)
            r2 = -1
            r3 = 0
            if (r1 != r2) goto L71
            r9.close()
            byte[] r9 = r10.toByteArray()
            r8.f8778b = r9
            r9 = 8
            int r9 = r8.readUnsignedShort(r9)
            int[] r9 = new int[r9]
            r8.items = r9
            int r9 = r9.length
            java.lang.String[] r10 = new java.lang.String[r9]
            r8.strings = r10
            r10 = 1
            r0 = 10
            r1 = r10
        L32:
            if (r1 >= r9) goto L6c
            int[] r2 = r8.items
            int r4 = r0 + 1
            r2[r1] = r4
            byte[] r2 = r8.f8778b
            r2 = r2[r0]
            r5 = 3
            if (r2 == r10) goto L61
            r4 = 15
            r6 = 4
            if (r2 == r4) goto L5f
            r4 = 18
            r7 = 5
            if (r2 == r4) goto L5d
            if (r2 == r5) goto L5d
            if (r2 == r6) goto L5d
            if (r2 == r7) goto L58
            r4 = 6
            if (r2 == r4) goto L58
            switch(r2) {
                case 9: goto L5d;
                case 10: goto L5d;
                case 11: goto L5d;
                case 12: goto L5d;
                default: goto L57;
            }
        L57:
            goto L69
        L58:
            int r1 = r1 + 1
            r5 = 9
            goto L69
        L5d:
            r5 = r7
            goto L69
        L5f:
            r5 = r6
            goto L69
        L61:
            int r2 = r8.readUnsignedShort(r4)
            int r5 = r5 + r2
            if (r5 <= r3) goto L69
            r3 = r5
        L69:
            int r0 = r0 + r5
            int r1 = r1 + r10
            goto L32
        L6c:
            r8.maxStringLength = r3
            r8.header = r0
            return
        L71:
            if (r1 <= 0) goto Le
            r10.write(r0, r3, r1)
            goto Le
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.asm.ClassReader.<init>(java.io.InputStream, boolean):void");
    }

    private int getAttributes() {
        int i2 = this.header;
        int unsignedShort = i2 + 8 + (readUnsignedShort(i2 + 6) * 2);
        for (int unsignedShort2 = readUnsignedShort(unsignedShort); unsignedShort2 > 0; unsignedShort2--) {
            for (int unsignedShort3 = readUnsignedShort(unsignedShort + 8); unsignedShort3 > 0; unsignedShort3--) {
                unsignedShort += readInt(unsignedShort + 12) + 6;
            }
            unsignedShort += 8;
        }
        int i3 = unsignedShort + 2;
        for (int unsignedShort4 = readUnsignedShort(i3); unsignedShort4 > 0; unsignedShort4--) {
            for (int unsignedShort5 = readUnsignedShort(i3 + 8); unsignedShort5 > 0; unsignedShort5--) {
                i3 += readInt(i3 + 12) + 6;
            }
            i3 += 8;
        }
        return i3 + 2;
    }

    private int readInt(int i2) {
        byte[] bArr = this.f8778b;
        return (bArr[i2 + 3] & 255) | ((bArr[i2] & 255) << 24) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8);
    }

    private int readMethod(TypeCollector typeCollector, char[] cArr, int i2) {
        int unsignedShort = readUnsignedShort(i2);
        String utf8 = readUTF8(i2 + 2, cArr);
        String utf82 = readUTF8(i2 + 4, cArr);
        int i3 = i2 + 8;
        int i4 = 0;
        int i5 = 0;
        for (int unsignedShort2 = readUnsignedShort(i2 + 6); unsignedShort2 > 0; unsignedShort2--) {
            String utf83 = readUTF8(i3, cArr);
            int i6 = readInt(i3 + 2);
            int i7 = i3 + 6;
            if (utf83.equals("Code")) {
                i5 = i7;
            }
            i3 = i7 + i6;
        }
        MethodCollector methodCollectorVisitMethod = typeCollector.visitMethod(unsignedShort, utf8, utf82);
        if (methodCollectorVisitMethod != null && i5 != 0) {
            int i8 = i5 + 8 + readInt(i5 + 4);
            int i9 = i8 + 2;
            for (int unsignedShort3 = readUnsignedShort(i8); unsignedShort3 > 0; unsignedShort3--) {
                i9 += 8;
            }
            int i10 = i9 + 2;
            int i11 = 0;
            for (int unsignedShort4 = readUnsignedShort(i9); unsignedShort4 > 0; unsignedShort4--) {
                String utf84 = readUTF8(i10, cArr);
                if (utf84.equals("LocalVariableTable")) {
                    i4 = i10 + 6;
                } else if (utf84.equals("LocalVariableTypeTable")) {
                    i11 = i10 + 6;
                }
                i10 += readInt(i10 + 2) + 6;
            }
            if (i4 != 0) {
                if (i11 != 0) {
                    int unsignedShort5 = readUnsignedShort(i11) * 3;
                    int i12 = i11 + 2;
                    int[] iArr = new int[unsignedShort5];
                    while (unsignedShort5 > 0) {
                        iArr[unsignedShort5 - 1] = i12 + 6;
                        iArr[unsignedShort5 - 2] = readUnsignedShort(i12 + 8);
                        unsignedShort5 -= 3;
                        iArr[unsignedShort5] = readUnsignedShort(i12);
                        i12 += 10;
                    }
                }
                int i13 = i4 + 2;
                for (int unsignedShort6 = readUnsignedShort(i4); unsignedShort6 > 0; unsignedShort6--) {
                    methodCollectorVisitMethod.visitLocalVariable(readUTF8(i13 + 4, cArr), readUnsignedShort(i13 + 8));
                    i13 += 10;
                }
            }
        }
        return i3;
    }

    private String readUTF(int i2, int i3, char[] cArr) {
        int i4;
        int i5 = i3 + i2;
        byte[] bArr = this.f8778b;
        int i6 = 0;
        char c2 = 0;
        char c3 = 0;
        while (i2 < i5) {
            int i7 = i2 + 1;
            byte b2 = bArr[i2];
            if (c2 == 0) {
                int i8 = b2 & 255;
                if (i8 < 128) {
                    cArr[i6] = (char) i8;
                    i6++;
                } else if (i8 >= 224 || i8 <= 191) {
                    c3 = (char) (b2 & 15);
                    c2 = 2;
                } else {
                    i4 = b2 & Ascii.US;
                    c3 = (char) i4;
                    c2 = 1;
                }
            } else if (c2 == 1) {
                cArr[i6] = (char) ((b2 & 63) | (c3 << 6));
                i6++;
                c2 = 0;
            } else if (c2 == 2) {
                i4 = (b2 & 63) | (c3 << 6);
                c3 = (char) i4;
                c2 = 1;
            }
            i2 = i7;
        }
        return new String(cArr, 0, i6);
    }

    private String readUTF8(int i2, char[] cArr) {
        int unsignedShort = readUnsignedShort(i2);
        String[] strArr = this.strings;
        String str = strArr[unsignedShort];
        if (str != null) {
            return str;
        }
        int i3 = this.items[unsignedShort];
        String utf = readUTF(i3 + 2, readUnsignedShort(i3), cArr);
        strArr[unsignedShort] = utf;
        return utf;
    }

    private int readUnsignedShort(int i2) {
        byte[] bArr = this.f8778b;
        return (bArr[i2 + 1] & 255) | ((bArr[i2] & 255) << 8);
    }

    public void accept(TypeCollector typeCollector) {
        int i2;
        char[] cArr = new char[this.maxStringLength];
        if (this.readAnnotations) {
            int attributes = getAttributes();
            for (int unsignedShort = readUnsignedShort(attributes); unsignedShort > 0; unsignedShort--) {
                if ("RuntimeVisibleAnnotations".equals(readUTF8(attributes + 2, cArr))) {
                    i2 = attributes + 8;
                    break;
                }
                attributes += readInt(attributes + 4) + 6;
            }
            i2 = 0;
        } else {
            i2 = 0;
        }
        int i3 = this.header;
        int unsignedShort2 = readUnsignedShort(i3 + 6);
        int i4 = i3 + 8;
        for (int i5 = 0; i5 < unsignedShort2; i5++) {
            i4 += 2;
        }
        int i6 = i4 + 2;
        int i7 = i6;
        for (int unsignedShort3 = readUnsignedShort(i4); unsignedShort3 > 0; unsignedShort3--) {
            i7 += 8;
            for (int unsignedShort4 = readUnsignedShort(i7 + 6); unsignedShort4 > 0; unsignedShort4--) {
                i7 += readInt(i7 + 2) + 6;
            }
        }
        int i8 = i7 + 2;
        for (int unsignedShort5 = readUnsignedShort(i7); unsignedShort5 > 0; unsignedShort5--) {
            i8 += 8;
            for (int unsignedShort6 = readUnsignedShort(i8 + 6); unsignedShort6 > 0; unsignedShort6--) {
                i8 += readInt(i8 + 2) + 6;
            }
        }
        int i9 = i8 + 2;
        for (int unsignedShort7 = readUnsignedShort(i8); unsignedShort7 > 0; unsignedShort7--) {
            i9 += readInt(i9 + 2) + 6;
        }
        if (i2 != 0) {
            int i10 = i2 + 2;
            for (int unsignedShort8 = readUnsignedShort(i2); unsignedShort8 > 0; unsignedShort8--) {
                typeCollector.visitAnnotation(readUTF8(i10, cArr));
            }
        }
        for (int unsignedShort9 = readUnsignedShort(i4); unsignedShort9 > 0; unsignedShort9--) {
            i6 += 8;
            for (int unsignedShort10 = readUnsignedShort(i6 + 6); unsignedShort10 > 0; unsignedShort10--) {
                i6 += readInt(i6 + 2) + 6;
            }
        }
        int method = i6 + 2;
        for (int unsignedShort11 = readUnsignedShort(i6); unsignedShort11 > 0; unsignedShort11--) {
            method = readMethod(typeCollector, cArr, method);
        }
    }
}
