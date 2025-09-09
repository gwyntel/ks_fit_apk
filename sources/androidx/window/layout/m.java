package androidx.window.layout;

import androidx.window.layout.WindowMetricsCalculator;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class m {
    static {
        WindowMetricsCalculator.Companion companion = WindowMetricsCalculator.INSTANCE;
    }

    public static WindowMetricsCalculator a() {
        return WindowMetricsCalculator.INSTANCE.getOrCreate();
    }

    public static void b(WindowMetricsCalculatorDecorator windowMetricsCalculatorDecorator) {
        WindowMetricsCalculator.INSTANCE.overrideDecorator(windowMetricsCalculatorDecorator);
    }

    public static void c() {
        WindowMetricsCalculator.INSTANCE.reset();
    }
}
