package org.joda.time.convert;

/* loaded from: classes5.dex */
class ConverterSet {
    private final Converter[] iConverters;
    private Entry[] iSelectEntries = new Entry[16];

    static class Entry {

        /* renamed from: a, reason: collision with root package name */
        final Class f26704a;

        /* renamed from: b, reason: collision with root package name */
        final Converter f26705b;

        Entry(Class cls, Converter converter) {
            this.f26704a = cls;
            this.f26705b = converter;
        }
    }

    ConverterSet(Converter[] converterArr) {
        this.iConverters = converterArr;
    }

    private static Converter selectSlow(ConverterSet converterSet, Class<?> cls) {
        Converter[] converterArr = converterSet.iConverters;
        int length = converterArr.length;
        int length2 = length;
        while (true) {
            length--;
            if (length < 0) {
                if (cls == null || length2 == 0) {
                    return null;
                }
                if (length2 == 1) {
                    return converterArr[0];
                }
                int i2 = length2;
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        break;
                    }
                    Class<?> supportedType = converterArr[length2].getSupportedType();
                    int length3 = i2;
                    while (true) {
                        i2--;
                        if (i2 >= 0) {
                            if (i2 != length2 && converterArr[i2].getSupportedType().isAssignableFrom(supportedType)) {
                                converterSet = converterSet.c(i2, null);
                                converterArr = converterSet.iConverters;
                                length3 = converterArr.length;
                                length2 = length3 - 1;
                            }
                        }
                    }
                    i2 = length3;
                }
                if (i2 == 1) {
                    return converterArr[0];
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to find best converter for type \"");
                sb.append(cls.getName());
                sb.append("\" from remaining set: ");
                for (int i3 = 0; i3 < i2; i3++) {
                    Converter converter = converterArr[i3];
                    Class<?> supportedType2 = converter.getSupportedType();
                    sb.append(converter.getClass().getName());
                    sb.append('[');
                    sb.append(supportedType2 == null ? null : supportedType2.getName());
                    sb.append("], ");
                }
                throw new IllegalStateException(sb.toString());
            }
            Converter converter2 = converterArr[length];
            Class<?> supportedType3 = converter2.getSupportedType();
            if (supportedType3 == cls) {
                return converter2;
            }
            if (supportedType3 == null || (cls != null && !supportedType3.isAssignableFrom(cls))) {
                converterSet = converterSet.c(length, null);
                converterArr = converterSet.iConverters;
                length2 = converterArr.length;
            }
        }
    }

    ConverterSet a(Converter converter, Converter[] converterArr) {
        Converter[] converterArr2 = this.iConverters;
        int length = converterArr2.length;
        for (int i2 = 0; i2 < length; i2++) {
            Converter converter2 = converterArr2[i2];
            if (converter.equals(converter2)) {
                if (converterArr != null) {
                    converterArr[0] = null;
                }
                return this;
            }
            if (converter.getSupportedType() == converter2.getSupportedType()) {
                Converter[] converterArr3 = new Converter[length];
                for (int i3 = 0; i3 < length; i3++) {
                    if (i3 != i2) {
                        converterArr3[i3] = converterArr2[i3];
                    } else {
                        converterArr3[i3] = converter;
                    }
                }
                if (converterArr != null) {
                    converterArr[0] = converter2;
                }
                return new ConverterSet(converterArr3);
            }
        }
        Converter[] converterArr4 = new Converter[length + 1];
        System.arraycopy(converterArr2, 0, converterArr4, 0, length);
        converterArr4[length] = converter;
        if (converterArr != null) {
            converterArr[0] = null;
        }
        return new ConverterSet(converterArr4);
    }

    void b(Converter[] converterArr) {
        Converter[] converterArr2 = this.iConverters;
        System.arraycopy(converterArr2, 0, converterArr, 0, converterArr2.length);
    }

    ConverterSet c(int i2, Converter[] converterArr) {
        Converter[] converterArr2 = this.iConverters;
        int length = converterArr2.length;
        if (i2 >= length) {
            throw new IndexOutOfBoundsException();
        }
        if (converterArr != null) {
            converterArr[0] = converterArr2[i2];
        }
        Converter[] converterArr3 = new Converter[length - 1];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (i4 != i2) {
                converterArr3[i3] = converterArr2[i4];
                i3++;
            }
        }
        return new ConverterSet(converterArr3);
    }

    ConverterSet d(Converter converter, Converter[] converterArr) {
        Converter[] converterArr2 = this.iConverters;
        int length = converterArr2.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (converter.equals(converterArr2[i2])) {
                return c(i2, converterArr);
            }
        }
        if (converterArr != null) {
            converterArr[0] = null;
        }
        return this;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x000e, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0052, code lost:
    
        r7 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    org.joda.time.convert.Converter e(java.lang.Class r10) {
        /*
            r9 = this;
            org.joda.time.convert.ConverterSet$Entry[] r0 = r9.iSelectEntries
            int r1 = r0.length
            r2 = 0
            if (r10 != 0) goto L7
            goto L1d
        L7:
            int r3 = r10.hashCode()
            int r4 = r1 + (-1)
            r3 = r3 & r4
        Le:
            r4 = r0[r3]
            if (r4 == 0) goto L1f
            java.lang.Class r5 = r4.f26704a
            if (r5 != r10) goto L19
            org.joda.time.convert.Converter r10 = r4.f26705b
            return r10
        L19:
            int r3 = r3 + 1
            if (r3 < r1) goto Le
        L1d:
            r3 = r2
            goto Le
        L1f:
            org.joda.time.convert.Converter r4 = selectSlow(r9, r10)
            org.joda.time.convert.ConverterSet$Entry r5 = new org.joda.time.convert.ConverterSet$Entry
            r5.<init>(r10, r4)
            java.lang.Object r10 = r0.clone()
            org.joda.time.convert.ConverterSet$Entry[] r10 = (org.joda.time.convert.ConverterSet.Entry[]) r10
            r10[r3] = r5
            r0 = r2
        L31:
            if (r0 >= r1) goto L3d
            r3 = r10[r0]
            if (r3 != 0) goto L3a
            r9.iSelectEntries = r10
            return r4
        L3a:
            int r0 = r0 + 1
            goto L31
        L3d:
            int r0 = r1 << 1
            org.joda.time.convert.ConverterSet$Entry[] r3 = new org.joda.time.convert.ConverterSet.Entry[r0]
            r5 = r2
        L42:
            if (r5 >= r1) goto L61
            r6 = r10[r5]
            java.lang.Class r7 = r6.f26704a
            if (r7 != 0) goto L4b
            goto L5a
        L4b:
            int r7 = r7.hashCode()
            int r8 = r0 + (-1)
            r7 = r7 & r8
        L52:
            r8 = r3[r7]
            if (r8 == 0) goto L5c
            int r7 = r7 + 1
            if (r7 < r0) goto L52
        L5a:
            r7 = r2
            goto L52
        L5c:
            r3[r7] = r6
            int r5 = r5 + 1
            goto L42
        L61:
            r9.iSelectEntries = r3
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.convert.ConverterSet.e(java.lang.Class):org.joda.time.convert.Converter");
    }

    int f() {
        return this.iConverters.length;
    }
}
