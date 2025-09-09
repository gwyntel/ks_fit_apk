package org.mozilla.javascript;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ConsString implements CharSequence, Serializable {
    private static final long serialVersionUID = -8432806714471372570L;
    private boolean isFlat = false;
    private CharSequence left;
    private final int length;
    private CharSequence right;

    public ConsString(CharSequence charSequence, CharSequence charSequence2) {
        this.left = charSequence;
        this.right = charSequence2;
        this.length = charSequence.length() + this.right.length();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0043 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:3:0x0001, B:5:0x0005, B:6:0x0015, B:8:0x0019, B:10:0x001f, B:13:0x0024, B:19:0x004b, B:14:0x002c, B:17:0x0043, B:20:0x0059), top: B:25:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized java.lang.String flatten() {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = r6.isFlat     // Catch: java.lang.Throwable -> L22
            if (r0 != 0) goto L59
            int r0 = r6.length     // Catch: java.lang.Throwable -> L22
            char[] r1 = new char[r0]     // Catch: java.lang.Throwable -> L22
            java.util.ArrayDeque r2 = new java.util.ArrayDeque     // Catch: java.lang.Throwable -> L22
            r2.<init>()     // Catch: java.lang.Throwable -> L22
            java.lang.CharSequence r3 = r6.left     // Catch: java.lang.Throwable -> L22
            r2.addFirst(r3)     // Catch: java.lang.Throwable -> L22
            java.lang.CharSequence r3 = r6.right     // Catch: java.lang.Throwable -> L22
        L15:
            boolean r4 = r3 instanceof org.mozilla.javascript.ConsString     // Catch: java.lang.Throwable -> L22
            if (r4 == 0) goto L2c
            org.mozilla.javascript.ConsString r3 = (org.mozilla.javascript.ConsString) r3     // Catch: java.lang.Throwable -> L22
            boolean r4 = r3.isFlat     // Catch: java.lang.Throwable -> L22
            if (r4 == 0) goto L24
            java.lang.CharSequence r3 = r3.left     // Catch: java.lang.Throwable -> L22
            goto L2c
        L22:
            r0 = move-exception
            goto L5f
        L24:
            java.lang.CharSequence r4 = r3.left     // Catch: java.lang.Throwable -> L22
            r2.addFirst(r4)     // Catch: java.lang.Throwable -> L22
            java.lang.CharSequence r3 = r3.right     // Catch: java.lang.Throwable -> L22
            goto L49
        L2c:
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Throwable -> L22
            int r4 = r3.length()     // Catch: java.lang.Throwable -> L22
            int r0 = r0 - r4
            int r4 = r3.length()     // Catch: java.lang.Throwable -> L22
            r5 = 0
            r3.getChars(r5, r4, r1, r0)     // Catch: java.lang.Throwable -> L22
            boolean r3 = r2.isEmpty()     // Catch: java.lang.Throwable -> L22
            if (r3 == 0) goto L43
            r3 = 0
            goto L49
        L43:
            java.lang.Object r3 = r2.removeFirst()     // Catch: java.lang.Throwable -> L22
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch: java.lang.Throwable -> L22
        L49:
            if (r3 != 0) goto L15
            java.lang.String r0 = new java.lang.String     // Catch: java.lang.Throwable -> L22
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L22
            r6.left = r0     // Catch: java.lang.Throwable -> L22
            java.lang.String r0 = ""
            r6.right = r0     // Catch: java.lang.Throwable -> L22
            r0 = 1
            r6.isFlat = r0     // Catch: java.lang.Throwable -> L22
        L59:
            java.lang.CharSequence r0 = r6.left     // Catch: java.lang.Throwable -> L22
            java.lang.String r0 = (java.lang.String) r0     // Catch: java.lang.Throwable -> L22
            monitor-exit(r6)
            return r0
        L5f:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L22
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ConsString.flatten():java.lang.String");
    }

    private Object writeReplace() {
        return toString();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i2) {
        return (this.isFlat ? (String) this.left : flatten()).charAt(i2);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.length;
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i2, int i3) {
        return (this.isFlat ? (String) this.left : flatten()).substring(i2, i3);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return this.isFlat ? (String) this.left : flatten();
    }
}
