package io.flutter.embedding.engine.dart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.util.TraceSection;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
class DartMessenger implements BinaryMessenger, PlatformMessageHandler {
    private static final String TAG = "DartMessenger";

    @NonNull
    private Map<String, List<BufferedMessageInfo>> bufferedMessages;

    @NonNull
    private WeakHashMap<BinaryMessenger.TaskQueue, DartMessengerTaskQueue> createdTaskQueues;

    @NonNull
    private final AtomicBoolean enableBufferingIncomingMessages;

    @NonNull
    private final FlutterJNI flutterJNI;

    @NonNull
    private final Object handlersLock;

    @NonNull
    private final Map<String, HandlerInfo> messageHandlers;
    private int nextReplyId;

    @NonNull
    private final Map<Integer, BinaryMessenger.BinaryReply> pendingReplies;

    @NonNull
    private final DartMessengerTaskQueue platformTaskQueue;

    @NonNull
    private TaskQueueFactory taskQueueFactory;

    private static class BufferedMessageInfo {

        @NonNull
        public final ByteBuffer message;
        long messageData;
        int replyId;

        BufferedMessageInfo(@NonNull ByteBuffer byteBuffer, int i2, long j2) {
            this.message = byteBuffer;
            this.replyId = i2;
            this.messageData = j2;
        }
    }

    static class ConcurrentTaskQueue implements DartMessengerTaskQueue {

        @NonNull
        private final ExecutorService executor;

        ConcurrentTaskQueue(ExecutorService executorService) {
            this.executor = executorService;
        }

        @Override // io.flutter.embedding.engine.dart.DartMessenger.DartMessengerTaskQueue
        public void dispatch(@NonNull Runnable runnable) {
            this.executor.execute(runnable);
        }
    }

    interface DartMessengerTaskQueue {
        void dispatch(@NonNull Runnable runnable);
    }

    private static class DefaultTaskQueueFactory implements TaskQueueFactory {
        ExecutorService executorService = FlutterInjector.instance().executorService();

        DefaultTaskQueueFactory() {
        }

        @Override // io.flutter.embedding.engine.dart.DartMessenger.TaskQueueFactory
        public DartMessengerTaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions taskQueueOptions) {
            return taskQueueOptions.getIsSerial() ? new SerialTaskQueue(this.executorService) : new ConcurrentTaskQueue(this.executorService);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class HandlerInfo {

        @NonNull
        public final BinaryMessenger.BinaryMessageHandler handler;

        @Nullable
        public final DartMessengerTaskQueue taskQueue;

        HandlerInfo(@NonNull BinaryMessenger.BinaryMessageHandler binaryMessageHandler, @Nullable DartMessengerTaskQueue dartMessengerTaskQueue) {
            this.handler = binaryMessageHandler;
            this.taskQueue = dartMessengerTaskQueue;
        }
    }

    static class Reply implements BinaryMessenger.BinaryReply {
        private final AtomicBoolean done = new AtomicBoolean(false);

        @NonNull
        private final FlutterJNI flutterJNI;
        private final int replyId;

        Reply(@NonNull FlutterJNI flutterJNI, int i2) {
            this.flutterJNI = flutterJNI;
            this.replyId = i2;
        }

        @Override // io.flutter.plugin.common.BinaryMessenger.BinaryReply
        public void reply(@Nullable ByteBuffer byteBuffer) {
            if (this.done.getAndSet(true)) {
                throw new IllegalStateException("Reply already submitted");
            }
            if (byteBuffer == null) {
                this.flutterJNI.invokePlatformMessageEmptyResponseCallback(this.replyId);
            } else {
                this.flutterJNI.invokePlatformMessageResponseCallback(this.replyId, byteBuffer, byteBuffer.position());
            }
        }
    }

    static class SerialTaskQueue implements DartMessengerTaskQueue {

        @NonNull
        private final ExecutorService executor;

        @NonNull
        private final ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        @NonNull
        private final AtomicBoolean isRunning = new AtomicBoolean(false);

        SerialTaskQueue(ExecutorService executorService) {
            this.executor = executorService;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: flush, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
        public void lambda$flush$1() {
            if (this.isRunning.compareAndSet(false, true)) {
                try {
                    Runnable runnablePoll = this.queue.poll();
                    if (runnablePoll != null) {
                        runnablePoll.run();
                    }
                } finally {
                    this.isRunning.set(false);
                    if (!this.queue.isEmpty()) {
                        this.executor.execute(new Runnable() { // from class: io.flutter.embedding.engine.dart.c
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f25057a.lambda$flush$1();
                            }
                        });
                    }
                }
            }
        }

        @Override // io.flutter.embedding.engine.dart.DartMessenger.DartMessengerTaskQueue
        public void dispatch(@NonNull Runnable runnable) {
            this.queue.add(runnable);
            this.executor.execute(new Runnable() { // from class: io.flutter.embedding.engine.dart.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25056a.lambda$dispatch$0();
                }
            });
        }
    }

