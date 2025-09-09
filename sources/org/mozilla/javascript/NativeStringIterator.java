package org.mozilla.javascript;

/* loaded from: classes5.dex */
public final class NativeStringIterator extends ES6Iterator {
    private static final String ITERATOR_TAG = "StringIterator";
    private static final long serialVersionUID = 1;
    private int index;
    private String string;

    private NativeStringIterator() {
    }

    static void init(ScriptableObject scriptableObject, boolean z2) {
        ES6Iterator.init(scriptableObject, z2, new NativeStringIterator(), ITERATOR_TAG);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "String Iterator";
    }

    @Override // org.mozilla.javascript.ES6Iterator
    protected String getTag() {
        return ITERATOR_TAG;
    }

    @Override // org.mozilla.javascript.ES6Iterator
    protected boolean isDone(Context context, Scriptable scriptable) {
        return this.index >= this.string.length();
    }

    @Override // org.mozilla.javascript.ES6Iterator
    protected Object nextValue(Context context, Scriptable scriptable) {
        int iOffsetByCodePoints = this.string.offsetByCodePoints(this.index, 1);
        String strSubstring = this.string.substring(this.index, iOffsetByCodePoints);
        this.index = iOffsetByCodePoints;
        return strSubstring;
    }

    NativeStringIterator(Scriptable scriptable, Scriptable scriptable2) {
        super(scriptable);
        this.index = 0;
        this.string = ScriptRuntime.toString(scriptable2);
    }
}
