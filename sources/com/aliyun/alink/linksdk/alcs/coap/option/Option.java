package com.aliyun.alink.linksdk.alcs.coap.option;

import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.alcs.coap.MediaTypeRegistry;
import com.aliyun.alink.linksdk.alcs.coap.option.OptionNumberRegistry;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class Option implements Comparable<Option> {
    private static final String TAG = "Option";
    private int number;
    private byte[] value;

    /* renamed from: com.aliyun.alink.linksdk.alcs.coap.option.Option$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$alink$linksdk$alcs$coap$option$OptionNumberRegistry$optionFormats;

        static {
            int[] iArr = new int[OptionNumberRegistry.optionFormats.values().length];
            $SwitchMap$com$aliyun$alink$linksdk$alcs$coap$option$OptionNumberRegistry$optionFormats = iArr;
            try {
                iArr[OptionNumberRegistry.optionFormats.INTEGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$alcs$coap$option$OptionNumberRegistry$optionFormats[OptionNumberRegistry.optionFormats.STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public Option() {
        this.value = new byte[0];
    }

    private String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte b2 : bArr) {
            sb.append(String.format("%02x", Integer.valueOf(b2 & 255)));
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Option)) {
            return false;
        }
        Option option = (Option) obj;
        return this.number == option.number && Arrays.equals(this.value, option.value);
    }

    public int getIntegerValue() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.value;
            if (i2 >= bArr.length) {
                return i3;
            }
            i3 += (bArr[(bArr.length - i2) - 1] & 255) << (i2 * 8);
            i2++;
        }
    }

    public int getLength() {
        return this.value.length;
    }

    public long getLongValue() {
        long j2 = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= this.value.length) {
                return j2;
            }
            j2 += (r3[(r3.length - i2) - 1] & 255) << (i2 * 8);
            i2++;
        }
    }

    public int getNumber() {
        return this.number;
    }

    public String getStringValue() {
        return new String(this.value, AlcsCoAPConstant.UTF8_CHARSET);
    }

    public byte[] getValue() {
        return this.value;
    }

    public int hashCode() {
        return (this.number * 31) + this.value.hashCode();
    }

    public boolean isCritical() {
        return (this.number & 1) != 0;
    }

    public boolean isNoCacheKey() {
        return (this.number & 30) == 28;
    }

    public boolean isUnSafe() {
        return (this.number & 2) != 0;
    }

    public void setIntegerValue(int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < 4 && (i2 >= (1 << (i4 * 8)) || i2 < 0); i4++) {
            i3++;
        }
        this.value = new byte[i3];
        for (int i5 = 0; i5 < i3; i5++) {
            this.value[(i3 - i5) - 1] = (byte) (i2 >> (i5 * 8));
        }
    }

    public void setLongValue(long j2) {
        int i2 = 0;
        for (int i3 = 0; i3 < 8 && (j2 >= (1 << (i3 * 8)) || j2 < 0); i3++) {
            i2++;
        }
        this.value = new byte[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            this.value[(i2 - i4) - 1] = (byte) (j2 >> (i4 * 8));
        }
    }

    public void setNumber(int i2) {
        this.number = i2;
    }

    public void setStringValue(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (str == null) {
            ALog.e(TAG, "str null");
        } else {
            this.value = str.getBytes(AlcsCoAPConstant.UTF8_CHARSET);
        }
    }

    public void setValue(byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (bArr == null) {
            ALog.e(TAG, "value empty");
        } else {
            this.value = bArr;
        }
    }

    public String toString() {
        return OptionNumberRegistry.toString(this.number) + ": " + toValueString();
    }

    public String toValueString() {
        int i2 = AnonymousClass1.$SwitchMap$com$aliyun$alink$linksdk$alcs$coap$option$OptionNumberRegistry$optionFormats[OptionNumberRegistry.getFormatByNr(this.number).ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                return toHexString(getValue());
            }
            return "\"" + getStringValue() + "\"";
        }
        int i3 = this.number;
        if (i3 == 17 || i3 == 12) {
            return "\"" + MediaTypeRegistry.toString(getIntegerValue()) + "\"";
        }
        if (i3 != 27 && i3 != 23) {
            return Integer.toString(getIntegerValue());
        }
        return "\"" + new BlockOption(this.value) + "\"";
    }

    @Override // java.lang.Comparable
    public int compareTo(Option option) {
        return this.number - option.number;
    }

    public Option(int i2) {
        this.number = i2;
        this.value = new byte[0];
    }

    public Option(int i2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.number = i2;
        setStringValue(str);
    }

    public Option(int i2, int i3) {
        this.number = i2;
        setIntegerValue(i3);
    }

    public Option(int i2, long j2) {
        this.number = i2;
        setLongValue(j2);
    }

    public Option(int i2, byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.number = i2;
        setValue(bArr);
    }
}
