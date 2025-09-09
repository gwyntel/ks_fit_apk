package org.mozilla.javascript;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.mozilla.javascript.ScriptableObject;

/* loaded from: classes5.dex */
public class HashSlotMap implements SlotMap {
    private final LinkedHashMap<Object, ScriptableObject.Slot> map = new LinkedHashMap<>();

    /* renamed from: org.mozilla.javascript.HashSlotMap$1, reason: invalid class name */
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

    private ScriptableObject.Slot createSlot(Object obj, int i2, Object obj2, ScriptableObject.SlotAccess slotAccess) {
        ScriptableObject.Slot slot;
        ScriptableObject.Slot slot2 = this.map.get(obj2);
        if (slot2 == null) {
            ScriptableObject.Slot getterSlot = slotAccess == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER ? new ScriptableObject.GetterSlot(obj, i2, 0) : new ScriptableObject.Slot(obj, i2, 0);
            if (slotAccess == ScriptableObject.SlotAccess.MODIFY_CONST) {
                getterSlot.setAttributes(13);
            }
            addSlot(getterSlot);
            return getterSlot;
        }
        if (slotAccess == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER && !(slot2 instanceof ScriptableObject.GetterSlot)) {
            slot = new ScriptableObject.GetterSlot(obj2, slot2.indexOrHash, slot2.getAttributes());
        } else {
            if (slotAccess != ScriptableObject.SlotAccess.CONVERT_ACCESSOR_TO_DATA || !(slot2 instanceof ScriptableObject.GetterSlot)) {
                if (slotAccess == ScriptableObject.SlotAccess.MODIFY_CONST) {
                    return null;
                }
                return slot2;
            }
            slot = new ScriptableObject.Slot(obj2, slot2.indexOrHash, slot2.getAttributes());
        }
        slot.value = slot2.value;
        this.map.put(obj2, slot);
        return slot;
    }

    @Override // org.mozilla.javascript.SlotMap
    public void addSlot(ScriptableObject.Slot slot) {
        Object objValueOf = slot.name;
        if (objValueOf == null) {
            objValueOf = String.valueOf(slot.indexOrHash);
        }
        this.map.put(objValueOf, slot);
    }

    @Override // org.mozilla.javascript.SlotMap
    public ScriptableObject.Slot get(Object obj, int i2, ScriptableObject.SlotAccess slotAccess) {
        Object objValueOf = obj == null ? String.valueOf(i2) : obj;
        ScriptableObject.Slot slot = this.map.get(objValueOf);
        int i3 = AnonymousClass1.$SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess[slotAccess.ordinal()];
        if (i3 == 1) {
            return slot;
        }
        if (i3 == 2 || i3 == 3) {
            if (slot != null) {
                return slot;
            }
        } else if (i3 != 4) {
            if (i3 == 5 && !(slot instanceof ScriptableObject.GetterSlot)) {
                return slot;
            }
        } else if (slot instanceof ScriptableObject.GetterSlot) {
            return slot;
        }
        return createSlot(obj, i2, objValueOf, slotAccess);
    }

    @Override // org.mozilla.javascript.SlotMap
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.lang.Iterable
    public Iterator<ScriptableObject.Slot> iterator() {
        return this.map.values().iterator();
    }

    @Override // org.mozilla.javascript.SlotMap
    public ScriptableObject.Slot query(Object obj, int i2) {
        if (obj == null) {
            obj = String.valueOf(i2);
        }
        return this.map.get(obj);
    }

    @Override // org.mozilla.javascript.SlotMap
    public void remove(Object obj, int i2) {
        Object objValueOf = obj == null ? String.valueOf(i2) : obj;
        ScriptableObject.Slot slot = this.map.get(objValueOf);
        if (slot != null) {
            if ((slot.getAttributes() & 4) == 0) {
                this.map.remove(objValueOf);
            } else if (Context.getContext().isStrictMode()) {
                throw ScriptRuntime.typeError1("msg.delete.property.with.configurable.false", obj);
            }
        }
    }

    @Override // org.mozilla.javascript.SlotMap
    public int size() {
        return this.map.size();
    }
}
