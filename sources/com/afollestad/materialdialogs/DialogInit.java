package com.afollestad.materialdialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDAdapter;
import com.afollestad.materialdialogs.internal.MDButton;
import com.afollestad.materialdialogs.internal.MDRootLayout;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.util.DialogUtils;
import java.util.ArrayList;
import java.util.Arrays;
import me.zhanghai.android.materialprogressbar.HorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.IndeterminateCircularProgressDrawable;
import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;

/* loaded from: classes2.dex */
class DialogInit {
    static int a(MaterialDialog.Builder builder) {
        return builder.f8226s != null ? R.layout.md_dialog_custom : (builder.f8212l == null && builder.X == null) ? builder.f8211k0 > -2 ? R.layout.md_dialog_progress : builder.f8207i0 ? builder.B0 ? R.layout.md_dialog_progress_indeterminate_horizontal : R.layout.md_dialog_progress_indeterminate : builder.f8219o0 != null ? builder.f8235w0 != null ? R.layout.md_dialog_input_check : R.layout.md_dialog_input : builder.f8235w0 != null ? R.layout.md_dialog_basic_check : R.layout.md_dialog_basic : builder.f8235w0 != null ? R.layout.md_dialog_list_check : R.layout.md_dialog_list;
    }

    static int b(MaterialDialog.Builder builder) {
        Context context = builder.f8190a;
        int i2 = R.attr.md_dark_theme;
        Theme theme = builder.K;
        Theme theme2 = Theme.DARK;
        boolean zResolveBoolean = DialogUtils.resolveBoolean(context, i2, theme == theme2);
        if (!zResolveBoolean) {
            theme2 = Theme.LIGHT;
        }
        builder.K = theme2;
        return zResolveBoolean ? R.style.MD_Dark : R.style.MD_Light;
    }

