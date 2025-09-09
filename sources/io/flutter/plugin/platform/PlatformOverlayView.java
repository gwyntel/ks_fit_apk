package io.flutter.plugin.platform;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.embedding.android.FlutterImageView;

/* loaded from: classes4.dex */
public class PlatformOverlayView extends FlutterImageView {

    @Nullable
    private AccessibilityEventsDelegate accessibilityDelegate;

    public PlatformOverlayView(@NonNull Context context, int i2, int i3, @NonNull AccessibilityEventsDelegate accessibilityEventsDelegate) {
        super(context, i2, i3, FlutterImageView.SurfaceKind.overlay);
        this.accessibilityDelegate = accessibilityEventsDelegate;
    }

    @Override // android.view.View
    public boolean onHoverEvent(@NonNull MotionEvent motionEvent) {
        AccessibilityEventsDelegate accessibilityEventsDelegate = this.accessibilityDelegate;
        if (accessibilityEventsDelegate == null || !accessibilityEventsDelegate.onAccessibilityHoverEvent(motionEvent, true)) {
            return super.onHoverEvent(motionEvent);
        }
        return true;
    }

    public PlatformOverlayView(@NonNull Context context) {
        this(context, 1, 1, null);
    }

    public PlatformOverlayView(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        this(context, 1, 1, null);
    }
}
