package com.huawei.hms.scankit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huawei.hms.feature.DynamicModuleInitializer;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.scankit.p.i8;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.w7;
import java.util.Locale;

/* loaded from: classes4.dex */
public class i extends e {
    protected ViewfinderView A;
    protected ImageView B;
    private ImageView C;
    private TextView D;
    private IObjectWrapper E;
    private RelativeLayout F;
    private int G;
    private int H;

    class a implements ViewTreeObserver.OnGlobalLayoutListener {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ RelativeLayout f16937a;

        a(RelativeLayout relativeLayout) {
            this.f16937a = relativeLayout;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (i.this.G == this.f16937a.getHeight() || i.this.H == this.f16937a.getWidth()) {
                return;
            }
            i.this.G = this.f16937a.getHeight();
            i.this.H = this.f16937a.getWidth();
            i.this.p();
        }
    }

    class b implements View.OnClickListener {
        b() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            View.OnClickListener onClickListener = i.this.f16909i;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    class c implements View.OnClickListener {
        c() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.isSelected()) {
                i.this.f();
                i.this.o();
                i.this.C.setContentDescription(i.this.C.getResources().getString(R.string.scankit_light));
            } else {
                i.this.g();
                view.setSelected(true);
                i.this.D.setText(R.string.scankit_light_off);
                i.this.C.setContentDescription(i.this.C.getResources().getString(R.string.scankit_light_off));
            }
        }
    }

    public i(Context context, int i2, IObjectWrapper iObjectWrapper, boolean z2, boolean z3) {
        super(context, i2, null, iObjectWrapper, z2, false, z3);
        this.G = 0;
        this.H = 0;
        this.E = iObjectWrapper;
        this.f16903c = context;
        this.f16902b = i2;
        this.f16916p = z2;
        this.f16914n = new Rect(-1, -1, -1, -1);
        this.f16919s = z3;
    }

    private boolean n() {
        return this.f16903c.getResources().getDisplayMetrics().widthPixels > 1990 && this.f16903c.getResources().getDisplayMetrics().widthPixels < 2300 && this.f16903c.getResources().getDisplayMetrics().heightPixels > 2190 && this.f16903c.getResources().getDisplayMetrics().heightPixels < 2600;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void o() {
        ImageView imageView = this.C;
        if (imageView != null) {
            imageView.setSelected(false);
        }
        TextView textView = this.D;
        if (textView != null) {
            textView.setText(R.string.scankit_light);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void p() {
        if ((w7.f(this.f16903c) && w7.b((Activity) this.f16903c)) || this.F == null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.F.getLayoutParams().width, this.F.getLayoutParams().height);
        layoutParams.setMargins(0, m(), 0, 0);
        this.F.setLayoutParams(layoutParams);
    }

    @Override // com.huawei.hms.scankit.e, com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public IObjectWrapper getView() {
        return ObjectWrapper.wrap(this.f16904d);
    }

    protected void i() {
        RelativeLayout relativeLayout = (RelativeLayout) this.f16904d.findViewById(R.id.scan_parent_view);
        this.f16923w = new LinearLayout(this.f16903c);
        o4.d("Scankit", "initlight adJustLightLayout open");
        this.f16923w.setVisibility(0);
        this.f16923w.setOrientation(1);
        ViewGroup viewGroup = (ViewGroup) this.C.getParent();
        viewGroup.removeView(this.C);
        viewGroup.removeView(this.D);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = i8.a(this.f16903c, 6);
        this.f16923w.setGravity(16);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i8.a(this.f16903c, 214), -1);
        layoutParams2.addRule(11);
        layoutParams2.addRule(15);
        LinearLayout linearLayout = new LinearLayout(this.f16903c);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams3.setMargins(i8.a(this.f16903c, 24), 0, i8.a(this.f16903c, 24), 0);
        linearLayout.setLayoutParams(layoutParams3);
        linearLayout.setGravity(16);
        linearLayout.setOrientation(1);
        ((LinearLayout.LayoutParams) this.C.getLayoutParams()).setMargins(0, 0, 0, 0);
        linearLayout.addView(this.C);
        linearLayout.addView(this.D, layoutParams);
        this.f16923w.addView(linearLayout);
        relativeLayout.addView(this.f16923w, layoutParams2);
    }

    protected void j() {
        if (Locale.getDefault() == null || !w7.d()) {
            return;
        }
        TextView textView = (TextView) this.f16904d.findViewById(R.id.title_scan);
        ImageView imageView = (ImageView) this.f16904d.findViewById(R.id.back_img_in);
        if (imageView != null) {
            imageView.setRotation(180.0f);
        }
        if (textView != null) {
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
                layoutParams2.addRule(1, R.id.img_btn);
                layoutParams2.rightMargin = 200;
                textView.setLayoutParams(layoutParams);
            }
        }
        k();
    }

    protected void k() {
        ViewGroup.LayoutParams layoutParams = this.f16923w.getLayoutParams();
        Context context = this.f16903c;
        if (context == null || context.getResources() == null || this.f16903c.getResources().getDisplayMetrics() == null || !n() || !(layoutParams instanceof FrameLayout.LayoutParams)) {
            return;
        }
        ((FrameLayout.LayoutParams) layoutParams).bottomMargin = 150;
        this.f16923w.setLayoutParams(layoutParams);
    }

    protected int l() {
        int iM = m();
        Context context = this.f16903c;
        if (context == null || context.getResources() == null) {
            return iM;
        }
        try {
            int identifier = this.f16903c.getResources().getIdentifier("hw_multiwindow_height_of_drag_bar", "dimen", "androidhwext");
            return identifier > 0 ? this.f16903c.getResources().getDimensionPixelSize(identifier) : iM;
        } catch (Exception e2) {
            Log.e("IRemoteViewDelegateImpl", "getMultiWindowDragBarHeight exception: " + e2.getMessage());
            return iM;
        }
    }

    protected int m() {
        int identifier;
        Context context = this.f16903c;
        if (context == null || context.getResources() == null || (identifier = this.f16903c.getResources().getIdentifier("status_bar_height", "dimen", "android")) <= 0) {
            return 0;
        }
        return this.f16903c.getResources().getDimensionPixelSize(identifier);
    }

    @Override // com.huawei.hms.scankit.e, android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i2) {
    }

    @Override // com.huawei.hms.scankit.e, com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.huawei.hms.scankit.e, com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onResume() {
        com.huawei.hms.scankit.b bVar = this.f16906f;
        if (bVar.f16821i == null) {
            bVar.f16821i = this.f16905e;
        }
        bVar.f();
        o();
        SensorManager sensorManager = this.f16908h;
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(5), 2);
    }

    @Override // com.huawei.hms.scankit.e, com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void setOnClickListener(IObjectWrapper iObjectWrapper) {
        this.f16909i = (View.OnClickListener) ObjectWrapper.unwrap(iObjectWrapper);
    }

    @Override // com.huawei.hms.scankit.e, com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void turnOffLight() throws RemoteException {
    }

    @Override // com.huawei.hms.scankit.e, com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void turnOnLight() throws RemoteException {
    }

    @Override // com.huawei.hms.scankit.e
    protected void c() {
        super.c();
        o4.d("Scankit", "initlight mIsContains " + this.f16910j);
        if (!this.f16910j && this.f16920t && w7.a(this.f16903c)) {
            o4.d("Scankit", "initlight open");
            this.f16923w.setVisibility(0);
        }
    }

    @Override // com.huawei.hms.scankit.e
    protected ProviderRemoteView d() {
        return new ProviderRemoteView(DynamicModuleInitializer.getContext() == null ? this.f16903c : DynamicModuleInitializer.getContext(), false);
    }

    @Override // com.huawei.hms.scankit.e
    protected void e() {
        ProviderRemoteView providerRemoteViewD = d();
        this.f16904d = providerRemoteViewD;
        this.F = (RelativeLayout) providerRemoteViewD.findViewById(R.id.scan_title);
        p();
        RelativeLayout relativeLayout = (RelativeLayout) this.f16904d.findViewById(R.id.scan_parent_view);
        if (relativeLayout != null) {
            relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new a(relativeLayout));
        }
        this.f16905e = (TextureView) this.f16904d.findViewById(R.id.surfaceView);
        this.A = (ViewfinderView) this.f16904d.findViewById(R.id.viewfinderView);
        com.huawei.hms.scankit.b bVar = new com.huawei.hms.scankit.b(this.f16903c, this.f16905e, this.A, this.f16914n, this.f16902b, this.E, this.f16916p, "DefaultView", false);
        this.f16906f = bVar;
        bVar.b(this.f16919s);
        ImageView imageView = (ImageView) this.f16904d.findViewById(R.id.img_btn);
        this.B = imageView;
        imageView.setOnClickListener(new b());
        this.f16923w = (LinearLayout) this.f16904d.findViewById(R.id.flash_light_ll);
        this.C = (ImageView) this.f16904d.findViewById(R.id.ivFlash);
        c();
        this.C.setOnClickListener(new c());
        this.D = (TextView) this.f16904d.findViewById(R.id.flash_light_text);
        a((Point) null, true);
        j();
        k();
    }
}
