package com.alipay.sdk.m.x;

import java.util.Iterator;
import java.util.Stack;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    public Stack<e> f9930a = new Stack<>();

    public void a(e eVar) {
        this.f9930a.push(eVar);
    }

    public boolean b() {
        return this.f9930a.isEmpty();
    }

    public e c() {
        return this.f9930a.pop();
    }

    public void a() {
        if (b()) {
            return;
        }
        Iterator<e> it = this.f9930a.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
        this.f9930a.clear();
    }
}
