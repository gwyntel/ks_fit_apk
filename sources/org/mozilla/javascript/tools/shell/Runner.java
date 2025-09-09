package org.mozilla.javascript.tools.shell;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

/* loaded from: classes5.dex */
class Runner implements Runnable, ContextAction {
    private Object[] args;

    /* renamed from: f, reason: collision with root package name */
    private Function f26767f;
    ContextFactory factory;

    /* renamed from: s, reason: collision with root package name */
    private Script f26768s;
    private Scriptable scope;

    Runner(Scriptable scriptable, Function function, Object[] objArr) {
        this.scope = scriptable;
        this.f26767f = function;
        this.args = objArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.factory.call(this);
    }

    @Override // org.mozilla.javascript.ContextAction
    public Object run(Context context) {
        Function function = this.f26767f;
        if (function == null) {
            return this.f26768s.exec(context, this.scope);
        }
        Scriptable scriptable = this.scope;
        return function.call(context, scriptable, scriptable, this.args);
    }

    Runner(Scriptable scriptable, Script script) {
        this.scope = scriptable;
        this.f26768s = script;
    }
}
