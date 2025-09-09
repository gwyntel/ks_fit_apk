package com.luck.picture.lib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.StyleUtils;

/* loaded from: classes4.dex */
public class TitleBar extends RelativeLayout implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    protected RelativeLayout f19148a;

    /* renamed from: b, reason: collision with root package name */
    protected ImageView f19149b;

    /* renamed from: c, reason: collision with root package name */
    protected ImageView f19150c;

    /* renamed from: d, reason: collision with root package name */
    protected ImageView f19151d;

    /* renamed from: e, reason: collision with root package name */
    protected MarqueeTextView f19152e;

    /* renamed from: f, reason: collision with root package name */
    protected TextView f19153f;

    /* renamed from: g, reason: collision with root package name */
    protected View f19154g;

    /* renamed from: h, reason: collision with root package name */
    protected View f19155h;

    /* renamed from: i, reason: collision with root package name */
    protected SelectorConfig f19156i;

    /* renamed from: j, reason: collision with root package name */
    protected View f19157j;

    /* renamed from: k, reason: collision with root package name */
    protected RelativeLayout f19158k;

    /* renamed from: l, reason: collision with root package name */
    protected OnTitleBarListener f19159l;

    public static class OnTitleBarListener {
        public void onBackPressed() {
        }

        public void onShowAlbumPopWindow(View view) {
        }

        public void onTitleDoubleClick() {
        }
    }

    public TitleBar(Context context) {
        super(context);
        c();
    }

    protected void a() {
    }

    protected void b() {
        LayoutInflater.from(getContext()).inflate(R.layout.ps_title_bar, this);
    }

    protected void c() {
        Context context;
        int i2;
        b();
        setClickable(true);
        setFocusable(true);
        this.f19156i = SelectorProviders.getInstance().getSelectorConfig();
        this.f19157j = findViewById(R.id.top_status_bar);
        this.f19158k = (RelativeLayout) findViewById(R.id.rl_title_bar);
        this.f19149b = (ImageView) findViewById(R.id.ps_iv_left_back);
        this.f19148a = (RelativeLayout) findViewById(R.id.ps_rl_album_bg);
        this.f19151d = (ImageView) findViewById(R.id.ps_iv_delete);
        this.f19155h = findViewById(R.id.ps_rl_album_click);
        this.f19152e = (MarqueeTextView) findViewById(R.id.ps_tv_title);
        this.f19150c = (ImageView) findViewById(R.id.ps_iv_arrow);
        this.f19153f = (TextView) findViewById(R.id.ps_tv_cancel);
        this.f19154g = findViewById(R.id.title_bar_line);
        this.f19149b.setOnClickListener(this);
        this.f19153f.setOnClickListener(this);
        this.f19148a.setOnClickListener(this);
        this.f19158k.setOnClickListener(this);
        this.f19155h.setOnClickListener(this);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
        a();
        if (!TextUtils.isEmpty(this.f19156i.defaultAlbumName)) {
            setTitle(this.f19156i.defaultAlbumName);
            return;
        }
        if (this.f19156i.chooseMode == SelectMimeType.ofAudio()) {
            context = getContext();
            i2 = R.string.ps_all_audio;
        } else {
            context = getContext();
            i2 = R.string.ps_camera_roll;
        }
        setTitle(context.getString(i2));
    }

    public ImageView getImageArrow() {
        return this.f19150c;
    }

    public ImageView getImageDelete() {
        return this.f19151d;
    }

    public View getTitleBarLine() {
        return this.f19154g;
    }

    public TextView getTitleCancelView() {
        return this.f19153f;
    }

    public String getTitleText() {
        return this.f19152e.getText().toString();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnTitleBarListener onTitleBarListener;
        int id = view.getId();
        if (id == R.id.ps_iv_left_back || id == R.id.ps_tv_cancel) {
            OnTitleBarListener onTitleBarListener2 = this.f19159l;
            if (onTitleBarListener2 != null) {
                onTitleBarListener2.onBackPressed();
                return;
            }
            return;
        }
        if (id == R.id.ps_rl_album_bg || id == R.id.ps_rl_album_click) {
            OnTitleBarListener onTitleBarListener3 = this.f19159l;
            if (onTitleBarListener3 != null) {
                onTitleBarListener3.onShowAlbumPopWindow(this);
                return;
            }
            return;
        }
        if (id != R.id.rl_title_bar || (onTitleBarListener = this.f19159l) == null) {
            return;
        }
        onTitleBarListener.onTitleDoubleClick();
    }

    public void setOnTitleBarListener(OnTitleBarListener onTitleBarListener) {
        this.f19159l = onTitleBarListener;
    }

    public void setTitle(String str) {
        this.f19152e.setText(str);
    }

    public void setTitleBarStyle() {
        if (this.f19156i.isPreviewFullScreenMode) {
            this.f19157j.getLayoutParams().height = DensityUtil.getStatusBarHeight(getContext());
        }
        TitleBarStyle titleBarStyle = this.f19156i.selectorStyle.getTitleBarStyle();
        int titleBarHeight = titleBarStyle.getTitleBarHeight();
        if (StyleUtils.checkSizeValidity(titleBarHeight)) {
            this.f19158k.getLayoutParams().height = titleBarHeight;
        } else {
            this.f19158k.getLayoutParams().height = DensityUtil.dip2px(getContext(), 48.0f);
        }
        if (this.f19154g != null) {
            if (titleBarStyle.isDisplayTitleBarLine()) {
                this.f19154g.setVisibility(0);
                if (StyleUtils.checkStyleValidity(titleBarStyle.getTitleBarLineColor())) {
                    this.f19154g.setBackgroundColor(titleBarStyle.getTitleBarLineColor());
                }
            } else {
                this.f19154g.setVisibility(8);
            }
        }
        int titleBackgroundColor = titleBarStyle.getTitleBackgroundColor();
        if (StyleUtils.checkStyleValidity(titleBackgroundColor)) {
            setBackgroundColor(titleBackgroundColor);
        }
        int titleLeftBackResource = titleBarStyle.getTitleLeftBackResource();
        if (StyleUtils.checkStyleValidity(titleLeftBackResource)) {
            this.f19149b.setImageResource(titleLeftBackResource);
        }
        String string = StyleUtils.checkStyleValidity(titleBarStyle.getTitleDefaultTextResId()) ? getContext().getString(titleBarStyle.getTitleDefaultTextResId()) : titleBarStyle.getTitleDefaultText();
        if (StyleUtils.checkTextValidity(string)) {
            this.f19152e.setText(string);
        }
        int titleTextSize = titleBarStyle.getTitleTextSize();
        if (StyleUtils.checkSizeValidity(titleTextSize)) {
            this.f19152e.setTextSize(titleTextSize);
        }
        int titleTextColor = titleBarStyle.getTitleTextColor();
        if (StyleUtils.checkStyleValidity(titleTextColor)) {
            this.f19152e.setTextColor(titleTextColor);
        }
        if (this.f19156i.isOnlySandboxDir) {
            this.f19150c.setImageResource(R.drawable.ps_ic_trans_1px);
        } else {
            int titleDrawableRightResource = titleBarStyle.getTitleDrawableRightResource();
            if (StyleUtils.checkStyleValidity(titleDrawableRightResource)) {
                this.f19150c.setImageResource(titleDrawableRightResource);
            }
        }
        int titleAlbumBackgroundResource = titleBarStyle.getTitleAlbumBackgroundResource();
        if (StyleUtils.checkStyleValidity(titleAlbumBackgroundResource)) {
            this.f19148a.setBackgroundResource(titleAlbumBackgroundResource);
        }
        if (titleBarStyle.isHideCancelButton()) {
            this.f19153f.setVisibility(8);
        } else {
            this.f19153f.setVisibility(0);
            int titleCancelBackgroundResource = titleBarStyle.getTitleCancelBackgroundResource();
            if (StyleUtils.checkStyleValidity(titleCancelBackgroundResource)) {
                this.f19153f.setBackgroundResource(titleCancelBackgroundResource);
            }
            String string2 = StyleUtils.checkStyleValidity(titleBarStyle.getTitleCancelTextResId()) ? getContext().getString(titleBarStyle.getTitleCancelTextResId()) : titleBarStyle.getTitleCancelText();
            if (StyleUtils.checkTextValidity(string2)) {
                this.f19153f.setText(string2);
            }
            int titleCancelTextColor = titleBarStyle.getTitleCancelTextColor();
            if (StyleUtils.checkStyleValidity(titleCancelTextColor)) {
                this.f19153f.setTextColor(titleCancelTextColor);
            }
            int titleCancelTextSize = titleBarStyle.getTitleCancelTextSize();
            if (StyleUtils.checkSizeValidity(titleCancelTextSize)) {
                this.f19153f.setTextSize(titleCancelTextSize);
            }
        }
        int previewDeleteBackgroundResource = titleBarStyle.getPreviewDeleteBackgroundResource();
        if (StyleUtils.checkStyleValidity(previewDeleteBackgroundResource)) {
            this.f19151d.setBackgroundResource(previewDeleteBackgroundResource);
        } else {
            this.f19151d.setBackgroundResource(R.drawable.ps_ic_delete);
        }
    }

    public TitleBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c();
    }

    public TitleBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        c();
    }
}
