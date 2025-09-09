package com.aliyun.iot.link.ui.component;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

/* loaded from: classes3.dex */
public class LinkTabPager extends LinearLayout {
    private ViewPager mViewPager;
    private TabLayout tableLayout;

    public LinkTabPager(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.link_tab_page, (ViewGroup) this, true);
        this.tableLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        this.mViewPager = viewPager;
        this.tableLayout.setupWithViewPager(viewPager);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mViewPager.addOnPageChangeListener(onPageChangeListener);
    }

    public void addOnTabSelectedListener(@NonNull TabLayout.OnTabSelectedListener onTabSelectedListener) {
        this.tableLayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    public int getCurrentItem() {
        return this.mViewPager.getCurrentItem();
    }

    public int getSelectedTabPosition() {
        return this.tableLayout.getSelectedTabPosition();
    }

    public int getTabCount() {
        return this.tableLayout.getTabCount();
    }

    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mViewPager.removeOnPageChangeListener(onPageChangeListener);
    }

    public void removeOnTabSelectedListener(@NonNull TabLayout.OnTabSelectedListener onTabSelectedListener) {
        this.tableLayout.removeOnTabSelectedListener(onTabSelectedListener);
    }

    public void setAdapter(PagerAdapter pagerAdapter) throws Resources.NotFoundException {
        this.mViewPager.setAdapter(pagerAdapter);
    }

    public void setCurrentItem(int i2, boolean z2) throws Resources.NotFoundException {
        this.mViewPager.setCurrentItem(i2, z2);
    }

    public void setTabMode(int i2) {
        this.tableLayout.setTabMode(i2);
    }

    public void setTabTextColors(int i2, int i3) {
        this.tableLayout.setTabTextColors(i2, i3);
    }

    public LinkTabPager(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.style.Widget_Design_TabLayout);
    }

    public LinkTabPager(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context);
    }
}
