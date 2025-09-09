package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.state.helpers.Facade;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes.dex */
public class HelperReference extends ConstraintReference implements Facade {

    /* renamed from: c0, reason: collision with root package name */
    protected final State f2836c0;

    /* renamed from: d0, reason: collision with root package name */
    final State.Helper f2837d0;

    /* renamed from: e0, reason: collision with root package name */
    protected ArrayList f2838e0;
    private HelperWidget mHelperWidget;

    public HelperReference(State state, State.Helper helper) {
        super(state);
        this.f2838e0 = new ArrayList();
        this.f2836c0 = state;
        this.f2837d0 = helper;
    }

    public HelperReference add(Object... objArr) {
        Collections.addAll(this.f2838e0, objArr);
        return this;
    }

    @Override // androidx.constraintlayout.core.state.ConstraintReference, androidx.constraintlayout.core.state.Reference
    public void apply() {
    }

    @Override // androidx.constraintlayout.core.state.ConstraintReference, androidx.constraintlayout.core.state.Reference
    public ConstraintWidget getConstraintWidget() {
        return getHelperWidget();
    }

    public HelperWidget getHelperWidget() {
        return this.mHelperWidget;
    }

    public State.Helper getType() {
        return this.f2837d0;
    }

    public void setHelperWidget(HelperWidget helperWidget) {
        this.mHelperWidget = helperWidget;
    }
}
