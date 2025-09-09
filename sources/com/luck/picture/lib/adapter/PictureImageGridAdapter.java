package com.luck.picture.lib.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class PictureImageGridAdapter extends RecyclerView.Adapter<BaseRecyclerMediaHolder> {
    public static final int ADAPTER_TYPE_AUDIO = 4;
    public static final int ADAPTER_TYPE_CAMERA = 1;
    public static final int ADAPTER_TYPE_IMAGE = 2;
    public static final int ADAPTER_TYPE_VIDEO = 3;
    private boolean isDisplayCamera;
    private OnItemClickListener listener;
    private final SelectorConfig mConfig;
    private final Context mContext;
    private ArrayList<LocalMedia> mData = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int i2, LocalMedia localMedia);

        void onItemLongClick(View view, int i2);

        int onSelected(View view, int i2, LocalMedia localMedia);

        void openCameraClick();
    }

    public PictureImageGridAdapter(Context context, SelectorConfig selectorConfig) {
        this.mConfig = selectorConfig;
        this.mContext = context;
    }

    private int getItemResourceId(int i2) {
        if (i2 == 1) {
            return R.layout.ps_item_grid_camera;
        }
        if (i2 == 3) {
            int layoutResource = InjectResourceSource.getLayoutResource(this.mContext, 4, this.mConfig);
            return layoutResource != 0 ? layoutResource : R.layout.ps_item_grid_video;
        }
        if (i2 != 4) {
            int layoutResource2 = InjectResourceSource.getLayoutResource(this.mContext, 3, this.mConfig);
            return layoutResource2 != 0 ? layoutResource2 : R.layout.ps_item_grid_image;
        }
        int layoutResource3 = InjectResourceSource.getLayoutResource(this.mContext, 5, this.mConfig);
        return layoutResource3 != 0 ? layoutResource3 : R.layout.ps_item_grid_audio;
    }

    public ArrayList<LocalMedia> getData() {
        return this.mData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.isDisplayCamera ? this.mData.size() + 1 : this.mData.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        boolean z2 = this.isDisplayCamera;
        if (z2 && i2 == 0) {
            return 1;
        }
        if (z2) {
            i2--;
        }
        String mimeType = this.mData.get(i2).getMimeType();
        if (PictureMimeType.isHasVideo(mimeType)) {
            return 3;
        }
        return PictureMimeType.isHasAudio(mimeType) ? 4 : 2;
    }

    public boolean isDataEmpty() {
        return this.mData.size() == 0;
    }

    public boolean isDisplayCamera() {
        return this.isDisplayCamera;
    }

    public void notifyItemPositionChanged(int i2) {
        notifyItemChanged(i2);
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setDataAndDataSetChanged(ArrayList<LocalMedia> arrayList) {
        if (arrayList != null) {
            this.mData = arrayList;
            notifyDataSetChanged();
        }
    }

    public void setDisplayCamera(boolean z2) {
        this.isDisplayCamera = z2;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseRecyclerMediaHolder baseRecyclerMediaHolder, int i2) {
        if (getItemViewType(i2) == 1) {
            baseRecyclerMediaHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.PictureImageGridAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PictureImageGridAdapter.this.listener != null) {
                        PictureImageGridAdapter.this.listener.openCameraClick();
                    }
                }
            });
            return;
        }
        if (this.isDisplayCamera) {
            i2--;
        }
        baseRecyclerMediaHolder.bindData(this.mData.get(i2), i2);
        baseRecyclerMediaHolder.setOnItemClickListener(this.listener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecyclerMediaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return BaseRecyclerMediaHolder.generate(viewGroup, i2, getItemResourceId(i2), this.mConfig);
    }
}
