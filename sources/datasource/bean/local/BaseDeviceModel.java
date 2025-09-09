package datasource.bean.local;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class BaseDeviceModel implements Parcelable {
    public static final Parcelable.Creator<BaseDeviceModel> CREATOR = new Parcelable.Creator<BaseDeviceModel>() { // from class: datasource.bean.local.BaseDeviceModel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseDeviceModel createFromParcel(Parcel parcel) {
            return new BaseDeviceModel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseDeviceModel[] newArray(int i2) {
            return new BaseDeviceModel[i2];
        }
    };
    public String commandDomain;
    public String commandName;
    public String commandType;

    public BaseDeviceModel() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getCommandDomain() {
        return this.commandDomain;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public String getCommandType() {
        return this.commandType;
    }

    public void setCommandDomain(String str) {
        this.commandDomain = str;
    }

    public void setCommandName(String str) {
        this.commandName = str;
    }

    public void setCommandType(String str) {
        this.commandType = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.commandDomain);
        parcel.writeString(this.commandName);
        parcel.writeString(this.commandType);
    }

    public BaseDeviceModel(Parcel parcel) {
        this.commandDomain = parcel.readString();
        this.commandName = parcel.readString();
        this.commandType = parcel.readString();
    }
}
