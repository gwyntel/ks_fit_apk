package com.afollestad.materialdialogs;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.util.DialogUtils;
import java.util.ArrayList;

/* loaded from: classes2.dex */
class DefaultRvAdapter extends RecyclerView.Adapter<DefaultVH> {
    private InternalListCallback callback;
    private final MaterialDialog dialog;
    private final GravityEnum itemGravity;

    @LayoutRes
    private final int layout;

    /* renamed from: com.afollestad.materialdialogs.DefaultRvAdapter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8159a;

        static {
            int[] iArr = new int[MaterialDialog.ListType.values().length];
            f8159a = iArr;
            try {
                iArr[MaterialDialog.ListType.SINGLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8159a[MaterialDialog.ListType.MULTI.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    static class DefaultVH extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        /* renamed from: a, reason: collision with root package name */
        final CompoundButton f8160a;

        /* renamed from: b, reason: collision with root package name */
        final TextView f8161b;

        /* renamed from: c, reason: collision with root package name */
        final DefaultRvAdapter f8162c;

        DefaultVH(View view, DefaultRvAdapter defaultRvAdapter) {
            super(view);
            this.f8160a = (CompoundButton) view.findViewById(R.id.md_control);
            this.f8161b = (TextView) view.findViewById(R.id.md_title);
            this.f8162c = defaultRvAdapter;
            view.setOnClickListener(this);
            if (defaultRvAdapter.dialog.f8165b.F != null) {
                view.setOnLongClickListener(this);
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.f8162c.callback == null || getAdapterPosition() == -1) {
                return;
            }
            this.f8162c.callback.onItemSelected(this.f8162c.dialog, view, getAdapterPosition(), (this.f8162c.dialog.f8165b.f8212l == null || getAdapterPosition() >= this.f8162c.dialog.f8165b.f8212l.size()) ? null : (CharSequence) this.f8162c.dialog.f8165b.f8212l.get(getAdapterPosition()), false);
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            if (this.f8162c.callback == null || getAdapterPosition() == -1) {
                return false;
            }
            return this.f8162c.callback.onItemSelected(this.f8162c.dialog, view, getAdapterPosition(), (this.f8162c.dialog.f8165b.f8212l == null || getAdapterPosition() >= this.f8162c.dialog.f8165b.f8212l.size()) ? null : (CharSequence) this.f8162c.dialog.f8165b.f8212l.get(getAdapterPosition()), true);
        }
    }

    interface InternalListCallback {
        boolean onItemSelected(MaterialDialog materialDialog, View view, int i2, CharSequence charSequence, boolean z2);
    }

    DefaultRvAdapter(MaterialDialog materialDialog, int i2) {
        this.dialog = materialDialog;
        this.layout = i2;
        this.itemGravity = materialDialog.f8165b.f8200f;
    }

    @TargetApi(17)
    private boolean isRTL() {
        return this.dialog.getBuilder().getContext().getResources().getConfiguration().getLayoutDirection() == 1;
    }

    @TargetApi(17)
    private void setupGravity(ViewGroup viewGroup) {
        ((LinearLayout) viewGroup).setGravity(this.itemGravity.getGravityInt() | 16);
        if (viewGroup.getChildCount() == 2) {
            if (this.itemGravity == GravityEnum.END && !isRTL() && (viewGroup.getChildAt(0) instanceof CompoundButton)) {
                View view = (CompoundButton) viewGroup.getChildAt(0);
                viewGroup.removeView(view);
                TextView textView = (TextView) viewGroup.getChildAt(0);
                viewGroup.removeView(textView);
                textView.setPadding(textView.getPaddingRight(), textView.getPaddingTop(), textView.getPaddingLeft(), textView.getPaddingBottom());
                viewGroup.addView(textView);
                viewGroup.addView(view);
                return;
            }
            if (this.itemGravity == GravityEnum.START && isRTL() && (viewGroup.getChildAt(1) instanceof CompoundButton)) {
                View view2 = (CompoundButton) viewGroup.getChildAt(1);
                viewGroup.removeView(view2);
                TextView textView2 = (TextView) viewGroup.getChildAt(0);
                viewGroup.removeView(textView2);
                textView2.setPadding(textView2.getPaddingRight(), textView2.getPaddingTop(), textView2.getPaddingRight(), textView2.getPaddingBottom());
                viewGroup.addView(view2);
                viewGroup.addView(textView2);
            }
        }
    }

    void c(InternalListCallback internalListCallback) {
        this.callback = internalListCallback;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList arrayList = this.dialog.f8165b.f8212l;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(DefaultVH defaultVH, int i2) {
        View view = defaultVH.itemView;
        boolean zIsIn = DialogUtils.isIn(Integer.valueOf(i2), this.dialog.f8165b.Q);
        int iAdjustAlpha = zIsIn ? DialogUtils.adjustAlpha(this.dialog.f8165b.f8205h0, 0.4f) : this.dialog.f8165b.f8205h0;
        defaultVH.itemView.setEnabled(!zIsIn);
        int i3 = AnonymousClass1.f8159a[this.dialog.f8181r.ordinal()];
        if (i3 == 1) {
            RadioButton radioButton = (RadioButton) defaultVH.f8160a;
            MaterialDialog.Builder builder = this.dialog.f8165b;
            boolean z2 = builder.O == i2;
            ColorStateList colorStateList = builder.f8230u;
            if (colorStateList != null) {
                MDTintHelper.setTint(radioButton, colorStateList);
            } else {
                MDTintHelper.setTint(radioButton, builder.f8228t);
            }
            radioButton.setChecked(z2);
            radioButton.setEnabled(!zIsIn);
        } else if (i3 == 2) {
            CheckBox checkBox = (CheckBox) defaultVH.f8160a;
            boolean zContains = this.dialog.f8182s.contains(Integer.valueOf(i2));
            MaterialDialog.Builder builder2 = this.dialog.f8165b;
            ColorStateList colorStateList2 = builder2.f8230u;
            if (colorStateList2 != null) {
                MDTintHelper.setTint(checkBox, colorStateList2);
            } else {
                MDTintHelper.setTint(checkBox, builder2.f8228t);
            }
            checkBox.setChecked(zContains);
            checkBox.setEnabled(!zIsIn);
        }
        defaultVH.f8161b.setText((CharSequence) this.dialog.f8165b.f8212l.get(i2));
        defaultVH.f8161b.setTextColor(iAdjustAlpha);
        MaterialDialog materialDialog = this.dialog;
        materialDialog.setTypeface(defaultVH.f8161b, materialDialog.f8165b.S);
        ViewGroup viewGroup = (ViewGroup) view;
        setupGravity(viewGroup);
        int[] iArr = this.dialog.f8165b.f8233v0;
        if (iArr != null) {
            if (i2 < iArr.length) {
                view.setId(iArr[i2]);
            } else {
                view.setId(-1);
            }
        }
        if (viewGroup.getChildCount() == 2) {
            if (viewGroup.getChildAt(0) instanceof CompoundButton) {
                viewGroup.getChildAt(0).setBackground(null);
            } else if (viewGroup.getChildAt(1) instanceof CompoundButton) {
                viewGroup.getChildAt(1).setBackground(null);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public DefaultVH onCreateViewHolder(ViewGroup viewGroup, int i2) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(this.layout, viewGroup, false);
        DialogUtils.setBackgroundCompat(viewInflate, this.dialog.e());
        return new DefaultVH(viewInflate, this);
    }
}
