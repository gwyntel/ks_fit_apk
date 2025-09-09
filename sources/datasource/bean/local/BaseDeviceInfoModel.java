package datasource.bean.local;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class BaseDeviceInfoModel implements Parcelable {
    public static final Parcelable.Creator<BaseDeviceInfoModel> CREATOR = new Parcelable.Creator<BaseDeviceInfoModel>() { // from class: datasource.bean.local.BaseDeviceInfoModel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseDeviceInfoModel createFromParcel(Parcel parcel) {
            return new BaseDeviceInfoModel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseDeviceInfoModel[] newArray(int i2) {
            return new BaseDeviceInfoModel[i2];
        }
    };
    public int batchNo;
    public String changeType;
    public String messageId;
    public String messageType;
    public int pageIndex;
    public int pageSize;
    public int size;

    public BaseDeviceInfoModel() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getBatchNo() {
        return this.batchNo;
    }

    public String getChangeType() {
        return this.changeType;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getSize() {
        return this.size;
    }

    public void setBatchNo(int i2) {
        this.batchNo = i2;
    }

    public void setChangeType(String str) {
        this.changeType = str;
    }

    public void setMessageId(String str) {
        this.messageId = str;
    }

    public void setMessageType(String str) {
        this.messageType = str;
    }

    public void setPageIndex(int i2) {
        this.pageIndex = i2;
    }

    public void setPageSize(int i2) {
        this.pageSize = i2;
    }

    public void setSize(int i2) {
        this.size = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.messageType);
        parcel.writeString(this.messageId);
        parcel.writeInt(this.batchNo);
        parcel.writeInt(this.size);
        parcel.writeInt(this.pageSize);
        parcel.writeInt(this.pageIndex);
        parcel.writeString(this.changeType);
    }

    public BaseDeviceInfoModel(Parcel parcel) {
        this.messageType = parcel.readString();
        this.messageId = parcel.readString();
        this.batchNo = parcel.readInt();
        this.size = parcel.readInt();
        this.pageSize = parcel.readInt();
        this.pageIndex = parcel.readInt();
        this.changeType = parcel.readString();
    }
}
