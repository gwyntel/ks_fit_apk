package android.support.v4.os;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.os.IResultReceiver;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@SuppressLint({"BanParcelableUsage"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new Parcelable.Creator<ResultReceiver>() { // from class: android.support.v4.os.ResultReceiver.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultReceiver createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultReceiver[] newArray(int i2) {
            return new ResultReceiver[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    final boolean f1867a;

    /* renamed from: b, reason: collision with root package name */
    final Handler f1868b;

    /* renamed from: c, reason: collision with root package name */
    IResultReceiver f1869c;

    class MyResultReceiver extends IResultReceiver.Stub {
        MyResultReceiver() {
        }

        @Override // android.support.v4.os.IResultReceiver
        public void send(int i2, Bundle bundle) {
            ResultReceiver resultReceiver = ResultReceiver.this;
            Handler handler = resultReceiver.f1868b;
            if (handler != null) {
                handler.post(resultReceiver.new MyRunnable(i2, bundle));
            } else {
                resultReceiver.a(i2, bundle);
            }
        }
    }

    class MyRunnable implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final int f1871a;

        /* renamed from: b, reason: collision with root package name */
        final Bundle f1872b;

        MyRunnable(int i2, Bundle bundle) {
            this.f1871a = i2;
            this.f1872b = bundle;
        }

        @Override // java.lang.Runnable
        public void run() {
            ResultReceiver.this.a(this.f1871a, this.f1872b);
        }
    }

    public ResultReceiver(Handler handler) {
        this.f1867a = true;
        this.f1868b = handler;
    }

    protected void a(int i2, Bundle bundle) {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void send(int i2, Bundle bundle) {
        if (this.f1867a) {
            Handler handler = this.f1868b;
            if (handler != null) {
                handler.post(new MyRunnable(i2, bundle));
                return;
            } else {
                a(i2, bundle);
                return;
            }
        }
        IResultReceiver iResultReceiver = this.f1869c;
        if (iResultReceiver != null) {
            try {
                iResultReceiver.send(i2, bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        synchronized (this) {
            try {
                if (this.f1869c == null) {
                    this.f1869c = new MyResultReceiver();
                }
                parcel.writeStrongBinder(this.f1869c.asBinder());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    ResultReceiver(Parcel parcel) {
        this.f1867a = false;
        this.f1868b = null;
        this.f1869c = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
    }
}
