package com.luck.picture.lib.adapter.holder;

import android.view.View;
import androidx.annotation.NonNull;
import com.luck.picture.lib.adapter.holder.BasePreviewHolder;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.photoview.OnViewTapListener;

/* loaded from: classes4.dex */
public class PreviewImageHolder extends BasePreviewHolder {
    public PreviewImageHolder(@NonNull View view) {
        super(view);
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void a(View view) {
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void c(LocalMedia localMedia, int i2, int i3) {
        if (this.f18978e.imageEngine != null) {
            String availablePath = localMedia.getAvailablePath();
            if (i2 == -1 && i3 == -1) {
                this.f18978e.imageEngine.loadImage(this.itemView.getContext(), availablePath, this.coverImageView);
            } else {
                this.f18978e.imageEngine.loadImage(this.itemView.getContext(), this.coverImageView, availablePath, i2, i3);
            }
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void d() {
        this.coverImageView.setOnViewTapListener(new OnViewTapListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewImageHolder.1
            @Override // com.luck.picture.lib.photoview.OnViewTapListener
            public void onViewTap(View view, float f2, float f3) {
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = PreviewImageHolder.this.f18979f;
                if (onPreviewEventListener != null) {
                    onPreviewEventListener.onBackPressed();
                }
            }
        });
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    protected void e(final LocalMedia localMedia) {
        this.coverImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewImageHolder.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = PreviewImageHolder.this.f18979f;
                if (onPreviewEventListener == null) {
                    return false;
                }
                onPreviewEventListener.onLongPressDownload(localMedia);
                return false;
            }
        });
    }
}
