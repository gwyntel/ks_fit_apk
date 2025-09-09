package androidx.recyclerview.widget;

import androidx.core.util.Pools;
import androidx.recyclerview.widget.OpReorderer;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.umeng.analytics.pro.f;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
final class AdapterHelper implements OpReorderer.Callback {
    private static final boolean DEBUG = false;
    private static final String TAG = "AHT";

    /* renamed from: a, reason: collision with root package name */
    final ArrayList f5784a;

    /* renamed from: b, reason: collision with root package name */
    final ArrayList f5785b;

    /* renamed from: c, reason: collision with root package name */
    final Callback f5786c;

    /* renamed from: d, reason: collision with root package name */
    Runnable f5787d;

    /* renamed from: e, reason: collision with root package name */
    final boolean f5788e;

    /* renamed from: f, reason: collision with root package name */
    final OpReorderer f5789f;
    private int mExistingUpdateTypes;
    private Pools.Pool<UpdateOp> mUpdateOpPool;

    interface Callback {
        RecyclerView.ViewHolder findViewHolder(int i2);

        void markViewHoldersUpdated(int i2, int i3, Object obj);

        void offsetPositionsForAdd(int i2, int i3);

        void offsetPositionsForMove(int i2, int i3);

        void offsetPositionsForRemovingInvisible(int i2, int i3);

        void offsetPositionsForRemovingLaidOutOrNewView(int i2, int i3);

        void onDispatchFirstPass(UpdateOp updateOp);

        void onDispatchSecondPass(UpdateOp updateOp);
    }

    static final class UpdateOp {

        /* renamed from: a, reason: collision with root package name */
        int f5790a;

        /* renamed from: b, reason: collision with root package name */
        int f5791b;

        /* renamed from: c, reason: collision with root package name */
        Object f5792c;

        /* renamed from: d, reason: collision with root package name */
        int f5793d;

        UpdateOp(int i2, int i3, int i4, Object obj) {
            this.f5790a = i2;
            this.f5791b = i3;
            this.f5793d = i4;
            this.f5792c = obj;
        }

