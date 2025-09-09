package io.flutter.plugins.webviewflutter;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class h0 {
    public static MessageCodec a() {
        return new StandardMessageCodec();
    }

    public static /* synthetic */ void b(GeneratedAndroidWebView.JavaScriptChannelHostApi javaScriptChannelHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        String str = (String) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        javaScriptChannelHostApi.create(lValueOf, str);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static void c(BinaryMessenger binaryMessenger, final GeneratedAndroidWebView.JavaScriptChannelHostApi javaScriptChannelHostApi) {
        BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.JavaScriptChannelHostApi.create", a());
        if (javaScriptChannelHostApi != null) {
            basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.g0
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    h0.b(javaScriptChannelHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel.setMessageHandler(null);
        }
    }
}