    static void c(MaterialDialog materialDialog) {
        MaterialDialog.Builder builder = materialDialog.f8165b;
        materialDialog.setCancelable(builder.L);
        materialDialog.setCanceledOnTouchOutside(builder.M);
        if (builder.f8203g0 == 0) {
            builder.f8203g0 = DialogUtils.resolveColor(builder.f8190a, R.attr.md_background_color, DialogUtils.resolveColor(materialDialog.getContext(), R.attr.colorBackgroundFloating));
        }
        if (builder.f8203g0 != 0) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(builder.f8190a.getResources().getDimension(R.dimen.md_bg_corner_radius));
            gradientDrawable.setColor(builder.f8203g0);
            materialDialog.getWindow().setBackgroundDrawable(gradientDrawable);
        }
        if (!builder.F0) {
            builder.f8232v = DialogUtils.resolveActionTextColorStateList(builder.f8190a, R.attr.md_positive_color, builder.f8232v);
        }
        if (!builder.G0) {
            builder.f8236x = DialogUtils.resolveActionTextColorStateList(builder.f8190a, R.attr.md_neutral_color, builder.f8236x);
        }
        if (!builder.H0) {
            builder.f8234w = DialogUtils.resolveActionTextColorStateList(builder.f8190a, R.attr.md_negative_color, builder.f8234w);
        }
        if (!builder.I0) {
            builder.f8228t = DialogUtils.resolveColor(builder.f8190a, R.attr.md_widget_color, builder.f8228t);
        }
        if (!builder.C0) {
            builder.f8206i = DialogUtils.resolveColor(builder.f8190a, R.attr.md_title_color, DialogUtils.resolveColor(materialDialog.getContext(), android.R.attr.textColorPrimary));
        }
        if (!builder.D0) {
            builder.f8208j = DialogUtils.resolveColor(builder.f8190a, R.attr.md_content_color, DialogUtils.resolveColor(materialDialog.getContext(), android.R.attr.textColorSecondary));
        }
        if (!builder.E0) {
            builder.f8205h0 = DialogUtils.resolveColor(builder.f8190a, R.attr.md_item_color, builder.f8208j);
        }
        materialDialog.f8167d = (TextView) materialDialog.f8163a.findViewById(R.id.md_title);
        materialDialog.f8166c = (ImageView) materialDialog.f8163a.findViewById(R.id.md_icon);
        materialDialog.f8171h = materialDialog.f8163a.findViewById(R.id.md_titleFrame);
        materialDialog.f8168e = (TextView) materialDialog.f8163a.findViewById(R.id.md_content);
        materialDialog.f8170g = (RecyclerView) materialDialog.f8163a.findViewById(R.id.md_contentRecyclerView);
        materialDialog.f8177n = (CheckBox) materialDialog.f8163a.findViewById(R.id.md_promptCheckbox);
        materialDialog.f8178o = (MDButton) materialDialog.f8163a.findViewById(R.id.md_buttonDefaultPositive);
        materialDialog.f8179p = (MDButton) materialDialog.f8163a.findViewById(R.id.md_buttonDefaultNeutral);
        materialDialog.f8180q = (MDButton) materialDialog.f8163a.findViewById(R.id.md_buttonDefaultNegative);
        if (builder.f8219o0 != null && builder.f8214m == null) {
            builder.f8214m = builder.f8190a.getText(android.R.string.ok);
        }
        materialDialog.f8178o.setVisibility(builder.f8214m != null ? 0 : 8);
        materialDialog.f8179p.setVisibility(builder.f8216n != null ? 0 : 8);
        materialDialog.f8180q.setVisibility(builder.f8218o != null ? 0 : 8);
        materialDialog.f8178o.setFocusable(true);
        materialDialog.f8179p.setFocusable(true);
        materialDialog.f8180q.setFocusable(true);
        if (builder.f8220p) {
            materialDialog.f8178o.requestFocus();
        }
        if (builder.f8222q) {
            materialDialog.f8179p.requestFocus();
        }
        if (builder.f8224r) {
            materialDialog.f8180q.requestFocus();
        }
        if (builder.U != null) {
            materialDialog.f8166c.setVisibility(0);
            materialDialog.f8166c.setImageDrawable(builder.U);
        } else {
            Drawable drawableResolveDrawable = DialogUtils.resolveDrawable(builder.f8190a, R.attr.md_icon);
            if (drawableResolveDrawable != null) {
                materialDialog.f8166c.setVisibility(0);
                materialDialog.f8166c.setImageDrawable(drawableResolveDrawable);
            } else {
                materialDialog.f8166c.setVisibility(8);
            }
        }
        int dimensionPixelSize = builder.W;
        if (dimensionPixelSize == -1) {
            dimensionPixelSize = DialogUtils.resolveDimension(builder.f8190a, R.attr.md_icon_max_size);
        }
        if (builder.V || DialogUtils.resolveBoolean(builder.f8190a, R.attr.md_icon_limit_icon_to_default_size)) {
            dimensionPixelSize = builder.f8190a.getResources().getDimensionPixelSize(R.dimen.md_icon_max_size);
        }
        if (dimensionPixelSize > -1) {
            materialDialog.f8166c.setAdjustViewBounds(true);
            materialDialog.f8166c.setMaxHeight(dimensionPixelSize);
            materialDialog.f8166c.setMaxWidth(dimensionPixelSize);
            materialDialog.f8166c.requestLayout();
        }
        if (!builder.J0) {
            builder.f8201f0 = DialogUtils.resolveColor(builder.f8190a, R.attr.md_divider_color, DialogUtils.resolveColor(materialDialog.getContext(), R.attr.md_divider));
        }
        materialDialog.f8163a.setDividerColor(builder.f8201f0);
        TextView textView = materialDialog.f8167d;
        if (textView != null) {
            materialDialog.setTypeface(textView, builder.T);
            materialDialog.f8167d.setTextColor(builder.f8206i);
            materialDialog.f8167d.setGravity(builder.f8194c.getGravityInt());
            materialDialog.f8167d.setTextAlignment(builder.f8194c.getTextAlignment());
            CharSequence charSequence = builder.f8192b;
            if (charSequence == null) {
                materialDialog.f8171h.setVisibility(8);
            } else {
                materialDialog.f8167d.setText(charSequence);
                materialDialog.f8171h.setVisibility(0);
            }
        }
        TextView textView2 = materialDialog.f8168e;
        if (textView2 != null) {
            textView2.setMovementMethod(new LinkMovementMethod());
            materialDialog.setTypeface(materialDialog.f8168e, builder.S);
            materialDialog.f8168e.setLineSpacing(0.0f, builder.N);
            ColorStateList colorStateList = builder.f8238y;
            if (colorStateList == null) {
                materialDialog.f8168e.setLinkTextColor(DialogUtils.resolveColor(materialDialog.getContext(), android.R.attr.textColorPrimary));
            } else {
                materialDialog.f8168e.setLinkTextColor(colorStateList);
            }
            materialDialog.f8168e.setTextColor(builder.f8208j);
            materialDialog.f8168e.setGravity(builder.f8196d.getGravityInt());
            materialDialog.f8168e.setTextAlignment(builder.f8196d.getTextAlignment());
            CharSequence charSequence2 = builder.f8210k;
            if (charSequence2 != null) {
                materialDialog.f8168e.setText(charSequence2);
                materialDialog.f8168e.setVisibility(0);
            } else {
                materialDialog.f8168e.setVisibility(8);
            }
        }
        CheckBox checkBox = materialDialog.f8177n;
        if (checkBox != null) {
            checkBox.setText(builder.f8235w0);
            materialDialog.f8177n.setChecked(builder.f8237x0);
            materialDialog.f8177n.setOnCheckedChangeListener(builder.f8239y0);
            materialDialog.setTypeface(materialDialog.f8177n, builder.S);
            materialDialog.f8177n.setTextColor(builder.f8208j);
            MDTintHelper.setTint(materialDialog.f8177n, builder.f8228t);
        }
        materialDialog.f8163a.setButtonGravity(builder.f8202g);
        materialDialog.f8163a.setButtonStackedGravity(builder.f8198e);
        materialDialog.f8163a.setStackingBehavior(builder.f8197d0);
        boolean zResolveBoolean = DialogUtils.resolveBoolean(builder.f8190a, android.R.attr.textAllCaps, true);
        if (zResolveBoolean) {
            zResolveBoolean = DialogUtils.resolveBoolean(builder.f8190a, R.attr.textAllCaps, true);
        }
        MDButton mDButton = materialDialog.f8178o;
        materialDialog.setTypeface(mDButton, builder.T);
        mDButton.setAllCapsCompat(zResolveBoolean);
        mDButton.setText(builder.f8214m);
        mDButton.setTextColor(builder.f8232v);
        MDButton mDButton2 = materialDialog.f8178o;
        DialogAction dialogAction = DialogAction.POSITIVE;
        mDButton2.setStackedSelector(materialDialog.d(dialogAction, true));
        materialDialog.f8178o.setDefaultSelector(materialDialog.d(dialogAction, false));
        materialDialog.f8178o.setTag(dialogAction);
        materialDialog.f8178o.setOnClickListener(materialDialog);
        MDButton mDButton3 = materialDialog.f8180q;
        materialDialog.setTypeface(mDButton3, builder.T);
        mDButton3.setAllCapsCompat(zResolveBoolean);
        mDButton3.setText(builder.f8218o);
        mDButton3.setTextColor(builder.f8234w);
        MDButton mDButton4 = materialDialog.f8180q;
        DialogAction dialogAction2 = DialogAction.NEGATIVE;
        mDButton4.setStackedSelector(materialDialog.d(dialogAction2, true));
        materialDialog.f8180q.setDefaultSelector(materialDialog.d(dialogAction2, false));
        materialDialog.f8180q.setTag(dialogAction2);
        materialDialog.f8180q.setOnClickListener(materialDialog);
        MDButton mDButton5 = materialDialog.f8179p;
        materialDialog.setTypeface(mDButton5, builder.T);
        mDButton5.setAllCapsCompat(zResolveBoolean);
        mDButton5.setText(builder.f8216n);
        mDButton5.setTextColor(builder.f8236x);
        MDButton mDButton6 = materialDialog.f8179p;
        DialogAction dialogAction3 = DialogAction.NEUTRAL;
        mDButton6.setStackedSelector(materialDialog.d(dialogAction3, true));
        materialDialog.f8179p.setDefaultSelector(materialDialog.d(dialogAction3, false));
        materialDialog.f8179p.setTag(dialogAction3);
        materialDialog.f8179p.setOnClickListener(materialDialog);
        if (builder.H != null) {
            materialDialog.f8182s = new ArrayList();
        }
        if (materialDialog.f8170g != null) {
            Object obj = builder.X;
            if (obj == null) {
                if (builder.G != null) {
                    materialDialog.f8181r = MaterialDialog.ListType.SINGLE;
                } else if (builder.H != null) {
                    materialDialog.f8181r = MaterialDialog.ListType.MULTI;
                    if (builder.P != null) {
                        materialDialog.f8182s = new ArrayList(Arrays.asList(builder.P));
                        builder.P = null;
                    }
                } else {
                    materialDialog.f8181r = MaterialDialog.ListType.REGULAR;
                }
                builder.X = new DefaultRvAdapter(materialDialog, MaterialDialog.ListType.getLayoutForType(materialDialog.f8181r));
            } else if (obj instanceof MDAdapter) {
                ((MDAdapter) obj).setDialog(materialDialog);
            }
        }
        setupProgressDialog(materialDialog);
        setupInputDialog(materialDialog);
        if (builder.f8226s != null) {
            ((MDRootLayout) materialDialog.f8163a.findViewById(R.id.md_root)).noTitleNoPadding();
            FrameLayout frameLayout = (FrameLayout) materialDialog.f8163a.findViewById(R.id.md_customViewFrame);
            materialDialog.f8172i = frameLayout;
            View view = builder.f8226s;
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            if (builder.f8199e0) {
                Resources resources = materialDialog.getContext().getResources();
                int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.md_dialog_frame_margin);
                ScrollView scrollView = new ScrollView(materialDialog.getContext());
                int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.md_content_padding_top);
                int dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.md_content_padding_bottom);
                scrollView.setClipToPadding(false);
                if (view instanceof EditText) {
                    scrollView.setPadding(dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize2, dimensionPixelSize4);
                } else {
                    scrollView.setPadding(0, dimensionPixelSize3, 0, dimensionPixelSize4);
                    view.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, 0);
                }
                scrollView.addView(view, new FrameLayout.LayoutParams(-1, -2));
                view = scrollView;
            }
            frameLayout.addView(view, new ViewGroup.LayoutParams(-1, -2));
        }
        DialogInterface.OnShowListener onShowListener = builder.f8195c0;
        if (onShowListener != null) {
            materialDialog.setOnShowListener(onShowListener);
        }
        DialogInterface.OnCancelListener onCancelListener = builder.f8191a0;
        if (onCancelListener != null) {
            materialDialog.setOnCancelListener(onCancelListener);
        }
        DialogInterface.OnDismissListener onDismissListener = builder.Z;
        if (onDismissListener != null) {
            materialDialog.setOnDismissListener(onDismissListener);
        }
        DialogInterface.OnKeyListener onKeyListener = builder.f8193b0;
        if (onKeyListener != null) {
            materialDialog.setOnKeyListener(onKeyListener);
        }
        materialDialog.a();
        materialDialog.g();
        materialDialog.b(materialDialog.f8163a);
        materialDialog.c();
        Display defaultDisplay = materialDialog.getWindow().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i2 = point.x;
        int i3 = point.y;
        int dimensionPixelSize5 = builder.f8190a.getResources().getDimensionPixelSize(R.dimen.md_dialog_vertical_margin);
        int dimensionPixelSize6 = builder.f8190a.getResources().getDimensionPixelSize(R.dimen.md_dialog_horizontal_margin);
        materialDialog.f8163a.setMaxHeight(i3 - (dimensionPixelSize5 * 2));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(materialDialog.getWindow().getAttributes());
        layoutParams.width = Math.min(builder.f8190a.getResources().getDimensionPixelSize(R.dimen.md_dialog_max_width), i2 - (dimensionPixelSize6 * 2));
        materialDialog.getWindow().setAttributes(layoutParams);
    }

    private static void fixCanvasScalingWhenHardwareAccelerated(ProgressBar progressBar) {
    }

    private static void setupInputDialog(MaterialDialog materialDialog) {
        MaterialDialog.Builder builder = materialDialog.f8165b;
        EditText editText = (EditText) materialDialog.f8163a.findViewById(android.R.id.input);
        materialDialog.f8169f = editText;
        if (editText == null) {
            return;
        }
        materialDialog.setTypeface(editText, builder.S);
        CharSequence charSequence = builder.f8215m0;
        if (charSequence != null) {
            materialDialog.f8169f.setText(charSequence);
        }
        materialDialog.h();
        materialDialog.f8169f.setHint(builder.f8217n0);
        materialDialog.f8169f.setSingleLine();
        materialDialog.f8169f.setTextColor(builder.f8208j);
        materialDialog.f8169f.setHintTextColor(DialogUtils.adjustAlpha(builder.f8208j, 0.3f));
        MDTintHelper.setTint(materialDialog.f8169f, materialDialog.f8165b.f8228t);
        int i2 = builder.f8223q0;
        if (i2 != -1) {
            materialDialog.f8169f.setInputType(i2);
            int i3 = builder.f8223q0;
            if (i3 != 144 && (i3 & 128) == 128) {
                materialDialog.f8169f.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
        TextView textView = (TextView) materialDialog.f8163a.findViewById(R.id.md_minMax);
        materialDialog.f8176m = textView;
        if (builder.f8227s0 > 0 || builder.f8229t0 > -1) {
            materialDialog.f(materialDialog.f8169f.getText().toString().length(), !builder.f8221p0);
        } else {
            textView.setVisibility(8);
            materialDialog.f8176m = null;
        }
    }

    private static void setupProgressDialog(MaterialDialog materialDialog) {
        MaterialDialog.Builder builder = materialDialog.f8165b;
        if (builder.f8207i0 || builder.f8211k0 > -2) {
            ProgressBar progressBar = (ProgressBar) materialDialog.f8163a.findViewById(android.R.id.progress);
            materialDialog.f8173j = progressBar;
            if (progressBar == null) {
                return;
            }
            if (!builder.f8207i0) {
                HorizontalProgressDrawable horizontalProgressDrawable = new HorizontalProgressDrawable(builder.getContext());
                horizontalProgressDrawable.setTint(builder.f8228t);
                materialDialog.f8173j.setProgressDrawable(horizontalProgressDrawable);
                materialDialog.f8173j.setIndeterminateDrawable(horizontalProgressDrawable);
            } else if (builder.B0) {
                IndeterminateHorizontalProgressDrawable indeterminateHorizontalProgressDrawable = new IndeterminateHorizontalProgressDrawable(builder.getContext());
                indeterminateHorizontalProgressDrawable.setTint(builder.f8228t);
                materialDialog.f8173j.setProgressDrawable(indeterminateHorizontalProgressDrawable);
                materialDialog.f8173j.setIndeterminateDrawable(indeterminateHorizontalProgressDrawable);
            } else {
                IndeterminateCircularProgressDrawable indeterminateCircularProgressDrawable = new IndeterminateCircularProgressDrawable(builder.getContext());
                indeterminateCircularProgressDrawable.setTint(builder.f8228t);
                materialDialog.f8173j.setProgressDrawable(indeterminateCircularProgressDrawable);
                materialDialog.f8173j.setIndeterminateDrawable(indeterminateCircularProgressDrawable);
            }
            boolean z2 = builder.f8207i0;
            if (!z2 || builder.B0) {
                materialDialog.f8173j.setIndeterminate(z2 && builder.B0);
                materialDialog.f8173j.setProgress(0);
                materialDialog.f8173j.setMax(builder.f8213l0);
                TextView textView = (TextView) materialDialog.f8163a.findViewById(R.id.md_label);
                materialDialog.f8174k = textView;
                if (textView != null) {
                    textView.setTextColor(builder.f8208j);
                    materialDialog.setTypeface(materialDialog.f8174k, builder.T);
                    materialDialog.f8174k.setText(builder.A0.format(0L));
                }
                TextView textView2 = (TextView) materialDialog.f8163a.findViewById(R.id.md_minMax);
                materialDialog.f8175l = textView2;
                if (textView2 != null) {
                    textView2.setTextColor(builder.f8208j);
                    materialDialog.setTypeface(materialDialog.f8175l, builder.S);
                    if (builder.f8209j0) {
                        materialDialog.f8175l.setVisibility(0);
                        materialDialog.f8175l.setText(String.format(builder.z0, 0, Integer.valueOf(builder.f8213l0)));
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) materialDialog.f8173j.getLayoutParams();
                        marginLayoutParams.leftMargin = 0;
                        marginLayoutParams.rightMargin = 0;
                    } else {
                        materialDialog.f8175l.setVisibility(8);
                    }
                } else {
                    builder.f8209j0 = false;
                }
            }
        }
        ProgressBar progressBar2 = materialDialog.f8173j;
        if (progressBar2 != null) {
            fixCanvasScalingWhenHardwareAccelerated(progressBar2);
        }
    }
}
