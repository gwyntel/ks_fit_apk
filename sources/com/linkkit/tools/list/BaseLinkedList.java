package com.linkkit.tools.list;

import com.linkkit.tools.a;
import java.util.LinkedList;

/* loaded from: classes4.dex */
public class BaseLinkedList<T> {
    private static final String TAG = "BaseLinkedList";

    /* renamed from: a, reason: collision with root package name */
    protected LinkedList f18855a;
    private final Object mListLock = new Object();

    public BaseLinkedList() {
        this.f18855a = null;
        this.f18855a = new LinkedList();
    }

    public void add(T t2) {
        a.a(TAG, "addChain chain=" + t2);
        synchronized (this.mListLock) {
            try {
                if (!this.f18855a.contains(t2)) {
                    this.f18855a.addFirst(t2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void addLast(T t2) {
        a.a(TAG, "addLast chain=" + t2);
        synchronized (this.mListLock) {
            try {
                if (!this.f18855a.contains(t2)) {
                    this.f18855a.addLast(t2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void addList(LinkedList<T> linkedList) {
        a.a(TAG, "addList chain=" + linkedList);
        synchronized (this.mListLock) {
            try {
                this.f18855a.clear();
                if (linkedList == null) {
                    return;
                }
                this.f18855a.addAll(linkedList);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void clear() {
        synchronized (this.mListLock) {
            this.f18855a.clear();
        }
    }

    public boolean contains(T t2) {
        synchronized (this.mListLock) {
            try {
                LinkedList linkedList = this.f18855a;
                if (linkedList != null && linkedList.size() >= 1) {
                    return this.f18855a.contains(t2);
                }
                return false;
            } finally {
            }
        }
    }

    public T getItem(int i2) {
        synchronized (this.mListLock) {
            if (i2 >= 0) {
                try {
                    if (i2 + 1 <= getSize()) {
                        return (T) this.f18855a.get(i2);
                    }
                } finally {
                }
            }
            return null;
        }
    }

    public int getSize() {
        int size;
        synchronized (this.mListLock) {
            size = this.f18855a.size();
        }
        return size;
    }

    public void remove(T t2) {
        a.a(TAG, "remove chain=" + t2);
        synchronized (this.mListLock) {
            this.f18855a.remove(t2);
        }
    }
}