        String a() {
            int i2 = this.f5790a;
            return i2 != 1 ? i2 != 2 ? i2 != 4 ? i2 != 8 ? "??" : "mv" : f.R : "rm" : TmpConstant.GROUP_OP_ADD;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UpdateOp)) {
                return false;
            }
            UpdateOp updateOp = (UpdateOp) obj;
            int i2 = this.f5790a;
            if (i2 != updateOp.f5790a) {
                return false;
            }
            if (i2 == 8 && Math.abs(this.f5793d - this.f5791b) == 1 && this.f5793d == updateOp.f5791b && this.f5791b == updateOp.f5793d) {
                return true;
            }
            if (this.f5793d != updateOp.f5793d || this.f5791b != updateOp.f5791b) {
                return false;
            }
            Object obj2 = this.f5792c;
            if (obj2 != null) {
                if (!obj2.equals(updateOp.f5792c)) {
                    return false;
                }
            } else if (updateOp.f5792c != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.f5790a * 31) + this.f5791b) * 31) + this.f5793d;
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + a() + ",s:" + this.f5791b + "c:" + this.f5793d + ",p:" + this.f5792c + "]";
        }
    }

    AdapterHelper(Callback callback) {
        this(callback, false);
    }

    private void applyAdd(UpdateOp updateOp) {
        postponeAndUpdateViewHolders(updateOp);
    }

    private void applyMove(UpdateOp updateOp) {
        postponeAndUpdateViewHolders(updateOp);
    }

    private void applyRemove(UpdateOp updateOp) {
        boolean z2;
        char c2;
        int i2 = updateOp.f5791b;
        int i3 = updateOp.f5793d + i2;
        char c3 = 65535;
        int i4 = i2;
        int i5 = 0;
        while (i4 < i3) {
            if (this.f5786c.findViewHolder(i4) != null || canFindInPreLayout(i4)) {
                if (c3 == 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(2, i2, i5, null));
                    z2 = true;
                } else {
                    z2 = false;
                }
                c2 = 1;
            } else {
                if (c3 == 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(2, i2, i5, null));
                    z2 = true;
                } else {
                    z2 = false;
                }
                c2 = 0;
            }
            if (z2) {
                i4 -= i5;
                i3 -= i5;
                i5 = 1;
            } else {
                i5++;
            }
            i4++;
            c3 = c2;
        }
        if (i5 != updateOp.f5793d) {
            recycleUpdateOp(updateOp);
            updateOp = obtainUpdateOp(2, i2, i5, null);
        }
        if (c3 == 0) {
            dispatchAndUpdateViewHolders(updateOp);
        } else {
            postponeAndUpdateViewHolders(updateOp);
        }
    }

    private void applyUpdate(UpdateOp updateOp) {
        int i2 = updateOp.f5791b;
        int i3 = updateOp.f5793d + i2;
        int i4 = 0;
        boolean z2 = -1;
        int i5 = i2;
        while (i2 < i3) {
            if (this.f5786c.findViewHolder(i2) != null || canFindInPreLayout(i2)) {
                if (!z2) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(4, i5, i4, updateOp.f5792c));
                    i5 = i2;
                    i4 = 0;
                }
                z2 = true;
            } else {
                if (z2) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(4, i5, i4, updateOp.f5792c));
                    i5 = i2;
                    i4 = 0;
                }
                z2 = false;
            }
            i4++;
            i2++;
        }
        if (i4 != updateOp.f5793d) {
            Object obj = updateOp.f5792c;
            recycleUpdateOp(updateOp);
            updateOp = obtainUpdateOp(4, i5, i4, obj);
        }
        if (z2) {
            postponeAndUpdateViewHolders(updateOp);
        } else {
            dispatchAndUpdateViewHolders(updateOp);
        }
    }

    private boolean canFindInPreLayout(int i2) {
        int size = this.f5785b.size();
        for (int i3 = 0; i3 < size; i3++) {
            UpdateOp updateOp = (UpdateOp) this.f5785b.get(i3);
            int i4 = updateOp.f5790a;
            if (i4 == 8) {
                if (e(updateOp.f5793d, i3 + 1) == i2) {
                    return true;
                }
            } else if (i4 == 1) {
                int i5 = updateOp.f5791b;
                int i6 = updateOp.f5793d + i5;
                while (i5 < i6) {
                    if (e(i5, i3 + 1) == i2) {
                        return true;
                    }
                    i5++;
                }
            } else {
                continue;
            }
        }
        return false;
    }

    private void dispatchAndUpdateViewHolders(UpdateOp updateOp) {
        int i2;
        int i3 = updateOp.f5790a;
        if (i3 == 1 || i3 == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int iUpdatePositionWithPostponed = updatePositionWithPostponed(updateOp.f5791b, i3);
        int i4 = updateOp.f5791b;
        int i5 = updateOp.f5790a;
        if (i5 == 2) {
            i2 = 0;
        } else {
            if (i5 != 4) {
                throw new IllegalArgumentException("op should be remove or update." + updateOp);
            }
            i2 = 1;
        }
        int i6 = 1;
        for (int i7 = 1; i7 < updateOp.f5793d; i7++) {
            int iUpdatePositionWithPostponed2 = updatePositionWithPostponed(updateOp.f5791b + (i2 * i7), updateOp.f5790a);
            int i8 = updateOp.f5790a;
            if (i8 == 2 ? iUpdatePositionWithPostponed2 != iUpdatePositionWithPostponed : !(i8 == 4 && iUpdatePositionWithPostponed2 == iUpdatePositionWithPostponed + 1)) {
                UpdateOp updateOpObtainUpdateOp = obtainUpdateOp(i8, iUpdatePositionWithPostponed, i6, updateOp.f5792c);
                c(updateOpObtainUpdateOp, i4);
                recycleUpdateOp(updateOpObtainUpdateOp);
                if (updateOp.f5790a == 4) {
                    i4 += i6;
                }
                i6 = 1;
                iUpdatePositionWithPostponed = iUpdatePositionWithPostponed2;
            } else {
                i6++;
            }
        }
        Object obj = updateOp.f5792c;
        recycleUpdateOp(updateOp);
        if (i6 > 0) {
            UpdateOp updateOpObtainUpdateOp2 = obtainUpdateOp(updateOp.f5790a, iUpdatePositionWithPostponed, i6, obj);
            c(updateOpObtainUpdateOp2, i4);
            recycleUpdateOp(updateOpObtainUpdateOp2);
        }
    }

    private void postponeAndUpdateViewHolders(UpdateOp updateOp) {
        this.f5785b.add(updateOp);
        int i2 = updateOp.f5790a;
        if (i2 == 1) {
            this.f5786c.offsetPositionsForAdd(updateOp.f5791b, updateOp.f5793d);
            return;
        }
        if (i2 == 2) {
            this.f5786c.offsetPositionsForRemovingLaidOutOrNewView(updateOp.f5791b, updateOp.f5793d);
            return;
        }
        if (i2 == 4) {
            this.f5786c.markViewHoldersUpdated(updateOp.f5791b, updateOp.f5793d, updateOp.f5792c);
        } else {
            if (i2 == 8) {
                this.f5786c.offsetPositionsForMove(updateOp.f5791b, updateOp.f5793d);
                return;
            }
            throw new IllegalArgumentException("Unknown update op type for " + updateOp);
        }
    }

    private int updatePositionWithPostponed(int i2, int i3) {
        int i4;
        int i5;
        for (int size = this.f5785b.size() - 1; size >= 0; size--) {
            UpdateOp updateOp = (UpdateOp) this.f5785b.get(size);
            int i6 = updateOp.f5790a;
            if (i6 == 8) {
                int i7 = updateOp.f5791b;
                int i8 = updateOp.f5793d;
                if (i7 < i8) {
                    i5 = i7;
                    i4 = i8;
                } else {
                    i4 = i7;
                    i5 = i8;
                }
                if (i2 < i5 || i2 > i4) {
                    if (i2 < i7) {
                        if (i3 == 1) {
                            updateOp.f5791b = i7 + 1;
                            updateOp.f5793d = i8 + 1;
                        } else if (i3 == 2) {
                            updateOp.f5791b = i7 - 1;
                            updateOp.f5793d = i8 - 1;
                        }
                    }
                } else if (i5 == i7) {
                    if (i3 == 1) {
                        updateOp.f5793d = i8 + 1;
                    } else if (i3 == 2) {
                        updateOp.f5793d = i8 - 1;
                    }
                    i2++;
                } else {
                    if (i3 == 1) {
                        updateOp.f5791b = i7 + 1;
                    } else if (i3 == 2) {
                        updateOp.f5791b = i7 - 1;
                    }
                    i2--;
                }
            } else {
                int i9 = updateOp.f5791b;
                if (i9 <= i2) {
                    if (i6 == 1) {
                        i2 -= updateOp.f5793d;
                    } else if (i6 == 2) {
                        i2 += updateOp.f5793d;
                    }
                } else if (i3 == 1) {
                    updateOp.f5791b = i9 + 1;
                } else if (i3 == 2) {
                    updateOp.f5791b = i9 - 1;
                }
            }
        }
        for (int size2 = this.f5785b.size() - 1; size2 >= 0; size2--) {
            UpdateOp updateOp2 = (UpdateOp) this.f5785b.get(size2);
            if (updateOp2.f5790a == 8) {
                int i10 = updateOp2.f5793d;
                if (i10 == updateOp2.f5791b || i10 < 0) {
                    this.f5785b.remove(size2);
                    recycleUpdateOp(updateOp2);
                }
            } else if (updateOp2.f5793d <= 0) {
                this.f5785b.remove(size2);
                recycleUpdateOp(updateOp2);
            }
        }
        return i2;
    }

    void a() {
        int size = this.f5785b.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.f5786c.onDispatchSecondPass((UpdateOp) this.f5785b.get(i2));
        }
        n(this.f5785b);
        this.mExistingUpdateTypes = 0;
    }

    public int applyPendingUpdatesToPosition(int i2) {
        int size = this.f5784a.size();
        for (int i3 = 0; i3 < size; i3++) {
            UpdateOp updateOp = (UpdateOp) this.f5784a.get(i3);
            int i4 = updateOp.f5790a;
            if (i4 != 1) {
                if (i4 == 2) {
                    int i5 = updateOp.f5791b;
                    if (i5 <= i2) {
                        int i6 = updateOp.f5793d;
                        if (i5 + i6 > i2) {
                            return -1;
                        }
                        i2 -= i6;
                    } else {
                        continue;
                    }
                } else if (i4 == 8) {
                    int i7 = updateOp.f5791b;
                    if (i7 == i2) {
                        i2 = updateOp.f5793d;
                    } else {
                        if (i7 < i2) {
                            i2--;
                        }
                        if (updateOp.f5793d <= i2) {
                            i2++;
                        }
                    }
                }
            } else if (updateOp.f5791b <= i2) {
                i2 += updateOp.f5793d;
            }
        }
        return i2;
    }

    void b() {
        a();
        int size = this.f5784a.size();
        for (int i2 = 0; i2 < size; i2++) {
            UpdateOp updateOp = (UpdateOp) this.f5784a.get(i2);
            int i3 = updateOp.f5790a;
            if (i3 == 1) {
                this.f5786c.onDispatchSecondPass(updateOp);
                this.f5786c.offsetPositionsForAdd(updateOp.f5791b, updateOp.f5793d);
            } else if (i3 == 2) {
                this.f5786c.onDispatchSecondPass(updateOp);
                this.f5786c.offsetPositionsForRemovingInvisible(updateOp.f5791b, updateOp.f5793d);
            } else if (i3 == 4) {
                this.f5786c.onDispatchSecondPass(updateOp);
                this.f5786c.markViewHoldersUpdated(updateOp.f5791b, updateOp.f5793d, updateOp.f5792c);
            } else if (i3 == 8) {
                this.f5786c.onDispatchSecondPass(updateOp);
                this.f5786c.offsetPositionsForMove(updateOp.f5791b, updateOp.f5793d);
            }
            Runnable runnable = this.f5787d;
            if (runnable != null) {
                runnable.run();
            }
        }
        n(this.f5784a);
        this.mExistingUpdateTypes = 0;
    }

    void c(UpdateOp updateOp, int i2) {
        this.f5786c.onDispatchFirstPass(updateOp);
        int i3 = updateOp.f5790a;
        if (i3 == 2) {
            this.f5786c.offsetPositionsForRemovingInvisible(i2, updateOp.f5793d);
        } else {
            if (i3 != 4) {
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            }
            this.f5786c.markViewHoldersUpdated(i2, updateOp.f5793d, updateOp.f5792c);
        }
    }

    int d(int i2) {
        return e(i2, 0);
    }

    int e(int i2, int i3) {
        int size = this.f5785b.size();
        while (i3 < size) {
            UpdateOp updateOp = (UpdateOp) this.f5785b.get(i3);
            int i4 = updateOp.f5790a;
            if (i4 == 8) {
                int i5 = updateOp.f5791b;
                if (i5 == i2) {
                    i2 = updateOp.f5793d;
                } else {
                    if (i5 < i2) {
                        i2--;
                    }
                    if (updateOp.f5793d <= i2) {
                        i2++;
                    }
                }
            } else {
                int i6 = updateOp.f5791b;
                if (i6 > i2) {
                    continue;
                } else if (i4 == 2) {
                    int i7 = updateOp.f5793d;
                    if (i2 < i6 + i7) {
                        return -1;
                    }
                    i2 -= i7;
                } else if (i4 == 1) {
                    i2 += updateOp.f5793d;
                }
            }
            i3++;
        }
        return i2;
    }

    boolean f(int i2) {
        return (i2 & this.mExistingUpdateTypes) != 0;
    }

    boolean g() {
        return this.f5784a.size() > 0;
    }

    boolean h() {
        return (this.f5785b.isEmpty() || this.f5784a.isEmpty()) ? false : true;
    }

    boolean i(int i2, int i3, Object obj) {
        if (i3 < 1) {
            return false;
        }
        this.f5784a.add(obtainUpdateOp(4, i2, i3, obj));
        this.mExistingUpdateTypes |= 4;
        return this.f5784a.size() == 1;
    }

    boolean j(int i2, int i3) {
        if (i3 < 1) {
            return false;
        }
        this.f5784a.add(obtainUpdateOp(1, i2, i3, null));
        this.mExistingUpdateTypes |= 1;
        return this.f5784a.size() == 1;
    }

    boolean k(int i2, int i3, int i4) {
        if (i2 == i3) {
            return false;
        }
        if (i4 != 1) {
            throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
        }
        this.f5784a.add(obtainUpdateOp(8, i2, i3, null));
        this.mExistingUpdateTypes |= 8;
        return this.f5784a.size() == 1;
    }

    boolean l(int i2, int i3) {
        if (i3 < 1) {
            return false;
        }
        this.f5784a.add(obtainUpdateOp(2, i2, i3, null));
        this.mExistingUpdateTypes |= 2;
        return this.f5784a.size() == 1;
    }

    void m() {
        this.f5789f.a(this.f5784a);
        int size = this.f5784a.size();
        for (int i2 = 0; i2 < size; i2++) {
            UpdateOp updateOp = (UpdateOp) this.f5784a.get(i2);
            int i3 = updateOp.f5790a;
            if (i3 == 1) {
                applyAdd(updateOp);
            } else if (i3 == 2) {
                applyRemove(updateOp);
            } else if (i3 == 4) {
                applyUpdate(updateOp);
            } else if (i3 == 8) {
                applyMove(updateOp);
            }
            Runnable runnable = this.f5787d;
            if (runnable != null) {
                runnable.run();
            }
        }
        this.f5784a.clear();
    }

    void n(List list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            recycleUpdateOp((UpdateOp) list.get(i2));
        }
        list.clear();
    }

    void o() {
        n(this.f5784a);
        n(this.f5785b);
        this.mExistingUpdateTypes = 0;
    }

    @Override // androidx.recyclerview.widget.OpReorderer.Callback
    public UpdateOp obtainUpdateOp(int i2, int i3, int i4, Object obj) {
        UpdateOp updateOpAcquire = this.mUpdateOpPool.acquire();
        if (updateOpAcquire == null) {
            return new UpdateOp(i2, i3, i4, obj);
        }
        updateOpAcquire.f5790a = i2;
        updateOpAcquire.f5791b = i3;
        updateOpAcquire.f5793d = i4;
        updateOpAcquire.f5792c = obj;
        return updateOpAcquire;
    }

    @Override // androidx.recyclerview.widget.OpReorderer.Callback
    public void recycleUpdateOp(UpdateOp updateOp) {
        if (this.f5788e) {
            return;
        }
        updateOp.f5792c = null;
        this.mUpdateOpPool.release(updateOp);
    }

    AdapterHelper(Callback callback, boolean z2) {
        this.mUpdateOpPool = new Pools.SimplePool(30);
        this.f5784a = new ArrayList();
        this.f5785b = new ArrayList();
        this.mExistingUpdateTypes = 0;
        this.f5786c = callback;
        this.f5788e = z2;
        this.f5789f = new OpReorderer(this);
    }
}
