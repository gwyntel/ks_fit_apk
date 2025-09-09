package io.flutter.plugins.webviewflutter;

import android.webkit.WebStorage;
import androidx.annotation.NonNull;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WebStorageHostApiImpl implements GeneratedAndroidWebView.WebStorageHostApi {
    private final InstanceManager instanceManager;
    private final WebStorageCreator webStorageCreator;

    public static class WebStorageCreator {
        @NonNull
        public WebStorage createWebStorage() {
            return WebStorage.getInstance();
        }
    }

    public WebStorageHostApiImpl(@NonNull InstanceManager instanceManager, @NonNull WebStorageCreator webStorageCreator) {
        this.instanceManager = instanceManager;
        this.webStorageCreator = webStorageCreator;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebStorageHostApi
    public void create(@NonNull Long l2) {
        this.instanceManager.addDartCreatedInstance(this.webStorageCreator.createWebStorage(), l2.longValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebStorageHostApi
    public void deleteAllData(@NonNull Long l2) {
        WebStorage webStorage = (WebStorage) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webStorage);
        webStorage.deleteAllData();
    }
}
