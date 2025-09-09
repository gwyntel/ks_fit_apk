package com.afollestad.materialdialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.core.content.res.ResourcesCompat;
import androidx.media3.common.C;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.DefaultRvAdapter;
import com.afollestad.materialdialogs.internal.MDButton;
import com.afollestad.materialdialogs.internal.MDRootLayout;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.afollestad.materialdialogs.util.DialogUtils;
import com.afollestad.materialdialogs.util.RippleHelper;
import com.afollestad.materialdialogs.util.TypefaceHelper;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class MaterialDialog extends DialogBase implements View.OnClickListener, DefaultRvAdapter.InternalListCallback {

    /* renamed from: b, reason: collision with root package name */
    protected final Builder f8165b;

    /* renamed from: c, reason: collision with root package name */
    protected ImageView f8166c;

    /* renamed from: d, reason: collision with root package name */
    protected TextView f8167d;

    /* renamed from: e, reason: collision with root package name */
    protected TextView f8168e;

    /* renamed from: f, reason: collision with root package name */
    EditText f8169f;

    /* renamed from: g, reason: collision with root package name */
    RecyclerView f8170g;

    /* renamed from: h, reason: collision with root package name */
    View f8171h;
    private final Handler handler;

    /* renamed from: i, reason: collision with root package name */
    FrameLayout f8172i;

    /* renamed from: j, reason: collision with root package name */
    ProgressBar f8173j;

    /* renamed from: k, reason: collision with root package name */
    TextView f8174k;

    /* renamed from: l, reason: collision with root package name */
    TextView f8175l;

    /* renamed from: m, reason: collision with root package name */
    TextView f8176m;

    /* renamed from: n, reason: collision with root package name */
    CheckBox f8177n;

    /* renamed from: o, reason: collision with root package name */
    MDButton f8178o;

    /* renamed from: p, reason: collision with root package name */
    MDButton f8179p;

    /* renamed from: q, reason: collision with root package name */
    MDButton f8180q;

    /* renamed from: r, reason: collision with root package name */
    ListType f8181r;

    /* renamed from: s, reason: collision with root package name */
    List f8182s;

    /* renamed from: com.afollestad.materialdialogs.MaterialDialog$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8188a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f8189b;

        static {
            int[] iArr = new int[ListType.values().length];
            f8189b = iArr;
            try {
                iArr[ListType.REGULAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8189b[ListType.SINGLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f8189b[ListType.MULTI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[DialogAction.values().length];
            f8188a = iArr2;
            try {
                iArr2[DialogAction.NEUTRAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f8188a[DialogAction.NEGATIVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f8188a[DialogAction.POSITIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static class Builder {
        protected SingleButtonCallback A;
        protected NumberFormat A0;
        protected SingleButtonCallback B;
        protected boolean B0;
        protected SingleButtonCallback C;
        protected boolean C0;
        protected SingleButtonCallback D;
        protected boolean D0;
        protected ListCallback E;
        protected boolean E0;
        protected ListLongCallback F;
        protected boolean F0;
        protected ListCallbackSingleChoice G;
        protected boolean G0;
        protected ListCallbackMultiChoice H;
        protected boolean H0;
        protected boolean I;
        protected boolean I0;
        protected boolean J;
        protected boolean J0;
        protected Theme K;
        protected int K0;
        protected boolean L;
        protected int L0;
        protected boolean M;
        protected int M0;
        protected float N;
        protected int N0;
        protected int O;
        protected int O0;
        protected Integer[] P;
        protected Object P0;
        protected Integer[] Q;
        protected boolean R;
        protected Typeface S;
        protected Typeface T;
        protected Drawable U;
        protected boolean V;
        protected int W;
        protected RecyclerView.Adapter X;
        protected RecyclerView.LayoutManager Y;
        protected DialogInterface.OnDismissListener Z;

        /* renamed from: a, reason: collision with root package name */
        protected final Context f8190a;

        /* renamed from: a0, reason: collision with root package name */
        protected DialogInterface.OnCancelListener f8191a0;

        /* renamed from: b, reason: collision with root package name */
        protected CharSequence f8192b;

        /* renamed from: b0, reason: collision with root package name */
        protected DialogInterface.OnKeyListener f8193b0;

        /* renamed from: c, reason: collision with root package name */
        protected GravityEnum f8194c;

        /* renamed from: c0, reason: collision with root package name */
        protected DialogInterface.OnShowListener f8195c0;

        /* renamed from: d, reason: collision with root package name */
        protected GravityEnum f8196d;

        /* renamed from: d0, reason: collision with root package name */
        protected StackingBehavior f8197d0;

        /* renamed from: e, reason: collision with root package name */
        protected GravityEnum f8198e;

        /* renamed from: e0, reason: collision with root package name */
        protected boolean f8199e0;

        /* renamed from: f, reason: collision with root package name */
        protected GravityEnum f8200f;

        /* renamed from: f0, reason: collision with root package name */
        protected int f8201f0;

        /* renamed from: g, reason: collision with root package name */
        protected GravityEnum f8202g;

        /* renamed from: g0, reason: collision with root package name */
        protected int f8203g0;

        /* renamed from: h, reason: collision with root package name */
        protected int f8204h;

        /* renamed from: h0, reason: collision with root package name */
        protected int f8205h0;

        /* renamed from: i, reason: collision with root package name */
        protected int f8206i;

        /* renamed from: i0, reason: collision with root package name */
        protected boolean f8207i0;

        /* renamed from: j, reason: collision with root package name */
        protected int f8208j;

        /* renamed from: j0, reason: collision with root package name */
        protected boolean f8209j0;

        /* renamed from: k, reason: collision with root package name */
        protected CharSequence f8210k;

        /* renamed from: k0, reason: collision with root package name */
        protected int f8211k0;

        /* renamed from: l, reason: collision with root package name */
        protected ArrayList f8212l;

        /* renamed from: l0, reason: collision with root package name */
        protected int f8213l0;

        /* renamed from: m, reason: collision with root package name */
        protected CharSequence f8214m;

        /* renamed from: m0, reason: collision with root package name */
        protected CharSequence f8215m0;

        /* renamed from: n, reason: collision with root package name */
        protected CharSequence f8216n;

        /* renamed from: n0, reason: collision with root package name */
        protected CharSequence f8217n0;

        /* renamed from: o, reason: collision with root package name */
        protected CharSequence f8218o;

        /* renamed from: o0, reason: collision with root package name */
        protected InputCallback f8219o0;

        /* renamed from: p, reason: collision with root package name */
        protected boolean f8220p;

        /* renamed from: p0, reason: collision with root package name */
        protected boolean f8221p0;

        /* renamed from: q, reason: collision with root package name */
        protected boolean f8222q;

        /* renamed from: q0, reason: collision with root package name */
        protected int f8223q0;

        /* renamed from: r, reason: collision with root package name */
        protected boolean f8224r;

        /* renamed from: r0, reason: collision with root package name */
        protected boolean f8225r0;

        /* renamed from: s, reason: collision with root package name */
        protected View f8226s;

        /* renamed from: s0, reason: collision with root package name */
        protected int f8227s0;

        /* renamed from: t, reason: collision with root package name */
        protected int f8228t;

        /* renamed from: t0, reason: collision with root package name */
        protected int f8229t0;

        /* renamed from: u, reason: collision with root package name */
        protected ColorStateList f8230u;

        /* renamed from: u0, reason: collision with root package name */
        protected int f8231u0;

        /* renamed from: v, reason: collision with root package name */
        protected ColorStateList f8232v;

        /* renamed from: v0, reason: collision with root package name */
        protected int[] f8233v0;

        /* renamed from: w, reason: collision with root package name */
        protected ColorStateList f8234w;

        /* renamed from: w0, reason: collision with root package name */
        protected CharSequence f8235w0;

        /* renamed from: x, reason: collision with root package name */
        protected ColorStateList f8236x;

        /* renamed from: x0, reason: collision with root package name */
        protected boolean f8237x0;

        /* renamed from: y, reason: collision with root package name */
        protected ColorStateList f8238y;

        /* renamed from: y0, reason: collision with root package name */
        protected CompoundButton.OnCheckedChangeListener f8239y0;

        /* renamed from: z, reason: collision with root package name */
        protected ButtonCallback f8240z;
        protected String z0;

        public Builder(@NonNull Context context) {
            GravityEnum gravityEnum = GravityEnum.START;
            this.f8194c = gravityEnum;
            this.f8196d = gravityEnum;
            this.f8198e = GravityEnum.END;
            this.f8200f = gravityEnum;
            this.f8202g = gravityEnum;
            this.f8204h = 0;
            this.f8206i = -1;
            this.f8208j = -1;
            this.I = false;
            this.J = false;
            Theme theme = Theme.LIGHT;
            this.K = theme;
            this.L = true;
            this.M = true;
            this.N = 1.2f;
            this.O = -1;
            this.P = null;
            this.Q = null;
            this.R = true;
            this.W = -1;
            this.f8211k0 = -2;
            this.f8213l0 = 0;
            this.f8223q0 = -1;
            this.f8227s0 = -1;
            this.f8229t0 = -1;
            this.f8231u0 = 0;
            this.C0 = false;
            this.D0 = false;
            this.E0 = false;
            this.F0 = false;
            this.G0 = false;
            this.H0 = false;
            this.I0 = false;
            this.J0 = false;
            this.f8190a = context;
            int iResolveColor = DialogUtils.resolveColor(context, R.attr.colorAccent, DialogUtils.getColor(context, R.color.md_material_blue_600));
            this.f8228t = iResolveColor;
            int iResolveColor2 = DialogUtils.resolveColor(context, android.R.attr.colorAccent, iResolveColor);
            this.f8228t = iResolveColor2;
            this.f8232v = DialogUtils.getActionTextStateList(context, iResolveColor2);
            this.f8234w = DialogUtils.getActionTextStateList(context, this.f8228t);
            this.f8236x = DialogUtils.getActionTextStateList(context, this.f8228t);
            this.f8238y = DialogUtils.getActionTextStateList(context, DialogUtils.resolveColor(context, R.attr.md_link_color, this.f8228t));
            this.f8204h = DialogUtils.resolveColor(context, R.attr.md_btn_ripple_color, DialogUtils.resolveColor(context, R.attr.colorControlHighlight, DialogUtils.resolveColor(context, android.R.attr.colorControlHighlight)));
            this.A0 = NumberFormat.getPercentInstance();
            this.z0 = "%1d/%2d";
            this.K = DialogUtils.isColorDark(DialogUtils.resolveColor(context, android.R.attr.textColorPrimary)) ? theme : Theme.DARK;
            checkSingleton();
            this.f8194c = DialogUtils.resolveGravityEnum(context, R.attr.md_title_gravity, this.f8194c);
            this.f8196d = DialogUtils.resolveGravityEnum(context, R.attr.md_content_gravity, this.f8196d);
            this.f8198e = DialogUtils.resolveGravityEnum(context, R.attr.md_btnstacked_gravity, this.f8198e);
            this.f8200f = DialogUtils.resolveGravityEnum(context, R.attr.md_items_gravity, this.f8200f);
            this.f8202g = DialogUtils.resolveGravityEnum(context, R.attr.md_buttons_gravity, this.f8202g);
            try {
                typeface(DialogUtils.resolveString(context, R.attr.md_medium_font), DialogUtils.resolveString(context, R.attr.md_regular_font));
            } catch (Throwable unused) {
            }
            if (this.T == null) {
                try {
                    this.T = Typeface.create("sans-serif-medium", 0);
                } catch (Throwable unused2) {
                    this.T = Typeface.DEFAULT_BOLD;
                }
            }
            if (this.S == null) {
                try {
                    this.S = Typeface.create(C.SANS_SERIF_NAME, 0);
                } catch (Throwable unused3) {
                    Typeface typeface = Typeface.SANS_SERIF;
                    this.S = typeface;
                    if (typeface == null) {
                        this.S = Typeface.DEFAULT;
                    }
                }
            }
        }

        private void checkSingleton() {
            if (ThemeSingleton.get(false) == null) {
                return;
            }
            ThemeSingleton themeSingleton = ThemeSingleton.get();
            if (themeSingleton.darkTheme) {
                this.K = Theme.DARK;
            }
            int i2 = themeSingleton.titleColor;
            if (i2 != 0) {
                this.f8206i = i2;
            }
            int i3 = themeSingleton.contentColor;
            if (i3 != 0) {
                this.f8208j = i3;
            }
            ColorStateList colorStateList = themeSingleton.positiveColor;
            if (colorStateList != null) {
                this.f8232v = colorStateList;
            }
            ColorStateList colorStateList2 = themeSingleton.neutralColor;
            if (colorStateList2 != null) {
                this.f8236x = colorStateList2;
            }
            ColorStateList colorStateList3 = themeSingleton.negativeColor;
            if (colorStateList3 != null) {
                this.f8234w = colorStateList3;
            }
            int i4 = themeSingleton.itemColor;
            if (i4 != 0) {
                this.f8205h0 = i4;
            }
            Drawable drawable = themeSingleton.icon;
            if (drawable != null) {
                this.U = drawable;
            }
            int i5 = themeSingleton.backgroundColor;
            if (i5 != 0) {
                this.f8203g0 = i5;
            }
            int i6 = themeSingleton.dividerColor;
            if (i6 != 0) {
                this.f8201f0 = i6;
            }
            int i7 = themeSingleton.btnSelectorStacked;
            if (i7 != 0) {
                this.L0 = i7;
            }
            int i8 = themeSingleton.listSelector;
            if (i8 != 0) {
                this.K0 = i8;
            }
            int i9 = themeSingleton.btnSelectorPositive;
            if (i9 != 0) {
                this.M0 = i9;
            }
            int i10 = themeSingleton.btnSelectorNeutral;
            if (i10 != 0) {
                this.N0 = i10;
            }
            int i11 = themeSingleton.btnSelectorNegative;
            if (i11 != 0) {
                this.O0 = i11;
            }
            int i12 = themeSingleton.widgetColor;
            if (i12 != 0) {
                this.f8228t = i12;
            }
            ColorStateList colorStateList4 = themeSingleton.linkColor;
            if (colorStateList4 != null) {
                this.f8238y = colorStateList4;
            }
            this.f8194c = themeSingleton.titleGravity;
            this.f8196d = themeSingleton.contentGravity;
            this.f8198e = themeSingleton.btnStackedGravity;
            this.f8200f = themeSingleton.itemsGravity;
            this.f8202g = themeSingleton.buttonsGravity;
        }

        public Builder adapter(@NonNull RecyclerView.Adapter<?> adapter, @Nullable RecyclerView.LayoutManager layoutManager) {
            if (this.f8226s != null) {
                throw new IllegalStateException("You cannot set adapter() when you're using a custom view.");
            }
            if (layoutManager != null && !(layoutManager instanceof LinearLayoutManager) && !(layoutManager instanceof GridLayoutManager)) {
                throw new IllegalStateException("You can currently only use LinearLayoutManager and GridLayoutManager with this library.");
            }
            this.X = adapter;
            this.Y = layoutManager;
            return this;
        }

        public Builder alwaysCallInputCallback() {
            this.f8225r0 = true;
            return this;
        }

        public Builder alwaysCallMultiChoiceCallback() {
            this.I = true;
            return this;
        }

        public Builder alwaysCallSingleChoiceCallback() {
            this.J = true;
            return this;
        }

        public Builder autoDismiss(boolean z2) {
            this.R = z2;
            return this;
        }

        public Builder backgroundColor(@ColorInt int i2) {
            this.f8203g0 = i2;
            return this;
        }

        public Builder backgroundColorAttr(@AttrRes int i2) {
            return backgroundColor(DialogUtils.resolveColor(this.f8190a, i2));
        }

        public Builder backgroundColorRes(@ColorRes int i2) {
            return backgroundColor(DialogUtils.getColor(this.f8190a, i2));
        }

        public Builder btnSelector(@DrawableRes int i2) {
            this.M0 = i2;
            this.N0 = i2;
            this.O0 = i2;
            return this;
        }

        public Builder btnSelectorStacked(@DrawableRes int i2) {
            this.L0 = i2;
            return this;
        }

        public Builder btnStackedGravity(@NonNull GravityEnum gravityEnum) {
            this.f8198e = gravityEnum;
            return this;
        }

        @UiThread
        public MaterialDialog build() {
            return new MaterialDialog(this);
        }

        public Builder buttonRippleColor(@ColorInt int i2) {
            this.f8204h = i2;
            return this;
        }

        public Builder buttonRippleColorAttr(@AttrRes int i2) {
            return buttonRippleColor(DialogUtils.resolveColor(this.f8190a, i2));
        }

        public Builder buttonRippleColorRes(@ColorRes int i2) {
            return buttonRippleColor(DialogUtils.getColor(this.f8190a, i2));
        }

        public Builder buttonsGravity(@NonNull GravityEnum gravityEnum) {
            this.f8202g = gravityEnum;
            return this;
        }

        public Builder callback(@NonNull ButtonCallback buttonCallback) {
            this.f8240z = buttonCallback;
            return this;
        }

        public Builder cancelListener(@NonNull DialogInterface.OnCancelListener onCancelListener) {
            this.f8191a0 = onCancelListener;
            return this;
        }

        public Builder cancelable(boolean z2) {
            this.L = z2;
            this.M = z2;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean z2) {
            this.M = z2;
            return this;
        }

        public Builder checkBoxPrompt(@NonNull CharSequence charSequence, boolean z2, @Nullable CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.f8235w0 = charSequence;
            this.f8237x0 = z2;
            this.f8239y0 = onCheckedChangeListener;
            return this;
        }

        public Builder checkBoxPromptRes(@StringRes int i2, boolean z2, @Nullable CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            return checkBoxPrompt(this.f8190a.getResources().getText(i2), z2, onCheckedChangeListener);
        }

        public Builder choiceWidgetColor(@Nullable ColorStateList colorStateList) {
            this.f8230u = colorStateList;
            return this;
        }

        public Builder content(@StringRes int i2) {
            return content(i2, false);
        }

        public Builder contentColor(@ColorInt int i2) {
            this.f8208j = i2;
            this.D0 = true;
            return this;
        }

        public Builder contentColorAttr(@AttrRes int i2) {
            contentColor(DialogUtils.resolveColor(this.f8190a, i2));
            return this;
        }

        public Builder contentColorRes(@ColorRes int i2) {
            contentColor(DialogUtils.getColor(this.f8190a, i2));
            return this;
        }

        public Builder contentGravity(@NonNull GravityEnum gravityEnum) {
            this.f8196d = gravityEnum;
            return this;
        }

        public Builder contentLineSpacing(float f2) {
            this.N = f2;
            return this;
        }

        public Builder customView(@LayoutRes int i2, boolean z2) {
            return customView(LayoutInflater.from(this.f8190a).inflate(i2, (ViewGroup) null), z2);
        }

        public Builder dismissListener(@NonNull DialogInterface.OnDismissListener onDismissListener) {
            this.Z = onDismissListener;
            return this;
        }

        public Builder dividerColor(@ColorInt int i2) {
            this.f8201f0 = i2;
            this.J0 = true;
            return this;
        }

        public Builder dividerColorAttr(@AttrRes int i2) {
            return dividerColor(DialogUtils.resolveColor(this.f8190a, i2));
        }

        public Builder dividerColorRes(@ColorRes int i2) {
            return dividerColor(DialogUtils.getColor(this.f8190a, i2));
        }

        public final Context getContext() {
            return this.f8190a;
        }

        public final int getItemColor() {
            return this.f8205h0;
        }

        public final Typeface getRegularFont() {
            return this.S;
        }

        public Builder icon(@NonNull Drawable drawable) {
            this.U = drawable;
            return this;
        }

        public Builder iconAttr(@AttrRes int i2) {
            this.U = DialogUtils.resolveDrawable(this.f8190a, i2);
            return this;
        }

        public Builder iconRes(@DrawableRes int i2) {
            this.U = ResourcesCompat.getDrawable(this.f8190a.getResources(), i2, null);
            return this;
        }

        public Builder input(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2, boolean z2, @NonNull InputCallback inputCallback) {
            if (this.f8226s != null) {
                throw new IllegalStateException("You cannot set content() when you're using a custom view.");
            }
            this.f8219o0 = inputCallback;
            this.f8217n0 = charSequence;
            this.f8215m0 = charSequence2;
            this.f8221p0 = z2;
            return this;
        }

        public Builder inputRange(@IntRange(from = 0, to = 2147483647L) int i2, @IntRange(from = -1, to = 2147483647L) int i3) {
            return inputRange(i2, i3, 0);
        }

        public Builder inputRangeRes(@IntRange(from = 0, to = 2147483647L) int i2, @IntRange(from = -1, to = 2147483647L) int i3, @ColorRes int i4) {
            return inputRange(i2, i3, DialogUtils.getColor(this.f8190a, i4));
        }

        public Builder inputType(int i2) {
            this.f8223q0 = i2;
            return this;
        }

        public Builder items(@NonNull Collection collection) {
            if (collection.size() > 0) {
                CharSequence[] charSequenceArr = new CharSequence[collection.size()];
                Iterator it = collection.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    charSequenceArr[i2] = it.next().toString();
                    i2++;
                }
                items(charSequenceArr);
            } else if (collection.size() == 0) {
                this.f8212l = new ArrayList();
            }
            return this;
        }

        public Builder itemsCallback(@NonNull ListCallback listCallback) {
            this.E = listCallback;
            this.G = null;
            this.H = null;
            return this;
        }

        public Builder itemsCallbackMultiChoice(@Nullable Integer[] numArr, @NonNull ListCallbackMultiChoice listCallbackMultiChoice) {
            this.P = numArr;
            this.E = null;
            this.G = null;
            this.H = listCallbackMultiChoice;
            return this;
        }

        public Builder itemsCallbackSingleChoice(int i2, @NonNull ListCallbackSingleChoice listCallbackSingleChoice) {
            this.O = i2;
            this.E = null;
            this.G = listCallbackSingleChoice;
            this.H = null;
            return this;
        }

        public Builder itemsColor(@ColorInt int i2) {
            this.f8205h0 = i2;
            this.E0 = true;
            return this;
        }

        public Builder itemsColorAttr(@AttrRes int i2) {
            return itemsColor(DialogUtils.resolveColor(this.f8190a, i2));
        }

        public Builder itemsColorRes(@ColorRes int i2) {
            return itemsColor(DialogUtils.getColor(this.f8190a, i2));
        }

        public Builder itemsDisabledIndices(@Nullable Integer... numArr) {
            this.Q = numArr;
            return this;
        }

        public Builder itemsGravity(@NonNull GravityEnum gravityEnum) {
            this.f8200f = gravityEnum;
            return this;
        }

        public Builder itemsIds(@NonNull int[] iArr) {
            this.f8233v0 = iArr;
            return this;
        }

        public Builder itemsLongCallback(@NonNull ListLongCallback listLongCallback) {
            this.F = listLongCallback;
            this.G = null;
            this.H = null;
            return this;
        }

        public Builder keyListener(@NonNull DialogInterface.OnKeyListener onKeyListener) {
            this.f8193b0 = onKeyListener;
            return this;
        }

        public Builder limitIconToDefaultSize() {
            this.V = true;
            return this;
        }

        public Builder linkColor(@ColorInt int i2) {
            return linkColor(DialogUtils.getActionTextStateList(this.f8190a, i2));
        }

        public Builder linkColorAttr(@AttrRes int i2) {
            return linkColor(DialogUtils.resolveActionTextColorStateList(this.f8190a, i2, null));
        }

        public Builder linkColorRes(@ColorRes int i2) {
            return linkColor(DialogUtils.getActionTextColorStateList(this.f8190a, i2));
        }

        public Builder listSelector(@DrawableRes int i2) {
            this.K0 = i2;
            return this;
        }

        public Builder maxIconSize(int i2) {
            this.W = i2;
            return this;
        }

        public Builder maxIconSizeRes(@DimenRes int i2) {
            return maxIconSize((int) this.f8190a.getResources().getDimension(i2));
        }

        public Builder negativeColor(@ColorInt int i2) {
            return negativeColor(DialogUtils.getActionTextStateList(this.f8190a, i2));
        }

        public Builder negativeColorAttr(@AttrRes int i2) {
            return negativeColor(DialogUtils.resolveActionTextColorStateList(this.f8190a, i2, null));
        }

        public Builder negativeColorRes(@ColorRes int i2) {
            return negativeColor(DialogUtils.getActionTextColorStateList(this.f8190a, i2));
        }

        public Builder negativeFocus(boolean z2) {
            this.f8224r = z2;
            return this;
        }

        public Builder negativeText(@StringRes int i2) {
            return i2 == 0 ? this : negativeText(this.f8190a.getText(i2));
        }

        public Builder neutralColor(@ColorInt int i2) {
            return neutralColor(DialogUtils.getActionTextStateList(this.f8190a, i2));
        }

        public Builder neutralColorAttr(@AttrRes int i2) {
            return neutralColor(DialogUtils.resolveActionTextColorStateList(this.f8190a, i2, null));
        }

        public Builder neutralColorRes(@ColorRes int i2) {
            return neutralColor(DialogUtils.getActionTextColorStateList(this.f8190a, i2));
        }

        public Builder neutralFocus(boolean z2) {
            this.f8222q = z2;
            return this;
        }

        public Builder neutralText(@StringRes int i2) {
            return i2 == 0 ? this : neutralText(this.f8190a.getText(i2));
        }

        public Builder onAny(@NonNull SingleButtonCallback singleButtonCallback) {
            this.D = singleButtonCallback;
            return this;
        }

        public Builder onNegative(@NonNull SingleButtonCallback singleButtonCallback) {
            this.B = singleButtonCallback;
            return this;
        }

        public Builder onNeutral(@NonNull SingleButtonCallback singleButtonCallback) {
            this.C = singleButtonCallback;
            return this;
        }

        public Builder onPositive(@NonNull SingleButtonCallback singleButtonCallback) {
            this.A = singleButtonCallback;
            return this;
        }

        public Builder positiveColor(@ColorInt int i2) {
            return positiveColor(DialogUtils.getActionTextStateList(this.f8190a, i2));
        }

        public Builder positiveColorAttr(@AttrRes int i2) {
            return positiveColor(DialogUtils.resolveActionTextColorStateList(this.f8190a, i2, null));
        }

        public Builder positiveColorRes(@ColorRes int i2) {
            return positiveColor(DialogUtils.getActionTextColorStateList(this.f8190a, i2));
        }

        public Builder positiveFocus(boolean z2) {
            this.f8220p = z2;
            return this;
        }

        public Builder positiveText(@StringRes int i2) {
            if (i2 == 0) {
                return this;
            }
            positiveText(this.f8190a.getText(i2));
            return this;
        }

        public Builder progress(boolean z2, int i2) {
            if (this.f8226s != null) {
                throw new IllegalStateException("You cannot set progress() when you're using a custom view.");
            }
            if (z2) {
                this.f8207i0 = true;
                this.f8211k0 = -2;
            } else {
                this.B0 = false;
                this.f8207i0 = false;
                this.f8211k0 = -1;
                this.f8213l0 = i2;
            }
            return this;
        }

        public Builder progressIndeterminateStyle(boolean z2) {
            this.B0 = z2;
            return this;
        }

        public Builder progressNumberFormat(@NonNull String str) {
            this.z0 = str;
            return this;
        }

        public Builder progressPercentFormat(@NonNull NumberFormat numberFormat) {
            this.A0 = numberFormat;
            return this;
        }

        @UiThread
        public MaterialDialog show() {
            MaterialDialog materialDialogBuild = build();
            materialDialogBuild.show();
            return materialDialogBuild;
        }

        public Builder showListener(@NonNull DialogInterface.OnShowListener onShowListener) {
            this.f8195c0 = onShowListener;
            return this;
        }

        public Builder stackingBehavior(@NonNull StackingBehavior stackingBehavior) {
            this.f8197d0 = stackingBehavior;
            return this;
        }

        public Builder tag(@Nullable Object obj) {
            this.P0 = obj;
            return this;
        }

        public Builder theme(@NonNull Theme theme) {
            this.K = theme;
            return this;
        }

        public Builder title(@StringRes int i2) {
            title(this.f8190a.getText(i2));
            return this;
        }

        public Builder titleColor(@ColorInt int i2) {
            this.f8206i = i2;
            this.C0 = true;
            return this;
        }

        public Builder titleColorAttr(@AttrRes int i2) {
            return titleColor(DialogUtils.resolveColor(this.f8190a, i2));
        }

        public Builder titleColorRes(@ColorRes int i2) {
            return titleColor(DialogUtils.getColor(this.f8190a, i2));
        }

        public Builder titleGravity(@NonNull GravityEnum gravityEnum) {
            this.f8194c = gravityEnum;
            return this;
        }

        public Builder typeface(@Nullable Typeface typeface, @Nullable Typeface typeface2) {
            this.T = typeface;
            this.S = typeface2;
            return this;
        }

        public Builder widgetColor(@ColorInt int i2) {
            this.f8228t = i2;
            this.I0 = true;
            return this;
        }

        public Builder widgetColorAttr(@AttrRes int i2) {
            return widgetColor(DialogUtils.resolveColor(this.f8190a, i2));
        }

        public Builder widgetColorRes(@ColorRes int i2) {
            return widgetColor(DialogUtils.getColor(this.f8190a, i2));
        }

        public Builder content(@StringRes int i2, boolean z2) {
            CharSequence text = this.f8190a.getText(i2);
            if (z2) {
                text = Html.fromHtml(text.toString().replace("\n", "<br/>"));
            }
            return content(text);
        }

        public Builder inputRange(@IntRange(from = 0, to = 2147483647L) int i2, @IntRange(from = -1, to = 2147483647L) int i3, @ColorInt int i4) {
            if (i2 < 0) {
                throw new IllegalArgumentException("Min length for input dialogs cannot be less than 0.");
            }
            this.f8227s0 = i2;
            this.f8229t0 = i3;
            if (i4 == 0) {
                this.f8231u0 = DialogUtils.getColor(this.f8190a, R.color.md_edittext_error);
            } else {
                this.f8231u0 = i4;
            }
            if (this.f8227s0 > 0) {
                this.f8221p0 = false;
            }
            return this;
        }

        public Builder itemsIds(@ArrayRes int i2) {
            return itemsIds(this.f8190a.getResources().getIntArray(i2));
        }

        public Builder linkColor(@NonNull ColorStateList colorStateList) {
            this.f8238y = colorStateList;
            return this;
        }

        public Builder negativeColor(@NonNull ColorStateList colorStateList) {
            this.f8234w = colorStateList;
            this.H0 = true;
            return this;
        }

        public Builder negativeText(@NonNull CharSequence charSequence) {
            this.f8218o = charSequence;
            return this;
        }

        public Builder neutralColor(@NonNull ColorStateList colorStateList) {
            this.f8236x = colorStateList;
            this.G0 = true;
            return this;
        }

        public Builder neutralText(@NonNull CharSequence charSequence) {
            this.f8216n = charSequence;
            return this;
        }

        public Builder positiveColor(@NonNull ColorStateList colorStateList) {
            this.f8232v = colorStateList;
            this.F0 = true;
            return this;
        }

        public Builder positiveText(@NonNull CharSequence charSequence) {
            this.f8214m = charSequence;
            return this;
        }

        public Builder title(@NonNull CharSequence charSequence) {
            this.f8192b = charSequence;
            return this;
        }

        public Builder customView(@NonNull View view, boolean z2) {
            if (this.f8210k == null) {
                if (this.f8212l == null) {
                    if (this.f8219o0 == null) {
                        if (this.f8211k0 <= -2 && !this.f8207i0) {
                            if (view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
                                ((ViewGroup) view.getParent()).removeView(view);
                            }
                            this.f8226s = view;
                            this.f8199e0 = z2;
                            return this;
                        }
                        throw new IllegalStateException("You cannot use customView() with a progress dialog");
                    }
                    throw new IllegalStateException("You cannot use customView() with an input dialog");
                }
                throw new IllegalStateException("You cannot use customView() when you have items set.");
            }
            throw new IllegalStateException("You cannot use customView() when you have content set.");
        }

        public Builder typeface(@Nullable String str, @Nullable String str2) {
            if (str != null && !str.trim().isEmpty()) {
                Typeface typeface = TypefaceHelper.get(this.f8190a, str);
                this.T = typeface;
                if (typeface == null) {
                    throw new IllegalArgumentException("No font asset found for \"" + str + "\"");
                }
            }
            if (str2 != null && !str2.trim().isEmpty()) {
                Typeface typeface2 = TypefaceHelper.get(this.f8190a, str2);
                this.S = typeface2;
                if (typeface2 == null) {
                    throw new IllegalArgumentException("No font asset found for \"" + str2 + "\"");
                }
            }
            return this;
        }

        public Builder btnSelector(@DrawableRes int i2, @NonNull DialogAction dialogAction) {
            int i3 = AnonymousClass4.f8188a[dialogAction.ordinal()];
            if (i3 == 1) {
                this.N0 = i2;
            } else if (i3 != 2) {
                this.M0 = i2;
            } else {
                this.O0 = i2;
            }
            return this;
        }

        public Builder content(@NonNull CharSequence charSequence) {
            if (this.f8226s == null) {
                this.f8210k = charSequence;
                return this;
            }
            throw new IllegalStateException("You cannot set content() when you're using a custom view.");
        }

        public Builder input(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2, @NonNull InputCallback inputCallback) {
            return input(charSequence, charSequence2, true, inputCallback);
        }

        public Builder content(@StringRes int i2, Object... objArr) {
            return content(Html.fromHtml(String.format(this.f8190a.getString(i2), objArr).replace("\n", "<br/>")));
        }

        public Builder input(@StringRes int i2, @StringRes int i3, boolean z2, @NonNull InputCallback inputCallback) {
            return input(i2 == 0 ? null : this.f8190a.getText(i2), i3 != 0 ? this.f8190a.getText(i3) : null, z2, inputCallback);
        }

        public Builder items(@ArrayRes int i2) {
            items(this.f8190a.getResources().getTextArray(i2));
            return this;
        }

        public Builder items(@NonNull CharSequence... charSequenceArr) {
            if (this.f8226s == null) {
                ArrayList arrayList = new ArrayList();
                this.f8212l = arrayList;
                Collections.addAll(arrayList, charSequenceArr);
                return this;
            }
            throw new IllegalStateException("You cannot set items() when you're using a custom view.");
        }

        public Builder progress(boolean z2, int i2, boolean z3) {
            this.f8209j0 = z3;
            return progress(z2, i2);
        }

        public Builder input(@StringRes int i2, @StringRes int i3, @NonNull InputCallback inputCallback) {
            return input(i2, i3, true, inputCallback);
        }
    }

    @Deprecated
    public static abstract class ButtonCallback {
        protected final Object clone() {
            return super.clone();
        }

        public final boolean equals(Object obj) {
            return super.equals(obj);
        }

        protected final void finalize() throws Throwable {
            super.finalize();
        }

        public final int hashCode() {
            return super.hashCode();
        }

        @Deprecated
        public void onAny(MaterialDialog materialDialog) {
        }

        @Deprecated
        public void onNegative(MaterialDialog materialDialog) {
        }

        @Deprecated
        public void onNeutral(MaterialDialog materialDialog) {
        }

        @Deprecated
        public void onPositive(MaterialDialog materialDialog) {
        }

        public final String toString() {
            return super.toString();
        }
    }

    private static class DialogException extends WindowManager.BadTokenException {
        DialogException(String str) {
            super(str);
        }
    }

    public interface InputCallback {
        void onInput(@NonNull MaterialDialog materialDialog, CharSequence charSequence);
    }

    public interface ListCallback {
        void onSelection(MaterialDialog materialDialog, View view, int i2, CharSequence charSequence);
    }

    public interface ListCallbackMultiChoice {
        boolean onSelection(MaterialDialog materialDialog, Integer[] numArr, CharSequence[] charSequenceArr);
    }

    public interface ListCallbackSingleChoice {
        boolean onSelection(MaterialDialog materialDialog, View view, int i2, CharSequence charSequence);
    }

    public interface ListLongCallback {
        boolean onLongSelection(MaterialDialog materialDialog, View view, int i2, CharSequence charSequence);
    }

    enum ListType {
        REGULAR,
        SINGLE,
        MULTI;

        public static int getLayoutForType(ListType listType) {
            int i2 = AnonymousClass4.f8189b[listType.ordinal()];
            if (i2 == 1) {
                return R.layout.md_listitem;
            }
            if (i2 == 2) {
                return R.layout.md_listitem_singlechoice;
            }
            if (i2 == 3) {
                return R.layout.md_listitem_multichoice;
            }
            throw new IllegalArgumentException("Not a valid list type");
        }
    }

    public interface SingleButtonCallback {
        void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction);
    }

    protected MaterialDialog(Builder builder) {
        super(builder.f8190a, DialogInit.b(builder));
        this.handler = new Handler();
        this.f8165b = builder;
        this.f8163a = (MDRootLayout) LayoutInflater.from(builder.f8190a).inflate(DialogInit.a(builder), (ViewGroup) null);
        DialogInit.c(this);
    }

    private boolean sendMultiChoiceCallback() {
        if (this.f8165b.H == null) {
            return false;
        }
        Collections.sort(this.f8182s);
        ArrayList arrayList = new ArrayList();
        for (Integer num : this.f8182s) {
            if (num.intValue() >= 0 && num.intValue() <= this.f8165b.f8212l.size() - 1) {
                arrayList.add(this.f8165b.f8212l.get(num.intValue()));
            }
        }
        ListCallbackMultiChoice listCallbackMultiChoice = this.f8165b.H;
        List list = this.f8182s;
        return listCallbackMultiChoice.onSelection(this, (Integer[]) list.toArray(new Integer[list.size()]), (CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]));
    }

    private boolean sendSingleChoiceCallback(View view) {
        CharSequence charSequence;
        Builder builder = this.f8165b;
        if (builder.G == null) {
            return false;
        }
        int i2 = builder.O;
        if (i2 < 0 || i2 >= builder.f8212l.size()) {
            charSequence = null;
        } else {
            Builder builder2 = this.f8165b;
            charSequence = (CharSequence) builder2.f8212l.get(builder2.O);
        }
        Builder builder3 = this.f8165b;
        return builder3.G.onSelection(this, view, builder3.O, charSequence);
    }

    final void c() {
        RecyclerView recyclerView = this.f8170g;
        if (recyclerView == null) {
            return;
        }
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.afollestad.materialdialogs.MaterialDialog.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                final int iIntValue;
                MaterialDialog.this.f8170g.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                MaterialDialog materialDialog = MaterialDialog.this;
                ListType listType = materialDialog.f8181r;
                ListType listType2 = ListType.SINGLE;
                if (listType == listType2 || listType == ListType.MULTI) {
                    if (listType == listType2) {
                        iIntValue = materialDialog.f8165b.O;
                        if (iIntValue < 0) {
                            return;
                        }
                    } else {
                        List list = materialDialog.f8182s;
                        if (list == null || list.size() == 0) {
                            return;
                        }
                        Collections.sort(MaterialDialog.this.f8182s);
                        iIntValue = ((Integer) MaterialDialog.this.f8182s.get(0)).intValue();
                    }
                    MaterialDialog.this.f8170g.post(new Runnable() { // from class: com.afollestad.materialdialogs.MaterialDialog.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MaterialDialog.this.f8170g.requestFocus();
                            MaterialDialog.this.f8165b.Y.scrollToPosition(iIntValue);
                        }
                    });
                }
            }
        });
    }

    public void clearSelectedIndices() {
        clearSelectedIndices(true);
    }

    Drawable d(DialogAction dialogAction, boolean z2) {
        if (z2) {
            Builder builder = this.f8165b;
            if (builder.L0 != 0) {
                return ResourcesCompat.getDrawable(builder.f8190a.getResources(), this.f8165b.L0, null);
            }
            Drawable drawableResolveDrawable = DialogUtils.resolveDrawable(builder.f8190a, R.attr.md_btn_stacked_selector);
            return drawableResolveDrawable != null ? drawableResolveDrawable : DialogUtils.resolveDrawable(getContext(), R.attr.md_btn_stacked_selector);
        }
        int i2 = AnonymousClass4.f8188a[dialogAction.ordinal()];
        if (i2 == 1) {
            Builder builder2 = this.f8165b;
            if (builder2.N0 != 0) {
                return ResourcesCompat.getDrawable(builder2.f8190a.getResources(), this.f8165b.N0, null);
            }
            Drawable drawableResolveDrawable2 = DialogUtils.resolveDrawable(builder2.f8190a, R.attr.md_btn_neutral_selector);
            if (drawableResolveDrawable2 != null) {
                return drawableResolveDrawable2;
            }
            Drawable drawableResolveDrawable3 = DialogUtils.resolveDrawable(getContext(), R.attr.md_btn_neutral_selector);
            RippleHelper.applyColor(drawableResolveDrawable3, this.f8165b.f8204h);
            return drawableResolveDrawable3;
        }
        if (i2 != 2) {
            Builder builder3 = this.f8165b;
            if (builder3.M0 != 0) {
                return ResourcesCompat.getDrawable(builder3.f8190a.getResources(), this.f8165b.M0, null);
            }
            Drawable drawableResolveDrawable4 = DialogUtils.resolveDrawable(builder3.f8190a, R.attr.md_btn_positive_selector);
            if (drawableResolveDrawable4 != null) {
                return drawableResolveDrawable4;
            }
            Drawable drawableResolveDrawable5 = DialogUtils.resolveDrawable(getContext(), R.attr.md_btn_positive_selector);
            RippleHelper.applyColor(drawableResolveDrawable5, this.f8165b.f8204h);
            return drawableResolveDrawable5;
        }
        Builder builder4 = this.f8165b;
        if (builder4.O0 != 0) {
            return ResourcesCompat.getDrawable(builder4.f8190a.getResources(), this.f8165b.O0, null);
        }
        Drawable drawableResolveDrawable6 = DialogUtils.resolveDrawable(builder4.f8190a, R.attr.md_btn_negative_selector);
        if (drawableResolveDrawable6 != null) {
            return drawableResolveDrawable6;
        }
        Drawable drawableResolveDrawable7 = DialogUtils.resolveDrawable(getContext(), R.attr.md_btn_negative_selector);
        RippleHelper.applyColor(drawableResolveDrawable7, this.f8165b.f8204h);
        return drawableResolveDrawable7;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (this.f8169f != null) {
            DialogUtils.hideKeyboard(this, this.f8165b);
        }
        super.dismiss();
    }

    final Drawable e() {
        Builder builder = this.f8165b;
        if (builder.K0 != 0) {
            return ResourcesCompat.getDrawable(builder.f8190a.getResources(), this.f8165b.K0, null);
        }
        Drawable drawableResolveDrawable = DialogUtils.resolveDrawable(builder.f8190a, R.attr.md_list_selector);
        return drawableResolveDrawable != null ? drawableResolveDrawable : DialogUtils.resolveDrawable(getContext(), R.attr.md_list_selector);
    }

    void f(int i2, boolean z2) {
        Builder builder;
        int i3;
        TextView textView = this.f8176m;
        if (textView != null) {
            if (this.f8165b.f8229t0 > 0) {
                textView.setText(String.format(Locale.getDefault(), "%d/%d", Integer.valueOf(i2), Integer.valueOf(this.f8165b.f8229t0)));
                this.f8176m.setVisibility(0);
            } else {
                textView.setVisibility(8);
            }
            boolean z3 = (z2 && i2 == 0) || ((i3 = (builder = this.f8165b).f8229t0) > 0 && i2 > i3) || i2 < builder.f8227s0;
            Builder builder2 = this.f8165b;
            int i4 = z3 ? builder2.f8231u0 : builder2.f8208j;
            Builder builder3 = this.f8165b;
            int i5 = z3 ? builder3.f8231u0 : builder3.f8228t;
            if (this.f8165b.f8229t0 > 0) {
                this.f8176m.setTextColor(i4);
            }
            MDTintHelper.setTint(this.f8169f, i5);
            getActionButton(DialogAction.POSITIVE).setEnabled(!z3);
        }
    }

    @Override // com.afollestad.materialdialogs.DialogBase, android.app.Dialog
    public /* bridge */ /* synthetic */ View findViewById(int i2) {
        return super.findViewById(i2);
    }

    final void g() {
        if (this.f8170g == null) {
            return;
        }
        ArrayList arrayList = this.f8165b.f8212l;
        if ((arrayList == null || arrayList.size() == 0) && this.f8165b.X == null) {
            return;
        }
        Builder builder = this.f8165b;
        if (builder.Y == null) {
            builder.Y = new LinearLayoutManager(getContext());
        }
        if (this.f8170g.getLayoutManager() == null) {
            this.f8170g.setLayoutManager(this.f8165b.Y);
        }
        this.f8170g.setAdapter(this.f8165b.X);
        if (this.f8181r != null) {
            ((DefaultRvAdapter) this.f8165b.X).c(this);
        }
    }

    public final MDButton getActionButton(@NonNull DialogAction dialogAction) {
        int i2 = AnonymousClass4.f8188a[dialogAction.ordinal()];
        return i2 != 1 ? i2 != 2 ? this.f8178o : this.f8180q : this.f8179p;
    }

    public final Builder getBuilder() {
        return this.f8165b;
    }

    @Nullable
    public final TextView getContentView() {
        return this.f8168e;
    }

    public final int getCurrentProgress() {
        ProgressBar progressBar = this.f8173j;
        if (progressBar == null) {
            return -1;
        }
        return progressBar.getProgress();
    }

    @Nullable
    public final View getCustomView() {
        return this.f8165b.f8226s;
    }

    public ImageView getIconView() {
        return this.f8166c;
    }

    @Nullable
    public final EditText getInputEditText() {
        return this.f8169f;
    }

    @Nullable
    public final ArrayList<CharSequence> getItems() {
        return this.f8165b.f8212l;
    }

    public final int getMaxProgress() {
        ProgressBar progressBar = this.f8173j;
        if (progressBar == null) {
            return -1;
        }
        return progressBar.getMax();
    }

    public ProgressBar getProgressBar() {
        return this.f8173j;
    }

    public RecyclerView getRecyclerView() {
        return this.f8170g;
    }

    public int getSelectedIndex() {
        Builder builder = this.f8165b;
        if (builder.G != null) {
            return builder.O;
        }
        return -1;
    }

    @Nullable
    public Integer[] getSelectedIndices() {
        if (this.f8165b.H == null) {
            return null;
        }
        List list = this.f8182s;
        return (Integer[]) list.toArray(new Integer[list.size()]);
    }

    @Nullable
    public Object getTag() {
        return this.f8165b.P0;
    }

    public final TextView getTitleView() {
        return this.f8167d;
    }

    public final View getView() {
        return this.f8163a;
    }

    void h() {
        EditText editText = this.f8169f;
        if (editText == null) {
            return;
        }
        editText.addTextChangedListener(new TextWatcher() { // from class: com.afollestad.materialdialogs.MaterialDialog.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                int length = charSequence.toString().length();
                MaterialDialog materialDialog = MaterialDialog.this;
                if (!materialDialog.f8165b.f8221p0) {
                    z = length == 0;
                    materialDialog.getActionButton(DialogAction.POSITIVE).setEnabled(!z);
                }
                MaterialDialog.this.f(length, z);
                MaterialDialog materialDialog2 = MaterialDialog.this;
                Builder builder = materialDialog2.f8165b;
                if (builder.f8225r0) {
                    builder.f8219o0.onInput(materialDialog2, charSequence);
                }
            }
        });
    }

    public final boolean hasActionButtons() {
        return numberOfActionButtons() > 0;
    }

    public final void incrementProgress(int i2) {
        setProgress(getCurrentProgress() + i2);
    }

    public final boolean isCancelled() {
        return !isShowing();
    }

    public final boolean isIndeterminateProgress() {
        return this.f8165b.f8207i0;
    }

    public boolean isPromptCheckBoxChecked() {
        CheckBox checkBox = this.f8177n;
        return checkBox != null && checkBox.isChecked();
    }

    @UiThread
    public final void notifyItemChanged(@IntRange(from = 0, to = 2147483647L) int i2) {
        this.f8165b.X.notifyItemChanged(i2);
    }

    @UiThread
    public final void notifyItemInserted(@IntRange(from = 0, to = 2147483647L) int i2) {
        this.f8165b.X.notifyItemInserted(i2);
    }

    @UiThread
    public final void notifyItemsChanged() {
        this.f8165b.X.notifyDataSetChanged();
    }

    public final int numberOfActionButtons() {
        int i2 = this.f8178o.getVisibility() == 0 ? 1 : 0;
        if (this.f8179p.getVisibility() == 0) {
            i2++;
        }
        return this.f8180q.getVisibility() == 0 ? i2 + 1 : i2;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        EditText editText;
        DialogAction dialogAction = (DialogAction) view.getTag();
        int i2 = AnonymousClass4.f8188a[dialogAction.ordinal()];
        if (i2 == 1) {
            ButtonCallback buttonCallback = this.f8165b.f8240z;
            if (buttonCallback != null) {
                buttonCallback.onAny(this);
                this.f8165b.f8240z.onNeutral(this);
            }
            SingleButtonCallback singleButtonCallback = this.f8165b.C;
            if (singleButtonCallback != null) {
                singleButtonCallback.onClick(this, dialogAction);
            }
            if (this.f8165b.R) {
                dismiss();
            }
        } else if (i2 == 2) {
            ButtonCallback buttonCallback2 = this.f8165b.f8240z;
            if (buttonCallback2 != null) {
                buttonCallback2.onAny(this);
                this.f8165b.f8240z.onNegative(this);
            }
            SingleButtonCallback singleButtonCallback2 = this.f8165b.B;
            if (singleButtonCallback2 != null) {
                singleButtonCallback2.onClick(this, dialogAction);
            }
            if (this.f8165b.R) {
                cancel();
            }
        } else if (i2 == 3) {
            ButtonCallback buttonCallback3 = this.f8165b.f8240z;
            if (buttonCallback3 != null) {
                buttonCallback3.onAny(this);
                this.f8165b.f8240z.onPositive(this);
            }
            SingleButtonCallback singleButtonCallback3 = this.f8165b.A;
            if (singleButtonCallback3 != null) {
                singleButtonCallback3.onClick(this, dialogAction);
            }
            if (!this.f8165b.J) {
                sendSingleChoiceCallback(view);
            }
            if (!this.f8165b.I) {
                sendMultiChoiceCallback();
            }
            Builder builder = this.f8165b;
            InputCallback inputCallback = builder.f8219o0;
            if (inputCallback != null && (editText = this.f8169f) != null && !builder.f8225r0) {
                inputCallback.onInput(this, editText.getText());
            }
            if (this.f8165b.R) {
                dismiss();
            }
        }
        SingleButtonCallback singleButtonCallback4 = this.f8165b.D;
        if (singleButtonCallback4 != null) {
            singleButtonCallback4.onClick(this, dialogAction);
        }
    }

    @Override // com.afollestad.materialdialogs.DefaultRvAdapter.InternalListCallback
    public boolean onItemSelected(MaterialDialog materialDialog, View view, int i2, CharSequence charSequence, boolean z2) {
        Builder builder;
        ListLongCallback listLongCallback;
        Builder builder2;
        ListCallback listCallback;
        boolean zSendSingleChoiceCallback = false;
        if (!view.isEnabled()) {
            return false;
        }
        ListType listType = this.f8181r;
        if (listType == null || listType == ListType.REGULAR) {
            if (this.f8165b.R) {
                dismiss();
            }
            if (!z2 && (listCallback = (builder2 = this.f8165b).E) != null) {
                listCallback.onSelection(this, view, i2, (CharSequence) builder2.f8212l.get(i2));
            }
            if (z2 && (listLongCallback = (builder = this.f8165b).F) != null) {
                return listLongCallback.onLongSelection(this, view, i2, (CharSequence) builder.f8212l.get(i2));
            }
        } else if (listType == ListType.MULTI) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.md_control);
            if (!checkBox.isEnabled()) {
                return false;
            }
            if (!this.f8182s.contains(Integer.valueOf(i2))) {
                this.f8182s.add(Integer.valueOf(i2));
                if (!this.f8165b.I || sendMultiChoiceCallback()) {
                    checkBox.setChecked(true);
                } else {
                    this.f8182s.remove(Integer.valueOf(i2));
                }
            } else {
                this.f8182s.remove(Integer.valueOf(i2));
                if (!this.f8165b.I || sendMultiChoiceCallback()) {
                    checkBox.setChecked(false);
                } else {
                    this.f8182s.add(Integer.valueOf(i2));
                }
            }
        } else if (listType == ListType.SINGLE) {
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.md_control);
            if (!radioButton.isEnabled()) {
                return false;
            }
            Builder builder3 = this.f8165b;
            int i3 = builder3.O;
            if (builder3.R && builder3.f8214m == null) {
                dismiss();
                this.f8165b.O = i2;
                sendSingleChoiceCallback(view);
            } else if (builder3.J) {
                builder3.O = i2;
                zSendSingleChoiceCallback = sendSingleChoiceCallback(view);
                this.f8165b.O = i3;
            } else {
                zSendSingleChoiceCallback = true;
            }
            if (zSendSingleChoiceCallback) {
                this.f8165b.O = i2;
                radioButton.setChecked(true);
                this.f8165b.X.notifyItemChanged(i3);
                this.f8165b.X.notifyItemChanged(i2);
            }
        }
        return true;
    }

    @Override // com.afollestad.materialdialogs.DialogBase, android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        if (this.f8169f != null) {
            DialogUtils.showKeyboard(this, this.f8165b);
            if (this.f8169f.getText().length() > 0) {
                EditText editText = this.f8169f;
                editText.setSelection(editText.getText().length());
            }
        }
        super.onShow(dialogInterface);
    }

    public void selectAllIndices() {
        selectAllIndices(true);
    }

    @UiThread
    public final void setActionButton(@NonNull DialogAction dialogAction, CharSequence charSequence) {
        int i2 = AnonymousClass4.f8188a[dialogAction.ordinal()];
        if (i2 == 1) {
            this.f8165b.f8216n = charSequence;
            this.f8179p.setText(charSequence);
            this.f8179p.setVisibility(charSequence == null ? 8 : 0);
        } else if (i2 != 2) {
            this.f8165b.f8214m = charSequence;
            this.f8178o.setText(charSequence);
            this.f8178o.setVisibility(charSequence == null ? 8 : 0);
        } else {
            this.f8165b.f8218o = charSequence;
            this.f8180q.setText(charSequence);
            this.f8180q.setVisibility(charSequence == null ? 8 : 0);
        }
    }

    @UiThread
    public final void setContent(CharSequence charSequence) {
        this.f8168e.setText(charSequence);
        this.f8168e.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
    }

    @Override // com.afollestad.materialdialogs.DialogBase, android.app.Dialog
    @Deprecated
    public /* bridge */ /* synthetic */ void setContentView(int i2) throws IllegalAccessError {
        super.setContentView(i2);
    }

    @UiThread
    public void setIcon(@DrawableRes int i2) {
        this.f8166c.setImageResource(i2);
        this.f8166c.setVisibility(i2 != 0 ? 0 : 8);
    }

    @UiThread
    public void setIconAttribute(@AttrRes int i2) {
        setIcon(DialogUtils.resolveDrawable(this.f8165b.f8190a, i2));
    }

    @UiThread
    public final void setItems(CharSequence... charSequenceArr) {
        Builder builder = this.f8165b;
        if (builder.X == null) {
            throw new IllegalStateException("This MaterialDialog instance does not yet have an adapter set to it. You cannot use setItems().");
        }
        if (charSequenceArr != null) {
            builder.f8212l = new ArrayList(charSequenceArr.length);
            Collections.addAll(this.f8165b.f8212l, charSequenceArr);
        } else {
            builder.f8212l = null;
        }
        if (!(this.f8165b.X instanceof DefaultRvAdapter)) {
            throw new IllegalStateException("When using a custom adapter, setItems() cannot be used. Set items through the adapter instead.");
        }
        notifyItemsChanged();
    }

    public final void setMaxProgress(int i2) {
        if (this.f8165b.f8211k0 <= -2) {
            throw new IllegalStateException("Cannot use setMaxProgress() on this dialog.");
        }
        this.f8173j.setMax(i2);
    }

    public final void setProgress(int i2) {
        if (this.f8165b.f8211k0 <= -2) {
            Log.w("MaterialDialog", "Calling setProgress(int) on an indeterminate progress dialog has no effect!");
        } else {
            this.f8173j.setProgress(i2);
            this.handler.post(new Runnable() { // from class: com.afollestad.materialdialogs.MaterialDialog.2
                @Override // java.lang.Runnable
                public void run() {
                    MaterialDialog materialDialog = MaterialDialog.this;
                    TextView textView = materialDialog.f8174k;
                    if (textView != null) {
                        textView.setText(materialDialog.f8165b.A0.format(materialDialog.getCurrentProgress() / MaterialDialog.this.getMaxProgress()));
                    }
                    MaterialDialog materialDialog2 = MaterialDialog.this;
                    TextView textView2 = materialDialog2.f8175l;
                    if (textView2 != null) {
                        textView2.setText(String.format(materialDialog2.f8165b.z0, Integer.valueOf(materialDialog2.getCurrentProgress()), Integer.valueOf(MaterialDialog.this.getMaxProgress())));
                    }
                }
            });
        }
    }

    public final void setProgressNumberFormat(String str) {
        this.f8165b.z0 = str;
        setProgress(getCurrentProgress());
    }

    public final void setProgressPercentFormat(NumberFormat numberFormat) {
        this.f8165b.A0 = numberFormat;
        setProgress(getCurrentProgress());
    }

    public void setPromptCheckBoxChecked(boolean z2) {
        CheckBox checkBox = this.f8177n;
        if (checkBox != null) {
            checkBox.setChecked(z2);
        }
    }

    @UiThread
    public void setSelectedIndex(int i2) {
        Builder builder = this.f8165b;
        builder.O = i2;
        RecyclerView.Adapter adapter = builder.X;
        if (adapter == null || !(adapter instanceof DefaultRvAdapter)) {
            throw new IllegalStateException("You can only use setSelectedIndex() with the default adapter implementation.");
        }
        adapter.notifyDataSetChanged();
    }

    @UiThread
    public void setSelectedIndices(@NonNull Integer[] numArr) {
        this.f8182s = new ArrayList(Arrays.asList(numArr));
        RecyclerView.Adapter adapter = this.f8165b.X;
        if (adapter == null || !(adapter instanceof DefaultRvAdapter)) {
            throw new IllegalStateException("You can only use setSelectedIndices() with the default adapter implementation.");
        }
        adapter.notifyDataSetChanged();
    }

    @Override // android.app.Dialog
    @UiThread
    public final void setTitle(CharSequence charSequence) {
        this.f8167d.setText(charSequence);
    }

    public final void setTypeface(TextView textView, Typeface typeface) {
        if (typeface == null) {
            return;
        }
        textView.setPaintFlags(textView.getPaintFlags() | 128);
        textView.setTypeface(typeface);
    }

    @Override // android.app.Dialog
    @UiThread
    public void show() {
        try {
            super.show();
        } catch (WindowManager.BadTokenException unused) {
            throw new DialogException("Bad window token, you cannot show a dialog before an Activity is created or after it's hidden.");
        }
    }

    public void clearSelectedIndices(boolean z2) {
        ListType listType = this.f8181r;
        if (listType == null || listType != ListType.MULTI) {
            throw new IllegalStateException("You can only use clearSelectedIndices() with multi choice list dialogs.");
        }
        RecyclerView.Adapter adapter = this.f8165b.X;
        if (adapter == null || !(adapter instanceof DefaultRvAdapter)) {
            throw new IllegalStateException("You can only use clearSelectedIndices() with the default adapter implementation.");
        }
        List list = this.f8182s;
        if (list != null) {
            list.clear();
        }
        this.f8165b.X.notifyDataSetChanged();
        if (!z2 || this.f8165b.H == null) {
            return;
        }
        sendMultiChoiceCallback();
    }

    public void selectAllIndices(boolean z2) {
        ListType listType = this.f8181r;
        if (listType == null || listType != ListType.MULTI) {
            throw new IllegalStateException("You can only use selectAllIndices() with multi choice list dialogs.");
        }
        RecyclerView.Adapter adapter = this.f8165b.X;
        if (adapter == null || !(adapter instanceof DefaultRvAdapter)) {
            throw new IllegalStateException("You can only use selectAllIndices() with the default adapter implementation.");
        }
        if (this.f8182s == null) {
            this.f8182s = new ArrayList();
        }
        for (int i2 = 0; i2 < this.f8165b.X.getItemCount(); i2++) {
            if (!this.f8182s.contains(Integer.valueOf(i2))) {
                this.f8182s.add(Integer.valueOf(i2));
            }
        }
        this.f8165b.X.notifyDataSetChanged();
        if (!z2 || this.f8165b.H == null) {
            return;
        }
        sendMultiChoiceCallback();
    }

    @Override // com.afollestad.materialdialogs.DialogBase, android.app.Dialog
    @Deprecated
    public /* bridge */ /* synthetic */ void setContentView(@NonNull View view) throws IllegalAccessError {
        super.setContentView(view);
    }

    @Override // android.app.Dialog
    @UiThread
    public final void setTitle(@StringRes int i2) {
        setTitle(this.f8165b.f8190a.getString(i2));
    }

    @UiThread
    public final void setContent(@StringRes int i2) {
        setContent(this.f8165b.f8190a.getString(i2));
    }

    @Override // com.afollestad.materialdialogs.DialogBase, android.app.Dialog
    @Deprecated
    public /* bridge */ /* synthetic */ void setContentView(@NonNull View view, ViewGroup.LayoutParams layoutParams) throws IllegalAccessError {
        super.setContentView(view, layoutParams);
    }

    @UiThread
    public void setIcon(Drawable drawable) {
        this.f8166c.setImageDrawable(drawable);
        this.f8166c.setVisibility(drawable != null ? 0 : 8);
    }

    @UiThread
    public final void setTitle(@StringRes int i2, @Nullable Object... objArr) {
        setTitle(this.f8165b.f8190a.getString(i2, objArr));
    }

    @UiThread
    public final void setContent(@StringRes int i2, @Nullable Object... objArr) {
        setContent(this.f8165b.f8190a.getString(i2, objArr));
    }

    public final void setActionButton(DialogAction dialogAction, @StringRes int i2) {
        setActionButton(dialogAction, getContext().getText(i2));
    }
}
