package com.aliyun.iot.link.ui.component;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LinkBottomDialog {
    LinearLayout mContainer;
    AlertDialog mDialog;
    Button mNegativeBtn;
    Button mPositiveBtn;
    TextView mTitleTv;

    public static class Builder {
        public static final int INPUT = 2;
        public static final int NORMAL = 1;
        Context mContext;
        OnClickListener mNegativeListener;
        String mPositiveBtnText;
        OnClickListener mPositiveListener;
        String mTitle = "title";
        String mNegativeBtnText = "CANCEL";
        boolean mCanceledOnTouchOutside = true;
        boolean mCancelable = true;
        int mPositiveBtnTextColor = -1;
        int mNegativeBtnTextColor = -1;
        List<ItemEntry> items = new ArrayList();

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder addItem(String str, OnItemClickListener onItemClickListener) {
            return addItem(str, Color.parseColor("#0079FF"), onItemClickListener);
        }

        public LinkBottomDialog create() {
            return new LinkBottomDialog(this);
        }

        public Builder setCancelable(boolean z2) {
            this.mCancelable = z2;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean z2) {
            this.mCanceledOnTouchOutside = z2;
            return this;
        }

        public Builder setNegativeButton(String str, OnClickListener onClickListener) {
            this.mNegativeBtnText = str;
            this.mNegativeListener = onClickListener;
            return this;
        }

        public Builder setNegativeButtonColor(@ColorInt int i2) {
            this.mNegativeBtnTextColor = i2;
            return this;
        }

        public Builder setPositiveButton(String str, OnClickListener onClickListener) {
            this.mPositiveBtnText = str;
            this.mPositiveListener = onClickListener;
            return this;
        }

        public Builder setPositiveButtonColor(@ColorInt int i2) {
            this.mPositiveBtnTextColor = i2;
            return this;
        }

        public Builder setTitle(String str) {
            this.mTitle = str;
            return this;
        }

        public Builder addItem(String str, @ColorInt int i2, OnItemClickListener onItemClickListener) {
            ItemEntry itemEntry = new ItemEntry();
            itemEntry.text = str;
            itemEntry.color = i2;
            itemEntry.onItemClickListener = onItemClickListener;
            this.items.add(itemEntry);
            return this;
        }
    }

    static class ItemEntry {
        int color;
        OnItemClickListener onItemClickListener;
        String text;

        ItemEntry() {
        }
    }

    public interface OnClickListener {
        void onClick(LinkBottomDialog linkBottomDialog);
    }

    public interface OnItemClickListener {
        void onItemClick(LinkBottomDialog linkBottomDialog, String str, int i2);
    }

    LinkBottomDialog(final Builder builder) {
        this.mDialog = new AlertDialog.Builder(builder.mContext).create();
        View viewInflate = LayoutInflater.from(builder.mContext).inflate(R.layout.bottom_dialog, (ViewGroup) null);
        this.mTitleTv = (TextView) viewInflate.findViewById(R.id.title);
        this.mPositiveBtn = (Button) viewInflate.findViewById(R.id.positive_btn);
        this.mNegativeBtn = (Button) viewInflate.findViewById(R.id.negative_btn);
        this.mContainer = (LinearLayout) viewInflate.findViewById(R.id.container);
        this.mDialog.setView(viewInflate);
        this.mTitleTv.setText(builder.mTitle);
        int i2 = builder.mNegativeBtnTextColor;
        if (-1 != i2) {
            this.mNegativeBtn.setTextColor(i2);
        }
        int i3 = builder.mPositiveBtnTextColor;
        if (-1 != i3) {
            this.mPositiveBtn.setTextColor(i3);
        }
        List<ItemEntry> list = builder.items;
        for (final int i4 = 0; i4 < list.size(); i4++) {
            final ItemEntry itemEntry = list.get(i4);
            TextView textView = (TextView) LayoutInflater.from(builder.mContext).inflate(R.layout.dialog_item, (ViewGroup) null);
            textView.setText(itemEntry.text);
            textView.setTextColor(itemEntry.color);
            this.mContainer.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.LinkBottomDialog.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ItemEntry itemEntry2 = itemEntry;
                    OnItemClickListener onItemClickListener = itemEntry2.onItemClickListener;
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(LinkBottomDialog.this, itemEntry2.text, i4);
                    }
                }
            });
        }
        if (TextUtils.isEmpty(builder.mPositiveBtnText)) {
            this.mPositiveBtn.setVisibility(8);
            viewInflate.findViewById(R.id.divider).setVisibility(8);
        } else {
            this.mPositiveBtn.setVisibility(0);
            this.mPositiveBtn.setText(builder.mPositiveBtnText);
        }
        this.mNegativeBtn.setText(builder.mNegativeBtnText);
        this.mPositiveBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.LinkBottomDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                OnClickListener onClickListener = builder.mPositiveListener;
                if (onClickListener != null) {
                    onClickListener.onClick(LinkBottomDialog.this);
                }
            }
        });
        this.mNegativeBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.link.ui.component.LinkBottomDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                OnClickListener onClickListener = builder.mNegativeListener;
                if (onClickListener != null) {
                    onClickListener.onClick(LinkBottomDialog.this);
                }
            }
        });
        this.mDialog.setCancelable(builder.mCancelable);
        this.mDialog.setCanceledOnTouchOutside(builder.mCanceledOnTouchOutside);
        this.mDialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
        this.mDialog.getWindow().setGravity(80);
        this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    public void dismiss() {
        this.mDialog.dismiss();
    }

    public void show() {
        this.mDialog.show();
    }
}
