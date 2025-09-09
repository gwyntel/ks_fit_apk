package androidx.localbroadcastmanager.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import com.alipay.sdk.m.u.i;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* loaded from: classes.dex */
public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    private static final String TAG = "LocalBroadcastManager";

    /* renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int f4613a = 0;
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final Context mAppContext;
    private final Handler mHandler;
    private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap<>();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();

    private static final class BroadcastRecord {

        /* renamed from: a, reason: collision with root package name */
        final Intent f4615a;

        /* renamed from: b, reason: collision with root package name */
        final ArrayList f4616b;

        BroadcastRecord(Intent intent, ArrayList arrayList) {
            this.f4615a = intent;
            this.f4616b = arrayList;
        }
    }

    private static final class ReceiverRecord {

        /* renamed from: a, reason: collision with root package name */
        final IntentFilter f4617a;

        /* renamed from: b, reason: collision with root package name */
        final BroadcastReceiver f4618b;

        /* renamed from: c, reason: collision with root package name */
        boolean f4619c;

        /* renamed from: d, reason: collision with root package name */
        boolean f4620d;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.f4617a = intentFilter;
            this.f4618b = broadcastReceiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.f4618b);
            sb.append(" filter=");
            sb.append(this.f4617a);
            if (this.f4620d) {
                sb.append(" DEAD");
            }
            sb.append(i.f9804d);
            return sb.toString();
        }
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) { // from class: androidx.localbroadcastmanager.content.LocalBroadcastManager.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    super.handleMessage(message);
                } else {
                    LocalBroadcastManager.this.a();
                }
            }
        };
    }

    @NonNull
    public static LocalBroadcastManager getInstance(@NonNull Context context) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            try {
                if (mInstance == null) {
                    mInstance = new LocalBroadcastManager(context.getApplicationContext());
                }
                localBroadcastManager = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return localBroadcastManager;
    }

    void a() {
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
                int size2 = broadcastRecord.f4616b.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ReceiverRecord receiverRecord = (ReceiverRecord) broadcastRecord.f4616b.get(i3);
                    if (!receiverRecord.f4620d) {
                        receiverRecord.f4618b.onReceive(this.mAppContext, broadcastRecord.f4615a);
                    }
                }
            }
        }
    }

    public void registerReceiver(@NonNull BroadcastReceiver broadcastReceiver, @NonNull IntentFilter intentFilter) {
        synchronized (this.mReceivers) {
            try {
                ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, broadcastReceiver);
                ArrayList<ReceiverRecord> arrayList = this.mReceivers.get(broadcastReceiver);
                if (arrayList == null) {
                    arrayList = new ArrayList<>(1);
                    this.mReceivers.put(broadcastReceiver, arrayList);
                }
                arrayList.add(receiverRecord);
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

    public boolean sendBroadcast(@NonNull Intent intent) {
        int i2;
        String str;
        ArrayList arrayList;
        ArrayList<ReceiverRecord> arrayList2;
        String str2;
        synchronized (this.mReceivers) {
            try {
                String action = intent.getAction();
                String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
                Uri data = intent.getData();
                String scheme = intent.getScheme();
                Set<String> categories = intent.getCategories();
                boolean z2 = (intent.getFlags() & 8) != 0;
                if (z2) {
                    Log.v(TAG, "Resolving type " + strResolveTypeIfNeeded + " scheme " + scheme + " of intent " + intent);
                }
                ArrayList<ReceiverRecord> arrayList3 = this.mActions.get(intent.getAction());
                if (arrayList3 != null) {
                    if (z2) {
                        Log.v(TAG, "Action list: " + arrayList3);
                    }
                    ArrayList arrayList4 = null;
                    int i3 = 0;
                    while (i3 < arrayList3.size()) {
                        ReceiverRecord receiverRecord = arrayList3.get(i3);
                        if (z2) {
                            Log.v(TAG, "Matching against filter " + receiverRecord.f4617a);
                        }
                        if (receiverRecord.f4619c) {
                            if (z2) {
                                Log.v(TAG, "  Filter's target already added");
                            }
                            i2 = i3;
                            arrayList2 = arrayList3;
                            str = action;
                            str2 = strResolveTypeIfNeeded;
                            arrayList = arrayList4;
                        } else {
                            i2 = i3;
                            str = action;
                            arrayList = arrayList4;
                            arrayList2 = arrayList3;
                            str2 = strResolveTypeIfNeeded;
                            int iMatch = receiverRecord.f4617a.match(action, strResolveTypeIfNeeded, scheme, data, categories, TAG);
                            if (iMatch >= 0) {
                                if (z2) {
                                    Log.v(TAG, "  Filter matched!  match=0x" + Integer.toHexString(iMatch));
                                }
                                arrayList4 = arrayList == null ? new ArrayList() : arrayList;
                                arrayList4.add(receiverRecord);
                                receiverRecord.f4619c = true;
                                i3 = i2 + 1;
                                action = str;
                                arrayList3 = arrayList2;
                                strResolveTypeIfNeeded = str2;
                            } else if (z2) {
                                Log.v(TAG, "  Filter did not match: " + (iMatch != -4 ? iMatch != -3 ? iMatch != -2 ? iMatch != -1 ? "unknown reason" : "type" : "data" : "action" : "category"));
                            }
                        }
                        arrayList4 = arrayList;
                        i3 = i2 + 1;
                        action = str;
                        arrayList3 = arrayList2;
                        strResolveTypeIfNeeded = str2;
                    }
                    ArrayList arrayList5 = arrayList4;
                    if (arrayList5 != null) {
                        for (int i4 = 0; i4 < arrayList5.size(); i4++) {
                            ((ReceiverRecord) arrayList5.get(i4)).f4619c = false;
                        }
                        this.mPendingBroadcasts.add(new BroadcastRecord(intent, arrayList5));
                        if (!this.mHandler.hasMessages(1)) {
                            this.mHandler.sendEmptyMessage(1);
                        }
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void sendBroadcastSync(@NonNull Intent intent) {
        if (sendBroadcast(intent)) {
            a();
        }
    }

    public void unregisterReceiver(@NonNull BroadcastReceiver broadcastReceiver) {
        synchronized (this.mReceivers) {
            try {
                ArrayList<ReceiverRecord> arrayListRemove = this.mReceivers.remove(broadcastReceiver);
                if (arrayListRemove == null) {
                    return;
                }
                for (int size = arrayListRemove.size() - 1; size >= 0; size--) {
                    ReceiverRecord receiverRecord = arrayListRemove.get(size);
                    receiverRecord.f4620d = true;
                    for (int i2 = 0; i2 < receiverRecord.f4617a.countActions(); i2++) {
                        String action = receiverRecord.f4617a.getAction(i2);
                        ArrayList<ReceiverRecord> arrayList = this.mActions.get(action);
                        if (arrayList != null) {
                            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                                ReceiverRecord receiverRecord2 = arrayList.get(size2);
                                if (receiverRecord2.f4618b == broadcastReceiver) {
                                    receiverRecord2.f4620d = true;
                                    arrayList.remove(size2);
                                }
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
