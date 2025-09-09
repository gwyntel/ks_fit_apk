package androidx.constraintlayout.motion.widget;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.HashMap;

/* loaded from: classes.dex */
public class DesignTool implements ProxyInterface {
    private static final boolean DEBUG = false;
    private static final String TAG = "DesignTool";

    /* renamed from: a, reason: collision with root package name */
    static final HashMap f2995a;

    /* renamed from: b, reason: collision with root package name */
    static final HashMap f2996b;
    private final MotionLayout mMotionLayout;
    private MotionScene mSceneCache;
    private String mLastStartState = null;
    private String mLastEndState = null;
    private int mLastStartStateId = -1;
    private int mLastEndStateId = -1;

    static {
        HashMap map = new HashMap();
        f2995a = map;
        HashMap map2 = new HashMap();
        f2996b = map2;
        map.put(Pair.create(4, 4), "layout_constraintBottom_toBottomOf");
        map.put(Pair.create(4, 3), "layout_constraintBottom_toTopOf");
        map.put(Pair.create(3, 4), "layout_constraintTop_toBottomOf");
        map.put(Pair.create(3, 3), "layout_constraintTop_toTopOf");
        map.put(Pair.create(6, 6), "layout_constraintStart_toStartOf");
        map.put(Pair.create(6, 7), "layout_constraintStart_toEndOf");
        map.put(Pair.create(7, 6), "layout_constraintEnd_toStartOf");
        map.put(Pair.create(7, 7), "layout_constraintEnd_toEndOf");
        map.put(Pair.create(1, 1), "layout_constraintLeft_toLeftOf");
        map.put(Pair.create(1, 2), "layout_constraintLeft_toRightOf");
        map.put(Pair.create(2, 2), "layout_constraintRight_toRightOf");
        map.put(Pair.create(2, 1), "layout_constraintRight_toLeftOf");
        map.put(Pair.create(5, 5), "layout_constraintBaseline_toBaselineOf");
        map2.put("layout_constraintBottom_toBottomOf", "layout_marginBottom");
        map2.put("layout_constraintBottom_toTopOf", "layout_marginBottom");
        map2.put("layout_constraintTop_toBottomOf", "layout_marginTop");
        map2.put("layout_constraintTop_toTopOf", "layout_marginTop");
        map2.put("layout_constraintStart_toStartOf", "layout_marginStart");
        map2.put("layout_constraintStart_toEndOf", "layout_marginStart");
        map2.put("layout_constraintEnd_toStartOf", "layout_marginEnd");
        map2.put("layout_constraintEnd_toEndOf", "layout_marginEnd");
        map2.put("layout_constraintLeft_toLeftOf", "layout_marginLeft");
        map2.put("layout_constraintLeft_toRightOf", "layout_marginLeft");
        map2.put("layout_constraintRight_toRightOf", "layout_marginRight");
        map2.put("layout_constraintRight_toLeftOf", "layout_marginRight");
    }

    public DesignTool(MotionLayout motionLayout) {
        this.mMotionLayout = motionLayout;
    }

    private static void Connect(int i2, ConstraintSet constraintSet, View view, HashMap<String, String> map, int i3, int i4) {
        String str = (String) f2995a.get(Pair.create(Integer.valueOf(i3), Integer.valueOf(i4)));
        String str2 = map.get(str);
        if (str2 != null) {
            String str3 = (String) f2996b.get(str);
            constraintSet.connect(view.getId(), i3, Integer.parseInt(str2), i4, str3 != null ? GetPxFromDp(i2, map.get(str3)) : 0);
        }
    }

    private static int GetPxFromDp(int i2, String str) {
        int iIndexOf;
        if (str == null || (iIndexOf = str.indexOf(100)) == -1) {
            return 0;
        }
        return (int) ((Integer.valueOf(str.substring(0, iIndexOf)).intValue() * i2) / 160.0f);
    }

    private static void SetAbsolutePositions(int i2, ConstraintSet constraintSet, View view, HashMap<String, String> map) {
        String str = map.get("layout_editor_absoluteX");
        if (str != null) {
            constraintSet.setEditorAbsoluteX(view.getId(), GetPxFromDp(i2, str));
        }
        String str2 = map.get("layout_editor_absoluteY");
        if (str2 != null) {
            constraintSet.setEditorAbsoluteY(view.getId(), GetPxFromDp(i2, str2));
        }
    }

