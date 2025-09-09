package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import androidx.webkit.ProxyConfig;
import com.alibaba.sdk.android.openaccount.ui.model.CountrySort;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class CountrySortAdapter extends BaseAdapter implements SectionIndexer {
    protected Context mContext;
    protected List<CountrySort> mList;

    public static class ViewHolder {
        public TextView country_name;
        public TextView country_number;
        public LinearLayout country_sortName;
        public TextView country_sortName_text;
    }

    public CountrySortAdapter(Context context, List<CountrySort> list) {
        this.mContext = context;
        if (list == null) {
            this.mList = new ArrayList();
        } else {
            this.mList = list;
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i2) {
        return this.mList.get(i2);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i2) {
        return i2;
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int i2) {
        if (i2 == 42) {
            return 0;
        }
        for (int i3 = 0; i3 < getCount(); i3++) {
            if (this.mList.get(i3).sortLetters.toUpperCase(Locale.CHINESE).charAt(0) == i2) {
                return i3;
            }
        }
        return -1;
    }

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int i2) {
        return this.mList.get(i2).sortLetters.charAt(0);
    }

    @Override // android.widget.SectionIndexer
    public Object[] getSections() {
        return null;
    }

    @Override // android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        View viewInflate;
        ViewHolder viewHolder;
        CountrySort countrySort = this.mList.get(i2);
        if (view == null) {
            viewHolder = new ViewHolder();
            viewInflate = LayoutInflater.from(this.mContext).inflate(ResourceUtils.getRLayout(this.mContext, "ali_sdk_openaccount_mobile_country_item"), (ViewGroup) null);
            viewHolder.country_sortName = (LinearLayout) viewInflate.findViewById(ResourceUtils.getRId(this.mContext, "country_catalog"));
            viewHolder.country_sortName_text = (TextView) viewInflate.findViewById(ResourceUtils.getRId(this.mContext, "country_catalog_text"));
            viewHolder.country_name = (TextView) viewInflate.findViewById(ResourceUtils.getRId(this.mContext, "country_name"));
            viewHolder.country_number = (TextView) viewInflate.findViewById(ResourceUtils.getRId(this.mContext, "country_code"));
            viewInflate.setTag(viewHolder);
        } else {
            viewInflate = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i2 == getPositionForSection(getSectionForPosition(i2))) {
            viewHolder.country_sortName.setVisibility(0);
            if (ProxyConfig.MATCH_ALL_SCHEMES.equals(countrySort.sortLetters)) {
                viewHolder.country_sortName_text.setText(ResourceUtils.getString(this.mContext, "ali_sdk_openaccount_text_register_hot_regions"));
            } else {
                viewHolder.country_sortName_text.setText(countrySort.sortLetters);
            }
        } else {
            viewHolder.country_sortName.setVisibility(8);
        }
        viewHolder.country_name.setText(this.mList.get(i2).displayName);
        viewHolder.country_number.setText(this.mList.get(i2).code);
        return viewInflate;
    }

    public void updateListView(List<CountrySort> list) {
        if (list == null) {
            this.mList = new ArrayList();
        } else {
            this.mList = list;
        }
        notifyDataSetChanged();
    }
}
