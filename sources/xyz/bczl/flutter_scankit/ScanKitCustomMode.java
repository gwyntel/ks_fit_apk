package xyz.bczl.flutter_scankit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.ImageDecoder$OnHeaderDecodedListener;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import com.huawei.hms.hmsscankit.OnLightVisibleCallBack;
import com.huawei.hms.hmsscankit.OnResultCallback;
import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.luck.picture.lib.config.SelectMimeType;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class ScanKitCustomMode implements LifecycleEventObserver, OnResultCallback, OnLightVisibleCallBack {
    private static final int EVENT_LIGHT_VISIBLE = 1;
    private static final int EVENT_SCAN_RESULT = 0;
    public static final int REQUEST_CODE_PHOTO = 4371;
    private Activity mActivity;
    private final EventChannel mEvenChannel;
    private EventChannel.EventSink mEvents;
    private RemoteView remoteView;
    private int scanTypes;

    /* renamed from: xyz.bczl.flutter_scankit.ScanKitCustomMode$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f26901a;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            f26901a = iArr;
            try {
                iArr[Lifecycle.Event.ON_CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f26901a[Lifecycle.Event.ON_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f26901a[Lifecycle.Event.ON_RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f26901a[Lifecycle.Event.ON_PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f26901a[Lifecycle.Event.ON_STOP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f26901a[Lifecycle.Event.ON_DESTROY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    ScanKitCustomMode(int i2, BinaryMessenger binaryMessenger, Map map, ActivityPluginBinding activityPluginBinding) {
        ArrayList arrayList;
        this.scanTypes = 0;
        EventChannel eventChannel = new EventChannel(binaryMessenger, "xyz.bczl.scankit/embedded/result/" + i2);
        this.mEvenChannel = eventChannel;
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() { // from class: xyz.bczl.flutter_scankit.ScanKitCustomMode.1
            @Override // io.flutter.plugin.common.EventChannel.StreamHandler
            public void onCancel(Object obj) {
                ScanKitCustomMode.this.mEvents = null;
            }

            @Override // io.flutter.plugin.common.EventChannel.StreamHandler
            public void onListen(Object obj, EventChannel.EventSink eventSink) {
                ScanKitCustomMode.this.mEvents = eventSink;
            }
        });
        activityPluginBinding.addActivityResultListener(new PluginRegistry.ActivityResultListener() { // from class: xyz.bczl.flutter_scankit.s
            @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
            public final boolean onActivityResult(int i3, int i4, Intent intent) {
                return this.f26916a.lambda$new$1(i3, i4, intent);
            }
        });
        this.mActivity = activityPluginBinding.getActivity();
        RemoteView.Builder builder = new RemoteView.Builder();
        builder.setContext(this.mActivity);
        if ((map.get("boundingBox") instanceof ArrayList) && (arrayList = (ArrayList) map.get("boundingBox")) != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            float f2 = displayMetrics.density;
            int iIntValue = (int) (((Integer) arrayList.get(0)).intValue() * f2);
            int iIntValue2 = (int) (((Integer) arrayList.get(1)).intValue() * f2);
            builder.setBoundingBox(new Rect(iIntValue, iIntValue2, ((int) (((Integer) arrayList.get(2)).intValue() * f2)) + iIntValue, ((int) (((Integer) arrayList.get(3)).intValue() * f2)) + iIntValue2));
        }
        int iIntValue3 = ((Integer) map.get("format")).intValue();
        this.scanTypes = iIntValue3;
        int[] arrayFromFlags = ScanKitUtilities.getArrayFromFlags(iIntValue3);
        builder.setFormat(arrayFromFlags[0], Arrays.copyOfRange(arrayFromFlags, 1, arrayFromFlags.length));
        builder.setContinuouslyScan(((Boolean) map.get("continuouslyScan")).booleanValue());
        RemoteView remoteViewBuild = builder.build();
        this.remoteView = remoteViewBuild;
        remoteViewBuild.setOnResultCallback(this);
        this.remoteView.setOnLightVisibleCallback(this);
        ((HiddenLifecycleReference) activityPluginBinding.getLifecycle()).getLifecycle().addObserver(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$1(int i2, int i3, Intent intent) {
        HmsScan hmsScan;
        if (i2 != 4371 || i3 != -1) {
            return false;
        }
        if (this.mEvents == null) {
            return true;
        }
        try {
            Bitmap bitmapDecodeBitmap = Build.VERSION.SDK_INT >= 28 ? ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.mActivity.getContentResolver(), intent.getData()), new ImageDecoder$OnHeaderDecodedListener() { // from class: xyz.bczl.flutter_scankit.q
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    p.a(imageDecoder, true);
                }
            }) : MediaStore.Images.Media.getBitmap(this.mActivity.getContentResolver(), intent.getData());
            HmsScanAnalyzerOptions.Creator creator = new HmsScanAnalyzerOptions.Creator();
            creator.setPhotoMode(true);
            int[] arrayFromFlags = ScanKitUtilities.getArrayFromFlags(this.scanTypes);
            creator.setHmsScanTypes(arrayFromFlags[0], Arrays.copyOfRange(arrayFromFlags, 1, arrayFromFlags.length));
            HmsScan[] hmsScanArrDecodeWithBitmap = ScanUtil.decodeWithBitmap(this.mActivity, bitmapDecodeBitmap, creator.create());
            if (hmsScanArrDecodeWithBitmap == null || hmsScanArrDecodeWithBitmap.length <= 0 || (hmsScan = hmsScanArrDecodeWithBitmap[0]) == null || TextUtils.isEmpty(hmsScan.getOriginalValue())) {
                this.mEvents.success(null);
            } else {
                HmsScan hmsScan2 = hmsScanArrDecodeWithBitmap[0];
                sendEvent(0, new ScanResult(hmsScan2.originalValue, ScanKitUtilities.scanTypeToFlags(hmsScan2.scanType)));
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return true;
    }

    private void sendEvent(int i2, Object obj) {
        if (this.mEvents != null) {
            HashMap map = new HashMap();
            map.put(NotificationCompat.CATEGORY_EVENT, Integer.valueOf(i2));
            if (obj instanceof ScanResult) {
                map.put("value", ((ScanResult) obj).a());
            } else {
                map.put("value", obj);
            }
            this.mEvents.success(map);
        }
    }

    public void dispose() {
        RemoteView remoteView = this.remoteView;
        if (remoteView != null) {
            remoteView.onPause();
            this.remoteView.onStop();
            this.remoteView.onDestroy();
            this.remoteView = null;
        }
    }

    public Boolean getLightStatus() {
        RemoteView remoteView = this.remoteView;
        return remoteView != null ? Boolean.valueOf(remoteView.getLightStatus()) : Boolean.FALSE;
    }

    public View getView() {
        return this.remoteView;
    }

    @Override // com.huawei.hms.hmsscankit.OnResultCallback
    public void onResult(HmsScan[] hmsScanArr) {
        HmsScan hmsScan;
        if (hmsScanArr == null || hmsScanArr.length <= 0 || (hmsScan = hmsScanArr[0]) == null || TextUtils.isEmpty(hmsScan.getOriginalValue())) {
            return;
        }
        HmsScan hmsScan2 = hmsScanArr[0];
        sendEvent(0, new ScanResult(hmsScan2.originalValue, ScanKitUtilities.scanTypeToFlags(hmsScan2.scanType)));
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (this.remoteView == null) {
        }
        switch (AnonymousClass2.f26901a[event.ordinal()]) {
            case 1:
                this.remoteView.onCreate(new Bundle());
                break;
            case 2:
                this.remoteView.onStart();
                break;
            case 3:
                this.remoteView.onResume();
                break;
            case 4:
                this.remoteView.onPause();
                break;
            case 5:
                this.remoteView.onStop();
                break;
            case 6:
                this.remoteView.onDestroy();
                break;
        }
    }

    @Override // com.huawei.hms.hmsscankit.OnLightVisibleCallBack
    public void onVisibleChanged(boolean z2) {
        sendEvent(1, Boolean.valueOf(z2));
    }

    public void pauseContinuouslyScan() {
        RemoteView remoteView = this.remoteView;
        if (remoteView != null) {
            remoteView.pauseContinuouslyScan();
        }
    }

    public void pickPhoto() {
        if (this.mActivity != null) {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Intent intent = new Intent("android.intent.action.PICK", uri);
            intent.setDataAndType(uri, SelectMimeType.SYSTEM_IMAGE);
            this.mActivity.startActivityForResult(intent, 4371);
        }
    }

    public void resumeContinuouslyScan() {
        RemoteView remoteView = this.remoteView;
        if (remoteView != null) {
            remoteView.resumeContinuouslyScan();
        }
    }

    public void switchLight() {
        RemoteView remoteView = this.remoteView;
        if (remoteView != null) {
            remoteView.switchLight();
        }
    }
}
