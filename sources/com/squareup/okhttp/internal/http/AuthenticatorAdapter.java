package com.squareup.okhttp.internal.http;

import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;

/* loaded from: classes4.dex */
public final class AuthenticatorAdapter implements Authenticator {
    public static final Authenticator INSTANCE = new AuthenticatorAdapter();

    private InetAddress getConnectToInetAddress(Proxy proxy, HttpUrl httpUrl) throws IOException {
        return (proxy == null || proxy.type() == Proxy.Type.DIRECT) ? InetAddress.getByName(httpUrl.host()) : ((InetSocketAddress) proxy.address()).getAddress();
    }

    @Override // com.squareup.okhttp.Authenticator
    public Request authenticate(Proxy proxy, Response response) throws IOException {
        PasswordAuthentication passwordAuthenticationRequestPasswordAuthentication;
        List<Challenge> listChallenges = response.challenges();
        Request request = response.request();
        HttpUrl httpUrl = request.httpUrl();
        int size = listChallenges.size();
        for (int i2 = 0; i2 < size; i2++) {
            Challenge challenge = listChallenges.get(i2);
            if ("Basic".equalsIgnoreCase(challenge.getScheme()) && (passwordAuthenticationRequestPasswordAuthentication = java.net.Authenticator.requestPasswordAuthentication(httpUrl.host(), getConnectToInetAddress(proxy, httpUrl), httpUrl.port(), httpUrl.scheme(), challenge.getRealm(), challenge.getScheme(), httpUrl.url(), Authenticator.RequestorType.SERVER)) != null) {
                return request.newBuilder().header("Authorization", Credentials.basic(passwordAuthenticationRequestPasswordAuthentication.getUserName(), new String(passwordAuthenticationRequestPasswordAuthentication.getPassword()))).build();
            }
        }
        return null;
    }

    @Override // com.squareup.okhttp.Authenticator
    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
        List<Challenge> listChallenges = response.challenges();
        Request request = response.request();
        HttpUrl httpUrl = request.httpUrl();
        int size = listChallenges.size();
        for (int i2 = 0; i2 < size; i2++) {
            Challenge challenge = listChallenges.get(i2);
            if ("Basic".equalsIgnoreCase(challenge.getScheme())) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) proxy.address();
                PasswordAuthentication passwordAuthenticationRequestPasswordAuthentication = java.net.Authenticator.requestPasswordAuthentication(inetSocketAddress.getHostName(), getConnectToInetAddress(proxy, httpUrl), inetSocketAddress.getPort(), httpUrl.scheme(), challenge.getRealm(), challenge.getScheme(), httpUrl.url(), Authenticator.RequestorType.PROXY);
                if (passwordAuthenticationRequestPasswordAuthentication != null) {
                    return request.newBuilder().header(HttpHeaders.PROXY_AUTHORIZATION, Credentials.basic(passwordAuthenticationRequestPasswordAuthentication.getUserName(), new String(passwordAuthenticationRequestPasswordAuthentication.getPassword()))).build();
                }
            }
        }
        return null;
    }
}
