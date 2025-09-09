package com.luck.picture.lib.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnAlbumItemClickListener;
import com.luck.picture.lib.style.AlbumWindowStyle;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PictureAlbumAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<LocalMediaFolder> albumList;
    private OnAlbumItemClickListener onAlbumItemClickListener;
    private final SelectorConfig selectorConfig;

    public class ViewHolder extends RecyclerView.ViewHolder {

        /* renamed from: a, reason: collision with root package name */
        ImageView f18969a;

        /* renamed from: b, reason: collision with root package name */
        TextView f18970b;

        /* renamed from: c, reason: collision with root package name */
        TextView f18971c;

        public ViewHolder(View view) {
            super(view);
            this.f18969a = (ImageView) view.findViewById(R.id.first_image);
            this.f18970b = (TextView) view.findViewById(R.id.tv_folder_name);
            this.f18971c = (TextView) view.findViewById(R.id.tv_select_tag);
            AlbumWindowStyle albumWindowStyle = PictureAlbumAdapter.this.selectorConfig.selectorStyle.getAlbumWindowStyle();
            int albumAdapterItemBackground = albumWindowStyle.getAlbumAdapterItemBackground();
            if (albumAdapterItemBackground != 0) {
                view.setBackgroundResource(albumAdapterItemBackground);
            }
            int albumAdapterItemSelectStyle = albumWindowStyle.getAlbumAdapterItemSelectStyle();
            if (albumAdapterItemSelectStyle != 0) {
                this.f18971c.setBackgroundResource(albumAdapterItemSelectStyle);
            }
            int albumAdapterItemTitleColor = albumWindowStyle.getAlbumAdapterItemTitleColor();
            if (albumAdapterItemTitleColor != 0) {
                this.f18970b.setTextColor(albumAdapterItemTitleColor);
            }
            int albumAdapterItemTitleSize = albumWindowStyle.getAlbumAdapterItemTitleSize();
            if (albumAdapterItemTitleSize > 0) {
                this.f18970b.setTextSize(albumAdapterItemTitleSize);
            }
        }
    }

    public PictureAlbumAdapter(SelectorConfig selectorConfig) {
        this.selectorConfig = selectorConfig;
    }

    public void bindAlbumData(List<LocalMediaFolder> list) {
        this.albumList = new ArrayList(list);
    }

    public List<LocalMediaFolder> getAlbumList() {
        List<LocalMediaFolder> list = this.albumList;
        return list != null ? list : new ArrayList();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.albumList.size();
    }

    public void setOnIBridgeAlbumWidget(OnAlbumItemClickListener onAlbumItemClickListener) {
        this.onAlbumItemClickListener = onAlbumItemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"NotifyDataSetChanged"})
    public void onBindViewHolder(ViewHolder viewHolder, final int i2) {
        final LocalMediaFolder localMediaFolder = this.albumList.get(i2);
        String folderName = localMediaFolder.getFolderName();
        int folderTotalNum = localMediaFolder.getFolderTotalNum();
        String firstImagePath = localMediaFolder.getFirstImagePath();
        viewHolder.f18971c.setVisibility(localMediaFolder.isSelectTag() ? 0 : 4);
        LocalMediaFolder localMediaFolder2 = this.selectorConfig.currentLocalMediaFolder;
        viewHolder.itemView.setSelected(localMediaFolder2 != null && localMediaFolder.getBucketId() == localMediaFolder2.getBucketId());
        if (PictureMimeType.isHasAudio(localMediaFolder.getFirstMimeType())) {
            viewHolder.f18969a.setImageResource(R.drawable.ps_audio_placeholder);
        } else {
            ImageEngine imageEngine = this.selectorConfig.imageEngine;
            if (imageEngine != null) {
                imageEngine.loadAlbumCover(viewHolder.itemView.getContext(), firstImagePath, viewHolder.f18969a);
            }
        }
        viewHolder.f18970b.setText(viewHolder.itemView.getContext().getString(R.string.ps_camera_roll_num, folderName, Integer.valueOf(folderTotalNum)));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.PictureAlbumAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PictureAlbumAdapter.this.onAlbumItemClickListener == null) {
                    return;
                }
                PictureAlbumAdapter.this.onAlbumItemClickListener.onItemClick(i2, localMediaFolder);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        int layoutResource = InjectResourceSource.getLayoutResource(viewGroup.getContext(), 6, this.selectorConfig);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        if (layoutResource == 0) {
            layoutResource = R.layout.ps_album_folder_item;
        }
        return new ViewHolder(layoutInflaterFrom.inflate(layoutResource, viewGroup, false));
    }
}
