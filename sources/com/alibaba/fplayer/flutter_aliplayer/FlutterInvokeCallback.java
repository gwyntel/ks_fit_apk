package com.alibaba.fplayer.flutter_aliplayer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Nullable;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.StringCodec;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class FlutterInvokeCallback {
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());
    private final String TAG = "FlutterInvoke";
    private FlutterPlugin.FlutterPluginBinding flutterPluginBinding;
    private BasicMessageChannel<String> mBasicMessageChannel;
    private Context mContext;

    public FlutterInvokeCallback(Context context, FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.mContext = context;
        this.flutterPluginBinding = flutterPluginBinding;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$invokeFourParameterFlutterCallback$3(String str, BasicMessageChannel.Reply reply) {
        Log.w("FlutterInvoke", "[D->F] setMessageHandler, message: " + str + ", reply: " + reply);
        reply.reply(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$invokeOneParameterFlutterCallback$0(String str, BasicMessageChannel.Reply reply) {
        Log.w("FlutterInvoke", "[D->F] setMessageHandler, message: " + str + ", reply: " + reply);
        reply.reply(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$invokeThreeParameterFlutterCallback$2(String str, BasicMessageChannel.Reply reply) {
        Log.w("FlutterInvoke", "[D->F] setMessageHandler, message: " + str + ", reply: " + reply);
        reply.reply(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$invokeTwoParameterFlutterCallback$1(String str, BasicMessageChannel.Reply reply) {
        Log.w("FlutterInvoke", "[D->F] setMessageHandler, message: " + str + ", reply: " + reply);
        reply.reply(null);
    }

    public Object invokeFourParameterFlutterCallback(final Object obj, final Object obj2, final Object obj3, final Object obj4, String str) {
        BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(this.flutterPluginBinding.getBinaryMessenger(), str, StringCodec.INSTANCE);
        this.mBasicMessageChannel = basicMessageChannel;
        basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.alibaba.fplayer.flutter_aliplayer.e
            @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
            public final void onMessage(Object obj5, BasicMessageChannel.Reply reply) {
                this.f8785a.lambda$invokeFourParameterFlutterCallback$3((String) obj5, reply);
            }
        });
        final Object[] objArr = {null};
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        runOnUiThread(new Runnable() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterInvokeCallback.4
            @Override // java.lang.Runnable
            public void run() {
                FlutterInvokeCallback.this.mBasicMessageChannel.send(String.format("{\"param1\": \"%s\", \"param2\": \"%s\",\"param3\": \"%s\",\"param4\": \"%s\"}", obj, obj2, obj3, obj4), new BasicMessageChannel.Reply<String>() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterInvokeCallback.4.1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                    public void reply(@Nullable String str2) {
                        Log.w("FlutterInvoke", "[D->F] invokeFlutterCallback reply: " + str2);
                        objArr[0] = Boolean.valueOf(Boolean.parseBoolean(str2));
                        countDownLatch.countDown();
                    }
                });
            }
        });
        Log.i("FlutterInvoke", "[F->D] invokeFlutterCallback await response...");
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            try {
                if (!countDownLatch.await(10L, TimeUnit.MILLISECONDS)) {
                    this.mBasicMessageChannel.setMessageHandler(null);
                }
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        } else {
            Log.e("FlutterInvoke", "[F->D] invokeFlutterCallback cannot synchronize and wait for execution on the main thread...");
        }
        Log.i("FlutterInvoke", "[F->D] invokeFlutterCallback with response: " + objArr[0]);
        return objArr[0];
    }

    public Object invokeOneParameterFlutterCallback(final Object obj, String str) {
        BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(this.flutterPluginBinding.getBinaryMessenger(), str, StringCodec.INSTANCE);
        this.mBasicMessageChannel = basicMessageChannel;
        basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.alibaba.fplayer.flutter_aliplayer.b
            @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
            public final void onMessage(Object obj2, BasicMessageChannel.Reply reply) {
                this.f8782a.lambda$invokeOneParameterFlutterCallback$0((String) obj2, reply);
            }
        });
        final Object[] objArr = {null};
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Log.i("FlutterInvoke", "[F->D] invokeFlutterCallback: " + obj);
        runOnUiThread(new Runnable() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterInvokeCallback.1
            @Override // java.lang.Runnable
            public void run() {
                FlutterInvokeCallback.this.mBasicMessageChannel.send(String.format("{\"param1\": \"%s\"}", obj), new BasicMessageChannel.Reply<String>() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterInvokeCallback.1.1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                    public void reply(@Nullable String str2) {
                        Log.w("FlutterInvoke", "[D->F] invokeFlutterCallback reply: " + str2);
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                        objArr[0] = str2;
                        countDownLatch.countDown();
                    }
                });
            }
        });
        Log.i("FlutterInvoke", "[F->D] invokeFlutterCallback await response...");
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            try {
                if (!countDownLatch.await(10L, TimeUnit.MILLISECONDS)) {
                    this.mBasicMessageChannel.setMessageHandler(null);
                }
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        } else {
            Log.e("FlutterInvoke", "[F->D] invokeFlutterCallback cannot synchronize and wait for execution on the main thread...");
        }
        Log.i("FlutterInvoke", "[F->D] invokeFlutterCallback with response: " + objArr[0]);
        return objArr[0];
    }

    public Object invokeThreeParameterFlutterCallback(final Object obj, final Object obj2, final Object obj3, String str) {
        BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(this.flutterPluginBinding.getBinaryMessenger(), str, StringCodec.INSTANCE);
        this.mBasicMessageChannel = basicMessageChannel;
        basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.alibaba.fplayer.flutter_aliplayer.d
            @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
            public final void onMessage(Object obj4, BasicMessageChannel.Reply reply) {
                this.f8784a.lambda$invokeThreeParameterFlutterCallback$2((String) obj4, reply);
            }
        });
        final Object[] objArr = {null};
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        runOnUiThread(new Runnable() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterInvokeCallback.3
            @Override // java.lang.Runnable
            public void run() {
                FlutterInvokeCallback.this.mBasicMessageChannel.send(String.format("{\"param1\": \"%s\", \"param2\": \"%s\",\"param3\": \"%s\"}", obj, obj2, obj3), new BasicMessageChannel.Reply<String>() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterInvokeCallback.3.1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                    public void reply(@Nullable String str2) {
                        Log.w("FlutterInvoke", "[D->F] invokeFlutterCallback reply: " + str2);
                        AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                        objArr[0] = str2;
                        countDownLatch.countDown();
                    }
                });
            }
        });
        Log.i("FlutterInvoke", "[F->D] invokeFlutterCallback await response...");
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            try {
                if (!countDownLatch.await(10L, TimeUnit.MILLISECONDS)) {
                    this.mBasicMessageChannel.setMessageHandler(null);
                }
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        } else {
            Log.e("FlutterInvoke", "[F->D] invokeFlutterCallback cannot synchronize and wait for execution on the main thread...");
        }
        Log.i("FlutterInvoke", "[F->D] invokeFlutterCallback with response: " + objArr[0]);
        return objArr[0];
    }

    public Object invokeTwoParameterFlutterCallback(final Object obj, final Object obj2, String str) {
        BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(this.flutterPluginBinding.getBinaryMessenger(), str, StringCodec.INSTANCE);
        this.mBasicMessageChannel = basicMessageChannel;
        basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.alibaba.fplayer.flutter_aliplayer.c
            @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
            public final void onMessage(Object obj3, BasicMessageChannel.Reply reply) {
                this.f8783a.lambda$invokeTwoParameterFlutterCallback$1((String) obj3, reply);
            }
        });
        final Object[] objArr = {null};
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        runOnUiThread(new Runnable() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterInvokeCallback.2
            @Override // java.lang.Runnable
            public void run() {
                FlutterInvokeCallback.this.mBasicMessageChannel.send(String.format("{\"param1\": \"%s\", \"param2\": \"%s\"}", obj, obj2), new BasicMessageChannel.Reply<String>() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterInvokeCallback.2.1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                    public void reply(@Nullable String str2) {
                        Log.w("FlutterInvoke", "[D->F] invokeFlutterCallback reply: " + str2);
                        AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                        objArr[0] = str2;
                        countDownLatch.countDown();
                    }
                });
            }
        });
        Log.i("FlutterInvoke", "[F->D] invokeFlutterCallback await response...");
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            try {
                if (!countDownLatch.await(10L, TimeUnit.MILLISECONDS)) {
                    this.mBasicMessageChannel.setMessageHandler(null);
                }
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        } else {
            Log.e("FlutterInvoke", "[F->D] invokeFlutterCallback cannot synchronize and wait for execution on the main thread...");
        }
        Log.i("FlutterInvoke", "[F->D] invokeFlutterCallback with response: " + objArr[0]);
        return objArr[0];
    }

    public void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            sMainHandler.post(runnable);
        }
    }
}
