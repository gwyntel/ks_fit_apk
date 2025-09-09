package androidx.core.view;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes.dex */
public final class DisplayCompat {
    private static final int DISPLAY_SIZE_4K_HEIGHT = 2160;
    private static final int DISPLAY_SIZE_4K_WIDTH = 3840;

    @RequiresApi(23)
    static class Api23Impl {
        private Api23Impl() {
        }

        static ModeCompat a(Context context, Display display) {
            Display.Mode mode = display.getMode();
            Point pointA = DisplayCompat.a(context, display);
            return (pointA == null || c(mode, pointA)) ? new ModeCompat(mode, true) : new ModeCompat(mode, pointA);
        }

        static boolean b(Display display) {
            Display.Mode mode = display.getMode();
            for (Display.Mode mode2 : display.getSupportedModes()) {
                if (mode.getPhysicalHeight() < mode2.getPhysicalHeight() || mode.getPhysicalWidth() < mode2.getPhysicalWidth()) {
                    return false;
                }
            }
            return true;
        }

        static boolean c(Display.Mode mode, Point point) {
            return (mode.getPhysicalWidth() == point.x && mode.getPhysicalHeight() == point.y) || (mode.getPhysicalWidth() == point.y && mode.getPhysicalHeight() == point.x);
        }

        static boolean d(Display.Mode mode, Display.Mode mode2) {
            return mode.getPhysicalWidth() == mode2.getPhysicalWidth() && mode.getPhysicalHeight() == mode2.getPhysicalHeight();
        }

        @NonNull
        @SuppressLint({"ArrayReturn"})
        public static ModeCompat[] getSupportedModes(@NonNull Context context, @NonNull Display display) {
            Display.Mode[] supportedModes = display.getSupportedModes();
            ModeCompat[] modeCompatArr = new ModeCompat[supportedModes.length];
            Display.Mode mode = display.getMode();
            Point pointA = DisplayCompat.a(context, display);
            if (pointA == null || c(mode, pointA)) {
                for (int i2 = 0; i2 < supportedModes.length; i2++) {
                    modeCompatArr[i2] = new ModeCompat(supportedModes[i2], d(supportedModes[i2], mode));
                }
            } else {
                for (int i3 = 0; i3 < supportedModes.length; i3++) {
                    modeCompatArr[i3] = d(supportedModes[i3], mode) ? new ModeCompat(supportedModes[i3], pointA) : new ModeCompat(supportedModes[i3], false);
                }
            }
            return modeCompatArr;
        }
    }

    private DisplayCompat() {
    }

    static Point a(Context context, Display display) {
        Point physicalDisplaySizeFromSystemProperties = Build.VERSION.SDK_INT < 28 ? parsePhysicalDisplaySizeFromSystemProperties("sys.display-size", display) : parsePhysicalDisplaySizeFromSystemProperties("vendor.display-size", display);
        if (physicalDisplaySizeFromSystemProperties != null) {
            return physicalDisplaySizeFromSystemProperties;
        }
        if (isSonyBravia4kTv(context) && b(display)) {
            return new Point(DISPLAY_SIZE_4K_WIDTH, DISPLAY_SIZE_4K_HEIGHT);
        }
        return null;
    }

    static boolean b(Display display) {
        return Api23Impl.b(display);
    }

    @NonNull
    private static Point getDisplaySize(@NonNull Context context, @NonNull Display display) {
        Point pointA = a(context, display);
        if (pointA != null) {
            return pointA;
        }
        Point point = new Point();
        display.getRealSize(point);
        return point;
    }

    @NonNull
    public static ModeCompat getMode(@NonNull Context context, @NonNull Display display) {
        return Api23Impl.a(context, display);
    }

    @NonNull
    @SuppressLint({"ArrayReturn"})
    public static ModeCompat[] getSupportedModes(@NonNull Context context, @NonNull Display display) {
        return Api23Impl.getSupportedModes(context, display);
    }

    @Nullable
    private static String getSystemProperty(String str) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).invoke(cls, str);
        } catch (Exception unused) {
            return null;
        }
    }

    private static boolean isSonyBravia4kTv(@NonNull Context context) {
        return isTv(context) && "Sony".equals(Build.MANUFACTURER) && Build.MODEL.startsWith("BRAVIA") && context.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd");
    }

    private static boolean isTv(@NonNull Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService("uimode");
        return uiModeManager != null && uiModeManager.getCurrentModeType() == 4;
    }

    private static Point parseDisplaySize(@NonNull String str) throws NumberFormatException {
        String[] strArrSplit = str.trim().split("x", -1);
        if (strArrSplit.length == 2) {
            int i2 = Integer.parseInt(strArrSplit[0]);
            int i3 = Integer.parseInt(strArrSplit[1]);
            if (i2 > 0 && i3 > 0) {
                return new Point(i2, i3);
            }
        }
        throw new NumberFormatException();
    }

    @Nullable
    private static Point parsePhysicalDisplaySizeFromSystemProperties(@NonNull String str, @NonNull Display display) throws ClassNotFoundException {
        if (display.getDisplayId() != 0) {
            return null;
        }
        String systemProperty = getSystemProperty(str);
        if (!TextUtils.isEmpty(systemProperty) && systemProperty != null) {
            try {
                return parseDisplaySize(systemProperty);
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    public static final class ModeCompat {
        private final boolean mIsNative;
        private final Display.Mode mMode;
        private final Point mPhysicalSize;

        @RequiresApi(23)
        static class Api23Impl {
            private Api23Impl() {
            }

            @DoNotInline
            static int a(Display.Mode mode) {
                return mode.getPhysicalHeight();
            }

            @DoNotInline
            static int b(Display.Mode mode) {
                return mode.getPhysicalWidth();
            }
        }

        ModeCompat(Display.Mode mode, boolean z2) {
            Preconditions.checkNotNull(mode, "mode == null, can't wrap a null reference");
            this.mPhysicalSize = new Point(Api23Impl.b(mode), Api23Impl.a(mode));
            this.mMode = mode;
            this.mIsNative = z2;
        }

        public int getPhysicalHeight() {
            return this.mPhysicalSize.y;
        }

        public int getPhysicalWidth() {
            return this.mPhysicalSize.x;
        }

        @Deprecated
        public boolean isNative() {
            return this.mIsNative;
        }

        @Nullable
        @RequiresApi(23)
        public Display.Mode toMode() {
            return this.mMode;
        }

        ModeCompat(Display.Mode mode, Point point) {
            Preconditions.checkNotNull(mode, "mode == null, can't wrap a null reference");
            Preconditions.checkNotNull(point, "physicalSize == null");
            this.mPhysicalSize = point;
            this.mMode = mode;
            this.mIsNative = true;
        }
    }
}
