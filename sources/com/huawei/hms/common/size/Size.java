package com.huawei.hms.common.size;

import androidx.webkit.ProxyConfig;
import com.huawei.hms.common.internal.Objects;

/* loaded from: classes4.dex */
public class Size {

    /* renamed from: a, reason: collision with root package name */
    private final int f16008a;

    /* renamed from: b, reason: collision with root package name */
    private final int f16009b;

    public Size(int i2, int i3) {
        this.f16008a = i2;
        this.f16009b = i3;
    }

    public static Size parseSize(String str) {
        try {
            int iIndexOf = str.indexOf("x");
            if (iIndexOf < 0) {
                iIndexOf = str.indexOf(ProxyConfig.MATCH_ALL_SCHEMES);
            }
            return new Size(Integer.parseInt(str.substring(0, iIndexOf)), Integer.parseInt(str.substring(iIndexOf + 1)));
        } catch (Exception unused) {
            throw new IllegalArgumentException("Size parses failed");
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Size)) {
            return false;
        }
        Size size = (Size) obj;
        return this.f16008a == size.f16008a && this.f16009b == size.f16009b;
    }

    public final int getHeight() {
        return this.f16009b;
    }

    public final int getWidth() {
        return this.f16008a;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(getWidth()), Integer.valueOf(getHeight()));
    }

    public final String toString() {
        return "Width is " + this.f16008a + " Height is " + this.f16009b;
    }
}
