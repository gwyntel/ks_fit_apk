package com.aliyun.iot.aep.sdk.page;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.iot.aep.sdk.framework.R;
import com.aliyun.iot.link.ui.component.nav.UIBarItem;
import com.aliyun.iot.link.ui.component.nav.UINavigationBar;

/* loaded from: classes3.dex */
public class OATitleBar extends LinearLayout {
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_SIMPLE = 1;

    /* renamed from: a, reason: collision with root package name */
    private int f12001a;

    /* renamed from: b, reason: collision with root package name */
    private ImageView f12002b;

    /* renamed from: c, reason: collision with root package name */
    private TextView f12003c;

    /* renamed from: d, reason: collision with root package name */
    private UINavigationBar f12004d;

    public OATitleBar(Context context) {
        this(context, null);
    }

    private void a() {
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.sdk_framework_ali_sdk_openaccount_title_layout_2, (ViewGroup) this, true);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_title);
        View viewFindViewById = findViewById(R.id.line_title);
        this.f12004d = (UINavigationBar) findViewById(R.id.top_bar);
        this.f12002b = (ImageView) findViewById(R.id.ali_sdk_openaccount_back);
        this.f12003c = (TextView) findViewById(R.id.ali_sdk_openaccount_title);
        if (this.f12001a == 1) {
            this.f12004d.setVisibility(0);
            relativeLayout.setVisibility(8);
            viewFindViewById.setVisibility(8);
        } else {
            this.f12004d.setVisibility(8);
            relativeLayout.setVisibility(0);
            viewFindViewById.setVisibility(0);
        }
    }

    public void setBackClickListener(final View.OnClickListener onClickListener) {
        if (this.f12001a == 1) {
            this.f12004d.setNavigationBackAction(new UIBarItem.Action() { // from class: com.aliyun.iot.aep.sdk.page.OATitleBar.1
                @Override // com.aliyun.iot.link.ui.component.nav.UIBarItem.Action
                public void invoke(View view) {
                    onClickListener.onClick(view);
                }
            });
        } else {
            this.f12002b.setOnClickListener(onClickListener);
        }
    }

    public void setTitle(String str) throws Resources.NotFoundException {
        if (this.f12001a == 1) {
            this.f12004d.setTitle(str);
        } else {
            this.f12003c.setText(str);
        }
    }

    public void setType(int i2) {
        this.f12001a = i2;
        a();
    }

    public void setmBtnBackVisible(int i2) {
    }

    public OATitleBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OATitleBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f12001a = 0;
        setOrientation(1);
        setType(0);
    }
}
