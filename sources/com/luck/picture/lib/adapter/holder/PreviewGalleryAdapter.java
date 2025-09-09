package com.luck.picture.lib.adapter.holder;

import android.graphics.ColorFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.StyleUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PreviewGalleryAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final boolean isBottomPreview;
    private OnItemClickListener listener;
    private final List<LocalMedia> mData;
    private OnItemLongClickListener mItemLongClickListener;
    private final SelectorConfig selectorConfig;

    public interface OnItemClickListener {
        void onItemClick(int i2, LocalMedia localMedia, View view);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i2, View view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /* renamed from: a, reason: collision with root package name */
        ImageView f19010a;

        /* renamed from: b, reason: collision with root package name */
        ImageView f19011b;

        /* renamed from: c, reason: collision with root package name */
        ImageView f19012c;

        /* renamed from: d, reason: collision with root package name */
        View f19013d;

        public ViewHolder(View view) {
            super(view);
            this.f19010a = (ImageView) view.findViewById(R.id.ivImage);
            this.f19011b = (ImageView) view.findViewById(R.id.ivPlay);
            this.f19012c = (ImageView) view.findViewById(R.id.ivEditor);
            this.f19013d = view.findViewById(R.id.viewBorder);
            SelectMainStyle selectMainStyle = PreviewGalleryAdapter.this.selectorConfig.selectorStyle.getSelectMainStyle();
            if (StyleUtils.checkStyleValidity(selectMainStyle.getAdapterImageEditorResources())) {
                this.f19012c.setImageResource(selectMainStyle.getAdapterImageEditorResources());
            }
            if (StyleUtils.checkStyleValidity(selectMainStyle.getAdapterPreviewGalleryFrameResource())) {
                this.f19013d.setBackgroundResource(selectMainStyle.getAdapterPreviewGalleryFrameResource());
            }
            int adapterPreviewGalleryItemSize = selectMainStyle.getAdapterPreviewGalleryItemSize();
            if (StyleUtils.checkSizeValidity(adapterPreviewGalleryItemSize)) {
                view.setLayoutParams(new RelativeLayout.LayoutParams(adapterPreviewGalleryItemSize, adapterPreviewGalleryItemSize));
            }
        }
    }

    public PreviewGalleryAdapter(SelectorConfig selectorConfig, boolean z2) {
        this.selectorConfig = selectorConfig;
        this.isBottomPreview = z2;
        this.mData = new ArrayList(selectorConfig.getSelectedResult());
        for (int i2 = 0; i2 < this.mData.size(); i2++) {
            LocalMedia localMedia = this.mData.get(i2);
            localMedia.setGalleryEnabledMask(false);
            localMedia.setChecked(false);
        }
    }

    private int getCurrentPosition(LocalMedia localMedia) {
        for (int i2 = 0; i2 < this.mData.size(); i2++) {
            LocalMedia localMedia2 = this.mData.get(i2);
            if (TextUtils.equals(localMedia2.getPath(), localMedia.getPath()) || localMedia2.getId() == localMedia.getId()) {
                return i2;
            }
        }
        return -1;
    }

    public void addGalleryData(LocalMedia localMedia) {
        int lastCheckPosition = getLastCheckPosition();
        if (lastCheckPosition != -1) {
            this.mData.get(lastCheckPosition).setChecked(false);
            notifyItemChanged(lastCheckPosition);
        }
        if (!this.isBottomPreview || !this.mData.contains(localMedia)) {
            localMedia.setChecked(true);
            this.mData.add(localMedia);
            notifyItemChanged(this.mData.size() - 1);
        } else {
            int currentPosition = getCurrentPosition(localMedia);
            LocalMedia localMedia2 = this.mData.get(currentPosition);
            localMedia2.setGalleryEnabledMask(false);
            localMedia2.setChecked(true);
            notifyItemChanged(currentPosition);
        }
    }

    public void clear() {
        this.mData.clear();
    }

    public List<LocalMedia> getData() {
        return this.mData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mData.size();
    }

    public int getLastCheckPosition() {
        for (int i2 = 0; i2 < this.mData.size(); i2++) {
            if (this.mData.get(i2).isChecked()) {
                return i2;
            }
        }
        return -1;
    }

    public void isSelectMedia(LocalMedia localMedia) {
        int lastCheckPosition = getLastCheckPosition();
        if (lastCheckPosition != -1) {
            this.mData.get(lastCheckPosition).setChecked(false);
            notifyItemChanged(lastCheckPosition);
        }
        int currentPosition = getCurrentPosition(localMedia);
        if (currentPosition != -1) {
            this.mData.get(currentPosition).setChecked(true);
            notifyItemChanged(currentPosition);
        }
    }

    public void removeGalleryData(LocalMedia localMedia) {
        int currentPosition = getCurrentPosition(localMedia);
        if (currentPosition != -1) {
            if (this.isBottomPreview) {
                this.mData.get(currentPosition).setGalleryEnabledMask(true);
                notifyItemChanged(currentPosition);
            } else {
                this.mData.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
        }
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public void setItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mItemLongClickListener = onItemLongClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i2) {
        final LocalMedia localMedia = this.mData.get(i2);
        ColorFilter colorFilter = StyleUtils.getColorFilter(viewHolder.itemView.getContext(), localMedia.isGalleryEnabledMask() ? R.color.ps_color_half_white : R.color.ps_color_transparent);
        if (localMedia.isChecked() && localMedia.isGalleryEnabledMask()) {
            viewHolder.f19013d.setVisibility(0);
        } else {
            viewHolder.f19013d.setVisibility(localMedia.isChecked() ? 0 : 8);
        }
        String path = localMedia.getPath();
        if (!localMedia.isEditorImage() || TextUtils.isEmpty(localMedia.getCutPath())) {
            viewHolder.f19012c.setVisibility(8);
        } else {
            path = localMedia.getCutPath();
            viewHolder.f19012c.setVisibility(0);
        }
        viewHolder.f19010a.setColorFilter(colorFilter);
        ImageEngine imageEngine = this.selectorConfig.imageEngine;
        if (imageEngine != null) {
            imageEngine.loadGridImage(viewHolder.itemView.getContext(), path, viewHolder.f19010a);
        }
        viewHolder.f19011b.setVisibility(PictureMimeType.isHasVideo(localMedia.getMimeType()) ? 0 : 8);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewGalleryAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PreviewGalleryAdapter.this.listener != null) {
                    PreviewGalleryAdapter.this.listener.onItemClick(viewHolder.getAbsoluteAdapterPosition(), localMedia, view);
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewGalleryAdapter.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (PreviewGalleryAdapter.this.mItemLongClickListener == null) {
                    return true;
                }
                PreviewGalleryAdapter.this.mItemLongClickListener.onItemLongClick(viewHolder, viewHolder.getAbsoluteAdapterPosition(), view);
                return true;
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        int layoutResource = InjectResourceSource.getLayoutResource(viewGroup.getContext(), 9, this.selectorConfig);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        if (layoutResource == 0) {
            layoutResource = R.layout.ps_preview_gallery_item;
        }
        return new ViewHolder(layoutInflaterFrom.inflate(layoutResource, viewGroup, false));
    }
}
