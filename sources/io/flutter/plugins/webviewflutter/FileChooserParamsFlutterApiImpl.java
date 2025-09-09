package io.flutter.plugins.webviewflutter;

import android.webkit.WebChromeClient;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.Arrays;

@RequiresApi(api = 21)
/* loaded from: classes4.dex */
public class FileChooserParamsFlutterApiImpl extends GeneratedAndroidWebView.FileChooserParamsFlutterApi {
    private final InstanceManager instanceManager;

    public FileChooserParamsFlutterApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        super(binaryMessenger);
        this.instanceManager = instanceManager;
    }

    private static GeneratedAndroidWebView.FileChooserMode toFileChooserEnumData(int i2) {
        if (i2 == 0) {
            return GeneratedAndroidWebView.FileChooserMode.OPEN;
        }
        if (i2 == 1) {
            return GeneratedAndroidWebView.FileChooserMode.OPEN_MULTIPLE;
        }
        if (i2 == 3) {
            return GeneratedAndroidWebView.FileChooserMode.SAVE;
        }
        throw new IllegalArgumentException(String.format("Unsupported FileChooserMode: %d", Integer.valueOf(i2)));
    }

    public void create(@NonNull WebChromeClient.FileChooserParams fileChooserParams, @NonNull GeneratedAndroidWebView.FileChooserParamsFlutterApi.Reply<Void> reply) {
        if (this.instanceManager.containsInstance(fileChooserParams)) {
            return;
        }
        create(Long.valueOf(this.instanceManager.addHostCreatedInstance(fileChooserParams)), Boolean.valueOf(fileChooserParams.isCaptureEnabled()), Arrays.asList(fileChooserParams.getAcceptTypes()), toFileChooserEnumData(fileChooserParams.getMode()), fileChooserParams.getFilenameHint(), reply);
    }
}
