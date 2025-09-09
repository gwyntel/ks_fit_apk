package io.flutter.plugins.webviewflutter;

import android.webkit.DownloadListener;
import androidx.annotation.NonNull;
import io.flutter.plugins.webviewflutter.DownloadListenerHostApiImpl;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;

/* loaded from: classes4.dex */
public class DownloadListenerHostApiImpl implements GeneratedAndroidWebView.DownloadListenerHostApi {
    private final DownloadListenerCreator downloadListenerCreator;
    private final DownloadListenerFlutterApiImpl flutterApi;
    private final InstanceManager instanceManager;

    public static class DownloadListenerCreator {
        @NonNull
        public DownloadListenerImpl createDownloadListener(@NonNull DownloadListenerFlutterApiImpl downloadListenerFlutterApiImpl) {
            return new DownloadListenerImpl(downloadListenerFlutterApiImpl);
        }
    }

    public static class DownloadListenerImpl implements DownloadListener {
        private final DownloadListenerFlutterApiImpl flutterApi;

        public DownloadListenerImpl(@NonNull DownloadListenerFlutterApiImpl downloadListenerFlutterApiImpl) {
            this.flutterApi = downloadListenerFlutterApiImpl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onDownloadStart$0(Void r02) {
        }

        @Override // android.webkit.DownloadListener
        public void onDownloadStart(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4, long j2) {
            this.flutterApi.onDownloadStart(this, str, str2, str3, str4, j2, new GeneratedAndroidWebView.DownloadListenerFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.c
                @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.DownloadListenerFlutterApi.Reply
                public final void reply(Object obj) {
                    DownloadListenerHostApiImpl.DownloadListenerImpl.lambda$onDownloadStart$0((Void) obj);
                }
            });
        }
    }

    public DownloadListenerHostApiImpl(@NonNull InstanceManager instanceManager, @NonNull DownloadListenerCreator downloadListenerCreator, @NonNull DownloadListenerFlutterApiImpl downloadListenerFlutterApiImpl) {
        this.instanceManager = instanceManager;
        this.downloadListenerCreator = downloadListenerCreator;
        this.flutterApi = downloadListenerFlutterApiImpl;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.DownloadListenerHostApi
    public void create(@NonNull Long l2) {
        this.instanceManager.addDartCreatedInstance(this.downloadListenerCreator.createDownloadListener(this.flutterApi), l2.longValue());
    }
}
