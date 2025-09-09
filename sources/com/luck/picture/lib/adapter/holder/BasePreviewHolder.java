package com.luck.picture.lib.adapter.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.utils.BitmapUtils;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.MediaUtils;

/* loaded from: classes4.dex */
public abstract class BasePreviewHolder extends RecyclerView.ViewHolder {
    public static final int ADAPTER_TYPE_AUDIO = 3;
    public static final int ADAPTER_TYPE_IMAGE = 1;
    public static final int ADAPTER_TYPE_VIDEO = 2;

    /* renamed from: a, reason: collision with root package name */
    protected final int f18974a;

    /* renamed from: b, reason: collision with root package name */
    protected final int f18975b;

    /* renamed from: c, reason: collision with root package name */
    protected final int f18976c;
    public PhotoView coverImageView;

    /* renamed from: d, reason: collision with root package name */
    protected LocalMedia f18977d;

    /* renamed from: e, reason: collision with root package name */
    protected final SelectorConfig f18978e;

    /* renamed from: f, reason: collision with root package name */
    protected OnPreviewEventListener f18979f;

    public interface OnPreviewEventListener {
        void onBackPressed();

        void onLongPressDownload(LocalMedia localMedia);

        void onPreviewVideoTitle(String str);
    }

    public BasePreviewHolder(@NonNull View view) {
        super(view);
        this.f18978e = SelectorProviders.getInstance().getSelectorConfig();
        this.f18974a = DensityUtil.getRealScreenWidth(view.getContext());
        this.f18975b = DensityUtil.getScreenHeight(view.getContext());
        this.f18976c = DensityUtil.getRealScreenHeight(view.getContext());
        this.coverImageView = (PhotoView) view.findViewById(R.id.preview_image);
        a(view);
    }

    public static BasePreviewHolder generate(ViewGroup viewGroup, int i2, int i3) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(i3, viewGroup, false);
        return i2 == 2 ? new PreviewVideoHolder(viewInflate) : i2 == 3 ? new PreviewAudioHolder(viewInflate) : new PreviewImageHolder(viewInflate);
    }

    protected abstract void a(View view);

    protected int[] b(LocalMedia localMedia) {
        return (!localMedia.isCut() || localMedia.getCropImageWidth() <= 0 || localMedia.getCropImageHeight() <= 0) ? new int[]{localMedia.getWidth(), localMedia.getHeight()} : new int[]{localMedia.getCropImageWidth(), localMedia.getCropImageHeight()};
    }

    public void bindData(LocalMedia localMedia, int i2) {
        this.f18977d = localMedia;
        int[] iArrB = b(localMedia);
        int[] maxImageSize = BitmapUtils.getMaxImageSize(iArrB[0], iArrB[1]);
        c(localMedia, maxImageSize[0], maxImageSize[1]);
        g(localMedia);
        f(localMedia);
        d();
        e(localMedia);
    }

    protected abstract void c(LocalMedia localMedia, int i2, int i3);

    protected abstract void d();

    protected abstract void e(LocalMedia localMedia);

    protected void f(LocalMedia localMedia) {
        if (MediaUtils.isLongImage(localMedia.getWidth(), localMedia.getHeight())) {
            this.coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            this.coverImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    protected void g(LocalMedia localMedia) {
        if (this.f18978e.isPreviewZoomEffect || this.f18974a >= this.f18975b || localMedia.getWidth() <= 0 || localMedia.getHeight() <= 0) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.coverImageView.getLayoutParams();
        layoutParams.width = this.f18974a;
        layoutParams.height = this.f18976c;
        layoutParams.gravity = 17;
    }

    public boolean isPlaying() {
        return false;
    }

    public void onViewAttachedToWindow() {
    }

    public void onViewDetachedFromWindow() {
    }

    public void release() {
    }

    public void resumePausePlay() {
    }

    public void setOnPreviewEventListener(OnPreviewEventListener onPreviewEventListener) {
        this.f18979f = onPreviewEventListener;
    }
}
