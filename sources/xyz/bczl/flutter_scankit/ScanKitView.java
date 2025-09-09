package xyz.bczl.flutter_scankit;

import android.util.LongSparseArray;
import android.view.View;
import io.flutter.plugin.platform.PlatformView;

/* loaded from: classes5.dex */
public class ScanKitView implements PlatformView {
    private final LongSparseArray<ScanKitCustomMode> customModeList;
    private final ScanKitCustomMode mode;
    private final int viewId;

    public ScanKitView(int i2, LongSparseArray<ScanKitCustomMode> longSparseArray) {
        this.viewId = i2;
        this.customModeList = longSparseArray;
        this.mode = longSparseArray.get(i2);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public void dispose() {
        this.customModeList.remove(this.viewId);
        this.mode.dispose();
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public View getView() {
        return this.mode.getView();
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
}
