package org.mozilla.javascript;

import org.mozilla.javascript.ScriptableObject;

/* loaded from: classes5.dex */
public interface SlotMap extends Iterable<ScriptableObject.Slot> {
    void addSlot(ScriptableObject.Slot slot);

    ScriptableObject.Slot get(Object obj, int i2, ScriptableObject.SlotAccess slotAccess);

    boolean isEmpty();

    ScriptableObject.Slot query(Object obj, int i2);

    void remove(Object obj, int i2);

    int size();
}
