package io.flutter.plugins.webviewflutter;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class q {
    public static MessageCodec a() {
        return new StandardMessageCodec();
    }

    public static /* synthetic */ void b(GeneratedAndroidWebView.FlutterAssetManagerHostApi flutterAssetManagerHostApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            arrayList.add(0, flutterAssetManagerHostApi.list((String) ((ArrayList) obj).get(0)));
        } catch (Throwable th) {
            arrayList = GeneratedAndroidWebView.wrapError(th);
        }
        reply.reply(arrayList);
    }

    public static /* synthetic */ void c(GeneratedAndroidWebView.FlutterAssetManagerHostApi flutterAssetManagerHostApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            arrayList.add(0, flutterAssetManagerHostApi.getAssetFilePathByName((String) ((ArrayList) obj).get(0)));
        } catch (Throwable th) {
            arrayList = GeneratedAndroidWebView.wrapError(th);
        }
        reply.reply(arrayList);
    }

    public static void d(BinaryMessenger binaryMessenger, final GeneratedAndroidWebView.FlutterAssetManagerHostApi flutterAssetManagerHostApi) {
        BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.FlutterAssetManagerHostApi.list", a());
        if (flutterAssetManagerHostApi != null) {
            basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.o
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    q.b(flutterAssetManagerHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.FlutterAssetManagerHostApi.getAssetFilePathByName", a());
        if (flutterAssetManagerHostApi != null) {
            basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.p
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    q.c(flutterAssetManagerHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel2.setMessageHandler(null);
        }
    }
}