    interface TaskQueueFactory {
        DartMessengerTaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions taskQueueOptions);
    }

    private static class TaskQueueToken implements BinaryMessenger.TaskQueue {
        private TaskQueueToken() {
        }
    }

    DartMessenger(@NonNull FlutterJNI flutterJNI, @NonNull TaskQueueFactory taskQueueFactory) {
        this.messageHandlers = new HashMap();
        this.bufferedMessages = new HashMap();
        this.handlersLock = new Object();
        this.enableBufferingIncomingMessages = new AtomicBoolean(false);
        this.pendingReplies = new HashMap();
        this.nextReplyId = 1;
        this.platformTaskQueue = new PlatformTaskQueue();
        this.createdTaskQueues = new WeakHashMap<>();
        this.flutterJNI = flutterJNI;
        this.taskQueueFactory = taskQueueFactory;
    }

    private void dispatchMessageToQueue(@NonNull final String str, @Nullable final HandlerInfo handlerInfo, @Nullable final ByteBuffer byteBuffer, final int i2, final long j2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DartMessengerTaskQueue dartMessengerTaskQueue = handlerInfo != null ? handlerInfo.taskQueue : null;
        TraceSection.beginAsyncSection("PlatformChannel ScheduleHandler on " + str, i2);
        Runnable runnable = new Runnable() { // from class: io.flutter.embedding.engine.dart.a
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                this.f25050a.lambda$dispatchMessageToQueue$0(str, i2, handlerInfo, byteBuffer, j2);
            }
        };
        if (dartMessengerTaskQueue == null) {
            dartMessengerTaskQueue = this.platformTaskQueue;
        }
        dartMessengerTaskQueue.dispatch(runnable);
    }

    private static void handleError(Error error) {
        Thread threadCurrentThread = Thread.currentThread();
        if (threadCurrentThread.getUncaughtExceptionHandler() == null) {
            throw error;
        }
        threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, error);
    }

    private void invokeHandler(@Nullable HandlerInfo handlerInfo, @Nullable ByteBuffer byteBuffer, int i2) {
        if (handlerInfo == null) {
            Log.v(TAG, "No registered handler for message. Responding to Dart with empty reply message.");
            this.flutterJNI.invokePlatformMessageEmptyResponseCallback(i2);
            return;
        }
        try {
            Log.v(TAG, "Deferring to registered handler to process message.");
            handlerInfo.handler.onMessage(byteBuffer, new Reply(this.flutterJNI, i2));
        } catch (Error e2) {
            handleError(e2);
        } catch (Exception e3) {
            Log.e(TAG, "Uncaught exception in binary message listener", e3);
            this.flutterJNI.invokePlatformMessageEmptyResponseCallback(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchMessageToQueue$0(String str, int i2, HandlerInfo handlerInfo, ByteBuffer byteBuffer, long j2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TraceSection.endAsyncSection("PlatformChannel ScheduleHandler on " + str, i2);
        try {
            TraceSection traceSectionScoped = TraceSection.scoped("DartMessenger#handleMessageFromDart on " + str);
            try {
                invokeHandler(handlerInfo, byteBuffer, i2);
                if (byteBuffer != null && byteBuffer.isDirect()) {
                    byteBuffer.limit(0);
                }
                if (traceSectionScoped != null) {
                    traceSectionScoped.close();
                }
            } finally {
            }
        } finally {
            this.flutterJNI.cleanupMessageData(j2);
        }
    }

    @Override // io.flutter.plugin.common.BinaryMessenger
    public void disableBufferingIncomingMessages() {
        Map<String, List<BufferedMessageInfo>> map;
        synchronized (this.handlersLock) {
            this.enableBufferingIncomingMessages.set(false);
            map = this.bufferedMessages;
            this.bufferedMessages = new HashMap();
        }
        for (Map.Entry<String, List<BufferedMessageInfo>> entry : map.entrySet()) {
            for (BufferedMessageInfo bufferedMessageInfo : entry.getValue()) {
                dispatchMessageToQueue(entry.getKey(), null, bufferedMessageInfo.message, bufferedMessageInfo.replyId, bufferedMessageInfo.messageData);
            }
        }
    }

    @Override // io.flutter.plugin.common.BinaryMessenger
    public void enableBufferingIncomingMessages() {
        this.enableBufferingIncomingMessages.set(true);
    }

    @UiThread
    public int getPendingChannelResponseCount() {
        return this.pendingReplies.size();
    }

    @Override // io.flutter.embedding.engine.dart.PlatformMessageHandler
    public void handleMessageFromDart(@NonNull String str, @Nullable ByteBuffer byteBuffer, int i2, long j2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HandlerInfo handlerInfo;
        boolean z2;
        Log.v(TAG, "Received message from Dart over channel '" + str + "'");
        synchronized (this.handlersLock) {
            try {
                handlerInfo = this.messageHandlers.get(str);
                z2 = this.enableBufferingIncomingMessages.get() && handlerInfo == null;
                if (z2) {
                    if (!this.bufferedMessages.containsKey(str)) {
                        this.bufferedMessages.put(str, new LinkedList());
                    }
                    this.bufferedMessages.get(str).add(new BufferedMessageInfo(byteBuffer, i2, j2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2) {
            return;
        }
        dispatchMessageToQueue(str, handlerInfo, byteBuffer, i2, j2);
    }

    @Override // io.flutter.embedding.engine.dart.PlatformMessageHandler
    public void handlePlatformMessageResponse(int i2, @Nullable ByteBuffer byteBuffer) {
        Log.v(TAG, "Received message reply from Dart.");
        BinaryMessenger.BinaryReply binaryReplyRemove = this.pendingReplies.remove(Integer.valueOf(i2));
        if (binaryReplyRemove != null) {
            try {
                Log.v(TAG, "Invoking registered callback for reply from Dart.");
                binaryReplyRemove.reply(byteBuffer);
                if (byteBuffer == null || !byteBuffer.isDirect()) {
                    return;
                }
                byteBuffer.limit(0);
            } catch (Error e2) {
                handleError(e2);
            } catch (Exception e3) {
                Log.e(TAG, "Uncaught exception in binary message reply handler", e3);
            }
        }
    }

    @Override // io.flutter.plugin.common.BinaryMessenger
    public /* synthetic */ BinaryMessenger.TaskQueue makeBackgroundTaskQueue() {
        return io.flutter.plugin.common.a.c(this);
    }

    @Override // io.flutter.plugin.common.BinaryMessenger
    @UiThread
    public void send(@NonNull String str, @NonNull ByteBuffer byteBuffer) {
        Log.v(TAG, "Sending message over channel '" + str + "'");
        send(str, byteBuffer, null);
    }

    @Override // io.flutter.plugin.common.BinaryMessenger
    public void setMessageHandler(@NonNull String str, @Nullable BinaryMessenger.BinaryMessageHandler binaryMessageHandler) {
        setMessageHandler(str, binaryMessageHandler, null);
    }

    @Override // io.flutter.plugin.common.BinaryMessenger
    public BinaryMessenger.TaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions taskQueueOptions) {
        DartMessengerTaskQueue dartMessengerTaskQueueMakeBackgroundTaskQueue = this.taskQueueFactory.makeBackgroundTaskQueue(taskQueueOptions);
        TaskQueueToken taskQueueToken = new TaskQueueToken();
        this.createdTaskQueues.put(taskQueueToken, dartMessengerTaskQueueMakeBackgroundTaskQueue);
        return taskQueueToken;
    }

    @Override // io.flutter.plugin.common.BinaryMessenger
    public void setMessageHandler(@NonNull String str, @Nullable BinaryMessenger.BinaryMessageHandler binaryMessageHandler, @Nullable BinaryMessenger.TaskQueue taskQueue) {
        DartMessengerTaskQueue dartMessengerTaskQueue;
        if (binaryMessageHandler == null) {
            Log.v(TAG, "Removing handler for channel '" + str + "'");
            synchronized (this.handlersLock) {
                this.messageHandlers.remove(str);
            }
            return;
        }
        if (taskQueue != null) {
            dartMessengerTaskQueue = this.createdTaskQueues.get(taskQueue);
            if (dartMessengerTaskQueue == null) {
                throw new IllegalArgumentException("Unrecognized TaskQueue, use BinaryMessenger to create your TaskQueue (ex makeBackgroundTaskQueue).");
            }
        } else {
            dartMessengerTaskQueue = null;
        }
        Log.v(TAG, "Setting handler for channel '" + str + "'");
        synchronized (this.handlersLock) {
            try {
                this.messageHandlers.put(str, new HandlerInfo(binaryMessageHandler, dartMessengerTaskQueue));
                List<BufferedMessageInfo> listRemove = this.bufferedMessages.remove(str);
                if (listRemove == null) {
                    return;
                }
                for (BufferedMessageInfo bufferedMessageInfo : listRemove) {
                    dispatchMessageToQueue(str, this.messageHandlers.get(str), bufferedMessageInfo.message, bufferedMessageInfo.replyId, bufferedMessageInfo.messageData);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // io.flutter.plugin.common.BinaryMessenger
    public void send(@NonNull String str, @Nullable ByteBuffer byteBuffer, @Nullable BinaryMessenger.BinaryReply binaryReply) {
        TraceSection traceSectionScoped = TraceSection.scoped("DartMessenger#send on " + str);
        try {
            Log.v(TAG, "Sending message with callback over channel '" + str + "'");
            int i2 = this.nextReplyId;
            this.nextReplyId = i2 + 1;
            if (binaryReply != null) {
                this.pendingReplies.put(Integer.valueOf(i2), binaryReply);
            }
            if (byteBuffer == null) {
                this.flutterJNI.dispatchEmptyPlatformMessage(str, i2);
            } else {
                this.flutterJNI.dispatchPlatformMessage(str, byteBuffer, byteBuffer.position(), i2);
            }
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    DartMessenger(@NonNull FlutterJNI flutterJNI) {
        this(flutterJNI, new DefaultTaskQueueFactory());
    }
}
