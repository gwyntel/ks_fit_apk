package com.aliyun.iot.aep.sdk.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.webkit.ProxyConfig;
import com.alibaba.sdk.android.openaccount.ui.model.CountrySort;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.aliyun.iot.aep.sdk.framework.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class CountryCodeAdapter extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private List<CountrySort> f11919a;

    /* renamed from: b, reason: collision with root package name */
    private Context f11920b;

    public static class ViewHolder {

        /* renamed from: a, reason: collision with root package name */
        LinearLayout f11921a;

        /* renamed from: b, reason: collision with root package name */
        TextView f11922b;

        /* renamed from: c, reason: collision with root package name */
        TextView f11923c;

        /* renamed from: d, reason: collision with root package name */
        TextView f11924d;

        /* renamed from: e, reason: collision with root package name */
        View f11925e;
    }

    public CountryCodeAdapter(Context context, List<CountrySort> list) {
        this.f11920b = context;
        if (list == null) {
            this.f11919a = new ArrayList();
        } else {
            this.f11919a = list;
        }
    }

    private int a(int i2) {
        return this.f11919a.get(i2).sortLetters.charAt(0);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.f11919a.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i2) {
        return this.f11919a.get(i2);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i2) {
        return i2;
    }

    public int getPositionForSection(int i2) {
        if (i2 == 42) {
            return 0;
        }
        for (int i3 = 0; i3 < getCount(); i3++) {
            if (this.f11919a.get(i3).sortLetters.toUpperCase(Locale.CHINESE).charAt(0) == i2) {
                return i3;
            }
        }
        return -1;
    }

    @Override // android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        View viewInflate;
        ViewHolder viewHolder;
        CountrySort countrySort = this.f11919a.get(i2);
        if (view == null) {
            viewHolder = new ViewHolder();
            viewInflate = LayoutInflater.from(this.f11920b).inflate(R.layout.sdk_framework_ali_sdk_openaccount_mobile_country_item2, (ViewGroup) null);
            viewHolder.f11921a = (LinearLayout) viewInflate.findViewById(ResourceUtils.getRId(this.f11920b, "country_catalog"));
            viewHolder.f11922b = (TextView) viewInflate.findViewById(ResourceUtils.getRId(this.f11920b, "country_catalog_text"));
            viewHolder.f11923c = (TextView) viewInflate.findViewById(ResourceUtils.getRId(this.f11920b, "country_name"));
            viewHolder.f11924d = (TextView) viewInflate.findViewById(ResourceUtils.getRId(this.f11920b, "country_code"));
            viewHolder.f11925e = viewInflate.findViewById(ResourceUtils.getRId(this.f11920b, "country_code_hint"));
            viewInflate.setTag(viewHolder);
        } else {
            viewInflate = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i2 == getPositionForSection(a(i2))) {
            viewHolder.f11921a.setVisibility(0);
            viewHolder.f11925e.setVisibility(4);
            if (ProxyConfig.MATCH_ALL_SCHEMES.equals(countrySort.sortLetters)) {
                viewHolder.f11922b.setText(R.string.account_hot_country);
            } else {
                viewHolder.f11922b.setText(countrySort.sortLetters);
            }
        } else {
            viewHolder.f11925e.setVisibility(0);
            viewHolder.f11921a.setVisibility(8);
        }
        viewHolder.f11923c.setText(this.f11919a.get(i2).displayName);
        viewHolder.f11924d.setText(this.f11919a.get(i2).code);
        return viewInflate;
    }
}
