package org.eclipse.paho.client.mqttv3;

import java.util.concurrent.ScheduledExecutorService;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.util.Debug;

/* loaded from: classes5.dex */
public class MqttClient implements IMqttClient {

    /* renamed from: a, reason: collision with root package name */
    protected MqttAsyncClient f26660a;

    /* renamed from: b, reason: collision with root package name */
    protected long f26661b;

    public MqttClient(String str, String str2) throws MqttException {
        this(str, str2, new MqttDefaultFilePersistence());
    }

    public static String generateClientId() {
        return MqttAsyncClient.generateClientId();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void close() throws MqttException {
        this.f26660a.close(false);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void connect() throws MqttException {
        connect(new MqttConnectOptions());
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttToken connectWithResult(MqttConnectOptions mqttConnectOptions) throws MqttException {
        IMqttToken iMqttTokenConnect = this.f26660a.connect(mqttConnectOptions, null, null);
        iMqttTokenConnect.waitForCompletion(getTimeToWait());
        return iMqttTokenConnect;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void disconnect() throws MqttException {
        this.f26660a.disconnect().waitForCompletion();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void disconnectForcibly() throws MqttException {
        this.f26660a.disconnectForcibly();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public String getClientId() {
        return this.f26660a.getClientId();
    }

    public String getCurrentServerURI() {
        return this.f26660a.getCurrentServerURI();
    }

    public Debug getDebug() {
        return this.f26660a.getDebug();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttDeliveryToken[] getPendingDeliveryTokens() {
        return this.f26660a.getPendingDeliveryTokens();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public String getServerURI() {
        return this.f26660a.getServerURI();
    }

    public long getTimeToWait() {
        return this.f26661b;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public MqttTopic getTopic(String str) {
        return this.f26660a.getTopic(str);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public boolean isConnected() {
        return this.f26660a.isConnected();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void messageArrivedComplete(int i2, int i3) throws MqttException {
        this.f26660a.messageArrivedComplete(i2, i3);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void publish(String str, byte[] bArr, int i2, boolean z2) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(bArr);
        mqttMessage.setQos(i2);
        mqttMessage.setRetained(z2);
        publish(str, mqttMessage);
    }

    public void reconnect() throws MqttException {
        this.f26660a.reconnect();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void setCallback(MqttCallback mqttCallback) {
        this.f26660a.setCallback(mqttCallback);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void setManualAcks(boolean z2) {
        this.f26660a.setManualAcks(z2);
    }

    public void setTimeToWait(long j2) throws IllegalArgumentException {
        if (j2 < -1) {
            throw new IllegalArgumentException();
        }
        this.f26661b = j2;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void subscribe(String str) throws MqttException, IllegalArgumentException {
        subscribe(new String[]{str}, new int[]{1});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttToken subscribeWithResponse(String str) throws MqttException {
        return subscribeWithResponse(new String[]{str}, new int[]{1});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void unsubscribe(String str) throws MqttException {
        unsubscribe(new String[]{str});
    }

    public MqttClient(String str, String str2, MqttClientPersistence mqttClientPersistence) throws MqttException {
        this.f26660a = null;
        this.f26661b = -1L;
        this.f26660a = new MqttAsyncClient(str, str2, mqttClientPersistence);
    }

    public void close(boolean z2) throws MqttException {
        this.f26660a.close(z2);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void connect(MqttConnectOptions mqttConnectOptions) throws MqttException {
        this.f26660a.connect(mqttConnectOptions, null, null).waitForCompletion(getTimeToWait());
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void disconnect(long j2) throws MqttException {
        this.f26660a.disconnect(j2, null, null).waitForCompletion();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void disconnectForcibly(long j2) throws MqttException {
        this.f26660a.disconnectForcibly(j2);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void subscribe(String[] strArr) throws MqttException, IllegalArgumentException {
        int length = strArr.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = 1;
        }
        subscribe(strArr, iArr);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttToken subscribeWithResponse(String str, IMqttMessageListener iMqttMessageListener) throws MqttException {
        return subscribeWithResponse(new String[]{str}, new int[]{1}, new IMqttMessageListener[]{iMqttMessageListener});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void unsubscribe(String[] strArr) throws MqttException {
        this.f26660a.unsubscribe(strArr, (Object) null, (IMqttActionListener) null).waitForCompletion(getTimeToWait());
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void disconnectForcibly(long j2, long j3) throws MqttException {
        this.f26660a.disconnectForcibly(j2, j3);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttToken subscribeWithResponse(String str, int i2) throws MqttException {
        return subscribeWithResponse(new String[]{str}, new int[]{i2});
    }

    public void disconnectForcibly(long j2, long j3, boolean z2) throws MqttException {
        this.f26660a.disconnectForcibly(j2, j3, z2);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttToken subscribeWithResponse(String str, int i2, IMqttMessageListener iMqttMessageListener) throws MqttException {
        return subscribeWithResponse(new String[]{str}, new int[]{i2}, new IMqttMessageListener[]{iMqttMessageListener});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void publish(String str, MqttMessage mqttMessage) throws MqttException {
        this.f26660a.publish(str, mqttMessage, (Object) null, (IMqttActionListener) null).waitForCompletion(getTimeToWait());
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void subscribe(String str, int i2) throws MqttException, IllegalArgumentException {
        subscribe(new String[]{str}, new int[]{i2});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttToken subscribeWithResponse(String[] strArr) throws MqttException {
        int length = strArr.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = 1;
        }
        return subscribeWithResponse(strArr, iArr);
    }

    public MqttClient(String str, String str2, MqttClientPersistence mqttClientPersistence, ScheduledExecutorService scheduledExecutorService) throws MqttException {
        this.f26660a = null;
        this.f26661b = -1L;
        this.f26660a = new MqttAsyncClient(str, str2, mqttClientPersistence, new ScheduledExecutorPingSender(scheduledExecutorService), scheduledExecutorService);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void subscribe(String[] strArr, int[] iArr) throws MqttException, IllegalArgumentException {
        IMqttToken iMqttTokenSubscribe = this.f26660a.subscribe(strArr, iArr, (Object) null, (IMqttActionListener) null);
        iMqttTokenSubscribe.waitForCompletion(getTimeToWait());
        int[] grantedQos = iMqttTokenSubscribe.getGrantedQos();
        for (int i2 = 0; i2 < grantedQos.length; i2++) {
            iArr[i2] = grantedQos[i2];
        }
        if (grantedQos.length == 1 && iArr[0] == 128) {
            throw new MqttException(128);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttToken subscribeWithResponse(String[] strArr, IMqttMessageListener[] iMqttMessageListenerArr) throws MqttException {
        int length = strArr.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = 1;
        }
        return subscribeWithResponse(strArr, iArr, iMqttMessageListenerArr);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttToken subscribeWithResponse(String[] strArr, int[] iArr) throws MqttException, IllegalArgumentException {
        IMqttToken iMqttTokenSubscribe = this.f26660a.subscribe(strArr, iArr, (Object) null, (IMqttActionListener) null);
        iMqttTokenSubscribe.waitForCompletion(getTimeToWait());
        return iMqttTokenSubscribe;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void subscribe(String str, IMqttMessageListener iMqttMessageListener) throws MqttException, IllegalArgumentException {
        subscribe(new String[]{str}, new int[]{1}, new IMqttMessageListener[]{iMqttMessageListener});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public IMqttToken subscribeWithResponse(String[] strArr, int[] iArr, IMqttMessageListener[] iMqttMessageListenerArr) throws MqttException, IllegalArgumentException {
        IMqttToken iMqttTokenSubscribeWithResponse = subscribeWithResponse(strArr, iArr);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            this.f26660a.comms.setMessageListener(strArr[i2], iMqttMessageListenerArr[i2]);
        }
        return iMqttTokenSubscribeWithResponse;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void subscribe(String[] strArr, IMqttMessageListener[] iMqttMessageListenerArr) throws MqttException, IllegalArgumentException {
        int length = strArr.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = 1;
        }
        subscribe(strArr, iArr, iMqttMessageListenerArr);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void subscribe(String str, int i2, IMqttMessageListener iMqttMessageListener) throws MqttException, IllegalArgumentException {
        subscribe(new String[]{str}, new int[]{i2}, new IMqttMessageListener[]{iMqttMessageListener});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttClient
    public void subscribe(String[] strArr, int[] iArr, IMqttMessageListener[] iMqttMessageListenerArr) throws MqttException, IllegalArgumentException {
        subscribe(strArr, iArr);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            this.f26660a.comms.setMessageListener(strArr[i2], iMqttMessageListenerArr[i2]);
        }
    }
}
