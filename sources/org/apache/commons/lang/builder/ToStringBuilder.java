package org.apache.commons.lang.builder;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

/* loaded from: classes5.dex */
public class ToStringBuilder {
    private static volatile ToStringStyle defaultStyle = ToStringStyle.DEFAULT_STYLE;
    private final StringBuffer buffer;
    private final Object object;
    private final ToStringStyle style;

    public ToStringBuilder(Object obj) {
        this(obj, null, null);
    }

    public static ToStringStyle getDefaultStyle() {
        return defaultStyle;
    }

    public static String reflectionToString(Object obj) {
        return ReflectionToStringBuilder.toString(obj);
    }

    public static void setDefaultStyle(ToStringStyle toStringStyle) {
        if (toStringStyle == null) {
            throw new IllegalArgumentException("The style must not be null");
        }
        defaultStyle = toStringStyle;
    }

    public ToStringBuilder append(boolean z2) {
        this.style.append(this.buffer, (String) null, z2);
        return this;
    }

    public ToStringBuilder appendAsObjectToString(Object obj) {
        ObjectUtils.identityToString(getStringBuffer(), obj);
        return this;
    }

    public ToStringBuilder appendSuper(String str) {
        if (str != null) {
            this.style.appendSuper(this.buffer, str);
        }
        return this;
    }

    public ToStringBuilder appendToString(String str) {
        if (str != null) {
            this.style.appendToString(this.buffer, str);
        }
        return this;
    }

    public Object getObject() {
        return this.object;
    }

    public StringBuffer getStringBuffer() {
        return this.buffer;
    }

    public ToStringStyle getStyle() {
        return this.style;
    }

    public String toString() {
        if (getObject() == null) {
            getStringBuffer().append(getStyle().getNullText());
        } else {
            this.style.appendEnd(getStringBuffer(), getObject());
        }
        return getStringBuffer().toString();
    }

    public ToStringBuilder(Object obj, ToStringStyle toStringStyle) {
        this(obj, toStringStyle, null);
    }

    public static String reflectionToString(Object obj, ToStringStyle toStringStyle) {
        return ReflectionToStringBuilder.toString(obj, toStringStyle);
    }

