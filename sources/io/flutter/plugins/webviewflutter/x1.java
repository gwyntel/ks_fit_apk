package io.flutter.plugins.webviewflutter;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class x1 {
    public static MessageCodec a() {
        return new StandardMessageCodec();
    }

    public static /* synthetic */ void b(GeneratedAndroidWebView.WebStorageHostApi webStorageHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webStorageHostApi.create(lValueOf);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void c(GeneratedAndroidWebView.WebStorageHostApi webStorageHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webStorageHostApi.deleteAllData(lValueOf);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static void d(BinaryMessenger binaryMessenger, final GeneratedAndroidWebView.WebStorageHostApi webStorageHostApi) {
        BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebStorageHostApi.create", a());
        if (webStorageHostApi != null) {
            basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.v1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    x1.b(webStorageHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebStorageHostApi.deleteAllData", a());
        if (webStorageHostApi != null) {
            basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.w1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    x1.c(webStorageHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel2.setMessageHandler(null);
        }
    }
}
