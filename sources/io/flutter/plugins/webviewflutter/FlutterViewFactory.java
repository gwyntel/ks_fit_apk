package io.flutter.plugins.webviewflutter;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

/* loaded from: classes4.dex */
class FlutterViewFactory extends PlatformViewFactory {
    private final InstanceManager instanceManager;

    FlutterViewFactory(InstanceManager instanceManager) {
        super(StandardMessageCodec.INSTANCE);
        this.instanceManager = instanceManager;
    }

    @Override // io.flutter.plugin.platform.PlatformViewFactory
    @NonNull
    public PlatformView create(Context context, int i2, @Nullable Object obj) {
        if (((Integer) obj) == null) {
            throw new IllegalStateException("An identifier is required to retrieve a View instance.");
        }
        final Object instanceManager = this.instanceManager.getInstance(r3.intValue());
        if (instanceManager instanceof PlatformView) {
            return (PlatformView) instanceManager;
        }
        if (instanceManager instanceof View) {
            return new PlatformView() { // from class: io.flutter.plugins.webviewflutter.FlutterViewFactory.1
                @Override // io.flutter.plugin.platform.PlatformView
                public void dispose() {
                }

                @Override // io.flutter.plugin.platform.PlatformView
                public View getView() {
                    return (View) instanceManager;
                }

                @Override // io.flutter.plugin.platform.PlatformView
                public /* synthetic */ void onFlutterViewAttached(View view) {
                    io.flutter.plugin.platform.f.a(this, view);
                }

                @Override // io.flutter.plugin.platform.PlatformView
                public /* synthetic */ void onFlutterViewDetached() {
                    io.flutter.plugin.platform.f.b(this);
                }

                @Override // io.flutter.plugin.platform.PlatformView
                public /* synthetic */ void onInputConnectionLocked() {
                    io.flutter.plugin.platform.f.c(this);
                }

                @Override // io.flutter.plugin.platform.PlatformView
                public /* synthetic */ void onInputConnectionUnlocked() {
                    io.flutter.plugin.platform.f.d(this);
                }
            };
        }
        throw new IllegalStateException("Unable to find a PlatformView or View instance: " + obj + ", " + instanceManager);
    }
}
