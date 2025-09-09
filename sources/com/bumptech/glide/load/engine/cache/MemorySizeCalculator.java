package com.bumptech.glide.load.engine.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.util.Preconditions;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes3.dex */
public final class MemorySizeCalculator {
    private static final int LOW_MEMORY_BYTE_ARRAY_POOL_DIVISOR = 2;
    private static final String TAG = "MemorySizeCalculator";
    private final int arrayPoolSize;
    private final int bitmapPoolSize;
    private final Context context;
    private final int memoryCacheSize;

    public static final class Builder {

        /* renamed from: i, reason: collision with root package name */
        static final int f12358i;

        /* renamed from: a, reason: collision with root package name */
        final Context f12359a;

        /* renamed from: b, reason: collision with root package name */
        ActivityManager f12360b;

        /* renamed from: c, reason: collision with root package name */
        ScreenDimensions f12361c;

        /* renamed from: e, reason: collision with root package name */
        float f12363e;

        /* renamed from: d, reason: collision with root package name */
        float f12362d = 2.0f;

        /* renamed from: f, reason: collision with root package name */
        float f12364f = 0.4f;

        /* renamed from: g, reason: collision with root package name */
        float f12365g = 0.33f;

        /* renamed from: h, reason: collision with root package name */
        int f12366h = 4194304;

        static {
            f12358i = Build.VERSION.SDK_INT < 26 ? 4 : 1;
        }

        public Builder(Context context) {
            this.f12363e = f12358i;
            this.f12359a = context;
            this.f12360b = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            this.f12361c = new DisplayMetricsScreenDimensions(context.getResources().getDisplayMetrics());
            if (Build.VERSION.SDK_INT < 26 || !MemorySizeCalculator.a(this.f12360b)) {
                return;
            }
            this.f12363e = 0.0f;
        }

        public MemorySizeCalculator build() {
            return new MemorySizeCalculator(this);
        }

        public Builder setArrayPoolSize(int i2) {
            this.f12366h = i2;
            return this;
        }

        public Builder setBitmapPoolScreens(float f2) {
            Preconditions.checkArgument(f2 >= 0.0f, "Bitmap pool screens must be greater than or equal to 0");
            this.f12363e = f2;
            return this;
        }

        public Builder setLowMemoryMaxSizeMultiplier(float f2) {
            Preconditions.checkArgument(f2 >= 0.0f && f2 <= 1.0f, "Low memory max size multiplier must be between 0 and 1");
            this.f12365g = f2;
            return this;
        }

        public Builder setMaxSizeMultiplier(float f2) {
            Preconditions.checkArgument(f2 >= 0.0f && f2 <= 1.0f, "Size multiplier must be between 0 and 1");
            this.f12364f = f2;
            return this;
        }

        public Builder setMemoryCacheScreens(float f2) {
            Preconditions.checkArgument(f2 >= 0.0f, "Memory cache screens must be greater than or equal to 0");
            this.f12362d = f2;
            return this;
        }
    }

    private static final class DisplayMetricsScreenDimensions implements ScreenDimensions {
        private final DisplayMetrics displayMetrics;

        DisplayMetricsScreenDimensions(DisplayMetrics displayMetrics) {
            this.displayMetrics = displayMetrics;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.ScreenDimensions
        public int getHeightPixels() {
            return this.displayMetrics.heightPixels;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.ScreenDimensions
        public int getWidthPixels() {
            return this.displayMetrics.widthPixels;
        }
    }

    interface ScreenDimensions {
        int getHeightPixels();

        int getWidthPixels();
    }

    MemorySizeCalculator(Builder builder) {
        this.context = builder.f12359a;
        int i2 = a(builder.f12360b) ? builder.f12366h / 2 : builder.f12366h;
        this.arrayPoolSize = i2;
        int maxSize = getMaxSize(builder.f12360b, builder.f12364f, builder.f12365g);
        float widthPixels = builder.f12361c.getWidthPixels() * builder.f12361c.getHeightPixels() * 4;
        int iRound = Math.round(builder.f12363e * widthPixels);
        int iRound2 = Math.round(widthPixels * builder.f12362d);
        int i3 = maxSize - i2;
        int i4 = iRound2 + iRound;
        if (i4 <= i3) {
            this.memoryCacheSize = iRound2;
            this.bitmapPoolSize = iRound;
        } else {
            float f2 = i3;
            float f3 = builder.f12363e;
            float f4 = builder.f12362d;
            float f5 = f2 / (f3 + f4);
            this.memoryCacheSize = Math.round(f4 * f5);
            this.bitmapPoolSize = Math.round(f5 * builder.f12363e);
        }
        if (Log.isLoggable(TAG, 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Calculation complete, Calculated memory cache size: ");
            sb.append(toMb(this.memoryCacheSize));
            sb.append(", pool size: ");
            sb.append(toMb(this.bitmapPoolSize));
            sb.append(", byte array size: ");
            sb.append(toMb(i2));
            sb.append(", memory class limited? ");
            sb.append(i4 > maxSize);
            sb.append(", max size: ");
            sb.append(toMb(maxSize));
            sb.append(", memoryClass: ");
            sb.append(builder.f12360b.getMemoryClass());
            sb.append(", isLowMemoryDevice: ");
            sb.append(a(builder.f12360b));
            Log.d(TAG, sb.toString());
        }
    }

    static boolean a(ActivityManager activityManager) {
        return activityManager.isLowRamDevice();
    }

    private static int getMaxSize(ActivityManager activityManager, float f2, float f3) {
        float memoryClass = activityManager.getMemoryClass() * 1048576;
        if (a(activityManager)) {
            f2 = f3;
        }
        return Math.round(memoryClass * f2);
    }

    private String toMb(int i2) {
        return Formatter.formatFileSize(this.context, i2);
    }

    public int getArrayPoolSizeInBytes() {
        return this.arrayPoolSize;
    }

    public int getBitmapPoolSize() {
        return this.bitmapPoolSize;
    }

    public int getMemoryCacheSize() {
        return this.memoryCacheSize;
    }
}
