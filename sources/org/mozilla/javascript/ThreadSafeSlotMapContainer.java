package org.mozilla.javascript;

import java.util.Iterator;
import java.util.concurrent.locks.StampedLock;
import org.mozilla.javascript.ScriptableObject;

/* loaded from: classes5.dex */
class ThreadSafeSlotMapContainer extends SlotMapContainer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final StampedLock lock;

    ThreadSafeSlotMapContainer(int i2) {
        super(i2);
        this.lock = g.a();
    }

    @Override // org.mozilla.javascript.SlotMapContainer, org.mozilla.javascript.SlotMap
    public void addSlot(ScriptableObject.Slot slot) {
        long jWriteLock = this.lock.writeLock();
        try {
            checkMapSize();
            this.map.addSlot(slot);
        } finally {
            this.lock.unlockWrite(jWriteLock);
        }
    }

    @Override // org.mozilla.javascript.SlotMapContainer
    protected void checkMapSize() {
        super.checkMapSize();
    }

    @Override // org.mozilla.javascript.SlotMapContainer
    public int dirtySize() {
        return this.map.size();
    }

    @Override // org.mozilla.javascript.SlotMapContainer, org.mozilla.javascript.SlotMap
    public ScriptableObject.Slot get(Object obj, int i2, ScriptableObject.SlotAccess slotAccess) {
        long jWriteLock = this.lock.writeLock();
        try {
            if (slotAccess != ScriptableObject.SlotAccess.QUERY) {
                checkMapSize();
            }
            ScriptableObject.Slot slot = this.map.get(obj, i2, slotAccess);
            this.lock.unlockWrite(jWriteLock);
            return slot;
        } catch (Throwable th) {
            this.lock.unlockWrite(jWriteLock);
            throw th;
        }
    }

    @Override // org.mozilla.javascript.SlotMapContainer, org.mozilla.javascript.SlotMap
    public boolean isEmpty() {
        long jTryOptimisticRead = this.lock.tryOptimisticRead();
        boolean zIsEmpty = this.map.isEmpty();
        if (this.lock.validate(jTryOptimisticRead)) {
            return zIsEmpty;
        }
        long lock = this.lock.readLock();
        try {
            return this.map.isEmpty();
        } finally {
            this.lock.unlockRead(lock);
        }
    }

    @Override // org.mozilla.javascript.SlotMapContainer, java.lang.Iterable
    public Iterator<ScriptableObject.Slot> iterator() {
        return this.map.iterator();
    }

    @Override // org.mozilla.javascript.SlotMapContainer, org.mozilla.javascript.SlotMap
    public ScriptableObject.Slot query(Object obj, int i2) {
        long jTryOptimisticRead = this.lock.tryOptimisticRead();
        ScriptableObject.Slot slotQuery = this.map.query(obj, i2);
        if (this.lock.validate(jTryOptimisticRead)) {
            return slotQuery;
        }
        long lock = this.lock.readLock();
        try {
            return this.map.query(obj, i2);
        } finally {
            this.lock.unlockRead(lock);
        }
    }

    @Override // org.mozilla.javascript.SlotMapContainer
    public long readLock() {
        return this.lock.readLock();
    }

    @Override // org.mozilla.javascript.SlotMapContainer, org.mozilla.javascript.SlotMap
    public void remove(Object obj, int i2) {
        long jWriteLock = this.lock.writeLock();
        try {
            this.map.remove(obj, i2);
        } finally {
            this.lock.unlockWrite(jWriteLock);
        }
    }

    @Override // org.mozilla.javascript.SlotMapContainer, org.mozilla.javascript.SlotMap
    public int size() {
        long jTryOptimisticRead = this.lock.tryOptimisticRead();
        int size = this.map.size();
        if (this.lock.validate(jTryOptimisticRead)) {
            return size;
        }
        long lock = this.lock.readLock();
        try {
            return this.map.size();
        } finally {
            this.lock.unlockRead(lock);
        }
    }

    @Override // org.mozilla.javascript.SlotMapContainer
    public void unlockRead(long j2) {
        this.lock.unlockRead(j2);
    }
}
