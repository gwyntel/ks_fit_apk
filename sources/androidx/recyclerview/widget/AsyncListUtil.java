package androidx.recyclerview.widget;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.recyclerview.widget.ThreadUtil;
import androidx.recyclerview.widget.TileList;

/* loaded from: classes2.dex */
public class AsyncListUtil<T> {

    /* renamed from: a, reason: collision with root package name */
    final Class f5806a;

    /* renamed from: b, reason: collision with root package name */
    final int f5807b;

    /* renamed from: c, reason: collision with root package name */
    final DataCallback f5808c;

    /* renamed from: d, reason: collision with root package name */
    final ViewCallback f5809d;

    /* renamed from: e, reason: collision with root package name */
    final TileList f5810e;

    /* renamed from: f, reason: collision with root package name */
    final ThreadUtil.MainThreadCallback f5811f;

    /* renamed from: g, reason: collision with root package name */
    final ThreadUtil.BackgroundCallback f5812g;

    /* renamed from: k, reason: collision with root package name */
    boolean f5816k;
    private final ThreadUtil.BackgroundCallback<T> mBackgroundCallback;
    private final ThreadUtil.MainThreadCallback<T> mMainThreadCallback;

    /* renamed from: h, reason: collision with root package name */
    final int[] f5813h = new int[2];

    /* renamed from: i, reason: collision with root package name */
    final int[] f5814i = new int[2];

    /* renamed from: j, reason: collision with root package name */
    final int[] f5815j = new int[2];
    private int mScrollHint = 0;

    /* renamed from: l, reason: collision with root package name */
    int f5817l = 0;

    /* renamed from: m, reason: collision with root package name */
    int f5818m = 0;

    /* renamed from: n, reason: collision with root package name */
    int f5819n = 0;

    /* renamed from: o, reason: collision with root package name */
    final SparseIntArray f5820o = new SparseIntArray();

    public static abstract class DataCallback<T> {
        @WorkerThread
        public abstract void fillData(@NonNull T[] tArr, int i2, int i3);

        @WorkerThread
        public int getMaxCachedTiles() {
            return 10;
        }

        @WorkerThread
        public void recycleData(@NonNull T[] tArr, int i2) {
        }

        @WorkerThread
        public abstract int refreshData();
    }

    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        @UiThread
        public void extendRangeInto(@NonNull int[] iArr, @NonNull int[] iArr2, int i2) {
            int i3 = iArr[1];
            int i4 = iArr[0];
            int i5 = (i3 - i4) + 1;
            int i6 = i5 / 2;
            iArr2[0] = i4 - (i2 == 1 ? i5 : i6);
            if (i2 != 2) {
                i5 = i6;
            }
            iArr2[1] = i3 + i5;
        }

        @UiThread
        public abstract void getItemRangeInto(@NonNull int[] iArr);

        @UiThread
        public abstract void onDataRefresh();

