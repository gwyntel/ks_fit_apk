package com.aliyun.alink.linksdk.securesigner.crypto;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Base64;
import com.aliyun.alink.linksdk.securesigner.SecurityImpl;
import com.aliyun.alink.linksdk.securesigner.util.AesEncryptor;
import com.aliyun.alink.linksdk.securesigner.util.SafeStorageUtil;
import com.yc.utesdk.utils.close.AESUtils;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes2.dex */
public class KeystoreSecureStorage implements SecureStorage {
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS7Padding";
    private static final String IV = "_iv";
    private static final String KEYSTORE_PROVIDER = "AndroidKeyStore";
    private static final String KEY_ALIAS = "IOT-KeyAlias";
    private static final String SAFE_KEY_LEVEL21 = "safe-key-level-21";
    private static final String SHARED_PREFS_NAME = "SecureDataStore";
    private static Context context;
    private static volatile KeystoreSecureStorage instance;
    private String afterEncryption;
    private KeyStore keyStore;
    SecurityImpl securityIml;
    private SharedPreferences sharedPreferences;

    private KeystoreSecureStorage(Context context2) throws Exception {
        this.sharedPreferences = context2.getSharedPreferences(SHARED_PREFS_NAME, 0);
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER);
        this.keyStore = keyStore;
        keyStore.load(null);
        createKeyIfNotExists();
        SecurityImpl securityImpl = new SecurityImpl();
        this.securityIml = securityImpl;
        if (securityImpl.getAppKey() != null) {
            this.afterEncryption = this.securityIml.sign(SAFE_KEY_LEVEL21 + SafeStorageUtil.getDeviceId(context2), "HmacSHA1").substring(0, 16);
        }
    }

    private void createKeyIfNotExists() throws Exception {
        if (this.keyStore.containsAlias(KEY_ALIAS)) {
            return;
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AESUtils.AES, KEYSTORE_PROVIDER);
        keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_ALIAS, 3).setBlockModes("CBC").setEncryptionPaddings("PKCS7Padding").build());
        keyGenerator.generateKey();
    }

    public static KeystoreSecureStorage getInstance(Context context2) {
        if (instance == null) {
            synchronized (KeystoreSecureStorage.class) {
                if (instance == null) {
                    try {
                        Context applicationContext = context2.getApplicationContext();
                        context = applicationContext;
                        instance = new KeystoreSecureStorage(applicationContext);
                    } catch (KeyStoreException e2) {
                        e = e2;
                        e.printStackTrace();
                        return instance;
                    } catch (NoSuchAlgorithmException e3) {
                        e = e3;
                        e.printStackTrace();
                        return instance;
                    } catch (Exception e4) {
                        throw new RuntimeException(e4);
                    }
                }
            }
        }
        return instance;
    }

    @Override // com.aliyun.alink.linksdk.securesigner.crypto.SecureStorage
    public String get(String str) throws NoSuchPaddingException, SecureStorageException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        String string = this.sharedPreferences.getString(str, null);
        String string2 = this.sharedPreferences.getString(str + IV, null);
        if (string == null) {
            return null;
        }
        try {
            SecretKey secretKey = (SecretKey) this.keyStore.getKey(KEY_ALIAS, null);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(2, secretKey, new IvParameterSpec(Base64.decode(string2, 0)));
            return new String(cipher.doFinal(Base64.decode(string, 0)), StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e2) {
            throw new SecureStorageException("Failed to decrypt and retrieve data", e2);
        }
    }

    @Override // com.aliyun.alink.linksdk.securesigner.crypto.SecureStorage
    public void put(String str, String str2) throws NoSuchPaddingException, SecureStorageException, NoSuchAlgorithmException, InvalidKeyException {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        try {
            SecretKey secretKey = (SecretKey) this.keyStore.getKey(KEY_ALIAS, null);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(1, secretKey);
            editorEdit.putString(str, Base64.encodeToString(cipher.doFinal(str2.getBytes(StandardCharsets.UTF_8)), 0));
            editorEdit.putString(str + IV, Base64.encodeToString(cipher.getIV(), 0)).apply();
            editorEdit.apply();
        } catch (GeneralSecurityException e2) {
            throw new SecureStorageException("Failed to encrypt and save data", e2);
        }
    }

    @Override // com.aliyun.alink.linksdk.securesigner.crypto.SecureStorage
    public void remove(String str) throws SecureStorageException {
        try {
            this.sharedPreferences.edit().remove(str).apply();
        } catch (Exception e2) {
            throw new SecureStorageException("Failed to remove data from secure storage", e2);
        }
    }

    public String safeDecrypt(String str) {
        try {
            return AesEncryptor.decrypt(this.afterEncryption, str);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public String safeEncrypt(String str) {
        try {
            return AesEncryptor.encrypt(this.afterEncryption, str);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
}
