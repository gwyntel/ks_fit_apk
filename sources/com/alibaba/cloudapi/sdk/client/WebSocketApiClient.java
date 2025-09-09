package com.alibaba.cloudapi.sdk.client;

import android.util.Base64;
import android.util.Log;
import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.enums.WebSocketApiType;
import com.alibaba.cloudapi.sdk.enums.WebSocketConnectStatus;
import com.alibaba.cloudapi.sdk.exception.SdkException;
import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiContext;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.cloudapi.sdk.model.ApiWebSocketListner;
import com.alibaba.cloudapi.sdk.model.WebSocketApiRequest;
import com.alibaba.cloudapi.sdk.model.WebSocketClientBuilderParams;
import com.alibaba.cloudapi.sdk.signature.SignerFactoryManager;
import com.alibaba.cloudapi.sdk.util.ApiRequestMaker;
import com.alibaba.cloudapi.sdk.util.CallbackManager;
import com.alibaba.cloudapi.sdk.util.HeartBeatManager;
import com.alibaba.cloudapi.sdk.util.HttpCommonUtil;
import com.alibaba.cloudapi.sdk.util.ObjectReference;
import com.alibaba.fastjson.JSON;
import com.xiaomi.mipush.sdk.Constants;
import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class WebSocketApiClient extends BaseApiClient {
    ApiWebSocketListner apiWebSocketListner;
    CallbackManager callbackManager;
    Thread callbackThread;
    OkHttpClient client;
    Request connectRequest;
    String deviceId;
    HeartBeatManager heartBeatManager;
    Thread heartbeatThread;
    private WebSocketApiClient instance;
    ApiCallback lastRegisterCallback;
    ApiRequest lastRegisterReqeust;
    WebSocketListener webSocketListener;
    String websocketUrl;
    final ObjectReference<WebSocket> webSocketRef = new ObjectReference<>();
    final ObjectReference<CountDownLatch> connectLatch = new ObjectReference<>();
    final ObjectReference<CountDownLatch> registerLatch = new ObjectReference<>();
    final ObjectReference<Boolean> registerCommandSuccess = new ObjectReference<>();
    final ObjectReference<String> errorMessage = new ObjectReference<>();
    AtomicInteger seq = new AtomicInteger(0);
    WebSocketConnectStatus status = WebSocketConnectStatus.LOST_CONNECTION;
    final int port = 8080;
    String connectionCredential = "";
    boolean isRegister = false;
    int heartBeatInterval = 25000;
    Object connectionLock = new Object();

    protected WebSocketApiClient() {
    }

    private String buildRequest(ApiRequest apiRequest) {
        apiRequest.setHost(this.host);
        apiRequest.setScheme(this.scheme);
        ApiRequestMaker.make(apiRequest, this.appKey, this.appSecret);
        WebSocketApiRequest webSocketApiRequest = new WebSocketApiRequest();
        webSocketApiRequest.setHost(this.host);
        webSocketApiRequest.setPath(apiRequest.getPath());
        webSocketApiRequest.setMethod(apiRequest.getMethod().getValue());
        webSocketApiRequest.setQuerys(apiRequest.getQuerys());
        webSocketApiRequest.setHeaders(apiRequest.getHeaders());
        webSocketApiRequest.setIsBase64(!apiRequest.isBase64BodyViaWebsocket() ? 0 : 1);
        MediaType mediaType = MediaType.parse(apiRequest.getFirstHeaderValue("content-type"));
        if (apiRequest.getFormParams() != null && apiRequest.getFormParams().size() > 0) {
            webSocketApiRequest.setBody(HttpCommonUtil.buildParamString(apiRequest.getFormParams()));
        } else if (apiRequest.getBody() != null) {
            webSocketApiRequest.setBody(new String(apiRequest.getBody(), mediaType.charset(SdkConstant.CLOUDAPI_ENCODING)));
        }
        if (apiRequest.isBase64BodyViaWebsocket()) {
            webSocketApiRequest.setBody(new String(Base64.encode(apiRequest.getBody(), 0), mediaType.charset(SdkConstant.CLOUDAPI_ENCODING)));
        }
        return JSON.toJSONString(webSocketApiRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void close() {
        synchronized (this.connectionLock) {
            try {
                try {
                    this.connectLatch.setObj(new CountDownLatch(1));
                    HeartBeatManager heartBeatManager = this.heartBeatManager;
                    if (heartBeatManager != null) {
                        heartBeatManager.stop();
                    }
                    if (this.webSocketRef.getObj() != null) {
                        Thread.sleep(1000L);
                        this.webSocketRef.getObj().close(1000, "Reconnect");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private String generateDeviceId() {
        return UUID.randomUUID().toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "").substring(0, 8);
    }

    private String generateDeviceSum() {
        return generateDeviceId() + "@" + this.appKey;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postSendWebsocketCommandApi(WebSocketApiType webSocketApiType, ApiResponse apiResponse) {
        if (WebSocketApiType.REGISTER == webSocketApiType && 200 == apiResponse.getCode()) {
            this.isRegister = true;
        }
        if (WebSocketApiType.UNREGISTER == webSocketApiType) {
            HeartBeatManager heartBeatManager = this.heartBeatManager;
            if (heartBeatManager != null) {
                heartBeatManager.stop();
            }
            this.lastRegisterReqeust = null;
            this.lastRegisterCallback = null;
            this.isRegister = false;
        }
    }

    private boolean preSendWebsocketCommandApi(ApiRequest apiRequest, ApiCallback apiCallback) {
        if (WebSocketApiType.REGISTER == apiRequest.getWebSocketApiType()) {
            try {
                try {
                    if (this.registerLatch.getObj() != null && !this.registerLatch.getObj().await(10L, TimeUnit.SECONDS)) {
                        Thread.sleep(5000L);
                        close();
                        apiCallback.onFailure(apiRequest, new SdkException("WebSocket conection lost , connecting"));
                        return false;
                    }
                    this.registerLatch.setObj(null);
                    if (!this.registerCommandSuccess.getObj().booleanValue()) {
                        apiCallback.onFailure(null, new SdkException("Register Comand return error :" + this.errorMessage.getObj()));
                        return false;
                    }
                    this.lastRegisterReqeust = apiRequest.duplicate();
                    this.lastRegisterCallback = apiCallback;
                } catch (InterruptedException e2) {
                    throw new SdkException("WebSocket register failed ", e2);
                }
            } finally {
                this.registerLatch.setObj(null);
            }
        }
        apiRequest.addHeader(SdkConstant.CLOUDAPI_X_CA_WEBSOCKET_API_TYPE, apiRequest.getWebSocketApiType().toString());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reSendRegister() {
        sendAsyncRequest(this.lastRegisterReqeust, this.lastRegisterCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reconnect() {
        this.status = WebSocketConnectStatus.LOST_CONNECTION;
        HeartBeatManager heartBeatManager = this.heartBeatManager;
        if (heartBeatManager != null) {
            heartBeatManager.stop();
        }
        connect();
    }

    public void connect() {
        if (this.connectLatch.getObj() == null) {
            this.connectLatch.setObj(new CountDownLatch(1));
        }
        if (this.webSocketListener == null) {
            this.webSocketListener = new WebSocketListener() { // from class: com.alibaba.cloudapi.sdk.client.WebSocketApiClient.1
                @Override // okhttp3.WebSocketListener
                public void onClosed(WebSocket webSocket, int i2, String str) {
                    WebSocketApiClient.this.webSocketRef.setObj(null);
                    WebSocketApiClient.this.reconnect();
                }

                @Override // okhttp3.WebSocketListener
                public void onFailure(WebSocket webSocket, Throwable th, Response response) throws InterruptedException {
                    ApiResponse apiResponse;
                    try {
                        if (response != null) {
                            apiResponse = new ApiResponse(response.code());
                            apiResponse.setMessage(response.message());
                        } else {
                            apiResponse = new ApiResponse(505);
                            apiResponse.setMessage("WebSocket inner failed");
                        }
                        apiResponse.setEx(new SdkException(th));
                        WebSocketApiClient.this.apiWebSocketListner.onFailure(th, apiResponse);
                    } catch (Exception e2) {
                        Log.e("SDK", "Failure block", e2);
                    }
                    if (th != null) {
                        if ((th instanceof ConnectException) || (th instanceof SocketTimeoutException) || (th instanceof UnknownHostException)) {
                            if (WebSocketApiClient.this.connectLatch.getObj() != null) {
                                WebSocketApiClient.this.connectLatch.getObj().countDown();
                            }
                            try {
                                Thread.sleep(500L);
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                            WebSocketApiClient.this.reconnect();
                            return;
                        }
                        if ((th instanceof SocketException) || (th instanceof EOFException)) {
                            if (WebSocketApiClient.this.connectLatch.getObj() == null) {
                                WebSocketApiClient.this.connectLatch.setObj(new CountDownLatch(1));
                            }
                            try {
                                Thread.sleep(500L);
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                            WebSocketApiClient.this.reconnect();
                            return;
                        }
                        return;
                        Log.e("SDK", "Failure block", e2);
                    }
                }

                @Override // okhttp3.WebSocketListener
                public void onMessage(WebSocket webSocket, String str) throws NumberFormatException {
                    if (str == null || "".equalsIgnoreCase(str)) {
                        return;
                    }
                    if (str.length() > 2 && str.startsWith(SdkConstant.CLOUDAPI_COMMAND_HEART_BEAT_RESPONSE)) {
                        if (WebSocketApiClient.this.connectionCredential.equalsIgnoreCase(str.substring(3))) {
                            return;
                        }
                        WebSocketApiClient.this.reSendRegister();
                        return;
                    }
                    if (SdkConstant.CLOUDAPI_COMMAND_OVER_FLOW_BY_SECOND.equalsIgnoreCase(str)) {
                        Log.i("SDK", "oerflow by server");
                        WebSocketApiClient.this.close();
                        return;
                    }
                    if (SdkConstant.CLOUDAPI_COMMAND_CONNECTION_RUNS_OUT.equalsIgnoreCase(str)) {
                        Log.i("SDK", "bye by server");
                        WebSocketApiClient.this.close();
                        return;
                    }
                    if (str.length() > 2 && str.startsWith(SdkConstant.CLOUDAPI_COMMAND_REGISTER_FAIL_REQUEST)) {
                        WebSocketApiClient.this.registerCommandSuccess.setObj(Boolean.FALSE);
                        WebSocketApiClient.this.errorMessage.setObj(str.split(MqttTopic.MULTI_LEVEL_WILDCARD)[1]);
                        if (WebSocketApiClient.this.registerLatch.getObj() != null) {
                            WebSocketApiClient.this.registerLatch.getObj().countDown();
                        }
                        HeartBeatManager heartBeatManager = WebSocketApiClient.this.heartBeatManager;
                        if (heartBeatManager != null) {
                            heartBeatManager.stop();
                            return;
                        }
                        return;
                    }
                    if (str.length() > 2 && str.startsWith(SdkConstant.CLOUDAPI_COMMAND_REGISTER_SUCCESS_RESPONSE)) {
                        WebSocketApiClient.this.registerCommandSuccess.setObj(Boolean.TRUE);
                        String[] strArrSplit = str.split(MqttTopic.MULTI_LEVEL_WILDCARD);
                        WebSocketApiClient webSocketApiClient = WebSocketApiClient.this;
                        webSocketApiClient.connectionCredential = strArrSplit[1];
                        webSocketApiClient.heartBeatInterval = Integer.parseInt(strArrSplit[2]);
                        if (WebSocketApiClient.this.registerLatch.getObj() != null) {
                            WebSocketApiClient.this.registerLatch.getObj().countDown();
                        }
                        HeartBeatManager heartBeatManager2 = WebSocketApiClient.this.heartBeatManager;
                        if (heartBeatManager2 != null) {
                            heartBeatManager2.stop();
                        }
                        WebSocketApiClient webSocketApiClient2 = WebSocketApiClient.this;
                        webSocketApiClient2.heartBeatManager = new HeartBeatManager(webSocketApiClient2.instance, WebSocketApiClient.this.heartBeatInterval);
                        WebSocketApiClient.this.heartbeatThread = new Thread(WebSocketApiClient.this.heartBeatManager);
                        WebSocketApiClient.this.heartbeatThread.start();
                        WebSocketApiClient webSocketApiClient3 = WebSocketApiClient.this;
                        if (webSocketApiClient3.isRegister) {
                            webSocketApiClient3.reSendRegister();
                            return;
                        }
                        return;
                    }
                    if (str.length() > 2 && str.startsWith(SdkConstant.CLOUDAPI_COMMAND_NOTIFY_REQUEST)) {
                        WebSocketApiClient.this.apiWebSocketListner.onNotify(str.substring(3));
                        WebSocketApiClient webSocketApiClient4 = WebSocketApiClient.this;
                        if (webSocketApiClient4.status != WebSocketConnectStatus.CONNECTED || webSocketApiClient4.webSocketRef.getObj() == null) {
                            return;
                        }
                        WebSocketApiClient.this.webSocketRef.getObj().send(SdkConstant.CLOUDAPI_COMMAND_NOTIFY_RESPONSE);
                        return;
                    }
                    if (str.length() <= 2 || str.startsWith("{") || !MqttTopic.MULTI_LEVEL_WILDCARD.equalsIgnoreCase(str.substring(3, 4))) {
                        try {
                            ApiResponse apiResponse = new ApiResponse(JSON.parseObject(str));
                            int i2 = Integer.parseInt(apiResponse.getFirstHeaderValue(SdkConstant.CLOUDAPI_X_CA_SEQ));
                            WebSocketApiType webSocketApiType = WebSocketApiClient.this.callbackManager.getContext(Integer.valueOf(i2)).getRequest().getWebSocketApiType();
                            if (webSocketApiType != WebSocketApiType.COMMON) {
                                WebSocketApiClient.this.postSendWebsocketCommandApi(webSocketApiType, apiResponse);
                            }
                            WebSocketApiClient.this.callbackManager.callback(i2, apiResponse);
                        } catch (Exception e2) {
                            Log.e("SDK", "Call back occue error", e2);
                        }
                    }
                }

                @Override // okhttp3.WebSocketListener
                public void onOpen(WebSocket webSocket, Response response) {
                    WebSocketApiClient.this.webSocketRef.setObj(webSocket);
                    WebSocketApiClient webSocketApiClient = WebSocketApiClient.this;
                    webSocketApiClient.status = WebSocketConnectStatus.CONNECTED;
                    webSocketApiClient.registerLatch.setObj(new CountDownLatch(1));
                    WebSocketApiClient.this.webSocketRef.getObj().send("RG#" + WebSocketApiClient.this.deviceId);
                    if (WebSocketApiClient.this.connectLatch.getObj() != null) {
                        WebSocketApiClient.this.connectLatch.getObj().countDown();
                    }
                }
            };
        }
        this.client.newWebSocket(this.connectRequest, this.webSocketListener);
    }

    protected String getDeviceId() {
        return this.deviceId;
    }

    public WebSocketConnectStatus getStatus() {
        return this.status;
    }

    protected void init(WebSocketClientBuilderParams webSocketClientBuilderParams) {
        if (webSocketClientBuilderParams == null) {
            throw new SdkException("WebSocketClientBuilderParams must not be null");
        }
        webSocketClientBuilderParams.check();
        this.appKey = webSocketClientBuilderParams.getAppKey();
        this.appSecret = webSocketClientBuilderParams.getAppSecret();
        this.deviceId = generateDeviceSum();
        StringBuilder sb = new StringBuilder();
        Scheme scheme = Scheme.WEBSOCKET;
        sb.append(scheme.getValue());
        sb.append(webSocketClientBuilderParams.getHost());
        this.websocketUrl = sb.toString();
        this.websocketUrl += ":8080";
        this.host = webSocketClientBuilderParams.getHost();
        this.scheme = scheme;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        long readTimeout = webSocketClientBuilderParams.getReadTimeout();
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        this.client = builder.readTimeout(readTimeout, timeUnit).writeTimeout(webSocketClientBuilderParams.getWriteTimeout(), timeUnit).connectTimeout(webSocketClientBuilderParams.getConnectionTimeout(), timeUnit).build();
        this.connectRequest = new Request.Builder().url(this.websocketUrl).build();
        this.apiWebSocketListner = webSocketClientBuilderParams.getApiWebSocketListner();
        this.callbackManager = new CallbackManager(webSocketClientBuilderParams.getCallbackThreadPoolCount(), webSocketClientBuilderParams.getRequestExpiredTime());
        Thread thread = new Thread(this.callbackManager);
        this.callbackThread = thread;
        thread.start();
        connect();
        SignerFactoryManager.init();
        this.registerCommandSuccess.setObj(Boolean.FALSE);
        this.errorMessage.setObj("");
        this.instance = this;
        this.isInit = true;
    }

    public boolean isOnline() {
        return this.isRegister && this.status == WebSocketConnectStatus.CONNECTED;
    }

    public boolean isRegister() {
        return this.isRegister;
    }

    @Override // com.alibaba.cloudapi.sdk.client.BaseApiClient
    protected void sendAsyncRequest(ApiRequest apiRequest, ApiCallback apiCallback) {
        checkIsInit();
        synchronized (this.connectionLock) {
            if (this.connectLatch.getObj() != null && this.connectLatch.getObj().getCount() == 1) {
                try {
                    try {
                        this.connectLatch.getObj().await(10L, TimeUnit.SECONDS);
                    } catch (InterruptedException e2) {
                        throw new SdkException("WebSocket connect server failed ", e2);
                    }
                } finally {
                    this.connectLatch.setObj(null);
                }
            }
            if (this.status == WebSocketConnectStatus.LOST_CONNECTION) {
                apiCallback.onFailure(apiRequest, new SdkException("WebSocket conection lost , connecting"));
                return;
            }
            if (WebSocketApiType.COMMON == apiRequest.getWebSocketApiType() || preSendWebsocketCommandApi(apiRequest, apiCallback)) {
                Integer numValueOf = Integer.valueOf(this.seq.getAndIncrement());
                apiRequest.addHeader(SdkConstant.CLOUDAPI_X_CA_SEQ, numValueOf.toString());
                this.callbackManager.add(numValueOf, new ApiContext(apiCallback, apiRequest));
                this.webSocketRef.getObj().send(buildRequest(apiRequest));
            }
        }
    }

    public void sendHeatbeart() {
        if (this.isInit && this.status == WebSocketConnectStatus.CONNECTED && this.webSocketRef.getObj() != null) {
            this.webSocketRef.getObj().send(SdkConstant.CLOUDAPI_COMMAND_HEART_BEAT_REQUEST);
        }
    }

    @Override // com.alibaba.cloudapi.sdk.client.BaseApiClient
    protected ApiResponse sendSyncRequest(ApiRequest apiRequest) {
        throw new SdkException("Not support sending sync request via websocket channel");
    }

    public void setStatus(WebSocketConnectStatus webSocketConnectStatus) {
        this.status = webSocketConnectStatus;
    }
}
