package com.huawei.hms.common.util;

import com.huawei.hms.common.Preconditions;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public final class Objects {

    public static final class ToStringHelper {

        /* renamed from: a, reason: collision with root package name */
        public final List<String> f16030a;

        /* renamed from: b, reason: collision with root package name */
        public final Object f16031b;

        public ToStringHelper(Object obj) {
            this.f16031b = Preconditions.checkNotNull(obj);
            this.f16030a = new ArrayList();
        }

        public ToStringHelper add(String str, Object obj) {
            String str2 = (String) Preconditions.checkNotNull(str);
            String strValueOf = String.valueOf(obj);
            this.f16030a.add(str2 + ContainerUtils.KEY_VALUE_DELIMITER + strValueOf);
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(100);
            sb.append(this.f16031b.getClass().getSimpleName());
            sb.append('{');
            int size = this.f16030a.size();
            for (int i2 = 0; i2 < size; i2++) {
                sb.append(this.f16030a.get(i2));
                if (i2 < size - 1) {
                    sb.append(", ");
                }
            }
            sb.append('}');
            return sb.toString();
        }
    }

    public Objects() {
        throw new AssertionError("illegal argument");
    }

    public static boolean equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static int hashCode(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj);
    }
}
