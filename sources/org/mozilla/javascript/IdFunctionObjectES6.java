package org.mozilla.javascript;

import androidx.media3.exoplayer.rtsp.SessionDescription;

/* loaded from: classes5.dex */
public class IdFunctionObjectES6 extends IdFunctionObject {
    private static final int Id_length = 1;
    private static final int Id_name = 3;
    private boolean myLength;
    private boolean myName;

    public IdFunctionObjectES6(IdFunctionCall idFunctionCall, Object obj, int i2, String str, int i3, Scriptable scriptable) {
        super(idFunctionCall, obj, i2, str, i3, scriptable);
        this.myLength = true;
        this.myName = true;
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        return str.equals(SessionDescription.ATTR_LENGTH) ? IdScriptableObject.instanceIdInfo(3, 1) : str.equals("name") ? IdScriptableObject.instanceIdInfo(3, 3) : super.findInstanceIdInfo(str);
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        return (i2 != 1 || this.myLength) ? (i2 != 3 || this.myName) ? super.getInstanceIdValue(i2) : Scriptable.NOT_FOUND : Scriptable.NOT_FOUND;
    }

    @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdValue(int i2, Object obj) throws RuntimeException {
        if (i2 == 1 && obj == Scriptable.NOT_FOUND) {
            this.myLength = false;
        } else if (i2 == 3 && obj == Scriptable.NOT_FOUND) {
            this.myName = false;
        } else {
            super.setInstanceIdValue(i2, obj);
        }
    }
}
