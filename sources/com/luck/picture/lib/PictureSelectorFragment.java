package com.luck.picture.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.luck.picture.lib.adapter.PictureImageGridAdapter;
import com.luck.picture.lib.animators.AlphaInAnimationAdapter;
import com.luck.picture.lib.animators.SlideInBottomAnimationAdapter;
import com.luck.picture.lib.basic.IBridgeLoaderFactory;
import com.luck.picture.lib.basic.IPictureSelectorEvent;
import com.luck.picture.lib.basic.PictureCommonFragment;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.dialog.AlbumListPopWindow;
import com.luck.picture.lib.engine.ExtendLoaderEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnAlbumItemClickListener;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.interfaces.OnQueryAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryAllAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.luck.picture.lib.interfaces.OnRecyclerViewPreloadMoreListener;
import com.luck.picture.lib.interfaces.OnRecyclerViewScrollListener;
import com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener;
import com.luck.picture.lib.interfaces.OnRequestPermissionListener;
import com.luck.picture.lib.loader.IBridgeMediaLoader;
import com.luck.picture.lib.loader.LocalMediaLoader;
import com.luck.picture.lib.loader.LocalMediaPageLoader;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.luck.picture.lib.permissions.PermissionResultCallback;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.luck.picture.lib.utils.AnimUtils;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.DoubleUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.luck.picture.lib.utils.ToastUtils;
import com.luck.picture.lib.utils.ValueOf;
import com.luck.picture.lib.widget.BottomNavBar;
import com.luck.picture.lib.widget.CompleteSelectView;
import com.luck.picture.lib.widget.RecyclerPreloadView;
import com.luck.picture.lib.widget.SlideSelectTouchListener;
import com.luck.picture.lib.widget.SlideSelectionHandler;
import com.luck.picture.lib.widget.TitleBar;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class PictureSelectorFragment extends PictureCommonFragment implements OnRecyclerViewPreloadMoreListener, IPictureSelectorEvent {
    private static final Object LOCK = new Object();
    private static int SELECT_ANIM_DURATION = 135;
    public static final String TAG = "PictureSelectorFragment";
    private AlbumListPopWindow albumListPopWindow;
    private int allFolderSize;
    private BottomNavBar bottomNarBar;
    private CompleteSelectView completeSelectView;
    private boolean isCameraCallback;
    private boolean isDisplayCamera;
    private boolean isMemoryRecycling;
    private PictureImageGridAdapter mAdapter;
    private SlideSelectTouchListener mDragSelectTouchListener;
    private RecyclerPreloadView mRecycler;
    private TitleBar titleBar;
    private TextView tvCurrentDataTime;
    private TextView tvDataEmpty;
    private long intervalClickTime = 0;
    private int currentPosition = -1;

    private void addAlbumPopWindowAction() {
        this.albumListPopWindow.setOnIBridgeAlbumWidget(new OnAlbumItemClickListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.7
            @Override // com.luck.picture.lib.interfaces.OnAlbumItemClickListener
            public void onItemClick(int i2, LocalMediaFolder localMediaFolder) {
                PictureSelectorFragment pictureSelectorFragment = PictureSelectorFragment.this;
                pictureSelectorFragment.isDisplayCamera = ((PictureCommonFragment) pictureSelectorFragment).f19027d.isDisplayCamera && localMediaFolder.getBucketId() == -1;
                PictureSelectorFragment.this.mAdapter.setDisplayCamera(PictureSelectorFragment.this.isDisplayCamera);
                PictureSelectorFragment.this.titleBar.setTitle(localMediaFolder.getFolderName());
                LocalMediaFolder localMediaFolder2 = ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.currentLocalMediaFolder;
                long bucketId = localMediaFolder2.getBucketId();
                if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.isPageStrategy) {
                    if (localMediaFolder.getBucketId() != bucketId) {
                        localMediaFolder2.setData(PictureSelectorFragment.this.mAdapter.getData());
                        localMediaFolder2.setCurrentDataPage(((PictureCommonFragment) PictureSelectorFragment.this).f19025b);
                        localMediaFolder2.setHasMore(PictureSelectorFragment.this.mRecycler.isEnabledLoadMore());
                        if (localMediaFolder.getData().size() <= 0 || localMediaFolder.isHasMore()) {
                            ((PictureCommonFragment) PictureSelectorFragment.this).f19025b = 1;
                            if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.loaderDataEngine != null) {
                                ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.loaderDataEngine.loadFirstPageMediaData(PictureSelectorFragment.this.getContext(), localMediaFolder.getBucketId(), ((PictureCommonFragment) PictureSelectorFragment.this).f19025b, ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.7.1
                                    @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                                    public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                                        PictureSelectorFragment.this.handleSwitchAlbum(arrayList, z2);
                                    }
                                });
                            } else {
                                ((PictureCommonFragment) PictureSelectorFragment.this).f19026c.loadPageMediaData(localMediaFolder.getBucketId(), ((PictureCommonFragment) PictureSelectorFragment.this).f19025b, ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.7.2
                                    @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                                    public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                                        PictureSelectorFragment.this.handleSwitchAlbum(arrayList, z2);
                                    }
                                });
                            }
                        } else {
                            PictureSelectorFragment.this.setAdapterData(localMediaFolder.getData());
                            ((PictureCommonFragment) PictureSelectorFragment.this).f19025b = localMediaFolder.getCurrentDataPage();
                            PictureSelectorFragment.this.mRecycler.setEnabledLoadMore(localMediaFolder.isHasMore());
                            PictureSelectorFragment.this.mRecycler.smoothScrollToPosition(0);
                        }
                    }
                } else if (localMediaFolder.getBucketId() != bucketId) {
                    PictureSelectorFragment.this.setAdapterData(localMediaFolder.getData());
                    PictureSelectorFragment.this.mRecycler.smoothScrollToPosition(0);
                }
                ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.currentLocalMediaFolder = localMediaFolder;
                PictureSelectorFragment.this.albumListPopWindow.dismiss();
                if (PictureSelectorFragment.this.mDragSelectTouchListener == null || !((PictureCommonFragment) PictureSelectorFragment.this).f19027d.isFastSlidingSelect) {
                    return;
                }
                PictureSelectorFragment.this.mDragSelectTouchListener.setRecyclerViewHeaderCount(PictureSelectorFragment.this.mAdapter.isDisplayCamera() ? 1 : 0);
            }
        });
    }

    private void addRecyclerAction() {
        this.mAdapter.setOnItemClickListener(new PictureImageGridAdapter.OnItemClickListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.16
            @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnItemClickListener
            public void onItemClick(View view, int i2, LocalMedia localMedia) {
                if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.selectionMode != 1 || !((PictureCommonFragment) PictureSelectorFragment.this).f19027d.isDirectReturnSingle) {
                    if (DoubleUtils.isFastDoubleClick()) {
                        return;
                    }
                    PictureSelectorFragment.this.onStartPreview(i2, false);
                } else {
                    ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.selectedResult.clear();
                    if (PictureSelectorFragment.this.confirmSelect(localMedia, false) == 0) {
                        PictureSelectorFragment.this.h();
                    }
                }
            }

            @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnItemClickListener
            public void onItemLongClick(View view, int i2) {
                if (PictureSelectorFragment.this.mDragSelectTouchListener == null || !((PictureCommonFragment) PictureSelectorFragment.this).f19027d.isFastSlidingSelect) {
                    return;
                }
                ((Vibrator) PictureSelectorFragment.this.getActivity().getSystemService("vibrator")).vibrate(50L);
                PictureSelectorFragment.this.mDragSelectTouchListener.startSlideSelection(i2);
            }

            @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnItemClickListener
            public int onSelected(View view, int i2, LocalMedia localMedia) throws Resources.NotFoundException {
                int iConfirmSelect = PictureSelectorFragment.this.confirmSelect(localMedia, view.isSelected());
                if (iConfirmSelect == 0) {
                    if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.onSelectAnimListener != null) {
                        long jOnSelectAnim = ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.onSelectAnimListener.onSelectAnim(view);
                        if (jOnSelectAnim > 0) {
                            int unused = PictureSelectorFragment.SELECT_ANIM_DURATION = (int) jOnSelectAnim;
                        }
                    } else {
                        Animation animationLoadAnimation = AnimationUtils.loadAnimation(PictureSelectorFragment.this.getContext(), R.anim.ps_anim_modal_in);
                        int unused2 = PictureSelectorFragment.SELECT_ANIM_DURATION = (int) animationLoadAnimation.getDuration();
                        view.startAnimation(animationLoadAnimation);
                    }
                }
                return iConfirmSelect;
            }

            @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnItemClickListener
            public void openCameraClick() {
                if (DoubleUtils.isFastDoubleClick()) {
                    return;
                }
                PictureSelectorFragment.this.openSelectedCamera();
            }
        });
        this.mRecycler.setOnRecyclerViewScrollStateListener(new OnRecyclerViewScrollStateListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.17
            @Override // com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener
            public void onScrollFast() {
                if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.imageEngine != null) {
                    ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.imageEngine.pauseRequests(PictureSelectorFragment.this.getContext());
                }
            }

            @Override // com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener
            public void onScrollSlow() {
                if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.imageEngine != null) {
                    ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.imageEngine.resumeRequests(PictureSelectorFragment.this.getContext());
                }
            }
        });
        this.mRecycler.setOnRecyclerViewScrollListener(new OnRecyclerViewScrollListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.18
            @Override // com.luck.picture.lib.interfaces.OnRecyclerViewScrollListener
            public void onScrollStateChanged(int i2) {
                if (i2 == 1) {
                    PictureSelectorFragment.this.showCurrentMediaCreateTimeUI();
                } else if (i2 == 0) {
                    PictureSelectorFragment.this.hideCurrentMediaCreateTimeUI();
                }
            }

            @Override // com.luck.picture.lib.interfaces.OnRecyclerViewScrollListener
            public void onScrolled(int i2, int i3) {
                PictureSelectorFragment.this.setCurrentMediaCreateTimeText();
            }
        });
        if (this.f19027d.isFastSlidingSelect) {
            final HashSet hashSet = new HashSet();
            SlideSelectTouchListener slideSelectTouchListenerWithSelectListener = new SlideSelectTouchListener().setRecyclerViewHeaderCount(this.mAdapter.isDisplayCamera() ? 1 : 0).withSelectListener(new SlideSelectionHandler(new SlideSelectionHandler.ISelectionHandler() { // from class: com.luck.picture.lib.PictureSelectorFragment.19
                @Override // com.luck.picture.lib.widget.SlideSelectionHandler.ISelectionHandler
                public void changeSelection(int i2, int i3, boolean z2, boolean z3) {
                    ArrayList<LocalMedia> data = PictureSelectorFragment.this.mAdapter.getData();
                    if (data.size() == 0 || i2 > data.size()) {
                        return;
                    }
                    LocalMedia localMedia = data.get(i2);
                    PictureSelectorFragment pictureSelectorFragment = PictureSelectorFragment.this;
                    PictureSelectorFragment.this.mDragSelectTouchListener.setActive(pictureSelectorFragment.confirmSelect(localMedia, ((PictureCommonFragment) pictureSelectorFragment).f19027d.getSelectedResult().contains(localMedia)) != -1);
                }

                @Override // com.luck.picture.lib.widget.SlideSelectionHandler.ISelectionHandler
                public HashSet<Integer> getSelection() {
                    for (int i2 = 0; i2 < ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.getSelectCount(); i2++) {
                        hashSet.add(Integer.valueOf(((PictureCommonFragment) PictureSelectorFragment.this).f19027d.getSelectedResult().get(i2).position));
                    }
                    return hashSet;
                }
            }));
            this.mDragSelectTouchListener = slideSelectTouchListenerWithSelectListener;
            this.mRecycler.addOnItemTouchListener(slideSelectTouchListenerWithSelectListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beginLoadData() {
        onPermissionExplainEvent(false, null);
        if (this.f19027d.isOnlySandboxDir) {
            loadOnlyInAppDirectoryAllMediaData();
        } else {
            loadAllAlbumData();
        }
    }

    private boolean checkNotifyStrategy(boolean z2) {
        SelectorConfig selectorConfig = this.f19027d;
        if (!selectorConfig.isMaxSelectEnabledMask) {
            return false;
        }
        if (selectorConfig.isWithVideoImage) {
            if (selectorConfig.selectionMode == 1) {
                return false;
            }
            int selectCount = selectorConfig.getSelectCount();
            SelectorConfig selectorConfig2 = this.f19027d;
            if (selectCount != selectorConfig2.maxSelectNum && (z2 || selectorConfig2.getSelectCount() != this.f19027d.maxSelectNum - 1)) {
                return false;
            }
        } else if (selectorConfig.getSelectCount() != 0 && (!z2 || this.f19027d.getSelectCount() != 1)) {
            if (PictureMimeType.isHasVideo(this.f19027d.getResultFirstMimeType())) {
                SelectorConfig selectorConfig3 = this.f19027d;
                int i2 = selectorConfig3.maxVideoSelectNum;
                if (i2 <= 0) {
                    i2 = selectorConfig3.maxSelectNum;
                }
                if (selectorConfig3.getSelectCount() != i2 && (z2 || this.f19027d.getSelectCount() != i2 - 1)) {
                    return false;
                }
            } else {
                int selectCount2 = this.f19027d.getSelectCount();
                SelectorConfig selectorConfig4 = this.f19027d;
                if (selectCount2 != selectorConfig4.maxSelectNum && (z2 || selectorConfig4.getSelectCount() != this.f19027d.maxSelectNum - 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAllAlbumData(boolean z2, List<LocalMediaFolder> list) {
        LocalMediaFolder localMediaFolder;
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (list.size() <= 0) {
            showDataNull();
            return;
        }
        if (z2) {
            localMediaFolder = list.get(0);
            this.f19027d.currentLocalMediaFolder = localMediaFolder;
        } else {
            localMediaFolder = this.f19027d.currentLocalMediaFolder;
            if (localMediaFolder == null) {
                localMediaFolder = list.get(0);
                this.f19027d.currentLocalMediaFolder = localMediaFolder;
            }
        }
        this.titleBar.setTitle(localMediaFolder.getFolderName());
        this.albumListPopWindow.bindAlbumData(list);
        SelectorConfig selectorConfig = this.f19027d;
        if (!selectorConfig.isPageStrategy) {
            setAdapterData(localMediaFolder.getData());
        } else if (selectorConfig.isPreloadFirst) {
            this.mRecycler.setEnabledLoadMore(true);
        } else {
            loadFirstPageMediaData(localMediaFolder.getBucketId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFirstPageMedia(ArrayList<LocalMedia> arrayList, boolean z2) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        this.mRecycler.setEnabledLoadMore(z2);
        if (this.mRecycler.isEnabledLoadMore() && arrayList.size() == 0) {
            onRecyclerViewPreloadMore();
        } else {
            setAdapterData(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInAppDirAllMedia(LocalMediaFolder localMediaFolder) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        String str = this.f19027d.sandboxDir;
        boolean z2 = localMediaFolder != null;
        this.titleBar.setTitle(z2 ? localMediaFolder.getFolderName() : new File(str).getName());
        if (!z2) {
            showDataNull();
        } else {
            this.f19027d.currentLocalMediaFolder = localMediaFolder;
            setAdapterData(localMediaFolder.getData());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMoreMediaData(List<LocalMedia> list, boolean z2) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        this.mRecycler.setEnabledLoadMore(z2);
        if (this.mRecycler.isEnabledLoadMore()) {
            removePageCameraRepeatData(list);
            if (list.size() > 0) {
                int size = this.mAdapter.getData().size();
                this.mAdapter.getData().addAll(list);
                PictureImageGridAdapter pictureImageGridAdapter = this.mAdapter;
                pictureImageGridAdapter.notifyItemRangeChanged(size, pictureImageGridAdapter.getItemCount());
                hideDataNull();
            } else {
                onRecyclerViewPreloadMore();
            }
            if (list.size() < 10) {
                RecyclerPreloadView recyclerPreloadView = this.mRecycler;
                recyclerPreloadView.onScrolled(recyclerPreloadView.getScrollX(), this.mRecycler.getScrollY());
            }
        }
    }

    private void handleRecoverAlbumData(List<LocalMediaFolder> list) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (list.size() <= 0) {
            showDataNull();
            return;
        }
        LocalMediaFolder localMediaFolder = this.f19027d.currentLocalMediaFolder;
        if (localMediaFolder == null) {
            localMediaFolder = list.get(0);
            this.f19027d.currentLocalMediaFolder = localMediaFolder;
        }
        this.titleBar.setTitle(localMediaFolder.getFolderName());
        this.albumListPopWindow.bindAlbumData(list);
        if (this.f19027d.isPageStrategy) {
            handleFirstPageMedia(new ArrayList<>(this.f19027d.dataSource), true);
        } else {
            setAdapterData(localMediaFolder.getData());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSwitchAlbum(ArrayList<LocalMedia> arrayList, boolean z2) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        this.mRecycler.setEnabledLoadMore(z2);
        if (arrayList.size() == 0) {
            this.mAdapter.getData().clear();
        }
        setAdapterData(arrayList);
        this.mRecycler.onScrolled(0, 0);
        this.mRecycler.smoothScrollToPosition(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideCurrentMediaCreateTimeUI() {
        if (!this.f19027d.isDisplayTimeAxis || this.mAdapter.getData().size() <= 0) {
            return;
        }
        this.tvCurrentDataTime.animate().setDuration(250L).alpha(0.0f).start();
    }

    private void hideDataNull() {
        if (this.tvDataEmpty.getVisibility() == 0) {
            this.tvDataEmpty.setVisibility(8);
        }
    }

    private void initAlbumListPopWindow() {
        AlbumListPopWindow albumListPopWindowBuildPopWindow = AlbumListPopWindow.buildPopWindow(getContext(), this.f19027d);
        this.albumListPopWindow = albumListPopWindowBuildPopWindow;
        albumListPopWindowBuildPopWindow.setOnPopupWindowStatusListener(new AlbumListPopWindow.OnPopupWindowStatusListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.4
            @Override // com.luck.picture.lib.dialog.AlbumListPopWindow.OnPopupWindowStatusListener
            public void onDismissPopupWindow() {
                if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.isOnlySandboxDir) {
                    return;
                }
                AnimUtils.rotateArrow(PictureSelectorFragment.this.titleBar.getImageArrow(), false);
            }

            @Override // com.luck.picture.lib.dialog.AlbumListPopWindow.OnPopupWindowStatusListener
            public void onShowPopupWindow() {
                if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.isOnlySandboxDir) {
                    return;
                }
                AnimUtils.rotateArrow(PictureSelectorFragment.this.titleBar.getImageArrow(), true);
            }
        });
        addAlbumPopWindowAction();
    }

    private void initBottomNavBar() {
        this.bottomNarBar.setBottomNavBarStyle();
        this.bottomNarBar.setOnBottomNavBarListener(new BottomNavBar.OnBottomNavBarListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.8
            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onCheckOriginalChange() {
                PictureSelectorFragment.this.sendSelectedOriginalChangeEvent();
            }

            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onPreview() {
                PictureSelectorFragment.this.onStartPreview(0, true);
            }
        });
        this.bottomNarBar.setSelectedChange();
    }

    private void initComplete() {
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.selectionMode == 1 && selectorConfig.isDirectReturnSingle) {
            selectorConfig.selectorStyle.getTitleBarStyle().setHideCancelButton(false);
            this.titleBar.getTitleCancelView().setVisibility(0);
            this.completeSelectView.setVisibility(8);
            return;
        }
        this.completeSelectView.setCompleteSelectViewStyle();
        this.completeSelectView.setSelectedChange(false);
        if (this.f19027d.selectorStyle.getSelectMainStyle().isCompleteSelectRelativeTop()) {
            if (this.completeSelectView.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ((ConstraintLayout.LayoutParams) this.completeSelectView.getLayoutParams()).topToTop = R.id.title_bar;
                ((ConstraintLayout.LayoutParams) this.completeSelectView.getLayoutParams()).bottomToBottom = R.id.title_bar;
                if (this.f19027d.isPreviewFullScreenMode) {
                    ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.completeSelectView.getLayoutParams())).topMargin = DensityUtil.getStatusBarHeight(getContext());
                }
            } else if ((this.completeSelectView.getLayoutParams() instanceof RelativeLayout.LayoutParams) && this.f19027d.isPreviewFullScreenMode) {
                ((RelativeLayout.LayoutParams) this.completeSelectView.getLayoutParams()).topMargin = DensityUtil.getStatusBarHeight(getContext());
            }
        }
        this.completeSelectView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.isEmptyResultReturn && ((PictureCommonFragment) PictureSelectorFragment.this).f19027d.getSelectCount() == 0) {
                    PictureSelectorFragment.this.o();
                } else {
                    PictureSelectorFragment.this.h();
                }
            }
        });
    }

    private void initRecycler(View view) {
        this.mRecycler = (RecyclerPreloadView) view.findViewById(R.id.recycler);
        SelectMainStyle selectMainStyle = this.f19027d.selectorStyle.getSelectMainStyle();
        int mainListBackgroundColor = selectMainStyle.getMainListBackgroundColor();
        if (StyleUtils.checkStyleValidity(mainListBackgroundColor)) {
            this.mRecycler.setBackgroundColor(mainListBackgroundColor);
        } else {
            this.mRecycler.setBackgroundColor(ContextCompat.getColor(i(), R.color.ps_color_black));
        }
        int i2 = this.f19027d.imageSpanCount;
        if (i2 <= 0) {
            i2 = 4;
        }
        if (this.mRecycler.getItemDecorationCount() == 0) {
            if (StyleUtils.checkSizeValidity(selectMainStyle.getAdapterItemSpacingSize())) {
                this.mRecycler.addItemDecoration(new GridSpacingItemDecoration(i2, selectMainStyle.getAdapterItemSpacingSize(), selectMainStyle.isAdapterItemIncludeEdge()));
            } else {
                this.mRecycler.addItemDecoration(new GridSpacingItemDecoration(i2, DensityUtil.dip2px(view.getContext(), 1.0f), selectMainStyle.isAdapterItemIncludeEdge()));
            }
        }
        this.mRecycler.setLayoutManager(new GridLayoutManager(getContext(), i2));
        RecyclerView.ItemAnimator itemAnimator = this.mRecycler.getItemAnimator();
        if (itemAnimator != null) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
            this.mRecycler.setItemAnimator(null);
        }
        if (this.f19027d.isPageStrategy) {
            this.mRecycler.setReachBottomRow(2);
            this.mRecycler.setOnRecyclerViewPreloadListener(this);
        } else {
            this.mRecycler.setHasFixedSize(true);
        }
        PictureImageGridAdapter pictureImageGridAdapter = new PictureImageGridAdapter(getContext(), this.f19027d);
        this.mAdapter = pictureImageGridAdapter;
        pictureImageGridAdapter.setDisplayCamera(this.isDisplayCamera);
        int i3 = this.f19027d.animationMode;
        if (i3 == 1) {
            this.mRecycler.setAdapter(new AlphaInAnimationAdapter(this.mAdapter));
        } else if (i3 != 2) {
            this.mRecycler.setAdapter(this.mAdapter);
        } else {
            this.mRecycler.setAdapter(new SlideInBottomAnimationAdapter(this.mAdapter));
        }
        addRecyclerAction();
    }

    private void initTitleBar() {
        if (this.f19027d.selectorStyle.getTitleBarStyle().isHideTitleBar()) {
            this.titleBar.setVisibility(8);
        }
        this.titleBar.setTitleBarStyle();
        this.titleBar.setOnTitleBarListener(new TitleBar.OnTitleBarListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.3
            @Override // com.luck.picture.lib.widget.TitleBar.OnTitleBarListener
            public void onBackPressed() {
                if (PictureSelectorFragment.this.albumListPopWindow.isShowing()) {
                    PictureSelectorFragment.this.albumListPopWindow.dismiss();
                } else {
                    PictureSelectorFragment.this.onKeyBackFragmentFinish();
                }
            }

            @Override // com.luck.picture.lib.widget.TitleBar.OnTitleBarListener
            public void onShowAlbumPopWindow(View view) {
                PictureSelectorFragment.this.albumListPopWindow.showAsDropDown(view);
            }

            @Override // com.luck.picture.lib.widget.TitleBar.OnTitleBarListener
            public void onTitleDoubleClick() {
                if (((PictureCommonFragment) PictureSelectorFragment.this).f19027d.isAutomaticTitleRecyclerTop) {
                    if (SystemClock.uptimeMillis() - PictureSelectorFragment.this.intervalClickTime < 500 && PictureSelectorFragment.this.mAdapter.getItemCount() > 0) {
                        PictureSelectorFragment.this.mRecycler.scrollToPosition(0);
                    } else {
                        PictureSelectorFragment.this.intervalClickTime = SystemClock.uptimeMillis();
                    }
                }
            }
        });
    }

    private boolean isAddSameImp(int i2) {
        int i3;
        return i2 != 0 && (i3 = this.allFolderSize) > 0 && i3 < i2;
    }

    private void mergeFolder(LocalMedia localMedia) {
        LocalMediaFolder folder;
        LocalMediaFolder localMediaFolder;
        String string;
        List<LocalMediaFolder> albumList = this.albumListPopWindow.getAlbumList();
        if (this.albumListPopWindow.getFolderCount() == 0) {
            folder = new LocalMediaFolder();
            if (TextUtils.isEmpty(this.f19027d.defaultAlbumName)) {
                string = getString(this.f19027d.chooseMode == SelectMimeType.ofAudio() ? R.string.ps_all_audio : R.string.ps_camera_roll);
            } else {
                string = this.f19027d.defaultAlbumName;
            }
            folder.setFolderName(string);
            folder.setFirstImagePath("");
            folder.setBucketId(-1L);
            albumList.add(0, folder);
        } else {
            folder = this.albumListPopWindow.getFolder(0);
        }
        folder.setFirstImagePath(localMedia.getPath());
        folder.setFirstMimeType(localMedia.getMimeType());
        folder.setData(this.mAdapter.getData());
        folder.setBucketId(-1L);
        folder.setFolderTotalNum(isAddSameImp(folder.getFolderTotalNum()) ? folder.getFolderTotalNum() : folder.getFolderTotalNum() + 1);
        LocalMediaFolder localMediaFolder2 = this.f19027d.currentLocalMediaFolder;
        if (localMediaFolder2 == null || localMediaFolder2.getFolderTotalNum() == 0) {
            this.f19027d.currentLocalMediaFolder = folder;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= albumList.size()) {
                localMediaFolder = null;
                break;
            }
            localMediaFolder = albumList.get(i2);
            if (TextUtils.equals(localMediaFolder.getFolderName(), localMedia.getParentFolderName())) {
                break;
            } else {
                i2++;
            }
        }
        if (localMediaFolder == null) {
            localMediaFolder = new LocalMediaFolder();
            albumList.add(localMediaFolder);
        }
        localMediaFolder.setFolderName(localMedia.getParentFolderName());
        if (localMediaFolder.getBucketId() == -1 || localMediaFolder.getBucketId() == 0) {
            localMediaFolder.setBucketId(localMedia.getBucketId());
        }
        if (this.f19027d.isPageStrategy) {
            localMediaFolder.setHasMore(true);
        } else if (!isAddSameImp(folder.getFolderTotalNum()) || !TextUtils.isEmpty(this.f19027d.outPutCameraDir) || !TextUtils.isEmpty(this.f19027d.outPutAudioDir)) {
            localMediaFolder.getData().add(0, localMedia);
        }
        localMediaFolder.setFolderTotalNum(isAddSameImp(folder.getFolderTotalNum()) ? localMediaFolder.getFolderTotalNum() : localMediaFolder.getFolderTotalNum() + 1);
        localMediaFolder.setFirstImagePath(this.f19027d.cameraPath);
        localMediaFolder.setFirstMimeType(localMedia.getMimeType());
        this.albumListPopWindow.bindAlbumData(albumList);
    }

    public static PictureSelectorFragment newInstance() {
        PictureSelectorFragment pictureSelectorFragment = new PictureSelectorFragment();
        pictureSelectorFragment.setArguments(new Bundle());
        return pictureSelectorFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0095  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onStartPreview(int r13, boolean r14) {
        /*
            r12 = this;
            androidx.fragment.app.FragmentActivity r0 = r12.getActivity()
            java.lang.String r10 = com.luck.picture.lib.PictureSelectorPreviewFragment.TAG
            boolean r0 = com.luck.picture.lib.utils.ActivityCompatHelper.checkFragmentNonExits(r0, r10)
            if (r0 == 0) goto Lbe
            r0 = 0
            if (r14 == 0) goto L24
            java.util.ArrayList r1 = new java.util.ArrayList
            com.luck.picture.lib.config.SelectorConfig r2 = r12.f19027d
            java.util.ArrayList r2 = r2.getSelectedResult()
            r1.<init>(r2)
            int r2 = r1.size()
            r3 = 0
        L20:
            r9 = r1
            r5 = r2
            r7 = r3
            goto L59
        L24:
            java.util.ArrayList r1 = new java.util.ArrayList
            com.luck.picture.lib.adapter.PictureImageGridAdapter r2 = r12.mAdapter
            java.util.ArrayList r2 = r2.getData()
            r1.<init>(r2)
            com.luck.picture.lib.config.SelectorConfig r2 = r12.f19027d
            com.luck.picture.lib.entity.LocalMediaFolder r2 = r2.currentLocalMediaFolder
            if (r2 == 0) goto L41
            int r3 = r2.getFolderTotalNum()
            long r4 = r2.getBucketId()
            r9 = r1
            r7 = r4
            r5 = r3
            goto L59
        L41:
            int r2 = r1.size()
            int r3 = r1.size()
            if (r3 <= 0) goto L56
            java.lang.Object r3 = r1.get(r0)
            com.luck.picture.lib.entity.LocalMedia r3 = (com.luck.picture.lib.entity.LocalMedia) r3
            long r3 = r3.getBucketId()
            goto L20
        L56:
            r3 = -1
            goto L20
        L59:
            if (r14 != 0) goto L73
            com.luck.picture.lib.config.SelectorConfig r1 = r12.f19027d
            boolean r2 = r1.isPreviewZoomEffect
            if (r2 == 0) goto L73
            com.luck.picture.lib.widget.RecyclerPreloadView r2 = r12.mRecycler
            boolean r1 = r1.isPreviewFullScreenMode
            if (r1 == 0) goto L68
            goto L70
        L68:
            android.content.Context r0 = r12.getContext()
            int r0 = com.luck.picture.lib.utils.DensityUtil.getStatusBarHeight(r0)
        L70:
            com.luck.picture.lib.magical.BuildRecycleItemViewParams.generateViewParams(r2, r0)
        L73:
            com.luck.picture.lib.config.SelectorConfig r0 = r12.f19027d
            com.luck.picture.lib.interfaces.OnPreviewInterceptListener r0 = r0.onPreviewInterceptListener
            if (r0 == 0) goto L95
            android.content.Context r1 = r12.getContext()
            int r4 = r12.f19025b
            com.luck.picture.lib.widget.TitleBar r2 = r12.titleBar
            java.lang.String r10 = r2.getTitleText()
            com.luck.picture.lib.adapter.PictureImageGridAdapter r2 = r12.mAdapter
            boolean r11 = r2.isDisplayCamera()
            r2 = r13
            r3 = r5
            r5 = r7
            r7 = r10
            r8 = r11
            r10 = r14
            r0.onPreview(r1, r2, r3, r4, r5, r7, r8, r9, r10)
            goto Lbe
        L95:
            androidx.fragment.app.FragmentActivity r0 = r12.getActivity()
            boolean r0 = com.luck.picture.lib.utils.ActivityCompatHelper.checkFragmentNonExits(r0, r10)
            if (r0 == 0) goto Lbe
            com.luck.picture.lib.PictureSelectorPreviewFragment r11 = com.luck.picture.lib.PictureSelectorPreviewFragment.newInstance()
            com.luck.picture.lib.widget.TitleBar r0 = r12.titleBar
            java.lang.String r2 = r0.getTitleText()
            com.luck.picture.lib.adapter.PictureImageGridAdapter r0 = r12.mAdapter
            boolean r3 = r0.isDisplayCamera()
            int r6 = r12.f19025b
            r0 = r11
            r1 = r14
            r4 = r13
            r0.setInternalPreviewData(r1, r2, r3, r4, r5, r6, r7, r9)
            androidx.fragment.app.FragmentActivity r0 = r12.getActivity()
            com.luck.picture.lib.basic.FragmentInjectManager.injectFragment(r0, r10, r11)
        Lbe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.PictureSelectorFragment.onStartPreview(int, boolean):void");
    }

    private boolean preloadPageFirstData() {
        Context contextRequireContext;
        int i2;
        SelectorConfig selectorConfig = this.f19027d;
        if (!selectorConfig.isPageStrategy || !selectorConfig.isPreloadFirst) {
            return false;
        }
        LocalMediaFolder localMediaFolder = new LocalMediaFolder();
        localMediaFolder.setBucketId(-1L);
        if (TextUtils.isEmpty(this.f19027d.defaultAlbumName)) {
            TitleBar titleBar = this.titleBar;
            if (this.f19027d.chooseMode == SelectMimeType.ofAudio()) {
                contextRequireContext = requireContext();
                i2 = R.string.ps_all_audio;
            } else {
                contextRequireContext = requireContext();
                i2 = R.string.ps_camera_roll;
            }
            titleBar.setTitle(contextRequireContext.getString(i2));
        } else {
            this.titleBar.setTitle(this.f19027d.defaultAlbumName);
        }
        localMediaFolder.setFolderName(this.titleBar.getTitleText());
        this.f19027d.currentLocalMediaFolder = localMediaFolder;
        loadFirstPageMediaData(localMediaFolder.getBucketId());
        return true;
    }

    private void recoverSaveInstanceData() {
        this.mAdapter.setDisplayCamera(this.isDisplayCamera);
        setEnterAnimationDuration(0L);
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.isOnlySandboxDir) {
            handleInAppDirAllMedia(selectorConfig.currentLocalMediaFolder);
        } else {
            handleRecoverAlbumData(new ArrayList(this.f19027d.albumDataSource));
        }
    }

    private void recoveryRecyclerPosition() {
        if (this.currentPosition > 0) {
            this.mRecycler.post(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorFragment.15
                @Override // java.lang.Runnable
                public void run() {
                    PictureSelectorFragment.this.mRecycler.scrollToPosition(PictureSelectorFragment.this.currentPosition);
                    PictureSelectorFragment.this.mRecycler.setLastVisiblePosition(PictureSelectorFragment.this.currentPosition);
                }
            });
        }
    }

    private void removePageCameraRepeatData(List<LocalMedia> list) {
        try {
            try {
                if (this.f19027d.isPageStrategy && this.isCameraCallback) {
                    synchronized (LOCK) {
                        try {
                            Iterator<LocalMedia> it = list.iterator();
                            while (it.hasNext()) {
                                if (this.mAdapter.getData().contains(it.next())) {
                                    it.remove();
                                }
                            }
                        } finally {
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            this.isCameraCallback = false;
        }
    }

    private void requestLoadData() {
        this.mAdapter.setDisplayCamera(this.isDisplayCamera);
        if (PermissionChecker.isCheckReadStorage(this.f19027d.chooseMode, getContext())) {
            beginLoadData();
            return;
        }
        final String[] readPermissionArray = PermissionConfig.getReadPermissionArray(i(), this.f19027d.chooseMode);
        onPermissionExplainEvent(true, readPermissionArray);
        if (this.f19027d.onPermissionsEventListener != null) {
            onApplyPermissionsEvent(-1, readPermissionArray);
        } else {
            PermissionChecker.getInstance().requestPermissions(this, readPermissionArray, new PermissionResultCallback() { // from class: com.luck.picture.lib.PictureSelectorFragment.5
                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onDenied() {
                    PictureSelectorFragment.this.handlePermissionDenied(readPermissionArray);
                }

                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onGranted() {
                    PictureSelectorFragment.this.beginLoadData();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NotifyDataSetChanged"})
    public void setAdapterData(final ArrayList<LocalMedia> arrayList) {
        long enterAnimationDuration = getEnterAnimationDuration();
        if (enterAnimationDuration > 0) {
            requireView().postDelayed(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorFragment.20
                @Override // java.lang.Runnable
                public void run() {
                    PictureSelectorFragment.this.setAdapterDataComplete(arrayList);
                }
            }, enterAnimationDuration);
        } else {
            setAdapterDataComplete(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdapterDataComplete(ArrayList<LocalMedia> arrayList) {
        setEnterAnimationDuration(0L);
        sendChangeSubSelectPositionEvent(false);
        this.mAdapter.setDataAndDataSetChanged(arrayList);
        this.f19027d.dataSource.clear();
        this.f19027d.albumDataSource.clear();
        recoveryRecyclerPosition();
        if (this.mAdapter.isDataEmpty()) {
            showDataNull();
        } else {
            hideDataNull();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentMediaCreateTimeText() {
        int firstVisiblePosition;
        if (!this.f19027d.isDisplayTimeAxis || (firstVisiblePosition = this.mRecycler.getFirstVisiblePosition()) == -1) {
            return;
        }
        ArrayList<LocalMedia> data = this.mAdapter.getData();
        if (data.size() <= firstVisiblePosition || data.get(firstVisiblePosition).getDateAddedTime() <= 0) {
            return;
        }
        this.tvCurrentDataTime.setText(DateUtils.getDataFormat(getContext(), data.get(firstVisiblePosition).getDateAddedTime()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCurrentMediaCreateTimeUI() {
        if (this.f19027d.isDisplayTimeAxis && this.mAdapter.getData().size() > 0 && this.tvCurrentDataTime.getAlpha() == 0.0f) {
            this.tvCurrentDataTime.animate().setDuration(150L).alphaBy(1.0f).start();
        }
    }

    private void showDataNull() {
        LocalMediaFolder localMediaFolder = this.f19027d.currentLocalMediaFolder;
        if (localMediaFolder == null || localMediaFolder.getBucketId() == -1) {
            if (this.tvDataEmpty.getVisibility() == 8) {
                this.tvDataEmpty.setVisibility(0);
            }
            this.tvDataEmpty.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ps_ic_no_data, 0, 0);
            this.tvDataEmpty.setText(getString(this.f19027d.chooseMode == SelectMimeType.ofAudio() ? R.string.ps_audio_empty : R.string.ps_empty));
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void dispatchCameraMediaResult(LocalMedia localMedia) {
        if (!isAddSameImp(this.albumListPopWindow.getFirstAlbumImageCount())) {
            this.mAdapter.getData().add(0, localMedia);
            this.isCameraCallback = true;
        }
        SelectorConfig selectorConfig = this.f19027d;
        if (selectorConfig.selectionMode == 1 && selectorConfig.isDirectReturnSingle) {
            selectorConfig.selectedResult.clear();
            if (confirmSelect(localMedia, false) == 0) {
                h();
            }
        } else {
            confirmSelect(localMedia, false);
        }
        this.mAdapter.notifyItemInserted(this.f19027d.isDisplayCamera ? 1 : 0);
        PictureImageGridAdapter pictureImageGridAdapter = this.mAdapter;
        boolean z2 = this.f19027d.isDisplayCamera;
        pictureImageGridAdapter.notifyItemRangeChanged(z2 ? 1 : 0, pictureImageGridAdapter.getData().size());
        SelectorConfig selectorConfig2 = this.f19027d;
        if (selectorConfig2.isOnlySandboxDir) {
            LocalMediaFolder localMediaFolder = selectorConfig2.currentLocalMediaFolder;
            if (localMediaFolder == null) {
                localMediaFolder = new LocalMediaFolder();
            }
            localMediaFolder.setBucketId(ValueOf.toLong(Integer.valueOf(localMedia.getParentFolderName().hashCode())));
            localMediaFolder.setFolderName(localMedia.getParentFolderName());
            localMediaFolder.setFirstMimeType(localMedia.getMimeType());
            localMediaFolder.setFirstImagePath(localMedia.getPath());
            localMediaFolder.setFolderTotalNum(this.mAdapter.getData().size());
            localMediaFolder.setCurrentDataPage(this.f19025b);
            localMediaFolder.setHasMore(false);
            localMediaFolder.setData(this.mAdapter.getData());
            this.mRecycler.setEnabledLoadMore(false);
            this.f19027d.currentLocalMediaFolder = localMediaFolder;
        } else {
            mergeFolder(localMedia);
        }
        this.allFolderSize = 0;
        if (this.mAdapter.getData().size() > 0 || this.f19027d.isDirectReturnSingle) {
            hideDataNull();
        } else {
            showDataNull();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment
    public String getFragmentTag() {
        return TAG;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public int getResourceId() {
        int layoutResource = InjectResourceSource.getLayoutResource(getContext(), 1, this.f19027d);
        return layoutResource != 0 ? layoutResource : R.layout.ps_fragment_selector;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void handlePermissionSettingResult(String[] strArr) {
        if (strArr == null) {
            return;
        }
        onPermissionExplainEvent(false, null);
        boolean z2 = strArr.length > 0 && TextUtils.equals(strArr[0], PermissionConfig.CAMERA[0]);
        OnPermissionsInterceptListener onPermissionsInterceptListener = this.f19027d.onPermissionsEventListener;
        if (onPermissionsInterceptListener != null ? onPermissionsInterceptListener.hasPermissions(this, strArr) : PermissionChecker.isCheckSelfPermission(getContext(), strArr)) {
            if (z2) {
                openSelectedCamera();
            } else {
                beginLoadData();
            }
        } else if (z2) {
            ToastUtils.showToast(getContext(), getString(R.string.ps_camera));
        } else {
            ToastUtils.showToast(getContext(), getString(R.string.ps_jurisdiction));
            onKeyBackFragmentFinish();
        }
        PermissionConfig.CURRENT_REQUEST_PERMISSION = new String[0];
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorEvent
    public void loadAllAlbumData() {
        ExtendLoaderEngine extendLoaderEngine = this.f19027d.loaderDataEngine;
        if (extendLoaderEngine != null) {
            extendLoaderEngine.loadAllAlbumData(getContext(), new OnQueryAllAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorFragment.9
                @Override // com.luck.picture.lib.interfaces.OnQueryAllAlbumListener
                public void onComplete(List<LocalMediaFolder> list) {
                    PictureSelectorFragment.this.handleAllAlbumData(false, list);
                }
            });
        } else {
            final boolean zPreloadPageFirstData = preloadPageFirstData();
            this.f19026c.loadAllAlbum(new OnQueryAllAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorFragment.10
                @Override // com.luck.picture.lib.interfaces.OnQueryAllAlbumListener
                public void onComplete(List<LocalMediaFolder> list) {
                    PictureSelectorFragment.this.handleAllAlbumData(zPreloadPageFirstData, list);
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorEvent
    public void loadFirstPageMediaData(long j2) {
        this.f19025b = 1;
        this.mRecycler.setEnabledLoadMore(true);
        SelectorConfig selectorConfig = this.f19027d;
        ExtendLoaderEngine extendLoaderEngine = selectorConfig.loaderDataEngine;
        if (extendLoaderEngine != null) {
            Context context = getContext();
            int i2 = this.f19025b;
            extendLoaderEngine.loadFirstPageMediaData(context, j2, i2, i2 * this.f19027d.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.11
                @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                    PictureSelectorFragment.this.handleFirstPageMedia(arrayList, z2);
                }
            });
        } else {
            IBridgeMediaLoader iBridgeMediaLoader = this.f19026c;
            int i3 = this.f19025b;
            iBridgeMediaLoader.loadPageMediaData(j2, i3, i3 * selectorConfig.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.12
                @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                    PictureSelectorFragment.this.handleFirstPageMedia(arrayList, z2);
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorEvent
    public void loadMoreMediaData() {
        if (this.mRecycler.isEnabledLoadMore()) {
            this.f19025b++;
            LocalMediaFolder localMediaFolder = this.f19027d.currentLocalMediaFolder;
            long bucketId = localMediaFolder != null ? localMediaFolder.getBucketId() : 0L;
            SelectorConfig selectorConfig = this.f19027d;
            ExtendLoaderEngine extendLoaderEngine = selectorConfig.loaderDataEngine;
            if (extendLoaderEngine == null) {
                this.f19026c.loadPageMediaData(bucketId, this.f19025b, selectorConfig.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.23
                    @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                    public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                        PictureSelectorFragment.this.handleMoreMediaData(arrayList, z2);
                    }
                });
                return;
            }
            Context context = getContext();
            int i2 = this.f19025b;
            int i3 = this.f19027d.pageSize;
            extendLoaderEngine.loadMoreMediaData(context, bucketId, i2, i3, i3, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.22
                @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                    PictureSelectorFragment.this.handleMoreMediaData(arrayList, z2);
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorEvent
    public void loadOnlyInAppDirectoryAllMediaData() {
        ExtendLoaderEngine extendLoaderEngine = this.f19027d.loaderDataEngine;
        if (extendLoaderEngine != null) {
            extendLoaderEngine.loadOnlyInAppDirAllMediaData(getContext(), new OnQueryAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorFragment.13
                @Override // com.luck.picture.lib.interfaces.OnQueryAlbumListener
                public void onComplete(LocalMediaFolder localMediaFolder) {
                    PictureSelectorFragment.this.handleInAppDirAllMedia(localMediaFolder);
                }
            });
        } else {
            this.f19026c.loadOnlyInAppDirAllMedia(new OnQueryAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorFragment.14
                @Override // com.luck.picture.lib.interfaces.OnQueryAlbumListener
                public void onComplete(LocalMediaFolder localMediaFolder) {
                    PictureSelectorFragment.this.handleInAppDirAllMedia(localMediaFolder);
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onApplyPermissionsEvent(int i2, String[] strArr) {
        if (i2 != -1) {
            super.onApplyPermissionsEvent(i2, strArr);
        } else {
            this.f19027d.onPermissionsEventListener.requestPermission(this, strArr, new OnRequestPermissionListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.6
                @Override // com.luck.picture.lib.interfaces.OnRequestPermissionListener
                public void onCall(String[] strArr2, boolean z2) {
                    if (z2) {
                        PictureSelectorFragment.this.beginLoadData();
                    } else {
                        PictureSelectorFragment.this.handlePermissionDenied(strArr2);
                    }
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onCheckOriginalChange() {
        this.bottomNarBar.setOriginalCheck();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onCreateLoader() {
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

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        SlideSelectTouchListener slideSelectTouchListener = this.mDragSelectTouchListener;
        if (slideSelectTouchListener != null) {
            slideSelectTouchListener.stopAutoScroll();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onFixedSelectedChange(LocalMedia localMedia) {
        this.mAdapter.notifyItemPositionChanged(localMedia.position);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onFragmentResume() {
        setRootViewKeyListener(requireView());
    }

    @Override // com.luck.picture.lib.interfaces.OnRecyclerViewPreloadMoreListener
    public void onRecyclerViewPreloadMore() {
        if (this.isMemoryRecycling) {
            requireView().postDelayed(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorFragment.21
                @Override // java.lang.Runnable
                public void run() {
                    PictureSelectorFragment.this.loadMoreMediaData();
                }
            }, 350L);
        } else {
            loadMoreMediaData();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(PictureConfig.EXTRA_ALL_FOLDER_SIZE, this.allFolderSize);
        bundle.putInt(PictureConfig.EXTRA_CURRENT_PAGE, this.f19025b);
        RecyclerPreloadView recyclerPreloadView = this.mRecycler;
        if (recyclerPreloadView != null) {
            bundle.putInt(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, recyclerPreloadView.getLastVisiblePosition());
        }
        PictureImageGridAdapter pictureImageGridAdapter = this.mAdapter;
        if (pictureImageGridAdapter != null) {
            bundle.putBoolean(PictureConfig.EXTRA_DISPLAY_CAMERA, pictureImageGridAdapter.isDisplayCamera());
            this.f19027d.addDataSource(this.mAdapter.getData());
        }
        AlbumListPopWindow albumListPopWindow = this.albumListPopWindow;
        if (albumListPopWindow != null) {
            this.f19027d.addAlbumDataSource(albumListPopWindow.getAlbumList());
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    @SuppressLint({"NotifyDataSetChanged"})
    public void onSelectedChange(boolean z2, LocalMedia localMedia) {
        this.bottomNarBar.setSelectedChange();
        this.completeSelectView.setSelectedChange(false);
        if (checkNotifyStrategy(z2)) {
            this.mAdapter.notifyItemPositionChanged(localMedia.position);
            this.mRecycler.postDelayed(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorFragment.1
                @Override // java.lang.Runnable
                public void run() {
                    PictureSelectorFragment.this.mAdapter.notifyDataSetChanged();
                }
            }, SELECT_ANIM_DURATION);
        } else {
            this.mAdapter.notifyItemPositionChanged(localMedia.position);
        }
        if (z2) {
            return;
        }
        sendChangeSubSelectPositionEvent(true);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        reStartSavedInstance(bundle);
        this.isMemoryRecycling = bundle != null;
        this.tvDataEmpty = (TextView) view.findViewById(R.id.tv_data_empty);
        this.completeSelectView = (CompleteSelectView) view.findViewById(R.id.ps_complete_select);
        this.titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        this.bottomNarBar = (BottomNavBar) view.findViewById(R.id.bottom_nar_bar);
        this.tvCurrentDataTime = (TextView) view.findViewById(R.id.tv_current_data_time);
        onCreateLoader();
        initAlbumListPopWindow();
        initTitleBar();
        initComplete();
        initRecycler(view);
        initBottomNavBar();
        if (this.isMemoryRecycling) {
            recoverSaveInstanceData();
        } else {
            requestLoadData();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void reStartSavedInstance(Bundle bundle) {
        if (bundle == null) {
            this.isDisplayCamera = this.f19027d.isDisplayCamera;
            return;
        }
        this.allFolderSize = bundle.getInt(PictureConfig.EXTRA_ALL_FOLDER_SIZE);
        this.f19025b = bundle.getInt(PictureConfig.EXTRA_CURRENT_PAGE, this.f19025b);
        this.currentPosition = bundle.getInt(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, this.currentPosition);
        this.isDisplayCamera = bundle.getBoolean(PictureConfig.EXTRA_DISPLAY_CAMERA, this.f19027d.isDisplayCamera);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendChangeSubSelectPositionEvent(boolean z2) {
        if (this.f19027d.selectorStyle.getSelectMainStyle().isSelectNumberStyle()) {
            int i2 = 0;
            while (i2 < this.f19027d.getSelectCount()) {
                LocalMedia localMedia = this.f19027d.getSelectedResult().get(i2);
                i2++;
                localMedia.setNum(i2);
                if (z2) {
                    this.mAdapter.notifyItemPositionChanged(localMedia.position);
                }
            }
        }
    }
}