    private static void SetBias(ConstraintSet constraintSet, View view, HashMap<String, String> map, int i2) {
        String str = map.get(i2 == 1 ? "layout_constraintVertical_bias" : "layout_constraintHorizontal_bias");
        if (str != null) {
            if (i2 == 0) {
                constraintSet.setHorizontalBias(view.getId(), Float.parseFloat(str));
            } else if (i2 == 1) {
                constraintSet.setVerticalBias(view.getId(), Float.parseFloat(str));
            }
        }
    }

    private static void SetDimensions(int i2, ConstraintSet constraintSet, View view, HashMap<String, String> map, int i3) {
        String str = map.get(i3 == 1 ? "layout_height" : "layout_width");
        if (str != null) {
            int iGetPxFromDp = !str.equalsIgnoreCase("wrap_content") ? GetPxFromDp(i2, str) : -2;
            if (i3 == 0) {
                constraintSet.constrainWidth(view.getId(), iGetPxFromDp);
            } else {
                constraintSet.constrainHeight(view.getId(), iGetPxFromDp);
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public int designAccess(int i2, String str, Object obj, float[] fArr, int i3, float[] fArr2, int i4) {
        MotionController motionController;
        View view = (View) obj;
        if (i2 != 0) {
            MotionLayout motionLayout = this.mMotionLayout;
            if (motionLayout.f3042j == null || view == null || (motionController = (MotionController) motionLayout.f3047o.get(view)) == null) {
                return -1;
            }
        } else {
            motionController = null;
        }
        if (i2 == 0) {
            return 1;
        }
        if (i2 == 1) {
            int duration = this.mMotionLayout.f3042j.getDuration() / 16;
            motionController.c(fArr2, duration);
            return duration;
        }
        if (i2 == 2) {
            int duration2 = this.mMotionLayout.f3042j.getDuration() / 16;
            motionController.b(fArr2, null);
            return duration2;
        }
        if (i2 != 3) {
            return -1;
        }
        this.mMotionLayout.f3042j.getDuration();
        return motionController.g(str, fArr2, i4);
    }

    public void disableAutoTransition(boolean z2) {
        this.mMotionLayout.I(z2);
    }

    public void dumpConstraintSet(String str) {
        MotionLayout motionLayout = this.mMotionLayout;
        if (motionLayout.f3042j == null) {
            motionLayout.f3042j = this.mSceneCache;
        }
        int iP = motionLayout.P(str);
        System.out.println(" dumping  " + str + " (" + iP + ")");
        try {
            this.mMotionLayout.f3042j.h(iP).dump(this.mMotionLayout.f3042j, new int[0]);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int getAnimationKeyFrames(Object obj, float[] fArr) {
        MotionScene motionScene = this.mMotionLayout.f3042j;
        if (motionScene == null) {
            return -1;
        }
        int duration = motionScene.getDuration() / 16;
        MotionController motionController = (MotionController) this.mMotionLayout.f3047o.get(obj);
        if (motionController == null) {
            return 0;
        }
        motionController.b(fArr, null);
        return duration;
    }

    public int getAnimationPath(Object obj, float[] fArr, int i2) {
        MotionLayout motionLayout = this.mMotionLayout;
        if (motionLayout.f3042j == null) {
            return -1;
        }
        MotionController motionController = (MotionController) motionLayout.f3047o.get(obj);
        if (motionController == null) {
            return 0;
        }
        motionController.c(fArr, i2);
        return i2;
    }

    public void getAnimationRectangles(Object obj, float[] fArr) {
        MotionScene motionScene = this.mMotionLayout.f3042j;
        if (motionScene == null) {
            return;
        }
        int duration = motionScene.getDuration() / 16;
        MotionController motionController = (MotionController) this.mMotionLayout.f3047o.get(obj);
        if (motionController == null) {
            return;
        }
        motionController.e(fArr, duration);
    }

    public String getEndState() {
        int endState = this.mMotionLayout.getEndState();
        if (this.mLastEndStateId == endState) {
            return this.mLastEndState;
        }
        String strN = this.mMotionLayout.N(endState);
        if (strN != null) {
            this.mLastEndState = strN;
            this.mLastEndStateId = endState;
        }
        return strN;
    }

    public int getKeyFrameInfo(Object obj, int i2, int[] iArr) {
        MotionController motionController = (MotionController) this.mMotionLayout.f3047o.get((View) obj);
        if (motionController == null) {
            return 0;
        }
        return motionController.getKeyFrameInfo(i2, iArr);
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public float getKeyFramePosition(Object obj, int i2, float f2, float f3) {
        MotionController motionController;
        if ((obj instanceof View) && (motionController = (MotionController) this.mMotionLayout.f3047o.get((View) obj)) != null) {
            return motionController.j(i2, f2, f3);
        }
        return 0.0f;
    }

    public int getKeyFramePositions(Object obj, int[] iArr, float[] fArr) {
        MotionController motionController = (MotionController) this.mMotionLayout.f3047o.get((View) obj);
        if (motionController == null) {
            return 0;
        }
        return motionController.getKeyFramePositions(iArr, fArr);
    }

    public Object getKeyframe(int i2, int i3, int i4) {
        MotionLayout motionLayout = this.mMotionLayout;
        MotionScene motionScene = motionLayout.f3042j;
        if (motionScene == null) {
            return null;
        }
        return motionScene.k(motionLayout.getContext(), i2, i3, i4);
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public Object getKeyframeAtLocation(Object obj, float f2, float f3) {
        MotionController motionController;
        View view = (View) obj;
        MotionLayout motionLayout = this.mMotionLayout;
        if (motionLayout.f3042j == null) {
            return -1;
        }
        if (view == null || (motionController = (MotionController) motionLayout.f3047o.get(view)) == null) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        return motionController.l(viewGroup.getWidth(), viewGroup.getHeight(), f2, f3);
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public Boolean getPositionKeyframe(Object obj, Object obj2, float f2, float f3, String[] strArr, float[] fArr) {
        if (!(obj instanceof KeyPositionBase)) {
            return Boolean.FALSE;
        }
        View view = (View) obj2;
        ((MotionController) this.mMotionLayout.f3047o.get(view)).o(view, (KeyPositionBase) obj, f2, f3, strArr, fArr);
        this.mMotionLayout.rebuildScene();
        this.mMotionLayout.f3051s = true;
        return Boolean.TRUE;
    }

    public float getProgress() {
        return this.mMotionLayout.getProgress();
    }

    public String getStartState() {
        int startState = this.mMotionLayout.getStartState();
        if (this.mLastStartStateId == startState) {
            return this.mLastStartState;
        }
        String strN = this.mMotionLayout.N(startState);
        if (strN != null) {
            this.mLastStartState = strN;
            this.mLastStartStateId = startState;
        }
        return this.mMotionLayout.N(startState);
    }

    public String getState() {
        if (this.mLastStartState != null && this.mLastEndState != null) {
            float progress = getProgress();
            if (progress <= 0.01f) {
                return this.mLastStartState;
            }
            if (progress >= 0.99f) {
                return this.mLastEndState;
            }
        }
        return this.mLastStartState;
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public long getTransitionTimeMs() {
        return this.mMotionLayout.getTransitionTimeMs();
    }

    public boolean isInTransition() {
        return (this.mLastStartState == null || this.mLastEndState == null) ? false : true;
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public void setAttributes(int i2, String str, Object obj, Object obj2) {
        View view = (View) obj;
        HashMap map = (HashMap) obj2;
        int iP = this.mMotionLayout.P(str);
        ConstraintSet constraintSetH = this.mMotionLayout.f3042j.h(iP);
        if (constraintSetH == null) {
            return;
        }
        constraintSetH.clear(view.getId());
        SetDimensions(i2, constraintSetH, view, map, 0);
        SetDimensions(i2, constraintSetH, view, map, 1);
        Connect(i2, constraintSetH, view, map, 6, 6);
        Connect(i2, constraintSetH, view, map, 6, 7);
        Connect(i2, constraintSetH, view, map, 7, 7);
        Connect(i2, constraintSetH, view, map, 7, 6);
        Connect(i2, constraintSetH, view, map, 1, 1);
        Connect(i2, constraintSetH, view, map, 1, 2);
        Connect(i2, constraintSetH, view, map, 2, 2);
        Connect(i2, constraintSetH, view, map, 2, 1);
        Connect(i2, constraintSetH, view, map, 3, 3);
        Connect(i2, constraintSetH, view, map, 3, 4);
        Connect(i2, constraintSetH, view, map, 4, 3);
        Connect(i2, constraintSetH, view, map, 4, 4);
        Connect(i2, constraintSetH, view, map, 5, 5);
        SetBias(constraintSetH, view, map, 0);
        SetBias(constraintSetH, view, map, 1);
        SetAbsolutePositions(i2, constraintSetH, view, map);
        this.mMotionLayout.updateState(iP, constraintSetH);
        this.mMotionLayout.requestLayout();
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public void setKeyFrame(Object obj, int i2, String str, Object obj2) {
        MotionScene motionScene = this.mMotionLayout.f3042j;
        if (motionScene != null) {
            motionScene.setKeyframe((View) obj, i2, str, obj2);
            MotionLayout motionLayout = this.mMotionLayout;
            motionLayout.f3050r = i2 / 100.0f;
            motionLayout.f3049q = 0.0f;
            motionLayout.rebuildScene();
            this.mMotionLayout.K(true);
        }
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public boolean setKeyFramePosition(Object obj, int i2, int i3, float f2, float f3) {
        if (!(obj instanceof View)) {
            return false;
        }
        MotionLayout motionLayout = this.mMotionLayout;
        if (motionLayout.f3042j != null) {
            MotionController motionController = (MotionController) motionLayout.f3047o.get(obj);
            MotionLayout motionLayout2 = this.mMotionLayout;
            int i4 = (int) (motionLayout2.f3048p * 100.0f);
            if (motionController != null) {
                View view = (View) obj;
                if (motionLayout2.f3042j.v(view, i4)) {
                    float fJ = motionController.j(2, f2, f3);
                    float fJ2 = motionController.j(5, f2, f3);
                    this.mMotionLayout.f3042j.setKeyframe(view, i4, "motion:percentX", Float.valueOf(fJ));
                    this.mMotionLayout.f3042j.setKeyframe(view, i4, "motion:percentY", Float.valueOf(fJ2));
                    this.mMotionLayout.rebuildScene();
                    this.mMotionLayout.K(true);
                    this.mMotionLayout.invalidate();
                    return true;
                }
            }
        }
        return false;
    }

    public void setKeyframe(Object obj, String str, Object obj2) {
        if (obj instanceof Key) {
            ((Key) obj).setValue(str, obj2);
            this.mMotionLayout.rebuildScene();
            this.mMotionLayout.f3051s = true;
        }
    }

    public void setState(String str) {
        if (str == null) {
            str = "motion_base";
        }
        if (this.mLastStartState == str) {
            return;
        }
        this.mLastStartState = str;
        this.mLastEndState = null;
        MotionLayout motionLayout = this.mMotionLayout;
        if (motionLayout.f3042j == null) {
            motionLayout.f3042j = this.mSceneCache;
        }
        int iP = motionLayout.P(str);
        this.mLastStartStateId = iP;
        if (iP != 0) {
            if (iP == this.mMotionLayout.getStartState()) {
                this.mMotionLayout.setProgress(0.0f);
            } else if (iP == this.mMotionLayout.getEndState()) {
                this.mMotionLayout.setProgress(1.0f);
            } else {
                this.mMotionLayout.transitionToState(iP);
                this.mMotionLayout.setProgress(1.0f);
            }
        }
        this.mMotionLayout.requestLayout();
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public void setToolPosition(float f2) {
        MotionLayout motionLayout = this.mMotionLayout;
        if (motionLayout.f3042j == null) {
            motionLayout.f3042j = this.mSceneCache;
        }
        motionLayout.setProgress(f2);
        this.mMotionLayout.K(true);
        this.mMotionLayout.requestLayout();
        this.mMotionLayout.invalidate();
    }

    public void setTransition(String str, String str2) {
        MotionLayout motionLayout = this.mMotionLayout;
        if (motionLayout.f3042j == null) {
            motionLayout.f3042j = this.mSceneCache;
        }
        int iP = motionLayout.P(str);
        int iP2 = this.mMotionLayout.P(str2);
        this.mMotionLayout.setTransition(iP, iP2);
        this.mLastStartStateId = iP;
        this.mLastEndStateId = iP2;
        this.mLastStartState = str;
        this.mLastEndState = str2;
    }

    public void setViewDebug(Object obj, int i2) {
        MotionController motionController;
        if ((obj instanceof View) && (motionController = (MotionController) this.mMotionLayout.f3047o.get(obj)) != null) {
            motionController.setDrawPath(i2);
            this.mMotionLayout.invalidate();
        }
    }

    public Object getKeyframe(Object obj, int i2, int i3) {
        if (this.mMotionLayout.f3042j == null) {
            return null;
        }
        int id = ((View) obj).getId();
        MotionLayout motionLayout = this.mMotionLayout;
        return motionLayout.f3042j.k(motionLayout.getContext(), i2, id, i3);
    }
}
