package com.luck.picture.lib.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class LocalMediaFolder implements Parcelable {
    public static final Parcelable.Creator<LocalMediaFolder> CREATOR = new Parcelable.Creator<LocalMediaFolder>() { // from class: com.luck.picture.lib.entity.LocalMediaFolder.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalMediaFolder createFromParcel(Parcel parcel) {
            return new LocalMediaFolder(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalMediaFolder[] newArray(int i2) {
            return new LocalMediaFolder[i2];
        }
    };
    private long bucketId;
    private int currentDataPage;
    private ArrayList<LocalMedia> data;
    private String firstImagePath;
    private String firstMimeType;
    private String folderName;
    private int folderTotalNum;
    private boolean isHasMore;
    private boolean isSelectTag;

    public LocalMediaFolder() {
        this.bucketId = -1L;
        this.data = new ArrayList<>();
        this.currentDataPage = 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getBucketId() {
        return this.bucketId;
    }

    public int getCurrentDataPage() {
        return this.currentDataPage;
    }

    public ArrayList<LocalMedia> getData() {
        ArrayList<LocalMedia> arrayList = this.data;
        return arrayList != null ? arrayList : new ArrayList<>();
    }

    public String getFirstImagePath() {
        return this.firstImagePath;
    }

    public String getFirstMimeType() {
        return this.firstMimeType;
    }

    public String getFolderName() {
        return TextUtils.isEmpty(this.folderName) ? "unknown" : this.folderName;
    }

    public int getFolderTotalNum() {
        return this.folderTotalNum;
    }

    public boolean isHasMore() {
        return this.isHasMore;
    }

    public boolean isSelectTag() {
        return this.isSelectTag;
    }

    public void setBucketId(long j2) {
        this.bucketId = j2;
    }

    public void setCurrentDataPage(int i2) {
        this.currentDataPage = i2;
    }

    public void setData(ArrayList<LocalMedia> arrayList) {
        this.data = arrayList;
    }

    public void setFirstImagePath(String str) {
        this.firstImagePath = str;
    }

    public void setFirstMimeType(String str) {
        this.firstMimeType = str;
    }

    public void setFolderName(String str) {
        this.folderName = str;
    }

    public void setFolderTotalNum(int i2) {
        this.folderTotalNum = i2;
    }

    public void setHasMore(boolean z2) {
        this.isHasMore = z2;
    }

    public void setSelectTag(boolean z2) {
        this.isSelectTag = z2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.bucketId);
        parcel.writeString(this.folderName);
        parcel.writeString(this.firstImagePath);
        parcel.writeString(this.firstMimeType);
        parcel.writeInt(this.folderTotalNum);
        parcel.writeByte(this.isSelectTag ? (byte) 1 : (byte) 0);
        parcel.writeTypedList(this.data);
        parcel.writeInt(this.currentDataPage);
        parcel.writeByte(this.isHasMore ? (byte) 1 : (byte) 0);
    }

    protected LocalMediaFolder(Parcel parcel) {
        this.bucketId = -1L;
        this.data = new ArrayList<>();
        this.currentDataPage = 1;
        this.bucketId = parcel.readLong();
        this.folderName = parcel.readString();
        this.firstImagePath = parcel.readString();
        this.firstMimeType = parcel.readString();
        this.folderTotalNum = parcel.readInt();
        this.isSelectTag = parcel.readByte() != 0;
        this.data = parcel.createTypedArrayList(LocalMedia.CREATOR);
        this.currentDataPage = parcel.readInt();
        this.isHasMore = parcel.readByte() != 0;
    }
}
