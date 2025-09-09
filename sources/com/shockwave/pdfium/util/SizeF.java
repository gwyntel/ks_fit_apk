package com.shockwave.pdfium.util;

/* loaded from: classes4.dex */
public class SizeF {
    private final float height;
    private final float width;

    public SizeF(float f2, float f3) {
        this.width = f2;
        this.height = f3;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SizeF)) {
            return false;
        }
        SizeF sizeF = (SizeF) obj;
        return this.width == sizeF.width && this.height == sizeF.height;
    }

    public float getHeight() {
        return this.height;
    }

    public float getWidth() {
        return this.width;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.width) ^ Float.floatToIntBits(this.height);
    }

    public Size toSize() {
        return new Size((int) this.width, (int) this.height);
    }

    public String toString() {
        return this.width + "x" + this.height;
    }
}
