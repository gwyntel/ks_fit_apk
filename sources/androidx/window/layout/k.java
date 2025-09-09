package androidx.window.layout;

import android.content.Context;
import androidx.window.layout.WindowInfoTracker;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class k {
    static {
        WindowInfoTracker.Companion companion = WindowInfoTracker.INSTANCE;
    }

    public static WindowInfoTracker a(Context context) {
        return WindowInfoTracker.INSTANCE.getOrCreate(context);
    }

    public static void b(WindowInfoTrackerDecorator windowInfoTrackerDecorator) {
        WindowInfoTracker.INSTANCE.overrideDecorator(windowInfoTrackerDecorator);
    }

    public static void c() {
        WindowInfoTracker.INSTANCE.reset();
    }
}
