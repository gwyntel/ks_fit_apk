package com.huawei.agconnect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import com.alipay.sdk.m.u.i;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* loaded from: classes3.dex */
public class LocalBrdMnger {
    private static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final Object M_LOCK = new Object();
    private static LocalBrdMnger mInstance;
    private final Context mAppContext;
    private final Handler mHandler;
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();

    private static class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent intent, ArrayList<ReceiverRecord> arrayList) {
            this.intent = intent;
            this.receivers = arrayList;
        }
    }

    private static class ReceiverRecord {
        boolean broadcasting;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public String toString() {
            return "Receiver{" + this.receiver + " filter=" + this.filter + i.f9804d;
        }
    }

    private LocalBrdMnger(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) { // from class: com.huawei.agconnect.LocalBrdMnger.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    LocalBrdMnger.this.executePendingBroadcasts();
                } else {
                    super.handleMessage(message);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executePendingBroadcasts() {
        int size;
        BroadcastRecord[] broadcastRecordArr;
        while (true) {
            synchronized (this.mReceivers) {
                try {
                    size = this.mPendingBroadcasts.size();
                    if (size <= 0) {
                        return;
                    }
                    broadcastRecordArr = new BroadcastRecord[size];
                    this.mPendingBroadcasts.toArray(broadcastRecordArr);
                    this.mPendingBroadcasts.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
            for (int i2 = 0; i2 < size; i2++) {
                BroadcastRecord broadcastRecord = broadcastRecordArr[i2];
                for (int i3 = 0; i3 < broadcastRecord.receivers.size(); i3++) {
                    broadcastRecord.receivers.get(i3).receiver.onReceive(this.mAppContext, broadcastRecord.intent);
                }
            }
        }
    }

    public static LocalBrdMnger getInstance(Context context) {
        LocalBrdMnger localBrdMnger;
        synchronized (M_LOCK) {
            try {
                if (mInstance == null) {
                    mInstance = new LocalBrdMnger(context.getApplicationContext());
                }
                localBrdMnger = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return localBrdMnger;
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.mReceivers) {
            try {
                ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, broadcastReceiver);
                ArrayList<IntentFilter> arrayList = this.mReceivers.get(broadcastReceiver);
                if (arrayList == null) {
                    arrayList = new ArrayList<>(1);
                    this.mReceivers.put(broadcastReceiver, arrayList);
                }
                arrayList.add(intentFilter);
                for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
                    String action = intentFilter.getAction(i2);
                    ArrayList<ReceiverRecord> arrayList2 = this.mActions.get(action);
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList<>(1);
                        this.mActions.put(action, arrayList2);
                    }
                    arrayList2.add(receiverRecord);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v2, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3, types: [int] */
    /* JADX WARN: Type inference failed for: r9v7 */
    public boolean sendBroadcast(Intent intent) {
        boolean z2;
        String str;
        String str2;
        int i2;
        ArrayList arrayList;
        Uri uri;
        boolean z3;
        synchronized (this.mReceivers) {
            try {
                String action = intent.getAction();
                String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
                Uri data = intent.getData();
                String scheme = intent.getScheme();
                Set<String> categories = intent.getCategories();
                ArrayList<ReceiverRecord> arrayList2 = this.mActions.get(intent.getAction());
                boolean z4 = false;
                if (arrayList2 != null) {
                    ArrayList arrayList3 = null;
                    int i3 = 0;
                    while (i3 < arrayList2.size()) {
                        ReceiverRecord receiverRecord = arrayList2.get(i3);
                        if (receiverRecord.broadcasting) {
                            i2 = i3;
                            str = action;
                            str2 = strResolveTypeIfNeeded;
                            uri = data;
                            arrayList = arrayList3;
                            z3 = z4;
                        } else {
                            str = action;
                            String str3 = strResolveTypeIfNeeded;
                            str2 = strResolveTypeIfNeeded;
                            i2 = i3;
                            Uri uri2 = data;
                            arrayList = arrayList3;
                            uri = data;
                            z3 = z4;
                            if (receiverRecord.filter.match(action, str3, scheme, uri2, categories, "LocalBroadcastManager") >= 0) {
                                arrayList3 = arrayList == null ? new ArrayList() : arrayList;
                                arrayList3.add(receiverRecord);
                                receiverRecord.broadcasting = true;
                            }
                            i3 = i2 + 1;
                            z4 = z3;
                            action = str;
                            strResolveTypeIfNeeded = str2;
                            data = uri;
                        }
                        arrayList3 = arrayList;
                        i3 = i2 + 1;
                        z4 = z3;
                        action = str;
                        strResolveTypeIfNeeded = str2;
                        data = uri;
                    }
                    ?? r11 = arrayList3;
                    z2 = z4;
                    if (r11 != 0) {
                        for (?? r9 = z2; r9 < r11.size(); r9++) {
                            ((ReceiverRecord) r11.get(r9)).broadcasting = z2;
                        }
                        this.mPendingBroadcasts.add(new BroadcastRecord(intent, r11));
                        if (!this.mHandler.hasMessages(1)) {
                            this.mHandler.sendEmptyMessage(1);
                        }
                        return true;
                    }
                } else {
                    z2 = false;
                }
                return z2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        synchronized (this.mReceivers) {
            try {
                ArrayList<IntentFilter> arrayListRemove = this.mReceivers.remove(broadcastReceiver);
                if (arrayListRemove == null) {
                    return;
                }
                for (int i2 = 0; i2 < arrayListRemove.size(); i2++) {
                    IntentFilter intentFilter = arrayListRemove.get(i2);
                    for (int i3 = 0; i3 < intentFilter.countActions(); i3++) {
                        String action = intentFilter.getAction(i3);
                        ArrayList<ReceiverRecord> arrayList = this.mActions.get(action);
                        if (arrayList != null) {
                            int i4 = 0;
                            while (i4 < arrayList.size()) {
                                if (arrayList.get(i4).receiver == broadcastReceiver) {
                                    arrayList.remove(i4);
                                    i4--;
                                }
                                i4++;
                            }
                            if (arrayList.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
