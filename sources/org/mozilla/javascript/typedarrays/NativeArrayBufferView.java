package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Undefined;

/* loaded from: classes5.dex */
public abstract class NativeArrayBufferView extends IdScriptableObject {
    private static final int Id_buffer = 1;
    private static final int Id_byteLength = 3;
    private static final int Id_byteOffset = 2;
    private static final int MAX_INSTANCE_ID = 3;
    private static final long serialVersionUID = 6884475582973958419L;
    protected final NativeArrayBuffer arrayBuffer;
    protected final int byteLength;
    protected final int offset;

    public NativeArrayBufferView() {
        this.arrayBuffer = NativeArrayBuffer.EMPTY_BUFFER;
        this.offset = 0;
        this.byteLength = 0;
    }

    protected static boolean isArg(Object[] objArr, int i2) {
        return objArr.length > i2 && !Undefined.instance.equals(objArr[i2]);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0025  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findInstanceIdInfo(java.lang.String r4) {
        /*
            r3 = this;
            int r0 = r4.length()
            r1 = 6
            r2 = 0
            if (r0 != r1) goto Lc
            java.lang.String r0 = "buffer"
            r1 = 1
            goto L27
        Lc:
            r1 = 10
            if (r0 != r1) goto L25
            r0 = 4
            char r0 = r4.charAt(r0)
            r1 = 76
            if (r0 != r1) goto L1d
            java.lang.String r0 = "byteLength"
            r1 = 3
            goto L27
        L1d:
            r1 = 79
            if (r0 != r1) goto L25
            java.lang.String r0 = "byteOffset"
            r1 = 2
            goto L27
        L25:
            r0 = 0
            r1 = r2
        L27:
            if (r0 == 0) goto L32
            if (r0 == r4) goto L32
            boolean r0 = r0.equals(r4)
            if (r0 != 0) goto L32
            goto L33
        L32:
            r2 = r1
        L33:
            if (r2 != 0) goto L3a
            int r4 = super.findInstanceIdInfo(r4)
            return r4
        L3a:
            r4 = 5
            int r4 = org.mozilla.javascript.IdScriptableObject.instanceIdInfo(r4, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.typedarrays.NativeArrayBufferView.findInstanceIdInfo(java.lang.String):int");
    }

    public NativeArrayBuffer getBuffer() {
        return this.arrayBuffer;
    }

    public int getByteLength() {
        return this.byteLength;
    }

    public int getByteOffset() {
        return this.offset;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? super.getInstanceIdName(i2) : "byteLength" : "byteOffset" : "buffer";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? super.getInstanceIdValue(i2) : ScriptRuntime.wrapInt(this.byteLength) : ScriptRuntime.wrapInt(this.offset) : this.arrayBuffer;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 3;
    }

    protected NativeArrayBufferView(NativeArrayBuffer nativeArrayBuffer, int i2, int i3) {
        this.offset = i2;
        this.byteLength = i3;
        this.arrayBuffer = nativeArrayBuffer;
    }
}
