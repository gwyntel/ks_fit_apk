package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* loaded from: classes5.dex */
class ExtendedByteArrayOutputStream extends ByteArrayOutputStream {

    /* renamed from: a, reason: collision with root package name */
    final WebSocketNetworkModule f26677a;

    /* renamed from: b, reason: collision with root package name */
    final WebSocketSecureNetworkModule f26678b;

    ExtendedByteArrayOutputStream(WebSocketNetworkModule webSocketNetworkModule) {
        this.f26677a = webSocketNetworkModule;
        this.f26678b = null;
    }

    OutputStream e() {
        WebSocketNetworkModule webSocketNetworkModule = this.f26677a;
        if (webSocketNetworkModule != null) {
            return webSocketNetworkModule.b();
        }
        WebSocketSecureNetworkModule webSocketSecureNetworkModule = this.f26678b;
        if (webSocketSecureNetworkModule != null) {
            return webSocketSecureNetworkModule.b();
        }
        return null;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        ByteBuffer byteBufferWrap;
        synchronized (this) {
            byteBufferWrap = ByteBuffer.wrap(toByteArray());
            reset();
        }
        e().write(new WebSocketFrame((byte) 2, true, byteBufferWrap.array()).encodeFrame());
        e().flush();
    }

    ExtendedByteArrayOutputStream(WebSocketSecureNetworkModule webSocketSecureNetworkModule) {
        this.f26677a = null;
        this.f26678b = webSocketSecureNetworkModule;
    }
}
