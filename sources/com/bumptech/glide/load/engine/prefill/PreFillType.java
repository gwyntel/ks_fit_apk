package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes3.dex */
public final class PreFillType {

    /* renamed from: a, reason: collision with root package name */
    static final Bitmap.Config f12376a = Bitmap.Config.RGB_565;
    private final Bitmap.Config config;
    private final int height;
    private final int weight;
    private final int width;

    public static class Builder {
        private Bitmap.Config config;
        private final int height;
        private int weight;
        private final int width;

        public Builder(int i2) {
            this(i2, i2);
        }

        PreFillType a() {
            return new PreFillType(this.width, this.height, this.config, this.weight);
        }

        Bitmap.Config b() {
            return this.config;
        }

        public Builder setConfig(@Nullable Bitmap.Config config) {
            this.config = config;
            return this;
        }

        public Builder setWeight(int i2) {
            if (i2 <= 0) {
                throw new IllegalArgumentException("Weight must be > 0");
            }
            this.weight = i2;
            return this;
        }

        public Builder(int i2, int i3) {
            this.weight = 1;
            if (i2 <= 0) {
                throw new IllegalArgumentException("Width must be > 0");
            }
            if (i3 <= 0) {
                throw new IllegalArgumentException("Height must be > 0");
            }
            this.width = i2;
            this.height = i3;
        }
    }

    PreFillType(int i2, int i3, Bitmap.Config config, int i4) {
        this.config = (Bitmap.Config) Preconditions.checkNotNull(config, "Config must not be null");
        this.width = i2;
        this.height = i3;
        this.weight = i4;
    }

    Bitmap.Config a() {
        return this.config;
    }

    int b() {
        return this.height;
    }

    int c() {
        return this.weight;
    }

    int d() {
        return this.width;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PreFillType)) {
            return false;
        }
        PreFillType preFillType = (PreFillType) obj;
        return this.height == preFillType.height && this.width == preFillType.width && this.weight == preFillType.weight && this.config == preFillType.config;
    }

    public int hashCode() {
        return (((((this.width * 31) + this.height) * 31) + this.config.hashCode()) * 31) + this.weight;
    }

    public String toString() {
        return "PreFillSize{width=" + this.width + ", height=" + this.height + ", config=" + this.config + ", weight=" + this.weight + '}';
    }
}
