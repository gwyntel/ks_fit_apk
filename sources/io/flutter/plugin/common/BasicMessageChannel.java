package io.flutter.plugin.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import io.flutter.Log;
import io.flutter.plugin.common.BinaryMessenger;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class BasicMessageChannel<T> {
    public static final String CHANNEL_BUFFERS_CHANNEL = "dev.flutter/channel-buffers";
    private static final String TAG = "BasicMessageChannel#";

    @NonNull
    private final MessageCodec<T> codec;

    @NonNull
    private final BinaryMessenger messenger;

    @NonNull
    private final String name;

    @Nullable
    private final BinaryMessenger.TaskQueue taskQueue;

    private final class IncomingMessageHandler implements BinaryMessenger.BinaryMessageHandler {
        private final MessageHandler<T> handler;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.flutter.plugin.common.BinaryMessenger.BinaryMessageHandler
        public void onMessage(@Nullable ByteBuffer byteBuffer, @NonNull final BinaryMessenger.BinaryReply binaryReply) {
            try {
                this.handler.onMessage(BasicMessageChannel.this.codec.decodeMessage(byteBuffer), new Reply<T>() { // from class: io.flutter.plugin.common.BasicMessageChannel.IncomingMessageHandler.1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                    public void reply(T t2) {
                        binaryReply.reply(BasicMessageChannel.this.codec.encodeMessage(t2));
                    }
                });
            } catch (RuntimeException e2) {
                Log.e(BasicMessageChannel.TAG + BasicMessageChannel.this.name, "Failed to handle message", e2);
                binaryReply.reply(null);
            }
        }

        private IncomingMessageHandler(@NonNull MessageHandler<T> messageHandler) {
            this.handler = messageHandler;
        }
    }

    private final class IncomingReplyHandler implements BinaryMessenger.BinaryReply {
        private final Reply<T> callback;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.flutter.plugin.common.BinaryMessenger.BinaryReply
        public void reply(@Nullable ByteBuffer byteBuffer) {
            try {
                this.callback.reply(BasicMessageChannel.this.codec.decodeMessage(byteBuffer));
            } catch (RuntimeException e2) {
                Log.e(BasicMessageChannel.TAG + BasicMessageChannel.this.name, "Failed to handle message reply", e2);
            }
        }

        private IncomingReplyHandler(@NonNull Reply<T> reply) {
            this.callback = reply;
        }
    }

    public interface MessageHandler<T> {
        void onMessage(@Nullable T t2, @NonNull Reply<T> reply);
    }

    public interface Reply<T> {
        void reply(@Nullable T t2);
    }

    public BasicMessageChannel(@NonNull BinaryMessenger binaryMessenger, @NonNull String str, @NonNull MessageCodec<T> messageCodec) {
        this(binaryMessenger, str, messageCodec, null);
    }

    private static ByteBuffer packetFromEncodedMessage(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        int iRemaining = byteBuffer.remaining();
        byte[] bArr = new byte[iRemaining];
        byteBuffer.get(bArr);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(iRemaining);
        byteBufferAllocateDirect.put(bArr);
        return byteBufferAllocateDirect;
    }

    public void resizeChannelBuffer(int i2) {
        resizeChannelBuffer(this.messenger, this.name, i2);
    }

    public void send(@Nullable T t2) {
        send(t2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [io.flutter.plugin.common.BinaryMessenger] */
    /* JADX WARN: Type inference failed for: r1v0, types: [io.flutter.plugin.common.BasicMessageChannel$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [io.flutter.plugin.common.BinaryMessenger$BinaryMessageHandler] */
    /* JADX WARN: Type inference failed for: r1v2 */
    @UiThread
    public void setMessageHandler(@Nullable MessageHandler<T> messageHandler) {
        if (this.taskQueue != null) {
            this.messenger.setMessageHandler(this.name, messageHandler != null ? new IncomingMessageHandler(messageHandler) : null, this.taskQueue);
        } else {
            this.messenger.setMessageHandler(this.name, messageHandler != null ? new IncomingMessageHandler(messageHandler) : 0);
        }
    }

    public void setWarnsOnChannelOverflow(boolean z2) {
        setWarnsOnChannelOverflow(this.messenger, this.name, z2);
    }

    public BasicMessageChannel(@NonNull BinaryMessenger binaryMessenger, @NonNull String str, @NonNull MessageCodec<T> messageCodec, BinaryMessenger.TaskQueue taskQueue) {
        this.messenger = binaryMessenger;
        this.name = str;
        this.codec = messageCodec;
        this.taskQueue = taskQueue;
    }

    public static void resizeChannelBuffer(@NonNull BinaryMessenger binaryMessenger, @NonNull String str, int i2) {
        binaryMessenger.send(CHANNEL_BUFFERS_CHANNEL, packetFromEncodedMessage(StandardMethodCodec.INSTANCE.encodeMethodCall(new MethodCall("resize", Arrays.asList(str, Integer.valueOf(i2))))));
    }

    public static void setWarnsOnChannelOverflow(@NonNull BinaryMessenger binaryMessenger, @NonNull String str, boolean z2) {
        binaryMessenger.send(CHANNEL_BUFFERS_CHANNEL, packetFromEncodedMessage(StandardMethodCodec.INSTANCE.encodeMethodCall(new MethodCall("overflow", Arrays.asList(str, Boolean.valueOf(!z2))))));
    }

    @UiThread
    public void send(@Nullable T t2, @Nullable Reply<T> reply) {
        this.messenger.send(this.name, this.codec.encodeMessage(t2), reply != null ? new IncomingReplyHandler(reply) : null);
    }
}
