package androidx.appcompat.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.widget.ActivityChooserModel;
import androidx.core.view.ActionProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.lang.reflect.InvocationTargetException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ActivityChooserView extends ViewGroup implements ActivityChooserModel.ActivityChooserModelClient {

    /* renamed from: a, reason: collision with root package name */
    final ActivityChooserViewAdapter f2224a;

    /* renamed from: b, reason: collision with root package name */
    final FrameLayout f2225b;

    /* renamed from: c, reason: collision with root package name */
    final FrameLayout f2226c;

    /* renamed from: d, reason: collision with root package name */
    ActionProvider f2227d;

    /* renamed from: e, reason: collision with root package name */
    final DataSetObserver f2228e;

    /* renamed from: f, reason: collision with root package name */
    PopupWindow.OnDismissListener f2229f;

    /* renamed from: g, reason: collision with root package name */
    boolean f2230g;

    /* renamed from: h, reason: collision with root package name */
    int f2231h;
    private final View mActivityChooserContent;
    private final Drawable mActivityChooserContentBackground;
    private final Callbacks mCallbacks;
    private int mDefaultActionButtonContentDescription;
    private final ImageView mDefaultActivityButtonImage;
    private final ImageView mExpandActivityOverflowButtonImage;
    private boolean mIsAttachedToWindow;
    private final int mListPopupMaxWidth;
    private ListPopupWindow mListPopupWindow;
    private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;

    private class ActivityChooserViewAdapter extends BaseAdapter {
        private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
        private static final int ITEM_VIEW_TYPE_COUNT = 3;
        private static final int ITEM_VIEW_TYPE_FOOTER = 1;
        public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
        public static final int MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE;
        private ActivityChooserModel mDataModel;
        private boolean mHighlightDefaultActivity;
        private int mMaxActivityCount = 4;
        private boolean mShowDefaultActivity;
        private boolean mShowFooterView;

        ActivityChooserViewAdapter() {
        }

        public int getActivityCount() {
            return this.mDataModel.getActivityCount();
        }

        @Override // android.widget.Adapter
        public int getCount() {
            int activityCount = this.mDataModel.getActivityCount();
            if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                activityCount--;
            }
            int iMin = Math.min(activityCount, this.mMaxActivityCount);
            return this.mShowFooterView ? iMin + 1 : iMin;
        }

        public ActivityChooserModel getDataModel() {
            return this.mDataModel;
        }

        public ResolveInfo getDefaultActivity() {
            return this.mDataModel.getDefaultActivity();
        }

        public int getHistorySize() {
            return this.mDataModel.getHistorySize();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i2) {
            int itemViewType = getItemViewType(i2);
            if (itemViewType != 0) {
                if (itemViewType == 1) {
                    return null;
                }
                throw new IllegalArgumentException();
            }
            if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                i2++;
            }
            return this.mDataModel.getActivity(i2);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i2) {
            return i2;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getItemViewType(int i2) {
            return (this.mShowFooterView && i2 == getCount() - 1) ? 1 : 0;
        }

        public boolean getShowDefaultActivity() {
            return this.mShowDefaultActivity;
        }

        @Override // android.widget.Adapter
        public View getView(int i2, View view, ViewGroup viewGroup) {
            int itemViewType = getItemViewType(i2);
            if (itemViewType != 0) {
                if (itemViewType != 1) {
                    throw new IllegalArgumentException();
                }
                if (view != null && view.getId() == 1) {
                    return view;
                }
                View viewInflate = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
                viewInflate.setId(1);
                ((TextView) viewInflate.findViewById(R.id.title)).setText(ActivityChooserView.this.getContext().getString(R.string.abc_activity_chooser_view_see_all));
                return viewInflate;
            }
            if (view == null || view.getId() != R.id.list_item) {
                view = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
            }
            PackageManager packageManager = ActivityChooserView.this.getContext().getPackageManager();
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);
            ResolveInfo resolveInfo = (ResolveInfo) getItem(i2);
            imageView.setImageDrawable(resolveInfo.loadIcon(packageManager));
            ((TextView) view.findViewById(R.id.title)).setText(resolveInfo.loadLabel(packageManager));
            if (this.mShowDefaultActivity && i2 == 0 && this.mHighlightDefaultActivity) {
                view.setActivated(true);
            } else {
                view.setActivated(false);
            }
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getViewTypeCount() {
            return 3;
        }

        public int measureContentWidth() {
            int i2 = this.mMaxActivityCount;
            this.mMaxActivityCount = Integer.MAX_VALUE;
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int count = getCount();
            int iMax = 0;
            View view = null;
            for (int i3 = 0; i3 < count; i3++) {
                view = getView(i3, view, null);
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                iMax = Math.max(iMax, view.getMeasuredWidth());
            }
            this.mMaxActivityCount = i2;
            return iMax;
        }

        public void setDataModel(ActivityChooserModel activityChooserModel) {
            ActivityChooserModel dataModel = ActivityChooserView.this.f2224a.getDataModel();
            if (dataModel != null && ActivityChooserView.this.isShown()) {
                dataModel.unregisterObserver(ActivityChooserView.this.f2228e);
            }
            this.mDataModel = activityChooserModel;
            if (activityChooserModel != null && ActivityChooserView.this.isShown()) {
                activityChooserModel.registerObserver(ActivityChooserView.this.f2228e);
            }
            notifyDataSetChanged();
        }

        public void setMaxActivityCount(int i2) {
            if (this.mMaxActivityCount != i2) {
                this.mMaxActivityCount = i2;
                notifyDataSetChanged();
            }
        }

        public void setShowDefaultActivity(boolean z2, boolean z3) {
            if (this.mShowDefaultActivity == z2 && this.mHighlightDefaultActivity == z3) {
                return;
            }
            this.mShowDefaultActivity = z2;
            this.mHighlightDefaultActivity = z3;
            notifyDataSetChanged();
        }

        public void setShowFooterView(boolean z2) {
            if (this.mShowFooterView != z2) {
                this.mShowFooterView = z2;
                notifyDataSetChanged();
            }
        }
    }

    private class Callbacks implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
        Callbacks() {
        }

        private void notifyOnDismissListener() {
            PopupWindow.OnDismissListener onDismissListener = ActivityChooserView.this.f2229f;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view != activityChooserView.f2226c) {
                if (view != activityChooserView.f2225b) {
                    throw new IllegalArgumentException();
                }
                activityChooserView.f2230g = false;
                activityChooserView.a(activityChooserView.f2231h);
                return;
            }
            activityChooserView.dismissPopup();
            Intent intentChooseActivity = ActivityChooserView.this.f2224a.getDataModel().chooseActivity(ActivityChooserView.this.f2224a.getDataModel().getActivityIndex(ActivityChooserView.this.f2224a.getDefaultActivity()));
            if (intentChooseActivity != null) {
                intentChooseActivity.addFlags(524288);
                ActivityChooserView.this.getContext().startActivity(intentChooseActivity);
            }
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            notifyOnDismissListener();
            ActionProvider actionProvider = ActivityChooserView.this.f2227d;
            if (actionProvider != null) {
                actionProvider.subUiVisibilityChanged(false);
            }
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            int itemViewType = ((ActivityChooserViewAdapter) adapterView.getAdapter()).getItemViewType(i2);
            if (itemViewType != 0) {
                if (itemViewType != 1) {
                    throw new IllegalArgumentException();
                }
                ActivityChooserView.this.a(Integer.MAX_VALUE);
                return;
            }
            ActivityChooserView.this.dismissPopup();
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (activityChooserView.f2230g) {
                if (i2 > 0) {
                    activityChooserView.f2224a.getDataModel().setDefaultActivity(i2);
                    return;
                }
                return;
            }
            if (!activityChooserView.f2224a.getShowDefaultActivity()) {
                i2++;
            }
            Intent intentChooseActivity = ActivityChooserView.this.f2224a.getDataModel().chooseActivity(i2);
            if (intentChooseActivity != null) {
                intentChooseActivity.addFlags(524288);
                ActivityChooserView.this.getContext().startActivity(intentChooseActivity);
            }
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view != activityChooserView.f2226c) {
                throw new IllegalArgumentException();
            }
            if (activityChooserView.f2224a.getCount() > 0) {
                ActivityChooserView activityChooserView2 = ActivityChooserView.this;
                activityChooserView2.f2230g = true;
                activityChooserView2.a(activityChooserView2.f2231h);
            }
            return true;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static class InnerLayout extends LinearLayout {
        private static final int[] TINT_ATTRS = {android.R.attr.background};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, TINT_ATTRS);
            setBackgroundDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(0));
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    public ActivityChooserView(@NonNull Context context) {
        this(context, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [boolean, int] */
    void a(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f2224a.getDataModel() == null) {
            throw new IllegalStateException("No data model. Did you call #setDataModel?");
        }
        getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        ?? r02 = this.f2226c.getVisibility() == 0 ? 1 : 0;
        int activityCount = this.f2224a.getActivityCount();
        if (i2 == Integer.MAX_VALUE || activityCount <= i2 + r02) {
            this.f2224a.setShowFooterView(false);
            this.f2224a.setMaxActivityCount(i2);
        } else {
            this.f2224a.setShowFooterView(true);
            this.f2224a.setMaxActivityCount(i2 - 1);
        }
        ListPopupWindow listPopupWindow = getListPopupWindow();
        if (listPopupWindow.isShowing()) {
            return;
        }
        if (this.f2230g || r02 == 0) {
            this.f2224a.setShowDefaultActivity(true, r02);
        } else {
            this.f2224a.setShowDefaultActivity(false, false);
        }
        listPopupWindow.setContentWidth(Math.min(this.f2224a.measureContentWidth(), this.mListPopupMaxWidth));
        listPopupWindow.show();
        ActionProvider actionProvider = this.f2227d;
        if (actionProvider != null) {
            actionProvider.subUiVisibilityChanged(true);
        }
        listPopupWindow.getListView().setContentDescription(getContext().getString(R.string.abc_activitychooserview_choose_application));
        listPopupWindow.getListView().setSelector(new ColorDrawable(0));
    }

    void b() {
        if (this.f2224a.getCount() > 0) {
            this.f2225b.setEnabled(true);
        } else {
            this.f2225b.setEnabled(false);
        }
        int activityCount = this.f2224a.getActivityCount();
        int historySize = this.f2224a.getHistorySize();
        if (activityCount == 1 || (activityCount > 1 && historySize > 0)) {
            this.f2226c.setVisibility(0);
            ResolveInfo defaultActivity = this.f2224a.getDefaultActivity();
            PackageManager packageManager = getContext().getPackageManager();
            this.mDefaultActivityButtonImage.setImageDrawable(defaultActivity.loadIcon(packageManager));
            if (this.mDefaultActionButtonContentDescription != 0) {
                this.f2226c.setContentDescription(getContext().getString(this.mDefaultActionButtonContentDescription, defaultActivity.loadLabel(packageManager)));
            }
        } else {
            this.f2226c.setVisibility(8);
        }
        if (this.f2226c.getVisibility() == 0) {
            this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
        } else {
            this.mActivityChooserContent.setBackgroundDrawable(null);
        }
    }

    public boolean dismissPopup() {
        if (!isShowingPopup()) {
            return true;
        }
        getListPopupWindow().dismiss();
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return true;
        }
        viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        return true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ActivityChooserModel getDataModel() {
        return this.f2224a.getDataModel();
    }

    ListPopupWindow getListPopupWindow() {
        if (this.mListPopupWindow == null) {
            ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
            this.mListPopupWindow = listPopupWindow;
            listPopupWindow.setAdapter(this.f2224a);
            this.mListPopupWindow.setAnchorView(this);
            this.mListPopupWindow.setModal(true);
            this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
            this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
        }
        return this.mListPopupWindow;
    }

    public boolean isShowingPopup() {
        return getListPopupWindow().isShowing();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ActivityChooserModel dataModel = this.f2224a.getDataModel();
        if (dataModel != null) {
            dataModel.registerObserver(this.f2228e);
        }
        this.mIsAttachedToWindow = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActivityChooserModel dataModel = this.f2224a.getDataModel();
        if (dataModel != null) {
            dataModel.unregisterObserver(this.f2228e);
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        }
        if (isShowingPopup()) {
            dismissPopup();
        }
        this.mIsAttachedToWindow = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        this.mActivityChooserContent.layout(0, 0, i4 - i2, i5 - i3);
        if (isShowingPopup()) {
            return;
        }
        dismissPopup();
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        View view = this.mActivityChooserContent;
        if (this.f2226c.getVisibility() != 0) {
            i3 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3), 1073741824);
        }
        measureChild(view, i2, i3);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    @Override // androidx.appcompat.widget.ActivityChooserModel.ActivityChooserModelClient
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setActivityChooserModel(ActivityChooserModel activityChooserModel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f2224a.setDataModel(activityChooserModel);
        if (isShowingPopup()) {
            dismissPopup();
            showPopup();
        }
    }

    public void setDefaultActionButtonContentDescription(int i2) {
        this.mDefaultActionButtonContentDescription = i2;
    }

    public void setExpandActivityOverflowButtonContentDescription(int i2) {
        this.mExpandActivityOverflowButtonImage.setContentDescription(getContext().getString(i2));
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        this.mExpandActivityOverflowButtonImage.setImageDrawable(drawable);
    }

    public void setInitialActivityCount(int i2) {
        this.f2231h = i2;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.f2229f = onDismissListener;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setProvider(ActionProvider actionProvider) {
        this.f2227d = actionProvider;
    }

    public boolean showPopup() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (isShowingPopup() || !this.mIsAttachedToWindow) {
            return false;
        }
        this.f2230g = false;
        a(this.f2231h);
        return true;
    }

    public ActivityChooserView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityChooserView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f2228e = new DataSetObserver() { // from class: androidx.appcompat.widget.ActivityChooserView.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                super.onChanged();
                ActivityChooserView.this.f2224a.notifyDataSetChanged();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                super.onInvalidated();
                ActivityChooserView.this.f2224a.notifyDataSetInvalidated();
            }
        };
        this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.widget.ActivityChooserView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (ActivityChooserView.this.isShowingPopup()) {
                    if (!ActivityChooserView.this.isShown()) {
                        ActivityChooserView.this.getListPopupWindow().dismiss();
                        return;
                    }
                    ActivityChooserView.this.getListPopupWindow().show();
                    ActionProvider actionProvider = ActivityChooserView.this.f2227d;
                    if (actionProvider != null) {
                        actionProvider.subUiVisibilityChanged(true);
                    }
                }
            }
        };
        this.f2231h = 4;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActivityChooserView, i2, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.ActivityChooserView, attributeSet, typedArrayObtainStyledAttributes, i2, 0);
        this.f2231h = typedArrayObtainStyledAttributes.getInt(R.styleable.ActivityChooserView_initialActivityCount, 4);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
        typedArrayObtainStyledAttributes.recycle();
        LayoutInflater.from(getContext()).inflate(R.layout.abc_activity_chooser_view, (ViewGroup) this, true);
        Callbacks callbacks = new Callbacks();
        this.mCallbacks = callbacks;
        View viewFindViewById = findViewById(R.id.activity_chooser_view_content);
        this.mActivityChooserContent = viewFindViewById;
        this.mActivityChooserContentBackground = viewFindViewById.getBackground();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.default_activity_button);
        this.f2226c = frameLayout;
        frameLayout.setOnClickListener(callbacks);
        frameLayout.setOnLongClickListener(callbacks);
        this.mDefaultActivityButtonImage = (ImageView) frameLayout.findViewById(R.id.image);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.expand_activities_button);
        frameLayout2.setOnClickListener(callbacks);
        frameLayout2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: androidx.appcompat.widget.ActivityChooserView.3
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCanOpenPopup(true);
            }
        });
        frameLayout2.setOnTouchListener(new ForwardingListener(frameLayout2) { // from class: androidx.appcompat.widget.ActivityChooserView.4
            @Override // androidx.appcompat.widget.ForwardingListener
            public ShowableListMenu getPopup() {
                return ActivityChooserView.this.getListPopupWindow();
            }

            @Override // androidx.appcompat.widget.ForwardingListener
            protected boolean onForwardingStarted() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ActivityChooserView.this.showPopup();
                return true;
            }

            @Override // androidx.appcompat.widget.ForwardingListener
            protected boolean onForwardingStopped() {
                ActivityChooserView.this.dismissPopup();
                return true;
            }
        });
        this.f2225b = frameLayout2;
        ImageView imageView = (ImageView) frameLayout2.findViewById(R.id.image);
        this.mExpandActivityOverflowButtonImage = imageView;
        imageView.setImageDrawable(drawable);
        ActivityChooserViewAdapter activityChooserViewAdapter = new ActivityChooserViewAdapter();
        this.f2224a = activityChooserViewAdapter;
        activityChooserViewAdapter.registerDataSetObserver(new DataSetObserver() { // from class: androidx.appcompat.widget.ActivityChooserView.5
            @Override // android.database.DataSetObserver
            public void onChanged() {
                super.onChanged();
                ActivityChooserView.this.b();
            }
        });
        Resources resources = context.getResources();
        this.mListPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    }
}
