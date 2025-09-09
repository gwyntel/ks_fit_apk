package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import androidx.annotation.Nullable;
import androidx.core.os.TraceCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
final class GapWorker implements Runnable {

    /* renamed from: d, reason: collision with root package name */
    static final ThreadLocal f5895d = new ThreadLocal();

    /* renamed from: e, reason: collision with root package name */
    static Comparator f5896e = new Comparator<Task>() { // from class: androidx.recyclerview.widget.GapWorker.1
        @Override // java.util.Comparator
        public int compare(Task task, Task task2) {
            RecyclerView recyclerView = task.view;
            if ((recyclerView == null) != (task2.view == null)) {
                return recyclerView == null ? 1 : -1;
            }
            boolean z2 = task.immediate;
            if (z2 != task2.immediate) {
                return z2 ? -1 : 1;
            }
            int i2 = task2.viewVelocity - task.viewVelocity;
            if (i2 != 0) {
                return i2;
            }
            int i3 = task.distanceToItem - task2.distanceToItem;
            if (i3 != 0) {
                return i3;
            }
            return 0;
        }
    };

    /* renamed from: b, reason: collision with root package name */
    long f5898b;

    /* renamed from: c, reason: collision with root package name */
    long f5899c;

    /* renamed from: a, reason: collision with root package name */
    ArrayList f5897a = new ArrayList();
    private ArrayList<Task> mTasks = new ArrayList<>();

    @SuppressLint({"VisibleForTests"})
    static class LayoutPrefetchRegistryImpl implements RecyclerView.LayoutManager.LayoutPrefetchRegistry {

        /* renamed from: a, reason: collision with root package name */
        int f5900a;

        /* renamed from: b, reason: collision with root package name */
        int f5901b;

        /* renamed from: c, reason: collision with root package name */
        int[] f5902c;

        /* renamed from: d, reason: collision with root package name */
        int f5903d;

        LayoutPrefetchRegistryImpl() {
        }

        void a() {
            int[] iArr = this.f5902c;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.f5903d = 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry
        public void addPosition(int i2, int i3) {
            if (i2 < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            }
            if (i3 < 0) {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            }
            int i4 = this.f5903d;
            int i5 = i4 * 2;
            int[] iArr = this.f5902c;
            if (iArr == null) {
                int[] iArr2 = new int[4];
                this.f5902c = iArr2;
                Arrays.fill(iArr2, -1);
            } else if (i5 >= iArr.length) {
                int[] iArr3 = new int[i4 * 4];
                this.f5902c = iArr3;
                System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            }
            int[] iArr4 = this.f5902c;
            iArr4[i5] = i2;
            iArr4[i5 + 1] = i3;
            this.f5903d++;
        }

        void b(RecyclerView recyclerView, boolean z2) {
            this.f5903d = 0;
            int[] iArr = this.f5902c;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            RecyclerView.LayoutManager layoutManager = recyclerView.f6026k;
            if (recyclerView.f6025j == null || layoutManager == null || !layoutManager.isItemPrefetchEnabled()) {
                return;
            }
            if (z2) {
                if (!recyclerView.f6018c.g()) {
                    layoutManager.collectInitialPrefetchPositions(recyclerView.f6025j.getItemCount(), this);
                }
            } else if (!recyclerView.hasPendingAdapterUpdates()) {
                layoutManager.collectAdjacentPrefetchPositions(this.f5900a, this.f5901b, recyclerView.B, this);
            }
            int i2 = this.f5903d;
            if (i2 > layoutManager.f6058i) {
                layoutManager.f6058i = i2;
                layoutManager.f6059j = z2;
                recyclerView.f6016a.A();
            }
        }

        boolean c(int i2) {
            if (this.f5902c != null) {
                int i3 = this.f5903d * 2;
                for (int i4 = 0; i4 < i3; i4 += 2) {
                    if (this.f5902c[i4] == i2) {
                        return true;
                    }
                }
            }
            return false;
        }

        void d(int i2, int i3) {
            this.f5900a = i2;
            this.f5901b = i3;
        }
    }

    static class Task {
        public int distanceToItem;
        public boolean immediate;
        public int position;
        public RecyclerView view;
        public int viewVelocity;

        Task() {
        }

        public void clear() {
            this.immediate = false;
            this.viewVelocity = 0;
            this.distanceToItem = 0;
            this.view = null;
            this.position = 0;
        }
    }

    GapWorker() {
    }

    static boolean a(RecyclerView recyclerView, int i2) {
        int i3 = recyclerView.f6019d.i();
        for (int i4 = 0; i4 < i3; i4++) {
            RecyclerView.ViewHolder viewHolderF = RecyclerView.F(recyclerView.f6019d.h(i4));
            if (viewHolderF.mPosition == i2 && !viewHolderF.isInvalid()) {
                return true;
            }
        }
        return false;
    }

