package androidx.recyclerview.widget;

import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class BatchingListUpdateCallback implements ListUpdateCallback {
    private static final int TYPE_ADD = 1;
    private static final int TYPE_CHANGE = 3;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_REMOVE = 2;

    /* renamed from: a, reason: collision with root package name */
    final ListUpdateCallback f5824a;

    /* renamed from: b, reason: collision with root package name */
    int f5825b = 0;

    /* renamed from: c, reason: collision with root package name */
    int f5826c = -1;

    /* renamed from: d, reason: collision with root package name */
    int f5827d = -1;

    /* renamed from: e, reason: collision with root package name */
    Object f5828e = null;

    public BatchingListUpdateCallback(@NonNull ListUpdateCallback listUpdateCallback) {
        this.f5824a = listUpdateCallback;
    }

    public void dispatchLastEvent() {
        int i2 = this.f5825b;
        if (i2 == 0) {
            return;
        }
        if (i2 == 1) {
            this.f5824a.onInserted(this.f5826c, this.f5827d);
        } else if (i2 == 2) {
            this.f5824a.onRemoved(this.f5826c, this.f5827d);
        } else if (i2 == 3) {
            this.f5824a.onChanged(this.f5826c, this.f5827d, this.f5828e);
        }
        this.f5828e = null;
        this.f5825b = 0;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onChanged(int i2, int i3, Object obj) {
        int i4;
        if (this.f5825b == 3) {
            int i5 = this.f5826c;
            int i6 = this.f5827d;
            if (i2 <= i5 + i6 && (i4 = i2 + i3) >= i5 && this.f5828e == obj) {
                this.f5826c = Math.min(i2, i5);
                this.f5827d = Math.max(i6 + i5, i4) - this.f5826c;
                return;
            }
        }
        dispatchLastEvent();
        this.f5826c = i2;
        this.f5827d = i3;
        this.f5828e = obj;
        this.f5825b = 3;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onInserted(int i2, int i3) {
        int i4;
        if (this.f5825b == 1 && i2 >= (i4 = this.f5826c)) {
            int i5 = this.f5827d;
            if (i2 <= i4 + i5) {
                this.f5827d = i5 + i3;
                this.f5826c = Math.min(i2, i4);
                return;
            }
        }
        dispatchLastEvent();
        this.f5826c = i2;
        this.f5827d = i3;
        this.f5825b = 1;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onMoved(int i2, int i3) {
        dispatchLastEvent();
        this.f5824a.onMoved(i2, i3);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onRemoved(int i2, int i3) {
        int i4;
        if (this.f5825b == 2 && (i4 = this.f5826c) >= i2 && i4 <= i2 + i3) {
            this.f5827d += i3;
            this.f5826c = i2;
        } else {
            dispatchLastEvent();
            this.f5826c = i2;
            this.f5827d = i3;
            this.f5825b = 2;
        }
    }
}
