package android.support.customtabs;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public interface ICustomTabsCallback extends IInterface {
    public static final String DESCRIPTOR = "android$support$customtabs$ICustomTabsCallback".replace('$', '.');

    public static class Default implements ICustomTabsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void extraCallback(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public Bundle extraCallbackWithResult(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onActivityLayout(int i2, int i3, int i4, int i5, int i6, Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onActivityResized(int i2, int i3, Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onMessageChannelReady(Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onMinimized(Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onNavigationEvent(int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onPostMessage(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onRelationshipValidationResult(int i2, Uri uri, boolean z2, Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onUnminimized(Bundle bundle) throws RemoteException {
        }

        @Override // android.support.customtabs.ICustomTabsCallback
        public void onWarmupCompleted(Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub extends Binder implements ICustomTabsCallback {

        private static class Proxy implements ICustomTabsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void extraCallback(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public Bundle extraCallbackWithResult(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) _Parcel.readTypedObject(parcelObtain2, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICustomTabsCallback.DESCRIPTOR;
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void onActivityLayout(int i2, int i3, int i4, int i5, int i6, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void onActivityResized(int i2, int i3, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void onMessageChannelReady(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void onMinimized(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void onNavigationEvent(int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i2);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void onPostMessage(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void onRelationshipValidationResult(int i2, Uri uri, boolean z2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i2);
                    _Parcel.writeTypedObject(parcelObtain, uri, 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void onUnminimized(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.ICustomTabsCallback
            public void onWarmupCompleted(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomTabsCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICustomTabsCallback.DESCRIPTOR);
        }

        public static ICustomTabsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICustomTabsCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICustomTabsCallback)) ? new Proxy(iBinder) : (ICustomTabsCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            String str = ICustomTabsCallback.DESCRIPTOR;
            if (i2 >= 1 && i2 <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i2 == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            switch (i2) {
                case 2:
                    onNavigationEvent(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 3:
                    extraCallback(parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 4:
                    onMessageChannelReady((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    onPostMessage(parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    onRelationshipValidationResult(parcel.readInt(), (Uri) _Parcel.readTypedObject(parcel, Uri.CREATOR), parcel.readInt() != 0, (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 7:
                    Bundle bundleExtraCallbackWithResult = extraCallbackWithResult(parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, bundleExtraCallbackWithResult, 1);
                    return true;
                case 8:
                    onActivityResized(parcel.readInt(), parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 9:
                    onWarmupCompleted((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 10:
                    onActivityLayout(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 11:
                    onMinimized((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 12:
                    onUnminimized((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                default:
                    return super.onTransact(i2, parcel, parcel2, i3);
            }
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t2, int i2) {
            if (t2 == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t2.writeToParcel(parcel, i2);
            }
        }
    }

    void extraCallback(String str, Bundle bundle) throws RemoteException;

    Bundle extraCallbackWithResult(String str, Bundle bundle) throws RemoteException;

    void onActivityLayout(int i2, int i3, int i4, int i5, int i6, Bundle bundle) throws RemoteException;

    void onActivityResized(int i2, int i3, Bundle bundle) throws RemoteException;

    void onMessageChannelReady(Bundle bundle) throws RemoteException;

    void onMinimized(Bundle bundle) throws RemoteException;

    void onNavigationEvent(int i2, Bundle bundle) throws RemoteException;

    void onPostMessage(String str, Bundle bundle) throws RemoteException;

    void onRelationshipValidationResult(int i2, Uri uri, boolean z2, Bundle bundle) throws RemoteException;

    void onUnminimized(Bundle bundle) throws RemoteException;

    void onWarmupCompleted(Bundle bundle) throws RemoteException;
}
