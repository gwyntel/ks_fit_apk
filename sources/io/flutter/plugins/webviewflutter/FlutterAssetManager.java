package io.flutter.plugins.webviewflutter;

import android.content.res.AssetManager;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import java.io.IOException;

/* loaded from: classes4.dex */
abstract class FlutterAssetManager {
    final AssetManager assetManager;

    static class PluginBindingFlutterAssetManager extends FlutterAssetManager {
        final FlutterPlugin.FlutterAssets flutterAssets;

        PluginBindingFlutterAssetManager(AssetManager assetManager, FlutterPlugin.FlutterAssets flutterAssets) {
            super(assetManager);
            this.flutterAssets = flutterAssets;
        }

        @Override // io.flutter.plugins.webviewflutter.FlutterAssetManager
        public String getAssetFilePathByName(String str) {
            return this.flutterAssets.getAssetFilePathByName(str);
        }
    }

    public FlutterAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    abstract String getAssetFilePathByName(String str);

    public String[] list(@NonNull String str) throws IOException {
        return this.assetManager.list(str);
    }
}
