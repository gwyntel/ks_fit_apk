package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.motion.widget.ViewTransition;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.SharedValues;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ViewTransitionController {

    /* renamed from: a, reason: collision with root package name */
    ArrayList f3155a;
    private final MotionLayout mMotionLayout;
    private HashSet<View> mRelatedViews;
    private ArrayList<ViewTransition> viewTransitions = new ArrayList<>();
    private String TAG = "ViewTransitionController";

    /* renamed from: b, reason: collision with root package name */
    ArrayList f3156b = new ArrayList();

    public ViewTransitionController(MotionLayout motionLayout) {
        this.mMotionLayout = motionLayout;
    }

    private void listenForSharedVariable(final ViewTransition viewTransition, final boolean z2) {
        final int sharedValueID = viewTransition.getSharedValueID();
        final int sharedValue = viewTransition.getSharedValue();
        ConstraintLayout.getSharedValues().addListener(viewTransition.getSharedValueID(), new SharedValues.SharedValuesListener() { // from class: androidx.constraintlayout.motion.widget.ViewTransitionController.1
            @Override // androidx.constraintlayout.widget.SharedValues.SharedValuesListener
            public void onNewValue(int i2, int i3, int i4) {
                int sharedValueCurrent = viewTransition.getSharedValueCurrent();
                viewTransition.setSharedValueCurrent(i3);
                if (sharedValueID != i2 || sharedValueCurrent == i3) {
                    return;
                }
                if (z2) {
                    if (sharedValue == i3) {
                        int childCount = ViewTransitionController.this.mMotionLayout.getChildCount();
                        for (int i5 = 0; i5 < childCount; i5++) {
                            View childAt = ViewTransitionController.this.mMotionLayout.getChildAt(i5);
                            if (viewTransition.h(childAt)) {
                                int currentState = ViewTransitionController.this.mMotionLayout.getCurrentState();
                                ConstraintSet constraintSet = ViewTransitionController.this.mMotionLayout.getConstraintSet(currentState);
                                ViewTransition viewTransition2 = viewTransition;
                                ViewTransitionController viewTransitionController = ViewTransitionController.this;
                                viewTransition2.c(viewTransitionController, viewTransitionController.mMotionLayout, currentState, constraintSet, childAt);
                            }
                        }
                        return;
                    }
                    return;
                }
                if (sharedValue != i3) {
                    int childCount2 = ViewTransitionController.this.mMotionLayout.getChildCount();
                    for (int i6 = 0; i6 < childCount2; i6++) {
                        View childAt2 = ViewTransitionController.this.mMotionLayout.getChildAt(i6);
                        if (viewTransition.h(childAt2)) {
                            int currentState2 = ViewTransitionController.this.mMotionLayout.getCurrentState();
                            ConstraintSet constraintSet2 = ViewTransitionController.this.mMotionLayout.getConstraintSet(currentState2);
                            ViewTransition viewTransition3 = viewTransition;
                            ViewTransitionController viewTransitionController2 = ViewTransitionController.this;
                            viewTransition3.c(viewTransitionController2, viewTransitionController2.mMotionLayout, currentState2, constraintSet2, childAt2);
                        }
                    }
                }
            }
        });
    }

    private void viewTransition(ViewTransition viewTransition, View... viewArr) {
        int currentState = this.mMotionLayout.getCurrentState();
        if (viewTransition.f3137a == 2) {
            viewTransition.c(this, this.mMotionLayout, currentState, null, viewArr);
            return;
        }
        if (currentState != -1) {
            ConstraintSet constraintSet = this.mMotionLayout.getConstraintSet(currentState);
            if (constraintSet == null) {
                return;
            }
            viewTransition.c(this, this.mMotionLayout, currentState, constraintSet, viewArr);
            return;
        }
        Log.w(this.TAG, "No support for ViewTransition within transition yet. Currently: " + this.mMotionLayout.toString());
    }

    public void add(ViewTransition viewTransition) {
        this.viewTransitions.add(viewTransition);
        this.mRelatedViews = null;
        if (viewTransition.getStateTransition() == 4) {
            listenForSharedVariable(viewTransition, true);
        } else if (viewTransition.getStateTransition() == 5) {
            listenForSharedVariable(viewTransition, false);
        }
    }

    void b(ViewTransition.Animate animate) {
        if (this.f3155a == null) {
            this.f3155a = new ArrayList();
        }
        this.f3155a.add(animate);
    }

    void c() {
        ArrayList arrayList = this.f3155a;
        if (arrayList == null) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((ViewTransition.Animate) it.next()).a();
        }
        this.f3155a.removeAll(this.f3156b);
        this.f3156b.clear();
        if (this.f3155a.isEmpty()) {
            this.f3155a = null;
        }
    }

    boolean d(int i2, MotionController motionController) {
        Iterator<ViewTransition> it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition next = it.next();
            if (next.e() == i2) {
                next.f3138b.addAllFrames(motionController);
                return true;
            }
        }
        return false;
    }

    void e(int i2, boolean z2) {
        Iterator<ViewTransition> it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition next = it.next();
            if (next.e() == i2) {
                next.i(z2);
                return;
            }
        }
    }

    void f() {
        this.mMotionLayout.invalidate();
    }

    boolean g(int i2) {
        Iterator<ViewTransition> it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition next = it.next();
            if (next.e() == i2) {
                return next.g();
            }
        }
        return false;
    }

    void h(ViewTransition.Animate animate) {
        this.f3156b.add(animate);
    }

    void i(MotionEvent motionEvent) {
        ViewTransition viewTransition;
        int currentState = this.mMotionLayout.getCurrentState();
        if (currentState == -1) {
            return;
        }
        if (this.mRelatedViews == null) {
            this.mRelatedViews = new HashSet<>();
            Iterator<ViewTransition> it = this.viewTransitions.iterator();
            while (it.hasNext()) {
                ViewTransition next = it.next();
                int childCount = this.mMotionLayout.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = this.mMotionLayout.getChildAt(i2);
                    if (next.h(childAt)) {
                        childAt.getId();
                        this.mRelatedViews.add(childAt);
                    }
                }
            }
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        Rect rect = new Rect();
        int action = motionEvent.getAction();
        ArrayList arrayList = this.f3155a;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator it2 = this.f3155a.iterator();
            while (it2.hasNext()) {
                ((ViewTransition.Animate) it2.next()).reactTo(action, x2, y2);
            }
        }
        if (action == 0 || action == 1) {
            ConstraintSet constraintSet = this.mMotionLayout.getConstraintSet(currentState);
            Iterator<ViewTransition> it3 = this.viewTransitions.iterator();
            while (it3.hasNext()) {
                ViewTransition next2 = it3.next();
                if (next2.j(action)) {
                    Iterator<View> it4 = this.mRelatedViews.iterator();
                    while (it4.hasNext()) {
                        View next3 = it4.next();
                        if (next2.h(next3)) {
                            next3.getHitRect(rect);
                            if (rect.contains((int) x2, (int) y2)) {
                                viewTransition = next2;
                                next2.c(this, this.mMotionLayout, currentState, constraintSet, next3);
                            } else {
                                viewTransition = next2;
                            }
                            next2 = viewTransition;
                        }
                    }
                }
            }
        }
    }

    void j(int i2, View... viewArr) {
        ArrayList arrayList = new ArrayList();
        Iterator<ViewTransition> it = this.viewTransitions.iterator();
        ViewTransition viewTransition = null;
        while (it.hasNext()) {
            ViewTransition next = it.next();
            if (next.e() == i2) {
                for (View view : viewArr) {
                    if (next.d(view)) {
                        arrayList.add(view);
                    }
                }
                if (!arrayList.isEmpty()) {
                    viewTransition(next, (View[]) arrayList.toArray(new View[0]));
                    arrayList.clear();
                }
                viewTransition = next;
            }
        }
        if (viewTransition == null) {
            Log.e(this.TAG, " Could not find ViewTransition");
        }
    }
}
