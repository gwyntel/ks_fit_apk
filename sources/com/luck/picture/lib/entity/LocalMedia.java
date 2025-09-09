package com.luck.picture.lib.entity;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.obj.pool.ObjectPools;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import java.io.File;

/* loaded from: classes4.dex */
public class LocalMedia implements Parcelable {
    public static final Parcelable.Creator<LocalMedia> CREATOR = new Parcelable.Creator<LocalMedia>() { // from class: com.luck.picture.lib.entity.LocalMedia.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalMedia createFromParcel(Parcel parcel) {
            return new LocalMedia(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalMedia[] newArray(int i2) {
            return new LocalMedia[i2];
        }
    };
    private static ObjectPools.SynchronizedPool<LocalMedia> sPool;
    private long bucketId;
    private int chooseModel;
    private LocalMedia compareLocalMedia;
    private String compressPath;
    private boolean compressed;
    private int cropImageHeight;
    private int cropImageWidth;
    private int cropOffsetX;
    private int cropOffsetY;
    private float cropResultAspectRatio;
    private String customData;
    private String cutPath;
    private long dateAddedTime;
    private long duration;
    private String fileName;
    private int height;
    private long id;
    private boolean isCameraSource;
    private boolean isChecked;
    private boolean isCut;
    private boolean isEditorImage;
    private boolean isGalleryEnabledMask;
    private boolean isMaxSelectEnabledMask;
    private boolean isOriginal;
    private String mimeType;
    private int num;
    private String originalPath;
    private String parentFolderName;
    private String path;
    public int position;
    private String realPath;
    private String sandboxPath;
    private long size;
    private String videoThumbnailPath;
    private String watermarkPath;
    private int width;

    public LocalMedia() {
        this.bucketId = -1L;
    }

    public static LocalMedia create() {
        return new LocalMedia();
    }

    public static void destroyPool() {
        ObjectPools.SynchronizedPool<LocalMedia> synchronizedPool = sPool;
        if (synchronizedPool != null) {
            synchronizedPool.destroy();
            sPool = null;
        }
    }

    public static LocalMedia generateHttpAsLocalMedia(String str) {
        LocalMedia localMediaCreate = create();
        localMediaCreate.setPath(str);
        localMediaCreate.setMimeType(MediaUtils.getMimeTypeFromMediaHttpUrl(str));
        return localMediaCreate;
    }

    public static LocalMedia generateLocalMedia(Context context, String str) {
        LocalMedia localMediaCreate = create();
        File file = PictureMimeType.isContent(str) ? new File(PictureFileUtils.getPath(context, Uri.parse(str))) : new File(str);
        localMediaCreate.setPath(str);
        localMediaCreate.setRealPath(file.getAbsolutePath());
        localMediaCreate.setFileName(file.getName());
        localMediaCreate.setParentFolderName(MediaUtils.generateCameraFolderName(file.getAbsolutePath()));
        localMediaCreate.setMimeType(MediaUtils.getMimeTypeFromMediaUrl(file.getAbsolutePath()));
        localMediaCreate.setSize(file.length());
        localMediaCreate.setDateAddedTime(file.lastModified() / 1000);
        String absolutePath = file.getAbsolutePath();
        if (absolutePath.contains("Android/data/") || absolutePath.contains("data/user/")) {
            localMediaCreate.setId(System.currentTimeMillis());
            localMediaCreate.setBucketId(file.getParentFile() != null ? r1.getName().hashCode() : 0L);
        } else {
            Long[] pathMediaBucketId = MediaUtils.getPathMediaBucketId(context, localMediaCreate.getRealPath());
            localMediaCreate.setId(pathMediaBucketId[0].longValue() == 0 ? System.currentTimeMillis() : pathMediaBucketId[0].longValue());
            localMediaCreate.setBucketId(pathMediaBucketId[1].longValue());
        }
        if (PictureMimeType.isHasVideo(localMediaCreate.getMimeType())) {
            MediaExtraInfo videoSize = MediaUtils.getVideoSize(context, str);
            localMediaCreate.setWidth(videoSize.getWidth());
            localMediaCreate.setHeight(videoSize.getHeight());
            localMediaCreate.setDuration(videoSize.getDuration());
        } else if (PictureMimeType.isHasAudio(localMediaCreate.getMimeType())) {
            localMediaCreate.setDuration(MediaUtils.getAudioSize(context, str).getDuration());
        } else {
            MediaExtraInfo imageSize = MediaUtils.getImageSize(context, str);
            localMediaCreate.setWidth(imageSize.getWidth());
            localMediaCreate.setHeight(imageSize.getHeight());
        }
        return localMediaCreate;
    }

    public static LocalMedia obtain() {
        if (sPool == null) {
            sPool = new ObjectPools.SynchronizedPool<>();
        }
        LocalMedia localMediaAcquire = sPool.acquire();
        return localMediaAcquire == null ? create() : localMediaAcquire;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        boolean z2 = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocalMedia)) {
            return false;
        }
        LocalMedia localMedia = (LocalMedia) obj;
        if (!TextUtils.equals(getPath(), localMedia.getPath()) && !TextUtils.equals(getRealPath(), localMedia.getRealPath()) && getId() != localMedia.getId()) {
            z2 = false;
        }
        if (!z2) {
            localMedia = null;
        }
        this.compareLocalMedia = localMedia;
        return z2;
    }

    public String getAvailablePath() {
        String path = getPath();
        if (isCut()) {
            path = getCutPath();
        }
        if (isCompressed()) {
            path = getCompressPath();
        }
        if (isToSandboxPath()) {
            path = getSandboxPath();
        }
        if (isOriginal()) {
            path = getOriginalPath();
        }
        return isWatermarkPath() ? getWatermarkPath() : path;
    }

    public long getBucketId() {
        return this.bucketId;
    }

    public int getChooseModel() {
        return this.chooseModel;
    }

    public LocalMedia getCompareLocalMedia() {
        return this.compareLocalMedia;
    }

    public String getCompressPath() {
        return this.compressPath;
    }

    public int getCropImageHeight() {
        return this.cropImageHeight;
    }

    public int getCropImageWidth() {
        return this.cropImageWidth;
    }

    public int getCropOffsetX() {
        return this.cropOffsetX;
    }

    public int getCropOffsetY() {
        return this.cropOffsetY;
    }

    public float getCropResultAspectRatio() {
        return this.cropResultAspectRatio;
    }

    public String getCustomData() {
        return this.customData;
    }

    public String getCutPath() {
        return this.cutPath;
    }

    public long getDateAddedTime() {
        return this.dateAddedTime;
    }

    public long getDuration() {
        return this.duration;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getHeight() {
        return this.height;
    }

    public long getId() {
        return this.id;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public int getNum() {
        return this.num;
    }

    public String getOriginalPath() {
        return this.originalPath;
    }

    public String getParentFolderName() {
        return this.parentFolderName;
    }

    public String getPath() {
        return this.path;
    }

    public int getPosition() {
        return this.position;
    }

    public String getRealPath() {
        return this.realPath;
    }

    public String getSandboxPath() {
        return this.sandboxPath;
    }

    public long getSize() {
        return this.size;
    }

    public String getVideoThumbnailPath() {
        return this.videoThumbnailPath;
    }

    public String getWatermarkPath() {
        return this.watermarkPath;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isCameraSource() {
        return this.isCameraSource;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public boolean isCompressed() {
        return this.compressed && !TextUtils.isEmpty(getCompressPath());
    }

    public boolean isCut() {
        return this.isCut && !TextUtils.isEmpty(getCutPath());
    }

    public boolean isEditorImage() {
        return this.isEditorImage && !TextUtils.isEmpty(getCutPath());
    }

    public boolean isGalleryEnabledMask() {
        return this.isGalleryEnabledMask;
    }

    public boolean isMaxSelectEnabledMask() {
        return this.isMaxSelectEnabledMask;
    }

    public boolean isOriginal() {
        return this.isOriginal && !TextUtils.isEmpty(getOriginalPath());
    }

    public boolean isToSandboxPath() {
        return !TextUtils.isEmpty(getSandboxPath());
    }

    public boolean isWatermarkPath() {
        return !TextUtils.isEmpty(getWatermarkPath());
    }

    public void recycle() {
        ObjectPools.SynchronizedPool<LocalMedia> synchronizedPool = sPool;
        if (synchronizedPool != null) {
            synchronizedPool.release(this);
        }
    }

    public void setBucketId(long j2) {
        this.bucketId = j2;
    }

    public void setCameraSource(boolean z2) {
        this.isCameraSource = z2;
    }

    public void setChecked(boolean z2) {
        this.isChecked = z2;
    }

    public void setChooseModel(int i2) {
        this.chooseModel = i2;
    }

    public void setCompressPath(String str) {
        this.compressPath = str;
    }

    public void setCompressed(boolean z2) {
        this.compressed = z2;
    }

    public void setCropImageHeight(int i2) {
        this.cropImageHeight = i2;
    }

    public void setCropImageWidth(int i2) {
        this.cropImageWidth = i2;
    }

    public void setCropOffsetX(int i2) {
        this.cropOffsetX = i2;
    }

    public void setCropOffsetY(int i2) {
        this.cropOffsetY = i2;
    }

    public void setCropResultAspectRatio(float f2) {
        this.cropResultAspectRatio = f2;
    }

    public void setCustomData(String str) {
        this.customData = str;
    }

    public void setCut(boolean z2) {
        this.isCut = z2;
    }

    public void setCutPath(String str) {
        this.cutPath = str;
    }

    public void setDateAddedTime(long j2) {
        this.dateAddedTime = j2;
    }

    public void setDuration(long j2) {
        this.duration = j2;
    }

    public void setEditorImage(boolean z2) {
        this.isEditorImage = z2;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setGalleryEnabledMask(boolean z2) {
        this.isGalleryEnabledMask = z2;
    }

    public void setHeight(int i2) {
        this.height = i2;
    }

    public void setId(long j2) {
        this.id = j2;
    }

    public void setMaxSelectEnabledMask(boolean z2) {
        this.isMaxSelectEnabledMask = z2;
    }

    public void setMimeType(String str) {
        this.mimeType = str;
    }

    public void setNum(int i2) {
        this.num = i2;
    }

    public void setOriginal(boolean z2) {
        this.isOriginal = z2;
    }

    public void setOriginalPath(String str) {
        this.originalPath = str;
    }

    public void setParentFolderName(String str) {
        this.parentFolderName = str;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public void setPosition(int i2) {
        this.position = i2;
    }

    public void setRealPath(String str) {
        this.realPath = str;
    }

    public void setSandboxPath(String str) {
        this.sandboxPath = str;
    }

    public void setSize(long j2) {
        this.size = j2;
    }

    public void setVideoThumbnailPath(String str) {
        this.videoThumbnailPath = str;
    }

    public void setWatermarkPath(String str) {
        this.watermarkPath = str;
    }

    public void setWidth(int i2) {
        this.width = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.id);
        parcel.writeString(this.path);
        parcel.writeString(this.realPath);
        parcel.writeString(this.originalPath);
        parcel.writeString(this.compressPath);
        parcel.writeString(this.cutPath);
        parcel.writeString(this.watermarkPath);
        parcel.writeString(this.videoThumbnailPath);
        parcel.writeString(this.sandboxPath);
        parcel.writeLong(this.duration);
        parcel.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isCut ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.position);
        parcel.writeInt(this.num);
        parcel.writeString(this.mimeType);
        parcel.writeInt(this.chooseModel);
        parcel.writeByte(this.isCameraSource ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.compressed ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.cropImageWidth);
        parcel.writeInt(this.cropImageHeight);
        parcel.writeInt(this.cropOffsetX);
        parcel.writeInt(this.cropOffsetY);
        parcel.writeFloat(this.cropResultAspectRatio);
        parcel.writeLong(this.size);
        parcel.writeByte(this.isOriginal ? (byte) 1 : (byte) 0);
        parcel.writeString(this.fileName);
        parcel.writeString(this.parentFolderName);
        parcel.writeLong(this.bucketId);
        parcel.writeLong(this.dateAddedTime);
        parcel.writeString(this.customData);
        parcel.writeByte(this.isMaxSelectEnabledMask ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isGalleryEnabledMask ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isEditorImage ? (byte) 1 : (byte) 0);
    }

    protected LocalMedia(Parcel parcel) {
        this.bucketId = -1L;
        this.id = parcel.readLong();
        this.path = parcel.readString();
        this.realPath = parcel.readString();
        this.originalPath = parcel.readString();
        this.compressPath = parcel.readString();
        this.cutPath = parcel.readString();
        this.watermarkPath = parcel.readString();
        this.videoThumbnailPath = parcel.readString();
        this.sandboxPath = parcel.readString();
        this.duration = parcel.readLong();
        this.isChecked = parcel.readByte() != 0;
        this.isCut = parcel.readByte() != 0;
        this.position = parcel.readInt();
        this.num = parcel.readInt();
        this.mimeType = parcel.readString();
        this.chooseModel = parcel.readInt();
        this.isCameraSource = parcel.readByte() != 0;
        this.compressed = parcel.readByte() != 0;
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.cropImageWidth = parcel.readInt();
        this.cropImageHeight = parcel.readInt();
        this.cropOffsetX = parcel.readInt();
        this.cropOffsetY = parcel.readInt();
        this.cropResultAspectRatio = parcel.readFloat();
        this.size = parcel.readLong();
        this.isOriginal = parcel.readByte() != 0;
        this.fileName = parcel.readString();
        this.parentFolderName = parcel.readString();
        this.bucketId = parcel.readLong();
        this.dateAddedTime = parcel.readLong();
        this.customData = parcel.readString();
        this.isMaxSelectEnabledMask = parcel.readByte() != 0;
        this.isGalleryEnabledMask = parcel.readByte() != 0;
        this.isEditorImage = parcel.readByte() != 0;
    }

    public static LocalMedia generateHttpAsLocalMedia(String str, String str2) {
        LocalMedia localMediaCreate = create();
        localMediaCreate.setPath(str);
        localMediaCreate.setMimeType(str2);
        return localMediaCreate;
    }

    @Deprecated
    public static LocalMedia generateLocalMedia(String str, String str2) {
        LocalMedia localMediaCreate = create();
        localMediaCreate.setPath(str);
        localMediaCreate.setMimeType(str2);
        return localMediaCreate;
    }
}
