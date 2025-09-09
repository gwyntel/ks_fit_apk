package com.luck.picture.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.luck.picture.lib.adapter.PicturePreviewAdapter;
import com.luck.picture.lib.adapter.holder.BasePreviewHolder;
import com.luck.picture.lib.adapter.holder.PreviewGalleryAdapter;
import com.luck.picture.lib.adapter.holder.PreviewVideoHolder;
import com.luck.picture.lib.basic.IBridgeLoaderFactory;
import com.luck.picture.lib.basic.PictureCommonFragment;
import com.luck.picture.lib.basic.PictureMediaScannerConnection;
import com.luck.picture.lib.config.Crop;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.decoration.HorizontalItemDecoration;
import com.luck.picture.lib.decoration.WrapContentLinearLayoutManager;
import com.luck.picture.lib.dialog.PictureCommonDialog;
import com.luck.picture.lib.engine.ExtendLoaderEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.luck.picture.lib.loader.IBridgeMediaLoader;
import com.luck.picture.lib.loader.LocalMediaLoader;
import com.luck.picture.lib.loader.LocalMediaPageLoader;
import com.luck.picture.lib.magical.BuildRecycleItemViewParams;
import com.luck.picture.lib.magical.MagicalView;
import com.luck.picture.lib.magical.OnMagicalViewCallback;
import com.luck.picture.lib.magical.ViewParams;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.DownloadFileUtils;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.luck.picture.lib.utils.ToastUtils;
import com.luck.picture.lib.utils.ValueOf;
import com.luck.picture.lib.widget.BottomNavBar;
import com.luck.picture.lib.widget.CompleteSelectView;
import com.luck.picture.lib.widget.PreviewBottomNavBar;
import com.luck.picture.lib.widget.PreviewTitleBar;
import com.luck.picture.lib.widget.TitleBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class PictureSelectorPreviewFragment extends PictureCommonFragment {
    public static final String TAG = "PictureSelectorPreviewFragment";
    protected View A;
    protected CompleteSelectView B;
    protected RecyclerView E;
    protected PreviewGalleryAdapter F;

    /* renamed from: g, reason: collision with root package name */
    protected MagicalView f18887g;

    /* renamed from: h, reason: collision with root package name */
    protected ViewPager2 f18888h;

    /* renamed from: i, reason: collision with root package name */
    protected PicturePreviewAdapter f18889i;

    /* renamed from: j, reason: collision with root package name */
    protected PreviewBottomNavBar f18890j;

    /* renamed from: k, reason: collision with root package name */
    protected PreviewTitleBar f18891k;

    /* renamed from: m, reason: collision with root package name */
    protected int f18893m;

    /* renamed from: n, reason: collision with root package name */
    protected boolean f18894n;

    /* renamed from: o, reason: collision with root package name */
    protected boolean f18895o;

    /* renamed from: p, reason: collision with root package name */
    protected String f18896p;

    /* renamed from: q, reason: collision with root package name */
    protected boolean f18897q;

    /* renamed from: r, reason: collision with root package name */
    protected boolean f18898r;

    /* renamed from: s, reason: collision with root package name */
    protected boolean f18899s;

    /* renamed from: t, reason: collision with root package name */
    protected boolean f18900t;

    /* renamed from: u, reason: collision with root package name */
    protected int f18901u;

    /* renamed from: v, reason: collision with root package name */
    protected int f18902v;

    /* renamed from: w, reason: collision with root package name */
    protected int f18903w;

    /* renamed from: y, reason: collision with root package name */
    protected TextView f18905y;

    /* renamed from: z, reason: collision with root package name */
    protected TextView f18906z;

    /* renamed from: f, reason: collision with root package name */
    protected ArrayList f18886f = new ArrayList();

    /* renamed from: l, reason: collision with root package name */
    protected boolean f18892l = true;

    /* renamed from: x, reason: collision with root package name */
    protected long f18904x = -1;
    protected boolean C = true;
    protected boolean D = false;
    protected List G = new ArrayList();
    private boolean isPause = false;
    private final ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.22
        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrolled(int i2, float f2, int i3) {
            if (PictureSelectorPreviewFragment.this.f18886f.size() > i2) {
                PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                int i4 = pictureSelectorPreviewFragment.f18902v / 2;
                ArrayList arrayList = pictureSelectorPreviewFragment.f18886f;
                if (i3 >= i4) {
                    i2++;
                }
                LocalMedia localMedia = (LocalMedia) arrayList.get(i2);
                PictureSelectorPreviewFragment pictureSelectorPreviewFragment2 = PictureSelectorPreviewFragment.this;
                pictureSelectorPreviewFragment2.f18905y.setSelected(pictureSelectorPreviewFragment2.l0(localMedia));
                PictureSelectorPreviewFragment.this.notifyGallerySelectMedia(localMedia);
                PictureSelectorPreviewFragment.this.notifySelectNumberStyle(localMedia);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageSelected(int i2) {
            PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
            pictureSelectorPreviewFragment.f18893m = i2;
            pictureSelectorPreviewFragment.f18891k.setTitle((PictureSelectorPreviewFragment.this.f18893m + 1) + "/" + PictureSelectorPreviewFragment.this.f18901u);
            if (PictureSelectorPreviewFragment.this.f18886f.size() > i2) {
                LocalMedia localMedia = (LocalMedia) PictureSelectorPreviewFragment.this.f18886f.get(i2);
                PictureSelectorPreviewFragment.this.notifySelectNumberStyle(localMedia);
                if (PictureSelectorPreviewFragment.this.isHasMagicalEffect()) {
                    PictureSelectorPreviewFragment.this.changeMagicalViewParams(i2);
                }
                if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.isPreviewZoomEffect) {
                    PictureSelectorPreviewFragment pictureSelectorPreviewFragment2 = PictureSelectorPreviewFragment.this;
                    if (pictureSelectorPreviewFragment2.f18894n && ((PictureCommonFragment) pictureSelectorPreviewFragment2).f19027d.isAutoVideoPlay) {
                        PictureSelectorPreviewFragment.this.startAutoVideoPlay(i2);
                    } else {
                        PictureSelectorPreviewFragment.this.f18889i.setVideoPlayButtonUI(i2);
                    }
                } else if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.isAutoVideoPlay) {
                    PictureSelectorPreviewFragment.this.startAutoVideoPlay(i2);
                }
                PictureSelectorPreviewFragment.this.notifyGallerySelectMedia(localMedia);
                PictureSelectorPreviewFragment.this.f18890j.isDisplayEditor(PictureMimeType.isHasVideo(localMedia.getMimeType()) || PictureMimeType.isHasAudio(localMedia.getMimeType()));
                PictureSelectorPreviewFragment pictureSelectorPreviewFragment3 = PictureSelectorPreviewFragment.this;
                if (pictureSelectorPreviewFragment3.f18898r || pictureSelectorPreviewFragment3.f18894n || ((PictureCommonFragment) pictureSelectorPreviewFragment3).f19027d.isOnlySandboxDir || !((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.isPageStrategy) {
                    return;
                }
                if (PictureSelectorPreviewFragment.this.f18892l) {
                    if (i2 == r0.f18889i.getItemCount() - 11 || i2 == PictureSelectorPreviewFragment.this.f18889i.getItemCount() - 1) {
                        PictureSelectorPreviewFragment.this.loadMoreData();
                    }
                }
            }
        }
    };

    private class MyOnPreviewEventListener implements BasePreviewHolder.OnPreviewEventListener {
        private MyOnPreviewEventListener() {
        }

        @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder.OnPreviewEventListener
        public void onBackPressed() {
            if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.isPreviewFullScreenMode) {
                PictureSelectorPreviewFragment.this.previewFullScreenMode();
                return;
            }
            PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
            if (pictureSelectorPreviewFragment.f18898r) {
                if (((PictureCommonFragment) pictureSelectorPreviewFragment).f19027d.isPreviewZoomEffect) {
                    PictureSelectorPreviewFragment.this.f18887g.backToMin();
                    return;
                } else {
                    PictureSelectorPreviewFragment.this.handleExternalPreviewBack();
                    return;
                }
            }
            if (pictureSelectorPreviewFragment.f18894n || !((PictureCommonFragment) pictureSelectorPreviewFragment).f19027d.isPreviewZoomEffect) {
                PictureSelectorPreviewFragment.this.n();
            } else {
                PictureSelectorPreviewFragment.this.f18887g.backToMin();
            }
        }

        @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder.OnPreviewEventListener
        public void onLongPressDownload(LocalMedia localMedia) {
            if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.isHidePreviewDownload) {
                return;
            }
            PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
            if (pictureSelectorPreviewFragment.f18898r) {
                pictureSelectorPreviewFragment.onExternalLongPressDownload(localMedia);
            }
        }

        @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder.OnPreviewEventListener
        public void onPreviewVideoTitle(String str) {
            if (!TextUtils.isEmpty(str)) {
                PictureSelectorPreviewFragment.this.f18891k.setTitle(str);
                return;
            }
            PictureSelectorPreviewFragment.this.f18891k.setTitle((PictureSelectorPreviewFragment.this.f18893m + 1) + "/" + PictureSelectorPreviewFragment.this.f18901u);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeMagicalViewParams(final int i2) {
        LocalMedia localMedia = (LocalMedia) this.f18886f.get(i2);
        if (PictureMimeType.isHasVideo(localMedia.getMimeType())) {
            getVideoRealSizeFromMedia(localMedia, false, new OnCallbackListener<int[]>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.24
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(int[] iArr) {
                    PictureSelectorPreviewFragment.this.setMagicalViewParams(iArr[0], iArr[1], i2);
                }
            });
        } else {
            getImageRealSizeFromMedia(localMedia, false, new OnCallbackListener<int[]>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.25
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(int[] iArr) {
                    PictureSelectorPreviewFragment.this.setMagicalViewParams(iArr[0], iArr[1], i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeViewParams(int[] iArr) {
        int i2;
        int i3;
        ViewParams itemViewParams = BuildRecycleItemViewParams.getItemViewParams(this.f18897q ? this.f18893m + 1 : this.f18893m);
        if (itemViewParams == null || (i2 = iArr[0]) == 0 || (i3 = iArr[1]) == 0) {
            this.f18887g.setViewParams(0, 0, 0, 0, iArr[0], iArr[1]);
            this.f18887g.resetStartNormal(iArr[0], iArr[1], false);
        } else {
            this.f18887g.setViewParams(itemViewParams.left, itemViewParams.f19095top, itemViewParams.width, itemViewParams.height, i2, i3);
            this.f18887g.resetStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NotifyDataSetChanged"})
    public void deletePreview() {
        OnExternalPreviewEventListener onExternalPreviewEventListener;
        if (!this.f18899s || (onExternalPreviewEventListener = this.f19027d.onExternalPreviewEventListener) == null) {
            return;
        }
        onExternalPreviewEventListener.onPreviewDelete(this.f18888h.getCurrentItem());
        int currentItem = this.f18888h.getCurrentItem();
        this.f18886f.remove(currentItem);
        if (this.f18886f.size() == 0) {
            handleExternalPreviewBack();
            return;
        }
        this.f18891k.setTitle(getString(R.string.ps_preview_image_num, Integer.valueOf(this.f18893m + 1), Integer.valueOf(this.f18886f.size())));
        this.f18901u = this.f18886f.size();
        this.f18893m = currentItem;
        if (this.f18888h.getAdapter() != null) {
            this.f18888h.setAdapter(null);
            this.f18888h.setAdapter(this.f18889i);
        }
        this.f18888h.setCurrentItem(this.f18893m, false);
    }

    private void externalPreviewStyle() {
        this.f18891k.getImageDelete().setVisibility(this.f18899s ? 0 : 8);
        this.f18905y.setVisibility(8);
        this.f18890j.setVisibility(8);
        this.B.setVisibility(8);
    }

    private void getImageRealSizeFromMedia(final LocalMedia localMedia, boolean z2, final OnCallbackListener<int[]> onCallbackListener) {
        int cropImageWidth;
        int cropImageHeight;
        boolean z3 = true;
        if (MediaUtils.isLongImage(localMedia.getWidth(), localMedia.getHeight())) {
            cropImageWidth = this.f18902v;
            cropImageHeight = this.f18903w;
        } else {
            int width = localMedia.getWidth();
            int height = localMedia.getHeight();
            if (z2 && ((width <= 0 || height <= 0 || width > height) && this.f19027d.isSyncWidthAndHeight)) {
                this.f18888h.setAlpha(0.0f);
                MediaUtils.getImageSize(getContext(), localMedia.getAvailablePath(), new OnCallbackListener<MediaExtraInfo>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.26
                    @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                    public void onCall(MediaExtraInfo mediaExtraInfo) {
                        if (mediaExtraInfo.getWidth() > 0) {
                            localMedia.setWidth(mediaExtraInfo.getWidth());
                        }
                        if (mediaExtraInfo.getHeight() > 0) {
                            localMedia.setHeight(mediaExtraInfo.getHeight());
                        }
                        OnCallbackListener onCallbackListener2 = onCallbackListener;
                        if (onCallbackListener2 != null) {
                            onCallbackListener2.onCall(new int[]{localMedia.getWidth(), localMedia.getHeight()});
                        }
                    }
                });
                z3 = false;
            }
            cropImageWidth = width;
            cropImageHeight = height;
        }
        if (localMedia.isCut() && localMedia.getCropImageWidth() > 0 && localMedia.getCropImageHeight() > 0) {
            cropImageWidth = localMedia.getCropImageWidth();
            cropImageHeight = localMedia.getCropImageHeight();
        }
        if (z3) {
            onCallbackListener.onCall(new int[]{cropImageWidth, cropImageHeight});
        }
    }

    private void getVideoRealSizeFromMedia(final LocalMedia localMedia, boolean z2, final OnCallbackListener<int[]> onCallbackListener) {
        if (!z2 || ((localMedia.getWidth() > 0 && localMedia.getHeight() > 0 && localMedia.getWidth() <= localMedia.getHeight()) || !this.f19027d.isSyncWidthAndHeight)) {
            onCallbackListener.onCall(new int[]{localMedia.getWidth(), localMedia.getHeight()});
        } else {
            this.f18888h.setAlpha(0.0f);
            MediaUtils.getVideoSize(getContext(), localMedia.getAvailablePath(), new OnCallbackListener<MediaExtraInfo>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.27
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(MediaExtraInfo mediaExtraInfo) {
                    if (mediaExtraInfo.getWidth() > 0) {
                        localMedia.setWidth(mediaExtraInfo.getWidth());
                    }
                    if (mediaExtraInfo.getHeight() > 0) {
                        localMedia.setHeight(mediaExtraInfo.getHeight());
                    }
                    OnCallbackListener onCallbackListener2 = onCallbackListener;
                    if (onCallbackListener2 != null) {
                        onCallbackListener2.onCall(new int[]{localMedia.getWidth(), localMedia.getHeight()});
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleExternalPreviewBack() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (this.f19027d.isPreviewFullScreenMode) {
            hideFullScreenStatusBar();
        }
        o();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMoreData(List<LocalMedia> list, boolean z2) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        this.f18892l = z2;
        if (z2) {
            if (list.size() <= 0) {
                loadMoreData();
                return;
            }
            int size = this.f18886f.size();
            this.f18886f.addAll(list);
            this.f18889i.notifyItemRangeChanged(size, this.f18886f.size());
        }
    }

    private void hideFullScreenStatusBar() {
        for (int i2 = 0; i2 < this.G.size(); i2++) {
            ((View) this.G.get(i2)).setEnabled(true);
        }
        this.f18890j.getEditor().setEnabled(true);
    }

    private void iniMagicalView() {
        if (!isHasMagicalEffect()) {
            this.f18887g.setBackgroundAlpha(1.0f);
            return;
        }
        float f2 = this.f18895o ? 1.0f : 0.0f;
        this.f18887g.setBackgroundAlpha(f2);
        for (int i2 = 0; i2 < this.G.size(); i2++) {
            if (!(this.G.get(i2) instanceof TitleBar)) {
                ((View) this.G.get(i2)).setAlpha(f2);
            }
        }
    }

    private void initBottomNavBar() {
        this.f18890j.setBottomNavBarStyle();
        this.f18890j.setSelectedChange();
        this.f18890j.setOnBottomNavBarListener(new BottomNavBar.OnBottomNavBarListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.15
            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onCheckOriginalChange() {
                PictureSelectorPreviewFragment.this.sendSelectedOriginalChangeEvent();
            }

            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onEditImage() {
                if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.onEditMediaEventListener != null) {
                    PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                    ((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.onEditMediaEventListener.onStartMediaEdit(PictureSelectorPreviewFragment.this, (LocalMedia) pictureSelectorPreviewFragment.f18886f.get(pictureSelectorPreviewFragment.f18888h.getCurrentItem()), Crop.REQUEST_EDIT_CROP);
                }
            }

            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onFirstCheckOriginalSelectedChange() {
                int currentItem = PictureSelectorPreviewFragment.this.f18888h.getCurrentItem();
                if (PictureSelectorPreviewFragment.this.f18886f.size() > currentItem) {
                    PictureSelectorPreviewFragment.this.confirmSelect((LocalMedia) PictureSelectorPreviewFragment.this.f18886f.get(currentItem), false);
                }
            }
        });
    }

    private void initComplete() {
        final SelectMainStyle selectMainStyle = this.f19027d.selectorStyle.getSelectMainStyle();
        if (StyleUtils.checkStyleValidity(selectMainStyle.getPreviewSelectBackground())) {
            this.f18905y.setBackgroundResource(selectMainStyle.getPreviewSelectBackground());
        } else if (StyleUtils.checkStyleValidity(selectMainStyle.getSelectBackground())) {
            this.f18905y.setBackgroundResource(selectMainStyle.getSelectBackground());
        }
        if (StyleUtils.checkStyleValidity(selectMainStyle.getPreviewSelectTextResId())) {
            this.f18906z.setText(getString(selectMainStyle.getPreviewSelectTextResId()));
        } else if (StyleUtils.checkTextValidity(selectMainStyle.getPreviewSelectText())) {
            this.f18906z.setText(selectMainStyle.getPreviewSelectText());
        } else {
            this.f18906z.setText("");
        }
        if (StyleUtils.checkSizeValidity(selectMainStyle.getPreviewSelectTextSize())) {
            this.f18906z.setTextSize(selectMainStyle.getPreviewSelectTextSize());
        }
        if (StyleUtils.checkStyleValidity(selectMainStyle.getPreviewSelectTextColor())) {
            this.f18906z.setTextColor(selectMainStyle.getPreviewSelectTextColor());
        }
        if (StyleUtils.checkSizeValidity(selectMainStyle.getPreviewSelectMarginRight())) {
            if (this.f18905y.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                if (this.f18905y.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                    ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.f18905y.getLayoutParams())).rightMargin = selectMainStyle.getPreviewSelectMarginRight();
                }
            } else if (this.f18905y.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.f18905y.getLayoutParams()).rightMargin = selectMainStyle.getPreviewSelectMarginRight();
            }
        }
        this.B.setCompleteSelectViewStyle();
        this.B.setSelectedChange(true);
        if (selectMainStyle.isCompleteSelectRelativeTop()) {
            if (this.B.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ((ConstraintLayout.LayoutParams) this.B.getLayoutParams()).topToTop = R.id.title_bar;
                ((ConstraintLayout.LayoutParams) this.B.getLayoutParams()).bottomToBottom = R.id.title_bar;
                if (this.f19027d.isPreviewFullScreenMode) {
                    ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.B.getLayoutParams())).topMargin = DensityUtil.getStatusBarHeight(getContext());
                }
            } else if ((this.B.getLayoutParams() instanceof RelativeLayout.LayoutParams) && this.f19027d.isPreviewFullScreenMode) {
                ((RelativeLayout.LayoutParams) this.B.getLayoutParams()).topMargin = DensityUtil.getStatusBarHeight(getContext());
            }
        }
        if (selectMainStyle.isPreviewSelectRelativeBottom()) {
            if (this.f18905y.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ((ConstraintLayout.LayoutParams) this.f18905y.getLayoutParams()).topToTop = R.id.bottom_nar_bar;
                ((ConstraintLayout.LayoutParams) this.f18905y.getLayoutParams()).bottomToBottom = R.id.bottom_nar_bar;
                ((ConstraintLayout.LayoutParams) this.f18906z.getLayoutParams()).topToTop = R.id.bottom_nar_bar;
                ((ConstraintLayout.LayoutParams) this.f18906z.getLayoutParams()).bottomToBottom = R.id.bottom_nar_bar;
                ((ConstraintLayout.LayoutParams) this.A.getLayoutParams()).topToTop = R.id.bottom_nar_bar;
                ((ConstraintLayout.LayoutParams) this.A.getLayoutParams()).bottomToBottom = R.id.bottom_nar_bar;
            }
        } else if (this.f19027d.isPreviewFullScreenMode) {
            if (this.f18906z.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.f18906z.getLayoutParams())).topMargin = DensityUtil.getStatusBarHeight(getContext());
            } else if (this.f18906z.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.f18906z.getLayoutParams()).topMargin = DensityUtil.getStatusBarHeight(getContext());
            }
        }
        this.B.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.6
            /* JADX WARN: Removed duplicated region for block: B:9:0x002d  */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onClick(android.view.View r5) {
                /*
                    r4 = this;
                    com.luck.picture.lib.style.SelectMainStyle r5 = r2
                    boolean r5 = r5.isCompleteSelectRelativeTop()
                    r0 = 1
                    r1 = 0
                    if (r5 == 0) goto L2f
                    com.luck.picture.lib.PictureSelectorPreviewFragment r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.this
                    com.luck.picture.lib.config.SelectorConfig r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.F(r5)
                    int r5 = r5.getSelectCount()
                    if (r5 != 0) goto L2f
                    com.luck.picture.lib.PictureSelectorPreviewFragment r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.this
                    java.util.ArrayList r2 = r5.f18886f
                    androidx.viewpager2.widget.ViewPager2 r3 = r5.f18888h
                    int r3 = r3.getCurrentItem()
                    java.lang.Object r2 = r2.get(r3)
                    com.luck.picture.lib.entity.LocalMedia r2 = (com.luck.picture.lib.entity.LocalMedia) r2
                    int r5 = r5.confirmSelect(r2, r1)
                    if (r5 != 0) goto L2d
                    goto L3b
                L2d:
                    r0 = r1
                    goto L3b
                L2f:
                    com.luck.picture.lib.PictureSelectorPreviewFragment r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.this
                    com.luck.picture.lib.config.SelectorConfig r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.P(r5)
                    int r5 = r5.getSelectCount()
                    if (r5 <= 0) goto L2d
                L3b:
                    com.luck.picture.lib.PictureSelectorPreviewFragment r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.this
                    com.luck.picture.lib.config.SelectorConfig r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.a0(r5)
                    boolean r5 = r5.isEmptyResultReturn
                    if (r5 == 0) goto L57
                    com.luck.picture.lib.PictureSelectorPreviewFragment r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.this
                    com.luck.picture.lib.config.SelectorConfig r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.e0(r5)
                    int r5 = r5.getSelectCount()
                    if (r5 != 0) goto L57
                    com.luck.picture.lib.PictureSelectorPreviewFragment r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.this
                    r5.o()
                    goto L5e
                L57:
                    if (r0 == 0) goto L5e
                    com.luck.picture.lib.PictureSelectorPreviewFragment r5 = com.luck.picture.lib.PictureSelectorPreviewFragment.this
                    com.luck.picture.lib.PictureSelectorPreviewFragment.f0(r5)
                L5e:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.PictureSelectorPreviewFragment.AnonymousClass6.onClick(android.view.View):void");
            }
        });
    }

    private void initTitleBar() {
        if (this.f19027d.selectorStyle.getTitleBarStyle().isHideTitleBar()) {
            this.f18891k.setVisibility(8);
        }
        this.f18891k.setTitleBarStyle();
        this.f18891k.setOnTitleBarListener(new TitleBar.OnTitleBarListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.7
            @Override // com.luck.picture.lib.widget.TitleBar.OnTitleBarListener
            public void onBackPressed() {
                PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                if (pictureSelectorPreviewFragment.f18898r) {
                    if (((PictureCommonFragment) pictureSelectorPreviewFragment).f19027d.isPreviewZoomEffect) {
                        PictureSelectorPreviewFragment.this.f18887g.backToMin();
                        return;
                    } else {
                        PictureSelectorPreviewFragment.this.handleExternalPreviewBack();
                        return;
                    }
                }
                if (pictureSelectorPreviewFragment.f18894n || !((PictureCommonFragment) pictureSelectorPreviewFragment).f19027d.isPreviewZoomEffect) {
                    PictureSelectorPreviewFragment.this.n();
                } else {
                    PictureSelectorPreviewFragment.this.f18887g.backToMin();
                }
            }
        });
        this.f18891k.setTitle((this.f18893m + 1) + "/" + this.f18901u);
        this.f18891k.getImageDelete().setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PictureSelectorPreviewFragment.this.deletePreview();
            }
        });
        this.A.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                if (pictureSelectorPreviewFragment.f18898r) {
                    pictureSelectorPreviewFragment.deletePreview();
                    return;
                }
                LocalMedia localMedia = (LocalMedia) pictureSelectorPreviewFragment.f18886f.get(pictureSelectorPreviewFragment.f18888h.getCurrentItem());
                PictureSelectorPreviewFragment pictureSelectorPreviewFragment2 = PictureSelectorPreviewFragment.this;
                if (pictureSelectorPreviewFragment2.confirmSelect(localMedia, pictureSelectorPreviewFragment2.f18905y.isSelected()) == 0) {
                    if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.onSelectAnimListener != null) {
                        ((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.onSelectAnimListener.onSelectAnim(PictureSelectorPreviewFragment.this.f18905y);
                    } else {
                        PictureSelectorPreviewFragment pictureSelectorPreviewFragment3 = PictureSelectorPreviewFragment.this;
                        pictureSelectorPreviewFragment3.f18905y.startAnimation(AnimationUtils.loadAnimation(pictureSelectorPreviewFragment3.getContext(), R.anim.ps_anim_modal_in));
                    }
                }
            }
        });
        this.f18905y.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PictureSelectorPreviewFragment.this.A.performClick();
            }
        });
    }

    private void initViewPagerData(ArrayList<LocalMedia> arrayList) {
        int i2;
        PicturePreviewAdapter picturePreviewAdapterJ0 = j0();
        this.f18889i = picturePreviewAdapterJ0;
        picturePreviewAdapterJ0.setData(arrayList);
        this.f18889i.setOnPreviewEventListener(new MyOnPreviewEventListener());
        this.f18888h.setOrientation(0);
        this.f18888h.setAdapter(this.f18889i);
        this.f19027d.selectedPreviewResult.clear();
        if (arrayList.size() == 0 || this.f18893m >= arrayList.size() || (i2 = this.f18893m) < 0) {
            onKeyBackFragmentFinish();
            return;
        }
        LocalMedia localMedia = arrayList.get(i2);
        this.f18890j.isDisplayEditor(PictureMimeType.isHasVideo(localMedia.getMimeType()) || PictureMimeType.isHasAudio(localMedia.getMimeType()));
        this.f18905y.setSelected(this.f19027d.getSelectedResult().contains(arrayList.get(this.f18888h.getCurrentItem())));
        this.f18888h.registerOnPageChangeCallback(this.pageChangeCallback);
        this.f18888h.setPageTransformer(new MarginPageTransformer(DensityUtil.dip2px(i(), 3.0f)));
        this.f18888h.setCurrentItem(this.f18893m, false);
        sendChangeSubSelectPositionEvent(false);
        notifySelectNumberStyle(arrayList.get(this.f18893m));
        s0(localMedia);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHasMagicalEffect() {
        return !this.f18894n && this.f19027d.isPreviewZoomEffect;
    }

    private boolean isPlaying() {
        PicturePreviewAdapter picturePreviewAdapter = this.f18889i;
        return picturePreviewAdapter != null && picturePreviewAdapter.isPlaying(this.f18888h.getCurrentItem());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMoreData() {
        int i2 = this.f19025b + 1;
        this.f19025b = i2;
        SelectorConfig selectorConfig = this.f19027d;
        ExtendLoaderEngine extendLoaderEngine = selectorConfig.loaderDataEngine;
        if (extendLoaderEngine == null) {
            this.f19026c.loadPageMediaData(this.f18904x, i2, selectorConfig.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.5
                @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                    PictureSelectorPreviewFragment.this.handleMoreData(arrayList, z2);
                }
            });
            return;
        }
        Context context = getContext();
        long j2 = this.f18904x;
        int i3 = this.f19025b;
        int i4 = this.f19027d.pageSize;
        extendLoaderEngine.loadMoreMediaData(context, j2, i3, i4, i4, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.4
            @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
            public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                PictureSelectorPreviewFragment.this.handleMoreData(arrayList, z2);
            }
        });
    }

    public static PictureSelectorPreviewFragment newInstance() {
        PictureSelectorPreviewFragment pictureSelectorPreviewFragment = new PictureSelectorPreviewFragment();
        pictureSelectorPreviewFragment.setArguments(new Bundle());
        return pictureSelectorPreviewFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyGallerySelectMedia(LocalMedia localMedia) {
        if (this.F == null || !this.f19027d.selectorStyle.getSelectMainStyle().isPreviewDisplaySelectGallery()) {
            return;
        }
        this.F.isSelectMedia(localMedia);
    }

    private void notifyPreviewGalleryData(boolean z2, LocalMedia localMedia) {
        if (this.F == null || !this.f19027d.selectorStyle.getSelectMainStyle().isPreviewDisplaySelectGallery()) {
            return;
        }
        if (this.E.getVisibility() == 4) {
            this.E.setVisibility(0);
        }
        if (z2) {
            if (this.f19027d.selectionMode == 1) {
                this.F.clear();
            }
            this.F.addGalleryData(localMedia);
            this.E.smoothScrollToPosition(this.F.getItemCount() - 1);
            return;
        }
        this.F.removeGalleryData(localMedia);
        if (this.f19027d.getSelectCount() == 0) {
            this.E.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onExternalLongPressDownload(final LocalMedia localMedia) {
        OnExternalPreviewEventListener onExternalPreviewEventListener = this.f19027d.onExternalPreviewEventListener;
        if (onExternalPreviewEventListener == null || onExternalPreviewEventListener.onLongPressDownload(getContext(), localMedia)) {
            return;
        }
        PictureCommonDialog.showDialog(getContext(), getString(R.string.ps_prompt), (PictureMimeType.isHasAudio(localMedia.getMimeType()) || PictureMimeType.isUrlHasAudio(localMedia.getAvailablePath())) ? getString(R.string.ps_prompt_audio_content) : (PictureMimeType.isHasVideo(localMedia.getMimeType()) || PictureMimeType.isUrlHasVideo(localMedia.getAvailablePath())) ? getString(R.string.ps_prompt_video_content) : getString(R.string.ps_prompt_image_content)).setOnDialogEventListener(new PictureCommonDialog.OnDialogEventListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.21
            @Override // com.luck.picture.lib.dialog.PictureCommonDialog.OnDialogEventListener
            public void onConfirm() {
                String availablePath = localMedia.getAvailablePath();
                if (PictureMimeType.isHasHttp(availablePath)) {
                    PictureSelectorPreviewFragment.this.showLoading();
                }
                DownloadFileUtils.saveLocalFile(PictureSelectorPreviewFragment.this.getContext(), availablePath, localMedia.getMimeType(), new OnCallbackListener<String>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.21.1
                    @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                    public void onCall(String str) {
                        PictureSelectorPreviewFragment.this.dismissLoading();
                        if (TextUtils.isEmpty(str)) {
                            ToastUtils.showToast(PictureSelectorPreviewFragment.this.getContext(), PictureMimeType.isHasAudio(localMedia.getMimeType()) ? PictureSelectorPreviewFragment.this.getString(R.string.ps_save_audio_error) : PictureMimeType.isHasVideo(localMedia.getMimeType()) ? PictureSelectorPreviewFragment.this.getString(R.string.ps_save_video_error) : PictureSelectorPreviewFragment.this.getString(R.string.ps_save_image_error));
                            return;
                        }
                        new PictureMediaScannerConnection(PictureSelectorPreviewFragment.this.getActivity(), str);
                        ToastUtils.showToast(PictureSelectorPreviewFragment.this.getContext(), PictureSelectorPreviewFragment.this.getString(R.string.ps_save_success) + "\n" + str);
                    }
                });
            }
        });
    }

    private void onKeyDownBackToMin() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (this.f18898r) {
            if (this.f19027d.isPreviewZoomEffect) {
                this.f18887g.backToMin();
                return;
            } else {
                o();
                return;
            }
        }
        if (this.f18894n) {
            n();
        } else if (this.f19027d.isPreviewZoomEffect) {
            this.f18887g.backToMin();
        } else {
            n();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void previewFullScreenMode() {
        if (this.f18900t) {
            return;
        }
        final boolean z2 = this.f18891k.getTranslationY() == 0.0f;
        AnimatorSet animatorSet = new AnimatorSet();
        float f2 = z2 ? 0.0f : -this.f18891k.getHeight();
        float f3 = z2 ? -this.f18891k.getHeight() : 0.0f;
        float f4 = z2 ? 1.0f : 0.0f;
        float f5 = z2 ? 0.0f : 1.0f;
        for (int i2 = 0; i2 < this.G.size(); i2++) {
            View view = (View) this.G.get(i2);
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", f4, f5));
            if (view instanceof TitleBar) {
                animatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationY", f2, f3));
            }
        }
        animatorSet.setDuration(350L);
        animatorSet.start();
        this.f18900t = true;
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.20
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            @SuppressLint({"WrongConstant"})
            public void onAnimationEnd(Animator animator) {
                PictureSelectorPreviewFragment.this.f18900t = false;
                if (SdkVersionUtils.isP() && PictureSelectorPreviewFragment.this.isAdded()) {
                    Window window = PictureSelectorPreviewFragment.this.requireActivity().getWindow();
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    if (!z2) {
                        attributes.flags &= -1025;
                        window.setAttributes(attributes);
                        window.clearFlags(512);
                    } else {
                        attributes.flags |= 1024;
                        attributes.layoutInDisplayCutoutMode = 1;
                        window.setAttributes(attributes);
                        window.addFlags(512);
                    }
                }
            }
        });
        if (z2) {
            showFullScreenStatusBar();
        } else {
            hideFullScreenStatusBar();
        }
    }

    private void resumePausePlay() {
        BasePreviewHolder currentHolder;
        PicturePreviewAdapter picturePreviewAdapter = this.f18889i;
        if (picturePreviewAdapter == null || (currentHolder = picturePreviewAdapter.getCurrentHolder(this.f18888h.getCurrentItem())) == null) {
            return;
        }
        currentHolder.resumePausePlay();
    }

    private void setMagicalViewBackgroundColor() {
        ArrayList arrayList;
        SelectMainStyle selectMainStyle = this.f19027d.selectorStyle.getSelectMainStyle();
        if (StyleUtils.checkStyleValidity(selectMainStyle.getPreviewBackgroundColor())) {
            this.f18887g.setBackgroundColor(selectMainStyle.getPreviewBackgroundColor());
            return;
        }
        if (this.f19027d.chooseMode == SelectMimeType.ofAudio() || ((arrayList = this.f18886f) != null && arrayList.size() > 0 && PictureMimeType.isHasAudio(((LocalMedia) this.f18886f.get(0)).getMimeType()))) {
            this.f18887g.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.ps_color_white));
        } else {
            this.f18887g.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.ps_color_black));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMagicalViewParams(int i2, int i3, int i4) {
        this.f18887g.changeRealScreenHeight(i2, i3, true);
        if (this.f18897q) {
            i4++;
        }
        ViewParams itemViewParams = BuildRecycleItemViewParams.getItemViewParams(i4);
        if (itemViewParams == null || i2 == 0 || i3 == 0) {
            this.f18887g.setViewParams(0, 0, 0, 0, i2, i3);
        } else {
            this.f18887g.setViewParams(itemViewParams.left, itemViewParams.f19095top, itemViewParams.width, itemViewParams.height, i2, i3);
        }
    }

    private void showFullScreenStatusBar() {
        for (int i2 = 0; i2 < this.G.size(); i2++) {
            ((View) this.G.get(i2)).setEnabled(false);
        }
        this.f18890j.getEditor().setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void start(final int[] iArr) {
        int i2;
        this.f18887g.changeRealScreenHeight(iArr[0], iArr[1], false);
        ViewParams itemViewParams = BuildRecycleItemViewParams.getItemViewParams(this.f18897q ? this.f18893m + 1 : this.f18893m);
        if (itemViewParams == null || ((i2 = iArr[0]) == 0 && iArr[1] == 0)) {
            this.f18888h.post(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.19
                @Override // java.lang.Runnable
                public void run() {
                    MagicalView magicalView = PictureSelectorPreviewFragment.this.f18887g;
                    int[] iArr2 = iArr;
                    magicalView.startNormal(iArr2[0], iArr2[1], false);
                }
            });
            this.f18887g.setBackgroundAlpha(1.0f);
            for (int i3 = 0; i3 < this.G.size(); i3++) {
                ((View) this.G.get(i3)).setAlpha(1.0f);
            }
        } else {
            this.f18887g.setViewParams(itemViewParams.left, itemViewParams.f19095top, itemViewParams.width, itemViewParams.height, i2, iArr[1]);
            this.f18887g.start(false);
        }
        ObjectAnimator.ofFloat(this.f18888h, "alpha", 0.0f, 1.0f).setDuration(50L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAutoVideoPlay(final int i2) {
        this.f18888h.post(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.23
            @Override // java.lang.Runnable
            public void run() {
                PictureSelectorPreviewFragment.this.f18889i.startAutoVideoPlay(i2);
            }
        });
    }

    public void addAminViews(View... viewArr) {
        Collections.addAll(this.G, viewArr);
    }

    public PicturePreviewAdapter getAdapter() {
        return this.f18889i;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment
    public String getFragmentTag() {
        return TAG;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public int getResourceId() {
        int layoutResource = InjectResourceSource.getLayoutResource(getContext(), 2, this.f19027d);
        return layoutResource != 0 ? layoutResource : R.layout.ps_fragment_preview;
    }

    public ViewPager2 getViewPager2() {
        return this.f18888h;
    }

    protected PicturePreviewAdapter j0() {
        return new PicturePreviewAdapter(this.f19027d);
    }

    protected void k0(ViewGroup viewGroup) {
        SelectMainStyle selectMainStyle = this.f19027d.selectorStyle.getSelectMainStyle();
        if (selectMainStyle.isPreviewDisplaySelectGallery()) {
            this.E = new RecyclerView(getContext());
            if (StyleUtils.checkStyleValidity(selectMainStyle.getAdapterPreviewGalleryBackgroundResource())) {
                this.E.setBackgroundResource(selectMainStyle.getAdapterPreviewGalleryBackgroundResource());
            } else {
                this.E.setBackgroundResource(R.drawable.ps_preview_gallery_bg);
            }
            viewGroup.addView(this.E);
            ViewGroup.LayoutParams layoutParams = this.E.getLayoutParams();
            if (layoutParams instanceof ConstraintLayout.LayoutParams) {
                ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
                ((ViewGroup.MarginLayoutParams) layoutParams2).width = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams2).height = -2;
                layoutParams2.bottomToTop = R.id.bottom_nar_bar;
                layoutParams2.startToStart = 0;
                layoutParams2.endToEnd = 0;
            }
            WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(getContext()) { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.11
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i2) {
                    super.smoothScrollToPosition(recyclerView, state, i2);
                    LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.11.1
                        @Override // androidx.recyclerview.widget.LinearSmoothScroller
                        protected float j(DisplayMetrics displayMetrics) {
                            return 300.0f / displayMetrics.densityDpi;
                        }
                    };
                    linearSmoothScroller.setTargetPosition(i2);
                    startSmoothScroll(linearSmoothScroller);
                }
            };
            RecyclerView.ItemAnimator itemAnimator = this.E.getItemAnimator();
            if (itemAnimator != null) {
                ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
            }
            if (this.E.getItemDecorationCount() == 0) {
                this.E.addItemDecoration(new HorizontalItemDecoration(Integer.MAX_VALUE, DensityUtil.dip2px(getContext(), 6.0f)));
            }
            wrapContentLinearLayoutManager.setOrientation(0);
            this.E.setLayoutManager(wrapContentLinearLayoutManager);
            if (this.f19027d.getSelectCount() > 0) {
                this.E.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.ps_anim_layout_fall_enter));
            }
            this.F = new PreviewGalleryAdapter(this.f19027d, this.f18894n);
            notifyGallerySelectMedia((LocalMedia) this.f18886f.get(this.f18893m));
            this.E.setAdapter(this.F);
            this.F.setItemClickListener(new PreviewGalleryAdapter.OnItemClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.12
                @Override // com.luck.picture.lib.adapter.holder.PreviewGalleryAdapter.OnItemClickListener
                public void onItemClick(final int i2, LocalMedia localMedia, View view) {
                    if (i2 == -1) {
                        return;
                    }
                    String string = TextUtils.isEmpty(((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.defaultAlbumName) ? PictureSelectorPreviewFragment.this.getString(R.string.ps_camera_roll) : ((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.defaultAlbumName;
                    PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                    if (pictureSelectorPreviewFragment.f18894n || TextUtils.equals(pictureSelectorPreviewFragment.f18896p, string) || TextUtils.equals(localMedia.getParentFolderName(), PictureSelectorPreviewFragment.this.f18896p)) {
                        PictureSelectorPreviewFragment pictureSelectorPreviewFragment2 = PictureSelectorPreviewFragment.this;
                        if (!pictureSelectorPreviewFragment2.f18894n) {
                            i2 = pictureSelectorPreviewFragment2.f18897q ? localMedia.position - 1 : localMedia.position;
                        }
                        if (i2 == pictureSelectorPreviewFragment2.f18888h.getCurrentItem() && localMedia.isChecked()) {
                            return;
                        }
                        LocalMedia item = PictureSelectorPreviewFragment.this.f18889i.getItem(i2);
                        if (item == null || (TextUtils.equals(localMedia.getPath(), item.getPath()) && localMedia.getId() == item.getId())) {
                            if (PictureSelectorPreviewFragment.this.f18888h.getAdapter() != null) {
                                PictureSelectorPreviewFragment.this.f18888h.setAdapter(null);
                                PictureSelectorPreviewFragment pictureSelectorPreviewFragment3 = PictureSelectorPreviewFragment.this;
                                pictureSelectorPreviewFragment3.f18888h.setAdapter(pictureSelectorPreviewFragment3.f18889i);
                            }
                            PictureSelectorPreviewFragment.this.f18888h.setCurrentItem(i2, false);
                            PictureSelectorPreviewFragment.this.notifyGallerySelectMedia(localMedia);
                            PictureSelectorPreviewFragment.this.f18888h.post(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.12.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.isPreviewZoomEffect) {
                                        PictureSelectorPreviewFragment.this.f18889i.setVideoPlayButtonUI(i2);
                                    }
                                }
                            });
                        }
                    }
                }
            });
            if (this.f19027d.getSelectCount() > 0) {
                this.E.setVisibility(0);
            } else {
                this.E.setVisibility(4);
            }
            addAminViews(this.E);
            final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.13
                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                    int lastCheckPosition;
                    viewHolder.itemView.setAlpha(1.0f);
                    PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                    if (pictureSelectorPreviewFragment.D) {
                        pictureSelectorPreviewFragment.D = false;
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.1f, 1.0f), ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.1f, 1.0f));
                        animatorSet.setInterpolator(new LinearInterpolator());
                        animatorSet.setDuration(50L);
                        animatorSet.start();
                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.13.2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                PictureSelectorPreviewFragment.this.C = true;
                            }
                        });
                    }
                    super.clearView(recyclerView, viewHolder);
                    PictureSelectorPreviewFragment.this.F.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition());
                    PictureSelectorPreviewFragment pictureSelectorPreviewFragment2 = PictureSelectorPreviewFragment.this;
                    if (pictureSelectorPreviewFragment2.f18894n && PictureSelectorPreviewFragment.this.f18888h.getCurrentItem() != (lastCheckPosition = pictureSelectorPreviewFragment2.F.getLastCheckPosition()) && lastCheckPosition != -1) {
                        if (PictureSelectorPreviewFragment.this.f18888h.getAdapter() != null) {
                            PictureSelectorPreviewFragment.this.f18888h.setAdapter(null);
                            PictureSelectorPreviewFragment pictureSelectorPreviewFragment3 = PictureSelectorPreviewFragment.this;
                            pictureSelectorPreviewFragment3.f18888h.setAdapter(pictureSelectorPreviewFragment3.f18889i);
                        }
                        PictureSelectorPreviewFragment.this.f18888h.setCurrentItem(lastCheckPosition, false);
                    }
                    if (!((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.selectorStyle.getSelectMainStyle().isSelectNumberStyle() || ActivityCompatHelper.isDestroy(PictureSelectorPreviewFragment.this.getActivity())) {
                        return;
                    }
                    List<Fragment> fragments = PictureSelectorPreviewFragment.this.getActivity().getSupportFragmentManager().getFragments();
                    for (int i2 = 0; i2 < fragments.size(); i2++) {
                        Fragment fragment = fragments.get(i2);
                        if (fragment instanceof PictureCommonFragment) {
                            ((PictureCommonFragment) fragment).sendChangeSubSelectPositionEvent(true);
                        }
                    }
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public long getAnimationDuration(@NonNull RecyclerView recyclerView, int i2, float f2, float f3) {
                    return super.getAnimationDuration(recyclerView, i2, f2, f3);
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                    viewHolder.itemView.setAlpha(0.7f);
                    return ItemTouchHelper.Callback.makeMovementFlags(12, 0);
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public boolean isLongPressDragEnabled() {
                    return true;
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float f2, float f3, int i2, boolean z2) {
                    PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                    if (pictureSelectorPreviewFragment.C) {
                        pictureSelectorPreviewFragment.C = false;
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.0f, 1.1f), ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.0f, 1.1f));
                        animatorSet.setDuration(50L);
                        animatorSet.setInterpolator(new LinearInterpolator());
                        animatorSet.start();
                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.13.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                PictureSelectorPreviewFragment.this.D = true;
                            }
                        });
                    }
                    super.onChildDraw(canvas, recyclerView, viewHolder, f2, f3, i2, z2);
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2) {
                    try {
                        int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
                        int absoluteAdapterPosition2 = viewHolder2.getAbsoluteAdapterPosition();
                        if (absoluteAdapterPosition < absoluteAdapterPosition2) {
                            int i2 = absoluteAdapterPosition;
                            while (i2 < absoluteAdapterPosition2) {
                                int i3 = i2 + 1;
                                Collections.swap(PictureSelectorPreviewFragment.this.F.getData(), i2, i3);
                                Collections.swap(((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.getSelectedResult(), i2, i3);
                                PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                                if (pictureSelectorPreviewFragment.f18894n) {
                                    Collections.swap(pictureSelectorPreviewFragment.f18886f, i2, i3);
                                }
                                i2 = i3;
                            }
                        } else {
                            for (int i4 = absoluteAdapterPosition; i4 > absoluteAdapterPosition2; i4--) {
                                int i5 = i4 - 1;
                                Collections.swap(PictureSelectorPreviewFragment.this.F.getData(), i4, i5);
                                Collections.swap(((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.getSelectedResult(), i4, i5);
                                PictureSelectorPreviewFragment pictureSelectorPreviewFragment2 = PictureSelectorPreviewFragment.this;
                                if (pictureSelectorPreviewFragment2.f18894n) {
                                    Collections.swap(pictureSelectorPreviewFragment2.f18886f, i4, i5);
                                }
                            }
                        }
                        PictureSelectorPreviewFragment.this.F.notifyItemMoved(absoluteAdapterPosition, absoluteAdapterPosition2);
                        return true;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return true;
                    }
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i2) {
                    super.onSelectedChanged(viewHolder, i2);
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i2) {
                }
            });
            itemTouchHelper.attachToRecyclerView(this.E);
            this.F.setItemLongClickListener(new PreviewGalleryAdapter.OnItemLongClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.14
                @Override // com.luck.picture.lib.adapter.holder.PreviewGalleryAdapter.OnItemLongClickListener
                public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i2, View view) {
                    ((Vibrator) PictureSelectorPreviewFragment.this.getActivity().getSystemService("vibrator")).vibrate(50L);
                    if (PictureSelectorPreviewFragment.this.F.getItemCount() != ((PictureCommonFragment) PictureSelectorPreviewFragment.this).f19027d.maxSelectNum) {
                        itemTouchHelper.startDrag(viewHolder);
                    } else if (viewHolder.getLayoutPosition() != PictureSelectorPreviewFragment.this.F.getItemCount() - 1) {
                        itemTouchHelper.startDrag(viewHolder);
                    }
                }
            });
        }
    }

    protected boolean l0(LocalMedia localMedia) {
        return this.f19027d.getSelectedResult().contains(localMedia);
    }

    protected void m0(float f2) {
        for (int i2 = 0; i2 < this.G.size(); i2++) {
            if (!(this.G.get(i2) instanceof TitleBar)) {
                ((View) this.G.get(i2)).setAlpha(f2);
            }
        }
    }

    protected void n0(MagicalView magicalView, boolean z2) {
        int width;
        int height;
        BasePreviewHolder currentHolder = this.f18889i.getCurrentHolder(this.f18888h.getCurrentItem());
        if (currentHolder == null) {
            return;
        }
        LocalMedia localMedia = (LocalMedia) this.f18886f.get(this.f18888h.getCurrentItem());
        if (!localMedia.isCut() || localMedia.getCropImageWidth() <= 0 || localMedia.getCropImageHeight() <= 0) {
            width = localMedia.getWidth();
            height = localMedia.getHeight();
        } else {
            width = localMedia.getCropImageWidth();
            height = localMedia.getCropImageHeight();
        }
        if (MediaUtils.isLongImage(width, height)) {
            currentHolder.coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            currentHolder.coverImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        if (currentHolder instanceof PreviewVideoHolder) {
            PreviewVideoHolder previewVideoHolder = (PreviewVideoHolder) currentHolder;
            if (this.f19027d.isAutoVideoPlay) {
                startAutoVideoPlay(this.f18888h.getCurrentItem());
            } else {
                if (previewVideoHolder.ivPlayButton.getVisibility() != 8 || isPlaying()) {
                    return;
                }
                previewVideoHolder.ivPlayButton.setVisibility(0);
            }
        }
    }

    public void notifySelectNumberStyle(LocalMedia localMedia) {
        if (this.f19027d.selectorStyle.getSelectMainStyle().isPreviewSelectNumberStyle() && this.f19027d.selectorStyle.getSelectMainStyle().isSelectNumberStyle()) {
            this.f18905y.setText("");
            for (int i2 = 0; i2 < this.f19027d.getSelectCount(); i2++) {
                LocalMedia localMedia2 = this.f19027d.getSelectedResult().get(i2);
                if (TextUtils.equals(localMedia2.getPath(), localMedia.getPath()) || localMedia2.getId() == localMedia.getId()) {
                    localMedia.setNum(localMedia2.getNum());
                    localMedia2.setPosition(localMedia.getPosition());
                    this.f18905y.setText(ValueOf.toString(Integer.valueOf(localMedia.getNum())));
                }
            }
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment
    protected void o() {
        PicturePreviewAdapter picturePreviewAdapter = this.f18889i;
        if (picturePreviewAdapter != null) {
            picturePreviewAdapter.destroy();
        }
        super.o();
    }

    protected void o0() {
        BasePreviewHolder currentHolder = this.f18889i.getCurrentHolder(this.f18888h.getCurrentItem());
        if (currentHolder == null) {
            return;
        }
        if (currentHolder.coverImageView.getVisibility() == 8) {
            currentHolder.coverImageView.setVisibility(0);
        }
        if (currentHolder instanceof PreviewVideoHolder) {
            PreviewVideoHolder previewVideoHolder = (PreviewVideoHolder) currentHolder;
            if (previewVideoHolder.ivPlayButton.getVisibility() == 0) {
                previewVideoHolder.ivPlayButton.setVisibility(8);
            }
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onCheckOriginalChange() {
        this.f18890j.setOriginalCheck();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (isHasMagicalEffect()) {
            int size = this.f18886f.size();
            int i2 = this.f18893m;
            if (size > i2) {
                LocalMedia localMedia = (LocalMedia) this.f18886f.get(i2);
                if (PictureMimeType.isHasVideo(localMedia.getMimeType())) {
                    getVideoRealSizeFromMedia(localMedia, false, new OnCallbackListener<int[]>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.2
                        @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                        public void onCall(int[] iArr) {
                            PictureSelectorPreviewFragment.this.changeViewParams(iArr);
                        }
                    });
                } else {
                    getImageRealSizeFromMedia(localMedia, false, new OnCallbackListener<int[]>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.3
                        @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                        public void onCall(int[] iArr) {
                            PictureSelectorPreviewFragment.this.changeViewParams(iArr);
                        }
                    });
                }
            }
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    @Nullable
    public Animation onCreateAnimation(int i2, boolean z2, int i3) throws Resources.NotFoundException {
        if (isHasMagicalEffect()) {
            return null;
        }
        PictureWindowAnimationStyle windowAnimationStyle = this.f19027d.selectorStyle.getWindowAnimationStyle();
        if (windowAnimationStyle.activityPreviewEnterAnimation == 0 || windowAnimationStyle.activityPreviewExitAnimation == 0) {
            return super.onCreateAnimation(i2, z2, i3);
        }
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getActivity(), z2 ? windowAnimationStyle.activityPreviewEnterAnimation : windowAnimationStyle.activityPreviewExitAnimation);
        if (z2) {
            onEnterFragment();
        } else {
            onExitFragment();
        }
        return animationLoadAnimation;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onCreateLoader() {
        if (this.f18898r) {
            return;
        }
        SelectorConfig selectorConfig = this.f19027d;
        IBridgeLoaderFactory iBridgeLoaderFactory = selectorConfig.loaderFactory;
        if (iBridgeLoaderFactory == null) {
            this.f19026c = selectorConfig.isPageStrategy ? new LocalMediaPageLoader(i(), this.f19027d) : new LocalMediaLoader(i(), this.f19027d);
            return;
        }
        IBridgeMediaLoader iBridgeMediaLoaderOnCreateLoader = iBridgeLoaderFactory.onCreateLoader();
        this.f19026c = iBridgeMediaLoaderOnCreateLoader;
        if (iBridgeMediaLoaderOnCreateLoader != null) {
            return;
        }
        throw new NullPointerException("No available " + IBridgeMediaLoader.class + " loader found");
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        PicturePreviewAdapter picturePreviewAdapter = this.f18889i;
        if (picturePreviewAdapter != null) {
            picturePreviewAdapter.destroy();
        }
        ViewPager2 viewPager2 = this.f18888h;
        if (viewPager2 != null) {
            viewPager2.unregisterOnPageChangeCallback(this.pageChangeCallback);
        }
        super.onDestroy();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onEditMedia(Intent intent) {
        if (this.f18886f.size() > this.f18888h.getCurrentItem()) {
            LocalMedia localMedia = (LocalMedia) this.f18886f.get(this.f18888h.getCurrentItem());
            Uri output = Crop.getOutput(intent);
            localMedia.setCutPath(output != null ? output.getPath() : "");
            localMedia.setCropImageWidth(Crop.getOutputImageWidth(intent));
            localMedia.setCropImageHeight(Crop.getOutputImageHeight(intent));
            localMedia.setCropOffsetX(Crop.getOutputImageOffsetX(intent));
            localMedia.setCropOffsetY(Crop.getOutputImageOffsetY(intent));
            localMedia.setCropResultAspectRatio(Crop.getOutputCropAspectRatio(intent));
            localMedia.setCut(!TextUtils.isEmpty(localMedia.getCutPath()));
            localMedia.setCustomData(Crop.getOutputCustomExtraData(intent));
            localMedia.setEditorImage(localMedia.isCut());
            localMedia.setSandboxPath(localMedia.getCutPath());
            if (this.f19027d.getSelectedResult().contains(localMedia)) {
                LocalMedia compareLocalMedia = localMedia.getCompareLocalMedia();
                if (compareLocalMedia != null) {
                    compareLocalMedia.setCutPath(localMedia.getCutPath());
                    compareLocalMedia.setCut(localMedia.isCut());
                    compareLocalMedia.setEditorImage(localMedia.isEditorImage());
                    compareLocalMedia.setCustomData(localMedia.getCustomData());
                    compareLocalMedia.setSandboxPath(localMedia.getCutPath());
                    compareLocalMedia.setCropImageWidth(Crop.getOutputImageWidth(intent));
                    compareLocalMedia.setCropImageHeight(Crop.getOutputImageHeight(intent));
                    compareLocalMedia.setCropOffsetX(Crop.getOutputImageOffsetX(intent));
                    compareLocalMedia.setCropOffsetY(Crop.getOutputImageOffsetY(intent));
                    compareLocalMedia.setCropResultAspectRatio(Crop.getOutputCropAspectRatio(intent));
                }
                sendFixedSelectedChangeEvent(localMedia);
            } else {
                confirmSelect(localMedia, false);
            }
            this.f18889i.notifyItemChanged(this.f18888h.getCurrentItem());
            notifyGallerySelectMedia(localMedia);
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onExitFragment() {
        if (this.f19027d.isPreviewFullScreenMode) {
            hideFullScreenStatusBar();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onKeyBackFragmentFinish() {
        onKeyDownBackToMin();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (isPlaying()) {
            resumePausePlay();
            this.isPause = true;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.isPause) {
            resumePausePlay();
            this.isPause = false;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(PictureConfig.EXTRA_CURRENT_PAGE, this.f19025b);
        bundle.putLong(PictureConfig.EXTRA_CURRENT_BUCKET_ID, this.f18904x);
        bundle.putInt(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, this.f18893m);
        bundle.putInt(PictureConfig.EXTRA_PREVIEW_CURRENT_ALBUM_TOTAL, this.f18901u);
        bundle.putBoolean(PictureConfig.EXTRA_EXTERNAL_PREVIEW, this.f18898r);
        bundle.putBoolean(PictureConfig.EXTRA_EXTERNAL_PREVIEW_DISPLAY_DELETE, this.f18899s);
        bundle.putBoolean(PictureConfig.EXTRA_DISPLAY_CAMERA, this.f18897q);
        bundle.putBoolean(PictureConfig.EXTRA_BOTTOM_PREVIEW, this.f18894n);
        bundle.putString(PictureConfig.EXTRA_CURRENT_ALBUM_NAME, this.f18896p);
        this.f19027d.addSelectedPreviewResult(this.f18886f);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onSelectedChange(boolean z2, LocalMedia localMedia) {
        this.f18905y.setSelected(this.f19027d.getSelectedResult().contains(localMedia));
        this.f18890j.setSelectedChange();
        this.B.setSelectedChange(true);
        notifySelectNumberStyle(localMedia);
        notifyPreviewGalleryData(z2, localMedia);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        reStartSavedInstance(bundle);
        this.f18895o = bundle != null;
        this.f18902v = DensityUtil.getRealScreenWidth(getContext());
        this.f18903w = DensityUtil.getScreenHeight(getContext());
        this.f18891k = (PreviewTitleBar) view.findViewById(R.id.title_bar);
        this.f18905y = (TextView) view.findViewById(R.id.ps_tv_selected);
        this.f18906z = (TextView) view.findViewById(R.id.ps_tv_selected_word);
        this.A = view.findViewById(R.id.select_click_area);
        this.B = (CompleteSelectView) view.findViewById(R.id.ps_complete_select);
        this.f18887g = (MagicalView) view.findViewById(R.id.magical);
        this.f18888h = new ViewPager2(getContext());
        this.f18890j = (PreviewBottomNavBar) view.findViewById(R.id.bottom_nar_bar);
        this.f18887g.setMagicalContent(this.f18888h);
        setMagicalViewBackgroundColor();
        r0();
        addAminViews(this.f18891k, this.f18905y, this.f18906z, this.A, this.B, this.f18890j);
        onCreateLoader();
        initTitleBar();
        initViewPagerData(this.f18886f);
        if (this.f18898r) {
            externalPreviewStyle();
        } else {
            initBottomNavBar();
            k0((ViewGroup) view);
            initComplete();
        }
        iniMagicalView();
    }

    protected void p0(boolean z2) {
        BasePreviewHolder currentHolder;
        ViewParams itemViewParams = BuildRecycleItemViewParams.getItemViewParams(this.f18897q ? this.f18893m + 1 : this.f18893m);
        if (itemViewParams == null || (currentHolder = this.f18889i.getCurrentHolder(this.f18888h.getCurrentItem())) == null) {
            return;
        }
        currentHolder.coverImageView.getLayoutParams().width = itemViewParams.width;
        currentHolder.coverImageView.getLayoutParams().height = itemViewParams.height;
        currentHolder.coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    protected void q0() {
        if (this.f18898r && m() && isHasMagicalEffect()) {
            o();
        } else {
            n();
        }
    }

    protected void r0() {
        if (isHasMagicalEffect()) {
            this.f18887g.setOnMojitoViewCallback(new OnMagicalViewCallback() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.1
                @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
                public void onBackgroundAlpha(float f2) {
                    PictureSelectorPreviewFragment.this.m0(f2);
                }

                @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
                public void onBeginBackMinAnim() {
                    PictureSelectorPreviewFragment.this.o0();
                }

                @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
                public void onBeginBackMinMagicalFinish(boolean z2) {
                    PictureSelectorPreviewFragment.this.p0(z2);
                }

                @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
                public void onBeginMagicalAnimComplete(MagicalView magicalView, boolean z2) {
                    PictureSelectorPreviewFragment.this.n0(magicalView, z2);
                }

                @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
                public void onMagicalViewFinish() {
                    PictureSelectorPreviewFragment.this.q0();
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void reStartSavedInstance(Bundle bundle) {
        if (bundle != null) {
            this.f19025b = bundle.getInt(PictureConfig.EXTRA_CURRENT_PAGE, 1);
            this.f18904x = bundle.getLong(PictureConfig.EXTRA_CURRENT_BUCKET_ID, -1L);
            this.f18893m = bundle.getInt(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, this.f18893m);
            this.f18897q = bundle.getBoolean(PictureConfig.EXTRA_DISPLAY_CAMERA, this.f18897q);
            this.f18901u = bundle.getInt(PictureConfig.EXTRA_PREVIEW_CURRENT_ALBUM_TOTAL, this.f18901u);
            this.f18898r = bundle.getBoolean(PictureConfig.EXTRA_EXTERNAL_PREVIEW, this.f18898r);
            this.f18899s = bundle.getBoolean(PictureConfig.EXTRA_EXTERNAL_PREVIEW_DISPLAY_DELETE, this.f18899s);
            this.f18894n = bundle.getBoolean(PictureConfig.EXTRA_BOTTOM_PREVIEW, this.f18894n);
            this.f18896p = bundle.getString(PictureConfig.EXTRA_CURRENT_ALBUM_NAME, "");
            if (this.f18886f.size() == 0) {
                this.f18886f.addAll(new ArrayList(this.f19027d.selectedPreviewResult));
            }
        }
    }

    protected void s0(LocalMedia localMedia) {
        if (this.f18895o || this.f18894n || !this.f19027d.isPreviewZoomEffect) {
            return;
        }
        this.f18888h.post(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.16
            @Override // java.lang.Runnable
            public void run() {
                PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                pictureSelectorPreviewFragment.f18889i.setCoverScaleType(pictureSelectorPreviewFragment.f18893m);
            }
        });
        if (PictureMimeType.isHasVideo(localMedia.getMimeType())) {
            getVideoRealSizeFromMedia(localMedia, !PictureMimeType.isHasHttp(localMedia.getAvailablePath()), new OnCallbackListener<int[]>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.17
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(int[] iArr) {
                    PictureSelectorPreviewFragment.this.start(iArr);
                }
            });
        } else {
            getImageRealSizeFromMedia(localMedia, !PictureMimeType.isHasHttp(localMedia.getAvailablePath()), new OnCallbackListener<int[]>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.18
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(int[] iArr) {
                    PictureSelectorPreviewFragment.this.start(iArr);
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendChangeSubSelectPositionEvent(boolean z2) {
        if (this.f19027d.selectorStyle.getSelectMainStyle().isPreviewSelectNumberStyle() && this.f19027d.selectorStyle.getSelectMainStyle().isSelectNumberStyle()) {
            int i2 = 0;
            while (i2 < this.f19027d.getSelectCount()) {
                LocalMedia localMedia = this.f19027d.getSelectedResult().get(i2);
                i2++;
                localMedia.setNum(i2);
            }
        }
    }

    public void setExternalPreviewData(int i2, int i3, ArrayList<LocalMedia> arrayList, boolean z2) {
        this.f18886f = arrayList;
        this.f18901u = i3;
        this.f18893m = i2;
        this.f18899s = z2;
        this.f18898r = true;
    }

    public void setInternalPreviewData(boolean z2, String str, boolean z3, int i2, int i3, int i4, long j2, ArrayList<LocalMedia> arrayList) {
        this.f19025b = i4;
        this.f18904x = j2;
        this.f18886f = arrayList;
        this.f18901u = i3;
        this.f18893m = i2;
        this.f18896p = str;
        this.f18897q = z3;
        this.f18894n = z2;
    }
}
