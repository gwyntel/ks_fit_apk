package com.shockwave.pdfium.util;

/* loaded from: classes4.dex */
public class Size {
    private final int height;
    private final int width;

    public Size(int i2, int i3) {
        this.width = i2;
        this.height = i3;
    }

    public boolean equals(Object obj) {
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
        return this.width == size.width && this.height == size.height;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        int i2 = this.height;
        int i3 = this.width;
        return i2 ^ ((i3 >>> 16) | (i3 << 16));
    }

    public String toString() {
        return this.width + "x" + this.height;
    }
}
