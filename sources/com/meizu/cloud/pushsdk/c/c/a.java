package com.meizu.cloud.pushsdk.c.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.sdk.m.n.d;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.yc.utesdk.utils.close.AESUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static a f19176a;

    /* renamed from: b, reason: collision with root package name */
    private static final Object f19177b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private byte[] f19178c;

    /* renamed from: d, reason: collision with root package name */
    private byte[] f19179d;

    /* renamed from: e, reason: collision with root package name */
    private byte[] f19180e;

    /* renamed from: f, reason: collision with root package name */
    private byte[] f19181f;

    /* renamed from: g, reason: collision with root package name */
    private byte[] f19182g;

    /* renamed from: h, reason: collision with root package name */
    private PublicKey f19183h;

    /* renamed from: i, reason: collision with root package name */
    private final SharedPreferences f19184i;

    /* renamed from: j, reason: collision with root package name */
    private final SharedPreferences f19185j;

    /* renamed from: k, reason: collision with root package name */
    private long f19186k = 0;

    private a(Context context) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, NumberFormatException, InvalidKeyException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.x.y.1", 0);
        this.f19184i = sharedPreferences;
        this.f19185j = context.getSharedPreferences("com.x.y.2", 0);
        Integer.parseInt(sharedPreferences.getString("keyTimeout", "0"));
        sharedPreferences.getLong("createDate", 0L);
        h();
        byte[] bArr = this.f19178c;
        if (bArr != null && bArr.length != 0) {
            byte[] bArr2 = this.f19179d;
            if (bArr2 == null || bArr2.length == 0) {
                PublicKey publicKeyB = b(context);
                this.f19183h = publicKeyB;
                if (publicKeyB != null) {
                    b();
                    return;
                }
                return;
            }
            return;
        }
        PublicKey publicKeyB2 = b(context);
        this.f19183h = publicKeyB2;
        if (publicKeyB2 != null) {
            c();
            return;
        }
        sharedPreferences.edit().clear().apply();
        try {
            a();
            PublicKey publicKeyB3 = b(context);
            this.f19183h = publicKeyB3;
            if (publicKeyB3 != null) {
                c();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private String a(InputStream inputStream) throws IOException {
        String string;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            try {
                int i2 = inputStream.read();
                if (i2 == -1) {
                    break;
                }
                byteArrayOutputStream.write(i2);
            } catch (IOException unused) {
                string = null;
            } catch (Throwable th) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
        }
        string = byteArrayOutputStream.toString();
        try {
            byteArrayOutputStream.close();
        } catch (IOException unused3) {
        }
        return string;
    }

    private PublicKey b(Context context) {
        a("load publicKey from preference");
        String string = this.f19185j.getString("publicKey", "");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return KeyFactory.getInstance(d.f9568a).generatePublic(new X509EncodedKeySpec(Base64.decode(string, 2)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private void c() throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        d();
        b();
    }

    private void d() throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AESUtils.AES);
            keyGenerator.init(128);
            byte[] encoded = keyGenerator.generateKey().getEncoded();
            this.f19178c = encoded;
            this.f19180e = Base64.encode(encoded, 2);
            a("***** rKey64: " + new String(this.f19180e));
            SharedPreferences.Editor editorEdit = this.f19184i.edit();
            editorEdit.putString("rKey64", new String(this.f19180e));
            editorEdit.apply();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static a e() {
        a aVar = f19176a;
        if (aVar != null) {
            return aVar;
        }
        throw new IllegalStateException("KeyMgr is not initialised - invoke at least once with parameterised init/get");
    }

    private void h() {
        a("loadKeys");
        String string = this.f19184i.getString("sKey64", "");
        a("saved sKey64: " + string);
        if (!TextUtils.isEmpty(string)) {
            this.f19182g = string.getBytes();
        }
        String string2 = this.f19184i.getString("aKey64", "");
        a("saved aKey64: " + string2);
        if (!TextUtils.isEmpty(string2)) {
            byte[] bytes = string2.getBytes();
            this.f19181f = bytes;
            this.f19179d = Base64.decode(bytes, 2);
        }
        String string3 = this.f19184i.getString("rKey64", "");
        a("saved rKey64: " + string3);
        if (TextUtils.isEmpty(string3)) {
            return;
        }
        byte[] bytes2 = string3.getBytes();
        this.f19180e = bytes2;
        this.f19178c = Base64.decode(bytes2, 2);
        a("saved rKey: " + new String(this.f19178c));
    }

    public byte[] f() {
        return this.f19181f;
    }

    public byte[] g() {
        return this.f19182g;
    }

    private void a() throws IOException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(PushConstants.URL_DOWNLOAD_PUBLIC_KEY).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            try {
                httpURLConnection.setRequestMethod("GET");
            } catch (ProtocolException e2) {
                e2.printStackTrace();
            }
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            InputStream inputStream = null;
            try {
                a("code = " + httpURLConnection.getResponseCode());
                inputStream = httpURLConnection.getInputStream();
                if (inputStream != null) {
                    String strA = a(inputStream);
                    a("body = " + strA);
                    if (!TextUtils.isEmpty(strA)) {
                        try {
                            JSONObject jSONObject = new JSONObject(strA);
                            if (jSONObject.getInt("code") == 200) {
                                String string = jSONObject.getString("value");
                                SharedPreferences.Editor editorEdit = this.f19185j.edit();
                                editorEdit.putString("publicKey", string);
                                editorEdit.apply();
                            }
                        } catch (Exception e3) {
                            b("downloadPublicKey message error " + e3.getMessage());
                        }
                    }
                }
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused) {
                    }
                }
                httpURLConnection.disconnect();
            }
        } catch (MalformedURLException unused2) {
        }
    }

    private void b() throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, this.f19183h);
            byte[] bArrDoFinal = cipher.doFinal(this.f19178c);
            this.f19179d = bArrDoFinal;
            this.f19181f = Base64.encode(bArrDoFinal, 2);
            a("***** aKey64: " + new String(this.f19181f));
            SharedPreferences.Editor editorEdit = this.f19184i.edit();
            editorEdit.putString("aKey64", new String(this.f19181f));
            editorEdit.apply();
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | Exception e2) {
            e2.printStackTrace();
        }
    }

    public void c(String str) {
        this.f19182g = str.getBytes();
        SharedPreferences.Editor editorEdit = this.f19184i.edit();
        editorEdit.putString("sKey64", new String(this.f19182g));
        editorEdit.apply();
    }

    public static void a(Context context) {
        if (f19176a == null) {
            synchronized (f19177b) {
                try {
                    if (f19176a == null) {
                        f19176a = new a(context);
                    }
                } finally {
                }
            }
        }
    }

    private void b(String str) {
        DebugLogger.e("HttpKeyMgr", str);
    }

    private void a(String str) {
        DebugLogger.d("HttpKeyMgr", str);
    }

    public byte[] a(byte[] bArr) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        String str;
        byte[] bArr2 = this.f19178c;
        if (bArr2 == null || bArr2.length == 0) {
            str = "rKey null!";
        } else {
            if (bArr != null && bArr.length != 0) {
                a(">>>>>>>>>> encrypt input >>>>>>>>>>\n" + new String(Base64.encode(bArr, 2)));
                a("<<<<<<<<<< encrypt input <<<<<<<<<<");
                try {
                    Cipher cipher = Cipher.getInstance(AESUtils.AES_ALGORITHM);
                    cipher.init(1, new SecretKeySpec(this.f19178c, AESUtils.AES), new IvParameterSpec(this.f19178c));
                    byte[] bArrDoFinal = cipher.doFinal(bArr);
                    a(">>>>>>>>>> encrypt output >>>>>>>>>>\n" + new String(Base64.encode(bArrDoFinal, 2)));
                    a("<<<<<<<<<< encrypt output <<<<<<<<<<");
                    return bArrDoFinal;
                } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | Exception e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
            str = "input null!";
        }
        b(str);
        return null;
    }
}
