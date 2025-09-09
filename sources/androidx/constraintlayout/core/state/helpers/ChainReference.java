package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;

/* loaded from: classes.dex */
public class ChainReference extends HelperReference {

    /* renamed from: f0, reason: collision with root package name */
    protected float f2863f0;

    /* renamed from: g0, reason: collision with root package name */
    protected State.Chain f2864g0;

    public ChainReference(State state, State.Helper helper) {
        super(state, helper);
        this.f2863f0 = 0.5f;
        this.f2864g0 = State.Chain.SPREAD;
    }

    public float getBias() {
        return this.f2863f0;
    }

    public State.Chain getStyle() {
        return State.Chain.SPREAD;
    }

    public ChainReference style(State.Chain chain) {
        this.f2864g0 = chain;
        return this;
    }

    @Override // androidx.constraintlayout.core.state.ConstraintReference
    public ChainReference bias(float f2) {
        this.f2863f0 = f2;
        return this;
    }
}
