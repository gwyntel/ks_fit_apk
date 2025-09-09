package xyz.bczl.flutter_scankit;

import android.app.Activity;
import android.util.LongSparseArray;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.android.agoo.message.MessageService;
import xyz.bczl.flutter_scankit.ScanKitAPI;

/* loaded from: classes5.dex */
public class FlutterScanKitPlugin implements FlutterPlugin, ScanKitAPI.ScanKitApi, ActivityAware {
    private Activity mActivity;
    private ActivityPluginBinding mBinding;
    private FlutterPlugin.FlutterPluginBinding mPluginBinding;
    private final AtomicLong defaultModeId = new AtomicLong(0);
    private final LongSparseArray<ScanKitDefaultMode> defaultModeList = new LongSparseArray<>();
    private final LongSparseArray<ScanKitCustomMode> customModeList = new LongSparseArray<>();

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    @NonNull
    public Long createDefaultMode() {
        long jIncrementAndGet = this.defaultModeId.incrementAndGet();
        this.defaultModeList.put(jIncrementAndGet, new ScanKitDefaultMode((int) jIncrementAndGet, this.mBinding, this.mPluginBinding.getBinaryMessenger()));
        return Long.valueOf(jIncrementAndGet);
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    @NonNull
    public Map<String, Object> decode(@NonNull byte[] bArr, @NonNull Long l2, @NonNull Long l3, @NonNull Map<String, Object> map) {
        throw new ScanKitAPI.FlutterError("notImplemented", "[decode] Method not implemented!", "");
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    @NonNull
    public Map<String, Object> decodeBitmap(@NonNull byte[] bArr, @NonNull Map<String, Object> map) {
        Activity activity = this.mActivity;
        if (activity != null) {
            return ScanKitBitmapMode.decodeBitmap(activity, bArr, map);
        }
        throw new ScanKitAPI.FlutterError("102", "decode: Activity is null!", "");
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    @NonNull
    public Map<String, Object> decodeYUV(@NonNull byte[] bArr, @NonNull Long l2, @NonNull Long l3, @NonNull Map<String, Object> map) {
        Activity activity = this.mActivity;
        if (activity != null) {
            return ScanKitBitmapMode.decode(activity, bArr, l2, l3, map);
        }
        throw new ScanKitAPI.FlutterError("102", "decode: Activity is null!", "");
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    public void disposeCustomizedMode(@NonNull Long l2) {
        ScanKitCustomMode scanKitCustomMode = this.customModeList.get(l2.longValue());
        if (scanKitCustomMode != null) {
            this.customModeList.remove(l2.longValue());
            scanKitCustomMode.dispose();
        }
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    public void disposeDefaultMode(@NonNull Long l2) {
        ScanKitDefaultMode scanKitDefaultMode = this.defaultModeList.get(l2.longValue());
        if (scanKitDefaultMode != null) {
            this.customModeList.remove(l2.longValue());
            scanKitDefaultMode.c();
        }
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    @NonNull
    public byte[] encode(@NonNull String str, @NonNull Long l2, @NonNull Long l3, @NonNull Map<String, Object> map) {
        return ScanKitBitmapMode.encode(str, l2, l3, map);
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    @NonNull
    public Boolean getLightStatus(@NonNull Long l2) {
        ScanKitCustomMode scanKitCustomMode = this.customModeList.get(l2.longValue());
        return scanKitCustomMode != null ? scanKitCustomMode.getLightStatus() : Boolean.FALSE;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        this.mBinding = activityPluginBinding;
        this.mActivity = activityPluginBinding.getActivity();
        FlutterPlugin.FlutterPluginBinding flutterPluginBinding = this.mPluginBinding;
        if (flutterPluginBinding != null) {
            flutterPluginBinding.getPlatformViewRegistry().registerViewFactory("ScanKitWidgetType", new ScanKitViewFactory(this.customModeList, this.mPluginBinding.getBinaryMessenger(), activityPluginBinding));
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        n.o(flutterPluginBinding.getBinaryMessenger(), this);
        this.mPluginBinding = flutterPluginBinding;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.mActivity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        this.mActivity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        n.o(flutterPluginBinding.getBinaryMessenger(), null);
        this.defaultModeList.clear();
        this.customModeList.clear();
        this.mPluginBinding = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    public void pauseContinuouslyScan(@NonNull Long l2) {
        ScanKitCustomMode scanKitCustomMode = this.customModeList.get(l2.longValue());
        if (scanKitCustomMode != null) {
            scanKitCustomMode.pauseContinuouslyScan();
        }
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    public void pickPhoto(@NonNull Long l2) {
        ScanKitCustomMode scanKitCustomMode = this.customModeList.get(l2.longValue());
        if (scanKitCustomMode != null) {
            scanKitCustomMode.pickPhoto();
        }
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    public void resumeContinuouslyScan(@NonNull Long l2) {
        ScanKitCustomMode scanKitCustomMode = this.customModeList.get(l2.longValue());
        if (scanKitCustomMode != null) {
            scanKitCustomMode.resumeContinuouslyScan();
        }
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    @NonNull
    public Long startScan(@NonNull Long l2, @NonNull Long l3, @NonNull Map<String, Object> map) {
        if (this.defaultModeList.get(l2.longValue()) != null) {
            return Long.valueOf(r4.startScan(l3, map));
        }
        throw new ScanKitAPI.FlutterError(MessageService.MSG_DB_COMPLETE, "ScanKitDefaultMode does not exist, please check if it was initialized successfully!", null);
    }

    @Override // xyz.bczl.flutter_scankit.ScanKitAPI.ScanKitApi
    public void switchLight(@NonNull Long l2) {
        ScanKitCustomMode scanKitCustomMode = this.customModeList.get(l2.longValue());
        if (scanKitCustomMode != null) {
            scanKitCustomMode.switchLight();
        }
    }
}
