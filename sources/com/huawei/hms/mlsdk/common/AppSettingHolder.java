package com.huawei.hms.mlsdk.common;

import com.alipay.sdk.m.u.i;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class AppSettingHolder<T> {
    private final String persistenceKey;
    private final T setting;

    private AppSettingHolder(String str, T t2) {
        if (str == null) {
            throw new NullPointerException("Null PersistentKey");
        }
        if (t2 == null) {
            throw new NullPointerException("Null setting");
        }
        this.persistenceKey = str;
        this.setting = t2;
    }

    public static <T> AppSettingHolder<T> create(String str, T t2) {
        return new AppSettingHolder<>(str, t2);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AppSettingHolder)) {
            return false;
        }
        AppSettingHolder appSettingHolder = (AppSettingHolder) obj;
        return this.persistenceKey.equals(appSettingHolder.persistenceKey) && this.setting.equals(appSettingHolder.setting);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.persistenceKey, this.setting});
    }

    public final String toString() {
        String str = this.persistenceKey;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 97 + String.valueOf(this.setting).length());
        sb.append("MlModelDriverInstanceKey{persistentKey=");
        sb.append(str);
        sb.append(", setting=");
        sb.append(this.setting);
        sb.append(i.f9804d);
        return sb.toString();
    }
}
