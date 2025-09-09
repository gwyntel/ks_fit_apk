package com.alibaba.fastjson.serializer;

import anetwork.channel.util.RequestConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.RyuDouble;
import com.alibaba.fastjson.util.RyuFloat;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public final class SerializeWriter extends Writer {
    private static int BUFFER_THRESHOLD;
    static final int nonDirectFeatures;
    protected boolean beanToArray;
    protected boolean browserSecure;
    protected char[] buf;
    protected int count;
    protected boolean disableCircularReferenceDetect;
    protected int features;
    protected char keySeperator;
    protected int maxBufSize;
    protected boolean notWriteDefaultValue;
    protected boolean quoteFieldNames;
    protected long sepcialBits;
    protected boolean sortField;
    protected boolean useSingleQuotes;
    protected boolean writeDirect;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected boolean writeNonStringValueAsString;
    private final Writer writer;
    private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
    private static final ThreadLocal<byte[]> bytesBufLocal = new ThreadLocal<>();
    private static final char[] VALUE_TRUE = ":true".toCharArray();
    private static final char[] VALUE_FALSE = ":false".toCharArray();

    static {
        int i2;
        BUFFER_THRESHOLD = 131072;
        try {
            String stringProperty = IOUtils.getStringProperty("fastjson.serializer_buffer_threshold");
            if (stringProperty != null && stringProperty.length() > 0 && (i2 = Integer.parseInt(stringProperty)) >= 64 && i2 <= 65536) {
                BUFFER_THRESHOLD = i2 * 1024;
            }
        } catch (Throwable unused) {
        }
        nonDirectFeatures = SerializerFeature.UseSingleQuotes.mask | SerializerFeature.BrowserCompatible.mask | SerializerFeature.PrettyFormat.mask | SerializerFeature.WriteEnumUsingToString.mask | SerializerFeature.WriteNonStringValueAsString.mask | SerializerFeature.WriteSlashAsSpecial.mask | SerializerFeature.IgnoreErrorGetter.mask | SerializerFeature.WriteClassName.mask | SerializerFeature.NotWriteDefaultValue.mask;
    }

    public SerializeWriter() {
        this((Writer) null);
    }

    private int encodeToUTF8(OutputStream outputStream) throws IOException {
        int i2 = (int) (this.count * 3.0d);
        ThreadLocal<byte[]> threadLocal = bytesBufLocal;
        byte[] bArr = threadLocal.get();
        if (bArr == null) {
            bArr = new byte[8192];
            threadLocal.set(bArr);
        }
        byte[] bArr2 = bArr.length < i2 ? new byte[i2] : bArr;
        int iEncodeUTF8 = IOUtils.encodeUTF8(this.buf, 0, this.count, bArr2);
        outputStream.write(bArr2, 0, iEncodeUTF8);
        if (bArr2 != bArr && bArr2.length <= BUFFER_THRESHOLD) {
            threadLocal.set(bArr2);
        }
        return iEncodeUTF8;
    }

    private byte[] encodeToUTF8Bytes() {
        int i2 = (int) (this.count * 3.0d);
        ThreadLocal<byte[]> threadLocal = bytesBufLocal;
        byte[] bArr = threadLocal.get();
        if (bArr == null) {
            bArr = new byte[8192];
            threadLocal.set(bArr);
        }
        byte[] bArr2 = bArr.length < i2 ? new byte[i2] : bArr;
        int iEncodeUTF8 = IOUtils.encodeUTF8(this.buf, 0, this.count, bArr2);
        byte[] bArr3 = new byte[iEncodeUTF8];
        System.arraycopy(bArr2, 0, bArr3, 0, iEncodeUTF8);
        if (bArr2 != bArr && bArr2.length <= BUFFER_THRESHOLD) {
            threadLocal.set(bArr2);
        }
        return bArr3;
    }

    private void writeEnumFieldValue(char c2, String str, String str2) {
        if (this.useSingleQuotes) {
            writeFieldValue(c2, str, str2);
        } else {
            writeFieldValueStringWithDoubleQuote(c2, str, str2);
        }
    }

    private void writeKeyWithSingleQuoteIfHasSpecial(String str) {
        byte[] bArr = IOUtils.specicalFlags_singleQuotes;
        int length = str.length();
        boolean z2 = true;
        int i2 = this.count + length + 1;
        int i3 = 0;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                if (length == 0) {
                    write(39);
                    write(39);
                    write(58);
                    return;
                }
                int i4 = 0;
                while (true) {
                    if (i4 < length) {
                        char cCharAt = str.charAt(i4);
                        if (cCharAt < bArr.length && bArr[cCharAt] != 0) {
                            break;
                        } else {
                            i4++;
                        }
                    } else {
                        z2 = false;
                        break;
                    }
                }
                if (z2) {
                    write(39);
                }
                while (i3 < length) {
                    char cCharAt2 = str.charAt(i3);
                    if (cCharAt2 >= bArr.length || bArr[cCharAt2] == 0) {
                        write(cCharAt2);
                    } else {
                        write(92);
                        write(IOUtils.replaceChars[cCharAt2]);
                    }
                    i3++;
                }
                if (z2) {
                    write(39);
                }
                write(58);
                return;
            }
            expandCapacity(i2);
        }
        if (length == 0) {
            int i5 = this.count;
            if (i5 + 3 > this.buf.length) {
                expandCapacity(i5 + 3);
            }
            char[] cArr = this.buf;
            int i6 = this.count;
            int i7 = i6 + 1;
            this.count = i7;
            cArr[i6] = '\'';
            int i8 = i6 + 2;
            this.count = i8;
            cArr[i7] = '\'';
            this.count = i6 + 3;
            cArr[i8] = ':';
            return;
        }
        int i9 = this.count;
        int i10 = i9 + length;
        str.getChars(0, length, this.buf, i9);
        this.count = i2;
        int i11 = i9;
        boolean z3 = false;
        while (i11 < i10) {
            char[] cArr2 = this.buf;
            char c2 = cArr2[i11];
            if (c2 < bArr.length && bArr[c2] != 0) {
                if (z3) {
                    i2++;
                    if (i2 > cArr2.length) {
                        expandCapacity(i2);
                    }
                    this.count = i2;
                    char[] cArr3 = this.buf;
                    int i12 = i11 + 1;
                    System.arraycopy(cArr3, i12, cArr3, i11 + 2, i10 - i11);
                    char[] cArr4 = this.buf;
                    cArr4[i11] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_WINDOWS;
                    cArr4[i12] = IOUtils.replaceChars[c2];
                    i10++;
                    i11 = i12;
                } else {
                    i2 += 3;
                    if (i2 > cArr2.length) {
                        expandCapacity(i2);
                    }
                    this.count = i2;
                    char[] cArr5 = this.buf;
                    int i13 = i11 + 1;
                    System.arraycopy(cArr5, i13, cArr5, i11 + 3, (i10 - i11) - 1);
                    char[] cArr6 = this.buf;
                    System.arraycopy(cArr6, i3, cArr6, 1, i11);
                    char[] cArr7 = this.buf;
                    cArr7[i9] = '\'';
                    cArr7[i13] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_WINDOWS;
                    i11 += 2;
                    cArr7[i11] = IOUtils.replaceChars[c2];
                    i10 += 2;
                    cArr7[this.count - 2] = '\'';
                    z3 = true;
                }
            }
            i11++;
            i3 = 0;
        }
        this.buf[i2 - 1] = ':';
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.writer != null && this.count > 0) {
            flush();
        }
        char[] cArr = this.buf;
        if (cArr.length <= BUFFER_THRESHOLD) {
            bufLocal.set(cArr);
        }
        this.buf = null;
    }

    protected void computeFeatures() {
        int i2 = this.features;
        boolean z2 = (SerializerFeature.QuoteFieldNames.mask & i2) != 0;
        this.quoteFieldNames = z2;
        boolean z3 = (SerializerFeature.UseSingleQuotes.mask & i2) != 0;
        this.useSingleQuotes = z3;
        this.sortField = (SerializerFeature.SortField.mask & i2) != 0;
        this.disableCircularReferenceDetect = (SerializerFeature.DisableCircularReferenceDetect.mask & i2) != 0;
        boolean z4 = (SerializerFeature.BeanToArray.mask & i2) != 0;
        this.beanToArray = z4;
        this.writeNonStringValueAsString = (SerializerFeature.WriteNonStringValueAsString.mask & i2) != 0;
        this.notWriteDefaultValue = (SerializerFeature.NotWriteDefaultValue.mask & i2) != 0;
        boolean z5 = (SerializerFeature.WriteEnumUsingName.mask & i2) != 0;
        this.writeEnumUsingName = z5;
        this.writeEnumUsingToString = (SerializerFeature.WriteEnumUsingToString.mask & i2) != 0;
        this.writeDirect = z2 && (nonDirectFeatures & i2) == 0 && (z4 || z5);
        this.keySeperator = z3 ? '\'' : Typography.quote;
        boolean z6 = (SerializerFeature.BrowserSecure.mask & i2) != 0;
        this.browserSecure = z6;
        this.sepcialBits = z6 ? 5764610843043954687L : (i2 & SerializerFeature.WriteSlashAsSpecial.mask) != 0 ? 140758963191807L : 21474836479L;
    }

    public void config(SerializerFeature serializerFeature, boolean z2) {
        if (z2) {
            int mask = this.features | serializerFeature.getMask();
            this.features = mask;
            SerializerFeature serializerFeature2 = SerializerFeature.WriteEnumUsingToString;
            if (serializerFeature == serializerFeature2) {
                this.features = (~SerializerFeature.WriteEnumUsingName.getMask()) & mask;
            } else if (serializerFeature == SerializerFeature.WriteEnumUsingName) {
                this.features = (~serializerFeature2.getMask()) & mask;
            }
        } else {
            this.features = (~serializerFeature.getMask()) & this.features;
        }
        computeFeatures();
    }

    public void expandCapacity(int i2) {
        ThreadLocal<char[]> threadLocal;
        char[] cArr;
        int i3 = this.maxBufSize;
        if (i3 != -1 && i2 >= i3) {
            throw new JSONException("serialize exceeded MAX_OUTPUT_LENGTH=" + this.maxBufSize + ", minimumCapacity=" + i2);
        }
        char[] cArr2 = this.buf;
        int length = cArr2.length + (cArr2.length >> 1) + 1;
        if (length >= i2) {
            i2 = length;
        }
        char[] cArr3 = new char[i2];
        System.arraycopy(cArr2, 0, cArr3, 0, this.count);
        if (this.buf.length < BUFFER_THRESHOLD && ((cArr = (threadLocal = bufLocal).get()) == null || cArr.length < this.buf.length)) {
            threadLocal.set(this.buf);
        }
        this.buf = cArr3;
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        Writer writer = this.writer;
        if (writer == null) {
            return;
        }
        try {
            writer.write(this.buf, 0, this.count);
            this.writer.flush();
            this.count = 0;
        } catch (IOException e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }

    public int getBufferLength() {
        return this.buf.length;
    }

    public int getMaxBufSize() {
        return this.maxBufSize;
    }

    public boolean isEnabled(SerializerFeature serializerFeature) {
        return (serializerFeature.mask & this.features) != 0;
    }

    public boolean isNotWriteDefaultValue() {
        return this.notWriteDefaultValue;
    }

    public boolean isSortField() {
        return this.sortField;
    }

    public void reset() {
        this.count = 0;
    }

    public void setMaxBufSize(int i2) {
        if (i2 >= this.buf.length) {
            this.maxBufSize = i2;
            return;
        }
        throw new JSONException("must > " + this.buf.length);
    }

    public int size() {
        return this.count;
    }

    public byte[] toBytes(String str) {
        return toBytes((str == null || "UTF-8".equals(str)) ? IOUtils.UTF8 : Charset.forName(str));
    }

    public char[] toCharArray() {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        int i2 = this.count;
        char[] cArr = new char[i2];
        System.arraycopy(this.buf, 0, cArr, 0, i2);
        return cArr;
    }

    public char[] toCharArrayForSpringWebSocket() {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        int i2 = this.count;
        char[] cArr = new char[i2 - 2];
        System.arraycopy(this.buf, 1, cArr, 0, i2 - 2);
        return cArr;
    }

    public String toString() {
        return new String(this.buf, 0, this.count);
    }

    @Override // java.io.Writer
    public void write(int i2) {
        int i3 = 1;
        int i4 = this.count + 1;
        if (i4 <= this.buf.length) {
            i3 = i4;
        } else if (this.writer == null) {
            expandCapacity(i4);
            i3 = i4;
        } else {
            flush();
        }
        this.buf[this.count] = (char) i2;
        this.count = i3;
    }

    public void writeByteArray(byte[] bArr) {
        if (isEnabled(SerializerFeature.WriteClassName.mask)) {
            writeHex(bArr);
            return;
        }
        int length = bArr.length;
        boolean z2 = this.useSingleQuotes;
        char c2 = z2 ? '\'' : Typography.quote;
        if (length == 0) {
            write(z2 ? "''" : "\"\"");
            return;
        }
        char[] cArr = IOUtils.CA;
        int i2 = (length / 3) * 3;
        int i3 = length - 1;
        int i4 = this.count;
        int i5 = (((i3 / 3) + 1) << 2) + i4;
        int i6 = i5 + 2;
        if (i6 > this.buf.length) {
            if (this.writer != null) {
                write(c2);
                int i7 = 0;
                while (i7 < i2) {
                    int i8 = i7 + 2;
                    int i9 = ((bArr[i7 + 1] & 255) << 8) | ((bArr[i7] & 255) << 16);
                    i7 += 3;
                    int i10 = i9 | (bArr[i8] & 255);
                    write(cArr[(i10 >>> 18) & 63]);
                    write(cArr[(i10 >>> 12) & 63]);
                    write(cArr[(i10 >>> 6) & 63]);
                    write(cArr[i10 & 63]);
                }
                int i11 = length - i2;
                if (i11 > 0) {
                    int i12 = ((bArr[i2] & 255) << 10) | (i11 == 2 ? (bArr[i3] & 255) << 2 : 0);
                    write(cArr[i12 >> 12]);
                    write(cArr[(i12 >>> 6) & 63]);
                    write(i11 == 2 ? cArr[i12 & 63] : '=');
                    write(61);
                }
                write(c2);
                return;
            }
            expandCapacity(i6);
        }
        this.count = i6;
        int i13 = i4 + 1;
        this.buf[i4] = c2;
        int i14 = 0;
        while (i14 < i2) {
            int i15 = i14 + 2;
            int i16 = ((bArr[i14 + 1] & 255) << 8) | ((bArr[i14] & 255) << 16);
            i14 += 3;
            int i17 = i16 | (bArr[i15] & 255);
            char[] cArr2 = this.buf;
            cArr2[i13] = cArr[(i17 >>> 18) & 63];
            cArr2[i13 + 1] = cArr[(i17 >>> 12) & 63];
            int i18 = i13 + 3;
            cArr2[i13 + 2] = cArr[(i17 >>> 6) & 63];
            i13 += 4;
            cArr2[i18] = cArr[i17 & 63];
        }
        int i19 = length - i2;
        if (i19 > 0) {
            int i20 = ((bArr[i2] & 255) << 10) | (i19 == 2 ? (bArr[i3] & 255) << 2 : 0);
            char[] cArr3 = this.buf;
            cArr3[i5 - 3] = cArr[i20 >> 12];
            cArr3[i5 - 2] = cArr[(i20 >>> 6) & 63];
            cArr3[i5 - 1] = i19 == 2 ? cArr[i20 & 63] : '=';
            cArr3[i5] = com.alipay.sdk.m.n.a.f9565h;
        }
        this.buf[i5 + 1] = c2;
    }

    public void writeDouble(double d2, boolean z2) throws IOException {
        if (Double.isNaN(d2) || Double.isInfinite(d2)) {
            writeNull();
            return;
        }
        int i2 = this.count + 24;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                String string = RyuDouble.toString(d2);
                write(string, 0, string.length());
                if (z2 && isEnabled(SerializerFeature.WriteClassName)) {
                    write(68);
                    return;
                }
                return;
            }
            expandCapacity(i2);
        }
        this.count += RyuDouble.toString(d2, this.buf, this.count);
        if (z2 && isEnabled(SerializerFeature.WriteClassName)) {
            write(68);
        }
    }

    public void writeEnum(Enum<?> r2) {
        if (r2 == null) {
            writeNull();
            return;
        }
        String string = (!this.writeEnumUsingName || this.writeEnumUsingToString) ? this.writeEnumUsingToString ? r2.toString() : null : r2.name();
        if (string == null) {
            writeInt(r2.ordinal());
            return;
        }
        int i2 = isEnabled(SerializerFeature.UseSingleQuotes) ? 39 : 34;
        write(i2);
        write(string);
        write(i2);
    }

    public void writeFieldName(String str) {
        writeFieldName(str, false);
    }

    public void writeFieldNameDirect(String str) {
        int length = str.length();
        int i2 = this.count + length;
        int i3 = i2 + 3;
        if (i3 > this.buf.length) {
            expandCapacity(i3);
        }
        int i4 = this.count;
        char[] cArr = this.buf;
        cArr[i4] = Typography.quote;
        str.getChars(0, length, cArr, i4 + 1);
        this.count = i3;
        char[] cArr2 = this.buf;
        cArr2[i2 + 1] = Typography.quote;
        cArr2[i2 + 2] = ':';
    }

    public void writeFieldValue(char c2, String str, char c3) {
        write(c2);
        writeFieldName(str);
        if (c3 == 0) {
            writeString("\u0000");
        } else {
            writeString(Character.toString(c3));
        }
    }

    public void writeFieldValueStringWithDoubleQuote(char c2, String str, String str2) {
        int length = str.length();
        int i2 = this.count;
        int length2 = str2.length();
        int i3 = i2 + length + length2 + 6;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                write(c2);
                writeStringWithDoubleQuote(str, ':');
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            expandCapacity(i3);
        }
        char[] cArr = this.buf;
        int i4 = this.count;
        cArr[i4] = c2;
        int i5 = i4 + 2;
        int i6 = i5 + length;
        cArr[i4 + 1] = Typography.quote;
        str.getChars(0, length, cArr, i5);
        this.count = i3;
        char[] cArr2 = this.buf;
        cArr2[i6] = Typography.quote;
        cArr2[i6 + 1] = ':';
        cArr2[i6 + 2] = Typography.quote;
        str2.getChars(0, length2, cArr2, i6 + 3);
        this.buf[this.count - 1] = Typography.quote;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x009d A[PHI: r1 r4 r6 r9 r10
      0x009d: PHI (r1v50 int) = (r1v41 int), (r1v3 int) binds: [B:53:0x00d6, B:30:0x0098] A[DONT_GENERATE, DONT_INLINE]
      0x009d: PHI (r4v21 int) = (r4v20 int), (r4v23 int) binds: [B:53:0x00d6, B:30:0x0098] A[DONT_GENERATE, DONT_INLINE]
      0x009d: PHI (r6v7 char) = (r6v6 char), (r6v9 char) binds: [B:53:0x00d6, B:30:0x0098] A[DONT_GENERATE, DONT_INLINE]
      0x009d: PHI (r9v11 int) = (r9v10 int), (r9v13 int) binds: [B:53:0x00d6, B:30:0x0098] A[DONT_GENERATE, DONT_INLINE]
      0x009d: PHI (r10v13 int) = (r10v3 int), (r10v15 int) binds: [B:53:0x00d6, B:30:0x0098] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeFieldValueStringWithDoubleQuoteCheck(char r22, java.lang.String r23, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 783
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeWriter.writeFieldValueStringWithDoubleQuoteCheck(char, java.lang.String, java.lang.String):void");
    }

    public void writeFloat(float f2, boolean z2) throws IOException {
        if (f2 != f2 || f2 == Float.POSITIVE_INFINITY || f2 == Float.NEGATIVE_INFINITY) {
            writeNull();
            return;
        }
        int i2 = this.count + 15;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                String string = RyuFloat.toString(f2);
                write(string, 0, string.length());
                if (z2 && isEnabled(SerializerFeature.WriteClassName)) {
                    write(70);
                    return;
                }
                return;
            }
            expandCapacity(i2);
        }
        this.count += RyuFloat.toString(f2, this.buf, this.count);
        if (z2 && isEnabled(SerializerFeature.WriteClassName)) {
            write(70);
        }
    }

    public void writeHex(byte[] bArr) {
        int length = this.count + (bArr.length * 2) + 3;
        if (length > this.buf.length) {
            expandCapacity(length);
        }
        char[] cArr = this.buf;
        int i2 = this.count;
        int i3 = i2 + 1;
        this.count = i3;
        cArr[i2] = 'x';
        this.count = i2 + 2;
        cArr[i3] = '\'';
        for (byte b2 : bArr) {
            int i4 = (b2 & 255) >> 4;
            int i5 = b2 & 15;
            char[] cArr2 = this.buf;
            int i6 = this.count;
            int i7 = i6 + 1;
            this.count = i7;
            int i8 = 55;
            cArr2[i6] = (char) (i4 + (i4 < 10 ? 48 : 55));
            this.count = i6 + 2;
            if (i5 < 10) {
                i8 = 48;
            }
            cArr2[i7] = (char) (i5 + i8);
        }
        char[] cArr3 = this.buf;
        int i9 = this.count;
        this.count = i9 + 1;
        cArr3[i9] = '\'';
    }

    public void writeInt(int i2) {
        if (i2 == Integer.MIN_VALUE) {
            write("-2147483648");
            return;
        }
        int iStringSize = i2 < 0 ? IOUtils.stringSize(-i2) + 1 : IOUtils.stringSize(i2);
        int i3 = this.count + iStringSize;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                char[] cArr = new char[iStringSize];
                IOUtils.getChars(i2, iStringSize, cArr);
                write(cArr, 0, iStringSize);
                return;
            }
            expandCapacity(i3);
        }
        IOUtils.getChars(i2, i3, this.buf);
        this.count = i3;
    }

    public void writeLong(long j2) {
        boolean z2 = isEnabled(SerializerFeature.BrowserCompatible) && !isEnabled(SerializerFeature.WriteClassName) && (j2 > 9007199254740991L || j2 < -9007199254740991L);
        if (j2 == Long.MIN_VALUE) {
            if (z2) {
                write("\"-9223372036854775808\"");
                return;
            } else {
                write("-9223372036854775808");
                return;
            }
        }
        int iStringSize = j2 < 0 ? IOUtils.stringSize(-j2) + 1 : IOUtils.stringSize(j2);
        int i2 = this.count + iStringSize;
        if (z2) {
            i2 += 2;
        }
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                char[] cArr = new char[iStringSize];
                IOUtils.getChars(j2, iStringSize, cArr);
                if (!z2) {
                    write(cArr, 0, iStringSize);
                    return;
                }
                write(34);
                write(cArr, 0, iStringSize);
                write(34);
                return;
            }
            expandCapacity(i2);
        }
        if (z2) {
            char[] cArr2 = this.buf;
            cArr2[this.count] = Typography.quote;
            int i3 = i2 - 1;
            IOUtils.getChars(j2, i3, cArr2);
            this.buf[i3] = Typography.quote;
        } else {
            IOUtils.getChars(j2, i2, this.buf);
        }
        this.count = i2;
    }

    public void writeLongAndChar(long j2, char c2) throws IOException {
        writeLong(j2);
        write(c2);
    }

    public void writeNull() {
        write(TmpConstant.GROUP_ROLE_UNKNOWN);
    }

    public void writeString(String str, char c2) {
        if (!this.useSingleQuotes) {
            writeStringWithDoubleQuote(str, c2);
        } else {
            writeStringWithSingleQuote(str);
            write(c2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:144:0x02b3 A[PHI: r5 r9 r11 r15
      0x02b3: PHI (r5v29 int) = (r5v27 int), (r5v31 int) binds: [B:166:0x02ed, B:143:0x02af] A[DONT_GENERATE, DONT_INLINE]
      0x02b3: PHI (r9v22 int) = (r9v15 int), (r9v3 int) binds: [B:166:0x02ed, B:143:0x02af] A[DONT_GENERATE, DONT_INLINE]
      0x02b3: PHI (r11v14 int) = (r11v12 int), (r11v16 int) binds: [B:166:0x02ed, B:143:0x02af] A[DONT_GENERATE, DONT_INLINE]
      0x02b3: PHI (r15v13 int) = (r15v2 int), (r15v15 int) binds: [B:166:0x02ed, B:143:0x02af] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x014f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeStringWithDoubleQuote(java.lang.String r23, char r24) {
        /*
            Method dump skipped, instructions count: 1345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeWriter.writeStringWithDoubleQuote(java.lang.String, char):void");
    }

    protected void writeStringWithSingleQuote(String str) {
        int i2 = 0;
        if (str == null) {
            int i3 = this.count + 4;
            if (i3 > this.buf.length) {
                expandCapacity(i3);
            }
            TmpConstant.GROUP_ROLE_UNKNOWN.getChars(0, 4, this.buf, this.count);
            this.count = i3;
            return;
        }
        int length = str.length();
        int i4 = this.count + length + 2;
        if (i4 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                while (i2 < str.length()) {
                    char cCharAt = str.charAt(i2);
                    if (cCharAt <= '\r' || cCharAt == '\\' || cCharAt == '\'' || (cCharAt == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[cCharAt]);
                    } else {
                        write(cCharAt);
                    }
                    i2++;
                }
                write(39);
                return;
            }
            expandCapacity(i4);
        }
        int i5 = this.count;
        int i6 = i5 + 1;
        int i7 = i6 + length;
        char[] cArr = this.buf;
        cArr[i5] = '\'';
        str.getChars(0, length, cArr, i6);
        this.count = i4;
        int i8 = -1;
        char c2 = 0;
        for (int i9 = i6; i9 < i7; i9++) {
            char c3 = this.buf[i9];
            if (c3 <= '\r' || c3 == '\\' || c3 == '\'' || (c3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                i2++;
                i8 = i9;
                c2 = c3;
            }
        }
        int i10 = i4 + i2;
        if (i10 > this.buf.length) {
            expandCapacity(i10);
        }
        this.count = i10;
        if (i2 == 1) {
            char[] cArr2 = this.buf;
            int i11 = i8 + 1;
            System.arraycopy(cArr2, i11, cArr2, i8 + 2, (i7 - i8) - 1);
            char[] cArr3 = this.buf;
            cArr3[i8] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_WINDOWS;
            cArr3[i11] = IOUtils.replaceChars[c2];
        } else if (i2 > 1) {
            char[] cArr4 = this.buf;
            int i12 = i8 + 1;
            System.arraycopy(cArr4, i12, cArr4, i8 + 2, (i7 - i8) - 1);
            char[] cArr5 = this.buf;
            cArr5[i8] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_WINDOWS;
            cArr5[i12] = IOUtils.replaceChars[c2];
            int i13 = i7 + 1;
            for (int i14 = i8 - 1; i14 >= i6; i14--) {
                char c4 = this.buf[i14];
                if (c4 <= '\r' || c4 == '\\' || c4 == '\'' || (c4 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    char[] cArr6 = this.buf;
                    int i15 = i14 + 1;
                    System.arraycopy(cArr6, i15, cArr6, i14 + 2, (i13 - i14) - 1);
                    char[] cArr7 = this.buf;
                    cArr7[i14] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_WINDOWS;
                    cArr7[i15] = IOUtils.replaceChars[c4];
                    i13++;
                }
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    public void writeTo(Writer writer) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        writer.write(this.buf, 0, this.count);
    }

    public int writeToEx(OutputStream outputStream, Charset charset) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (charset == IOUtils.UTF8) {
            return encodeToUTF8(outputStream);
        }
        byte[] bytes = new String(this.buf, 0, this.count).getBytes(charset);
        outputStream.write(bytes);
        return bytes.length;
    }

    public SerializeWriter(Writer writer) {
        this(writer, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
    }

    public boolean isEnabled(int i2) {
        return (i2 & this.features) != 0;
    }

    public void writeFieldName(String str, boolean z2) {
        if (str == null) {
            write("null:");
            return;
        }
        if (this.useSingleQuotes) {
            if (!this.quoteFieldNames) {
                writeKeyWithSingleQuoteIfHasSpecial(str);
                return;
            } else {
                writeStringWithSingleQuote(str);
                write(58);
                return;
            }
        }
        if (this.quoteFieldNames) {
            writeStringWithDoubleQuote(str, ':');
            return;
        }
        int i2 = 0;
        boolean z3 = true;
        boolean z4 = str.length() == 0;
        while (true) {
            if (i2 >= str.length()) {
                z3 = z4;
                break;
            }
            char cCharAt = str.charAt(i2);
            if ((cCharAt < '@' && (this.sepcialBits & (1 << cCharAt)) != 0) || cCharAt == '\\') {
                break;
            } else {
                i2++;
            }
        }
        if (z3) {
            writeStringWithDoubleQuote(str, ':');
        } else {
            write(str);
            write(58);
        }
    }

    public void writeNull(SerializerFeature serializerFeature) {
        writeNull(0, serializerFeature.mask);
    }

    public SerializeWriter(SerializerFeature... serializerFeatureArr) {
        this((Writer) null, serializerFeatureArr);
    }

    public void writeNull(int i2, int i3) {
        if ((i2 & i3) == 0 && (this.features & i3) == 0) {
            writeNull();
            return;
        }
        int i4 = SerializerFeature.WriteMapNullValue.mask;
        if ((i2 & i4) != 0 && (i2 & (~i4) & SerializerFeature.WRITE_MAP_NULL_FEATURES) == 0) {
            writeNull();
            return;
        }
        if (i3 == SerializerFeature.WriteNullListAsEmpty.mask) {
            write("[]");
            return;
        }
        if (i3 == SerializerFeature.WriteNullStringAsEmpty.mask) {
            writeString("");
            return;
        }
        if (i3 == SerializerFeature.WriteNullBooleanAsFalse.mask) {
            write(RequestConstant.FALSE);
        } else if (i3 == SerializerFeature.WriteNullNumberAsZero.mask) {
            write(48);
        } else {
            writeNull();
        }
    }

    public SerializeWriter(Writer writer, SerializerFeature... serializerFeatureArr) {
        this(writer, 0, serializerFeatureArr);
    }

    public byte[] toBytes(Charset charset) {
        if (this.writer == null) {
            if (charset == IOUtils.UTF8) {
                return encodeToUTF8Bytes();
            }
            return new String(this.buf, 0, this.count).getBytes(charset);
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public void writeTo(OutputStream outputStream, String str) throws IOException {
        writeTo(outputStream, Charset.forName(str));
    }

    public SerializeWriter(Writer writer, int i2, SerializerFeature... serializerFeatureArr) {
        this.maxBufSize = -1;
        this.writer = writer;
        ThreadLocal<char[]> threadLocal = bufLocal;
        char[] cArr = threadLocal.get();
        this.buf = cArr;
        if (cArr != null) {
            threadLocal.set(null);
        } else {
            this.buf = new char[2048];
        }
        for (SerializerFeature serializerFeature : serializerFeatureArr) {
            i2 |= serializerFeature.getMask();
        }
        this.features = i2;
        computeFeatures();
    }

    public void writeFieldValue(char c2, String str, boolean z2) {
        if (!this.quoteFieldNames) {
            write(c2);
            writeFieldName(str);
            write(z2);
            return;
        }
        int i2 = z2 ? 4 : 5;
        int length = str.length();
        int i3 = this.count + length + 4 + i2;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                write(c2);
                writeString(str);
                write(58);
                write(z2);
                return;
            }
            expandCapacity(i3);
        }
        int i4 = this.count;
        this.count = i3;
        char[] cArr = this.buf;
        cArr[i4] = c2;
        int i5 = i4 + length;
        cArr[i4 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i4 + 2);
        char[] cArr2 = this.buf;
        cArr2[i5 + 2] = this.keySeperator;
        if (z2) {
            System.arraycopy(VALUE_TRUE, 0, cArr2, i5 + 3, 5);
        } else {
            System.arraycopy(VALUE_FALSE, 0, cArr2, i5 + 3, 6);
        }
    }

    public void writeString(String str) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(str);
        } else {
            writeStringWithDoubleQuote(str, (char) 0);
        }
    }

    public void writeTo(OutputStream outputStream, Charset charset) throws IOException {
        writeToEx(outputStream, charset);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence charSequence) throws IOException {
        String string = charSequence == null ? TmpConstant.GROUP_ROLE_UNKNOWN : charSequence.toString();
        write(string, 0, string.length());
        return this;
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i2, int i3) throws IOException {
        int i4;
        if (i2 < 0 || i2 > cArr.length || i3 < 0 || (i4 = i2 + i3) > cArr.length || i4 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 == 0) {
            return;
        }
        int i5 = this.count + i3;
        if (i5 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i5);
            } else {
                do {
                    char[] cArr2 = this.buf;
                    int length = cArr2.length;
                    int i6 = this.count;
                    int i7 = length - i6;
                    System.arraycopy(cArr, i2, cArr2, i6, i7);
                    this.count = this.buf.length;
                    flush();
                    i3 -= i7;
                    i2 += i7;
                } while (i3 > this.buf.length);
                i5 = i3;
            }
        }
        System.arraycopy(cArr, i2, this.buf, this.count, i3);
        this.count = i5;
    }

    public void writeString(char[] cArr) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(cArr);
        } else {
            writeStringWithDoubleQuote(new String(cArr), (char) 0);
        }
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence charSequence, int i2, int i3) throws IOException {
        if (charSequence == null) {
            charSequence = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        String string = charSequence.subSequence(i2, i3).toString();
        write(string, 0, string.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(char c2) {
        write(c2);
        return this;
    }

    public SerializeWriter(int i2) {
        this((Writer) null, i2);
    }

    public SerializeWriter(Writer writer, int i2) {
        this.maxBufSize = -1;
        this.writer = writer;
        if (i2 > 0) {
            this.buf = new char[i2];
            computeFeatures();
        } else {
            throw new IllegalArgumentException("Negative initial size: " + i2);
        }
    }

    @Override // java.io.Writer
    public void write(String str, int i2, int i3) throws IOException {
        int i4;
        int i5 = this.count + i3;
        if (i5 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i5);
            } else {
                while (true) {
                    char[] cArr = this.buf;
                    int length = cArr.length;
                    int i6 = this.count;
                    int i7 = length - i6;
                    i4 = i2 + i7;
                    str.getChars(i2, i4, cArr, i6);
                    this.count = this.buf.length;
                    flush();
                    i3 -= i7;
                    if (i3 <= this.buf.length) {
                        break;
                    } else {
                        i2 = i4;
                    }
                }
                i5 = i3;
                i2 = i4;
            }
        }
        str.getChars(i2, i3 + i2, this.buf, this.count);
        this.count = i5;
    }

    public void writeFieldValue(char c2, String str, int i2) {
        if (i2 != Integer.MIN_VALUE && this.quoteFieldNames) {
            int iStringSize = i2 < 0 ? IOUtils.stringSize(-i2) + 1 : IOUtils.stringSize(i2);
            int length = str.length();
            int i3 = this.count + length + 4 + iStringSize;
            if (i3 > this.buf.length) {
                if (this.writer != null) {
                    write(c2);
                    writeFieldName(str);
                    writeInt(i2);
                    return;
                }
                expandCapacity(i3);
            }
            int i4 = this.count;
            this.count = i3;
            char[] cArr = this.buf;
            cArr[i4] = c2;
            int i5 = i4 + length;
            cArr[i4 + 1] = this.keySeperator;
            str.getChars(0, length, cArr, i4 + 2);
            char[] cArr2 = this.buf;
            cArr2[i5 + 2] = this.keySeperator;
            cArr2[i5 + 3] = ':';
            IOUtils.getChars(i2, this.count, cArr2);
            return;
        }
        write(c2);
        writeFieldName(str);
        writeInt(i2);
    }

    @Override // java.io.Writer
    public void write(String str) {
        if (str == null) {
            writeNull();
        } else {
            write(str, 0, str.length());
        }
    }

    public void write(List<String> list) {
        boolean z2;
        int i2;
        if (list.isEmpty()) {
            write("[]");
            return;
        }
        int i3 = this.count;
        int size = list.size();
        int i4 = i3;
        int i5 = 0;
        while (i5 < size) {
            String str = list.get(i5);
            if (str == null) {
                z2 = true;
            } else {
                int length = str.length();
                z2 = false;
                for (int i6 = 0; i6 < length; i6++) {
                    char cCharAt = str.charAt(i6);
                    z2 = cCharAt < ' ' || cCharAt > '~' || cCharAt == '\"' || cCharAt == '\\';
                    if (z2) {
                        break;
                    }
                }
            }
            if (z2) {
                this.count = i3;
                write(91);
                for (int i7 = 0; i7 < list.size(); i7++) {
                    String str2 = list.get(i7);
                    if (i7 != 0) {
                        write(44);
                    }
                    if (str2 == null) {
                        write(TmpConstant.GROUP_ROLE_UNKNOWN);
                    } else {
                        writeStringWithDoubleQuote(str2, (char) 0);
                    }
                }
                write(93);
                return;
            }
            int length2 = str.length() + i4;
            int i8 = length2 + 3;
            if (i5 == list.size() - 1) {
                i8 = length2 + 4;
            }
            if (i8 > this.buf.length) {
                this.count = i4;
                expandCapacity(i8);
            }
            if (i5 == 0) {
                i2 = i4 + 1;
                this.buf[i4] = '[';
            } else {
                i2 = i4 + 1;
                this.buf[i4] = ',';
            }
            int i9 = i2 + 1;
            this.buf[i2] = Typography.quote;
            str.getChars(0, str.length(), this.buf, i9);
            int length3 = i9 + str.length();
            this.buf[length3] = Typography.quote;
            i5++;
            i4 = length3 + 1;
        }
        this.buf[i4] = ']';
        this.count = i4 + 1;
    }

    protected void writeStringWithSingleQuote(char[] cArr) {
        int i2 = 0;
        if (cArr == null) {
            int i3 = this.count + 4;
            if (i3 > this.buf.length) {
                expandCapacity(i3);
            }
            TmpConstant.GROUP_ROLE_UNKNOWN.getChars(0, 4, this.buf, this.count);
            this.count = i3;
            return;
        }
        int length = cArr.length;
        int i4 = this.count + length + 2;
        if (i4 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                while (i2 < cArr.length) {
                    char c2 = cArr[i2];
                    if (c2 > '\r' && c2 != '\\' && c2 != '\'' && (c2 != '/' || !isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(c2);
                    } else {
                        write(92);
                        write(IOUtils.replaceChars[c2]);
                    }
                    i2++;
                }
                write(39);
                return;
            }
            expandCapacity(i4);
        }
        int i5 = this.count;
        int i6 = i5 + 1;
        int i7 = length + i6;
        char[] cArr2 = this.buf;
        cArr2[i5] = '\'';
        System.arraycopy(cArr, 0, cArr2, i6, cArr.length);
        this.count = i4;
        int i8 = -1;
        char c3 = 0;
        for (int i9 = i6; i9 < i7; i9++) {
            char c4 = this.buf[i9];
            if (c4 <= '\r' || c4 == '\\' || c4 == '\'' || (c4 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                i2++;
                i8 = i9;
                c3 = c4;
            }
        }
        int i10 = i4 + i2;
        if (i10 > this.buf.length) {
            expandCapacity(i10);
        }
        this.count = i10;
        if (i2 == 1) {
            char[] cArr3 = this.buf;
            int i11 = i8 + 1;
            System.arraycopy(cArr3, i11, cArr3, i8 + 2, (i7 - i8) - 1);
            char[] cArr4 = this.buf;
            cArr4[i8] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_WINDOWS;
            cArr4[i11] = IOUtils.replaceChars[c3];
        } else if (i2 > 1) {
            char[] cArr5 = this.buf;
            int i12 = i8 + 1;
            System.arraycopy(cArr5, i12, cArr5, i8 + 2, (i7 - i8) - 1);
            char[] cArr6 = this.buf;
            cArr6[i8] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_WINDOWS;
            cArr6[i12] = IOUtils.replaceChars[c3];
            int i13 = i7 + 1;
            for (int i14 = i8 - 1; i14 >= i6; i14--) {
                char c5 = this.buf[i14];
                if (c5 <= '\r' || c5 == '\\' || c5 == '\'' || (c5 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    char[] cArr7 = this.buf;
                    int i15 = i14 + 1;
                    System.arraycopy(cArr7, i15, cArr7, i14 + 2, (i13 - i14) - 1);
                    char[] cArr8 = this.buf;
                    cArr8[i14] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_WINDOWS;
                    cArr8[i15] = IOUtils.replaceChars[c5];
                    i13++;
                }
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    public void writeFieldValue(char c2, String str, long j2) {
        if (j2 != Long.MIN_VALUE && this.quoteFieldNames && !isEnabled(SerializerFeature.BrowserCompatible.mask)) {
            int iStringSize = j2 < 0 ? IOUtils.stringSize(-j2) + 1 : IOUtils.stringSize(j2);
            int length = str.length();
            int i2 = this.count + length + 4 + iStringSize;
            if (i2 > this.buf.length) {
                if (this.writer != null) {
                    write(c2);
                    writeFieldName(str);
                    writeLong(j2);
                    return;
                }
                expandCapacity(i2);
            }
            int i3 = this.count;
            this.count = i2;
            char[] cArr = this.buf;
            cArr[i3] = c2;
            int i4 = i3 + length;
            cArr[i3 + 1] = this.keySeperator;
            str.getChars(0, length, cArr, i3 + 2);
            char[] cArr2 = this.buf;
            cArr2[i4 + 2] = this.keySeperator;
            cArr2[i4 + 3] = ':';
            IOUtils.getChars(j2, this.count, cArr2);
            return;
        }
        write(c2);
        writeFieldName(str);
        writeLong(j2);
    }

    public void write(boolean z2) {
        if (z2) {
            write("true");
        } else {
            write(RequestConstant.FALSE);
        }
    }

    public void writeFieldValue(char c2, String str, float f2) throws IOException {
        write(c2);
        writeFieldName(str);
        writeFloat(f2, false);
    }

    public void writeFieldValue(char c2, String str, double d2) throws IOException {
        write(c2);
        writeFieldName(str);
        writeDouble(d2, false);
    }

    public void writeFieldValue(char c2, String str, String str2) {
        if (this.quoteFieldNames) {
            if (this.useSingleQuotes) {
                write(c2);
                writeFieldName(str);
                if (str2 == null) {
                    writeNull();
                    return;
                } else {
                    writeString(str2);
                    return;
                }
            }
            if (isEnabled(SerializerFeature.BrowserCompatible)) {
                write(c2);
                writeStringWithDoubleQuote(str, ':');
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            writeFieldValueStringWithDoubleQuoteCheck(c2, str, str2);
            return;
        }
        write(c2);
        writeFieldName(str);
        if (str2 == null) {
            writeNull();
        } else {
            writeString(str2);
        }
    }

    public void writeFieldValue(char c2, String str, Enum<?> r4) {
        if (r4 == null) {
            write(c2);
            writeFieldName(str);
            writeNull();
        } else if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            writeEnumFieldValue(c2, str, r4.name());
        } else if (this.writeEnumUsingToString) {
            writeEnumFieldValue(c2, str, r4.toString());
        } else {
            writeFieldValue(c2, str, r4.ordinal());
        }
    }

    public void writeFieldValue(char c2, String str, BigDecimal bigDecimal) {
        String string;
        write(c2);
        writeFieldName(str);
        if (bigDecimal == null) {
            writeNull();
            return;
        }
        int iScale = bigDecimal.scale();
        if (isEnabled(SerializerFeature.WriteBigDecimalAsPlain) && iScale >= -100 && iScale < 100) {
            string = bigDecimal.toPlainString();
        } else {
            string = bigDecimal.toString();
        }
        write(string);
    }

    /* JADX WARN: Removed duplicated region for block: B:166:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0147  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeStringWithDoubleQuote(char[] r23, char r24) {
        /*
            Method dump skipped, instructions count: 1340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeWriter.writeStringWithDoubleQuote(char[], char):void");
    }
}
