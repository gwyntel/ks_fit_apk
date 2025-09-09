package org.apache.commons.lang.enums;

import com.huawei.hms.framework.common.ContainerUtils;
import java.lang.reflect.InvocationTargetException;
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

    private int getValueInOtherClassLoader(Object obj) {
        try {
            return ((Integer) obj.getClass().getMethod("getValue", null).invoke(obj, null)).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            throw new IllegalStateException("This should not happen");
        }
    }

    @Override // org.apache.commons.lang.enums.Enum, java.lang.Comparable
    public int compareTo(Object obj) {
        int i2;
        int valueInOtherClassLoader;
        if (obj == this) {
            return 0;
        }
        if (obj.getClass() == getClass()) {
            i2 = this.iValue;
            valueInOtherClassLoader = ((ValuedEnum) obj).iValue;
        } else {
            if (!obj.getClass().getName().equals(getClass().getName())) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Different enum class '");
                stringBuffer.append(ClassUtils.getShortClassName(obj.getClass()));
                stringBuffer.append("'");
                throw new ClassCastException(stringBuffer.toString());
            }
            i2 = this.iValue;
            valueInOtherClassLoader = getValueInOtherClassLoader(obj);
        }
        return i2 - valueInOtherClassLoader;
    }

    public final int getValue() {
        return this.iValue;
    }

    @Override // org.apache.commons.lang.enums.Enum
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
