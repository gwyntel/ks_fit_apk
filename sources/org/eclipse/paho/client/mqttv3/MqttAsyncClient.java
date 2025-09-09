package org.eclipse.paho.client.mqttv3;

import android.content.Context;
import android.content.Intent;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.iot.aep.sdk.bridge.invoker.SyncBoneInvoker;
import com.taobao.accs.utl.BaseMonitor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.AlarmTimer;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.internal.ConnectActionListener;
import org.eclipse.paho.client.mqttv3.internal.DisconnectedMessageBuffer;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.internal.NetworkModule;
import org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;
import org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketSecureNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSubscribe;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubscribe;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.util.Debug;

/* loaded from: classes5.dex */
public class MqttAsyncClient implements IMqttAsyncClient {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.MqttAsyncClient";
    private static final String CLIENT_ID_PREFIX = "paho";
    private static final long DISCONNECT_TIMEOUT = 10000;
    private static final char MAX_HIGH_SURROGATE = 56319;
    private static final char MIN_HIGH_SURROGATE = 55296;
    private static final long QUIESCE_TIMEOUT = 30000;
    private String clientId;
    protected ClientComms comms;
    private MqttConnectOptions connOpts;
    private ScheduledExecutorService executorService;
    private MqttCallback mqttCallback;
    private MqttClientPersistence persistence;
    private AlarmTimer reconnectAralmTimer;
    private Timer reconnectTimer;
    private boolean reconnecting;
    private long retryWindowSize;
    private String serverURI;
    private Hashtable topics;
    private Object userContext;
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, MqttAsyncClient.class.getName());
    private static int reconnectDelay = 1000;
    private static Object clientLock = new Object();

    class MqttReconnectActionListener implements IMqttActionListener {

        /* renamed from: a, reason: collision with root package name */
        final String f26655a;

        MqttReconnectActionListener(String str) {
            this.f26655a = str;
        }

        private void rescheduleReconnectCycle(int i2) {
            MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, this.f26655a + ":rescheduleReconnectCycle", "505", new Object[]{MqttAsyncClient.this.clientId, String.valueOf(MqttAsyncClient.reconnectDelay)});
            synchronized (MqttAsyncClient.clientLock) {
                try {
                    if (MqttAsyncClient.this.connOpts.isAutomaticReconnect()) {
                        if (MqttAsyncClient.this.reconnectTimer != null) {
                            MqttAsyncClient.this.reconnectTimer.schedule(new ReconnectTask(), i2);
                        } else {
                            int unused = MqttAsyncClient.reconnectDelay = i2;
                            MqttAsyncClient.this.startReconnectCycle();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
        public void onFailure(IMqttToken iMqttToken, Throwable th) {
            MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, this.f26655a, "502", new Object[]{iMqttToken.getClient().getClientId()});
            if (MqttAsyncClient.reconnectDelay < 128000) {
                MqttAsyncClient.reconnectDelay *= 2;
            }
            rescheduleReconnectCycle(MqttAsyncClient.reconnectDelay);
        }

        @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
        public void onSuccess(IMqttToken iMqttToken) {
            MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, this.f26655a, SyncBoneInvoker.ERROR_SUB_CODE_METHOD_NOT_IMPLEMENTED, new Object[]{iMqttToken.getClient().getClientId()});
            MqttAsyncClient.this.comms.setRestingState(false);
            MqttAsyncClient.this.stopReconnectCycle();
        }
    }

    class MqttReconnectCallback implements MqttCallbackExtended {

        /* renamed from: a, reason: collision with root package name */
        final boolean f26657a;

        MqttReconnectCallback(boolean z2) {
            this.f26657a = z2;
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallbackExtended
        public void connectComplete(boolean z2, String str) {
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallback
        public void connectionLost(Throwable th) {
            if (this.f26657a) {
                MqttAsyncClient.this.comms.setRestingState(true);
                MqttAsyncClient.this.reconnecting = true;
                MqttAsyncClient.this.startReconnectCycle();
            }
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallback
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallback
        public void messageArrived(String str, MqttMessage mqttMessage) throws Exception {
        }
    }

    private class ReconnectTask extends TimerTask {
        private static final String methodName = "ReconnectTask.run";

        private ReconnectTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, methodName, "506");
            MqttAsyncClient.this.attemptReconnect();
        }
    }

    public MqttAsyncClient(String str, String str2) throws MqttException {
        this(str, str2, new MqttDefaultFilePersistence());
    }

    protected static boolean Character_isHighSurrogate(char c2) {
        return c2 >= 55296 && c2 <= 56319;
    }

    private NetworkModule createNetworkModule(String str, MqttConnectOptions mqttConnectOptions) throws IllegalAccessException, NoSuchFieldException, NoSuchAlgorithmException, UnrecoverableKeyException, MqttException, SecurityException, IOException, KeyStoreException, CertificateException, KeyManagementException, IllegalArgumentException {
        SSLSocketFactoryFactory sSLSocketFactoryFactory;
        String[] enabledCipherSuites;
        SSLSocketFactoryFactory sSLSocketFactoryFactory2;
        String[] enabledCipherSuites2;
        Logger logger = log;
        String str2 = CLASS_NAME;
        logger.fine(str2, "createNetworkModule", "115", new Object[]{str});
        SocketFactory socketFactory = mqttConnectOptions.getSocketFactory();
        int iValidateURI = MqttConnectOptions.validateURI(str);
        try {
            URI uri = new URI(str);
            if (uri.getHost() == null && str.contains(OpenAccountUIConstants.UNDER_LINE)) {
                try {
                    Field declaredField = URI.class.getDeclaredField("host");
                    declaredField.setAccessible(true);
                    declaredField.set(uri, getHostName(str.substring(uri.getScheme().length() + 3)));
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e2) {
                    throw ExceptionHelper.createMqttException(e2.getCause());
                }
            }
            String host = uri.getHost();
            int port = uri.getPort();
            if (iValidateURI == 0) {
                if (port == -1) {
                    port = 1883;
                }
                if (socketFactory == null) {
                    socketFactory = SocketFactory.getDefault();
                } else if (socketFactory instanceof SSLSocketFactory) {
                    throw ExceptionHelper.createMqttException(32105);
                }
                TCPNetworkModule tCPNetworkModule = new TCPNetworkModule(socketFactory, host, port, this.clientId);
                tCPNetworkModule.setConnectTimeout(mqttConnectOptions.getConnectionTimeout());
                return tCPNetworkModule;
            }
            if (iValidateURI == 1) {
                if (port == -1) {
                    port = 8883;
                }
                if (socketFactory == null) {
                    sSLSocketFactoryFactory = new SSLSocketFactoryFactory();
                    Properties sSLProperties = mqttConnectOptions.getSSLProperties();
                    if (sSLProperties != null) {
                        sSLSocketFactoryFactory.initialize(sSLProperties, null);
                    }
                    socketFactory = sSLSocketFactoryFactory.createSocketFactory(null);
                } else {
                    if (!(socketFactory instanceof SSLSocketFactory)) {
                        throw ExceptionHelper.createMqttException(32105);
                    }
                    sSLSocketFactoryFactory = null;
                }
                SSLNetworkModule sSLNetworkModule = new SSLNetworkModule((SSLSocketFactory) socketFactory, host, port, this.clientId);
                sSLNetworkModule.setSSLhandshakeTimeout(mqttConnectOptions.getConnectionTimeout());
                sSLNetworkModule.setSSLHostnameVerifier(mqttConnectOptions.getSSLHostnameVerifier());
                if (sSLSocketFactoryFactory != null && (enabledCipherSuites = sSLSocketFactoryFactory.getEnabledCipherSuites(null)) != null) {
                    sSLNetworkModule.setEnabledCiphers(enabledCipherSuites);
                }
                return sSLNetworkModule;
            }
            if (iValidateURI == 3) {
                int i2 = port == -1 ? 80 : port;
                if (socketFactory == null) {
                    socketFactory = SocketFactory.getDefault();
                } else if (socketFactory instanceof SSLSocketFactory) {
                    throw ExceptionHelper.createMqttException(32105);
                }
                WebSocketNetworkModule webSocketNetworkModule = new WebSocketNetworkModule(socketFactory, str, host, i2, this.clientId);
                webSocketNetworkModule.setConnectTimeout(mqttConnectOptions.getConnectionTimeout());
                return webSocketNetworkModule;
            }
            if (iValidateURI != 4) {
                logger.fine(str2, "createNetworkModule", "119", new Object[]{str});
                return null;
            }
            int i3 = port == -1 ? 443 : port;
            if (socketFactory == null) {
                SSLSocketFactoryFactory sSLSocketFactoryFactory3 = new SSLSocketFactoryFactory();
                Properties sSLProperties2 = mqttConnectOptions.getSSLProperties();
                if (sSLProperties2 != null) {
                    sSLSocketFactoryFactory3.initialize(sSLProperties2, null);
                }
                socketFactory = sSLSocketFactoryFactory3.createSocketFactory(null);
                sSLSocketFactoryFactory2 = sSLSocketFactoryFactory3;
            } else {
                if (!(socketFactory instanceof SSLSocketFactory)) {
                    throw ExceptionHelper.createMqttException(32105);
                }
                sSLSocketFactoryFactory2 = null;
            }
            WebSocketSecureNetworkModule webSocketSecureNetworkModule = new WebSocketSecureNetworkModule((SSLSocketFactory) socketFactory, str, host, i3, this.clientId);
            webSocketSecureNetworkModule.setSSLhandshakeTimeout(mqttConnectOptions.getConnectionTimeout());
            if (sSLSocketFactoryFactory2 != null && (enabledCipherSuites2 = sSLSocketFactoryFactory2.getEnabledCipherSuites(null)) != null) {
                webSocketSecureNetworkModule.setEnabledCiphers(enabledCipherSuites2);
            }
            return webSocketSecureNetworkModule;
        } catch (URISyntaxException e3) {
            throw new IllegalArgumentException("Malformed URI: " + str + ", " + e3.getMessage());
        }
    }

    public static String generateClientId() {
        return CLIENT_ID_PREFIX + System.nanoTime();
    }

    private String getHostName(String str) {
        int iIndexOf = str.indexOf(58);
        if (iIndexOf == -1) {
            iIndexOf = str.indexOf(47);
        }
        if (iIndexOf == -1) {
            iIndexOf = str.length();
        }
        return str.substring(0, iIndexOf);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startReconnectCycle() {
        Logger logger = log;
        String str = CLASS_NAME;
        logger.fine(str, "startReconnectCycle", "503", new Object[]{this.clientId, new Long(reconnectDelay)});
        ClientComms clientComms = this.comms;
        if (clientComms == null || !(clientComms.getPingSender() instanceof AlarmMqttPingSender)) {
            Timer timer = new Timer("MQTT Reconnect: " + this.clientId);
            this.reconnectTimer = timer;
            timer.schedule(new ReconnectTask(), (long) reconnectDelay);
            return;
        }
        logger.fine(str, "startReconnectCycle", "alarm MQTT Reconnect: " + this.clientId);
        try {
            if (this.reconnectTimer == null) {
                this.reconnectAralmTimer = new AlarmTimer(((AlarmMqttPingSender) this.comms.getPingSender()).getContext(), "reconnect_" + ((AlarmMqttPingSender) this.comms.getPingSender()).getContext().getPackageName());
            }
            if (this.connOpts.isAutomaticReconnect()) {
                this.reconnectAralmTimer.schedule(new AlarmTimer.AlarmTask() { // from class: org.eclipse.paho.client.mqttv3.MqttAsyncClient.1
                    @Override // org.eclipse.paho.client.mqttv3.AlarmTimer.AlarmTask
                    public void onReceive(Context context, Intent intent) {
                        MqttAsyncClient.this.attemptReconnect();
                    }
                }, reconnectDelay, this.retryWindowSize);
            }
        } catch (Exception unused) {
            Timer timer2 = new Timer("MQTT Reconnect(alarm->timer):" + this.clientId);
            this.reconnectTimer = timer2;
            timer2.schedule(new ReconnectTask(), (long) reconnectDelay);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopReconnectCycle() {
        log.fine(CLASS_NAME, "stopReconnectCycle", "504", new Object[]{this.clientId});
        synchronized (clientLock) {
            try {
                if (this.connOpts.isAutomaticReconnect()) {
                    Timer timer = this.reconnectTimer;
                    if (timer != null) {
                        timer.cancel();
                        this.reconnectTimer = null;
                    }
                    reconnectDelay = 1000;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected void attemptReconnect() {
        log.fine(CLASS_NAME, "attemptReconnect", SyncBoneInvoker.ERROR_SUB_CODE_EXCEPTION, new Object[]{this.clientId});
        try {
            connect(this.connOpts, this.userContext, new MqttReconnectActionListener("attemptReconnect"));
        } catch (MqttSecurityException e2) {
            log.fine(CLASS_NAME, "attemptReconnect", "804", null, e2);
        } catch (MqttException e3) {
            log.fine(CLASS_NAME, "attemptReconnect", "804", null, e3);
        }
    }

    public IMqttToken checkPing(Object obj, IMqttActionListener iMqttActionListener) throws MqttException {
        Logger logger = log;
        String str = CLASS_NAME;
        logger.fine(str, "ping", "117");
        MqttToken mqttTokenCheckForActivity = this.comms.checkForActivity();
        logger.fine(str, "ping", "118");
        return mqttTokenCheckForActivity;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void close() throws MqttException {
        close(false);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken connect(Object obj, IMqttActionListener iMqttActionListener) throws MqttException {
        return connect(new MqttConnectOptions(), obj, iMqttActionListener);
    }

    protected NetworkModule[] createNetworkModules(String str, MqttConnectOptions mqttConnectOptions) {
        log.fine(CLASS_NAME, "createNetworkModules", "116", new Object[]{str});
        String[] serverURIs = mqttConnectOptions.getServerURIs();
        if (serverURIs == null || serverURIs.length == 0) {
            serverURIs = new String[]{str};
        }
        NetworkModule[] networkModuleArr = new NetworkModule[serverURIs.length];
        for (int i2 = 0; i2 < serverURIs.length; i2++) {
            networkModuleArr[i2] = createNetworkModule(serverURIs[i2], mqttConnectOptions);
        }
        log.fine(CLASS_NAME, "createNetworkModules", "108");
        return networkModuleArr;
    }

    public void deleteBufferedMessage(int i2) {
        this.comms.deleteBufferedMessage(i2);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken disconnect(Object obj, IMqttActionListener iMqttActionListener) throws MqttException {
        return disconnect(30000L, obj, iMqttActionListener);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void disconnectForcibly() throws MqttException {
        disconnectForcibly(30000L, 10000L);
    }

    public MqttMessage getBufferedMessage(int i2) {
        return this.comms.getBufferedMessage(i2);
    }

    public int getBufferedMessageCount() {
        return this.comms.getBufferedMessageCount();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public String getClientId() {
        return this.clientId;
    }

    public String getCurrentServerURI() {
        return this.comms.getNetworkModules()[this.comms.getNetworkModuleIndex()].getServerURI();
    }

    public Debug getDebug() {
        return new Debug(this.clientId, this.comms);
    }

    public int getInFlightMessageCount() {
        return this.comms.getActualInFlight();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken[] getPendingDeliveryTokens() {
        return this.comms.getPendingDeliveryTokens();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public String getServerURI() {
        return this.serverURI;
    }

    protected MqttTopic getTopic(String str) throws IllegalArgumentException {
        MqttTopic.validate(str, false);
        MqttTopic mqttTopic = (MqttTopic) this.topics.get(str);
        if (mqttTopic != null) {
            return mqttTopic;
        }
        MqttTopic mqttTopic2 = new MqttTopic(str, this.comms);
        this.topics.put(str, mqttTopic2);
        return mqttTopic2;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public boolean isConnected() {
        return this.comms.isConnected();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void messageArrivedComplete(int i2, int i3) throws MqttException {
        this.comms.messageArrivedComplete(i2, i3);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken publish(String str, byte[] bArr, int i2, boolean z2, Object obj, IMqttActionListener iMqttActionListener) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(bArr);
        mqttMessage.setQos(i2);
        mqttMessage.setRetained(z2);
        return publish(str, mqttMessage, obj, iMqttActionListener);
    }

    public void reconnect() throws MqttException {
        log.fine(CLASS_NAME, "reconnect", SyncBoneInvoker.ERROR_SUB_CODE_EXCEPTION, new Object[]{this.clientId});
        if (this.comms.isConnected()) {
            throw ExceptionHelper.createMqttException(32100);
        }
        if (this.comms.isConnecting()) {
            throw new MqttException(32110);
        }
        if (this.comms.isDisconnecting()) {
            throw new MqttException(32102);
        }
        if (this.comms.isClosed()) {
            throw new MqttException(32111);
        }
        stopReconnectCycle();
        attemptReconnect();
    }

    public void setBufferOpts(DisconnectedBufferOptions disconnectedBufferOptions) {
        this.comms.setDisconnectedMessageBuffer(new DisconnectedMessageBuffer(disconnectedBufferOptions));
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void setCallback(MqttCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
        this.comms.setCallback(mqttCallback);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void setManualAcks(boolean z2) {
        this.comms.setManualAcks(z2);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String str, int i2, Object obj, IMqttActionListener iMqttActionListener) throws MqttException {
        return subscribe(new String[]{str}, new int[]{i2}, obj, iMqttActionListener);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken unsubscribe(String str, Object obj, IMqttActionListener iMqttActionListener) throws MqttException {
        return unsubscribe(new String[]{str}, obj, iMqttActionListener);
    }

    public MqttAsyncClient(String str, String str2, MqttClientPersistence mqttClientPersistence) throws MqttException {
        this(str, str2, mqttClientPersistence, new TimerPingSender());
    }

    public void close(boolean z2) throws MqttException {
        log.fine(CLASS_NAME, "close", "113");
        this.comms.close(z2);
        try {
            AlarmTimer alarmTimer = this.reconnectAralmTimer;
            if (alarmTimer != null) {
                alarmTimer.destroy();
                this.reconnectAralmTimer = null;
            }
        } catch (Exception unused) {
        }
        log.fine(CLASS_NAME, "close", "114");
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken connect() throws MqttException {
        return connect(null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken disconnect() throws MqttException {
        return disconnect(null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void disconnectForcibly(long j2) throws MqttException {
        disconnectForcibly(30000L, j2);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String str, int i2) throws MqttException {
        return subscribe(new String[]{str}, new int[]{i2}, (Object) null, (IMqttActionListener) null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken unsubscribe(String str) throws MqttException {
        return unsubscribe(new String[]{str}, (Object) null, (IMqttActionListener) null);
    }

    public MqttAsyncClient(String str, String str2, MqttClientPersistence mqttClientPersistence, MqttPingSender mqttPingSender) throws MqttException {
        this(str, str2, mqttClientPersistence, mqttPingSender, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken connect(MqttConnectOptions mqttConnectOptions) throws MqttException {
        return connect(mqttConnectOptions, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken disconnect(long j2) throws MqttException {
        return disconnect(j2, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void disconnectForcibly(long j2, long j3) throws MqttException {
        this.comms.disconnectForcibly(j2, j3);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String[] strArr, int[] iArr) throws MqttException {
        return subscribe(strArr, iArr, (Object) null, (IMqttActionListener) null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken unsubscribe(String[] strArr) throws MqttException {
        return unsubscribe(strArr, (Object) null, (IMqttActionListener) null);
    }

    public MqttAsyncClient(String str, String str2, MqttClientPersistence mqttClientPersistence, MqttPingSender mqttPingSender, ScheduledExecutorService scheduledExecutorService) throws MqttException {
        this.reconnecting = false;
        this.retryWindowSize = 60000L;
        log.setResourceName(str2);
        if (str2 != null) {
            int i2 = 0;
            int i3 = 0;
            while (i2 < str2.length() - 1) {
                if (Character_isHighSurrogate(str2.charAt(i2))) {
                    i2++;
                }
                i3++;
                i2++;
            }
            if (i3 <= 65535) {
                MqttConnectOptions.validateURI(str);
                this.serverURI = str;
                this.clientId = str2;
                this.persistence = mqttClientPersistence;
                if (mqttClientPersistence == null) {
                    this.persistence = new MemoryPersistence();
                }
                this.executorService = scheduledExecutorService;
                if (scheduledExecutorService == null) {
                    this.executorService = Executors.newScheduledThreadPool(10);
                }
                log.fine(CLASS_NAME, "MqttAsyncClient", "101", new Object[]{str2, str, mqttClientPersistence});
                this.persistence.open(str2, str);
                this.comms = new ClientComms(this, this.persistence, mqttPingSender, this.executorService);
                this.persistence.close();
                this.topics = new Hashtable();
                return;
            }
            throw new IllegalArgumentException("ClientId longer than 65535 characters");
        }
        throw new IllegalArgumentException("Null clientId");
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken connect(MqttConnectOptions mqttConnectOptions, Object obj, IMqttActionListener iMqttActionListener) throws MqttException {
        if (!this.comms.isConnected()) {
            if (!this.comms.isConnecting()) {
                if (!this.comms.isDisconnecting()) {
                    if (!this.comms.isClosed()) {
                        if (mqttConnectOptions == null) {
                            mqttConnectOptions = new MqttConnectOptions();
                        }
                        MqttConnectOptions mqttConnectOptions2 = mqttConnectOptions;
                        this.connOpts = mqttConnectOptions2;
                        this.userContext = obj;
                        boolean zIsAutomaticReconnect = mqttConnectOptions2.isAutomaticReconnect();
                        log.fine(CLASS_NAME, BaseMonitor.ALARM_POINT_CONNECT, "103", new Object[]{Boolean.valueOf(mqttConnectOptions2.isCleanSession()), new Integer(mqttConnectOptions2.getConnectionTimeout()), new Integer(mqttConnectOptions2.getKeepAliveInterval()), mqttConnectOptions2.getUserName(), mqttConnectOptions2.getPassword() == null ? "[null]" : "[notnull]", mqttConnectOptions2.getWillMessage() == null ? "[null]" : "[notnull]", obj, iMqttActionListener});
                        this.comms.setNetworkModules(createNetworkModules(this.serverURI, mqttConnectOptions2));
                        this.comms.setReconnectCallback(new MqttReconnectCallback(zIsAutomaticReconnect));
                        MqttToken mqttToken = new MqttToken(getClientId());
                        ConnectActionListener connectActionListener = new ConnectActionListener(this, this.persistence, this.comms, mqttConnectOptions2, mqttToken, obj, iMqttActionListener, this.reconnecting);
                        mqttToken.setActionCallback(connectActionListener);
                        mqttToken.setUserContext(this);
                        MqttCallback mqttCallback = this.mqttCallback;
                        if (mqttCallback instanceof MqttCallbackExtended) {
                            connectActionListener.setMqttCallbackExtended((MqttCallbackExtended) mqttCallback);
                        }
                        this.comms.setNetworkModuleIndex(0);
                        connectActionListener.connect();
                        return mqttToken;
                    }
                    throw new MqttException(32111);
                }
                throw new MqttException(32102);
            }
            throw new MqttException(32110);
        }
        throw ExceptionHelper.createMqttException(32100);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken disconnect(long j2, Object obj, IMqttActionListener iMqttActionListener) throws MqttException {
        log.fine(CLASS_NAME, "disconnect", "104", new Object[]{new Long(j2), obj, iMqttActionListener});
        try {
            stopReconnectCycle();
        } catch (Exception unused) {
        }
        MqttConnectOptions mqttConnectOptions = this.connOpts;
        if (mqttConnectOptions != null) {
            mqttConnectOptions.setAutomaticReconnect(false);
        }
        MqttToken mqttToken = new MqttToken(getClientId());
        mqttToken.setActionCallback(iMqttActionListener);
        mqttToken.setUserContext(obj);
        try {
            this.comms.disconnect(new MqttDisconnect(), j2, mqttToken);
            log.fine(CLASS_NAME, "disconnect", "108");
            return mqttToken;
        } catch (MqttException e2) {
            log.fine(CLASS_NAME, "disconnect", "105", null, e2);
            throw e2;
        }
    }

    public void disconnectForcibly(long j2, long j3, boolean z2) throws MqttException {
        this.comms.disconnectForcibly(j2, j3, z2);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String[] strArr, int[] iArr, Object obj, IMqttActionListener iMqttActionListener) throws MqttException, IllegalArgumentException {
        if (strArr.length == iArr.length) {
            for (String str : strArr) {
                this.comms.removeMessageListener(str);
            }
            if (log.isLoggable(5)) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i2 = 0; i2 < strArr.length; i2++) {
                    if (i2 > 0) {
                        stringBuffer.append(", ");
                    }
                    stringBuffer.append("topic=");
                    stringBuffer.append(strArr[i2]);
                    stringBuffer.append(" qos=");
                    stringBuffer.append(iArr[i2]);
                    MqttTopic.validate(strArr[i2], true);
                }
                log.fine(CLASS_NAME, "subscribe", "106", new Object[]{stringBuffer.toString(), obj, iMqttActionListener});
            }
            MqttToken mqttToken = new MqttToken(getClientId());
            mqttToken.setActionCallback(iMqttActionListener);
            mqttToken.setUserContext(obj);
            mqttToken.internalTok.setTopics(strArr);
            this.comms.sendNoWait(new MqttSubscribe(strArr, iArr), mqttToken);
            log.fine(CLASS_NAME, "subscribe", "109");
            return mqttToken;
        }
        throw new IllegalArgumentException();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken unsubscribe(String[] strArr, Object obj, IMqttActionListener iMqttActionListener) throws MqttException, IllegalArgumentException {
        if (log.isLoggable(5)) {
            String str = "";
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (i2 > 0) {
                    str = str + ", ";
                }
                str = str + strArr[i2];
            }
            log.fine(CLASS_NAME, "unsubscribe", "107", new Object[]{str, obj, iMqttActionListener});
        }
        for (String str2 : strArr) {
            MqttTopic.validate(str2, true);
        }
        for (String str3 : strArr) {
            this.comms.removeMessageListener(str3);
        }
        MqttToken mqttToken = new MqttToken(getClientId());
        mqttToken.setActionCallback(iMqttActionListener);
        mqttToken.setUserContext(obj);
        mqttToken.internalTok.setTopics(strArr);
        this.comms.sendNoWait(new MqttUnsubscribe(strArr), mqttToken);
        log.fine(CLASS_NAME, "unsubscribe", "110");
        return mqttToken;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken publish(String str, byte[] bArr, int i2, boolean z2) throws MqttException {
        return publish(str, bArr, i2, z2, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken publish(String str, MqttMessage mqttMessage) throws MqttException {
        return publish(str, mqttMessage, (Object) null, (IMqttActionListener) null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken publish(String str, MqttMessage mqttMessage, Object obj, IMqttActionListener iMqttActionListener) throws MqttException, IllegalArgumentException {
        Logger logger = log;
        String str2 = CLASS_NAME;
        logger.fine(str2, "publish", "111", new Object[]{str, obj, iMqttActionListener});
        MqttTopic.validate(str, false);
        MqttDeliveryToken mqttDeliveryToken = new MqttDeliveryToken(getClientId());
        mqttDeliveryToken.setActionCallback(iMqttActionListener);
        mqttDeliveryToken.setUserContext(obj);
        mqttDeliveryToken.a(mqttMessage);
        mqttDeliveryToken.internalTok.setTopics(new String[]{str});
        this.comms.sendNoWait(new MqttPublish(str, mqttMessage), mqttDeliveryToken);
        logger.fine(str2, "publish", "112");
        return mqttDeliveryToken;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String str, int i2, Object obj, IMqttActionListener iMqttActionListener, IMqttMessageListener iMqttMessageListener) throws MqttException {
        return subscribe(new String[]{str}, new int[]{i2}, obj, iMqttActionListener, new IMqttMessageListener[]{iMqttMessageListener});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String str, int i2, IMqttMessageListener iMqttMessageListener) throws MqttException {
        return subscribe(new String[]{str}, new int[]{i2}, (Object) null, (IMqttActionListener) null, new IMqttMessageListener[]{iMqttMessageListener});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String[] strArr, int[] iArr, IMqttMessageListener[] iMqttMessageListenerArr) throws MqttException {
        return subscribe(strArr, iArr, (Object) null, (IMqttActionListener) null, iMqttMessageListenerArr);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String[] strArr, int[] iArr, Object obj, IMqttActionListener iMqttActionListener, IMqttMessageListener[] iMqttMessageListenerArr) throws MqttException, IllegalArgumentException {
        if (iMqttMessageListenerArr.length == iArr.length && iArr.length == strArr.length) {
            IMqttToken iMqttTokenSubscribe = subscribe(strArr, iArr, obj, iMqttActionListener);
            for (int i2 = 0; i2 < strArr.length; i2++) {
                this.comms.setMessageListener(strArr[i2], iMqttMessageListenerArr[i2]);
            }
            return iMqttTokenSubscribe;
        }
        throw new IllegalArgumentException();
    }
}
