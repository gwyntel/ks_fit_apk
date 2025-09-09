package io.flutter.plugins.pathprovider;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.pathprovider.Messages;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class h {
    public static MessageCodec a() {
        return new StandardMessageCodec();
    }

    public static /* synthetic */ void b(Messages.PathProviderApi pathProviderApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            arrayList.add(0, pathProviderApi.getTemporaryPath());
        } catch (Throwable th) {
            arrayList = Messages.wrapError(th);
        }
        reply.reply(arrayList);
    }

    public static /* synthetic */ void c(Messages.PathProviderApi pathProviderApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            arrayList.add(0, pathProviderApi.getApplicationSupportPath());
        } catch (Throwable th) {
            arrayList = Messages.wrapError(th);
        }
        reply.reply(arrayList);
    }

    public static /* synthetic */ void d(Messages.PathProviderApi pathProviderApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            arrayList.add(0, pathProviderApi.getApplicationDocumentsPath());
        } catch (Throwable th) {
            arrayList = Messages.wrapError(th);
        }
        reply.reply(arrayList);
    }

    public static /* synthetic */ void e(Messages.PathProviderApi pathProviderApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            arrayList.add(0, pathProviderApi.getApplicationCachePath());
        } catch (Throwable th) {
            arrayList = Messages.wrapError(th);
        }
        reply.reply(arrayList);
    }

    public static /* synthetic */ void f(Messages.PathProviderApi pathProviderApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            arrayList.add(0, pathProviderApi.getExternalStoragePath());
        } catch (Throwable th) {
            arrayList = Messages.wrapError(th);
        }
        reply.reply(arrayList);
    }

    public static /* synthetic */ void g(Messages.PathProviderApi pathProviderApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            arrayList.add(0, pathProviderApi.getExternalCachePaths());
        } catch (Throwable th) {
            arrayList = Messages.wrapError(th);
        }
        reply.reply(arrayList);
    }

    public static /* synthetic */ void h(Messages.PathProviderApi pathProviderApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        try {
            arrayList.add(0, pathProviderApi.getExternalStoragePaths(arrayList2.get(0) == null ? null : Messages.StorageDirectory.values()[((Integer) arrayList2.get(0)).intValue()]));
        } catch (Throwable th) {
            arrayList = Messages.wrapError(th);
        }
        reply.reply(arrayList);
    }

    public static void i(BinaryMessenger binaryMessenger, final Messages.PathProviderApi pathProviderApi) {
        BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.PathProviderApi.getTemporaryPath", a(), binaryMessenger.makeBackgroundTaskQueue());
        if (pathProviderApi != null) {
            basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.pathprovider.a
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    h.b(pathProviderApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.PathProviderApi.getApplicationSupportPath", a(), binaryMessenger.makeBackgroundTaskQueue());
        if (pathProviderApi != null) {
            basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.pathprovider.b
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    h.c(pathProviderApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel2.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.PathProviderApi.getApplicationDocumentsPath", a(), binaryMessenger.makeBackgroundTaskQueue());
        if (pathProviderApi != null) {
            basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.pathprovider.c
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    h.d(pathProviderApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel3.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.PathProviderApi.getApplicationCachePath", a(), binaryMessenger.makeBackgroundTaskQueue());
        if (pathProviderApi != null) {
            basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.pathprovider.d
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    h.e(pathProviderApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel4.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel5 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.PathProviderApi.getExternalStoragePath", a(), binaryMessenger.makeBackgroundTaskQueue());
        if (pathProviderApi != null) {
            basicMessageChannel5.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.pathprovider.e
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    h.f(pathProviderApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel5.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel6 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.PathProviderApi.getExternalCachePaths", a(), binaryMessenger.makeBackgroundTaskQueue());
        if (pathProviderApi != null) {
            basicMessageChannel6.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.pathprovider.f
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    h.g(pathProviderApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel6.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel7 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.PathProviderApi.getExternalStoragePaths", a(), binaryMessenger.makeBackgroundTaskQueue());
        if (pathProviderApi != null) {
            basicMessageChannel7.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.pathprovider.g
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    h.h(pathProviderApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel7.setMessageHandler(null);
        }
    }
}
