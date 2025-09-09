package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;
import androidx.core.util.Pools;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
class ViewInfoStore {
    private static final boolean DEBUG = false;

    /* renamed from: a, reason: collision with root package name */
    final SimpleArrayMap f6162a = new SimpleArrayMap();

    /* renamed from: b, reason: collision with root package name */
    final LongSparseArray f6163b = new LongSparseArray();

    static class InfoRecord {

        /* renamed from: d, reason: collision with root package name */
        static Pools.Pool f6164d = new Pools.SimplePool(20);

        /* renamed from: a, reason: collision with root package name */
        int f6165a;

        /* renamed from: b, reason: collision with root package name */
        RecyclerView.ItemAnimator.ItemHolderInfo f6166b;

        /* renamed from: c, reason: collision with root package name */
        RecyclerView.ItemAnimator.ItemHolderInfo f6167c;

        private InfoRecord() {
        }

        static void a() {
            while (f6164d.acquire() != null) {
            }
        }

        static InfoRecord b() {
            InfoRecord infoRecord = (InfoRecord) f6164d.acquire();
            return infoRecord == null ? new InfoRecord() : infoRecord;
        }

        static void c(InfoRecord infoRecord) {
            infoRecord.f6165a = 0;
            infoRecord.f6166b = null;
            infoRecord.f6167c = null;
            f6164d.release(infoRecord);
        }
    }

    interface ProcessCallback {
        void processAppeared(RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void processDisappeared(RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void processPersistent(RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void unused(RecyclerView.ViewHolder viewHolder);
    }

    ViewInfoStore() {
    }

    private RecyclerView.ItemAnimator.ItemHolderInfo popFromLayoutStep(RecyclerView.ViewHolder viewHolder, int i2) {
        InfoRecord infoRecord;
        RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo;
        int iIndexOfKey = this.f6162a.indexOfKey(viewHolder);
        if (iIndexOfKey >= 0 && (infoRecord = (InfoRecord) this.f6162a.valueAt(iIndexOfKey)) != null) {
            int i3 = infoRecord.f6165a;
            if ((i3 & i2) != 0) {
                int i4 = (~i2) & i3;
                infoRecord.f6165a = i4;
                if (i2 == 4) {
                    itemHolderInfo = infoRecord.f6166b;
                } else {
                    if (i2 != 8) {
                        throw new IllegalArgumentException("Must provide flag PRE or POST");
                    }
                    itemHolderInfo = infoRecord.f6167c;
                }
                if ((i4 & 12) == 0) {
                    this.f6162a.removeAt(iIndexOfKey);
                    InfoRecord.c(infoRecord);
                }
                return itemHolderInfo;
            }
        }
        return null;
    }

    void a(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecordB = (InfoRecord) this.f6162a.get(viewHolder);
        if (infoRecordB == null) {
            infoRecordB = InfoRecord.b();
            this.f6162a.put(viewHolder, infoRecordB);
        }
        infoRecordB.f6165a |= 2;
        infoRecordB.f6166b = itemHolderInfo;
    }

    void b(RecyclerView.ViewHolder viewHolder) {
        InfoRecord infoRecordB = (InfoRecord) this.f6162a.get(viewHolder);
        if (infoRecordB == null) {
            infoRecordB = InfoRecord.b();
            this.f6162a.put(viewHolder, infoRecordB);
        }
        infoRecordB.f6165a |= 1;
    }

    void c(long j2, RecyclerView.ViewHolder viewHolder) {
        this.f6163b.put(j2, viewHolder);
    }

    void d(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecordB = (InfoRecord) this.f6162a.get(viewHolder);
        if (infoRecordB == null) {
            infoRecordB = InfoRecord.b();
            this.f6162a.put(viewHolder, infoRecordB);
        }
        infoRecordB.f6167c = itemHolderInfo;
        infoRecordB.f6165a |= 8;
    }

    void e(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecordB = (InfoRecord) this.f6162a.get(viewHolder);
        if (infoRecordB == null) {
            infoRecordB = InfoRecord.b();
            this.f6162a.put(viewHolder, infoRecordB);
        }
        infoRecordB.f6166b = itemHolderInfo;
        infoRecordB.f6165a |= 4;
    }

    void f() {
        this.f6162a.clear();
        this.f6163b.clear();
    }

    RecyclerView.ViewHolder g(long j2) {
        return (RecyclerView.ViewHolder) this.f6163b.get(j2);
    }

    boolean h(RecyclerView.ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.f6162a.get(viewHolder);
        return (infoRecord == null || (infoRecord.f6165a & 1) == 0) ? false : true;
    }

    boolean i(RecyclerView.ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.f6162a.get(viewHolder);
        return (infoRecord == null || (infoRecord.f6165a & 4) == 0) ? false : true;
    }

    void j() {
        InfoRecord.a();
    }

    RecyclerView.ItemAnimator.ItemHolderInfo k(RecyclerView.ViewHolder viewHolder) {
        return popFromLayoutStep(viewHolder, 8);
    }

    RecyclerView.ItemAnimator.ItemHolderInfo l(RecyclerView.ViewHolder viewHolder) {
        return popFromLayoutStep(viewHolder, 4);
    }

    void m(ProcessCallback processCallback) {
        for (int size = this.f6162a.size() - 1; size >= 0; size--) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) this.f6162a.keyAt(size);
            InfoRecord infoRecord = (InfoRecord) this.f6162a.removeAt(size);
            int i2 = infoRecord.f6165a;
            if ((i2 & 3) == 3) {
                processCallback.unused(viewHolder);
            } else if ((i2 & 1) != 0) {
                RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo = infoRecord.f6166b;
                if (itemHolderInfo == null) {
                    processCallback.unused(viewHolder);
                } else {
                    processCallback.processDisappeared(viewHolder, itemHolderInfo, infoRecord.f6167c);
                }
            } else if ((i2 & 14) == 14) {
                processCallback.processAppeared(viewHolder, infoRecord.f6166b, infoRecord.f6167c);
            } else if ((i2 & 12) == 12) {
                processCallback.processPersistent(viewHolder, infoRecord.f6166b, infoRecord.f6167c);
            } else if ((i2 & 4) != 0) {
                processCallback.processDisappeared(viewHolder, infoRecord.f6166b, null);
            } else if ((i2 & 8) != 0) {
                processCallback.processAppeared(viewHolder, infoRecord.f6166b, infoRecord.f6167c);
            }
            InfoRecord.c(infoRecord);
        }
    }

    void n(RecyclerView.ViewHolder viewHolder) {
        InfoRecord infoRecord = (InfoRecord) this.f6162a.get(viewHolder);
        if (infoRecord == null) {
            return;
        }
        infoRecord.f6165a &= -2;
    }

    void o(RecyclerView.ViewHolder viewHolder) {
        int size = this.f6163b.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (viewHolder == this.f6163b.valueAt(size)) {
                this.f6163b.removeAt(size);
                break;
            }
            size--;
        }
        InfoRecord infoRecord = (InfoRecord) this.f6162a.remove(viewHolder);
        if (infoRecord != null) {
            InfoRecord.c(infoRecord);
        }
    }

    public void onViewDetached(RecyclerView.ViewHolder viewHolder) {
        n(viewHolder);
    }
}
