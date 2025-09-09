package anetwork.channel.unified;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import anet.channel.bytes.ByteArray;
import anet.channel.bytes.a;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import anetwork.channel.aidl.Connection;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableFuture;
import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.aidl.RemoteNetwork;
import anetwork.channel.aidl.adapter.ConnectionDelegate;
import anetwork.channel.aidl.adapter.ParcelableFutureResponse;
import anetwork.channel.aidl.adapter.ParcelableNetworkListenerWrapper;
import anetwork.channel.http.NetworkSdkSetting;
import java.io.ByteArrayOutputStream;

/* loaded from: classes2.dex */
public abstract class UnifiedNetworkDelegate extends RemoteNetwork.Stub {
    public static final int DEGRADABLE = 1;
    public static final int HTTP = 0;
    private static final String TAG = "anet.UnifiedNetworkDelegate";
    public int type = 1;

    public UnifiedNetworkDelegate(Context context) {
        NetworkSdkSetting.init(context);
    }

    private NetworkResponse convertToSync(ParcelableRequest parcelableRequest) {
        NetworkResponse networkResponse = new NetworkResponse();
        try {
            ConnectionDelegate connectionDelegate = (ConnectionDelegate) getConnection(parcelableRequest);
            ParcelableInputStream inputStream = connectionDelegate.getInputStream();
            if (inputStream != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.length() > 0 ? inputStream.length() : 1024);
                ByteArray byteArrayA = a.C0009a.f6699a.a(2048);
                while (true) {
                    int i2 = inputStream.read(byteArrayA.getBuffer());
                    if (i2 == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(byteArrayA.getBuffer(), 0, i2);
                }
                networkResponse.setBytedata(byteArrayOutputStream.toByteArray());
            }
            int statusCode = connectionDelegate.getStatusCode();
            if (statusCode < 0) {
                networkResponse.setBytedata(null);
            } else {
                networkResponse.setConnHeadFields(connectionDelegate.getConnHeadFields());
            }
            networkResponse.setStatusCode(statusCode);
            networkResponse.setStatisticData(connectionDelegate.getStatisticData());
            return networkResponse;
        } catch (RemoteException e2) {
            networkResponse.setStatusCode(-103);
            String message = e2.getMessage();
            if (!TextUtils.isEmpty(message)) {
                networkResponse.setDesc(StringUtils.concatString(networkResponse.getDesc(), "|", message));
            }
            return networkResponse;
        } catch (Exception unused) {
            networkResponse.setStatusCode(-201);
            return networkResponse;
        }
    }

    @Override // anetwork.channel.aidl.RemoteNetwork
    public ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
        try {
            return asyncSend(new anetwork.channel.entity.g(parcelableRequest, this.type, false), parcelableNetworkListener);
        } catch (Exception e2) {
            ALog.e(TAG, "asyncSend failed", parcelableRequest.seqNo, e2, new Object[0]);
            throw new RemoteException(e2.getMessage());
        }
    }

    @Override // anetwork.channel.aidl.RemoteNetwork
    public Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException {
        try {
            anetwork.channel.entity.g gVar = new anetwork.channel.entity.g(parcelableRequest, this.type, true);
            ConnectionDelegate connectionDelegate = new ConnectionDelegate(gVar);
            connectionDelegate.setFuture(asyncSend(gVar, new ParcelableNetworkListenerWrapper(connectionDelegate, null, null)));
            return connectionDelegate;
        } catch (Exception e2) {
            ALog.e(TAG, "asyncSend failed", parcelableRequest.seqNo, e2, new Object[0]);
            throw new RemoteException(e2.getMessage());
        }
    }

    @Override // anetwork.channel.aidl.RemoteNetwork
    public NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException {
        return convertToSync(parcelableRequest);
    }

    private ParcelableFuture asyncSend(anetwork.channel.entity.g gVar, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
        return new ParcelableFutureResponse(new k(gVar, new anetwork.channel.entity.c(parcelableNetworkListener, gVar)).a());
    }
}
