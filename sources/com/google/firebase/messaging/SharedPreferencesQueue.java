package com.google.firebase.messaging;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
final class SharedPreferencesQueue {

    /* renamed from: a, reason: collision with root package name */
    final ArrayDeque f15106a = new ArrayDeque();

    @GuardedBy("internalQueue")
    private boolean bulkOperation = false;
    private final String itemSeparator;
    private final String queueName;
    private final SharedPreferences sharedPreferences;
    private final Executor syncExecutor;

    private SharedPreferencesQueue(SharedPreferences sharedPreferences, String str, String str2, Executor executor) {
        this.sharedPreferences = sharedPreferences;
        this.queueName = str;
        this.itemSeparator = str2;
        this.syncExecutor = executor;
    }

    static SharedPreferencesQueue b(SharedPreferences sharedPreferences, String str, String str2, Executor executor) {
        SharedPreferencesQueue sharedPreferencesQueue = new SharedPreferencesQueue(sharedPreferences, str, str2, executor);
        sharedPreferencesQueue.initQueue();
        return sharedPreferencesQueue;
    }

    @GuardedBy("internalQueue")
    private String checkAndSyncState(String str) {
        checkAndSyncState(str != null);
        return str;
    }

    @WorkerThread
    private void initQueue() {
        synchronized (this.f15106a) {
            try {
                this.f15106a.clear();
                String string = this.sharedPreferences.getString(this.queueName, "");
                if (!TextUtils.isEmpty(string) && string.contains(this.itemSeparator)) {
                    String[] strArrSplit = string.split(this.itemSeparator, -1);
                    if (strArrSplit.length == 0) {
                        Log.e(Constants.TAG, "Corrupted queue. Please check the queue contents and item separator provided");
                    }
                    for (String str : strArrSplit) {
                        if (!TextUtils.isEmpty(str)) {
                            this.f15106a.add(str);
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public void syncState() {
        synchronized (this.f15106a) {
            this.sharedPreferences.edit().putString(this.queueName, serialize()).commit();
        }
    }

    private void syncStateAsync() {
        this.syncExecutor.execute(new Runnable() { // from class: com.google.firebase.messaging.d0
            @Override // java.lang.Runnable
            public final void run() {
                this.f15125a.syncState();
            }
        });
    }

    public boolean add(@NonNull String str) {
        boolean zCheckAndSyncState;
        if (TextUtils.isEmpty(str) || str.contains(this.itemSeparator)) {
            return false;
        }
        synchronized (this.f15106a) {
            zCheckAndSyncState = checkAndSyncState(this.f15106a.add(str));
        }
        return zCheckAndSyncState;
    }

    @GuardedBy("internalQueue")
    public void beginTransaction() {
        this.bulkOperation = true;
    }

    public void clear() {
        synchronized (this.f15106a) {
            this.f15106a.clear();
            checkAndSyncState(true);
        }
    }

    @GuardedBy("internalQueue")
    public void commitTransaction() {
        this.bulkOperation = false;
        syncStateAsync();
    }

    @Nullable
    public String peek() {
        String str;
        synchronized (this.f15106a) {
            str = (String) this.f15106a.peek();
        }
        return str;
    }

    public boolean remove(@Nullable Object obj) {
        boolean zCheckAndSyncState;
        synchronized (this.f15106a) {
            zCheckAndSyncState = checkAndSyncState(this.f15106a.remove(obj));
        }
        return zCheckAndSyncState;
    }

    @NonNull
    @GuardedBy("internalQueue")
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.f15106a.iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
            sb.append(this.itemSeparator);
        }
        return sb.toString();
    }

    @VisibleForTesting
    public String serializeSync() {
        String strSerialize;
        synchronized (this.f15106a) {
            strSerialize = serialize();
        }
        return strSerialize;
    }

    public int size() {
        int size;
        synchronized (this.f15106a) {
            size = this.f15106a.size();
        }
        return size;
    }

    @NonNull
    public List<String> toList() {
        ArrayList arrayList;
        synchronized (this.f15106a) {
            arrayList = new ArrayList(this.f15106a);
        }
        return arrayList;
    }

    @GuardedBy("internalQueue")
    private boolean checkAndSyncState(boolean z2) {
        if (z2 && !this.bulkOperation) {
            syncStateAsync();
        }
        return z2;
    }

    public String remove() {
        String strCheckAndSyncState;
        synchronized (this.f15106a) {
            strCheckAndSyncState = checkAndSyncState((String) this.f15106a.remove());
        }
        return strCheckAndSyncState;
    }
}
