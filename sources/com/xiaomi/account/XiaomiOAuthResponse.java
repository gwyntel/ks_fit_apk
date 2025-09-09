package com.xiaomi.account;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.account.IXiaomiAuthResponse;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;

/* loaded from: classes4.dex */
public class XiaomiOAuthResponse implements Parcelable {
    public static final Parcelable.Creator<XiaomiOAuthResponse> CREATOR = new Parcelable.Creator<XiaomiOAuthResponse>() { // from class: com.xiaomi.account.XiaomiOAuthResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public XiaomiOAuthResponse createFromParcel(Parcel parcel) {
            return new XiaomiOAuthResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public XiaomiOAuthResponse[] newArray(int i2) {
            return new XiaomiOAuthResponse[i2];
        }
    };
    private static final String TAG = "com.xiaomi.account.XiaomiOAuthResponse";
    private IXiaomiAuthResponse mResponse;

    public XiaomiOAuthResponse(IXiaomiAuthResponse iXiaomiAuthResponse) {
        this.mResponse = iXiaomiAuthResponse;
    }

    public static void setIXiaomiAuthResponseCancel(IXiaomiAuthResponse iXiaomiAuthResponse) {
        if (iXiaomiAuthResponse == null) {
            return;
        }
        try {
            iXiaomiAuthResponse.onCancel();
        } catch (RemoteException e2) {
            Log.e(TAG, "RemoteException", e2);
        } catch (RuntimeException e3) {
            Log.e(TAG, "RuntimeException", e3);
        }
    }

    public static void setIXiaomiAuthResponseResult(IXiaomiAuthResponse iXiaomiAuthResponse, Bundle bundle) {
        if (iXiaomiAuthResponse == null || bundle == null) {
            return;
        }
        try {
            iXiaomiAuthResponse.onResult(bundle);
        } catch (RemoteException e2) {
            Log.e(TAG, "RemoteException", e2);
        } catch (RuntimeException e3) {
            Log.e(TAG, "RemoteException", e3);
            Bundle bundle2 = new Bundle();
            bundle2.putInt(XiaomiOAuthConstants.EXTRA_ERROR_CODE, -1);
            bundle2.putString(XiaomiOAuthConstants.EXTRA_ERROR_DESCRIPTION, e3.getMessage());
            try {
                iXiaomiAuthResponse.onResult(bundle2);
            } catch (RemoteException e4) {
                Log.e(TAG, "RemoteException", e4);
            } catch (RuntimeException e5) {
                Log.e(TAG, "RuntimeException", e5);
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void onCancel() {
        setIXiaomiAuthResponseCancel(this.mResponse);
    }

    public void onError(int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt(XiaomiOAuthConstants.EXTRA_ERROR_CODE, i2);
        bundle.putString(XiaomiOAuthConstants.EXTRA_ERROR_DESCRIPTION, str);
        setIXiaomiAuthResponseResult(this.mResponse, bundle);
    }

    public void onResult(Bundle bundle) {
        setIXiaomiAuthResponseResult(this.mResponse, bundle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeStrongBinder(this.mResponse.asBinder());
    }

    public XiaomiOAuthResponse(Parcel parcel) {
        this.mResponse = IXiaomiAuthResponse.Stub.asInterface(parcel.readStrongBinder());
    }
}
