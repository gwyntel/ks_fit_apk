package org.eclipse.paho.client.mqttv3.internal;

import com.taobao.accs.utl.BaseMonitor;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.BufferedMessage;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class ClientComms {
    public static String BUILD_LEVEL = "L${build.level}";
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.ClientComms";
    private static final byte CLOSED = 4;
    private static final byte CONNECTED = 0;
    private static final byte CONNECTING = 1;
    private static final byte DISCONNECTED = 3;
    private static final byte DISCONNECTING = 2;
    public static String VERSION = "${project.version}";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, ClientComms.class.getName());
    private CommsCallback callback;
    private IMqttAsyncClient client;
    private ClientState clientState;
    private MqttConnectOptions conOptions;
    private DisconnectedMessageBuffer disconnectedMessageBuffer;
    private ExecutorService executorService;
    private int networkModuleIndex;
    private NetworkModule[] networkModules;
    private MqttClientPersistence persistence;
    private MqttPingSender pingSender;
    private CommsReceiver receiver;
    private CommsSender sender;
    private CommsTokenStore tokenStore;
    private boolean stoppingComms = false;
    private Object conLock = new Object();
    private boolean closePending = false;
    private boolean resting = false;
    private byte conState = 3;

    private class ConnectBG implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        ClientComms f26664a;

        /* renamed from: b, reason: collision with root package name */
        MqttToken f26665b;

        /* renamed from: c, reason: collision with root package name */
        MqttConnect f26666c;
        private String threadName;

        ConnectBG(ClientComms clientComms, MqttToken mqttToken, MqttConnect mqttConnect, ExecutorService executorService) {
            this.f26664a = clientComms;
            this.f26665b = mqttToken;
            this.f26666c = mqttConnect;
            this.threadName = "MQTT Con: " + ClientComms.this.getClient().getClientId();
        }

        void a() {
            ClientComms.this.executorService.execute(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            MqttException mqttExceptionCreateMqttException;
            Thread.currentThread().setName(this.threadName);
            ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "220");
            try {
                MqttDeliveryToken[] outstandingDelTokens = ClientComms.this.tokenStore.getOutstandingDelTokens();
                int i2 = 0;
                while (true) {
                    mqttExceptionCreateMqttException = null;
                    if (i2 >= outstandingDelTokens.length) {
                        break;
                    }
                    outstandingDelTokens[i2].internalTok.setException(null);
                    i2++;
                }
                ClientComms.this.tokenStore.d(this.f26665b, this.f26666c);
                NetworkModule networkModule = ClientComms.this.networkModules[ClientComms.this.networkModuleIndex];
                networkModule.start();
                ClientComms.this.receiver = new CommsReceiver(this.f26664a, ClientComms.this.clientState, ClientComms.this.tokenStore, networkModule.getInputStream());
                ClientComms.this.receiver.start("MQTT Rec: " + ClientComms.this.getClient().getClientId(), ClientComms.this.executorService);
                ClientComms.this.sender = new CommsSender(this.f26664a, ClientComms.this.clientState, ClientComms.this.tokenStore, networkModule.getOutputStream());
                ClientComms.this.sender.start("MQTT Snd: " + ClientComms.this.getClient().getClientId(), ClientComms.this.executorService);
                ClientComms.this.callback.start("MQTT Call: " + ClientComms.this.getClient().getClientId(), ClientComms.this.executorService);
                ClientComms.this.o(this.f26666c, this.f26665b);
            } catch (MqttException e2) {
                ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "212", null, e2);
                mqttExceptionCreateMqttException = e2;
            } catch (Exception e3) {
                ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "209", null, e3);
                mqttExceptionCreateMqttException = ExceptionHelper.createMqttException(e3);
            }
            if (mqttExceptionCreateMqttException != null) {
                ClientComms.this.shutdownConnection(this.f26665b, mqttExceptionCreateMqttException);
            }
        }
    }

    private class DisconnectBG implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        MqttDisconnect f26668a;

        /* renamed from: b, reason: collision with root package name */
        long f26669b;

        /* renamed from: c, reason: collision with root package name */
        MqttToken f26670c;
        private String threadName;

        DisconnectBG(MqttDisconnect mqttDisconnect, long j2, MqttToken mqttToken, ExecutorService executorService) {
            this.f26668a = mqttDisconnect;
            this.f26669b = j2;
            this.f26670c = mqttToken;
        }

        void a() {
            this.threadName = "MQTT Disc: " + ClientComms.this.getClient().getClientId();
            ClientComms.this.executorService.execute(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            Thread.currentThread().setName(this.threadName);
            ClientComms.log.fine(ClientComms.CLASS_NAME, "disconnectBG:run", "221");
            ClientComms.this.clientState.quiesce(this.f26669b);
            try {
                ClientComms.this.o(this.f26668a, this.f26670c);
                this.f26670c.internalTok.waitUntilSent();
            } catch (MqttException unused) {
            } catch (Throwable th) {
                this.f26670c.internalTok.c(null, null);
                ClientComms.this.shutdownConnection(this.f26670c, null);
                throw th;
            }
            this.f26670c.internalTok.c(null, null);
            ClientComms.this.shutdownConnection(this.f26670c, null);
        }
    }

    class ReconnectDisconnectedBufferCallback implements IDisconnectedBufferCallback {

        /* renamed from: a, reason: collision with root package name */
        final String f26672a;

        ReconnectDisconnectedBufferCallback(String str) {
            this.f26672a = str;
        }

        @Override // org.eclipse.paho.client.mqttv3.internal.IDisconnectedBufferCallback
        public void publishBufferedMessage(BufferedMessage bufferedMessage) throws MqttException {
            if (!ClientComms.this.isConnected()) {
                ClientComms.log.fine(ClientComms.CLASS_NAME, this.f26672a, "208");
                throw ExceptionHelper.createMqttException(32104);
            }
            while (ClientComms.this.clientState.getActualInFlight() >= ClientComms.this.clientState.getMaxInFlight() - 1) {
                Thread.yield();
            }
            ClientComms.log.fine(ClientComms.CLASS_NAME, this.f26672a, "510", new Object[]{bufferedMessage.getMessage().getKey()});
            ClientComms.this.o(bufferedMessage.getMessage(), bufferedMessage.getToken());
            ClientComms.this.clientState.unPersistBufferedMessage(bufferedMessage.getMessage());
        }
    }

    public ClientComms(IMqttAsyncClient iMqttAsyncClient, MqttClientPersistence mqttClientPersistence, MqttPingSender mqttPingSender, ExecutorService executorService) throws MqttException {
        this.client = iMqttAsyncClient;
        this.persistence = mqttClientPersistence;
        this.pingSender = mqttPingSender;
        mqttPingSender.init(this);
        this.executorService = executorService;
        this.tokenStore = new CommsTokenStore(getClient().getClientId());
        this.callback = new CommsCallback(this);
        ClientState clientState = new ClientState(mqttClientPersistence, this.tokenStore, this.callback, this, mqttPingSender);
        this.clientState = clientState;
        this.callback.setClientState(clientState);
        log.setResourceName(getClient().getClientId());
    }

    private MqttToken handleOldTokens(MqttToken mqttToken, MqttException mqttException) {
        log.fine(CLASS_NAME, "handleOldTokens", "222");
        MqttToken mqttToken2 = null;
        if (mqttToken != null) {
            try {
                if (this.tokenStore.getToken(mqttToken.internalTok.getKey()) == null) {
                    this.tokenStore.c(mqttToken, mqttToken.internalTok.getKey());
                }
            } catch (Exception unused) {
            }
        }
        Enumeration enumerationElements = this.clientState.resolveOldTokens(mqttException).elements();
        while (enumerationElements.hasMoreElements()) {
            MqttToken mqttToken3 = (MqttToken) enumerationElements.nextElement();
            if (mqttToken3.internalTok.getKey().equals(MqttDisconnect.KEY) || mqttToken3.internalTok.getKey().equals("Con")) {
                mqttToken2 = mqttToken3;
            } else {
                this.callback.asyncOperationComplete(mqttToken3);
            }
        }
        return mqttToken2;
    }

    private void handleRunException(Exception exc) {
        log.fine(CLASS_NAME, "handleRunException", "804", null, exc);
        shutdownConnection(null, !(exc instanceof MqttException) ? new MqttException(32109, exc) : (MqttException) exc);
    }

    private void shutdownExecutorService() {
        this.executorService.shutdown();
        try {
            ExecutorService executorService = this.executorService;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            if (executorService.awaitTermination(1L, timeUnit)) {
                return;
            }
            this.executorService.shutdownNow();
            if (this.executorService.awaitTermination(1L, timeUnit)) {
                return;
            }
            log.fine(CLASS_NAME, "shutdownExecutorService", "executorService did not terminate");
        } catch (InterruptedException unused) {
            this.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public MqttToken checkForActivity() {
        return checkForActivity(null);
    }

    public void close(boolean z2) throws MqttException {
        synchronized (this.conLock) {
            try {
                if (!isClosed()) {
                    if (!isDisconnected() || z2) {
                        log.fine(CLASS_NAME, "close", "224");
                        if (isConnecting()) {
                            throw new MqttException(32110);
                        }
                        if (isConnected()) {
                            throw ExceptionHelper.createMqttException(32100);
                        }
                        if (isDisconnecting()) {
                            this.closePending = true;
                            return;
                        }
                    }
                    this.conState = (byte) 4;
                    shutdownExecutorService();
                    this.clientState.c();
                    this.clientState = null;
                    this.callback = null;
                    this.persistence = null;
                    this.sender = null;
                    this.pingSender = null;
                    this.receiver = null;
                    this.networkModules = null;
                    this.conOptions = null;
                    this.tokenStore = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void connect(MqttConnectOptions mqttConnectOptions, MqttToken mqttToken) throws MqttException {
        synchronized (this.conLock) {
            try {
                if (!isDisconnected() || this.closePending) {
                    log.fine(CLASS_NAME, BaseMonitor.ALARM_POINT_CONNECT, "207", new Object[]{new Byte(this.conState)});
                    if (isClosed() || this.closePending) {
                        throw new MqttException(32111);
                    }
                    if (isConnecting()) {
                        throw new MqttException(32110);
                    }
                    if (!isDisconnecting()) {
                        throw ExceptionHelper.createMqttException(32100);
                    }
                    throw new MqttException(32102);
                }
                log.fine(CLASS_NAME, BaseMonitor.ALARM_POINT_CONNECT, "214");
                this.conState = (byte) 1;
                this.conOptions = mqttConnectOptions;
                MqttConnect mqttConnect = new MqttConnect(this.client.getClientId(), this.conOptions.getMqttVersion(), this.conOptions.isCleanSession(), this.conOptions.getKeepAliveInterval(), this.conOptions.getUserName(), this.conOptions.getPassword(), this.conOptions.getWillMessage(), this.conOptions.getWillDestination());
                this.clientState.p(this.conOptions.getKeepAliveInterval());
                this.clientState.o(this.conOptions.isCleanSession());
                this.clientState.q(this.conOptions.getMaxInflight());
                this.tokenStore.open();
                new ConnectBG(this, mqttToken, mqttConnect, this.executorService).a();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void connectComplete(MqttConnack mqttConnack, MqttException mqttException) throws MqttException {
        int returnCode = mqttConnack.getReturnCode();
        synchronized (this.conLock) {
            try {
                if (returnCode != 0) {
                    log.fine(CLASS_NAME, "connectComplete", "204", new Object[]{new Integer(returnCode)});
                    throw mqttException;
                }
                log.fine(CLASS_NAME, "connectComplete", "215");
                this.conState = (byte) 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void deleteBufferedMessage(int i2) {
        this.disconnectedMessageBuffer.deleteMessage(i2);
    }

    public void disconnect(MqttDisconnect mqttDisconnect, long j2, MqttToken mqttToken) throws MqttException {
        synchronized (this.conLock) {
            try {
                if (isClosed()) {
                    log.fine(CLASS_NAME, "disconnect", "223");
                    throw ExceptionHelper.createMqttException(32111);
                }
                if (isDisconnected()) {
                    log.fine(CLASS_NAME, "disconnect", "211");
                    throw ExceptionHelper.createMqttException(32101);
                }
                if (isDisconnecting()) {
                    log.fine(CLASS_NAME, "disconnect", "219");
                    throw ExceptionHelper.createMqttException(32102);
                }
                if (Thread.currentThread() == this.callback.b()) {
                    log.fine(CLASS_NAME, "disconnect", "210");
                    throw ExceptionHelper.createMqttException(32107);
                }
                log.fine(CLASS_NAME, "disconnect", "218");
                this.conState = (byte) 2;
                new DisconnectBG(mqttDisconnect, j2, mqttToken, this.executorService).a();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void disconnectForcibly(long j2, long j3) throws MqttException {
        disconnectForcibly(j2, j3, true);
    }

    public int getActualInFlight() {
        return this.clientState.getActualInFlight();
    }

    public MqttMessage getBufferedMessage(int i2) {
        return ((MqttPublish) this.disconnectedMessageBuffer.getMessage(i2).getMessage()).getMessage();
    }

    public int getBufferedMessageCount() {
        return this.disconnectedMessageBuffer.getMessageCount();
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    public ClientState getClientState() {
        return this.clientState;
    }

    public MqttConnectOptions getConOptions() {
        return this.conOptions;
    }

    public Properties getDebug() {
        Properties properties = new Properties();
        properties.put("conState", new Integer(this.conState));
        properties.put("serverURI", getClient().getServerURI());
        properties.put("callback", this.callback);
        properties.put("stoppingComms", new Boolean(this.stoppingComms));
        return properties;
    }

    public long getKeepAlive() {
        return this.clientState.h();
    }

    public int getNetworkModuleIndex() {
        return this.networkModuleIndex;
    }

    public NetworkModule[] getNetworkModules() {
        return this.networkModules;
    }

    public MqttDeliveryToken[] getPendingDeliveryTokens() {
        return this.tokenStore.getOutstandingDelTokens();
    }

    public MqttPingSender getPingSender() {
        return this.pingSender;
    }

    public boolean isClosed() {
        boolean z2;
        synchronized (this.conLock) {
            z2 = this.conState == 4;
        }
        return z2;
    }

    public boolean isConnected() {
        boolean z2;
        synchronized (this.conLock) {
            z2 = this.conState == 0;
        }
        return z2;
    }

    public boolean isConnecting() {
        boolean z2;
        synchronized (this.conLock) {
            z2 = true;
            if (this.conState != 1) {
                z2 = false;
            }
        }
        return z2;
    }

    public boolean isDisconnected() {
        boolean z2;
        synchronized (this.conLock) {
            z2 = this.conState == 3;
        }
        return z2;
    }

    public boolean isDisconnecting() {
        boolean z2;
        synchronized (this.conLock) {
            z2 = this.conState == 2;
        }
        return z2;
    }

    public boolean isResting() {
        boolean z2;
        synchronized (this.conLock) {
            z2 = this.resting;
        }
        return z2;
    }

    protected void m(int i2) {
        this.clientState.d(i2);
    }

    public void messageArrivedComplete(int i2, int i3) throws MqttException {
        this.callback.messageArrivedComplete(i2, i3);
    }

    protected void n(MqttPublish mqttPublish) {
        this.clientState.e(mqttPublish);
    }

    public void notifyConnect() {
        if (this.disconnectedMessageBuffer != null) {
            log.fine(CLASS_NAME, "notifyConnect", "509");
            this.disconnectedMessageBuffer.setPublishCallback(new ReconnectDisconnectedBufferCallback("notifyConnect"));
            this.executorService.execute(this.disconnectedMessageBuffer);
        }
    }

    void o(MqttWireMessage mqttWireMessage, MqttToken mqttToken) throws MqttException {
        Logger logger = log;
        String str = CLASS_NAME;
        logger.fine(str, "internalSend", "200", new Object[]{mqttWireMessage.getKey(), mqttWireMessage, mqttToken});
        if (mqttToken.getClient() != null) {
            logger.fine(str, "internalSend", "213", new Object[]{mqttWireMessage.getKey(), mqttWireMessage, mqttToken});
            throw new MqttException(32201);
        }
        mqttToken.internalTok.f(getClient());
        try {
            this.clientState.send(mqttWireMessage, mqttToken);
        } catch (MqttException e2) {
            if (mqttWireMessage instanceof MqttPublish) {
                this.clientState.r((MqttPublish) mqttWireMessage);
            }
            throw e2;
        }
    }

    public void removeMessageListener(String str) {
        this.callback.removeMessageListener(str);
    }

    public void sendNoWait(MqttWireMessage mqttWireMessage, MqttToken mqttToken) throws MqttException {
        if (!isConnected() && ((isConnected() || !(mqttWireMessage instanceof MqttConnect)) && (!isDisconnecting() || !(mqttWireMessage instanceof MqttDisconnect)))) {
            if (this.disconnectedMessageBuffer == null) {
                log.fine(CLASS_NAME, "sendNoWait", "208");
                throw ExceptionHelper.createMqttException(32104);
            }
            log.fine(CLASS_NAME, "sendNoWait", "508", new Object[]{mqttWireMessage.getKey()});
            if (this.disconnectedMessageBuffer.isPersistBuffer()) {
                this.clientState.persistBufferedMessage(mqttWireMessage);
            }
            this.disconnectedMessageBuffer.putMessage(mqttWireMessage, mqttToken);
            return;
        }
        DisconnectedMessageBuffer disconnectedMessageBuffer = this.disconnectedMessageBuffer;
        if (disconnectedMessageBuffer == null || disconnectedMessageBuffer.getMessageCount() == 0) {
            o(mqttWireMessage, mqttToken);
            return;
        }
        log.fine(CLASS_NAME, "sendNoWait", "507", new Object[]{mqttWireMessage.getKey()});
        if (this.disconnectedMessageBuffer.isPersistBuffer()) {
            this.clientState.persistBufferedMessage(mqttWireMessage);
        }
        this.disconnectedMessageBuffer.putMessage(mqttWireMessage, mqttToken);
    }

    public void setCallback(MqttCallback mqttCallback) {
        this.callback.setCallback(mqttCallback);
    }

    public void setDisconnectedMessageBuffer(DisconnectedMessageBuffer disconnectedMessageBuffer) {
        this.disconnectedMessageBuffer = disconnectedMessageBuffer;
    }

    public void setManualAcks(boolean z2) {
        this.callback.setManualAcks(z2);
    }

    public void setMessageListener(String str, IMqttMessageListener iMqttMessageListener) {
        this.callback.setMessageListener(str, iMqttMessageListener);
    }

    public void setNetworkModuleIndex(int i2) {
        this.networkModuleIndex = i2;
    }

    public void setNetworkModules(NetworkModule[] networkModuleArr) {
        this.networkModules = networkModuleArr;
    }

    public void setReconnectCallback(MqttCallbackExtended mqttCallbackExtended) {
        this.callback.setReconnectCallback(mqttCallbackExtended);
    }

    public void setRestingState(boolean z2) {
        this.resting = z2;
    }

    public void shutdownConnection(MqttToken mqttToken, MqttException mqttException) {
        CommsCallback commsCallback;
        MqttClientPersistence mqttClientPersistence;
        NetworkModule networkModule;
        synchronized (this.conLock) {
            try {
                if (!this.stoppingComms && !this.closePending && !isClosed()) {
                    this.stoppingComms = true;
                    log.fine(CLASS_NAME, "shutdownConnection", "216");
                    boolean z2 = isConnected() || isDisconnecting();
                    this.conState = (byte) 2;
                    if (mqttToken != null && !mqttToken.isComplete()) {
                        mqttToken.internalTok.setException(mqttException);
                    }
                    CommsCallback commsCallback2 = this.callback;
                    if (commsCallback2 != null) {
                        commsCallback2.stop();
                    }
                    CommsReceiver commsReceiver = this.receiver;
                    if (commsReceiver != null) {
                        commsReceiver.stop();
                    }
                    try {
                        NetworkModule[] networkModuleArr = this.networkModules;
                        if (networkModuleArr != null && (networkModule = networkModuleArr[this.networkModuleIndex]) != null) {
                            networkModule.stop();
                        }
                    } catch (Exception unused) {
                    }
                    this.tokenStore.a(new MqttException(32102));
                    MqttToken mqttTokenHandleOldTokens = handleOldTokens(mqttToken, mqttException);
                    try {
                        this.clientState.disconnected(mqttException);
                        if (this.clientState.g()) {
                            this.callback.removeMessageListeners();
                        }
                    } catch (Exception unused2) {
                    }
                    CommsSender commsSender = this.sender;
                    if (commsSender != null) {
                        commsSender.stop();
                    }
                    MqttPingSender mqttPingSender = this.pingSender;
                    if (mqttPingSender != null) {
                        mqttPingSender.stop();
                    }
                    try {
                        if (this.disconnectedMessageBuffer == null && (mqttClientPersistence = this.persistence) != null) {
                            mqttClientPersistence.close();
                        }
                    } catch (Exception unused3) {
                    }
                    synchronized (this.conLock) {
                        log.fine(CLASS_NAME, "shutdownConnection", "217");
                        this.conState = (byte) 3;
                        this.stoppingComms = false;
                    }
                    boolean z3 = mqttTokenHandleOldTokens != null;
                    CommsCallback commsCallback3 = this.callback;
                    if (z3 & (commsCallback3 != null)) {
                        commsCallback3.asyncOperationComplete(mqttTokenHandleOldTokens);
                    }
                    if (z2 && (commsCallback = this.callback) != null) {
                        commsCallback.connectionLost(mqttException);
                    }
                    synchronized (this.conLock) {
                        if (this.closePending) {
                            try {
                                close(true);
                            } catch (Exception unused4) {
                            }
                        }
                    }
                }
            } finally {
            }
        }
    }

    public MqttToken checkForActivity(IMqttActionListener iMqttActionListener) {
        try {
            return this.clientState.checkForActivity(iMqttActionListener);
        } catch (MqttException e2) {
            handleRunException(e2);
            return null;
        } catch (Exception e3) {
            handleRunException(e3);
            return null;
        }
    }

    public void disconnectForcibly(long j2, long j3, boolean z2) throws MqttException {
        ClientState clientState = this.clientState;
        if (clientState != null) {
            clientState.quiesce(j2);
        }
        MqttToken mqttToken = new MqttToken(this.client.getClientId());
        if (z2) {
            try {
                o(new MqttDisconnect(), mqttToken);
                mqttToken.waitForCompletion(j3);
            } catch (Exception unused) {
            } catch (Throwable th) {
                mqttToken.internalTok.c(null, null);
                shutdownConnection(mqttToken, null);
                throw th;
            }
        }
        mqttToken.internalTok.c(null, null);
        shutdownConnection(mqttToken, null);
    }
}
