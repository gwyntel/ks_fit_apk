package com.google.firebase.messaging;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.GuardedBy;
import androidx.annotation.WorkerThread;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
final class TopicsStore {
    private static final String DIVIDER_QUEUE_OPERATIONS = ",";

    @GuardedBy("TopicsStore.class")
    private static WeakReference<TopicsStore> topicsStoreWeakReference;
    private final SharedPreferences sharedPreferences;
    private final Executor syncExecutor;
    private SharedPreferencesQueue topicOperationsQueue;

    private TopicsStore(SharedPreferences sharedPreferences, Executor executor) {
        this.syncExecutor = executor;
        this.sharedPreferences = sharedPreferences;
    }

    @WorkerThread
    public static synchronized TopicsStore getInstance(Context context, Executor executor) {
        TopicsStore topicsStore;
        try {
            WeakReference<TopicsStore> weakReference = topicsStoreWeakReference;
            topicsStore = weakReference != null ? weakReference.get() : null;
            if (topicsStore == null) {
                topicsStore = new TopicsStore(context.getSharedPreferences("com.google.android.gms.appid", 0), executor);
                topicsStore.initStore();
                topicsStoreWeakReference = new WeakReference<>(topicsStore);
            }
        } catch (Throwable th) {
            throw th;
        }
        return topicsStore;
    }

    @WorkerThread
    private synchronized void initStore() {
        this.topicOperationsQueue = SharedPreferencesQueue.b(this.sharedPreferences, "topic_operation_queue", ",", this.syncExecutor);
    }

    synchronized boolean a(TopicOperation topicOperation) {
        return this.topicOperationsQueue.add(topicOperation.serialize());
    }

    synchronized TopicOperation b() {
        return TopicOperation.a(this.topicOperationsQueue.peek());
    }

    synchronized boolean c(TopicOperation topicOperation) {
        return this.topicOperationsQueue.remove(topicOperation.serialize());
    }
}
