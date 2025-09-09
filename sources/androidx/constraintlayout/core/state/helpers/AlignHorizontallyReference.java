package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import java.util.Iterator;

/* loaded from: classes.dex */
public class AlignHorizontallyReference extends HelperReference {
    private float mBias;

    public AlignHorizontallyReference(State state) {
        super(state, State.Helper.ALIGN_VERTICALLY);
        this.mBias = 0.5f;
    }

    @Override // androidx.constraintlayout.core.state.HelperReference, androidx.constraintlayout.core.state.ConstraintReference, androidx.constraintlayout.core.state.Reference
    public void apply() {
        Iterator it = this.f2838e0.iterator();
        while (it.hasNext()) {
            ConstraintReference constraintReferenceConstraints = this.f2836c0.constraints(it.next());
            constraintReferenceConstraints.clearHorizontal();
            Object obj = this.N;
            if (obj != null) {
                constraintReferenceConstraints.startToStart(obj);
            } else {
                Object obj2 = this.O;
                if (obj2 != null) {
                    constraintReferenceConstraints.startToEnd(obj2);
                } else {
                    constraintReferenceConstraints.startToStart(State.PARENT);
                }
            }
            Object obj3 = this.P;
            if (obj3 != null) {
                constraintReferenceConstraints.endToStart(obj3);
            } else {
                Object obj4 = this.Q;
                if (obj4 != null) {
                    constraintReferenceConstraints.endToEnd(obj4);
                } else {
                    constraintReferenceConstraints.endToEnd(State.PARENT);
                }
            }
            float f2 = this.mBias;
            if (f2 != 0.5f) {
                constraintReferenceConstraints.horizontalBias(f2);
            }
        }
    }
}
