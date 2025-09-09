package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public abstract class Transition implements Cloneable {
    private static final String LOG_TAG = "Transition";
    private static final int MATCH_FIRST = 1;
    public static final int MATCH_ID = 3;
    private static final String MATCH_ID_STR = "id";
    public static final int MATCH_INSTANCE = 1;
    private static final String MATCH_INSTANCE_STR = "instance";
    public static final int MATCH_ITEM_ID = 4;
    private static final String MATCH_ITEM_ID_STR = "itemId";
    private static final int MATCH_LAST = 4;
    public static final int MATCH_NAME = 2;
    private static final String MATCH_NAME_STR = "name";

    /* renamed from: g, reason: collision with root package name */
    TransitionPropagation f6322g;
    private ArrayList<TransitionValues> mEndValuesList;
    private EpicenterCallback mEpicenterCallback;
    private ArrayMap<String, String> mNameOverrides;
    private ArrayList<TransitionValues> mStartValuesList;
    private static final int[] DEFAULT_MATCH_ORDER = {2, 1, 3, 4};
    private static final PathMotion STRAIGHT_PATH_MOTION = new PathMotion() { // from class: androidx.transition.Transition.1
        @Override // androidx.transition.PathMotion
        public Path getPath(float f2, float f3, float f4, float f5) {
            Path path = new Path();
            path.moveTo(f2, f3);
            path.lineTo(f4, f5);
            return path;
        }
    };
    private static ThreadLocal<ArrayMap<Animator, AnimationInfo>> sRunningAnimators = new ThreadLocal<>();
    private String mName = getClass().getName();
    private long mStartDelay = -1;

    /* renamed from: a, reason: collision with root package name */
    long f6316a = -1;
    private TimeInterpolator mInterpolator = null;

    /* renamed from: b, reason: collision with root package name */
    ArrayList f6317b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    ArrayList f6318c = new ArrayList();
    private ArrayList<String> mTargetNames = null;
    private ArrayList<Class<?>> mTargetTypes = null;
    private ArrayList<Integer> mTargetIdExcludes = null;
    private ArrayList<View> mTargetExcludes = null;
    private ArrayList<Class<?>> mTargetTypeExcludes = null;
    private ArrayList<String> mTargetNameExcludes = null;
    private ArrayList<Integer> mTargetIdChildExcludes = null;
    private ArrayList<View> mTargetChildExcludes = null;
    private ArrayList<Class<?>> mTargetTypeChildExcludes = null;
    private TransitionValuesMaps mStartValues = new TransitionValuesMaps();
    private TransitionValuesMaps mEndValues = new TransitionValuesMaps();

    /* renamed from: d, reason: collision with root package name */
    TransitionSet f6319d = null;
    private int[] mMatchOrder = DEFAULT_MATCH_ORDER;

    /* renamed from: e, reason: collision with root package name */
    boolean f6320e = false;

    /* renamed from: f, reason: collision with root package name */
    ArrayList f6321f = new ArrayList();
    private int mNumInstances = 0;
    private boolean mPaused = false;
    private boolean mEnded = false;
    private ArrayList<TransitionListener> mListeners = null;
    private ArrayList<Animator> mAnimators = new ArrayList<>();
    private PathMotion mPathMotion = STRAIGHT_PATH_MOTION;

    private static class AnimationInfo {

        /* renamed from: a, reason: collision with root package name */
        View f6326a;

        /* renamed from: b, reason: collision with root package name */
        String f6327b;

        /* renamed from: c, reason: collision with root package name */
        TransitionValues f6328c;

        /* renamed from: d, reason: collision with root package name */
        WindowIdImpl f6329d;

        /* renamed from: e, reason: collision with root package name */
        Transition f6330e;

        AnimationInfo(View view, String str, Transition transition, WindowIdImpl windowIdImpl, TransitionValues transitionValues) {
            this.f6326a = view;
            this.f6327b = str;
            this.f6328c = transitionValues;
            this.f6329d = windowIdImpl;
            this.f6330e = transition;
        }
    }

    private static class ArrayListManager {
        private ArrayListManager() {
        }

        static ArrayList a(ArrayList arrayList, Object obj) {
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            if (!arrayList.contains(obj)) {
                arrayList.add(obj);
            }
            return arrayList;
        }

        static ArrayList b(ArrayList arrayList, Object obj) {
            if (arrayList == null) {
                return arrayList;
            }
            arrayList.remove(obj);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList;
        }
    }

    public static abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter(@NonNull Transition transition);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface MatchOrder {
    }

    public interface TransitionListener {
        void onTransitionCancel(@NonNull Transition transition);

        void onTransitionEnd(@NonNull Transition transition);

        void onTransitionPause(@NonNull Transition transition);

        void onTransitionResume(@NonNull Transition transition);

        void onTransitionStart(@NonNull Transition transition);
    }

    public Transition() {
    }

    private void addUnmatched(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            TransitionValues transitionValuesValueAt = arrayMap.valueAt(i2);
            if (i(transitionValuesValueAt.view)) {
                this.mStartValuesList.add(transitionValuesValueAt);
                this.mEndValuesList.add(null);
            }
        }
        for (int i3 = 0; i3 < arrayMap2.size(); i3++) {
            TransitionValues transitionValuesValueAt2 = arrayMap2.valueAt(i3);
            if (i(transitionValuesValueAt2.view)) {
                this.mEndValuesList.add(transitionValuesValueAt2);
                this.mStartValuesList.add(null);
            }
        }
    }

    private static void addViewValues(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues transitionValues) {
        transitionValuesMaps.f6345a.put(view, transitionValues);
        int id = view.getId();
        if (id >= 0) {
            if (transitionValuesMaps.f6346b.indexOfKey(id) >= 0) {
                transitionValuesMaps.f6346b.put(id, null);
            } else {
                transitionValuesMaps.f6346b.put(id, view);
            }
        }
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            if (transitionValuesMaps.f6348d.containsKey(transitionName)) {
                transitionValuesMaps.f6348d.put(transitionName, null);
            } else {
                transitionValuesMaps.f6348d.put(transitionName, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                if (transitionValuesMaps.f6347c.indexOfKey(itemIdAtPosition) < 0) {
                    ViewCompat.setHasTransientState(view, true);
                    transitionValuesMaps.f6347c.put(itemIdAtPosition, view);
                    return;
                }
                View view2 = (View) transitionValuesMaps.f6347c.get(itemIdAtPosition);
                if (view2 != null) {
                    ViewCompat.setHasTransientState(view2, false);
                    transitionValuesMaps.f6347c.put(itemIdAtPosition, null);
                }
            }
        }
    }

    private static boolean alreadyContains(int[] iArr, int i2) {
        int i3 = iArr[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            if (iArr[i4] == i3) {
                return true;
            }
        }
        return false;
    }

    private void captureHierarchy(View view, boolean z2) {
        if (view == null) {
            return;
        }
        int id = view.getId();
        ArrayList<Integer> arrayList = this.mTargetIdExcludes;
        if (arrayList == null || !arrayList.contains(Integer.valueOf(id))) {
            ArrayList<View> arrayList2 = this.mTargetExcludes;
            if (arrayList2 == null || !arrayList2.contains(view)) {
                ArrayList<Class<?>> arrayList3 = this.mTargetTypeExcludes;
                if (arrayList3 != null) {
                    int size = arrayList3.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if (this.mTargetTypeExcludes.get(i2).isInstance(view)) {
                            return;
                        }
                    }
                }
                if (view.getParent() instanceof ViewGroup) {
                    TransitionValues transitionValues = new TransitionValues(view);
                    if (z2) {
                        captureStartValues(transitionValues);
                    } else {
                        captureEndValues(transitionValues);
                    }
                    transitionValues.f6344a.add(this);
                    b(transitionValues);
                    if (z2) {
                        addViewValues(this.mStartValues, view, transitionValues);
                    } else {
                        addViewValues(this.mEndValues, view, transitionValues);
                    }
                }
                if (view instanceof ViewGroup) {
                    ArrayList<Integer> arrayList4 = this.mTargetIdChildExcludes;
                    if (arrayList4 == null || !arrayList4.contains(Integer.valueOf(id))) {
                        ArrayList<View> arrayList5 = this.mTargetChildExcludes;
                        if (arrayList5 == null || !arrayList5.contains(view)) {
                            ArrayList<Class<?>> arrayList6 = this.mTargetTypeChildExcludes;
                            if (arrayList6 != null) {
                                int size2 = arrayList6.size();
                                for (int i3 = 0; i3 < size2; i3++) {
                                    if (this.mTargetTypeChildExcludes.get(i3).isInstance(view)) {
                                        return;
                                    }
                                }
                            }
                            ViewGroup viewGroup = (ViewGroup) view;
                            for (int i4 = 0; i4 < viewGroup.getChildCount(); i4++) {
                                captureHierarchy(viewGroup.getChildAt(i4), z2);
                            }
                        }
                    }
                }
            }
        }
    }

    private ArrayList<Integer> excludeId(ArrayList<Integer> arrayList, int i2, boolean z2) {
        return i2 > 0 ? z2 ? ArrayListManager.a(arrayList, Integer.valueOf(i2)) : ArrayListManager.b(arrayList, Integer.valueOf(i2)) : arrayList;
    }

    private static <T> ArrayList<T> excludeObject(ArrayList<T> arrayList, T t2, boolean z2) {
        return t2 != null ? z2 ? ArrayListManager.a(arrayList, t2) : ArrayListManager.b(arrayList, t2) : arrayList;
    }

    private ArrayList<Class<?>> excludeType(ArrayList<Class<?>> arrayList, Class<?> cls, boolean z2) {
        return cls != null ? z2 ? ArrayListManager.a(arrayList, cls) : ArrayListManager.b(arrayList, cls) : arrayList;
    }

    private ArrayList<View> excludeView(ArrayList<View> arrayList, View view, boolean z2) {
        return view != null ? z2 ? ArrayListManager.a(arrayList, view) : ArrayListManager.b(arrayList, view) : arrayList;
    }

    private static ArrayMap<Animator, AnimationInfo> getRunningAnimators() {
        ArrayMap<Animator, AnimationInfo> arrayMap = sRunningAnimators.get();
        if (arrayMap != null) {
            return arrayMap;
        }
        ArrayMap<Animator, AnimationInfo> arrayMap2 = new ArrayMap<>();
        sRunningAnimators.set(arrayMap2);
        return arrayMap2;
    }

    private static boolean isValidMatch(int i2) {
        return i2 >= 1 && i2 <= 4;
    }

    private static boolean isValueChanged(TransitionValues transitionValues, TransitionValues transitionValues2, String str) {
        Object obj = transitionValues.values.get(str);
        Object obj2 = transitionValues2.values.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return !obj.equals(obj2);
    }

    private void matchIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, SparseArray<View> sparseArray, SparseArray<View> sparseArray2) {
        View view;
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            View viewValueAt = sparseArray.valueAt(i2);
            if (viewValueAt != null && i(viewValueAt) && (view = sparseArray2.get(sparseArray.keyAt(i2))) != null && i(view)) {
                TransitionValues transitionValues = arrayMap.get(viewValueAt);
                TransitionValues transitionValues2 = arrayMap2.get(view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove(viewValueAt);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    private void matchInstances(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        TransitionValues transitionValuesRemove;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View viewKeyAt = arrayMap.keyAt(size);
            if (viewKeyAt != null && i(viewKeyAt) && (transitionValuesRemove = arrayMap2.remove(viewKeyAt)) != null && i(transitionValuesRemove.view)) {
                this.mStartValuesList.add(arrayMap.removeAt(size));
                this.mEndValuesList.add(transitionValuesRemove);
            }
        }
    }

    private void matchItemIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, LongSparseArray<View> longSparseArray, LongSparseArray<View> longSparseArray2) {
        View view;
        int size = longSparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            View viewValueAt = longSparseArray.valueAt(i2);
            if (viewValueAt != null && i(viewValueAt) && (view = longSparseArray2.get(longSparseArray.keyAt(i2))) != null && i(view)) {
                TransitionValues transitionValues = arrayMap.get(viewValueAt);
                TransitionValues transitionValues2 = arrayMap2.get(view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove(viewValueAt);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    private void matchNames(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, ArrayMap<String, View> arrayMap3, ArrayMap<String, View> arrayMap4) {
        View view;
        int size = arrayMap3.size();
        for (int i2 = 0; i2 < size; i2++) {
            View viewValueAt = arrayMap3.valueAt(i2);
            if (viewValueAt != null && i(viewValueAt) && (view = arrayMap4.get(arrayMap3.keyAt(i2))) != null && i(view)) {
                TransitionValues transitionValues = arrayMap.get(viewValueAt);
                TransitionValues transitionValues2 = arrayMap2.get(view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove(viewValueAt);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    private void matchStartAndEnd(TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        ArrayMap<View, TransitionValues> arrayMap = new ArrayMap<>(transitionValuesMaps.f6345a);
        ArrayMap<View, TransitionValues> arrayMap2 = new ArrayMap<>(transitionValuesMaps2.f6345a);
        int i2 = 0;
        while (true) {
            int[] iArr = this.mMatchOrder;
            if (i2 >= iArr.length) {
                addUnmatched(arrayMap, arrayMap2);
                return;
            }
            int i3 = iArr[i2];
            if (i3 == 1) {
                matchInstances(arrayMap, arrayMap2);
            } else if (i3 == 2) {
                matchNames(arrayMap, arrayMap2, transitionValuesMaps.f6348d, transitionValuesMaps2.f6348d);
            } else if (i3 == 3) {
                matchIds(arrayMap, arrayMap2, transitionValuesMaps.f6346b, transitionValuesMaps2.f6346b);
            } else if (i3 == 4) {
                matchItemIds(arrayMap, arrayMap2, transitionValuesMaps.f6347c, transitionValuesMaps2.f6347c);
            }
            i2++;
        }
    }

    private static int[] parseMatchOrder(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
        int[] iArr = new int[stringTokenizer.countTokens()];
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String strTrim = stringTokenizer.nextToken().trim();
            if ("id".equalsIgnoreCase(strTrim)) {
                iArr[i2] = 3;
            } else if (MATCH_INSTANCE_STR.equalsIgnoreCase(strTrim)) {
                iArr[i2] = 1;
            } else if ("name".equalsIgnoreCase(strTrim)) {
                iArr[i2] = 2;
            } else if (MATCH_ITEM_ID_STR.equalsIgnoreCase(strTrim)) {
                iArr[i2] = 4;
            } else {
                if (!strTrim.isEmpty()) {
                    throw new InflateException("Unknown match type in matchOrder: '" + strTrim + "'");
                }
                int[] iArr2 = new int[iArr.length - 1];
                System.arraycopy(iArr, 0, iArr2, 0, i2);
                i2--;
                iArr = iArr2;
            }
            i2++;
        }
        return iArr;
    }

    private void runAnimator(Animator animator, final ArrayMap<Animator, AnimationInfo> arrayMap) {
        if (animator != null) {
            animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.Transition.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    arrayMap.remove(animator2);
                    Transition.this.f6321f.remove(animator2);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                    Transition.this.f6321f.add(animator2);
                }
            });
            a(animator);
        }
    }

    protected void a(Animator animator) {
        if (animator == null) {
            f();
            return;
        }
        if (getDuration() >= 0) {
            animator.setDuration(getDuration());
        }
        if (getStartDelay() >= 0) {
            animator.setStartDelay(getStartDelay() + animator.getStartDelay());
        }
        if (getInterpolator() != null) {
            animator.setInterpolator(getInterpolator());
        }
        animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.Transition.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                Transition.this.f();
                animator2.removeListener(this);
            }
        });
        animator.start();
    }

    @NonNull
    public Transition addListener(@NonNull TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(transitionListener);
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull View view) {
        this.f6318c.add(view);
        return this;
    }

    void b(TransitionValues transitionValues) {
        String[] propagationProperties;
        if (this.f6322g == null || transitionValues.values.isEmpty() || (propagationProperties = this.f6322g.getPropagationProperties()) == null) {
            return;
        }
        for (String str : propagationProperties) {
            if (!transitionValues.values.containsKey(str)) {
                this.f6322g.captureValues(transitionValues);
                return;
            }
        }
    }

    void c(ViewGroup viewGroup, boolean z2) {
        ArrayList<String> arrayList;
        ArrayList<Class<?>> arrayList2;
        ArrayMap<String, String> arrayMap;
        d(z2);
        if ((this.f6317b.size() > 0 || this.f6318c.size() > 0) && (((arrayList = this.mTargetNames) == null || arrayList.isEmpty()) && ((arrayList2 = this.mTargetTypes) == null || arrayList2.isEmpty()))) {
            for (int i2 = 0; i2 < this.f6317b.size(); i2++) {
                View viewFindViewById = viewGroup.findViewById(((Integer) this.f6317b.get(i2)).intValue());
                if (viewFindViewById != null) {
                    TransitionValues transitionValues = new TransitionValues(viewFindViewById);
                    if (z2) {
                        captureStartValues(transitionValues);
                    } else {
                        captureEndValues(transitionValues);
                    }
                    transitionValues.f6344a.add(this);
                    b(transitionValues);
                    if (z2) {
                        addViewValues(this.mStartValues, viewFindViewById, transitionValues);
                    } else {
                        addViewValues(this.mEndValues, viewFindViewById, transitionValues);
                    }
                }
            }
            for (int i3 = 0; i3 < this.f6318c.size(); i3++) {
                View view = (View) this.f6318c.get(i3);
                TransitionValues transitionValues2 = new TransitionValues(view);
                if (z2) {
                    captureStartValues(transitionValues2);
                } else {
                    captureEndValues(transitionValues2);
                }
                transitionValues2.f6344a.add(this);
                b(transitionValues2);
                if (z2) {
                    addViewValues(this.mStartValues, view, transitionValues2);
                } else {
                    addViewValues(this.mEndValues, view, transitionValues2);
                }
            }
        } else {
            captureHierarchy(viewGroup, z2);
        }
        if (z2 || (arrayMap = this.mNameOverrides) == null) {
            return;
        }
        int size = arrayMap.size();
        ArrayList arrayList3 = new ArrayList(size);
        for (int i4 = 0; i4 < size; i4++) {
            arrayList3.add(this.mStartValues.f6348d.remove(this.mNameOverrides.keyAt(i4)));
        }
        for (int i5 = 0; i5 < size; i5++) {
            View view2 = (View) arrayList3.get(i5);
            if (view2 != null) {
                this.mStartValues.f6348d.put(this.mNameOverrides.valueAt(i5), view2);
            }
        }
    }

    protected void cancel() {
        for (int size = this.f6321f.size() - 1; size >= 0; size--) {
            ((Animator) this.f6321f.get(size)).cancel();
        }
        ArrayList<TransitionListener> arrayList = this.mListeners;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
        int size2 = arrayList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((TransitionListener) arrayList2.get(i2)).onTransitionCancel(this);
        }
    }

    public abstract void captureEndValues(@NonNull TransitionValues transitionValues);

    public abstract void captureStartValues(@NonNull TransitionValues transitionValues);

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        return null;
    }

    void d(boolean z2) {
        if (z2) {
            this.mStartValues.f6345a.clear();
            this.mStartValues.f6346b.clear();
            this.mStartValues.f6347c.clear();
        } else {
            this.mEndValues.f6345a.clear();
            this.mEndValues.f6346b.clear();
            this.mEndValues.f6347c.clear();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void e(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList arrayList, ArrayList arrayList2) {
        Animator animatorCreateAnimator;
        int i2;
        View view;
        Animator animator;
        TransitionValues transitionValues;
        Animator animator2;
        TransitionValues transitionValues2;
        ArrayMap<Animator, AnimationInfo> runningAnimators = getRunningAnimators();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        long jMin = Long.MAX_VALUE;
        int i3 = 0;
        while (i3 < size) {
            TransitionValues transitionValues3 = (TransitionValues) arrayList.get(i3);
            TransitionValues transitionValues4 = (TransitionValues) arrayList2.get(i3);
            if (transitionValues3 != null && !transitionValues3.f6344a.contains(this)) {
                transitionValues3 = null;
            }
            if (transitionValues4 != null && !transitionValues4.f6344a.contains(this)) {
                transitionValues4 = null;
            }
            if (!(transitionValues3 == null && transitionValues4 == null) && ((transitionValues3 == null || transitionValues4 == null || isTransitionRequired(transitionValues3, transitionValues4)) && (animatorCreateAnimator = createAnimator(viewGroup, transitionValues3, transitionValues4)) != null)) {
                if (transitionValues4 != null) {
                    view = transitionValues4.view;
                    String[] transitionProperties = getTransitionProperties();
                    if (transitionProperties != null && transitionProperties.length > 0) {
                        transitionValues2 = new TransitionValues(view);
                        i2 = size;
                        TransitionValues transitionValues5 = (TransitionValues) transitionValuesMaps2.f6345a.get(view);
                        if (transitionValues5 != null) {
                            int i4 = 0;
                            while (i4 < transitionProperties.length) {
                                Map<String, Object> map = transitionValues2.values;
                                String str = transitionProperties[i4];
                                map.put(str, transitionValues5.values.get(str));
                                i4++;
                                transitionProperties = transitionProperties;
                            }
                        }
                        int size2 = runningAnimators.size();
                        int i5 = 0;
                        while (true) {
                            if (i5 >= size2) {
                                animator2 = animatorCreateAnimator;
                                break;
                            }
                            AnimationInfo animationInfo = runningAnimators.get(runningAnimators.keyAt(i5));
                            if (animationInfo.f6328c != null && animationInfo.f6326a == view && animationInfo.f6327b.equals(getName()) && animationInfo.f6328c.equals(transitionValues2)) {
                                animator2 = null;
                                break;
                            }
                            i5++;
                        }
                    } else {
                        i2 = size;
                        animator2 = animatorCreateAnimator;
                        transitionValues2 = null;
                    }
                    animator = animator2;
                    transitionValues = transitionValues2;
                } else {
                    i2 = size;
                    view = transitionValues3.view;
                    animator = animatorCreateAnimator;
                    transitionValues = null;
                }
                if (animator != null) {
                    TransitionPropagation transitionPropagation = this.f6322g;
                    if (transitionPropagation != null) {
                        long startDelay = transitionPropagation.getStartDelay(viewGroup, this, transitionValues3, transitionValues4);
                        sparseIntArray.put(this.mAnimators.size(), (int) startDelay);
                        jMin = Math.min(startDelay, jMin);
                    }
                    runningAnimators.put(animator, new AnimationInfo(view, getName(), this, ViewUtils.d(viewGroup), transitionValues));
                    this.mAnimators.add(animator);
                    jMin = jMin;
                }
            } else {
                i2 = size;
            }
            i3++;
            size = i2;
        }
        if (sparseIntArray.size() != 0) {
            for (int i6 = 0; i6 < sparseIntArray.size(); i6++) {
                Animator animator3 = this.mAnimators.get(sparseIntArray.keyAt(i6));
                animator3.setStartDelay((sparseIntArray.valueAt(i6) - jMin) + animator3.getStartDelay());
            }
        }
    }

    @NonNull
    public Transition excludeChildren(@NonNull View view, boolean z2) {
        this.mTargetChildExcludes = excludeView(this.mTargetChildExcludes, view, z2);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull View view, boolean z2) {
        this.mTargetExcludes = excludeView(this.mTargetExcludes, view, z2);
        return this;
    }

    protected void f() {
        int i2 = this.mNumInstances - 1;
        this.mNumInstances = i2;
        if (i2 == 0) {
            ArrayList<TransitionListener> arrayList = this.mListeners;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                int size = arrayList2.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((TransitionListener) arrayList2.get(i3)).onTransitionEnd(this);
                }
            }
            for (int i4 = 0; i4 < this.mStartValues.f6347c.size(); i4++) {
                View view = (View) this.mStartValues.f6347c.valueAt(i4);
                if (view != null) {
                    ViewCompat.setHasTransientState(view, false);
                }
            }
            for (int i5 = 0; i5 < this.mEndValues.f6347c.size(); i5++) {
                View view2 = (View) this.mEndValues.f6347c.valueAt(i5);
                if (view2 != null) {
                    ViewCompat.setHasTransientState(view2, false);
                }
            }
            this.mEnded = true;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void g(ViewGroup viewGroup) {
        ArrayMap<Animator, AnimationInfo> runningAnimators = getRunningAnimators();
        int size = runningAnimators.size();
        if (viewGroup == null || size == 0) {
            return;
        }
        WindowIdImpl windowIdImplD = ViewUtils.d(viewGroup);
        ArrayMap arrayMap = new ArrayMap(runningAnimators);
        runningAnimators.clear();
        for (int i2 = size - 1; i2 >= 0; i2--) {
            AnimationInfo animationInfo = (AnimationInfo) arrayMap.valueAt(i2);
            if (animationInfo.f6326a != null && windowIdImplD != null && windowIdImplD.equals(animationInfo.f6329d)) {
                ((Animator) arrayMap.keyAt(i2)).end();
            }
        }
    }

    public long getDuration() {
        return this.f6316a;
    }

    @Nullable
    public Rect getEpicenter() {
        EpicenterCallback epicenterCallback = this.mEpicenterCallback;
        if (epicenterCallback == null) {
            return null;
        }
        return epicenterCallback.onGetEpicenter(this);
    }

    @Nullable
    public EpicenterCallback getEpicenterCallback() {
        return this.mEpicenterCallback;
    }

    @Nullable
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    @NonNull
    public String getName() {
        return this.mName;
    }

    @NonNull
    public PathMotion getPathMotion() {
        return this.mPathMotion;
    }

    @Nullable
    public TransitionPropagation getPropagation() {
        return this.f6322g;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    @NonNull
    public List<Integer> getTargetIds() {
        return this.f6317b;
    }

    @Nullable
    public List<String> getTargetNames() {
        return this.mTargetNames;
    }

    @Nullable
    public List<Class<?>> getTargetTypes() {
        return this.mTargetTypes;
    }

    @NonNull
    public List<View> getTargets() {
        return this.f6318c;
    }

    @Nullable
    public String[] getTransitionProperties() {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public TransitionValues getTransitionValues(@NonNull View view, boolean z2) {
        TransitionSet transitionSet = this.f6319d;
        if (transitionSet != null) {
            return transitionSet.getTransitionValues(view, z2);
        }
        return (TransitionValues) (z2 ? this.mStartValues : this.mEndValues).f6345a.get(view);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x002d, code lost:
    
        if (r3 < 0) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x002f, code lost:
    
        if (r7 == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0031, code lost:
    
        r6 = r5.mEndValuesList;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0034, code lost:
    
        r6 = r5.mStartValuesList;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003d, code lost:
    
        return r6.get(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:?, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    androidx.transition.TransitionValues h(android.view.View r6, boolean r7) {
        /*
            r5 = this;
            androidx.transition.TransitionSet r0 = r5.f6319d
            if (r0 == 0) goto L9
            androidx.transition.TransitionValues r6 = r0.h(r6, r7)
            return r6
        L9:
            if (r7 == 0) goto Le
            java.util.ArrayList<androidx.transition.TransitionValues> r0 = r5.mStartValuesList
            goto L10
        Le:
            java.util.ArrayList<androidx.transition.TransitionValues> r0 = r5.mEndValuesList
        L10:
            r1 = 0
            if (r0 != 0) goto L14
            return r1
        L14:
            int r2 = r0.size()
            r3 = 0
        L19:
            if (r3 >= r2) goto L2c
            java.lang.Object r4 = r0.get(r3)
            androidx.transition.TransitionValues r4 = (androidx.transition.TransitionValues) r4
            if (r4 != 0) goto L24
            return r1
        L24:
            android.view.View r4 = r4.view
            if (r4 != r6) goto L29
            goto L2d
        L29:
            int r3 = r3 + 1
            goto L19
        L2c:
            r3 = -1
        L2d:
            if (r3 < 0) goto L3d
            if (r7 == 0) goto L34
            java.util.ArrayList<androidx.transition.TransitionValues> r6 = r5.mEndValuesList
            goto L36
        L34:
            java.util.ArrayList<androidx.transition.TransitionValues> r6 = r5.mStartValuesList
        L36:
            java.lang.Object r6 = r6.get(r3)
            r1 = r6
            androidx.transition.TransitionValues r1 = (androidx.transition.TransitionValues) r1
        L3d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.Transition.h(android.view.View, boolean):androidx.transition.TransitionValues");
    }

    boolean i(View view) {
        ArrayList<Class<?>> arrayList;
        ArrayList<String> arrayList2;
        int id = view.getId();
        ArrayList<Integer> arrayList3 = this.mTargetIdExcludes;
        if (arrayList3 != null && arrayList3.contains(Integer.valueOf(id))) {
            return false;
        }
        ArrayList<View> arrayList4 = this.mTargetExcludes;
        if (arrayList4 != null && arrayList4.contains(view)) {
            return false;
        }
        ArrayList<Class<?>> arrayList5 = this.mTargetTypeExcludes;
        if (arrayList5 != null) {
            int size = arrayList5.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (this.mTargetTypeExcludes.get(i2).isInstance(view)) {
                    return false;
                }
            }
        }
        if (this.mTargetNameExcludes != null && ViewCompat.getTransitionName(view) != null && this.mTargetNameExcludes.contains(ViewCompat.getTransitionName(view))) {
            return false;
        }
        if ((this.f6317b.size() == 0 && this.f6318c.size() == 0 && (((arrayList = this.mTargetTypes) == null || arrayList.isEmpty()) && ((arrayList2 = this.mTargetNames) == null || arrayList2.isEmpty()))) || this.f6317b.contains(Integer.valueOf(id)) || this.f6318c.contains(view)) {
            return true;
        }
        ArrayList<String> arrayList6 = this.mTargetNames;
        if (arrayList6 != null && arrayList6.contains(ViewCompat.getTransitionName(view))) {
            return true;
        }
        if (this.mTargetTypes != null) {
            for (int i3 = 0; i3 < this.mTargetTypes.size(); i3++) {
                if (this.mTargetTypes.get(i3).isInstance(view)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isTransitionRequired(@Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return false;
        }
        String[] transitionProperties = getTransitionProperties();
        if (transitionProperties == null) {
            Iterator<String> it = transitionValues.values.keySet().iterator();
            while (it.hasNext()) {
                if (isValueChanged(transitionValues, transitionValues2, it.next())) {
                }
            }
            return false;
        }
        for (String str : transitionProperties) {
            if (!isValueChanged(transitionValues, transitionValues2, str)) {
            }
        }
        return false;
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void j(ViewGroup viewGroup) {
        AnimationInfo animationInfo;
        this.mStartValuesList = new ArrayList<>();
        this.mEndValuesList = new ArrayList<>();
        matchStartAndEnd(this.mStartValues, this.mEndValues);
        ArrayMap<Animator, AnimationInfo> runningAnimators = getRunningAnimators();
        int size = runningAnimators.size();
        WindowIdImpl windowIdImplD = ViewUtils.d(viewGroup);
        for (int i2 = size - 1; i2 >= 0; i2--) {
            Animator animatorKeyAt = runningAnimators.keyAt(i2);
            if (animatorKeyAt != null && (animationInfo = runningAnimators.get(animatorKeyAt)) != null && animationInfo.f6326a != null && windowIdImplD.equals(animationInfo.f6329d)) {
                TransitionValues transitionValues = animationInfo.f6328c;
                View view = animationInfo.f6326a;
                TransitionValues transitionValues2 = getTransitionValues(view, true);
                TransitionValues transitionValuesH = h(view, true);
                if (transitionValues2 == null && transitionValuesH == null) {
                    transitionValuesH = (TransitionValues) this.mEndValues.f6345a.get(view);
                }
                if ((transitionValues2 != null || transitionValuesH != null) && animationInfo.f6330e.isTransitionRequired(transitionValues, transitionValuesH)) {
                    if (animatorKeyAt.isRunning() || animatorKeyAt.isStarted()) {
                        animatorKeyAt.cancel();
                    } else {
                        runningAnimators.remove(animatorKeyAt);
                    }
                }
            }
        }
        e(viewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
        k();
    }

    protected void k() {
        m();
        ArrayMap<Animator, AnimationInfo> runningAnimators = getRunningAnimators();
        Iterator<Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            Animator next = it.next();
            if (runningAnimators.containsKey(next)) {
                m();
                runAnimator(next, runningAnimators);
            }
        }
        this.mAnimators.clear();
        f();
    }

    void l(boolean z2) {
        this.f6320e = z2;
    }

    protected void m() {
        if (this.mNumInstances == 0) {
            ArrayList<TransitionListener> arrayList = this.mListeners;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                int size = arrayList2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((TransitionListener) arrayList2.get(i2)).onTransitionStart(this);
                }
            }
            this.mEnded = false;
        }
        this.mNumInstances++;
    }

    String n(String str) {
        String str2 = str + getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + ": ";
        if (this.f6316a != -1) {
            str2 = str2 + "dur(" + this.f6316a + ") ";
        }
        if (this.mStartDelay != -1) {
            str2 = str2 + "dly(" + this.mStartDelay + ") ";
        }
        if (this.mInterpolator != null) {
            str2 = str2 + "interp(" + this.mInterpolator + ") ";
        }
        if (this.f6317b.size() <= 0 && this.f6318c.size() <= 0) {
            return str2;
        }
        String str3 = str2 + "tgts(";
        if (this.f6317b.size() > 0) {
            for (int i2 = 0; i2 < this.f6317b.size(); i2++) {
                if (i2 > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + this.f6317b.get(i2);
            }
        }
        if (this.f6318c.size() > 0) {
            for (int i3 = 0; i3 < this.f6318c.size(); i3++) {
                if (i3 > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + this.f6318c.get(i3);
            }
        }
        return str3 + ")";
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void pause(View view) {
        if (this.mEnded) {
            return;
        }
        for (int size = this.f6321f.size() - 1; size >= 0; size--) {
            AnimatorUtils.b((Animator) this.f6321f.get(size));
        }
        ArrayList<TransitionListener> arrayList = this.mListeners;
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
            int size2 = arrayList2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((TransitionListener) arrayList2.get(i2)).onTransitionPause(this);
            }
        }
        this.mPaused = true;
    }

    @NonNull
    public Transition removeListener(@NonNull TransitionListener transitionListener) {
        ArrayList<TransitionListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return this;
        }
        arrayList.remove(transitionListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull View view) {
        this.f6318c.remove(view);
        return this;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void resume(View view) {
        if (this.mPaused) {
            if (!this.mEnded) {
                for (int size = this.f6321f.size() - 1; size >= 0; size--) {
                    AnimatorUtils.c((Animator) this.f6321f.get(size));
                }
                ArrayList<TransitionListener> arrayList = this.mListeners;
                if (arrayList != null && arrayList.size() > 0) {
                    ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                    int size2 = arrayList2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((TransitionListener) arrayList2.get(i2)).onTransitionResume(this);
                    }
                }
            }
            this.mPaused = false;
        }
    }

    @NonNull
    public Transition setDuration(long j2) {
        this.f6316a = j2;
        return this;
    }

    public void setEpicenterCallback(@Nullable EpicenterCallback epicenterCallback) {
        this.mEpicenterCallback = epicenterCallback;
    }

    @NonNull
    public Transition setInterpolator(@Nullable TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public void setMatchOrder(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            this.mMatchOrder = DEFAULT_MATCH_ORDER;
            return;
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (!isValidMatch(iArr[i2])) {
                throw new IllegalArgumentException("matches contains invalid value");
            }
            if (alreadyContains(iArr, i2)) {
                throw new IllegalArgumentException("matches contains a duplicate value");
            }
        }
        this.mMatchOrder = (int[]) iArr.clone();
    }

    public void setPathMotion(@Nullable PathMotion pathMotion) {
        if (pathMotion == null) {
            this.mPathMotion = STRAIGHT_PATH_MOTION;
        } else {
            this.mPathMotion = pathMotion;
        }
    }

    public void setPropagation(@Nullable TransitionPropagation transitionPropagation) {
        this.f6322g = transitionPropagation;
    }

    @NonNull
    public Transition setStartDelay(long j2) {
        this.mStartDelay = j2;
        return this;
    }

    public String toString() {
        return n("");
    }

    @NonNull
    public Transition addTarget(@IdRes int i2) {
        if (i2 != 0) {
            this.f6317b.add(Integer.valueOf(i2));
        }
        return this;
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Transition mo17clone() {
        try {
            Transition transition = (Transition) super.clone();
            transition.mAnimators = new ArrayList<>();
            transition.mStartValues = new TransitionValuesMaps();
            transition.mEndValues = new TransitionValuesMaps();
            transition.mStartValuesList = null;
            transition.mEndValuesList = null;
            return transition;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @NonNull
    public Transition excludeChildren(@IdRes int i2, boolean z2) {
        this.mTargetIdChildExcludes = excludeId(this.mTargetIdChildExcludes, i2, z2);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@IdRes int i2, boolean z2) {
        this.mTargetIdExcludes = excludeId(this.mTargetIdExcludes, i2, z2);
        return this;
    }

    @NonNull
    public Transition removeTarget(@IdRes int i2) {
        if (i2 != 0) {
            this.f6317b.remove(Integer.valueOf(i2));
        }
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull String str) {
        if (this.mTargetNames == null) {
            this.mTargetNames = new ArrayList<>();
        }
        this.mTargetNames.add(str);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull Class<?> cls, boolean z2) {
        this.mTargetTypeChildExcludes = excludeType(this.mTargetTypeChildExcludes, cls, z2);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull String str, boolean z2) {
        this.mTargetNameExcludes = excludeObject(this.mTargetNameExcludes, str, z2);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull String str) {
        ArrayList<String> arrayList = this.mTargetNames;
        if (arrayList != null) {
            arrayList.remove(str);
        }
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull Class<?> cls, boolean z2) {
        this.mTargetTypeExcludes = excludeType(this.mTargetTypeExcludes, cls, z2);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull Class<?> cls) {
        ArrayList<Class<?>> arrayList = this.mTargetTypes;
        if (arrayList != null) {
            arrayList.remove(cls);
        }
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull Class<?> cls) {
        if (this.mTargetTypes == null) {
            this.mTargetTypes = new ArrayList<>();
        }
        this.mTargetTypes.add(cls);
        return this;
    }

    @SuppressLint({"RestrictedApi"})
    public Transition(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.f6307c);
        XmlResourceParser xmlResourceParser = (XmlResourceParser) attributeSet;
        long namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainStyledAttributes, xmlResourceParser, "duration", 1, -1);
        if (namedInt >= 0) {
            setDuration(namedInt);
        }
        long namedInt2 = TypedArrayUtils.getNamedInt(typedArrayObtainStyledAttributes, xmlResourceParser, "startDelay", 2, -1);
        if (namedInt2 > 0) {
            setStartDelay(namedInt2);
        }
        int namedResourceId = TypedArrayUtils.getNamedResourceId(typedArrayObtainStyledAttributes, xmlResourceParser, "interpolator", 0, 0);
        if (namedResourceId > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, namedResourceId));
        }
        String namedString = TypedArrayUtils.getNamedString(typedArrayObtainStyledAttributes, xmlResourceParser, "matchOrder", 3);
        if (namedString != null) {
            setMatchOrder(parseMatchOrder(namedString));
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
