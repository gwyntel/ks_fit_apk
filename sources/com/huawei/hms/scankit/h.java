package com.huawei.hms.scankit;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.huawei.hms.feature.DynamicModuleInitializer;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import com.huawei.hms.scankit.drawable.ScanDrawable;
import com.huawei.hms.scankit.p.i8;
import com.huawei.hms.scankit.p.w7;
import java.util.Locale;

/* loaded from: classes4.dex */
public class h extends i implements View.OnClickListener {
    private ScanDrawable I;
    private ImageView J;
    private ImageView K;
    private ImageView L;
    private ImageView M;
    private ImageView N;
    private RelativeLayout O;
    private LinearLayout P;
    private LinearLayout Q;
    private LinearLayout R;
    private LinearLayout S;
    private int T;
    private int U;
    private LinearLayout V;
    private TextView W;
    private TextView X;
    private TextView Y;
    private TextView Z;

    /* renamed from: a0, reason: collision with root package name */
    private LinearLayout f16933a0;

    /* renamed from: b0, reason: collision with root package name */
    private boolean f16934b0;

    /* renamed from: c0, reason: collision with root package name */
    private boolean f16935c0;

    class a implements ViewTreeObserver.OnGlobalLayoutListener {
        a() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            Rect rect = new Rect();
            Context context = h.this.f16903c;
            if (context == null) {
                return;
            }
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            if (w7.c((Activity) h.this.f16903c)) {
                return;
            }
            int width = ((Activity) h.this.f16903c).getWindow().getDecorView().getRootView().getWidth() - rect.right;
            if (!w7.d()) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) h.this.S.getLayoutParams();
                if (width != 0) {
                    if (layoutParams.getMarginEnd() != i8.a(h.this.f16903c, r2.T - 12)) {
                        layoutParams.setMarginEnd(i8.a(h.this.f16903c, r1.T - 12));
                        return;
                    }
                    return;
                }
                if (layoutParams.getMarginEnd() != i8.a(h.this.f16903c, r2.T - 12) + i8.a(h.this.f16903c)) {
                    layoutParams.setMarginEnd(i8.a(h.this.f16903c, r1.T - 12) + i8.a(h.this.f16903c));
                    return;
                }
                return;
            }
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) h.this.O.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) h.this.f16933a0.getLayoutParams();
            if (width != 0) {
                if (layoutParams2.getMarginStart() != i8.a(h.this.f16903c, r3.T - 12)) {
                    layoutParams2.setMarginStart(i8.a(h.this.f16903c, r1.T - 12));
                }
                if (layoutParams3.getMarginStart() != i8.a(h.this.f16903c, r1.T - 12)) {
                    layoutParams3.setMarginStart(i8.a(h.this.f16903c, r0.T - 12));
                    return;
                }
                return;
            }
            if (layoutParams2.getMarginStart() != i8.a(h.this.f16903c, r3.T - 12) + i8.a(h.this.f16903c)) {
                layoutParams2.setMarginStart(i8.a(h.this.f16903c, r1.T - 12) + i8.a(h.this.f16903c));
            }
            if (layoutParams3.getMarginStart() != i8.a(h.this.f16903c, r1.T - 12) + i8.a(h.this.f16903c)) {
                layoutParams3.setMarginStart(i8.a(h.this.f16903c, r0.T - 12) + i8.a(h.this.f16903c));
            }
        }
    }

    public h(Context context, int i2, IObjectWrapper iObjectWrapper, boolean z2, boolean z3, int i3, boolean z4, boolean z5) {
        super(context, i2, iObjectWrapper, z2, z3);
        this.T = 24;
        this.U = i3;
        this.f16934b0 = z4;
        this.f16935c0 = z5;
    }

    private void q() {
        ((RelativeLayout.LayoutParams) this.P.getLayoutParams()).setMarginStart(i8.a(this.f16903c, this.T - 12));
        ((RelativeLayout.LayoutParams) this.Q.getLayoutParams()).setMarginEnd(i8.a(this.f16903c, this.T - 12));
        ((RelativeLayout.LayoutParams) this.R.getLayoutParams()).setMarginStart(i8.a(this.f16903c, this.T - 12));
        ((RelativeLayout.LayoutParams) this.S.getLayoutParams()).setMarginEnd(i8.a(this.f16903c, this.T - 12));
    }

    private void r() {
        Context context = this.f16903c;
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    private void s() {
        this.f16904d.findViewById(R.id.scan_title_landscape).setVisibility(8);
        this.f16904d.findViewById(R.id.scan_title).setVisibility(0);
    }

    private void t() {
        int currentModeType = ((UiModeManager) ((Activity) this.f16903c).getSystemService("uimode")).getCurrentModeType();
        if (currentModeType == 3) {
            this.T = 32;
            return;
        }
        if (currentModeType == 4) {
            this.T = 48;
        } else if (currentModeType != 6) {
            this.T = 24;
        } else {
            this.T = 26;
        }
    }

    private void u() {
        if (Build.VERSION.SDK_INT >= 24) {
            Context context = this.f16903c;
            Display defaultDisplay = (context instanceof Activity ? ((Activity) context).getWindowManager() : (WindowManager) context.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            if (defaultDisplay.getWidth() < (w7.c((Activity) this.f16903c) ? point.x : point.y)) {
                s();
            } else {
                v();
            }
        }
    }

    private void v() {
        this.V.setVisibility(8);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.O.getLayoutParams();
        layoutParams.setMargins(0, l(), 0, 0);
        this.O.setLayoutParams(layoutParams);
        this.f16904d.findViewById(R.id.scan_title_landscape).setVisibility(0);
        this.f16904d.findViewById(R.id.scan_title).setVisibility(8);
    }

    @Override // com.huawei.hms.scankit.i, com.huawei.hms.scankit.e
    protected void e() {
        super.e();
        this.f16906f.c(this.f16935c0);
        this.J = (ImageView) this.f16904d.findViewById(R.id.ivScan);
        this.K = (ImageView) this.f16904d.findViewById(R.id.back_img_in);
        this.L = (ImageView) this.f16904d.findViewById(R.id.img_btn);
        this.M = (ImageView) this.f16904d.findViewById(R.id.scankit_back_img_in_land);
        this.N = (ImageView) this.f16904d.findViewById(R.id.scankit_img_btn_in_land);
        this.O = (RelativeLayout) this.f16904d.findViewById(R.id.scan_title_landscape);
        this.P = (LinearLayout) this.f16904d.findViewById(R.id.back_layout);
        this.Q = (LinearLayout) this.f16904d.findViewById(R.id.gallery_Layout);
        this.R = (LinearLayout) this.f16904d.findViewById(R.id.back_layout_landscape);
        this.S = (LinearLayout) this.f16904d.findViewById(R.id.gallery_Layout_landscape);
        this.V = (LinearLayout) this.f16904d.findViewById(R.id.scan_title_layout);
        this.W = (TextView) this.f16904d.findViewById(R.id.title_scan);
        this.X = (TextView) this.f16904d.findViewById(R.id.title_scan_level_two);
        this.Y = (TextView) this.f16904d.findViewById(R.id.scankit_title_scan_land);
        this.Z = (TextView) this.f16904d.findViewById(R.id.scankit_title_scan_land_level_two);
        this.f16933a0 = (LinearLayout) this.f16904d.findViewById(R.id.scanLayout);
        this.P.setOnClickListener(this);
        this.Q.setOnClickListener(this);
        this.K.setOnClickListener(this);
        this.L.setOnClickListener(this);
        this.M.setOnClickListener(this);
        this.N.setOnClickListener(this);
        this.R.setOnClickListener(this);
        this.S.setOnClickListener(this);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.V.getLayoutParams();
        layoutParams.setMargins(0, i8.a(this.f16903c, 80) + w7.d(this.f16903c), 0, 0);
        layoutParams.addRule(6);
        this.V.setLayoutParams(layoutParams);
        this.V.bringToFront();
        this.V.setVisibility(0);
        t();
        q();
        if (this.U == 0) {
            this.W.setText(R.string.scankit_title_all);
            TextView textView = this.W;
            textView.setContentDescription(textView.getResources().getString(R.string.scankit_title_all));
            this.X.setText(R.string.scankit_title_all_level_two);
            this.Y.setText(R.string.scankit_title_all);
            TextView textView2 = this.Y;
            textView2.setContentDescription(textView2.getResources().getString(R.string.scankit_title_all));
            this.Z.setText(R.string.scankit_title_all_level_two);
        } else {
            this.W.setText(R.string.scankit_title_qr);
            TextView textView3 = this.W;
            textView3.setContentDescription(textView3.getResources().getString(R.string.scankit_title_qr));
            this.X.setText(R.string.scankit_title_qr_level_two);
            this.Y.setText(R.string.scankit_title_qr);
            TextView textView4 = this.Y;
            textView4.setContentDescription(textView4.getResources().getString(R.string.scankit_title_qr));
            this.Z.setText(R.string.scankit_title_qr_level_two);
        }
        this.Z.bringToFront();
        try {
            ImageView imageView = this.J;
            if (imageView != null) {
                ViewGroup.LayoutParams layoutParams2 = imageView.getLayoutParams();
                if (i8.b(this.f16903c).x > i8.a(this.f16903c, DeviceCommonConstants.WHAT_DHCP_TIMEOUT)) {
                    layoutParams2.width = i8.b(this.f16903c).x / 2;
                } else {
                    layoutParams2.width = i8.b(this.f16903c).x;
                }
                this.J.setLayoutParams(layoutParams2);
                ScanDrawable scanDrawable = new ScanDrawable(this.f16903c.getResources());
                this.I = scanDrawable;
                this.J.setImageDrawable(scanDrawable);
            }
            if (!w7.j(this.f16903c)) {
                if (w7.c((Activity) this.f16903c)) {
                    s();
                } else {
                    u();
                }
                if (w7.f(this.f16903c) && !w7.b((Activity) this.f16903c) && !w7.h(this.f16903c)) {
                    if (w7.f()) {
                        u();
                    } else {
                        v();
                    }
                }
            }
            if (w7.f(this.f16903c)) {
                LinearLayout linearLayout = (LinearLayout) this.f16904d.findViewById(R.id.scan_title_layout);
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
                if (w7.b((Activity) this.f16903c)) {
                    layoutParams3.setMargins(0, i8.a(this.f16903c, 80), 0, 0);
                    layoutParams3.addRule(10);
                } else if (!w7.h(this.f16903c)) {
                    if (w7.f()) {
                        layoutParams3.setMargins(0, i8.a(this.f16903c, 80), 0, 0);
                        layoutParams3.addRule(10);
                    } else {
                        layoutParams3.setMargins(0, i8.a(this.f16903c, 0), 0, 0);
                        layoutParams3.addRule(12);
                    }
                }
                linearLayout.setLayoutParams(layoutParams3);
                linearLayout.bringToFront();
            }
            if (!w7.c((Activity) this.f16903c) && w7.k(this.f16903c) && !w7.j(this.f16903c) && !w7.f(this.f16903c)) {
                if (Locale.getDefault() == null || !w7.d()) {
                    ((RelativeLayout.LayoutParams) this.S.getLayoutParams()).setMarginEnd(i8.a(this.f16903c, this.T - 12) + i8.a(this.f16903c));
                } else {
                    RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.O.getLayoutParams();
                    layoutParams4.setMarginEnd(i8.a(this.f16903c, 0));
                    layoutParams4.setMarginStart(i8.a(this.f16903c, this.T - 12) + i8.a(this.f16903c));
                    RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.f16933a0.getLayoutParams();
                    layoutParams5.setMarginEnd(i8.a(this.f16903c, 0));
                    layoutParams5.setMarginStart(i8.a(this.f16903c, this.T - 12) + i8.a(this.f16903c));
                }
            }
            if (!w7.c((Activity) this.f16903c) && w7.i(this.f16903c) && this.f16920t && this.f16934b0) {
                i();
            }
        } catch (NullPointerException unused) {
            Log.e("scankitSDk", "initView: NullPoint");
        } catch (RuntimeException unused2) {
            Log.e("scankitSDk", "initView: Exception");
        }
        ((RelativeLayout) this.f16904d.findViewById(R.id.scan_parent_view)).getViewTreeObserver().addOnGlobalLayoutListener(new a());
        if (this.f16934b0) {
            return;
        }
        this.J.setVisibility(8);
    }

    @Override // com.huawei.hms.scankit.i
    protected void j() {
        if (Locale.getDefault() == null || !w7.d()) {
            return;
        }
        this.K = (ImageView) this.f16904d.findViewById(R.id.back_img_in);
        this.M = (ImageView) this.f16904d.findViewById(R.id.scankit_back_img_in_land);
        ImageView imageView = this.K;
        if (imageView != null) {
            imageView.setImageResource(R.drawable.scankit_ic_back_mirroring);
            this.M.setImageResource(R.drawable.scankit_ic_back_mirroring);
        }
        k();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.back_layout) {
            r();
        }
        if (view.getId() == R.id.back_layout_landscape) {
            r();
        }
        if (view.getId() == R.id.back_img_in) {
            r();
        }
        if (view.getId() == R.id.scankit_back_img_in_land) {
            r();
        }
        if (view.getId() == R.id.gallery_Layout) {
            this.f16909i.onClick(view);
        }
        if (view.getId() == R.id.gallery_Layout_landscape) {
            this.f16909i.onClick(view);
        }
        if (view.getId() == R.id.scankit_img_btn_in_land) {
            this.f16909i.onClick(view);
        }
        if (view.getId() == R.id.img_btn) {
            this.f16909i.onClick(view);
        }
    }

    @Override // com.huawei.hms.scankit.e, com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onStart() {
        super.onStart();
        if (this.f16934b0) {
            ScanDrawable scanDrawable = this.I;
            if (scanDrawable != null) {
                if (scanDrawable.isRunning()) {
                    return;
                }
                this.I.start();
            } else {
                ScanDrawable scanDrawable2 = new ScanDrawable(this.f16903c.getResources());
                this.I = scanDrawable2;
                this.J.setImageDrawable(scanDrawable2);
                this.I.start();
            }
        }
    }

    @Override // com.huawei.hms.scankit.e, com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onStop() {
        ScanDrawable scanDrawable;
        super.onStop();
        if (this.f16934b0 && (scanDrawable = this.I) != null && scanDrawable.isRunning()) {
            this.I.stop();
        }
    }

    @Override // com.huawei.hms.scankit.i, com.huawei.hms.scankit.e
    protected ProviderRemoteView d() {
        return new ProviderRemoteView(DynamicModuleInitializer.getContext() == null ? this.f16903c : DynamicModuleInitializer.getContext(), false, false);
    }
}
