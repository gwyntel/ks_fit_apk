package org.mozilla.javascript;

/* loaded from: classes5.dex */
public final class NativeArrayIterator extends ES6Iterator {
    private static final String ITERATOR_TAG = "ArrayIterator";
    private static final long serialVersionUID = 1;
    private Scriptable arrayLike;
    private int index;

    private NativeArrayIterator() {
    }

    static void init(ScriptableObject scriptableObject, boolean z2) {
        ES6Iterator.init(scriptableObject, z2, new NativeArrayIterator(), ITERATOR_TAG);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Array Iterator";
    }

    @Override // org.mozilla.javascript.ES6Iterator
    protected String getTag() {
        return ITERATOR_TAG;
    }

    @Override // org.mozilla.javascript.ES6Iterator
    protected boolean isDone(Context context, Scriptable scriptable) {
        return ((long) this.index) >= NativeArray.getLengthProperty(context, this.arrayLike);
    }

    @Override // org.mozilla.javascript.ES6Iterator
    protected Object nextValue(Context context, Scriptable scriptable) {
        Scriptable scriptable2 = this.arrayLike;
        int i2 = this.index;
        this.index = i2 + 1;
        Object obj = scriptable2.get(i2, scriptable2);
        return obj == Scriptable.NOT_FOUND ? Undefined.instance : obj;
    }

    public NativeArrayIterator(Scriptable scriptable, Scriptable scriptable2) {
        super(scriptable);
        this.index = 0;
        this.arrayLike = scriptable2;
    }
}