    private void buildTaskList() {
        Task task;
        int size = this.f5897a.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            RecyclerView recyclerView = (RecyclerView) this.f5897a.get(i3);
            if (recyclerView.getWindowVisibility() == 0) {
                recyclerView.A.b(recyclerView, false);
                i2 += recyclerView.A.f5903d;
            }
        }
        this.mTasks.ensureCapacity(i2);
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            RecyclerView recyclerView2 = (RecyclerView) this.f5897a.get(i5);
            if (recyclerView2.getWindowVisibility() == 0) {
                LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView2.A;
                int iAbs = Math.abs(layoutPrefetchRegistryImpl.f5900a) + Math.abs(layoutPrefetchRegistryImpl.f5901b);
                for (int i6 = 0; i6 < layoutPrefetchRegistryImpl.f5903d * 2; i6 += 2) {
                    if (i4 >= this.mTasks.size()) {
                        task = new Task();
                        this.mTasks.add(task);
                    } else {
                        task = this.mTasks.get(i4);
                    }
                    int[] iArr = layoutPrefetchRegistryImpl.f5902c;
                    int i7 = iArr[i6 + 1];
                    task.immediate = i7 <= iAbs;
                    task.viewVelocity = iAbs;
                    task.distanceToItem = i7;
                    task.view = recyclerView2;
                    task.position = iArr[i6];
                    i4++;
                }
            }
        }
        Collections.sort(this.mTasks, f5896e);
    }

    private void flushTaskWithDeadline(Task task, long j2) {
        RecyclerView.ViewHolder viewHolderPrefetchPositionWithDeadline = prefetchPositionWithDeadline(task.view, task.position, task.immediate ? Long.MAX_VALUE : j2);
        if (viewHolderPrefetchPositionWithDeadline == null || viewHolderPrefetchPositionWithDeadline.mNestedRecyclerView == null || !viewHolderPrefetchPositionWithDeadline.isBound() || viewHolderPrefetchPositionWithDeadline.isInvalid()) {
            return;
        }
        prefetchInnerRecyclerViewWithDeadline(viewHolderPrefetchPositionWithDeadline.mNestedRecyclerView.get(), j2);
    }

    private void flushTasksWithDeadline(long j2) {
        for (int i2 = 0; i2 < this.mTasks.size(); i2++) {
            Task task = this.mTasks.get(i2);
            if (task.view == null) {
                return;
            }
            flushTaskWithDeadline(task, j2);
            task.clear();
        }
    }

    private void prefetchInnerRecyclerViewWithDeadline(@Nullable RecyclerView recyclerView, long j2) {
        if (recyclerView == null) {
            return;
        }
        if (recyclerView.f6037v && recyclerView.f6019d.i() != 0) {
            recyclerView.Y();
        }
        LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.A;
        layoutPrefetchRegistryImpl.b(recyclerView, true);
        if (layoutPrefetchRegistryImpl.f5903d != 0) {
            try {
                TraceCompat.beginSection("RV Nested Prefetch");
                recyclerView.B.b(recyclerView.f6025j);
                for (int i2 = 0; i2 < layoutPrefetchRegistryImpl.f5903d * 2; i2 += 2) {
                    prefetchPositionWithDeadline(recyclerView, layoutPrefetchRegistryImpl.f5902c[i2], j2);
                }
            } finally {
                TraceCompat.endSection();
            }
        }
    }

    private RecyclerView.ViewHolder prefetchPositionWithDeadline(RecyclerView recyclerView, int i2, long j2) {
        if (a(recyclerView, i2)) {
            return null;
        }
        RecyclerView.Recycler recycler = recyclerView.f6016a;
        try {
            recyclerView.S();
            RecyclerView.ViewHolder viewHolderY = recycler.y(i2, false, j2);
            if (viewHolderY != null) {
                if (!viewHolderY.isBound() || viewHolderY.isInvalid()) {
                    recycler.a(viewHolderY, false);
                } else {
                    recycler.recycleView(viewHolderY.itemView);
                }
            }
            recyclerView.U(false);
            return viewHolderY;
        } catch (Throwable th) {
            recyclerView.U(false);
            throw th;
        }
    }

    public void add(RecyclerView recyclerView) {
        this.f5897a.add(recyclerView);
    }

    void b(RecyclerView recyclerView, int i2, int i3) {
        if (recyclerView.isAttachedToWindow() && this.f5898b == 0) {
            this.f5898b = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        recyclerView.A.d(i2, i3);
    }

    void c(long j2) {
        buildTaskList();
        flushTasksWithDeadline(j2);
    }

    public void remove(RecyclerView recyclerView) {
        this.f5897a.remove(recyclerView);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            TraceCompat.beginSection("RV Prefetch");
            if (!this.f5897a.isEmpty()) {
                int size = this.f5897a.size();
                long jMax = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    RecyclerView recyclerView = (RecyclerView) this.f5897a.get(i2);
                    if (recyclerView.getWindowVisibility() == 0) {
                        jMax = Math.max(recyclerView.getDrawingTime(), jMax);
                    }
                }
                if (jMax != 0) {
                    c(TimeUnit.MILLISECONDS.toNanos(jMax) + this.f5899c);
                    this.f5898b = 0L;
                    TraceCompat.endSection();
                }
            }
        } finally {
            this.f5898b = 0L;
            TraceCompat.endSection();
        }
    }
}
