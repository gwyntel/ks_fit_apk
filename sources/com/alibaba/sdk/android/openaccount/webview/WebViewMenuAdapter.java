package com.alibaba.sdk.android.openaccount.webview;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import java.util.List;

/* loaded from: classes2.dex */
public class WebViewMenuAdapter extends BaseAdapter {
    private Context context;
    private List<MenuItem> titleList;

    static class ViewHolder {
        TextView titleTV;

        ViewHolder() {
        }
    }

    public WebViewMenuAdapter(Context context, List<MenuItem> list) {
        this.context = context;
        this.titleList = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<MenuItem> list = this.titleList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i2) {
        return this.titleList.get(i2);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i2) {
        return 0L;
    }

    @Override // android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((Activity) this.context).getLayoutInflater().inflate(ResourceUtils.getRLayout(this.context, "com_taobao_tae_sdk_web_view_menu_item"), viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTV = (TextView) view.findViewById(ResourceUtils.getIdentifier(this.context, "id", "com_taobao_tae_sdk_web_view_menu_item_title"));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.titleTV.setText(this.titleList.get(i2).title);
        return view;
    }
}
