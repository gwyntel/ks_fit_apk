package androidx.core.content;

import androidx.core.util.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class d implements Predicate {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f3504a;

    @Override // androidx.core.util.Predicate
    public /* synthetic */ Predicate and(Predicate predicate) {
        return androidx.core.util.k.a(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public /* synthetic */ Predicate negate() {
        return androidx.core.util.k.b(this);
    }

    @Override // androidx.core.util.Predicate
    public /* synthetic */ Predicate or(Predicate predicate) {
        return androidx.core.util.k.c(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final boolean test(Object obj) {
        return this.f3504a.equals((String) obj);
    }
}
