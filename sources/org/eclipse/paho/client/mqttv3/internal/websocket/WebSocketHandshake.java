package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes5.dex */
public class WebSocketHandshake {
    private static final String ACCEPT_SALT = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private static final String EMPTY = "";
    private static final String HTTP_HEADER_CONNECTION = "connection";
    private static final String HTTP_HEADER_CONNECTION_VALUE = "upgrade";
    private static final String HTTP_HEADER_SEC_WEBSOCKET_ACCEPT = "sec-websocket-accept";
    private static final String HTTP_HEADER_SEC_WEBSOCKET_PROTOCOL = "sec-websocket-protocol";
    private static final String HTTP_HEADER_UPGRADE = "upgrade";
    private static final String HTTP_HEADER_UPGRADE_WEBSOCKET = "websocket";
    private static final String LINE_SEPARATOR = "\r\n";
    private static final String SHA1_PROTOCOL = "SHA1";

    /* renamed from: a, reason: collision with root package name */
    InputStream f26679a;

    /* renamed from: b, reason: collision with root package name */
    OutputStream f26680b;

    /* renamed from: c, reason: collision with root package name */
    String f26681c;

    /* renamed from: d, reason: collision with root package name */
    String f26682d;

    /* renamed from: e, reason: collision with root package name */
    int f26683e;

    public WebSocketHandshake(InputStream inputStream, OutputStream outputStream, String str, String str2, int i2) {
        this.f26679a = inputStream;
        this.f26680b = outputStream;
        this.f26681c = str;
        this.f26682d = str2;
        this.f26683e = i2;
    }

    private Map getHeaders(ArrayList arrayList) {
        HashMap map = new HashMap();
        for (int i2 = 1; i2 < arrayList.size(); i2++) {
            String[] strArrSplit = ((String) arrayList.get(i2)).split(":");
            map.put(strArrSplit[0].toLowerCase(), strArrSplit[1]);
        }
        return map;
    }

    private void receiveHandshakeResponse(String str) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.f26679a));
        ArrayList arrayList = new ArrayList();
        String line = bufferedReader.readLine();
        if (line == null) {
            throw new IOException("WebSocket Response header: Invalid response from Server, It may not support WebSockets.");
        }
        while (!line.equals("")) {
            arrayList.add(line);
            line = bufferedReader.readLine();
        }
        Map headers = getHeaders(arrayList);
        String str2 = (String) headers.get(HTTP_HEADER_CONNECTION);
        if (str2 == null || str2.equalsIgnoreCase("upgrade")) {
            throw new IOException("WebSocket Response header: Incorrect connection header");
        }
        String str3 = (String) headers.get("upgrade");
        if (str3 == null || !str3.toLowerCase().contains(HTTP_HEADER_UPGRADE_WEBSOCKET)) {
            throw new IOException("WebSocket Response header: Incorrect upgrade.");
        }
        if (((String) headers.get(HTTP_HEADER_SEC_WEBSOCKET_PROTOCOL)) == null) {
            throw new IOException("WebSocket Response header: empty sec-websocket-protocol");
        }
        if (!headers.containsKey(HTTP_HEADER_SEC_WEBSOCKET_ACCEPT)) {
            throw new IOException("WebSocket Response header: Missing Sec-WebSocket-Accept");
        }
        try {
            verifyWebSocketKey(str, (String) headers.get(HTTP_HEADER_SEC_WEBSOCKET_ACCEPT));
        } catch (NoSuchAlgorithmException e2) {
            throw new IOException(e2.getMessage());
        } catch (HandshakeFailedException unused) {
            throw new IOException("WebSocket Response header: Incorrect Sec-WebSocket-Key");
        }
    }

    private void sendHandshakeRequest(String str) throws IOException {
        try {
            String rawPath = "/mqtt";
            URI uri = new URI(this.f26681c);
            if (uri.getRawPath() != null && !uri.getRawPath().isEmpty()) {
                rawPath = uri.getRawPath();
                if (uri.getRawQuery() != null && !uri.getRawQuery().isEmpty()) {
                    rawPath = rawPath + "?" + uri.getRawQuery();
                }
            }
            PrintWriter printWriter = new PrintWriter(this.f26680b);
            printWriter.print("GET " + rawPath + " HTTP/1.1\r\n");
            int i2 = this.f26683e;
            if (i2 == 80 || i2 == 443) {
                printWriter.print("Host: " + this.f26682d + "\r\n");
            } else {
                printWriter.print("Host: " + this.f26682d + ":" + this.f26683e + "\r\n");
            }
            printWriter.print("Upgrade: websocket\r\n");
            printWriter.print("Connection: Upgrade\r\n");
            printWriter.print("Sec-WebSocket-Key: " + str + "\r\n");
            printWriter.print("Sec-WebSocket-Protocol: mqtt\r\n");
            printWriter.print("Sec-WebSocket-Version: 13\r\n");
            String userInfo = uri.getUserInfo();
            if (userInfo != null) {
                printWriter.print("Authorization: Basic " + Base64.encode(userInfo) + "\r\n");
            }
            printWriter.print("\r\n");
            printWriter.flush();
        } catch (URISyntaxException e2) {
            throw new IllegalStateException(e2.getMessage());
        }
    }

    private byte[] sha1(String str) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(SHA1_PROTOCOL).digest(str.getBytes());
    }

    private void verifyWebSocketKey(String str, String str2) throws HandshakeFailedException, NoSuchAlgorithmException {
        if (!Base64.encodeBytes(sha1(str + ACCEPT_SALT)).trim().equals(str2.trim())) {
            throw new HandshakeFailedException();
        }
    }

    public void execute() throws IOException {
        byte[] bArr = new byte[16];
        System.arraycopy(UUID.randomUUID().toString().getBytes(), 0, bArr, 0, 16);
        String strEncodeBytes = Base64.encodeBytes(bArr);
        sendHandshakeRequest(strEncodeBytes);
        receiveHandshakeResponse(strEncodeBytes);
    }
}
