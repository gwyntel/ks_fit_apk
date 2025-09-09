package androidx.recyclerview.widget;

import androidx.recyclerview.widget.AdapterHelper;
import java.util.List;

/* loaded from: classes2.dex */
class OpReorderer {

    /* renamed from: a, reason: collision with root package name */
    final Callback f6012a;

    interface Callback {
        AdapterHelper.UpdateOp obtainUpdateOp(int i2, int i3, int i4, Object obj);

        void recycleUpdateOp(AdapterHelper.UpdateOp updateOp);
    }

    OpReorderer(Callback callback) {
        this.f6012a = callback;
    }

    private int getLastMoveOutOfOrder(List<AdapterHelper.UpdateOp> list) {
        boolean z2 = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).f5790a != 8) {
                z2 = true;
            } else if (z2) {
                return size;
            }
        }
        return -1;
    }

    private void swapMoveAdd(List<AdapterHelper.UpdateOp> list, int i2, AdapterHelper.UpdateOp updateOp, int i3, AdapterHelper.UpdateOp updateOp2) {
        int i4 = updateOp.f5793d;
        int i5 = updateOp2.f5791b;
        int i6 = i4 < i5 ? -1 : 0;
        int i7 = updateOp.f5791b;
        if (i7 < i5) {
            i6++;
        }
        if (i5 <= i7) {
            updateOp.f5791b = i7 + updateOp2.f5793d;
        }
        int i8 = updateOp2.f5791b;
        if (i8 <= i4) {
            updateOp.f5793d = i4 + updateOp2.f5793d;
        }
        updateOp2.f5791b = i8 + i6;
        list.set(i2, updateOp2);
        list.set(i3, updateOp);
    }

    private void swapMoveOp(List<AdapterHelper.UpdateOp> list, int i2, int i3) {
        AdapterHelper.UpdateOp updateOp = list.get(i2);
        AdapterHelper.UpdateOp updateOp2 = list.get(i3);
        int i4 = updateOp2.f5790a;
        if (i4 == 1) {
            swapMoveAdd(list, i2, updateOp, i3, updateOp2);
        } else if (i4 == 2) {
            b(list, i2, updateOp, i3, updateOp2);
        } else {
            if (i4 != 4) {
                return;
            }
            c(list, i2, updateOp, i3, updateOp2);
        }
    }

    void a(List list) {
        while (true) {
            int lastMoveOutOfOrder = getLastMoveOutOfOrder(list);
            if (lastMoveOutOfOrder == -1) {
                return;
            } else {
                swapMoveOp(list, lastMoveOutOfOrder, lastMoveOutOfOrder + 1);
            }
        }
    }

    void b(List list, int i2, AdapterHelper.UpdateOp updateOp, int i3, AdapterHelper.UpdateOp updateOp2) {
        boolean z2;
        int i4 = updateOp.f5791b;
        int i5 = updateOp.f5793d;
        boolean z3 = false;
        if (i4 < i5) {
            if (updateOp2.f5791b == i4 && updateOp2.f5793d == i5 - i4) {
                z2 = false;
                z3 = true;
            } else {
                z2 = false;
            }
        } else if (updateOp2.f5791b == i5 + 1 && updateOp2.f5793d == i4 - i5) {
            z2 = true;
            z3 = true;
        } else {
            z2 = true;
        }
        int i6 = updateOp2.f5791b;
        if (i5 < i6) {
            updateOp2.f5791b = i6 - 1;
        } else {
            int i7 = updateOp2.f5793d;
            if (i5 < i6 + i7) {
                updateOp2.f5793d = i7 - 1;
                updateOp.f5790a = 2;
                updateOp.f5793d = 1;
                if (updateOp2.f5793d == 0) {
                    list.remove(i3);
                    this.f6012a.recycleUpdateOp(updateOp2);
                    return;
                }
                return;
            }
        }
        int i8 = updateOp.f5791b;
        int i9 = updateOp2.f5791b;
        AdapterHelper.UpdateOp updateOpObtainUpdateOp = null;
        if (i8 <= i9) {
            updateOp2.f5791b = i9 + 1;
        } else {
            int i10 = updateOp2.f5793d;
            if (i8 < i9 + i10) {
                updateOpObtainUpdateOp = this.f6012a.obtainUpdateOp(2, i8 + 1, (i9 + i10) - i8, null);
                updateOp2.f5793d = updateOp.f5791b - updateOp2.f5791b;
            }
        }
        if (z3) {
            list.set(i2, updateOp2);
            list.remove(i3);
            this.f6012a.recycleUpdateOp(updateOp);
            return;
        }
        if (z2) {
            if (updateOpObtainUpdateOp != null) {
                int i11 = updateOp.f5791b;
                if (i11 > updateOpObtainUpdateOp.f5791b) {
                    updateOp.f5791b = i11 - updateOpObtainUpdateOp.f5793d;
                }
                int i12 = updateOp.f5793d;
                if (i12 > updateOpObtainUpdateOp.f5791b) {
                    updateOp.f5793d = i12 - updateOpObtainUpdateOp.f5793d;
                }
            }
            int i13 = updateOp.f5791b;
            if (i13 > updateOp2.f5791b) {
                updateOp.f5791b = i13 - updateOp2.f5793d;
            }
            int i14 = updateOp.f5793d;
            if (i14 > updateOp2.f5791b) {
                updateOp.f5793d = i14 - updateOp2.f5793d;
            }
        } else {
            if (updateOpObtainUpdateOp != null) {
                int i15 = updateOp.f5791b;
                if (i15 >= updateOpObtainUpdateOp.f5791b) {
                    updateOp.f5791b = i15 - updateOpObtainUpdateOp.f5793d;
                }
                int i16 = updateOp.f5793d;
                if (i16 >= updateOpObtainUpdateOp.f5791b) {
                    updateOp.f5793d = i16 - updateOpObtainUpdateOp.f5793d;
                }
            }
            int i17 = updateOp.f5791b;
            if (i17 >= updateOp2.f5791b) {
                updateOp.f5791b = i17 - updateOp2.f5793d;
            }
            int i18 = updateOp.f5793d;
            if (i18 >= updateOp2.f5791b) {
                updateOp.f5793d = i18 - updateOp2.f5793d;
            }
        }
        list.set(i2, updateOp2);
        if (updateOp.f5791b != updateOp.f5793d) {
            list.set(i3, updateOp);
        } else {
            list.remove(i3);
        }
        if (updateOpObtainUpdateOp != null) {
            list.add(i2, updateOpObtainUpdateOp);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void c(java.util.List r9, int r10, androidx.recyclerview.widget.AdapterHelper.UpdateOp r11, int r12, androidx.recyclerview.widget.AdapterHelper.UpdateOp r13) {
        /*
            r8 = this;
            int r0 = r11.f5793d
            int r1 = r13.f5791b
            r2 = 4
            r3 = 1
            r4 = 0
            if (r0 >= r1) goto Ld
            int r1 = r1 - r3
            r13.f5791b = r1
            goto L20
        Ld:
            int r5 = r13.f5793d
            int r1 = r1 + r5
            if (r0 >= r1) goto L20
            int r5 = r5 - r3
            r13.f5793d = r5
            androidx.recyclerview.widget.OpReorderer$Callback r0 = r8.f6012a
            int r1 = r11.f5791b
            java.lang.Object r5 = r13.f5792c
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r0 = r0.obtainUpdateOp(r2, r1, r3, r5)
            goto L21
        L20:
            r0 = r4
        L21:
            int r1 = r11.f5791b
            int r5 = r13.f5791b
            if (r1 > r5) goto L2b
            int r5 = r5 + r3
            r13.f5791b = r5
            goto L41
        L2b:
            int r6 = r13.f5793d
            int r7 = r5 + r6
            if (r1 >= r7) goto L41
            int r5 = r5 + r6
            int r5 = r5 - r1
            androidx.recyclerview.widget.OpReorderer$Callback r4 = r8.f6012a
            int r1 = r1 + r3
            java.lang.Object r3 = r13.f5792c
            androidx.recyclerview.widget.AdapterHelper$UpdateOp r4 = r4.obtainUpdateOp(r2, r1, r5, r3)
            int r1 = r13.f5793d
            int r1 = r1 - r5
            r13.f5793d = r1
        L41:
            r9.set(r12, r11)
            int r11 = r13.f5793d
            if (r11 <= 0) goto L4c
            r9.set(r10, r13)
            goto L54
        L4c:
            r9.remove(r10)
            androidx.recyclerview.widget.OpReorderer$Callback r11 = r8.f6012a
            r11.recycleUpdateOp(r13)
        L54:
            if (r0 == 0) goto L59
            r9.add(r10, r0)
        L59:
            if (r4 == 0) goto L5e
            r9.add(r10, r4)
        L5e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.OpReorderer.c(java.util.List, int, androidx.recyclerview.widget.AdapterHelper$UpdateOp, int, androidx.recyclerview.widget.AdapterHelper$UpdateOp):void");
    }
}
