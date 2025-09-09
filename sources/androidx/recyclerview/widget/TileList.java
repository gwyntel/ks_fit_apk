package androidx.recyclerview.widget;

import android.util.SparseArray;
import java.lang.reflect.Array;

/* loaded from: classes2.dex */
class TileList<T> {

    /* renamed from: a, reason: collision with root package name */
    final int f6152a;

    /* renamed from: b, reason: collision with root package name */
    Tile f6153b;
    private final SparseArray<Tile<T>> mTiles = new SparseArray<>(10);

    public static class Tile<T> {

        /* renamed from: a, reason: collision with root package name */
        Tile f6154a;
        public int mItemCount;
        public final T[] mItems;
        public int mStartPosition;

        public Tile(Class<T> cls, int i2) {
            this.mItems = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i2));
        }

        boolean a(int i2) {
            int i3 = this.mStartPosition;
            return i3 <= i2 && i2 < i3 + this.mItemCount;
        }

        Object b(int i2) {
            return this.mItems[i2 - this.mStartPosition];
        }
    }

    public TileList(int i2) {
        this.f6152a = i2;
    }

    public Tile<T> addOrReplace(Tile<T> tile) {
        int iIndexOfKey = this.mTiles.indexOfKey(tile.mStartPosition);
        if (iIndexOfKey < 0) {
            this.mTiles.put(tile.mStartPosition, tile);
            return null;
        }
        Tile<T> tileValueAt = this.mTiles.valueAt(iIndexOfKey);
        this.mTiles.setValueAt(iIndexOfKey, tile);
        if (this.f6153b == tileValueAt) {
            this.f6153b = tile;
        }
        return tileValueAt;
    }

    public void clear() {
        this.mTiles.clear();
    }

    public Tile<T> getAtIndex(int i2) {
        if (i2 < 0 || i2 >= this.mTiles.size()) {
            return null;
        }
        return this.mTiles.valueAt(i2);
    }

    public T getItemAt(int i2) {
        Tile tile = this.f6153b;
        if (tile == null || !tile.a(i2)) {
            int iIndexOfKey = this.mTiles.indexOfKey(i2 - (i2 % this.f6152a));
            if (iIndexOfKey < 0) {
                return null;
            }
            this.f6153b = this.mTiles.valueAt(iIndexOfKey);
        }
        return (T) this.f6153b.b(i2);
    }

    public Tile<T> removeAtPos(int i2) {
        Tile<T> tile = this.mTiles.get(i2);
        if (this.f6153b == tile) {
            this.f6153b = null;
        }
        this.mTiles.delete(i2);
        return tile;
    }

    public int size() {
        return this.mTiles.size();
    }
}
