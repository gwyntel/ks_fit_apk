package io.flutter.plugins.webviewflutter;

import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import androidx.annotation.NonNull;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;

/* loaded from: classes4.dex */
public class JavaScriptChannel {
    private final JavaScriptChannelFlutterApiImpl flutterApi;
    final String javaScriptChannelName;
    private final Handler platformThreadHandler;

    public JavaScriptChannel(@NonNull JavaScriptChannelFlutterApiImpl javaScriptChannelFlutterApiImpl, @NonNull String str, @NonNull Handler handler) {
        this.flutterApi = javaScriptChannelFlutterApiImpl;
        this.javaScriptChannelName = str;
        this.platformThreadHandler = handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postMessage$0(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postMessage$1(String str) {
        this.flutterApi.postMessage(this, str, new GeneratedAndroidWebView.JavaScriptChannelFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.n3
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.JavaScriptChannelFlutterApi.Reply
            public final void reply(Object obj) {
                JavaScriptChannel.lambda$postMessage$0((Void) obj);
            }
        });
    }

    @JavascriptInterface
    public void postMessage(@NonNull final String str) {
        Runnable runnable = new Runnable() { // from class: io.flutter.plugins.webviewflutter.o3
            @Override // java.lang.Runnable
            public final void run() {
                this.f25264a.lambda$postMessage$1(str);
            }
        };
        if (this.platformThreadHandler.getLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            this.platformThreadHandler.post(runnable);
        }
    }
}
