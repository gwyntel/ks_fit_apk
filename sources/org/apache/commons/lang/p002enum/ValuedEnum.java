package org.apache.commons.lang.p002enum;

import com.huawei.hms.framework.common.ContainerUtils;
import org.apache.commons.lang.ClassUtils;

/* loaded from: classes5.dex */
public abstract class ValuedEnum extends Enum {
    private static final long serialVersionUID = -7129650521543789085L;
    private final int iValue;

    protected ValuedEnum(String str, int i2) {
        super(str);
        this.iValue = i2;
    }

    protected static Enum getEnum(Class cls, int i2) {
        if (cls == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        for (ValuedEnum valuedEnum : Enum.getEnumList(cls)) {
            if (valuedEnum.getValue() == i2) {
                return valuedEnum;
            }
        }
        return null;
    }

    @Override // org.apache.commons.lang.p002enum.Enum, java.lang.Comparable
    public int compareTo(Object obj) {
        return this.iValue - ((ValuedEnum) obj).iValue;
    }

    public final int getValue() {
        return this.iValue;
    }

    @Override // org.apache.commons.lang.p002enum.Enum
    public String toString() {
        if (this.iToString == null) {
            String shortClassName = ClassUtils.getShortClassName(getEnumClass());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(shortClassName);
            stringBuffer.append("[");
            stringBuffer.append(getName());
            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
            stringBuffer.append(getValue());
            stringBuffer.append("]");
            this.iToString = stringBuffer.toString();
        }
        return this.iToString;
    }
}
