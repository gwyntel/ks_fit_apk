package com.aliyun.iot.link.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class LinkPageIndicator extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "PageIndicator";
    private OnTabClickListener onTabClickListener;
    private TabAdapter tabAdapter;

    public static abstract class BaseTabViewHolder<T> {
        public static final int LEFT_EDGE_TAB = 1;
        public static final int MIDDLE_TAB = 2;
        public static final int ONLY_ONE_TAB = 4;
        public static final int RIGHT_EDGE_TAB = 3;
        private int mPosition;
        private T mTabData;
        private int mTabType;
        public View mView;

        public BaseTabViewHolder(View view) {
            this.mView = view;
            view.setTag(this);
            initView();
        }

        public int getPosition() {
            return this.mPosition;
        }

        public T getTabData() {
            return this.mTabData;
        }

        public int getTabType() {
            return this.mTabType;
        }

        public View getView() {
            return this.mView;
        }

        public abstract void initView();

        public abstract void onTabChecked(boolean z2);

        public void setPosition(int i2) {
            this.mPosition = i2;
        }

        public void setTabData(T t2) {
            this.mTabData = t2;
            try {
                upDateData(t2);
            } catch (Exception e2) {
                Log.e(LinkPageIndicator.TAG, "ilop pageindicator bind data error!");
                e2.printStackTrace();
            }
        }

        public void setType(int i2) {
            this.mTabType = i2;
        }

        public abstract void upDateData(T t2);
    }

    public interface OnTabClickListener {
        void onTabClick(View view, BaseTabViewHolder baseTabViewHolder);
    }

    public LinkPageIndicator(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void buildView() {
        removeAllViews();
        setOrientation(this.tabAdapter.getOrientation());
        if (this.tabAdapter != null) {
            for (int i2 = 0; i2 < this.tabAdapter.getTabCount(); i2++) {
                BaseTabViewHolder baseTabViewHolderOnCreatTabViewHolder = this.tabAdapter.onCreatTabViewHolder(getContext(), this, i2, this.tabAdapter.getTabType(i2));
                baseTabViewHolderOnCreatTabViewHolder.setPosition(i2);
                baseTabViewHolderOnCreatTabViewHolder.getView().setLayoutParams(getOrientation() == 0 ? new LinearLayout.LayoutParams(0, -1, 1.0f) : new LinearLayout.LayoutParams(-1, 0, 1.0f));
                baseTabViewHolderOnCreatTabViewHolder.getView().setOnClickListener(this);
                this.tabAdapter.onBindTabViewHolder(baseTabViewHolderOnCreatTabViewHolder, i2);
                addView(baseTabViewHolderOnCreatTabViewHolder.getView());
            }
        }
    }

    private void init() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshIndicator() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getTag() != null && (childAt.getTag() instanceof BaseTabViewHolder)) {
                BaseTabViewHolder baseTabViewHolder = (BaseTabViewHolder) childAt.getTag();
                this.tabAdapter.onBindTabViewHolder(baseTabViewHolder, baseTabViewHolder.getPosition());
            }
        }
    }

    private void refreshTabState(View view) {
        if (view.getTag() != null && (view.getTag() instanceof BaseTabViewHolder)) {
            ((BaseTabViewHolder) view.getTag()).onTabChecked(true);
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getTag() != null && (childAt.getTag() instanceof BaseTabViewHolder)) {
                BaseTabViewHolder baseTabViewHolder = (BaseTabViewHolder) childAt.getTag();
                if (childAt == view) {
                    baseTabViewHolder.onTabChecked(true);
                } else {
                    baseTabViewHolder.onTabChecked(false);
                }
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        refreshTabState(view);
        OnTabClickListener onTabClickListener = this.onTabClickListener;
        if (onTabClickListener != null) {
            onTabClickListener.onTabClick(view, (BaseTabViewHolder) view.getTag());
        } else {
            Log.d(TAG, "there is no OnTabCLickListener");
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
    }

    public void setNeedShowNumPoint() {
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    public void setTabAdapter(TabAdapter tabAdapter) {
        this.tabAdapter = tabAdapter;
        tabAdapter.setLinkPageIndicator(this);
        buildView();
        requestLayout();
    }

    public static abstract class TabAdapter {
        private LinkPageIndicator mLinkPageIndicator;
        private int orientation;

        public TabAdapter(int i2) {
            this.orientation = i2;
        }

        public int getOrientation() {
            return this.orientation;
        }

        public int getTabCount() {
            return 0;
        }

        public abstract int getTabType(int i2);

        public void notifyDataSetChanged() {
            this.mLinkPageIndicator.buildView();
            this.mLinkPageIndicator.requestLayout();
        }

        public void notifyDataSetRefresh() {
            this.mLinkPageIndicator.refreshIndicator();
        }

        public abstract void onBindTabViewHolder(BaseTabViewHolder baseTabViewHolder, int i2);

        public abstract BaseTabViewHolder onCreatTabViewHolder(Context context, ViewGroup viewGroup, int i2, int i3);

        public void setLinkPageIndicator(LinkPageIndicator linkPageIndicator) {
            this.mLinkPageIndicator = linkPageIndicator;
        }

        public TabAdapter() {
            this(0);
        }
    }

    public LinkPageIndicator(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinkPageIndicator(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
