package xyz.bczl.flutter_scankit;

import android.content.Context;
import android.util.LongSparseArray;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import java.util.Map;

/* loaded from: classes5.dex */
public class ScanKitViewFactory extends PlatformViewFactory {
    private final ActivityPluginBinding binding;
    private final LongSparseArray<ScanKitCustomMode> customModeList;
    private final BinaryMessenger messenger;

    public ScanKitViewFactory(LongSparseArray<ScanKitCustomMode> longSparseArray, @NonNull BinaryMessenger binaryMessenger, ActivityPluginBinding activityPluginBinding) {
        super(StandardMessageCodec.INSTANCE);
        this.customModeList = longSparseArray;
        this.messenger = binaryMessenger;
        this.binding = activityPluginBinding;
    }

    @Override // io.flutter.plugin.platform.PlatformViewFactory
    public PlatformView create(Context context, int i2, Object obj) {
        if (!(obj instanceof Map)) {
            throw new RuntimeException("ScanKitViewFactory create args error!");
        }
        this.customModeList.put(i2, new ScanKitCustomMode(i2, this.messenger, (Map) obj, this.binding));
        return new ScanKitView(i2, this.customModeList);
    }
}
