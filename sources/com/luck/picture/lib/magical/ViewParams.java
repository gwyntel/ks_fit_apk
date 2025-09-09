package com.luck.picture.lib.magical;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class ViewParams implements Parcelable {
    public static final Parcelable.Creator<ViewParams> CREATOR = new Parcelable.Creator<ViewParams>() { // from class: com.luck.picture.lib.magical.ViewParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViewParams createFromParcel(Parcel parcel) {
            return new ViewParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViewParams[] newArray(int i2) {
            return new ViewParams[i2];
        }
    };
    public int height;
    public int left;

    /* renamed from: top, reason: collision with root package name */
    public int f19095top;
    public int width;

    public ViewParams() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getHeight() {
        return this.height;
    }

    public int getLeft() {
        return this.left;
    }

    public int getTop() {
        return this.f19095top;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int i2) {
        this.height = i2;
    }

    public void setLeft(int i2) {
        this.left = i2;
    }

    public void setTop(int i2) {
        this.f19095top = i2;
    }

    public void setWidth(int i2) {
        this.width = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.left);
        parcel.writeInt(this.f19095top);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
    }

    protected ViewParams(Parcel parcel) {
        this.left = parcel.readInt();
        this.f19095top = parcel.readInt();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
    }
}
