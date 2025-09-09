package com.luck.picture.lib.adapter.holder;

import android.content.Context;
import android.graphics.ColorFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.adapter.PictureImageGridAdapter;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnGridItemSelectAnimListener;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.AnimUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.luck.picture.lib.utils.ValueOf;

/* loaded from: classes4.dex */
public class BaseRecyclerMediaHolder extends RecyclerView.ViewHolder {
    public View btnCheck;
    private ColorFilter defaultColorFilter;
    public boolean isHandleMask;
    public boolean isSelectNumberStyle;
    public ImageView ivPicture;
    private PictureImageGridAdapter.OnItemClickListener listener;
    public Context mContext;
    private ColorFilter maskWhiteColorFilter;
    private ColorFilter selectColorFilter;
    public SelectorConfig selectorConfig;
    public TextView tvCheck;

    public BaseRecyclerMediaHolder(@NonNull View view) {
        super(view);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x005c, code lost:
    
        if (com.luck.picture.lib.config.PictureMimeType.isHasImage(r5.getMimeType()) == false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0076, code lost:
    
        if (com.luck.picture.lib.config.PictureMimeType.isHasVideo(r5.getMimeType()) == false) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void dispatchHandleMask(com.luck.picture.lib.entity.LocalMedia r5) {
        /*
            r4 = this;
            com.luck.picture.lib.config.SelectorConfig r0 = r4.selectorConfig
            int r0 = r0.getSelectCount()
            if (r0 <= 0) goto L83
            com.luck.picture.lib.config.SelectorConfig r0 = r4.selectorConfig
            java.util.ArrayList r0 = r0.getSelectedResult()
            boolean r0 = r0.contains(r5)
            if (r0 != 0) goto L83
            com.luck.picture.lib.config.SelectorConfig r0 = r4.selectorConfig
            boolean r1 = r0.isWithVideoImage
            r2 = 2147483647(0x7fffffff, float:NaN)
            r3 = 1
            if (r1 == 0) goto L34
            int r1 = r0.selectionMode
            if (r1 != r3) goto L29
            int r0 = r0.getSelectCount()
            if (r0 != r2) goto L83
            goto L78
        L29:
            int r0 = r0.getSelectCount()
            com.luck.picture.lib.config.SelectorConfig r1 = r4.selectorConfig
            int r1 = r1.maxSelectNum
            if (r0 != r1) goto L83
            goto L78
        L34:
            java.lang.String r0 = r0.getResultFirstMimeType()
            boolean r0 = com.luck.picture.lib.config.PictureMimeType.isHasVideo(r0)
            if (r0 == 0) goto L5f
            com.luck.picture.lib.config.SelectorConfig r0 = r4.selectorConfig
            int r1 = r0.selectionMode
            if (r1 != r3) goto L45
            goto L4e
        L45:
            int r1 = r0.maxVideoSelectNum
            if (r1 <= 0) goto L4b
        L49:
            r2 = r1
            goto L4e
        L4b:
            int r1 = r0.maxSelectNum
            goto L49
        L4e:
            int r0 = r0.getSelectCount()
            if (r0 == r2) goto L78
            java.lang.String r0 = r5.getMimeType()
            boolean r0 = com.luck.picture.lib.config.PictureMimeType.isHasImage(r0)
            if (r0 == 0) goto L83
            goto L78
        L5f:
            com.luck.picture.lib.config.SelectorConfig r0 = r4.selectorConfig
            int r1 = r0.selectionMode
            if (r1 != r3) goto L66
            goto L68
        L66:
            int r2 = r0.maxSelectNum
        L68:
            int r0 = r0.getSelectCount()
            if (r0 == r2) goto L78
            java.lang.String r0 = r5.getMimeType()
            boolean r0 = com.luck.picture.lib.config.PictureMimeType.isHasVideo(r0)
            if (r0 == 0) goto L83
        L78:
            android.widget.ImageView r0 = r4.ivPicture
            android.graphics.ColorFilter r1 = r4.maskWhiteColorFilter
            r0.setColorFilter(r1)
            r5.setMaxSelectEnabledMask(r3)
            goto L87
        L83:
            r0 = 0
            r5.setMaxSelectEnabledMask(r0)
        L87:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.dispatchHandleMask(com.luck.picture.lib.entity.LocalMedia):void");
    }

    public static BaseRecyclerMediaHolder generate(ViewGroup viewGroup, int i2, int i3, SelectorConfig selectorConfig) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(i3, viewGroup, false);
        return i2 != 1 ? i2 != 3 ? i2 != 4 ? new ImageViewHolder(viewInflate, selectorConfig) : new AudioViewHolder(viewInflate, selectorConfig) : new VideoViewHolder(viewInflate, selectorConfig) : new CameraViewHolder(viewInflate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSelected(LocalMedia localMedia) {
        LocalMedia compareLocalMedia;
        boolean zContains = this.selectorConfig.getSelectedResult().contains(localMedia);
        if (zContains && (compareLocalMedia = localMedia.getCompareLocalMedia()) != null && compareLocalMedia.isEditorImage()) {
            localMedia.setCutPath(compareLocalMedia.getCutPath());
            localMedia.setCut(!TextUtils.isEmpty(compareLocalMedia.getCutPath()));
            localMedia.setEditorImage(compareLocalMedia.isEditorImage());
        }
        return zContains;
    }

    private void notifySelectNumberStyle(LocalMedia localMedia) {
        this.tvCheck.setText("");
        for (int i2 = 0; i2 < this.selectorConfig.getSelectCount(); i2++) {
            LocalMedia localMedia2 = this.selectorConfig.getSelectedResult().get(i2);
            if (TextUtils.equals(localMedia2.getPath(), localMedia.getPath()) || localMedia2.getId() == localMedia.getId()) {
                localMedia.setNum(localMedia2.getNum());
                localMedia2.setPosition(localMedia.getPosition());
                this.tvCheck.setText(ValueOf.toString(Integer.valueOf(localMedia.getNum())));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectedMedia(boolean z2) {
        if (this.tvCheck.isSelected() != z2) {
            this.tvCheck.setSelected(z2);
        }
        if (this.selectorConfig.isDirectReturnSingle) {
            this.ivPicture.setColorFilter(this.defaultColorFilter);
        } else {
            this.ivPicture.setColorFilter(z2 ? this.selectColorFilter : this.defaultColorFilter);
        }
    }

    public void bindData(final LocalMedia localMedia, final int i2) {
        localMedia.position = getAbsoluteAdapterPosition();
        selectedMedia(isSelected(localMedia));
        if (this.isSelectNumberStyle) {
            notifySelectNumberStyle(localMedia);
        }
        if (this.isHandleMask && this.selectorConfig.isMaxSelectEnabledMask) {
            dispatchHandleMask(localMedia);
        }
        String path = localMedia.getPath();
        if (localMedia.isEditorImage()) {
            path = localMedia.getCutPath();
        }
        d(path);
        this.tvCheck.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BaseRecyclerMediaHolder.this.btnCheck.performClick();
            }
        });
        this.btnCheck.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int iOnSelected;
                OnGridItemSelectAnimListener onGridItemSelectAnimListener;
                if (BaseRecyclerMediaHolder.this.listener == null || (iOnSelected = BaseRecyclerMediaHolder.this.listener.onSelected(BaseRecyclerMediaHolder.this.tvCheck, i2, localMedia)) == -1) {
                    return;
                }
                if (iOnSelected == 0) {
                    BaseRecyclerMediaHolder baseRecyclerMediaHolder = BaseRecyclerMediaHolder.this;
                    SelectorConfig selectorConfig = baseRecyclerMediaHolder.selectorConfig;
                    if (selectorConfig.isSelectZoomAnim) {
                        OnGridItemSelectAnimListener onGridItemSelectAnimListener2 = selectorConfig.onItemSelectAnimListener;
                        if (onGridItemSelectAnimListener2 != null) {
                            onGridItemSelectAnimListener2.onSelectItemAnim(baseRecyclerMediaHolder.ivPicture, true);
                        } else {
                            AnimUtils.selectZoom(baseRecyclerMediaHolder.ivPicture);
                        }
                    }
                } else if (iOnSelected == 1) {
                    BaseRecyclerMediaHolder baseRecyclerMediaHolder2 = BaseRecyclerMediaHolder.this;
                    SelectorConfig selectorConfig2 = baseRecyclerMediaHolder2.selectorConfig;
                    if (selectorConfig2.isSelectZoomAnim && (onGridItemSelectAnimListener = selectorConfig2.onItemSelectAnimListener) != null) {
                        onGridItemSelectAnimListener.onSelectItemAnim(baseRecyclerMediaHolder2.ivPicture, false);
                    }
                }
                BaseRecyclerMediaHolder baseRecyclerMediaHolder3 = BaseRecyclerMediaHolder.this;
                baseRecyclerMediaHolder3.selectedMedia(baseRecyclerMediaHolder3.isSelected(localMedia));
            }
        });
        this.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (BaseRecyclerMediaHolder.this.listener == null) {
                    return false;
                }
                BaseRecyclerMediaHolder.this.listener.onItemLongClick(view, i2);
                return false;
            }
        });
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.4
            /* JADX WARN: Code restructure failed: missing block: B:22:0x0054, code lost:
            
                if (r4.selectionMode != 1) goto L24;
             */
            /* JADX WARN: Removed duplicated region for block: B:17:0x003e  */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onClick(android.view.View r4) {
                /*
                    r3 = this;
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.adapter.PictureImageGridAdapter$OnItemClickListener r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.a(r4)
                    if (r4 != 0) goto L9
                    return
                L9:
                    com.luck.picture.lib.entity.LocalMedia r4 = r2
                    java.lang.String r4 = r4.getMimeType()
                    boolean r4 = com.luck.picture.lib.config.PictureMimeType.isHasImage(r4)
                    if (r4 == 0) goto L1d
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.config.SelectorConfig r4 = r4.selectorConfig
                    boolean r4 = r4.isEnablePreviewImage
                    if (r4 != 0) goto L5f
                L1d:
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.config.SelectorConfig r4 = r4.selectorConfig
                    boolean r4 = r4.isDirectReturnSingle
                    if (r4 != 0) goto L5f
                    com.luck.picture.lib.entity.LocalMedia r4 = r2
                    java.lang.String r4 = r4.getMimeType()
                    boolean r4 = com.luck.picture.lib.config.PictureMimeType.isHasVideo(r4)
                    r0 = 1
                    if (r4 == 0) goto L3e
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.config.SelectorConfig r4 = r4.selectorConfig
                    boolean r1 = r4.isEnablePreviewVideo
                    if (r1 != 0) goto L5f
                    int r4 = r4.selectionMode
                    if (r4 == r0) goto L5f
                L3e:
                    com.luck.picture.lib.entity.LocalMedia r4 = r2
                    java.lang.String r4 = r4.getMimeType()
                    boolean r4 = com.luck.picture.lib.config.PictureMimeType.isHasAudio(r4)
                    if (r4 == 0) goto L57
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.config.SelectorConfig r4 = r4.selectorConfig
                    boolean r1 = r4.isEnablePreviewAudio
                    if (r1 != 0) goto L5f
                    int r4 = r4.selectionMode
                    if (r4 != r0) goto L57
                    goto L5f
                L57:
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    android.view.View r4 = r4.btnCheck
                    r4.performClick()
                    goto L79
                L5f:
                    com.luck.picture.lib.entity.LocalMedia r4 = r2
                    boolean r4 = r4.isMaxSelectEnabledMask()
                    if (r4 == 0) goto L68
                    return
                L68:
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.adapter.PictureImageGridAdapter$OnItemClickListener r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.a(r4)
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r0 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    android.widget.TextView r0 = r0.tvCheck
                    int r1 = r3
                    com.luck.picture.lib.entity.LocalMedia r2 = r2
                    r4.onItemClick(r0, r1, r2)
                L79:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.AnonymousClass4.onClick(android.view.View):void");
            }
        });
    }

    protected void d(String str) {
        ImageEngine imageEngine = this.selectorConfig.imageEngine;
        if (imageEngine != null) {
            imageEngine.loadGridImage(this.ivPicture.getContext(), str, this.ivPicture);
        }
    }

    public void setOnItemClickListener(PictureImageGridAdapter.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public BaseRecyclerMediaHolder(@NonNull View view, SelectorConfig selectorConfig) {
        int i2;
        super(view);
        this.selectorConfig = selectorConfig;
        Context context = view.getContext();
        this.mContext = context;
        this.defaultColorFilter = StyleUtils.getColorFilter(context, R.color.ps_color_20);
        this.selectColorFilter = StyleUtils.getColorFilter(this.mContext, R.color.ps_color_80);
        this.maskWhiteColorFilter = StyleUtils.getColorFilter(this.mContext, R.color.ps_color_half_white);
        SelectMainStyle selectMainStyle = this.selectorConfig.selectorStyle.getSelectMainStyle();
        this.isSelectNumberStyle = selectMainStyle.isSelectNumberStyle();
        this.ivPicture = (ImageView) view.findViewById(R.id.ivPicture);
        this.tvCheck = (TextView) view.findViewById(R.id.tvCheck);
        this.btnCheck = view.findViewById(R.id.btnCheck);
        boolean z2 = true;
        if (selectorConfig.selectionMode == 1 && selectorConfig.isDirectReturnSingle) {
            this.tvCheck.setVisibility(8);
            this.btnCheck.setVisibility(8);
        } else {
            this.tvCheck.setVisibility(0);
            this.btnCheck.setVisibility(0);
        }
        if (selectorConfig.isDirectReturnSingle || ((i2 = selectorConfig.selectionMode) != 1 && i2 != 2)) {
            z2 = false;
        }
        this.isHandleMask = z2;
        int adapterSelectTextSize = selectMainStyle.getAdapterSelectTextSize();
        if (StyleUtils.checkSizeValidity(adapterSelectTextSize)) {
            this.tvCheck.setTextSize(adapterSelectTextSize);
        }
        int adapterSelectTextColor = selectMainStyle.getAdapterSelectTextColor();
        if (StyleUtils.checkStyleValidity(adapterSelectTextColor)) {
            this.tvCheck.setTextColor(adapterSelectTextColor);
        }
        int selectBackground = selectMainStyle.getSelectBackground();
        if (StyleUtils.checkStyleValidity(selectBackground)) {
            this.tvCheck.setBackgroundResource(selectBackground);
        }
        int[] adapterSelectStyleGravity = selectMainStyle.getAdapterSelectStyleGravity();
        if (StyleUtils.checkArrayValidity(adapterSelectStyleGravity)) {
            if (this.tvCheck.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.tvCheck.getLayoutParams()).removeRule(21);
                for (int i3 : adapterSelectStyleGravity) {
                    ((RelativeLayout.LayoutParams) this.tvCheck.getLayoutParams()).addRule(i3);
                }
            }
            if (this.btnCheck.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.btnCheck.getLayoutParams()).removeRule(21);
                for (int i4 : adapterSelectStyleGravity) {
                    ((RelativeLayout.LayoutParams) this.btnCheck.getLayoutParams()).addRule(i4);
                }
            }
            int adapterSelectClickArea = selectMainStyle.getAdapterSelectClickArea();
            if (StyleUtils.checkSizeValidity(adapterSelectClickArea)) {
                ViewGroup.LayoutParams layoutParams = this.btnCheck.getLayoutParams();
                layoutParams.width = adapterSelectClickArea;
                layoutParams.height = adapterSelectClickArea;
            }
        }
    }
}
