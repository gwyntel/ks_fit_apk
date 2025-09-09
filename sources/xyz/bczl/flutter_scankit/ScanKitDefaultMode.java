package xyz.bczl.flutter_scankit;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.util.Arrays;
import java.util.Map;
import org.android.agoo.message.MessageService;
import xyz.bczl.flutter_scankit.ScanKitAPI;

/* loaded from: classes5.dex */
public class ScanKitDefaultMode {
    private final int id;
    private final Activity mActivity;
    private EventChannel.EventSink mEvents;
    private final EventChannel mResultChannel;

    ScanKitDefaultMode(final int i2, ActivityPluginBinding activityPluginBinding, BinaryMessenger binaryMessenger) {
        this.id = i2;
        this.mActivity = activityPluginBinding.getActivity();
        EventChannel eventChannel = new EventChannel(binaryMessenger, "xyz.bczl.scankit/result/" + i2);
        this.mResultChannel = eventChannel;
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() { // from class: xyz.bczl.flutter_scankit.ScanKitDefaultMode.1
            @Override // io.flutter.plugin.common.EventChannel.StreamHandler
            public void onCancel(Object obj) {
                ScanKitDefaultMode.this.mEvents = null;
            }

            @Override // io.flutter.plugin.common.EventChannel.StreamHandler
            public void onListen(Object obj, EventChannel.EventSink eventSink) {
                ScanKitDefaultMode.this.mEvents = eventSink;
            }
        });
        activityPluginBinding.addActivityResultListener(new PluginRegistry.ActivityResultListener() { // from class: xyz.bczl.flutter_scankit.t
            @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
            public final boolean onActivityResult(int i3, int i4, Intent intent) {
                return this.f26917a.lambda$new$0(i2, i3, i4, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(int i2, int i3, int i4, Intent intent) {
        Log.d("ActivityResult", "requestCode=" + i3 + "  resultCode=" + i4);
        if (i3 != i2) {
            return false;
        }
        if (i4 != -1 || intent == null) {
            e(null);
            return true;
        }
        int intExtra = intent.getIntExtra(ScanUtil.RESULT_CODE, 0);
        Log.d("ActivityResult", "errorCode=" + intExtra);
        if (intExtra == 0) {
            HmsScan hmsScan = (HmsScan) intent.getParcelableExtra(ScanUtil.RESULT);
            if (hmsScan != null) {
                e(new ScanResult(hmsScan.originalValue, hmsScan.scanType));
            } else {
                d("101", "[onActivityResult]: scan_result is null");
            }
        } else {
            d("" + intExtra, "[onActivityResult]:scan error");
        }
        return true;
    }

    void c() {
    }

    void d(String str, String str2) {
        EventChannel.EventSink eventSink = this.mEvents;
        if (eventSink != null) {
            eventSink.error(str, str2, null);
        }
    }

    void e(ScanResult scanResult) {
        EventChannel.EventSink eventSink = this.mEvents;
        if (eventSink != null) {
            if (scanResult != null) {
                eventSink.success(scanResult.a());
            } else {
                eventSink.success(null);
            }
        }
    }

    public int startScan(@NonNull Long l2, @NonNull Map<String, Object> map) {
        if (this.mActivity == null) {
            throw new ScanKitAPI.FlutterError(MessageService.MSG_DB_COMPLETE, "Activity is null", null);
        }
        int[] arrayFromFlags = ScanKitUtilities.getArrayFromFlags(l2.intValue());
        int i2 = arrayFromFlags[0];
        int[] iArrCopyOfRange = Arrays.copyOfRange(arrayFromFlags, 1, arrayFromFlags.length);
        Object obj = map.get("errorCheck");
        Object obj2 = map.get("photoMode");
        Object obj3 = map.get("viewType");
        HmsScanAnalyzerOptions.Creator creator = new HmsScanAnalyzerOptions.Creator();
        creator.setHmsScanTypes(i2, iArrCopyOfRange);
        if (obj != null) {
            creator.setErrorCheck(((Boolean) obj).booleanValue());
        }
        if (obj2 != null) {
            creator.setPhotoMode(((Boolean) obj2).booleanValue());
        }
        if (obj3 != null) {
            creator.setViewType(((Integer) obj3).intValue());
        }
        return ScanUtil.startScan(this.mActivity, this.id, creator.create());
    }
}
