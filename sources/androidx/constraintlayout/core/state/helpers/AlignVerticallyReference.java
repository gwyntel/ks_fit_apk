package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import java.util.Iterator;

/* loaded from: classes.dex */
public class AlignVerticallyReference extends HelperReference {
    private float mBias;

    public AlignVerticallyReference(State state) {
        super(state, State.Helper.ALIGN_VERTICALLY);
        this.mBias = 0.5f;
    }

    @Override // androidx.constraintlayout.core.state.HelperReference, androidx.constraintlayout.core.state.ConstraintReference, androidx.constraintlayout.core.state.Reference
    public void apply() {
        Iterator it = this.f2838e0.iterator();
        while (it.hasNext()) {
            ConstraintReference constraintReferenceConstraints = this.f2836c0.constraints(it.next());
            constraintReferenceConstraints.clearVertical();
            Object obj = this.R;
            if (obj != null) {
                constraintReferenceConstraints.topToTop(obj);
            } else {
                Object obj2 = this.S;
                if (obj2 != null) {
                    constraintReferenceConstraints.topToBottom(obj2);
                } else {
                    constraintReferenceConstraints.topToTop(State.PARENT);
                }
            }
            Object obj3 = this.T;
            if (obj3 != null) {
                constraintReferenceConstraints.bottomToTop(obj3);
            } else {
                Object obj4 = this.U;
                if (obj4 != null) {
                    constraintReferenceConstraints.bottomToBottom(obj4);
                } else {
                    constraintReferenceConstraints.bottomToBottom(State.PARENT);
                }
            }
            float f2 = this.mBias;
            if (f2 != 0.5f) {
                constraintReferenceConstraints.verticalBias(f2);
            }
        }
    }
}
