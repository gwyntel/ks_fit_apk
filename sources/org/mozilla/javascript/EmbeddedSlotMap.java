package org.mozilla.javascript;

import java.util.Iterator;
import org.mozilla.javascript.ScriptableObject;

/* loaded from: classes5.dex */
public class EmbeddedSlotMap implements SlotMap {
    private static final int INITIAL_SLOT_SIZE = 4;
    private int count;
    private ScriptableObject.Slot firstAdded;
    private ScriptableObject.Slot lastAdded;
    private ScriptableObject.Slot[] slots;

    /* renamed from: org.mozilla.javascript.EmbeddedSlotMap$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess;

        static {
            int[] iArr = new int[ScriptableObject.SlotAccess.values().length];
            $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess = iArr;
            try {
                iArr[ScriptableObject.SlotAccess.QUERY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess[ScriptableObject.SlotAccess.MODIFY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess[ScriptableObject.SlotAccess.MODIFY_CONST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess[ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess[ScriptableObject.SlotAccess.CONVERT_ACCESSOR_TO_DATA.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static final class Iter implements Iterator<ScriptableObject.Slot> {
        private ScriptableObject.Slot next;

        Iter(ScriptableObject.Slot slot) {
            this.next = slot;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next != null;
        }

        @Override // java.util.Iterator
        public ScriptableObject.Slot next() {
            ScriptableObject.Slot slot = this.next;
            this.next = slot.orderedNext;
            return slot;
        }
    }

    private void addKnownAbsentSlot(ScriptableObject.Slot[] slotArr, ScriptableObject.Slot slot) {
        int slotIndex = getSlotIndex(slotArr.length, slot.indexOrHash);
        ScriptableObject.Slot slot2 = slotArr[slotIndex];
        slotArr[slotIndex] = slot;
        slot.next = slot2;
    }

    private void copyTable(ScriptableObject.Slot[] slotArr, ScriptableObject.Slot[] slotArr2) {
        for (ScriptableObject.Slot slot : slotArr) {
            while (slot != null) {
                ScriptableObject.Slot slot2 = slot.next;
                slot.next = null;
                addKnownAbsentSlot(slotArr2, slot);
                slot = slot2;
            }
        }
    }

    private ScriptableObject.Slot createSlot(Object obj, int i2, ScriptableObject.SlotAccess slotAccess, ScriptableObject.Slot slot) {
        ScriptableObject.Slot slot2;
        Object obj2;
        if (this.count == 0) {
            this.slots = new ScriptableObject.Slot[4];
        } else if (slot != null) {
            int slotIndex = getSlotIndex(this.slots.length, i2);
            ScriptableObject.Slot slot3 = this.slots[slotIndex];
            ScriptableObject.Slot slot4 = slot3;
            while (slot3 != null && (slot3.indexOrHash != i2 || ((obj2 = slot3.name) != obj && (obj == null || !obj.equals(obj2))))) {
                slot4 = slot3;
                slot3 = slot3.next;
            }
            if (slot3 != null) {
                if (slotAccess == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER && !(slot3 instanceof ScriptableObject.GetterSlot)) {
                    slot2 = new ScriptableObject.GetterSlot(obj, i2, slot3.getAttributes());
                } else {
                    if (slotAccess != ScriptableObject.SlotAccess.CONVERT_ACCESSOR_TO_DATA || !(slot3 instanceof ScriptableObject.GetterSlot)) {
                        if (slotAccess == ScriptableObject.SlotAccess.MODIFY_CONST) {
                            return null;
                        }
                        return slot3;
                    }
                    slot2 = new ScriptableObject.Slot(obj, i2, slot3.getAttributes());
                }
                slot2.value = slot3.value;
                slot2.next = slot3.next;
                ScriptableObject.Slot slot5 = this.firstAdded;
                if (slot3 == slot5) {
                    this.firstAdded = slot2;
                } else {
                    while (slot5 != null) {
                        ScriptableObject.Slot slot6 = slot5.orderedNext;
                        if (slot6 == slot3) {
                            break;
                        }
                        slot5 = slot6;
                    }
                    if (slot5 != null) {
                        slot5.orderedNext = slot2;
                    }
                }
                slot2.orderedNext = slot3.orderedNext;
                if (slot3 == this.lastAdded) {
                    this.lastAdded = slot2;
                }
                if (slot4 == slot3) {
                    this.slots[slotIndex] = slot2;
                } else {
                    slot4.next = slot2;
                }
                return slot2;
            }
        }
        int i3 = (this.count + 1) * 4;
        ScriptableObject.Slot[] slotArr = this.slots;
        if (i3 > slotArr.length * 3) {
            ScriptableObject.Slot[] slotArr2 = new ScriptableObject.Slot[slotArr.length * 2];
            copyTable(slotArr, slotArr2);
            this.slots = slotArr2;
        }
        ScriptableObject.Slot getterSlot = slotAccess == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER ? new ScriptableObject.GetterSlot(obj, i2, 0) : new ScriptableObject.Slot(obj, i2, 0);
        if (slotAccess == ScriptableObject.SlotAccess.MODIFY_CONST) {
            getterSlot.setAttributes(13);
        }
        insertNewSlot(getterSlot);
        return getterSlot;
    }

    private static int getSlotIndex(int i2, int i3) {
        return (i2 - 1) & i3;
    }

    private void insertNewSlot(ScriptableObject.Slot slot) {
        this.count++;
        ScriptableObject.Slot slot2 = this.lastAdded;
        if (slot2 != null) {
            slot2.orderedNext = slot;
        }
        if (this.firstAdded == null) {
            this.firstAdded = slot;
        }
        this.lastAdded = slot;
        addKnownAbsentSlot(this.slots, slot);
    }

    @Override // org.mozilla.javascript.SlotMap
    public void addSlot(ScriptableObject.Slot slot) {
        if (this.slots == null) {
            this.slots = new ScriptableObject.Slot[4];
        }
        insertNewSlot(slot);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0056, code lost:
    
        if (r1 != null) goto L39;
     */
    @Override // org.mozilla.javascript.SlotMap
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.mozilla.javascript.ScriptableObject.Slot get(java.lang.Object r4, int r5, org.mozilla.javascript.ScriptableObject.SlotAccess r6) {
        /*
            r3 = this;
            org.mozilla.javascript.ScriptableObject$Slot[] r0 = r3.slots
            r1 = 0
            if (r0 != 0) goto La
            org.mozilla.javascript.ScriptableObject$SlotAccess r0 = org.mozilla.javascript.ScriptableObject.SlotAccess.QUERY
            if (r6 != r0) goto La
            return r1
        La:
            if (r4 == 0) goto L10
            int r5 = r4.hashCode()
        L10:
            org.mozilla.javascript.ScriptableObject$Slot[] r0 = r3.slots
            if (r0 == 0) goto L59
            int r0 = r0.length
            int r0 = getSlotIndex(r0, r5)
            org.mozilla.javascript.ScriptableObject$Slot[] r1 = r3.slots
            r0 = r1[r0]
            r1 = r0
        L1e:
            if (r1 == 0) goto L34
            java.lang.Object r0 = r1.name
            int r2 = r1.indexOrHash
            if (r5 != r2) goto L31
            if (r0 == r4) goto L34
            if (r4 == 0) goto L31
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L31
            goto L34
        L31:
            org.mozilla.javascript.ScriptableObject$Slot r1 = r1.next
            goto L1e
        L34:
            int[] r0 = org.mozilla.javascript.EmbeddedSlotMap.AnonymousClass1.$SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess
            int r2 = r6.ordinal()
            r0 = r0[r2]
            r2 = 1
            if (r0 == r2) goto L58
            r2 = 2
            if (r0 == r2) goto L56
            r2 = 3
            if (r0 == r2) goto L56
            r2 = 4
            if (r0 == r2) goto L51
            r2 = 5
            if (r0 == r2) goto L4c
            goto L59
        L4c:
            boolean r0 = r1 instanceof org.mozilla.javascript.ScriptableObject.GetterSlot
            if (r0 != 0) goto L59
            return r1
        L51:
            boolean r0 = r1 instanceof org.mozilla.javascript.ScriptableObject.GetterSlot
            if (r0 == 0) goto L59
            return r1
        L56:
            if (r1 == 0) goto L59
        L58:
            return r1
        L59:
            org.mozilla.javascript.ScriptableObject$Slot r4 = r3.createSlot(r4, r5, r6, r1)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.EmbeddedSlotMap.get(java.lang.Object, int, org.mozilla.javascript.ScriptableObject$SlotAccess):org.mozilla.javascript.ScriptableObject$Slot");
    }

    @Override // org.mozilla.javascript.SlotMap
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override // java.lang.Iterable
    public Iterator<ScriptableObject.Slot> iterator() {
        return new Iter(this.firstAdded);
    }

    @Override // org.mozilla.javascript.SlotMap
    public ScriptableObject.Slot query(Object obj, int i2) {
        if (this.slots == null) {
            return null;
        }
        if (obj != null) {
            i2 = obj.hashCode();
        }
        for (ScriptableObject.Slot slot = this.slots[getSlotIndex(this.slots.length, i2)]; slot != null; slot = slot.next) {
            Object obj2 = slot.name;
            if (i2 == slot.indexOrHash && (obj2 == obj || (obj != null && obj.equals(obj2)))) {
                return slot;
            }
        }
        return null;
    }

    @Override // org.mozilla.javascript.SlotMap
    public void remove(Object obj, int i2) {
        Object obj2;
        if (obj != null) {
            i2 = obj.hashCode();
        }
        if (this.count != 0) {
            int slotIndex = getSlotIndex(this.slots.length, i2);
            ScriptableObject.Slot slot = this.slots[slotIndex];
            ScriptableObject.Slot slot2 = slot;
            while (slot != null && (slot.indexOrHash != i2 || ((obj2 = slot.name) != obj && (obj == null || !obj.equals(obj2))))) {
                slot2 = slot;
                slot = slot.next;
            }
            if (slot != null) {
                if ((slot.getAttributes() & 4) != 0) {
                    if (Context.getContext().isStrictMode()) {
                        throw ScriptRuntime.typeError1("msg.delete.property.with.configurable.false", obj);
                    }
                    return;
                }
                this.count--;
                if (slot2 == slot) {
                    this.slots[slotIndex] = slot.next;
                } else {
                    slot2.next = slot.next;
                }
                ScriptableObject.Slot slot3 = this.firstAdded;
                if (slot == slot3) {
                    this.firstAdded = slot.orderedNext;
                    slot3 = null;
                } else {
                    while (true) {
                        ScriptableObject.Slot slot4 = slot3.orderedNext;
                        if (slot4 == slot) {
                            break;
                        } else {
                            slot3 = slot4;
                        }
                    }
                    slot3.orderedNext = slot.orderedNext;
                }
                if (slot == this.lastAdded) {
                    this.lastAdded = slot3;
                }
            }
        }
    }

    @Override // org.mozilla.javascript.SlotMap
    public int size() {
        return this.count;
    }
}