    public ToStringBuilder append(boolean[] zArr) {
        this.style.append(this.buffer, (String) null, zArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder(Object obj, ToStringStyle toStringStyle, StringBuffer stringBuffer) {
        toStringStyle = toStringStyle == null ? getDefaultStyle() : toStringStyle;
        stringBuffer = stringBuffer == null ? new StringBuffer(512) : stringBuffer;
        this.buffer = stringBuffer;
        this.style = toStringStyle;
        this.object = obj;
        toStringStyle.appendStart(stringBuffer, obj);
    }

    public static String reflectionToString(Object obj, ToStringStyle toStringStyle, boolean z2) {
        return ReflectionToStringBuilder.toString(obj, toStringStyle, z2, false, null);
    }

    public ToStringBuilder append(byte b2) {
        this.style.append(this.buffer, (String) null, b2);
        return this;
    }

    public static String reflectionToString(Object obj, ToStringStyle toStringStyle, boolean z2, Class cls) {
        return ReflectionToStringBuilder.toString(obj, toStringStyle, z2, false, cls);
    }

    public ToStringBuilder append(byte[] bArr) {
        this.style.append(this.buffer, (String) null, bArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(char c2) {
        this.style.append(this.buffer, (String) null, c2);
        return this;
    }

    public ToStringBuilder append(char[] cArr) {
        this.style.append(this.buffer, (String) null, cArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(double d2) {
        this.style.append(this.buffer, (String) null, d2);
        return this;
    }

    public ToStringBuilder append(double[] dArr) {
        this.style.append(this.buffer, (String) null, dArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(float f2) {
        this.style.append(this.buffer, (String) null, f2);
        return this;
    }

    public ToStringBuilder append(float[] fArr) {
        this.style.append(this.buffer, (String) null, fArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(int i2) {
        this.style.append(this.buffer, (String) null, i2);
        return this;
    }

    public ToStringBuilder append(int[] iArr) {
        this.style.append(this.buffer, (String) null, iArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(long j2) {
        this.style.append(this.buffer, (String) null, j2);
        return this;
    }

    public ToStringBuilder append(long[] jArr) {
        this.style.append(this.buffer, (String) null, jArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(Object obj) {
        this.style.append(this.buffer, (String) null, obj, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(Object[] objArr) {
        this.style.append(this.buffer, (String) null, objArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(short s2) {
        this.style.append(this.buffer, (String) null, s2);
        return this;
    }

    public ToStringBuilder append(short[] sArr) {
        this.style.append(this.buffer, (String) null, sArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, boolean z2) {
        this.style.append(this.buffer, str, z2);
        return this;
    }

    public ToStringBuilder append(String str, boolean[] zArr) {
        this.style.append(this.buffer, str, zArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, boolean[] zArr, boolean z2) {
        this.style.append(this.buffer, str, zArr, BooleanUtils.toBooleanObject(z2));
        return this;
    }

    public ToStringBuilder append(String str, byte b2) {
        this.style.append(this.buffer, str, b2);
        return this;
    }

    public ToStringBuilder append(String str, byte[] bArr) {
        this.style.append(this.buffer, str, bArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, byte[] bArr, boolean z2) {
        this.style.append(this.buffer, str, bArr, BooleanUtils.toBooleanObject(z2));
        return this;
    }

    public ToStringBuilder append(String str, char c2) {
        this.style.append(this.buffer, str, c2);
        return this;
    }

    public ToStringBuilder append(String str, char[] cArr) {
        this.style.append(this.buffer, str, cArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, char[] cArr, boolean z2) {
        this.style.append(this.buffer, str, cArr, BooleanUtils.toBooleanObject(z2));
        return this;
    }

    public ToStringBuilder append(String str, double d2) {
        this.style.append(this.buffer, str, d2);
        return this;
    }

    public ToStringBuilder append(String str, double[] dArr) {
        this.style.append(this.buffer, str, dArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, double[] dArr, boolean z2) {
        this.style.append(this.buffer, str, dArr, BooleanUtils.toBooleanObject(z2));
        return this;
    }

    public ToStringBuilder append(String str, float f2) {
        this.style.append(this.buffer, str, f2);
        return this;
    }

    public ToStringBuilder append(String str, float[] fArr) {
        this.style.append(this.buffer, str, fArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, float[] fArr, boolean z2) {
        this.style.append(this.buffer, str, fArr, BooleanUtils.toBooleanObject(z2));
        return this;
    }

    public ToStringBuilder append(String str, int i2) {
        this.style.append(this.buffer, str, i2);
        return this;
    }

    public ToStringBuilder append(String str, int[] iArr) {
        this.style.append(this.buffer, str, iArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, int[] iArr, boolean z2) {
        this.style.append(this.buffer, str, iArr, BooleanUtils.toBooleanObject(z2));
        return this;
    }

    public ToStringBuilder append(String str, long j2) {
        this.style.append(this.buffer, str, j2);
        return this;
    }

    public ToStringBuilder append(String str, long[] jArr) {
        this.style.append(this.buffer, str, jArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, long[] jArr, boolean z2) {
        this.style.append(this.buffer, str, jArr, BooleanUtils.toBooleanObject(z2));
        return this;
    }

    public ToStringBuilder append(String str, Object obj) {
        this.style.append(this.buffer, str, obj, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, Object obj, boolean z2) {
        this.style.append(this.buffer, str, obj, BooleanUtils.toBooleanObject(z2));
        return this;
    }

    public ToStringBuilder append(String str, Object[] objArr) {
        this.style.append(this.buffer, str, objArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, Object[] objArr, boolean z2) {
        this.style.append(this.buffer, str, objArr, BooleanUtils.toBooleanObject(z2));
        return this;
    }

    public ToStringBuilder append(String str, short s2) {
        this.style.append(this.buffer, str, s2);
        return this;
    }

    public ToStringBuilder append(String str, short[] sArr) {
        this.style.append(this.buffer, str, sArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, short[] sArr, boolean z2) {
        this.style.append(this.buffer, str, sArr, BooleanUtils.toBooleanObject(z2));
        return this;
    }
}