        @UiThread
        public abstract void onItemLoaded(int i2);
    }

    public AsyncListUtil(@NonNull Class<T> cls, int i2, @NonNull DataCallback<T> dataCallback, @NonNull ViewCallback viewCallback) {
        ThreadUtil.MainThreadCallback<T> mainThreadCallback = new ThreadUtil.MainThreadCallback<T>() { // from class: androidx.recyclerview.widget.AsyncListUtil.1
            private boolean isRequestedGeneration(int i3) {
                return i3 == AsyncListUtil.this.f5819n;
            }

            private void recycleAllTiles() {
                for (int i3 = 0; i3 < AsyncListUtil.this.f5810e.size(); i3++) {
                    AsyncListUtil asyncListUtil = AsyncListUtil.this;
                    asyncListUtil.f5812g.recycleTile(asyncListUtil.f5810e.getAtIndex(i3));
                }
                AsyncListUtil.this.f5810e.clear();
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
            public void addTile(int i3, TileList.Tile<T> tile) {
                if (!isRequestedGeneration(i3)) {
                    AsyncListUtil.this.f5812g.recycleTile(tile);
                    return;
                }
                TileList.Tile<T> tileAddOrReplace = AsyncListUtil.this.f5810e.addOrReplace(tile);
                if (tileAddOrReplace != null) {
                    Log.e("AsyncListUtil", "duplicate tile @" + tileAddOrReplace.mStartPosition);
                    AsyncListUtil.this.f5812g.recycleTile(tileAddOrReplace);
                }
                int i4 = tile.mStartPosition + tile.mItemCount;
                int i5 = 0;
                while (i5 < AsyncListUtil.this.f5820o.size()) {
                    int iKeyAt = AsyncListUtil.this.f5820o.keyAt(i5);
                    if (tile.mStartPosition > iKeyAt || iKeyAt >= i4) {
                        i5++;
                    } else {
                        AsyncListUtil.this.f5820o.removeAt(i5);
                        AsyncListUtil.this.f5809d.onItemLoaded(iKeyAt);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
            public void removeTile(int i3, int i4) {
                if (isRequestedGeneration(i3)) {
                    TileList.Tile<T> tileRemoveAtPos = AsyncListUtil.this.f5810e.removeAtPos(i4);
                    if (tileRemoveAtPos != null) {
                        AsyncListUtil.this.f5812g.recycleTile(tileRemoveAtPos);
                        return;
                    }
                    Log.e("AsyncListUtil", "tile not found @" + i4);
                }
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
            public void updateItemCount(int i3, int i4) {
                if (isRequestedGeneration(i3)) {
                    AsyncListUtil asyncListUtil = AsyncListUtil.this;
                    asyncListUtil.f5817l = i4;
                    asyncListUtil.f5809d.onDataRefresh();
                    AsyncListUtil asyncListUtil2 = AsyncListUtil.this;
                    asyncListUtil2.f5818m = asyncListUtil2.f5819n;
                    recycleAllTiles();
                    AsyncListUtil asyncListUtil3 = AsyncListUtil.this;
                    asyncListUtil3.f5816k = false;
                    asyncListUtil3.a();
                }
            }
        };
        this.mMainThreadCallback = mainThreadCallback;
        ThreadUtil.BackgroundCallback<T> backgroundCallback = new ThreadUtil.BackgroundCallback<T>() { // from class: androidx.recyclerview.widget.AsyncListUtil.2

            /* renamed from: a, reason: collision with root package name */
            final SparseBooleanArray f5822a = new SparseBooleanArray();
            private int mFirstRequiredTileStart;
            private int mGeneration;
            private int mItemCount;
            private int mLastRequiredTileStart;
            private TileList.Tile<T> mRecycledRoot;

            private TileList.Tile<T> acquireTile() {
                TileList.Tile<T> tile = this.mRecycledRoot;
                if (tile != null) {
                    this.mRecycledRoot = tile.f6154a;
                    return tile;
                }
                AsyncListUtil asyncListUtil = AsyncListUtil.this;
                return new TileList.Tile<>(asyncListUtil.f5806a, asyncListUtil.f5807b);
            }

            private void addTile(TileList.Tile<T> tile) {
                this.f5822a.put(tile.mStartPosition, true);
                AsyncListUtil.this.f5811f.addTile(this.mGeneration, tile);
            }

            private void flushTileCache(int i3) {
                int maxCachedTiles = AsyncListUtil.this.f5808c.getMaxCachedTiles();
                while (this.f5822a.size() >= maxCachedTiles) {
                    int iKeyAt = this.f5822a.keyAt(0);
                    SparseBooleanArray sparseBooleanArray = this.f5822a;
                    int iKeyAt2 = sparseBooleanArray.keyAt(sparseBooleanArray.size() - 1);
                    int i4 = this.mFirstRequiredTileStart - iKeyAt;
                    int i5 = iKeyAt2 - this.mLastRequiredTileStart;
                    if (i4 > 0 && (i4 >= i5 || i3 == 2)) {
                        removeTile(iKeyAt);
                    } else {
                        if (i5 <= 0) {
                            return;
                        }
                        if (i4 >= i5 && i3 != 1) {
                            return;
                        } else {
                            removeTile(iKeyAt2);
                        }
                    }
                }
            }

            private int getTileStart(int i3) {
                return i3 - (i3 % AsyncListUtil.this.f5807b);
            }

            private boolean isTileLoaded(int i3) {
                return this.f5822a.get(i3);
            }

            private void log(String str, Object... objArr) {
                Log.d("AsyncListUtil", "[BKGR] " + String.format(str, objArr));
            }

            private void removeTile(int i3) {
                this.f5822a.delete(i3);
                AsyncListUtil.this.f5811f.removeTile(this.mGeneration, i3);
            }

            private void requestTiles(int i3, int i4, int i5, boolean z2) {
                int i6 = i3;
                while (i6 <= i4) {
                    AsyncListUtil.this.f5812g.loadTile(z2 ? (i4 + i3) - i6 : i6, i5);
                    i6 += AsyncListUtil.this.f5807b;
                }
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void loadTile(int i3, int i4) {
                if (isTileLoaded(i3)) {
                    return;
                }
                TileList.Tile<T> tileAcquireTile = acquireTile();
                tileAcquireTile.mStartPosition = i3;
                int iMin = Math.min(AsyncListUtil.this.f5807b, this.mItemCount - i3);
                tileAcquireTile.mItemCount = iMin;
                AsyncListUtil.this.f5808c.fillData(tileAcquireTile.mItems, tileAcquireTile.mStartPosition, iMin);
                flushTileCache(i4);
                addTile(tileAcquireTile);
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void recycleTile(TileList.Tile<T> tile) {
                AsyncListUtil.this.f5808c.recycleData(tile.mItems, tile.mItemCount);
                tile.f6154a = this.mRecycledRoot;
                this.mRecycledRoot = tile;
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void refresh(int i3) {
                this.mGeneration = i3;
                this.f5822a.clear();
                int iRefreshData = AsyncListUtil.this.f5808c.refreshData();
                this.mItemCount = iRefreshData;
                AsyncListUtil.this.f5811f.updateItemCount(this.mGeneration, iRefreshData);
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void updateRange(int i3, int i4, int i5, int i6, int i7) {
                if (i3 > i4) {
                    return;
                }
                int tileStart = getTileStart(i3);
                int tileStart2 = getTileStart(i4);
                this.mFirstRequiredTileStart = getTileStart(i5);
                int tileStart3 = getTileStart(i6);
                this.mLastRequiredTileStart = tileStart3;
                if (i7 == 1) {
                    requestTiles(this.mFirstRequiredTileStart, tileStart2, i7, true);
                    requestTiles(tileStart2 + AsyncListUtil.this.f5807b, this.mLastRequiredTileStart, i7, false);
                } else {
                    requestTiles(tileStart, tileStart3, i7, false);
                    requestTiles(this.mFirstRequiredTileStart, tileStart - AsyncListUtil.this.f5807b, i7, true);
                }
            }
        };
        this.mBackgroundCallback = backgroundCallback;
        this.f5806a = cls;
        this.f5807b = i2;
        this.f5808c = dataCallback;
        this.f5809d = viewCallback;
        this.f5810e = new TileList(i2);
        MessageThreadUtil messageThreadUtil = new MessageThreadUtil();
        this.f5811f = messageThreadUtil.getMainThreadProxy(mainThreadCallback);
        this.f5812g = messageThreadUtil.getBackgroundProxy(backgroundCallback);
        refresh();
    }

    private boolean isRefreshPending() {
        return this.f5819n != this.f5818m;
    }

    void a() {
        int i2;
        this.f5809d.getItemRangeInto(this.f5813h);
        int[] iArr = this.f5813h;
        int i3 = iArr[0];
        int i4 = iArr[1];
        if (i3 > i4 || i3 < 0 || i4 >= this.f5817l) {
            return;
        }
        if (this.f5816k) {
            int[] iArr2 = this.f5814i;
            if (i3 > iArr2[1] || (i2 = iArr2[0]) > i4) {
                this.mScrollHint = 0;
            } else if (i3 < i2) {
                this.mScrollHint = 1;
            } else if (i3 > i2) {
                this.mScrollHint = 2;
            }
        } else {
            this.mScrollHint = 0;
        }
        int[] iArr3 = this.f5814i;
        iArr3[0] = i3;
        iArr3[1] = i4;
        this.f5809d.extendRangeInto(iArr, this.f5815j, this.mScrollHint);
        int[] iArr4 = this.f5815j;
        iArr4[0] = Math.min(this.f5813h[0], Math.max(iArr4[0], 0));
        int[] iArr5 = this.f5815j;
        iArr5[1] = Math.max(this.f5813h[1], Math.min(iArr5[1], this.f5817l - 1));
        ThreadUtil.BackgroundCallback backgroundCallback = this.f5812g;
        int[] iArr6 = this.f5813h;
        int i5 = iArr6[0];
        int i6 = iArr6[1];
        int[] iArr7 = this.f5815j;
        backgroundCallback.updateRange(i5, i6, iArr7[0], iArr7[1], this.mScrollHint);
    }

    @Nullable
    public T getItem(int i2) {
        if (i2 < 0 || i2 >= this.f5817l) {
            throw new IndexOutOfBoundsException(i2 + " is not within 0 and " + this.f5817l);
        }
        T t2 = (T) this.f5810e.getItemAt(i2);
        if (t2 == null && !isRefreshPending()) {
            this.f5820o.put(i2, 0);
        }
        return t2;
    }

    public int getItemCount() {
        return this.f5817l;
    }

    public void onRangeChanged() {
        if (isRefreshPending()) {
            return;
        }
        a();
        this.f5816k = true;
    }

    public void refresh() {
        this.f5820o.clear();
        ThreadUtil.BackgroundCallback backgroundCallback = this.f5812g;
        int i2 = this.f5819n + 1;
        this.f5819n = i2;
        backgroundCallback.refresh(i2);
    }
}
